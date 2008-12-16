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


import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.charting.Axis;
import com.objetdirect.tatami.client.charting.Chart2D;
import com.objetdirect.tatami.client.charting.Plot;
import com.objetdirect.tatami.client.charting.Plot2D;
import com.objetdirect.tatami.client.charting.Point;
import com.objetdirect.tatami.client.charting.Serie;
import com.objetdirect.tatami.testpages.client.TestPage;

public class TestChartUpdatingPage extends TestPage{

	Serie<Point> serie1;
	
	Serie<Point> serie2;
	
	Serie<Point> serie3;
	
	Serie[] series;
	
	Integer[] points = new Integer[]{0,50,100,150,200,250,300};
	
	Chart2D updatingChart;
	
	int nextXIndex = 10;
	
	protected TestChartUpdatingPage() {
		super(TestChartUpdatingPage.class.getName(), "Test Updating Chart");
	}
	

	
	@Override
	public Widget getTestPage() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML("<span>This chart updates itself two times a second</span>"));
		updatingChart = new Chart2D("300px","300px");
		panel.add(updatingChart);
		updatingChart.setTheme("PlotKit.orange");
		Axis xAxis = Axis.simpleXAxis();
		xAxis.setFixLower("minor");
		xAxis.setNatural(true);
		xAxis.setMajorTicks(false);
		xAxis.setMinorTicks(false);
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setMajorTicks(false);
		yAxis.setMinorTicks(false);
		yAxis.setMin(0);
		yAxis.setMax(300);
		updatingChart.setDefaultXAxis(xAxis);
		updatingChart.setDefaultYAxis(yAxis);
		serie1 = getSerie(0);
		serie2 = getSerie(7);
		serie3 = getSerie(12);
		series = new Serie[]{serie1,serie2,serie3};
		Plot<Point> plot = new Plot<Point>(Plot2D.PLOT_TYPE_SCATTER);
		plot.addSerie(serie1);
		plot.addSerie(serie2);
		plot.addSerie(serie3);
		updatingChart.addPlot(plot);
		Button button = new Button("Update Series",new ClickListener(){

			public void onClick(Widget sender) {
				serie1.removeData(0);
				serie2.removeData(0);
				serie3.removeData(0);
				serie1.addData(getNextPoint(nextXIndex,0));
				serie2.addData(getNextPoint(nextXIndex,3));
				serie3.addData(getNextPoint(nextXIndex,5));
				updatingChart.updateSeries(series);
				nextXIndex++;
			}
			
		});
		DOM.setElementAttribute(updatingChart.getElement(),"id","chart");
		DOM.setElementAttribute(button.getElement(),"id","button");
		panel.add(button);
		return panel;
	}
	
	public Serie<Point> getSerie(int modulo){
		Serie<Point> serie = new Serie<Point>();
		for(int i = 0; i < 10; i++){
			serie.addData(getNextPoint(i,modulo));
		}
		return serie;
	}
	
	public Point getNextPoint(int xValue , int modulo){
		return new Point(xValue,points[(xValue + modulo)%7]);
	}

}
