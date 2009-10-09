package com.objetdirect.tatamix.client.widget;

/**
 * This is the model expected by the <code>Table</code> widget to present some data. 
 * 
 * @author Vianney 
 *@deprecated used the datagrid of tatami instead
 */
public interface TableModel {

	/**
	 * Returns the value at the row index and column position in the model
	 * @param row the index of a row
	 * @param col the index of a column
	 * @return the value at the row index and column position in the model
	 * @throws IndexOutOfBoundsException if row or column are negative or greater than the model can expect
	 */
	public Object getValueAt(int row,int col);
	
	/**
	 * Returns the number of columns in the model
	 * @return the number of columns in the model
	 */
	public int getColumnCount();
	
	/**
	 * Returns the name of a column at the specified index
	 * @param col the index of a column
	 * @return the name of a column at the specified index
	 */
	public String getColumnName(int col);
	
	/**
	 * Sets a value at the specified position in the model
	 * @param row the index of a row
	 * @param col the index of a column 
	 * @param value the value to use.
	 * @throws  IndexOutOfBoundsException if row or column are negative or greater than the model can expect
	 */
	public void setValueAt(int row,int col, Object value);
	
	/**
	 * Returns the number of rows in the model
	 * @return the number of rows in the model
	 */
	public int getRowCount();
	
	/**
	 * Return the class used by a column
	 * @param col the index of a column
	 * @return the class used by a column
	 * IndexOutOfBoundsException if column are negative or greater than the model can expect
	 */
	public Class getColumnClass(int col);
	
}
