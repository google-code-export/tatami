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
package com.objetdirect.tatami.client.dnd;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.objetdirect.tatami.client.DojoAfterCreationEventsSource;
import com.objetdirect.tatami.client.DojoAfterCreationListener;
import com.objetdirect.tatami.client.DojoController;
import com.objetdirect.tatami.client.tree.TreeItem;

/**
 * This class is the tree drag and drop controller. It contains the dojo widget's definition 
 * for a tree source, and the methods to register a tree source or a tree target.
 * 
 * @author rdunklau
 *
 */
public class DnDTreeController extends IDnDController<TreeSource, TreeTarget> implements DojoAfterCreationListener{
	

		private static DnDTreeController instance;
		
		private Collection<TreeSource> sourcesWaitingToBeRegistered = new ArrayList<TreeSource>();
		
		private Collection<TreeTarget> targetsWaitingToBeRegistered = new ArrayList<TreeTarget>();
		
		private DnDTreeController(){
			super();
			DojoController.getInstance().require("dijit._tree.dndSource");
			declareDojoWidgetSource();
		}
		
		/**
		 * @return The DnDTreeController singleton
		 */
		public static DnDTreeController getInstance(){
			if(instance == null){
				instance = new DnDTreeController();
			}
			return instance;
		}
		
		/*
		 * GWT/Dojo interaction : source and target management
		 * 
		 */
		
		/**
		 * Adds the given jsSource reference to given jsTree. It is used to register the tree as 
		 * a dojo tree. 
		 * 
		 * @param jsTree
		 * @param jsSource
		 * @return
		 */
		private native JavaScriptObject prepareTree(JavaScriptObject jsTree , JavaScriptObject jsSource)/*-{
			jsTree.dndController = jsSource;
			return jsTree;
		}-*/;
		
		
		/**
		 * Creates a dojo treesource from a tree and a controller 
		 * 
		 * @param jsTree
		 * @param dndController
		 * @return
		 */
		private native JavaScriptObject createJSSourceFromDOM(JavaScriptObject jsTree , DnDMainController dndController)/*-{
			var dndParams = {};
			var dndSource = new $wnd.dojox.tatami._tree.dndSource(jsTree,dndParams);
			dndSource.gwtDndController = dndController;
			return dndSource;
		}-*/;
		
		
		/**
		 * Creates a dojo treetarget from a tree and a controller
		 * 
		 * @param domNode
		 * @param dndController
		 * @return
		 */
		private native JavaScriptObject createJSTargetFromDOM(JavaScriptObject jsTree , DnDMainController dndController)/*-{
			var dndParams = {};
			var dndTarget = new $wnd.dojox.tatami._tree.dndTarget(jsTree,dndParams);
			dndTarget.gwtDndController = dndController;
			jsTree.dndController = dndTarget;
			return dndTarget;
		}-*/;
		
		private static JavaScriptObject getChildByEvent(Event e){
			Element node = (Element) e.getTarget();
			while(node != null){
				if(node.getClassName().contains("dijitTreeContent")){
					return node;
				}
				node = (Element) node.getParentElement();
			}
			return null;
		}
		
