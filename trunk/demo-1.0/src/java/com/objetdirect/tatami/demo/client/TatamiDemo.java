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
 * Authors: Henri Darmet, Vianney Grassaud
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.demo.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.BasePicker;
import com.objetdirect.tatami.client.Clock;
import com.objetdirect.tatami.client.ColorChooser;
import com.objetdirect.tatami.client.DatePicker;
import com.objetdirect.tatami.client.DojoContentPane;
import com.objetdirect.tatami.client.DragAndDropListener;
import com.objetdirect.tatami.client.DragAndDropPanel;
import com.objetdirect.tatami.client.DropdownContainer;
import com.objetdirect.tatami.client.DropdownDatePicker;
import com.objetdirect.tatami.client.DropdownTimePicker;
import com.objetdirect.tatami.client.FishEye;
import com.objetdirect.tatami.client.Paddle;
import com.objetdirect.tatami.client.Slider;
import com.objetdirect.tatami.client.TimePicker;
import com.objetdirect.tatami.client.Toaster;
import com.objetdirect.tatami.client.Toggler;
import com.objetdirect.tatami.client.test.TestUtil;

/**
 * Entry point classes define <code>onModuleLoad()</code>.  	
 * Demo of Tatami, the demo consists in showing some new widget GWT inserting wigdet Dojo. 
 * For it a particular menu that is with icons cliquables and putting on weight near the mouse, 
 * this menu will be the widget FishEye. An element which made the glory of Dojo a bit and "very " impressive.
 * Every item of the menu is therefore going to serve for introducing categories of widgets GWT as selectors 
 * of dates, clock etc.. or then widgets simply as Toggler makes windows, to slider... 
 * In every new chosen item a notification will be made, another new object GWT which offers Tatami!!
 * 
 * @author Henri Darmet, Vianney Grassaud
 */

public class TatamiDemo implements EntryPoint {

	/* the Tatami components  */
	private FishEye fishEye;

	private Paddle paddle;

	private Slider verticalSlider;

	private Slider horizSlider;

	private Clock clock;

	private DropdownDatePicker dddp;

	private DropdownTimePicker ddtp;

	private DatePicker dp;

	private TimePicker tp;

	private Toggler togglerNero;

	private Toggler togglerTrajan;

	private Toggler togglerHadrian;

	private Toggler togglerCaracalla;

	private DojoContentPane paneNero;

	private DojoContentPane paneTrajan;

	private DojoContentPane paneHadrian;

	private DojoContentPane paneCaracalla;

	private ColorChooser colorChooser;

	private ColorChooser colorChooser2;

	private DragAndDropPanel amoursCelebres;

	/*-- widget GWT --*/
	private Image cubicImg;

	private Image waterImg;

	private Label colorLabel;

	/*-- other --*/
	private int page = 0;

	private final int PADDLE = 1;

	private final int SLIDERS = 3;

	private final int DATE_TIME = 2;

	private final int TOGGLER = 4;

	// private final int FLOATING_PANE = 5;

	private final int COLOR = 7;

	private final int DRAG_N_DROP = 6;

	/**
	 * Loads the demo module
	 * The module aims to present the components of Tatami. 
	 * each class or component (it depends) are demonstrated by clicking on the FishEye menu a component of Tatami.
	 */
	public void onModuleLoad() {
       
		// menu fisheye
		fishEye = new FishEye();
		Toaster toaster = new Toaster("message");
		addItem("background.png", PADDLE, "paddle");
		addItem("browser.png", SLIDERS, "sliders");
		addItem("kalarm.png", DATE_TIME, "date-time");
		addItem("blackboard.png", TOGGLER, "toggler");
		
		// addItem("floating.png", FLOATING_PANE, "floating panes");
		addItem("icoColorPic.gif", COLOR, "color tools");
		addItem("amor.png", DRAG_N_DROP, "drap'n'drop");
		RootPanel rootPanel = RootPanel.get();
		rootPanel.add(fishEye, 50, 50);
		rootPanel.add(toaster);

		Timer timer = new Timer() {
			public void run() {
				try {
					TestUtil.fireEvent("click", 75, 75, TestUtil.MOUSE_BUTTON_1, 1,null);
				} catch (Exception e) {
					GWT.log("ERROR", e);
				}
			}
		};
		timer.schedule(1000);
	}

