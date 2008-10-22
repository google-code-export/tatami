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
 * This class represents a Grid layout.
 * It is composed of {@link GridView}, and has an optional rowbar to
 * permit user to select a whole row without selecting a particular cell
 * 
 * 
 * @author rdunklau
 *
 */
public class GridLayout implements ConvertibleToJSObject{

	/**
	 * List of all views
	 */
	private List views = new ArrayList();

	/**
	 * Determines wether a row selection bar 
	 * should be displayed
	 */
	private boolean hasRowBar = true;
	
	/**
	 * @param index
	 * @return the view at given index
	 */
	public GridView getView(int index) {
		return (GridView) views.get(index);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.ConvertibleToJSObject#toJSObject()
	 */
	public JavaScriptObject toJSObject() {
		JavaScriptObject jsLayout = JSHelper.convertObjectToJSObject(views);
		return jsLayout;
	}
	
	private native void debugLayout(JavaScriptObject jsLayout)/*-{
		$wnd.console.log(jsLayout);
	}-*/;

	/**
	 * @param index: the index where the view should be added
	 * @param view : the view to add
	 */
	public void addView(int index, GridView view) {
		views.add(index, view);
	}

	/**
	 * @param view : the view to add
	 * @return
	 */
	public boolean addView(GridView view) {
		return views.add(view);
	}

	/**
	 * @param index : index of the view to remove
	 * @return 
	 */
	public Object removeView(int index) {
		Object removedView = views.remove(index);
		return removedView;
	}

	/**
	 * @param view : view to remove
	 * @return
	 */
	public boolean removeView(GridView view) {
		return views.remove(view);
	}

	/**
	 * removes all views
	 */
	public void clear() {
		views.clear();
	}
	
	/**
	 * Set wether a row selection bar (permitting to select a
	 * row without firing an on click event) should be displayer
	 * 
	 * @param shouldItExist 
	 */
	public void setRowBar(boolean shouldItExist){
		hasRowBar = shouldItExist;
	}
	
	/**
	 * @return true if this layout is set to display a row selection bar,
	 * false otherwise
	 */
	public boolean hasRowBar(){
		return hasRowBar;
	}
	
	/**
	 * @return : the total number of cells among all views
	 */
	public int getNbCells(){
		int nbColumns = 0;
		for (Iterator iterator = views.iterator(); iterator.hasNext();) {
			Object view = (Object) iterator.next();
			if(view instanceof GridView){
				nbColumns += ((GridView) view).getNbCells();
			}
		}
		return nbColumns;
	}
	
	
	/**
	 * Adds a cell at the end of the last row from the last view
	 * @param cell
	 */
	public void addCellToLastRowFromLastView(Cell cell){
		getLastView().addCellToLastRow(cell);
	}
	
	/**
	 * Adds a cell at given index of the last row from the last view
	 * @param cell
	 * @param index
	 */
	public void addCellToLastRowFromLastView(Cell cell , int index){
		getLastView().addCellToLastRow(cell , index);
	}
	
	/**
	 * Adds a cell at given index of the given row from the last view
	 * 
	 * @param cell
	 * @param row
	 * @param index
	 */
	public void addCellToLastView(Cell cell , int row , int index){
		getLastView().addCellToRow(cell, row , index);
	}
	
	/**
	 * Adds a cell at the end of the given row of the last view
	 * 
	 * @param cell
	 * @param row
	 */
	public void addCellToLastView(Cell cell ,  int row){
		getLastView().addCellToRow(cell, row);
	}

	/**
	 * @return the last view in the layout
	 */
	public GridView getLastView() {
		GridView lastView ;
		if(views.size() < 1){
			views.add(new GridView());
		}
		lastView = (GridView) views.get(views.size() - 1 );
		return lastView;
	}
	
	/**
	 * @param index
	 * @return the cell with the given index 
	 * among all views , indepently from the number of views or rows 
	 * in the views. 
	 */
	public Cell getCellAmongAllViews(int index){
		int previousSize = 0;
		for (Iterator iterator = views.iterator(); iterator.hasNext();) {
			GridView view = (GridView) iterator.next();
			int size = view.getNbCells();
			if(size > index - previousSize ){
				return view.getCell(index - previousSize ) ;
			}
			previousSize += size;
		}
		return null;
	}
	
	/**
	 * Removes a cell, wherever it is in this layout views
	 * 
	 * @param cell 
	 */
	public void removeCellAmongAllViews(Cell cell){
		for (Iterator iterator = views.iterator(); iterator.hasNext();) {
			GridView view = (GridView) iterator.next();
			view.removeCell(cell);
		}
	}
	
	/**
	 * @return the number of views
	 */ 
	public int getNbViews(){
		return views.size();
	}
	
	
}
