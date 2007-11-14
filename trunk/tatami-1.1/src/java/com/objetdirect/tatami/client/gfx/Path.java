package com.objetdirect.tatami.client.gfx;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * <p>Paths represent the outline of a shape which can be filled,
 * stroked, used as a clipping path, or any combination of the three. 
 * A path is described using the concept of a current point.
 * In an analogy with drawing on paper, the current point can be thought 
 * of as the location of the pen. The position of the pen can be changed, 
 * and the outline of a shape (open or closed) can be traced by dragging 
 * the pen in either straight lines or curves.
 * </p>
 * <p>
 * Paths represent the geometry of the outline of an object, 
 * defined in terms of these elements : 
 * <ul>
 *   <li><code>moveto</code>  (set a new current point).
 *   <li><code>lineto</code>  (draw a straight line).
 *   <li><code>curveto</code> (draw a curve using a cubic Bezier),
 *   <li><code>arcTo</code>   (elliptical or circular arc)
 *   <li><code>closepath</code> (close the current shape by drawing a line to the last moveto)
 * </ul>  
 * 
 * Compound paths (i.e., a path with multiple subpaths) are possible to 
 * allow effects such as "donut holes" in objects.
 * 
 * @author Vianney
 *
 */
public class Path extends GraphicObject {

	private boolean absoluteMode = true;
	
	/**
	 * Creates an empty Path DOJO GFX 
	 * @param surface the canvas
	 * @return the Path DOJO GFX object
	 */
	protected JavaScriptObject createGfx(JavaScriptObject surface) {
		return createPath(surface);
	}

	/**
	 * Sets the mode for the path.
	 * if <code>true</code>   : indicates that absolute coordinates will follow.
	 * if <code>false</code>  : indicates that relative coordinates will follow.
	 * @param mode the mode to use 
	 */
	public void setAbsoluteMode(boolean mode) {
		if (getShape() !=null) {
			this.absoluteMode = mode;
		    setAbsoluteMode(getShape(),mode);
		}
	}
	
	/*
	 * See the java method #setAbsoluteMode(boolean)
	 */
	native private void setAbsoluteMode(JavaScriptObject path,boolean mode)/*-{
	   path.setAbsoluteMode(mode);
	 }-*/;
	
	
	/**
	 * Returns if the path is in absolute mode or in relative mode.
	 * @return true if the path is in absolute, false otherwise
	 */
	public boolean getAbsoluteMode() {
		return this.absoluteMode;
	}
	
	
	/**
	 * Returns the last position in the path
	 * @return the last poistion in the path can be null
	 */
	public Point getLastPosition(){
		Point point = null;
		if ( getShape() != null) {
		  JavaScriptObject jsPoint =  getLastPosition(getShape());
		  if ( jsPoint != null) {
		      point = new Point();
			  point.setPoint(jsPoint);
		  } 
		} 
		return point;
	}
	
	/**
	 * Returns the last position in the path
	 * @param path the DOJO GFX Path
	 * @return the last poistion in the path or null
	 */
	native private JavaScriptObject getLastPosition(JavaScriptObject path)/*-{
	   return path.getLastPosition();
	}-*/;
	
	
	/**
	 * Creates an empty Path DOJO GFX object
	 * @param surface the canvas
	 * @return a Path
	 */
	private native JavaScriptObject createPath(JavaScriptObject surface) /*-{
		return surface.createPath();
	}-*/;
	
	
	

	/**
	 * Start a new sub-path at the given (x,y) coordinate. 
	 * If a relative moveto appears as the first element of the path,
	 * then it is treated as a pair of absolute coordinates.
	 * If a moveto is followed by multiple pairs of coordinates, the subsequent 
	 * pairs are treated as implicit lineto commands.
	 * @param x the x coordinate
	 * @param y the y coordinate
	 *
	 */
	public void moveTo(double x, double y) {
		if ( getShape()!=null) {
			moveTo(getShape(),x,y);
		}
	}
	
	
	/**
	 * Start a new sub-path at the given (x,y) coordinate. 
	 * If a relative moveto appears as the first element of the path,
	 * then it is treated as a pair of absolute coordinates.
	 * If a moveto is followed by multiple pairs of coordinates, the subsequent 
	 * pairs are treated as implicit lineto commands.
	 * @param point the point
	 */
	public void moveTo(Point point) {
		moveTo(point.getX(),point.getY());
	}
	
	
	/*
	 * see the Java method #moveTo(double,double)
	 */
	private native void moveTo(JavaScriptObject shape, double x, double y) /*-{
		shape.moveTo(x,y);
	}-*/;
	
