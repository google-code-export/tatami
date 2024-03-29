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
package com.objetdirect.tatami.client.grid.formatters;

import com.google.gwt.core.client.JavaScriptObject;

public abstract class BaseFormatter implements Formatter{
	
	public JavaScriptObject getFormatter() {
		return getJSFormatter();
	}
	
	protected native JavaScriptObject getJSFormatter()/*-{
		var f = function(toFormat){
			var javavalue;
			if(typeof toFormat == "boolean"){
			 		javavalue = @java.lang.Boolean::valueOf(Ljava/lang/String;)(toFormat+"");
			}else if(typeof toFormat == "number"){
				javavalue = @java.lang.Double::valueOf(Ljava/lang/String;)(toFormat+"");
			}else{
				javavalue = toFormat;
			}
			return this.formatter.gwtFormatter.@com.objetdirect.tatami.client.grid.formatters.BaseFormatter::format(Ljava/lang/Object;)(javavalue);
		};
		f.gwtFormatter = this;
		return f;
	}-*/;
	
	public abstract Object format(Object toFormat);

}
