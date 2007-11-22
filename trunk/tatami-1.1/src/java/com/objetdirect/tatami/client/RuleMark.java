package com.objetdirect.tatami.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class RuleMark extends AbstractDojo {

	static public final String HORIZONTAL ="horizontal";
	static public final String VERTICAL ="vertical";

	private String size = "5px";
    private String type = HORIZONTAL;
    private int count = 3;
    protected String position = "containerNode";

            
    /**
     * 
     * @param type
     * @param count
     * @param style
     * @param position
     */
	public  RuleMark(String type,int count, String size,String position) {
		super();
		if ( !HORIZONTAL.equals(type)) {
			this.type = VERTICAL;	
		}
		Element el = getElement();
		this.count = count;
		this.size = size;
		this.position = position;
	}
	
	/**
	 * 
	 * @param type
	 * @param count
	 * @param size
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

	
	private native JavaScriptObject createVerticalRule(int count,String ruleStyle, String position)/*-{
        return new $wnd.dijit.form.VerticalRule({count:count,ruleStyle:ruleStyle,container:position});
     }-*/;

    private native JavaScriptObject createHorizontalRule(int count,String ruleStyle, String position)/*-{
      return new $wnd.dijit.form.HorizontalRule({count:count,ruleStyle:ruleStyle,container:position});
    }-*/;
}
