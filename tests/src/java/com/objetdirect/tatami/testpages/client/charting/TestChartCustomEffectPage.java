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
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.charting.Axis;
import com.objetdirect.tatami.client.charting.BarPlot;
import com.objetdirect.tatami.client.charting.Bubble;
import com.objetdirect.tatami.client.charting.BubblePlot;
import com.objetdirect.tatami.client.charting.Chart2D;
import com.objetdirect.tatami.client.charting.PiePiece;
import com.objetdirect.tatami.client.charting.PiePlot;
import com.objetdirect.tatami.client.charting.Plot2D;
import com.objetdirect.tatami.client.charting.Point;
import com.objetdirect.tatami.client.charting.Serie;
import com.objetdirect.tatami.client.charting.Themes;
import com.objetdirect.tatami.client.charting.effects.EffectEvent;
import com.objetdirect.tatami.client.charting.effects.EffectTooltip;
import com.objetdirect.tatami.client.charting.effects.PlotMouseListener;
import com.objetdirect.tatami.testpages.client.TestPage;

public class TestChartCustomEffectPage extends TestPage{

	protected TestChartCustomEffectPage() {
		super(TestChartCustomEffectPage.class.getName(), "Test Charts Custom Effect");
	}
	
	
	@Override
	public Widget getTestPage() {
		FlowPanel panel = new FlowPanel();
		panel.add(getChart1());
		panel.add(getChart2());
		panel.add(getChart3());
		panel.add(getChart4());
		return panel;
	}
	
	
	public Widget getChart1(){
		FlowPanel panel = new FlowPanel();
		panel.setStylePrimaryName("testChartCustomEffectPage-chartAndLabelsContainer");
		Chart2D chart = new Chart2D("300px","300px");
		PiePlot<PiePiece> plot = new PiePlot<PiePiece>();
		final HTML onMouseOver = new HTML();
		final HTML onMouseClick = new HTML();
		final HTML onMouseOut = new HTML();
		Serie<PiePiece> serie1 = new Serie<PiePiece>();
		serie1.setName("continents");
		serie1.addData(new PiePiece(30370000,"Africa","#FFFFFF"));
		serie1.addData(new PiePiece(42330000,"America"));
		serie1.addData(new PiePiece(13720000,"Antarctica"));
		serie1.addData(new PiePiece(43810000 ,"Asia"));
		serie1.addData(new PiePiece(9010000,"Australia"));
		serie1.addData(new PiePiece(10180000,"Europe"));
		plot.addEffect(new EffectTooltip());
		PlotMouseListener listener = new PlotMouseListener() {
			public void processEvent(EffectEvent event) {
				String label ="";
				if(EffectEvent.ELEMENT_TYPE_SLICE.equals(event.getElementType())){
					label =  ((PiePiece)event.getAssociatedObject()).getLabel();	
				}
				if(event.getType().compareTo(EffectEvent.TYPE_ONCLICK) == 0){
					onMouseClick.setHTML("Clicked on : " + label);
				}else if(event.getType().compareTo(EffectEvent.TYPE_ONMOUSEOVER) == 0){
					onMouseOver.setHTML("Mouse Over : " + label);
				}else if(event.getType().compareTo(EffectEvent.TYPE_ONMOUSEOUT) == 0){
					onMouseOut.setHTML("Mouse Out : " + label);
				}
			}
		};
		plot.addEffect(listener);
		plot.addSerie(serie1);
		chart.addPlot(plot);
		DOM.setElementAttribute(chart.getElement(),"id","chart1");
		DOM.setElementAttribute(onMouseOver.getElement(),"id","chart1MouseOver");
		DOM.setElementAttribute(onMouseClick.getElement(),"id","chart1MouseClick");
		DOM.setElementAttribute(onMouseOut.getElement(),"id","chart1MouseOut");
		panel.add(chart);
		panel.add(onMouseClick);
		panel.add(onMouseOver);
		panel.add(onMouseOut);
		return panel;
	}
	
	public Widget getChart2(){
		Chart2D chart = new Chart2D("400px","200px");
		chart.setTheme(Themes.PlotKit_green);
		Plot2D<Point> plot = new Plot2D<Point>(Plot2D.PLOT_TYPE_SCATTER);
		plot.setShowMarkers(true);
		plot.setShowLines(false);
		Point[] points1 = new Point[]{new Point(0.5,5.0,"FirstPoint"),new Point(1.5,1.5,"SecondPoint"),new Point(5.,0.3,"ThirdPoint")};
		Serie<Point> serie1 = new Serie<Point>(Arrays.asList(points1), "Serie 1");
		plot.addSerie(serie1);
		chart.addPlot(plot);
		final HTML onMouseOver = new HTML();
		final HTML onMouseClick = new HTML();
		final HTML onMouseOut = new HTML();
		PlotMouseListener listener = new PlotMouseListener() {
			public void processEvent(EffectEvent event) {
				String label ="";
				if(EffectEvent.ELEMENT_TYPE_MARKER.equals(event.getElementType())){
					label = "X : " + event.getX() + " Y : " + event.getY() +
					" X From Point object : " + ((Point)event.getAssociatedObject()).getX() + 
					" Y From Point Object : " +((Point)event.getAssociatedObject()).getY() +
					"Tooltip : " + ((Point)event.getAssociatedObject()).getTooltip();
				}
				if(event.getType().compareTo(EffectEvent.TYPE_ONCLICK) == 0){
					onMouseClick.setHTML("Clicked on : " + label);
				}else if(event.getType().compareTo(EffectEvent.TYPE_ONMOUSEOVER) == 0){
					onMouseOver.setHTML("Mouse Over : " + label);
				}else if(event.getType().compareTo(EffectEvent.TYPE_ONMOUSEOUT) == 0){
					onMouseOut.setHTML("Mouse Out : " + label);
				}
			}
		};

		plot.addEffect(listener);
		FlowPanel chartPanel = new FlowPanel();
		chartPanel.add(chart);
		chartPanel.add(onMouseClick);
		chartPanel.add(onMouseOver);
		chartPanel.add(onMouseOut);
		DOM.setElementAttribute(chart.getElement(),"id","chart2");
		DOM.setElementAttribute(onMouseOver.getElement(),"id","chart2MouseOver");
		DOM.setElementAttribute(onMouseClick.getElement(),"id","chart2MouseClick");
		DOM.setElementAttribute(onMouseOut.getElement(),"id","chart2MouseOut");
		return chartPanel;
	}
	