		/**
		 * Declare tatami treedndsource and tree dndtarget dojo class. 
		 */
		private native void declareDojoWidgetSource()/*-{	
			$wnd.dojo.declare("dojox.tatami._tree.dndSource" , $wnd.dijit._tree.dndSource , {
			checkAcceptance: function(source, nodes){
				var accepted = this.gwtDndController.@com.objetdirect.tatami.client.dnd.DnDMainController::checkAcceptance(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(source,this,nodes);
				return accepted;
			},
			_getChildByEvent: function(e){
				return @com.objetdirect.tatami.client.dnd.DnDTreeController::getChildByEvent(Lcom/google/gwt/user/client/Event;)(e);
			},
			getItemFromJSNode : function(node){
				var childTreeNode = $wnd.dijit.getEnclosingWidget(node);
				var childItem = childTreeNode.item;
				if(childItem == this.tree.model.root){
					return null;
				}
				return childItem;
			},
			onDndDrop: function(source, nodes, copy){
				if(this.containerState == "Over"){
					var tree = this.tree,
						model = tree.model,
						target = this.current,
						requeryRoot = false;	// set to true iff top level items change
					this.isDragging = false;
					var targetWidget = $wnd.dijit.getEnclosingWidget(target),
					newParentItem = (targetWidget && targetWidget.item) || tree.item;
					this.gwtDndController.@com.objetdirect.tatami.client.dnd.DnDMainController::onDndDrop(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Z)(source,this,nodes,this.store.getIdentity(newParentItem),copy);
					this.tree._expandNode(targetWidget);
				}
				this.onDndCancel();
			},
			onDndStart: function(source, nodes, copy){
				this.gwtDndController.@com.objetdirect.tatami.client.dnd.DnDMainController::onDnDStart(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Z)(source,nodes,copy);
				if(this.isSource){
					this._changeState("Source", this == source ? (copy ? "Copied" : "Moved") : "");
				}
				var accepted = this.checkAcceptance(source, nodes);
				this._changeState("Target", accepted ? "" : "Disabled");
				if(accepted){
					$wnd.dojo.dnd.manager().overSource(this);
				}
				this.isDragging = true;
			}
		});
		$wnd.dojo.declare("dojox.tatami._tree.dndTarget", $wnd.dojox.tatami._tree.dndSource, {
			// summary: a Target object, which can be used as a DnD target
			constructor: function(node, params){
				// summary: a constructor of the Target --- see the Source constructor for details
				this.isSource = false;
				$wnd.dojo.removeClass(this.node, "dojoDndSource");
			},
		
			// markup methods
			markupFactory: function(params, node){
				params._skipStartup = true;
				return new $wnd.dojox.tatami._tree.dndTarget(node, params);
			}
		});
		
		}-*/;


		/* (non-Javadoc)
		 * @see com.objetdirect.tatami.client.dnd.IDnDController#cleanupSource(com.objetdirect.tatami.client.dnd.IDnDSource)
		 */
		@Override
		public void cleanupSource(TreeSource source) {
			destroyJSSource(gwtToDojoSourceMap.get(source));
		}

		/* (non-Javadoc)
		 * @see com.objetdirect.tatami.client.dnd.IDnDController#cleanupTarget(com.objetdirect.tatami.client.dnd.IDnDTarget)
		 */
		@Override
		public void cleanupTarget(TreeTarget target) {
			destroyJSSource(gwtToDojoTargetMap.get(target));
		}

		/* (non-Javadoc)
		 * @see com.objetdirect.tatami.client.dnd.IDnDController#createAndSetupJSSource(com.objetdirect.tatami.client.dnd.IDnDSource)
		 */
		@Override
		public JavaScriptObject createAndSetupJSSource(TreeSource source) throws JSSourceCreationException {
			JavaScriptObject jsTree = source.getTree().getDojoWidget();
			if(jsTree == null){
				source.getTree().addAfterCreationListener(this);
				sourcesWaitingToBeRegistered.add(source);
				throw new JSSourceCreationException("The tree you would like to turn into a Tree DnD Source is not yet ready. The TreeSource will be created and registered as soon as the Tree is ready");
			}
			JavaScriptObject jsSource = createJSSourceFromDOM(jsTree ,  DnDMainController.getInstance());
			jsTree = prepareTree(jsTree, jsSource);
			return jsSource;
		}

		/* (non-Javadoc)
		 * @see com.objetdirect.tatami.client.dnd.IDnDController#createAndSetupJSTarget(com.objetdirect.tatami.client.dnd.IDnDTarget)
		 */
		@Override
		public JavaScriptObject createAndSetupJSTarget(TreeTarget target) throws JSSourceCreationException{
			JavaScriptObject jsTree = target.getTree().getDojoWidget();
			if(jsTree == null){
				target.getTree().addAfterCreationListener(this);
				targetsWaitingToBeRegistered.add(target);
				throw new JSSourceCreationException("The tree you would like to turn into a Tree DnD Source is not yet ready. The TreeSource will be created and registered as soon as the Tree is ready");
			}
			JavaScriptObject jstarget = createJSTargetFromDOM(jsTree ,  DnDMainController.getInstance());
			jsTree = prepareTree(jsTree, jstarget);
			return jstarget;
		}

