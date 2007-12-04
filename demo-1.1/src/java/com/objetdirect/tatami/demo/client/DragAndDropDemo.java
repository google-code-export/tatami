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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.DragAndDropListener;
import com.objetdirect.tatami.client.DragAndDropPanel;

public class DragAndDropDemo extends Composite implements DragAndDropListener {

	private DragAndDropPanel amoursCelebres;
	private SimplePanel romeoPanel;
	private SimplePanel tristanPanel;
	private Image romeo;
	private Image juliette;
	private Image iseult;
	private Image tristan;
	private DockPanel mainPanel;
	
	public DragAndDropDemo() {
		initComponents();
		initWidget(mainPanel);
	}
	
	
	/**
	 * Inits all the components. 
	 * only two images are draggable and can be droped to one image
	 * 
	 *
	 */
	private void initComponents() {
		mainPanel = new DockPanel();
		mainPanel.setSpacing(20);
		amoursCelebres = new DragAndDropPanel();
		amoursCelebres.setSize("480px", "300px");
         
		romeoPanel = new SimplePanel();
		romeo = new Image("romeo.png");
		DOM.setStyleAttribute(romeo.getElement(),"cursor","pointer");
		
		romeo.setTitle("romeo");
		romeoPanel.add(romeo);
		juliette = new Image("juliette.png");
		juliette.setTitle("juliette");

		amoursCelebres.addDraggableWidget(romeoPanel, 100, 20,"romeo_et_juliette");		
		amoursCelebres.addTargetWidget(juliette, 300, 20, "romeo_et_juliette");

		tristanPanel = new SimplePanel();
		tristan = new Image("tristan.png");
		DOM.setStyleAttribute(tristan.getElement(),"cursor","pointer");
		tristanPanel.add(tristan);
		tristan.setTitle("tristan");
		iseult = new Image("iseult.png");
		iseult.setTitle("iseult");
		
		amoursCelebres.addDraggableWidget(tristanPanel, 300, 180,"tristan_et_iseult");
		amoursCelebres.addTargetWidget(iseult, 100, 180, "tristan_et_iseult");

		amoursCelebres.addDragDropListener(this);
		mainPanel.add(new HTML("<b>Drag and Drop</b> : Recompose the famous lovers by moving the images of the amants."),DockPanel.NORTH);
		mainPanel.add(amoursCelebres,DockPanel.CENTER);
		
	}
	
	/**
	 * Change the image when the onDrop event is fired
	 */
	public void onDrop(Widget draggable, Widget target) {
		Image dulcinee = (Image) target;
		Image couple = new Image("couple_" + dulcinee.getTitle()+ ".png");
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
