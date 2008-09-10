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
package com.objetdirect.tatami.client.grid.editor;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.grid.formatters.DateFormatter;

/**
 * A grid Date Editor (similar to drop down date picker)
 * 
 * @author rdunklau
 *
 */
public class DateEditor implements GridEditor{

	private final String dojoName = "DateTextBox";
	
	private Map attributes = new HashMap();
	
	public DateEditor(){
	}
	
	public DateEditor(String dateFormat , String timeFormat){
	}
	
	
	public Map getAttributes() {
		return attributes;
	}

	public String getDojoGridEditorName() {
		return dojoName;
	}

}