	/**
	 *Draws a line from the current point to the given (x,y) 
	 *coordinate which becomes the new current point. 
	 *At the end of the command, the new current 
	 *point is set to the final set of coordinates provided.
	 *@param x the x coordinate
	 *@param y the y coordinate 
	 */
	public void lineTo(double x, double y) {
		if ( getShape()!=null) {
			lineTo(getShape(),x,y);
		}
	}
	
	/**
	 *Draws a line from the current point to the given (x,y) 
	 *coordinate which becomes the new current point. 
	 *At the end of the command, the new current 
	 *point is set to the final set of coordinates provided.
	 *@param point the Point  
	 */
	public void lineTo(Point point) {
		lineTo(point.getX(),point.getY());
	}
	
    /*
     * see the Java method #lineTo(double,double)
     */		
	native private void lineTo(JavaScriptObject path,double x, double y)/*-{
	   path.lineTo(x,y);
	}-*/; 
	
	
	/**
	 * Draws a horizontal line from the current point (cpx, cpy) to (x, cpy).
	 * At the end of the command, the new current point becomes (x, cpy) 
	 * for the final value of x.
	 * @param x an x coordinate
	 */
	public void hLineTo(double x) {
		if ( getShape()!=null) {
			hLineTo(getShape(),x);
		}
	}
	/*
	 * see the java method #hLineTo(double)
	 */
	private native void hLineTo(JavaScriptObject path,double x)/*-{
	   path.hLineTo(x);
	}-*/;
	
	/**
	 * Draws a vertical line from the current point (cpx, cpy) to (cpx, y). 
	 * At the end of the command, the new current point
	 * becomes (cpx, y) for the final value of y.
	 * @param y an y coordinate
	 */
	public void vLineTo(double y) {
		if ( getShape() != null) {
			vLineTo(getShape(),y);
		}
	}
	
	/*
	 * see the java method #vLineTo(double)
	 */
	private native void vLineTo(JavaScriptObject path,double y)/*-{
	   path.vLineTo(y);
	}-*/;
	
	
	/**
	 * Draws a cubic Bezier curve from the current point to (x,y)
	 * using (x1,y1) as the control point at the beginning of the curve 
	 * and (x2,y2) as the control point at the end of the curve.
	 * At the end of the command, the new current point becomes the final (x,y)
	 *  coordinate pair used in the polybezier.
	 * @param x1 the x coordinate of the controle point at the begining of the curve
	 * @param y1 the y coordinate of the controle point at the begining of the curve
	 * @param x2 the x coordinate of the controle point at the end of the curve
	 * @param y2 the y coordinate of the controle point at the end of the curve
	 * @param x the x coordinate of the point to curve
	 * @param y the y coordinate of the point to curve
	 */
	public void curveTo(double x1,double y1, double x2,double y2, double x, double y) {
		if ( getShape() != null) {
			curveTo(getShape(),x1,y1,x2,y2,x,y);
		}
	}
	
	/*
	 * see the java method #curveTo(double,double,double,double,double,double)
	 */
	private native void curveTo(JavaScriptObject path,double x1,double y1, double x2,double y2, double x, double y)/*-{
	   path.curveTo(x1,y1,x2,y2,x,y);
	}-*/;
	
	/**
	 * Draws a cubic Bezier curve from the current point to the point <code>point</code>(x,y)
	 * using <code>controlBegin</code> as the control point at the beginning of the curve 
	 * and <code>controlEnd</code> as the control point at the end of the curve.
	 * At the end of the command, the new current point becomes the final (x,y)
	 * coordinate pair used in the polybezier.
	 * @param controlBegin  the controle point at the begining of the curve
	 * @param controlEnd the controle point at the end of the curve
	 * @param point the point 
	 */
	public void curveTo(Point controlBegin,Point controlEnd, Point point) {
       curveTo(controlBegin.getX(),controlBegin.getY(),controlEnd.getX(),controlEnd.getY(),point.getX(),point.getY());		
	}
	
	
	/**
	 *Draws a cubic Bezier curve from the current point to (x,y).
	 *The first control point is assumed to be the reflection of the second control 
	 *point on the previous command relative to the current point. 
	 *(If there is no previous command or if the previous command was not an 
	 *{@link #curveTo(double,double,double,double,double,double}  or 
	 *{@link #smoothCurveTo(double,double,double,double} assume the first control point
	 * is coincident with the current point.) 
	 *(x2,y2) is the second control point (i.e., the control point at the end of the curve).
	 *At the end of the command, the new current point becomes the final (x,y) coordinate 
	 *pair used in the polybezier.
	 *@param x2 the x coordinate of the control point at the end of the curve.
	 *@param y2 the y coordinate of the control point at the end of the curve.
	 *@param x the x coordinate for the point
	 *@param y the y coordinate for the point
	 *
	 */ 
	public void smoothCurveTo(double x2,double y2, double x,double y) {
		if ( getShape() != null) {
			smoothCurveTo(getShape(),x2,y2,x,y);
		}
	}
	/*
	 * see the java method #smoothCurveTo(double,double,double,double)
	 */
	private native void smoothCurveTo(JavaScriptObject path,double x2,double y2, double x,double y)/*-{
	   path.smoothCurveTo(x2,y2,x,y);
	}-*/;
	
	
	/**
	 *Draws a cubic Bezier curve from the current point to <code>pt</code> (x,y).
	 *The first control point is assumed to be the reflection of the second control 
	 *point on the previous command relative to the current point. 
	 *(If there is no previous command or if the previous command was not an 
	 *{@link #curveTo(double,double,double,double,double,double}  or 
	 *{@link #smoothCurveTo(double,double,double,double} assume the first control point
	 * is coincident with the current point.) 
	 *<code>control</code> is the second control point (i.e., the control point at the end of the curve).
	 *At the end of the command, the new current point becomes the final (x,y) coordinate 
	 *pair used in the polybezier.
	 *@param control the control point 
	 *@param pt the point 
	 *
	 */ 
	public void smoothCurveTo(Point control,Point pt) {
		smoothCurveTo(control.getX(),control.getY(),pt.getX(),pt.getY());
	}
	
	
	
