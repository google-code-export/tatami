package com.objetdirect.tatami.client.charting.effects;

import com.objetdirect.tatami.client.DojoController;

public class EffectTooltip extends AbstractJSEffect{

	String getEffectName() {
		return "Tooltip";
	}

	public EffectTooltip() {
		super();
		DojoController.getInstance().require("dojox.charting.action2d."+getEffectName());
	}

	public EffectTooltip(int duration) {
		super(duration);
		DojoController.getInstance().require("dojox.charting.action2d."+getEffectName());
	}
	
	

}