	/**
	 * Creates the message for the Toggler component
	 * @param icon icon to insert in the message
	 * @return The HTML code corresponding to a message
	 */
	private String getMessage(String icon) {
		return "<table><tr><td>Vous avez s&#233;lectionn&#233;: </td><td><img src=\""
				+ icon + "\"></td></tr></table>";
	}

	/**
	 * Adds a item to the FishEye menu.
	 * The command for each item is the demo of a component or a class of components from Tatami
	 * @param icon icon to represent for the item.
	 * @param page the number of the demo page to present when the command is invoked
	 * @param title title for the notification (displayed by the Toggler component)
	 */
	private void addItem(String icon, int page, String title) {
		this.fishEye.add(icon, title, new DemoCommand(icon, page));
	}

	/**
	 * Unloads the content of page 
	 */
	private void unLoadPage() {
		// on decharge la page courante :
		switch (this.page) {
		default: {
			// on ne fait rien
		}
		case PADDLE: {
			Widget[] widgets = { paddle, waterImg };
			unsetPage(widgets);
			break;
		}

		case SLIDERS: {
			Widget[] widgets = { verticalSlider, horizSlider, cubicImg };
			unsetPage(widgets);
			break;
		}

		case DATE_TIME: {
			Widget[] widgets = { dp, dddp, ddtp, tp, clock };
			unsetPage(widgets);
			break;
		}

		case DRAG_N_DROP: {
			unsetPageDragNDrop();
			break;
		}

		case TOGGLER: {
			Widget[] widgets = { togglerNero, paneNero, togglerTrajan,
					paneTrajan, togglerHadrian, paneHadrian, togglerCaracalla,
					paneCaracalla };
			unsetPage(widgets);
			break;
		}
		case COLOR: {
			Widget[] widgets = { colorChooser, colorChooser2, colorLabel };
			unsetPage(widgets);
			break;
		}

		}

	}

