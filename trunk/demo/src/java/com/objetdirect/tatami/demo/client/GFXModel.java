package com.objetdirect.tatami.demo.client;

import com.objetdirect.tatami.client.gfx.Circle;
import com.objetdirect.tatami.client.gfx.Ellipse;
import com.objetdirect.tatami.client.gfx.Font;
import com.objetdirect.tatami.client.gfx.ImageGfx;
import com.objetdirect.tatami.client.gfx.Line;
import com.objetdirect.tatami.client.gfx.Path;
import com.objetdirect.tatami.client.gfx.Point;
import com.objetdirect.tatami.client.gfx.Polyline;
import com.objetdirect.tatami.client.gfx.Rect;
import com.objetdirect.tatami.client.gfx.Text;
import com.objetdirect.tatami.client.gfx.TextPath;
import com.objetdirect.tatami.client.gfx.VirtualGroup;
import com.objetdirect.tatamix.client.hmvc.Event;
import com.objetdirect.tatamix.client.hmvc.ModelEvent;
import com.objetdirect.tatamix.client.hmvc.ModelImpl;
import com.objetdirect.tatamix.client.hmvc.Processor;

public class GFXModel extends ModelImpl implements TatamiDemoEvent {

	public GFXModel() {
		Processor drawPolyline = new Processor() {
			public void run(Event event) {
				createPolyLine();
			}
		};
		
		Processor drawLine = new Processor() {
			public void run(Event event) {
				final Point pointA = new Point(50, 50);
				final Point pointB = new Point(200, 360);
				Line line = new Line(pointA, pointB);
				line.setStrokeStyle(Line.LONGDASH);
				fire(new ModelEvent(DRAW_GRAPHIC_OBJECT,this,line));
			}
		};
		
		Processor drawEllipse = new Processor() {
			public void run(Event event) {
				fire(new ModelEvent(DRAW_GRAPHIC_OBJECT,this,new Ellipse(200,100)));
			}
		};
		
		Processor drawRect = new Processor() {
			public void run(Event event) {
			   fire(new ModelEvent(DRAW_GRAPHIC_OBJECT,this,new Rect(300,100)));	
			}
		};
		
		Processor drawCircle = new Processor() {
			public void run(Event event) {
			   fire(new ModelEvent(DRAW_GRAPHIC_OBJECT,this,new Circle(50)));	
			}
		};
		
		Processor drawImage = new Processor() {
			public void run(Event event) {
				
				ImageGfx image = new ImageGfx(TatamiDemo.getIconURL("od-logo.jpg"),105, 52);
				VirtualGroup virtual = new VirtualGroup();
				virtual.add(image);
				
				fire(new ModelEvent(DRAW_GRAPHIC_OBJECT,this,virtual));
			}
		};
		
		Processor drawVirtualGroup = new Processor() {
			public void run(Event event) {
				createVirtualGroup();
			}
		};
		
		Processor drawPath = new Processor() {
			public void run(Event event) {
				createPath();
			}
		};
		
		
		Processor drawText = new Processor() {
			public void run(Event event) {
				createText();
			}
		};
		
		Processor drawTextPath = new Processor() {
			public void run(Event event) {
				createTextPath();
			}
		};
		
		register(DRAW_POLYLINE,drawPolyline);
		register(DRAW_LINE,drawLine);
		register(DRAW_ELLIPSE,drawEllipse);
		register(DRAW_RECT,drawRect);
		register(DRAW_CIRCLE,drawCircle);
		register(DRAW_IMAGE,drawImage);
		register(DRAW_VIRTUAL_GROUP,drawVirtualGroup);
		register(DRAW_PATH,drawPath);
		register(DRAW_TEXT,drawText);
		register(DRAW_TEXT_PATH,drawTextPath);
		
	}
	
	
	public void createPolyLine() {
		Point[] arrow = new Point[8];
		arrow[0] = new Point(-2, 15);
		arrow[1] = new Point(2, 15);
		arrow[2] = new Point(2, -105);
		arrow[3] = new Point(6, -105);
		arrow[4] = new Point(0, -116);
		arrow[5] = new Point(-6, -105);
		arrow[6] = new Point(-2, -105);
		arrow[7] = new Point(-2, 15);

		Polyline poly = new Polyline(arrow);
		fire(new ModelEvent(DRAW_GRAPHIC_OBJECT,this,poly));
	}
	