	/**
	 * Draws a quadratic Bezier curve from the current point to (x,y) 
	 * using (x1,y1) as the control point. At the end of the command, 
	 * the new current point becomes the final (x,y) coordinate pair used in the polybezier.
	 * @param x1 the x coordinate of the control point
	 * @param y1 the y coordinate of the control point
	 * @param x the x coordinate of the point 
	 * @param y the y coordinate of the point
	 */
	public void qCurveTo(double x1,double y1, double x, double y) {
		if ( getShape() != null) {
			qCurveTo(getShape(),x1,y1,x,y);
		}
	}
	
	/*
	 * see the java method #qCurveTo(double,double,double,double)
	 */
	private native void qCurveTo(JavaScriptObject path,double x1,double y1, double x, double y)/*-{
	   path.qCurveTo(x1,y1,x,y);
	}-*/;
	
	/**
	 * Draws a quadratic Bezier curve from the current point to the point <code>pt</code>(x,y) 
	 * using the <code>control</code> as the control point. At the end of the command, 
	 * @param control the control point
	 * @param pt the target point 
	 */
	public void qCurveTo(Point control,Point pt){
		qCurveTo(control.getX(),control.getY(),pt.getX(),pt.getY());
	}
	
	
	/**
	 * Draws a quadratic Bezier curve from the current point to (x,y). 
	 * The control point is assumed to be the reflection of the control point 
	 * on the previous command relative to the current point. 
	 * (If there is no previous command or if the previous command was not a
	 * {@link  #qCurveTo(double,double,double,double} or {@link #qSmoothCurveTo(double, double)}
	 *  assume the control point is coincident with the current point.)
	 * At the end of the command, the new current point becomes the final 
	 * (x,y) coordinate pair used in the polybezier.
	 * @param x the x coordinate of the point 
	 * @param y the y coordinate of the point 
	 */
	public void qSmoothCurveTo(double x,double y) {
		if ( getShape() != null) {
			qSmoothCurveTo(getShape(),x,y);
		}
	}
	/*
	 * See the java method #qSmoothCurveTo(double,double)
	 */
	private native void qSmoothCurveTo(JavaScriptObject path,double x,double y)/*-{
	    path.qSmoothCurveTo(x,y);
	}-*/;
	
	/**
	 * Draws a quadratic Bezier curve from the current point to the point <code>pt</code>(x,y). 
	 * The control point is assumed to be the reflection of the control point 
	 * on the previous command relative to the current point. 
	 * (If there is no previous command or if the previous command was not a
	 * {@link  #qCurveTo(double,double,double,double} or {@link #qSmoothCurveTo(double, double)}
	 *  assume the control point is coincident with the current point.)
	 * At the end of the command, the new current point becomes the final 
	 * (x,y) coordinate pair used in the polybezier.
	 * @param pt the target point
	 */
	public void qSmoothCurveTo(Point pt) {
		qSmoothCurveTo(pt.getX(),pt.getY());
	}
	
