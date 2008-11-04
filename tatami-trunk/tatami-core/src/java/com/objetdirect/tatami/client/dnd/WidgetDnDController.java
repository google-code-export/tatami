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


import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.objetdirect.tatami.client.test.Logger;

/**
 * This class is the widget drag and drop controller. It contains the dojo widget's definition 
 * for a standard source, and the methods to register a panel as a  source or a widget target.
 * 
 * @author rdunklau
 *
 */
public class WidgetDnDController extends IDnDController<WidgetSource, WidgetTarget>{

	
	private static WidgetDnDController instance;
	
	/**
	 * Standard private constructor
	 */
	private WidgetDnDController(){
		super();
		declareDojoWidgetSource();
	}
	
	/**
	 * @return the WidgetDnDController singleton
	 */
	public static WidgetDnDController getInstance(){
		if(instance == null){
			instance = new WidgetDnDController();
		}
		return instance;
	}
	
	/*
	 * GWT/Dojo interaction : source and target management
	 * 
	 */
	
	
	/**
	 * @param domNode
	 * @param dndController
	 * @return
	 */
	private native JavaScriptObject createJSSourceFromDOM(JavaScriptObject domNode , DnDMainController dndController)/*-{
		 var jsSource = new $wnd.dojox.tatami.dnd.WidgetSource(domNode, {gwtDndController : dndController,accept: ["all"]});
		 return jsSource;
	}-*/;
	
	
	/**
	 * @param domNode
	 * @param dndController
	 * @return
	 */
	private native JavaScriptObject createJSTargetFromDOM(JavaScriptObject domNode , DnDMainController dndController)/*-{
		var jsSource = new $wnd.dojox.tatami.dnd.WidgetTarget(domNode, {gwtDndController : dndController,accept: ["all"]});
		return jsSource;
	}-*/;
	
	private static JavaScriptObject getChildByEvent(Event e){
		Logger.log("GET CHILD BY EVENT " + e);
		Element node = (Element) e.getTarget();
		Logger.log("TARGET  " + node);
		while(node != null){
			if(node.getClassName().contains("dojoDndItem")){
				Logger.log("FOUND  " + node);
				return node;
			}
			node = (Element) node.getParentElement();
		}
		return null;
	}
	
	
	/**
	 * Defines the Dojo tatami widget source and target objects 
	 */
	private native void declareDojoWidgetSource()/*-{	
		$wnd.dojo.declare("dojox.tatami.dnd.WidgetSource" , $wnd.dojo.dnd.Source , {
			destroy: function(){
				this.gwtDndController = null;
			},
			checkAcceptance: function(source, nodes){
				var accepted = this.gwtDndController.@com.objetdirect.tatami.client.dnd.DnDMainController::checkAcceptance(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(source,this,nodes); 
				return accepted;
			},
			_getChildByEvent: function(e){
				return @com.objetdirect.tatami.client.dnd.WidgetDnDController::getChildByEvent(Lcom/google/gwt/user/client/Event;)(e);
			},
			getItemFromJSNode : function(node){
				return this.getItem(node.id).data[0];
			},
			onDndDrop: function(source, nodes, copy){
				do{ //break box
					if(this.containerState != "Over"){ break; }
					var oldCreator = this._normalizedCreator;
					if(this != source){
					}else{
						// transferring nodes within the single source
						if(this.current && this.current.id in this.selection){ break; }
						if(this.creator){
						}else{
						}
					}
					this._removeSelection();
					if(this != source){
						this._removeAnchor();
					}
					if(this != source && !copy && !this.creator){
						source.selectNone();
					}
					var id = this.current.id == undefined ? null : this.current.id;
					this.gwtDndController.@com.objetdirect.tatami.client.dnd.DnDMainController::onDndDrop(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Z)(source,this,nodes, id,copy);
				}while(false);
				this.onDndCancel();
			},
			onDndStart: function(source, nodes, copy){
				// summary: topic event processor for /dnd/start, called to initiate the DnD operation
				// source: Object: the source which provides items
				// nodes: Array: the list of transferred items
				// copy: Boolean: copy items, if true, move items otherwise
				this.gwtDndController.@com.objetdirect.tatami.client.dnd.DnDMainController::onDnDStart(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Z)(source,nodes,copy);
				if(this.autoSync){ this.sync(); }
				if(this.isSource){
					this._changeState("Source", this == source ? (copy ? "Copied" : "Moved") : "");
				}
				var accepted = this.accept && this.checkAcceptance(source, nodes);
				this._changeState("Target", accepted ? "" : "Disabled");
				if(this == source){
					$wnd.dojo.dnd.manager().overSource(this);
				}
				this.isDragging = true;
			}
		});
		$wnd.dojo.declare("dojox.tatami.dnd.WidgetTarget", $wnd.dojox.tatami.dnd.WidgetSource, {
			constructor: function(node, params){
				// summary: a constructor of the Target --- see the Source constructor for details
				this.isSource = false;
				$wnd.dojo.removeClass(this.node, "dojoDndSource");
			},
			// markup methods
			markupFactory: function(params, node){
				params._skipStartup = true;
				return new $wnd.dojox.tatami.dnd.WidgetTarget(node, params);
			},
			onDndDrop: function(source, nodes, copy){
				do{ //break box
					var oldCreator = this._normalizedCreator;
					if(this != source){
					}else{
						// transferring nodes within the single source
						if(this.current && this.current.id in this.selection){ break; }
						if(this.creator){
						}else{
						}
					}
					this._removeSelection();
					if(this != source){
						this._removeAnchor();
					}
					if(this != source && !copy && !this.creator){
						source.selectNone();
					}
					var id = this.current == undefined ? null : (this.current.id == undefined ? null : this.current.id);
					this.gwtDndController.@com.objetdirect.tatami.client.dnd.DnDMainController::onDndDrop(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Z)(source,this,nodes,id,copy);
				}while(false);
				this.onDndCancel();
			}
		});
	}-*/;


