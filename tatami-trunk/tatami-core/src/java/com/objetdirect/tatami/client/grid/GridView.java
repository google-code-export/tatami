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
import com.objetdirect.tatami.client.ConvertibleToJSObject;
import com.objetdirect.tatami.client.JSHelper;

/**
 * This class represents a GridView.
 * 
 * A GridView defines a set of cells , which are used 
 * to represent data.
 * 
 * A Grid View can be scrollable or not
 * 
 * A GridView is composed of one or more rows of cells,
 * which define data. 
 * 
 * When there is only one row, a cell correspond to a column definition.
 * When there are more rows, each GridView row correspond to a "sub row" in
 * the final grid rendering and to a row in the grid headers. 
 * 
 * Please read Tatami User's guide
 * for a graphical explanation.
 * 
 *
 * 
 * @author rdunklau
 */
public class GridView implements ConvertibleToJSObject {

	/**
	 * List of rows of cells
	 */
	private List rows = new ArrayList();
	
	/**
	 * View's width in standard css units  
	 */
	private String width;
	
	/**
	 * Wether the view should display scrollbars
	 */
	private boolean isScrollable = true;
	
	/**
	 * Adds a cell to this view's last row.
	 * 
	 * @param cell
	 */
	public void addCellToLastRow(Cell cell){
		if(rows.size() < 1){
			List row = new ArrayList();
			rows.add(row);
		}
		((List)rows.get(rows.size() - 1 )).add(cell);
	}
	
	/**
	 * Adds a cell at the given index in the given row 
	 * 
	 * @param cell
	 * @param row
	 * @param index
	 */
	public void addCellToRow(Cell cell , int row , int index){
		if(row > rows.size() - 1){
			List newRow = new ArrayList();
			newRow.add(cell);
			rows.add(newRow);
		}else{
			((List)rows.get(row)).add(index , cell);
		}
	}
	
	/**
	 * Adds a cell to the given row 
	 * 
	 * @param cell
	 * @param row
	 */
	public void addCellToRow(Cell cell , int row){
		if(row > rows.size() - 1){
			List newRow = new ArrayList();
			newRow.add(cell);
			rows.add(newRow);
		}else{
			((List)rows.get(row)).add(cell);
		}
	}
	
	/**
	 * Adds a cell at given index from the last row
	 * 
	 * @param cell
	 * @param index
	 */
	public void addCellToLastRow(Cell cell , int index){
		if(rows.size() < 1){
			List row = new ArrayList();
			rows.add(row);
		}
		((List)rows.get(rows.size() - 1 )).add(index , cell);
	}
	
	/**
	 * Removes a cell wherever it is in the view
	 * 
	 * @param cell
	 */
	public void removeCell(Cell cell){
		for (Iterator iterator = rows.iterator(); iterator.hasNext();) {
			List row = (List) iterator.next();
			row.remove(cell);
		}
	}
	
	/**
	 * Removes the given cell from the given row
	 * 
	 * @param cell
	 * @param row
	 */
	public void removeCell(Cell cell , int row){
		((List)rows.get(row)).remove(cell);
	}
	
	/**
	 * Removes the cell at given index in the given row
	 * 
	 * @param row
	 * @param index
	 */
	public void removeCell(int row , int index){
		((List)rows.get(row)).remove(index);
	}
	
	/**
	 * Removes a cell by its index, independtly from the row
	 * in which it is located
	 * @param index
	 */
	public void removeCell(int index){
		removeCell(getCell(index));
	}
	
	/**
	 * @return total number of cells among all rows
	 */
	public int getNbCells(){
		int size = 0;
		for (Iterator iterator = rows.iterator(); iterator.hasNext();) {
			List row = (List) iterator.next();
			size += row.size();
		}
		return size;
	}
	
	/**
	 * @param index
	 * @return the cell at given index. For example, if there are
	 * two rows of 3 cells each, getCell(3) will return the first cell from the second row
	 */
	public Cell getCell(int index){
		int previousSize = 0;
		for (Iterator iterator = rows.iterator(); iterator.hasNext();) {
			List row = (List) iterator.next();
			int size = row.size();
			if(size > index - previousSize ){
				return (Cell)row.get(index - previousSize ) ;
			}
			previousSize += size;
		}
		return null;
	}

	/**
	 * @param row
	 * @param index
	 * @return the cell at given index in the given row
	 */
	public Cell getCellFromRow(int row , int index){
		return (Cell) ((List)rows.get(row)).get(index);
	}
	
	/**
	 * @param index
	 * @return the row at given index. A row is a List of cells.
	 */
	public List getRow(int index){
		return (List) rows.get(index);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.ConvertibleToJSObject#toJSObject()
	 */
	public JavaScriptObject toJSObject() {
		JavaScriptObject cellsJSArray = JSHelper.convertObjectToJSObject(rows);
		JavaScriptObject jsview = createJSObject((cellsJSArray));
		if(width != null){
			jsview = addWidthToJSView(jsview, width);
		}
		jsview = setIsScrollable(jsview, !isScrollable); 
		return jsview;
	}
	
	/**
	 * Internal method used to generate the javascript view object
	 * 
	 * @param jsview
	 * @param width
	 * @return
	 */
	private native JavaScriptObject addWidthToJSView(JavaScriptObject jsview, String width)/*-{
		jsview['width'] = width;
		return jsview;
	}-*/;
	
	/**
	 * Internal method used to generate the javascript view object
	 * 
	 * @param view
	 * @param isNotScrollable
	 * @return
	 */
	private native JavaScriptObject setIsScrollable(JavaScriptObject view , boolean isNotScrollable)/*-{
		view['noscroll'] = isNotScrollable;
		return view;
	}-*/;
	
	/**
	 * Internal method used to generate the javascript view object
	 * 
	 * @param cells
	 * @return
	 */
	private native JavaScriptObject createJSObject(JavaScriptObject cells)/*-{
		var toReturn = @com.google.gwt.core.client.JavaScriptObject::createObject()();
		toReturn['cells'] = cells;
		return toReturn;
	}-*/;

	/**
	 * @return true if the view displays scrollbars, false otherwise
	 */
	public boolean isScrollable() {
		return isScrollable;
	}

	/**
	 * Determines wether the view displays scrollbars
	 * @param isScrollable
	 */
	public void setScrollable(boolean isScrollable) {
		this.isScrollable = isScrollable;
	}

	/**
	 * @return the grid view width , in standard css units
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * Sets the view width, in standard css units
	 * 
	 * @param width
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * Removes all rows from this view
	 */
	public void clear() {
		rows.clear();
	}
	
	
	
}
