package com.objetdirect.tatami.client.layout;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.objetdirect.tatami.client.DefaultTatamiTest;

public class TestBorderContainer extends DefaultTatamiTest{

	BorderContainer container;
	
	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();
		container = new BorderContainer();
	}
	
	public void testAddingContentPanesAfterAttachment(){
		RootPanel.get().add(container);
		ContentPanel panel = new ContentPanel(new HTML("TOP"));
		ContentPanel panel2 = new ContentPanel(new HTML("BOTTOM"));
		ContentPanel panel3 = new ContentPanel(new HTML("CENTER"));
		container.add(panel,BorderContainer.REGION_TOP);
		container.add(panel2,BorderContainer.REGION_BOTTOM);
		container.add(panel3,BorderContainer.REGION_CENTER);
		assertEquals(3,container.getWidgetCount());
		assertEquals(0,container.getWidgetIndex(panel));
		assertEquals(1,container.getWidgetIndex(panel2));
		assertEquals(2,container.getWidgetIndex(panel3));
		assertEquals(panel , container.getWidget(0));
		assertEquals(panel2 , container.getWidget(1));
		assertEquals(panel3 , container.getWidget(2));
	}
	
	public void testRemovingContentPanesAfterAttachment(){
		RootPanel.get().add(container);
		ContentPanel panel = new ContentPanel(new HTML("TOP"));
		ContentPanel panel2 = new ContentPanel(new HTML("CENTER"));
		container.add(panel,BorderContainer.REGION_TOP);
		container.add(panel2,BorderContainer.REGION_CENTER);
		assertEquals(2,container.getWidgetCount());
		container.remove(panel);
		container.remove(panel2);
		assertEquals(0,container.getWidgetCount());
		container.add(panel,BorderContainer.REGION_TOP);
		container.add(panel2,BorderContainer.REGION_CENTER);
		assertEquals(2,container.getWidgetCount());
		container.remove(0);
		assertEquals(1,container.getWidgetCount());
		assertEquals(panel2,container.getWidget(0));
	}
	
	public void testAddingNonContentPanes(){
		try{
			container.add(new HTML("AH AH AH"));
			fail();
		}catch(IllegalArgumentException e){
		}
	}
	

	@Override
	protected void gwtTearDown() throws Exception {
		RootPanel.get().clear();
		super.gwtTearDown();
	}

	
	
}
