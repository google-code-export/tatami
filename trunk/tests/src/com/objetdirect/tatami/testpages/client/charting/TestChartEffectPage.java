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

import java.util.Arrays;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.charting.Axis;
import com.objetdirect.tatami.client.charting.BarPlot;
import com.objetdirect.tatami.client.charting.Bubble;
import com.objetdirect.tatami.client.charting.BubblePlot;
import com.objetdirect.tatami.client.charting.Chart2D;
import com.objetdirect.tatami.client.charting.Legend;
import com.objetdirect.tatami.client.charting.PiePiece;
import com.objetdirect.tatami.client.charting.PiePlot;
import com.objetdirect.tatami.client.charting.Plot2D;
import com.objetdirect.tatami.client.charting.Point;
import com.objetdirect.tatami.client.charting.Serie;
import com.objetdirect.tatami.client.charting.Themes;
import com.objetdirect.tatami.client.charting.effects.EffectHighlight;
import com.objetdirect.tatami.client.charting.effects.EffectMagnify;
import com.objetdirect.tatami.client.charting.effects.EffectMoveSlice;
import com.objetdirect.tatami.client.charting.effects.EffectShake;
import com.objetdirect.tatami.client.charting.effects.EffectTooltip;
import com.objetdirect.tatami.testpages.client.TestPage;

public class TestChartEffectPage extends TestPage{

	protected TestChartEffectPage() {
		super(TestChartEffectPage.class.getName(), "Test Charts Effects");
	}

	@Override
	public Widget getTestPage() {
		Panel flowPanel = new FlowPanel();
		flowPanel.add(new HTML("<span>1: Markers, lines, 2D data, custom axis. Actions: Magnify, Tooltip.</span>"));
		flowPanel.add(getChart1());
		flowPanel.add(new HTML("<span>2: Stacked lines, markers, shadows, no axes, custom strokes, fills, and markers. Actions: Magnify, Highlight.</span>"));
		flowPanel.add(getChart2());
		flowPanel.add(new HTML("<span>3: Columns with gaps beetwen them, vertical axis aligned on major ticks, custom strokes, fills. Actions: Highlight, Tooltip.</span>"));
		flowPanel.add(getChart3());
		flowPanel.add(new HTML("<span>4: Bars, axes aligned on major ticks, no minor ticks, custom strokes and fills. Actions: Highlight, Tooltip.</span>"));
		flowPanel.add(getChart4());
		flowPanel.add(new HTML("<span>5: Clustered bars, custom axes, custom strokes, fills, and gap. Actions: Highlight, Tooltip.</span>"));
		flowPanel.add(getChart5());
		flowPanel.add(new HTML("<span>6: Clustered columns with positive and negative values, axes, and grid. Actions: Highlight, Shake, Tooltip.</span>"));
		flowPanel.add(getChart6());
		flowPanel.add(new HTML("<span>7: Stacked columns, no axes, custom strokes and fills. Actions: Highlight, Shake, Tooltip.</span>"));
		flowPanel.add(getChart7());
		flowPanel.add(new HTML("<span>8: Stacked bars, no axes, custom strokes and fills. Actions: Highlight, Shake, Tooltip.</span>"));
		flowPanel.add(getChart8());
		flowPanel.add(new HTML("<span>9: Bubble chart, axes. Actions: Magnify, Highlight, Tooltip.</span>"));
		flowPanel.add(getChart9());
		flowPanel.add(new HTML("<span>10: Pie with internal custom labels, custom colors, and custom tooltips. Actions: MoveSlice, Highlight, Tooltip.</span>"));
		flowPanel.add(getChart10());
		return flowPanel;
	}
	
	public Widget getChart1(){
		Chart2D chart = new Chart2D("400px","200px");
		chart.setTheme(Themes.PlotKit_green);
		Plot2D<Point> plot = new Plot2D<Point>();
		plot.setShowMarkers(true);
		plot.setShowLines(true);
		plot.setTension("2");
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setMin(0);
		xAxis.setMax(6);
		xAxis.setMajorTicksColor("black");
		xAxis.setMajorTicksLength(3.);
		xAxis.setMinorTicksColor("gray");
		xAxis.setMinorTicksLength(3.);
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setMin(0);
		yAxis.setMax(10.);
		yAxis.setMajorTicksColor("black");
		yAxis.setMajorTicksLength(3.);
		yAxis.setMinorTicksColor("gray");
		yAxis.setMinorTicksLength(3.);
		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		plot.addEffect(new EffectMagnify(2));
		//plot.addEffect(new EffectTooltip());
		Point[] points1 = new Point[]{new Point(0.5,5.0),new Point(1.5,1.5),new Point(2.,9.),new Point(5.,0.3)};
		Point[] points2 = new Point[]{new Point(0.3,8.0),new Point(4.,6.,"Custom tooltip"),new Point(5.5,2.)};
		Serie<Point> serie1 = new Serie<Point>(Arrays.asList(points1), "Serie 1");
		Serie<Point> serie2 = new Serie<Point>(Arrays.asList(points2), "Serie 2");
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		chart.addPlot(plot);
		Legend legend = new Legend(chart,false);
		FlowPanel chartPanel = new FlowPanel();
		chartPanel.add(chart);
		chartPanel.add(legend);
		DOM.setElementAttribute(chart.getElement(), "id", "chart1");
		return chartPanel;
	}
	
