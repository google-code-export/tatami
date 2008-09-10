package com.objetdirect.tatami.unit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.javascript.host.Access;
import com.gargoylesoftware.htmlunit.javascript.host.ComputedCSSStyleDeclaration;
import com.gargoylesoftware.htmlunit.javascript.host.HTMLElement;
import com.gargoylesoftware.htmlunit.javascript.host.MouseEvent;

public class WeirdTestGWT {
final private WebWindow window;
	
	public static final int BUTTON_LEFT = MouseEvent.BUTTON_LEFT;
	
	public static final int BUTTON_MIDDLE = MouseEvent.BUTTON_MIDDLE;
	
	public static final int BUTTON_RIGHT = MouseEvent.BUTTON_RIGHT;

	
	public WeirdTestGWT(WebWindow window){
		this.window = window;
	}
	
	public void mouseDown(int x , int y , int button , boolean altKey , boolean shiftKey , boolean ctrlKey){
		MouseEvent event = new MouseEvent();
		event.jsxFunction_initMouseEvent(MouseEvent.TYPE_MOUSE_DOWN, false, false, window, 1, x, y, x, y, ctrlKey, altKey, shiftKey, false, button, window);
	}
	
	public HtmlElement locateHtmlElement(HtmlElement element ,int x , int y){
		Iterable<HtmlElement> iterable = element.getAllHtmlChildElements();
		List<HtmlElement> matchingElems = new ArrayList<HtmlElement>();
		for (HtmlElement elem : iterable) {
			HTMLElement jsElem = (HTMLElement) elem.getScriptObject();
			int top = getAbsoluteTop(jsElem);
			int left = getAbsoluteLeft(jsElem);
			ComputedCSSStyleDeclaration elemCss = jsElem.jsxGet_currentStyle();
			
			int width = Access.getCalculatedWidth(elemCss, true, true);
			int height = 0;
			System.out.println("ELEMENT " + elem.getId() + " OF TYPE " + elem.getTagName() + " TOP = " + top + " LEFT = " + left + " WIDTH = " + width + " HEIGHT = " + height );
			if (x >= left && x <= left + width && y >= top && y <= top + height) {
				List<HtmlElement> temporaryMatchingElems = new ArrayList<HtmlElement>();
				temporaryMatchingElems.add(elem);
				temporaryMatchingElems.add(locateHtmlElement(elem, x, y));
				matchingElems.add(determineHigherElement(temporaryMatchingElems));
			}
		}
		return determineHigherElement(matchingElems);
	}
	
	private int getWidth(HTMLElement elem){
		ComputedCSSStyleDeclaration css =elem.jsxGet_currentStyle();
		int width = intValue(css.jsxGet_width());
        final int borderLeft = intValue(css.jsxGet_borderLeftWidth());
        final int borderRight = intValue(css.jsxGet_borderRightWidth());
        width += borderLeft + borderRight;
        final int paddingLeft = intValue(css.jsxGet_paddingLeft());
        final int paddingRight = intValue(css.jsxGet_paddingRight());
        width += paddingLeft + paddingRight;
        return width;
	}
	
	private int intValue(final String value) {
        return NumberUtils.toInt(value.replaceAll("(\\d+).*", "$1"), 0);
    }
	
	private HtmlElement determineHigherElement(List<HtmlElement> elems){
		HtmlElement higherElement = null;
		int higherZIndex = 0;
		for (Iterator iterator = elems.iterator(); iterator.hasNext();) {
			HtmlElement htmlElement = (HtmlElement) iterator.next();
			if(higherElement == null){
				higherElement = htmlElement;
			}else{
				//TODO follow css z index style rules
			}
		}
		return higherElement;
	}
	
	private int getAbsoluteLeft(HTMLElement element){
		int cumulativeOffset = 0;
		element.getBoxObject().jsxGet_screenX();
        while (element != null) {
            cumulativeOffset += element.jsxGet_offsetLeft();
            element = element.jsxGet_offsetParent();
        }
        return cumulativeOffset;
	}
	
	private int getAbsoluteTop(HTMLElement element){
		int cumulativeOffset = 0;
        while (element != null) {
            cumulativeOffset += element.jsxGet_offsetTop();
            element = element.jsxGet_offsetParent();
        }
        return cumulativeOffset;
	}
}
