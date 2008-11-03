package com.objetdirect.tatami.client.charting;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.objetdirect.tatami.client.AbstractDojo;
import com.objetdirect.tatami.client.DojoAfterCreationEventsSource;
import com.objetdirect.tatami.client.DojoAfterCreationListener;

public class Legend extends AbstractDojo implements DojoAfterCreationListener{

	private Chart2D chart;
	private boolean isChartInited = false;
	private boolean horizontal = true;
	private Element dojoElem = DOM.createTable();
	
	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public Legend(Chart2D chart){
		this.chart = chart;
		getElement().appendChild(dojoElem);
		chart.addAfterCreationListener(this);
	}
	

	public Legend(Chart2D chart , boolean horizontal){
		this(chart);
		this.horizontal = horizontal;
	}
	
	public void createDojoWidget() throws Exception {
		if(isChartInited){
			dojoWidget = createDojoLegend(horizontal, chart.getDojoWidget() , dojoElem);
		}
	}

	public String getDojoName() {
		return "dojox.charting.widget.Legend";
	}

	public void dojoAfterCreation(DojoAfterCreationEventsSource source) {
		isChartInited = true;
		if(isAttached()){
			dojoWidget = createDojoLegend(horizontal, chart.getDojoWidget() , dojoElem);
		}
	}
	
	private native JavaScriptObject createDojoLegend(boolean horizontal , JavaScriptObject chart , JavaScriptObject element)/*-{
		var legend = new $wnd.dojox.charting.widget.Legend({chart:chart,horizontal: horizontal},element);
		return legend;
	}-*/;

}