	public Widget getChart2(){
		Chart2D chart = new Chart2D("400px","200px");
		chart.setTheme(Themes.PlotKit_green);
		Plot2D<Double> plot = new Plot2D<Double>(Plot2D.PLOT_TYPE_LINES_STACKED);
		plot.setShowMarkers(true);
		plot.setTension("3");
		plot.setShadow(2,2,2);
		plot.addEffect(new EffectMagnify(3));
		plot.addEffect(new EffectHighlight());
		Double[] points1 = new Double[]{1., 1.1, 1.2, 1.3, 1.4, 1.5, 1.6};
		Double[] points2 = new Double[]{1., 1.6, 1.3, 1.4, 1.1, 1.5, 1.1};
		Double[] points3 = new Double[]{1., 1.1, 1.2, 1.3, 1.4, 1.5, 1.6};
		Serie<Double> serie1 = new Serie<Double>(Arrays.asList(points1),"Series A");
		serie1.setStrokeColor("red");
		serie1.setStrokeWidth(2);
		serie1.setFillColor("lightpink");
		serie1.setMarkerType(Serie.MARKER_TYPE_SQUARE);
		Serie<Double> serie2 = new Serie<Double>(Arrays.asList(points2),"Series B");
		serie2.setStrokeColor("blue");
		serie2.setStrokeWidth(2);
		serie2.setFillColor("lightblue");
		serie2.setMarkerType(Serie.MARKER_TYPE_CIRCLE);
		Serie<Double> serie3 = new Serie<Double>(Arrays.asList(points3),"Series C");
		serie3.setStrokeColor("green");
		serie3.setStrokeWidth(2);
		serie3.setFillColor("lightgreen");
		serie3.setMarkerType(Serie.MARKER_TYPE_DIAMOND);
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		plot.addSerie(serie3);
		chart.addPlot(plot);
		Legend legend = new Legend(chart);
		FlowPanel chartPanel = new FlowPanel();
		chartPanel.add(chart);
		chartPanel.add(legend);
		DOM.setElementAttribute(chart.getElement(), "id", "chart2");
		return chartPanel;
	}

