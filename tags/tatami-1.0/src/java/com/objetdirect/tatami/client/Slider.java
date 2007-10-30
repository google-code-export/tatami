/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@objectweb.org
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
 * Authors: Henri Darmet, Vianney Grassaud
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Class for widgets with a cursor that permit to choose an integer  value bounded by a max value and a min value. 
 * The max and min value are specified when the widget is created.
 *
 * <p>
 * This component is an encapsulation GWT of Dojo components. Warning the widget
 * exist under 3 faces :
 * <ul>
 * <li>a GWT widget (this!)</li>
 * <li>a Dojo widget (which play the role of 'this' for GWT)</li>
 * <li>a structur of DOM elements, the only exploitable presentation by the
 * navigator.</li>
 * </ul>
 * </p>
 */
public class Slider extends AbstractDojoFocus {

	/** The cursor is in a HORIZONTAL position */
	static public final String HORIZONTAL = "horizontal";
	
	/** The cursor is in a VERTICAL position */
	static public final String VERTICAL = "vertical";

	/** Type for the cursor position */
	private String type = HORIZONTAL;

	/** min value */
	private int minimum = 0;

	/** max value */
	private int maximum = 100;

	/**
	 * No increasing for the Slider. This property had been removed because a bug was detected 
	 * by the unit tests. If teh value of snapValues is 
	 * different than the property <code>maximum</code> then an error of 
	 * RuntimeException is thrown indicating an incompatibility in web
	 * mode. To avoid this problem, the feature will have the value of 
	 * <code>maximum</code> We keep a trace of this property nonetheless.
	 */

	private int snapValues = maximum;

	/** value choose by the user */
	private int value = 0;

	/**
	 * Creates a Slider cursor
	 * @param type  the position of the cursor : VERTICAL, HORIZONTAL
	 * @parma minimum the min value  for the cursor
	 * @param maximum the max value for the cursor
	 * @param initialValue the initial value
	 * @param boolean  disables (enables) the value to change while you are dragging, or just after drag finishes
	 */
	public Slider(String type, int minimum, int maximum, int initialValue) {
		super();
		this.maximum = maximum;
		this.minimum = minimum;
		this.snapValues = this.maximum;
		this.value = initialValue;
		setType(type);
		this.setElement(getElement());

	}

	/**
	 * Returns the name of the DOJO widget
	 * @return "Slider"
	 */
	public String getDojoName() {
		return "Slider";
	}

	/**
	 * Sets the type position for the cursor.
     * @param type the position for the cursor : VERTICAL || HORIZONTAL
	 * @throws IllegalArgumentException if the given type is not valid.
	 */
	private void setType(String type) {
		if (HORIZONTAL.equals(type) || VERTICAL.equals(type)) {
			this.type = type;
		} else {
			throw new IllegalArgumentException("type : " + type);
		}
	}

	/**
	 * Returns the max value for the cursor. The value is defined during the creation.
	 * @return an integer.
	 */
	public int getMaximum() {
		return this.maximum;
	}

	/**
	 * Returns the mininal value for the cursor.
	 * @return an integer.
	 */

	public int getMinimum() {
		return this.minimum;
	}

	/**
	 * Creates the Slider object in a horizontal or a  vertical position according to the type.
	 */
	public void createDojoWidget() throws Exception {

		if (HORIZONTAL.equals(type)) {
			this.dojoWidget = createHorizontalSlider(minimum, maximum,
					snapValues, value);
		} else {
			this.dojoWidget = createVerticalSlider(minimum, maximum,
					snapValues, value);
		}

	}

