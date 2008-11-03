package com.objetdirect.tatami.client.charting.effects;

import com.objetdirect.tatami.client.DojoController;


public class EffectMoveSlice extends AbstractJSEffect{
	@Override
	String getEffectName() {
		return "MoveSlice";
	}
	
	public EffectMoveSlice(int duration){
		super(duration);
		DojoController.getInstance().require("dojox.charting.action2d."+getEffectName());
	}
	
	public EffectMoveSlice(){
		super();
		DojoController.getInstance().require("dojox.charting.action2d."+getEffectName());
	}
	
}
