package com.objetdirect.tatami.test.widgets;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.objetdirect.tatami.testpages.client.widgets.TestDNDPage;
import com.objetdirect.tatami.testpages.client.widgets.TestDNDSimplestPage;
import com.objetdirect.tatami.testpages.client.widgets.TestMD5Page;
import com.objetdirect.tatami.unit.TestGWT;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper.TreeNode;

public class TestMD5 extends AbstractTestWidgets {

	private HtmlTextInput input;
	private HtmlElement output;
	private HtmlElement apply;
	
	@Override
	protected String getTestPageId() {
		return TestMD5Page.class.getName();
	}

	public void setUp() throws Exception{
		super.setUp();
		input = page.getHtmlElementById("INPUT");
		output = page.getHtmlElementById("OUTPUT");
		apply = page.getHtmlElementById("APPLY");
		testGwt.waitForBackgroundTasksToComplete(5000);
	}
	
	public void testMD5(){
		try {
			input.type("GROU");
			testGwt.mouseClick(apply);
			assertEquals("222956cdd4e0062456618dc7bcadde61",output.asText());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
