package com.objetdirect.tatami.demo.client;

import com.objetdirect.tatamix.client.hmvc.ControllerImpl;
import com.objetdirect.tatamix.client.hmvc.ControllerProcessor;
import com.objetdirect.tatamix.client.hmvc.Event;

public class GFXController extends ControllerImpl implements TatamiDemoEvent {

	
	public GFXController() {
		
		ControllerProcessor forward = new ControllerProcessor() {
			public void run(Event event) {
				forward(event);
			}
		};
					
		
		
		register(DRAW_GRAPHIC_OBJECT,forward);
		register(DRAW_CIRCLE,forward);
		register(DRAW_POLYLINE,forward);
		register(DRAW_LINE,forward);
		register(DRAW_ELLIPSE,forward);
		register(DRAW_RECT,forward);
		register(DRAW_IMAGE,forward);
		register(DRAW_VIRTUAL_GROUP,forward);
		register(DRAW_PATH,forward);
		register(DRAW_TEXT,forward);
		register(DRAW_TEXT_PATH,forward);
	}
	
}
