package com.objetdirect.tatami.client.encoding;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.DojoController;

/**
 * @author rdunklau
 *
 *	This class allow to cipher using the BlowFish symmetric algorithm.
 *	For information about this algorithm , please see  
 *		http://en.wikipedia.org/wiki/Blowfish_(cipher)
 *
 */
public class BlowFishEncryption {
	
	
	
	
	/**
	 * Singleton
	 */
	private static BlowFishEncryption instance;
	
	
	
	
	
	/**
	 *  Base64 output type constant.
	 *  Currently the only supported input/output type for the cipher
	 */
	public static final EncodingTypeConstant Base64OutputType = new EncodingTypeConstant(0);
	public static final EncodingTypeConstant HexOutputType = new EncodingTypeConstant(1);
	
	private BlowFishEncryption(){
		DojoController.getInstance().require("dojox.encoding.crypto.Blowfish");
	}
	
	/**
	 * @return the BlowFishEncryption singleton
	 */
	public static BlowFishEncryption getInstance(){
		if(instance == null){
			instance = new BlowFishEncryption();
		}
		return instance;
	}
	
	
	/**
	 * @param outputTypeConstant : an object of type EncodingTypeConstant representing
	 * 			the output encoding (currently, only Base64 output type is supported)
	 * 			@see BlowFishEncryption.EncodingTypeConstant
	 * @param toCrypt : the plaintext to crypt
	 * @param key : the private key to use for ciphering
	 * @return the encrypted text
	 */
	public String encrypt(EncodingTypeConstant outputTypeConstant ,String toCrypt , String key){
		return dojoEncrypt(outputTypeConstant.getAOObject() , toCrypt, key);
	}
	
	
	
	/**
	 * @param inputTypeConstant : an object of type EncodingTypeConstant representing
	 * 			the input encoding (currently, only Base64 output type is supported)
	 * 			@see BlowFishEncryption.EncodingTypeConstant
	 * @param toDecrypt : the ciphered text
	 * @param key : the private key used for ciphering
	 * @return : the decrypted text
	 */
	public String decrypt(EncodingTypeConstant inputTypeConstant , String toDecrypt , String key){
		return dojoDecrypt(inputTypeConstant.getAOObject(), toDecrypt, key);
	}
	
	
	
	
	
	
	/**
	 * @param aoObject : JavaScript object containing the ciphering options
	 * @param toCrypt : the string to cipher
	 * @param key : the private key
	 * @return : ciphered text
	 */
	private native String dojoEncrypt(JavaScriptObject encodingOptions , String toCrypt , String key)/*-{
		return $wnd.dojox.encoding.crypto.Blowfish.encrypt(toCrypt , key , encodingOptions);
	}-*/;
	
	
	/**
	 * @param aoObject : JavaScript object containing the ciphering options
	 * @param toCrypt : the string to decipher
	 * @param key : the private key
	 * @return : deciphered text
	 */
	private native String dojoDecrypt(JavaScriptObject encodingOptions , String toDecrypt , String key)/*-{
		return $wnd.dojox.encoding.crypto.Blowfish.decrypt(toDecrypt , key , encodingOptions);
	}-*/;
	
	
	
	
}
