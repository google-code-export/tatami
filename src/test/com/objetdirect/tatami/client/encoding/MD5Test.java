package com.objetdirect.tatami.client.encoding;

import com.objetdirect.tatami.client.DefaultTatamiTest;
import com.objetdirect.tatami.client.encoding.MD5;

public class MD5Test extends DefaultTatamiTest{
	
	public void testMD5(){
		MD5 md5 = MD5.getInstance();
		String text = "text";
		String expectedMD5 = "1cb251ec0d568de6a929b520c4aed8d1";
		String resultingMD5 = md5.encode(text , MD5.HexOutputType);
		assertEquals(expectedMD5 , resultingMD5);
	}
}
