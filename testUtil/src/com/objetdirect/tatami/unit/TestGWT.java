package com.objetdirect.tatami.unit;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.MouseEvent;

public class TestGWT {
	
final private WebWindow window;
	
	public static final int BUTTON_LEFT = MouseEvent.BUTTON_LEFT;
	
	public static final int BUTTON_MIDDLE = MouseEvent.BUTTON_MIDDLE;
	
	public static final int BUTTON_RIGHT = MouseEvent.BUTTON_RIGHT;

	AlertHandler alertHandler = new AlertHandler() {
		public void handleAlert(Page arg0, String arg1) {
			System.out.println("Alert : " + arg1);
		}
	};
	
	public TestGWT(WebWindow window){
		this.window = window;
	}
	
	public void mouseDown(HtmlElement elem){
		MouseEvent event = new MouseEvent(elem , MouseEvent.TYPE_MOUSE_DOWN , false ,false, false, BUTTON_LEFT );
		elem.fireEvent(event);
	}
	
	public void mouseDown(HtmlElement elem , int button , boolean altKey , boolean shiftKey , boolean ctrlKey){
		MouseEvent event = new MouseEvent(elem , MouseEvent.TYPE_MOUSE_DOWN , shiftKey ,ctrlKey, altKey, button );
		elem.fireEvent(event);
	}
	
	public void mouseUp(HtmlElement elem){
		MouseEvent event = new MouseEvent(elem , MouseEvent.TYPE_MOUSE_UP , false ,false, false, BUTTON_LEFT );
		elem.fireEvent(event);
	}
	
	public void mouseUp(HtmlElement elem , int button , boolean altKey , boolean shiftKey , boolean ctrlKey){
		MouseEvent event = new MouseEvent(elem , MouseEvent.TYPE_MOUSE_UP , shiftKey ,ctrlKey, altKey, button );
		elem.fireEvent(event);
	}
	
	public void mouseClick(HtmlElement elem , int button , boolean altKey , boolean shiftKey , boolean ctrlKey){
		MouseEvent event = new MouseEvent(elem , MouseEvent.TYPE_CLICK , shiftKey ,ctrlKey, altKey, button );
		elem.fireEvent(event);
	}
	
	public void mouseClick(HtmlElement elem){
		MouseEvent event = new MouseEvent(elem , MouseEvent.TYPE_CLICK , false ,false, false, BUTTON_LEFT );
		elem.fireEvent(event);
	}
	
	public void mouseClickOnFocusedElement(int button , boolean altKey , boolean shiftKey , boolean ctrlKey){
		HtmlElement focusedElement = getFocusedElement();
		MouseEvent event = new MouseEvent( focusedElement, MouseEvent.TYPE_CLICK , shiftKey ,ctrlKey, altKey, button );
		focusedElement.fireEvent(event);
	}
	
	public void mouseDblClick(HtmlElement elem){
		MouseEvent event = new MouseEvent(elem , MouseEvent.TYPE_DBL_CLICK , false ,false, false, BUTTON_LEFT );
		elem.fireEvent(event);
	}
	
	public void mouseDblClick(HtmlElement elem , int button , boolean altKey , boolean shiftKey , boolean ctrlKey){
		MouseEvent event = new MouseEvent(elem , MouseEvent.TYPE_CLICK, shiftKey ,ctrlKey, altKey, button );
		elem.fireEvent(event);
		elem.fireEvent(event);
		event = new MouseEvent(elem , MouseEvent.TYPE_DBL_CLICK , shiftKey ,ctrlKey, altKey, button );
		elem.fireEvent(event);
	}
	
	public void mouseDblClickOnFocusedElement(int button , boolean altKey , boolean shiftKey , boolean ctrlKey){
		HtmlElement focusedElement = getFocusedElement();
		MouseEvent event = new MouseEvent(focusedElement , MouseEvent.TYPE_CLICK, shiftKey ,ctrlKey, altKey, button );
		focusedElement.fireEvent(event);
		focusedElement.fireEvent(event);
		event = new MouseEvent(focusedElement , MouseEvent.TYPE_DBL_CLICK , shiftKey ,ctrlKey, altKey, button );
		focusedElement.fireEvent(event);
	}
	
	public void mouseMove(HtmlElement elem , int button ,  boolean altKey , boolean shiftKey , boolean ctrlKey){
		MouseEvent event = new MouseEvent(elem , MouseEvent.TYPE_MOUSE_MOVE , shiftKey ,ctrlKey, altKey, button );
		elem.fireEvent(event);
	}  
	
