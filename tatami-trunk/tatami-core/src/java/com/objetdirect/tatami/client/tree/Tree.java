/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@googlegroups.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * Authors:  Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s):
 */

package com.objetdirect.tatami.client.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.objetdirect.tatami.client.AbstractDojo;
import com.objetdirect.tatami.client.DojoAfterCreationEventsSource;
import com.objetdirect.tatami.client.DojoAfterCreationListener;
import com.objetdirect.tatami.client.DojoController;
import com.objetdirect.tatami.client.JSHelper;
import com.objetdirect.tatami.client.data.AbstractDataStore;
import com.objetdirect.tatami.client.data.DatumChangeListener;
import com.objetdirect.tatami.client.data.FetchEventSource;
import com.objetdirect.tatami.client.data.FetchListener;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.data.Request;


/**
 * @author rdunklau
 *
 * This class wraps dojo's Tree widget, which is connected to the same kind of DataStore as the Grid
 * It provides custom Tree icons, labels.
 * 
 * 
 * It has one "virtual" root (which doe not represent any item on the datastore)
 * which can be displayed or not
 *
 */
public class Tree extends AbstractDojo implements FetchListener , DatumChangeListener , TreeEventsSource , DojoAfterCreationEventsSource{

	/**
	 * The underlying datastore
	 */
	protected AbstractDataStore store;

	/**
	 * Collection of tree listeners to be notified
	 * @see TreeListener
	 */
	private Collection<TreeListener> treeListeners = new ArrayList<TreeListener>();
	
	/**
	 * Datastore's items attribute name to match when querying tree roots
	 * (tree roots are under the virtual root)
	 */
	private String rootCriteriaName = "root";
	
	/**
	 * Datastore's items attribute value to match when querying tree roots
	 * (tree roots are under the virtual root)
	 */
	private Object rootCriteriaValue = Boolean.TRUE;
	
	/**
	 * The css class for the virtual root icon
	 */
	private Object rootIconClass ="dijitFolderOpened";
	
	/**
	 * Default css class to be applied on a tree opened folder
	 */
	private String defaultFolderOpenClass = "dijitFolderOpened";
	
	
	/**
	 * Default css class to be applied on a tree closed folder
	 */
	private String defaultFolderClosedClass = "dijitFolderClosed";
	
	/**
	 * Default css class to be applied on a tree leaf
	 */
	private String defaultLeafClass = "dijitLeaf";
	
	/**
	 * Should the tree show its virtual root ? 
	 */
	private boolean showRoot;
	
	/**
	 * The label to be displayed on the virtual root
	 */
	private String rootLabel = "root";

	private Collection<DojoAfterCreationListener> afterCreationListeners = new ArrayList<DojoAfterCreationListener>();



	/**
	 * Default constructor. Instantiates a tree with the default TreeStore
	 */
	public Tree(){
		this(new TreeStore());
	}
	

	
	/**
	 * @param store The store to use for tree items
	 */
	public Tree(AbstractDataStore store) {
		super(DOM.createElement("div"));
		this.store = store;
		DojoController.getInstance().require(getDojoName());
		store.addFetchListener(this);
		store.addDatumChangeListener(this);
	}

	
	

	
	/**
	 * @return The store to use for tree items
	 */
	public AbstractDataStore getStore() {
		return store;
	}

	/**
	 * @param store The store to use for tree items
	 */
	public void setStore(AbstractDataStore store) {
		this.store.removeFetchListener(this);
		this.store.removeDatumChangeListener(this);
		this.store = store;
		store.addFetchListener(this);
		store.addDatumChangeListener(this);
	}
	
	/**
	 * @return The label to be displayed on the virtual root
	 */
	public String getRootLabel() {
		return rootLabel;
	}

	/**
	 * @param rootLabel The label to be displayed on the virtual root
	 */
	public void setRootLabel(String rootLabel) {
		this.rootLabel = rootLabel;
	}
	


