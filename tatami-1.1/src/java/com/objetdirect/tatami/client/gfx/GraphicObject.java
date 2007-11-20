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
package com.objetdirect.tatami.client.gfx;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * <p>This is the abstract class to represent a shape object in the gfx package.
 * A shape is added to a <code>GraphicalCanvas</code>, and this canvas can show or hide its components.</p>
 * <p>
 * Some basics transformation can be done on a <code>GraphicalObject</code> like 
 * translation ,scaling, rotation. 
 * </p>
 * <p>A shape has a stroke and a fill which can be set by the user : 
 *     <ul>
 *     
 *        <li>to have a stroke width of 2px and with a red color: <br><br> 
 *        <code>graphicalObject.setStroke(2,Color.RED);</code><br><br>
 *        <li> to set the color of the fill : <br><br>
 *        <code>graphicalObject.setFillColor(Color.BLACK);<code><br><br>
 *        <li>to use a pattern to fill the shape : <br><br> 
 *       <code> graphicalObject.applyPattern(pattern);</code><br><br>
 *     
 *     </ul>
 * </p>
 * <p>
 * The center location is set by the center of the bounds of the <code>GraphicObject</code>. The center is used for the rotate tranformation. 
 * So it's important to override the <b>initCenterLocation</b>() method. 
 * if you need to set a specific center for the <code>GraphicObject</code><br>
 * The <b>createGfx</b>(JavaScriptObject surface) is the method which creates the corresponding shape, example : 
 * circle, rectangle, ellipse, etc...
 *  
 * </p>  
 *  
 * @author Henri, Vianney
 *
 */
public abstract class GraphicObject {
	
	/**
	 * The position of this <code>GraphicalObject</code>
	 */
	private Point position;
	
	/**
	 * The bounds of this <code>GraphicObject</code>
	 */
    private Rectangle bounds;	
	
    /**
     * The center of this <code>GraphicObject</code>
     */
    private Point center;
	
	/**
	 * The shape of this Graphical object
	 */
	private JavaScriptObject shape;
	
	/** The matrix	 */
	private JavaScriptObject matrix;
	
	/** The pattern to use */
	private Pattern pattern =null;
	
	
	/** Color for the background */
	private Color fillColor = Color.BLACK;
	
	/** color of the stroke */
	private Color strokeColor = Color.BLACK;
	
	/** the width of the stroke	 */
	private int strokeWidth = 1;
			
	
	private GraphicCanvas parent;
	
	/**
	 *The default constructor for a <code>GraphicObject</code> 
	 *
	 */
	protected GraphicObject() {
		position = new Point();
		center   = new Point();
		bounds = new Rectangle();
	}
	
	/**
	 * Returns the shape of this object
	 * @return the shape of this object
	 */
	protected JavaScriptObject getShape() {
		return this.shape;
	}
	
	/**
	 * Returns the <code>GraphicCanvas</code> which is the parent of this
	 * <code>GraphicObject</code>
	 * @return the <code>GraphicCanvas</code> containing this <code>GraphicObject</code>
	 */
	protected GraphicCanvas getParent() {
		return this.parent;
	}
	
	
	/**
	 *Returns all the shapes of this <code>GraphicObject</code>
	 *This is used for a <code>VirtualGroup</code> for example
	 *@return a Collection of <code>JavaScriptObject</code> corresponding to the different shapes
	 */
	protected Collection getShapes() {
		List list = new ArrayList();
		list.add(shape);
		return list;
	}
	
	/**
	 * Returns the X Position 
	 * @return the X position
	 */
	public double getX() {
		return position.getX();
	}
	
	/**
	 * Returns the position of this GraphicalObject
	 * @return the position of this GraphicalObject
	 */
	public Point getLocation() {
		return this.position;
	}
	
	/**
	 * Inits the center location of this <code>GraphicObject</code>
	 * By default the center is the the center of the bounds of the <code>GraphicObject</code>.
	 * Note that this method is called after that the DOJO GFX shape was created. Otherwise the bounds 
	 * are a <code>Rectangle</code> which is empty.
	 * You can override this method if you want to specify an other center location than the center of the bounds.
	 * @see #show(JavaScriptObject)
	 */
	protected void initCenterLocation() {
		this.center = getBounds().getCenter();
	}
	
	/**
	 * Returns the center position
	 * @return the center position
	 */
	public Point getCenter() {
		return this.center;
	}
	
	/**
	 * Returns the width of the stroke
	 * @return  the width of the stroke
	 */
	public int getStrokeWidth() {
		return this.strokeWidth;
	}
	
