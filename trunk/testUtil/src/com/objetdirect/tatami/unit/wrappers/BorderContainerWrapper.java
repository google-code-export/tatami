package com.objetdirect.tatami.unit.wrappers;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.HTMLElement;
import com.objetdirect.tatami.unit.TestGWT;

public class BorderContainerWrapper {

	HtmlElement baseelement;
	TestGWT testGwt;
	
	public BorderContainerWrapper(HtmlPage page , String id) throws Exception{
		this(page.getHtmlElementById(id));
	}
	
	public BorderContainerWrapper(HtmlElement elem) throws Exception{
		this.testGwt = new TestGWT(elem.getPage().getEnclosingWindow());
		this.baseelement = elem;
	}
	
	public HtmlElement getWidgetAtRegion(String region){
		return (HtmlElement) baseelement.getHtmlElementsByAttribute("div", "region",region).get(0).getFirstChild();
	}
	
	
}
