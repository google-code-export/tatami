/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@googlegroups.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * Authors:  Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s):
 */
package com.objetdirect.tatami.testpages.client.widgets;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.tree.Tree;
import com.objetdirect.tatami.client.tree.TreeListener;
import com.objetdirect.tatami.testpages.client.TestPage;

public class TestTreePage extends TestPage{

	public TestTreePage(){
		 super(TestTreePage.class.getName(), "Test Tree");
	}
	
	public Widget getTestPage() {
		final Tree tree = new Tree(new Item("Root","Root"));
		DOM.setElementAttribute(tree.getElement(), "id", "TreeContainer");
		final Item item1 = new Item("item1" , "Item 1");
		final Item item11 = new Item("item1.1" , "Item 1.1");
		final Item item12 = new Item("item1.2" , "Item 1.2");
		item1.addChild(item11);
		item1.addChild(item12);
		item12.addChild(new Item("item1.2.1" , "Item 1.2.1"));
		item12.addChild(new Item("item1.2.2" , "Item 1.2.2"));
		item1.setValue(Tree.labelClassAttribute,"myLabelClass");
		
		final Item item2 = new Item("item2" , "Item 2");
		tree.setDefaultFolderClosedClass("myTreeClosed");
		tree.setDefaultFolderOpenClass("myTreeOpened");
		tree.getRootItem().addChild(item1);
		tree.getRootItem().addChild(item2);
		//tree.setShowRoot(false);
		HorizontalPanel panel = new HorizontalPanel();
		final HTML openValue = new HTML("Opened :");
		final HTML closedValue = new HTML("Closed :");
		final HTML onClickValue = new HTML("Clicked :");
		final HTML onDblClickValue = new HTML("DblClicked :");
		panel.add(tree);
		tree.addTreeListener(new TreeListener() {
			public void onOpen(Item item) {
				openValue.setHTML("Opened :" + tree.getStore().getIdentity(item));
			}
			public void onClose(Item item) {
				closedValue.setHTML("Closed :" + tree.getStore().getIdentity(item));
			}
			public void onClick(Item item) {
				onClickValue.setHTML("Clicked :" + tree.getStore().getIdentity(item));
			}
			public void onDblClick(Item item) {
				onDblClickValue.setHTML("DblClicked :" + tree.getStore().getIdentity(item));
			}
		});
		
		DOM.setElementAttribute(openValue.getElement(), "id", "TreeOpenValue");
		DOM.setElementAttribute(closedValue.getElement(), "id", "TreeClosedValue");
		DOM.setElementAttribute(onClickValue.getElement(), "id", "TreeClickValue");
		DOM.setElementAttribute(onDblClickValue.getElement(), "id", "TreeDblClickValue");
		Item newItem =  new Item("item3" , "Item 3");
		tree.getRootItem().addChild(newItem);
		Item newItem2 =  new Item("item3.1" , "Item 3.1");
		newItem.addChild(newItem2);
		tree.setSize("200px", "200px");
		panel.add(onClickValue);
		panel.add(onDblClickValue);
		panel.add(closedValue);
		panel.add(openValue);
		
		return panel;
	}

}
