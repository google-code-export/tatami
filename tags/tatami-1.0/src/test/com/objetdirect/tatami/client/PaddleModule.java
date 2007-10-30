/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@objectweb.org
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * Authors: Vianney Grassaud
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class PaddleModule implements EntryPoint {

	private Paddle paddle;

	private int minimumX;

	private int maximumX;

	private int initialValueX;

	private int minimumY;

	private int maximumY;

	private int initialValueY;

	/**
	 * Creates the module
	 * @param minX the minimal value on the X axis for the Paddle
	 * @param maxX the maximal value on the X axis for the Paddle
	 * @param valueX the initial value on the X axis for the Paddle
	 * @param minY the minimal value on the Y axis for the Paddle
	 * @param maxY the maximal value on the Y axis for the Paddle
	 * @param valueY the initial value on the Y axis for the Paddle
	 */
	public PaddleModule(int minX, int maxX, int valueX, int minY, int maxY,
			int valueY) {
		minimumX = minX;
		maximumX = maxX;
		initialValueX = valueX;
		minimumY = minY;
		maximumY = maxY;
		initialValueY = valueY;

	}

	/**
	 * Loads the module to test components
	 */
	public void onModuleLoad() {
		RootPanel.get().add(getPaddle());

	}

	/**
	 * Returns the instance of the Paddle.
	 * @return an instance of Paddle, a new instance if created if it was not existed
	 */
	public Paddle getPaddle() {
		if (paddle == null) {
			paddle = new Paddle(minimumX, maximumX, initialValueX, minimumY,maximumY, initialValueY);
		}
		return this.paddle;
	}

}
