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

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.objetdirect.tatami.client.DefaultTatamiTest;
import com.objetdirect.tatami.client.test.Task;
import com.objetdirect.tatami.client.test.TestUtil;

public class GraphicCanvasTest extends DefaultTatamiTest implements GraphicObjectListener {

  private GraphicCanvas canvas;
  private boolean clicked = false;
  private boolean moved = false;
  private boolean pressed = false;
  private boolean released = false;
  private boolean doubleClicked = false;
  

  private GraphicCanvas getCanvas() {
	  if ( canvas == null) {
		  this.canvas = new GraphicCanvas();
		  
	  }
	  RootPanel.get().add(canvas,10,10);
	  canvas.setSize("300px","300px");
	  return canvas;
  }

  /**
   * Tests the adding of some <code>GraphicObject</code>
   *  and the removing of its.
   *
   */
  public void testAddGraphicObject() {
	   Circle circle = new Circle(50);
	   Rect rect = new Rect(15,30);
	   canvas = getCanvas();
	   canvas.add(circle, 50, 50);
	   canvas.add(rect, 80, 80);
	   assertSame(rect,canvas.getGraphicObject(1));
	   canvas.remove(circle);
	   assertSame(rect,canvas.getGraphicObject(0));
	   canvas.add(circle, 50, 50);
	   canvas.clear();
	   assertEquals(0,canvas.countGraphicObject());
  }
  
  
  public void gwtTearDown() throws Exception{
	  canvas = null;
	  moved = false;
	  pressed = false;
	  clicked = false;
	  released = false;
	  doubleClicked = false;
	  super.gwtTearDown();
		  
  }
  
  public void testListener() {
	  canvas = getCanvas();
	  canvas.add(new Circle(50),75,75);
	  canvas.addGraphicObjectListener(this);
	  
	  final Task taskClick = new Task() {
			public void run() {
              	System.out.println("mouse clicked on canvas");
				assertTrue("No click event",clicked);
			}
	  };
	  
	  final Task taskDblClick = new Task() {
			public void run() {
            	System.out.println("mouse double clicked on canvas");
				assertTrue("No double click event",doubleClicked);
			}
	  };
	  
	  final Task taskPressed = new Task() {
			public void run() {
				System.out.println("mouse pressed on canvas");
				assertTrue("No press event",pressed);
			}
	  };
	  

	  final Task taskReleased = new Task() {
			public void run() {
				System.out.println("mouse released on canvas");
				assertTrue("No release event",released);
			}
	  };
	  
	  final Task taskMove = new Task() {
			public void run() {
				System.out.println("mouse moved on canvas");
				assertTrue("No move event",moved);
			}
	  };
	  Timer timer = new Timer() {
			public void run(){
			    TestUtil.fireEvent("click", 75,75,TestUtil.MOUSE_BUTTON_1,1,taskClick);
			    TestUtil.fireEvent("dblclick", 75,75,TestUtil.MOUSE_BUTTON_1,1,taskDblClick);
			    TestUtil.fireEvent("mouseup", 75,75,TestUtil.MOUSE_BUTTON_1,1,taskPressed);
			    TestUtil.fireEvent("mousedown", 75,75,TestUtil.MOUSE_BUTTON_1,1,taskReleased);
			    TestUtil.fireEvent("mousemove", 75,75,TestUtil.MOUSE_BUTTON_1,1,taskMove);
			    finishTest();
			}
	   };
	   canvas.removeGraphicObjectListener(this);
	   this.delayTestFinish(150);
	   timer.schedule(100);
  }

  
  
  public void mouseClicked(GraphicObject graphicObject,Event evt) {
	  clicked = true;
	}


  public void mouseMoved(GraphicObject graphicObject, Event evt) {
	  moved = true;
	}


  public void mousePressed(GraphicObject graphicObject,Event evt) {
       pressed = true;
	}


  public void mouseReleased(GraphicObject graphicObject,Event evt) {
	   released = true;
	}
  
  public void mouseDblClicked(GraphicObject graphicObject,Event evt) {
	  doubleClicked = true;
	}



}//end of class
