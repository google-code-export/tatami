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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowCloseListener;
import com.objetdirect.tatami.client.DojoController;

/**
 * This class acts as a super-dndcontroller.
 * It coordinates the various DnDControllers which register themselves to this
 * main controller.
 * It also dispatches the various events to the behaviors.
 * It is implemented as a singleton
 * @author rdunklau
 *
 */
public class DnDMainController {

	private static  DnDMainController instance;
	
	private Map<JavaScriptObject , IDnDController<?, ?>> jssources;
	
	private Map<JavaScriptObject , IDnDController<?, ?>> jstargets;
	
	private Collection<IDnDController<?, ?>> controllers;
	
	private IDnDSource<?> lastDnDSource;

	private Collection<? extends IDnDElement> lastDnDElements;
	
	/**
	 * Private default constructor
	 * It adds a WindowListener to clean up the various dnd elements 
	 */
	private DnDMainController(){
		DojoController.getInstance().require("dojo.dnd.Source");
		jssources = new HashMap<JavaScriptObject, IDnDController<?,?>>();
		jstargets = new HashMap<JavaScriptObject, IDnDController<?,?>>();
		controllers = new ArrayList<IDnDController<?, ?>>();
		Window.addWindowCloseListener(new WindowCloseListener() {
			public String onWindowClosing() {
				return null;
			}
			public void onWindowClosed() {
				destroyDnDEngine();
			}
		});
	}
	
	public void destroyDnDEngine(){
		for (Iterator<IDnDController<?,?>> iterator = controllers.iterator(); iterator
		.hasNext();) {
			IDnDController<?,?> controller =  iterator.next();
			controller.destroyAllSourcesAndTargets();
		}
		unRegisterAllDnDControllers();
	}
	
	/**
	 * @return the dnd main controller
	 */
	static DnDMainController getInstance(){
		if(instance == null){
			instance = new DnDMainController();
		}
		return instance;
	}
	
	/**
	 * @param controller : the controller to which the source is registered
	 * @param source : the source to register
	 */
	protected void registerDnDSource(IDnDController<? , ?> controller, JavaScriptObject source){
		jssources.put(source, controller);
	}
	
	/**
	 * @param controller : the controller to which the target is registered
	 * @param target : the target to register
	 */
	protected void registerDnDTarget(IDnDController<? , ?> controller, JavaScriptObject target){
		jstargets.put(target, controller);
	}
	
	/**
	 * @param source : the concrete javascript dojo source 
	 */
	protected void unregisterDnDSource(JavaScriptObject source){
		jssources.remove(source);
	}
	
	/**
	 * @param target : the concrete javascript dojo target
	 */
	protected void unregisterDnDTarget(JavaScriptObject target){
		jstargets.remove(target);
	}
	
	
	
	
	/**
	 * @param controller the DnDController to register
	 */
	protected void registerDnDController(IDnDController<? , ?> controller){
		this.controllers.add(controller);
	}
	
	/**
	 * @param controller the DnDController to unregister
	 */
	protected void unRegisterDnDController(IDnDController<? , ?> controller){
		this.controllers.remove(controller);
		Collection<IDnDController<? , ?>> controllers = jssources.values();
		for (Iterator<IDnDController<?,?>> iterator = controllers.iterator(); iterator.hasNext();) {
			IDnDController<?, ?> dnDController = iterator.next();
			if(dnDController == controller){
				iterator.remove();
			}
		}
	}
	
	protected void unRegisterAllDnDControllers(){
		Collection<IDnDController<? , ?>> controllers = jssources.values();
		jssources.clear();
		jstargets.clear();
		controllers.clear();
	}
	
