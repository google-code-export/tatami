package com.objetdirect.tatamix.client.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Command;

/**
 * An <code>ActionCommand</code> permits to link
 * <code>Buttons</code> with the command. If the action is set to enabled then
 * the buttons associate with this action will be enabled too and vice-versa if the action is set to disabled.
 * Moreover, the text and the title of buttons can be set automatically by
 * the action with the <code>NAME,LONG_DESCRIPTION</code> properties.
 *
 * @author Vianney
 *
 */
public abstract class AbstractAction implements Command,Enablable {

	/**
	 * Properties of this action
	 *
	 */
	private Map properties;

	private boolean enabled;


	private List enablables;

	/**
	 * Use this property to set the name of an action
	 */
	public static final String NAME = "NAME";

	/**
	 * Use this property to set a description of an action
	 */
	public static final String LONG_DESCRIPTION = "LONG_DESCRIPTION";

	/**
	 * Use this property to set the icon of an action
	 */
	public static final String ICON_SRC = "ICON_SRC";


	/**
	 * Creates an enabled action with no name
	 *
	 */
	public AbstractAction() {
		properties = new HashMap();
		enablables = new ArrayList();
		enabled = true;
	}

	/**
	 * Creates an action with the given name
	 * @param name the name of the action
	 */
	public AbstractAction(String name) {
		this();
		putValue(NAME,name);
		putValue(LONG_DESCRIPTION,name);
	}



    /**
     * Put a value for this action
     * @param name name of the properties
     * @param value value for the properties
     */
	public void putValue(String name,Object value) {
		properties.put(name,value);
	}

	/**
	 * Get the value of a property
	 * @param name the name of a property
	 * @return the value of the property or <code>null</code> if not found
	 */
	public Object getValue(String name) {
		return properties.get(name);
	}

	/**
	 * Sets the action  enabled (<code>true</code>) or disabled (<code>false</code>).
	 * @param b boolean
	 */
	public void setEnabled(boolean b) {
		this.enabled = b;
		for ( Iterator ite =enablables.iterator(); ite.hasNext();) {
			Object object = ite.next();
			if ( object instanceof Enablable ) {
			  ((Enablable)object).setEnabled(enabled );
			}
		}
	}

	/**
	 * Checks if this action is enabled or not
	 * @return <code>true</code> if it enabled
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * Adds a Enablable associated with this action.
	 * This method is used by the <code>Button</code> class.
	 * @param b the <code>Enablable</code> to associate
	 */
	protected void addEnablable(Enablable b) {
		b.setEnabled(isEnabled());
		this.enablables.add(b);
	}
	/**
	 * removes a Enablable which it was associated to this action
	 * @param b the <code>Enablable</code> to remove
	 */
	protected void removeEnablable(Enablable b) {
		this.enablables.remove(b);
	}

}//end of class