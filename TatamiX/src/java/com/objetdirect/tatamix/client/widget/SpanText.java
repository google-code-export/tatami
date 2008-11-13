package com.objetdirect.tatamix.client.widget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class SpanText extends Widget implements HasHTML, HasText {

    private String text;

	public SpanText() {
		super();
		setElement(DOM.createSpan());
	}

	public String getHTML() {
		return DOM.getInnerHTML(getElement());
	}

	public void setHTML(String html) {
		this.text = html;
		DOM.setInnerHTML(getElement(), text);

	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
		if (text != null) {
			DOM.setInnerText(getElement(), text);
		}

	}

}
