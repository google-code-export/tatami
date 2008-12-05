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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.ConvertibleToJSObject;
import com.objetdirect.tatami.client.JSHelper;
import com.objetdirect.tatami.client.grid.editor.GridEditor;
import com.objetdirect.tatami.client.grid.formatters.Formatter;


/**
 * This class represents a cell.
 * A cell defines an area of data in a grid. 
 * It is the atomic entity in the grid layout.
 * 
 * A cell content can be defined in two ways :
 * 	- a field name : if a field name is specified, the cell will be filled 
 * with the same named attribute in the grid data store
 * 	- no field name and a default value : the cell content will be constant
 * a cell content can contain HTML. 
 * 
 * 
 * @author rdunklau
 *
 */
public class Cell implements ConvertibleToJSObject{

	
	
	
	/**
	 * If !null, the cell is editable with this editor
	 */
	private GridEditor editor;
	
	/**
	 * Map containing all the cell attributes
	 */
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	
	/**
	 * Constructs a new cell which content will be the 
	 * value of the specified field in the datastore, and which
	 * header will be the given name
	 * 
	 * @param field
	 * @param name
	 */
	public Cell(String field, String name) {
		this(name);
		attributes.put("field" , field);
	}
	
	/**
	 * Constructs a new cell which content will be the default value
	 * 
	 * @param name
	 */
	public Cell(String name){
		attributes.put("name"  , name);
	}
	
	/**
	 * Constructs a new cell which content will be the 
	 * value of the specified field in the datastore, and which
	 * header will be the given name.
	 * This cell will be editable via the given GridEditor
	 *  
	 * @param field
	 * @param name
	 * @param editor
	 */
	public Cell(String field , String name , GridEditor editor){
		this(field , name);
		setEditor(editor);
	}
	