	/**
	 * Refresh the tree, re-querying the top level items with the @link {@link Tree#rootCriteriaName} and the @link {@link #rootCriteriaValue}
	 */
	public void refreshTree(){
		if(dojoWidget != null){
			dojoRefreshTree();
		}
	}

	
	
	private native void dojoRefreshTree()/*-{
		this.@com.objetdirect.tatami.client.AbstractDojo::dojoWidget.model._requeryTop();
	}-*/;
	
	@Override
	public void doAfterCreation() {
		DojoController.getInstance().setGWTWidget(getDojoTreeModel(), this);
		for (DojoAfterCreationListener listener : afterCreationListeners) {
			listener.dojoAfterCreation(this);
		}
		super.doAfterCreation();
	}

	
	
	@Override
	public void doBeforeDestruction() {
		this.store.doBeforeDestruction();
	}
	
	/**
	 * @return the dojo tree model
	 */
	private native JavaScriptObject getDojoTreeModel()/*-{
		return this.@com.objetdirect.tatami.client.AbstractDojo::dojoWidget.model;
	}-*/;


	public void createDojoWidget() throws Exception {
		this.dojoWidget = createDojoTree(store.getDojoWidget() , this , showRoot , rootCriteriaName , rootCriteriaValue.toString(), rootLabel);
	}
	
	private native JavaScriptObject prepareDojoTree(JavaScriptObject dataStore , Tree gwtWidget , boolean showRoot , String rootCriteriaName , String rootCriteriaValue , String rootLabel)/*-{
		var rootQuery = {};
		rootQuery[rootCriteriaName] = rootCriteriaValue;
		var model = new $wnd.dojox.tree.TatamiTreeStoreModel({store : dataStore , gwtWidget : gwtWidget , query : rootQuery , rootLabel : rootLabel, rootId : "___TATAMI_ROOT_NODE___" });
		var tree = new $wnd.dojox.tree.TatamiTree({model : model , persist: false ,gwtWidget : gwtWidget, showRoot : showRoot, query : rootQuery });
		tree.domNode.height = "100%";
		tree.domNode.width = "100%";
		return tree;
	}-*/;
	
	private native JavaScriptObject  createDojoTree(JavaScriptObject dataStore , Tree gwtWidget , boolean showRoot , String rootCriteriaName , String rootCriteriaValue , String rootLabel)/*-{
		var rootQuery = {};
		rootQuery[rootCriteriaName] = rootCriteriaValue;
		var model = new $wnd.dojox.tree.TatamiTreeStoreModel({store : dataStore , gwtWidget : gwtWidget , query : rootQuery , rootLabel : rootLabel, rootId : "___TATAMI_ROOT_NODE___" });
		var tree = new $wnd.dojox.tree.TatamiTree({model : model , persist: false ,gwtWidget : gwtWidget, showRoot : showRoot, query : rootQuery });
		tree.domNode.height = "100%";
		tree.domNode.width = "100%";
		return tree;
	}-*/;
	

	public String getDojoName() {
		return "dijit.Tree";
	}
	
	/**
	 * Adds a child to an item. You have to use it if you want 
	 * the change to be propagated.
	 *  
	 * @param parent : the item to add child to 
	 * @param child : the child item to add to the parent
	 */
	public void addChildToItem(TreeItem parent, TreeItem child){
		if(child.getParentItem() != null){
			removeChildFromItem(child.getParentItem(), child);
		}
		parent.addChild(child);
		store.setValue(parent, "children", parent.getChildren());
	}
	
	public void addChildrenToItem(TreeItem parent, Collection<TreeItem> children){
		for (Iterator<TreeItem> iterator = children.iterator(); iterator.hasNext();) {
			TreeItem treeItem = (TreeItem) iterator.next();
			parent.addChild(treeItem);
		}
		store.setValue(parent, "children", parent.getChildren());
	}
	
	/**
	 * This method SHOULD NOT be called by the developer
	 * 
	 * @param items : Resulting items after the fetch operation
	 * @param onCompleteFunction : the Javascript function to execute
	 */
	private native void proceedJSOnComplete(JavaScriptObject items, JavaScriptObject onCompleteFunction)/*-{
		onCompleteFunction(items);
	}-*/;
	


