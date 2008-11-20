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
 * Authors:  Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s):
 */
package com.objetdirect.tatami.client.grid.editor;

import java.util.HashMap;
import java.util.Map;

import com.objetdirect.tatami.client.DojoController;
import com.objetdirect.tatami.client.NumberSpinner;


/**
 * NumberSpinner grid editor
 * @See {@link NumberSpinner}
 * 
 * @author rdunklau
 *
 */
public class NumberSpinnerEditor extends BaseEditor{

	/**
	 * Default NumberSpinner editor, with no constraints
	 */
	public NumberSpinnerEditor(){
		DojoController.getInstance().require("dojox.grid.cells.dijit");
		declareEditor();
	}
	
	/**
	 * @see {@link NumberSpinner#NumberSpinner(int, String, boolean, float, String, String, float, boolean, float, Map)}
	 * 
	 * 
	 * @param defaultTimeout
	 * @param invalidMessage
	 * @param intermediateChanges
	 * @param delta
	 * @param promptMessage
	 * @param rangeMessage
	 * @param timeoutChangeRate
	 * @param trim
	 * @param constraints
	 */
	public NumberSpinnerEditor(int defaultTimeout, String invalidMessage,
			boolean intermediateChanges, float delta, 
			String promptMessage, String rangeMessage,
			float timeoutChangeRate, boolean trim , Map<String, Float> constraints) {
		this();
		addAttribute("constraints", constraints);
		addAttribute("smallDelta", ""+delta);
		addAttribute("promptMessage", promptMessage);
		addAttribute("invalidMessage", invalidMessage);
		addAttribute("trim", new Boolean(trim));
		addAttribute("defaultTimeout", new Integer(defaultTimeout));
		addAttribute("intermediateChanges", new Boolean(intermediateChanges));
		addAttribute("rangeMessage" , rangeMessage);
		addAttribute("timeoutChangeRate" , ""+timeoutChangeRate);
	}
	
	/**
	 * @see {@link NumberSpinner#NumberSpinner(int, String, boolean, float, float, String, String, float, boolean, float, float, float)}
	 * 
	 * @param defaultTimeout
	 * @param invalidMessage
	 * @param intermediateChanges
	 * @param smallDelta
	 * @param largeDelta
	 * @param promptMessage
	 * @param rangeMessage
	 * @param timeoutChangeRate
	 * @param trim
	 * @param minValue
	 * @param maxValue
	 */
	public NumberSpinnerEditor(int defaultTimeout, String invalidMessage,
			boolean intermediateChanges, float smallDelta, float largeDelta,
			String promptMessage, String rangeMessage,
			float timeoutChangeRate, boolean trim ,  float minValue , float maxValue) {
		this(defaultTimeout , invalidMessage , intermediateChanges , smallDelta , promptMessage , rangeMessage , timeoutChangeRate , trim , null);
		Map<String, Float> constraints = new HashMap<String, Float>();
		constraints.put("min", new Float(minValue));
		constraints.put("max", new Float(maxValue));
		addAttribute("constraints", constraints);
	}
	
	/**
	 * @see {@link NumberSpinner#NumberSpinner(String, float, String, String, float, Map)}
	 * 
	 * @param invalidMessage
	 * @param smallDelta
	 * @param promptMessage
	 * @param rangeMessage
	 * @param constraints
	 */
	public NumberSpinnerEditor(String invalidMessage , float smallDelta , String promptMessage , String rangeMessage  ,  Map<String, Float> constraints){
		this();
		addAttribute("promptMessage", promptMessage);
		addAttribute("rangeMessage" , rangeMessage);
		addAttribute("invalidMessage", invalidMessage);
		addAttribute("smallDelta", ""+smallDelta);
		addAttribute("constraints", constraints);
	}
	
	/**
	 * @see {@link NumberSpinner#NumberSpinner(float, float, float, float)}
	 * 
	 * @param minValue
	 * @param maxValue
	 * @param delta
	 */
	public NumberSpinnerEditor( float minValue , float maxValue , float delta){
		this();
		Map<String, Float> constraints = new HashMap<String, Float>();
		constraints.put("min",new Float(minValue));
		constraints.put("max",new Float(maxValue));
		addAttribute("constraints", constraints);
		addAttribute("smallDelta", delta +"");
	}
	
	
	/**
	 * Declares the dojo class corresponding to this editor
	 */
	private void declareEditor(){
		declareDojoEditor();
	}
	
	/**
	 * Declares the dojo class corresponding to this editor
	 */
	private native void declareDojoEditor()/*-{
	$wnd.dojo.declare("dojox.grid.cells.TatamiNumberSpinner", $wnd.dojox.grid.cells._Widget, {
		widgetClass: "dijit.form.NumberSpinner",
		getWidgetProps: function(inDatum){
			return $wnd.dojo.mixin(this.inherited(arguments), {
				value: inDatum,
				constraints: (this.constraints == undefined ? {} : this.constraints),
				smallDelta : (this.smallDelta == undefined ? 1 : this.smallDelta),
				promptMessage : this.promptMessage ,
				rangeMessage : this.rangeMessage
			});
		}
	});
	}-*/;
	


	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.grid.editor.GridEditor#getDojoGridEditorName()
	 */
	public String getDojoGridEditorName() {
		return "dojox.grid.cells.TatamiNumberSpinner";
	}


}
