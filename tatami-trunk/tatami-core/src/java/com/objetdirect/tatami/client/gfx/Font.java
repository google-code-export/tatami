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

import com.google.gwt.core.client.JavaScriptObject;
/**
 * Represent a Font for text object
 * TODO fix this class : 
 *  
 */
public class Font {

	/** 
	 * use for variant attribute : 
	 * In a small-caps font the lower case 
	 * letters look similar to the uppercase ones, 
	 * but in a smaller size and with slightly different proportions.
	 **/
	public static final String SMALL_CAPS = "small-caps";
	
	/**
	 * use for variant, style, weight attributes
	 */
	public static final String NORMAL = "normal";

	/**use for the weight attribute */
	public static final String BOLD = "bold";
	
	/**use for the weight attribute */
	public static final String BOLDER = "bolder";
	
	/**use for the weight attribute */
	public static final String LIGHTER = "lighter";
	
	/** use for style */	
	public static final String OBLIQUE = "oblique";
	
	/** use for style */
	public static final String ITALIC = "italic";
	
	
	/** the family of the font */
	private String family;
    /**
     * the style of the font 
     */
	private String style;
    /** the size of the font */
	private int size;
    /** the variant of the font */
	private String variant;
    /** the weight of the font */
	private String weight;

	/** the default font used (serif 10)*/
	public static final Font DEFAULT_FONT = new Font("Arial",10,NORMAL,NORMAL,NORMAL);
/**
 * Creates a Font object for text object
 * @param family the family to use
 * @param size the size of the font 
 * @param style the style to use : <code>NORMAL,OBLIQUE,ITALIC  </code>
 * @param variant the varient to use : <code>NORMAL,SMALL_CAPS</code>
 * @param weight the wieght to use : <code>	NORMAL,BOLD,BOLDER,LIGHTER ,100,200,300,400,500,600,700,800,900</code>
 */
public Font(String family,int size,String style,String variant,String weight) {
   this.family = family;    	
   this.size = size;
   this.style = style;
   this.variant = variant;
   this.weight = weight;
}


/**
 * Creates a new Font from a given font.
 * @param font the font to copy
 */
public  Font(Font font) {
	this(font.getFamily(),
		font.getSize(),
 		font.getStyle(),
			font.getVariant(),
			font.getWeight()
);
}


/**
 * Returns the family of the font
 * @return the family of the font
 */
public String getFamily() {
	return family;
}

/**
 * Sets the family of the font
 * @param family a family
 */
public void setFamily(String family) {
	this.family = family;
}

/**
 * Returns the size of the font 
 * @return the size of the font
 */
public int getSize() {
	return size;
}

/**
 * Sets the size of the font in pt
 * @param size size of the font
 */
public void setSize(int size) {
	this.size = size;
}

/**
 * Returns the style used by the font
 * @return style used by the font
 */
public String getStyle() {
	return style;
}


/**
 * sets the style for the font 
 * @param style <code>NORMAL,OBLIQUE,ITALIC  </code>
 */
public void setStyle(String style) {
	this.style = style;
}

/**
 * Returns the variant of the font 
 * @return the variant of the font
 */
public String getVariant() {
	return variant;
}

/**
 * Sets the variant of the font.In a small-caps font the 
 * lower case letters look similar to the uppercase ones,
 * but in a smaller size and with slightly different proportions.
 * @param variant  <code>NORMAL,SMALL_CAPS</code>
 */
public void setVariant(String variant) {
	this.variant = variant;
}

/**
 * Returns the weight of the font 
 * @return the weight of the font
 */
public String getWeight() {
	return weight;
}

/**
 * Sets the weight of the font
 * @param weight <code>	NORMAL,BOLD,BOLDER,LIGHTER ,100,200,300,400,500,600,700,800,900</code>
 */
public void setWeight(String weight) {
	this.weight = weight;
}

/**
 * Creates the DOJO GFX font from the JAVA font
 * @return the DOJO GFX font
 */
protected JavaScriptObject createFont() {
	return createFont(getFamily(),getSize()+"pt",getStyle(),getVariant(),getWeight());
}

/**
 * Creates the DOJO GFX font from the JAVA font
 * @param family the family to use
 * @param size the size of the font 
 * @param style the style to use : <code>NORMAL,OBLIQUE,ITALIC  </code>
 * @param variant the varient to use : <code>NORMAL,SMALL_CAPS</code>
 * @param weight the wieght to use : <code>	NORMAL,BOLD,BOLDER,LIGHTER ,100,200,300,400,500,600,700,800,900</code>
 * @return the DOJO GFX font
 */
native private JavaScriptObject createFont(String family,String size,String style,String variant,String weight )/*-{
   var font = {family:family,
               size:size,
               style:style,
               variant:variant,
               weight:weight};
   return font;
}-*/;

}//end of class
