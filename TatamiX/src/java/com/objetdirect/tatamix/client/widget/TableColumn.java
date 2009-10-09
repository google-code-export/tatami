package com.objetdirect.tatamix.client.widget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @deprecated used the datagrid of tatami instead
 *
 */
public class TableColumn extends Widget implements HasHTML {

	private TableCellRenderer renderer;
    
	private Object value;
	private int modelIndex;
	
	
	public static final String STYLE_SORT_ASC = "header-table-sort-asc";
	
	private boolean sortable = true;
	
	
	private boolean sort_asc = false;
	
	public TableColumn() {
		super();
		setElement(DOM.createTH());
		DOM.setElementAttribute(getElement(),"scope","col");
		setStylePrimaryName("header-table");
		sinkEvents(Event.MOUSEEVENTS);
		sinkEvents(Event.ONCLICK);
        DOM.setEventListener(getElement(), this);
		
	}
	
	
	 public void onBrowserEvent(Event event) {
		   switch (DOM.eventGetType(event)) {
			case Event.ONMOUSEOVER:{
				this.addStyleDependentName("hover");
				break;
			}
			case Event.ONMOUSEOUT:{
				this.removeStyleDependentName("hover");
				
				break;
			}
			case Event.ONCLICK:{
				onClick();
				break;
			}
		   }
		   }
 
	

		/**
		 * Sets the text content for this header
		 * @param value
		 */
		public void setText(String text) {
			 
			 DOM.setInnerText(getElement(), value.toString());
		}
		
		/**
		 * 
		 */
		public String getText() {
			return DOM.getInnerText(getElement());
		}

	 
		
	/**
	 * 
	 * @param value
	 */
	public void setHTML(String value) {
		 
		 DOM.setInnerHTML(getElement(), value.toString());
	}
	
	/**
	 * 
	 */
	public String getHTML() {
		return DOM.getInnerHTML(getElement());
	}
	
	/**
	 * 
	 * @param renderer
	 */
	public void setCellRenderer(TableCellRenderer renderer) {
		this.renderer = renderer;
		
	}
	
	
		
	/**
	 * 
	 * @return
	 */
	public TableCellRenderer getCellRenderer() {
		return this.renderer;
	}
	
	
		
	public int getModelIndex() {
		return this.modelIndex;
	}
	
	public void setModelIndex(int index) {
		this.modelIndex = index;
	}
	
	public boolean isSortable() {
		return this.sortable;
	}
	
	public boolean isAscSort() {
		return this.sort_asc;
	}
	
	public void onClick() {
		if ( sortable ) {
			if ( sort_asc) {
				sort_asc = false ;
				removeStyleDependentName("sort-asc");
				addStyleDependentName("sort-desc");
			} else {
				sort_asc = true ;
				removeStyleDependentName("sort-desc");
				addStyleDependentName("sort-asc");
			}
			
			
			
		}
	}

	
	
	
	
}