		/* (non-Javadoc)
		 * @see com.objetdirect.tatami.client.dnd.IDnDController#getDndElementAtGivenIndex(int, com.google.gwt.core.client.JavaScriptObject, com.google.gwt.core.client.JavaScriptObject)
		 */
		@Override
		public IDnDElement getDndElementAtGivenIndex(int index , JavaScriptObject source , JavaScriptObject nodesArray ){
			TreeItem item = getItemFromJavascriptArray(index, source , nodesArray);
			return new DndTreeElement(item);
		}
		
		/**
		 * This convenience method is used by getDnDElementAtGivenIndex to return a tree item from the javascrip array
		 * 
		 * @param index
		 * @param source
		 * @param nodesArray
		 * @return
		 */
		private native TreeItem getItemFromJavascriptArray(int index , JavaScriptObject source , JavaScriptObject nodesArray )/*-{
			var toReturn = source.getItemFromJSNode(nodesArray[index]); 
			return toReturn;
		}-*/;
		
		/* (non-Javadoc)
		 * @see com.objetdirect.tatami.client.dnd.IDnDController#getJSSourceFromGWTSource(com.objetdirect.tatami.client.dnd.IDnDSource)
		 */
		@Override
		public JavaScriptObject getJSSourceFromGWTSource(TreeSource source) {
			return gwtToDojoSourceMap.get(source); 
		}

		/* (non-Javadoc)
		 * @see com.objetdirect.tatami.client.dnd.IDnDController#getJSTargetFromGWTTarget(com.objetdirect.tatami.client.dnd.IDnDTarget)
		 */
		@Override
		public JavaScriptObject getJSTargetFromGWTTarget(TreeTarget target) {
			return gwtToDojoTargetMap.get(target);
		}
		
		/* (non-Javadoc)
		 * @see com.objetdirect.tatami.client.dnd.IDnDController#addDnDElementToJSSource(com.objetdirect.tatami.client.dnd.IDnDSource, com.objetdirect.tatami.client.dnd.IDnDElement)
		 */
		@Override
		public void addDnDElementToJSSource(TreeSource source, IDnDElement element){
			JavaScriptObject jsSource = this.getJSSourceFromGWTSource(source);
			element.setSource(source);
			addElementToJSSource(jsSource, element , element.getDndId() , element.getElement());
		}
		
		
		/**
		 * Adds an element to a javascript source
		 * 
		 * @param source
		 * @param element
		 * @param id
		 * @param dndElementDomElement
		 */
		private native void addElementToJSSource(JavaScriptObject source ,IDnDElement element,  String id , Element dndElementDomElement)/*-{
			$wnd.dojo.addClass(dndElementDomElement, "dojoDndItem");
			source.setItem(id, {data: [element], type: ["all"]});
		}-*/;

		/**
		 * Remove an element from a javascript source
		 * 
		 * @param source
		 * @param id
		 * @param dndElementDomElement
		 */
		private native void removeElementFromJSSource(JavaScriptObject source ,  String id , Element dndElementDomElement)/*-{
			$wnd.dojo.removeClass(dndElementDomElement, "dojoDndItem");
			source.delItem(id);
		}-*/;
		
		/* (non-Javadoc)
		 * @see com.objetdirect.tatami.client.dnd.IDnDController#removeDnDElementFromJSSource(com.objetdirect.tatami.client.dnd.IDnDSource, com.objetdirect.tatami.client.dnd.IDnDElement)
		 */
		public void removeDnDElementFromJSSource(TreeSource source , IDnDElement draggable){
			JavaScriptObject jsSource = (JavaScriptObject) gwtToDojoSourceMap.get(source);
			removeElementFromJSSource(jsSource,draggable.getDndId() , draggable.getElement());
			DnDMainController.getInstance().unregisterDnDSource(jsSource);
		}
		

		/**
		 * @param source the javascript source object to destroy
		 */
		private native void destroyJSSource(JavaScriptObject source)/*-{
			source.destroy();
		}-*/;

		/* (non-Javadoc)
		 * @see com.objetdirect.tatami.client.DojoAfterCreationListener#dojoAfterCreation(com.objetdirect.tatami.client.DojoAfterCreationEventsSource)
		 */
		public void dojoAfterCreation(DojoAfterCreationEventsSource source) {
			for (TreeSource treesource : sourcesWaitingToBeRegistered) {
				super.registerSource(treesource);
			}
			for (TreeTarget treetarget : targetsWaitingToBeRegistered) {
				super.registerTarget(treetarget);
			}
			
		}
		
		
}
