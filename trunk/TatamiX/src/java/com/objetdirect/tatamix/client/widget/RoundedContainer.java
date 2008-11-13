package com.objetdirect.tatamix.client.widget;

import java.util.Iterator;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class RoundedContainer extends Composite {


	private FlowPanel layout;
	private FlowPanel content;

	public RoundedContainer() {

	   layout = new FlowPanel();
	   initWidget(layout);
	   setStylePrimaryName("block");

	   FlowPanel leftCorner = new FlowPanel();
	   leftCorner.setStylePrimaryName("leftRounded");
	   FlowPanel center = new FlowPanel();
	   center.setStylePrimaryName("centerRounded");
	   FlowPanel rightCorner = new FlowPanel();
	   rightCorner.setStylePrimaryName("rightRounded");
	   content = new FlowPanel();
	   content.setStylePrimaryName("contentRounded");

	   rightCorner.add(content);
       leftCorner.add(rightCorner);
       center.add(leftCorner);
	   layout.add(center);

	}


	public int getWidgetCount() {
		return content.getWidgetCount();
	}

	public int getWidgetIndex(Widget w) {
		return content.getWidgetIndex(w);
	}

	public boolean remove(Widget w) {
		return content.remove(w);
	}

	public boolean remove(int index)  {
		return content.remove(index);
	}




	public Iterator iterator() {
		return content.iterator();
	}

	public void addWidget(Widget w) {
		this.content.add(w);
	}

	public void insert(Widget w,int beforeIndex) {
		this.content.insert(w, beforeIndex);
	}

}