	/**
	 * Constructs a new cell which content will be the 
	 * value of the specified field in the datastore, and which
	 * header will be the given name.
	 * This cell will be editable via the given GridEditor
	 * This cell will be formatted with the given formatter
	 * 
	 * @param field
	 * @param name
	 * @param editor
	 * @param formatter
	 */
	public Cell(String field , String name , GridEditor editor , Formatter formatter){
		this(field , name , editor);
		setFormatter(formatter);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.ConvertibleToJSObject#toJSObject()
	 */
	public JavaScriptObject toJSObject(){
		JavaScriptObject jsRepresentation = JavaScriptObject.createObject();
		Set<String> cellAttributesKey = attributes.keySet();
		for (Iterator<String> iterator = cellAttributesKey.iterator(); iterator
				.hasNext();) {
			String cellAttributeKey = iterator.next();
			Object cellAttributeValue = attributes.get(cellAttributeKey);
			if(cellAttributeValue instanceof String){
				addAttribute(jsRepresentation, cellAttributeKey, (String)cellAttributeValue);
			}else if(cellAttributeValue instanceof Number){
				addAttribute(jsRepresentation, cellAttributeKey, ((Number)cellAttributeValue).floatValue());
			}else if(cellAttributeValue instanceof Boolean){
				addAttribute(jsRepresentation, cellAttributeKey, ((Boolean) cellAttributeValue).booleanValue());
			}else{
				addAttribute(jsRepresentation, cellAttributeKey, JSHelper.convertObjectToJSObject(cellAttributeValue));
			}
		}
		if(editor != null){
			jsRepresentation = setCellEditor(jsRepresentation, editor.getDojoGridEditorName(), Boolean.TRUE);
			Map<String,? extends Object> editorAttrs = editor.getAttributes();
			if(editorAttrs != null){
				Set<String> editorProperties = editorAttrs.keySet();
				for (String attr : editorProperties) {
					addAttribute(jsRepresentation,attr, JSHelper.convertObjectToJSObject(editor.getAttributes().get(attr)));
				}
			}
		}
		return jsRepresentation;
	}
	
	/**
	 * Internal method helping to construct the javascript cell object 
	 * 
	 * @param toModify
	 * @param attrName
	 * @param attrValue
	 * @return
	 */
	private native JavaScriptObject addAttribute(JavaScriptObject toModify , String attrName , JavaScriptObject attrValue)/*-{
		toModify[attrName] = attrValue;
		return toModify;
	}-*/;
	
	/**
	 * Internal method helping to construct the javascript cell object
	 * 
	 * 
	 * @param toModify
	 * @param attrName
	 * @param attrValue
	 * @return
	 */
	private native JavaScriptObject addAttribute(JavaScriptObject toModify , String attrName , String attrValue)/*-{
		toModify[attrName] = attrValue;
		return toModify;
	}-*/;
	
	/**
	 * Internal method helping to construct the javascript cell object
	 * 
	 * 
	 * @param toModify
	 * @param attrName
	 * @param attrValue
	 * @return
	 */
	private native JavaScriptObject addAttribute(JavaScriptObject toModify , String attrName , float attrValue)/*-{
	toModify[attrName] = attrValue;
	return toModify;
	}-*/;
	
	/**
	 * Internal method helping to construct the javascript cell object
	 * 
	 * @param toModify
	 * @param attrName
	 * @param attrValue
	 * @return
	 */
	private native JavaScriptObject addAttribute(JavaScriptObject toModify , String attrName , boolean attrValue)/*-{
		toModify[attrName] = attrValue;
		return toModify;
	}-*/;
	
	/**
	 * Sets the cell editor according to its dojo class name
	 * 
	 * @param toModify : the javascript cell object
	 * @param editorName : the editor class name
	 * @param editable : wether the cell is editable
	 * @return the modified javascript cell object
	 */
	private native JavaScriptObject setCellEditor(JavaScriptObject toModify , String editorName , boolean editable)/*-{
		toModify['type'] = $wnd.dojo.getObject(editorName);
		toModify['editable'] = editable;
		return toModify;
	}-*/;
	
	/**
	 * @return the css classes which must be applied to this cell data
	 * (it is NOT applied to the cell header)
	 */
	public String getCellClasses() {
		return (String) attributes.get("cellClasses");
	}



	/**
	 * @param cellClasses :the css classes which must be applied to this cell data
	 * (it is NOT applied to the cell header)
	 */
	public void setCellClasses(String cellClasses) {
		attributes.put("cellClasses", cellClasses);
	}
	
	



	/**
	 * @return : the css styles which must be applied to this cell data
	 * (it is NOT applied to the cell header)
	 */
	public String getCellStyles() {
		return (String) attributes.get("cellStyles");
	}



	/**
	 * @param cellStyles : the css styles which must be applied to this cell data
	 * (it is NOT applied to the cell header)
	 */
	public void setCellStyles(String cellStyles) {
		attributes.put("cellStyles", cellStyles);
	}
	
	
	/**
	 * @return the css classes which must be applied to this cell header
	 * 
	 */
	public String getHeaderClasses() {
		return (String) attributes.get("headerClasses");
	}



	/**
	 * @param cellClasses :the css classes which must be applied to this cell header
	 * 
	 */
	public void setHeaderClasses(String cellClasses) {
		attributes.put("headerClasses", cellClasses);
	}
	
	



	/**
	 * @return : the css styles which must be applied to this cell header
	 * 
	 */
	public String getHeaderStyles() {
		return (String) attributes.get("headerStyles");
	}



	/**
	 * @param cellStyles : the css styles which must be applied to this cell header
	 * 
	 */
	public void setHeaderStyles(String cellStyles) {
		attributes.put("headerStyles", cellStyles);
	}



	/**
	 * @return the css classes which must be applied to the whole cell (data + header)
	 */
	public String getClasses() {
		return (String) attributes.get("classes");
	}

	

	/**
	 * @param classes : the css classes which must be applied to the whole cell (data + header)
	 */
	public void setClasses(String classes) {
		attributes.put("classes", classes);
	}

	


	/**
	 * @return the css styles which must be applied to the whole cell (data + header)
	 */
	public String getStyles(){
		return (String) attributes.get("styles");
	}
	
	/**
	 * @param styles :  the css styles which must be applied to the whole cell (data + header)
	 */
	public void setStyles(String styles) {
		attributes.put("styles", styles);
	}

	/**
	 * @return this cell colspan
	 */
	public Integer getColSpan() {
		return (Integer) attributes.get("colSpan");
	}



	/**
	 * @param colSpan : this cell colspan
	 */
	public void setColSpan(Integer colSpan) {
		attributes.put("colSpan", colSpan);
	}

	

	/**
	 * @return this cell width in standard css units
	 */
	public String getWidth() {
		return (String) attributes.get("width");
	}



	/**
	 * @param width : this cell width in standard css units
	 */
	public void setWidth(String width) {
		attributes.put("width", width);
	}

	/**
	 * @return the cell rowspan 
	 */
	public Integer getRowSpan() {
		return (Integer) attributes.get("rowSpan");
	}



	/**
	 * @param rowSpan : the cell rowspan 
	 */
	public void setRowSpan(Integer rowSpan) {
		attributes.put("rowSpan", rowSpan);
	}
	
	/**
	 * @return true if the cell is not resizable, 
	 * false if the cell is resizable (default : false)
	 */ 
	public Boolean getIsNotResizable() {
		return (Boolean) attributes.get("noresize");
	}



	/**
	 * @param noresize : true if the cell is not resizable, 
	 * false if the cell is resizable
	 */
	public void setIsNotResizable(Boolean noresize) {
		attributes.put("noresize", noresize);
	}



	/**
	 * @return the value to be displayed in this cell if it isn't 
	 * connected to a field, or when this field is not specified for the item in the datastore
	 */
	public String getDefaultValue() {
		return (String) attributes.get("value");
	}



	/**
	 * @param defaultValue : the value to be displayed in this cell if it isn't 
	 * connected to a field, or when this field is not specified for the item in the datastore
	 */
	public void setDefaultValue(String defaultValue) {
		attributes.put("value", defaultValue);
	}

	/**
	 * @return : the attribute name which is used to fill this cell content
	 */
	public String getField() {
		return (String) attributes.get("field");
	}

	/**
	 * @return the cell name (header's label)
	 */
	public String getName() {
		return (String) attributes.get("name");
	}

	/**
	 * @param field : the attribute name which is used to fill this cell content
	 */
	public void setField(String field) {
		attributes.put("field" , field);
	}

	/**
	 * @param name : the cell name (header's label)
	 */
	public void setName(String name) {
		attributes.put("name" , name);
	}
	
	/**
	 * @return the editor used to edit this cell. 
	 * Null indicates that the cell is not editable (default : null)
	 */
	public GridEditor getEditor() {
		return editor;
	}

	/**
	 * @param editor : the editor used to edit this cell. 
	 * Null indicates that the cell is not editable (default : null) 
	 */
	public void setEditor(GridEditor editor) {
		this.editor = editor;
		Map<String , ? extends Object> editorAttributes = editor.getAttributes(); 
	}

	/**
	 * @param formatter : the formatter used  to format the cell content
	 */
	public void setFormatter(Formatter formatter){
		attributes.put("formatter", formatter.getFormatter());
	}
	
	/**
	 * @return : the formatter used  to format the cell content
	 */
	public Formatter getFormatter(){
		return (Formatter) attributes.get("formatter");
	}
}
