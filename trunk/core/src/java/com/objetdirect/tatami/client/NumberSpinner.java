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
 * Author: Ronan Dunklau
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.HasValue;



/**
 * This class wraps the dojo NumberSpinner.
 * It's a simple number spinner with typematic and validation constraints
 * 
 * @author rdunklau
 *
 */
public class NumberSpinner extends AbstractDojoFocus implements HasValue<Number> {

	
	/**
	 * Constant used to specify the spinner minimum value
	 * @see #addConstraint(String, String)
	 * @see #NumberSpinner(int, String, boolean, float, String, String, float, boolean, float, Map)
	 * @see #NumberSpinner(String, float, String, String, float, Map)
	 */
	public static final String CONSTRAINT_MIN = "min";
	
	/**
	 * Constant used to specify the spinner maximum value
	 * @see #addConstraint(String, String)
	 * @see #NumberSpinner(int, String, boolean, float, String, String, float, boolean, float, Map)
	 * @see #NumberSpinner(String, float, String, String, float, Map)
	 */
	public static final String CONSTRAINT_MAX = "max";
	
	
	/**
	 * Constant used to specify the spinner number pattern.
	 * @see #addConstraint(String, String)
	 * @see #NumberSpinner(int, String, boolean, float, String, String, float, boolean, float, Map)
	 * @see #NumberSpinner(String, float, String, String, float, Map)
	 */
	public static final String CONSTRAINT_PATTERN = "pattern";
	
	
	/**
	 * Constant used to specify the spinner number type (percent, currency, decimal).
	 * @see #addConstraint(String, String)
	 * @see #NumberSpinner(int, String, boolean, float, String, String, float, boolean, float, Map)
	 * @see #NumberSpinner(String, float, String, String, float, Map)
	 */
	public static final String CONSTRAINT_TYPE = "type";
	
	
	/**
	 * Constant used to specify the spinner currency symbol 
	 * @see #addConstraint(String, String)
	 * @see #NumberSpinner(int, String, boolean, float, String, String, float, boolean, float, Map)
	 * @see #NumberSpinner(String, float, String, String, float, Map)
	 */
	public static final String CONSTRAINT_CURRENCY = "currency";
	
	/**
	 * @deprecated
	 */
	protected ChangeListenerCollection changeListeners;
	
	/**
	 * Delay until the "TypeMatic" is triggered (in ms)
	 * TypeMatic is what permits the spinner to spin more quickly
	 * when the user performs a "long click"
	 *  
	 */
	private int defaultTimeout = 500;

	/**
	 * Defines the speed at which the typematic increases the spinner 
	 */
	private float timeoutChangeRate = 0.90f;
	
	
	/**
	 * Message to display when the constraints are not respected (@see constraints)
	 * 
	 */
	private String invalidMessage = "Invalid Value" ; 
	
	/**
	 * Indicates wether to fire "onChange" events each time the 
	 * spinner value change
	 * 
	 * if true : a simple press over the spinner arrows fire an onChange event
	 * 
	 * if false : onChange events are fired only when the spinner loses focus
	 * 
	 */
	private boolean intermediateChanges = true;
	
	/**
	 * Increment which is used when the user click on the spinner arrows (or up/down keys) 
	 */
	private float smallDelta = 1f;
	

	/**
	 * Increment which is used when the user uses pageUp / pageDown 
	 * (Not yet implemented by underlying JavaScript Widget !)
	 *  
	 */
	private float largeDelta = 10f; 
	
	/**
	 * Hint String to be displayed to help the user 
	 * 
	 * 
	 */
	private String promptMessage = "Hint String";
	
	/**
	 * String to be displayed when the value is out of range 
	 * (ie, it is not comprised between the constraints "min" & "max" values  
	 * 
	 */
	private String rangeMessage = "Out of range Value";
	

	
	/**
	 * 
	 * Initial value
	 * 
	 */
	private float value = 0;
	
