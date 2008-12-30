package com.objetdirect.tatami.demo.client;

import java.util.Collection;
import java.util.Iterator;

import com.objetdirect.tatami.client.charting.Axis;
import com.objetdirect.tatami.client.charting.Chart2D;
import com.objetdirect.tatami.client.charting.Legend;
import com.objetdirect.tatami.client.charting.Plot2D;
import com.objetdirect.tatami.client.charting.Point;
import com.objetdirect.tatami.client.charting.Serie;
import com.objetdirect.tatami.client.charting.effects.EffectHighlight;
import com.objetdirect.tatami.client.charting.effects.EffectMagnify;
import com.objetdirect.tatami.client.charting.effects.EffectTooltip;
import com.objetdirect.tatami.client.data.AbstractDataStore;
import com.objetdirect.tatami.client.grid.Cell;
import com.objetdirect.tatami.client.grid.Grid;
import com.objetdirect.tatami.client.grid.editor.BooleanEditor;
import com.objetdirect.tatami.client.grid.editor.ComboBoxEditor;
import com.objetdirect.tatami.client.grid.formatters.BaseFormatter;
import com.objetdirect.tatami.client.grid.formatters.CurrencyFormatter;
import com.objetdirect.tatami.client.grid.formatters.Formatter;
import com.objetdirect.tatami.client.layout.BorderContainer;
import com.objetdirect.tatami.client.layout.ContentPanel;
import com.objetdirect.tatamix.client.hmvc.CompositeView;
import com.objetdirect.tatamix.client.hmvc.ControllerProcessor;
import com.objetdirect.tatamix.client.hmvc.Event;
import com.objetdirect.tatamix.client.hmvc.ViewEvent;

public class LayoutAndChartDemo extends CompositeView implements TatamiDemoEvent {
	
	BorderContainer layout;
	Grid grid;
	Chart2D chart;
	Plot2D<Point> plot;
	Legend legend;
	ContentPanel chartAndLegendCP;
	
	
	public LayoutAndChartDemo() {
		layout = new BorderContainer();
		ControllerProcessor updateSeriesProcessor = new ControllerProcessor() {
			public void run(Event event) {
				if(chart == null){
					initChart();
				}
				Collection<Serie<Point>> series = (Collection<Serie<Point>>)event.getData();
				for (Iterator<Serie<Point>> iterator = series.iterator(); iterator.hasNext();) {
					Serie<Point> serie =  iterator.next();
					plot.addSerie(serie);
				}
				chart.refreshChart();
				legend.refresh();
			}
		};
		ControllerProcessor addSerieProcessor = new ControllerProcessor() {
			public void run(Event event) {
				if(chart == null){
					initChart();
				}
				plot.addSerie((Serie<Point>) event.getData());
				chart.refreshChart();
				legend.refresh();
			}
		};	
		ControllerProcessor removeSerieProcessor = new ControllerProcessor() {
			public void run(Event event) {
				if(chart == null){
					initChart();
				}
				plot.removeSerie((Serie<Point>) event.getData());
				chart.refreshChart();
				legend.refresh();
			}
		};
		ControllerProcessor dataStoreUpdateProcessor = new ControllerProcessor(){
			public void run(Event event) {
				grid.setStore((AbstractDataStore) event.getSource());
			}
		};
		ControllerProcessor resetChartProcessor = new ControllerProcessor(){
			public void run(Event event) {
				if ( chartAndLegendCP != null) {
				layout.remove(chartAndLegendCP);
				}
				chart = null;
				plot = null;
				legend = null;
			}
		};
		register(CHART_DATASTORE_FULLUPDATE, dataStoreUpdateProcessor);
		register(CHART_UPDATE_SERIES, updateSeriesProcessor);
		register(CHART_REMOVE_SERIE, removeSerieProcessor);
		register(CHART_ADD_SERIE,addSerieProcessor);
		register(RESET_CHART,resetChartProcessor);
	}

	public void init(){
		initWidget(layout);
		initComponents();
	}
	
	private void initComponents() {
		layout.setSize("100%","500px");
		layout.setLiveSplitters(false);
		initGrid();
		ContentPanel gridCp = new ContentPanel(grid);
		layout.add(gridCp,BorderContainer.REGION_CENTER,false,"20%","100%");
		fire(new ViewEvent(INIT_CHART_GRID,LayoutAndChartDemo.this));
	}
	
	private void initGrid(){
		grid = new Grid();
		grid.setSize("100%","100%");
		grid.addColumn(LayoutAndChartGridColumnsDefinition.COMPANY_COLUMN_NAME,LayoutAndChartGridColumnsDefinition.COMPANY_FIELD);
		grid.addColumn(LayoutAndChartGridColumnsDefinition.SHOW_GRAPH_COLUMN_NAME,LayoutAndChartGridColumnsDefinition.SHOW_GRAPH_FIELD,new BooleanEditor());
		Formatter colorFormatter = new BaseFormatter() {
			public Object format(Object toFormat) {
				return "<span style='color:"+toFormat+";'>"+toFormat+"</span>";
			}
		};
		Cell colorCell = new Cell(LayoutAndChartGridColumnsDefinition.COLOR_FIELD,LayoutAndChartGridColumnsDefinition.COLOR_COLUMN_NAME,new ComboBoxEditor(LayoutAndChartGridColumnsDefinition.DEFINED_COLORS),colorFormatter);
		colorCell.setWidth("auto");
		grid.addCell(colorCell);
	}
	
	private void initChart(){
		BorderContainer chartPanel = new BorderContainer();
		//chartPanel.setGutters(false);
		chart = new Chart2D("100%","100%");
		chartPanel.setSize("100%", "100%");
		plot = new Plot2D<Point>(Plot2D.PLOT_TYPE_MARKERS_LINES);
		plot.setShadow(2, 2, 2);
		plot.setTension("S");
		plot.addEffect(new EffectTooltip());
		plot.addEffect(new EffectHighlight());
		plot.addEffect(new EffectMagnify());
		chart.addPlot(plot);
		Axis xAxis = Axis.simpleXAxis();
		xAxis.addLabels(LayoutAndChartGridColumnsDefinition.DEFINED_MONTHS);
		xAxis.setMajorLabels(true);
		Axis yAxis = Axis.simpleYAxis();
		yAxis.setMinorTicks(false);
		yAxis.setMin(0);
		chart.setDefaultXAxis(xAxis);
		chart.setDefaultYAxis(yAxis);
		ContentPanel chartCp = new ContentPanel(chart);
		//chartCp.setHeight("90%");
		chartPanel.add(chartCp,BorderContainer.REGION_CENTER,false);
		legend = new Legend(chart);
		legend.setHorizontal(false);
		ContentPanel legendPanel = new ContentPanel(legend);
		legendPanel.setHeight("10%");
		chartPanel.add(legendPanel,BorderContainer.REGION_BOTTOM,false);
		chartAndLegendCP = new ContentPanel(chartPanel);
		chartAndLegendCP.setWidth("60%");
		layout.add(chartAndLegendCP,BorderContainer.REGION_RIGHT,true,"150","Infinity");
		layout.adaptSize();
	}
	
}
