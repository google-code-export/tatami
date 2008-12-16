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
package com.objetdirect.tatami.testpages.client.charting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.charting.Axis;
import com.objetdirect.tatami.client.charting.AxisLabel;
import com.objetdirect.tatami.client.charting.BarPlot;
import com.objetdirect.tatami.client.charting.Chart2D;
import com.objetdirect.tatami.client.charting.Legend;
import com.objetdirect.tatami.client.charting.Plot2D;
import com.objetdirect.tatami.client.charting.Point;
import com.objetdirect.tatami.client.charting.Serie;
import com.objetdirect.tatami.client.charting.Themes;
import com.objetdirect.tatami.testpages.client.TestPage;

public class TestChartAxesPage extends TestPage{

	protected TestChartAxesPage() {
		super(TestChartAxesPage.class.getName(), "Test Charts Axes");
	}
	@Override
	public Widget getTestPage() {
		FlowPanel panel = new FlowPanel();
		panel.add(getChart1());
		panel.add(getChart2());
		panel.add(getChart3());
		return panel;
	}

	
	public Widget getChart1(){
		FlowPanel panel = new FlowPanel();
		Chart2D chart = new Chart2D("500px","500px");
		
		//Constructing the Column plot
		BarPlot<Integer> columns = new BarPlot<Integer>(BarPlot.PLOT_TYPE_COLUMNS);
		
		
		//We add the serie to the column plot
		columns.addSerie(getColumnSerie());
		
		//Constructing the line plot
		Plot2D<Point> lines = new Plot2D<Point>(Plot2D.PLOT_TYPE_MARKERS_LINES);
		
		
		//We add the serie to the column plot
		lines.addSerie(getLineSerie());
		
		//The plot are added to the chart
		chart.addPlot(lines);
		chart.addPlot(columns);
		
		//The chart will have default X and Y axis
		chart.setDefaultXAxis(Axis.simpleXAxis());
		chart.setDefaultYAxis(Axis.simpleYAxis());
		
		//We override this setting for the line plot
		//(The getLineXAxis and getLineYAxis are not shown 
		// here, but will be presented in the "Axis options" section)
		lines.setXAxis(getLineXAxis());
		lines.setYAxis(getLineYAxis());
		panel.add(chart);
		panel.add(new Legend(chart));
		DOM.setElementAttribute(chart.getElement(), "id", "chart1");
		
		return panel;
	}
	
	public Serie<Point> getLineSerie(){
		Serie<Point> linesSerie = new Serie<Point>();
		linesSerie.addData(new Point(12.,41.));
		linesSerie.addData(new Point(25.,68.));
		linesSerie.addData(new Point(48.,21));
		linesSerie.addData(new Point(72,10));
		linesSerie.addData(new Point(121,142));
		//Simple Serie options, see the "Series" section for more info
		linesSerie.setStrokeColor("red");
		return linesSerie;
	}
	
	public Serie<Integer> getColumnSerie(){
		//Constructing its series
		Serie<Integer> columnsSerie = new Serie<Integer>();
		columnsSerie.addData(4);
		columnsSerie.addData(3);
		columnsSerie.addData(2);
		columnsSerie.addData(9);
		columnsSerie.addData(3);
		columnsSerie.addData(12);
		columnsSerie.addData(7);
		columnsSerie.addData(18);
		columnsSerie.addData(6);
		columnsSerie.addData(5);
		columnsSerie.addData(15);
		columnsSerie.addData(7);
		//Simple Serie options, see the "Series" section for more info
		return columnsSerie;
	}
	
	public Chart2D getChart2(){
		Chart2D chart = new Chart2D("500px","100px");
		Plot2D<Point> lines = new Plot2D<Point>(Plot2D.PLOT_TYPE_MARKERS_LINES);
		lines.addSerie(getLineSerie());
		chart.addPlot(lines);
		Axis xLinesAxis = new Axis(Axis.BOTTOM | Axis.HORIZONTAL);
		xLinesAxis.setFixed(true);
		xLinesAxis.setFixLower(Axis.FIX_TYPE_MAJOR);
		xLinesAxis.setFixUpper(Axis.FIX_TYPE_MICRO);
		xLinesAxis.setFont("normal normal bold 12pt Tahoma");
		xLinesAxis.setFontColor("green");
		xLinesAxis.setMajorTickStep(50);
		xLinesAxis.setMajorTicksLength(15);
		xLinesAxis.setMajorTicksColor("blue");
		xLinesAxis.setMinorTickStep(20);
		xLinesAxis.setMinorTicksLength(10);
		xLinesAxis.setMinorLabels(true);
		xLinesAxis.setMinorTicksColor("red");
		xLinesAxis.setMicroTicks(true);
		xLinesAxis.setMicroTickStep(10);
		xLinesAxis.setMicroTicksLength(5);
		xLinesAxis.setMicroTicksColor("green");
		lines.setXAxis(xLinesAxis);
		
		DOM.setElementAttribute(chart.getElement(), "id", "chart2");
		lines.setXAxis(xLinesAxis);
		return chart;
	}
	
	public Chart2D getChart3(){
		Chart2D chart = new Chart2D("300px","300px");
		Plot2D<Point> line = new Plot2D<Point>(Plot2D.PLOT_TYPE_MARKERS_LINES);
		line.addSerie(new Serie<Point>(Arrays.asList(new Point[]{new Point(-10,5),new Point(-5,3),new Point(0,3),new Point(5,6)})));
		chart.addPlot(line);
		chart.setTheme(Themes.Adobebricks);
		Axis xLinesAxis = new Axis(Axis.BOTTOM | Axis.HORIZONTAL);
		line.setXAxis(xLinesAxis);
		xLinesAxis.addLabels(getDiscreteLabels());
		xLinesAxis.setMin(-10);
		xLinesAxis.setMax(10);
		xLinesAxis.setMajorTickStep(10);
		xLinesAxis.setMinorTicks(false);
		xLinesAxis.setFixUpper(Axis.FIX_TYPE_MAJOR);
		xLinesAxis.setFixLower(Axis.FIX_TYPE_MAJOR);
		line.setXAxis(xLinesAxis);
		DOM.setElementAttribute(chart.getElement(), "id", "chart3");
		return chart;
	}
	public List<AxisLabel> getDiscreteLabels(){
		List<AxisLabel> labels = new ArrayList<AxisLabel>();
		labels.add(new AxisLabel("Zero",0));
		labels.add(new AxisLabel("Max",10));
		labels.add(new AxisLabel("Min",-10));
		return labels;
	}

	public String[] getLabels(){
		return new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};
	}
	
	public Axis getLineXAxis(){
		Axis lineXAxis = new Axis(Axis.HORIZONTAL | Axis.BOTTOM); 
		lineXAxis.setMajorTickStep(20);
		lineXAxis.setMinorTicks(false);
		lineXAxis.setMajorTicksColor("red");
		lineXAxis.setFontColor("red");
		return lineXAxis;
	}
	
	public Axis getLineYAxis(){
		Axis lineYAxis = new Axis(Axis.VERTICAL | Axis.RIGHT);
		lineYAxis.setMajorTicksColor("red");
		lineYAxis.setMinorTicks(false);
		lineYAxis.setMajorTickStep(20);
		lineYAxis.setFontColor("red");
		return lineYAxis;
	}
	
}
