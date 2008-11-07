package com.objetdirect.tatami.test;

import java.util.Iterator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.objetdirect.tatami.unit.TatamiTestCase;
import com.objetdirect.tatami.unit.TestGWT;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper.TreeNode;

public class TestDnDToTree extends TatamiTestCase {

	private TreeWrapper tree;
	private HtmlElement displayBehavior;
	
	
	@Override
	protected String getPageName() {
		return "http://localhost:8888/com.objetdirect.tatami.testpages.TestMainPage/TestMainPage.html";
	}

	@Override
	protected String getTestPageId() {
		return "com.objetdirect.tatami.testpages.client.TestDnDToTreePage";
	}

	public void setUp() throws Exception{
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
		tree = new TreeWrapper(page.getHtmlElementById("Tree"));
		displayBehavior = page.getHtmlElementById("displayLastBehaviorCalled");
		testGwt.waitForBackgroundTasksToComplete(5000);
	}
	
	public void testSubItemToRootItem(){
		TreeNode node1 = tree.getNode(0);
		TreeNode node2 = tree.getNode(1);
		testGwt.mouseClick(node1.getExpandNode(), TestGWT.BUTTON_LEFT, false, false, false);
		TreeNode node3 = tree.getChildNode(node1,0);
		testGwt.dragElementTo(node3.getNodeElement(),node1.getNodeElement(), false);
		assertEquals(tree.getChildNode(node1,0).getLabel(),"Item 1.1");
	}
	
	public void testSubItemToSubItem(){
		testGwt.mouseClick(tree.getRoot().getChildNodeByPath(0).getExpandNode());
		testGwt.mouseClick(tree.getRoot().getChildNodeByPath(1).getExpandNode());
		TreeNode subTreeItem = tree.getChildNodeByPath(tree.getRoot(), 0,1);
		TreeNode subTreeItem2 = tree.getChildNodeByPath(tree.getRoot(), 1,1);
		
		testGwt.dragElementTo(subTreeItem.getNodeElement(),subTreeItem2.getNodeElement(), false);
		assertEquals(tree.getChildNodeByPath(tree.getRoot(),1,1,0).getLabel(),"Item 1.1");
		
	}
	
	
}
