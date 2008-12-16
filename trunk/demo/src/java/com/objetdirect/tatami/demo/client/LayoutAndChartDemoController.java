package com.objetdirect.tatami.demo.client;

import com.objetdirect.tatamix.client.hmvc.ControllerImpl;
import com.objetdirect.tatamix.client.hmvc.ControllerProcessor;
import com.objetdirect.tatamix.client.hmvc.Event;
import com.objetdirect.tatamix.client.hmvc.ModelEvent;
import com.objetdirect.tatamix.client.hmvc.ViewEvent;

public class LayoutAndChartDemoController extends ControllerImpl implements TatamiDemoEvent{

	public final static String CONTROLLER_NAME = "LayoutAndChartDemoController";
	
	public LayoutAndChartDemoController(){
		ControllerProcessor transmitProcessor = new ControllerProcessor(){
			public void run(Event event) {
				forward(event);
			}
		};
		ControllerProcessor showContentEvent = new ControllerProcessor(){
			public void run(Event event) {
				fire(new ModelEvent(INIT_CHART_GRID,LayoutAndChartDemoController.this));
				fire(new ViewEvent(RESET_CHART,LayoutAndChartDemoController.this));
			}
		};
		register(CHART_DATASTORE_FULLUPDATE, transmitProcessor);
		register(CHART_UPDATE_SERIES, transmitProcessor);
		register(CHART_ADD_SERIE, transmitProcessor);
		register(CHART_REMOVE_SERIE, transmitProcessor);
		register(INIT_CHART_GRID,transmitProcessor);
		register(SHOW_CHART_DEMO,showContentEvent);
	}
	
}
