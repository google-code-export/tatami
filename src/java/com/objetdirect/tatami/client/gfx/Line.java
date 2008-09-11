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
 * This line is constructed with 2 points 
 * @author Vianney
 *
 */
public class Line extends GraphicObject {
    /**
     * The  coordinates of the A
     */
	private Point pointA;
	/**
	 * The  coordinates of the B
	 */
	private Point pointB;
	
	/**
	 * Creates a line from 2 points
	 * @param a the point a 
	 * @param b the point b
	 */
	public Line(Point a,Point b) {
		super();
		this.pointA = new Point(a);
		this.pointB = new Point(b);
	}
	
	
	/**
	 * Returns the first point of this <code>Line</code>
	 * @return the first point of this <code>Line</code>
	 */
	public Point getPointA() {
		return this.pointA;
	}
	
	/**
	 * Returns the second point of this <code>Line</code>
	 * @return the second point of this <code>Line</code>	 
	 **/
	public Point getPointB() {
		return this.pointB;
	}
	
	/**
	 * Returns the X coordinaite of the first point
	 * @return the X coordinaite of the first point
	 */
	public double getXA() {
		return pointA.getX();
	}
	
	/**
	 * Returns the Y coordinaite of the first point
	 * @return the Y coordinaite of the first point
	 */
	public double getYA() {
		return pointA.getY();
	}
	
	/**
	 * Returns the X coordinaite of the second point 
	 * @return the X coordinaite of the second point
	 */
	public double getXB() {
		return pointB.getX();
	}
	
	/**
	 * Returns the Y coordinaite of the second point
	 * @return the Y coordinaite of the second point
	 */
	public double getYB() {
		return pointB.getY();
	}
	
	/**
	 * Creates a line from the coordinates of two points
	 * @param xa the x coordinate of the point a
	 * @param ya the a coordinate of the point a
	 * @param xb the x coordinate of the point b
	 * @param yb the y coordinate of the point b
	 */
	public Line(int xa,int ya, int xb, int yb) {
		this(new Point(xa,ya),new Point(xb,yb));
	}
	
	/**
	 * Creates the DOJO gfx line
	 * @param surface the canvas
	 */
	protected JavaScriptObject createGfx(JavaScriptObject surface) {
		return createLine(surface,pointA.getX(),pointA.getY(),pointB.getX(),pointB.getY());
	}

    /**
     * Creates a DOJO GFX line
     * @param surface the canvas
     * @param xa the x coordinate of the point a
	 * @param ya the a coordinate of the point a
	 * @param xb the x coordinate of the point b
	 * @param yb the y coordinate of the point b
     * @return the DOJO GFX line
     */	
	private native JavaScriptObject createLine(JavaScriptObject surface,double xa,double ya,double xb,double yb) /*-{
	    return surface.createLine({
	      x1:xa,
	      y1:ya,
	      x2:xb,
	      y2:yb
	    });
	}-*/;
	
	

	
	/**
     * Returns the distance from a <code>Point</code> to this line
     * segment.
     * The distance measured is the distance between the specified
     * point and the closest point between the current line's endpoints.  
     * If the specified point intersects the line segment in between the
     * endpoints, this method returns 0.0.
     * @param pt the specified <code>Point</code> being measured
     *		against this line segment
     * @return a double value that is the distance from the specified
     *				<code>Point</code> to the current line
     *				segment.
     * @see #ptLineDist(Point)
     */
    public double ptSegDist(Point pt) {
	   return ptSegDist(getXA(), getYA(), getXB(), getYB(),pt.getX(), pt.getY());
    }
	
    
    /**
     * Returns the distance from a point to a line segment.
     * The distance measured is the distance between the specified
     * point and the closest point between the specified endpoints.  
     * If the specified point intersects the line segment in between the
     * endpoints, this method returns 0.0.
     * @param XA,&nbsp;YA the coordinates of the beginning of the
     *		specified line segment
     * @param XB,&nbsp;YB the coordinates of the end of the specified line
     * 		segment
     * @param PX,&nbsp;PY the coordinates of the specified point being
     *		measured against the specified line segment
     * @return a double value that is the distance from the specified point
     *				to the specified line segment.
     * @see #ptLineDist(double, double, double, double, double, double)
     */
    public static double ptSegDist(double XA, double YA, double XB, double YB,  double PX, double PY) {
	   return Math.sqrt(ptSegDistSq(XA, YA, XB, YB, PX, PY));
    }
    
