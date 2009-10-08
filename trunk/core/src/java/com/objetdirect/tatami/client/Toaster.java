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
package com.objetdirect.tatami.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * This widget does display in a corner of a container some messages which
 * disappear afetr a delay.
 * <p>
 * This is class is esay : a widget of type Toaster is created and attached -
 * without be positioned - on the GWT widget. It is invisible. This widget have
 * a property topic. When a message is publish doing a reference to this topic,
 * it is presented by the Toaster.
 * <p>
 * The static methods <code>publishXXX</code> permit to publish the messages
 * <p>
 * We can create several toasters. Some of them can share the same topic. In
 * this case, the message is shown in several places
 * 
 * @author Henry, Vianney
 */
public class Toaster extends AbstractDojo {

	/** position right lower, goes from the bottom to the top */
	static public final String BOTTOM_RIGHT_UP = "br-up";

	/** position right lower, goes from the right to the left */
	static public final String BOTTOM_RIGHT_LEFT = "br-left";

	/** position left lower, goes from the bottom to the top */
	static public final String BOTTOM_LEFT_UP = "bl-up";

	/** position left lower, goes from the left to the right */
	static public final String BOTTOM_LEFT_RIGHT = "bl-right";

	/** position right upper, goes from the top to the bottom */
	static public final String TOP_RIGHT_DOWN = "tr-down";

	/** position right upper, goes from the right to the left */
	static public final String TOP_RIGHT_LEFT = "tr-left";

	/** position left upper, goes from the bottom to the top */
	static public final String TOP_LEFT_DOWN = "tl-down";

	/** position left upper , goes from the left to the right */
	static public final String TOP_LEFT_RIGHT = "tl-right";

	/** DELAY for the message by default is 1 second */
	static public final int DELAY = 1000;

	/**
	 * Error message type.
	 */
	static public final String ERROR_MESSAGE = "error";

	/**
	 * warning message type
	 */
	static public final String WARNING_MESSAGE = "warning";

	/**
	 * FATAL message type
	 */
	static public final String FATAL_MESSAGE = "fatal";

	/**
	 * PLAIN message type
	 */
	static public final String PLAIN_MESSAGE = "message";

	/** Message to display */
	private String messageTopic;

	/**
	 * Postion for the messages.
	 */
	private String position;

	private int duration;

	private String message;

	private String typeMessage;

	
	
	/**
	 * Creates the Toatser specifying the topic of the messages. The message
	 * will have the position <code>BOTTOM_RIGHT_UP</code>
	 * 
	 * @param messageTopic
	 *            the topic for the messages
	 */
	public Toaster(String messageTopic) {
		this(messageTopic, BOTTOM_RIGHT_UP);
	}

	/**
	 * Returns the name of DOJO widget
	 * 
	 * @return "Toaster"
	 */
	public String getDojoName() {
		return "dojox.widget.Toaster";
	}

	/**
	 * Creates the DOJO Toaster component. Use the topic and the position given
	 * by the constructor.
	 * 
	 * @throws Exception
	 * @see #createToaster(String, String)
	 */
	public void createDojoWidget() throws Exception {
		this.dojoWidget = createToaster(messageTopic, position);
	}

	/**
	 * Creates the Toaster at the specific position and with the specific topic.
	 * 
	 * @param messageTopic
	 *            the topic for the Toaster
	 * @param position
	 *            position for the message to display. Availables values are
	 *            <code>
	 *            BOTTOM_RIGHT_UP, BOTTOM_RIGHT_LEFT, BOTTOM_LEFT_UP,
	 *            BOTTOM_LEFT_RIGHT TOP_RIGHT_DOWN, TOP_RIGHT_LEFT,
	 *            TOP_LEFT_DOWN, TOP_LEFT_RIGHT</code>
	 */
	public Toaster(String messageTopic, String position) {
		super();
		this.messageTopic = messageTopic;
		this.position = position;
		this.duration = DELAY;
		this.message = "";
		this.typeMessage = PLAIN_MESSAGE;
	}

