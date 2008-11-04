package com.objetdirect.tatami.client.charting.effects;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.DojoController;
import com.objetdirect.tatami.client.charting.Chart2D;
import com.objetdirect.tatami.client.charting.Plot;

public abstract class PlotMouseListener extends AbstractJSEffect implements Effect{

	private JavaScriptObject connectionHandle;

	public PlotMouseListener(){
		DojoController.getInstance().require("dojox.charting.action2d.Base");
		defineTatamiEffect();
	}
	
	@Override
	public void initEffect(Chart2D chart, Plot<?> plot) {
		jsEffect = createEffect(this, chart.getDojoWidget(), plot.getName());
	}
	
	private native JavaScriptObject createEffect(PlotMouseListener listener,JavaScriptObject jsChart , String plotName)/*-{
		return new $wnd.dojox.charting.action2d.TatamiMouseListener(jsChart,plotName,{gwtEffect:listener});
	}-*/;
	
	@Override
	public void destroyEffect() {
		super.destroyEffect();
		unlinkGWTeffect(jsEffect);
	}

	public abstract void processEvent(EffectEvent event);
	
	private native void unlinkGWTeffect(JavaScriptObject dojoEffect)/*-{
		dojoEffect.gwtEffect = null;
	}-*/;
	
	private static native void defineTatamiEffect()/*-{
		$wnd.dojo.declare("dojox.charting.action2d.TatamiMouseListener", $wnd.dojox.charting.action2d.Base, {
			overOutEvents: {onmouseover: 1, onmouseout: 1,onclick : 1},
			gwtEffect : null,
			constructor: function(chart, plot, kwArgs){
				this.connect();
				this.gwtEffect = kwArgs.gwtEffect;
			},
			process: function(o){
				this.gwtEffect.@com.objetdirect.tatami.client.charting.effects.PlotMouseListener::processEvent(Lcom/objetdirect/tatami/client/charting/effects/EffectEvent;)(o);
			}
		});
	}-*/;

	@Override
	String getEffectName() {
		return "PlotMouseListener";
	}

}
