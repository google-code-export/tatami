package com.objetdirect.tatamix.client.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasHTML;

public class Paragraph extends FocusWidget implements HasHTML  {

	private String text;


	public Paragraph() {
		super();
		setElement(Document.get().createPElement());
		
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
			getElement().setInnerText(text);
		}

	}

	

}
