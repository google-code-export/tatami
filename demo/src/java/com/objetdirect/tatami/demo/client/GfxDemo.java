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
package com.objetdirect.tatami.demo.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.ColorChooser;
import com.objetdirect.tatami.client.Slider;
import com.objetdirect.tatami.client.gfx.Color;
import com.objetdirect.tatami.client.gfx.GraphicCanvas;
import com.objetdirect.tatami.client.gfx.GraphicObject;
import com.objetdirect.tatami.client.gfx.GraphicObjectListener;
import com.objetdirect.tatami.client.gfx.Pattern;
import com.objetdirect.tatamix.client.hmvc.DefaultView;
import com.objetdirect.tatamix.client.hmvc.Processor;
import com.objetdirect.tatamix.client.hmvc.ViewEvent;
import com.objetdirect.tatamix.client.widget.ImageLink;


/**
 * Demo for the GFX package
 * @author Vianney
 *
 */
public class GfxDemo extends DefaultView implements GraphicObjectListener,	ClickListener, ChangeListener, TatamiDemoEvent {

	private enum ACTION { SCALE, ROTATE,APPLY_COLOR, APPLY_PATTERN,GO_BACK,GO_FRONT,
		                  DRAW_LINE,DRAW_RECT,DRAW_TEXT, DRAW_POLYLINE, DRAW_PATH, DRAW_ELLIPSE,
		                  DRAW_CIRCLE,DRAW_VIRTUAL_GROUP,DRAW_TEXT_PATH,DRAW_IMAGE,
		                  SHOW_PROPERTIES,DELETE,STROKE_1, STROKE_2,STROKE_3,STROKE_4
		              
		                  };
	private Map<SourcesClickEvents,ACTION> actionMap;
	
	/** factor for scaling */
	float scaleFactor = 1;

	int rotateDegree = 0;


	/** the last position of the last widget which had been moved */
	private int[] lastPosition = { 0, 0 };

	private FlowPanel gridShape;

	private FlowPanel gridTransform;

	/** the canvas */
	private GraphicCanvas canvas;

	/** The buttons panel */
	private FlowPanel palette;

	/**
	 * flag to indicate if a graphical object is movable
	 */
	private boolean movable = false;
		

	private PopupPanel popup = new PopupPanel(true);

	private Slider opacity;

	private HTML html = new HTML("X,Y");

	private GraphicObject current = null;

	private Color currentFillColor = Color.WHITE;

	private Color currentStrokeColor = Color.BLACK;

	private Color lastStrokeColor = currentStrokeColor;

	private HTML fill;

	private HTML[] strokeSize;

	private int currentStrokeSize = 1;

	private int lastStrokeSize = currentStrokeSize;

	/**
	 *
	 * Creates the GFXDemo
	 */
	public GfxDemo() {
		this.actionMap = new HashMap<SourcesClickEvents,ACTION>();
		initComponents();
		
		Processor drawGraphicalObject = new Processor() {
			public void run(com.objetdirect.tatamix.client.hmvc.Event event) {
				if ( event.getData() instanceof GraphicObject) {
					showGraphicObject((GraphicObject)event.getData(),100,100);
				}
			}
		};
		register(DRAW_GRAPHIC_OBJECT,drawGraphicalObject);
	}