	@Override
	public void cleanupSource(WidgetSource source) {
		destroyJSSource(gwtToDojoSourceMap.get(source));
	}

	@Override
	public void cleanupTarget(WidgetTarget source) {
		destroyJSSource(gwtToDojoTargetMap.get(source));
	}

	@Override
	public JavaScriptObject createAndSetupJSSource(WidgetSource source) {
		return createJSSourceFromDOM(source.getDomElement() ,  DnDMainController.getInstance());
	}

	@Override
	public JavaScriptObject createAndSetupJSTarget(WidgetTarget target) {
		return createJSTargetFromDOM(target.getDomElement() ,  DnDMainController.getInstance());
	}

	@Override
	public native IDnDElement getDndElementAtGivenIndex(int index , JavaScriptObject source , JavaScriptObject nodesArray )/*-{
		var toReturn = source.getItemFromJSNode(nodesArray[index]); 
		return toReturn;
	}-*/;
	
	@Override
	public JavaScriptObject getJSSourceFromGWTSource(WidgetSource source) {
		return gwtToDojoSourceMap.get(source); 
	}

	@Override
	public JavaScriptObject getJSTargetFromGWTTarget(WidgetTarget target) {
		return gwtToDojoTargetMap.get(target);
	}
	
	@Override
	public void addDnDElementToJSSource(WidgetSource source, IDnDElement element){
		JavaScriptObject jsSource = this.getJSSourceFromGWTSource(source);
		element.setSource(source);
		addElementToJSSource(jsSource, element , element.getDndId() , element.getElement());
	}
	
	/**
	 * @param target
	 * @param element
	 */
	public void addDnDElementToJSTarget(WidgetTarget target, IDnDElement element){
		JavaScriptObject jsSource = this.getJSTargetFromGWTTarget(target);
		addElementToJSSource(jsSource, element , element.getDndId() , element.getElement());
	}
	
	
	private native void addElementToJSSource(JavaScriptObject source ,IDnDElement element,  String id , Element dndElementDomElement)/*-{
		$wnd.dojo.addClass(dndElementDomElement, "dojoDndItem");
		source.setItem(id, {data: [element], type: ["all"]});
	}-*/;

	private native void removeElementFromJSSource(JavaScriptObject source ,  String id , Element dndElementDomElement)/*-{
		$wnd.dojo.removeClass(dndElementDomElement, "dojoDndItem");
		source.delItem(id);
	}-*/;
	
	@Override
	public void removeDnDElementFromJSSource(WidgetSource source , IDnDElement draggable){
		JavaScriptObject jsSource = (JavaScriptObject) gwtToDojoSourceMap.get(source);
		source.removeElement((WidgetDnDElement) draggable);
		removeElementFromJSSource(jsSource,draggable.getDndId() , draggable.getElement());
		DnDMainController.getInstance().unregisterDnDSource(jsSource);
	}
	

	private native void destroyJSSource(JavaScriptObject source)/*-{
		source.destroy();
	}-*/;
	
	
}
