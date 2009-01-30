package com.objetdirect.tatamix.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatamix.client.widget.resources.OptionPanelString;

public  class OptionPanel extends Form implements ClickListener, WindowResizeListener,PopupListener{

	public  static final boolean YES_OPTION = true;
	public  static final boolean NO_OPTION = false;

	
	public static final int CONFIRM_MESSAGE = 0;
	public static final int ERROR_MESSAGE = 1;
	public static final int MESSAGE = 2;
	
	
	private FlowPanel layout;
	
	private String message;
	private int typeMessage = CONFIRM_MESSAGE;
	private boolean value;
	
	private com.objetdirect.tatami.client.Button yesButton;
	private com.objetdirect.tatami.client.Button noButton;
	
	
	private OptionPanelString strings;
	private int zIndex = 100;
	
	private static DivElement mask = Document.get().createDivElement();
	
	/**
	 * Creates the OptionPanel
	 */
	protected OptionPanel() {
		super();
		layout = new FlowPanel();
		strings = GWT.create(OptionPanelString.class);
		initWidget(layout);
		
		Style style = mask.getStyle();
		style.setProperty("backgroundColor", "#aaa");
		style.setProperty("position", "absolute");
		style.setProperty("top", "0px");
		style.setProperty("left", "0px");
		style.setProperty("visibility", "visible");
		setStylePrimaryName("OptionPanel");
	}
	
	
	public void reset() {
		
	}
	
	/**
	 * Initializes the components
	 */
	protected void initComponents() {
		HTML label = new HTML();
		
		label.setText(message);
		label.setStylePrimaryName("label");
		layout.add(label);
		yesButton = new com.objetdirect.tatami.client.Button();
		yesButton.addClickListener(this);
		setValidator(yesButton);
		layout.add(yesButton);
	
		switch(this.typeMessage) {
		   default : {
			   yesButton.setText(strings.yes());
			   noButton = new com.objetdirect.tatami.client.Button();
			   noButton.addClickListener(this);
			   noButton.setText(strings.no());
			   layout.add(noButton);
			   setCancel(noButton);
			   label.addStyleDependentName("confirmMessage");
			   break;
		   }
		   case MESSAGE: {
			   yesButton.setText(strings.ok());
			   label.addStyleDependentName("message");
			   break;
		   }
		   case ERROR_MESSAGE: {
			   yesButton.setText(strings.ok());
			   label.addStyleDependentName("errorMessage");
			   break;
		   }
		   
		}
	
		
		
		
		
	}
	
	/**
	 * Returns the zIndex style attribute of this <code>OptionPanel</code>
	 * @return the zIndex style attribute of this <code>OptionPanel</code>
	 */
	public int getZIndex() {
		return this.zIndex;
	}
	
	/**
	 * Sets the z-index of this OptionPanel. This is useful when you need to display an OptionPane in a DialogBox
	 * @param zIndex a zIndex
	 */
	public void setZIndex(int zIndex) {
		this.zIndex = zIndex;
	}
	
	/**
	 * Sets the type of the message
	 * @param type <code>CONFIRM_MESSAGE, MESSAGE,ERROR_MESSAGE</code>
	 */
	public void setTypeMessage(int type) {
		this.typeMessage = type;
	}
	
		
	/**
	 * Sets the message text
	 * @param message the message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Returns the value of the option
	 * @return the value of the option
	 */
	public boolean getValue() {
		return this.value;
	}
	
	
	/**
	 * set the value to return is the use click on the yes or no button
	 */
	public void onClick(Widget sender) {
		if ( sender.equals(yesButton)) {
			value = YES_OPTION;
			
		} else if ( sender.equals(noButton)) {
			value = NO_OPTION;
		
		} 
		
		
		
	}
	
	/**
	 * Returns the max height between the window and the document to fix the height of the glass panel 
	 * @return the max height between the window and the document to fix the height of the glass panel 
	 */
	private static int getMaxHeight() {
		return   Math.max(RootPanel.get().getOffsetHeight(),Window.getClientHeight()) + Window.getScrollTop();
	}