	/**
	 * Demo page to  present the component ColorChooser.
	 * 
	 */
	private void setPageColor() {
		colorChooser = new ColorChooser();
		colorChooser2 = new ColorChooser(ColorChooser.TWELVE_COLORS);
		colorLabel = new Label("no color selected");

		colorChooser.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				String color = colorChooser.getColor();
				if (color != null) {
					colorLabel.setText("The color selected : " + color);
					DOM.setStyleAttribute(colorLabel.getElement(), "color",
							color);

				}
			}
		});
		colorChooser2.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				String color = colorChooser2.getColor();
				if (color != null) {
					colorLabel.setText("The color selected : " + color);
					DOM.setStyleAttribute(colorLabel.getElement(), "color",
							color);

				}
			}
		});
		RootPanel.get().add(colorChooser, 50, 120);
		final int yPosition = colorChooser.getAbsoluteTop()
				+ colorChooser.getOffsetHeight() + 10;
		RootPanel.get().add(colorLabel, 50, yPosition);

		RootPanel.get()
				.add(
						colorChooser2,
						50,
						15 + colorLabel.getOffsetHeight()
								+ colorLabel.getAbsoluteTop());

	}

	/**
	 * Returns the number of the curent demo page
     * @return the number of the current page
	 */
	private int getPage() {
		return this.page;
	}

	/**
	 * Loads and displays on the screen the current demo page
	 * 
	 */
	private void loadPage() {
		// on decharge la page courante :
		switch (getPage()) {
		default: {
			// on ne fait rien
		}
		case PADDLE: {
			setPagePaddle();
			break;
		}

		case SLIDERS: {
			setPageSliders();
			break;
		}
		case DATE_TIME: {
			setPageDateTime();
			break;
		}

		case DRAG_N_DROP: {
			setPageDragNDrop();
			break;
		}

		case TOGGLER: {
			setPageToggler();
			break;
		}
		case COLOR: {
			setPageColor();
			break;
		}

		}
	}

	/**
	 * Sets a demo page to load and display 
	 * @param page the number of the page to display
	 */
	private void setPage(int page) {
		this.unLoadPage();
		this.page = page;
		this.loadPage();

	}

	/**
	 * Demo page for the Paddle component. When the cursor of the Paddle is changed then 
	 * the dimension of an image is also modify according to the values of the cursor.
	 * 
	 */
	private void setPagePaddle() {
		paddle = new Paddle(0, 200, 200, 0, 200, 200);
		paddle.setSize("200px", "200px");
		waterImg = new Image("water.jpg");
		paddle.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				if (paddle.getValueX() != -1) {
					waterImg.setWidth(paddle.getValueX() + "px");
				}
				if (paddle.getValueY() != -1) {
					waterImg.setHeight(paddle.getValueY() + "px");
				}
			}
		});
		paddle.setValue(50, 50);
		RootPanel.get().add(paddle, 20, 180);
		RootPanel.get().add(waterImg, 280, 180);
	}

	/**
	 * Demo page for the Slider component. When the cursor of the Slider is changed then 
	 * the dimension of an image is also modify according to the values of the cursor.
	 * 
	 */
	private void setPageSliders() {
		verticalSlider = new Slider(Slider.VERTICAL, 0, 200, 200);
		cubicImg = new Image("cubic.jpg");
		cubicImg.setSize("200px", "200px");
		RootPanel.get().add(cubicImg, 120 + 32, 160);
		verticalSlider.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				if (verticalSlider.getValue() != -1)
					cubicImg.setHeight(verticalSlider.getValue() + "px");
			}
		});
		verticalSlider.setValue(100);
		RootPanel.get().add(verticalSlider, 120, 160 - 16);
		horizSlider = new Slider(Slider.HORIZONTAL, 0, 200, 200);
		horizSlider.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				if (horizSlider.getValue() != -1) {

					cubicImg.setWidth(horizSlider.getValue() + "px");
				}
			}
		});

		RootPanel.get().add(horizSlider, 120 + 16, 376);

	}

	/**
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	boolean equalsObj(Object o1, Object o2) {
		if (o1 == null && o2 == null) {
			return true;
		} else if (o1 == null || o2 == null) {
			return false;
		} else {
			return o1.equals(o2);
		}
	}

	/**
	 * Demo page for date time component classes. 
	 * DatePicker, TimePicker, DropdownTimePicker, DropdownDatePicker and clock 
	 * are presented. 
	 * 
	 */
	private void setPageDateTime() {

		clock = new Clock();
		RootPanel.get().add(clock, 490, 150);

		dddp = new DropdownDatePicker();

		dddp.setDate(new Date());
		dp = new DatePicker();
		dp.setDate(new Date());
		this.linkDropdownAndPicker(dddp, dp);
		RootPanel.get().add(dddp, 30, 150);

		RootPanel.get().add(dp, 180, 150);

		ddtp = new DropdownTimePicker();
		ddtp.setTime(new Date());
		tp = new TimePicker();
		RootPanel.get().add(ddtp, 30, 200);
		tp.setDate(new Date());
		this.linkDropdownAndPicker(ddtp, tp);
		RootPanel.get().add(tp, 350, 150);
	}

	/**
	 * Creates a link between a <code>DropdownContainer</code> and 
	 * <code>BasePicker</code> with a <code>ChangeListener</code>. 
	 * So if the container is modify, it will update (if it's necessary) the picker and  
	 * si le container est modifié, il mettra à jour si nécessaire le picker et
	 * vice versa.
	 * @param container DropdownContainer to link
	 * @param picker BasePicker to link
	 */
	private void linkDropdownAndPicker(final DropdownContainer container,
			final BasePicker picker) {
		container.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				if (!equalsObj(picker.getDate(), container.getDate())) {
					picker.setDate(container.getDate());
				}

			}
		});

		picker.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				if (!equalsObj(container.getDate(), picker.getDate())) {
					container.setDate(picker.getDate());

				}
			}
		});

	}

	/**
	 * Removes the widgets from the browser
	 * @param widgets an array of widgets to remove 
	 */
	private void unsetPage(Widget[] widgets) {
		for (int i = 0; i < widgets.length; i++) {
			if (widgets[i] != null) {
				RootPanel.get().remove(widgets[i]);
			}
		}
	}

	/**
	 * Demo page for the Toggler component 
	 * 
	 */
	private void setPageToggler() {
		paneNero = new DojoContentPane("Nero");
		Image neroImg = new Image("nero.png");
		paneNero.add(neroImg);
		RootPanel.get().add(paneNero, 30, 150);

		togglerNero = new Toggler("Nero");

		Image littleNeroImg = new Image("littleNero.png");
		littleNeroImg.setTitle("nero");
		togglerNero.add(littleNeroImg);

		RootPanel.get().add(togglerNero, 160, 370);

		paneTrajan = new DojoContentPane("Trajan");
		Image trajanImg = new Image("trajan.png");
		paneTrajan.add(trajanImg);
		RootPanel.get().add(paneTrajan, 120, 120);

		togglerTrajan = new Toggler("Trajan");
		Image littleTrajanImg = new Image("littleTrajan.png");
		littleTrajanImg.setTitle("trajan");
		togglerTrajan.add(littleTrajanImg);
		RootPanel.get().add(togglerTrajan, 210, 370);

		paneHadrian = new DojoContentPane("Hadrian");
		Image hadrianImg = new Image("hadrian.png");
		paneHadrian.add(hadrianImg);
		RootPanel.get().add(paneHadrian, 210, 140);

		togglerHadrian = new Toggler("Hadrian");
		Image littleHadrianImg = new Image("littleHadrian.png");
		littleHadrianImg.setTitle("Hadrian");
		togglerHadrian.add(littleHadrianImg);
		RootPanel.get().add(togglerHadrian, 260, 370);

		paneCaracalla = new DojoContentPane("Caracalla");
		Image caracallaImg = new Image("caracalla.png");
		paneCaracalla.add(caracallaImg);
		RootPanel.get().add(paneCaracalla, 280, 160);

		togglerCaracalla = new Toggler("Caracalla");
		Image littleCaracallaImg = new Image("littleCaracalla.png");
		littleCaracallaImg.setTitle("Caracalla");
		togglerCaracalla.add(littleCaracallaImg);
		RootPanel.get().add(togglerCaracalla, 310, 370);
	}

	/**
	 * Demo page for the Drag and Drop using
	 *
	 */
	private void setPageDragNDrop() {
		amoursCelebres = new DragAndDropPanel();
		amoursCelebres.setSize("480px", "300px");

		SimplePanel romeoPanel = new SimplePanel();
		Image romeo = new Image("romeo.png");
		romeoPanel.add(romeo);
		romeo.setTitle("romeo");
		amoursCelebres.addDraggableWidget(romeoPanel, 100, 20,
				"romeo_et_juliette");
		Image juliette = new Image("juliette.png");
		juliette.setTitle("juliette");
		amoursCelebres.addTargetWidget(juliette, 300, 20, "romeo_et_juliette");

		SimplePanel tristanPanel = new SimplePanel();
		Image tristan = new Image("tristan.png");
		tristanPanel.add(tristan);
		tristan.setTitle("tristan");
		amoursCelebres.addDraggableWidget(tristanPanel, 300, 180,
				"tristan_et_iseult");
		Image iseult = new Image("iseult.png");
		iseult.setTitle("iseult");
		amoursCelebres.addTargetWidget(iseult, 100, 180, "tristan_et_iseult");

		DragAndDropListener listener = new DragAndDropListener() {
			public void onDrop(Widget draggable, Widget target) {
				Image dulcinee = (Image) target;
				Image couple = new Image("couple_" + dulcinee.getTitle()
						+ ".png");
				amoursCelebres.add(couple,
						amoursCelebres.getWidgetLeft(target) - 50,
						amoursCelebres.getWidgetTop(target) - 25);
			}

			public boolean acceptDrop(Widget draggable, Widget target) {
				return true;
			}
		};
		amoursCelebres.addDragDropListener(listener);

		RootPanel.get().add(amoursCelebres, 10, 100);
	}

	
	private void unsetPageDragNDrop() {
		RootPanel.get().remove(amoursCelebres);
	}

	/**
	 * Class to represent a command for each item of the menu FishEye
	 * @author Vianney
	 * 
	 */
	private class DemoCommand implements Command {
		private String icone = "";

		private int page = 0;

		/**
		 * Creates a command for an item of the menu FishEye (the main component of the demo).
		 * the command aims to change a "page" to disaplay on the screencf <code>setPage(int)</code>
		 * @param icon icon for a notification that the command is selected.
		 * @param page number of the page to display.
		 */
		public DemoCommand(String icon, int page) {
			this.icone = icon;
			this.page = page;
		}

		/**
		 * Executes the command
		 */
		public void execute() {
			Toaster.publishMessage("message", getMessage(icone));
			setPage(page);
		}
	}
}
