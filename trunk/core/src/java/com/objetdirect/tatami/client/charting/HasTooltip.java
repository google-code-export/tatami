package com.objetdirect.tatami.client.charting;

/**
 * This interface describes object which can be added to series and may have a tooltip
 * 
 * @author rdunklau
 *
 */
public interface HasTooltip {

	public String getTooltip();
	
	public void setTooltip(String tooltip);
}
