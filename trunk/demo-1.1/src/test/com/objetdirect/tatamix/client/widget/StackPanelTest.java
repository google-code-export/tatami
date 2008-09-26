package com.objetdirect.tatamix.client.widget;




import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Label;

public class StackPanelTest extends GWTTestCase {

	private StackPanel panel;

	public String getModuleName() {
		return "com.objetdirect.tatamix.Tatamix";
	}


	public void testAdd() {
		initPanel();
		final int size = 20;
		addItem(size);
		assertEquals(size,panel.countItems());
	}


	public void testInsert() {
		initPanel();
		final int size = 20;
		addItem(20);
		Title t = new Title();
		t.setHTML("inserted");
		panel.insert(t, new Label("insert3"), 3);
		assertEquals(size+1,panel.countItems());
		Title tt = panel.getTitleAt(3);
		assertEquals(tt,t);
	}



	private void addItem(int size) {
		for ( int i=0; i < size; i++) {
			Title t = new Title();
			t.setHTML("item"+i);
			panel.add(t,new Label("widget" + i));
		}

	}

	public void testRemove() {
		initPanel();
		addItem(20);
		Title t = panel.getTitleAt(6);
		panel.remove(5);
		assertEquals(19,panel.countItems());
		assertEquals(t,panel.getTitleAt(5));
		Label label = new Label("new");
		panel.add(new Title(Title.H1,"new"),label);
		assertTrue(panel.remove(label));

	}

	public void testSelectItem() {
		initPanel();
		addItem(10);
		panel.selectItem(7);
		Label w = (Label)panel.getSelectedItem();
		assertEquals("widget7",w.getText());
	}


	public void testGetTitle() {
		initPanel();
		addItem(20);
		Title t = panel.getTitleAt(10);
		assertEquals(t.getHTML(),"item10");
		addItem(20);

		Title t4 = panel.getTitleAt(4);
		assertEquals(4,t4.getLevel());
		panel.setTitleTextAt("titre4", 4);
		assertEquals("titre4",t4.getHTML());


	}


	public void testSetTitle() {
		initPanel();
	}


	private void initPanel() {
		this.panel = new StackPanel();


	}



}//fin de la classe
