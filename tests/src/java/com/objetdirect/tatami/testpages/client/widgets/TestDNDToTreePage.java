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



import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
import com.objetdirect.tatami.testpages.client.TestPage;

public class TestDNDToTreePage extends TestPage{

	public TestDNDToTreePage(){
		super(TestDNDToTreePage.class.getName(), "Test DnD To Tree");
	}
	
	
	public Widget getTestPage() {
		//initializing description and the text displaying the
		//last called behavior
		VerticalPanel panel = new VerticalPanel();
		final HTML lastDropBehaviorCalled = new HTML();
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
					IDnDSource<? super DndTreeElement> source,
					IDnDTarget target, String targetNodeId, boolean isCopy) {
				for (Iterator iterator = dndElements.iterator(); iterator
						.hasNext();) {
					DndTreeElement dndTreeElement = (DndTreeElement) iterator
							.next();
					Item newParent = tree.getStore().getItemByIdentity(targetNodeId);
					Item oldParent = dndTreeElement.getItem().getParentItem();
					Item child;
					if(isCopy){
						Item dropped = dndTreeElement.getItem();
						String[] oldAttributes = dropped.getAttributes();
						Map<String,Object> newAttributes = new HashMap<String, Object>();
						for (int i = 0; i < oldAttributes.length; i++) {
							String currAttr = oldAttributes[i];
							newAttributes.put(currAttr,dropped.getValue(currAttr));
						}
						child = new Item(newAttributes);
					}else{
						oldParent.removeChild(dndTreeElement.getItem());
						child = dndTreeElement.getItem();
					}
					newParent.addChild(dndTreeElement.getItem());
				}
				return true;
			}
		};
		
		DnDGenericBehavior<WidgetDnDElement> panelToTreeBehavior = new DnDGenericBehavior<WidgetDnDElement>(){
			public boolean onDrop(Collection<WidgetDnDElement> dndElements,
					IDnDSource<? super WidgetDnDElement> source,
					IDnDTarget target, String targetNodeId, boolean isCopy) {
				for (Iterator iterator = dndElements.iterator(); iterator
						.hasNext();) {
					WidgetDnDElement dndElement = (WidgetDnDElement) iterator
							.next();
					((Item)tree.getStore().getItemByIdentity(targetNodeId)).addChild(new Item(dndElement.getDndId(),((HTML)dndElement.getWidget()).getHTML()));
					dndElement.getWidget().removeFromParent();
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
		final Tree tree = new Tree(new Item("Root","Root"));
		Item firstRoot = new Item("Item 1" , "1");
		firstRoot.addChild(new Item("Item 1.1" , "1.1"));
		firstRoot.addChild(new Item("Item 1.2" , "1.2"));
		Item secondRoot = new Item("Item 2" , "2");
		Item depth2 = new Item("Item 2.1" , "2.1");
		depth2.addChild(new Item("Item 2.1.1","2.1.1"));
		secondRoot.addChild(depth2);
		secondRoot.addChild(new Item("Item 2.2" , "2.2"));
		secondRoot.addChild(new Item("Item 2.3" , "2.3"));
		tree.getRootItem().addChild(firstRoot);
		tree.getRootItem().addChild(secondRoot);
		DOM.setElementAttribute(tree.getElement(),"id", "Tree");
		DnD.registerTreeSource(tree);
		DnD.registerTarget(tree);
		return tree;
	}
	
	public Panel sourceOnlyPanel(){
		VerticalPanel vpanel2 = new VerticalPanel();
		vpanel2.setBorderWidth(2);
		HTML widget1 = new HTML("Widget 1");
		HTML widget2 = new HTML("Widget 2");
		vpanel2.add(widget1);
		vpanel2.add(widget2);
		vpanel2.setHeight("200px");
		vpanel2.setWidth("300px");
		DOM.setElementAttribute(vpanel2.getElement(),"id", "SourceOnlyPanel");
		DOM.setElementAttribute(widget1.getElement(),"id", "widget1");
		DOM.setElementAttribute(widget2.getElement(),"id", "widget2");
		DnD.registerSource(vpanel2);
		DnD.registerElement(vpanel2,widget1);
		DnD.registerElement(vpanel2,widget2);
		return vpanel2;
	}
	
	
}
