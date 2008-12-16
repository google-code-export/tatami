package com.objetdirect.tatami.test.widgets;


import com.objetdirect.tatami.testpages.client.widgets.TestDNDToTreePage;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper.TreeNode;

public class TestDNDToTree extends AbstractTestWidgets {

	private TreeWrapper tree;
	
	

	@Override
	protected String getTestPageId() {
		return TestDNDToTreePage.class.getName();
	}

	public void setUp() throws Exception{
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
		tree = new TreeWrapper(page.getHtmlElementById("Tree"));
		testGwt.waitForBackgroundTasksToComplete(5000);
	}
	
	public void testSubItemToRootItem(){
		TreeNode root = tree.getRoot();
		testGwt.mouseClick(TreeWrapper.getChildNodeByPath(tree.getRoot(),0).getExpandNode());
		testGwt.waitForBackgroundTasksToComplete(500);
		TreeNode node1 = TreeWrapper.getChildNodeByPath(tree.getRoot(),0,1);
		testGwt.dragElementTo(node1.getNodeContent(),root.getNodeContent(), false);
		assertEquals("1.2",TreeWrapper.getChildNode(tree.getRoot(),0).getLabel());
	}
	
	public void testSubItemToSubItem(){
		testGwt.mouseClick(tree.getRoot().getChildNodeByPath(0).getExpandNode());
		testGwt.mouseClick(tree.getRoot().getChildNodeByPath(1).getExpandNode());
		testGwt.waitForBackgroundTasksToComplete(5000);
		TreeNode subTreeItem = TreeWrapper.getChildNodeByPath(tree.getRoot(),0,0);
		TreeNode subTreeItem2 = TreeWrapper.getChildNodeByPath(tree.getRoot(),1,0);
		testGwt.dragElementTo(subTreeItem.getNodeContent(),subTreeItem2.getNodeContent(), false);
		testGwt.waitForBackgroundTasksToComplete(5000);
		assertEquals("1.1",(TreeWrapper.getChildNodeByPath(tree.getRoot(),1,0,0).getLabel()));
	}
	
	
	
	
}
