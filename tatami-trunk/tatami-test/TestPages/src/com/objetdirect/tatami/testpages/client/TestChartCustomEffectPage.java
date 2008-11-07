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

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.charting.Axis;
import com.objetdirect.tatami.client.charting.BarPlot;
import com.objetdirect.tatami.client.charting.Chart2D;
import com.objetdirect.tatami.client.charting.PiePiece;
import com.objetdirect.tatami.client.charting.PiePlot;
import com.objetdirect.tatami.client.charting.Serie;
import com.objetdirect.tatami.client.charting.Themes;
import com.objetdirect.tatami.client.charting.effects.EffectEvent;
import com.objetdirect.tatami.client.charting.effects.EffectTooltip;
import com.objetdirect.tatami.client.charting.effects.PlotMouseListener;

public class TestChartCustomEffectPage extends TestPage{

	protected TestChartCustomEffectPage() {
		super("com.objetdirect.tatami.testpages.client.TestChartCustomEffectPage", "Test Charts Custom Effect");
	}
	final Serie<Number> serie2 = new Serie<Number>("detail");
	final Chart2D continentChart = getContinentChart();
	final Chart2D detailedChart = getDetailedChart();
	
	
	@Override
	public Widget getTestPage() {
		FlowPanel panel = new FlowPanel();
		final ListBox themes = new ListBox();
		for(int i = 0 ; i < Themes.Themes.length;i++){
			themes.addItem(Themes.Themes[i]);
		}
		themes.addChangeListener(new ChangeListener(){
			public void onChange(Widget sender) {
				continentChart.setTheme(themes.getValue(themes.getSelectedIndex()));
				detailedChart.setTheme(themes.getValue(themes.getSelectedIndex()));
				continentChart.refreshChart();
				detailedChart.refreshChart();
			}
		});
		HTML title = new HTML("<h1>Territories Area (in km²)</h1>");
		panel.add(themes);
		panel.add(continentChart);
		panel.add(detailedChart);
		return panel;
	}
	
	
	
	public Chart2D getDetailedChart(){
		final Chart2D detailedChart = new Chart2D("900px","300px");
		BarPlot<Number> plot2 = new BarPlot<Number>(BarPlot.PLOT_TYPE_COLUMNS);
		plot2.addSerie(serie2);
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setMin(0);
		detailedChart.setDefaultYAxis(yAxis);
		detailedChart.addPlot(plot2);
		return detailedChart;
	}
	
	public Chart2D getContinentChart(){
		final Chart2D chart = new Chart2D("300px","300px");
		PiePlot<PiePiece> plot = new PiePlot<PiePiece>();
		final Serie<PiePiece> serie1 = new Serie<PiePiece>();
		serie1.setName("continents");
		serie1.addData(new PiePiece(30370000,"Africa"));
		serie1.addData(new PiePiece(42330000,"America"));
		serie1.addData(new PiePiece(13720000,"Antarctica"));
		serie1.addData(new PiePiece(43810000 ,"Asia"));
		serie1.addData(new PiePiece(9010000,"Australia"));
		serie1.addData(new PiePiece(10180000,"Europe"));
		plot.addEffect(new EffectTooltip());
		PlotMouseListener listener = new PlotMouseListener() {
			public void processEvent(EffectEvent event) {
				if(event.getType().compareTo(EffectEvent.TYPE_ONCLICK) == 0){
					PiePiece piece = (PiePiece) event.getAssociatedObject();
					if(piece.getLabel().compareTo("America")== 0){
						Axis xAxis = Axis.simpleXAxis();
						xAxis.addLabels(new String[]{"North America","South America"});
						detailedChart.setDefaultXAxis(xAxis);
						detailedChart.updateSerie(getAmericanSerie());
						detailedChart.refreshChart();
					}else if(piece.getLabel().compareTo("Europe")== 0) {
						Axis xAxis = Axis.simpleXAxis();
						xAxis.addLabels(getEuropeanLabels());
						xAxis.setMin(0);
						xAxis.setFont("normal normal 1pt Tahoma");
						detailedChart.setDefaultXAxis(xAxis);
						detailedChart.updateSerie(getEuropeanSerie());
						detailedChart.refreshChart();
					}else{
						Window.alert("We don't have more information about this continent");
					}
				}
			}
		};
		plot.addEffect(listener);
		plot.addSerie(serie1);
		chart.addPlot(plot);
		return chart;
	}

	public Serie<Number> getAmericanSerie(){
		serie2.setData(Arrays.asList(new Number[]{24490000,17840000}));
		return serie2;
	}
	
	public String[] getEuropeanLabels(){
		List<String> labels = new ArrayList<String>();
		labels.add("Belgium");
		labels.add("Estonia");
		labels.add("Finland");
		labels.add("France");
		labels.add("Germany");
		labels.add("Greece");
		labels.add("Hungary");
		labels.add("Ireland");
		labels.add("Italy");
		labels.add("Lithuania");
		labels.add("Netherlands");
		labels.add("Norway");
		labels.add("Poland");
		labels.add("Portugal");
		labels.add("Romania");
		labels.add("Slovakia");
		labels.add("Slovenia");
		labels.add("Spain");
		labels.add("Sweden");
		labels.add("Switzerland");
		labels.add("United Kingdom");
		return labels.toArray(new String[0]);
	}
	
	public Serie<Number> getEuropeanSerie(){
		List<Number> list = new ArrayList<Number>();
		list.add(30510);
		list.add(45226);
		list.add(336593);
		list.add(547030);
		list.add(357021);
		list.add(131940);
		list.add(93030);
		list.add(70280);
		list.add(301230);
		list.add(65200);
		list.add(41526);
		list.add(324220);
		list.add(312685);
		list.add(91568);
		list.add(238391);
		list.add(48845);
		list.add(20273);
		list.add(504851);
		list.add(449964);
		list.add(41290);
		list.add(244820);
		serie2.setData(list);
		return serie2;
	}
	
	
	
}
