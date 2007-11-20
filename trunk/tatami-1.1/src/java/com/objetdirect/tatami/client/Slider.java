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
import com.google.gwt.user.client.Element;

/**
 * Class for widgets with a cursor that permit to choose an integer value
 * bounded by a max value and a min value. The max and min value are specified
 * when the widget is created.
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

	private final String LEFT = "leftDecoration";

	private final String RIGHT = "rightDecoration";

	private final String TOP = "topDecoration";

	private final String BOTTOM = "bottomDecoration";

	private SliderRule sliderRuleTop;

	private SliderRule sliderRuleBottom;

	private SliderRule sliderLabelTop;

	private SliderRule sliderLabelBottom;

	/** Type for the cursor position */
	private String type = HORIZONTAL;

	/** min value */
	private int minimum = 0;

	/** max value */
	private int maximum = 100;

	private boolean showButtons = true;

	private int width = 200;

	private int height = 10;

	/** value choose by the user */
	private int value = 0;

	/**
	 * Creates a Slider cursor
	 * 
	 * @param type  the position of the cursor : VERTICAL, HORIZONTAL
	 * @parma minimum the min value for the cursor
	 * @param maximum  the max value for the cursor
	 * @param initialValue the initial value
	 * @param showButtons  Show increment/decrement buttons at the ends of the slider
	 */
	public Slider(String type, int minimum, int maximum, int initialValue,
			boolean showButtons) {
		super();
		this.maximum = maximum;
		this.minimum = minimum;
		this.value = initialValue;
		this.showButtons = showButtons;
		setType(type);
		this.setElement(getElement());

	}

	/**
	 * Sets the size if the DOJO DOM element.
	 * 
	 * @param width
	 *            the width to apply
	 * @param height
	 *            the height to apply
	 */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		applySize();
	}

	/**
	 * Applies the new size to the DOJO DOM element
	 * 
	 */
	private void applySize() {
		if (isAttached()) {
			Element element = DojoController.getInstance().getDomNode(this);
			DOM.setStyleAttribute(element, "width", width + "px");
			DOM.setStyleAttribute(element, "height", height + "px");
		}
	}

	/**
	 * Sets enabled or not the Slider bar
	 * 
	 * @param enabled
	 *            true to set the slider enabled
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (isAttached()) {
			setEnabled(getDojoWidget(), enabled);
		}
	}

	/**
	 * Sets enabled or not the Slider bar
	 * 
	 * @param dojoWidget
	 *            the DOJO slider
	 * @param enabled
	 *            true to set enabled
	 */
	native private void setEnabled(JavaScriptObject dojoWidget, boolean enabled) /*-{
	 dojoWidget.setDisabled(!enabled);
	 }-*/;

	/**
	 * Returns the name of the DOJO widget
	 * 
	 * @return "Slider"
	 */
	public String getDojoName() {
		return "dijit.form.Slider";
	}

	/**
	 * Sets the type position for the cursor.
	 * 
	 * @param type
	 *            the position for the cursor : VERTICAL || HORIZONTAL
	 * @throws IllegalArgumentException
	 *             if the given type is not valid.
	 */
	private void setType(String type) {
		if (HORIZONTAL.equals(type) || VERTICAL.equals(type)) {
			this.type = type;
		} else {
			throw new IllegalArgumentException("type : " + type);
		}
	}

	/**
	 * Returns the max value for the cursor. The value is defined during the
	 * creation.
	 * 
	 * @return an integer.
	 */
	public int getMaximum() {
		return this.maximum;
	}

	/**
	 * Returns the mininal value for the cursor.
	 * 
	 * @return an integer.
	 */

	public int getMinimum() {
		return this.minimum;
	}

	/**
	 * Creates the Slider object in a horizontal or a vertical position
	 * according to the type.
	 */
	public void createDojoWidget() throws Exception {

		if (HORIZONTAL.equals(type)) {
			this.dojoWidget = createHorizontalSlider(minimum, maximum, value,
					showButtons);
		} else {
			this.dojoWidget = createVerticalSlider(minimum, maximum, value,
					showButtons);
			setSize(10, 200);
		}

	}

	/**
	 * Creates a Slider DOJO widgte in a vertical position.
	 * 
	 * @parma minimum the min value for the cursor.
	 * @param maximum
	 *            the max value for the cursor.
	 * @param initialValue
	 *            the intial value for the cursor.
	 * @param showButtons
	 *            Show increment/decrement buttons at the ends of the slider
	 * @return the Slider DOJO widget
	 */
	private native JavaScriptObject createVerticalSlider(int minimum,
			int maximum, int initialValue, boolean showButtons)
	/*-{
	 var widget = new $wnd.dijit.form.VerticalSlider( 	 {
	 minimum: minimum,
	 maximum: maximum,
	 showButtons :  showButtons,
	 value:initialValue,
	 style:"height:200px;",
	 discreteValues: maximum,
	 intermediateChanges: true
	 }
	 );
	 
	 return widget;
	 }-*/;

	/**
	 * Creates a Slider DOJO widgte in a horinzontal position.
	 * 
	 * @parma minimum the min value for the cursor.
	 * @param maximum
	 *            the max value for the cursor.
	 * @param initialValue
	 *            the intial value for the cursor.
	 * @param showButtons
	 *            Show increment/decrement buttons at the ends of the slider
	 * @return the Slider DOJO widget
	 */
	static native JavaScriptObject createHorizontalSlider(int minimum,
			int maximum, int initialValue, boolean showButtons)

	/*-{
	 var widget = new $wnd.dijit.form.HorizontalSlider( 	 {
	 minimum: minimum,
	 maximum: maximum,
	 showButtons :  showButtons,
	 value:initialValue,
	 
	 discreteValues: maximum,
	 intermediateChanges: true
	 }
	 );
	 return widget;
	 }-*/;

	/**
	 * Arms a callbakc on the call of the method <code>onValueChanged</code>
	 * of the DOJO widget. This one gives the hand to the method
	 * <code> onValueChanged</code> of the GWT widget.
	 * 
	 * @param dojoWidget
	 *            the DOJO widget fulfilling the cursor.
	 */
	private native void setEventCallback(JavaScriptObject dojoWidget)
	/*-{
	 dojoWidget.onChange = function(val) {
	 dojoWidget.gwtWidget.@com.objetdirect.tatami.client.Slider::onValueChanged(D)(val);
	 
	 };
	 }-*/;

	/**
	 * Initializes the callbacks when the GWT widget is attached. Re-sets the
	 * last value of the cursor
	 */

	protected void onLoad() {
		setEventCallback(getDojoWidget());
		setEnabled(isEnabled());
		setValue(getValue()); // 
		applySize();
	
		buildSliderRuleTop();
		buildSliderRuleBottom();

	}

	/**
	 * Returns the height of the Slider
	 * 
	 * @return the height of the Slider
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Returns the width of the Slider
	 * 
	 * @return the width of the Slider
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Returns the value of the cursor.
	 * 
	 * @return an integer
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value for the cursor.
	 * 
	 * @param value
	 *            the new value to choose.
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
	 * Tries to set the last value of the cursor after that the DOJO widget is
	 * created.
	 */
	public void doAfterCreation() {
		try {
		
			doSetValue(getDojoWidget(), getValue());
			
		} catch (Exception e) {
			GWT.log("ERROR", e);
		}
	}

	
	/**
	 * 
	 *
	 */
	public void removeRuleTop() {
		removeDojoRule(sliderRuleTop);
		sliderRuleTop = null;
	}
	
    /**
     * 
     *
     */
	public void removeRuleBottom() {
    	removeDojoRule(sliderRuleBottom);
		sliderRuleBottom = null;
	}
	
	
	
	public void removeRuleAndLabel() {
		removeDojoRule(sliderRuleBottom);
		removeDojoRule(sliderRuleTop);
	}
	
	
	private void removeDojoRule(SliderRule rule) {
		if ( rule != null) {
		 removeChild(getDojoWidget(),rule.child);
		 DojoController.getInstance().destroy(rule.child);
		}
		
	}
    /**
     * 
     * @param count
     * @param ruleStyle
     */
	public void setRuleTop(int count, String ruleStyle) {
		if ( sliderRuleTop != null) {
			removeRuleTop();
		}
		this.sliderRuleTop = new SliderRule();
		this.sliderRuleTop.count = count;
		this.sliderRuleTop.ruleStyle = ruleStyle;
		if ( isAttached()) {
			buildSliderRuleTop();
		}
	}
	
	/**
	 * 
	 */
	public void doBeforeDestruction() {
		this.removeRuleAndLabel();
		
	}
	
	
	/**
	 * 
	 * @param count
	 * @param ruleStyle
	 */
	public void setRuleBottom(int count, String ruleStyle) {
		//if we have already a sliderrule 
		if ( sliderRuleBottom != null) {
			removeRuleBottom();
		}
		this.sliderRuleBottom = new SliderRule();
		this.sliderRuleBottom.count = count;
		this.sliderRuleBottom.ruleStyle = ruleStyle;
		if ( isAttached()) {
			buildSliderRuleBottom();
		}
	}
	
	
	public void setRuleLeft(int count, String ruleStyle) {
		setRuleTop(count,ruleStyle);
	}
	
	public void setRuleRight(int count, String ruleStyle) {
		setRuleBottom(count,ruleStyle);
	}
	

	
	/**
	 * Asks to the widget DOJO to set the value of the cursor
	 * 
	 * @param dojoWidget
	 *            the DOJO widget
	 * @param value
	 *            the value for the cursor
	 */
	private native void doSetValue(JavaScriptObject dojoWidget, int value)
	/*-{
	 dojoWidget.setValue(value);
	 
	 }-*/;

	/**
	 * Change the value of cursor for the GWT widget. If the value is a new
	 * value then a notification of a chaning state is thrown.
	 * 
	 * @param value
	 *            the new value.
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
	 * This method change the value for the cursor of the GWT widget to
	 * correspond with the value of the DOJO widget.
	 * 
	 * @param value
	 *            the new value.
	 * 
	 */
	public void onValueChanged(double value) {
		changeValue((int) value);
	}

	private class SliderRule {
		int count = 3;
		JavaScriptObject child;
		String position = TOP;

		String ruleStyle = "";
	}

	private native void addChild(JavaScriptObject dojoWidget,JavaScriptObject child)/*-{
	     dojoWidget.addChild(child);
	     dojoWidget.startup();
	     child.startup();
	 }-*/;

	private native void removeChild(JavaScriptObject dojoWidget,JavaScriptObject child)/*-{
	    dojoWidget.removeChild(child);
	}-*/;
	
	/**
	 * Assumes that the Slider is attached.
	 * 
	 */
	private void buildSliderRuleTop() {
		if (sliderRuleTop != null) {
			if (VERTICAL.equals(type)) {
				sliderRuleTop.position = LEFT;
				sliderRuleTop.child = createVerticalRule(sliderRuleTop.count,sliderRuleTop.ruleStyle, sliderRuleTop.position);
			} else {
				sliderRuleTop.position = TOP;
				sliderRuleTop.child = createHorizontalRule(sliderRuleTop.count,sliderRuleTop.ruleStyle, sliderRuleTop.position);
			}
			addChild(getDojoWidget(), sliderRuleTop.child);
		}
   	    
    }
	
	private void buildSliderRuleBottom() {
		if (sliderRuleBottom != null) {
			if (VERTICAL.equals(type)) {
				sliderRuleBottom.position = RIGHT;
				sliderRuleBottom.child = createVerticalRule(sliderRuleBottom.count, sliderRuleBottom.ruleStyle,sliderRuleBottom.position);
			} else {
				sliderRuleBottom.position = BOTTOM;
				sliderRuleBottom.child = createHorizontalRule(sliderRuleBottom.count, sliderRuleBottom.ruleStyle,sliderRuleBottom.position);
			}
		addChild(getDojoWidget(), sliderRuleBottom.child);
   	    }
	}

	/**
	 * inner class to represent a label
	 * 
	 *
	 */
	private class SliderLabels {
		String labels[] = new String[0];
		String position = TOP;
		String labelStyle = "";
		JavaScriptObject child;
	}

	private native JavaScriptObject createVerticalRule(int count,String ruleStyle, String position)/*-{
	     return new $wnd.dijit.form.VerticalRule({count:count,ruleStyle:ruleStyle,container:position});
	 }-*/;

	private native JavaScriptObject createHorizontalRule(int count,String ruleStyle, String position)/*-{
	    return new $wnd.dijit.form.HorizontalRule({count:count,ruleStyle:ruleStyle,container:position});
	 }-*/;

} // end of class
