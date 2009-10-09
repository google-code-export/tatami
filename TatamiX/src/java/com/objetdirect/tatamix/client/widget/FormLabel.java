package com.objetdirect.tatamix.client.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class FormLabel extends FocusWidget implements HasText, HasHTML {

	/**
	 * Creates a LABEL HTML element
	 *
	 */
	public FormLabel() {
		super(Document.get().createLabelElement());
		
	}

	/**
	 * Returns the HTML text of this <code>FormLabel</code>
	 * @return HTML text
	 */
	public String getHTML() {
		return getElement().getInnerHTML();
	}

	/**
	 * Sets the HTML content for this <code>FormLabel</code>
	 * @param html the HTML content 
	 */
	public void setHTML(String html) {
		getElement().setInnerHTML(html);
		
	}

	/**
	 * Returns the text content of this <code>FormLabel</code>
	 * @returns the text content
	 */
	public String getText() {
	
		return getElement().getInnerText();
	}

	/**
	 * Sets the Text content for this <code>FormLabel</code>
	 * @param html the text content 
	 */
	public void setText(String text) {
		getElement().setInnerText(text);
		
	}

	/**
	 *Associates a <code>Widget</code> to this <code>FormLabel</code>
	 *If the label recieve an ONCLICK event then the associated widget will get the 
	 *focus. 
	 *@param id the id to associate between this <code>FormLabel</code> and the <code>Widget</code>
	 *@param Widget a widget should be an input widget like <code>TextBox</code>... 
	 **/
	public void setFor(String id, Widget widget) {
		getElement().getStyle().setProperty("htmlFor",id);
		widget.getElement().getStyle().setProperty("id",id);
		
	}

	/**
	 * Returns the id of a <code>Widget</code> which is associated to this
	 *         <code>FormLabel</code>
	 * @return return an id or <code>null</code> if no association was set.
	 */
   public String getFor() {
	   return getElement().getStyle().getProperty("for");
   }


}//end of class
