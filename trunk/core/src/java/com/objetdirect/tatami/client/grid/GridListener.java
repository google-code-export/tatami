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

import com.objetdirect.tatami.client.data.Item;

/**
 * This interface describes a GridListener, which is notified each time an event
 * occurs on the grid.
 * 
 * 
 * @author rdunklau
 */
public interface GridListener {

	
	
	/**
	 * This method is called each time an onCellClick event is fired
	 * 
	 * @param grid : the grid on wich the event occured
	 * @param rowIndex : the row index on which the user clicked
	 * @param colIndex : the column index on which the user clicked
	 * @param colName : the field assigned to the column which was clicked
	 */
	public void onCellClick(Grid grid , int rowIndex , int colIndex , String colField);
	
	
	
	/**
	 * This method is called each time an onSelectionChanged event is
	 * fired, that is, every time a row is selected or unselected
	 * 
	 * To get the actual selection , please use {@link Grid#getSelectedIndexes()} for the
	 * row indexes or {@link Grid#getSelectedItems()} for the actual items represented
	 * by the selected rows
	 * 
	 * @param grid : the grid on which the event occured
	 */
	public void onSelectionChanged(Grid grid);
	
	/**
	 * This method is called each time an onDataChange event is fired, that is,
	 * every time an item changed one of his attribute in the underlying 
	 * datastore.
	 * 
	 * @param grid : the grid on which the event occured
	 * @param itemWhichChanged : the item from which one value has changed
	 * @param attributeName : the name of the attribute which has changed
	 * @param oldValue : the previous attribute value
	 * @param newValue : the new attribute value
	 */
	public void onDataChange(Grid grid , Item itemWhichChanged, String attributeName, Object oldValue, Object newValue);



	public void onCellDblClick(Grid grid, int rowIndex, int colIndex,
			String colField);
	
}



