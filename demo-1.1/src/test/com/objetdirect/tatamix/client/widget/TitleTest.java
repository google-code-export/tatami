package com.objetdirect.tatamix.client.widget;


import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.RootPanel;

public class TitleTest extends GWTTestCase {

	private Title title;
	private final String text = "Un titre &agrave; moi";

	public String getModuleName() {
		return "com.objetdirect.tatamix.Tatamix";
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
		title = new Title(Title.H2,text,false);
		RootPanel.get().add(title);
		assertEquals(text,title.getText());
		assertFalse(text.equals(title.getHTML()));

	}

	public void testConstructor2() {
		title = new Title(Title.H2,text,true);
		RootPanel.get().add(title);
		assertEquals(text,title.getText());
		assertFalse(text.equals(title.getHTML()));

	}


	public void testLevel() {
		title = new Title(Title.H4,text);
		RootPanel.get().add(title);
		//title.setLevel(4);
		assertEquals(4,title.getLevel());
		title = new Title(0,text);
		assertEquals(1,title.getLevel());
		title = new Title(7,text);
		assertEquals(6,title.getLevel());

		title = new Title(1,text);
		assertEquals(1,title.getLevel());
		title = new Title(6,text);
		assertEquals(6,title.getLevel());


	}





}//fin de la classe
