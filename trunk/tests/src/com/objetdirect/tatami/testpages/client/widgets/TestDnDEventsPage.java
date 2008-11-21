package com.objetdirect.tatami.testpages.client.widgets;

import java.util.Collection;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.dnd.AbstractDnDBehavior;
import com.objetdirect.tatami.client.dnd.DnD;
import com.objetdirect.tatami.client.dnd.IDnDController;
import com.objetdirect.tatami.client.dnd.IDnDTarget;
import com.objetdirect.tatami.client.dnd.WidgetDnDElement;
import com.objetdirect.tatami.client.dnd.WidgetSource;
import com.objetdirect.tatami.client.dnd.WidgetTarget;
import com.objetdirect.tatami.client.dnd.DnDBehaviors.BehaviorScopeException;
import com.objetdirect.tatami.testpages.client.TestPage;

public class TestDnDEventsPage extends TestPage{

	final HTML onDnDStartHasBeenCalledLbl = new HTML("BEHAVIOR  ON START : ");
	final HTML onDnDCancelHasBeenCalledLbl = new HTML("BEHAVIOR  ON CANCEL :");
	final HTML onDnDCheckSourceHasBeenCalledLbl = new HTML("BEHAVIOR  ON CHECKSOURCE : ");
	final HTML onDnDCheckTargetHasBeenCalledLbl = new HTML("BEHAVIOR  ON CHECKTARGET :");
	final HTML onDnDDragOverHasBeenCalledLbl = new HTML("BEHAVIOR  ON DRAGOVER : ");
	final HTML onDnDElementsAcceptedHasBeenCalledLbl = new HTML("BEHAVIOR  ON ACCEPTED :");
	final HTML onDnDDropHtmlLbl = new HTML("BEHAVIOR  ON DROP :");
	final HTML onDnDStartHasBeenCalled = new HTML("false");
	final HTML onDnDCancelHasBeenCalled = new HTML("false");
	final HTML onDnDCheckSourceHasBeenCalled = new HTML("false");
	final HTML onDnDCheckTargetHasBeenCalled = new HTML("false");
	final HTML onDnDDragOverHasBeenCalled = new HTML("false");
	final HTML onDnDElementsAcceptedHasBeenCalled = new HTML("false");
	final HTML onDnDDropHtml = new HTML("false");
	final HTML onDnDStartHasBeenCalled2 = new HTML("false");
	final HTML onDnDCancelHasBeenCalled2 = new HTML("false");
	final HTML onDnDCheckSourceHasBeenCalled2 = new HTML("false");
	final HTML onDnDCheckTargetHasBeenCalled2 = new HTML("false");
	final HTML onDnDDragOverHasBeenCalled2 = new HTML("false");
	final HTML onDnDElementsAcceptedHasBeenCalled2 = new HTML("false");
	final HTML onDnDDropHtml2 = new HTML("false");
	
	
	public TestDnDEventsPage(){
		super(TestDnDEventsPage.class.getName(), "Test DnD Events");
	}

