package com.objetdirect.tatamix.client.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class SpanText extends Widget implements HasHTML, HasText {

    private String text;

	public SpanText() {
		super();
		setElement(Document.get().createSpanElement());
	}

	public String getHTML() {
		return getElement().getInnerHTML();
	}

	public void setHTML(String html) {
		this.text = html;
		getElement().setInnerHTML(text);

	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
		if (text != null) {
			getElement().setInnerText( text);
		}

	}

}