    /**
     * Returns the square of the distance from a point to a line segment.
     * The distance measured is the distance between the specified
     * point and the closest point between the specified endpoints.  
     * If the specified point intersects the line segment in between the
     * endpoints, this method returns 0.0.     
     * @param XA,&nbsp;YA the coordinates of the beginning of the 
     *			specified line segment
     * @param XB,&nbsp;YB the coordinates of the end of the specified 
     *		line segment
     * @param PX,&nbsp;PY the coordinates of the specified point being
     *		measured against the specified line segment
     * @return a double value that is the square of the distance from the
     *			specified point to the specified line segment.
     * @see #ptLineDistSq(double, double, double, double, double, double)
     */
    public static double ptSegDistSq(double XA, double YA, double XB, double YB,double PX, double PY) {
	// Adjust vectors relative to XA,YA
	// XB,YB becomes relative vector from XA,YA to end of segment
	XB -= XA;
	YB -= YA;
	// PX,PY becomes relative vector from XA,YA to test point
	PX -= XA;
	PY -= YA;
	double dotprod = PX * XB + PY * YB;
	double projlenSq;
	if (dotprod <= 0.0) {
	    // PX,PY is on the side of XA,YA away from XB,YB
	    // distance to segment is length of PX,PY vector
	    // "length of its (clipped) projection" is now 0.0
	    projlenSq = 0.0;
	} else {
	    // switch to backwards vectors relative to XB,YB
	    // XB,YB are already the negative of XA,YA=>XB,YB
	    // to get PX,PY to be the negative of PX,PY=>XB,YB
	    // the dot product of two negated vectors is the same
	    // as the dot product of the two normal vectors
	    PX = XB - PX;
	    PY = YB - PY;
	    dotprod = PX * XB + PY * YB;
	    if (dotprod <= 0.0) {
		// PX,PY is on the side of XB,YB away from XA,YA
		// distance to segment is length of (backwards) PX,PY vector
		// "length of its (clipped) projection" is now 0.0
		projlenSq = 0.0;
	    } else {
		// PX,PY is between XA,YA and XB,YB
		// dotprod is the length of the PX,PY vector
		// projected on the XB,YB=>XA,YA vector times the
		// length of the XB,YB=>XA,YA vector
		projlenSq = dotprod * dotprod / (XB * XB + YB * YB);
	    }
	}
	// Distance to line is now the length of the relative point
	// vector minus the length of its projection onto the line
	// (which is zero if the projection falls outside the range
	//  of the line segment).
	double lenSq = PX * PX + PY * PY - projlenSq;
	if (lenSq < 0) {
	    lenSq = 0;
	}
	return lenSq;
    }
    
    
    /**
     * Returns the square of the distance from a point to this line segment.
     * The distance measured is the distance between the specified
     * point and the closest point between the current line's endpoints.  
     * If the specified point intersects the line segment in between the
     * endpoints, this method returns 0.0.
     * @param PX,&nbsp;PY the coordinates of the specified point being
     * 		measured against this line segment
     * @return a double value that is the square of the distance from the
     *			specified point to the current line segment.
     * @see #ptLineDistSq(double, double)
     */
    public double ptSegDistSq(double PX, double PY) {
	return ptSegDistSq(getXA(), getYA(), getXB(), getYB(), PX, PY);
    }
    
    /**
     * Returns the square of the distance from a <code>Point</code> to 
     * this line segment.
     * The distance measured is the distance between the specified
     * point and the closest point between the current line's endpoints.  
     * If the specified point intersects the line segment in between the
     * endpoints, this method returns 0.0.
     * @param pt the specified <code>Point</code> being measured against
     *	         this line segment.
     * @return a double value that is the square of the distance from the
     *			specified <code>Point</code> to the current 
     *			line segment.
     * @see #ptLineDistSq(Point)
     */
    public double ptSegDistSq(Point pt) {
	    return ptSegDistSq(getXA(), getYA(), getXB(), getYB(), pt.getX(), pt.getY());
    }

