package com.objetdirect.tatami.client.gfx;

public class TextTest extends TestGraphicObject {

	private final String text = "Some text"; 
	
	protected GraphicObject createInstance() {
	   
		return new Text(text);
	}

}
