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

import com.objetdirect.tatami.client.DojoController;

public class MD5 {

	private static MD5 instance;
	
	public static final EncodingTypeConstant Base64OutputType = new EncodingTypeConstant(0);
	
	public static final EncodingTypeConstant StringOutputType = new EncodingTypeConstant(2);
	
	public static final EncodingTypeConstant HexOutputType = new EncodingTypeConstant(1);
	
	private MD5(){
		DojoController.getInstance().require("dojox.encoding.digests.MD5");
	}
	
	public static MD5 getInstance(){
		if(instance == null){
			instance = new MD5();
		}
		return instance;
	}
	
	public String encode(String toEncode , EncodingTypeConstant encodingType){
		return dojoEncode(toEncode , encodingType.encodingType);
	}
	
	private native String dojoEncode(String toEncode , int constant)/*-{
		return $wnd.dojox.encoding.digests.MD5(toEncode , constant);
	}-*/;
	
}
