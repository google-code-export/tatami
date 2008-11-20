/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
Contact: tatami@googlegroups.com
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

public class RuleLabels extends RuleMark {

	private String labels[] = new String[0];
	private String style = "";
	
	/**
	 * Creates a rule labels 
	 * @param type the position for the label HORINZONTAL | VERTICAL
	 * @param labels the labels to show
	 * @param style the style to use for each label
	 * @param position the position to use default : containerNode
	 */
	public RuleLabels(String type,String[] labels,String style,String position) {
         super(type,labels.length,"0px",position);
         this.labels = labels;
         this.style = style;
	}
	
	/**
	 * Creates a rule labels 
	 * @param type the position for the label HORINZONTAL | VERTICAL
	 * @param labels the labels to show
	 * @param style the style to use for each label
	 */
	public RuleLabels(String type,String[] labels,String style) {
		super(type,labels.length,"0px");
		this.labels = labels;
        this.style = style;
	}
	
	/**
	 * 
	 */
	public void createDojoWidget() throws Exception {
		if ( VERTICAL.equals(getType())) {
		  
			this.dojoWidget = createVerticalLabels(DojoController.createArray(labels),style,position);
		} else {
			
			this.dojoWidget = createHorizontalLabels(DojoController.createArray(labels),style,position);
		}

	}


	private native JavaScriptObject createVerticalLabels(JavaScriptObject labels,String style, String position)/*-{
      return new $wnd.dijit.form.VerticalRuleLabels({labels:labels,labelStyle:style,container:position},$wnd.dojo.doc.createElement("div"));
      
      
    }-*/;

   private native JavaScriptObject createHorizontalLabels(JavaScriptObject labels,String style, String position)/*-{
    return new $wnd.dijit.form.HorizontalRuleLabels({labels:labels,labelStyle:style,container:position},$wnd.dojo.doc.createElement("div"));
   }-*/;
	
   /**
    * Returns an array of the labels of this <code>RuleLabels</code>
    * @return an array of the labels of this <code>RuleLabels</code>
    */
   public String[] getLabels() {
	   return this.labels;
   }
	
   /**
    * Returns the style used for the labels. 
    * @return the style used for each label.
    */
   public String getStyle() {
	   return this.style;
   }

}//end of class


