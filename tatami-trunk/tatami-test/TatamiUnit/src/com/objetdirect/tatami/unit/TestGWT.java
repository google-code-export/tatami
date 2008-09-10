package com.objetdirect.tatami.unit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.w3c.dom.NamedNodeMap;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.MouseEvent;

public class TestGWT {
	
final private WebWindow window;
	
	public static final int BUTTON_LEFT = MouseEvent.BUTTON_LEFT;
	
	public static final int BUTTON_MIDDLE = MouseEvent.BUTTON_MIDDLE;
	
	public static final int BUTTON_RIGHT = MouseEvent.BUTTON_RIGHT;

	boolean isLoggingAlert = false;
	
	AlertHandler alertHandler = new AlertHandler() {
		@Override
		public void handleAlert(Page arg0, String arg1) {
			System.out.println("Alert : " + arg1);
		}
	};
	
	public TestGWT(WebWindow window){
		this.window = window;
	}
	
	
	public void mouseDown(HtmlElement elem , int button , boolean altKey , boolean shiftKey , boolean ctrlKey){
		MouseEvent event = new MouseEvent(elem , MouseEvent.TYPE_MOUSE_DOWN , shiftKey ,ctrlKey, altKey, button );
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
	
	public void mouseClickOnFocusedElement(int button , boolean altKey , boolean shiftKey , boolean ctrlKey){
		HtmlElement focusedElement = getFocusedElement();
		MouseEvent event = new MouseEvent( focusedElement, MouseEvent.TYPE_CLICK , shiftKey ,ctrlKey, altKey, button );
		focusedElement.fireEvent(event);
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
	
	public void waitForBackgroundTasksToComplete(int timeout){
		window.getThreadManager().joinAll(timeout);
	}
	
	public void typeOnFocusedElement(String toType , boolean clearItBefore){
		typeOnElement(getFocusedElement() , toType , clearItBefore);
	}
	
	public void typeOnElement(HtmlElement elem , String toType , boolean clearItBefore){
		if(clearItBefore){
			elem.setAttribute("value","");
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
	
	private void toggleJSAlertLogging(){
			if(isLoggingAlert){
				window.getWebClient().setAlertHandler(null);
				isLoggingAlert = false;
			}else{
				window.getWebClient().setAlertHandler(alertHandler);
				isLoggingAlert = true;
			}
	}
	
}