	protected void setContent(String message, String type, int delay) {
		this.message = message;
		this.typeMessage = type;
		this.duration = delay;
		setContent(getDojoWidget(), message, type, delay);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setTypeMessage(String typeMessage) {
		this.typeMessage = typeMessage;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getMessage() {
		return this.message;
	}

	public String getTypeMessage() {
		return this.typeMessage;
	}

	public int getDuration() {
		return this.duration;
	}

	public void show() {
		if (isAttached()) {
			setContent(getMessage(), getTypeMessage(), getDuration());
			show(getDojoWidget());
		}
	}

	public void hide() {
		if (isAttached()) {
		hide(getDojoWidget());
		}
	}

	static private native void hide(JavaScriptObject dojoWidget) /*-{
	 dojoWidget.hide();
	 }-*/;

	static private native void show(JavaScriptObject dojoWidget) /*-{
	 dojoWidget.show();
	 }-*/;

	static private native void setContent(JavaScriptObject dojoWidget,
			String message, String type, int delay) /*-{
	 dojoWidget.setContent(message,type,delay);
	 }-*/;

	/**
	 * Creates the Toaster DOJO widget at the specific position and with the
	 * specific topic.
	 * 
	 * @param messageTopic
	 *            the topic for the Toaster
	 * @param position
	 *            position for the message to display. Availables values are
	 *            <code>
	 *            BOTTOM_RIGHT_UP, BOTTOM_RIGHT_LEFT, BOTTOM_LEFT_UP,
	 *            BOTTOM_LEFT_RIGHT TOP_RIGHT_DOWN, TOP_RIGHT_LEFT,
	 *            TOP_LEFT_DOWN, TOP_LEFT_RIGHT</code>
	 */
	private native JavaScriptObject createToaster(String messageTopic,
			String position)
	/*-{
	 var widget = new $wnd.dojox.widget.Toaster( {	
	 messageTopic: messageTopic,	
	 positionDirection: position,	
	 }
	 );
	 return widget;
	 }-*/;
	

	/**
	 * Publishes a PLAIN type message on a specific topic. the delay is specify
	 * by the constant <code>DELAY</code>.
	 * 
	 * @param topic,
	 *            the topic for the message
	 * @param message
	 *            the content of the message, can be some code HTML
	 * @see {@link #publish(String, String, String, int)}
	 * @see {@link #publishError(String, String)}
	 * @see {@link #publishWarning(String, String)}
	 */
	public static void publishMessage(String topic, String message) {
		dojoPublishMessage(topic, message, PLAIN_MESSAGE, DELAY);
	}

	/**
	 * Publishes a WARNING type message on a specific topic. the delay is
	 * specify by the constant <code>DELAY</code>.
	 * 
	 * @param topic,
	 *            the topic for the message
	 * @param message
	 *            the content of the message, can be some code HTML
	 * @see {@link #publish(String, String, String, int)}
	 * @see {@link #publishError(String, String)}
	 * @see {@link #publishMessage(String, String)}
	 */
	public static void publishWarning(String topic, String message) {
		dojoPublishMessage(topic, message, WARNING_MESSAGE, DELAY);
	}

	/**
	 * Publishes an ERROR type message on a specific topic. the delay is specify
	 * by the constant <code>DELAY</code>.
	 * 
	 * @param topic,
	 *            the topic for the message
	 * @param message
	 *            the content of the message, can be some code HTML
	 * @see {@link #publish(String, String, String, int)}
	 * @see {@link #publishMessage(String, String)}
	 * @see {@link #publishWarning(String, String)}
	 */
	public static void publishError(String topic, String message) {
		dojoPublishMessage(topic, message, ERROR_MESSAGE, DELAY);
	}

	/**
	 * Publishes a message on a specific topic. All paramaters are requiered to
	 * specify the message.
	 * 
	 * @param topic
	 *            the topic for the message.
	 * @param message
	 *            the content of the message. Can be some HTML code.
	 * @param type
	 *            the type for the message. Possible values are :
	 *            <code>PLAIN_MESSAGE, 
	 *        WARNING_MESSAGE, ERROR_MESSAGE et FATAL_MESSAGE</code>
	 * @param delay
	 *            the delay for the message present to the screen in
	 *            milliseconds
	 */
	public static void publish(String topic, String message, String type,
			int delay) {

		dojoPublishMessage(topic, message, type, delay);
	}

	/**
	 * Asks to DOJO widget to publish the message.
	 * 
	 * @param topic
	 *            the topic that the message is attached. The Tosater
	 *            asssociated with this topic will display the message
	 * @param message
	 *            the message content (can be HTML code).
	 * @param type
	 *            the type of the message to publish. Values possibless are :
	 *            <code>PLAIN_MESSAGE, 
	 *            WARNING_MESSAGE, ERROR_MESSAGE et FATAL_MESSAGE</code>
	 * @param delay
	 *            the delay for the message in milliseconds
	 */
	private native static void dojoPublishMessage(String topic, String message,
			String type, int delay)
	/*-{
	 $wnd.dojo.publish(topic, [{message:message,
	 type: type,
	 duration :delay}] );
	 }-*/;

}// end of the class
