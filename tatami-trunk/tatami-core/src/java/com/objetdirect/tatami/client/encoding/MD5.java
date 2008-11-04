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
