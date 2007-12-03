/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@googlegroups.com
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
 * Authors:  Vianney Grassaud
 * Initial developer(s): Vianney Grassaud
 * Contributor(s):
 */
package com.objetdirect.tatami.client.gfx;

public class PathTest extends TestGraphicObject {

	protected void initGraphics() {
		super.initGraphics();
        toPath().moveTo(20,20);
        toPath().lineTo(95,20);
        toPath().lineTo(102,56);
        toPath().lineTo(13,56);
        toPath().lineTo(20,20);
	}
	
	protected GraphicObject createInstance() {
			return new Path();
	}

	/**
	 * Cast the component to a <code>Path</code>
	 * @return a <code>Path</code>
	 */
	public Path toPath() {
		return (Path)component;
	}
	
	public void testAbsoluteMode() {
		initGraphics();
		toPath().setAbsoluteMode(false);
		assertFalse(toPath().getAbsoluteMode());
	}
	
	/**
	 * Tests if the arcTo method is well executed 
	 **/
	public void testArcTo() {
		initGraphics();
		toPath().arcTo(20, 30, 35, true, true, 20,35);
	}
	
	/**
	 * Tests if the curve methods are well executed 
	 **/
	public void testCurve() {
		initGraphics();
		toPath().curveTo(20, 30, 35, 40, 20,35);
     	toPath().qCurveTo(50,30,40,35);
    	toPath().qSmoothCurveTo(45,45);
		toPath().smoothCurveTo(70,15,60,65);
	}

	/**
	 * Tests if the closePath method is welle executed
	 *
	 */
	public void testClosePath() {
		initGraphics();
		toPath().closePath();
	}
   /**
    * Tests if the vLineTo and hLineTo is well executed 
    *
    */
    public void testVHLine() {
    	initGraphics();
    	toPath().vLineTo(50);
    	toPath().hLineTo(-56);
    }
}//end of class