	public void createPath() {
		// start point
		Point p1 = new Point(50, 50);
		Point p2 = new Point(80, 50);
		Point p3 = new Point(50, 100);
		Point p4 = new Point(80, 100);

		Path t = new Path();
	//	t.setFillColor(currentFillColor);
	//	t.setStroke(currentStrokeColor, currentStrokeSize);

		t.moveTo(p1);
		t.lineTo(p2);
		t.lineTo(p3);
		t.lineTo(p4);
		t.lineTo(p1);
		t.moveTo((p1.getX() + p4.getX()) / 2, (p1.getY() + p4.getY()) / 2);
		t.lineTo((p2.getX() + p3.getX()) / 2, (p2.getY() + p3.getY()) / 2);
		t.moveTo((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2);

		t.arcTo(20, 30, 35, true, true, p3);
		t.lineTo((p3.getX() + p4.getX()) / 2, (p3.getY() + p4.getY()) / 2);
		fire(new ModelEvent(DRAW_GRAPHIC_OBJECT,this,t));
	}
	
	public void createVirtualGroup() {
		VirtualGroup virtual = new VirtualGroup();
		Rect r = new Rect(100, 20);
		Circle c = new Circle(20);
		c.translate(0, 15);
		r.translate(0, 25);
		virtual.add(c);
		virtual.add(r);
		fire(new ModelEvent(DRAW_GRAPHIC_OBJECT,this,virtual));
	}
	
	
	public void createText() {
		Text text = new Text("Tatami GFX,\n default font");

		//it seems that to set a font bug in FF
//		Font font = new Font("Courier", 10, Font.NORMAL, Font.NORMAL,
//				Font.NORMAL);
//		Text text2 = new Text("Tatami GFX, courier bolder 10");
//		Font font2 = new Font("Courier", 10, Font.NORMAL, Font.NORMAL,
//				Font.BOLDER);
//		Text text3 = new Text("Tatami GFX, courier lighter 10");
//		Font font3 = new Font("Courier", 10, Font.NORMAL, Font.NORMAL,
//				Font.LIGHTER);
//		Text text4 = new Text("Tatami GFX Arial 24 Bold");
		Font font4 = new Font("Arial", 24, Font.ITALIC, Font.NORMAL, Font.LIGHTER);

		//text.setFont(font);
//		text3.setFont(font3);
		text.setFont(font4);
		fire(new ModelEvent(DRAW_GRAPHIC_OBJECT,this,text));

//		showGraphicObject(text2, 10, 40);
//		showGraphicObject(text3, 10, 60);
//		showGraphicObject(text4, 10, 100);
//		text4.setFont(font4);

	}
	
	public void createTextPath() {

		TextPath textPath = new TextPath(
		"Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Praesent erat.In malesuada ultricies velit. Vestibulum tempor odio vitae diam. Morbi arcu lectus, laoreet eget, nonummy at, elementum a, quam.");

		int CPD = 30;
		Font times = new Font("times", 12, Font.NORMAL, Font.NORMAL,
				Font.NORMAL);
		textPath.setFont(times);
		textPath.moveTo(0, 100);
		textPath.setAbsoluteMode(false);
		textPath.curveTo(CPD, 0, 100 - CPD, 300, 100, 300);
		textPath.curveTo(CPD, 0, 100 - CPD, -300, 100, -300);
		textPath.curveTo(CPD, 0, 100 - CPD, 300, 100, 300);
		textPath.curveTo(CPD, 0, 100 - CPD, -300, 100, -300);
		textPath.curveTo(CPD, 0, 100 - CPD, 300, 100, 300);
		fire(new ModelEvent(DRAW_GRAPHIC_OBJECT,this,textPath));
	}
	
}//end of class