	/**
	 * Wether to trim the spinner's content 
	 * 
	 */
	private boolean trim = false;
	
	
	/**
	 * Constraints on the spinner content. 
	 * 
	 * Principal constraints keys :
	 * 
	 * Min : minimum value
	 * Max : maximum value
	 * pattern : Unicode standard number pattern used to format numbers
	 * 			(for exemple, number : 1234.567 with pattern #,##0.### 
	 * 			will be displayed 1 234,567, according to the locale)
	 * places : number of decimal places to accept. This value is STRICT (ie, 
	 * 			if places = 2 , the user cannot enter value 6.0)
	 *  
	 *
	 * 
	 * @see http://dojotoolkit.org/book/dojo-book-0-9/part-2-dijit/form-validation-specialized-input/textbox-validating-currency-number
	 * 
	 * 
	 */
	private Map<String,Object> constraints = new HashMap<String,Object>();
	
	
	
	
	

	/**
	 * 
	 * Constructor using all attributes.
	 * See attributes comments for detailed explanation
	 * 
	 * 
	 * 
	 * @param defaultTimeout
	 * @param invalidMessage
	 * @param intermediateChanges
	 * @param smallDelta
	 * @param largeDelta
	 * @param promptMessage
	 * @param rangeMessage
	 * @param timeoutChangeRate
	 * @param trim
	 * @param value
	 * @param constraints
	 */
	public NumberSpinner(int defaultTimeout, String invalidMessage,	boolean intermediateChanges, float delta, 
			String promptMessage, String rangeMessage,
			float timeoutChangeRate, boolean trim , float value , Map<String,Object> constraints) {
		
		this();
		this.defaultTimeout = defaultTimeout;
		this.invalidMessage = invalidMessage;
		this.intermediateChanges = intermediateChanges;
		this.smallDelta = delta;
		this.promptMessage = promptMessage;
		this.rangeMessage = rangeMessage;
		this.timeoutChangeRate = timeoutChangeRate;
		this.trim = trim;
		this.value = value;
		this.constraints = constraints;
	}
	
	/**
	 * 
	 * @param defaultTimeout
	 * @param invalidMessage
	 * @param intermediateChanges
	 * @param smallDelta
	 * @param largeDelta
	 * @param promptMessage
	 * @param rangeMessage
	 * @param timeoutChangeRate
	 * @param trim
	 * @param value
	 * @param minValue
	 * @param maxValue
	 */
	public NumberSpinner(int defaultTimeout, String invalidMessage,
			boolean intermediateChanges, float smallDelta, float largeDelta,
			String promptMessage, String rangeMessage,
			float timeoutChangeRate, boolean trim , float value , float minValue , float maxValue) {
		this();
		this.defaultTimeout = defaultTimeout;
		this.invalidMessage = invalidMessage;
		this.intermediateChanges = intermediateChanges;
		this.smallDelta = smallDelta;
		this.largeDelta = largeDelta;
		this.promptMessage = promptMessage;
		this.rangeMessage = rangeMessage;
		this.timeoutChangeRate = timeoutChangeRate;
		this.trim = trim;
		this.value = value;
		constraints.put("min",new Float(minValue));
		constraints.put("max",new Float(maxValue));
	}
	
	/**
	 * 
	 * @param invalidMessage
	 * @param smallDelta
	 * @param promptMessage
	 * @param rangeMessage
	 * @param value
	 * @param constraints
	 */
	public NumberSpinner(String invalidMessage , float smallDelta , String promptMessage , String rangeMessage ,float value ,  Map<String,Object> constraints){
		this();
		this.promptMessage = promptMessage;
		this.rangeMessage = rangeMessage;
		this.invalidMessage = invalidMessage;
		this.smallDelta = smallDelta;
		this.value = value;
		this.constraints = constraints;
	}
	
	
	public NumberSpinner(float initValue , float minValue , float maxValue , float delta){
		this();
		constraints.put("min",minValue);
		constraints.put("max",maxValue);
		this.smallDelta = delta ;  
		this.value = initValue;
	}
	
	
	/**
	 * Default constructor.
	 * It takes all the default values to create the spinner.
	 * 
	 */
	public NumberSpinner(Element element){
		super(Document.get().createDivElement());
		changeListeners = new ChangeListenerCollection();
		constraints = new HashMap<String, Object>();
	}
	
	public NumberSpinner(){
		this(Document.get().createDivElement());
	}
	
	

