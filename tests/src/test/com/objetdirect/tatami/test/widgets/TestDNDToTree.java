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
		TreeNode node1 = tree.getNode(0);
		TreeNode node2 = tree.getNode(1);
		testGwt.mouseClick(node1.getExpandNode(), TestGWT.BUTTON_LEFT, false, false, false);
		TreeNode node3 = tree.getChildNode(node1,0);
		testGwt.dragElementTo(node3.getNodeElement(),node1.getNodeElement(), false);
		assertEquals("1.1",tree.getChildNode(node1,0).getLabel());
	}
	
	public void testSubItemToSubItem(){
		testGwt.mouseClick(tree.getRoot().getChildNodeByPath(0).getExpandNode());
		testGwt.mouseClick(tree.getRoot().getChildNodeByPath(1).getExpandNode());
		testGwt.waitForBackgroundTasksToComplete(5000);
		TreeNode subTreeItem = tree.getChildNodeByPath(tree.getRoot(),0,0);
		TreeNode subTreeItem2 = tree.getChildNodeByPath(tree.getRoot(),1,0);
		testGwt.dragElementTo(subTreeItem.getNodeElement(),subTreeItem2.getNodeElement(), false);
		testGwt.mouseClick(subTreeItem2.getExpandNode());
		testGwt.waitForBackgroundTasksToComplete(5000);
		assertEquals("2.1.1",tree.getChildNodeByPath(subTreeItem2,0).getLabel());
	}
	
	public void testWidgetToTree(){
		testGwt.mouseClick(tree.getRoot().getChildNodeByPath(0).getExpandNode());
		testGwt.dragElementTo(page.getHtmlElementById("widget1"),tree.getChildNodeByPath(tree.getRoot(),0,1).getNodeElement(),false);
		testGwt.waitForBackgroundTasksToComplete(5000);
		testGwt.mouseClick(tree.getChildNodeByPath(tree.getRoot(),0,1).getExpandNode());
		testGwt.waitForBackgroundTasksToComplete(5000);
		System.out.println(tree.getChildNodeByPath(tree.getRoot(),0,1).getNodeElement().asXml());
		assertEquals("Widget 1",tree.getChildNodeByPath(tree.getRoot(),0,1,0).getLabel());
	}
	
	
}
