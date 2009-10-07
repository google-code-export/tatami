package com.objetdirect.tatami.unit.wrappers;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class NumberSpinnerWrapper {

	private HtmlElement downArrow;
	
	private HtmlElement upArrow;
	
	private HtmlElement input;
	
	public NumberSpinnerWrapper(HtmlPage page , String id) throws Exception{
		this(page.getHtmlElementById(id));
	}
	
	public NumberSpinnerWrapper(HtmlElement elem) throws Exception{
		try{
			this.downArrow = elem.getElementsByAttribute("div" , "statemodifier", "DownArrow").get(0);
			this.upArrow = elem.getElementsByAttribute("div" , "statemodifier", "UpArrow").get(0);
			this.input = elem.getElementsByAttribute("input" , "wairole", "spinbutton").get(0);
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("Given element is not a valid spinner");
		}
	}
	
	public HtmlElement getUpArrow(){
		return upArrow;
	}
	
	public HtmlElement getDownArrow(){
		return downArrow;
	}
	
	public HtmlElement getInput(){
		return input;
	}
	
	public String getValue(){
		return ((HtmlInput) input).getValueAttribute();
	} 
}
