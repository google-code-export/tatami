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
 * Authors:  Vianney Grassaud
 * Initial developer(s): Vianney Grassaud
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Group of utilitarian methods of manipulation / edition of dates. These methods integrate some DOJO functions.
 * They allow of landing the fact that GWT does'nt integrate  DateFormat in the JRE emulation library.
 * 
 * TODO : add some usefull methods from DOJO 0.9
 * @author Henry,Vianney
 */
public class DateUtil {

	
	static private final String DOJO_DATE_LOCALE = "dojo.date.locale"; 
	//static private final String DOJO_DATE_STAMP = "dojo.date.stamp";
	//static private final String DOJO_DATE = "dojo.date";
	
	static private final String SELECTOR_DATE= "date";
	static private final String SELECTOR_TIME= "time";
	static private final String SELECTOR_BOTH= "dateTime";
	
	private DateUtil() {
		
	}
	
	/**
	 * Converts a date (day, mounth, year). in a String format 
	 * @param date the JAVA date to convert.
	 * @param datePattern see the DOJO explanations : it's like in JAVA for the most of principals options
     * @param lang locale to use. example : "fr-FR" or "en-US"
	 * @return a string representing the given date with the specific options 
	 */
	public static String formatDate(Date date,String datePattern,String lang) 	{
		
		DojoController.getInstance().require(DOJO_DATE_LOCALE);
		JavaScriptObject theDate = getJSDate(date);
		return dojoDateFormat(theDate, datePattern, null, SELECTOR_DATE, lang);
	}

	/**
	 * Converts a hour information  (hours, minutes, seconds).
	 * @param date the JAVA hour information to convert.
     * @param timePattern see the DOJO explanations : it's like in JAVA for the most of principals options
     * @param lang locale to use. example : "fr-FR" or "en-US"
	 * @return a string corresponding to the JAVA date and specifics options.
	 */
	public static String formatTime(Date date,String timePattern,String lang){
		DojoController.getInstance().require(DOJO_DATE_LOCALE);
		JavaScriptObject theDate = getJSDate(date);
		return dojoDateFormat(theDate, null, timePattern, SELECTOR_TIME, lang);
	}
	
	/**
	 * Converts a JAVA Date (day,mounth,year, hours,minutes, seconds) with specified options
	 * @param date the JAVA date to convert
     * @param datePattern see the DOJO explanations : it's like in JAVA for the most of principals options
	 * @param timePattern see the DOJO explanations : it's like in JAVA for the most of principals options  
     * @param lang locale to use. example : "fr-FR" or "en-US"
	 * @return a string corresponding to the JAVA date and the specified option for the convertion. 
	 */
	public static String formatDateTime(Date date,String datePattern,String timePattern,String lang){
		DojoController.getInstance().require(DOJO_DATE_LOCALE);
		JavaScriptObject theDate = getJSDate(date);
		return dojoDateFormat(theDate, datePattern, timePattern, null, lang);
	}

	/**
	 * Formats a Date object as a String, using locale-specific settings.
	 * Create a string from a Date object using a known localized pattern.
	 * By default, this method formats both date and time from dateObject.
	 *	Formatting patterns are chosen appropriate to the locale.  Different
	 *	formatting lengths may be chosen, with "full" used by default.
	 *	Custom patterns may be used or registered with translations using
	 *	the addCustomBundle method.
	 *	Formatting patterns are implemented using the syntax described at
	 *	http://www.unicode.org/reports/tr35/tr35-4.html#Date_Format_Patterns
	 * @param theDate the date and/or time to be formatted.  If a time only is formatted,
     *  	  the values in the year, month, and day fields are irrelevant.  The
     *		  opposite is true when formatting only dates.
     * @param datePattern see the DOJO explanations : it's like in JAVA for the most of principals options 
     * @param timePattern see the DOJO explanations : it's like in JAVA for the most of principals options
     * @param selector : choice of timeOnly,dateOnly (default: date and time)
	 * @param lang locale to use. example : "fr-FR" or "en-US"
	 * @return a string corresponding to the JavaScript Date Object and the specified option
	 */
	static native String dojoDateFormat(JavaScriptObject theDate,String datePattern,String timePattern,	String selector,String lang) 
	/*-{  
		return $wnd.dojo.date.locale.format(
			theDate,
			{
				datePattern:	datePattern,
				timePattern:	timePattern,
				selector:		selector, 
				locale:			lang
			}
		);	
	}-*/;
	
