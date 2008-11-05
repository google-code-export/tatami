/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@googlegroups.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * Authors: Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s): 
 */
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