	public void dragElementTo(HtmlElement toDrag , HtmlElement toDropOn , boolean ctrlKey ){
		HtmlElement fromNeighbor = getParentDnDSource(toDrag);
		HtmlElement toNeighbor = getNeighbor(toDropOn);
		Page page = toDrag.getPage();
		Page page2 = window.getEnclosedPage();
		toDrag.mouseOver(false, ctrlKey, false, MouseEvent.BUTTON_LEFT);
		toDrag.mouseDown(false, ctrlKey, false, MouseEvent.BUTTON_LEFT);
		toDrag.mouseMove(false, ctrlKey, false, MouseEvent.BUTTON_LEFT);
	//	waitForBackgroundTasksToComplete(500);
		fromNeighbor.mouseOver(false, ctrlKey, false, MouseEvent.BUTTON_LEFT);
		fromNeighbor.mouseMove(false, ctrlKey, false, MouseEvent.BUTTON_LEFT);
		fromNeighbor.mouseOut(false, ctrlKey, false, MouseEvent.BUTTON_LEFT);
		toNeighbor.mouseOver(false, ctrlKey, false, MouseEvent.BUTTON_LEFT);
		toNeighbor.mouseMove(false, ctrlKey, false, MouseEvent.BUTTON_LEFT);
		toNeighbor.mouseOut(false, ctrlKey, false, MouseEvent.BUTTON_LEFT);
		toDropOn.mouseOver(false, ctrlKey, false, MouseEvent.BUTTON_LEFT);
		toDropOn.mouseMove(false, ctrlKey, false, MouseEvent.BUTTON_LEFT);
		toDropOn.mouseUp(false , ctrlKey , false, MouseEvent.BUTTON_LEFT);
	}
	
	private HtmlElement getNeighbor(HtmlElement element){
		DomNode tempneighbor = (DomNode) element.getNextSibling();
		HtmlElement neighbor = (HtmlElement) ((tempneighbor instanceof HtmlElement)? tempneighbor : null); 
		tempneighbor =  (neighbor == null ? element.getPreviousSibling() : neighbor);
		neighbor = (HtmlElement) ((tempneighbor instanceof HtmlElement)? tempneighbor : null);
		tempneighbor =  (neighbor == null ? element.getParentNode() : neighbor);
		neighbor = (HtmlElement) ((tempneighbor instanceof HtmlElement)? tempneighbor : null); 
		return neighbor;
	}
	
	private HtmlElement getParentDnDSource(HtmlElement element){
		HtmlElement parent = (HtmlElement)element.getParentNode();
		if(parent.getAttribute("class").contains("dojoDndSource")){
			return parent;
		}else{
			return getParentDnDSource(parent);
		}
	}
	
//	public void waitForBackgroundTasksToComplete(int timeout){
//		window.getThreadManager().joinAll(timeout);
//	}
	
	public void waitForBackgroundTasksToComplete(int timeout){
         //do nothing
   }
	
	public void typeOnFocusedElement(String toType , boolean clearItBefore){
		typeOnElement(getFocusedElement() , toType , clearItBefore);
	}
	
	public void typeOnElement(HtmlElement elem , String toType , boolean clearItBefore){
		if(clearItBefore){
			elem.setAttribute("value","");
			//waitForBackgroundTasksToComplete(200);
		}
		try {
			elem.type(toType);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public HtmlElement getFocusedElement(){
		HtmlElement focusedElement = ((HtmlPage)window.getEnclosedPage()).getFocusedElement();
		return focusedElement;
	}
	
	public void toggleJSAlertLogging(boolean toggle){
			if(toggle){
				window.getWebClient().setAlertHandler(alertHandler);
			}else{
				window.getWebClient().setAlertHandler(null);
			}
	}
	
	 
	public String normalizeSVG(String svg){
		svg = svg.toLowerCase();
		svg = svg.replace("\r\n","");
		svg = svg.replaceAll(">\\s*<", "><");
		svg = svg.replaceAll("\\s*/>", "/>");
		System.out.println("BEFORE NORMALIZE : \n" + svg);
		//Replace all numeric values by 
		svg = svg.replaceAll("<(\\w*)\\s*.*?([\\>>])","<$1$2");
		System.out.println("AFTER NORMALIZE : \n" + svg);
		return svg;
	}
	
	
}