	/**
	 * Returns the color of the stroke
	 * @return the color of the stroke
	 */
	public Color getStrokeColor() {
		return this.strokeColor;
	}
	
	/**
	 * Return the color of the fill
	 * @return the color of the fill
	 */
	public Color getFillColor() {
		return this.fillColor;
	}
	/**
	 * Returns the Y Position 
	 * @return the Y position
	 */
	public double getY() {
		return position.getY();
	}
	
	

	/**
	 * Returns the x coordinate of the center location
	 * @return the x coordinate of the center Location
	 */
	public double getCenterX() {
        return center.getX();

	}
	
	/**
	 * Returns the y coordinate of the center Location
	 * @return the y coordinate of the center Location
	 */
	public double getCenterY() {
		return center.getY();
	}



	/**
	 * Fills the background with the given color
	 * @param color the Color to use
	 * @return the <code>GraphicObject</code> itself
	 */
	public GraphicObject setFillColor(Color color) {
		fillColor = color;
		if (shape!=null) {
			configureFill();
		}
		return this;
	}
	
	/**
	 * Sets the width and the color of the stroke. 
	 * @param color the color of the stroke
	 * @param width the width of the stroke
	 * @return the <code>GraphicObject</code> itself
	 */
	public GraphicObject setStroke(Color color, int width) {
		strokeColor = color;
		strokeWidth = width;
		if (shape!=null) {
			configureStroke();
		}
		return this;
	}
	
	/**
	 * Sets the color of the stroke of this <code>GraphicObject</code>
	 * @param color the color of the stroke to apply
	 * @return the <code>GraphicObject</code> itsefl
	 */
	public GraphicObject setStrokeColor(Color color) {
		 return setStroke(color,getStrokeWidth());
	}

	/**
	 * Sets the width of the stroke of this <code>GraphicObject</code>
	 * @param width the new width. 
	 * @return the <code>GraphicObject</code> itsefl
	 */
	public GraphicObject setStrokeWidth(int width) {
		 return setStroke(getStrokeColor(),width);
	}
	
	/**
	 * Does a translation of this object
	 * @param xLag a lag for the  x coordinate value
	 * @param yLag a lag for the  y coordinate value
	 * @return the <code>GraphicObject</code> itself
	 */
	public GraphicObject translate(final int xLag, final int yLag) {
		if ( xLag!=0 || yLag!=0) {
			final JavaScriptObject matrixTranslated = getTranslationMatrix( xLag, yLag);
			position.translate(xLag,yLag);
			center.translate(xLag, yLag);
			bounds.translate(xLag,yLag);
			applyModification(matrixTranslated);
			setBounds();
		}
		
		return this;
	}

	/**
	 * Applies some modification to the matrix.
	 * if the matrix is already defined then, the methode multiply the 
	 * given matrix with the matix of this <code>GraphicOject</code>.
	 * Also if shape is null, the shape will be transformed.
	 * @param newMatrix the new matrix
	 */
	private void applyModification( JavaScriptObject newMatrix) {
		if (matrix==null) {
			matrix = newMatrix;
		} else {
			matrix = multiplyMatrix(newMatrix, matrix);
		}
		if (shape!=null) {
			applyTransform(shape, newMatrix);
		}
	}
	
	/**
	 * Rotates a picture using a specified point as a center of rotation
	 * @param angle an angle of rotation in radians (>0 for CW)
	 * @return the <code>GraphicObject</code> itself
	 */
	public GraphicObject rotate(float angle) {
		if (angle!=0) {
			final JavaScriptObject matrixRotated = getRotationMatrix(angle, getCenterX(), getCenterY());
			position.rotate(angle,getCenter());
			//it seem that this method is not perfect yet
			bounds.rotate(angle);
			applyModification(matrixRotated);
			setBounds();
		}
		return this;
	}

	/**
	 * Scales a picture using a specified point as a center of scaling
	 * @param factorX  a scaling factor for the X coordinate
	 * @param factorY  a scaling factor for the Y coordinate
	 * @return the <code>GraphicObject</code> itself
	 */
	public GraphicObject scale(float factorX,float factorY) {
		if (factorX!=1.0 || factorY != 1.0) {
			final JavaScriptObject matrixScaled = getScalingMatrix(factorX,factorY, getX(), getY());
			if ( !position.equals(center)) {
	    	    this.bounds.setRect(bounds.getX(), bounds.getY(), bounds.getWidth()*factorX, bounds.getHeight()*factorY);
	    	    center.setLocation(bounds.getCenter());
 			   
			} else { //the center is the position of the object
                final double newWidth =  bounds.getWidth() * factorX;
                final double newHeight = bounds.getHeight() * factorY;
				final double newX = center.getX() - (newWidth/2);
                final double newY = center.getY() - (newHeight/2);
				bounds.setRect(newX, newY, newWidth,newHeight);
			}
     		applyModification(matrixScaled);
			setBounds();
		}
		return this;
	}

