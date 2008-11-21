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
import com.objetdirect.tatami.client.dnd.DnDBehaviors.BehaviorScopeException;
import com.objetdirect.tatami.client.tree.Tree;
import com.objetdirect.tatami.testpages.client.TestPage;

public class TestDnDTreeToPanelPage extends TestPage{

	public TestDnDTreeToPanelPage(){
		super(TestDNDPage.class.getName(), "Test DnD");
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
		
		
		//Initializing the left panel ("Source Only"),
		final Panel sourceOnlyPanel = sourceOnlyPanel();
		// the second one ("target only")
		final Panel targetOnlyPanel = targetOnlyPanel();
		// and both trees (source and target only)
		final Tree sourceOnlyTree = sourceOnlyTree();
		
		
		HorizontalPanel hpan = new HorizontalPanel();
		hpan.add(sourceOnlyPanel);
		hpan.add(targetOnlyPanel);
		hpan.add(sourceOnlyTree);
		panel.add(hpan);
		
		
		DnDGenericBehavior<IDnDElement> treeToPanel1Behavior = new DnDGenericBehavior<IDnDElement>(){

			@Override
			public boolean onDrop(Collection<IDnDElement> dndElements,
					IDnDSource<? extends IDnDElement> source,
					IDnDTarget target, String targetNodeId, boolean isCopy) {
				lastDropBehaviorCalled.setHTML("Tree to LEFTPANEL");
				return true;
			}
			
		};
		
		
//		try {
//			DnD.registerBehavior(treeToPanel1Behavior,sourceOnlyTree, targetOnlyPanel);
//		} catch (BehaviorScopeException e1) {
//			e1.printStackTrace();
//		}
		DnDGenericBehavior<IDnDElement> grosBehavior = new DnDGenericBehavior<IDnDElement>() {
			@Override
			public boolean onDrop(Collection<IDnDElement> dndElements, IDnDSource<? extends IDnDElement> source,
					IDnDTarget target, String targetNodeId, boolean isCopy) {
				lastDropBehaviorCalled.setHTML("Default behavior");
				return true;
			}
		};
		WidgetDnDBehavior myBehavior = new DnDDefaultWidgetBehavior() {
			@Override
			public boolean onDrop(Collection<Widget> draggedWidgets,
					Panel source, Panel target, String targetNodeId,
					boolean isCopy) {
				lastDropBehaviorCalled.setHTML("Right panel to left panel");
				for (Widget widget : draggedWidgets) {
					DnD.move(widget, source, target);
				}
				return true;
			}
		};
		DnDGenericBehavior<DndTreeElement> treeToTreeBehavior = new DnDGenericBehavior<DndTreeElement>(){
			@Override
			public boolean onDrop(Collection<DndTreeElement> dndElements, IDnDSource<? extends DndTreeElement> source,
					IDnDTarget target, String targetNodeId, boolean isCopy) {
				System.out.println("GROU DROP");
				lastDropBehaviorCalled.setHTML("Tree To Tree Behavior : dropped on tree item : " + targetNodeId);
				return true;
			}

			@Override
			public boolean checkSourceItemAcceptance(
					IDnDSource<? extends DndTreeElement> source,
					IDnDTarget target, Collection<DndTreeElement> dndElements) {
				return super.checkSourceItemAcceptance(source, target, dndElements);
			}


			@Override
			public void dragOver(IDnDTarget target) {
				super.dragOver(target);
			}

			@Override
			public void elementsAccepted(
					IDnDSource<? extends DndTreeElement> source,
					IDnDTarget target, Collection<DndTreeElement> elements,
					boolean copied, IDnDController<?, IDnDTarget> controller) {
				super.elementsAccepted(source, target, elements, copied, controller);
			}

			@Override
			public void onCancel() {
				super.onCancel();
			}

			@Override
			public void onDndStart(
					Collection<DndTreeElement> elementBeingDragged,
					IDnDSource<? extends DndTreeElement> source, boolean ctrlKey) {
				super.onDndStart(elementBeingDragged, source, ctrlKey);
			}
		};
		try {
			//DnD.registerBehavior(grosBehavior, null, null);
			//DnD.registerBehavior(myBehavior, sourceOnlyPanel, targetOnlyPanel);
			DnD.registerBehavior(treeToTreeBehavior, sourceOnlyPanel, sourceOnlyTree);
		} catch (BehaviorScopeException e) {
			e.printStackTrace();
		}
		return panel;
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
	
	public Panel targetOnlyPanel(){
		final VerticalPanel vpanel1 = new VerticalPanel(){
		};
		vpanel1.setBorderWidth(2);
		final HTML widget1 = new HTML("<p>Please Drag me<img src='folder.png'></img></p>");
		final HTML widget2 = new HTML("Please Drag me too");
		vpanel1.add(widget1);
		vpanel1.add(widget2);
		vpanel1.setHeight("200px");
		vpanel1.setWidth("300px");
		DnD.registerTarget(vpanel1);
		DOM.setElementAttribute(vpanel1.getElement(),"id", "TargetOnlyPanel");
		DOM.setElementAttribute(widget1.getElement(),"id", "widget1");
		DOM.setElementAttribute(widget2.getElement(),"id", "widget2");
		DnD.registerElement(vpanel1,widget1);
		DnD.registerElement(vpanel1,widget2);
		return vpanel1;
	}
	
	public Tree sourceOnlyTree(){
		final Tree tree = new Tree();
		Item firstRoot = new Item("Grou" , "1");
		firstRoot.addChild(new Item("Grou 1" , "1.1"));
		firstRoot.addChild(new Item("Grou 2" , "1.2"));
		tree.setRootItem(firstRoot);
		DOM.setElementAttribute(tree.getElement(),"id", "Tree");
		DnD.registerTreeSource(tree);
		DnD.registerTarget(tree);
		return tree;
	}
	
}
