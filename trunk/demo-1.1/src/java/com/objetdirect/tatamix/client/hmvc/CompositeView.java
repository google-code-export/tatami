package com.objetdirect.tatamix.client.hmvc;

import com.google.gwt.user.client.ui.Composite;

/**
 * In GWT a view will be a GWT <code>Composite</code>.
 * @author Vianney Grassaud
 *
 */
public class CompositeView extends Composite implements View {

	/**The HMVC view */
	private View view;

	/**
	 * Creates a new View
	 *
	 */
	public CompositeView() {
		super();
		view = new ViewImpl();
	}

	/**
	 * Adds a a listener to the view
	 * @param sl an <code>MVCComponent</code> should be a controller
	 */
	public void addListener(MVCComponent _sl) {
		view.addListener(_sl);

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
	public boolean process(Event _e) {
		return view.process(_e);
	}

	/**
	 * Registers an event to listen for the view.
	 * When the event is fired by a controller, the given processor is executed.
	 * @param _t the type of the event.
	 * @param _processor the <code>EventProcessor</code> to execute when the event will be fired
	 */
	public void register(int _t, EventProcessor _processor) {
		 view.register(_t, _processor);

	}

	/**
	 * Removes a listener
	 * @param _sl the listener to remove.
	 */
	public void removeListener(MVCComponent _sl) {
		view.removeListener(_sl);

	}

}//end of class
