package com.objetdirect.tatami.client.layout;

import com.google.gwt.user.client.ui.Panel;
import com.objetdirect.tatami.client.HasDojo;

/**
 * This interface is designed as a "marker" for Tatami's panel implementations
 * compatible with dojo layouts. 
 * 
 * It as only one method, which implementation should usally return the panel itself,
 * since it is intended for it to be an actual GWT Panel subclass.
 * 
 * @author rdunklau
 *
 */
public interface DojoPanel extends HasDojo{
	/**
	 * @return the dojopanel as a widget, to avoid unecessary cast operations
	 */
	public Panel asPanel();
	
	
}
