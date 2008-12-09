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
//		mainPanel.add(new ContentPane(new HTML("Grou"){
//			@Override
//			public void onAttach(){
//				Window.alert("HTML WIDGET ATTACHED");
//				super.onAttach();
//			}
//			@Override
//			public void onDetach(){
//				Window.alert("HTML WIDGET DETACHED");
//				super.onDetach();
//			}
//		}));
		return mainPanel;
	}
	
	private BorderContainer getContainer1(){
		BorderContainer panel = new BorderContainer();
		panel.setSize("300px","300px");
		HTML left = new HTML("LEFT");
		left.setSize("50px", "50px");
		HTML right = new HTML("RIGHT");
		right.setSize("50px", "50px");
		HTML top = new HTML("TOP");
		top.setSize("50px", "50px");
		HTML bottom = new HTML("BOTTOM");
		bottom.setSize("50px", "50px");
		HTML center = new HTML("CENTER");
		center.setSize("100%","100%");
		panel.add(left,"left");
		panel.add(right,"right");
		panel.add(top,"top");
		panel.add(bottom,"bottom");
		panel.add(center);
		return panel;
	}
	
	private BorderContainer getContainer2(){
		BorderContainer panel = new BorderContainer();
		panel.setSize("300px","300px");
		HTML left = new HTML("LEFT");
		left.setSize("10%", "10%");
		HTML right = new HTML("RIGHT");
		right.setSize("10%", "10%");
		HTML top = new HTML("TOP");
		top.setSize("10%", "10%");
		HTML bottom = new HTML("BOTTOM");
		bottom.setSize("10%", "10%");
		HTML center = new HTML("CENTER");
		panel.add(left,"left");
		panel.add(right,"right");
		panel.add(top,"top");
		panel.add(bottom,"bottom");
		panel.add(center);
		return panel;
	}
	
	
	private BorderContainer getContainer3(){
		BorderContainer panel = new BorderContainer();
		panel.setSize("300px","300px");
		HTML left = new HTML("LEFT");
		left.setSize("100px","100px");
		HTML right = new HTML("RIGHT");
		right.setSize("100px","100px");
		HTML top = new HTML("TOP");
		top.setSize("100px","100px");
		HTML bottom = new HTML("BOTTOM");
		bottom.setSize("100px","100px");
		HTML center = new HTML("CENTER");
		panel.add(left,"left",true);
		panel.add(right,"right",true);
		panel.add(top,"top",true);
		panel.add(bottom,"bottom",true);
		panel.add(center,"center");
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
		Cell cell1 = new Cell("Col4","Col4");
		cell1.setWidth("auto");
		grid.setElasticView(0);
		grid.addCell(cell1);
		grid.addRow(new Object[]{"1","2","3","4"});
		grid.setSize("100%","100%");
		panel.add(new Label("Title"),"top",true);
		panel.add(tree,"left",true);
		panel.add(grid,"center");
		return panel;
	}

}
