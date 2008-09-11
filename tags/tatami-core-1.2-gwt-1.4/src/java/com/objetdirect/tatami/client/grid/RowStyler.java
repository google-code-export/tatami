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
