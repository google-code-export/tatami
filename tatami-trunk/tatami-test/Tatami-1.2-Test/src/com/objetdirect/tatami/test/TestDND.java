package com.objetdirect.tatami.test;

import java.util.Iterator;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.javascript.host.MouseEvent;
import com.gargoylesoftware.htmlunit.javascript.host.Window;
import com.objetdirect.tatami.unit.TatamiTestCase;
import com.objetdirect.tatami.unit.TestGWT;
import com.objetdirect.tatami.unit.wrappers.GridWrapper;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper.TreeNode;

public class TestDND extends TatamiTestCase {

	private TreeWrapper tree;
	private HtmlElement leftpanel;
	private HtmlElement rightpanel;
	private HtmlElement displayBehavior;
	
	
	@Override
	protected String getPageName() {
		return "http://localhost:8888/com.objetdirect.tatami.testpages.TestMainPage/TestMainPage.html";
	}

	@Override
	protected String getTestPageId() {
		return "com.objetdirect.tatami.testpages.client.TestDNDPage";
	}

	public void setUp() throws Exception{
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
		tree = new TreeWrapper(page.getHtmlElementById("Tree"));
		displayBehavior = page.getHtmlElementById("displayLastBehaviorCalled");
		leftpanel = page.getHtmlElementById("TargetOnlyPanel");
		rightpanel = page.getHtmlElementById("SourceOnlyPanel");
		testGwt.waitForBackgroundTasksToComplete(5000);
	}
	
	public void testWidgetSourceToWidgetTargetDND(){
		testGwt.toggleJSAlertLogging(true);
		HtmlElement widget3 = page.getHtmlElementById("widget3");
		HtmlElement widget1 = page.getHtmlElementById("widget1");
		testGwt.dragElementTo(widget3,widget1, false);
		testGwt.waitForBackgroundTasksToComplete(1000);
		assertEquals( "Right panel to left panel" , displayBehavior.getTextContent());
		Iterator<HtmlElement> leftPanelChildren = leftpanel.getFirstChild().getAllHtmlChildElements().iterator();
		assertEquals("Please Drag me" ,leftPanelChildren.next().getTextContent());
		assertTrue(leftPanelChildren.next().getTextContent().contains("Please Drag me"));
	}
	
	public void testTreeSourceToTreeTargetDND(){
		testGwt.mouseClick(tree.getRoot().getExpandNode(),TestGWT.BUTTON_LEFT,false,false,false);
		TreeNode node = tree.getNode(0);
		testGwt.dragElementTo(node.getNodeContent(), leftpanel, false);
		assertEquals( "Tree to LEFTPANEL" , displayBehavior.getTextContent());
	}
	
}
