/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@googlegroups.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * Authors: Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s): 
 */
package com.objetdirect.tatami.client.charting.effects;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.DojoController;
import com.objetdirect.tatami.client.JSHelper;
import com.objetdirect.tatami.client.charting.Chart2D;
import com.objetdirect.tatami.client.charting.Plot;

/**
 * Abstract class defining the common methods for 
 * dojo's predefined chart effects.
 * 
 * @author rdunklau
 *
 */
public abstract class AbstractJSEffect implements Effect{

	JavaScriptObject jsEffect;
	
	Map<String,Object> options = new HashMap<String, Object>();
	
	
	/**
	 * Creates an effect with the given duration
	 * 
	 * @param duration
	 */
	public AbstractJSEffect(int duration){
		this();
		setDuration(duration);
	}
	
	/**
	 * Creates the effect
	 */
	public AbstractJSEffect(){
		DojoController.getInstance().require("dojo.fx");
	}
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.charting.effects.Effect#processEvent(com.objetdirect.tatami.client.charting.effects.EffectEvent)
	 */
	public void processEvent(EffectEvent event) {
		
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.charting.effects.Effect#destroyEffect()
	 */
	public void destroyEffect() {
		jsDestroy(jsEffect);
	}

	/**
	 * @return : the dojo effect class name
	 */
	abstract String getEffectName();
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.charting.effects.Effect#initEffect(com.objetdirect.tatami.client.charting.Chart2D, com.objetdirect.tatami.client.charting.Plot)
	 */
	public void initEffect(Chart2D chart, Plot<?> plot) {
		jsEffect = createEffect(chart.getDojoWidget(), plot.getName(), getEffectName(),JSHelper.convertObjectToJSObject(options));
	}

	/**
	 * Creates the javascript object representing the effect
	 * 
	 * @param dojoChart: the dojoChart object
	 * @param plotName: the plot to which this effect must be applied
	 * @param effectName: the effect name
	 * @param options: the effect options
	 * @return
	 */
	private native JavaScriptObject createEffect(JavaScriptObject dojoChart, String plotName , String effectName , JavaScriptObject options)/*-{
		return new $wnd.dojox.charting.action2d[effectName](dojoChart,plotName,options);
	}-*/;
	
	
	//FIXME : change to effect.destroy as soon as the bug is corrected in dojo
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
	
	/**
	 * Sets an option to the effect
	 * 
	 * @param optionName
	 * @param option
	 */
	public void setOption(String optionName, Object option){
		options.put(optionName, option);
	}
	
	/**
	 * Sets the effect duration
	 * @param duration
	 */
	public void setDuration(int duration){
		options.put("duration",duration);
	}
	
	/**
	 * @return: the effect duration
	 */
	public int getDuration(){
		return (Integer) options.get("duration");
	}
	
}