	/**
	 * Create an canvas (at the right) and a toolbar containg some actions.
	 * The actions are : creates a new gfx component, like <code>Rect</code>...,
	 * changes the color, the width of the stroke, does some rotation, scaling.
	 *
	 */
	private void initComponents() {
		
		palette = new FlowPanel();
		palette.setStylePrimaryName("GfxDemo-palette");
		canvas = new GraphicCanvas();
		canvas.setStylePrimaryName("GfxDemo-canvas");
		canvas.setDimensions(600, 600);
		popup.add(html);

		gridShape = new FlowPanel();
		gridShape.setStylePrimaryName("shapeButtons");
		gridTransform = new FlowPanel();
		gridTransform.setStylePrimaryName("transformButtons");
		
		

		fill = new HTML("&nbsp;&nbsp;&nbsp;");
		fill.setStylePrimaryName("fillProperty");
		Style fillStyle = fill.getElement().getStyle();
		fillStyle.setProperty("backgroundColor", currentFillColor.toHex());
		fillStyle.setProperty("border", "solid");
		fillStyle.setProperty("borderWidth", "thin");
		fillStyle.setProperty("borderColor",currentStrokeColor.toHex());
		
		addAction(fill,ACTION.APPLY_COLOR);
		
		
		palette.add(gridShape);
		palette.add(fill);
		
		strokeSize = new HTML[4];
		strokeSize[0] = this.createSrokeSize(1,ACTION.STROKE_1);
		strokeSize[1] = this.createSrokeSize(2,ACTION.STROKE_2);
		strokeSize[2] = this.createSrokeSize(3,ACTION.STROKE_3);
		strokeSize[3] = this.createSrokeSize(5,ACTION.STROKE_4);

		palette.add(gridTransform);

		opacity = new Slider(Slider.HORIZONTAL, 0, 100, 100, true);
		opacity.addChangeListener(this);

		addAction(gridShape,"Circle", "gfx/circle.gif",ACTION.DRAW_CIRCLE);
		addAction(gridShape, "Ellipse", "gfx/ellipse.gif",ACTION.DRAW_ELLIPSE);
		addAction(gridShape, "Rect", "gfx/rect.gif",ACTION.DRAW_RECT);
		addAction(gridShape, "Line", "gfx/line.gif",ACTION.DRAW_LINE);
		addAction(gridShape,"Polyline","gfx/polyline.gif",ACTION.DRAW_POLYLINE);
		addAction(gridShape, "Text", "gfx/text.gif",ACTION.DRAW_TEXT);
		addAction(gridShape, "Image", "gfx/image.gif",ACTION.DRAW_IMAGE);
		addAction(gridShape,"Path", "gfx/path.GIF",ACTION.DRAW_PATH);
		addAction(gridShape, "Text Path","gfx/textpath.gif",ACTION.DRAW_TEXT_PATH);
		addAction(gridShape,  "Virtual", "gfx/group.gif",ACTION.DRAW_VIRTUAL_GROUP);
		addAction(gridShape, "Delete", "gfx/delete.gif",ACTION.DELETE);
		addAction(gridTransform, "set color","gfx/color.gif",ACTION.APPLY_COLOR);
		addAction(gridTransform, "Scale", "gfx/scale.gif",ACTION.SCALE);
		addAction(gridTransform,"Rotate","gfx/rotate.gif",ACTION.ROTATE);
		addAction(gridTransform, "Move to back","gfx/back.gif",ACTION.GO_BACK);
		addAction(gridTransform, "Move to front","gfx/front.gif",ACTION.GO_FRONT);
		addAction(gridTransform,"Properties","gfx/properties.gif",ACTION.SHOW_PROPERTIES);

	
		add(new HTML(TatamiDemo.getMessages().paragraph_gfx()));
		
		add(palette);
		add(canvas);
		canvas.addGraphicObjectListener(this);

	}
	
	
	
	
	private void addAction(SourcesClickEvents widget,ACTION action) {
		actionMap.put(widget,action);
		widget.addClickListener(this);
	}
	
	
	

	/**
	 *
	 * @param size
	 * @return
	 */
	private HTML createSrokeSize(int size,ACTION action) {
		HTML strokeSize = new HTML("&nbsp;&nbsp;&nbsp;");
		strokeSize.setStylePrimaryName("strokeProperty");
		strokeSize.setTitle("Size of stroke " + size);
		Style style = strokeSize.getElement().getStyle();
		style.setProperty("borderTop", "solid");
		style.setPropertyPx("borderWidth", size);
		palette.add(strokeSize);
		this.addAction(strokeSize, action);
		
		return strokeSize;
	}

	/**
	 * Chooses a width for the stroke of the <code>GraphicObject</code>
	 * @param index an index of the array <code>strokeSize</code>
	 * @param size  the size to apply
	 */
	private void chooseStrokeSize(int index, int size) {
		for (int i = 0; i < strokeSize.length; i++) {
			Style style = strokeSize[i].getElement().getStyle();
			if (i == index) {
				
				style.setProperty("borderColor", "red");
				currentStrokeSize = size;
			} else {
				style.setProperty("borderColor", "black");
				
			}
		}
	}

