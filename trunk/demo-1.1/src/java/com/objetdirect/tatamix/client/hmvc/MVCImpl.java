/*
 * Copyright 2001 by Olivier Refalo
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 * @author: Olivier Refalo orefalo@yahoo.com
 * @see http://www.crionics.com
 */

package com.objetdirect.tatamix.client.hmvc;

/**
 * This class implements the <code>MVC</code> interface to 
 * create triad (Model,View, Controller) easily. 
 * 
 * <p>
 * If you just want to connect all the three components call the helper method <code>createTriad</code> </p>
 * <p>
 * <code>
 * MVC triad = MVCImpl.createTriad(model, view, controller);
 * </code>
 * </p> 
 * 
 *
 */
public class MVCImpl implements MVC {
	
	protected Model model;

	protected View view;

	protected Controller controller;

	/**
	 * Creates an MVC triad
	 * @param _m the model can be <code>null</code>
	 * @param _v the view
	 * @param _c the controller
	 * @throws IllegalArgumentException if controller is <code>null</code>
	 */
	public MVCImpl(Model _m, View _v, Controller _c) {
		model = _m;
		view = _v;
		if ( _c == null) {
			throw new IllegalArgumentException("controller can not be null");
		} else {
		  controller = _c;
		  addModel();
		  addView();
		}
	}

	/**
	 * Adds the controller of the given triad to the controller of this triad.
	 * @param _name the name of the child controller
	 * @param _mvc the triad to add
	 */
	public void addChild(String _name, MVC _mvc) {
		getController().addChild(_name, _mvc.getController());
	}

	/**
	 * Returns the  <code>Controller</code> of this triad
	 * @return the  <code>Controller</code> of this triad
	 */
	public Controller getController() {
		return controller;
	}

	/**
	 * Returns the <code>Model</code> of this triad
	 * @return the <code>Model</code> of this triad, can be <code>null</code>
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * Returns the <code>View</code> of this triad. 
	 * @return Returns the <code>View</code> of this triad.
	 */
	public View getView() {
		return view;
	}

	
	/**
	 * Sets the <code>Controller</code> of this triad.
	 * @param _c the <code>Controller</code> of this triad. 
	 * @throws IllegalArgumentException if the given controller is <code>null</code> 
	 */
	public void setController(Controller _c) {
		if ( _c == null) {
			throw new IllegalArgumentException("controller can not be null");
		} else {
			removeModel();
			removeView();
			controller = _c;
			addModel();
			addView();
		}
	}

	/**
	 *Sets the <code>Model</code> of this triad
	 *@param _m the <code>Model</code> for the triad, can be <code>null</code> 
	 * 
	 */
	public void setModel(Model _m) {
		removeModel();
		model = _m;
		addModel();
	}

	/**
	 * Adds the model to the listeners
	 *
	 */
	private void addModel() {
		if ( model !=null) {
		  model.addListener(controller);
		  controller.addListener(model);
		}
	}
	
	
	/**
	 * Removes the view from the listeners
	 *
	 */
	private void removeView() {
		if (view != null) {
			controller.removeListener(view);
			view.removeListener(controller);
		}
	}
	
	/**
	 * Removes the model from the listeners
	 *
	 */
	private void removeModel() {
		if (model != null) {
			controller.removeListener(model);
			model.removeListener(controller);
		}
	}
	
	/**
	 * Adds the view to the listeners
	 *
	 */
	private void addView() {
		if ( view != null) {
		   view.addListener(controller);
		   controller.addListener(view);
		}
	}
	
	/**
	 * 
	 * @return com.crionics.fw.ui.View
	 */
	public void setView(View _v) {
		removeView();
		view = _v;
		addView();
	}
	
	/**
	 * Creates a triad MVC
	 * @param m the model 
	 * @param v the view
	 * @param c the controller
	 * @return returns a triad MVC 
	 */
	public static MVC createTriad(Model m, View v, Controller c) {
		return new MVCImpl(m,v,c);
	}
}//end of class
