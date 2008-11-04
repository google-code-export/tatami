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

import java.util.Collection;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.DojoController;
import com.objetdirect.tatami.client.dnd.DnDBehaviors.BehaviorScopeException;
import com.objetdirect.tatami.client.tree.Tree;

/**
 * @author rdunklau
 *
 *	This class is a Helper for using Drag and Drop interface. 
 *	The method presented here should be sufficient for most usages.
 */
public class DnD {

	private static DnDSourceTargetRegistry<Panel,WidgetSource,WidgetTarget> widgetsSourceRegistry = new DnDSourceTargetRegistry<Panel, WidgetSource, WidgetTarget>();
	
	private static DnDSourceTargetRegistry<Tree,TreeSource,TreeTarget> treeSourcesRegistry = new DnDSourceTargetRegistry<Tree, TreeSource, TreeTarget>();
	
	static{
		DojoController.getInstance().require("dojo.dnd.Source");
	}
	
	/**
	 * Registers a tree as a "TreeSource".
	 * That mean each of the TreeItems can be dragged from the tree.
	 * 
	 * @param tree the tree to register
	 * @return an object representing the DnDSource which has been registered
	 */
	public static TreeSource registerTreeSource(Tree tree){
		TreeSource sourcetree = new TreeSource(tree);
		DnDTreeController.getInstance().registerSource(sourcetree);
		treeSourcesRegistry.addSourceMapping(tree, sourcetree);
		return sourcetree;
	}
	
	/**
	 * Unregisters a tree as a "TreeSource".
	 * Thant mean the TreeItems in the tree won't be draggable anymore
	 * 
	 * @param tree a registered tree
	 * @return an object representing the DnDSource which has been unregistered
	 */
	public static TreeSource unregisterSource(Tree tree){
		TreeSource source = treeSourcesRegistry.getSource(tree);
		DnDTreeController.getInstance().unRegisterSource(source);
		treeSourcesRegistry.removeSourceMapping(tree);
		return source;
	}
	
	
	/**
	 * Registers a Panel as a WidgetSource. 
	 * A WidgetSource contains draggable widgets
	 * 
	 * @param panel the panel to be registered as a widget source
	 * @return an object representing the DnDSource which has been registered
	 */
	public static WidgetSource registerSource(Panel panel){
		WidgetSource panelSource = new WidgetSource(panel);
		WidgetDnDController.getInstance().registerSource(panelSource);
		widgetsSourceRegistry.addSourceMapping(panel , panelSource);
		return panelSource;
	}
	
	/**
	 * Unregisters a panel as a Widget Source.
	 * That means its Element will not be draggable anymore. 
	 * 
	 * @param panel : a registered panel source
	 * @return an object representing the DnDSource which has been unregistered
	 */
	public static WidgetSource unregisterSource(Panel panel){
		WidgetSource source = widgetsSourceRegistry.getSource(panel);
		WidgetDnDController.getInstance().unRegisterSource(source);
		widgetsSourceRegistry.removeSourceMapping(panel);
		return source;
	}
	
	/**
	 * Registers a widget as a draggable element within its panel
	 * 
	 * @param panel : a registered panel , which contains the widget (directly or not)
	 * @param widget : the widget to register as a draggable element
	 * @return a WidgetDnDElement representing the draggable object.
	 */
	public static WidgetDnDElement registerElement(Panel panel, Widget widget){
		WidgetSource source = widgetsSourceRegistry.getSource(panel);
		WidgetDnDElement dndElem;
		if(source == null){
			WidgetTarget target = widgetsSourceRegistry.getTarget(panel);
			if(target == null){
				return null;
			}
			dndElem = new WidgetDnDElement(widget);
			WidgetDnDController.getInstance().addDnDElementToJSTarget(target, dndElem);
			return dndElem;
		}
		dndElem = new WidgetDnDElement(widget);
		WidgetDnDController.getInstance().addDnDElementToJSSource(source, dndElem);
		return dndElem;
	}
	
