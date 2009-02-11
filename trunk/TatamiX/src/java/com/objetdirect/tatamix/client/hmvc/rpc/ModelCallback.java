package com.objetdirect.tatamix.client.hmvc.rpc;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.asm.commons.Method;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.objetdirect.tatamix.client.hmvc.MVCComponent;
import com.objetdirect.tatamix.client.hmvc.Model;
import com.objetdirect.tatamix.client.hmvc.ModelEvent;

public class ModelCallback<T> implements AsyncCallback<T> {

	private MVCComponent model;
	private String name="model";
	
	private Map<Class< ? extends Throwable>,String> errorMessages;
	
	private String errorMessage = null;
	private String successEvent = null;
	private String errorEvent = null;

	/**
	 * Creates a <code>ModelAsyncCallback</code>
	 * @param model <code>ModelImpl</code>
	 */
	public ModelCallback(MVCComponent model) {
		if ( !(model instanceof Model)) {
			throw new IllegalArgumentException("The MVCComponent must be implements the Model interface !!");
		}
		this.model = model;
		errorMessages = new HashMap<Class< ? extends Throwable>,String>();

	}

	
	public void setErrorMessage(Class<? extends Throwable> class_,String message) {
		errorMessages.put(class_, message);
	}

	/**
	 * Sets the error Message on failure.
	 * Use the {@link Method} {@link #setErrorMessage(Class, String)} instead
	 * @param error message the message related to a basic error
	 * @deprecated
	 */
	public void setErrorMessage(String message) {
		errorMessage = message;
	}
	
	
	
	/**
	 * Returns the error message used on failure.
	 * @return the error message used on failure could be <code>null</code>.
	 * @deprecated use the method {@link #getErrorMessage(Class)} instead.
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	/**
	 * 
	 * @param class_
	 * @return
	 */
	public String getErrorMessage(Class< ? extends Throwable> class_) {
		return this.errorMessages.get(class_);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
	 */
	public void onFailure(Throwable error) {
		
		String message = getErrorMessage(error.getClass());
		
		if ( message == null ) {
			message = error.getMessage();
			
		}
		errorMessage = message;
		GWT.log("ERROR:" + name,error);
		model.fire(new ModelEvent(getErrorEvent(), model, message));

	}

	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess(T result) {
		Object result_ = doBeforeFire(result);
		doFire(result_);

	}

	/**
	 * Gets the given result and do something with it.
	 * @param result the result given on success
	 * @return the result itself or an other object. The Object returned will be send in the <code>ModelEvent</code>
	 * @see #doFire(Object)
	 *    
	 */
	protected Object doBeforeFire(T result) {
		return result;
	}

	/**
	 * Fires the given result with the type successEvent given at the instanciation of this callback
	 * @param result
	 */
	protected void doFire(Object result) {
		fire(successEvent,result);
	}

	/**
	 * Fires an event by the model
	 * @param event
	 * @param result
	 */
	protected void fire(String event, Object result) {
		setSuccessEvent(event);
		model.fire(new ModelEvent(getSuccessEvent(), model, result));
	}

	public String getErrorEvent() {
		return errorEvent;
	}

	public void setErrorEvent(String errorEvent) {
		this.errorEvent = errorEvent;
	}

	public String getSuccessEvent() {
		return successEvent;
	}

	public void setSuccessEvent(String successEvent) {
		this.successEvent = successEvent;
	}


}