	public Widget getTestPage() {
		Panel container = new FlowPanel();
		container.add(new HTML("<p>This test presents two panels. The left one is a \"Source Only\" panel, whereas the" +
				"right one is a target only one. <br/>" +
				"Two behaviors are registered : one for drag and drop from source 1 to target 1, the other from source1 to anything. To each of these behaviors, there is an associated column," +
				"which shows wether the corresponding behavior methods have been called</p>" +
				"<br/>" +
				"onDnDStart and onDnDCancel are called on each of registered behaviors for the source, whereas other events are called" +
				" on the best matching Behavior scope "));
		container.add(initDisplayPanel());
		Panel panel = new HorizontalPanel();
		container.add(panel);
		Panel source = sourceOnlyPanel();
		Panel target = targetOnlyPanel();
		panel.add(source);
		panel.add(target);
		
		
		AbstractDnDBehavior<WidgetDnDElement, WidgetSource,WidgetTarget> behavior = new AbstractDnDBehavior<WidgetDnDElement, WidgetSource, WidgetTarget>(){

			@Override
			public boolean checkSourceItemAcceptance(WidgetSource source,
					WidgetTarget target,
					Collection<WidgetDnDElement> dndElements) {
				onDnDCheckSourceHasBeenCalled.setHTML("TRUE");
				return super.checkItemAcceptance(source, target, dndElements);
			}

			@Override
			public boolean checkTargetItemAcceptance(WidgetSource source,
					WidgetTarget target,
					Collection<WidgetDnDElement> dndElements) {
				onDnDCheckTargetHasBeenCalled.setHTML("TRUE");
				return super.checkTargetItemAcceptance(source, target, dndElements);
			}

			@Override
			public void dragOver(IDnDTarget target) {
				onDnDDragOverHasBeenCalled.setHTML("TRUE");
				super.dragOver(target);
			}

			@Override
			public void elementsAccepted(WidgetSource source,
					WidgetTarget target, Collection<WidgetDnDElement> elements,
					boolean copied, IDnDController<?, WidgetTarget> controller) {
				onDnDElementsAcceptedHasBeenCalled.setHTML("TRUE");
				super.elementsAccepted(source, target, elements, copied, controller);
			}

			@Override
			public void onCancel() {
				onDnDCancelHasBeenCalled.setHTML("TRUE");
				super.onCancel();
			}

			@Override
			public void onDndStart(
					Collection<WidgetDnDElement> elementBeingDragged,
					WidgetSource source, boolean ctrlKey) {
				onDnDStartHasBeenCalled.setHTML("TRUE");
				super.onDndStart(elementBeingDragged, source, ctrlKey);
			}

			@Override
			public boolean onDrop(Collection<WidgetDnDElement> dndElements,
					WidgetSource source, WidgetTarget target,
					String targetNodeId, boolean isCopy) {
				onDnDDropHtml.setHTML("TRUE");
				return super.onDrop(dndElements, source, target, targetNodeId, isCopy);
			}
		};
		
		AbstractDnDBehavior<WidgetDnDElement, WidgetSource,WidgetTarget> behavior2 = new AbstractDnDBehavior<WidgetDnDElement, WidgetSource, WidgetTarget>(){

			@Override
			public boolean checkItemAcceptance(WidgetSource source,
					WidgetTarget target,
					Collection<WidgetDnDElement> dndElements) {
				onDnDCheckSourceHasBeenCalled2.setHTML("TRUE");
				return super.checkItemAcceptance(source, target, dndElements);
			}


			@Override
			public void dragOver(IDnDTarget target) {
				onDnDDragOverHasBeenCalled2.setHTML("TRUE");
				super.dragOver(target);
			}

			@Override
			public void elementsAccepted(WidgetSource source,
					WidgetTarget target, Collection<WidgetDnDElement> elements,
					boolean copied, IDnDController<?, WidgetTarget> controller) {
				onDnDElementsAcceptedHasBeenCalled2.setHTML("TRUE");
				super.elementsAccepted(source, target, elements, copied, controller);
			}

			@Override
			public void onCancel() {
				onDnDCancelHasBeenCalled2.setHTML("TRUE");
				super.onCancel();
			}

			@Override
			public void onDndStart(
					Collection<WidgetDnDElement> elementBeingDragged,
					WidgetSource source, boolean ctrlKey) {
				onDnDStartHasBeenCalled2.setHTML("TRUE");
				super.onDndStart(elementBeingDragged, source, ctrlKey);
			}

			@Override
			public boolean onDrop(Collection<WidgetDnDElement> dndElements,
					WidgetSource source, WidgetTarget target,
					String targetNodeId, boolean isCopy) {
				onDnDDropHtml2.setHTML("TRUE");
				return super.onDrop(dndElements, source, target, targetNodeId, isCopy);
			}
		};
		
		try {
			DnD.registerBehavior(behavior, source, target);
			DnD.registerBehavior(behavior2, null, null);
		} catch (BehaviorScopeException e) {
			e.printStackTrace();
		}
		return container;
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
	
	private Panel initDisplayPanel(){
		HorizontalPanel hpan = new HorizontalPanel();
		hpan.setSpacing(5);
		VerticalPanel labels = new VerticalPanel();
		hpan.add(labels);
		labels.add(onDnDStartHasBeenCalledLbl);
		labels.add(onDnDCancelHasBeenCalledLbl);
		labels.add(onDnDCheckSourceHasBeenCalledLbl);
		labels.add(onDnDCheckTargetHasBeenCalledLbl);
		labels.add(onDnDDragOverHasBeenCalledLbl);
		labels.add(onDnDElementsAcceptedHasBeenCalledLbl);
		labels.add(onDnDDropHtmlLbl);
		VerticalPanel displayPanel = new VerticalPanel();
		hpan.add(displayPanel);
		displayPanel.add(onDnDStartHasBeenCalled);
		displayPanel.add(onDnDCancelHasBeenCalled);
		displayPanel.add(onDnDCheckSourceHasBeenCalled);
		displayPanel.add(onDnDCheckTargetHasBeenCalled);
		displayPanel.add(onDnDDragOverHasBeenCalled);
		displayPanel.add(onDnDElementsAcceptedHasBeenCalled);
		displayPanel.add(onDnDDropHtml);
		VerticalPanel displayPanel2 = new VerticalPanel();
		hpan.add(displayPanel2);
		displayPanel2.add(onDnDStartHasBeenCalled2);
		displayPanel2.add(onDnDCancelHasBeenCalled2);
		displayPanel2.add(onDnDCheckSourceHasBeenCalled2);
		displayPanel2.add(onDnDCheckTargetHasBeenCalled2);
		displayPanel2.add(onDnDDragOverHasBeenCalled2);
		displayPanel2.add(onDnDElementsAcceptedHasBeenCalled2);
		displayPanel2.add(onDnDDropHtml2);
		initIds();
		return hpan;
	}
	
	private void initIds(){
		DOM.setElementAttribute(onDnDStartHasBeenCalled.getElement(),"id","onDnDStartHasBeenCalled");
		DOM.setElementAttribute(onDnDCancelHasBeenCalled.getElement(),"id","onDnDCancelHasBeenCalled");
		DOM.setElementAttribute(onDnDCheckSourceHasBeenCalled.getElement(),"id","onDnDCheckSourceHasBeenCalled");
		DOM.setElementAttribute(onDnDCheckTargetHasBeenCalled.getElement(),"id","onDnDCheckTargetHasBeenCalled");
		DOM.setElementAttribute(onDnDDragOverHasBeenCalled.getElement(),"id","onDnDDragOverHasBeenCalled");
		DOM.setElementAttribute(onDnDElementsAcceptedHasBeenCalled.getElement(),"id","onDnDElementsAcceptedHasBeenCalled");
		DOM.setElementAttribute(onDnDDropHtml.getElement(),"id","onDnDDropHtml");
		DOM.setElementAttribute(onDnDStartHasBeenCalled2.getElement(),"id","onDnDStartHasBeenCalled2");
		DOM.setElementAttribute(onDnDCancelHasBeenCalled2.getElement(),"id","onDnDCancelHasBeenCalled2");
		DOM.setElementAttribute(onDnDCheckSourceHasBeenCalled2.getElement(),"id","onDnDCheckSourceHasBeenCalled2");
		DOM.setElementAttribute(onDnDCheckTargetHasBeenCalled2.getElement(),"id","onDnDCheckTargetHasBeenCalled2");
		DOM.setElementAttribute(onDnDDragOverHasBeenCalled2.getElement(),"id","onDnDDragOverHasBeenCalled2");
		DOM.setElementAttribute(onDnDElementsAcceptedHasBeenCalled2.getElement(),"id","onDnDElementsAcceptedHasBeenCalled2");
		DOM.setElementAttribute(onDnDDropHtml2.getElement(),"id","onDnDDropHtml2");
	}
	
}
