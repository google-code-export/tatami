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

	private RuleMark sliderRuleTop;

	private RuleMark sliderRuleBottom;

	private RuleLabels labelTop;

	private RuleLabels labelBottom;

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
	public Slider(String type, int minimum, int maximum, int initialValue,boolean showButtons) {
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
	 * @param width the width to apply
	 * @param height the height to apply
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
	 * @param enabled <code>true</code> to set the slider enabled
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (isAttached()) {
			setEnabled(getDojoWidget(), enabled);
		}
	}

	/**
	 * Sets enabled or not the Slider bar
	 * @param dojoWidget the DOJO slider
	 * @param enabled <code>true</code> to set enabled
	 */
	native private void setEnabled(JavaScriptObject dojoWidget, boolean enabled) /*-{
	 dojoWidget.setDisabled(!enabled);
	 }-*/;

	/**
	 * Returns the name of the DOJO widget
	 * @return "dijit.form.Slider"
	 */
	public String getDojoName() {
		return "dijit.form.Slider";
	}

	/**
	 * Sets the type position for the cursor.
	 * @param type  the position for the cursor : VERTICAL || HORIZONTAL
	 * @throws IllegalArgumentException  if the given type is not valid.
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
	 * @param maximum the max value for the cursor.
	 * @param initialValue  the intial value for the cursor.
	 * @param showButtons Show increment/decrement buttons at the ends of the slider
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
	 * @parma minimum the min value for the cursor.
	 * @param maximum the max value for the cursor.
	 * @param initialValue the intial value for the cursor.
	 * @param showButtons  Show increment/decrement buttons at the ends of the slider
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
	 * @param dojoWidget the DOJO widget fulfilling the cursor.
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
	
		buildRuleMark(sliderRuleTop);
		buildRuleMark(sliderRuleBottom);
		buildRuleMark(labelTop);
		buildRuleMark(labelBottom);

	}

	/**
	 * Returns the height of the Slider
	 * @return the height of the Slider
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Returns the width of the Slider
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
	 * Removes the rule mark at the top of this <code>Slider</code>
	 *
	 */
	public void removeRuleTop() {
		removeDojoRule(sliderRuleTop);
		sliderRuleTop = null;
	}
	
    /**
     * * Removes the rule mark at the bottom of this <code>Slider</code>
     *
     */
	public void removeRuleBottom() {
    	removeDojoRule(sliderRuleBottom);
		sliderRuleBottom = null;
	}
	
	/**
	 * Removes the labels at the top of this <code>Slider</code>
	 *
	 */
	public void removeLabelTop() {
		removeDojoRule(labelTop);
		labelTop = null;
	}
	
	/**
	 * Removes the labels at the bottom of this <code>Slider</code>
	 *
	 */
	public void removeLabelBottom() {
		removeDojoRule(labelBottom);
		labelBottom = null;
	}
	
	/**
	 *Removes all rule mark and labels from this <code>Slider</code>
	 *@see #removeLabelBottom()
	 *@see #removeLabelTop()
	 *@see #removeRuleBottom()
	 *@see #removeRuleTop()
	 */
	public void removeRuleAndLabel() {
		removeLabelBottom();
		removeLabelTop();
		removeRuleBottom();
		removeRuleTop();
	}
	
	/**
	 * Removes a <code>RuleMark</code> from this <code>Slider</code>
	 * @param rule a <code>RuleMark</code> 
	 */
	private void removeDojoRule(RuleMark rule) {
		if ( rule != null) {
		 DojoController controller = DojoController.getInstance();
		 controller.removeChild(this,rule.getDojoWidget());
		 controller.destroy(rule.getDojoWidget());
		}
		
	}
    
	/**
     * Sets the <code>RuleMark</code> at the top of this <code>slider</code> 
     * If this <code>Slider</code> is vertical then the  <code>RuleMark</code>
     * will position at the left.
     * @param count the number of mark to print 
     * @param size the size for the mark in pixel, em etc...
     */
	public void setRuleTop(int count, String size) {
		if ( sliderRuleTop != null) {
			removeRuleTop();
		}
		if (VERTICAL.equals(type)) {
			sliderRuleTop = new RuleMark(RuleMark.VERTICAL,count,size,LEFT);
		} else {
			sliderRuleTop = new RuleMark(RuleMark.HORIZONTAL,count,size,TOP);
		}
			
		if ( isAttached()) {
			buildRuleMark(sliderRuleTop);
		}
	}
	
	
	/**
	 * Sets the <code>RuleLabels</code> to print at the top of this <code>Slider</code>
	 * If this <code>Slider</code> is vertical then the  <code>RuleLabels</code>
     * will position at the left.
	 * @param labels the labels to print 
	 * @param style the style to use for the label (color, font etc...)
	 */
	public void setLabelsTop(String[] labels,String style) {
		if ( labelTop != null) {
			removeLabelTop();
		}
		if (VERTICAL.equals(type)) {
			labelTop = new RuleLabels(RuleLabels.VERTICAL,labels,style,LEFT);
		} else {
			labelTop = new RuleLabels(RuleLabels.HORIZONTAL,labels,style,TOP);
		}
			
		if ( isAttached()) {
			buildRuleMark(labelTop);
		}
	}
	
	/**
	 * Sets the <code>RuleLabels</code> to print at the bottom of this <code>Slider</code>
	 * If this <code>Slider</code> is vertical then the  <code>RuleLabels</code>
     * will position at the right.
	 * @param labels the labels to print 
	 * @param style the style to use for the label (color, font etc...)
	 */
	public void setLabelsBottom(String[] labels,String style) {
		if ( labelBottom != null) {
			removeLabelTop();
		}
		if (VERTICAL.equals(type)) {
			labelBottom = new RuleLabels(RuleLabels.VERTICAL,labels,style,RIGHT);
		} else {
			labelBottom = new RuleLabels(RuleLabels.HORIZONTAL,labels,style,BOTTOM);
		}
			
		if ( isAttached()) {
			buildRuleMark(labelBottom);
		}
	}
	
	
	/**
	 *Removes all <code>RuleMark</code> and <code>RuleLabel</code> 
	 */
	public void doBeforeDestruction() {
		this.removeRuleAndLabel();
    }
	
	
	/**
     * Sets the <code>RuleMark</code> at the bottom of this <code>slider</code>.
     * If this <code>Slider</code> is vertical then the  <code>RuleMark</code>
     * will position at the right. 
     * @param count the number of mark to print 
     * @param size the size for the mark in pixel, em etc...
     */
	public void setRuleBottom(int count, String size) {
		//if we have already a sliderrule 
		if ( sliderRuleBottom != null) {
			removeRuleBottom();
		}
		if (VERTICAL.equals(type)) {
			sliderRuleBottom = new RuleMark(RuleMark.VERTICAL,count,size,RIGHT);
		} else {
			sliderRuleBottom = new RuleMark(RuleMark.HORIZONTAL,count,size,BOTTOM);
		}
		
		if ( isAttached()) {
			buildRuleMark(sliderRuleBottom);
			
		}
	}
	
	/**
     * Sets the <code>RuleMark</code> at the left of this <code>slider</code> 
     * If this <code>Slider</code> is horizontal then the  <code>RuleMark</code>
     * will position at the top.
     * @param count the number of mark to print 
     * @param size the size for the mark in pixel, em etc...
     */
	public void setRuleLeft(int count, String size) {
		setRuleTop(count,size);
	}
	
	/**
	 * Sets the <code>RuleLabels</code> to print at the left of this <code>Slider</code>
	 * If this <code>Slider</code> is horizontal then the <code>RuleLabels</code>
     * will position at the top.
	 * @param labels the labels to print 
	 * @param style the style to use for the label (color, font etc...)
	 */
	public void setLabelsLeft(String[] labels, String style) {
		setLabelsTop(labels,style);
	}
	
	/**
     * Sets the <code>RuleMark</code> at the right of this <code>slider</code> 
     * If this <code>Slider</code> is horizontal then the  <code>RuleMark</code>
     * will position at the bottom.
     * @param count the number of mark to print 
     * @param size the size for the mark in pixel, em etc...
     */
	public void setRuleRight(int count, String size) {
		setRuleBottom(count,size);
	}
	
	/**
	 * Sets the <code>RuleLabels</code> to print at the right of this <code>Slider</code>
	 * If this <code>Slider</code> is vertical then the  <code>RuleLabels</code>
     * will position at the bottom.
	 * @param labels the labels to print 
	 * @param style the style to use for the label (color, font etc...)
	 */
	public void setLabelsRight(String[] labels, String style) {
		setLabelsBottom(labels,style);
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
	 * Change the value of cursor for the GWT widget. If the value is a new
	 * value then a notification of a chaning state is thrown.
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
	 * This method change the value for the cursor of the GWT widget to
	 * correspond with the value of the DOJO widget.
	 * @param value the new value.
	 * 
	 */
	public void onValueChanged(double value) {
		changeValue((int) value);
	}
		

	/**
	 * Assumes that the Slider is attached.
	 * @param rule a <code>RuleMark</code>
	 * 
	 */
	private void buildRuleMark(RuleMark rule) {
		if ( rule != null) {
		  try {
		  	rule.createDojoWidget();
		    DojoController.getInstance().addChild(this, rule.getDojoWidget());
		  	
		  } catch (Exception e ) {
			 GWT.log("ERROR", e);
		  }
		}
    }


} // end of class
