/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@objectweb.org
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
 * Authors: Henri Darmet, Vianney Grassaud
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import java.util.Date;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.SimplePanel;
import com.objetdirect.tatami.client.gfx.Circle;
import com.objetdirect.tatami.client.gfx.Color;
import com.objetdirect.tatami.client.gfx.GraphicCanvas;
import com.objetdirect.tatami.client.gfx.GraphicObject;
import com.objetdirect.tatami.client.gfx.ImageGfx;
import com.objetdirect.tatami.client.gfx.Point;
import com.objetdirect.tatami.client.gfx.Polyline;

/**
 * A clock component with nipples
 * 
 * @author Vianney
 *
 */
public class Clock extends SimplePanel {

	/** 
	 * 
	 */
	private GraphicCanvas canvas;
	
	private Polyline hour_shadow;
	private Polyline hour_hand;
	private Polyline minute_shadow;
	private Polyline minute_hand;
	private Polyline second_hand;
	private Polyline second_shadow;
	
	private String image = null;
	private int width = 385;
	
	
	/** Color of the shadow */ 
	private final Color shadowColor = new Color(0,0,0,15);
	
	private Date current_time;
	
	private double angleMinute = 0.0;
	private double angleHour = 0.0;
	private double angleSecond = 0.0;
	
	private int defaultWidth = 385;
    private  Point center;
   
	
	/**
	 * Creates a clock without an image 
	 * and with a width equal to 192
	 *
	 */
	public Clock() {
	   this(null,192);
	}
	
	/**
	 * Creates a clock component. 
	 * An image can be used to fill the clock component. 
	 * @param url an url of an image to use for background if null no image will be set
	 * @param width the width for the image.
	 */
	public Clock(String url,int width) {
	  setElement(DOM.createDiv());
	  current_time = new Date();
	  this.width = width;
	  center = new Point(width/2,width/2);
	  image = url;
      makeClock();
	}
	
	/**
	 * Returns the current time;
	 * @return the current time
	 */
	public Date getTime() {
		return this.current_time;
	}
	
	/**
	 * Sets the color of the stroke for the minute nipples.
	 * @param color a color to apply
	 */
	public void setMinuteStrokeColor(Color color) {
		this.minute_hand.setStrokeColor(color);
	}
	
	/**
	 * Sets the width of the stroke for the minutes nipple
	 * @param width a width for the stroke
	 */
	public void setMinuteStrokeWidth(int width) {
		this.minute_hand.setStrokeWidth(width);
	}
	
	/**
	 * Sets the color for the stroke for the hours nipple
	 * @param color
	 */
	public void setHourStrokeColor(Color color) {
		this.hour_hand.setStrokeColor(color);
	}
	
	/**
	 * Sets the width of the strohe for the hours nipple
	 * @param width the width of the stroke
	 */
	public void setHourStrokeWidth(int width) {
		this.hour_hand.setStrokeWidth(width);
	}
	
	/**
	 * Sets the color of the stroke for the seconds nipple
	 * @param color a color
	 */
	public void setSecondStrokeColor(Color color) {
		this.second_hand.setStrokeColor(color);
	}
	
	/**
	 * Sets the fill color for the minutes nipple
	 * @param color a color
	 */
	public void setMinuteColor(Color color) {
		this.minute_hand.setFillColor(color);
	}
	
	
	/**
	 * Sets the fill color for the hours nipple
	 * @param color a color
	 */
	public void setHourColor(Color color) {
		this.hour_hand.setFillColor(color);
	}
	

	/**
	 * Sets the second color for the seconds nipple
	 * @param color a color
	 */
	public void setSecondColor(Color color) {
		this.second_hand.setFillColor(color);
	}
	
	
	/**
	 * Shows the clock when the widget is attached
	 * 
	 */
	public void onAttach() {
		super.onAttach();
		add(canvas);
		
		Timer timer = new Timer() {
			public void run() {
				reflectTime();
				current_time.setSeconds(current_time.getSeconds()+1);
				
			}
		};
		timer.scheduleRepeating(1000);
	}
	

