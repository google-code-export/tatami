package com.objetdirect.tatamix.client.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.TableListenerCollection;
import com.google.gwt.user.client.ui.Widget;
/**
 * This class permits to create a <code>Table</code> like
 * <code>FlexTable</code>. The table used a <code>TableModel</code>
 *  to populate the data. Some CSS specifications were added in order to
 *  have a better aspect.
 *
 *  below an example of a CSS classes used with the <code>Table</code>
 *
 *  .clara-Table {} for the Table
 *  .clara-Table .header-table {} for the headers of the table
    .clara-Table .header-table-sort-asc {} for headers when the column is sorted
    .clara-Table .header-table-sort-desc {} the same
    .clara-Table .header-table-hover {} used when the mouse over an header
    .clara-Table tr.odd {} used for each odd row in the table
    .clara-Table tr.even {} used for even row in the table
    .clara-Table tr.hover{} used when the mouse is over on a row
    .clara-Table tr.odd-selected, .diamants-Table .tr.even-selected {} used
    when a row is selected.


   You can also specify a <code>TableCellRenderer</code> for each column of the table.
 *
 *
 *
 *
 * @author Vianney Grassaud
 *
 */
public class Table extends FocusWidget implements SourcesTableEvents,  HasText, HasHTML {

	private TableModel model; //the model of the table

	private TableColumn[] columns; //the columns of the table

	/**
	 * The list of <code>RowData</code>
	 */
	private List rowData;

	private Map rendererMap; //a map between TableCellRenderer and column classes

	//the tbody HTML element for the table
	private Element tbody;
	//the tHead HTML element for the table
	private Element tHead;

	private Element caption;

	//the last header sorted
	private int lastHeaderSorted = -1;

	//the first row selected
	private int rowSelected = -1; //the current row selected

	//the rows selected if multiple
	private List selection;


	public static final int MULTIPLE_SELECTION = 0;
	public static final int SINGLE_SELECTION = 1;
	public static final int NO_SELECTION = 2;

	private int selectionMode = SINGLE_SELECTION;

	private TableListenerCollection tableListenerCollection;
	private SortListenerCollection sortListeners;

	private final String SUMMARY_ATT = "summary";

	private String summary = "";

	/**
	 * Creates a new <code>Table</code> from a <code>TableModel</code>
	 * @param model the model to use
	 */
	public Table(TableModel model) {
		super();
		setElement(DOM.createTable());
		DOM.setElementAttribute(getElement(), SUMMARY_ATT, "");
		tbody  = DOM.createTBody();
		tHead = DOM.createTHead();
		caption = DOM.createCaption();
		DOM.appendChild(getElement(), caption);
		DOM.appendChild(getElement(), tHead);
		DOM.appendChild(getElement(), tbody);
		setCellSpacing(0);
		setCellPadding(0);
		setStylePrimaryName("clara-Table");
		this.selection = new ArrayList();
		this.rowData = new ArrayList();
		this.model = model;
		rendererMap = new HashMap();
		setHeader();
		setTbody();
		sinkEvents(Event.ONCLICK);


	}


	/**
	 * Sets the caption of this <code>Table</code>.
	 * @param text text use for the caption, the text will not be interpreted as HTML code
	 * @see #setHTML(String)
	 */
	public void setText(String text) {
		DOM.setInnerText(caption,text);
	}

	/**
	 * Returns the text of the caption of this <code>Table</code>
	 * @return the inner text of the caption element.
	 * @see #getHTML()
	 */
	public String getText() {
		return DOM.getInnerText(caption);
	}

	/**
	 * Sets the text of the caption of this <code>Table</code>
	 * @param html, the string is interpreted as HTML content.
	 * @see {@link #setText(String)}
	 */
	public void setHTML(String html) {
		DOM.setInnerHTML(caption,html);
	}

	/**
	 * Return the inner html code of the caption of this <code>Table</code>
	 * @return the inner HTML code
	 * @see #getText()
	 */
	public String getHTML() {
		return DOM.getInnerHTML(caption);
	}

	/**
	 * Returns the value of summary attribute of this <code>Table</code>
	 * @return the summary used for accessibility
	 */
	public String getSummary() {
		return this.summary;
	}

