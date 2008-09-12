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

import java.util.HashMap;
import java.util.Map;

/**
 * put your documentation comment here
 */
public class ControllerImpl extends AbstractProcessor implements Controller {
	protected Controller parent_ = null;

	protected Map children_ = null;

	protected Model model_ = new ModelImpl(this);

	protected View view_ = new ViewImpl(this);

	protected Map registered_ = null;

	/**
	 * 
	 */
	public ControllerImpl() {
	}

	/**
	 * put your documentation comment here
	 * 
	 * @param _name
	 * @param _m
	 */
	public void addChild(String _name, Controller _m) {
		if (children_ == null)
			children_ = new HashMap();
		children_.put(_name, _m);
		_m.setParent(this);
	}

	/**
	 * put your documentation comment here
	 * 
	 * @param _name
	 * @return
	 */
	public Controller removeChild(String _name) {
		Controller c = null;
		if (children_ != null) {
			c = (Controller) children_.remove(_name);
			if (c != null) {
				c.setParent(null);
			}
		}
		return c;
	}

	/**
	 * put your documentation comment here
	 * 
	 * @param _name
	 * @param _mvc
	 */
	public void addChild(String _name, MVC _mvc) {
		addChild(_name, _mvc.getController());
	}

	/**
	 * @see com.crionics.fw.ui.StateListener
	 */
	public void addListener(MVCComponent _c) {
		if (_c instanceof Model) {
			model_.addListener(_c);
		} else {
			view_.addListener(_c);
		}
	}

	/**
	 * put your documentation comment here
	 * 
	 * @param _name
	 * @param _e
	 */
	public void delegateToChild(String _name, Event _e) {
		if (children_ != null) {
			MVCComponent c = (MVCComponent) children_.get(_name);
			if (c != null) {
				// Category.getInstance(Constants.LOGGER_NAME).info("Delegating
				// to child");
				c.process(_e);
				return;
			}
		}
		// else {
		// Category.getInstance(Constants.LOGGER_NAME).info(
		// "delegateToChild:Bottom of hierachy, Event "
		// + _e
		// + " cannot be delegated to any children");
		// }
	}

	/**
	 * Insert the method's description here. Creation date: (6/5/01 4:05:31 PM)
	 * 
	 * @param e
	 *            com.test.Event
	 */
	public boolean delegateToParent(Event _e) {
		if (parent_ != null) {
			return parent_.process(_e);
		} else {
			// Category.getInstance(Constants.LOGGER_NAME).info(
			// "delegateToParent:No parent controller, loosing event " + _e);
			return false;
		}
	}

	/**
	 * @see com.crionics.fw.ui.StateListener
	 */
	public void fire(Event _e) {
		if (_e instanceof ModelEvent) {
			model_.fire(_e);
		} else
			view_.fire(_e);
	}

	public void register(int _t, EventProcessor _processor) {

		if (!(_processor instanceof ControllerProcessor)) {
			// Category.getInstance(Constants.LOGGER_NAME).info("processor is
			// not a ControllerProcessor");
			return;
		}
		super.register(_t, _processor);
	}

	/**
	 * @see com.crionics.fw.ui.Observer
	 */
	public boolean process(Event _e) {

		boolean b = super.process(_e);
		if (b)
			return true;
		else
			return delegateToParent(_e);

	}

	/**
	 * @see com.crionics.fw.ui.StateListener
	 */
	public void removeListener(MVCComponent _c) {
		if (_c instanceof Model) {
			model_.removeListener(_c);
		} else {
			view_.removeListener(_c);
		}
	}

	/**
	 * put your documentation comment here
	 * 
	 * @param m
	 */
	public void setParent(Controller m) {
		parent_ = m;
	}
}