	/**
	 * Changes the opacity of a <code>GraphicObject</code>
	 * @param sender the <code>Slider</code> to change the alpha property of <code>Color</code>
	 */
	public void onChange(Widget sender) {
		if (sender.equals(opacity)) {
			if (current != null) {
				int value = opacity.getValue();
				
				current.setOpacity(value);
			}

		}
	}

	/**
	 * Adds a "button" it means a clickable image to the toolbar (the <code>Grid</code>).
	 * @param title the title for the image
	 * @param icon the image (url) to load.
	 * @return a clickable <code>Image</code>.
	 */
	private ImageLink addAction(FlowPanel panel,String title,String icon,ACTION action) {
		ImageLink button = new ImageLink();
		
		button.setImageSrc(TatamiDemo.getIconURL(icon));
		button.setAlt(title);
		button.setTitle(title);
		
		panel.add(button);
		addAction(button,action);
		return button;
	}

	/**
	 * When a user clicks on the canvas and, no <code>GraphicObject</code> intersect with
	 * the coordinate of the mouse, the current <code>GraphicObject</code> will be unselected.
	 */
	public void mouseClicked(GraphicObject graphicObject, Event evt) {
		if (graphicObject == null) {
			unSelectAll();
		}
	}

	/**
	 * Unselects the current <code>GraphicObject</code>
	 *
	 */
	private void unSelectAll() {
		if (current != null) {
			this.current.setStroke(lastStrokeColor, lastStrokeSize);
		}
		current = null;
	}

	/**
	 * translates a <code>GraphicObject</code> if the current <code>GraphicObject</code>
	 * is not null. Shows also the coordinate of the mouse
	 */
	public void mouseMoved(GraphicObject graphicObject,Event evt) {
		if (current != null) {
            int x = DOM.eventGetClientX(evt);
            int y = DOM.eventGetClientY(evt);
			int newX = x - canvas.getAbsoluteLeft() + Window.getScrollLeft();
			int newY = y - canvas.getAbsoluteTop() + Window.getScrollTop();
			html.setHTML(newX + "," + newY + " [" + x + "," + y + "]");
			popup.setPopupPosition(10 + newX + canvas.getAbsoluteLeft(), newY
					+ canvas.getAbsoluteTop());
			Color color = current.getFillColor();
			Color reverseColor = color.reverse();
			reverseColor.setAlpha(255);
			DOM.setStyleAttribute(html.getElement(), "color", reverseColor
					.toHex());
			popup.show();
			if ( movable) {

				if ( current.getGroup() != null) {
					  GraphicObject object = this.current.getGroup();
					  object.translate(newX - lastPosition[0], newY- lastPosition[1]);

				} else {
					current.translate(newX - lastPosition[0], newY- lastPosition[1]);
					}


				lastPosition[0] = newX;
				lastPosition[1] = newY;
			}
		} else {
			popup.hide();
		}

	}
	
	
	private void manageAction(ACTION action,Widget sender) {
		switch (action ) {
		 default: {
			 Window.alert("Not implemented");
			 break;
		 }
		 case ROTATE : {
			 showPopupRotate(sender);
			 break;
		 }
		 case SCALE : {
			 showPopupScaler(sender);
			 break;
		 }
		 case DRAW_RECT : {
			 fire(new ViewEvent(DRAW_RECT,this));
			 break;
		 }
		 
		 case DRAW_POLYLINE : {
			 fire(new ViewEvent(DRAW_POLYLINE,this));
			 break;
		 }
		 case DRAW_CIRCLE : {
			 fire(new ViewEvent(DRAW_CIRCLE,this));
			 break;
		 }
		 case DRAW_LINE : {
			 fire(new ViewEvent(DRAW_LINE,this));
			 break;
		 }
		 case DRAW_TEXT: {
			 fire(new ViewEvent(DRAW_TEXT,this));
			 break;
		 }
		 case DRAW_TEXT_PATH: {
			 fire(new ViewEvent(DRAW_TEXT_PATH,this));
			 break;
		 }
		 case DRAW_VIRTUAL_GROUP: {
			 fire(new ViewEvent(DRAW_VIRTUAL_GROUP,this));
			 break;
		 }
		 case DRAW_IMAGE : {
			 fire(new ViewEvent(DRAW_IMAGE,this));
			 break;
		 }
		 case DRAW_PATH : {
			 fire(new ViewEvent(DRAW_PATH,this));
			 break;
		 }
		 case DRAW_ELLIPSE : {
			 fire(new ViewEvent(DRAW_ELLIPSE,this));
			 break;
		 }
		 case STROKE_1 : {
			 chooseStrokeSize(0, 1);
			 break; 
		 }
		 case STROKE_2 : {
			 chooseStrokeSize(1, 2);	
			 break; 
			 }
		 case STROKE_3 : {
			 chooseStrokeSize(2, 3);	
			 break; 
			 }
		 case STROKE_4 : {
			 chooseStrokeSize(3, 5);	
			 break; 
			 }
		 case SHOW_PROPERTIES : {
			 if ( current != null) {
			 showProperties(current);
			 }
			 break;
		 }
		 case GO_FRONT : {
			 if ( current != null) {
				 current.moveToFront();
			 }
			 break;
		 }
		 case GO_BACK : {
             if ( current != null) {
            	 current.moveToBack();
			 }
			 break;
		 }
		 case DELETE : {
			 if ( current != null) {
				 if ( current.getGroup() != null) {
						canvas.remove(current.getGroup());
					} else {
					   canvas.remove(current);
					} 
			 }
			 break;
		 }
		 case APPLY_COLOR: {
			 showPopupColor(sender);
			 break;
			 
		 }
		}
	}
	
	

