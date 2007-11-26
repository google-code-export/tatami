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
  

  private GraphicCanvas getCanvas() {
	  if ( canvas == null) {
		  this.canvas = new GraphicCanvas();
		  
	  }
	  RootPanel.get().add(canvas,10,10);
	  canvas.setSize("300px","300px");
	  return canvas;
  }


  public void testAddGraphicObject() {
	  
  }
  
  public void tearDown() {
	  canvas = null;
	  moved = false;
	  pressed = false;
	  clicked = false;
	  released = false;
		  
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
	  
	}



}//end of class
