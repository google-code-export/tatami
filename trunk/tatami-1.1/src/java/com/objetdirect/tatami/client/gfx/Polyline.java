package com.objetdirect.tatami.client.gfx;

import com.google.gwt.core.client.JavaScriptObject;

public class Polyline extends GraphicObject {

	private Point[] points;
	
	
	public Polyline(Point[] points) {
		super();
		this.points = points; 
	}
	
	public Point[] getPoints() {
		return this.points;
	}
	
	
	private JavaScriptObject createArray(Point[] points) {
		JavaScriptObject jsArray = JavaScriptObject.createArray();
		for ( int i=0; i < points.length; i++) {
			jsArray = put(jsArray,points[i].getGFXPoint(),i);
		}
		return jsArray;
	}
	
	
	private native JavaScriptObject put(JavaScriptObject array, JavaScriptObject point, int index)/*-{
	    array[index] = point;
	    return array;
	}-*/;
	
	
	protected JavaScriptObject createGfx(JavaScriptObject surface) {
		final JavaScriptObject arrayPoints = createArray(points);
		return createPolyline(surface,arrayPoints);
	}
	
	private native JavaScriptObject createPolyline(JavaScriptObject surface,JavaScriptObject points) /*-{
	    return surface.createPolyline({points:points});
	}-*/;
	
	

}
