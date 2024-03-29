package com.objetdirect.tatamix.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;

/**
 * This class aims to associate a <code>Command</code> to a <code>Button</code>
 * If a command is set to the button then when a click event is fired the command will be the
 * first executed before the other cliklistener.
 * @author Vianney Grassaud
 * @deprecated use the Tatami Button instead
 */
public class Button extends com.google.gwt.user.client.ui.Button implements Enablable {

	private Command command;

	/**
	 * Creates <code>Button</code>
	 *
	 */
	public Button() {
		super();

		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			   if ( command != null) {
				   command.execute();
			   }
			}
		});
	}


	/**
	 * Sets the command to this button
	 * @param command a command to execute when an action is performed
	 */
	public void setCommand(Command cmd) {

		if ( command instanceof AbstractAction) {
			AbstractAction action = (AbstractAction)command;
			action.removeEnablable(this);

		}
		this.command = cmd;
		if ( cmd instanceof AbstractAction) {
			AbstractAction action = (AbstractAction)cmd;
			action.addEnablable(this);
			setHTML((String)action.getValue(AbstractAction.NAME));
			setTitle((String)action.getValue(AbstractAction.LONG_DESCRIPTION));
		}

	}

	/**
	 * Returns the command associated to this button
	 * @return a <code>Commande</code> or <code>null</code> if no command was set
	 */
	public Command getCommand() {
		return this.command;
	}


}
