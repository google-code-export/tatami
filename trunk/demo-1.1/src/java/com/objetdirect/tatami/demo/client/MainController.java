package com.objetdirect.tatami.demo.client;

import com.objetdirect.tatamix.client.hmvc.ControllerImpl;
import com.objetdirect.tatamix.client.hmvc.ControllerProcessor;
import com.objetdirect.tatamix.client.hmvc.Event;
import com.objetdirect.tatamix.client.hmvc.MVC;
import com.objetdirect.tatamix.client.hmvc.MVCImpl;
import com.objetdirect.tatamix.client.hmvc.ViewEvent;

public class MainController extends ControllerImpl implements TatamiDemoEvent {


	private MVC[] triads;
	private final int SIZE = 6;
	private final int sliderMVC = 0;
	private final int dateTimeMVC = 1 ;
	private final int colorMVC = 2;
	private final int gfxMVC= 3;
	private final int dataGridMVC = 4;
	private final int dndMVC = 5;



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

		ControllerProcessor showHome = new ControllerProcessor() {
			public void run(Event evt) {
				fire(new ViewEvent(SHOW_HOME,this));
			}
		};

		register(SHOW_SLIDER_DEMO,showSlider);
		register(SHOW_DATE_TIME_DEMO,showDateTime);
		register(SHOW_GFX_DEMO,showGfx);
		register(SHOW_COLOR_DEMO,showColor);
		register(SHOW_DND_DEMO,showDnd);
		register(SHOW_HOME,showHome);
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
			triads[gfxMVC].setView(new GfxDemo());
		}
		displayTriad(gfxMVC);
	}

	public void showColor() {
		if ( triads[colorMVC].getView() == null) {
			triads[colorMVC].setView(new ColorDemo());
		}
		displayTriad(colorMVC);
	}

	public void showDnd() {
		triads[dndMVC].setView(new DragAndDropDemo());
		displayTriad(dndMVC);
	}



	private void displayTriad(int index) {
		fire(new ViewEvent(UPDATE_CONTENT,this,triads[index].getView()));
	}


}//end of class
