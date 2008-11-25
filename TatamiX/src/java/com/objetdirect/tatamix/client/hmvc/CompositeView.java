package com.objetdirect.tatamix.client.hmvc;

import com.google.gwt.user.client.ui.Composite;

/**
 * The abstract GWT view used with HMVC pattern
 * @author Vianney Grassaud
 *
 */
public abstract class CompositeView extends Composite implements View {
 
	/**The view of the HMVC pattern*/
	private View view;

	/**
	 * Creates a new View.
	 *
	 */
	public CompositeView() {
		super();
		view = new ViewImpl();
	}

	/**
	 * Adds a a listener to the view
	 * @param sl an <code>MVCComponent</code> should be a <code>Controller</code>
	 */
	public void addListener(MVCComponent controller) {
		view.addListener(controller);
	}

	/**
	 * Fires an event to the listeners. The event should be a
	 * <code>ViewEvent</code>
	 * @param e <code>Event</code> event to fire.
	 */
	public void fire(Event e) {
		view.fire(e);

	}

	/**
	 * Processes an event. The event should be an <code>ViewEvent</code>
	 * @param _e an <code>Event</code>
	 */
	public boolean process(Event event) {
		return view.process(event);
	}

	/**
	 * Registers an event to listen for the view.
	 * When the event is fired by a controller, the given processor is executed.
	 * @param t the type of the event.
	 * @param processor the <code>EventProcessor</code> to execute when the event will be fired
	 */
	public void register(String type, EventProcessor processor) {
		 view.register(type, processor);

	}

	/**
	 * Removes a listener
	 * @param controller the listener to remove.
	 */
	public void removeListener(MVCComponent controller) {
		view.removeListener(controller);

	}

}//end of class
