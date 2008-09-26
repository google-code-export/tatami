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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.ColorChooser;
import com.objetdirect.tatami.client.Slider;
import com.objetdirect.tatami.client.gfx.Circle;
import com.objetdirect.tatami.client.gfx.Color;
import com.objetdirect.tatami.client.gfx.Ellipse;
import com.objetdirect.tatami.client.gfx.Font;
import com.objetdirect.tatami.client.gfx.GraphicCanvas;
import com.objetdirect.tatami.client.gfx.GraphicObject;
import com.objetdirect.tatami.client.gfx.GraphicObjectListener;
import com.objetdirect.tatami.client.gfx.ImageGfx;
import com.objetdirect.tatami.client.gfx.Line;
import com.objetdirect.tatami.client.gfx.Path;
import com.objetdirect.tatami.client.gfx.Pattern;
import com.objetdirect.tatami.client.gfx.Point;
import com.objetdirect.tatami.client.gfx.Polyline;
import com.objetdirect.tatami.client.gfx.Rect;
import com.objetdirect.tatami.client.gfx.Text;
import com.objetdirect.tatami.client.gfx.TextPath;
import com.objetdirect.tatami.client.gfx.VirtualGroup;
import com.objetdirect.tatamix.client.hmvc.CompositeView;


/**
 * Demo for the GFX package
 * @author Vianney
 *
 */
