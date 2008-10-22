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
import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.JSHelper;

/**
 * @author rdunklau
 *
 * @param <T> an IDnDSource type which should be managed by the controller implementation
 * @param <U> an IDnDTarget type which should be managed by the controller implementation
 */
/**
 * @author rdunklau
 *
 * @param <T>
 * @param <U>
 */
public abstract class IDnDController<T extends IDnDSource<?>,U extends IDnDTarget> {

	protected Map<T , JavaScriptObject> gwtToDojoSourceMap;
	protected Map<U , JavaScriptObject> gwtToDojoTargetMap;
	protected Map<JavaScriptObject,T> dojoToGWTSourceMap;
	protected Map<JavaScriptObject,U> dojoToGWTTargetMap;
	
	
	/**
	 * Default constructor
	 */
	protected IDnDController(){
		DnDMainController.getInstance().registerDnDController(this);
		gwtToDojoSourceMap = new HashMap<T, JavaScriptObject>();
		gwtToDojoTargetMap = new HashMap<U, JavaScriptObject>();
		dojoToGWTSourceMap = new HashMap<JavaScriptObject, T>();
		dojoToGWTTargetMap = new HashMap<JavaScriptObject, U>();
	}
	
	
	/**
	 * Registers a source for this controller.
	 * The abstract IDnDController already takes care of registering the source
	 * to the main controller, and keeping the javascriptobject/source object mapping.
	 * It calls the createAndSetupJSSource method, which must be implemented
	 * 
	 * @param source : a source to register.
	 * 
	 */
	public void registerSource(T source){
		try{
			JavaScriptObject jsSource = createAndSetupJSSource(source);
			gwtToDojoSourceMap.put(source, jsSource);
			dojoToGWTSourceMap.put(jsSource, source);
			DnDMainController.getInstance().registerDnDSource(this, jsSource);
			Collection<? extends IDnDElement> elems = (Collection<? extends IDnDElement>) source.getRegisteredDndElement();
			for (Iterator<? extends IDnDElement> iterator = elems.iterator(); iterator.hasNext();) {
				IDnDElement dnDElement = (IDnDElement) iterator.next();
				addDnDElementToJSSource(source, dnDElement);
			}
		}catch (JSSourceCreationException e) {
			return;
		}
		
	}
	
