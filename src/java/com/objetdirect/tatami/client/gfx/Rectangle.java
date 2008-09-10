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
 * A <code>Rectangle</code> specifies an area in a coordinate space that is 
 * enclosed by the <code>Rectangle</code> object's top-left point 
 * (<i>x</i>,&nbsp;<i>y</i>) in the coordinate space, its width, and its height. 
 * <p>
 * 
 * </p>
 * @author 	Vianney
 * 
 */
public class Rectangle {

   /** Coordinates of this <code>Rectangle</code> */
	private Point point;
    /** width of this <code>Rectangle</code> */
	private double width = 0;
    /** height of this <code>Rectangle</code> */
	private double height = 0;

  
/**
 * Creates a <code>Rectangle</code>
 * @param pt the coordinates of this <code>Rectangle</code> 
 * @param width the width for the <code>Rectangle</code>
 * @param height the height for the <code>Rectangle</code>
 */
public Rectangle(Point pt,double width, double height) {
   this(pt.getX(),pt.getY(),width,height);
}




/**
 * Creates a <code>Rectangle</code>
 * @param x the X coordinate
 * @param y the Y coordinate
 * @param width the width for the <code>Rectangle</code>
 * @param height the height for the <code>Rectangle</code>c
 */
public Rectangle(double x, double y, double width, double height) {
	setRect(x,y,width,height);
	
}

/**
 * Creates an empty rectangle
 * 
 */
public Rectangle() {
	this(0,0,0,0);
}


/**
 * Returns the location of this<code>Rectangle</code>
 * @return the location of this<code>Rectangle</code>
 */
public Point getLocation() {
	return this.point;
}

/**
 * Returns the X coordinate of this <code>Rectangle</code>
 * @return the X coordinate of this <code>Rectangle</code> in double precision
 */

public double getX() {
	return point.getX();
}

/**
 * Returns the Y coordinate of this <code>Rectangle</code>
 * @return the Y coordinate of this <code>Rectangle</code> in double precision
 */
public double getY() {
	return point.getY();
}

/**
 * Returns the width of this <code>Rectangle</code>
 * @return the width of this <code>Rectangle</code> in double precision
 */
public double getWidth() {
	return width;
}

/**
 * Returns the height of this <code>Rectangle</code>
 * @return the height of this <code>Rectangle</code> in double precision
 */
public double getHeight() {
	return height;
}


/**
 * Returns a new <code>Rectangle</code> object representing 
 * the intersection of this <code>Rectangle</code> with the
 * specified <code>Rectangle</code>.
 * @param r the <code>Rectangle</code> to be intersected 
 * with this <code>Rectangle</code>
 * @return the largest <code>Rectangle</code> contained in 
 *         both the specified <code>Rectangle</code> and in this
 *         <code>Rectangle</code>.
 */
public Rectangle createIntersection(Rectangle r) {
    return Rectangle.intersect(this, r);
    
}

/**
 * Returns a new <code>Rectangle</code> object representing 
 * the union of this <code>Rectangle</code> with the
 * specified <code>Rectangle</code>.
 * @param r the <code>Rectangle</code> to be combined with
 * this <code>Rectangle</code>
 * @return  the smallest <code>Rectangle</code> containing 
 * both the specified <code>Rectangle</code> and this 
 * <code>Rectangle</code>.
 
 */
public Rectangle createUnion(Rectangle r) {
     return Rectangle.union(this, r);
    
}

/**
 * Returns the <code>String</code> representation of this
 * <code>Rectangle</code>.
 * @return a <code>String</code> representing this <code>Rectangle</code>.
 */
public String toString() {
    return  "Rectangle [x=" + point.getX() +
	",y=" + point.getY() +
	",w=" + width +
	",h=" + height + "]";
}

/**
 * Unions the pair of source <code>Rectangle</code> objects 
 * and return the result <code>Rectangle</code> object.  
 * @param src1 the first of a pair of <code>Rectangle</code>
 * objects to be combined with each other
 * @param src2 the second of a pair of <code>Rectangle</code>
 * objects to be combined with each other
 * @returns the <code>Rectangle</code> that holds the
 *          results of the union of <code>src1</code> and  
 *          <code>src2</code>
 
 */
public static Rectangle union(Rectangle src1,Rectangle src2) {
  final double x1 = Math.min(src1.getMinX(), src2.getMinX());
  final double y1 = Math.min(src1.getMinY(), src2.getMinY());
  final double x2 = Math.max(src1.getMaxX(), src2.getMaxX());
  final double y2 = Math.max(src1.getMaxY(), src2.getMaxY());
  return new Rectangle(x1, y1, x2, y2);
}


/**
 * Determines whether or not this <code>Rectangle</code> is empty. A 
 * <code>Rectangle</code> is empty if its width or its height is less 
 * than or equal to zero. 
 * @return     <code>true</code> if this <code>Rectangle</code> is empty; 
 *             <code>false</code> otherwise.
 */
public boolean isEmpty() {
   return (width <= 0) || (height <= 0);
}


/**
 * Intersects the pair of specified source <code>Rectangle</code>
 * objects and return the result <code>Rectangle</code> object.
 * @param src1 the first of a pair of <code>Rectangle</code> 
 *        objects to be intersected with each other
 * @param src2 the second of a pair of <code>Rectangle</code>
 *         objects to be intersected with each other
 * @return the <code>Rectangle</code> that holds the
 * results of the intersection of <code>src1</code> and
 * <code>src2</code>
 * 
 */
public static Rectangle intersect(Rectangle src1,Rectangle src2) {
 final double x1 = Math.max(src1.getMinX(), src2.getMinX());
 final double y1 = Math.max(src1.getMinY(), src2.getMinY());
 final double x2 = Math.min(src1.getMaxX(), src2.getMaxX());
 final double y2 = Math.min(src1.getMaxY(), src2.getMaxY());
 return new Rectangle(x1,y1,x2-x1,y2-y1);

}

/**
 * Determines whether or not this <code>Rectangle</code> and the specified 
 * <code>Rectangle</code> intersect. Two rectangles intersect if 
 * their intersection is nonempty. 
 *
 * @param r the specified <code>Rectangle</code>
 * @return    <code>true</code> if the specified <code>Rectangle</code> 
 *            and this <code>Rectangle</code> intersect; 
 *            <code>false</code> otherwise.
 */
public boolean intersects(Rectangle r) {
double tw = this.width;
double th = this.height;
double rw = r.getWidth();
double rh = r.getHeight();
if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
    return false;
}
final double tx = getX();
final double ty = getY();
final double rx = r.getX();
final double ry = r.getY();
rw += rx;
rh += ry;
tw += tx;
th += ty;
//      overflow || intersect
return ((rw < rx || rw > tx) &&
	(rh < ry || rh > ty) &&
	(tw < tx || tw > rx) &&
	(th < ty || th > ry));
}


