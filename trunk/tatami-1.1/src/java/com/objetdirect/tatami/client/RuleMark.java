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
package com.objetdirect.tatami.client;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * 
 * This class permits to create some mark like a rule. 
 * The mark are positioned automatically, it depends of the number of mark 
 * to display and also the size of the <code>RuleMark</code> widget.
 * @author Vianney
 *
 */
public class RuleMark extends AbstractDojo {

	/**
	 * Horizontal type for the <code>RuleMark</code>
	 */
	static public final String HORIZONTAL ="horizontal";
	/**
	 * Vertical type for the <code>RuleMark</code>
	 */
	static public final String VERTICAL ="vertical";

	/**
	 * default size for the mark
	 */
	private String size = "5px";
    /**
     * Default type
     */
	private String type = HORIZONTAL;
    /**
     * default number of mark to display
     */
	private int count = 3;
    /**
     * default position
     */
	protected String position = "containerNode";

            
    /**
     * Creates a <code>RuleMark</code>
     * @param type the type of marks <code>VERTICAL,HORIZONTAL</code>   
     * @param count the number of mark to display
     * @param size the size for the mark in pixel, em... 
     * @param position the position for a parent container see <code>Slider</code>
     */
	public  RuleMark(String type,int count, String size,String position) {
		super();
		if ( !HORIZONTAL.equals(type)) {
			this.type = VERTICAL;	
		}
		this.count = count;
		this.size = size;
		this.position = position;
	}
	
	/**
	 * Creates a <code>RuleMark</code>
     * @param type the type of marks <code>VERTICAL,HORIZONTAL</code>
	 * @param count the number of mark to display
     * @param size the size for the mark in pixel, em...
	 */
	public RuleMark(String type,int count,String size) {
		this(type,count,size,"containerNode");
	}
	
	
	/**
	 * Returns the size of the mark
	 * @return the size of the mark
	 */
	public String getSize() {
		return this.size;
	}
	
	
	/**
	 * Returns the position of this <code>RuleMark</code>
	 * @return the position of this <code>RuleMark</code>
	 */
	public String getPosition() {
		return this.position;
	}
	
	
	/**
	 * Returns the number of mark displayed
	 * @return the number of mark displayed
	 */
	public int getCount() {
		return this.count;
	}
	
	/**
	 * Returns the type of this <code>RuleMark</code>
	 * @return HORIZONTAL | VERTICAL
	 */
	public String getType() {
		return this.type;
	}
	
	
	/**
	 * Creates the DOJO RuleMark widget
	 */
	public void createDojoWidget() throws Exception {
		if ( VERTICAL.equals(type)) {
		    String style = "width:" + size;
			this.dojoWidget = createVerticalRule(getCount(),style,position);
		} else {
			String style = "height:" + size;
			this.dojoWidget = createHorizontalRule(getCount(),style,position);
		}

	}
	
	

	/**
	 * Returns "dijit.form.Slider"
	 * @return "dijit.form.Slider"
	 */
	public String getDojoName() {
       		return "dijit.form.Slider";
	}

	/**
	 * Creates a vertical DOJO RuleMark
	 * @param count number of marks 
	 * @param ruleStyle the size of the marks
	 * @param position the position
	 * @return the vertical DOJO RuleMark
	 */
	private native JavaScriptObject createVerticalRule(int count,String ruleStyle, String position)/*-{
        return new $wnd.dijit.form.VerticalRule({count:count,ruleStyle:ruleStyle,container:position});
     }-*/;

	/**
	 * Creates an horizontal DOJO RuleMark
	 * @param count number of marks 
	 * @param ruleStyle the size of the marks
	 * @param position the position
	 * @return the vertical DOJO RuleMark
	 */
    private native JavaScriptObject createHorizontalRule(int count,String ruleStyle, String position)/*-{
      return new $wnd.dijit.form.HorizontalRule({count:count,ruleStyle:ruleStyle,container:position});
    }-*/;
}
