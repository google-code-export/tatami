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
import com.google.gwt.user.client.Window;
import com.objetdirect.tatami.client.AbstractDojo;
import com.objetdirect.tatami.client.DojoAfterCreationEventsSource;
import com.objetdirect.tatami.client.DojoAfterCreationListener;
import com.objetdirect.tatami.client.DojoController;
import com.objetdirect.tatami.client.JSHelper;
import com.objetdirect.tatami.client.data.AbstractDataStore;
import com.objetdirect.tatami.client.data.DatumChangeListener;
import com.objetdirect.tatami.client.data.DefaultDataStore;
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

	final public static String labelClassAttribute = "labelClass";
	final public static String leafClassAttribute = "leaf-class";
	final public static String folderClosedClassAttribute = "folder-closed-class";
	final public static String folderOpenedClassAttribute =	"folder-open-class";
	private  String rootCriteriaName = "___tatami__root_attr";
	

	private  Object rootCriteriaValue = Boolean.TRUE;
	private Item rootItem;
	private boolean showRoot = true;
	

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
	
	

	private Collection<DojoAfterCreationListener> afterCreationListeners = new ArrayList<DojoAfterCreationListener>();
	



	/**
	 * Default constructor. Instantiates a tree with the default TreeStore
	 */
	public Tree(){
		this(new DefaultDataStore());
	}
	
	public Tree(Item item){
		this();
		setRootItem(item);
	}
	
	public Tree(Item item , AbstractDataStore store){
		this(store);
		setRootItem(item);
	}
	
	public Item getRootItem() {
		return rootItem;
	}

	public void setRootItem(Item rootItem) {
		this.rootItem = rootItem;
		rootItem.setValue(rootCriteriaName, rootCriteriaValue);
		store.add(rootItem);
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
		this.dojoWidget = createDojoTree(store.getDojoWidget() , this , showRoot);
	}
	
	private native JavaScriptObject  createDojoTree(JavaScriptObject dataStore , Tree gwtWidget , boolean showRoot)/*-{
		var rootQuery = {};
		rootQuery[this.@com.objetdirect.tatami.client.tree.Tree::rootCriteriaName]  = this.@com.objetdirect.tatami.client.tree.Tree::rootCriteriaValue;
		var model = new $wnd.dojox.tree.TatamiTreeStoreModel({store : dataStore , gwtWidget : gwtWidget , query : rootQuery});
		var tree = new $wnd.dojox.tree.TatamiTree({model : model , persist: false ,gwtWidget : gwtWidget,query : rootQuery , showRoot : showRoot});
		tree.domNode.height = "100%";
		tree.domNode.width = "100%";
		return tree;
	}-*/;
	

	public String getDojoName() {
		return "dijit.Tree";
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
		$wnd.dojo.declare("dojox.tree.TatamiTree" , [$wnd.dijit.Tree] , {
			getIconClass : function(item , opened){
				var iconClass;
				iconClass = this.gwtWidget.@com.objetdirect.tatami.client.tree.Tree::getIconClass(Lcom/objetdirect/tatami/client/data/Item;Z)(item,opened);
				return iconClass;
			},
			getLabelClass : function(item){
				return this.gwtWidget.@com.objetdirect.tatami.client.tree.Tree::getLabelClass(Lcom/objetdirect/tatami/client/data/Item;)(item);
			},
			onClick: function( item, node){
				this.gwtWidget.@com.objetdirect.tatami.client.tree.Tree::propagateClick(Lcom/objetdirect/tatami/client/data/Item;)(item);
			},
			onDblClick: function( item, node){
				this.gwtWidget.@com.objetdirect.tatami.client.tree.Tree::propagateDblClick(Lcom/objetdirect/tatami/client/data/Item;)(item);
			},
			onOpen: function(item,  node){
				this.gwtWidget.@com.objetdirect.tatami.client.tree.Tree::propagateOpen(Lcom/objetdirect/tatami/client/data/Item;)(item);
			},
			onClose: function(item,node){
				this.gwtWidget.@com.objetdirect.tatami.client.tree.Tree::propagateClose(Lcom/objetdirect/tatami/client/data/Item;)(item);
			}
		});
		$wnd.dojo.declare("dojox.tree.TatamiTreeStoreModel" , $wnd.dijit.tree.TreeStoreModel , {
			childrenAttrs : [@com.objetdirect.tatami.client.data.Item::childAttribute],
			pasteItem: function( childItem,  oldParentItem,  newParentItem,  bCopy){
				this.oldParentItem.@com.objetdirect.tatami.client.data.Item::removeChild(Lcom/objetdirect/tatami/client/data/Item;)(childItem);
				this.newParentItem.@com.objetdirect.tatami.client.data.Item::addChild(Lcom/objetdirect/tatami/client/data/Item;)(childItem);
			},
			mayHaveChildren: function(item){
				return this.gwtWidget.@com.objetdirect.tatami.client.tree.Tree::mayHaveChildren(Lcom/objetdirect/tatami/client/data/Item;)(item);
			},
			_onNewItem: function(item, parentInfo){
			},
			getChildren : function(parentItem,callback,onError){
				var query = {};
				query[@com.objetdirect.tatami.client.data.Item::parentAttribute] = parentItem;
				this.store.fetch({
					query: {parent : parentItem},
					onComplete: $wnd.dojo.hitch(this, function(items){
						callback(items);
					}),
					onError: onError
				});
			}
		});
	}-*/;
	
	/**
	 * Notifies the tree listeners that a onClick event occured on the item
	 * 
	 * @param item : the item on wich the onClick event has occured 
	 */
	private void propagateClick(Item item){
		for (Iterator<TreeListener> iterator = treeListeners.iterator(); iterator.hasNext();) {
			TreeListener listener =  iterator.next();
			listener.onClick(item);
		}
	}
	
	private void propagateDblClick(Item item){
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
	private void propagateOpen(Item item){
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
	private void propagateClose(Item item){
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
	public boolean mayHaveChildren(Item item){
		List<Item> children = item.getChildren();
		return children != null ? children.size() > 0 : false;
	}
	
	/**
	 * @param item: adds an item to the underlying store.
	 */
	protected void addItem(Item item){
		this.store.add(item);
	}
	
	/**
	 * @param item the item to remove from the underlying store 
	 */
	public void removeItem(Item item){
		store.remove(item);
		item.getParentItem().removeChild(item);
	}
	
	
	
	@Override
	public void onDojoLoad() {
		defineTatamiTree();
	}
	

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.FetchListener#onBegin(com.objetdirect.tatami.client.data.FetchEventSource, int, com.objetdirect.tatami.client.data.Request)
	 */
	public void onBegin(FetchEventSource source, int size, Request request) {
		
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






	public void addAfterCreationListener(DojoAfterCreationListener listener) {
		if(afterCreationListeners.contains(listener))
			return;
		afterCreationListeners.add(listener);
	}



	public void removeAfterCreationListener(DojoAfterCreationListener listener) {
		afterCreationListeners.remove(listener);
	}
	
	private String getIconClass(Item item , boolean opened ){
		String iconClass = item.getChildren() != null ?  (opened ? (String)item.getValue(folderOpenedClassAttribute,null) : (String)item.getValue(folderClosedClassAttribute,null) ) : (String)item.getValue(leafClassAttribute,null);
		if(iconClass == null){
			if(item.hasAttribute(Item.childAttribute)){
				if(opened){
					iconClass = defaultFolderOpenClass;
				}else{
					iconClass = defaultFolderClosedClass;
				}
			}else{
				iconClass = defaultLeafClass;
			}
		}
		return iconClass;
	}
	
	/**
	 * @param item : the item we want to know the label's class
	 * @return the item label's
	 */
	public String getLabelClass(Item item){
		return (String) item.getValue(labelClassAttribute , null);
	}
	
	
	public boolean isShowRoot() {
		return showRoot;
	}

	public void setShowRoot(boolean showRoot) {
		this.showRoot = showRoot;
	}
	
	public String getRootCriteriaName() {
		return rootCriteriaName;
	}

	public void setRootCriteriaName(String rootCriteriaName) {
		this.rootCriteriaName = rootCriteriaName;
	}

	public Object getRootCriteriaValue() {
		return rootCriteriaValue;
	}

	public void setRootCriteriaValue(Object rootCriteriaValue) {
		this.rootCriteriaValue = rootCriteriaValue;
	}
	
	
}
