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
 * Authors:  Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s):
 */

package com.objetdirect.tatami.client.grid;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.objetdirect.tatami.client.AbstractDojo;
import com.objetdirect.tatami.client.DojoController;
import com.objetdirect.tatami.client.JSHelper;
import com.objetdirect.tatami.client.data.AbstractDataStore;
import com.objetdirect.tatami.client.data.DataStore;
import com.objetdirect.tatami.client.data.DataStorePaginator;
import com.objetdirect.tatami.client.data.DatumChangeListener;
import com.objetdirect.tatami.client.data.FetchEventSource;
import com.objetdirect.tatami.client.data.FetchListener;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.data.Request;
import com.objetdirect.tatami.client.grid.editor.GridEditor;
import com.objetdirect.tatami.client.grid.formatters.Formatter;



/**
 * This class is a simple wrapper around Dojo Grid.
 * It relies on an underlying GridDataStore, and uses a GridLayout to define its
 * appearance 
 * 
 * @author rdunklau
 *
 */
public class Grid extends AbstractDojo implements FetchListener , DatumChangeListener{


 
	/**
	* The underlying dataStore
	*/
	protected AbstractDataStore store;
	
	
	protected String rowSelector;
	
	public String getRowSelector() {
		return rowSelector;
	}

	public void setRowSelector(String rowSelector) {
		this.rowSelector = rowSelector;
	}

	/**
	 * The layout , used to define how the grid should be presented
	 */
	protected GridLayout layout;
	
	/**
	 * List containing the grid listeners 
	 * @see : {@link GridListener}
	 */
	protected List<GridListener> gridListeners = new ArrayList<GridListener>();
	
	/**
	 *  List containing currently selected indexes
	 */
	protected List<Integer> selectedIndexes = new ArrayList<Integer>();
	
	/**
	 * Number of displayed rows
	 */
	private int count;
	
	/**
	 * Should the grid autoAdapt it's height ?
	 */
	protected boolean autoHeight = false;
	
	
	/**
	 * Should the grid autoAdapt it's width ?
	 */
	protected boolean autoWidth = false;
	
	/**
	 * Indicates wether a user click on a cell header sorts the grid
	 * according to this cell field.
	 * 
	 */
	protected boolean userSortable = true;
	
	/**
	 * Indicates which view should be elastic
	 * -1 indicates no one. 
	 */
	private int elasticView = -1;
	
	/**
	 * Indicates wether the sort is ascending or descending
	 */
	private boolean isSortAsc = true;
	
	/**
	 * Indicates which column serves to sort the grid
	 */
	private int sortIndex = -1;
	
	/**
	 * Indicates the maximum number of item that is return from a fetch operation
	 * (Used by onScroll paging)
	 */
	private int maximumFetchCountAtAtime = 25;
	
	private DataStorePaginator paginator;
	
	private boolean renderGridOnLoad = true;
	
	private RowStyler styler;
	

	/**
	 * Default constructor
	 * It creates a new empty grid
	 */
	public Grid(){
		this( new GridDataStore() , new GridView());
		this.setWidth("200px");
		this.setHeight("200px");
	}
	
	/**
	 * Constructs a grid with the specified store and 
	 * a layout containing only one view
	 * 
	 * @param store : store used to create the grid
	 * @param view : the only view in the layout
	 */
	public Grid(AbstractDataStore store , GridView view){
		this(store , new GridLayout());
		layout.addView(view);
	}
	
	/**
	 * Constructs a grid with the specified store and 
	 * layout
	 * 
	 * @param store
	 * @param layout
	 */
	public Grid(AbstractDataStore store , GridLayout layout){
		super();
		this.styler = new RowStyler() {
		
			public String getRowCSSStyles(int rowIndex, boolean selected,
					boolean mouseover, boolean odd) {
				return null;
			}
		
			public String getRowCSSClasses(int rowIndex, boolean selected,
					boolean mouseover, boolean odd) {
				return null;
			}
		};
		this.paginator = new DataStorePaginator(store);
		setStore(store);
		this.layout = layout;
	}
	
	/**
	 * Removes all items from the grid and from the underlying data store
	 */
	public void clearGrid(){
		store.clearDataStore();
		if(dojoWidget != null){
			dojoClearGrid(dojoWidget);
			updateGrid();
		}
	}
	
	private native void dojoClearGrid(JavaScriptObject dojoWidget)/*-{
		dojoWidget._clearData();
	}-*/;
	
	/**
	 * Defines wether the grid should display a handle on the left 
	 * to select rows without selecting any cell in particular
	 * 
	 * @param shouldItExist
	 */
	public void setRowBar(boolean shouldItExist){
		layout.setRowBar(shouldItExist);
	}
	
	/**
	 * @return true if the grid displays a rowbar, false otherwise
	 */
	public boolean hasRowBar(){
		return layout.hasRowBar();
	}

	/**
	 * Adds a column with the specified name
	 * @param name : name of the column
	 */
	public Cell addColumn(String name){
		return addColumn(name ,  String.valueOf(layout.getNbCells()) , null , null , null);
	}
	
	
	
	/**
	 * @param name : name of the column
	 * @param fieldName : the fieldname used to get data to fill the grid
	 */
	public Cell addColumn(String name , String fieldName){
		return addColumn(name , fieldName , null , null , null);
	}
	
