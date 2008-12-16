package com.objetdirect.tatami.test.widgets;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.objetdirect.tatami.client.layout.BorderContainer;
import com.objetdirect.tatami.testpages.client.widgets.TestBorderContainerPage;

public class TestBorderContainer extends AbstractTestWidgets{

	HtmlElement bordercontainer;
	HtmlElement removeTop;
	HtmlElement addTop;
	HtmlElement removeLeft;
	HtmlElement addLeft;
	HtmlElement updateTop;
	HtmlElement addRight;
	
	HtmlElement topPanel;
	HtmlElement centerPanel;
	HtmlElement leftPanel;
	
	
	@Override
	protected String getTestPageId() {
		return TestBorderContainerPage.class.getName();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		bordercontainer = page.getHtmlElementById("BorderContainer");
		removeTop = page.getHtmlElementById("removeTop");
		addTop = page.getHtmlElementById("addTop");
		removeLeft = page.getHtmlElementById("removeLeft");
		addLeft = page.getHtmlElementById("addLeft");
		updateTop = page.getHtmlElementById("updateTop");
		addRight = page.getHtmlElementById("addRight");
		topPanel = page.getHtmlElementById("TOPPANEL");
		centerPanel = page.getHtmlElementById("CENTERPANEL");
		leftPanel = page.getHtmlElementById("LEFTPANEL");
	}
	
	public void testRemoveAndAddTop(){
		assertEquals("Title",topPanel.getFirstChild().asText());
		testGwt.mouseClick(removeTop);
		try{
			topPanel = page.getHtmlElementById("TOPPANEL");
			fail();
		}catch(ElementNotFoundException e){
			
		}
		testGwt.mouseClick(addTop);
		topPanel = page.getHtmlElementById("TOPPANEL");
		assertEquals("Title",topPanel.getFirstChild().asText());
	}
	
	public void testUpdateTop(){
		assertEquals("Title",topPanel.getFirstChild().asText());
		testGwt.mouseClick(updateTop);
		assertEquals("I'VE CHANGED",topPanel.getFirstChild().asText());
		testGwt.mouseClick(removeTop);
		try{
			topPanel = page.getHtmlElementById("TOPPANEL");
			fail();
		}catch(ElementNotFoundException e){
			
		}
		testGwt.mouseClick(addTop);
		topPanel = page.getHtmlElementById("TOPPANEL");
		assertEquals("I'VE CHANGED",topPanel.getFirstChild().asText());
	}
	
}
