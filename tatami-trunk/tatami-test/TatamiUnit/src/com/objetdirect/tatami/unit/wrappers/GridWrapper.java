package com.objetdirect.tatami.unit.wrappers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.objetdirect.tatami.unit.TestGWT;

public class GridWrapper {
	
	HtmlElement gridBaseElement;
	TestGWT testGwt;
	
	
	public GridWrapper(HtmlPage page , String id) throws Exception{
		this(page.getHtmlElementById(id));
	}
	
	public GridWrapper(HtmlElement elem) throws Exception{
		this.gridBaseElement = elem;
		this.testGwt = new TestGWT(elem.getPage().getEnclosingWindow());
	}
	
	public void clickOnCell(int row , int col){
		testGwt.mouseClick(getCell(row , col), testGwt.BUTTON_LEFT, false, false, false);
	}
	
	public void dblClickOnCell(int row , int col){
		testGwt.mouseDblClick(getCell(row , col), testGwt.BUTTON_LEFT, false, false, false);
	}
	
	public void clickOnCellHeader(int index){
		testGwt.mouseClick(getCellHeader(index), testGwt.BUTTON_LEFT, false, false, false);
	}
	
	public int getRowCount(){
		int count = 0;
		Iterable<HtmlElement> rows = gridBaseElement.getHtmlElementById("dojox_grid_TatamiGrid_0-page-0").getChildElements();
		for(Iterator<HtmlElement> iterator = rows.iterator() ; iterator.hasNext();){
			iterator.next();
			count +=1;
		}
		return count;
	}
	
	public HtmlElement getCell(int row , int col){
		List<? extends HtmlElement> views = gridBaseElement.getHtmlElementsByAttribute("div", "class", "dojoxGrid-view");
		HtmlElement cell = null;
		for (HtmlElement htmlElement : views) {
			if(!htmlElement.getAttribute("id").equals("dojox_GridRowView_0")){
				HtmlElement a =  htmlElement.getHtmlElementsByTagName("div").get(0);
				HtmlElement b = a.getHtmlElementsByTagName("div").get(0);
				HtmlElement c = b.getHtmlElementsByTagName("div").get(0);
				Iterable<HtmlElement> rows = (Iterable<HtmlElement>) c.getChildElements();
				int count = 0;
				HtmlElement htmlRow = null;
				for (Iterator iterator = rows.iterator(); iterator.hasNext() && count <= row;) {
					htmlRow = (HtmlElement) iterator.next();
					count++;
				}
				if(htmlRow != null){
					cell = getCellInRow(htmlRow, col);
					if(cell != null){
						return cell;
					}
				}
			}
		}
		return cell;
	}
	
	private HtmlElement getCellInRow(HtmlElement row , int cellIndex){
		List<HtmlElement> cell = (List<HtmlElement>) row.getHtmlElementsByAttribute("td", "idx", "" + cellIndex);
		if(cell.size() == 1 ){
			HtmlElement elem =  cell.get(0);
			return elem;
		}else{
			return null;
		}
	}
	
	public HtmlElement getCellHeader(int index){
		List<HtmlElement>  headers = (List<HtmlElement>) gridBaseElement.getHtmlElementsByAttribute("table", "class", "dojoxGrid-row-table");
		List<HtmlElement> matchingHeader = new ArrayList<HtmlElement>();
		for (HtmlElement htmlElement : headers) {
			matchingHeader.addAll(htmlElement.getHtmlElementsByAttribute("th", "idx", "" + index));
		}
		if(matchingHeader.size() == 1){
			return matchingHeader.get(0);
		}else{
			return null;
		}
	}
	
	
	public HtmlElement getView(int index){
		return gridBaseElement.getHtmlElementById("dojox_GridView_" + index);
	}
	
	
}