	/**
	 * @param name : name of the column
	 * @param fieldName : the fieldname used to get data to fill the grid
	 * @param widthInPx : the width of the column , in pixels.
	 */
	public Cell addColumn(String name , String fieldName , int widthInPx){
		return addColumn(name , fieldName , widthInPx+"px" , null , null);
	}
	
	/**
	 * @param name : name of the column
	 * @param fieldName : the fieldname used to get data to fill the grid
	 * @param editor :: the cell editor
	 */
	public Cell addColumn(String name , String fieldName , GridEditor editor){
		return addColumn(name ,fieldName  , null ,editor, null );
	}
	
	
	/**
	 * @param name : name of the column
	 * @param fieldName : the fieldname used to get data to fill the grid
	 * @param cellFormatter : the cell formatter
	 */
	public Cell addColumn(String name , String fieldName , Formatter cellFormatter){
		return addColumn(name , fieldName  , null ,null, cellFormatter );
	}
	
	/**
	 * @param name : name of the column
	 * @param fieldName : the fieldname used to get data to fill the grid
	 * @param editor :: the cell editor
	 * @param cellFormatter : the cell formatter
	 */
	public Cell addColumn(String name , String fieldName , GridEditor editor , Formatter cellFormatter){
		return addColumn(name , fieldName , null , editor , cellFormatter);
	}
	
	/**
	 * @param name : name of the column
	 * @param editor : the cell editor
	 * @param cellFormatter : the cell formatter
	 * @param width : column's width in standard css unit
	 */
	public Cell addColumn(String name ,GridEditor editor , Formatter cellFormatter , String width){
		return addColumn(name , null , width , editor , cellFormatter);
	}
	

	/**
	 * @param name : name of the column
	 * @param fieldName : the fieldname used to get data to fill the grid
	 * @param width : column's width in standard css unit
	 * @param editor : the cell editor
	 * @param cellFormatter : the cell formatter
	 *
	 */
	 
	public Cell addColumn(String name, String fieldName , String width , GridEditor editor , Formatter cellFormatter){
		Cell cellToAdd = new Cell( fieldName , name);
		if(width != null){
			cellToAdd.setWidth(width);
		}
		if(cellFormatter != null){
			cellToAdd.setFormatter(cellFormatter);
		}
		if(editor != null){
			cellToAdd.setEditor(editor);
		}
		addCell(cellToAdd);
		return cellToAdd;
	}
	
	
	/**
	 * @param cell : the cell to be added to the underlying layout
	 */
	public void addCell(Cell cell){
		layout.addCellToLastRowFromLastView(cell);
	}
	
	/**
	 * @param name : name of the column
	 * @param index : index where it should be inserted
	 */
	public Cell insertColumn(String name , int index){
		return insertColumn(name ,  String.valueOf(index) , null , null , null , index);
	}
	
	
	/**
	 * @param name : name of the column
	 * @param fieldName : the fieldname used to get data to fill the grid
	 * @param index : index where it should be inserted
	 */
	public Cell insertColumn(String name , String fieldName , int index){
		return insertColumn(name , fieldName , null , null , null , index);
	}
	
	/**
	 * @param name : name of the column
	 * @param fieldName : the fieldname used to get data to fill the grid
	 * @param editor :: it's editor
	 * @param index : index where it should be inserted
	 */
	public Cell insertColumn(String name , String fieldName , GridEditor editor , int index){
		return insertColumn(name , fieldName  , null ,editor, null , index);
	}
	
	
	/**
	 * @param name : name of the column
	 * @param fieldName : the fieldname used to get data to fill the grid
	 * @param cellFormatter : the cell formatter
	 * @param index : index where it should be inserted
	 */
	public Cell insertColumn(String name , String fieldName , Formatter cellFormatter , int index){
		return insertColumn(name , fieldName  , null ,null, cellFormatter , index);
	}
	
	
	/**
	 * @param name : name of the column
	 * @param fieldName : the fieldname used to get data to fill the grid
	 * @param editor :: it's editor
	 * @param cellFormatter : the cell formatter
	 * @param index : index where it should be inserted
	 */
	public Cell insertColumn(String name , String fieldName , GridEditor editor , Formatter cellFormatter , int index){
		return insertColumn(name , fieldName , null , editor , cellFormatter , index);
	}

	
	/**
	 * @param name : name of the column
	 * @param fieldName : the fieldname used to get data to fill the grid
	 * @param width : column's width , in standard css unit
	 * @param editor :: it's editor
	 * @param cellFormatter : the cell formatter
	 * @param index : index where it should be inserted
	 */
	public Cell insertColumn(String name, String fieldName , String width , GridEditor editor , Formatter cellFormatter , int index){
		Cell cellToAdd = new Cell( fieldName , name);
		if(width != null){
			cellToAdd.setWidth(width);
		}
		if(cellFormatter != null){
			cellToAdd.setFormatter(cellFormatter);
		}
		if(editor != null){
			cellToAdd.setEditor(editor);
		}
		insertCell(cellToAdd , index);
		return cellToAdd;
	}
	
	
	/**
	 * @param cell : the cell to insert
	 * @param index : the index where it should be inserted
	 */
	public void insertCell(Cell cell , int index){
		layout.addCellToLastRowFromLastView(cell, index);
	}
	
