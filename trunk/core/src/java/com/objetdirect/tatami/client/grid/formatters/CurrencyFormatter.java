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
	
	private String currency;
	
	public CurrencyFormatter(String symbol) {
		this();
		this.symbol = symbol;
	}
	
	public CurrencyFormatter(){
		DojoController.getInstance().require("dojo.currency");
	}
	
	public native JavaScriptObject nativeGetFormatter(String symbol , String currency)/*-{
		var format = function(value){
			return $wnd.dojo.currency.format(value , this.formatter.options);
		}
		format.options = {}
		if(symbol != null){
			format.options.symbol = symbol;
		}
		if(currency != null){
			format.options.currency = currency;
		}
		return format;
	}-*/;
	
	public JavaScriptObject getFormatter(){
		return nativeGetFormatter(symbol,currency);
	}

	public String getSymbol() {
		return symbol;
	}

	public CurrencyFormatter setSymbol(String symbol) {
		this.symbol = symbol;
		return this;
	}
	
	public CurrencyFormatter setCurrency(String currency) {
		this.currency = currency;
		return this;
	}
	
	public String getCurrency() {
		return currency;
	}
	
}
