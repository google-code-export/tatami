package com.objetdirect.tatami.client;
/**
 * This class aims to specify some options of the TimePicker widget.
 * The constraints setable for the TimePicker are : 
 * <ul>
 *   <li>clickableIncrement:
				ISO-8601 string representing the amount by which 
				every clickable element in the time picker increases
				Set in non-Zulu time, without a time zone
				Example: "T00:15:00" creates 15 minute increments
				Must divide visibleIncrement evenly
                default is "T00:15:00".

		<li> visibleIncrement: 
				ISO-8601 string representing the amount by which 
				every element with a visible time in the time picker increases
				Set in non Zulu time, without a time zone
				Example: "T01:00:00" creates text in every 1 hour increment
		        default is "T01:00:00",

		<li> visibleRange: 
				ISO-8601 string representing the range of this TimePicker
				The TimePicker will only display times in this range
				Example: "T05:00:00" displays 5 hours of options
		        default is "T05:00:00" :
		<li> timePattern :
		     timePattern see the DOJO explanations : it's like in JAVA for the most of principals options
		     default is "HH:mm" 
		

 * </ul>
 * 
 */
public class TimePickerConstraints {
	
	public final static String EVERY_FIVE_MINUTES = "T00:05:00";
	public final static String EVERY_TEN_MINUTES  = "T00:10:00";
	public final static String EVERY_QUARTER_HOUR = "T00:15:00";
	public final static String EVERY_HALF_HOUR    = "T00:30:00";
	
	public final static String EVERY_ONE_HOUR     = "T01:00:00";
	public final static String EVERY_FIVE_HOURS   = "T05:00:00";
	public final static String EVERY_TEN_HOURS    = "T10:00:00";
	
	public final static String RANGE_FIVE_HOURS   = "T05:00:00";
	public final static String RANGE_TEN_HOURS    = "T10:00:00";
	public final static String RANGE_ONE_HOUR     = "T01:00:00";
	
	
	public final static String TIME_PATTERN_SHORT = "HH:mm";
	public final static String TIME_PATTERN_MEDIUM = "HH:mm:ss";
	
	public String visibleRange       = RANGE_FIVE_HOURS;
	public String clickableIncrement = EVERY_QUARTER_HOUR;
	public String visibleIncrement   = EVERY_ONE_HOUR;
	public String timePattern        = TIME_PATTERN_SHORT;
	
	
}