/**
 * Sets the location and size of this <code>Rectangle</code>
 * to the specified double values.
 * @param x,&nbsp;y the coordinates to which to set the
 * location of the upper left corner of this
 * <code>Rectangle</code>
 * @param w the value to use to set the width of this
 * <code>Rectangle</code>
 * @param h the value to use to set the height of this
 * <code>Rectangle</code>
 
 */
final public void setRect(double x,double y, double w, double h) {
	point = new Point(x,y);
	width = w;
	height = h;
}

/**
 * Sets this <code>Rectangle</code> to be the same as the
 * specified <code>Rectangle</code>.
 * @param r the specified <code>Rectangle</code>
 */
public void setRect(Rectangle r) {
    setRect(r.getX(),r.getY(),r.getWidth(),r.getHeight());
}

/**
 * Returns the smallest X coordinate of the framing
 * rectangle of the <code>Shape</code> in <code>double</code>
 * precision.
 * @return the smallest x coordinate of the framing 
 * 		rectangle of the <code>Shape</code>.
 */
public double getMinX() {
return getX();
}

/**
 * Returns the smallest Y coordinate of the framing
 * rectangle of the <code>Shape</code> in <code>double</code> 
 * precision.
 * @return the smallest y coordinate of the framing 
 * 		rectangle of the <code>Shape</code>.
 */
public double getMinY() {
return getY();
}


/**
 * Returns the largest X coordinate of the framing 
 * rectangle of the <code>Shape</code> in <code>double</code>
 * precision.
 * @return the largest x coordinate of the framing
 * 		rectangle of the <code>Shape</code>.
 */
public double getMaxX() {
return getX() + getWidth();
}

/**
 * Returns the largest Y coordinate of the framing 
 * rectangle of the <code>Shape</code> in <code>double</code> 
 * precision.
 * @return the largest y coordinate of the framing 
 *		rectangle of the <code>Shape</code>.
 */
public double getMaxY() {
return getY() + getHeight();
}

/**
 * Returns the X coordinate of the center of the framing
 * rectangle of the <code>Shape</code> in <code>double</code>
 * precision.
 * @return the x coordinate of the framing rectangle 
 * 		of the <code>Shape</code> object's center.
 */
public double getCenterX() {
return getX() + getWidth() / 2.0;
}

/**
 * Returns the Y coordinate of the center of the framing 
 * rectangle of the <code>Shape</code> in <code>double</code>
 * precision.
 * @return the y coordinate of the framing rectangle 
 * 		of the <code>Shape</code> object's center.
 */
public double getCenterY() {
  return getY() + getHeight() / 2.0;
}

/**
 * Returns the center of the rectangle
 * @return the center of the rectangle
 */
public Point getCenter() {
	return new Point(getCenterX(),getCenterY());
}