	/**
	 * Sets the summary attribute for this <code>Table</code>
	 * this attribute is used for the accessibility.
	 * @param text text which summarize the table.
	 */
	public void setSummary(String text) {
		this.summary = text;
		DOM.setElementAttribute(getElement(), SUMMARY_ATT, summary);
	}

	/**
	 * Sets the selection mode for this <code>Table</code>
	 * @param mode availables values : MULTIPLE_SELECTION | SINGLE_SELECTION | NO_SELECTION
	 */
	public void setSelectionMode(int mode) {
		clearSelection();
		this.selectionMode = mode;
		if ( selectionMode == NO_SELECTION) {
			selection = null;
		} else {
			selection = new ArrayList();
		}
	}

	/**
	 * Returns the selection mode used
	 * @return MULTIPLE_SELECTION | SINGLE_SELECTION | NO_SELECTION
	 */
	public int getSelectionMode() {
		return this.selectionMode;
	}


	/**
	 * Sets the cell spacing between cells
	 * @param spacing the spacing between cells in pixels, em etc...
	 */
	public void setCellSpacing(int spacing) {
		DOM.setElementPropertyInt(getElement(),"cellSpacing",spacing);
	}

	/**
	 * Sets the spacing within cells
	 * @param padding the spacing within cells in pixels, em etc...
	 */
	public void setCellPadding(int padding) {
		DOM.setElementPropertyInt(getElement(),"cellPadding",padding);
	}