	/**
	 * Unregisters a widget as a draggable Element. It will not be draggable anymore.
	 * 
	 * @param panel : a registered panel , which contains the widget (directly or not) 
	 * @param widget : a registered widget 
	 * @return a WidgetDnDElement representing the unregistered object.
	 */
	public static WidgetDnDElement unRegisterElement(Panel panel , Widget widget){
		WidgetSource source = widgetsSourceRegistry.getSource(panel);
		Collection<WidgetDnDElement> registeredElements = source.getRegisteredDndElement();
		for (WidgetDnDElement widgetDnDElement : registeredElements) {
			if(widgetDnDElement.getWidget() == widget){
				WidgetDnDController.getInstance().removeDnDElementFromJSSource(source, widgetDnDElement);
				return widgetDnDElement;
			}
		}
		return null;
	}
	
	/**
	 * Registers a panel as a WidgetTarget. A widget target may accept dropped 
	 * elements. If it is already registered as a source, the "target" behavior will be triggered on 
	 * the very same WidgetSource object.
	 * 
	 * @param panel : the panel to register as a Target.
	 * @return
	 */
	public static WidgetTarget registerTarget(Panel panel){
		WidgetSource existingSource = widgetsSourceRegistry.getSource(panel);
		WidgetTarget panelTarget;
		if(existingSource != null){
			panelTarget = existingSource;
		}else{
			panelTarget = new WidgetTarget(panel);
		}
		WidgetDnDController.getInstance().registerTarget(panelTarget);
		widgetsSourceRegistry.addTargetMapping(panel , panelTarget);
		return panelTarget;
	}
	
	/**
	 * Registers a tree as target, allowing it to receive dropped elements.
	 * 
	 * @param tree
	 * @return
	 */
	public static TreeTarget registerTarget(Tree tree){
		TreeSource existingSource = treeSourcesRegistry.getSource(tree);
		TreeTarget treeTarget;
		if(existingSource != null){
			treeTarget = existingSource;
		}else{
			treeTarget = new TreeTarget(tree);
		}
		DnDTreeController.getInstance().registerTarget(treeTarget);
		treeSourcesRegistry.addTargetMapping(tree , treeTarget);
		return treeTarget;
	}
	
	/**
	 * Unregister a target, which will not accept dropped elements anymore.
	 * 
	 * @param panel
	 * @return
	 */
	public static WidgetSource unregisterTarget(Panel panel){
		WidgetSource source = widgetsSourceRegistry.getSource(panel);
		WidgetDnDController.getInstance().unRegisterSource(source);
		widgetsSourceRegistry.removeSourceMapping(panel);
		return source;
	}
	
	/**
	 * Register the given behavior for dnd operations from the source to the target. 
	 * When the source is null, the behavior is registered for dnd operations from any source to the target
	 * When the target is null, the behavior is registered for dnd operations from the source to any target
	 * When both source and target are null, the behavior is registered for dnd operations from any source to any target
	 * 
	 * If such an imprecise behavior is registered, it will be overridden by any more precise behavior.
	 * If a  behavior is registered, another behavior cannot be added to the same "source-target" couple.
	 * 
	 * @param <E> : A type of IDnDElement which is handled by both the source and the Behavior
	 * @param <S> : A type of IDnDSource which handles IDnDElements of type <E>, and is handled by the behavior
	 * @param <T> : A type of Target which is handled by the Behavior
	 * @param behavior : a behavior managing Elements from type <E>, Sources from type <S> , and Targets from type <T> 
	 * @param source : a widget which is registered as source (it can be wether a Tree registered as a TreeSource, or a Panel registered as a WidgetSource, or null 
	 * @param target :a widget which is registered as target, or null
	 * @throws BehaviorScopeException : when a behavior is already registered for this source-target couple
	 */
	@SuppressWarnings("unchecked")
	public static <E extends IDnDElement , S extends IDnDSource<? extends E> , T extends IDnDTarget>  void registerBehavior(IDnDBehavior<E, S, T> behavior, Widget source, Widget target) throws BehaviorScopeException{
		S dsource = null;
		T dtarget = null;
		try{
			dsource = (S) widgetsSourceRegistry.getSource((Panel)source);
		}catch(ClassCastException e){
			try{
				dsource = (S) treeSourcesRegistry.getSource((Tree)source);	
			}catch(ClassCastException e2){
			
			}
		}
		try{
			dtarget = (T) widgetsSourceRegistry.getTarget((Panel)target);
		}catch(ClassCastException e){
			try{
				dtarget = (T) treeSourcesRegistry.getTarget((Tree)target);
			}catch(ClassCastException e2){
			
			}
		}
		registerBehaviorForDnDObjects(behavior, dsource, dtarget);
	}
	
