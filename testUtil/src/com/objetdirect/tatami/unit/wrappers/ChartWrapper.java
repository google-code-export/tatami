package com.objetdirect.tatami.unit.wrappers;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.objetdirect.tatami.unit.TestGWT;

public class ChartWrapper {

	HtmlElement chartBaseElement;
	TestGWT testGwt;
	
	
	public ChartWrapper(HtmlPage page , String id) throws Exception{
		this(page.getHtmlElementById(id));
	}
	
	public ChartWrapper(HtmlElement elem) throws Exception{
		this.testGwt = new TestGWT(elem.getPage().getEnclosingWindow());
		this.chartBaseElement = elem;
	}
	
}
