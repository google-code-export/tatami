package com.objetdirect.tatami.unit.wrappers;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<HtmlElement> getPiePieces(){
		List<HtmlElement> elems = chartBaseElement.getHtmlElementsByTagName("path");
		List<HtmlElement> pies = new ArrayList<HtmlElement>();
		for (HtmlElement htmlElement : elems) {
			if(htmlElement.getAttribute("d").startsWith("M 150 150L") && htmlElement.getAttribute("d").endsWith("150 150Z")){
				pies.add(htmlElement);
			}
		}
		return pies;
	}
	
	public List<HtmlElement> getCircleMarkers(){
		List<HtmlElement> elems = chartBaseElement.getHtmlElementsByTagName("path");
		List<HtmlElement> markers = new ArrayList<HtmlElement>();
		for (HtmlElement htmlElement : elems) {
			if(htmlElement.getAttribute("d").endsWith("m-3,0 c0,-4 6,-4 6,0 m-6,0 c0,4 6,4 6,0")){
				markers.add(htmlElement);
			}
		}
		return markers;
	}
	
}
