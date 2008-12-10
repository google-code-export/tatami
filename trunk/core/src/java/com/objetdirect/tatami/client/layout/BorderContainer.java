package com.objetdirect.tatami.client.layout;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.DojoController;

/**
 * This Panel replaces both GWT dock panel and GWT splitpanel.
 * You can only add ContentPanel to it, since they must be wrapped as dojowidgets.
 * 
 * @author rdunklau
 *
 */
public class BorderContainer extends AbstractDojoComplexPanel {

	static public final String REGION_CENTER = "center";
	static public final String REGION_TOP = "top";
	static public final String REGION_BOTTOM = "bottom";
	static public final String REGION_LEFT = "left";
	static public final String REGION_RIGHT = "right";
	static public final String REGION_LEADING = "leading";
	static public final String REGION_TRAILING = "trailing";
	
	private final String ATTRIBUTE_REGION = "region";
	private final String ATTRIBUTE_SPLITTER = "splitter";
	
	/**
	 * Default construtor
	 */
	public BorderContainer(){
		super();
	}
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.layout.AbstractDojoComplexPanel#createDojoLayout()
	 */
	public JavaScriptObject createDojoLayout() {
		return createBorderContainer();
	}
	
	/**
	 * This is the actual implementation of the createDojoLayout method.
	 * It instantiates a bordercontainer filling the whole gwt container 
	 * and returns it.
	 * 
	 * @return
	 */
	private native JavaScriptObject createBorderContainer()/*-{
		return new $wnd.dijit.layout.BorderContainer({style:"width:100%;height:100%;",liveSplitters: true});
	}-*/;
	

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.layout.AbstractDojoComplexPanel#getDojoName()
	 */
	public String getDojoName() {
		return "dijit.layout.BorderContainer";
	}
	
	/**
	 * This method is internally called by every "add" method.
	 * It validates that the added widget is a ContentPanel, and sets
	 * its options according to the other param
	 * 
	 * @param child: a ContentPanel to be added to the layout
	 * @param region: one of BorderContainer.REGION_... constants, which 
	 * indicates where the panel should be placed.
	 * @param splitter: if true, a splitter will be created between the added panel
	 * and the center panel, allowing the user to resize them.
	 */
	private void addPanel(Widget child , String region , boolean splitter){
		ContentPanel cp = null;
		if(!(child instanceof ContentPanel)){
	    	throw new IllegalArgumentException("The Border Container only accepts ContentPanel");
	    }else{
	    	cp = (ContentPanel) child;
	    }
		cp.addProperty(ATTRIBUTE_REGION, region);
		if(splitter){
			cp.addProperty(ATTRIBUTE_SPLITTER, splitter);
		}
		super.add(cp);
	}
	
	
	/**
	 * Adds a ContentPanel to the BorderContainer
	 * 
	 * @param child: a ContentPanel object
	 * @param region: one of BorderContainer.REGION_... constants, which 
	 * indicates where the panel should be placed.
	 */
	public void add(Widget child, String region){
		addPanel(child,region, false);
	}
	
	/**
	 * @param child: a ContentPanel to be added to the layout
	 * @param region: one of BorderContainer.REGION_... constants, which 
	 * indicates where the panel should be placed.
	 * @param splitter: if true, a splitter will be created between the added panel
	 * and the center panel, allowing the user to resize them.
	 */
	public void add(Widget child, String region, boolean hasSplitter){
		addPanel(child, region,hasSplitter);
	}
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.layout.AbstractDojoComplexPanel#add(com.google.gwt.user.client.ui.Widget)
	 */
	public void add(Widget child){
		throw new UnsupportedOperationException("The border container does not support the no-arg add operation. Use add(Widget,String) instead");
	}
	

}
