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
 * 
 */
public class ControllerImpl extends AbstractProcessor implements Controller {
	
	/**
	 * The parent controller
	 */
	protected Controller parent = null;

	/**
	 * The children of this controller
	 */
	protected Map<String,Controller> children = null;

	/**
	 * The model attached to this controller
	 */
	protected Model model = new ModelImpl(this);

	/**
	 * The view attached to this controller
	 */
	protected View view = new ViewImpl(this);

	//protected Map<Integer,EventProcessor> registered = null;

	/**
	 * Create a instance of a <code>Controller</code>
	 */
	public ControllerImpl() {
	  super();
	}

	/**
	 * 
	 * Adds a controller child to this controller
	 * @param name the name of the child controller
	 * @param child the controller to add 
	 */
	public void addChild(String name, Controller child) {
		if (children == null) {
			children = new HashMap<String,Controller>();
		}
		children.put(name, child);
		//this controller becomes the parent of the given child
		child.setParent(this);
	}

	/**
	 * 
	 * Removes a child controller from this controller
	 * @param name the name of the child controller to remove
	 * @return the removed controller, <code>null</code> if the name of the controller was not found.
	 */
	public Controller removeChild(String name) {
		Controller child = null;
		if (children != null) {
			child =  children.remove(name);
			if (child != null) {
				//detach the controller from its parent
				child.setParent(null);
			}
		}
		return child;
	}

	/**
	 *Adds the controller of a MVC facade  
	 * @param name the name for the child controller
	 * @param mvc the <code>MVC</code> facade 
	 */
	public void addChild(String name, MVC mvc) {
		addChild(name, mvc.getController());
	}

	/**
	 * Adds a <code>MVCComponent</code> to be controlled by this controller. 
	 * @param component The <code>MVCComponent</code> can be a <code>Model</code> or a <code>View</code>
	 */
	public void addListener(MVCComponent component) {
		if (component instanceof Model) {
			model.addListener(component);
		} else {
			view.addListener(component);
		}
	}

	/**
	 * 
	 * 
	 * @param _name
	 * @param _e
	 */
	public void delegateToChild(String name, Event event) {
		if (children != null) {//if there are children
			MVCComponent child = children.get(name);
			if (child != null) {
			  child.process(event);
			}
		}
	}

	/**
	 * 
	 * Delegates the event to the parent controller
	 * @param event the event to delegate
	 * @return the result of the process executed by the parent controller if it exists. Otherwise, return <code>false</code>            
	 */
	public boolean delegateToParent(Event event) {
		boolean result = false;
		if (parent != null) {
			result = parent.process(event);
		} 
		return result;
	}

	/**
	 * 
	 */
	public void fire(Event event) {
		if (event instanceof ModelEvent) {
			model.fire(event);
		} else {
			view.fire(event);
		}
	}

	@Override
	public void register(int type, EventProcessor processor) {
        //the processor must be a ControllerProcessor
		if (processor instanceof ControllerProcessor) {
			super.register(type, processor);	
		} else {
			throw new IllegalArgumentException("The processor must be a ControllerProcessor");
		}

	}

	/**
	 * 
	 */
	public boolean process(Event event) {
		boolean result = false;
		if (super.process(event)) {
			 result= true;
		}	else {
			result =  delegateToParent(event);
		}
		return result;
	}

	/**
	 * 
	 */
	public void removeListener(MVCComponent component) {
		if (component instanceof Model) {
			model.removeListener(component);
		} else {
			view.removeListener(component);
		}
	}

	/**
	 * Sets the parent controller of this controller
	 * @param parent the parent controller
	 */
	public void setParent(Controller parent) {
		this.parent = parent;
	}
}//end of class