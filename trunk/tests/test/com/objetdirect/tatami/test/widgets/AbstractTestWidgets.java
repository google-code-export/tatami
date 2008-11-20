package com.objetdirect.tatami.test.widgets;

import java.util.Iterator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.objetdirect.tatami.unit.TatamiTestCase;
import com.objetdirect.tatami.unit.TestGWT;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper;
import com.objetdirect.tatami.unit.wrappers.TreeWrapper.TreeNode;

public abstract class AbstractTestWidgets extends TatamiTestCase {

	@Override
	final protected String getPageName() {
		return "http://localhost:7778/com.objetdirect.tatami.testpages.TestWidgets/testWidgets.html";
	}
	
}