	/**
	 * This method is called by dojo on each dnd drop.
	 * 
	 * @param <E>
	 * @param jssource
	 * @param jstarget
	 * @param nodes
	 * @param targetNodeId
	 * @param ctrlPressed
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <E extends IDnDElement> boolean onDndDrop(JavaScriptObject jssource , JavaScriptObject jstarget , JavaScriptObject nodes , String targetNodeId ,  boolean ctrlPressed){
		try{
			IDnDController<IDnDSource<E>,?> sourceOwner =  (IDnDController<IDnDSource<E>, ?>) jssources.get(jssource);
			IDnDController<?,IDnDTarget> targetOwner = (IDnDController<?, IDnDTarget>) jstargets.get(jstarget);
			IDnDSource<E> dndSource = sourceOwner.getSource(jssource);
			IDnDTarget dndTarget = targetOwner.getTarget(jstarget);
			Collection<E> dndElements = (Collection<E>) sourceOwner.getGWTDnDElements(jssource , nodes);
			IDnDBehavior<E, IDnDSource<E>, IDnDTarget> behavior = (IDnDBehavior<E, IDnDSource<E>, IDnDTarget>) DnDBehaviors.getBehaviorFor(dndSource, dndTarget);
			if(behavior != null && behavior.onDrop(dndElements , dndSource , dndTarget, targetNodeId, ctrlPressed)){
				behavior.elementsAccepted(dndSource, dndTarget, dndElements, ctrlPressed, targetOwner);
				return true;
			}
			return false;
		}catch(ClassCastException e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This method is called by dojo on each dnd start
	 * 
	 * @param <E>
	 * @param jssource
	 * @param nodes
	 * @param copy
	 */
	@SuppressWarnings("unchecked")
	protected <E extends IDnDElement> void onDnDStart(JavaScriptObject jssource, JavaScriptObject nodes, boolean copy){
		try{
			IDnDController<IDnDSource<E>,?> sourceOwner =  (IDnDController<IDnDSource<E>, ?>) jssources.get(jssource);
			IDnDSource<E> dndSource =  sourceOwner.getSource(jssource);
			Collection<E> dndElements = (Collection<E>) sourceOwner.getGWTDnDElements(jssource , nodes);
			lastDnDSource = dndSource;
			lastDnDElements = dndElements;
			Collection<IDnDBehavior<?,?,?>> behaviors = DnDBehaviors.getAllBehaviorForSource(lastDnDSource);
			for (Iterator iterator = behaviors.iterator(); iterator.hasNext();) {
				IDnDBehavior<? extends IDnDElement, ? extends IDnDSource<?>, ? extends IDnDTarget> dnDBehavior = (IDnDBehavior<? extends IDnDElement, ? extends IDnDSource<?>, ? extends IDnDTarget>) iterator
						.next();
				((IDnDBehavior<E,IDnDSource<E>,IDnDTarget>)dnDBehavior).onDndStart(dndElements,dndSource, false);
			}
		}catch(ClassCastException e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	protected <E extends IDnDElement> void onDnDCancel(){
		try{
			Collection<IDnDBehavior<? extends IDnDElement,? extends IDnDSource<?>,? extends IDnDTarget>> behaviors = DnDBehaviors.getAllBehaviorForSource(lastDnDSource);
			for (Iterator iterator = behaviors.iterator(); iterator.hasNext();) {
				IDnDBehavior<? extends IDnDElement, ? extends IDnDSource<?>, ? extends IDnDTarget> dnDBehavior = (IDnDBehavior<? extends IDnDElement, ? extends IDnDSource<?>, ? extends IDnDTarget>) iterator
						.next();
				dnDBehavior.onCancel();
			}
		}catch(ClassCastException e){
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * This method is called by dojo to check wether a drop should be accepted
	 * 
	 * @param <E>
	 * @param jssource
	 * @param jstarget
	 * @param nodes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <E extends IDnDElement> boolean checkAcceptance(JavaScriptObject jssource , JavaScriptObject jstarget , JavaScriptObject nodes){
		try{
			IDnDController<IDnDSource<E>,?> sourceOwner =  (IDnDController<IDnDSource<E>, ?>) jssources.get(jssource);
			IDnDController<?,IDnDTarget> targetOwner = (IDnDController<?, IDnDTarget>) jstargets.get(jstarget);
			if(targetOwner != null){
				IDnDSource<E> dndSource =  sourceOwner.getSource(jssource);
				IDnDTarget dndTarget =  targetOwner.getTarget(jstarget);
				Collection<E> dndElements = (Collection<E>) sourceOwner.getGWTDnDElements(jssource , nodes);
				if(dndTarget != null){
					IDnDBehavior<E, IDnDSource<E>, IDnDTarget> behavior = (IDnDBehavior<E, IDnDSource<E>, IDnDTarget>) DnDBehaviors.getBehaviorFor(dndSource, dndTarget);
					if(behavior != null){
						if(behavior.checkTargetItemAcceptance(dndSource , dndTarget , dndElements)){
							return behavior.checkSourceItemAcceptance(dndSource, dndTarget, dndElements);
						}
					}
				}
			}
			return false;
		}catch(ClassCastException e){
			e.printStackTrace();
			return false;
		}
	}
	
	protected <E extends IDnDElement> void  dragOver(JavaScriptObject jstarget){
		try{
			IDnDController<?,IDnDTarget> targetOwner = (IDnDController<?, IDnDTarget>) jstargets.get(jstarget);
			if(targetOwner != null){
				IDnDTarget dndTarget =  targetOwner.getTarget(jstarget);
				if(dndTarget != null){
					IDnDBehavior<E,? extends IDnDSource<E>,? extends IDnDTarget> behavior = (IDnDBehavior<E, ? extends IDnDSource<E>, ? extends IDnDTarget>) DnDBehaviors.getBehaviorFor(lastDnDSource, dndTarget);
					if(behavior != null){
						behavior.dragOver(dndTarget);
					}
				}
			}
		}catch(ClassCastException e){
			e.printStackTrace();
		}
	}
	
	
}
