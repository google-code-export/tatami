package com.objetdirect.tatamix.client.hmvc.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.objetdirect.tatamix.client.hmvc.ModelEvent;
import com.objetdirect.tatamix.client.hmvc.ModelImpl;

public class ModelCallback implements AsyncCallback {

	private ModelImpl model;
	private String name="model";
	private String errorMessage = null;
	private String successEvent = null;
	private String errorEvent = null;

	/**
	 * Creates a <code>ModelAsyncCallback</code>
	 * @param model <code>ModelImpl</code>
	 */
	public ModelCallback(ModelImpl model) {
		this.model = model;

	}

	/**
	 * Sets the error Message on failure
	 * @param error
	 */
	public void setErrorMessage(String error) {
		this.errorMessage = error;
	}

	/**
	 * Returns the error message used on failure
	 * @return the error message used on failure could be <code>null</code>
	 */
	public String getErrorMessage() {
		return this.errorMessage;
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
		if ( errorMessage == null) {
			errorMessage = error.getMessage();
		}
		GWT.log("ERROR:" + name,error);
		model.fire(new ModelEvent(getErrorEvent(), model, errorMessage));

	}

	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	public void onSuccess(Object result) {
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
	protected Object doBeforeFire(Object result) {
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
	public void fire(String event, Object result) {
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
