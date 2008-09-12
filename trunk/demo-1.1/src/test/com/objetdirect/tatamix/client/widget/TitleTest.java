package com.objetdirect.tatamix.client.widget;


import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.RootPanel;

public class TitleTest extends GWTTestCase {

	private Title title;
	private final String text = "Un titre &agrave; moi";
	
	public String getModuleName() {
		return "com.francetelecom.diamants.presentation.Diamants";
	}
	
	public void testText() {
		title = new Title();
		RootPanel.get().add(title);
		
		title.setText(text);
		assertEquals(text,title.getText());
	}
	
	public void testHTML() {
		title = new Title();
		RootPanel.get().add(title);
		title.setHTML(text);
		assertFalse(text.equals(title.getHTML()));
		assertEquals(text,title.getText());
	}
	
	public void testConstructor1() {
		title = new Title(text,false);
		RootPanel.get().add(title);
		assertEquals(text,title.getText());
		assertFalse(text.equals(title.getHTML()));
		
	}
	
	public void testConstructor2() {
		title = new Title(text,true);
		RootPanel.get().add(title);
		assertEquals(text,title.getText());
		assertFalse(text.equals(title.getHTML()));
		
	}
	
	
	public void testLevel() {
		title = new Title(text);
		RootPanel.get().add(title);
		title.setLevel(4);
		assertEquals(4,title.getLevel());
		title.setLevel(0);
		assertEquals(1,title.getLevel());
		title.setLevel(7);
		assertEquals(6,title.getLevel());
		
		title.setLevel(1);
		assertEquals(1,title.getLevel());
		title.setLevel(6);
		assertEquals(6,title.getLevel());
		
		
	}
	
	
	
	public void tearDown() {
		title = null;
	}


}//fin de la classe
