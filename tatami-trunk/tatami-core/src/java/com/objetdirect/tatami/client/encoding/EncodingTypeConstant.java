package com.objetdirect.tatami.client.encoding;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author rdunklau
 *
 * Class used for representing an encoding type for 
 * the input/output of the cipher.
 * Currently, only Base64 encoding type is supported.
 * 
 */
public  class EncodingTypeConstant {
	
	public final int encodingType;
	
	public EncodingTypeConstant(int encodingType){
		this.encodingType = encodingType;
	}
	
	
	/**
	 * @param outputTypeConstant : int used by dojo to represent the ciphering types
	 * @return the dojo object containing the ciphering options
	 */
	public native JavaScriptObject getAOObject()/*-{
		var ao = @com.google.gwt.core.client.JavaScriptObject::createObject()();
		ao.outputType = this.@com.objetdirect.tatami.client.encoding.EncodingTypeConstant::encodingType;
		return ao;
	}-*/;
	
}