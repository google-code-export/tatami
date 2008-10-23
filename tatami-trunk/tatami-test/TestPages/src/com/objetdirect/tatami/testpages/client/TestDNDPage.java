package com.objetdirect.tatami.testpages.client;



import java.util.Collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IndexedPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.dnd.DnD;
import com.objetdirect.tatami.client.dnd.DnDBehaviors;
import com.objetdirect.tatami.client.dnd.DnDDefaultWidgetBehavior;
import com.objetdirect.tatami.client.dnd.DnDGenericBehavior;
import com.objetdirect.tatami.client.dnd.DnDMainController;
import com.objetdirect.tatami.client.dnd.DnDTreeController;
import com.objetdirect.tatami.client.dnd.DndTreeElement;
import com.objetdirect.tatami.client.dnd.IDnDBehavior;
import com.objetdirect.tatami.client.dnd.IDnDController;
import com.objetdirect.tatami.client.dnd.IDnDElement;
import com.objetdirect.tatami.client.dnd.IDnDSource;
import com.objetdirect.tatami.client.dnd.IDnDTarget;
import com.objetdirect.tatami.client.dnd.TreeSource;
import com.objetdirect.tatami.client.dnd.WidgetDnDBehavior;
import com.objetdirect.tatami.client.dnd.WidgetDnDController;
import com.objetdirect.tatami.client.dnd.WidgetDnDElement;
import com.objetdirect.tatami.client.dnd.WidgetSource;
import com.objetdirect.tatami.client.dnd.WidgetTarget;
import com.objetdirect.tatami.client.dnd.DnDBehaviors.BehaviorScopeException;
import com.objetdirect.tatami.client.tree.Tree;
import com.objetdirect.tatami.client.tree.TreeItem;

public class TestDNDPage extends TestPage{

	public TestDNDPage(){
		super("com.objetdirect.tatami.testpages.client.TestDNDPage", "Test DnD");
	}
	
	
	public Widget getTestPage() {
		
		HTML description = new HTML("Drag and drop test <br/> " +
				"The left panel is a target only dnd panel, that means its elements cannot be dragged. <br/>" +
				"The middle panel is a source only panel, meaning it can't accept any dropped items, but it's items can be dragged <br/>" +
				"You can try to drag any cell from the right panel to the left one " +
				"You can also try to drag any items from the tree to the left panel, or from the right panel to the tree"
		);
		
		
		
		final VerticalPanel vpanel1 = new VerticalPanel(){
		};
		vpanel1.sinkEvents(Event.MOUSEEVENTS);
		RootPanel.get().sinkEvents(Event.MOUSEEVENTS);
		vpanel1.setBorderWidth(2);
		final HTML widget1 = new HTML("<p>Please Drag me<img src='folder.png'></img></p>");
		final HTML widget2 = new HTML("Please Drag me too");
		vpanel1.add(widget1);
		vpanel1.add(widget2);
		vpanel1.setHeight("200px");
		vpanel1.setWidth("300px");
		DOM.setElementAttribute(vpanel1.getElement(),"id", "TargetOnlyPanel");
		
		
		final VerticalPanel vpanel2 = new VerticalPanel();
		vpanel2.setBorderWidth(2);
		final HTML widget3 = new HTML("<p>I can be dragged anywhere ! <img src='folder-open.png'></img></p>");
		final HTML widget4 = new HTML("I can be dragged in source 1");
		vpanel2.add(widget3);
		vpanel2.add(widget4);
		vpanel2.setHeight("200px");
		vpanel2.setWidth("300px");
		HorizontalPanel hpan = new HorizontalPanel();
		hpan.add(description);
		hpan.add(vpanel1);
		hpan.add(vpanel2);
		DOM.setElementAttribute(vpanel2.getElement(),"id", "SourceOnlyPanel");
		DOM.setElementAttribute(widget3.getElement(),"id", "widget3");
		DOM.setElementAttribute(widget4.getElement(),"id", "widget4");
		DOM.setElementAttribute(widget1.getElement(),"id", "widget1");
		DOM.setElementAttribute(widget2.getElement(),"id", "widget2");
		final Tree tree = new Tree();
		TreeItem firstRoot = new TreeItem("Grou" , "1");
		firstRoot.addChild(new TreeItem("Grou 1" , "1.1"));
		firstRoot.addChild(new TreeItem("Grou 2" , "1.2"));
		tree.addRootItem(firstRoot);
		TreeSource sourcetree = new TreeSource(tree);
		DnDTreeController.getInstance().registerSource(sourcetree);
		hpan.add(tree);
		DOM.setElementAttribute(tree.getElement(),"id", "Tree");
		DnD.registerSource(vpanel2);
		WidgetTarget target = DnD.registerTarget(vpanel1);
		DnD.registerElement(vpanel2,widget3);
		DnD.registerElement(vpanel2,widget4);
		DnD.registerElement(vpanel2,tree);
		DnD.registerElement(vpanel1,widget1);
		DnD.registerElement(vpanel1,widget2);
		final HTML lastDropBehaviorCalled = new HTML();
		DOM.setElementAttribute(lastDropBehaviorCalled.getElement(), "id","displayLastBehaviorCalled");
		hpan.add(lastDropBehaviorCalled);
		IDnDBehavior<DndTreeElement, TreeSource, WidgetTarget> treeBehavior = new IDnDBehavior<DndTreeElement, TreeSource, WidgetTarget>() {
			
			public boolean onDrop(Collection<DndTreeElement> dndElements,
					TreeSource source, WidgetTarget target, String targetNodeId,
					boolean isCopy) {
				for (DndTreeElement dndTreeElement : dndElements) {
					target.getPanel().add(new HTML((String)dndTreeElement.getItem().getValue("label", "Empty item")));
				}
				lastDropBehaviorCalled.setHTML("Tree to LEFTPANEL");
				return true;
			}
		
			public void onDndStart(Collection<DndTreeElement> elementBeingDragged,
					TreeSource source, boolean ctrlKey) {
			}
		
			public void onCancel() {
			}
		
			public void elementsAccepted(TreeSource source, WidgetTarget target,
					Collection<DndTreeElement> elements, boolean copied,
					IDnDController<?, WidgetTarget> controller) {
				
			}
		
			public void dragOver(IDnDTarget target) {
			}
		
			public boolean checkTargetItemAcceptance(TreeSource source,
					WidgetTarget target, Collection<DndTreeElement> dndElements) {
				return true;
			}
		
			public boolean checkSourceItemAcceptance(TreeSource source,
					WidgetTarget target,
					Collection<? extends DndTreeElement> dndElements) {
				return true;
			}
		};
		
		try {
			DnD.registerBehavior(treeBehavior,tree, vpanel1);
		} catch (BehaviorScopeException e1) {
			e1.printStackTrace();
		}
		
		
		
		DnDGenericBehavior<IDnDElement> grosBehavior = new DnDGenericBehavior<IDnDElement>() {

			@Override
			public boolean onDrop(Collection dndElements, IDnDSource source,
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
		try {
			DnD.registerBehavior(grosBehavior, null, null);
			DnD.registerBehavior(myBehavior, vpanel2, vpanel1);
		} catch (BehaviorScopeException e) {
			e.printStackTrace();
		}
		
		
		return hpan;
	}
	
	
}