/**
 * Sets the diagonal of the framing rectangle of this <code>Rectangle</code>
 * based on the two specified coordinates. 
 * @param x1,&nbsp;y1 the first specified coordinates
 * @param x2,&nbsp;y2 the second specified coordinates
 */
public void setRectFromDiagonal(double x1, double y1,double x2, double y2) {
    if (x2 < x1) {
     double t = x1;
     x1 = x2;
     x2 = t;
  }
  if (y2 < y1) {
     double t = y1;
     y1 = y2;
     y2 = t;
  }
   
  setRect(x1, y1, x2 - x1, y2 - y1);
}


/**
 * Determines whether or not the specified <code>Object</code> is
 * equal to this <code>Rectangle</code>.  The specified 
 * <code>Object</code> is equal to this <code>Rectangle</code>
 * if it is an instance of <code>Rectangle</code> and if its
 * location and size are the same as this <code>Rectangle</code>.
 * @param obj an <code>Object</code> to be compared with this
 * <code>Rectangle</code>.
 * @return   <code>true</code> if <code>obj</code> is an instance
 *                     of <code>Rectangle</code> and has
 *                     the same values; <code>false</code> otherwise.
 */
public boolean equals(Object obj) {
  boolean equal = false;
  if (obj == this) {
     return true;
  }
  if (obj instanceof Rectangle) {
    final Rectangle r2d = (Rectangle) obj;
    equal = ((getX() == r2d.getX()) &&
	    (getY() == r2d.getY()) &&
	    (getWidth() == r2d.getWidth()) &&
	    (getHeight() == r2d.getHeight()));
}
return equal;
}

/**
 * Sets this <code>Rectangle</code> from a dojo GFX Rectangle object. 
 * @param rect the DOJO GFX Rectangle object
 */
protected void setRect(JavaScriptObject rect) {
	setRect(getRectX(rect),
			getRectY(rect),
			getRectWidth(rect),
			getRectHeight(rect));
}

/**
 * Sets this <code>Rectangle</code> from an Array of four DOJO GFX points
 * The indexes of the corners: 
 * <code><br>
 * 0-----1<br>
 * |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
 * 3-----2<br>
 * </code>
 * @param points an Array of four DOJO GFX points
 */
protected void setRectFromPoints(JavaScriptObject points) {
	Point[] javaPoints = new Point[4];
    for (int i=0; i<javaPoints.length;i++) {
    	javaPoints[i] = new Point();
    	javaPoints[i].setPoint(getPoint(points,i));
    	
    }
	setRect(javaPoints[0].getX(),javaPoints[0].getY(),
			javaPoints[0].distance(javaPoints[1]),
			javaPoints[0].distance(javaPoints[3]));
	
}

/**
 * Returns a DOJO GFX Point from an Array of DOJO GFX Point
 * @param points the array 
 * @param index the index of the point to return
 * @return a DOJO GFX point
 */
native private JavaScriptObject getPoint(JavaScriptObject points,int index) /*-{
    return points[index];
}-*/; 

/**
 * Returns the X coordinate of the DOJO GFX rectangle
 * @param rect the DOJO GFX Rectangle object
 * @return the X coordinate of the DOJO GFX rectangle
 */
private native double getRectX(JavaScriptObject rect)/*-{
   return rect.x;
}-*/;

/**
 * Returns the Y coordinate of the DOJO GFX rectangle
 * @param rect the DOJO GFX Rectangle object
 * @return the Y coordinate of the DOJO GFX rectangle
 */
private native double getRectY(JavaScriptObject rect)/*-{
  return rect.y;
}-*/;

/**
 * Returns the width  of the DOJO GFX rectangle
 * @param rect the DOJO GFX Rectangle object
 * @return the width  of the DOJO GFX rectangle
 */

private native double getRectWidth(JavaScriptObject rect)/*-{
  return rect.width;
}-*/;

/**
 * Returns the height  of the DOJO GFX rectangle
 * @param rect the DOJO GFX Rectangle object
 * @return the height  of the DOJO GFX rectangle
 */
private native double getRectHeight(JavaScriptObject rect)/*-{
  return rect.height;
}-*/;

/**
 * Executes a translation of this <code>Rectangle</code>
 * the point which determines the position of the rectangle is translated.
 * @param dx the distance for the x coordinate to translate
 * @param dy the distance for the y coordinate to translate
 */
public void translate(double dx, double dy) {
	this.point.translate(dx, dy);
}

/**
 * Executes a rotation of this <code>Rectangle</code>
 * @param angle the angle of the rotation in degree
 */
public void rotate(double angle) {
	rotate(angle,getCenter());
	
}

/**
 * Executes a rotation of this <code>Rectangle</code>
 * @param angle the angle of the rotation in degree
 * @param center the center of the rotation
 */
public void rotate(double angle,Point center) {
	point.rotate(angle,center);
	
}


}//end of class
