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
 * Authors:  Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s):
 */
package com.objetdirect.tatami.testpages.client;

import java.util.Arrays;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.charting.Axis;
import com.objetdirect.tatami.client.charting.BarPlot;
import com.objetdirect.tatami.client.charting.Chart2D;
import com.objetdirect.tatami.client.charting.Plot;
import com.objetdirect.tatami.client.charting.Serie;

public class TestChartLabelsPage extends TestPage{


	protected TestChartLabelsPage() {
		super("com.objetdirect.tatami.testpages.client.TestChartLabelsPage", "Test Charts Labels");
	}
	
	@Override
	public Widget getTestPage() {
		Panel panel = new FlowPanel();
		panel.add(getChart1());
		panel.add(getChart2());
		return panel;
	}
	
	public Chart2D getChart1(){
		Chart2D chart = new Chart2D("200px","200px");
		
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setFixLower("major");
		xAxis.setFixUpper("major");
		xAxis.setIncludeZero(true);
		
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setFixLower("major");
		yAxis.setFixUpper("major");
		yAxis.setNatural(true);

		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		
		Plot<Integer> plot = new BarPlot<Integer>(BarPlot.PLOT_TYPE_BARS);
		
		Serie<Integer> serie1 = new Serie<Integer>(Arrays.asList(new Integer[]{1,2,3,4,5}));
		serie1.setStrokeColor("red");
		serie1.setFillColor("lightpink");
		
		Serie<Integer> serie2 = new Serie<Integer>(Arrays.asList(new Integer[]{5,4,3,2,1}));
		serie2.setStrokeColor("blue");
		serie2.setFillColor("lightblue");
		
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		chart.addPlot(plot);
		
		return chart;
	}
	
	
	public Chart2D getChart2(){
		Chart2D chart = new Chart2D("200px","200px");
		
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setFixLower("major");
		xAxis.setFixUpper("major");
		xAxis.setIncludeZero(true);
		
		xAxis.addLabel("two", 2);
		xAxis.addLabel("four", 4);
		
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setFixLower("major");
		yAxis.setFixUpper("major");
		yAxis.setNatural(true);

		yAxis.addLabel("Jan", 1);
		yAxis.addLabel("Feb", 2);
		yAxis.addLabel("Mar", 3);
		yAxis.addLabel("Apr", 4);
		yAxis.addLabel("May", 5);
		yAxis.addLabel("Jun", 6);
		
		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		Plot<Integer> plot = new BarPlot<Integer>(BarPlot.PLOT_TYPE_BARS);
		
		Serie<Integer> serie1 = new Serie<Integer>(Arrays.asList(new Integer[]{1,2,3,4,5}));
		serie1.setStrokeColor("red");
		serie1.setFillColor("lightpink");
		
		Serie<Integer> serie2 = new Serie<Integer>(Arrays.asList(new Integer[]{5,4,3,2,1}));
		serie2.setStrokeColor("blue");
		serie2.setFillColor("lightblue");
		
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		chart.addPlot(plot);
		
		return chart;
	}

}