	/**
	 * Performs some action when a click event is fired.
	 * @param sender the widget which fired a click event
	 */
	public void onClick(Widget sender) {
		ACTION action = actionMap.get((SourcesClickEvents)sender);
		if ( action != null) {
			manageAction(action,sender);
			
		}
	}

	


	
	/**
	 * Show some properties about a <code>GraphicObjectw</code>
	 * @param object the <code>GraphicObject</code> to show the properties
	 * TODO change the layout of the dialog
	 */
	private void showProperties(GraphicObject object) {
		final DialogBox dialog = new DialogBox(false);
		dialog.setText("Properties");
		Grid panel = new Grid(5, 4);
		panel.setCellPadding(5);
		panel.setCellSpacing(10);
		panel.setWidget(0, 0, new HTML("<b>Position</b>"));
		panel.setWidget(0, 1, new Label(object.getX() + "," + object.getY()));
		panel.setWidget(0, 2, new HTML("<b>Center</b>"));
		panel.setWidget(0, 3, new Label(object.getCenterX() + ","
				+ object.getCenterY()));
		panel.setWidget(1, 0, new HTML("<b>Size</b>"));
		panel.setWidget(1, 1, new Label("? x ? px"));
		panel.setWidget(2, 0, new HTML("<b>Color of the stroke</b>"));
		final String color = lastStrokeColor.toHex();
		final Label label = new Label(color);
		label.setTitle(color);
		DOM.setStyleAttribute(label.getElement(), "color", color);
		panel.setWidget(2, 1, label);
		panel.setWidget(2, 2, new HTML("<b>Size of the stroke</b>"));
		panel.setWidget(2, 3, new Label(lastStrokeSize + "px"));
		panel.setWidget(3, 0, new HTML("<b>Fill color</b>"));
		final String fillColor = object.getFillColor().toHex();
		final Label labelFill = new Label(fillColor);
		labelFill.setTitle(fillColor);
		panel.setWidget(3, 1, labelFill);
		DOM.setStyleAttribute(labelFill.getElement(), "color", fillColor);

		Button close = new Button("Close");
		close.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				dialog.hide();
			}
		});
		panel.setWidget(4, 0, close);

		dialog.setPopupPosition(Window.getClientWidth() / 2, Window
				.getClientHeight() / 2);
		dialog.addStyleName("GfxDemo-properties");
		dialog.setWidget(panel);
		dialog.show();
		System.out.println("bounds " + object.getBounds());
	}

	/**
	 * shows a popup to scale a graphical Object
	 **/
	private void showPopupScaler(Widget sender) {
		if (current != null) {
			scaleFactor = 1;
			final PopupPanel popupScaler = new PopupPanel(true);
			popupScaler.addStyleName("GfxDemo-popup");
			final Slider scaler = new Slider(Slider.HORIZONTAL, -10, 10, 1,
					true);
			scaler.setRuleMarkBottom(6, "3px");
			HorizontalPanel scalePanel = new HorizontalPanel();
			scalePanel.setSpacing(5);

			final Label labelScaler = new Label();
			scalePanel.add(scaler);
			scalePanel.add(labelScaler);

			labelScaler.setText("x" + scaleFactor);

			ChangeListener scaleChange = new ChangeListener() {
				public void onChange(Widget sender) {
					int value = scaler.getValue();
					if (current != null && value != 0) {
						if (value < 0) {
							final float minus = (scaler.getMinimum() - value)
									* (-1);
							float factor = (minus / (float) scaler.getMaximum())
									/ scaleFactor;
							if ( current.getGroup() != null) {
								current.getGroup().scale(factor);
							} else {
							current.scale(factor);
							}
							scaleFactor = (minus / (float) scaler.getMaximum());

						} else {
							float factor = (float) value / scaleFactor;
							if ( current.getGroup() != null) {
								current.getGroup().scale(factor);
							} else {
							current.scale(factor);
							}
							scaleFactor = (float) value;
						}

					}
					labelScaler.setText("x" + scaleFactor);
				}
			};
			scaler.addChangeListener(scaleChange);
			popupScaler.add(scalePanel);
			popupScaler.setPopupPosition(sender.getAbsoluteLeft(),	sender.getAbsoluteTop());
			popupScaler.show();
		}
	}

	/**
	 * Show a popup with a <code>Slider</code> to
	 * perform some rotation of the current <code>GraphicObject</code>
	 *
	 */
	private void showPopupRotate(Widget sender) {
		if (current != null) {
			rotateDegree = 0;
			final PopupPanel popupRotate = new PopupPanel(true);
			popupRotate.addStyleName("GfxDemo-popup");
			final Slider rotate = new Slider(Slider.HORIZONTAL, 0, 360, 0, true);
			HorizontalPanel rotatePanel = new HorizontalPanel();
			rotatePanel.setSpacing(5);

			final Label label = new Label();
			rotatePanel.add(rotate);
			rotatePanel.add(label);
			label.setText("" + rotateDegree);
			ChangeListener rotateChange = new ChangeListener() {
				public void onChange(Widget sender) {
					int value = rotate.getValue();
					if (current != null && value != 0) {
						int degree = value - rotateDegree;

						if ( current.getGroup() != null) {
						   current.getGroup().rotate(degree);
						} else {
							current.rotate(degree);
						}

						rotateDegree = value;
					}
					label.setText(rotateDegree + "");

				}
			};
			rotate.addChangeListener(rotateChange);
			popupRotate.add(rotatePanel);
			popupRotate.setPopupPosition(sender.getAbsoluteLeft(),sender.getAbsoluteTop());
			popupRotate.show();
		}
	}

	/**
	 * Shows a popup to select a color for the background or the stroke.
	 * The popup contains a TabPanel with 3 tab, a <code>ColorChooser</code>,
	 *  a <code>Slider</code> to change the opacity of the <code>Color</code>,
	 *  some <code>Pattern</code> to apply
	 */
	private void showPopupColor(Widget sender) {

		final PopupPanel popupColor = new PopupPanel(true);
		popupColor.addStyleName("GfxDemo-popupColor");
		TabPanel tabPanel = new TabPanel();
		FlowPanel colPanel = new FlowPanel();
		
		final CheckBox checkFill = new CheckBox("Background");
		checkFill.setChecked(true);
		colPanel.add(checkFill);

		final ColorChooser colorChooser = new ColorChooser();

		colPanel.add(colorChooser);

		tabPanel.add(colPanel, new Label("Color"));

		ChangeListener colorChange = new ChangeListener() {
			public void onChange(Widget sender) {
				String color = colorChooser.getColor();
				Color colorSelected = Color.getColor(color);
				if (checkFill.isChecked()) {
					currentFillColor = colorSelected;
					DOM.setStyleAttribute(fill.getElement(), "backgroundColor",color);
					//currentFillColor.setAlpha(opacity.getValue());
					if (current != null) {
						current.setFillColor(currentFillColor);
					}
				} else {
					currentStrokeColor = colorSelected;
					lastStrokeColor = currentStrokeColor;
					DOM.setStyleAttribute(fill.getElement(), "borderColor",
							color);
					if (current != null) {
					
						current.setStroke(currentStrokeColor, 1);
					}
				}
			}
		};
		colorChooser.addChangeListener(colorChange);

		FlowPanel patternContainer = new FlowPanel();
		
		
		
		patternContainer.add(createImagePattern("gfx/none.gif"));
		patternContainer.add(createImagePattern("littleNero.png"));
		patternContainer.add(createImagePattern("littleTrajan.png"));
		patternContainer.add(createImagePattern("cubic.jpg"));
		patternContainer.add(createImagePattern("logo_ft.gif"));
		patternContainer.add(createImagePattern("od-logo.jpg"));
		tabPanel.add(patternContainer, new Label("Pattern"));
		tabPanel.add(this.opacity, new Label("Opacity"));

		tabPanel.selectTab(0);
		popupColor.add(tabPanel);
		popupColor.setPopupPosition(sender.getAbsoluteLeft(), sender.getAbsoluteTop());
		popupColor.show();
	}

	/**
	 * Creates a clickable <code>Image</code> when a click event is fired, a pattern correpsonding to
	 * the image is applied.
	 * @param url the url of the image
	 * @return the <code>Image</code> created
	 */
	private Image createImagePattern(final String url) {
		final Image image = new Image(TatamiDemo.getIconURL(url));
		image.setStylePrimaryName("pattern");
		image.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				if (current != null) {
					Pattern pattern = null;
					if (url == "gfx/none.gif") {
						pattern = Pattern.DEFAULT_PATTERN;
					} else {
						pattern = new Pattern(new Image(TatamiDemo.getIconURL(url)), 0, 0);
					}
					current.applyPattern(pattern);
				}
			}
		});
		return image;
	}

	

	/**
	 * Adds a <code>GraphicObject</code> to the <code>GraphicCanvas</code>
	 * @param object the <code>GraphicObject</code> to add
	 * @param int x the X coordinate
	 * @param int y the Y coordinate
	 *
	 */
	private void showGraphicObject(GraphicObject object, int x, int y) {
		object.setFillColor(currentFillColor);
		object.setStroke(currentStrokeColor, currentStrokeSize);
		this.canvas.add(object, x, y);

	}

	/**
	 * Selects a <code>GraphicObject</code> when a mouse is pressed on a <code>GraphicObject</code>
	 * in the canvas. Keeps also the postion of the mouse. The <code>GraphicObject</code>
	 *  is also movable.
	 */
	public void mousePressed(GraphicObject graphicObject,Event evt) {
		if (graphicObject != null) {
			int x = DOM.eventGetClientX(evt);
			int y = DOM.eventGetClientY(evt);
			selectObject(graphicObject);
			movable = true;
			this.lastPosition[0] = x - canvas.getAbsoluteLeft()
					+ Window.getScrollLeft();
			this.lastPosition[1] = y - canvas.getAbsoluteTop()
					+ Window.getScrollTop();

		}

	}

	/**
	 * Selects the given <code>GraphicObject</code>. The current <code>GraphicObject</code>
	 * will become the given <code>GraphicObject</code>
	 * @param object <code>GraphicObject</code>
	 */
	private void selectObject(GraphicObject object) {
		if (current != null) {
			current.setStroke(lastStrokeColor, lastStrokeSize);
		}
		current = object;

		lastStrokeColor = current.getStrokeColor();
		lastStrokeSize  = current.getStrokeWidth();
		current.setStroke(Color.RED, 2);

		opacity.removeChangeListener(this);
        opacity.setValue(current.getOpacity());
        opacity.addChangeListener(this);


	}


	/**
	 * When the mouse is released no translation is possible
	 */
	public void mouseReleased(GraphicObject graphicObject,Event evt) {
		movable = false;

	}

	/**
	 * When a double clik is fired on a <code>GraphicObject</code> in the canvas,
	 * we show the properties of it.
	 */
	public void mouseDblClicked(GraphicObject graphicObject,Event evt) {
		if ( graphicObject != null) {
			showProperties(graphicObject);

		}
	}
}// end of class