	public Widget getChart3(){
		Chart2D chart = new Chart2D("400px","200px");
		chart.setTheme(Themes.PlotKit_green);
		BarPlot<Integer> plot = new BarPlot<Integer>(BarPlot.PLOT_TYPE_COLUMNS);
		plot.setGap(2);
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setMin(0);
		yAxis.setMax(5);
		plot.setYAxis(yAxis);
		chart.addPlot(plot);
		Serie<Integer> serie1 = new Serie<Integer>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}),"Series A");
		serie1.setStrokeColor("black");
		serie1.setFillColor("red");
		plot.addSerie(serie1);
		FlowPanel chartPanel = new FlowPanel();
		chartPanel.add(chart);
		final HTML onMouseOver = new HTML();
		final HTML onMouseClick = new HTML();
		final HTML onMouseOut = new HTML();
		PlotMouseListener listener = new PlotMouseListener() {
			public void processEvent(EffectEvent event) {
				String label ="";
				System.out.println(event.getElementType());
				if(EffectEvent.ELEMENT_TYPE_COLUMN.equals(event.getElementType())){
					label = "Value From Bar = " + ((Double)event.getAssociatedObject());
				}
				if(event.getType().compareTo(EffectEvent.TYPE_ONCLICK) == 0){
					onMouseClick.setHTML("Clicked on : " + label);
				}else if(event.getType().compareTo(EffectEvent.TYPE_ONMOUSEOVER) == 0){
					onMouseOver.setHTML("Mouse Over : " + label);
				}else if(event.getType().compareTo(EffectEvent.TYPE_ONMOUSEOUT) == 0){
					onMouseOut.setHTML("Mouse Out : " + label);
				}
			}
		};
		chartPanel.add(onMouseClick);
		chartPanel.add(onMouseOver);
		chartPanel.add(onMouseOut);
		plot.addEffect(listener);
		DOM.setElementAttribute(chart.getElement(),"id","chart3");
		DOM.setElementAttribute(onMouseOver.getElement(),"id","chart3MouseOver");
		DOM.setElementAttribute(onMouseClick.getElement(),"id","chart3MouseClick");
		DOM.setElementAttribute(onMouseOut.getElement(),"id","chart3MouseOut");
		return chartPanel;
	}

	public Widget getChart4(){
		Chart2D chart = new Chart2D("500px", "300px");
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
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		FlowPanel chartPanel = new FlowPanel();
		chartPanel.add(chart);
		final HTML onMouseOver = new HTML();
		final HTML onMouseClick = new HTML();
		final HTML onMouseOut = new HTML();
		PlotMouseListener listener = new PlotMouseListener() {
			public void processEvent(EffectEvent event) {
				String label ="";
				System.out.println(event.getElementType());
				if(EffectEvent.ELEMENT_TYPE_CIRCLE.equals(event.getElementType())){
					label = "Size = " + ((Bubble)event.getAssociatedObject()).getSize() + 
					"X = " + ((Bubble)event.getAssociatedObject()).getX() +
					"Y = " + ((Bubble)event.getAssociatedObject()).getY() +
					"Tooltip = " + ((Bubble)event.getAssociatedObject()).getTooltip();
				}
				if(event.getType().compareTo(EffectEvent.TYPE_ONCLICK) == 0){
					onMouseClick.setHTML("Clicked on : " + label);
				}else if(event.getType().compareTo(EffectEvent.TYPE_ONMOUSEOVER) == 0){
					onMouseOver.setHTML("Mouse Over : " + label);
				}else if(event.getType().compareTo(EffectEvent.TYPE_ONMOUSEOUT) == 0){
					onMouseOut.setHTML("Mouse Out : " + label);
				}
			}
		};
		chartPanel.add(onMouseClick);
		chartPanel.add(onMouseOver);
		chartPanel.add(onMouseOut);
		plot.addEffect(listener);
		DOM.setElementAttribute(chart.getElement(),"id","chart4");
		DOM.setElementAttribute(onMouseOver.getElement(),"id","chart4MouseOver");
		DOM.setElementAttribute(onMouseClick.getElement(),"id","chart4MouseClick");
		DOM.setElementAttribute(onMouseOut.getElement(),"id","chart4MouseOut");
		return chartPanel;
	}
	
	
	
}
