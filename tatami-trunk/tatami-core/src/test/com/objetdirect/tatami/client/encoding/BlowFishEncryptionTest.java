package com.objetdirect.tatami.client.encoding;

import com.google.gwt.user.client.Random;
import com.objetdirect.tatami.client.DefaultTatamiTest;
import com.objetdirect.tatami.client.encoding.BlowFishEncryption;

public class BlowFishEncryptionTest extends DefaultTatamiTest{

	public void testEncryptionWithHex(){
		BlowFishEncryption cipher = BlowFishEncryption.getInstance();
		String text = "Tatami rulezzz";
		String key = "this is the key";
		String expectedCiphered = "9677e2a7973edf767343b4f1334cb1ba"; 
		String ciphered = cipher.encrypt(BlowFishEncryption.HexOutputType , text, key);
		assertEquals(expectedCiphered,ciphered);
	}
	
	public void testEncryptionWithBase64(){
		BlowFishEncryption cipher = BlowFishEncryption.getInstance();
		String text = "Tatami rulezzz";
		String key = "this is the key";
		String expectedCiphered = "lnfip5c+33ZzQ7TxM0yxug==";
		String ciphered = cipher.encrypt(BlowFishEncryption.Base64OutputType , text, key);
		assertEquals(expectedCiphered,ciphered);
	}
	
	public void testDecryptionWithHex(){
		BlowFishEncryption cipher = BlowFishEncryption.getInstance();
		String expectedText = "Tatami rulezzz";
		String key = "this is the key";
		String ciphered = "9677e2a7973edf767343b4f1334cb1ba" ;
		String deciphered = cipher.decrypt(BlowFishEncryption.HexOutputType , ciphered, key);
		assertEquals(expectedText, deciphered);
	}
	
	public void testDecryptionWithBase64(){
		BlowFishEncryption cipher = BlowFishEncryption.getInstance();
		String expectedText = "Tatami rulezzz";
		String key = "this is the key";
		String ciphered = "lnfip5c+33ZzQ7TxM0yxug==";
		String deciphered = cipher.decrypt(BlowFishEncryption.Base64OutputType , ciphered, key);
		assertEquals(expectedText, deciphered);
	}
	
	public void testEncryptDecryptWithARandomWord(){
		BlowFishEncryption cipher = BlowFishEncryption.getInstance();
		String key = "groum";
		String randomString = "à@#ê";
		String ciphered = cipher.encrypt(BlowFishEncryption.Base64OutputType, randomString, key);
		String deciphered = cipher.decrypt(BlowFishEncryption.Base64OutputType, ciphered, key);
		assertTrue(randomString.equals(deciphered));
		String ciphered2 = cipher.encrypt(BlowFishEncryption.HexOutputType, randomString, key);
		String deciphered2 = cipher.decrypt(BlowFishEncryption.HexOutputType, ciphered2, key);
		assertTrue(randomString.equals(deciphered2));
		
	}
	
}
