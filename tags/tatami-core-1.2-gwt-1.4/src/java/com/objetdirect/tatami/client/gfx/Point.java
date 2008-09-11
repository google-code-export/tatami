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
package com.objetdirect.tatami.client.gfx;

import com.google.gwt.core.client.JavaScriptObject;

/** 
 * Represents a point in a 2D graphic environement
 * A point has two coordinates (integer) : x and y  
 * @author Vianney
 */
public class Point {

	private double x = 0;
	private double y = 0;
	
	/** 
	 * Constructs and initializes a point at the origin 
	 * (0, 0) of the coordinate space.
	 */
	public Point() {
		this(0,0);
	}
	
	/**
	 * Constructs and initializes a point with 
	 * the same location as the specified Point object.
	 * @param point
	 */
	public Point(Point point) {
		this(point.getX(),point.getY());
	}
	
	/**
	 * Creates a point
	 * @param x x coordinate 
	 * @param y y coordinate
	 */
	public Point(int x, int y) {
		setX(x);
		setY(y);
	}
	
	
	/**
	 * Creates a point
	 * @param x x coordinate 
	 * @param y y coordinate
	 */
	public Point(double x, double y) {
		setX(x);
		setY(y);
	}
	
	
	/**
	 * Changes the point to have the specified location.
	 * @param x the X coordinate of the new location
	 * @param y the Y coordinate of the new location
	 */
	public void setLocation(int x, int y) {
		setX(x);
		setY(y);
	}
	
	/**
	 * Changes the point to have the specified location.
	 * @param x the X coordinate of the new location
	 * @param y the Y coordinate of the new location
	 */
	public void setLocation(double x, double y) {
		setX(x);
		setY(y);
	}
	
	/**
	 * Changes the point to have the specified location.
	 * @param p the new point 
	 */
	public void setLocation(Point p) {
		setLocation(p.getX(),p.getY());
	}
	
	/**
	 * Returns the location of this point. 
	 * @return a copy of this point, at the same location
	 */
	public Point getLocation() {
		return new Point(this);
	}
	
	/**
	 * Sets the x coordinate
	 * @param x the x coordinate
	 */
	final public void setX(int x) {
	   this.x = x;	
	}
	
	/**
	 * Sets the x coordinate
	 * @param x the x coordinate
	 */
	final public void setX(double x) {
	   this.x = x;	
	}
	
	
	/**
	 * Sets the y coordinate
	 * @param y the y coordinate
	 */
	final public void setY(int y) {
		this.y = y;
	}
	
	
	/**
	 * Sets the y coordinate
	 * @param y the y coordinate
	 */
	final public void setY(double y) {
		this.y = y;
	}
	/**
	 * Returns the x coordinate
	 * @return the x coordinate
	 */
	public double getX() {
		return this.x;
	}
	/**
	 * Returns the y coordinate
	 * @return the y coordinate
	 */
	public double getY() {
		return this.y;
	}
	/**
	 * Creates the DOJO gfx Point
	 * @return the DOJO gfx Point
	 */
	protected JavaScriptObject getGFXPoint() {
		return createPoint(getX(),getY());
	}
	
	/**
	 * Creates the DOJO gfx Point
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return the DOJO GFX point
	 */
	private native JavaScriptObject createPoint(double x,double y)/*-{
	    return {x:x,y:y};
	}-*/; 
	
	/**
	 * Translates this point, at location (x,y),
	 *  by dx along the x axis and dy along the y axis so 
	 *  that it now represents the point (x+dx,y+dy).
	 * @param dx the distance to move this point along the X axis
	 * @param dy the distance to move this point along the Y axis
	 */
	public void translate(double dx, double dy) {
		final double newX = getX() + dx;
		final double newY = getY() + dy;
		setLocation(newX,newY);
	}
	
	/**
	 * Returns the distance from this <code>Point</code> to a specified <code>Point</code>.
	 * @param b the specified point to be measured against this <code>Point</code> 
	 * @return the distance between this <code>Point</code> and the specified <code>Point</code>.
	 */
	public double distance(Point b) {
		final double dx  = b.getX() - getX();
		final double dy  = b.getY() - getY();
		final double squarred = dx*dx + dy*dy;
		return Math.sqrt(squarred);
	}
	
	/**
	 * Returns the distance from this <code>Point</code> to a specified <code>Point</code>.
	 * @param xb the X coordinate of the specified point to be measured against this <code>Point</code> 
	 * @param yb the Y coordinate of the specified point to be measured against this <code>Point</code>
	 * @return the distance between this <code>Point</code> and the specified <code>Point</code>.
	 */
	public double distance(double xb,double yb) {
		return distance(new Point(xb,yb));
	}
	
	/**
	 * Returns the distance between two points.
	 * @param xa the X coordinate of the first specified point
	 * @param ya the Y coordinate of the first specified point
	 * @param xb the X coordinate of the second specified point
	 * @param yb the Y coordinate of the second specified point
	 * @return the distance between the two sets of specified coordinates.
	 */
	public static double distance(double xa,double ya, double xb, double yb) {
		final Point a = new Point(xa,ya);
		return a.distance(xb, yb);
	}
	
	/**
	 * Determines whether or not two points are equal.
	 * Two instances of <code>Point</code> are equal if the values of their x and y member fields, representing their position in 
	 * the coordinate space, are the same.
	 * @param object an object to be compared with this <code>Point</code>
	 * @return true if the object to be compared is an instance of <code>Point</code> and has the same values; false otherwise.
	 */
	public boolean equals(Object object) {
		boolean equals = false;
		if ( object instanceof Point) {
			final Point point = (Point)object;
			equals = (getX() == point.getX()) && (getY() == point.getY());
		} 
		return equals;
	}
	

	/**
	 * Sets this <code>Point</code> from a JavaScript DOJO
	 * GFX point
	 * @param point the DOJO GFX point
	 */
   protected void setPoint(JavaScriptObject point) {
	   setLocation(getX(point),getY(point));
   }

   /**
    * Returns the x coordinate of a GFX DOJO point
    * @param point the DOJO GFX point
    * @return the x coordinate of the DOJO GFX point
    */
   private native double getX(JavaScriptObject point) /*-{
	   return point.x;
   }-*/;
   
   /**
    * Returns the y coordinate of a GFX DOJO point
    * @param point the DOJO GFX point
    * @return the y coordinate of the DOJO GFX point
    */
   private native double getY(JavaScriptObject point) /*-{
	   return point.y;
   }-*/;
   
   public String toString() {
	   return "Point:(" + getX() +"," + getY()+")";  
   }

   /**
    * executes a rotation with the specify angle and the center of the rotation
    * @param a the angle of the rotation in degree
    * @param center the center point of the rotation
    */
   public void rotate(double angle,Point center) {
       final double radian = Math.toRadians(angle);
       final double sina = Math.sin(radian);
       final double cosa = Math.cos(radian);
       final double x1 = cosa * (x-center.getX()) - sina *  (y - center.getY()) + center.getX();
       final double y1 = sina * (x-center.getX()) +  cosa * (y - center.getY()) + center.getY();
       x = x1;
       y = y1;
   }
   
   
   
}//end of class
