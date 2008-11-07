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
import java.util.List;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.charting.Chart2D;
import com.objetdirect.tatami.client.charting.PiePiece;
import com.objetdirect.tatami.client.charting.PiePlot;
import com.objetdirect.tatami.client.charting.Serie;

public class TestPieChartPage extends TestPage {

	
	protected TestPieChartPage() {
		super("com.objetdirect.tatami.testpages.client.TestPieChartPage", "Test Pie Charts");
	}

	public Widget getTestPage() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML("<span>1: Pie with internal labels.</span>"));
		panel.add(getChart1());
		panel.add(new HTML("<span>2: Pie with external labels and precision=0.</span>"));
		panel.add(getChart2());
		panel.add(new HTML("<span>3/4: Two pies with internal and external labels with a constant radius."));
		panel.add(getChart3());
		panel.add(getChart4());
		panel.add(new HTML("<span>5/6: Pie with internal custom labels and custom colors.</span>"));
		panel.add(getChart5());
		panel.add(getChart6());
		panel.add(new HTML("<span>7: Degenerated pie with 1 element."));
		panel.add(getChart7());
		panel.add(new HTML("<span>8: Degenerated pie with 1 positive elements (out of 5)."));
		panel.add(getChart8());
		return panel;
	}	
	
	public Chart2D getChart1(){
		Chart2D chart = new Chart2D("300px","300px");
		chart.setTheme("PlotKit.blue");
		PiePlot<Integer> plot = new PiePlot<Integer>();
		plot.setFont("normal normal bold 12pt Tahoma");
		plot.setFontColor("white");
		plot.setLabelOffset(40);
		List<Integer> data = Arrays.asList((new Integer[]{4,2,1,1}));
		plot.addSerie(new Serie<Integer>(data));
		chart.addPlot(plot);
		return chart;
	}
	
	public Chart2D getChart2(){
		Chart2D chart = new Chart2D("300px","300px");
		chart.setTheme("PlotKit.blue");
		PiePlot<Integer> plot = new PiePlot<Integer>();
		plot.setFont("normal normal bold 12pt Tahoma");
		plot.setFontColor("black");
		plot.setLabelOffset(-25);
		plot.setPrecision(0);
		plot.addSerie(new Serie<Integer>(Arrays.asList(new Integer[]{4,2,1,1})));
		chart.addPlot(plot);
		return chart;
	}
	
	public Chart2D getChart3(){
		Chart2D chart = new Chart2D("300px","300px");
		chart.setTheme("PlotKit.green");
		PiePlot<Integer> plot = new PiePlot<Integer>();
		plot.setFont("normal normal bold 12pt Tahoma");
		plot.setFontColor("white");
		plot.setLabelOffset(25);
		plot.setRadius(90);
		plot.addSerie(new Serie<Integer>(Arrays.asList(new Integer[]{4,2,1,1})));
		chart.addPlot(plot);
		return chart;
	}
	
	public Chart2D getChart4(){
		Chart2D chart = new Chart2D("300px","300px");
		chart.setTheme("PlotKit.green");
		PiePlot<Integer> plot = new PiePlot<Integer>();
		plot.setFont("normal normal bold 12pt Tahoma");
		plot.setFontColor("black");
		plot.setLabelOffset(-25);
		plot.setRadius(90);
		plot.addSerie(new Serie<Integer>(Arrays.asList(new Integer[]{4,2,1,1})));
		chart.addPlot(plot);
		return chart;
	}
	
	public Chart2D getChart5(){
		Chart2D chart = new Chart2D("300px","300px");
		chart.setTheme("PlotKit.red");
		PiePlot<PiePiece> plot = new PiePlot<PiePiece>();
		plot.setFont("normal normal bold 12pt Tahoma");
		plot.setFontColor("white");
		plot.setLabelOffset(40);
		PiePiece[] pieces = new PiePiece[]{
				new PiePiece(4.,"Red"),
				new PiePiece(2.,"Green"),
				new PiePiece(1.,"Blue"),
				new PiePiece(1.,"Other")
		};
		plot.addSerie(new Serie<PiePiece>(Arrays.asList(pieces)));
		chart.addPlot(plot);
		return chart;
	}
	
	public Chart2D getChart6(){
		Chart2D chart = new Chart2D("300px","300px");
		chart.setTheme("PlotKit.red");
		PiePlot<PiePiece> plot = new PiePlot<PiePiece>();
		plot.setFont("normal normal bold 12pt Tahoma");
		plot.setFontColor("white");
		plot.setLabelOffset(40);
		PiePiece[] pieces = new PiePiece[]{
				new PiePiece(4.,"Red","red"),
				new PiePiece(2.,"Green","green"),
				new PiePiece(1.,"Blue","blue"),
				new PiePiece(1.,"Other","white","black")
		};
		plot.addSerie(new Serie<PiePiece>(Arrays.asList(pieces)));
		chart.addPlot(plot);
		return chart;
	}
	
	public Chart2D getChart7(){
		Chart2D chart = new Chart2D("200px","200px");
		chart.setTheme("Adobebricks");
		PiePlot<Integer> plot = new PiePlot<Integer>();
		plot.setFont("normal normal bold 12pt Tahoma");
		plot.setFontColor("white");
		plot.setRadius(80);
		plot.addSerie(new Serie<Integer>(Arrays.asList(new Integer[]{4})));
		chart.addPlot(plot);
		return chart;
	}
	
	public Chart2D getChart8(){
		Chart2D chart = new Chart2D("200px","200px");
		chart.setTheme("Algae");
		PiePlot<PiePiece> plot = new PiePlot<PiePiece>();
		plot.setFont("normal normal bold 12pt Tahoma");
		plot.setFontColor("white");
		plot.setRadius(80);
		PiePiece[] pieces = new PiePiece[]{
				new PiePiece(-1.,"Red","red"),
				new PiePiece(5.,"Green","green"),
				new PiePiece(0.,"Blue","blue"),
				new PiePiece(0.,"Other","white","black")
		};
		plot.addSerie(new Serie<PiePiece>(Arrays.asList(pieces)));
		chart.addPlot(plot);
		return chart;
	}
	
	
}
