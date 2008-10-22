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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.objetdirect.tatami.client.AbstractDojo;
import com.objetdirect.tatami.client.DojoController;
import com.objetdirect.tatami.client.NumberSpinner;


/**
 * NumberSpinner grid editor
 * @See {@link NumberSpinner}
 * 
 * @author rdunklau
 *
 */
public class NumberSpinnerEditor implements GridEditor {

	private Map attributes = new HashMap();
	
	
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
			float timeoutChangeRate, boolean trim , Map constraints) {
		this();
		attributes.put("constraints", constraints);
		attributes.put("smallDelta", ""+delta);
		attributes.put("promptMessage", promptMessage);
		attributes.put("invalidMessage", invalidMessage);
		attributes.put("trim", new Boolean(trim));
		attributes.put("defaultTimeout", new Integer(defaultTimeout));
		attributes.put("intermediateChanges", new Boolean(intermediateChanges));
		attributes.put("rangeMessage" , rangeMessage);
		attributes.put("timeoutChangeRate" , ""+timeoutChangeRate);
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
		Map constraints = new HashMap();
		constraints.put("min", new Float(minValue));
		constraints.put("max", new Float(maxValue));
		attributes.put("constraints", constraints);
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
	public NumberSpinnerEditor(String invalidMessage , float smallDelta , String promptMessage , String rangeMessage  ,  Map constraints){
		this();
		attributes.put("promptMessage", promptMessage);
		attributes.put("rangeMessage" , rangeMessage);
		attributes.put("invalidMessage", invalidMessage);
		attributes.put("smallDelta", ""+smallDelta);
		attributes.put("constraints", constraints);
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
		Map constraints = new HashMap();
		constraints.put("min",new Float(minValue));
		constraints.put("max",new Float(maxValue));
		attributes.put("constraints", constraints);
		attributes.put("smallDelta", delta +"");
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
		getValue: function(inRowIndex){
			return this.editor.getValue();
		},
		getWidgetProps: function(inDatum){
			return $wnd.dojo.mixin(this.inherited(arguments), {
				value: inDatum,
				constraints: (this.cell.constraints == undefined ? {} : this.cell.constraints),
				smallDelta : (this.cell.smallDelta == undefined ? 1 : this.cell.smallDelta),
				promptMessage : this.cell.promptMessage ,
				rangeMessage : this.cell.rangeMessage
			});
		}
	});
	}-*/;
	

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.grid.editor.GridEditor#getAttributes()
	 */
	public Map getAttributes() {
		return attributes;
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.grid.editor.GridEditor#getDojoGridEditorName()
	 */
	public String getDojoGridEditorName() {
		return "dojox.grid.cells.TatamiNumberSpinner";
	}


}