	/**
	 * /**
	 * Registers a target for this controller.
	 * The abstract IDnDController already takes care of registering the target
	 * to the main controller, and keeping the javascriptobject/target object mapping.
	 * It calls the createAndSetupJSTarget method, which must be implemented
	 * 
	 * @param target : a target to register.
	 * 
	 */
	public void registerTarget(U target){
		if(target instanceof IDnDSource<?>){
			JavaScriptObject registeredSource = gwtToDojoSourceMap.get(target);
			if(registeredSource != null){
				gwtToDojoTargetMap.put(target, registeredSource);
				dojoToGWTTargetMap.put(registeredSource, target);
				DnDMainController.getInstance().registerDnDTarget(this, registeredSource);
				return;
			}
		}
		try{
			JavaScriptObject jsTarget = createAndSetupJSTarget(target);
			gwtToDojoTargetMap.put(target, jsTarget);
			dojoToGWTTargetMap.put(jsTarget, target);
			DnDMainController.getInstance().registerDnDTarget(this, jsTarget);
		}catch (JSSourceCreationException e) {
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * Unregisters a source.
	 * This template method takes care of destroying the JSSource and 
	 * unregistering the source.
	 * 
	 * @param source : the source to unregister
	 */
	public void unRegisterSource(T source){
		DnDMainController.getInstance().unregisterDnDSource(getJSSourceFromGWTSource(source));
		JavaScriptObject jsSource = gwtToDojoSourceMap.get(source);
		Collection<? extends IDnDElement> elems = (Collection<? extends IDnDElement>) source.getRegisteredDndElement();
		for (Iterator<? extends IDnDElement> iterator = elems.iterator(); iterator.hasNext();) {
			IDnDElement dnDElement = (IDnDElement) iterator.next();
			removeDnDElementFromJSSource(source , dnDElement);
		}
		cleanupSource(source);
		dojoToGWTSourceMap.remove(jsSource);
		gwtToDojoSourceMap.remove(source);
	}
		
	/**
	 * Unregisters a source.
	 * This template method takes care of destroying the JSSource and 
	 * unregistering the source.
	 * 
	 * @param target : the target to unregister
	 */
	public void unRegisterTarget(U target){
		DnDMainController.getInstance().unregisterDnDTarget(getJSTargetFromGWTTarget(target));
		cleanupTarget(target);
		JavaScriptObject jsTarget = gwtToDojoTargetMap.remove(target);
		dojoToGWTTargetMap.remove(jsTarget);
	}
	
	/**
	 * @param jsSource
	 * @return the GWT source corresponding to the given JS source object
	 */
	public T getSource(JavaScriptObject jsSource){
		return dojoToGWTSourceMap.get(jsSource);
	}
	
	/**
	 * @param jsTarget 
	 * @return the GWT target corresponding to the given JS target object 
	 */
	public U getTarget(JavaScriptObject jsTarget){
		return dojoToGWTTargetMap.get(jsTarget);
	}
	
	/**
	 * This method is used by the main controller to retrived IDnDElement objects from a javascript array,
	 * containing the dojo javascript representations from the elements
	 * 
	 * @param source : the source in which the elements should be gotten 
	 * @param jsDnDElements : a javascript array containing the JS representations 
	 * of the IDnDElements
	 * @return 
	 */
	public Collection<IDnDElement> getGWTDnDElements(JavaScriptObject source ,JavaScriptObject jsDnDElements) {
		Collection<IDnDElement> dndElementList = new ArrayList<IDnDElement>();
		int length = JSHelper.getArraySize(jsDnDElements);
		for(int i = 0; i < length ; i++){
			dndElementList.add(getDndElementAtGivenIndex(i,  source , jsDnDElements));
		}
		return dndElementList;
	}
	
	
	/**
	 * This method must be implemented by an actual controller
	 * to retrieve an IDnDElement from a JavaScript array.
	 * 
	 * @param index : the index from wich the IDnDElement should be extracted
	 * @param source : the JSSource object
	 * @param nodesArray  : the JSArray containing the dndElements
	 * @return
	 */
	public abstract IDnDElement getDndElementAtGivenIndex(int index , JavaScriptObject source , JavaScriptObject nodesArray );
	
	/**
	 * Creates a dojo javascript source from a Java source 
	 * 
	 * @param source
	 * @return
	 * @throws JSSourceCreationException
	 */
	public abstract JavaScriptObject createAndSetupJSSource(T source) throws JSSourceCreationException;
	
	/**
	 * Creates a dojo javascript target from a Java source
	 * 
	 * @param target
	 * @return
	 * @throws JSSourceCreationException
	 */
	public abstract JavaScriptObject createAndSetupJSTarget(U target) throws JSSourceCreationException;
	
	/**
	 * @param source : the java source
	 * @return the corresponding javascript source
	 */
	public abstract JavaScriptObject getJSSourceFromGWTSource(T source);
	
	/**
	 * @param target : the java target
	 * @return the corresponding javascript target 
	 */
	public abstract JavaScriptObject getJSTargetFromGWTTarget(U target);

	/**
	 * Destroy all associated JavaScript objects, and performs any finalization operations
	 * 
	 * @param source : the source to finalize
	 */
	public abstract void cleanupSource(T source);
	
	/**
	 * Destroy all associated JavaScript objects, and performs any finalization operations
	 * 
	 * @param target : the target to finalize
	 */
	public abstract void cleanupTarget(U target);
	
	
	
	/**
	 * This method is called by the DnDMainController when it receives a onWindowClosed event. It must ensure all 
	 * JS sources and targets are properly destroyed.
	 */
	public void destroyAllSourcesAndTargets(){
		Set<T> sourceskeys = gwtToDojoSourceMap.keySet();
		for (T source : sourceskeys) {
			cleanupSource(source);
			dojoToGWTSourceMap.remove(gwtToDojoSourceMap.get(source));
			gwtToDojoSourceMap.remove(source);
		}
		Set<U> targetKeys = gwtToDojoTargetMap.keySet();
		for (U target : targetKeys) {
			cleanupTarget(target);
			dojoToGWTTargetMap.remove(gwtToDojoTargetMap.get(target));
			gwtToDojoTargetMap.remove(target);
		}
	}
	

	/**
	 * @param source : the source to which the element should be added
	 * @param element : the element to add
	 */
	public abstract void addDnDElementToJSSource(T source,
			IDnDElement element);
	
	/**
	 * @param source : the source from which the element should be removed
	 * @param element : the element to remove
	 */
	public abstract void removeDnDElementFromJSSource(T source,
			IDnDElement element);
}