	/**
	 * Creates a Slider DOJO widgte in a vertical position.
	 * @parma minimum the min value for the cursor.
	 * @param maximum the max value for the cursor.
	 * @param snapValues number of values to be represented by slider in the vertical direction
		      =0 means no snapping
	 * @param initialValue the intial value for the cursor.
	 * @param boolean  disables (enables) the value to change while you are dragging, or just after drag finishes
	 * @return the Slider DOJO widget
	 */
	private native JavaScriptObject createVerticalSlider(int minimum,
			int maximum, int snapValues, int initialValue)
	/*-{
	 var widget = $wnd.dojo.widget.createWidget(
	 "SliderVertical",
	 {
	 minimumY: 			minimum,
	 maximumY:			maximum,
	 snapValuesY: 		snapValues,
	 isEnableY: true,
	 isEnableX: false,
	 initialValueY: 		initialValue,
	 backgroundSize:		"height:200px;width:10px;",
	 activeDrag: 		true
	 }
	 );
	 return widget;
	 }-*/;

	/**
	 * Creates a Slider DOJO widgte in a horinzontal position.
	 * @parma minimum the min value for the cursor.
	 * @param maximum the max value for the cursor.
	 * @param snapValues number of values to be represented by slider in the vertical direction
		      =0 means no snapping
	 * @param initialValue the intial value for the cursor.
	 * @param boolean  disables (enables) the value to change while you are dragging, or just after drag finishes
	 * @return the Slider DOJO widget
	 */
	static native JavaScriptObject createHorizontalSlider(int minimum,
			int maximum, int snapValues, int initialValue)
	/*-{
	 var widget = $wnd.dojo.widget.createWidget(
	 "SliderHorizontal",
	 {
	 minimumX: 			minimum,
	 maximumX:			maximum,
	 snapValuesX: 		snapValues,
	 isEnableX: true,
	 isEnableY: false,
	 initialValueX: 		initialValue,
	 backgroundSize:		"height:10px;width:200px;",
	 activeDrag: 		true
	 }
	 );
	 return widget;
	 }-*/;

	/**
	 * Arms a callbakc on the call of the method <code>onValueChanged</code> of the DOJO widget.
	 * This one gives the hand to the method <code> onValueChanged</code> of the GWT widget.
	 * @param dojoWidget the DOJO widget fulfilling the cursor.
	 */
	private native void setEventCallback(JavaScriptObject dojoWidget)
	/*-{
	 dojoWidget.onValueChanged = function(val) {
	 dojoWidget.gwtWidget.@com.objetdirect.tatami.client.Slider::onValueChanged(D)(val);
	 };
	 }-*/;

	/**
	 * Initializes the callbacks when the GWT widget is attached.
	 * Re-sets the last value of the cursor
	 */

	protected void onLoad() {
		setEventCallback(getDojoWidget());
		setValue(getValue()); // remet la valeur après que widget soit
		// rattachée.

	}

	/**
	 * Returns the value of the cursor.
	 * @return an integer 
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value for the cursor.
	 * @param value the new value to choose.
	 *            
	 */
	public void setValue(int value) {
		changeValue(value);
		if (isAttached()) {
			try {
				doSetValue(getDojoWidget(), getValue());
			} catch (Exception e) {
				GWT.log("ERROR", e);
			}
		}
	}

	/** 
	 * Tries to set the last value of the cursor after that the DOJO widget is created.
	 */
	public void doAfterCreation() {
		try {
			doSetValue(getDojoWidget(), getValue());
		} catch (Exception e) {
			GWT.log("ERROR", e);
		}
	}

	/**
	 * Asks to the widget DOJO to set the value of the cursor
	 * @param dojoWidget the DOJO widget
	 * @param value the value for the cursor
	 */
	private native void doSetValue(JavaScriptObject dojoWidget, int value)
	/*-{
	 dojoWidget.setValue(value);
	 
	 }-*/;

	/**
	 * Change the value of cursor for the GWT widget.
	 * If the value is a new value then a notification of a chaning state is 
	 * thrown.
	 * @param value the new value.
	 */
	private void changeValue(int value) {
		if (this.value != value) {
			this.value = value;
			if (changeListeners != null) {
				changeListeners.fireChange(this);
			}
		}
	}

	/**
	 * Callback method invoked by the DOJO widget when the cursor has changed.
	 * This method change the value for the cursor of the GWT widget to correspond with
	 * the value of the DOJO widget.
	 * @param value the new value.

	 */
	public void onValueChanged(double value) {
		changeValue((int) value);
	}

} //end of class
