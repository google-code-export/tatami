package com.objetdirect.tatami.client.charting;

import java.util.List;

import com.objetdirect.tatami.client.DojoController;

public class Chart2DFactory {
	
	static{
		DojoController.getInstance().require("dojox.charting.Chart2D");
	}
	
	
	public static Chart2D createChart(){
		return new Chart2D("200px","200px");
	}
	
	public static Chart2D createSimpleChart(String plotType , List<?> data){
		Chart2D chart = createChart();
		Serie serie = new Serie(data);
		Plot plot = new Plot(plotType);
		plot.addSerie(serie);
		chart.addPlot(plot);
		return chart;
	}
	
	public static Chart2D createSimpleChart(String plotType , List<?>[] data){
		Chart2D chart = createChart();
		Plot plot = new Plot(plotType);
		for (int i = 0; i < data.length; i++) {
			Serie serie = new Serie(data[i]);
			plot.addSerie(serie);
		}
		chart.addPlot(plot);
		return chart;
	}
	
	
}