    /**
     * Returns the distance from a point to this line segment.
     * The distance measured is the distance between the specified
     * point and the closest point between the current line's endpoints.  
     * If the specified point intersects the line segment in between the
     * endpoints, this method returns 0.0.
     * @param PX,&nbsp;PY the coordinates of the specified point
     *			  being measured against this line segment
     * @return a double value that is the distance from the specified 
     *			point to the current line segment.
     * @see #ptLineDist(double, double)
     */
    public double ptSegDist(double PX, double PY) {
       	return ptSegDist(getXA(), getYA(), getXB(), getYB(), PX, PY);
    }
    
    /**
     * Returns the square of the distance from a point to a line.
     * The distance measured is the distance between the specified
     * point and the closest point on the infinitely-extended line
     * defined by the specified coordinates.  If the specified point 
     * intersects the line, this method returns 0.0.
     * @param XA,&nbsp;YA the coordinates of one point on the
     * 		specified line
     * @param XB,&nbsp;YB the coordinates of another point on 
     *		the specified line
     * @param PX,&nbsp;PY the coordinates of the specified point being
     * 		measured against the specified line
     * @return a double value that is the square of the distance from the
     *			specified point to the specified line.
     * @see #ptSegDistSq(double, double, double, double, double, double)
     */
    public static double ptLineDistSq(double XA, double YA,double XB, double YB, double PX, double PY) {
	// Adjust vectors relative to XA,YA
	// XB,YB becomes relative vector from XA,YA to end of segment
	XB -= XA;
	YB -= YA;
	// PX,PY becomes relative vector from X1,Y1 to test point
	PX -= XA;
	PY -= YA;
	double dotprod = PX * XB + PY * YB;
	// dotprod is the length of the PX,PY vector
	// projected on the XA,YA=>XB,YB vector times the
	// length of the XA,YA=>XB,YB vector
	double projlenSq = dotprod * dotprod / (XB * XB + YB * YB);
	// Distance to line is now the length of the relative point
	// vector minus the length of its projection onto the line
	double lenSq = PX * PX + PY * PY - projlenSq;
	if (lenSq < 0) {
	    lenSq = 0;
	}
	return lenSq;
    }

    /**
     * Returns the distance from a point to a line.
     * The distance measured is the distance between the specified
     * point and the closest point on the infinitely-extended line
     * defined by the specified coordinates.  If the specified point 
     * intersects the line, this method returns 0.0.
     * @param XA,&nbsp;YA the coordinates of one point on the
     *		specified line
     * @param XB,&nbsp;YB the coordinates of another point on the
     *		specified line
     * @param PX,&nbsp;PY the coordinates of the specified point being
     *		measured against the specified line
     * @return a double value that is the distance from the specified
     *			 point to the specified line.
     * @see #ptSegDist(double, double, double, double, double, double)
     */
    public static double ptLineDist(double XA, double YA, double XB, double YB, double PX, double PY) {
	    return Math.sqrt(ptLineDistSq(XA, YA, XB, YB, PX, PY));
    }

    /**
     * Returns the square of the distance from a point to this line.
     * The distance measured is the distance between the specified
     * point and the closest point on the infinitely-extended line
     * defined by this <code>Line</code>.  If the specified point 
     * intersects the line, this method returns 0.0.
     * @param PX,&nbsp;PY the coordinates of the specified point being
     *		measured against this line
     * @return a double value that is the square of the distance from a 
     *			specified point to the current line.
     * @see #ptSegDistSq(double, double)
     */
    public double ptLineDistSq(double PX, double PY) {
	return ptLineDistSq(getXA(), getYA(), getXB(), getYB(), PX, PY);
    }