	/**
	 * Converts a date information (day , mounth, year) in parsing a String 
	 * and using a date pattern.
	 * @param date a string representing a date information.
     * @param datePattern see the DOJO explanations : it's like in JAVA for the most of principals options
	 * @param lang locale to use. example : "fr-FR" or "en-US"
	 * @return the JAVA Date corresponding to the date information.
	 */
	public static Date parseDate(String date,String datePattern,String lang)
	{
		DojoController.getInstance().require(DOJO_DATE_LOCALE);
		JavaScriptObject theDate = dojoDateParse(date, datePattern, null, SELECTOR_DATE, lang);
		if (theDate == null) {
			throw new IllegalArgumentException(date);
		}
		return getJavaDate(theDate);
	}

	/**
	 * Converts a hour information (hours, minutes, second) in parsing a String 
	 * and using a time pattern.
	 * @param date a string representing a hour information.
     * @param timePattern see the DOJO explanations : it's like in JAVA for the most of principals options
	 * @param lang locale to use. example : "fr-FR" or "en-US"
	 * @return the JAVA Date corresponding to the hour inf.
	 */
	public static Date parseTime(String date,String timePattern,String lang)
	{
		DojoController.getInstance().require(DOJO_DATE_LOCALE);
		JavaScriptObject theDate = dojoDateParse(date, null, timePattern, SELECTOR_TIME, lang);
		if (theDate==null) {
			throw new IllegalArgumentException(date);
		}
		return getJavaDate(theDate);
	}

	/**
	 * Converts a temporal stamp (day, mounth, year, hours, minutes, second) in parsing a string 
	 * and using a time pattern and a date pattern.
	 * @param date the temporal stamp.
	 * @param datePattern see the DOJO explanations : it's like in JAVA for the most of principals options
	 * @param timePattern see the DOJO explanations : it's like in JAVA for the most of principals options  
     * @param lang locale to use. example : "fr-FR" or "en-US"
	 * @return the JAVA  Date corresponding to the temporal stamp 
	 */
	public static Date parseDateTime(String date,String datePattern,String timePattern,	String lang) 	{
		DojoController.getInstance().require(DOJO_DATE_LOCALE);
		JavaScriptObject theDate = dojoDateParse(date, datePattern, timePattern, SELECTOR_BOTH, lang);
		if (theDate==null) {
			throw new IllegalArgumentException(date);
		}
		return getJavaDate(theDate);
	}

	/**
	 * Converts a properly formatted string to a primitive Date object,
	 * using locale-specific settings. Create a Date object from a string using a known localized pattern.
	 * By default, this method parses looking for both date and time in the string.
	 * Formatting patterns are chosen appropriate to the locale.  Different
	 * formatting lengths may be chosen, with "full" used by default.
	 * Custom patterns may be used or registered with translations using
	 * the addCustomBundle method.
	 * Formatting patterns are implemented using the syntax described at
	 * http://www.unicode.org/reports/tr35/#Date_Format_Patterns
	 * @param date string representing the temporal information.
	 * @param datePattern see the DOJO explanations : it's like in JAVA for the most of principals options
	 * @param timePattern see the DOJO explanations : it's like in JAVA for the most of principals options 
	 * 
	 * @param lang locale to use. example : "fr-FR" or "en-US"
	 * @return the temporal information in a JavaScript Date object
	 */
	static native JavaScriptObject dojoDateParse(String theDate,String datePattern,	String timePattern,	String selector,String lang) 
	/*-{  
		return $wnd.dojo.date.parse(
			theDate,
			{
				datePattern:	datePattern,
				timePattern:	timePattern,
				selector:		selector, 
				locale:			lang
			}
		);	
	}-*/;

	
	
	
	/**
	 * Converts a JAVA Date object to a JavaScript Date object.
	 * @param date the JAVA date object to convert
	 * @return the JavaScript date object corresponding to the given JAVA object.
	 */
	public static JavaScriptObject getJSDate(Date date) {
		return getJSDate(date.getTime());
	}
	
	/**
	 * Converts a JavaScript Date object to a JAVA Date object.
	 * @param date the Javascript date object to convert
	 * @return the JAVA date object corresponding to the given JavaScript object.
	 */
	public static Date getJavaDate(JavaScriptObject date) {
		
		return new Date(getJSTime(date));
	}
	
	/**
	 * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT 
	 * represented by this JavaScript Date object.
	 * @param date the Javascript Date
	 * @return the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this date.
	 */
	public static native long getJSTime(JavaScriptObject theDate) 
	/*-{
		return theDate.getTime();
		
	}-*/;
	
	/**
	 * Allocates a JavaScript Date object and initializes it to represent the specified number of milliseconds 
	 * since the standard base time known as "the epoch", namely January 1, 1970, 00:00:00 GMT.
	 * @param time the milliseconds since January 1, 1970, 00:00:00 GMT.
	 * @return the Javascript  Date allocated
	 */
	public static native JavaScriptObject getJSDate(long time)
	/*-{
		return new Date(time);
	}-*/;
	
	
	
}//end of class
