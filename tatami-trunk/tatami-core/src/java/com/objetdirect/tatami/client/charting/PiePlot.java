package com.objetdirect.tatami.client.charting;

public class PiePlot<T> extends Plot<T>{

	final public static String PLOT_TYPE_PIE = "Pie";
	
	public PiePlot(){
		super(PiePlot.PLOT_TYPE_PIE);
	}
	
	public void setLabelOffset(int offset){
		options.put("labelOffset", offset);
	}
	
	public void setPrecision(int precision){
		options.put("precision", precision);
	}
	
	public void setFont(String font){
		options.put("font", font);
	}
	
	public void setFontColor(String fontColor){
		options.put("fontColor", fontColor);
	}
	
	public void setRadius(int radius){
		options.put("radius", radius);
	}
	
}
