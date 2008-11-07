package com.objetdirect.tatami.test;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.objetdirect.tatami.unit.TatamiTestCase;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper.TreeNode;

public class TestTree extends TatamiTestCase{

	TreeWrapper tree;
	HtmlElement openLabel;
	HtmlElement closedLabel;
	HtmlElement clickedLabel;
	
	
	protected String getPageName() {
		return "http://localhost:8888/com.objetdirect.tatami.testpages.TestMainPage/TestMainPage.html";
	}

	protected String getTestPageId() {
		return "com.objetdirect.tatami.testpages.client.TestTreePage";
	}

	public void setUp() throws Exception{
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
		tree =  new TreeWrapper(page , "TreeContainer");
		openLabel = page.getHtmlElementById("TreeOpenValue");
		closedLabel = page.getHtmlElementById("TreeClosedValue");
		clickedLabel = page.getHtmlElementById("TreeClickValue");
	}
	
	public void testGetItem(){
		TreeNode node = tree.getNode(0);
		assertEquals("item1",node.getLabel());
	}
	
	public void testExpand(){
		TreeNode firstNode = tree.getNode(0);
		HtmlElement expandNode = firstNode.getExpandNode();		
		HtmlElement iconNode = firstNode.getIconNode();
		assertEquals("dijitTreeIcon myTreeClosed",iconNode.getAttribute("class"));
		assertEquals("dijitTreeExpando dijitTreeExpandoClosed" , expandNode.getAttribute("class"));
		testGwt.mouseClick(expandNode, 1, false, false, false);
		assertEquals("dijitTreeExpando dijitTreeExpandoOpened",expandNode.getAttribute("class"));
		assertEquals("dijitTreeIcon myTreeOpened",iconNode.getAttribute("class"));
	}
	
	public void testTreeEvents(){
		assertEquals("Opened :",openLabel.getTextContent());
		assertEquals("Closed :",closedLabel.getTextContent());
		assertEquals("Clicked :",clickedLabel.getTextContent());
		TreeNode firstNode = tree.getNode(0);
		testGwt.mouseClick(firstNode.getExpandNode(), 1, false, false, false);
		assertEquals("Opened :Item 1",openLabel.getTextContent());
		assertEquals("Closed :",closedLabel.getTextContent());
		assertEquals("Clicked :",clickedLabel.getTextContent());
		testGwt.mouseClick(firstNode.getExpandNode(), 1, false, false, false);
		assertEquals("Opened :Item 1",openLabel.getTextContent());
		assertEquals("Closed :Item 1",closedLabel.getTextContent());
		assertEquals("Clicked :",clickedLabel.getTextContent());
		testGwt.mouseClick(firstNode.getNodeContent(), 1, false, false, false);
		assertEquals("Opened :Item 1",openLabel.getTextContent());
		assertEquals("Closed :Item 1",closedLabel.getTextContent());
		assertEquals("Clicked :Item 1",clickedLabel.getTextContent());
	}
	
}
