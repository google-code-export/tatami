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

/**
 * @author rdunklau
 *
 * This interface defines a row styler. The row styler is called each time 
 * a row must be rendered.
 *
 */
public interface RowStyler {
	
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
	public String getRowCSSClasses(int rowIndex , boolean selected, boolean mouseover , boolean odd);
	
	
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
	public String getRowCSSStyles(int rowIndex , boolean selected, boolean mouseover , boolean odd);
	
}