	/**
	 * Returns the max width between the window and the document to fix the width of the glass panel 
	 * @return the max width between the window and the document to fix the width of the glass panel 
	 */
	private static int getMaxWidth() {
		return   Math.max(RootPanel.get().getOffsetWidth(),Window.getClientWidth()) + Window.getScrollLeft();
	}
	
	
	
	/**
	 * Displays a basic message in a dialog box.
	 * Note you can use the CSS class OptionPanel-errorMessage to style the given message
	 * @param message the error message to display.
	 */
	public static void showMessageDialog(String message) {
		OptionPanel panel = new OptionPanel();
		panel.setMessage(message);
		panel.setTypeMessage(OptionPanel.MESSAGE);
		displayOptionPanel(panel);
		
		
	}
	
	
	/**
	 * Displays an error message in a dialog box.
	 * Note you can use the CSS class OptionPanel-errorMessage to style the given message
	 * @param message the error message to display.
	 */
	public static void showErrorMessageDialog(String message) {
		OptionPanel panel = new OptionPanel();
		panel.setMessage(message);
		panel.setTypeMessage(OptionPanel.ERROR_MESSAGE);
		displayOptionPanel(panel);
	}
	
	
	/**
	 * Displays the <code>OptionPanel</code> in a PopupPanel.
	 * The popup is model, we use a mask to do it. 
	 * @param panel the panel to display
	 */
	private static void displayOptionPanel(final OptionPanel panel) {
		final PopupPanel popup = new PopupPanel(false,false) {
			
			public void show() {
				BodyElement body = Document.get().getBody();
		
		        int width = getMaxWidth();
		        int height = getMaxHeight();

				Style style = mask.getStyle();
		        style.setPropertyPx("width",width);
		        style.setPropertyPx("height",height);
				style.setProperty("opacity", "0.5");
				style.setProperty("filter","alpha(opacity=50)");
				style.setProperty( "zIndex",String.valueOf(panel.getZIndex()));
				Style popupStyle = getElement().getStyle();
				popupStyle.setProperty("zIndex", String.valueOf(panel.getZIndex()+1));
				
				body.appendChild(mask);
				super.show();

				


				Window.addWindowResizeListener(panel);
				
			
			}
			
		};
		popup.addPopupListener(panel);
		
		FormListener listener = new FormListener() {
			public void onSubmit(Form form) {
				popup.hide();
			}
			public void onCancel(Form form) {
				popup.hide();
			}
		};
		panel.initComponents();
		panel.addFormListener(listener);
		popup.setWidget(panel);
		popup.center();
	}
	
	/**
	 * Displays a confirm dialog. 
	 * Note you can use the CSS class OptionPanel-confirmMessage to set a style for the
	 * given message.  
	 * @param message a message to display in the dialog
	 * @param optionListener  a <code>FormListener</code> in order to get the value of the confirm dialog when the dialog is hiding
	 */
	public static void showConfirmDialog(String message,FormListener optionListener) {
		OptionPanel panel = new OptionPanel();
		panel.addFormListener(optionListener);
		panel.setMessage(message);
		panel.setTypeMessage(OptionPanel.CONFIRM_MESSAGE);
		displayOptionPanel(panel);
     }

	
	 /**
     * Resizes the mask. Note the given argument are not used	
     */
	public void onWindowResized(int arg0, int arg1) {
    	Style style = mask.getStyle();
		style.setPropertyPx("width",getMaxWidth());
		style.setPropertyPx("height",getMaxHeight());
		
	}

	/**
	 * Removes the mask when the dialog is closed.
	 * 
	 */
	public void onPopupClosed(PopupPanel sender, boolean autoClosed) {
		
        BodyElement body = Document.get().getBody();
        if ( body.isOrHasChild(mask)) {
        	body.removeChild(mask);
        }
		
		//remove the window resize listener
        Window.removeWindowResizeListener(this);
	}
	
}//end of class
