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
	
	public List<HtmlElement> getCircleMarkers(int serieIndex) throws Exception{
		List<HtmlElement> elems = getSerie(serieIndex).getHtmlElementsByTagName("path");
		List<HtmlElement> markers = new ArrayList<HtmlElement>();
		for (HtmlElement htmlElement : elems) {
			if(htmlElement.getAttribute("d").endsWith("m-3 0c 0-4 6-4 6 0m-6 0c 0 4 6 4 6 0") && !htmlElement.getAttribute("fill").equals("none")){
				markers.add(htmlElement);
			}
		}
		return markers;
	}
	
	public List<HtmlElement> getBubbles(int serieIndex) throws Exception{
		List<HtmlElement> elems = getSerie(serieIndex).getHtmlElementsByTagName("circle");
		List<HtmlElement> bubbles = new ArrayList<HtmlElement>();
		for (HtmlElement htmlElement : elems) {
			if(!htmlElement.getAttribute("fill").equals("none")){
				bubbles.add(htmlElement);
			}
		}
		return bubbles;
	}
	
	public List<HtmlElement> getColumns(int serieIndex) throws Exception{
		List<HtmlElement> bars = getSerie(serieIndex).getHtmlElementsByTagName("rect");
		return bars;
	}
	
	public List<HtmlElement> getSeries(){
		List<HtmlElement> series = (List<HtmlElement>) chartBaseElement.getByXPath("svg/g/g");
		return series;
	}
	
	public HtmlElement getSerie(int serieIndex) throws Exception{
		List<HtmlElement> series = getSeries();
		if(series.size() < serieIndex){
			throw new Exception("Not so much series in this chart");
		}else{
			return series.get(serieIndex);
		}
	}
	
	
}
