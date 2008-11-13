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
package com.objetdirect.tatami.testpages.client;



import java.util.Collection;
import java.util.Iterator;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.dnd.DnD;
import com.objetdirect.tatami.client.dnd.DnDDefaultWidgetBehavior;
import com.objetdirect.tatami.client.dnd.DnDGenericBehavior;
import com.objetdirect.tatami.client.dnd.DndTreeElement;
import com.objetdirect.tatami.client.dnd.IDnDController;
import com.objetdirect.tatami.client.dnd.IDnDElement;
import com.objetdirect.tatami.client.dnd.IDnDSource;
import com.objetdirect.tatami.client.dnd.IDnDTarget;
import com.objetdirect.tatami.client.dnd.WidgetDnDBehavior;
import com.objetdirect.tatami.client.dnd.WidgetDnDElement;
import com.objetdirect.tatami.client.dnd.DnDBehaviors.BehaviorScopeException;
import com.objetdirect.tatami.client.tree.Tree;

public class TestDnDToTreePage extends TestPage{

	public TestDnDToTreePage(){
		super("com.objetdirect.tatami.testpages.client.TestDnDToTreePage", "Test DnD Tree To Tree");
	}
	
	
	public Widget getTestPage() {
		//initializing description and the text displaying the
		//last called behavior
		HTML description = new HTML("Drag and drop test <br/> " +
				"The left panel is a target only dnd panel, that means its elements cannot be dragged. <br/>" +
				"The middle panel is a source only panel, meaning it can't accept any dropped items, but it's items can be dragged <br/>" +
				"You can try to drag any cell from the right panel to the left one " +
				"You can also try to drag any items from the tree to the left panel, or from the right panel to the tree"
		);
		VerticalPanel panel = new VerticalPanel();
		final HTML lastDropBehaviorCalled = new HTML();
		panel.add(description);
		panel.add(lastDropBehaviorCalled);
		
		
		DOM.setElementAttribute(lastDropBehaviorCalled.getElement(), "id","displayLastBehaviorCalled");
		
		
		final Tree tree = initTree();
		final Panel sourcePanel = sourceOnlyPanel();
		panel.add(sourcePanel);
		
		HorizontalPanel hpan = new HorizontalPanel();
		hpan.add(tree);
		panel.add(hpan);
		
		
		DnDGenericBehavior<DndTreeElement> treeToTreeBehavior = new DnDGenericBehavior<DndTreeElement>(){
			@Override
			public boolean onDrop(Collection<DndTreeElement> dndElements,
					IDnDSource<? extends DndTreeElement> source,
					IDnDTarget target, String targetNodeId, boolean isCopy) {
				for (Iterator iterator = dndElements.iterator(); iterator
						.hasNext();) {
					DndTreeElement dndTreeElement = (DndTreeElement) iterator
							.next();
					tree.moveItem(dndTreeElement.getItem(),(Item)tree.getStore().getItemByIdentity(targetNodeId));
				}
				return true;
			}
		};
		
		DnDGenericBehavior<WidgetDnDElement> panelToTreeBehavior = new DnDGenericBehavior<WidgetDnDElement>(){
			public boolean onDrop(Collection<WidgetDnDElement> dndElements,
					IDnDSource<? extends WidgetDnDElement> source,
					IDnDTarget target, String targetNodeId, boolean isCopy) {
				for (Iterator iterator = dndElements.iterator(); iterator
						.hasNext();) {
					WidgetDnDElement dndElement = (WidgetDnDElement) iterator
							.next();
					tree.addChildToItem((Item)tree.getStore().getItemByIdentity(targetNodeId), new Item(dndElement.getDndId(),((HTML)dndElement.getWidget()).getHTML()));
				}
				return true;
			}
		};
		
		try {
			DnD.registerBehavior(treeToTreeBehavior, tree, tree);
			DnD.registerBehavior(panelToTreeBehavior,sourcePanel, tree);
		} catch (BehaviorScopeException e) {
			e.printStackTrace();
		}
		return panel;
	}
	
	
	
	public Tree initTree(){
		final Tree tree = new Tree();
		Item firstRoot = new Item("Item 1" , "1");
		tree.addChildToItem(firstRoot,new Item("Item 1.1" , "1.1"));
		tree.addChildToItem(firstRoot,new Item("Item 1.2" , "1.2"));
		Item secondRoot = new Item("Item 2" , "2");
		tree.addChildToItem(secondRoot,new Item("Item 2.1" , "2.1"));
		tree.addChildToItem(secondRoot,new Item("Item 2.2" , "2.2"));
		tree.addRootItem(firstRoot);
		tree.addRootItem(secondRoot);
		DOM.setElementAttribute(tree.getElement(),"id", "Tree");
		DnD.registerTreeSource(tree);
		DnD.registerTarget(tree);
		return tree;
	}
	
	public Panel sourceOnlyPanel(){
		VerticalPanel vpanel2 = new VerticalPanel();
		vpanel2.setBorderWidth(2);
		HTML widget3 = new HTML("<p>I can be dragged anywhere ! <img src='folder-open.png'></img></p>");
		HTML widget4 = new HTML("I can be dragged in source 1");
		vpanel2.add(widget3);
		vpanel2.add(widget4);
		vpanel2.setHeight("200px");
		vpanel2.setWidth("300px");
		DOM.setElementAttribute(vpanel2.getElement(),"id", "SourceOnlyPanel");
		DOM.setElementAttribute(widget3.getElement(),"id", "widget3");
		DOM.setElementAttribute(widget4.getElement(),"id", "widget4");
		DnD.registerSource(vpanel2);
		DnD.registerElement(vpanel2,widget3);
		DnD.registerElement(vpanel2,widget4);
		return vpanel2;
	}
	
	
}
