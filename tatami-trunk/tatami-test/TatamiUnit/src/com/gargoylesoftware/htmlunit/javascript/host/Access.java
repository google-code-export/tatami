package com.gargoylesoftware.htmlunit.javascript.host;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Access {

	public static int getCalculatedWidth(ComputedCSSStyleDeclaration css , boolean includeBorder , boolean includePadding){
		return css.getCalculatedWidth(includeBorder, includePadding);
	}
	
	
}
