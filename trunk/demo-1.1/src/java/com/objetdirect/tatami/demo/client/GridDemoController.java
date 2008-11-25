package com.objetdirect.tatami.demo.client;

import com.objetdirect.tatamix.client.hmvc.ControllerImpl;
import com.objetdirect.tatamix.client.hmvc.ControllerProcessor;
import com.objetdirect.tatamix.client.hmvc.Event;
import com.objetdirect.tatamix.client.hmvc.ModelEvent;
import com.objetdirect.tatamix.client.hmvc.ViewEvent;

public class GridDemoController extends ControllerImpl implements TatamiDemoEvent{
	
	final public static String CONTROLLER_NAME = "GridController";
	
	public GridDemoController() {
		ControllerProcessor addRowProcessor = new ControllerProcessor(){
			public void run(Event event) {
				fire(new ModelEvent(event));
			}
		};
		ControllerProcessor removeRowProcessor = new ControllerProcessor(){
			public void run(Event event) {
				fire(new ModelEvent(event));
			}
		};
		ControllerProcessor initGridProcessor = new ControllerProcessor(){
			public void run(Event event) {
				fire(new ModelEvent(event));
			}
		};
		ControllerProcessor dataStoreFullUpdateProcessor = new ControllerProcessor(){
			public void run(Event event) {
				fire(new ViewEvent(event));
			}
		};
		ControllerProcessor showContentEvent = new ControllerProcessor(){
			public void run(Event event) {
				fire(new ModelEvent(INIT_GRID,GridDemoController.this));
			}
		};
		register(ADD_ROW, addRowProcessor);
		register(REMOVE_ROWS, removeRowProcessor);
		register(INIT_GRID, initGridProcessor);
		register(DATASTORE_FULLUPDATE, dataStoreFullUpdateProcessor);
		register(SHOW_GRID_DEMO, showContentEvent);
	}

	public void init(){
		
	}
	
	
}
