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
	
   
	

}//end of class


