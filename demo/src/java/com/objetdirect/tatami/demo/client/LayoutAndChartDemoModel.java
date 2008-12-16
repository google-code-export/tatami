package com.objetdirect.tatami.demo.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Random;
import com.objetdirect.tatami.client.charting.Point;
import com.objetdirect.tatami.client.charting.Serie;
import com.objetdirect.tatami.client.data.AbstractDataStore;
import com.objetdirect.tatami.client.data.DatumChangeListener;
import com.objetdirect.tatami.client.data.DefaultDataStore;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.grid.GridDataStore;
import com.objetdirect.tatamix.client.hmvc.ControllerProcessor;
import com.objetdirect.tatamix.client.hmvc.Event;
import com.objetdirect.tatamix.client.hmvc.ModelEvent;
import com.objetdirect.tatamix.client.hmvc.ModelImpl;

public class LayoutAndChartDemoModel extends ModelImpl implements TatamiDemoEvent {
	
	private AbstractDataStore store;
	private Map<Item,Serie<Point>> series = new HashMap<Item,Serie<Point>>();
	private final String[] companyNames = {"Acme, inc.",
		"Widget Corp",
		"123 Warehousing",
		"Demo Company",
		"Smith and Co.",
		"Foo Bars",
		"ABC Telecom",
		"Fake Brothers",
		"QWERTY Logistics",
		"Demo, inc.",
		"Sample Company",
		"Sample, inc",
		"Acme Corp",
		"Allied Biscuit",
		"Ankh-Sto Associates",
		"Extensive Enterprise",
		"Galaxy Corp",
		"Globo-Chem",
		"Mr. Sparkle"};
	
	class DemoPoint extends Point{

		private String companyName;
		private String month;
		
		public DemoPoint(double x, double y, String companyName) {
			super(x, y);
			this.companyName = companyName;
		}

		@Override
		public String getTooltip() {
			return companyName + " share price in  " + LayoutAndChartGridColumnsDefinition.DEFINED_MONTHS[(int) getX()-1] + ": " + getY()+"$";
		}
	}
	
	public LayoutAndChartDemoModel() {
		series.clear();
		store = new GridDataStore();
		ControllerProcessor initGridProcessor = new ControllerProcessor() {
			public void run(Event event) {
				initDataStore();
				fire(new ModelEvent(CHART_DATASTORE_FULLUPDATE,store));
			}
		};
		register(INIT_CHART_GRID, initGridProcessor);
	}

	private void initDataStore(){
		store = new DefaultDataStore();
		for(int i = 0 ; i < this.companyNames.length;i++){
			Item companyItem = new Item();
			companyItem.setId(i);
			companyItem.setValue(LayoutAndChartGridColumnsDefinition.COMPANY_FIELD,this.companyNames[i]);
			List<Point> companyPoints = new ArrayList<Point>();
			double lastY = Random.nextInt(100) +1;
			for(int j = 1; j < 13 ; j++){
				int signum = Random.nextBoolean() ? 1 : -1;
				lastY = lastY + signum * Random.nextInt(10);
				companyPoints.add(new DemoPoint(j, lastY ,this.companyNames[i]));
			}
			companyItem.setValue(LayoutAndChartGridColumnsDefinition.DATA_FIELD,companyPoints);
			companyItem.setValue(LayoutAndChartGridColumnsDefinition.SHOW_GRAPH_FIELD,false);
			String color = LayoutAndChartGridColumnsDefinition.DEFINED_COLORS[i%LayoutAndChartGridColumnsDefinition.DEFINED_COLORS.length];
			companyItem.setValue(LayoutAndChartGridColumnsDefinition.COLOR_FIELD,color);
			store.add(companyItem);
		}
		store.addDatumChangeListener(new DatumChangeListener(){
			public void onDataChange(Item item, String attributeName,
					Object oldValue, Object newValue) {
				if(LayoutAndChartGridColumnsDefinition.SHOW_GRAPH_FIELD.equals(attributeName)){
					if((Boolean)newValue){
						Serie<Point> newSerie = generateSerie(item);
						series.put(item,newSerie);
						ModelEvent event = new ModelEvent(CHART_ADD_SERIE,this);
						event.setData(newSerie);
						fire(event);
					}else{
						ModelEvent event = new ModelEvent(CHART_REMOVE_SERIE,this);
						Serie<Point> serie = series.remove(item);
						if(serie != null){
							event.setData(serie);
							fire(event);
						}
					}
				}else if(LayoutAndChartGridColumnsDefinition.COLOR_FIELD.equals(attributeName)){
					Serie<Point> toUpdate = series.get(item);
					if(toUpdate != null){
						toUpdate.setStrokeColor((String) newValue);
						toUpdate.setFillColor((String) newValue);
						ModelEvent event = new ModelEvent(CHART_UPDATE_SERIES,this);
						event.setData(series.values());
						fire(event);
					}
				}
				
			}
			public void onDelete(Item item) {
				
			}
			public void onNew(Item item) {
				
			}
			
		});
		
	}
	
	
	
	Serie<Point> generateSerie(Item item){
		Serie<Point> serie = new Serie<Point>();
		serie.setData((List<Point>) item.getValue(LayoutAndChartGridColumnsDefinition.DATA_FIELD));
		serie.setFillColor((String) item.getValue(LayoutAndChartGridColumnsDefinition.COLOR_FIELD));
		serie.setStrokeColor((String) item.getValue(LayoutAndChartGridColumnsDefinition.COLOR_FIELD));
		serie.setName((String) item.getValue(LayoutAndChartGridColumnsDefinition.COMPANY_FIELD));
		return serie;
	}
	
}