	public Widget getChart3(){
		Chart2D chart = new Chart2D("400px","200px");
		chart.setTheme(Themes.PlotKit_green);
		BarPlot<Integer> plot = new BarPlot<Integer>(BarPlot.PLOT_TYPE_COLUMNS);
		plot.setGap(2);
		plot.addEffect(new EffectHighlight());
		plot.addEffect(new EffectTooltip());
		chart.addPlot(plot);
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setFixLower(Axis.FIX_TYPE_MAJOR);
		yAxis.setFixUpper(Axis.FIX_TYPE_MAJOR);
		chart.setDefaultYAxis(yAxis);
		Serie<Integer> serie1 = new Serie<Integer>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}),"Series A");
		serie1.setStrokeColor("black");
		serie1.setFillColor("red");
		Serie<Integer> serie2 = new Serie<Integer>(Arrays.asList(new Integer[]{5, 4, 3, 2, 1}),"Series B");
		serie2.setStrokeColor("black");
		serie2.setFillColor("blue");
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		FlowPanel chartPanel = new FlowPanel();
		chartPanel.add(chart);
		chartPanel.add(new Legend(chart,false));
		DOM.setElementAttribute(chart.getElement(), "id", "chart3");
		return chartPanel;
	}
	
	public Widget getChart4(){
		Chart2D chart = new Chart2D("400px","200px");
		chart.setTheme(Themes.PlotKit_green);
		BarPlot<Integer> plot = new BarPlot<Integer>(BarPlot.PLOT_TYPE_BARS);
		plot.addEffect(new EffectHighlight("gold"));
		plot.addEffect(new EffectTooltip());
		chart.addPlot(plot);
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setFixLower(Axis.FIX_TYPE_MAJOR);
		xAxis.setFixUpper(Axis.FIX_TYPE_MAJOR);
		xAxis.setIncludeZero(true);
		chart.setDefaultXAxis(xAxis);
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setFixLower(Axis.FIX_TYPE_MAJOR);
		yAxis.setFixUpper(Axis.FIX_TYPE_MAJOR);
		yAxis.setNatural(true);
		chart.setDefaultYAxis(yAxis);
		Serie<Integer> serie1 = new Serie<Integer>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}),"Series A");
		serie1.setStrokeColor("black");
		serie1.setFillColor("red");
		Serie<Integer> serie2 = new Serie<Integer>(Arrays.asList(new Integer[]{5, 4, 3, 2, 1}),"Series B");
		serie2.setStrokeColor("black");
		serie2.setFillColor("blue");
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		FlowPanel chartPanel = new FlowPanel();
		chartPanel.add(chart);
		chartPanel.add(new Legend(chart));
		DOM.setElementAttribute(chart.getElement(), "id", "chart4");
		return chartPanel;
	}
	
	public Widget getChart5(){
		Chart2D chart = new Chart2D("400px","200px");
		chart.setTheme(Themes.PlotKit_green);
		BarPlot<Integer> plot = new BarPlot<Integer>(BarPlot.PLOT_TYPE_BARS_CLUSTERED);
		plot.setGap(5);
		EffectHighlight fxHl = new EffectHighlight();
		fxHl.setDuration(450);
		plot.addEffect(fxHl);
		plot.addEffect(new EffectTooltip());
		chart.addPlot(plot);
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setFixLower(Axis.FIX_TYPE_MAJOR);
		xAxis.setFixUpper(Axis.FIX_TYPE_MAJOR);
		xAxis.setIncludeZero(true);
		chart.setDefaultXAxis(xAxis);
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setFixLower(Axis.FIX_TYPE_MINOR);
		yAxis.setFixUpper(Axis.FIX_TYPE_MINOR);
		yAxis.setNatural(true);
		chart.setDefaultYAxis(yAxis);
		Serie<Integer> serie1 = new Serie<Integer>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}),"Series A");
		serie1.setStrokeColor("black");
		serie1.setFillColor("red");
		Serie<Integer> serie2 = new Serie<Integer>(Arrays.asList(new Integer[]{5, 4, 3, 2, 1}),"Series B");
		serie2.setStrokeColor("black");
		serie2.setFillColor("blue");
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		FlowPanel chartPanel = new FlowPanel();
		chartPanel.add(chart);
		chartPanel.add(new Legend(chart,false));
		DOM.setElementAttribute(chart.getElement(), "id", "chart5");
		return chartPanel;
	}
	
	public Widget getChart6(){
		Chart2D chart = new Chart2D("400px","200px");
		chart.setTheme(Themes.PlotKit_green);
		BarPlot<Double> plot = new BarPlot<Double>(BarPlot.PLOT_TYPE_COLUMNS_CLUSTERED);
		plot.setGap(10);
		EffectHighlight fxHl = new EffectHighlight();
		fxHl.setDuration(450);
		plot.addEffect(fxHl);
		plot.addEffect(new EffectTooltip());
		plot.addEffect(new EffectShake());
		chart.addPlot(plot);
		chart.addGrid(true,false, true,false);
		chart.setDefaultXAxis(Axis.simpleXAxis());
		chart.setDefaultYAxis(Axis.simpleYAxis());
		Serie<Double> serie1 = new Serie<Double>(Arrays.asList(new Double[]{2., 1., 0.5, -1., -2.}),"Series A");
		serie1.setStrokeColor("black");
		serie1.setFillColor("red");
		Serie<Double> serie2 = new Serie<Double>(Arrays.asList(new Double[]{-2., -1., -0.5, 1., 2.}),"Series B");
		serie2.setStrokeColor("black");
		serie2.setFillColor("blue");
		Serie<Double> serie3 = new Serie<Double>(Arrays.asList(new Double[]{1., 0.5, -1., -2., -3.}),"Series C");
		serie3.setStrokeColor("black");
		serie3.setFillColor("green");
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		plot.addSerie(serie3);
		FlowPanel chartPanel = new FlowPanel();
		chartPanel.add(chart);
		chartPanel.add(new Legend(chart));
		DOM.setElementAttribute(chart.getElement(), "id", "chart6");
		return chartPanel;
	}
	
	public Widget getChart7(){
		Chart2D chart = new Chart2D("400px","200px");
		chart.setTheme(Themes.PlotKit_green);
		BarPlot<Integer> plot = new BarPlot<Integer>(BarPlot.PLOT_TYPE_COLUMNS_STACKED);
		plot.setGap(5);
		plot.addEffect(new EffectHighlight(450));
		plot.addEffect(new EffectShake());
		plot.addEffect(new EffectTooltip());
		chart.addPlot(plot);
		Serie<Integer> serie1 = new Serie<Integer>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}),"Series A");
		serie1.setStrokeColor("black");
		serie1.setFillColor("red");
		Serie<Integer> serie2 = new Serie<Integer>(Arrays.asList(new Integer[]{2, 1, 2, 1, 2}),"Series B");
		serie2.setStrokeColor("black");
		serie2.setFillColor("blue");
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		FlowPanel chartPanel = new FlowPanel();
		chartPanel.add(chart);
		chartPanel.add(new Legend(chart,false));
		DOM.setElementAttribute(chart.getElement(), "id", "chart7");
		return chartPanel;
	}
	
	public Widget getChart8(){
		Chart2D chart = new Chart2D("400px","200px");
		chart.setTheme(Themes.PlotKit_green);
		BarPlot<Integer> plot = new BarPlot<Integer>(BarPlot.PLOT_TYPE_BARS_STACKED);
		plot.addEffect(new EffectHighlight());
		plot.addEffect(new EffectShake());
		plot.addEffect(new EffectTooltip());
		chart.addPlot(plot);
		Serie<Integer> serie1 = new Serie<Integer>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}),"Series A");
		serie1.setStrokeColor("black");
		serie1.setFillColor("red");
		Serie<Integer> serie2 = new Serie<Integer>(Arrays.asList(new Integer[]{2, 1, 2, 1, 2}),"Series B");
		serie2.setStrokeColor("black");
		serie2.setFillColor("blue");
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		FlowPanel chartPanel = new FlowPanel();
		chartPanel.add(chart);
		chartPanel.add(new Legend(chart));
		DOM.setElementAttribute(chart.getElement(), "id", "chart8");
		return chartPanel;
	}
	
	public Widget getChart9(){
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
		Serie<Bubble> serie1 = new Serie<Bubble>(Arrays.asList(new Bubble[]{new Bubble(0.5,5.,1.4),new Bubble(1.5,1.5,4.5,"Big Bubble !!"),new Bubble(2,9,1.5),new Bubble(5.,0.3,0.8)}),"Series A");
		Serie<Bubble> serie2 = new Serie<Bubble>(Arrays.asList(new Bubble[]{new Bubble(0.3,8,2.5),new Bubble(4,6,1.1),new Bubble(5.5,2,3.2)}),"Series B");
		serie1.setFillColor("red");
		serie2.setFillColor("blue");
		chart.addPlot(plot);
		plot.addEffect(new EffectMagnify(1.1));
		plot.addEffect(new EffectTooltip());
		plot.addEffect(new EffectHighlight());
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		DOM.setElementAttribute(chart.getElement(), "id", "chart30");
		FlowPanel chartPanel = new FlowPanel();
		chartPanel.add(chart);
		chartPanel.add(new Legend(chart,false));
		DOM.setElementAttribute(chart.getElement(), "id", "chart9");
		return chartPanel;
	}
	
	public Widget getChart10(){
		Chart2D chart = new Chart2D("300px","300px");
		chart.setTheme("PlotKit.green");
		PiePlot<PiePiece> plot = new PiePlot<PiePiece>();
		plot.addEffect(new EffectMoveSlice());
		plot.addEffect(new EffectHighlight());
		plot.addEffect(new EffectTooltip());
		plot.setFont("normal normal bold 11pt Tahoma");
		plot.setFontColor("black");
		plot.setLabelOffset(-30);
		plot.setRadius(80);
		PiePiece[] pieces = new PiePiece[]{
				new PiePiece(4.,"Red","red","black","Red is 50%"),
				new PiePiece(2.,"Green","green","black","Green is 25%"),
				new PiePiece(1.,"Blue","blue","black","I am feeling Blue!"),
				new PiePiece(1.,"Other","white","black","Mighty <strong>strong</strong><br>With two lines!")
		};
		plot.addSerie(new Serie<PiePiece>(Arrays.asList(pieces)));
		chart.addPlot(plot);
		FlowPanel chartPanel = new FlowPanel();
		chartPanel.add(chart);
		chartPanel.add(new Legend(chart,false));
		DOM.setElementAttribute(chart.getElement(), "id", "chart10");
		return chartPanel;
	}

	
}
