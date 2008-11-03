package com.objetdirect.tatami.client.charting.effects;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.DojoController;
import com.objetdirect.tatami.client.JSHelper;
import com.objetdirect.tatami.client.charting.Chart2D;
import com.objetdirect.tatami.client.charting.Plot;

public abstract class AbstractJSEffect implements Effect{

	JavaScriptObject jsEffect;
	
	Map<String,Object> options = new HashMap<String, Object>();
	
	
	public AbstractJSEffect(int duration){
		this();
		setDuration(duration);
	}
	
	public AbstractJSEffect(){
		DojoController.getInstance().require("dojo.fx");
	}
	
	public void processEvent(EffectEvent event) {
		
	}

	public void destroyEffect() {
		jsDestroy(jsEffect);
	}

	abstract String getEffectName();
	
	public void initEffect(Chart2D chart, Plot plot) {
		jsEffect = createEffect(chart.getDojoWidget(), plot.getName(), getEffectName(),JSHelper.convertObjectToJSObject(options));
	}

	private native JavaScriptObject createEffect(JavaScriptObject dojoChart, String plotName , String effectName , JavaScriptObject options)/*-{
		return new $wnd.dojox.charting.action2d[effectName](dojoChart,plotName,options);
	}-*/;
	
	
	//XXX : change to effect.destroy as soon as the bug is corrected in dojo
	// See: http://bugs.dojotoolkit.org/ticket/7991
	protected native void jsDestroy(JavaScriptObject effect)/*-{
		if(effect.handle){
			effect.disconnect();
		}
		$wnd.dojox.lang.functional.forIn(effect.anim, function(o){
			$wnd.dojox.lang.functional.forIn(o, function(anim){
				anim.action.stop(true);
			});
		});
		effect.anim = {};
	}-*/;
	
	public void setOption(String optionName, Object option){
		options.put(optionName, option);
	}
	
	public void setDuration(int duration){
		options.put("duration",duration);
	}
	
	public int getDuration(){
		return (Integer) options.get("duration");
	}
	
}