	/**
	 * Draws an elliptical arc from the current point to (x, y).
	 * The size and orientation of the ellipse are defined by 
	 * two radii (rx, ry) and an <code>xAxisRot</code>, 
	 * which indicates how the ellipse as a whole is rotated relative
	 * to the current coordinate system. 
	 * The center (cx, cy) of the ellipse is calculated automatically
	 * to satisfy the constraints imposed by the other parameters.
	 * <code>largeArcFlag</code> and <code>sweepFlag</code> contribute to the automatic calculations 
	 * and help determine how the arc is drawn.
	 * 
	 * For most situations, there are actually four different arcs 
	 * (two different ellipses, each with two different arc sweeps)
	 * that satisfy these constraints. <code>largeArcFlag</code>  and <code>sweepFlag</code> 
	 * indicate which one of the four arcs are drawn, as follows:
     * <ul>
     *  <li>
     *   Of the four candidate arc sweeps, two will represent an arc
     *   sweep of greater than or equal to 180 degrees, and two will represent 
     *   an arc sweep of less than or equal to 180 degrees (the "small-arc").
     *   If <code>largeArcFlag</code> is <code>true</code>, then one of the two larger 
     *   arc sweeps will be chosen; otherwise, if large-arc-flag is <code>false</code>, 
     *   one of the smaller arc sweeps will be chosen.
     * 
     * <li>
     *  If <code>sweepFlag</code> is <code>true</code>, then the arc will be drawn in a "positive-angle" 
     *  direction (i.e., the ellipse formula x=cx+rx*cos(theta) and y=cy+ry*sin(theta)
     *  is evaluated such that theta starts at an angle corresponding to the current
     *  point and increases positively until the arc reaches (x,y)).
     *  A value of <code>false</code> causes the arc to be drawn in a "negative-angle" 
     *  direction (i.e., theta starts at an angle value corresponding 
     *  to the current point and decreases until the arc reaches (x,y)).
     *</ul>
	 * <p>
	 * The following link illustrate the four combinations of
	 * <code>largeArcFlag</code> and <code>sweepFlag</code>  and the four 
	 * different arcs that will be drawn based on the values of these flags. 
	 * For each case, the following path parameter was used:
	 *  <p>
	 *  <code>rx=100,ry=50 xAxisRot =0 largeArcFlag=? ,sweepFlag=? x=100 y=50</code>
	 *  </p>
	 *  <a href="http://www.w3.org/TR/SVG/images/paths/arcs02.svg">
	 *    View this example as SVG (SVG-enabled browsers only)
	 *  </a>
	 * </p>
	 * @param rx the radius for the X axis 
	 * @param ry the radius for the Y axis
	 * @param xAxisRot an angle in degree
	 * @param largeArcFlag true to set the flag
	 * @param sweepFlag true to set the flag 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 *
	 */
	public void arcTo(int rx, int ry,int xAxisRot,boolean largeArcFlag, boolean sweepFlag, double x, double y) {
		if ( getShape() != null) {
			arcTo(getShape(),rx,ry,xAxisRot,largeArcFlag,sweepFlag,x,y);
		}
	}
	/*
	 * see the java method #arcTo(int,int,double,boolean,boolean,double,double)
	 */
	native private void arcTo(JavaScriptObject path,int rx, int ry,double xAxisRot,boolean largeArcFlag, boolean sweepFlag, double x, double y )/*-{
	   path.arcTo(rx,ry,xAxisRot,largeArcFlag,sweepFlag,x,y);
	}-*/; 
	
	/**
	 * Draws an elliptical arc from the current point to the point <code>pt</code>(x, y).
	 * The size and orientation of the ellipse are defined by 
	 * two radii (rx, ry) and an <code>xAxisRot</code>, 
	 * which indicates how the ellipse as a whole is rotated relative
	 * to the current coordinate system. 
	 * The center (cx, cy) of the ellipse is calculated automatically
	 * to satisfy the constraints imposed by the other parameters.
	 * <code>largeArcFlag</code> and <code>sweepFlag</code> contribute to the automatic calculations 
	 * and help determine how the arc is drawn.
	 * @param rx the radius for the X axis 
	 * @param ry the radius for the Y axis
	 * @param xAxisRot  an angle in degree
	 * @param largeArcFlag true to set the flag
	 * @param sweepFlag true to set the flag 
	 * @param pt  the target point
	 */
	public void arcTo(int rx,int ry,int xAxisRot,boolean largeArcFlag, boolean sweepFlag,Point pt) {
		arcTo(rx,ry,xAxisRot,largeArcFlag,sweepFlag,pt.getX(),pt.getY());
	}
	/**
	 * Close the current subpath by drawing a straight
	 * line from the current point to current subpath's initial point.
	 */
	public void closePath() {
		if ( getShape()!= null) {
		  closePath(getShape());
		}
	}
	
    /*
     * see the java method #closePath()
     */
	native private void  closePath(JavaScriptObject path) /*-{
	  path.closePath();
	}-*/; 
	
	
	
	
}//end of class
