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
import com.google.gwt.user.client.DOM;

/**
 * Class for widgets with a cursor that permit to choose 2 integer values bounded by a max value and a min value.
 * On value is to choose on a X axis and the other value is to choose on the Y axis. Each value is bounded. 
 * Each max and min value for the axis is specified when the widget is created.
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
public class Paddle extends AbstractDojoFocus {

	/** the min value for the X position*/
	private int minimumX = 0;

	/** the max value for the X position*/
	private int maximumX = 0;

	/**
	 * No increasing for the Paddle. This property had been removed because a bug was detected 
	 * by the unit tests. If teh value of snapValues is 
	 * different than the property <code>maximum</code> then an error of 
	 * RuntimeException is thrown indicating an incompatibility in web
	 * mode. To avoid this problem, the feature will have the value of 
	 * <code>maximum</code> We keep a trace of this property nonetheless.
	 */

	private int snapValuesX = 0;

	/** the min value for the Y position*/
	private int minimumY = 0;

	/** the min value for the Y position*/
	private int maximumY = 0;

	/**
	 * No increasing for the Paddle. This property had been removed because a bug was detected 
	 * by the unit tests. If teh value of snapValues is 
	 * different than the property <code>maximum</code> then an error of 
	 * RuntimeException is thrown indicating an incompatibility in web
	 * mode. To avoid this problem, the feature will have the value of 
	 * <code>maximum</code> We keep a trace of this property nonetheless.
	 */
	private int snapValuesY = 0;

	/**
	 * the value for the X positon of the cursor 
	 * 
	 */
	private int valueX;

	/**
	 * the value for the Y positon of the cursor 
	 * 
	 */
	private int valueY;

	/**
	 * Creates the "Paddle" widget 
	 * @parma minimumX the min value for the X
	 * @param maximumX the max value for the X
	  * @param initialValueX the initial value for the X position
	 * @parma minimumY the min value for the Y 
	 * @param maximumY the max value for the Y
	 * @param initialValueY the initial value for the Y 
     */
	public Paddle(int minimumX, int maximumX, int initialValueX, int minimumY,
			int maximumY, int initialValueY) {
		super();
		this.maximumX = maximumX;
		this.maximumY = maximumY;
		this.minimumX = minimumX;
		this.minimumY = minimumY;
		this.snapValuesX = maximumX;
		this.snapValuesY = maximumY;
		this.valueX = initialValueX;
		this.valueY = initialValueY;

		this.setElement(getElement());
		DOM.setStyleAttribute(getElement(), "background-color", "black");
		DOM.setStyleAttribute(getElement(), "border", "1px solid black");
		
	}

	/**
	 * Creates the DOJO widget Paddle
	 * @throws Exception 
	 */
	public void createDojoWidget() throws Exception {
		// For unknown reasons when we call the method
		// setValue(int,int) and we attach 
		// the widget to the browser, the method onValueChanged is called with the default parameter
		// of initialValueX and initialValueY and we doesn't take count the modification of 
		// valueX and valueY, however,
		// the call of setValue(int x,int y) is well done first !!!, that's why 
		// valueX and valueY are given in parameter

		this.dojoWidget = createPaddle(minimumX, maximumX, snapValuesX, valueX,
				minimumY, maximumY, snapValuesY, valueY);

	}

	/**
	 * Returns the maximal value for the X position
	 * @return an integer position.
	 */
	
	public int getMaximumX() {
		return this.maximumX;
	}
	
	/**
	 * Returns the minimal value for the X position
	 * @return an integer position.
	 */
	
	
	public int getMinimumX() {
		return this.minimumX;
	}
	
	/**
	 * Returns the maximal value for the Y position
	 * @return an integer position.
	 */
	
	public int getMaximumY() {
		return this.maximumY;
	}
	
	/**
	 * Returns the minimal value for the Y position
	 * @return an integer value 
	 */
	
	public int getMinimumY() {
		return this.minimumY;
	}
	
	
	/**
	 * Creates the DOJO widget "Paddle"
	 * @parma minimumX the min value for the X
	 * @param maximumX the max value for the X
	 * @param snapValuesX number of values to be represented by slider in the vertical direction
		      =0 means no snapping
	 * @param initialValueX the initial value for the X position
	 * @parma minimumY the min value for the Y 
	 * @param maximumY the max value for the Y
	 * @param snapValuesY number of values to be represented by slider in the vertical direction
		      =0 means no snapping
	 * @param initialValueY the initial value for the Y 
	 * @param boolean disables (enables) the value to change while you are dragging, or just after drag finishes
	 * @return the DOJO widget.
	 */
	static native JavaScriptObject createPaddle(int minimumX, int maximumX,
			int snapValuesX, int initialValueX, int minimumY, int maximumY,
			int snapValuesY, int initialValueY)
	/*-{
	 var widget = $wnd.dojo.widget.createWidget(
	 "Slider",
	 {
	 minimumX: 			minimumX,
	 maximumX:			maximumX,
	 snapValuesX: 		snapValuesX,
	 initialValueX: 		initialValueX,
	 minimumY: 			minimumY,
	 maximumY:			maximumY,
	 snapValuesY: 		snapValuesY,
	 initialValueY: 		initialValueY,
	 isEnableY: true,
	 isEnableX: true,
	 activeDrag: true,
	 backgroundSize:		"width:166px;height:166px;"
	 }
	 );
	 return widget;
	 }-*/;

	/**
	 * Arms a callback on the call of the method <code>onValueChanged</code> of the DOJO widget.
	 * This one gives the hand to the method <code> onValueChanged</code> of the GWT widget.
	 * @param dojoWidget the DOJO widget fulfilling the cursor.
	 */
	private native void setEventCallback(JavaScriptObject dojoWidget)
	/*-{
	 dojoWidget.onValueChanged = function(valueX, valueY) {
	 dojoWidget.gwtWidget.@com.objetdirect.tatami.client.Paddle::onValueChanged(DD)(valueX, valueY);
	 };
	 }-*/;

	
	/**
	 * Initializes the callbacks when the GWT widget is attached.
	 * Re-sets the last values of the cursor
	 */
	protected void onLoad() {
		setEventCallback(getDojoWidget());
		setValue(valueX, valueY);
	}

	/**
	 * Returns the name of the DOJO widget
	 * @return "Slider"
	 */
	public String getDojoName() {
		return "Slider";
	}

	/**
	 * Returns the value of the cursor in the X position.
	 * @return an integer 
	 */
	public int getValueX() {
		return valueX;
	}

	/**
	 * Returns the value of the cursor in the Y position.
	 * @return an integer 
	 */
	public int getValueY() {
		return valueY;
	}

	/**
	 * Sets the values for the cursor.
	 * @param valueX the new value for the X
     * @param valueY the new value for the Y
	 */
	public void setValue(int valueX, int valueY) {
		changeValue(valueX, valueY);
		if (isAttached()) {
			try {
				doSetValue(getDojoWidget(), getValueX(), getValueY());
			} catch (Exception e) {
				GWT.log("ERROR", e);
			}
		}
	}

	/**
	 * Asks to the widget DOJO to set the value of the cursor
	 * @param dojoWidget the DOJO widget 
	 * @param valueX the value for the X
	 * @param valueY the value for the Y
	 * 
	 */
	private native void doSetValue(JavaScriptObject dojoWidget, int valueX,
			int valueY)
	/*-{
	 dojoWidget.setValueX(valueX);
	 dojoWidget.setValueY(valueY);
	 }-*/;

	/**
	 * Changes the values of cursor for the GWT widget.
	 * If a value is a new value then a notification of a chaning state is 
	 * thrown.
	 * @param valueX the new value for X.
	 * @param valueY the new value for Y.
	 */
	void changeValue(int valueX, int valueY) {
		if (this.valueX != valueX || this.valueY != valueY) {
			this.valueX = valueX;
			this.valueY = valueY;
			if (changeListeners != null) {
				changeListeners.fireChange(this);
			}
		}
	}

	/** 
	 * Tries to set the last values of the cursor after that the DOJO widget is created.
	 */
	public void doAfterCreation() {
		try {
			doSetValue(getDojoWidget(), getValueX(), getValueY());
		} catch (Exception e) {
			GWT.log("ERROR", e);
		}
	}

	/**
	 * A Callback method invoked by the DOJO widget when the cursor has changed.
	 * This method change the values for the cursor of the GWT widget to correspond with
	 * the value of the DOJO widget.
	 * @param valueX the new value for X
	 * @param valueY the new value for Y
	 */
	public void onValueChanged(double valueX, double valueY) {
		changeValue((int) valueX, (int) valueY);
	}

}//end of class
