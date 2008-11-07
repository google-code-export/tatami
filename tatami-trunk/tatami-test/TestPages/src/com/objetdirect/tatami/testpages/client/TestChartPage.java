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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.charting.Axis;
import com.objetdirect.tatami.client.charting.BarPlot;
import com.objetdirect.tatami.client.charting.Bubble;
import com.objetdirect.tatami.client.charting.BubblePlot;
import com.objetdirect.tatami.client.charting.Chart2D;
import com.objetdirect.tatami.client.charting.Plot;
import com.objetdirect.tatami.client.charting.Plot2D;
import com.objetdirect.tatami.client.charting.Point;
import com.objetdirect.tatami.client.charting.Serie;

public class TestChartPage extends TestPage {

	protected TestChartPage() {
		super("com.objetdirect.tatami.testpages.client.TestChartPage", "Test Charts");
	}

	@Override
	public Widget getTestPage() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML("<span>1: Defaults: lines, no axes.</span>"));
		panel.add(getChart1());
		panel.add(new HTML("<span>2: Defaults: lines, no axes, and custom strokes.</span>"));
		panel.add(getChart2());
		panel.add(new HTML("<span>3: Areas, Happy theme, no axes.</span>"));
		panel.add(getChart3());
		panel.add(new HTML("<span>4: Areas, no axes, custom strokes and fills.</span>"));
		panel.add(getChart4());
		panel.add(new HTML("<span>5: Lines, axes, blue theme.</span>"));
		panel.add(getChart5());
		panel.add(new HTML("<span>6: Lines, axes (aligned on minor ticks), cyan theme.</span>"));
		panel.add(getChart6());
		panel.add(new HTML("<span>7: Lines, axes (aligned on major ticks), green theme.</span>"));
		panel.add(getChart7());
		panel.add(new HTML("<span>8: Lines and markers, no axes, purple theme, custom min/max.</span>"));
		panel.add(getChart8());
		panel.add(new HTML("<span>9: Markers only, no axes, custom theme, custom markers, custom min/max.</span>"));
		panel.add(getChart9());
		panel.add(new HTML("<span>10: Lines and markers, shadows, no axes, custom theme, custom markers, custom min/max.</span>"));
		panel.add(getChart10());
		panel.add(new HTML("<span>11: Stacked lines, markers, shadows, no axes, custom strokes, fills, and markers.</span>"));
		panel.add(getChart11());
		panel.add(new HTML("<span>12: Stacked areas, axes (aligned on major ticks), custom strokes and fills.</span>"));
		panel.add(getChart12());
		panel.add(new HTML("<span>13: Columns, no axes, custom strokes and fills.</span>"));
		panel.add(getChart13());
		panel.add(new HTML("<span>14: Columns with gaps beetwen them, vertical axis aligned on major ticks, custom strokes, fills.</span>"));
		panel.add(getChart14());
		panel.add(new HTML("<span>15: Stacked columns, no axes, custom strokes and fills.</span>"));
		panel.add(getChart15());
		panel.add(new HTML("<span>16: Bars, axes aligned on major ticks, no minor ticks, custom strokes and fills.</span>"));
		panel.add(getChart16());
		panel.add(new HTML("<span>17: Stacked bars, no axes, custom strokes and fills.</span>"));
		panel.add(getChart17());
		panel.add(new HTML("<span>18: Clustered columns, custom axes, custom strokes, fills, and gap.</span>"));
		panel.add(getChart18());
		panel.add(new HTML("<span>19: Clustered bars, custom axes, custom strokes, fills, and gap.</span>"));
		panel.add(getChart19());
		panel.add(new HTML("<span>20: Columns with gaps beetwen them, grids, custom strokes, fills, axes.</span>"));
		panel.add(getChart20());
		panel.add(new HTML("<span>21: Columns with gaps beetwen them, grids, custom strokes, fills, axes, with min=0, max=8, and manually specified ticks on the vertical axis.</span>"));
		panel.add(getChart21());
		panel.add(new HTML("<span>22: Columns with positive and negative values, axes, and grid.</span>"));
		panel.add(getChart22());
		panel.add(new HTML("<span>23: Clustered columns with positive and negative values, axes, and grid.</span>"));
		panel.add(getChart23());
		panel.add(new HTML("<span>24: Bars with positive and negative values, axes, and grid."));
		panel.add(getChart24());
		panel.add(new HTML("<span>25: Clustered bars with positive and negative values, axes, and grid.</span>"));
		panel.add(getChart25());
		panel.add(new HTML("<span>26: Default lines with 2D data, custom axis, red theme.</span>"));
		panel.add(getChart26());
		panel.add(new HTML("<span>27: Scatter chart, custom axis, purple theme.</span>"));
		panel.add(getChart27());
		panel.add(new HTML("<span>28: Markers, lines, 2D data, custom axis, blue theme.</span>"));
		panel.add(getChart28());
		panel.add(new HTML("<span>29: Clustered columns with positive and negative values, readable theme.</span>"));
		panel.add(getChart29());
		panel.add(new HTML("<span>30: Bubble chart, green theme.</span>"));
		panel.add(getChart30());
		return panel;
	}
	
	private List<Integer> getSampleUniDimensionalData(){
		List<Integer> data = new ArrayList<Integer>();
		data.add(1);
		data.add(2);
		data.add(1);
		data.add(2);
		data.add(1);
		data.add(2);
		data.add(1);
		return data;
	}
	
	private List<Integer> getSampleUniDimensionalData2(){
		List<Integer> data = new ArrayList<Integer>();
		data.add(2);
		data.add(1);
		data.add(2);
		data.add(1);
		data.add(2);
		data.add(1);
		data.add(2);
		return data;
	}
	
	private Chart2D getChart1(){
		Chart2D lineChart = new Chart2D("400px", "200px");
		Plot<Integer> plot = new Plot<Integer>();
		plot.addSerie(new Serie<Integer>(getSampleUniDimensionalData()));
		plot.addSerie(new Serie<Integer>(getSampleUniDimensionalData2()));
		lineChart.addPlot(plot);
		
		lineChart.setSize("400px", "200px");
		DOM.setElementAttribute(lineChart.getElement(), "id", "chart1");
		return lineChart;
	}
	
	private Chart2D getChart2(){
		Chart2D lineChart = new Chart2D("200px", "200px");
		Plot2D<Integer> linePlot = new Plot2D<Integer>(Plot2D.PLOT_TYPE_LINES);
		Serie<Integer> mySerie1 = new Serie<Integer>(getSampleUniDimensionalData());
		mySerie1.setStrokeColor("red");
		Serie<Integer> mySerie2 = new Serie<Integer>(getSampleUniDimensionalData2());
		mySerie2.setStrokeColor("blue");
		linePlot.addSerie(mySerie1);
		linePlot.addSerie(mySerie2);
		lineChart.addPlot(linePlot);
		DOM.setElementAttribute(lineChart.getElement(), "id", "chart2");
		return lineChart;
	}

	private Chart2D getChart3(){
		Chart2D chart = new Chart2D("400px", "400px");
		Plot2D<Double> plot = new Plot2D<Double>(Plot2D.PLOT_TYPE_AREAS);
		Double[]data1 = new Double[]{1., 2., 0.5, 1.5, 1., 2.8, 0.4};
		Double[]data2 = new Double[]{2.6, 1.8, 2., 1., 1.4, 0.7, 2.};
		Double[]data3 = new Double[]{6.3, 1.8, 3., 0.5, 4.4, 2.7, 2.};
		plot.addSerie(new Serie<Double>(Arrays.asList(data1)));
		plot.addSerie(new Serie<Double>(Arrays.asList(data2)));
		plot.addSerie(new Serie<Double>(Arrays.asList(data3)));
		plot.setTension("X");
		chart.addPlot(plot);
		chart.setTheme("Shrooms");
		DOM.setElementAttribute(chart.getElement(), "id", "chart3");
		return chart;
	}
	
	private Chart2D getChart4(){
		Chart2D chart = new Chart2D("400px", "200px");
		Plot2D<Integer> plot = new Plot2D<Integer>(Plot2D.PLOT_TYPE_AREAS);
		Serie<Integer> mySerie1 = new Serie<Integer>(getSampleUniDimensionalData());
		mySerie1.setFillColor("lightpink");
		mySerie1.setStrokeColor("red");
		mySerie1.setStrokeWidth(2);
		Serie<Integer> mySerie2 = new Serie<Integer>(getSampleUniDimensionalData2());
		mySerie2.setFillColor("lightblue");
		mySerie2.setStrokeColor("blue");
		mySerie2.setStrokeWidth(2);
		plot.addSerie(mySerie1);
		plot.addSerie(mySerie2);
		chart.addPlot(plot);
		DOM.setElementAttribute(chart.getElement(), "id", "chart4");
		return chart;
	}
	
	private Chart2D getChart5(){
		Chart2D chart = new Chart2D("400px", "200px");
		Plot2D<Integer> plot = new Plot2D<Integer>();
		chart.setTheme("PlotKit.blue");
		Serie<Integer> mySerie1 = new Serie<Integer>(getSampleUniDimensionalData());
		Serie<Integer> mySerie2 = new Serie<Integer>(getSampleUniDimensionalData2());
		chart.setDefaultXAxis(Axis.simpleXAxis());
		chart.setDefaultYAxis(Axis.simpleYAxis());
		plot.addSerie(mySerie1);
		plot.addSerie(mySerie2);
		chart.addPlot(plot);
		DOM.setElementAttribute(chart.getElement(), "id", "chart5");
		return chart;
	}
	
	private Chart2D getChart6(){
		Chart2D chart = new Chart2D("400px", "200px");
		chart.setTheme("PlotKit.cyan");
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setFixLower("minor");
		xAxis.setFixUpper("minor");
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setFixLower("minor");
		yAxis.setFixUpper("minor");
		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		Plot<Integer> plot = new Plot<Integer>();
		plot.addSerie(new Serie<Integer>(getSampleUniDimensionalData()));
		plot.addSerie(new Serie<Integer>(getSampleUniDimensionalData2()));
		chart.addPlot(plot);
		DOM.setElementAttribute(chart.getElement(), "id", "chart6");
		return chart;
	}
	
	
	private Chart2D getChart7(){
		Chart2D chart = new Chart2D("400px", "200px");
		chart.setTheme("PlotKit.green");
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setFixLower("major");
		xAxis.setFixUpper("major");
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setFixLower("major");
		yAxis.setFixUpper("major");
		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		Plot<Integer> plot = new Plot<Integer>();
		plot.addSerie(new Serie<Integer>(getSampleUniDimensionalData()));
		plot.addSerie(new Serie<Integer>(getSampleUniDimensionalData2()));
		chart.addPlot(plot);
		DOM.setElementAttribute(chart.getElement(), "id", "chart7");
		return chart;
	}
	
	private Chart2D getChart8(){
		Chart2D chart = new Chart2D("400px", "200px");
		chart.setTheme("SageToLime");
		Plot2D<Integer> plot = new Plot2D<Integer>(Plot2D.PLOT_TYPE_MARKERS_LINES);
		Serie<Integer> serie1 = new Serie<Integer>(getSampleUniDimensionalData());
		serie1.setMaxDisplayed(3.);
		serie1.setMinDisplayed(0.);
		Serie<Integer> serie2 = new Serie<Integer>(getSampleUniDimensionalData2());
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		chart.addPlot(plot);
		DOM.setElementAttribute(chart.getElement(), "id", "chart8");
		return chart;
	}
	
	private Chart2D getChart9(){
		Chart2D chart = new Chart2D("400px", "200px");
		chart.setTheme("SageToLime");
		Plot2D<Integer> plot = new Plot2D<Integer>(Plot2D.PLOT_TYPE_SCATTER);
		Serie<Integer> serie1 = new Serie<Integer>(getSampleUniDimensionalData());
		serie1.setMaxDisplayed(3.);
		serie1.setMinDisplayed(0.);
		serie1.setStrokeColor("red");
		serie1.setStrokeWidth(2);
		serie1.setMarkerType(Serie.MARKER_TYPE_CIRCLE);
		Serie<Integer> serie2 = new Serie<Integer>(getSampleUniDimensionalData2());
		serie2.setStrokeColor("blue");
		serie2.setStrokeWidth(2);
		serie2.setMarkerType(Serie.MARKER_TYPE_SQUARE);
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		chart.addPlot(plot);
		DOM.setElementAttribute(chart.getElement(), "id", "chart9");
		return chart;
	}

	private Chart2D getChart10(){
		Chart2D chart = new Chart2D("400px", "200px");
		Plot2D<Integer> plot = new Plot2D<Integer>(Plot2D.PLOT_TYPE_MARKERS_LINES);
		plot.setShadow(2,2,2);
		Serie<Integer> serie1 = new Serie<Integer>(getSampleUniDimensionalData());
		serie1.setMaxDisplayed(3.);
		serie1.setMinDisplayed(0.);
		serie1.setStrokeColor("red");
		serie1.setStrokeWidth(2);
		serie1.setMarkerType(Serie.MARKER_TYPE_CIRCLE);
		Serie<Integer> serie2 = new Serie<Integer>(getSampleUniDimensionalData2());
		serie2.setStrokeColor("blue");
		serie2.setStrokeWidth(2);
		serie2.setMarkerType(Serie.MARKER_TYPE_SQUARE);
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		chart.addPlot(plot);
		DOM.setElementAttribute(chart.getElement(), "id", "chart10");
		return chart;
	}
	
	private Chart2D getChart11(){
		Chart2D chart = new Chart2D("200px", "200px");
		Plot2D<Double> plot = new Plot2D<Double>(Plot2D.PLOT_TYPE_LINES_STACKED);
		plot.setShadow(2, 2, 2);
		plot.setTension("S");
		plot.setShowMarkers(true);
		Serie<Double> serie1 = new Serie<Double>(Arrays.asList(new Double[]{1., 1.1, 1.2, 1.3, 1.4, 1.5, 1.6}));
		serie1.setStrokeColor("red");
		serie1.setStrokeWidth(2);
		serie1.setFillColor("lightpink");
		serie1.setMarkerType(Serie.MARKER_TYPE_SQUARE);
		Serie<Double> serie2 = new Serie<Double>(Arrays.asList(new Double[]{1., 1.6, 1.3, 1.4, 1.1, 1.5, 1.1}));
		serie2.setStrokeColor("blue");
		serie2.setStrokeWidth(2);
		serie2.setFillColor("lightblue");
		serie2.setMarkerType(Serie.MARKER_TYPE_CIRCLE);
		Serie<Double> serie3 = new Serie<Double>(Arrays.asList(new Double[]{1., 1.1, 1.2, 1.3, 1.4, 1.5, 1.6}));
		serie3.setStrokeColor("green");
		serie3.setStrokeWidth(2);
		serie3.setFillColor("lightgreen");
		serie3.setMarkerType(Serie.MARKER_TYPE_DIAMOND);
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		plot.addSerie(serie3);
		chart.addPlot(plot);
		DOM.setElementAttribute(chart.getElement(), "id", "chart11");
		return chart;
	}
	
	private Chart2D getChart12(){
		Chart2D chart = new Chart2D("400px", "200px");
		Plot2D<Double> plot = new Plot2D<Double>(Plot2D.PLOT_TYPE_AREAS_STACKED);
		plot.setTension("S");
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setFixUpper("major");
		xAxis.setFixLower("major");
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setFixUpper("major");
		yAxis.setFixLower("major");
		yAxis.setMin(0);
		chart.addPlot(plot);
		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		Serie<Double> serie1 = new Serie<Double>(Arrays.asList(new Double[]{-2., 1.1, 1.2, 1.3, 1.4, 1.5, -1.6}));
		serie1.setStrokeColor("red");
		serie1.setStrokeWidth(2);
		serie1.setFillColor("lightpink");
		Serie<Double> serie2 = new Serie<Double>(Arrays.asList(new Double[]{1., 1.6, 1.3, 1.4, 1.1, 1.5, 1.1}));
		serie2.setStrokeColor("blue");
		serie2.setStrokeWidth(2);
		serie2.setFillColor("lightblue");
		Serie<Double> serie3 = new Serie<Double>(Arrays.asList(new Double[]{1., 1.1, 1.2, 1.3, 1.4, 1.5, 1.6}));
		serie3.setStrokeColor("green");
		serie3.setStrokeWidth(2);
		serie3.setFillColor("lightgreen");
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		plot.addSerie(serie3);
		DOM.setElementAttribute(chart.getElement(), "id", "chart12");
		return chart;
	}
	
	private Chart2D getChart13(){
		Chart2D chart = new Chart2D("400px", "200px");
		BarPlot<Integer> plot = new BarPlot<Integer>(BarPlot.PLOT_TYPE_COLUMNS);
		chart.addPlot(plot);
		Serie<Integer> serie1 = new Serie<Integer>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));
		serie1.setStrokeColor("red");
		serie1.setFillColor("lightpink");
		Serie<Integer> serie2 = new Serie<Integer>(Arrays.asList(new Integer[]{5,4,3,2,1}));
		serie2.setStrokeColor("blue");
		serie2.setFillColor("lightblue");
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		DOM.setElementAttribute(chart.getElement(), "id", "chart13");
		return chart;
	}
	private Chart2D getChart14(){
		Chart2D chart = new Chart2D("400px", "200px");
		BarPlot<Integer> plot = new BarPlot<Integer>(BarPlot.PLOT_TYPE_COLUMNS);
		plot.setGap(2);
		chart.addPlot(plot);
		Axis vAxis = Axis.simpleYAxis();
		vAxis.setFixLower("major");
		vAxis.setFixUpper("major");
		chart.setDefaultYAxis(vAxis);
		Serie<Integer> serie1 = new Serie<Integer>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));
		serie1.setStrokeColor("red");
		serie1.setFillColor("lightpink");
		Serie<Integer> serie2 = new Serie<Integer>(Arrays.asList(new Integer[]{5,4,3,2,1}));
		serie2.setStrokeColor("blue");
		serie2.setFillColor("lightblue");
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		DOM.setElementAttribute(chart.getElement(), "id", "chart14");
		return chart;
	}
	
	private Chart2D getChart15(){
		Chart2D chart = new Chart2D("400px", "200px");
		BarPlot<Integer> plot = new BarPlot<Integer>(BarPlot.PLOT_TYPE_COLUMNS_STACKED);
		plot.setGap(2);
		chart.addPlot(plot);
		Serie<Integer> serie1 = new Serie<Integer>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));
		serie1.setStrokeColor("red");
		serie1.setFillColor("lightpink");
		Serie<Integer> serie2 = new Serie<Integer>(Arrays.asList(new Integer[]{2, 1, 2, 1, 2}));
		serie2.setStrokeColor("blue");
		serie2.setFillColor("lightblue");
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		DOM.setElementAttribute(chart.getElement(), "id", "chart15");
		return chart;
	}
	
	private Chart2D getChart16(){
		Chart2D chart = new Chart2D("400px", "200px");
		BarPlot<Integer> plot = new BarPlot<Integer>(BarPlot.PLOT_TYPE_BARS);
		chart.addPlot(plot);
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setFixLower("major");
		xAxis.setFixUpper("major");
		xAxis.setIncludeZero(true);
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setFixLower("major");
		yAxis.setFixUpper("major");
		yAxis.setNatural(true);
		Serie<Integer> serie1 = new Serie<Integer>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));
		serie1.setStrokeColor("red");
		serie1.setFillColor("lightpink");
		Serie<Integer> serie2 = new Serie<Integer>(Arrays.asList(new Integer[]{5,4,3,2,1}));
		serie2.setStrokeColor("blue");
		serie2.setFillColor("lightblue");
		plot.setXAxis(xAxis);
		plot.setYAxis(yAxis);
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		DOM.setElementAttribute(chart.getElement(), "id", "chart16");
		return chart;
	} 
	
	private Chart2D getChart17(){
		Chart2D chart = new Chart2D("400px", "200px");
		BarPlot<Integer> plot = new BarPlot<Integer>(BarPlot.PLOT_TYPE_BARS_STACKED);
		chart.addPlot(plot);
		Serie<Integer> serie1 = new Serie<Integer>(Arrays.asList(new Integer[]{1,2,3,4,5}));
		serie1.setStrokeColor("red");
		serie1.setFillColor("lightpink");
		Serie<Integer> serie2 = new Serie<Integer>(Arrays.asList(new Integer[]{2,1,2,1,2}));
		serie2.setStrokeColor("blue");
		serie2.setFillColor("lightblue");
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		DOM.setElementAttribute(chart.getElement(), "id", "chart17");
		return chart;
	} 

	private Chart2D getChart18(){
		Chart2D chart = new Chart2D("400px", "200px");
		BarPlot<Integer> plot = new BarPlot<Integer>(BarPlot.PLOT_TYPE_COLUMNS_CLUSTERED);
		plot.setGap(10);
		chart.addPlot(plot);
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setFixLower("minor");
		xAxis.setFixUpper("minor");
		xAxis.setNatural(true);
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setFixLower("major");
		yAxis.setFixUpper("major");
		yAxis.setIncludeZero(true);
		plot.setXAxis(xAxis);
		plot.setYAxis(yAxis);
		Serie<Integer> serie1 = new Serie<Integer>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));
		serie1.setStrokeColor("red");
		serie1.setFillColor("lightpink");
		Serie<Integer> serie2 = new Serie<Integer>(Arrays.asList(new Integer[]{5,4,3,2,1}));
		serie2.setStrokeColor("blue");
		serie2.setFillColor("lightblue");
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		DOM.setElementAttribute(chart.getElement(), "id", "chart18");
		return chart;
	}

	private Chart2D getChart19(){
		Chart2D chart = new Chart2D("400px", "200px");
		BarPlot<Integer> plot = new BarPlot<Integer>(BarPlot.PLOT_TYPE_BARS_CLUSTERED);
		plot.setGap(5);
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setFixLower("major");
		xAxis.setFixUpper("major");
		xAxis.setIncludeZero(true);
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setFixLower("minor");
		yAxis.setFixUpper("minor");
		yAxis.setNatural(true);
		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		Serie<Integer> serie1 = new Serie<Integer>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));
		serie1.setStrokeColor("red");
		serie1.setFillColor("lightpink");
		Serie<Integer> serie2 = new Serie<Integer>(Arrays.asList(new Integer[]{5,4,3,2,1}));
		serie2.setStrokeColor("blue");
		serie2.setFillColor("lightblue");
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		chart.addPlot(plot);
		DOM.setElementAttribute(chart.getElement(), "id", "chart19");
		return chart;
	}

	private Chart2D getChart20(){
		Chart2D chart = new Chart2D("400px", "200px");
		BarPlot<Integer> plot = new BarPlot<Integer>(BarPlot.PLOT_TYPE_COLUMNS);
		plot.setGap(10);
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setFixLower("minor");
		xAxis.setFixUpper("minor");
		xAxis.setNatural(true);
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setFixLower("major");
		yAxis.setFixUpper("major");
		yAxis.setIncludeZero(true);
		yAxis.setMinorTicks(false);
		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		Serie<Integer> serie1 = new Serie<Integer>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));
		serie1.setStrokeColor("red");
		serie1.setFillColor("lightpink");
		Serie<Integer> serie2 = new Serie<Integer>(Arrays.asList(new Integer[]{5,4,3,2,1}));
		serie2.setStrokeColor("blue");
		serie2.setFillColor("lightblue");
		chart.addGrid(true,false,false,false);
		chart.addPlot(plot);
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		chart.addGrid(false,false,true,false);
		DOM.setElementAttribute(chart.getElement(), "id", "chart20");
		return chart;
	}
	
	private Chart2D getChart21(){
		Chart2D chart = new Chart2D("400px", "200px");
		BarPlot<Integer> plot = new BarPlot<Integer>(BarPlot.PLOT_TYPE_COLUMNS);
		plot.setGap(10);
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setFixLower("minor");
		xAxis.setFixUpper("minor");
		xAxis.setNatural(true);
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setFixLower("major");
		yAxis.setFixUpper("major");
		yAxis.setIncludeZero(true);
		yAxis.setMinorTicks(true);
		yAxis.setMajorTicks(true);
		yAxis.setMajorTickStep(2);
		yAxis.setMinorTickStep(1);
		yAxis.setMin(0);
		yAxis.setMax(8);
		yAxis.setMinorLabels(false);
		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		Serie<Integer> serie1 = new Serie<Integer>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));
		serie1.setStrokeColor("red");
		serie1.setFillColor("lightpink");
		Serie<Integer> serie2 = new Serie<Integer>(Arrays.asList(new Integer[]{5,4,3,2,1}));
		serie2.setStrokeColor("blue");
		serie2.setFillColor("lightblue");
		chart.addGrid(true,false,false,false);
		chart.addPlot(plot);
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		chart.addGrid(false,false,true,false);
		DOM.setElementAttribute(chart.getElement(), "id", "chart21");
		return chart;
	}
	
	private Chart2D getChart22(){
		Chart2D chart = new Chart2D("400px", "200px");
		BarPlot<Double> plot = new BarPlot<Double>(BarPlot.PLOT_TYPE_COLUMNS);
		plot.setGap(10);
		Axis xAxis = Axis.simpleXAxis();
		Axis yAxis = Axis.simpleYAxis();
		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		Serie<Double> serie1 = new Serie<Double>(Arrays.asList(new Double[]{2., 1., 0.5, -1., -2.}));
		serie1.setStrokeColor("red");
		serie1.setFillColor("lightpink");
		Serie<Double> serie2 = new Serie<Double>(Arrays.asList(new Double[]{-2., -1., -0.5, 1., 2.}));
		serie2.setStrokeColor("blue");
		serie2.setFillColor("lightblue");
		chart.addPlot(plot);
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		chart.addGrid(true,false,true,false);
		DOM.setElementAttribute(chart.getElement(), "id", "chart22");
		return chart;
	}

	private Chart2D getChart23(){
		Chart2D chart = new Chart2D("400px", "200px");
		BarPlot<Double> plot = new BarPlot<Double>(BarPlot.PLOT_TYPE_COLUMNS_CLUSTERED);
		plot.setGap(10);
		Axis xAxis = Axis.simpleXAxis();
		Axis yAxis = Axis.simpleYAxis();
		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		Serie<Double> serie1 = new Serie<Double>(Arrays.asList(new Double[]{2., 1., 0.5, -1., -2.}));
		serie1.setStrokeColor("red");
		serie1.setFillColor("lightpink");
		Serie<Double> serie2 = new Serie<Double>(Arrays.asList(new Double[]{-2., -1., -0.5, 1., 2.}));
		serie2.setStrokeColor("blue");
		serie2.setFillColor("lightblue");
		Serie<Double> serie3 = new Serie<Double>(Arrays.asList(new Double[]{1., 0.5, -1., -2., -3.}));
		serie3.setStrokeColor("green");
		serie3.setFillColor("lightgreen");
		chart.addPlot(plot);
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		plot.addSerie(serie3);
		chart.addGrid(true,false,true,false);
		DOM.setElementAttribute(chart.getElement(), "id", "chart23");
		return chart;
	}
	
	private Chart2D getChart24(){
		Chart2D chart = new Chart2D("400px", "200px");
		BarPlot<Double> plot = new BarPlot<Double>(BarPlot.PLOT_TYPE_BARS);
		plot.setGap(10);
		Axis xAxis = Axis.simpleXAxis();
		Axis yAxis = Axis.simpleYAxis();
		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		Serie<Double> serie1 = new Serie<Double>(Arrays.asList(new Double[]{2., 1., 0.5, -1., -2.}));
		serie1.setStrokeColor("red");
		serie1.setFillColor("lightpink");
		Serie<Double> serie2 = new Serie<Double>(Arrays.asList(new Double[]{-2., -1., -0.5, 1., 2.}));
		serie2.setStrokeColor("blue");
		serie2.setFillColor("lightblue");
		chart.addPlot(plot);
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		chart.addGrid(true,false,true,false);
		DOM.setElementAttribute(chart.getElement(), "id", "chart24");
		return chart;
	}
	
	private Chart2D getChart25(){
		Chart2D chart = new Chart2D("400px", "200px");
		BarPlot<Double> plot = new BarPlot<Double>(BarPlot.PLOT_TYPE_BARS_CLUSTERED);
		plot.setGap(10);
		Axis xAxis = Axis.simpleXAxis();
		Axis yAxis = Axis.simpleYAxis();
		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		Serie<Double> serie1 = new Serie<Double>(Arrays.asList(new Double[]{2., 1., 0.5, -1., -2.}));
		serie1.setStrokeColor("red");
		serie1.setFillColor("lightpink");
		Serie<Double> serie2 = new Serie<Double>(Arrays.asList(new Double[]{-2., -1., -0.5, 1., 2.}));
		serie2.setStrokeColor("blue");
		serie2.setFillColor("lightblue");
		Serie<Double> serie3 = new Serie<Double>(Arrays.asList(new Double[]{1., 0.5, -1., -2., -3.}));
		serie3.setStrokeColor("green");
		serie3.setFillColor("lightgreen");
		chart.addPlot(plot);
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		plot.addSerie(serie3);
		chart.addGrid(true,false,true,false);
		DOM.setElementAttribute(chart.getElement(), "id", "chart25");
		return chart;
	}
	
	private Chart2D getChart26(){
		Chart2D chart = new Chart2D("400px", "200px");
		chart.setTheme("Minty");
		Plot<Point> plot = new Plot<Point>(Plot2D.PLOT_TYPE_LINES);
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setMin(0);
		xAxis.setMax(6.);
		xAxis.setMajorTicksColor("black");
		xAxis.setMajorTicksLength(3);
		xAxis.setMinorTicksColor("gray");
		xAxis.setMinorTicksLength(3);
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setMin(0);
		yAxis.setMax(10.);
		yAxis.setMajorTicksColor("black");
		yAxis.setMajorTicksLength(3);
		yAxis.setMinorTicksColor("gray");
		yAxis.setMinorTicksLength(3);
		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		Serie<Point> serie1 = new Serie<Point>(Arrays.asList(new Point[]{new Point(0.5,5), new Point(1.5,1.5),new Point(2,9),new Point(5,3)}));
		Serie<Point> serie2 = new Serie<Point>(Arrays.asList(new Point[]{new Point(0.3,8), new Point(4,6),new Point(5.5,2)}));
		chart.addPlot(plot);
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		DOM.setElementAttribute(chart.getElement(), "id", "chart26");
		return chart;
	}
	
	private Chart2D getChart27(){
		Chart2D chart = new Chart2D("400px", "200px");
		chart.setTheme("Ireland");
		Plot<Point> plot = new Plot<Point>(Plot2D.PLOT_TYPE_SCATTER);
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setMin(0);
		xAxis.setMax(6.);
		xAxis.setMajorTicksColor("black");
		xAxis.setMajorTicksLength(3);
		xAxis.setMinorTicksColor("gray");
		xAxis.setMinorTicksLength(3);
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setMin(0);
		yAxis.setMax(10.);
		yAxis.setMajorTicksColor("black");
		yAxis.setMajorTicksLength(3);
		yAxis.setMinorTicksColor("gray");
		yAxis.setMinorTicksLength(3);
		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		Serie<Point> serie1 = new Serie<Point>(Arrays.asList(new Point[]{new Point(0.5,5), new Point(1.5,1.5),new Point(2,9),new Point(5,0.3)}));
		Serie<Point> serie2 = new Serie<Point>(Arrays.asList(new Point[]{new Point(0.3,8), new Point(4,6),new Point(5.5,2)}));
		chart.addPlot(plot);
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		DOM.setElementAttribute(chart.getElement(), "id", "chart27");
		return chart;
	}
	
	private Chart2D getChart28(){
		Chart2D chart = new Chart2D("400px", "200px");
		chart.setTheme("PlotKit.blue");
		Plot2D<Point> plot = new Plot2D<Point>();
		plot.setShowMarkers(true);
		plot.setShowLines(true);
		plot.setTension("2");
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setMin(0);
		xAxis.setMax(6.);
		xAxis.setMajorTicksColor("black");
		xAxis.setMajorTicksLength(3);
		xAxis.setMinorTicksColor("gray");
		xAxis.setMinorTicksLength(3);
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setMin(0);
		yAxis.setMax(10.);
		yAxis.setMajorTicksColor("black");
		yAxis.setMajorTicksLength(3);
		yAxis.setMinorTicksColor("gray");
		yAxis.setMinorTicksLength(3);
		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		Serie<Point> serie1 = new Serie<Point>(Arrays.asList(new Point[]{new Point(0.5,5), new Point(1.5,1.5),new Point(2,9),new Point(5,0.3)}));
		Serie<Point> serie2 = new Serie<Point>(Arrays.asList(new Point[]{new Point(0.3,8), new Point(4,6),new Point(5.5,2)}));
		chart.addPlot(plot);
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		DOM.setElementAttribute(chart.getElement(), "id", "chart28");
		return chart;
	}
	
	private Chart2D getChart29(){
		Chart2D chart = new Chart2D("500px", "300px");
		chart.setTheme("Tufte");
		BarPlot<Double> plot = new BarPlot<Double>(BarPlot.PLOT_TYPE_COLUMNS_CLUSTERED);
		plot.setGap(10);
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setFixLower("minor");
		xAxis.setFixUpper("minor");
		xAxis.setNatural(true);
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setFixLower("major");
		yAxis.setFixUpper("major");
		yAxis.setIncludeZero(true);
		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		Serie<Double> serie1 = new Serie<Double>(Arrays.asList(new Double[]{2., 1., 0.5, -1., -2.}));
		Serie<Double> serie2 = new Serie<Double>(Arrays.asList(new Double[]{-2., -1., -0.5, 1., 2.}));
		Serie<Double> serie3 = new Serie<Double>(Arrays.asList(new Double[]{1., 0.5, -1., -2., -3.}));
		Serie<Double> serie4 = new Serie<Double>(Arrays.asList(new Double[]{0.7, 1.5, -1.2, -1.25, 3.}));
		chart.addPlot(plot);
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		plot.addSerie(serie3);
		plot.addSerie(serie4);
		DOM.setElementAttribute(chart.getElement(), "id", "chart29");
		return chart;
	}
	
	private Chart2D getChart30(){
		Chart2D chart = new Chart2D("500px", "300px");
		chart.setTheme("SageToLime");
		BubblePlot plot = new BubblePlot();
		plot.setShadow(2, 2, 2);
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setMin(0);
		xAxis.setMax(6.);
		xAxis.setMajorTicksColor("black");
		xAxis.setMajorTicksLength(3);
		xAxis.setMinorTicksColor("gray");
		xAxis.setMinorTicksLength(3);
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setMin(0);
		yAxis.setMax(10.);
		yAxis.setMajorTicksColor("black");
		yAxis.setMajorTicksLength(3);
		yAxis.setMinorTicksColor("gray");
		yAxis.setMinorTicksLength(3);
		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		Serie<Bubble> serie1 = new Serie<Bubble>(Arrays.asList(new Bubble[]{new Bubble(0.5,5.,1.4),new Bubble(1.5,1.5,4.5),new Bubble(2,9,1.5),new Bubble(5.,0.3,0.8)}));
		Serie<Bubble> serie2 = new Serie<Bubble>(Arrays.asList(new Bubble[]{new Bubble(0.3,8,2.5),new Bubble(4,6,1.1),new Bubble(5.5,2,3.2)}));
		chart.addPlot(plot);
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		DOM.setElementAttribute(chart.getElement(), "id", "chart30");
		return chart;
	}
	
}
