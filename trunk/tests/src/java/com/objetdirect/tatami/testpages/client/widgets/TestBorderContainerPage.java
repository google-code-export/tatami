package com.objetdirect.tatami.testpages.client.widgets;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.DojoAfterCreationEventsSource;
import com.objetdirect.tatami.client.DojoAfterCreationListener;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.grid.Cell;
import com.objetdirect.tatami.client.grid.Grid;
import com.objetdirect.tatami.client.layout.BorderContainer;
import com.objetdirect.tatami.client.layout.ContentPanel;
import com.objetdirect.tatami.client.tree.Tree;
import com.objetdirect.tatami.testpages.client.TestPage;

public class TestBorderContainerPage extends TestPage{

	BorderContainer container;
	ContentPanel treePane;
	ContentPanel gridPane;
	ContentPanel topPane;
	
	protected TestBorderContainerPage() {
		super(TestBorderContainerPage.class.getName(),"Test Border Container");
	}

	public Widget getTestPage() {
		FlowPanel  mainPanel = new FlowPanel();
		initContainer();
		mainPanel.add(container);
		DOM.setElementAttribute(container.getElement(), "id", "BorderContainer");
		Button removeTop = new Button("REMOVE TOP",new ClickListener(){
			public void onClick(Widget sender) {
				container.remove(topPane);
			}
		});
		mainPanel.add(removeTop);
		DOM.setElementAttribute(removeTop.getElement(), "id", "removeTop");
		Button addTop = new Button("ADD TOP",new ClickListener(){
			public void onClick(Widget sender) {
				container.add(topPane,BorderContainer.REGION_TOP,true);
			}
		});
		mainPanel.add(addTop);
		DOM.setElementAttribute(addTop.getElement(), "id", "addTop");
		Button removeLeft = new Button("REMOVE LEFT",new ClickListener(){
			public void onClick(Widget sender) {
				container.remove(treePane);
			}
		});
		mainPanel.add(removeLeft);
		DOM.setElementAttribute(removeLeft.getElement(), "id", "removeLeft");
		Button addLeft = new Button("ADD LEFT",new ClickListener(){
			public void onClick(Widget sender) {
				container.add(treePane,BorderContainer.REGION_LEFT,true);
			}
		});
		mainPanel.add(addLeft);
		DOM.setElementAttribute(addLeft.getElement(), "id", "addLeft");
		Button updateTop = new Button("UPDATE TOP",new ClickListener(){
			public void onClick(Widget sender) {
				topPane.setWidget(new Label("I'VE CHANGED"));
			}
		});
		mainPanel.add(updateTop);
		DOM.setElementAttribute(updateTop.getElement(), "id", "updateTop");
		Button addRight = new Button("ADD RIGHT",new ClickListener(){
			public void onClick(Widget sender) {
				container.add(new ContentPanel(new Label("I'm NEW")),BorderContainer.REGION_RIGHT);
			}
		});
		mainPanel.add(addRight);
		DOM.setElementAttribute(addRight.getElement(), "id", "addRight");
		mainPanel.add(new ContentPanel(new Label("I m a lonely Content Pane")));
		return mainPanel;
	}
	
	
	private void initContainer(){
		container = new BorderContainer();
		container.setSize("600px","300px");
		Tree tree = new Tree(new Item("Collection","Collection"));
		tree.getRootItem().addChild(new Item("Item1","Item1"));
		tree.getRootItem().addChild(new Item("Item2","Item2"));
		tree.getRootItem().addChild(new Item("Item3","Item3"));
		tree.setWidth("80px");
		
		Grid grid = new Grid();
		grid.setSize("100%", "100%");
		grid.addColumn("Col1");
		grid.addColumn("Col2");
		grid.addColumn("Col3");
		Cell cell4 = new Cell("Col4","Col4");
		cell4.setWidth("auto");
		grid.setElasticView(0);
		grid.addCell(cell4);
		grid.addRow(new Object[]{"1","2","3","4"});
		grid.setSize("100%","100%");
		this.treePane = new ContentPanel(tree);
		this.gridPane = new ContentPanel(grid);
		this.topPane = new ContentPanel(new Label("Title"));
		
		treePane.addAfterCreationListener(new DojoAfterCreationListener(){
			public void dojoAfterCreation(DojoAfterCreationEventsSource source) {
				DOM.setElementAttribute(treePane.getDojoElement(), "id", "LEFTPANEL");
			}
		});
		gridPane.addAfterCreationListener(new DojoAfterCreationListener(){
			public void dojoAfterCreation(DojoAfterCreationEventsSource source) {
				DOM.setElementAttribute(gridPane.getDojoElement(), "id", "CENTERPANEL");
			}
		});
		topPane.addAfterCreationListener(new DojoAfterCreationListener(){
			public void dojoAfterCreation(DojoAfterCreationEventsSource source) {
				DOM.setElementAttribute(topPane.getDojoElement(), "id", "TOPPANEL");
			}
		});
		
		
		container.add(topPane,BorderContainer.REGION_TOP,false);
		container.add(treePane,BorderContainer.REGION_LEFT,true);
		container.add(gridPane,BorderContainer.REGION_CENTER);
	}
	

}
