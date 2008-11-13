package com.objetdirect.tatamix.client.widget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class FieldSet extends ComplexPanel {

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


	/**
	   * Adds a new child widget to the panel.
	   *
	   * @param w the widget to be added
	   */
	  @Override
	  public void add(Widget w) {
	    add(w, getElement());
	  }
   
	  /**
	   * Inserts a widget before the specified index.
	   *
	   * @param w the widget to be inserted
	   * @param beforeIndex the index before which it will be inserted
	   * @throws IndexOutOfBoundsException if <code>beforeIndex</code> is out of
	   *           range
	   */
	  public void insert(Widget w, int beforeIndex) {
	    insert(w, getElement(), beforeIndex, true);
	  }
}