	/**
	 * Declares the dojo tatami tree widget
	 */
	private native void defineTatamiTree()/*-{
		$wnd.dojo.declare("dojox.tree.TatamiTree" , $wnd.dijit.Tree , {
			getIconClass : function(item , opened){
				if(item == this.model.root){
					return;
				}
				return this.gwtWidget.@com.objetdirect.tatami.client.tree.Tree::getIconClass(Lcom/objetdirect/tatami/client/tree/TreeItem;Z)(item,opened);
			},
			getLabelClass : function(item){
				if(item == this.model.root){
					return;
				}
				return this.gwtWidget.@com.objetdirect.tatami.client.tree.Tree::getLabelClass(Lcom/objetdirect/tatami/client/tree/TreeItem;)(item);
			},
			onClick: function( item, node){
				this.gwtWidget.@com.objetdirect.tatami.client.tree.Tree::propagateClick(Lcom/objetdirect/tatami/client/tree/TreeItem;)(item);
			},
			onDblClick: function( item, node){
				this.gwtWidget.@com.objetdirect.tatami.client.tree.Tree::propagateDblClick(Lcom/objetdirect/tatami/client/tree/TreeItem;)(item);
			},
			onOpen: function(item,  node){
				if(item == this.model.root || item == null){
					return;
				}
				this.gwtWidget.@com.objetdirect.tatami.client.tree.Tree::propagateOpen(Lcom/objetdirect/tatami/client/tree/TreeItem;)(item);
			},
			onClose: function(item,node){
				if(item == this.model.root || item == null){
					return;
				}
				this.gwtWidget.@com.objetdirect.tatami.client.tree.Tree::propagateClose(Lcom/objetdirect/tatami/client/tree/TreeItem;)(item);
			}
		});
		$wnd.dojo.declare("dojox.tree.TatamiTreeStoreModel" , $wnd.dijit.tree.ForestStoreModel , {
			pasteItem: function( childItem,  oldParentItem,  newParentItem,  bCopy){
				this.gwtWidget.@com.objetdirect.tatami.client.tree.Tree::moveItem(Lcom/objetdirect/tatami/client/tree/TreeItem;Lcom/objetdirect/tatami/client/tree/TreeItem;)(childItem , newParentItem == this.root || newParentItem == undefined ? null : newParentItem);
			},
			mayHaveChildren: function(item){
				return this.gwtWidget.@com.objetdirect.tatami.client.tree.Tree::mayHaveChildren(Lcom/objetdirect/tatami/client/tree/TreeItem;)(item);
			},
			getRoot: function(onItem, onError){
				if(this.root){
					onItem(this.root);
				}
			}
		});
	}-*/;
	
	/**
	 * Notifies the tree listeners that a onClick event occured on the item
	 * 
	 * @param item : the item on wich the onClick event has occured 
	 */
	private void propagateClick(TreeItem item){
		for (Iterator<TreeListener> iterator = treeListeners.iterator(); iterator.hasNext();) {
			TreeListener listener =  iterator.next();
			listener.onClick(item);
		}
	}
	
	private void propagateDblClick(TreeItem item){
		for (Iterator<TreeListener> iterator = treeListeners.iterator(); iterator.hasNext();) {
			TreeListener listener =  iterator.next();
			listener.onDblClick(item);
		}
	}
	
	/**
	 * Notifies the tree listeners that a onOpen event occured on the item
	 * 
	 * @param item : the item on wich the onOpen event has occured 
	 */
	private void propagateOpen(TreeItem item){
		for (Iterator<TreeListener> iterator = treeListeners.iterator(); iterator.hasNext();) {
			TreeListener type =  iterator.next();
			type.onOpen(item);
		}
	}
	/**
	 * Notifies the tree listeners that a onClose event occured on the item
	 * 
	 * @param item : the item on wich the onClose event has occured 
	 */
	private void propagateClose(TreeItem item){
		for (Iterator<TreeListener> iterator = treeListeners.iterator(); iterator.hasNext();) {
			TreeListener type =  iterator.next();
			type.onClose(item);
		}
	}
	