    /**
     * Returns the square of the distance from a specified 
     * <code>Point</code> to this line.
     * The distance measured is the distance between the specified
     * point and the closest point on the infinitely-extended line
     * defined by this <code>Line</code>.  If the specified point 
     * intersects the line, this method returns 0.0.
     * @param pt the specified <code>Point</code> being measured
     *           against this line
     * @return a double value that is the square of the distance from a
     *			specified <code>Point</code> to the current
     *			line.
     * @see #ptSegDistSq(Point)
     */
    public double ptLineDistSq(Point pt) {
	     return ptLineDistSq(getXA(), getYA(), getXB(), getYB(),pt.getX(), pt.getY());
    }

    /**
     * Returns the distance from a point to this line.
     * The distance measured is the distance between the specified
     * point and the closest point on the infinitely-extended line
     * defined by this <code>Line</code>.  If the specified point 
     * intersects the line, this method returns 0.0.
     * @param PX,&nbsp;PY the coordinates of the specified point being
     *		measured against this line
     * @return a double value that is the distance from a specified point
     *			to the current line.
     * @see #ptSegDist(double, double)
     */
    public double ptLineDist(double PX, double PY) {
	  return ptLineDist(getXA(), getYA(), getXB(), getYB(), PX, PY);
    }

    /**
     * Returns the distance from a <code>Point</code> to this line.
     * The distance measured is the distance between the specified
     * point and the closest point on the infinitely-extended line
     * defined by this <code>Line</code>.  If the specified point 
     * intersects the line, this method returns 0.0.
     * @param pt the specified <code>Point</code> being measured
     * @return a double value that is the distance from a specified 
     *			<code>Point</code> to the current line.
     * @see #ptSegDist(Point)
     */
    public double ptLineDist(Point pt) {
	   return ptLineDist(getXA(), getYA(), getXB(), getYB(),pt.getX(), pt.getY());
    }

    
    
    /**
     * Returns an indicator of where the specified point 
     * (PX,&nbsp;PY) lies with respect to the line segment from 
     * (XA,&nbsp;YA) to (XB,&nbsp;YB).
     * The return value can be either 1, -1, or 0 and indicates
     * in which direction the specified line must pivot around its
     * first endpoint, (XA,&nbsp;YA), in order to point at the
     * specified point (PX,&nbsp;PY).
     * <p>A return value of 1 indicates that the line segment must
     * turn in the direction that takes the positive X axis towards
     * the negative Y axis.  In the default coordinate system  this direction is counterclockwise.  
     * <p>A return value of -1 indicates that the line segment must
     * turn in the direction that takes the positive X axis towards
     * the positive Y axis.  In the default coordinate system, this 
     * direction is clockwise.
     * <p>A return value of 0 indicates that the point lies
     * exactly on the line segment.  Note that an indicator value 
     * of 0 is rare and not useful for determining colinearity 
     * because of floating point rounding issues. 
     * <p>If the point is colinear with the line segment, but 
     * not between the endpoints, then the value will be -1 if the point
     * lies "beyond (XA,&nbsp;YA)" or 1 if the point lies 
     * "beyond (XB,&nbsp;YB)".
     * @param XA,&nbsp;YA the coordinates of the beginning of the
     *		specified line segment
     * @param XB,&nbsp;YB the coordinates of the end of the specified
     *		line segment
     * @param PX,&nbsp;PY the coordinates of the specified point to be
     * 		compared with the specified line segment
     * @return an integer that indicates the position of the third specified
     *			coordinates with respect to the line segment formed
     *			by the first two specified coordinates.
     */
    public static int relativeCCW(double XA, double YA, double XB, double YB, double PX, double PY) {
	XB -= XA;
	YB -= YA;
	PX -= XA;
	PY -= YA;
	double ccw = PX * YB - PY * XB;
	if (ccw == 0.0) {
	    // The point is colinear, classify based on which side of
	    // the segment the point falls on.  We can calculate a
	    // relative value using the projection of PX,PY onto the
	    // segment - a negative value indicates the point projects
	    // outside of the segment in the direction of the particular
	    // endpoint used as the origin for the projection.
	    ccw = PX * XB + PY * YB;
	    if (ccw > 0.0) {
		// Reverse the projection to be relative to the original X2,Y2
		// X2 and Y2 are simply negated.
		// PX and PY need to have (XB - XA) or (YB - YA) subtracted
		//    from them (based on the original values)
		// Since we really want to get a positive answer when the
		//    point is "beyond (XB,YB)", then we want to calculate
		//    the inverse anyway - thus we leave XB & YB negated.
		PX -= XB;
		PY -= YB;
		ccw = PX * XB + PY * YB;
		if (ccw < 0.0) {
		    ccw = 0.0;
		}
	    }
	}
	return (ccw < 0.0) ? -1 : ((ccw > 0.0) ? 1 : 0);
    }

