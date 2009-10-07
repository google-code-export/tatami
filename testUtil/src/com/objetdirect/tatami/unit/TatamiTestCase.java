package com.objetdirect.tatami.unit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import junit.framework.TestCase;

public abstract class TatamiTestCase extends TestCase{

	protected HtmlPage page;
	protected TestGWT testGwt;
	
	protected abstract String getPageName();
	protected abstract String getTestPageId();
	
	
	protected void setUp() throws Exception {
		System.out.println("Starting TEST : " + getTestPageId() + "\n" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
		final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3);
		if(page != null){
			page.refresh();
		}else{
			page = (HtmlPage) webClient.getPage(getPageName());
		}
	    testGwt = new TestGWT(page.getEnclosingWindow());
	  //  testGwt.waitForBackgroundTasksToComplete(2000);
	    HtmlElement elem = (HtmlElement) page.getElementById(getTestPageId());
		testGwt.mouseClick(elem, TestGWT.BUTTON_LEFT, false, false, false);
	}
	
	
	protected void tearDown() throws Exception {
		System.gc();
		System.out.println("Ending TEST : " + getTestPageId() + "\n" +  (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
		

	}
	
	
}
