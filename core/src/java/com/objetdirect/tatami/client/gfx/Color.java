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
 * Authors: Henri Darmet, Vianney Grassaud
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client.gfx;

import com.google.gwt.core.client.JavaScriptObject;


/**
 * Represents a color
 * @author Henri, Vianney
 *
 */
public class Color {
    
	/**
	 * The red component 
	 */
	private int red;
	/**
	 * The green component 
	 */
	private int green;
	/**
	 * The blue component 
	 */
	private int blue;
	/**
	 * The alpha component 
	 */
	private int alpha;
	
	/**
	 * Creates an sRGB color with the specified red, green, blue, and alpha values in the range (0 - 255).
	 * @param red the red component
	 * @param green the green component
	 * @param blue the blue component
	 * @param alpha the alpha component
	 * @throws IllegalArgumentException if red, green, blue or alpha are outside of the range 0 to 255, inclusive
	 */
	public Color(int red, int green, int blue, int alpha) {
		//DojoController.getInstance().require("dojo.colors");
		setRed(red);
		setGreen(green);
		setBlue(blue);
		setAlpha(alpha);
	}

	/**
	 * Returns the red component of the color
	 * @return the red component an integer in the range 0-255 inclusive
	 */
	public int getRed() {
		return red;
	}
	
	/**
	 * Tests if the givent component is in the range 0-255, inclusive
	 * @param component the component to test
	 * @return true if the component is valid false otherwise
	 * @throws IllegalArgumentException if the component is not valid
	 */
	private boolean isValidComponent(int component) {
		final boolean invalid = (component < 0 || component >= 256);
		if ( invalid) {
			throw new IllegalArgumentException("Component must be between in 0-255 inclusive");
		} 
		return !invalid;
	}
	
	/**
	 * Sets the red component of the Color
	 * @param red the new red component in range 0-255
	 * @throws IllegalArgumentException if red is outside
	 *         of the range 0 to 255, inclusive
	 */
	final public void setRed(int red) {
		if ( isValidComponent(red))  {
		  this.red = red;
		}
	}
	
	
	/**
	 * Returns the green component of the color
	 * @return the green component of the color, 
	 *         an integer in range 0-255
	 */
	public int getGreen() {
		return green;
	}
	
	/**
	 * Sets the green component of the Color
	 * @param green the green component in range 0-255
     * @throws IllegalArgumentException if green is outside 
     *         of the range 0 to 255, inclusive
	 */
	final public void setGreen(int green) {
		if ( isValidComponent(green))  {
		   this.green = green;
		}
	}

	/**
	 * Returns the blue component of the color
	 * @return the blue component of the color,
	 *         an integer in range 0-255
	 */
	public int getBlue() {
		return blue;
	}
	
	
	
	public boolean equals(Object obj) {
		boolean result = false;
		if ( obj instanceof Color) {
			Color color = (Color)obj;
			result = color.getAlpha() == getAlpha() && 
			         color.getBlue() == getBlue() && 
			         color.getGreen() == getGreen() &&
			         color.getRed() == getRed();
		} else {
			result= toString().equals(obj.toString());
		}
		return result;
	}
	
	
	/**
	 * Sets the blue component of the Color
	 * @param blue the green component in range 0-255
	 * @throws IllegalArgumentException if blue is outside 
	 *         of the range 0 to 255, inclusive
	 */
	final public void setBlue(int blue) {
		if ( isValidComponent(blue))  {
			   this.blue = blue;
			}
	}

	/**
	 * Returns the alpha component
	 * @return the alpha component, an integer between 0-255, inclusive
	 */
	public int getAlpha() {
		return alpha;
	}
	
	/**
	 * Sets the alpha component of the Color
	 * @param alpha the green component in range 0-255
	 * @throws IllegalArgumentException if alpha is outside 
	 *         of the range 0 to 255, inclusive
	 */
	final public void setAlpha(int alpha) {
		if ( isValidComponent(alpha))  {
		   this.alpha = alpha;
		}
		
	}

	/**
	 * Returns the DOJO Color corresponding to this object
	 * @return the DOJO color object
	 */
	protected JavaScriptObject getDojoColor() {
		return createColor(red, green, blue, alpha/255.0f);
	}
	
	/**
	 * Creates the DOJO color Object
	 * @param red the red component
	 * @param green the green component 
	 * @param blue the blue component
	 * @param alpha the alpha component
	 * @return the Color JavaScriptObject
	 */
	private  native JavaScriptObject createColor(int red, int green, int blue, float alpha) /*-{
	   	return  $wnd.dojo.colorFromArray([red,green,blue,alpha]);
	}-*/;
	

