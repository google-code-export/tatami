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