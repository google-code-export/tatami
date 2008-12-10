package com.objetdirect.tatami.testpages.client.widgets;

import com.google.gwt.dom.client.TitleElement;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.grid.Cell;
import com.objetdirect.tatami.client.grid.Grid;
import com.objetdirect.tatami.client.layout.BorderContainer;
import com.objetdirect.tatami.client.layout.ContentPanel;
import com.objetdirect.tatami.client.tree.Tree;
import com.objetdirect.tatami.testpages.client.TestPage;

public class TestBorderContainerPage extends TestPage{

	protected TestBorderContainerPage() {
		super(TestBorderContainerPage.class.getName(),"Test Border Container");
	}

	public Widget getTestPage() {
		FlowPanel  mainPanel = new FlowPanel();
		mainPanel.add(new HTML("Border Container with fixed sizes for all panels"));
		mainPanel.add(getContainer1());
		mainPanel.add(new HTML("Border Container with relative sizes"));
		mainPanel.add(getContainer2());
		mainPanel.add(new HTML("Border Container with sizers "));
		mainPanel.add(getContainer3());
		mainPanel.add(getContainer4());
		mainPanel.add(getContainer5());
		return mainPanel;
	}
	
	private BorderContainer getContainer1(){
		BorderContainer panel = new BorderContainer();
		panel.setSize("300px","300px");
		HTML left = new HTML("LEFT");
		left.setSize("50px", "50px");
		HTML right = new HTML(BorderContainer.REGION_RIGHT);
		right.setSize("50px", "50px");
		HTML top = new HTML(BorderContainer.REGION_TOP);
		top.setSize("50px", "50px");
		HTML bottom = new HTML(BorderContainer.REGION_BOTTOM);
		bottom.setSize("50px", "50px");
		HTML center = new HTML(BorderContainer.REGION_CENTER);
		center.setSize("100%","100%");
		panel.add(new ContentPanel(left),BorderContainer.REGION_LEFT);
		panel.add(new ContentPanel(right),BorderContainer.REGION_RIGHT);
		panel.add(new ContentPanel(top),BorderContainer.REGION_TOP);
		panel.add(new ContentPanel(bottom),BorderContainer.REGION_BOTTOM);
		panel.add(new ContentPanel(center),BorderContainer.REGION_CENTER);
		return panel;
	}
	
	private BorderContainer getContainer2(){
		BorderContainer panel = new BorderContainer();
		panel.setSize("300px","300px");
		HTML left = new HTML(BorderContainer.REGION_LEFT);
		left.setSize("10%", "10%");
		HTML right = new HTML(BorderContainer.REGION_RIGHT);
		right.setSize("10%", "10%");
		HTML top = new HTML(BorderContainer.REGION_TOP);
		top.setSize("10%", "10%");
		HTML bottom = new HTML(BorderContainer.REGION_BOTTOM);
		bottom.setSize("10%", "10%");
		HTML center = new HTML(BorderContainer.REGION_CENTER);
		panel.add(new ContentPanel(left),BorderContainer.REGION_LEFT);
		panel.add(new ContentPanel(right),BorderContainer.REGION_RIGHT);
		panel.add(new ContentPanel(top),BorderContainer.REGION_TOP);
		panel.add(new ContentPanel(bottom),BorderContainer.REGION_BOTTOM);
		panel.add(new ContentPanel(center),BorderContainer.REGION_CENTER);
		return panel;
	}
	
	
	private BorderContainer getContainer3(){
		BorderContainer panel = new BorderContainer();
		panel.setSize("300px","300px");
		panel.setLiveSplitters(false);
		panel.setDesign(BorderContainer.DESIGN_SIDEBAR);
		HTML left = new HTML(BorderContainer.REGION_LEFT);
		HTML right = new HTML(BorderContainer.REGION_RIGHT);
		HTML top = new HTML(BorderContainer.REGION_TOP);
		HTML bottom = new HTML(BorderContainer.REGION_BOTTOM);
		HTML center = new HTML(BorderContainer.REGION_CENTER);
		panel.add(new ContentPanel(left),BorderContainer.REGION_LEFT,true);
		panel.add(new ContentPanel(right),BorderContainer.REGION_RIGHT,true);
		panel.add(new ContentPanel(top),BorderContainer.REGION_TOP,true);
		panel.add(new ContentPanel(bottom),BorderContainer.REGION_BOTTOM,true);
		panel.add(new ContentPanel(center),BorderContainer.REGION_CENTER);
		return panel;
	}
	
	private BorderContainer getContainer4(){
		BorderContainer panel = new BorderContainer();
		panel.setSize("600px","300px");
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
		panel.add(new ContentPanel(new Label("Title")),BorderContainer.REGION_TOP,false);
		panel.add(new ContentPanel(tree),BorderContainer.REGION_LEFT,true);
		panel.add(new ContentPanel(grid),BorderContainer.REGION_CENTER);
		return panel;
	}
	
	private BorderContainer getContainer5(){
		BorderContainer panel = new BorderContainer();
		panel.setSize("300px","300px");
		panel.add(new ContentPanel(new HTML("TOP SIDE")),BorderContainer.REGION_TOP,true);
		panel.add(new ContentPanel(new HTML("BOTTOM SIDE")),BorderContainer.REGION_CENTER);
		return panel;
	}

}