public class GfxDemo extends CompositeView implements GraphicObjectListener,
		ClickListener, ChangeListener {

	/** factor for scaling */
	float scaleFactor = 1;

	int rotateDegree = 0;

	/** The main panel */
	private DockPanel panel;

	/** the last position of the last widget which had been moved */
	private int[] lastPosition = { 0, 0 };

	private Grid gridShape;

	private Grid gridTransform;

	/** the canvas */
	private GraphicCanvas canvas;

	/** The buttons panel */
	private VerticalPanel buttonPanel;

	/**
	 * flag to indicate if a graphical object is movable
	 */
	private boolean movable = false;

	/**
	 * for creating a circle
	 */
	private Image circleButton;

	/**
	 * for creating a rectangle
	 */
	private Image rectButton;

	/**
	 * for creating some text
	 */
	private Image textButton;

	/**
	 * for creating a GFX image
	 */
	private Image imageButton;

	/**
	 * for creating lineButton
	 */
	private Image lineButton;

	private Image polylineButton;

	/**
	 * for creating an Ellipse
	 */
	private Image ellipseButton;

	/**
	 * for creating a graphical object
	 */
	private Image scaleButton;

	/** for moving an object to the back */
	private Image backButton;

	/**
	 * for moving an object to the front
	 */
	private Image frontButton;

	/** to create a path */
	private Image pathButton;

	/** to create a path */
	private Image textPathButton;

	/**
	 * for creating a rotation of an object
	 */
	private Image rotateButton;

	private Image virtualButton;

	/**
	 * To delete an object
	 */
	private Image deleteButton;

	/**
	 * To change color
	 */
	private Image colorButton;

	/**
	 * The popup panels
	 */

	private Image propertiesButton;

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
		initComponents();
		this.initWidget(panel);
	}

	/**
	 * Create an canvas (at the right) and a toolbar containg some actions.
	 * The actions are : creates a new gfx component, like <code>Rect</code>...,
	 * changes the color, the width of the stroke, does some rotation, scaling.
	 *
	 */
	private void initComponents() {
		panel = new DockPanel();
		canvas = new GraphicCanvas();
		canvas.setStyleName("GfxDemo-canvas");
		buttonPanel = new VerticalPanel();
		buttonPanel.setSpacing(10);
		popup.add(html);

		gridShape = new Grid(6, 2);
		gridShape.setCellSpacing(5);
		gridShape.setCellPadding(5);
		gridTransform = new Grid(3, 2);
		gridTransform.setCellSpacing(5);
		gridTransform.setCellPadding(5);

		canvas.setPixelSize(600, 600);

		fill = new HTML("&nbsp;&nbsp;&nbsp;");
		DOM.setStyleAttribute(fill.getElement(), "backgroundColor",
				this.currentFillColor.toHex());
		DOM.setStyleAttribute(fill.getElement(), "border", "solid");
		DOM.setStyleAttribute(fill.getElement(), "borderWidth", "thin");
		DOM.setStyleAttribute(fill.getElement(), "borderColor",
				this.currentStrokeColor.toHex());
		fill.setSize("25px", "25px");
		fill.addClickListener(this);
		buttonPanel.add(gridShape);
		buttonPanel.add(fill);
		buttonPanel
				.setCellHorizontalAlignment(fill, VerticalPanel.ALIGN_CENTER);

		strokeSize = new HTML[4];
		strokeSize[0] = this.createSrokeSize(1);
		strokeSize[1] = this.createSrokeSize(2);
		strokeSize[2] = this.createSrokeSize(3);
		strokeSize[3] = this.createSrokeSize(5);

		buttonPanel.add(gridTransform);

		opacity = new Slider(Slider.HORIZONTAL, 0, 255, 255, true);
		opacity.addChangeListener(this);

		circleButton = addToGrid(gridShape, 0, 0, "Circle", "gfx/circle.gif");
		ellipseButton = addToGrid(gridShape, 0, 1, "Ellipse", "gfx/ellipse.gif");
		rectButton = addToGrid(gridShape, 1, 0, "Rect", "gfx/rect.gif");
		lineButton = addToGrid(gridShape, 1, 1, "Line", "gfx/line.gif");
		polylineButton = addToGrid(gridShape, 2, 0, "Polyline",
				"gfx/polyline.gif");
		textButton = addToGrid(gridShape, 2, 1, "Text", "gfx/text.gif");

		imageButton = addToGrid(gridShape, 3, 0, "Image", "gfx/image.gif");
		pathButton = addToGrid(gridShape, 3, 1, "Path", "gfx/path.GIF");
		textPathButton = addToGrid(gridShape, 4, 0, "Text Path",
				"gfx/textpath.gif");
		virtualButton = addToGrid(gridShape, 4, 1, "Virtual", "gfx/group.gif");
		deleteButton = addToGrid(gridShape, 5, 0, "Delete", "gfx/delete.gif");

		colorButton = addToGrid(gridTransform, 0, 0, "set color",
				"gfx/color.gif");
		scaleButton = addToGrid(gridTransform, 0, 1, "Scale", "gfx/scale.gif");
		rotateButton = addToGrid(gridTransform, 1, 0, "Rotate",
				"gfx/rotate.gif");
		backButton = addToGrid(gridTransform, 1, 1, "Move to back",
				"gfx/back.gif");
		frontButton = addToGrid(gridTransform, 2, 0, "Move to front",
				"gfx/front.gif");
		propertiesButton = addToGrid(gridTransform, 2, 1, "Properties",
				"gfx/properties.gif");

		HTML html = new HTML("<p>The <b>GFX</b> package permits to draw some graphic components. You can draw circles, rectangles, ellipses... You can also apply some affine transformation like translation, rotation... on these graphic components.<b>Click on an icon to create a graphic component in the canvas.</p>");
		panel.add(html, DockPanel.NORTH);
		panel.add(canvas, DockPanel.CENTER);
		panel.add(buttonPanel, DockPanel.WEST);
		canvas.addGraphicObjectListener(this);

	}

	/**
	 *
	 * @param size
	 * @return
	 */
	private HTML createSrokeSize(int size) {
		HTML strokeSize = new HTML("&nbsp;&nbsp;&nbsp;");
		strokeSize.setSize("32px", "8px");
		strokeSize.setTitle("Size of stroke " + size);
		DOM.setStyleAttribute(strokeSize.getElement(), "borderTop", "solid");
		DOM
				.setStyleAttribute(strokeSize.getElement(), "borderWidth", ""
						+ size);
		buttonPanel.add(strokeSize);
		buttonPanel.setCellHorizontalAlignment(strokeSize,
				VerticalPanel.ALIGN_CENTER);
		strokeSize.addClickListener(this);
		return strokeSize;
	}

	/**
	 * Chooses a width for the stroke of the <code>GraphicObject</code>
	 * @param index an index of the array <code>strokeSize</code>
	 * @param size  the size to apply
	 */
	private void chooseStrokeSize(int index, int size) {
		for (int i = 0; i < strokeSize.length; i++) {
			if (i == index) {
				DOM.setStyleAttribute(strokeSize[i].getElement(),
						"borderColor", "red");
				currentStrokeSize = size;
			} else {
				DOM.setStyleAttribute(strokeSize[i].getElement(),
						"borderColor", "black");
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
				final Color color = current.getFillColor();
				Color newColor = new Color(color.getRed(), color.getGreen(),
						color.getBlue(), value);
				current.setFillColor(newColor);
			}

		}
	}

	/**
	 * Adds a "button" it means a clickable image to the toolbar (the <code>Grid</code>).
	 * @param title the title for the image
	 * @param icon the image (url) to load.
	 * @return a clickable <code>Image</code>.
	 */
	private Image addToGrid(Grid grid, int row, int col, String title,
			String icon) {
		Image button = new Image(GWT.getModuleBaseURL() + "images/"+icon);
		button.setTitle(title);
		button.setSize("29px", "29px");
		grid.setWidget(row, col, button);
		button.addClickListener(this);
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

	/**
	 * Performs some action when a click event is fired.
	 * @param sender the widget which fired a click event
	 */
	public void onClick(Widget sender) {
		if (sender.equals(rectButton)) {
			Rect rect = new Rect(300, 100);
			showGraphicObject(rect, 300, 300);
			rect.setWidth(100);

		} else if (sender.equals(circleButton)) {
			showGraphicObject(new Circle(50), 300, 300);
		} else if (sender.equals(colorButton)) {
			showPopupColor();
		} else if (sender.equals(textButton)) {
			showText();

		} else if (sender.equals(scaleButton)) {
			this.showPopupScaler();
		} else if (sender.equals(rotateButton)) {
			showPopupRotate();
		} else if (sender.equals(imageButton)) {
			ImageGfx img = new ImageGfx("od-logo.jpg", 105, 52);
			showGraphicObject(img, 300, 300);

		} else if (sender.equals(ellipseButton)) {
			showGraphicObject(new Ellipse(200, 100), 300, 300);
		} else if (sender.equals(strokeSize[0])) {
			this.chooseStrokeSize(0, 1);
		} else if (sender.equals(strokeSize[1])) {
			this.chooseStrokeSize(1, 2);
		} else if (sender.equals(strokeSize[2])) {
			this.chooseStrokeSize(2, 3);
		} else if (sender.equals(strokeSize[3])) {
			this.chooseStrokeSize(3, 5);
		} else if (sender.equals(propertiesButton) && current != null) {
			showProperties(current);
		} else if (sender.equals(frontButton) && current != null) {
			this.current.moveToFront();
		} else if (sender.equals(backButton) && current != null) {
			this.current.moveToBack();
		} else if (sender.equals(deleteButton) && current != null) {
			if ( current.getGroup() != null) {
				canvas.remove(current.getGroup());
			} else {
			   canvas.remove(current);
			}
		} else if (sender.equals(lineButton)) {
			final Point pointA = new Point(50, 50);
			final Point pointB = new Point(200, 360);
			Line line = new Line(pointA, pointB);
			line.setStrokeStyle(Line.LONGDASH);
			showGraphicObject(line, 300, 300);
		} else if (sender.equals(pathButton)) {
			showPath();
		} else if (sender.equals(virtualButton)) {
			this.showVirtual();

		} else if (sender.equals(textPathButton)) {
			this.showTextPath();
		} else if (sender.equals(polylineButton)) {
			showPolyline();

		}
	}

	/**
	 * Shows an example of a <code>Polyline</code> object
	 *
	 */
	private void showPolyline() {

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
		this.showGraphicObject(poly, 300, 300);
	}


	/**
	 * Shows an example of a <code>Path</code> object
	 *
	 */
	private void showPath() {
		// start point
		Point p1 = new Point(50, 50);
		Point p2 = new Point(80, 50);
		Point p3 = new Point(50, 100);
		Point p4 = new Point(80, 100);

		Path t = new Path();
		t.setFillColor(currentFillColor);
		t.setStroke(currentStrokeColor, currentStrokeSize);

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
		canvas.add(t, 60, 100);
		canvas.setPixelSize(600, 600);
	}


	/**
	 * Show some properties about a <code>GraphicObjectw</code>
	 * @param object the <code>GraphicObject</code> to show the properties
	 * TODO change the layout of the dialog
	 */
	private void showProperties(GraphicObject object) {
		final DialogBox dialog = new DialogBox(false);
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
	private void showPopupScaler() {
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
			popupScaler.setPopupPosition(scaleButton.getAbsoluteLeft(),
					scaleButton.getAbsoluteTop());
			popupScaler.show();
		}
	}

	/**
	 * Show a popup with a <code>Slider</code> to
	 * perform some rotation of the current <code>GraphicObject</code>
	 *
	 */
	private void showPopupRotate() {
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
			popupRotate.setPopupPosition(rotateButton.getAbsoluteLeft(),
					rotateButton.getAbsoluteTop());
			popupRotate.show();
		}
	}

	/**
	 * Shows a popup to select a color for the background or the stroke.
	 * The popup contains a TabPanel with 3 tab, a <code>ColorChooser</code>,
	 *  a <code>Slider</code> to change the opacity of the <code>Color</code>,
	 *  some <code>Pattern</code> to apply
	 */
	private void showPopupColor() {

		final PopupPanel popupColor = new PopupPanel(true);
		popupColor.addStyleName("GfxDemo-popupColor");
		TabPanel tabPanel = new TabPanel();
		VerticalPanel colPanel = new VerticalPanel();
		colPanel.setSpacing(5);
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
					DOM.setStyleAttribute(fill.getElement(), "backgroundColor",
							color);
					currentFillColor.setAlpha(opacity.getValue());
					if (current != null) {
						current.setFillColor(currentFillColor);
					}
				} else {
					currentStrokeColor = colorSelected;
					lastStrokeColor = currentStrokeColor;
					DOM.setStyleAttribute(fill.getElement(), "borderColor",
							color);
					if (current != null) {
						System.out.println(currentStrokeColor);
						current.setStroke(currentStrokeColor, 1);
					}
				}
			}
		};
		colorChooser.addChangeListener(colorChange);

		Grid grid = new Grid(2, 3);
		grid.setCellSpacing(10);
		grid.setCellPadding(10);
		grid.setWidget(0, 0, createImagePattern("gfx/none.gif"));
		grid.setWidget(0, 1, createImagePattern("littleNero.png"));
		grid.setWidget(0, 2, createImagePattern("littleTrajan.png"));
		grid.setWidget(1, 0, createImagePattern("cubic.jpg"));
		grid.setWidget(1, 1, createImagePattern("logo_ft.gif"));
		grid.setWidget(1, 2, createImagePattern("od-logo.jpg"));
		tabPanel.add(grid, new Label("Pattern"));

		tabPanel.add(this.opacity, new Label("Opacity"));

		tabPanel.selectTab(0);
		popupColor.add(tabPanel);
		popupColor.setPopupPosition(colorButton.getAbsoluteLeft(), colorButton
				.getAbsoluteTop());
		popupColor.show();
	}

	/**
	 * Creates a clickable <code>Image</code> when a click event is fired, a pattern correpsonding to
	 * the image is applied.
	 * @param url the url of the image
	 * @return the <code>Image</code> created
	 */
	private Image createImagePattern(final String url) {
		final Image image = new Image(url);
		image.setSize("32px", "32px");
		image.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				if (current != null) {
					Pattern pattern = null;
					if (url == "gfx/none.gif") {
						pattern = Pattern.DEFAULT_PATTERN;
					} else {
						pattern = new Pattern(new Image(url), 0, 0);
					}
					current.applyPattern(pattern);
				}
			}
		});
		return image;
	}

	/**
	 * Shows an example of a <code>Text</code> object
	 */
	private void showText() {
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
		showGraphicObject(text, 10, 20);

//		showGraphicObject(text2, 10, 40);
//		showGraphicObject(text3, 10, 60);
//		showGraphicObject(text4, 10, 100);
//		text4.setFont(font4);

	}

	/**
	 * Shows an example of a <code>TextPath</code> object
	 *
	 */
	private void showTextPath() {
		TextPath textPath = new TextPath(
				"Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Praesent erat.In malesuada ultricies velit. Vestibulum tempor odio vitae diam. Morbi arcu lectus, laoreet eget, nonummy at, elementum a, quam.");
		this.showGraphicObject(textPath, 10, 10);
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

	}


	/**
	 * Shows an example of a <code>VirtualGroup</code> object
	 *
	 */
	private void showVirtual() {
		VirtualGroup virtual = new VirtualGroup();
		Rect r = new Rect(100, 20);
		Circle c = new Circle(20);
		c.translate(0, 15);
		r.translate(0, 25);
		virtual.add(c);
		virtual.add(r);
		this.showGraphicObject(virtual, 300, 300);

	}

	/**
	 * Adds a <code>GraphicObject</code> to the <code>GraphicCanvas</code>
	 * @param object the <code>GraphicObject</code> to add
	 * @param int x the X coordinate
	 * @param int y the Y coordinate
	 *
	 */
	private void showGraphicObject(GraphicObject object, int x, int y) {
		// this.canvas.removeAllGraphics();
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
        opacity.setValue(current.getFillColor().getAlpha());
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
