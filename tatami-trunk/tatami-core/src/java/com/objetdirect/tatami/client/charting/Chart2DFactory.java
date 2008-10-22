package com.objetdirect.tatami.client.charting;

import java.util.List;

import com.objetdirect.tatami.client.DojoController;

public class Chart2DFactory {
	
	static{
		DojoController.getInstance().require("dojox.charting.Chart2D");
	}
	
	
	public static Chart2D createChart(){
		return new Chart2D();
	}
	
	public static Chart2D createChart(String plotType , List<?> serie){
		Chart2D chart = createChart();
		Axis xAxis = new Axis(Axis.BOTTOM | Axis.HORIZONTAL);
		Axis yAxis = new Axis(Axis.LEFT | Axis.VERTICAL);
		chart.addAxis(xAxis);
		chart.addAxis(yAxis);
		Plot plot = new Plot(plotType);
		chart.addPlot(plot);
		return chart;
	}
	
	
}
