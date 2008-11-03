package com.objetdirect.tatami.client.charting.effects;

import com.objetdirect.tatami.client.DojoController;


public class EffectHighlight extends AbstractJSEffect{
	
	@Override
	String getEffectName() {
		return "Highlight";
	}
	
	public EffectHighlight(){
		super();
		DojoController.getInstance().require("dojox.charting.action2d."+getEffectName());
	}
	
	public EffectHighlight(int duration){
		super(duration);
		DojoController.getInstance().require("dojox.charting.action2d."+getEffectName());
	}
	
	public EffectHighlight(int duration, String color){
		this(duration);
		setColor(color);
	}
	
	public EffectHighlight(String color){
		this();
		setColor(color);
	}
	
	public void setColor(String color){
		options.put("highlight",color);
	}
	
	public String getColor(){
		return (String) options.get("highlight");
	}
}
