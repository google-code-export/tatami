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

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Widget;

public class PaddleTest extends DefaultTatamiTest{

    /**
     * Tests the change value on the Paddle
     *
     */
	public void testChangeValue() {
    PaddleModule module = new PaddleModule(0,100,0,0,100,0);
    module.onModuleLoad();
    
    final Paddle paddle = module.getPaddle();
    paddle.addChangeListener(new ChangeListener() {
    	public void onChange(Widget sender) {
    		 assertEquals(paddle.getValueX(),10);
    		 assertEquals(paddle.getValueY(),30);
    	}
    });
    paddle.setValue(10, 30);
    
 }

}//end of the class