	/**
	 * @param row : an array of Object containing the various columns content (in order)
	 * A mapping is automatically performed between the Object array and the fieldnames used by the colums
	 * An id is generated if the id is not one of the shown columns.
	 * Then , it inserts the constructed item in the underlying data store
	 * 
	 * @param index : the index where the row should be inserted
	 * 
	 */
	public void addRow(Object[] row , int index){
		Item item = new Item();
		for (int i = 0; i < row.length; i++) {
			String field = layout.getCellAmongAllViews(i).getField();
			item.setValue(field , row[i]);
		}
		if(store.getIdentity(item) == null){
			item.setId(new Integer(index) + row[0].toString());
		}
		addRow(item , index );
	}
	
	/**
	 * @param row : an array of Object containing the various columns content (in order)
	 * A mapping is automatically performed between the Object array and the fieldnames used by the colums
	 * An id is generated if the id is not one of the shown columns.
	 * Then , it inserts the constructed item in the underlying data store
	 */
	public void addRow(Object[] row){
		addRow(row , this.count);
	}
	
	/**
	 * @param item : the item to insert in the grid
	 * @param index : the row index where it should be inserted
	 */
	public void addRow(Item item , int index) {
		//If the dojo grid widget is built, it is responsible for creating a row, adding the item 
		//to the datastore.
		//Else, the item is directly added to the datastore, and dojo's grid will 
		//ask it for items when it loads.
		store.add(item , makeParentInfo(index));
	}
	
	private native JavaScriptObject makeParentInfo(int index)/*-{
		var toReturn = {index: index}; 
		if(index < 0){
			return null;
		}
		return toReturn;
	}-*/;
	
	/**
	 * @param item : the item to insert in the underlying datastore. 
	 * It creates a new row after the existing ones.
	 */
	public void addRow(Item item) {
		addRow(item , this.count);
	}
	
	/**
	 * Removes the row at given index
	 * @param index
	 */
	public void removeRow(int index){
		Item toRemove = getItemFromRow(index);
		removeRow(toRemove);
	}
	
	
	/**
	 * Removes the column at given index
	 * @param index
	 */
	public void removeColumn(int index){
		removeColumn(layout.getCellAmongAllViews(index));
	}
	
	/**
	 * Removes the given Cell 
	 * @param cell
	 */
	public void removeColumn(Cell cell){
		layout.removeCellAmongAllViews(cell);
	}
	
	/**
	 * Removes the item from the datastore, removing the grid row
	 * by the same time
	 * @param item
	 */
	public void removeRow(Item item){
		store.remove(item);
	}
	
	/**
	 * Internal method used to force dojo's grid widget
	 * to add the given row at the given index
	 * 
	 * @param item
	 * @param index
	 */
	private native void dojoAddRow(Item item , int index)/*-{
		var grid = this.@com.objetdirect.tatami.client.AbstractDojo::dojoWidget;
		grid.addRow(item , index);
	}-*/;
	
	
	
	
	/**
	 * Returns the item corresponding to the given row index
	 * 
	 * @param rowNumber
	 * @return
	 */
	public Item getItemFromRow(int rowNumber){
		if(dojoWidget != null){
			return dojoGetItemFromRow(rowNumber);
		}else{
			return null;
		}
	}
	
	/**
	 * Internal method used to retrieve the item from it's rowindex
	 * @param rowNumber
	 * @return
	 */
	private native Item dojoGetItemFromRow(int rowNumber)/*-{
		return this.@com.objetdirect.tatami.client.AbstractDojo::dojoWidget.getItem(rowNumber);
	}-*/;
	
	/**
	 * @param item : item from which we want to know the corresponding row
	 * @return item's row index
	 */
	public int getRowFromItem(Item item){
		return dojoGetRowFromItem(item);
	}
	
