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
import com.objetdirect.tatami.client.DojoController;

public class CurrencyFormatter implements Formatter{

	private String symbol;
	
	public CurrencyFormatter(String symbol) {
		this();
		this.symbol = symbol;
	}

	public CurrencyFormatter(){
		DojoController.getInstance().require("dojo.currency");
	}
	
	public native JavaScriptObject nativeGetFormatter(String symbol)/*-{
		var format = function(value){
			return $wnd.dojo.currency.format(value , this.formatter.options);
		}
		format.options = {}
		format.options.type = "currency";
		format.options.symbol = symbol
		return format;
	}-*/;
	
	public JavaScriptObject getFormatter(){
		return nativeGetFormatter(symbol);
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
