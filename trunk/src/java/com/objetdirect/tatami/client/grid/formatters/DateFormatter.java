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

/**
 * @author rdunklau
 *
 */
public class DateFormatter implements Formatter {

	public static final int displayTimeAndDate = 0;
	
	public static final int displayTimeOnly = 1;
	
	public static final int displayDateOnly = 2;
	
	private int selector = displayTimeAndDate;

	private String datePattern;
	
	private String timePattern;
	
	public DateFormatter(){
		
	}
	
	public DateFormatter(int selector){
		this.selector = selector;
	}
	
	public DateFormatter(String datePattern , String timePattern){
		if(datePattern == null || datePattern.equals("")){
			this.selector = displayTimeOnly;
			this.timePattern = timePattern;
		}else if(timePattern == null || timePattern.equals("")){
			this.selector = displayDateOnly;
			this.datePattern = datePattern;
		}else{
			this.timePattern = timePattern ;
			this.datePattern = datePattern ;
		}
	}
	
	public JavaScriptObject getFormatter() {
		return getDateFormatter(selector , datePattern , timePattern);
	}
	
	private native JavaScriptObject getDateFormatter(int selector , String datePattern , String timePattern)/*-{
		var dateFormatter = function(date){
			if(date instanceof(Date)){
				return $wnd.dojo.date.locale.format(date , this.formatter.options);
			}else{
				var tempDate = new Date(date);
				return $wnd.dojo.date.locale.format(tempDate , this.formatter.options);
			}
		};
		dateFormatter.options = {}; 
		switch(selector){
			case 1 : 
				dateFormatter.options.selector = 'time';
				break;
			case 2 : 
				dateFormatter.options.selector = 'date';
				break;
			default : 
				break;
		}
		if(datePattern != null && datePattern != ''){
			dateFormatter.options.datePattern = datePattern;
		}
		if(timePattern != null && timePattern != ''){
			dateFormatter.options.timePattern = timePattern;
		}
		return dateFormatter;
	}-*/;

	public int getSelector() {
		return selector;
	}

	public void setSelector(int selector) {
		this.selector = selector;
	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	public String getTimePattern() {
		return timePattern;
	}

	public void setTimePattern(String timePattern) {
		this.timePattern = timePattern;
	}

}