	private native void dojoSetConstraints(JavaScriptObject constraints , JavaScriptObject dojoWidget)/*-{
		dojoWidget.constraints = constraints;
	}-*/;
	
	/**
	 * @return : the spinner's increment
	 */
	public float getDelta() {
		return smallDelta;
	}

	public void setDelta(float smallDelta) {
		this.smallDelta = smallDelta;
		if(dojoWidget != null){
			dojoSetDelta(smallDelta, dojoWidget);
		}
	}
	
	private native void dojoSetDelta(float delta, JavaScriptObject dojoWidget)/*-{
		dojoWidget.smallDelta = delta;
	}-*/;
	
	
	/**
	 * @return the constraints' HashMap
	 */
	public Map<String, Object> getConstraints() {
		return constraints;
	}
	
	/**
	 * @param constraints : the constraints to be applied to the spinner
	 * {@link #constraints}
	 */
	public void setConstraints(Map<String, Object> constraints) {
		this.constraints = constraints;
		if(dojoWidget != null){
			dojoSetConstraints(JSHelper.convertObjectToJSObject(constraints), dojoWidget);
		}
	}
	
	
	/***
	 * Adds a constraint to the spinner
	 * 
	 * @param constraintName : the constraint name. It can be one of:
	 * @link NumberSpinner#CONSTRAINT_MIN
	 * @link NumberSpinner#CONSTRAINT_MAX
	 * @link NumberSpinner#CONSTRAINT_PATTERN
	 * @link NumberSpinner#CONSTRAINT_TYPE
	 * @link NumberSpinner#CONSTRAINT_CURRENCY
	 * 
	 * @param value
	 */
	public void addConstraint(String constraintName, String value){
		constraints.put(constraintName , value);
		if(dojoWidget != null){
			dojoSetConstraints(JSHelper.convertObjectToJSObject(constraints), dojoWidget);
		}
	}
	
	/***
	 * Removes a constraint from the spinner
	 * 
	 * @param constraintName : the constraint name. It can be one of:
	 * @link NumberSpinner#CONSTRAINT_MIN
	 * @link NumberSpinner#CONSTRAINT_MAX
	 * @link NumberSpinner#CONSTRAINT_PATTERN
	 * @link NumberSpinner#CONSTRAINT_TYPE
	 * @link NumberSpinner#CONSTRAINT_CURRENCY
	 * 
	 * @param value
	 */
	public void removeConstraint(String constraintName){
		constraints.remove(constraintName);
		if(dojoWidget != null){
			dojoSetConstraints(JSHelper.convertObjectToJSObject(constraints), dojoWidget);
		}
	}
	
	/**
	 * A JSNI method which declare our TatamiNumberSpinner.
	 * TatamiNumberSpinner is the same as Dojo NumberSpinner, except 
	 * that we override the onChange function to call the GWT widget's "notifyChangeListeners" 
	 * method.
	 */
	private native void defineTatamiNumberSpinner()/*-{
		$wnd.dojo.declare("dojox.form.TatamiNumberSpinner", $wnd.dijit.form.NumberSpinner , {
	     onChange:function (e) {
	 		   this.gwtWidget.@com.objetdirect.tatami.client.NumberSpinner::notifyChange()();
	
	    }});
	}-*/;
	
	

	/**
	 * Instantiates the dojo widget
	 * 
	 */
	
	public void onDojoLoad() {
		defineTatamiNumberSpinner();
	}
	
	
	/**
	 * Instantiates the dojo widget
	 * 
	 * 
	 * @param defaultTimeout
	 * @param invalidMessage
	 * @param intermediateChanges
	 * @param smallDelta
	 * @param largeDelta
	 * @param promptMessage
	 * @param rangeMessage
	 * @param timeoutChangeRate
	 * @param trim
	 * @param value
	 * @param constraints
	 * @return
	 */
	
