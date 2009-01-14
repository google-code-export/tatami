package com.objetdirect.tatami.demo.client;

import com.objetdirect.tatamix.client.hmvc.Controller;
import com.objetdirect.tatamix.client.hmvc.ControllerImpl;
import com.objetdirect.tatamix.client.hmvc.ControllerProcessor;
import com.objetdirect.tatamix.client.hmvc.Event;
import com.objetdirect.tatamix.client.hmvc.MVC;
import com.objetdirect.tatamix.client.hmvc.MVCImpl;
import com.objetdirect.tatamix.client.hmvc.Model;
import com.objetdirect.tatamix.client.hmvc.View;
import com.objetdirect.tatamix.client.hmvc.ViewEvent;

public class MainController extends ControllerImpl implements TatamiDemoEvent {


	private MVC[] triads;
	private final int SIZE = 9;
	
	private final int sliderMVC = 0;
	private final int dateTimeMVC = 1 ;
	private final int colorMVC = 2;
	private final int gfxMVC= 3;
	private final int dataGridMVC = 4;
	private final int dndMVC = 5;
	private final int layoutAndChartMVC = 6;
	private final int chartMVC = 7;
	private final int showBug = 8;


	public  MainController() {
		super();
        triads = new MVC[SIZE];
        initAllMVC();

		ControllerProcessor showSlider = new ControllerProcessor() {
			public void run(Event evt) {
				showSlider();
			}
		};
		ControllerProcessor showDnd = new ControllerProcessor() {
			public void run(Event evt) {
				showDnd();
			}
		};
		ControllerProcessor showDateTime = new ControllerProcessor() {
			public void run(Event evt) {
				showDateTime();
			}
		};
		ControllerProcessor showColor = new ControllerProcessor() {
			public void run(Event evt) {
				showColor();
			}
		};
		ControllerProcessor showGfx = new ControllerProcessor() {
			public void run(Event evt) {
				showGfx();
			}
		};

		ControllerProcessor showGrid = new ControllerProcessor() {
			public void run(Event evt) {
				showGrid();
			}
		};
		
		ControllerProcessor showLayoutAndChart = new ControllerProcessor() {
			public void run(Event evt) {
				showLayoutAndChart();
			}
		};
		
		ControllerProcessor showChart = new ControllerProcessor() {
			public void run(Event evt) {
				showChart();
			}
		};
		
		ControllerProcessor showHome = new ControllerProcessor() {
			public void run(Event evt) {
				fire(new ViewEvent(SHOW_HOME,this));
			}
		};
		
		ControllerProcessor showBug = new ControllerProcessor() {
			public void run(Event event) {
				showBug();
			}
		};
		
		
		register(SHOW_SLIDER_DEMO,showSlider);
		register(SHOW_DATE_TIME_DEMO,showDateTime);
		register(SHOW_GFX_DEMO,showGfx);
		register(SHOW_COLOR_DEMO,showColor);
		register(SHOW_DND_DEMO,showDnd);
		register(SHOW_GRID_DEMO,showGrid);
		register(SHOW_LAYOUT_AND_CHART_DEMO,showLayoutAndChart);
		register(SHOW_CHART_DEMO,showChart);
		register(SHOW_HOME,showHome);
		register(SHOW_BUG,showBug);
	}


	private void initAllMVC() {
		for (int i=0; i < SIZE; i++) {
		 triads[i] = MVCImpl.createTriad(null,null,this);
		}
	}



	public void showSlider() {
		if ( triads[sliderMVC].getView() == null) {
			triads[sliderMVC].setView(new SliderDemo());
		}
		displayTriad(sliderMVC);
	}

	public void showDateTime() {
		if ( triads[dateTimeMVC].getView() == null) {
			triads[dateTimeMVC].setView(new DateTimeDemo());
		}
		displayTriad(dateTimeMVC);
	}

	public void showGfx() {
		if ( triads[gfxMVC].getView() == null) {
			Controller controller = new GFXController();
			Model model = new GFXModel();
			triads[gfxMVC].setView(new GfxDemo());
			triads[gfxMVC].setModel(model);
			triads[gfxMVC].setController(controller);
			addChild("GFX",controller);
		}
		displayTriad(gfxMVC);
	}

	public void showColor() {
		if ( triads[colorMVC].getView() == null) {
			triads[colorMVC].setView(new ColorDemo());
		}
		displayTriad(colorMVC);
	}
	
	public void showGrid() {
		if ( triads[dataGridMVC].getView() == null) {
			GridDemoController gridDemoController = new GridDemoController();
			GridDemo demo = new GridDemo();
			triads[dataGridMVC].setView(demo);
			triads[dataGridMVC].setController(gridDemoController);
			triads[dataGridMVC].setModel(new GridModel());
			addChild(GridDemoController.CONTROLLER_NAME, gridDemoController);
			demo.init();
		}else{
			delegateToChild(GridDemoController.CONTROLLER_NAME,new ViewEvent(SHOW_GRID_DEMO,this));
		}
		displayTriad(dataGridMVC);
	}
	
	public void showChart() {
		if ( triads[chartMVC].getView() == null) {
			triads[chartMVC].setView(new ChartDemo());
		}
		displayTriad(chartMVC);
	}
	
	public void showLayoutAndChart() {
		if ( triads[layoutAndChartMVC].getView() == null) {
			LayoutAndChartDemoController layoutAndChartDemoController = new LayoutAndChartDemoController();
			LayoutAndChartDemo demo = new LayoutAndChartDemo();
			triads[layoutAndChartMVC].setView(demo);
			triads[layoutAndChartMVC].setController(layoutAndChartDemoController);
			triads[layoutAndChartMVC].setModel(new LayoutAndChartDemoModel());
			addChild(LayoutAndChartDemoController.CONTROLLER_NAME, layoutAndChartDemoController);
			demo.init();
		}else{
			delegateToChild(LayoutAndChartDemoController.CONTROLLER_NAME,new ViewEvent(SHOW_LAYOUT_AND_CHART_DEMO,this));
		}
		displayTriad(layoutAndChartMVC);
	}

	public void showDnd() {
		triads[dndMVC].setView(new DragAndDropDemo());
		displayTriad(dndMVC);
	}


	private void showBug() {
		if ( triads[showBug].getView() == null) {
			View view = new BugView();
			triads[showBug].setView(view);
		}
		displayTriad(showBug);
	} 
	
	public void init(boolean debug) {
		if ( debug) {
			fire(new ViewEvent(DEBUG,this));
			
		}
	}

	private void displayTriad(int index) {
		fire(new ViewEvent(UPDATE_CONTENT,this,triads[index].getView()));
	}


}//end of class
