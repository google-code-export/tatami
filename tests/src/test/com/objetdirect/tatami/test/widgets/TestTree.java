package com.objetdirect.tatami.test.widgets;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.objetdirect.tatami.testpages.client.widgets.TestTreePage;
import com.objetdirect.tatami.unit.TatamiTestCase;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper.TreeNode;

public class TestTree extends AbstractTestWidgets{

	TreeWrapper tree;
	HtmlElement openLabel;
	HtmlElement closedLabel;
	HtmlElement clickedLabel;
	
	

	protected String getTestPageId() {
		return TestTreePage.class.getName();
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
		assertEquals("Item 1",node.getLabel());
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
		assertEquals("Opened :Root",openLabel.getTextContent());
		assertEquals("Closed :",closedLabel.getTextContent());
		assertEquals("Clicked :",clickedLabel.getTextContent());
		TreeNode firstNode = tree.getRoot().getChildNodeByPath(0);
		testGwt.mouseClick(firstNode.getExpandNode(), 1, false, false, false);
		assertEquals("Opened :item1",openLabel.getTextContent());
		assertEquals("Closed :",closedLabel.getTextContent());
		assertEquals("Clicked :",clickedLabel.getTextContent());
		testGwt.mouseClick(firstNode.getExpandNode(), 1, false, false, false);
		assertEquals("Opened :item1",openLabel.getTextContent());
		assertEquals("Closed :item1",closedLabel.getTextContent());
		assertEquals("Clicked :",clickedLabel.getTextContent());
		testGwt.mouseClick(firstNode.getNodeContent(), 1, false, false, false);
		assertEquals("Opened :item1",openLabel.getTextContent());
		assertEquals("Closed :item1",closedLabel.getTextContent());
		assertEquals("Clicked :item1",clickedLabel.getTextContent());
	}
	
}
