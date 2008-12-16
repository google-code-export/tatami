package com.objetdirect.tatami.test.widgets;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.objetdirect.tatami.testpages.client.widgets.TestDNDPage;
import com.objetdirect.tatami.testpages.client.widgets.TestDNDSimplestPage;
import com.objetdirect.tatami.unit.TestGWT;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper.TreeNode;

public class TestDNDSimplest extends AbstractTestWidgets {

	private HtmlElement sourcePanel;
	private HtmlElement targetPanel;
	
	@Override
	protected String getTestPageId() {
		return TestDNDSimplestPage.class.getName();
	}

	public void setUp() throws Exception{
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
		sourcePanel = page.getHtmlElementById("SOURCEPANEL");
		targetPanel = page.getHtmlElementById("TARGETPANEL");
		testGwt.waitForBackgroundTasksToComplete(5000);
	}
	
	public void testWidgetSourceToWidgetTargetDND(){
		testGwt.toggleJSAlertLogging(true);
		HtmlElement widget2 = page.getHtmlElementById("WIDGET2");
		testGwt.dragElementTo(widget2,targetPanel, false);
		testGwt.waitForBackgroundTasksToComplete(1000);
		assertTrue(targetPanel.hasHtmlElementWithId("WIDGET2"));
	}
	
}
