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
	
	public void initEffect(Chart2D chart, Plot<?> plot) {
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