	/**
	 * When implementing tree for specific needs (for example, remote loading), you should
	 * maybe override this method.
	 * 
	 * @param item : an item from wich we want to know if it has children
	 * @return true if the item may have children. That means, it "can" have children, but they are not 
	 * necessarily loaded yet, or once loaded it may appear that it doesn't have children at all.
	 * 		  false if the item is definetely a lead
	 */
	public boolean mayHaveChildren(TreeItem item){
		return item.hasAttribute("children");
	}
	
	/**
	 * @param item: adds an item to the underlying store.
	 */
	public void addItem(TreeItem item){
		this.store.add(item);
	}
	
	/**
	 * @param item the item to remove from the underlying store 
	 */
	public void removeItem(TreeItem item){
		store.remove(item);
		removeChildFromItem(item.getParentItem(), item);
	}
	
	/**
	 * @param item : an item to add as a "virtual root" child. That means it will appear as 
	 * a root if the virtual root is not displayed.
	 */
	public void addRootItem(TreeItem item){
		this.store.add(item);
		this.store.setValue(item, rootCriteriaName, rootCriteriaValue);
		if(dojoWidget != null){
			dojoAddRootItem(getDojoTreeModel(), item);
		}
	}
	
	/**
	 * Native method used to add an item to the dojo tree model
	 * 
	 * @param model
	 * @param item
	 */
	private native void dojoAddRootItem(JavaScriptObject model , TreeItem item)/*-{
		model.root.children.push(item);
	}-*/;
	
	@Override
	public void onDojoLoad() {
		defineTatamiTree();
	}
	