	private native JavaScriptObject createDojoSpinner(int defaultTimeout, String invalidMessage,
			boolean intermediateChanges, float smallDelta, float largeDelta,
			String promptMessage, String rangeMessage, 
			float timeoutChangeRate, boolean trim, float value , JavaScriptObject constraints)
			/*-{
			 var spinner = new $wnd.dojox.form.TatamiNumberSpinner(
			 	 {
			 defaultTimeout : defaultTimeout,
			 invalidMessage : invalidMessage,
			 intermediateChanges : intermediateChanges,
			 smallDelta : smallDelta,
			 largeDelta : largeDelta,
			 promptMessage : promptMessage,
			 rangeMessage : rangeMessage,
			 timeoutChangeRate : timeoutChangeRate,
			 trim : trim,
			 value : value,
			 constraints : constraints
			 }
			 );
			 return spinner;
			 }-*/
	;

	
	
	
	/**
	 *  This method is called when the dojo widget raises an "onChange" event.
	 *  It notifies the ChangeListeners
	 */
	private void notifyChange(){
		this.value = dojoGetValue(dojoWidget);
		ValueChangeEvent.fire(this,value);
		if ( this.changeListeners != null) {
			changeListeners.fireChange(this);
		}
		
	}
	
	@Override
	
	/**
	 * @deprecated
	 */
	public void addChangeListener(ChangeListener listener) {
		changeListeners.add(listener);
	}

	/**
	 * @deprecated
	 */
	@Override
	public void removeChangeListener(ChangeListener listener) {
		changeListeners.remove(listener);
	}

	
	
	private native float dojoGetValue(JavaScriptObject dojoWidget)
	/*-{
	 return dojoWidget.getValue();
	}-*/
	;
	
	
	
	
	private native void dojoSetValue(JavaScriptObject dojoWidget , float value)
	/*-{
	  dojoWidget.setValue(value);
	}-*/
	;
	
	
	public String getDojoName() {
		return "dijit.form.NumberSpinner";
	}
	
	public void createDojoWidget() throws Exception {
		this.dojoWidget = createDojoSpinner(defaultTimeout,invalidMessage,
				intermediateChanges,smallDelta,
				largeDelta,promptMessage,rangeMessage,
				timeoutChangeRate,trim,value,JSHelper.convertObjectToJSObject(constraints));
	}

	
	

	public void doAfterCreation() {
		DojoController.startup(this);
	//	setBrowserEventCallback(getDojoWidget());
	}

	public void doBeforeDestruction() {
	}

	public void free() {
		destroyWidget(dojoWidget);
		this.dojoWidget = null;
	}

	private native void destroyWidget(JavaScriptObject dojoWidget)/*-{
		dojoWidget.destroy();
	}-*/;
	
	
	/**
	 * This method arms some callback
	 * @param dojoWidget the DOJO widget.
	 */
	private native void setBrowserEventCallback(JavaScriptObject dojoWidget)
	/*-{
	    dojoWidget.textbox.onbrowserevent = function(e) {
	    dojoWidget.gwtWidget.@com.objetdirect.tatami.client.DropdownContainer::onBrowserEvent(Lcom/google/gwt/user/client/Event;)(e);
	 };
	 }-*/;

	/**
	 * @return true if the spinner fires an on change event each time an arrow is pressed,
	 * false if it fires it only when it loses focus
	 */
	public boolean isIntermediateChanges() {
		return intermediateChanges;
	}

	/**
	 * @param intermediateChanges : true if the spinner fires an on change event each time an arrow is pressed,
	 * false if it fires it only when it loses focus
	 */
	public void setIntermediateChanges(boolean intermediateChanges) {
		this.intermediateChanges = intermediateChanges;
		if(dojoWidget != null){
			setIntermediateChanges(intermediateChanges , dojoWidget);
		}
	}

	private native void setIntermediateChanges(boolean intermediateChanges , JavaScriptObject dojoWidget)/*-{
		dojoWidget.intermediateChanges = intermediateChanges;
	}-*/;

	@Override
	public Number getValue() {
	    if(this.dojoWidget != null){
			this.value = dojoGetValue(dojoWidget);
		}
		return this.value;
	}

	@Override
	public void setValue(Number number) {
		this.value = number.floatValue();
		if(dojoWidget != null){
			dojoSetValue(dojoWidget,this.value);
		}
		
	}

	@Override
	public void setValue(Number number, boolean fire) {
	 setValue(number);
	 notifyChange();
		
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Number> handler) {
		
		return addHandler(handler,ValueChangeEvent.getType());
	}
	
}