	/**
	 * Manage the browser event
	 * @param event
	 */
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		switch (DOM.eventGetType(event)) {

		case Event.ONCLICK: {
			Element el = DOM.eventGetTarget(event);
			Element tr = DOM.getFirstChild(tHead);
			int indexHeader = DOM.getChildIndex(tr,el);
			if ( indexHeader != -1) {
				TableColumn column = columns[indexHeader];
				if ( column.isSortable() && sortListeners!=null) {

					sortListeners.fireSort(column.isAscSort(), column.getModelIndex());
				}
				if ( lastHeaderSorted != -1 && lastHeaderSorted != indexHeader) {
			    	columns[lastHeaderSorted].removeStyleDependentName("sort-asc");
			    	columns[lastHeaderSorted].removeStyleDependentName("sort-desc");
  			    }
				lastHeaderSorted = indexHeader;
			}

			//manage the selection of a cell (a row)
			if ( DOM.isOrHasChild(tbody,el)) {
			//get the row of a the cell
			Element rowCell = getRow(el);
			//the index of the cell in the row
			int indexCell = DOM.getChildIndex(rowCell,getCell(rowCell,el));
			if ( indexCell != -1) {
			    //the index of the row in the tbody element
				int indexRow = DOM.getChildIndex(tbody,rowCell);
				if ( indexRow != -1 ) {
					manageSelection(indexRow,indexCell,event);
				}

			}
			}
			break;
		}
		}

	}

	/**
	 * Manage the selection of a cell
	 * @param indexRow index of the row
	 * @param indexCol index of the cell
	 * @param event the mouse event
	 */

	private void manageSelection(int indexRow,int indexCol,Event event) {


		boolean fireClick = false;
		switch (getSelectionMode()) {

		  default : {
			  fireClick = true;
			  clearSelection();
			  rowSelected = indexRow;
			  selection.add(new Integer(indexRow));
			  //getRowData(indexRow).setSelected(true);
			  selectRowData(indexRow,true);
			  break;

	      }
		  case MULTIPLE_SELECTION: {
			  if ( DOM.eventGetCtrlKey(event)) {
				  rowSelected = indexRow;
				  selection.add(new Integer(indexRow));
				  selectRowData(indexRow,true);
				  //this.getRowData(indexRow).setSelected(true);;
			  } else if (  DOM.eventGetShiftKey(event)) {
				  int oldIndex = indexRow;
				  if ( !this.selection.isEmpty()) {
			    	  oldIndex = ((Integer)selection.get(0)).intValue();
			      }

			      clearSelection();
			      rowSelected = indexRow;
			      if ( indexRow > oldIndex) {
			    	  for (int i=oldIndex; i <=indexRow;i++) {
			    		  selectRowData(i,true);
			    		  //this.getRowData(i).setSelected(true);
			    		  selection.add(new Integer(i));
			    	  }
			      } else {
			    	  for (int i=indexRow; i<= oldIndex;i++) {
			    		  selectRowData(i,true);
			    		  //this.getRowData(i).setSelected(true);
			    		  selection.add(new Integer(i));

			    	  }
			      }

			  } else {
				  clearSelection();
				  rowSelected = indexRow;
				  selection.add(new Integer(indexRow));
				  selectRowData(indexRow,true);
				  //this.getRowData(indexRow).setSelected(true);
			  }
			  fireClick = true;
			  break;
		  }
		  case NO_SELECTION: {
			  fireClick = false;
              break;
		  }

		}

		if ( fireClick && this.tableListenerCollection != null ) {
			tableListenerCollection.fireCellClicked(this,indexRow,indexCol);
		}



	}

	private void selectRowData( int index, boolean select) {
		RowData row = getRowData(index);
		row.setSelected(select);
		updateRowData(row, index);
	}


	/**
	 * Returns the index of the selected rows
	 * @return
	 */
	public int[] getSelectedRows() {
		int[] rows = new int[this.selection.size()];
		for (int i=0; i <rows.length;i++) {
			Integer nSelect = (Integer)selection.get(i);
			rows[i]= nSelect.intValue();
		}
		return rows;
	}

	/**
	 * Clear the selection
	 *
	 */
	public void clearSelection() {
		int[] s = getSelectedRows();
		for (int i=0; i < s.length; i++) {
			//getRowData(s[i]).removeStyleDependentName("selected");
			selectRowData(s[i],false);
			//replace the style of the row odd or even it depends of the index
			 if ( s[i] %2 == 0) {
				 this.getRowData(s[i]).setStylePrimaryName("odd");
			 } else {
				 this.getRowData(s[i]).setStylePrimaryName("even");
			 }
		}
		this.selection.clear();
		this.rowSelected = -1;
	}

	/**
	 * Returns the TR HTML element of a cell value, cell means an element in a TD or the TD itself
	 * @param cell an <code>Element</code> included in a TD or the TD itself
	 * @return the TR parent
	 */
	private Element getRow(Element cell) {

		if ( DOM.getChildIndex(tbody,cell) != -1) {
			return  cell;
		} else {
			return getRow(DOM.getParent(cell));
		}
	}

	/**
	 * Returns the TD HTML element of a cell value, cell means an element in a TD or the TD itself
	 * @param root the TR element which is parent of the TD to return
	 * @param cell the cell element
	 * @return a TD HTML element child of the root element
	 */
	private Element getCell(Element root,Element cell) {
		if ( DOM.getChildIndex(root,cell) != -1) {
			return  cell;
		} else {
			return getCell(root,DOM.getParent(cell));
		}
	}

	/**
	 * Adds a <code>SortListener</code> to the headers of this  <code>Table</code>
	 * @param listener the listener to add
	 */
	public void addSortListener(SortListener listener) {
		if ( this.sortListeners == null) {
			sortListeners = new SortListenerCollection();
		}
		sortListeners.add(listener);

	}

	/**
	 * Removes a <code>SortListener</code> from the headers of this  <code>Table</code>
	 * @param listener the listener to remove
	 */
	public void removeSortListener(SortListener listener) {
		if ( this.sortListeners != null) {
			sortListeners.remove(listener);
		}
	}

	/**
	 * Sorts localy an header of this <code>Table</code>
	 * @param asc sort mode ascendant if <code>true</code> or descendant mode
	 * @param columnIndex  the index of the header to sort
	 */
	public void sortLocaly(boolean asc, int columnIndex) {
		List sortedRow = new ArrayList();
		for (int i=0; i< model.getRowCount();i++) {
    		RowSorter row = new RowSorter(columnIndex);
    		row.setRowData(getRowData(i));
    		sortedRow.add(row);
    	}
       	Collections.sort(sortedRow);
       	if (!asc) {
       		Collections.reverse(sortedRow);
       	}
		rowData.clear();
		clearTbody();
		Iterator ite = sortedRow.iterator();
		int i =0;

		if ( getSelectionMode() != NO_SELECTION) {
		   this.selection.clear();
		    this.rowSelected = -1;
		}
		while (ite.hasNext()) {
			RowSorter rs = (RowSorter)ite.next();
			RowData data = rs.getRowData();
			rowData.add(data);
			DOM.appendChild(tbody,data.getElement());

			if( i%2 == 0 ) {
				 data.setStylePrimaryName("odd");
			  } else {
				data.setStylePrimaryName("even");
			  }
			if ( data.isSelected()) {
			   selection.add(new Integer(i));
			   rowSelected = i;
			}
			i = i+1;
		}
  	}


	/**
	 * Returns the <code>TableModel</code> of this <code>Table</code>
	 * @return the <code>TableModel</code> of this <code>Table</code>
	 */
	public TableModel getModel() {
		return this.model;
	}

	/**
	 * Returns the number of rows in the table
	 * @return the number of rows in the table
	 */
	public int getRowCount() {
		return this.model.getRowCount();
	}
	/**
	 * Returns the number of columns in the table
	 * @return the number of columns in the table
	 */
	public int getColumnCount() {
		return this.model.getColumnCount();
	}
	/**
	 * Sets the model to apply in this <code>Table</code>
	 * @param model the model to use should not be <code>null</code>
	 */
	public void setModel(TableModel model) {
		clearSelection();
		this.model = model;
        //setRendererMap();
		this.setHeader();
		this.rowData.clear();
		this.setTbody();
	}

	/** Sets the header of the table */
	private void setHeader() {
		clearTHead();
		Element tr = DOM.createTR();
		DOM.appendChild(tHead, tr);

			columns = new TableColumn[model.getColumnCount()];
			for ( int i=0; i< model.getColumnCount(); i++) {
				String name = model.getColumnName(i);
				columns[i] = new TableColumn();
				columns[i].setHTML(name);
				columns[i].setModelIndex(i);
				columns[i].setCellRenderer(getCellRenderer(model.getColumnClass(i)));
				DOM.appendChild(tr,columns[i].getElement() );
			}

	}




	/**
	 * Sets a <code>TableCellRenderer</code> for a column
	 * @param className
	 * @param renderer
	 */
	public void setCellRenderer(Class className, TableCellRenderer renderer) {
		rendererMap.put(className, renderer);
		boolean stop = false;
		for (int i=0; i < columns.length && !stop; i++) {
			Class c = model.getColumnClass(i);
			if ( c.equals(className)) {
				stop = true;
				columns[i].setCellRenderer(renderer);
			}
		}
		this.updateUI();

	}


	private void updateUI() {
	   for ( int i=0; i < model.getRowCount(); i++) {
		    updateRowData((RowData)this.rowData.get(i),i);
       }
	}


	/**
	 * Returns the <code>TableCellRenderer</code> associated to a class
	 * @param name the name of the class
	 * @return a <code>TableCellRenderer</code> or null if no <code>TableCellRenderer</code>
	 *         was found for the given name
	 */
	public TableCellRenderer getCellRenderer(Class name) {
		return (TableCellRenderer)rendererMap.get(name);
	}

	/**
	 * Checks if a row is selected
	 * @param row the index of the row to check
	 * @return  <code>true</code> if it is, <code>false</code> otherwise
	 */
	private boolean isSelected(int row) {
		Integer s = new Integer(row);
		boolean res = false;
		if ( selection != null) {
			res = selection.contains(s);
		}
		return res;
	}

	/**
	 * Returns the value at specified position in the table
	 * @param row the row index
	 * @param col the column index
	 * @return the value at specified position in the table
	 */
	public Object getValueAt(int row, int col) {
		RowData data = (RowData)rowData.get(row);
				return model.getValueAt(data.getModelIndex(), col);
	}

	/**
	 * Sets the value at the specified position in the table
	 * @param row the row index
	 * @param col the column index
	 * @param value the new value
	 */
	public void setValueAt(final int row, final int col,final Object value) {
		final RowData data = (RowData)this.rowData.get(row);
		this.model.setValueAt(data.getModelIndex(), col, value);


		Timer timer = new Timer() {
			public void run() {
				updateRowData(data, row);
			}
		};
		timer.schedule(10);

	}



    /**
     * Sets the body of the table
     *
     */
	private void setTbody() {
		clearTbody();
		DOM.appendChild(getElement(), tbody);
		for ( int i=0; i < model.getRowCount(); i++) {
			if ( i < rowData.size()) {
				RowData data =(RowData)rowData.get(i);
				this.updateRowData(data,i);
			} else {
				this.createRowData(i);
			}

		}
	}


	private void updateRowData(RowData row,int indexRow) {
		 for ( int i=0; i < model.getColumnCount(); i++) {
			 Object value = getValueAt(indexRow,i);
			 TableCellRenderer renderer = columns[i].getCellRenderer();
			 Widget w = null;
			 if ( renderer != null) {
			  //w = renderer.getTableCellRendererWidget(this, value,isSelected(indexRow),indexRow, i);
			  w = renderer.getTableCellRendererWidget(this, value,row.isSelected(),indexRow, i);
			 }
			  row.setValueAt(i, value, w);
		 }
		 if ( indexRow % 2 ==0 ) {
			 row.setStylePrimaryName("odd");
		 } else {
			 row.setStylePrimaryName("even");
		 }
	}

	/**
	 * Removes the tbody element and creates a new one
	 *
	 */
	private void clearTbody() {
		DOM.removeChild(getElement(),tbody);
		tbody = DOM.createTBody();
		DOM.appendChild(getElement(), tbody);

	}


	/**
	 * Removes the thead element and creates a new one
	 *
	 */
	private void clearTHead() {
		DOM.removeChild(getElement(),tHead);
		tHead = DOM.createTHead();
		DOM.appendChild(getElement(), tHead);
	}
	/**
	 * Returns a <code>RowData</code> of this <code>Table</code>
	 * @param row the index of the row to return
	 * @return the row data
	 * @throws OutOfIndexBoundException if the index is lesser than 0 or greater than the
	 *         number of rows
	 */
	public RowData getRowData(int row) {
		return  (RowData)this.rowData.get(row);
	}


	/**
	 * Creates a <code>RowData</code> for this <code>Table</code>
	 * @param indexRow
	 */
	protected void createRowData(int indexRow) {
		 RowData row = new RowData();
		 row.setModelIndex(indexRow);
		 for ( int i=0; i < model.getColumnCount(); i++) {
			 Object value = model.getValueAt(indexRow,i);
			 TableCellRenderer renderer = columns[i].getCellRenderer();
			 Widget w  = null;
			 if( renderer != null) {
			    w = renderer.getTableCellRendererWidget(this, value,isSelected(indexRow),indexRow, i);
			 }
			 row.addValue(value,w);


		 }
		 if ( indexRow % 2 ==0 ) {
			 row.setStylePrimaryName("odd");
		 } else {
			 row.setStylePrimaryName("even");
		 }
		 this.rowData.add(row);
		 DOM.appendChild(tbody, row.getElement());

	}






	/**
	 * Returns the selected row in this <code>Table</code>
	 * @return the selected row, or -1 if no row is selected
	 */
	public int getRowSelected() {
		return this.rowSelected;
	}

	/**
	 *
	 */
	public void addTableListener(TableListener listener) {
		if (this.tableListenerCollection == null) {
			this.tableListenerCollection = new TableListenerCollection();
		}
		this.tableListenerCollection.add(listener);
	}


	/**
	 *
	 */
	public void removeTableListener(TableListener listener) {
		if (this.tableListenerCollection != null) {
			this.tableListenerCollection.remove(listener);
		}

	}




   private class RowSorter implements Comparable {


	    	public int compareTo(Object o) {
			  int result = 0;
			  Object toCompare = null;
			  if ( o instanceof RowSorter ) {
	    		  toCompare = ((RowSorter)o).getIdentifier();
	    	  }

			  if ( toCompare instanceof Comparable) {
				  result = ((Comparable)toCompare).compareTo(getIdentifier());
			  } else if ( toCompare != null){
				  result = (toCompare.toString()).compareTo(getIdentifier().toString());

			  }
			  return result;
			}


			private int columnIndex;
	    	private RowData rows;

	    	public RowSorter(int columnIndex) {
	    		this.columnIndex = columnIndex;

	    	}

	        public void setRowData(RowData data) {
	        	this.rows = data;
	        }

	       public RowData getRowData() {
	    	   return rows;
	       }


	        public Object getValueAt(int index) {
	    	   return rows.getValueAt(index);
	       }

	        public Object getIdentifier() {
	        	return getValueAt(columnIndex);
	        }


	    }




}//end of class