	/**
	 * Scales a picture using a specified point as a center of scaling
	 * @param factor
	 * @return the <code>GraphicObject</code> itself
	 */
	public GraphicObject scale(float factor) {
		return scale(factor,factor);
	}
	
	/**
	 * Shows this <code>GraphicalObject</code> in the canvas.
	 * @param surface the canvas
	 */
	protected void show(GraphicCanvas surface) {
		this.parent = surface;
		shape = createGfx(surface.getDojoCanvas());
		configureShape();
		
		
	}
	
	/**
	 * Hides this <code>GraphicalObject</code> in the canvas.
	 * Deletes the shape of this  <code>GraphicalObject</code>
	 */
	protected void hide() {
		deleteGfx(shape);
		shape = null;
	}
	
	
	private native JavaScriptObject getTransformedBound(JavaScriptObject shape)/*-{
	      	return shape.getTransformedBoundingBox();
	}-*/;
	
	private void setBounds() {
		if (shape != null) {
			final JavaScriptObject points =getTransformedBound(getShape()); 
			if (points!=null ) {
			   bounds.setRectFromPoints(points);
			}
		}
	}
	
	/**
	 * Configures the shape. 
	 * @see #show(JavaScriptObject)
	 */
	private void configureShape() {
		JavaScriptObject jsRect = getBounds(shape);
		if ( jsRect != null) {
			final double dx = bounds.getX();
			final double dy = bounds.getY(); 
			bounds.setRect(jsRect);
			bounds.translate(dx, dy);
		}
		initCenterLocation();
		configureFill();
		configureStroke();
		configureTransform();
	}

	/**
	 * Configures the fill of this object
	 *
	 */
	private void configureFill() {
		configureFill(shape, fillColor.getDojoColor());
	}
	
	/**
	 * Sets a fill object
	 * @param shape the DOJO shape object
	 * @param fillColor the color to use
	 */
	private static native void configureFill(JavaScriptObject shape,JavaScriptObject fillColor) /*-{
		shape.setFill(fillColor);
	}-*/;
	
	/**
	 *Configures the stroke of this object 
	 *
	 */
	private void configureStroke() {
		configureStroke(shape, strokeColor.getDojoColor(), strokeWidth);
	}
	

	/**
	 * Sets a stroke object
	 * @param shape the shape DOJO object
	 * @param strokeColor the color for the stroke
	 * @param strokeWidth the width for the stroke
	 */
	private static native void configureStroke(JavaScriptObject shape,JavaScriptObject strokeColor,	int strokeWidth) /*-{
		shape.setStroke({color: strokeColor, width: strokeWidth});
	}-*/;

	/**
	 * Configures a Transform operation
	 * @see #applyTransform(JavaScriptObject, JavaScriptObject)
	 */
	private void configureTransform() {
		if (matrix!=null) {
			applyTransform(shape, matrix);
			setBounds();
		}
	}
	
	/**
	 * Creates the DOJO GFX shape of this <code>GraphicalObject</code>
	 * @param surface the canvas
	 * @return the DOJO GFX shape
	 */
	abstract protected JavaScriptObject createGfx(JavaScriptObject surface);
	

	/**
	 * Removes the shape of this <code>GraphicalObject</code> from the canvas.
	 * @param shape the shape to remove
	 */
	protected native void deleteGfx(JavaScriptObject shape) /*-{
		shape.removeShape(true);
	}-*/;
	
	/**
	 * Moves a shape to front of its parent's list of shapes
	 */
	public void moveToFront() {
		if ( shape != null) {
		  shape =moveToFront(shape); 
		}
	}

	/**
	 * Moves a shape to back of its parent's list of shapes
	 */
	public void moveToBack() {
		if ( shape != null) {
		  shape =moveToBack(shape); 
		}
	}
	
	/**
	 *Moves a shape to front of its parent's list of shapes
	 *@param shape 
	 *@return the shape
	 */
	native private JavaScriptObject moveToFront(JavaScriptObject shape)/*-{
	   return shape.moveToFront();
	}-*/; 
	
	
	/**
	 *Moves a shape to back of its parent's list of shapes
	 *@param shape 
	 *@return the shape
	 */
	native private JavaScriptObject moveToBack(JavaScriptObject shape)/*-{
	   return shape.moveToBack();
	}-*/; 
	