	/**
	 * Returns a css color string in hexadecimal representation
	 * @return a css color string in hexadecimal representation
	 */
	public String toHex() {
		return toHex(getDojoColor());
	}
	
	/**
	 * Returns the css color string in rgb(a) representation
	 * @param includeAlpha include or not the alpha component. 
	 * @return  the css color string in rgb(a) representation
	 */
	public String toCss(boolean includeAlpha) {
		return toCss(getDojoColor(),includeAlpha);
	}
	
	/**
	 *Returns a visual representation of the color
	 *calls the {@link #toCss()} method
	 */
	public String toString() {
		return toCss(true);
	}
	
	
	/**
	 * Returns a css color string in hexadecimal representation
	 * @param color the DOJO color
	 * @return a css color string in hexadecimal representation
	 */
	private static native String toHex(JavaScriptObject color)/*-{
	  return color.toHex();
	}-*/;
	
	/**
	 * Returns the css color string in rgb(a) representation
	 * @param color the DOJO color
	 * @param includeAlpha include or not the alpha component. 
	 * @return  the css color string in rgb(a) representation
	 */
	private static native String toCss(JavaScriptObject color,boolean includeAlpha)/*-{
	  return color.toCss(includeAlpha);
	}-*/;
	
	/**
	 * converts a hex string with a '#' prefix to a color object.
	 * @param hex the hexadecimal string 
	 * @return the color 
	 */
	public static Color getColor(String hex) {
		final JavaScriptObject color = getColorFromHex(hex);
		return new Color(getRed(color),getGreen(color),getBlue(color),255);
	}
	
	/**
	 * converts a hex string with a '#' prefix to a color object.
	 * @param hex the hexadecimal string 
	 * @return the color 
	 */
	private static native JavaScriptObject getColorFromHex(String hex) /*-{
	   return $wnd.dojo.colorFromHex(hex);
	}-*/;

	/**
	 * Returns the red component of the color
	 * @param color the JavaScriptColor
	 * @return the red component
	 */
	
	private static native int getRed(JavaScriptObject color) /*-{
	   return color.r;
	}-*/;
	
	/**
	 * Returns the green component of the color
	 * @param color the JavaScriptColor
	 * @return the green component
	 */
	
	private static native int getGreen(JavaScriptObject color) /*-{
	   return color.g;
	}-*/;
	
	/**
	 * Returns the blue component of the color
	 * @param color the JavaScriptColor
	 * @return the blue component
	 */
	private static native int getBlue(JavaScriptObject color) /*-{
	   return color.b;
	}-*/;
	
	/**
	 * Reverses the color with the same alpha
	 * @return the reverse color. 
	 */
	public Color reverse() {
		return new Color(255-getRed(),255-getGreen(),255-getBlue(),getAlpha());
	}
	
	
	/**
	 * The balck color in RGB
	 */
	static public  final Color BLACK   = new Color(0,0,0,255); 
	/**
	 * The silver color inRGB
	 */
	static public  final Color SILVER  = new Color(192,192,192,255);
	/**
	 * The gray color in RGB
	 */
	static public  final Color GRAY    = new Color(128,128,128,255);
	/**
	 * The white color in RGB
	 */
	static public  final Color WHITE   = new Color(255,255,255,255);
	/**
	 * The maroon color in RGB
	 */
	static public  final Color MAROON  = new Color(128,0,0,255);
	/**
	 * The red color in RGB
	 */
	static public  final Color RED     = new Color(255,0,0,255);
	/**
	 * The purpble color in RGB
	 */
	static public  final Color PURPLE  = new Color(128,0,128,255);
	/**
	 * The fuchsia color in RGB
	 */
	static public  final Color FUCHSIA = new Color(255,0,255,255);
	/**
	 * The green color in RGB
	 */
	static public  final Color GREEN   = new Color(0,128,0,255);
	/**
	 * The lime color in RGB
	 */
	static public  final Color LIME    = new Color(0,255,0,255);
	/**
	 * The olive color in RGB
	 */
	static public  final Color OLIVE   = new Color(128,128,0,255);
	/**
	 * The yellow color in RGB
	 */
	static public  final Color YELLOW  = new Color(255,255,0,255);
	/**
	 * The navy color in RGB
	 */
	static public  final Color NAVY    = new Color(0,0,128,255);
	/**
	 * The blue color in RGB
	 */
	static public  final Color BLUE    = new Color(0,0,255,255);
	/**
	 * The teal color in RGB
	 */
	static public  final Color TEAL    = new Color(0,128,128,255);
	/**
	 * The aqua color in RGB
	 */
	static public  final Color AQUA    = new Color(0,255,255,255);
	
	
	
	
	
}//end of class
