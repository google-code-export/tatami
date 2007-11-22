package com.objetdirect.tatami.client;

import java.util.Date;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.SimplePanel;
import com.objetdirect.tatami.client.gfx.Circle;
import com.objetdirect.tatami.client.gfx.Color;
import com.objetdirect.tatami.client.gfx.GraphicCanvas;
import com.objetdirect.tatami.client.gfx.GraphicObject;
import com.objetdirect.tatami.client.gfx.ImageGfx;
import com.objetdirect.tatami.client.gfx.Point;
import com.objetdirect.tatami.client.gfx.Polyline;

public class Clock extends SimplePanel {

	private GraphicCanvas canvas;
	private Polyline hour_shadow;
	private Polyline hour_hand;
	private Polyline minute_shadow;
	private Polyline minute_hand;
	private Polyline second_hand;
	private Polyline second_shadow;
	
	private final Color shadowColor = new Color(0,0,0,15);
	private Color minuteColor = new Color(127,127,127,255);
	private Color hourColor =   Color.SILVER;
	
	private Date current_time;
	
	private double angleMinute = 0.0;
	private double angleHour = 0.0;
	private double angleSecond = 0.0;
	
	
	final Point center = new Point(385/2,385/2); 
	
	public Clock() {
	   setElement(DOM.createDiv());
	   current_time = new Date();
	}
	
	
	public void onAttach() {
		super.onAttach();
		makeClock();
		Timer timer = new Timer() {
			public void run() {
				reflectTime();
				current_time.setSeconds(current_time.getSeconds()+1);
				
			}
		};
		timer.scheduleRepeating(1000);
	}
	

	private void makeClock() {
		canvas = new GraphicCanvas();
		canvas.setPixelSize(385, 385);
		ImageGfx clock = new ImageGfx("images/clock_face.jpg",385,385);
		canvas.add(clock, 0, 0);
		Point[] hour_hand_points = new Point[4];
		hour_hand_points[0] =new Point(-7,15);
		hour_hand_points[1] = new Point(7,15);
		hour_hand_points[2] = new Point(0,-60); 
		hour_hand_points[3] = new Point(-7,15);

        hour_hand = new Polyline(hour_hand_points);
        hour_hand.setStrokeWidth(2);
        hour_hand.setFillColor(hourColor);
		canvas.add(hour_hand,(int)center.getX(),(int)center.getY());
        
		hour_shadow = new Polyline(hour_hand_points);
        setShadow(hour_shadow,3,3);
        
        Point[]  minute_hand_points =  new Point[4];
		minute_hand_points[0] = new Point(-5,15);
		minute_hand_points[1] = new Point(5,15);
		minute_hand_points[2] = new Point(0,-100);
		minute_hand_points[3] = new Point(-5,15);
        minute_hand = new Polyline(minute_hand_points);
        minute_hand.setFillColor(minuteColor);
        
        canvas.add(minute_hand,(int)center.getX(),(int)center.getY());
        
		minute_shadow = new Polyline(minute_hand_points);
        setShadow(minute_shadow,3,3);
        
        
        Point[] second_hand_points = new Point[8];
		second_hand_points[0] = new Point(-2,15);
		second_hand_points[1] = new Point(2,15);
		second_hand_points[2] = new Point(2,-105);
		second_hand_points[3] = new Point(6,-105);
		second_hand_points[4] = new Point(0,-116);
		second_hand_points[5] = new Point(-6,-105);
		second_hand_points[6] = new Point(-2,-105);
		second_hand_points[7] = new Point(-2,15);
		
		second_hand = new Polyline(second_hand_points);
		second_hand.setFillColor(Color.RED);
		second_hand.setStrokeColor(Color.PURPLE);
		canvas.add(second_hand,(int)center.getX(),(int)center.getY());
		
		
		second_shadow = new Polyline(second_hand_points);
		setShadow(second_shadow,4,4);
		
		Circle circle = new Circle(1);
		canvas.add(circle,(int)center.getX(),(int)center.getY());
		
		
		
		add(canvas);
		
	}
	
    private void setShadow(GraphicObject shadow,int dx,int dy) {
    	shadow.setFillColor(shadowColor);
    	shadow.setStrokeStyle(GraphicObject.NONE);
    	shadow.setStrokeColor(shadowColor);
    	canvas.add(shadow,(int)center.getX(),(int)center.getY());
    	shadow.translate(dx,dy);
    }
	
	private void reflectTime(){
		
		int h = current_time.getHours();
		int m = current_time.getMinutes();
		int s = current_time.getSeconds();
		placeHourHand(h, m, s);
		placeMinuteHand(m, s);
		placeSecondHand(s);
		
	};
	
	
	
	


	private void placeHourHand(int h, int m, int s){
		double angle = 30 * (h % 12 + m / 60 + s / 3600);
		placeHand(hour_hand, angle - angleHour);
		placeHand(hour_shadow, angle - angleHour);
		angleHour = angle;

	};

	private void placeMinuteHand(int m,int s){
		double  angle = 6 * (m + s / 60);
		placeHand(minute_hand, angle - angleMinute);
		placeHand(minute_shadow, angle - angleMinute);
		angleMinute = angle;
		
	};

	private void placeSecondHand(int s){
		double  angle = 6 * s;
		placeHand(second_hand,angle - angleSecond);
		placeHand(second_shadow, angle- angleSecond);
		angleSecond = angle;
	};
	
		
	private GraphicObject placeHand(GraphicObject shape,double  angle){
		return shape.rotate((float)angle,center);
	};
}
