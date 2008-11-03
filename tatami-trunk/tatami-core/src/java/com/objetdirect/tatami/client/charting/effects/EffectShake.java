package com.objetdirect.tatami.client.charting.effects;

import com.objetdirect.tatami.client.DojoController;


public class EffectShake extends AbstractJSEffect{
	@Override
	String getEffectName() {
		return "Shake";
	}
	
	public EffectShake(int duration){
		super(duration);
		DojoController.getInstance().require("dojox.charting.action2d."+getEffectName());
	}
	
	public EffectShake(){
		super();
		DojoController.getInstance().require("dojox.charting.action2d."+getEffectName());
	}
	
}