	/**
	 * Makes the clock width GFX component
	 * The clock has 3 nipples (hour, minute and second). Each nipple 
	 * has also a shadow. So there 6 nipples to rotate. 
	 * An image is set in background, if it is null a circle is created instead.
	 * 
	 */
	private void makeClock() {
		canvas = new GraphicCanvas();
		canvas.setPixelSize(width, width);
		if ( image != null) {
		  ImageGfx clock = new ImageGfx(image,width,width);
		  canvas.add(clock, 0, 0);
		} else {
			Circle border = new Circle(width/3);
			border.setFillColor(Color.WHITE);
			canvas.add(border, (int)center.getX(), (int)center.getY());
		}
		
		//creates the hour and hour shadow
		Point[] hour_hand_points = new Point[4];
		hour_hand_points[0] = new Point(-7,15);
		hour_hand_points[1] = new Point(7,15);
		hour_hand_points[2] = new Point(0,-60); 
		hour_hand_points[3] = new Point(-7,15);

        hour_hand = new Polyline(hour_hand_points);
        hour_hand.setStrokeWidth(2);
        //default color for the hour
        hour_hand.setFillColor( Color.SILVER);
        resizeShape(hour_hand);
        
        canvas.add(hour_hand,(int)center.getX(),(int)center.getY());
        
		hour_shadow = new Polyline(hour_hand_points);
		resizeShape(hour_shadow);
		
	    setShadow(hour_shadow,3,3);
		//creates the minute and minutes shadow
        Point[]  minute_hand_points =  new Point[4];
		minute_hand_points[0] = new Point(-5,15);
		minute_hand_points[1] = new Point(5,15);
		minute_hand_points[2] = new Point(0,-100);
		minute_hand_points[3] = new Point(-5,15);
        minute_hand = new Polyline(minute_hand_points);
        resizeShape(minute_hand);
        // default color for the minutes
        minute_hand.setFillColor(new Color(127,127,127,255));
        canvas.add(minute_hand,(int)center.getX(),(int)center.getY());
        minute_shadow = new Polyline(minute_hand_points);
        resizeShape(minute_shadow);
        setShadow(minute_shadow,3,3);
        
        //creates the second and second shadow
        Point[] second_hand_points = new Point[8];
		second_hand_points[0] = new Point(-2,15);
		second_hand_points[1] = new Point(2,15);
		second_hand_points[2] = new Point(2,-105);
		second_hand_points[3] = new Point(6,-105);
		second_hand_points[4] = new Point(0,-116);
		second_hand_points[5] = new Point(-6,-105);
		second_hand_points[6] = new Point(-2,-105);
		second_hand_points[7] = new Point(-2,15);
		
		second_hand = new Polyline(second_hand_points);
		 resizeShape(second_hand);
		second_hand.setFillColor(Color.RED);
		second_hand.setStrokeColor(Color.PURPLE);
		canvas.add(second_hand,(int)center.getX(),(int)center.getY());
		
		
		second_shadow = new Polyline(second_hand_points);
		resizeShape(second_shadow);
		setShadow(second_shadow,4,4);
		//add a little circle in the center
		Circle circle = new Circle(1);
		canvas.add(circle,(int)center.getX(),(int)center.getY());
				
		
		
	}
	
	/**
	 * Sets properties for a shadow and add the shadow to the clock.
	 * Note the shadow is smally translated from the original nipple. 
	 * @param shadow a <code>GraphicObject</code> representing a shadow for a nipple
	 * @param dx the lag for the x coordinate of the shadow 
	 * @param dy the lag for the y coordinate of the shadow
	 */
    private void setShadow(GraphicObject shadow,int dx,int dy) {
    	shadow.setFillColor(shadowColor);
    	shadow.setStrokeStyle(GraphicObject.NONE);
    	shadow.setStrokeColor(shadowColor);
    	canvas.add(shadow,(int)center.getX(),(int)center.getY());
    	shadow.translate(dx,dy);
    }
	
	/**
	 * Resizes a nipple with the specified size given in the constructor
	 * @param shape the nipple a <code>GraphicObject</code> to resize
	 */
    private void resizeShape(GraphicObject shape) {
    	float factor =  (float)width/defaultWidth;
    	if ( factor != 1) {
    	  shape.scale(factor);
    	}
    }
    
    /**
     * Places the nipples in the clock to represent
     * the current time.
     *
     */
    private void reflectTime(){
		int h = current_time.getHours();
		int m = current_time.getMinutes();
		int s = current_time.getSeconds();
		placeHourHand(h, m, s);
		placeMinuteHand(m, s);
		placeSecondHand(s);
		
	};
	
	
   /**
    * Places the hour nipple
    * @param h the hour time
    * @param m the minute time
    * @param s the second time
    */
	private void placeHourHand(int h, int m, int s){
		double angle = 30 * (h % 12 + m / 60 + s / 3600);
		placeHand(hour_hand, angle - angleHour);
		placeHand(hour_shadow, angle - angleHour);
		angleHour = angle;

	};

	/**
	 * Places the minute nipple
	 * @param m the minute time
	 * @param s the second time
	 */
	private void placeMinuteHand(int m,int s){
		double  angle = 6 * (m + s / 60);
		placeHand(minute_hand, angle - angleMinute);
		placeHand(minute_shadow, angle - angleMinute);
		angleMinute = angle;
		
	};
/**
 * Places the second nipple
 * @param s the time of the seconds
 */
	private void placeSecondHand(int s){
		double  angle = 6 * s;
		placeHand(second_hand,angle - angleSecond);
		placeHand(second_shadow, angle- angleSecond);
		angleSecond = angle;
	};
	
	/**
	 * Rotates a nipple to represent its time.
	 * @param shape the nipple to rotate
	 * @param angle the angle for the rotation
	 
	 */
	private void placeHand(GraphicObject shape,double  angle){
		 shape.rotate((float)angle,center);
	}
}