	/**
	 * Register the given behavior for dnd operations from the source to the target. 
	 * When the source is null, the behavior is registered for dnd operations from any source to the target
	 * When the target is null, the behavior is registered for dnd operations from the source to any target
	 * When both source and target are null, the behavior is registered for dnd operations from any source to any target
	 * 
	 * If such an imprecise behavior is registered, it will be overridden by any more precise behavior.
	 * If a  behavior is registered, another behavior cannot be added to the same "source-target" couple.
	 * 
	 * @param <E> : A type of IDnDElement which is handled by both the source and the Behavior
	 * @param <S> : A type of IDnDSource which handles IDnDElements of type <E>, and is handled by the behavior
	 * @param <T> : A type of Target which is handled by the Behavior
	 * @param behavior : a behavior managing Elements from type <E>, Sources from type <S> , and Targets from type <T> 
	 * @param source : a dnd source
	 * @param target :a dnd target
	 * @throws BehaviorScopeException : when a behavior is already registered for this source-target couple
	 */
	public static <E extends IDnDElement , S extends IDnDSource<? extends  E> , T extends IDnDTarget>  void registerBehaviorForDnDObjects(IDnDBehavior<E, S, T> behavior, S source ,  T target) throws BehaviorScopeException{
		DnDBehaviors.addScopeToBehavior(behavior, source, target);
	}
	
	/**
	 * Helper method designed to move a draggable element from a source panel to a target panel (or another source panel)
	 * 
	 * @param widget : widget to move from a panel to another
	 * @param panelsrc : the origin panel
	 * @param paneldst : the destination panel
	 */
	public static void move(Widget widget, Panel panelsrc , Panel paneldst){
		unRegisterElement(panelsrc, widget);
		panelsrc.remove(widget);
		paneldst.add(widget);
		registerElement(paneldst, widget);
	}
	
	/**
	 * Helper method used to retrieve the deepest widget which was under the drop.  
	 * @param targetNodeId : the targetNodeId, usually a DOM id. It is the same as given in @link {@link WidgetDnDBehavior}
	 * @param target : the target in which the target node should be located
	 * @return the widget which was under the drop, within the Target.
	 */
	public static Widget getWidgetUnderDrop(String targetNodeId , Panel target){
		if(targetNodeId == null){
			return null;
		}
		Element elem = DOM.getElementById(targetNodeId);
		return getWidgetFromDomNodeInWidget(target, elem);
	}
	
	/**
	 * Assign a random but unique id to a widget. 
	 * A widget without an id cannot be located within a target
	 * 
	 * @param widget
	 * @return the generated id.
	 */
	public static String setId(Widget widget){
		String id = dojoGetUniqueId();
		DOM.setElementAttribute(widget.getElement(), "id", id);
		return id;
	}
	
	/**
	 * @param widget
	 * @return this widget's DOM Element id
	 */
	public static String getId(Widget widget){
		String id =DOM.getElementAttribute(widget.getElement(), "id");
		return id;
	}

	/**
	 * Internal method used to retrieve a widget within a DOMElement.
	 * 
	 * @param parent
	 * @param elem
	 * @return
	 */
	private static Widget getWidgetFromDomNodeInWidget(Widget parent, Element elem){
		if(parent.getElement().isOrHasChild(elem)){
			if(parent instanceof Panel){
				Panel parentPanel = (Panel) parent;
				for (Widget widget : parentPanel) {
					Widget toReturn = getWidgetFromDomNodeInWidget(widget, elem);
					if(toReturn != null){
						return toReturn;
					}
				}
			}else{
				return parent;
			}
		}
		return null;
	}
		
	/**
	 * @return a unique drag and drop id generated by Dojo
	 */
	public static native String dojoGetUniqueId()/*-{
		return $wnd.dojo.dnd.getUniqueId();
	}-*/;
	
}
