package com.objetdirect.tatami.client.charting.effects;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.JSHelper;
import com.objetdirect.tatami.client.charting.PiePiece;

public class EffectEvent extends JavaScriptObject{

	final public static String TYPE_ONCLICK = "onclick";
	final public static String TYPE_ONMOUSEOVER = "onmouseover";
	final public static String TYPE_ONMOUSEOUT = "onmouseout";
	
	final public static String ELEMENT_TYPE_MARKER = "marker";
	final public static String ELEMENT_TYPE_BAR = "bar";
	final public static String ELEMENT_TYPE_COLUMN = "column";
	final public static String ELEMENT_TYPE_CIRCLE = "circle";
	final public static String ELEMENT_TYPE_SLICE = "slice";
	
	
	public final Object getAssociatedObject(){
		if(getAssociatedInternalObject() == null){
			initAssociatedObject();
		}
		return getAssociatedInternalObject();
	}

	
	
	private final void initAssociatedObject(){
		String elementType = getElementType();
		if(getElementType().compareTo("slice") == 0){
			JavaScriptObject data = getJSDataObject();
			Double value = getValue();
			String text = (String) JSHelper.getElementAtIndex(data,"text");
			String color = getColor();
			String tooltip = getTooltip();
			String fontColor = getFontColor();
			setAssociatedObject(new PiePiece(value,text,color,fontColor,tooltip));
		}
	}
	
	private final native double getValue()/*-{
		return this.run.data[this.index].y;
	}-*/;
	
	private final native String getText()/*-{
		return this.run.data[this.index].text;
	}-*/;
	
	private final native String getColor()/*-{
		return this.run.data[this.index].color;
	}-*/;
	
	private final native String getTooltip()/*-{
		return this.run.data[this.index].tooltip;
	}-*/;
	
	private final native String getFontColor()/*-{
		return this.run.data[this.index].tooltip;
	}-*/;
	
	protected EffectEvent(){
		
	}
	
	
	private final native JavaScriptObject getJSDataObject()/*-{return this.run.data[this.index];}-*/;
	public final native String getType()/*-{return this.type;}-*/;
	public final native String getElementType()/*-{return this.element;}-*/;
	private final native void setAssociatedObject(Object object)/*-{this.gwtValue = object;}-*/;
	private final native Object getAssociatedInternalObject()/*-{return this.gwtValue;}-*/;
}
