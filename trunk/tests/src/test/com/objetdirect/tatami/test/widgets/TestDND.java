package com.objetdirect.tatami.test.widgets;

import java.util.Iterator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.objetdirect.tatami.testpages.client.widgets.TestDNDPage;
import com.objetdirect.tatami.unit.TestGWT;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper.TreeNode;

public class TestDND extends AbstractTestWidgets {

	private TreeWrapper tree;
	private HtmlElement targetOnlyPanel;
	private HtmlElement sourceOnlyPanel;
	private HtmlElement displayBehavior;
	
	@Override
	protected String getTestPageId() {
		return TestDNDPage.class.getName();
	}

	public void setUp() throws Exception{
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
		tree = new TreeWrapper(page.getHtmlElementById("Tree"));
		displayBehavior = page.getHtmlElementById("displayLastBehaviorCalled");
		targetOnlyPanel = page.getHtmlElementById("TargetOnlyPanel");
		sourceOnlyPanel = page.getHtmlElementById("SourceOnlyPanel");
		testGwt.waitForBackgroundTasksToComplete(5000);
	}
	
	public void testWidgetSourceToWidgetTargetDND(){
		testGwt.toggleJSAlertLogging(true);
		HtmlElement widget4 = page.getHtmlElementById("widget4");
		testGwt.dragElementTo(widget4,targetOnlyPanel, false);
		testGwt.waitForBackgroundTasksToComplete(1000);
		assertEquals( "Right panel to left panel" , displayBehavior.getTextContent());
		Iterator<HtmlElement> leftPanelChildren = targetOnlyPanel.getFirstChild().getAllHtmlChildElements().iterator();
		assertEquals("Please Drag me" ,leftPanelChildren.next().getTextContent());
		assertTrue(leftPanelChildren.next().getTextContent().contains("Please Drag me"));
	}
	
	public void testTreeSourceToWidgetTargetDND(){
		testGwt.mouseClick(tree.getRoot().getExpandNode(),TestGWT.BUTTON_LEFT,false,false,false);
		TreeNode node = tree.getNode(0);
		HtmlElement widget1 = page.getHtmlElementById("widget1");
		testGwt.dragElementTo(node.getNodeContent(), widget1, false);
		assertEquals( "Tree to LEFTPANEL" , displayBehavior.getTextContent());
	}
	
}
