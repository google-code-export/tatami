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

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.DragAndDropListener;
import com.objetdirect.tatami.client.DragAndDropPanel;
import com.objetdirect.tatamix.client.hmvc.CompositeView;
import com.objetdirect.tatamix.client.widget.Paragraph;

public class DragAndDropDemo extends CompositeView implements DragAndDropListener {

	private DragAndDropPanel amoursCelebres;
	private SimplePanel romeoPanel;
	private SimplePanel tristanPanel;
	private Image romeo;
	private Image juliette;
	private Image iseult;
	private Image tristan;
	private FlowPanel layout;

	public DragAndDropDemo() {
		layout = new FlowPanel();
		initComponents();
		initWidget(layout);
		setStylePrimaryName("block");
	}


	/**
	 * Inits all the components.
	 * only two images are draggable and can be droped to one image
	 *
	 *
	 */
	private void initComponents() {

		amoursCelebres = new DragAndDropPanel();
		amoursCelebres.setStylePrimaryName("bogueAbsolute");
		romeoPanel = new SimplePanel();
		romeo = createImage("romeo.png","romeo",true);
		romeoPanel.add(romeo);

		juliette =createImage("juliette.png","juliette",false);

		amoursCelebres.addDraggableWidget(romeoPanel, 100, 20,"romeo_et_juliette");
		amoursCelebres.addTargetWidget(juliette, 300, 20, "romeo_et_juliette");

		tristanPanel = new SimplePanel();

		tristan = createImage("tristan.png","tristan",true);
		tristanPanel.add(tristan);

		iseult = createImage("iseult.png","iseult",false);

		amoursCelebres.addDraggableWidget(tristanPanel, 300, 180,"tristan_et_iseult");
		amoursCelebres.addTargetWidget(iseult, 100, 180, "tristan_et_iseult");

		amoursCelebres.addDragDropListener(this);

        Paragraph intro = new Paragraph();
        intro.setHTML(TatamiDemo.getMessages().dnd_intro());



        layout.add(intro);
        layout.add(amoursCelebres);



	}


	private Image createImage(String icon,String title,boolean draggable ) {
		Image img = new Image(TatamiDemo.getIconURL(icon));
		img.setTitle(title);
		if (draggable ) {
			img.setStylePrimaryName("draggable");
		}
		return img;
	}

	/**
	 * Change the image when the onDrop event is fired
	 */
	public void onDrop(Widget draggable, Widget target) {
		Image dulcinee = (Image) target;
		Image couple = new Image(TatamiDemo.getIconURL("couple_" + dulcinee.getTitle()+ ".png"));
		amoursCelebres.add(couple,
				amoursCelebres.getWidgetLeft(target) - 50,
				amoursCelebres.getWidgetTop(target) - 25);
	}

	/**
	 * Accepts the drop
	 */
	public boolean acceptDrop(Widget draggable, Widget target) {
		return true;
	}
} //end of class
