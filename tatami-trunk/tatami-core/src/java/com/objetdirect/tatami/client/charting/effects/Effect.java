package com.objetdirect.tatami.client.charting.effects;

import com.objetdirect.tatami.client.charting.Chart2D;
import com.objetdirect.tatami.client.charting.Plot;

public interface Effect {
	
	/**
	 * @param event
	 */
	public void processEvent(EffectEvent event);
	
	void destroyEffect();
	
	void initEffect(Chart2D chart, Plot<?> plot);
}