	/**
	 * Removes a child item from its parent
	 * 
	 * @param parent
	 * @param child
	 */
	public void removeChildFromItem(TreeItem parent , TreeItem child){
		if(parent == null){
			refreshTree();
		}else{
			parent.removeChild(child);
			store.setValue(parent, "children", parent.getChildren());
		}
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.FetchListener#onBegin(com.objetdirect.tatami.client.data.FetchEventSource, int, com.objetdirect.tatami.client.data.Request)
	 */
	public void onBegin(FetchEventSource source, int size, Request request) {
		
	}

	/**
	 * Moves an item to a new parent.
	 * 
	 * @param itemToMove
	 * @param newParent
	 */
	public void moveItem(TreeItem itemToMove, TreeItem newParent){
		if(newParent == null){
			removeChildFromItem(itemToMove.getParentItem(), itemToMove);
			addRootItem(itemToMove);
		}else{
			if(itemToMove.getParentItem() != null){
				removeChildFromItem(itemToMove.getParentItem(), itemToMove);
			}else{
				itemToMove.removeAttribute(rootCriteriaName);
				refreshTree();
			}
			addChildToItem(newParent, itemToMove);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.FetchListener#onComplete(com.objetdirect.tatami.client.data.FetchEventSource, java.util.List, com.objetdirect.tatami.client.data.Request)
	 */
	public void onComplete(FetchEventSource source, List<?> items, Request request) {
		assert source!=null;
		assert request.getOnCompleteCallback() !=null;
		proceedJSOnComplete(JSHelper.convertObjectToJSObject(items), request.getOnCompleteCallback());
	} 
	

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.FetchListener#onError(com.objetdirect.tatami.client.data.FetchEventSource)
	 */
	public void onError(FetchEventSource source) {
	}


	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.FetchListener#onItem(com.objetdirect.tatami.client.data.FetchEventSource, com.objetdirect.tatami.client.data.Item)
	 */
	public void onItem(FetchEventSource source, Item item) {
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.tree.TreeEventsSource#addTreeListener(com.objetdirect.tatami.client.tree.TreeListener)
	 */
	public boolean addTreeListener(TreeListener listener) {
		return treeListeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.tree.TreeEventsSource#removeTreeListener(com.objetdirect.tatami.client.tree.TreeListener)
	 */
	public boolean removeTreeListener(TreeListener listener) {
		return treeListeners.remove(listener);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.DatumChangeListener#onDataChange(com.objetdirect.tatami.client.data.Item, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void onDataChange(Item item, String attributeName, Object oldValue,
			Object newValue) {
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.DatumChangeListener#onDelete(com.objetdirect.tatami.client.data.Item)
	 */
	public void onDelete(Item item) {
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.DatumChangeListener#onNew(com.objetdirect.tatami.client.data.Item)
	 */
	public void onNew(Item item) {
	}
	
	/**
	 * @return Default css class to apply on a tree opened folder
	 */
	public String getDefaultFolderOpenClass() {
		return defaultFolderOpenClass;
	}

	/**
	 * @param defaultFolderOpenClass : Default css class toapply on a tree opened folder
	 */
	public void setDefaultFolderOpenClass(String defaultFolderOpenClass) {
		this.defaultFolderOpenClass = defaultFolderOpenClass;
	}

	/**
	 * @return Default css class to apply on a tree closed folder
	 */
	public String getDefaultFolderClosedClass() {
		return defaultFolderClosedClass;
	}

	/**
	 * @param defaultFolderClosedClass : Default css class to apply on a tree closed folder
	 */
	public void setDefaultFolderClosedClass(String defaultFolderClosedClass) {
		this.defaultFolderClosedClass = defaultFolderClosedClass;
	}

	/**
	 * @return Default css class to apply to a tree leaf
	 */
	public String getDefaultLeafClass() {
		return defaultLeafClass;
	}

	/**
	 * @param defaultLeafClass Default css class to apply to a tree leaf
	 */
	public void setDefaultLeafClass(String defaultLeafClass) {
		this.defaultLeafClass = defaultLeafClass;
	}

	/**
	 * @return Datastore's items attribute name to match when querying tree roots
	 * (tree roots are under the virtual root)
	 */
	public String getRootCriteriaName() {
		return rootCriteriaName;
	}

	/**
	 * @param rootCriteriaName :Datastore's items attribute name to match when querying tree roots
	 * (tree roots are under the virtual root)
	 */
	public void setRootCriteriaName(String rootCriteriaName) {
		this.rootCriteriaName = rootCriteriaName;
	}
	
	/**
	 * @return Datastore's items attribute value to match when querying tree roots
	 * (tree roots are under the virtual root)
	 */
	public Object getRootCriteriaValue() {
		return rootCriteriaValue;
	}

	/**
	 * @param rootCriteriaValue : Datastore's items attribute value to match when querying tree roots
	 * (tree roots are under the virtual root)
	 */
	public void setRootCriteriaValue(Object rootCriteriaValue) {
		this.rootCriteriaValue = rootCriteriaValue;
	}
	
	
	/**
	 * 
	 * 
	 * @param item : the item we want to know the css class
	 * @param opened
	 * @return the css class applied to this item, according to its state
	 */
	public String getIconClass(TreeItem item , boolean opened ){
		String iconClass = item.hasAttribute("children") ?  (opened ? item.getFolderOpenIconClass() : item.getFolderClosedIconClass() ) : item.getLeafIconClass();
		iconClass = iconClass == null ? (item.hasAttribute("children") ?  (opened ? defaultFolderOpenClass : defaultFolderClosedClass) : defaultLeafClass ) : iconClass;
		return iconClass;
	}
	
	/**
	 * @param item : the item we want to know the label's class
	 * @return the item label's
	 */
	public String getLabelClass(TreeItem item){
		return item.getLabelClass();
	}
	
	

	/**
	 * @return Should the tree show its virtual root ? 
	 */
	public boolean isShowRoot() {
		return showRoot;
	}

	/**
	 * @param showRoot Should the tree show its virtual root ? 
	 */
	public void setShowRoot(boolean showRoot) {
		this.showRoot = showRoot;
	}



	public void addAfterCreationListener(DojoAfterCreationListener listener) {
		afterCreationListeners.add(listener);
	}



	public void removeAfterCreationListener(DojoAfterCreationListener listener) {
		afterCreationListeners.remove(listener);
	}
	
}
