package com.objetdirect.tatami.testpages.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Text;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.test.Logger;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TestMainPage implements EntryPoint {

  private static FlowPanel console;
	
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
	  addTestPage(new TestSpinnerPage());
	  addTestPage(new TestSimpleGridPage());
	  addTestPage(new TestMoreComplexGridPage());
	  addTestPage(new TestLayoutGridPage());
	  addTestPage(new TestPourDoc());
	  addTestPage(new TestTreePage());
	  addTestPage(new TestDNDPage());
	  addTestPage(new TestGridRowStylerBugPage());
	  addTestPage(new TestFillingEmtyGridPage());
  }
  
  public void addTestPage(TestPage testPage){
	  Button button = new DisplayTestPageButton(testPage);
	  RootPanel.get().add(button);
  }
  
  public void loadTestPage(TestPage page){
	  RootPanel.get().clear();
	  RootPanel.get().add(page.getTestPage());
	  RootPanel.get().add(Logger.getLoggerWindow());
  }
  
  private class DisplayTestPageButton extends Button{
	  final TestPage pageToLoad;
	  DisplayTestPageButton(TestPage page){
		  super(page.TESTPAGE_LABEL);
		  this.pageToLoad = page;
		  DOM.setElementAttribute(this.getElement() , "id" , page.TESTPAGE_ID);
		  this.addClickListener(new ClickListener(){
			  public void onClick(Widget sender) {
				  	((Button) sender).setText("CLCIKED");
					loadTestPage(pageToLoad);			
				}
		  });
	  }
  }
  
}
