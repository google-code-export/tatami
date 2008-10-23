package com.objetdirect.tatami.testpages.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.DragAndDropListener;
import com.objetdirect.tatami.client.DragAndDropPanel;
import com.objetdirect.tatami.client.tree.Tree;
import com.objetdirect.tatami.client.tree.TreeItem;
import com.objetdirect.tatami.client.tree.TreeListener;

public class TestTreePage extends TestPage{

	public TestTreePage(){
		 super("com.objetdirect.tatami.testpages.client.TestTreePage", "Test Tree");
	}
	
	public Widget getTestPage() {
		final Tree tree = new Tree();
		DOM.setElementAttribute(tree.getElement(), "id", "TreeContainer");
		final TreeItem item1 = new TreeItem("item1" , "Item 1");
		final TreeItem item11 = new TreeItem("item1.1" , "Item 1.1");
		final TreeItem item12 = new TreeItem("item1.2" , "Item 1.2");
		item1.addChild(item11);
		item1.addChild(item12);
		item12.addChild(new TreeItem("item1.2.1" , "Item 1.2.1"));
		item12.addChild(new TreeItem("item1.2.2" , "Item 1.2.2"));
		item1.setLabelClass("myLabelClass");
		
		final TreeItem item2 = new TreeItem("item2" , "Item 2");
		tree.setDefaultFolderClosedClass("myTreeClosed");
		tree.setDefaultFolderOpenClass("myTreeOpened");
		tree.addRootItem(item1);
		tree.addRootItem(item2);
		tree.setShowRoot(false);
		HorizontalPanel panel = new HorizontalPanel();
		final HTML openValue = new HTML("Opened :");
		final HTML closedValue = new HTML("Closed :");
		final HTML onClickValue = new HTML("Clicked :");
		panel.add(tree);
		panel.add(new Button("Refresh", new ClickListener() {
			public void onClick(Widget sender) {
				tree.refreshTree();
			}
		}));
		tree.addTreeListener(new TreeListener() {
			public void onOpen(TreeItem item) {
				openValue.setHTML("Opened :" + tree.getStore().getIdentity(item));
			}
			public void onClose(TreeItem item) {
				closedValue.setHTML("Closed :" + tree.getStore().getIdentity(item));
			}
			public void onClick(TreeItem item) {
				onClickValue.setHTML("Clicked :" + tree.getStore().getIdentity(item));
			}
		});
		
		DOM.setElementAttribute(openValue.getElement(), "id", "TreeOpenValue");
		DOM.setElementAttribute(closedValue.getElement(), "id", "TreeClosedValue");
		DOM.setElementAttribute(onClickValue.getElement(), "id", "TreeClickValue");
		TreeItem newItem =  new TreeItem("item3" , "Item 3");
		tree.addRootItem(newItem);
		TreeItem newItem2 =  new TreeItem("item3.1" , "Item 3.1");
		newItem.addChild(newItem2);
		tree.setSize("200px", "200px");
		panel.add(onClickValue);
		panel.add(closedValue);
		panel.add(openValue);
		return panel;
	}

}
