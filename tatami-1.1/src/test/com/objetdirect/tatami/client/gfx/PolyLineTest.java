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

public class PolyLineTest extends TestGraphicObject {

	private final Point[] arrow = {
			new Point(-2, 15),new Point(2, 15),new Point(2, -105),
			new Point(6, -105),new Point(0, -116),new Point(-6, -105),
			new Point(-2, -105),new Point(-2, 15)	}; 
	
	
	protected GraphicObject createInstance() {
		return new Polyline(arrow);
	}

	
	public Polyline toPolyline()  {
		return (Polyline)component;
	}
	
	public void testgetPoints() {
		initGraphics();
		assertEquals(arrow,toPolyline().getPoints());
	}
}