	/**
	 * Applies a pattern to this object 
	 * @param pattern the pattern to apply.
	 */
	public GraphicObject applyPattern(Pattern pattern) {
      		this.pattern = pattern;
      		if ( pattern != null && shape != null) {
      			configureFill(getShape(), pattern.getGFXPattern());
      		}
      		return this;
	}
	
	/**
	 * Returns the pattern
	 * @return the pattern used to fill the shape
	 */
	public Pattern getPattern() {
		return pattern;
	}
	
	
//	/**
//	 * Sets the location of this <code>GraphicalObject</code>
//	 * @param xCoord the x coordinate 
//	 * @param yCoord the y coordinate
//	 */
//	protected void setLocation(double xCoord, double yCoord) {
//		position.setLocation(xCoord, yCoord);
//    }
	
//	/**
//	 * Sets the location of this <code>GraphicalObject</code>
//	 * @param newPosition a new position 
//	 */
//	protected void setLocation(Point newPosition) {
//		position.setLocation(newPosition);
//    }
//	
//	
	/**
	 * Multiplies 2 matrix and return the matrix resulting 
	 * @param mx1 a matrix
	 * @param mx2 b matrix
	 * @return the new matrix
	 */
	private static native JavaScriptObject multiplyMatrix(JavaScriptObject mx1, JavaScriptObject mx2) /*-{
		return $wnd.dojox.gfx.matrix.multiply(mx1, mx2);
	}-*/;
	
	/**
	 * Makes a translation of a matrix
	 * @param xLag the lag for x coordinate
	 * @param yLag the lag for the y coordinate
	 * @return the matrix translated
	 */
	private static native JavaScriptObject getTranslationMatrix(int xLag, int yLag) /*-{
		return $wnd.dojox.gfx.matrix.translate(xLag, yLag);
	}-*/;

	/**
	 * Makes a rotation of the matrix of this <code>GraphicalObject</code>
	 * @param angle the angle of the rotation in degree
	 * @param centerX the x coordinate of the rotation
	 * @param centerY the y coordinate of the rotation
	 * @return the new matrix
	 */
	private static native JavaScriptObject getRotationMatrix(float angle, double centerX, double centerY) /*-{
		return $wnd.dojox.gfx.matrix.rotategAt(angle, centerX, centerY);
	}-*/;
	
	/**
	 * Scales the matrix of this <code>GraphicalObject</code>
	 * @param factorX the scaling factor for the X 
	 * @param factory the sclaing factor for the Y
	 * @param centerX the x coordinate of the center of this <code>GraphicalObject</code>
	 * @param centerY the y coordinate of the center of this <code>GraphicalObject</code>
	 * @return the matrix
	 */
	private static native JavaScriptObject getScalingMatrix(float factorX,float factorY, double centerX, double centerY) /*-{
		return $wnd.dojox.gfx.matrix.scaleAt(factorX,factorY, centerX, centerY);
	}-*/;

	/**
	 * Apply left transformation from a matrix
	 * @param shape the shape
	 * @param matrix the matrix transformed
	 */
	private static native void applyTransform(JavaScriptObject shape, JavaScriptObject matrix) /*-{
		shape.applyLeftTransform(matrix);
	}-*/;
	
	/**
	 * Rreturns the transformed bounding box or null
	 * @param shape the shape of this <code>GraphicObject</code>
	 * @return an array of four DOJO GFX point or null
	 */
	private native JavaScriptObject getBounds(JavaScriptObject shape)/*-{
	   return shape.getBoundingBox();
	}-*/;
	
	/**
	 * Returns the bounding box of this <code>GraphicObject</code>
	 * If the shape if not constructed return an empty <code>Rectangle</code>
	 * @return the bounding box of this <code>GraphicObject</code>
	 */
	public Rectangle getBounds() {
		return this.bounds;
	}

	
	
	
	
	 /**
	  * Experimental
	  * @param shape
	  */
	protected void setShape(JavaScriptObject shape) {
		this.shape = shape;
	}
	
	
	/**
	 * returns the source of the event of the graphicObject
	 * @param graphicObject the graphicObject
	 * @return the source of the event
	 */
	protected static native JavaScriptObject getEventSource(JavaScriptObject graphicObject) /*-{
		return graphicObject.getEventSource();
	}-*/;
}//end of class
