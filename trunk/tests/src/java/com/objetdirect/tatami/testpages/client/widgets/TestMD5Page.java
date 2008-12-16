package com.objetdirect.tatami.testpages.client.widgets;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.encoding.MD5;
import com.objetdirect.tatami.testpages.client.TestPage;

public class TestMD5Page extends TestPage {

	MD5 md5 = MD5.getInstance();
	
	public TestMD5Page(){
		super(TestMD5Page.class.getName(),"Test MD5");
		md5.init();
	}
	
	@Override
	public Widget getTestPage() {
		final FlowPanel panel = new FlowPanel();
		final TextBox input = new TextBox();
		panel.add(input);
		final HTML output = new HTML();
		Button apply = new Button("Generate MD5!",new ClickListener(){
			public void onClick(Widget sender) {
				output.setHTML(MD5.getInstance().encode(input.getText(),MD5.HexOutputType));
			}
		}); 
		panel.add(apply);
		DOM.setElementAttribute(output.getElement(), "id", "OUTPUT");
		DOM.setElementAttribute(input.getElement(), "id", "INPUT");
		DOM.setElementAttribute(apply.getElement(), "id", "APPLY");
		panel.add(output);
		return panel;
	}

}
