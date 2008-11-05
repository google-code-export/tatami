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
