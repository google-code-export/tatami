package com.objetdirect.tatamix.client.widget.renderer;

import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.grid.formatters.BaseFormatter;

public  abstract class ObjectFormatter extends BaseFormatter {

	
	protected abstract Widget getObjectRenderer(Object object);

	
	public String format(Object toFormat) {
        Widget widget = getObjectRenderer(toFormat);
        
		return widget.getElement().getString();
	}
	
	
	
			
	
		
		
		

	}
	

