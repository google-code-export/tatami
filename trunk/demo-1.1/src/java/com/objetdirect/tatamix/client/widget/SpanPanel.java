package com.objetdirect.tatamix.client.widget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;

public class SpanPanel extends FlowPanel {

	public SpanPanel() {
		super();
		setElement(DOM.createSpan());
	}

}
