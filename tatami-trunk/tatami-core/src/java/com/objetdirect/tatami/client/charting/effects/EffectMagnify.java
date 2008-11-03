package com.objetdirect.tatami.client.charting.effects;

import com.objetdirect.tatami.client.DojoController;

public class EffectMagnify extends AbstractJSEffect{
	
	public EffectMagnify(double scale){
		this();
		setScale(scale);
	}
	
	public EffectMagnify(){
		super();
		DojoController.getInstance().require("dojox.charting.action2d."+getEffectName());
	}
	
	public EffectMagnify(int duration){
		super(duration);
		DojoController.getInstance().require("dojox.charting.action2d."+getEffectName());
	}
	
	public EffectMagnify(int duration, double scale){
		this(duration);
		setScale(scale);
	}
	
	@Override
	String getEffectName() {
		return "Magnify";
	}
	
	public void setScale(double scale){
		options.put("scale",scale);
	}
	
	public double getScale(){
		return (Double) options.get("scale");
	}
}
