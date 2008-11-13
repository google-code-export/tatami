package com.objetdirect.tatamix.client.widget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.ColorChooser;

/**
 * This widget aims to edit a color value using hexadecimal format easily.
 * The value of the color is edited with <code>TextBox</code>. When this <code>TextBox</code>
 * takes the focus, a <code>ColorChooser</code> will be displayed in order to choose easily a color.
 * The value of the selected color will be the value of the <code>TextBox</code>.
 * Also when a color is selected, the border of the <code>TextBox</code> takes the same color.
 * @author Vianney Grassaud
 *
 */
public class ColorEditor extends Composite implements ChangeListener, FocusListener {

	//layout to display textbox and colorChooser
	private FlowPanel layout;
    /** widget to chooser a color*/
	private ColorChooser colorChooser;
	/** widget to enter a color value */
	private TextBox colorText;
	/** the popup which display the colorChooser */
	private PopupPanel popup;

	/**
	 * Creates the colorEditor
	 *
	 */
	public ColorEditor(String size) {
		layout = new FlowPanel();
		initWidget(layout);
		initComponents(size);
	}



	public ColorEditor() {
		this(ColorChooser.SEVENTY_COLORS);
	}


    public TextBox getInput() {
    	return this.colorText;
    }


	/**
	 * Inits the UI components
	 *
	 */
	private void initComponents(String size) {
		colorChooser = new ColorChooser(size);
		colorChooser.addChangeListener(this);
		colorText = new TextBox();

		colorText.addFocusListener(this);
		layout.add(colorText);

	}

    /**
     * Returns the value of the edited color.
     * @return the value of the edited color, return an empty string if no
     *          color was edited or selected in the color chooser
     */
	public String getColor() {
		return colorText.getText();
	}

	/**
	 * Sets the color to select in the color chooser.
	 * @param color the color to select
	 */
	public void setColor(String color) {
		colorChooser.setColor(color);
	}

    /**
     * Changes the value of the <code>TextBox</code> when a color is selected in the color chooser
     */
	public void onChange(Widget sender) {
		String color = colorChooser.getColor();
		colorText.setText(color);
		if ( popup != null) {
		 popup.hide();
		}
		 try {
			 DOM.setStyleAttribute(colorText.getElement(), "borderColor", color);
	        } catch (Exception e ) {
	        	//DOM.setStyleAttribute(colorText.getElement(), "borderColor", "#000");
	        }

	}

	/**
	 * Displays the color chooser when the widget takes the focus
	 */
	public void onFocus(Widget sender) {
		showPopup(sender);

	}

	/**
	 * Do nothing
	 */
	public void onLostFocus(Widget sender) {

		//popup.hide();
	}

	/**
	 * Inits the popup panel which displays the color chooser
	 *
	 */
	private void initPopup() {
		popup = new PopupPanel(true);

		DOM.setStyleAttribute(popup.getElement(), "zIndex","10");
		popup.setWidget(colorChooser);
	}

	/**
	 * Display the popup. The popup is positioned at the right of the <code>TextBox</code>
	 * @param sender the <code>TextBox</code>
	 */
	private void showPopup(final Widget sender) {
		  if ( popup ==null) {
	    	  initPopup();
	      }
	      popup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
	          public void setPosition(int offsetWidth, int offsetHeight) {
                int left = sender.getAbsoluteLeft() + sender.getOffsetWidth();
	        	  popup.setPopupPosition(left, sender.getAbsoluteTop());
	          }
	        });

	}


}//end of class