	/**
	 * Internal method used to retrieve an item's row index
	 * 
	 * @param item 
	 * @return
	 */
	private native int dojoGetRowFromItem(Item item)/*-{
		return this.@com.objetdirect.tatami.client.AbstractDojo::dojoWidget.getItemIndex(item);
	}-*/;
	
	
	
	
	
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#createDojoWidget()
	 */
	public void createDojoWidget() throws Exception {
		JavaScriptObject jsstore = store.getDojoWidget();
		JavaScriptObject jslayout = this.layout.toJSObject();
		if(this.rowSelector == null && layout.hasRowBar()){
			this.rowSelector = "20px";
		}
		this.dojoWidget = createDojoGrid(jsstore , jslayout , autoHeight , 
				autoWidth , userSortable ,  elasticView , sortIndex , isSortAsc , maximumFetchCountAtAtime , renderGridOnLoad , rowSelector , JSHelper.convertObjectToJSObject(store.getLastRequest().getQuery()));
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#getDojoName()
	 */
	public String getDojoName() {
		return "dojox.grid.DataGrid";
	}
	
	/**
	 * @param jsstore : the dojo store used to construct the grid
	 * @param layout : the jsobject representing the layout
	 * @param autoHeight : true if the grid should autoAdapt its height, false otherwise
	 * @param autoWidth : true if the grid should autoAdapt its width, false otherwise
	 * @param canSort : wether the user can sort the grid by clicking the cell header
	 * @param elasticView : "elastic view"'s indexes 
	 * @param sortCol : if != -1 , sorts the grid according to the column whose index is given
	 * @param ascending : if sortCol != -1 , ascending indicates wether the sort should be in ascending or
	 * descending order
	 * @param maximumFetchCountAtAtime : maximum number of items fetched at a time. Used by dojo
	 * when it does his "scroll paging"
	 * @return a javascript object representing the dojo grid object.
	 */
	private native JavaScriptObject createDojoGrid(JavaScriptObject jsstore , JavaScriptObject layout , 
			boolean autoHeight , boolean autoWidth , boolean canSort 
			 , int elasticView , int sortCol , boolean ascending , int maximumFetchCountAtAtime , boolean autoRender ,String rowSelector , JavaScriptObject query )/*-{
		
		//FIXME: When the sort : {sortCol : index, isAsc : boolean} constructor
		//parameter comes back in DataGrid, remove this silly sortinfo calculation. 
		var si = sortCol +1;
		si *= (ascending == true ? 1 : -1);
		var gridOptions = {
			store : jsstore , 
			sortInfo: si , 
			structure : layout , 
//			width: "100%" ,
//			height: "100%" ,
			gwtWidget : this ,
			query : query
		}; 
		if(rowSelector){
			gridOptions.rowSelector = rowSelector;
		}
		var grid = new $wnd.dojox.grid.TatamiGrid(gridOptions);
  		
  		return grid;
	}-*/;

	
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.AbstractDojo#onDojoLoad()
	 */
	@Override
	public void onDojoLoad() {
		defineTatamiGrid();
	}
	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#setHeight(java.lang.String)
	 */
	@Override
	public void setHeight(String height) {
		super.setHeight(height);
		if(dojoWidget != null){
			resizeGrid(dojoWidget);
		}
	}


	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#setSize(java.lang.String, java.lang.String)
	 */
	@Override
	public void setSize(String width, String height) {
		super.setSize(width, height);
		if(dojoWidget != null){
			resizeGrid(dojoWidget);
		}
	}


	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#setWidth(java.lang.String)
	 */
	@Override
	public void setWidth(String width) {
		super.setWidth(width);
		if(dojoWidget != null){
			resizeGrid(dojoWidget);
		}
	}

	/**
	 * Calls the dojo "resize" method on the grid. It is needed to apply 
	 * selected size changes.
	 * @param widget : the dojo widget
	 */
	private native void resizeGrid(JavaScriptObject widget)/*-{
		widget.resize();
	}-*/;
	
	/**
	 * Declare the TatamiGrid Class in Dojo,
	 * overriding the default onSelectionChanged and onCellClick functions
	 */
	private native void defineTatamiGrid()/*-{
		$wnd.dojo.declare("dojox.grid.TatamiGrid" , $wnd.dojox.grid.DataGrid , {
			onSelectionChanged : function(){
				this.gwtWidget.@com.objetdirect.tatami.client.grid.Grid::onSelectionChange(Lcom/google/gwt/core/client/JavaScriptObject;)(this.selection.selected);
			},
			onCellClick : function(e){
				this._click[0] = this._click[1];
				this._click[1] = e;
				if(!this.edit.isEditCell(e.rowIndex, e.cellIndex)){
					this.focus.setFocusCell(e.cell, e.rowIndex);
				}
				this.onRowClick(e);
				var cell = e.cell;
				this.gwtWidget.@com.objetdirect.tatami.client.grid.Grid::onCellClick(ILjava/lang/String;I)(e.rowIndex, (e.cell.field == undefined ? null : e.cell.field) , e.cellIndex);
			},
			onCellDblClick: function(e){
				if($wnd.dojo.isIE){
					this.edit.setEditCell(this._click[1].cell, this._click[1].rowIndex);
				}else if(this._click[0].rowIndex != this._click[1].rowIndex){
					this.edit.setEditCell(this._click[0].cell, this._click[0].rowIndex);
				}else{
					this.edit.setEditCell(e.cell, e.rowIndex);
				}
				this.gwtWidget.@com.objetdirect.tatami.client.grid.Grid::onCellDblClick(ILjava/lang/String;I)(e.rowIndex, (e.cell.field == undefined ? null : e.cell.field) , e.cellIndex);
			},
			onStyleRow: function(inRow){
				var i = inRow;
				i.selected = i.selected ? i.selected : false;
				i.customClasses += (i.odd?" dojoxGridRowOdd":"") + (i.selected?" dojoxGridRowSelected":"") + (i.over?" dojoxGridRowOver":"");
				i.customClasses += " " +this.gwtWidget.@com.objetdirect.tatami.client.grid.Grid::getRowCSSClasses(IZZZ)(i.index , i.selected  , i.over, i.odd);
				i.customStyles += " "+ this.gwtWidget.@com.objetdirect.tatami.client.grid.Grid::getRowCSSStyles(IZZZ)(i.index , i.selected , i.over, i.odd);
				this.focus.styleRow(inRow);
				this.edit.styleRow(inRow);
			},
			_onNew: function(item, parentInfo){
				var indexToPut = this.rowCount;
				if(parentInfo != undefined && parentInfo.index!= undefined){
					indexToPut = parentInfo.index;
				}
				this.updateRowCount(this.rowCount+1);
				this._addItem(item,indexToPut);
				this.updateRows(indexToPut +1, this.rowCount - indexToPut);
				this.showMessage();
			},
 			_addItem: function(item, index, noUpdate){
				var idty = this._hasIdentity ? this.store.getIdentity(item) : dojo.toJson(this.query) + ":idx:" + index + ":sort:" + dojo.toJson(this.getSortProps());
				var o = { idty: idty, item: item };
				$wnd.dojox.grid.util.arrayInsert(this._by_idx,index,o);
				this._by_idty[idty] = o;
				if(!noUpdate){
					this.updateRow(index);
				}
			}
		});
	}-*/;
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.AbstractDojo#doBeforeDestruction()
	 */
	@Override
	public void doBeforeDestruction() {
		this.store.doBeforeDestruction();
	}
	

	

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.AbstractDojo#doAfterCreation()
	 */
	@Override
	public void doAfterCreation() {
		DOM.sinkEvents(getElement(),Event.MOUSEEVENTS);
		DOM.sinkEvents(getElement(),Event.FOCUSEVENTS);
		DojoController.startup(this);
	}

	/**
	 * Internal method called whenever a cell click is performed
	 * 
	 * @param rowIndex
	 * @param colField
	 * @param colIndex
	 */
	private void onCellClick(int rowIndex , String colField , int colIndex){
		notifyGridListenersOnCellClick(rowIndex, colField , colIndex);
	}
	
	
	/**
	 * Internal method called whenever a cell dblclick is performed
	 * 
	 * @param rowIndex
	 * @param colField
	 * @param colIndex
	 */
	private void onCellDblClick(int rowIndex , String colField , int colIndex){
		notifyGridListenersOnCellDblClick(rowIndex, colField , colIndex);
	}
	
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.FetchListener#onBegin(int)
	 * 
	 * Called by the store when the item fetch begin.
	 */
	public void onBegin(FetchEventSource source ,int size , Request request) {
		delegateBeginning(size, request.toJSObject());
	}
	

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.FetchListener#onComplete(com.objetdirect.tatami.client.data.Item[])
	 * 
	 * Called by the store when the item fetch ends
	 */
	public void onComplete(FetchEventSource source ,List<?> items , Request request) {
		JavaScriptObject itemArray = JavaScriptObject.createArray();
		for (Iterator<?> iterator = items.iterator(); iterator.hasNext();) {
			Item item = (Item) iterator.next();
			itemArray = addItemToJSArray(itemArray, item);
		}
		delegateProcessRows(itemArray , request.toJSObject());
	}
	
	/**
	 * Internal helper method which adds an item to a javascript array 
	 * 
	 * @param array
	 * @param item
	 * @return
	 */
	private native JavaScriptObject addItemToJSArray(JavaScriptObject array , Item item)/*-{
		array.push(item);
		return array;
	}-*/;
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.LoadItemListener#onItem(com.objetdirect.tatami.client.data.Item)
	 * 
	 * Called by the store each time a new item has been fetched
	 */
	public void onItem(FetchEventSource source , Item item) {
	}
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.LoadItemListener#onError()
	 * 
	 * Called by the store if an error occured during the fetch.
	 */
	public void onError(FetchEventSource source) {
	}

	
	/**
	 * This method is used to callback the dojo model "beginReturn" method.
	 * @param size : the expected number of items which are fetched
	 * @param grid : the dojo grid object
	 */
	private native void delegateBeginning(int size , JavaScriptObject request)/*-{
		request.onBegin(size,request);
	}-*/;
	
	
	/**
	 * This method is used to callback the dojo model "processRows"
	 * @param items : a javascript object representing an array of items fetched
	 * @param request : the dojo request object
	 */
	private native void delegateProcessRows(JavaScriptObject items , JavaScriptObject request)/*-{
		request.onComplete(items,request);
	}-*/;


	
	
	/**
	 * Asks the grid to refresh its data from the store.
	 */
	public void updateGrid(){
		if(dojoWidget != null){
			dojoUpdateGrid();
		}else{
			renderGridOnLoad = true;
		}
	}
	
	
	/**
	 * Internal method used to update the grid
	 */
	private native void dojoUpdateGrid()/*-{
		var grid = this.@com.objetdirect.tatami.client.AbstractDojo::dojoWidget;
		grid.render();
	}-*/;
	
	/**
	 * Asks the grid to rebuild itself from its layout
	 */
	public void updateView(){
		if(dojoWidget != null){
			dojoUpdateView(JSHelper.convertObjectToJSObject(layout));
		}
	}
	
	/**
	 * Internal method used to update grid layout
	 * @param layout
	 */
	private native void dojoUpdateView(JavaScriptObject layout)/*-{
		var grid = this.@com.objetdirect.tatami.client.AbstractDojo::dojoWidget;
		grid.setStructure(layout);
	}-*/;
	

	/**
	 * @return wether the grid should auto adapt its height
	 */
	public boolean isAutoHeight() {
		return autoHeight;
	}

	/**
	 * @param autoHeight : true indicates that the grid should auto adapt its height, 
	 * false that its height is fixes
	 */
	public void setAutoHeight(boolean autoHeight) {
		this.autoHeight = autoHeight;
		if(dojoWidget != null){
			dojoSetAutoHeight(autoHeight);
		}
	}

	/**
	 * Internal method used to set autoheight in dojo
	 * @param autoHeight
	 */
	private native void dojoSetAutoHeight(boolean autoHeight)/*-{
		var grid = this.@com.objetdirect.tatami.client.AbstractDojo::dojoWidget;
		grid.autoWidth = autoHeight;
	}-*/;
	
	/**
	 * @return wether the grid should auto adapt its width
	 */
	public boolean isAutoWidth() {
		return autoWidth;
	}

	
	/**
	 * @param autoWidth : true if the grid should auto adapt its width, false otherwise
	 */
	public void setAutoWidth(boolean autoWidth) {
		this.autoWidth = autoWidth;
		if(dojoWidget != null){
			dojoSetAutoWidth(autoWidth);
		}
	}

	/**
	 * Internal method used to set autoWidth in dojo
	 * @param autoWidth
	 */
	private native void dojoSetAutoWidth(boolean autoWidth)/*-{
		var grid = this.@com.objetdirect.tatami.client.AbstractDojo::dojoWidget;
		grid.autoWidth = autoWidth;
	}-*/;

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.DatumChangeListener#onDataChange(com.objetdirect.tatami.client.data.Item, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void onDataChange(Item item ,  String attributeName , Object oldValue , Object newValue) {
		notifyGridListenersOnDataChange(item ,  attributeName ,  oldValue ,  newValue );
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.DatumChangeListener#onDelete(com.objetdirect.tatami.client.data.Item)
	 */
	public void onDelete(Item item) {
		count--;
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.data.DatumChangeListener#onNew(com.objetdirect.tatami.client.data.Item)
	 */
	public void onNew(Item item) {
		count++;
	}

	/**
	 * 
	 * @return Current column count
	 */
	public int getColumnCount(){
		return layout.getNbCells();
	}
	
	
	/**
	 * 
	 * @return Current row count
	 */
	public int getRowCount() {
		return count;
	}


	/**
	 * @return wether the user can sort the grid by clicking on the cell headers
	 */
	public boolean isUserSortable() {
		return userSortable;
	}


	/**
	 * Sets wether the user can sort the grid by clicking on
	 * the column header.
	 * 
	 * Default : true.
	 * 
	 * @param userSortable
	 */
	public void setUserSortable(boolean userSortable) {
		this.userSortable = userSortable;
	}

	/**
	 * Sets the grid sorting. 
	 * 
	 * @param sortIndex : the column index by which the grid 
	 * should be sorted 
	 * @param ascending : indicates wether the sort should be 
	 * performed in ascending or descending order
	 */
	public void setSortIndex(int sortIndex , boolean ascending){
		this.sortIndex = sortIndex;
		this.isSortAsc = ascending;
		if(dojoWidget != null){
			dojoSetSortIndex(sortIndex, ascending);
		}
	}
	
	/**
	 * Internal method to set the sort index in dojo grid
	 * 
	 * @param sortIndex : the column index which should be used to sort
	 * @param ascending : wether the sort should be performed in ascending or descending order
	 */
	private native void dojoSetSortIndex(int sortIndex , boolean ascending)/*-{
		var grid = this.@com.objetdirect.tatami.client.AbstractDojo::dojoWidget;
		grid.setSortIndex(sortIndex , ascending);
	}-*/;
	
	/**
	 * Adds a listener for grid events
	 * 
	 * @param listener : a grid Listener
	 * @see {@link GridListener}
	 */
	public void addGridListener(GridListener listener){
		gridListeners.add(listener);
	}
	
	/**
	 * Removes a listener for grid events
	 * 
	 * @param listener : a grid Listener
	 * @see {@link GridListener}
	 */
	public void removeGridListener(GridListener listener){
		gridListeners.remove(listener);
	}
	
	/**
	 * Internal method used to transform dojo's onSelectionChange event into
	 * something more usable in GWT
	 * 
	 * @param selection
	 */
	private native void onSelectionChange(JavaScriptObject selection)/*-{
		var javaSelectionList = this.@com.objetdirect.tatami.client.grid.Grid::selectedIndexes;
		javaSelectionList.@java.util.List::clear()();
		if(selection != undefined){
			for(var i = 0 ; i < selection.length ; i++){
				if(selection[i]){
					this.@com.objetdirect.tatami.client.grid.Grid::addSelectedIndex(I)(i);
				}
			}
		}
		this.@com.objetdirect.tatami.client.grid.Grid::notifyGridListenersOnSelectionChange()();
	}-*/;
	
	/**
	 * Internal method used to add a selected index to the selectedIndexes list
	 * 
	 * @param i
	 */
	private void addSelectedIndex(int i){
		selectedIndexes.add(new Integer(i));
	}
	
	
	/**
	 * Notifies grid listeners that a cell click occured
	 * 
	 * @param rowIndex : the row index where the click happened
	 * @param colField : the attribute which is used by the clicked cell to get it's data 
	 * (null if the column is a constant one)
	 * @param colIndex : the column index where the click happened 
	 */
	private void notifyGridListenersOnCellClick(int rowIndex , String colField , int colIndex){
		for (Iterator<GridListener> iterator = gridListeners.iterator(); iterator.hasNext();) {
			GridListener gridListener = iterator.next();
			gridListener.onCellClick(this , rowIndex,  colIndex , colField);
		}
	}
	
	/**
	 * Notifies grid listeners that a cell DblClick occured
	 * 
	 * @param rowIndex : the row index where the click happened
	 * @param colField : the attribute which is used by the clicked cell to get it's data 
	 * (null if the column is a constant one)
	 * @param colIndex : the column index where the click happened 
	 */
	private void notifyGridListenersOnCellDblClick(int rowIndex , String colField , int colIndex){
		for (Iterator<GridListener> iterator = gridListeners.iterator(); iterator.hasNext();) {
			GridListener gridListener = iterator.next();
			gridListener.onCellDblClick(this , rowIndex,  colIndex , colField);
		}
	}
	
	/**
	 * Notifies grid listeners that a data change occured
	 * 
	 * @param item : the item which has changed
	 * @param attributeName : the attribute which value has changed
	 * @param oldValue : value before it changed
	 * @param newValue : new value
	 */
	private void notifyGridListenersOnDataChange(Item item, String attributeName, Object oldValue, Object newValue){
		for (Iterator<GridListener> iterator = gridListeners.iterator(); iterator.hasNext();) {
			GridListener gridListener = iterator.next();
			gridListener.onDataChange(this , item , attributeName , oldValue , newValue );
		}
	}
	
	/**
	 * Notifies grid listeners that the row selection has changed
	 * 
	 */
	private void notifyGridListenersOnSelectionChange(){
		for (Iterator<GridListener> iterator = gridListeners.iterator(); iterator.hasNext();) {
			GridListener gridListener = iterator.next();
			gridListener.onSelectionChanged(this);
		}
	}
	
	
	/**
	 * @return the underlying data store
	 */
	public DataStore getStore() {
		return store;
	}
	
	/**
	 * Sets the store that the grid should use
	 * 
	 * @param store : the store which should be use to aquire data
	 */
	public void setStore(AbstractDataStore store) {
		if(this.store != null){
			this.store.removeDatumChangeListener(this);
			this.paginator.removeFetchListener(this);
		}
		this.store = store;
		store.addDatumChangeListener(this);
		paginator.setStore(store);
		paginator.addFetchListener(this);
		if(dojoWidget != null){
			dojoSetStore(store.getDojoWidget());
		}
	}
	
	/**
	 * Internal method used to propagate the store change to dojo
	 * 
	 * @param store
	 */
	private native void dojoSetStore(JavaScriptObject store)/*-{
		this.@com.objetdirect.tatami.client.AbstractDojo::dojoWidget.model.setData(store);
	}-*/;
	
	
	
	/**
	 * Get the row indexes which are selected
	 * 
	 * @return : an array of int containing the selected indexes
	 */
	public int[] getSelectedIndexes() {
		int[] select = new int[selectedIndexes.size()];
		for (int i = 0;  i < selectedIndexes.size(); i++) {
			select[i] = ((Number)selectedIndexes.get(i)).intValue();
		}
		return select;
	}

	
	/**
	 * Get the items corresponding to the selected rows
	 * 
	 * @return : an array of selected Items 
	 */
	public Item[] getSelectedItems(){
		Item[] selectedItems = new Item[selectedIndexes.size()];
		for(int i = 0 ; i < selectedIndexes.size() ; i++) {
			Number selectedIndex = selectedIndexes.get(i);
			selectedItems[i] = getItemFromRow(selectedIndex.intValue());
		}
		return selectedItems;
	}

	/**
	 * @return the number of rows per page in Tatami's pagination system
	 */
	public int getRowsPerPage() {
		return paginator.getRowsPerPage();
	}

	/**
	* Sets the number of rows per page.
	*
	* @param rowsPerPage : the number of rows per page.  
	* A value of -1 indicates that all rows should fit on a single page. 
	* That does not mean they are all loaded at one time, since only the displayed rows are loaded, 
	* and updated when the user scrolls. 
	*
	* Default : -1
	*/
	public void setRowsPerPage(int rowsPerPage) {
		paginator.setRowsPerPage(rowsPerPage);
	}

	/**
	 * @return the view index corresponding to the "elastic" view. 
	 * The elastic view is the one which width is adapted to fit the remaining space
	 */
	public int whichElasticView() {
		return elasticView;
	}

	/**
	 * @param elasticViewIndex : the index of the view that should be elastic
	 * The elastic view is the one which width is adapted to fit the remaining space
	 */
	public void setElasticView(int elasticViewIndex) {
		this.elasticView = elasticViewIndex;
		if(dojoWidget != null){
			dojoSetElasticView(elasticView, dojoWidget);
		}
	}
	
	private native void dojoSetElasticView(int index , JavaScriptObject widget)/*-{
		widget.elasticView = index;
	}-*/;
	
	/**
	 * Goes to the nextPage
	 */
	public void nextPage(){
		paginator.nextPage();
	}
	
	
	/**
	 * Goes to the previous page
	 */
	public void previousPage(){
		//dojoPreparePageChange();
		paginator.previousPage();
	}
	
	/**
	 * @param index : the page index where to go
	 */
	public void goToPage(int index){
		dojoPreparePageChange();
		paginator.fetchPage(index);
	}

	private int getIndexToUpdate(){
		int rowsPerPage = paginator.getRowsPerPage();
		int index = rowsPerPage < 0 ? 0 : rowsPerPage * paginator.getCurrentPage();
		return  index;
	}
	
	/**
	 * @return : the maximum number of item that should be fetched at a time.
	 * This is used by dojo during its "sroll paging"
	 */
	public int getMaximumFetchCountAtAtime() {
		return maximumFetchCountAtAtime;
	}

	/**
	 * Sets the maximum number that are returned at a time. 
	 * It indicates how many items should be loaded. When the user scrolls, 
	 * items to be displayed are fetched by groups of maximumFetchCountAtAtime
	 * items.
	 * 
	 * A typical setting for this value is 1.5 the number of rows to be displayed at a time.
	 * (That is , the grid won't have to load items until the user scrolls)
	 * 
	 * @param maximumFetchCountAtAtime
	 */
	public void setMaximumFetchCountAtAtime(int maximumFetchCountAtAtime) {
		this.maximumFetchCountAtAtime = maximumFetchCountAtAtime;
		if(dojoWidget != null){
			setDojoMaximumFetchCountAtATime(maximumFetchCountAtAtime);
		}
	}
	
	/**
	 * Internal method used to change maximumFetchCountAtAtime in dojo's widget 
	 * 
	 * @param maximumFetchCountAtAtime
	 */
	private native void setDojoMaximumFetchCountAtATime(int maximumFetchCountAtAtime)/*-{
		this.@com.objetdirect.tatami.client.AbstractDojo::dojoWidget.model.rowsPerPage = maximumFetchCountAtAtime;
	}-*/;
	
	/**
	 * Internal method used to clear data kept in dojo widget before loading a new page
	 */
	private native void dojoPreparePageChange()/*-{
		this.@com.objetdirect.tatami.client.AbstractDojo::dojoWidget.model.clearData(true);
	}-*/;
	
	
	/**
	 * Remove currently selected rows
	 */
	public void removeSelectedRows(){
		dojoRemoveSelectedRows();
	}
	
	/**
	 * Internal method which removes selected rows in dojo.
	 */
	private native void dojoRemoveSelectedRows()/*-{
		this.@com.objetdirect.tatami.client.AbstractDojo::dojoWidget.removeSelectedRows();
	}-*/;

	/**
	 * @return the current GridLayout
	 */
	public GridLayout getLayout() {
		return layout;
	}

	/**
	 * Sets the layout used to display the grid
	 * 
	 * @param layout : a grid layout
	 */
	public void setLayout(GridLayout layout) {
		this.layout = layout;
	}
	
	/**
	 * Adds a filter to the next fetch.
	 * 
	 * @param fieldName : the field name on which we want to filter
	 * @param criterium : what must this filter be equal to
	 */
	public void addFilter(String fieldName , Object criterium){
		Request request = store.getLastRequest();
		request.getQuery().put(fieldName, criterium);
		if(dojoWidget != null){
			dojoSetQuery(request.toJSObject());
		}
	}
	
	
	/**
	 * Removes a filter for the next fetch
	 * 
	 * @param fieldName : the field on which we don't want to filter anymore
	 */
	public void removeFilter(String fieldName){
		Request request = store.getLastRequest();
		request.getQuery().remove(fieldName);
		if(dojoWidget != null){
			dojoSetQuery(request.toJSObject());
		}
	}
	
	/**
	 * Internal method used to set query in dojo's widget
	 * @param query
	 */
	private native void dojoSetQuery(JavaScriptObject query)/*-{
		this.@com.objetdirect.tatami.client.AbstractDojo::dojoWidget.setQuery(query.query);
	}-*/;
	
	
	/**
	 * Convenience method to get the content from a specific cell 
	 * 
	 * @param rowIndex 
	 * @param colIndex
	 * @return the content from the cell at row "rowindex" and at column "colindex"
	 * if this cell is filled from the datastore. Returns null otherwise
	 */
	public Object getDataAt(int rowIndex , int colIndex){
		return getItemFromRow(rowIndex).getValue(layout.getCellAmongAllViews(colIndex).getField(), null);
	}
	
	/**
	 * Forces the grid to reload data from the store for the given row
	 * 
	 * @param rowIndex
	 */
	public void updateRow(int rowIndex){
		if(dojoWidget != null){
			dojoUpdateRow(rowIndex);
		}
	}
	
	/**
	 * Internal method used to perform actual row update
	 * 
	 * @param rowIndex
	 */
	private native void dojoUpdateRow(int rowIndex)/*-{
		var grid = this.@com.objetdirect.tatami.client.AbstractDojo::dojoWidget;
		grid.updateRow(rowIndex);
	}-*/;

	
	
	/**
	 * @return true if the grid should be rendered as soon as it is attached, false otherwise
	 */
	public boolean isRenderGridOnLoad() {
		return renderGridOnLoad;
	}

	/**
	 * @param renderGridOnLoad : wether the grid should be rendered as soon as it is attached
	 */
	public void setRenderGridOnLoad(boolean renderGridOnLoad) {
		this.renderGridOnLoad = renderGridOnLoad;
	}
	
	/**
	 * Used to determine which css classes should be applied to the 
	 * given row in the given state
	 * 
	 * @param rowIndex : index of the row which we want to know th css classes
	 * @param selected : wheter this row is currently selected or not
	 * @param mouseover : wether the mouse is over the row
	 * @param odd : wether the row index is odd
	 * @return
	 */
	private String getRowCSSClasses(int rowIndex, boolean selected , boolean mouseover, boolean odd) {
		return styler.getRowCSSClasses(rowIndex, selected ,mouseover, odd);
	}

	
	/**
	 * Used to determine which css styles should be applied to the 
	 * given row in the given state
	 * 
	 * @param rowIndex : index of the row which we want to know th css classes
	 * @param selected : wheter this row is currently selected or not
	 * @param mouseover : wether the mouse is over the row
	 * @param odd : wether the row index is odd
	 * @return
	 */
	private String getRowCSSStyles(int rowIndex, boolean selected ,boolean mouseover, boolean odd) {
		return styler.getRowCSSStyles(rowIndex,selected , mouseover, odd);
	}

	
	/**
	 * @return the current row styler
	 */
	public RowStyler getStyler() {
		return styler;
	}

	/**
	 * @param styler sets the current row styler
	 */
	public void setStyler(RowStyler styler) {
		this.styler = styler;
	}

}
