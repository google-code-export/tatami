package com.objetdirect.tatami.demo.client;

import java.util.Arrays;

import com.google.gwt.user.client.ui.FlowPanel;
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
import com.objetdirect.tatami.client.charting.Serie;
import com.objetdirect.tatami.client.charting.Themes;
import com.objetdirect.tatami.client.charting.effects.EffectHighlight;
import com.objetdirect.tatami.client.charting.effects.EffectMagnify;
import com.objetdirect.tatami.client.charting.effects.EffectMoveSlice;
import com.objetdirect.tatami.client.charting.effects.EffectShake;
import com.objetdirect.tatami.client.charting.effects.EffectTooltip;
import com.objetdirect.tatamix.client.hmvc.CompositeView;

public class ChartDemo extends CompositeView{

	  private FlowPanel layout;
	
	private class MyFlowPanel extends FlowPanel{

		@Override
		protected void doAttachChildren() {
			super.doAttachChildren();
		}
		
	}
	
	public ChartDemo(){
		layout = new MyFlowPanel();
		initWidget(layout);
		initComponents();
	}
	
	public void initComponents(){
		layout.add(getChart1());
		layout.add(getChart2());
		layout.add(getChart3());
		layout.add(getChart4());
	}
	
	public Widget getChart1(){
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
		return chartPanel;
	}

	public Widget getChart2(){
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
		return chartPanel;
	}
	
	public Widget getChart3(){
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
		FlowPanel chartPanel = new FlowPanel();
		chartPanel.add(chart);
		chartPanel.add(new Legend(chart,false));
		return chartPanel;
	}
	
	public Widget getChart4(){
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
		return chartPanel;
	}
	
	
}