    /**
     * Returns an indicator of where the specified point 
     * (PX,&nbsp;PY) lies with respect to this line segment.
     * See the method comments of 
     * {@link #relativeCCW(double, double, double, double, double, double)}
     * to interpret the return value.
     * @param PX,&nbsp;PY the coordinates of the specified point
     *			to be compared with the current line segment
     * @return an integer that indicates the position of the specified
     *			coordinates with respect to the current line segment.
     * @see #relativeCCW(double, double, double, double, double, double)
     */
    public int relativeCCW(double PX, double PY) {
	     return relativeCCW(getXA(), getYA(), getXB(), getYB(), PX, PY);
    }

    /**
     * Returns an indicator of where the specified <code>Point</code>
     * lies with respect to this line segment.
     * See the method comments of
     * {@link #relativeCCW(double, double, double, double, double, double)}
     * to interpret the return value.
     * @param p the specified <code>Point</code> to be compared 
     *			with the current line segment
     * @return an integer that indicates the position of the 
     *			<code>Point</code> with respect to the current 
     *			line segment.
     * @see #relativeCCW(double, double, double, double, double, double)
     */
    public int relativeCCW(Point p) {
	   return relativeCCW(getXA(), getYA(), getXB(), getYB(), p.getX(), p.getY());
    }

    /**
     * Tests if the line segment from (XA,&nbsp;YA) to 
     * (XB,&nbsp;YB) intersects the line segment from (XC,&nbsp;YC) 
     * to (XD,&nbsp;YD).
     * @param XA,&nbsp;YA the coordinates of the beginning of the first 
     *			specified line segment
     * @param XB,&nbsp;YB the coordinates of the end of the first 
     *			specified line segment
     * @param XC,&nbsp;YC the coordinates of the beginning of the second
     *			 specified line segment
     * @param XD,&nbsp;YD the coordinates of the end of the second 
     *			specified line segment
     * @return <code>true</code> if the first specified line segment 
     *			and the second specified line segment intersect  
     *			each other; <code>false</code> otherwise.  
     */
    public static boolean linesIntersect(double XA, double YA, double XB, double YB,
					                     double XC, double YC, double XD, double YD) {
	return ((relativeCCW(XA, YA, XB, YB, XC, YC) *
		     relativeCCW(XA, YA, XB, YB, XD, YD) <= 0)
		     && (relativeCCW(XC, YC, XD, YD, XA, YA) *
		        relativeCCW(XC, YC, XD, YD, XB, YB) <= 0));
    }
    
    /**
     * Tests if the line segment from (XA,&nbsp;YB) to 
     * (XB,&nbsp;YB) intersects this line segment.
     * @param XA,&nbsp;YA the coordinates of the beginning of the 
     *			specified line segment
     * @param XB,&nbsp;YB the coordinates of the end of the specified
     *			line segment			
     * @return <true> if this line segment and the specified line segment
     *			intersect each other; <code>false</code> otherwise.
     */
    public boolean intersectsLine(double XA, double YA, double XB, double YB) {
	   return linesIntersect(XA, YA, XB, YB,getXA(), getYA(), getXB(), getYB());
    }

    /**
     * Tests if the specified line segment intersects this line segment.
     * @param l the specified <code>Line</code>
     * @return <code>true</code> if this line segment and the specified line
     *			segment intersect each other; 
     *			<code>false</code> otherwise.
     */
    public boolean intersectsLine(Line l) {
	  return linesIntersect(l.getXA(), l.getYA(), l.getXB(), l.getYB(),getXA(), getYA(), getXB(), getYB());
    }

}//end of class
