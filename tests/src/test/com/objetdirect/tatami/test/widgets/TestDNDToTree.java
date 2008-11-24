package com.objetdirect.tatami.test.widgets;


import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.objetdirect.tatami.testpages.client.widgets.TestDNDToTreePage;
import com.objetdirect.tatami.unit.TatamiTestCase;
import com.objetdirect.tatami.unit.TestGWT;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper.TreeNode;

public class TestDNDToTree extends AbstractTestWidgets {

	private TreeWrapper tree;
	private HtmlElement displayBehavior;
	
	

	@Override
	protected String getTestPageId() {
		return TestDNDToTreePage.class.getName();
	}

	public void setUp() throws Exception{
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
		tree = new TreeWrapper(page.getHtmlElementById("Tree"));
		displayBehavior = page.getHtmlElementById("displayLastBehaviorCalled");
		testGwt.waitForBackgroundTasksToComplete(5000);
	}
	
	public void testSubItemToRootItem(){
		TreeNode root = tree.getRoot();
		testGwt.mouseClick(tree.getChildNodeByPath(tree.getRoot(),0).getExpandNode());
		testGwt.waitForBackgroundTasksToComplete(500);
		TreeNode node1 = tree.getChildNodeByPath(tree.getRoot(),0,1);
		testGwt.dragElementTo(node1.getNodeContent(),root.getNodeContent(), false);
		assertEquals("1.2",tree.getChildNode(tree.getRoot(),0).getLabel());
	}
	
	public void testSubItemToSubItem(){
		testGwt.mouseClick(tree.getRoot().getChildNodeByPath(0).getExpandNode());
		testGwt.mouseClick(tree.getRoot().getChildNodeByPath(1).getExpandNode());
		testGwt.waitForBackgroundTasksToComplete(5000);
		TreeNode subTreeItem = tree.getChildNodeByPath(tree.getRoot(),0,0);
		TreeNode subTreeItem2 = tree.getChildNodeByPath(tree.getRoot(),1,0);
		testGwt.dragElementTo(subTreeItem.getNodeContent(),subTreeItem2.getNodeContent(), false);
		testGwt.waitForBackgroundTasksToComplete(5000);
		assertEquals("1.1",(tree.getChildNodeByPath(tree.getRoot(),1,0,0).getLabel()));
	}
	
	
	
	
}
