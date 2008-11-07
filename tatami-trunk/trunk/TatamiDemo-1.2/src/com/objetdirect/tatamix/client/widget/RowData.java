package com.objetdirect.tatamix.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

/**
 * Don't use directly this class. 
 * This class is use by the object <code>Table</code>
 * @author Vianney Grassaud
 *
 */
public class RowData extends Widget {

	private List<Object> values;
	private int modelIndex;
	private boolean selected ;
	
	/**
	 * 
	 *Creates a <code>RowData</code> with mouse events
	 *
	 */
	public RowData() {
		super();
		values = new ArrayList<Object>();
		setElement(DOM.createTR());
		sinkEvents(Event.MOUSEEVENTS);
		selected = false;
		modelIndex = -1;
	    DOM.setEventListener(getElement(),this);
	}
	
	/**
	 * Sets the index of the row in the model
	 * @param index index of the row in the model
	 */
	public void setModelIndex(int index) {
		this.modelIndex = index;
	}
	
	/**
	 * Returns the index of the row in the model
	 * @return the index of the row in the model
	 */
	public int getModelIndex() {
		return this.modelIndex;
	}
	
	/**
	 * Add value associated with a renderer (a <code>Widget</code>) to this <code>DataRow</code> 
	 * @param value  the value to add
	 * @param renderer the <code>Widget</code> which represent the value. 
	 */
	public void addValue(Object value,Widget renderer) {
		Element td = DOM.createTD();
		DOM.appendChild(getElement(),td);
		
		if ( renderer != null) {
		  DOM.appendChild(td,renderer.getElement());
		} else {
			if ( value != null) {
			DOM.setInnerHTML(td,value.toString());
			}
		}
		DOM.sinkEvents(td,Event.ONCLICK);
		DOM.setEventListener(td,this);
		values.add(value);
	}
	
	/**
	 * Sets a value at the specified column
	 * @param col the index of the column 
	 * @param value the new value should not be <code>null</code>
	 * @param renderer the new renderer for the value,  can be <code>null</code>, if <code>null</code>, 
	 *        the <code>toString()</code> method is called on the value
	 */
	public void setValueAt(int col,Object value, Widget renderer) {
		Element td = DOM.getChild(getElement(),col);
		if ( renderer != null) {
			Element first = DOM.getFirstChild(td);
			DOM.removeChild(td,first);
			DOM.appendChild(td,renderer.getElement());
		
		} else {
			DOM.setInnerHTML(td,value.toString());
		}
		this.values.set(col,value);
	}
	
	/**
	 * Sets this <code>RowData</code> as selected
	 * @param selected <code>true</code> to set selected
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
		if ( selected) {
			this.addStyleDependentName("selected");
		} else {
			this.removeStyleDependentName("selected");
		}
	}
	
	/**
	 * Checks if this <code>RowData</code> is selected or not
	 * @return <code>true</code> if selected
	 */
	public boolean isSelected() {
		return this.selected;
	}
	
	/**
	 * Manages event
	 * @param event browser event
	 */
	public void onBrowserEvent(Event event) {
		 switch (DOM.eventGetType(event)) {
			case Event.ONMOUSEOVER:{
				this.addStyleName("hover");
				break;
			}
			case Event.ONMOUSEOUT:{
				this.removeStyleName("hover");
				
				break;
			}
			case Event.ONCLICK:{
				
				break;
			}
		   }
     }
	
	/**
	 * Returns the value at the specified column
	 * @param col index of a column
	 * @return an <code>Object</code>
	 */
	public Object getValueAt(int col) {
		return this.values.get(col);
	}
	
	/**
	 * Returns the number of column of this <code>DataRow</code>
	 * @return the number of column of this <code>DataRow</code>
	 */
	public int getColumnCount() {
		return this.values.size();
	}
	
}//end of the class
