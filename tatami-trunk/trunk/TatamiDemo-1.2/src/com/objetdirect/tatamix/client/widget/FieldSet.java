package com.objetdirect.tatamix.client.widget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;

public class FieldSet extends FlowPanel {

	/**
	 * Legend of the fieldset
	 */
	private Element legend;
	
	/**
	 * Creates a FieldSet HTML element
	 * with an empty legend
	 */
	public FieldSet() {
		super();
		setElement(DOM.createFieldSet());
		legend = DOM.createLegend();
		DOM.appendChild(getElement(),legend);
	}
	
	/**
	 * Sets the legend of this <code>FieldSet</code>
	 * @param text the text for the legend, can be HTML code
	 */
	public void setLegend(String text) {
		DOM.setInnerHTML(legend,text);
	}
	
	/**
	 * Returns the legend text of this <code>FieldSet</code>
	 * @return the legend text
	 */
	public String getLegend() {
		return DOM.getInnerHTML(legend);
	}
	
}
