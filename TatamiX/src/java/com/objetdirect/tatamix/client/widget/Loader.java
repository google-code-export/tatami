package com.objetdirect.tatamix.client.widget;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 *A widget in order to display some text and a icon during a loading.
 * The widget which is loading or waiting some data will
 *be removed by this widget. At the end of process, when the
 * method <code>hide()</code>is called, the widget will
 * be replaced at the same position.
 *
 * @author Vianney
 *
 */
public class Loader extends Composite  implements HasText {

    
	private Image image;
    private FlowPanel panel;
    private Label label;
    private Widget widgetLoading;
    private int widgetIndex;

    /**
     * Creates the loader.
     *
     */
	public Loader() {
      super();
	  panel = new FlowPanel();
	  initWidget(panel);
	  setOpacity(this,0.8f);
	  initComponents();
	}


    /**
     * Sets the opacity of a widget
     * @param widget the widget to set the opacity
     * @param opacity the value of the transparency, a lower value makes the makes the element more transparent.
     */
	private void setOpacity(Widget widget,float opacity) {
		 DOM.setStyleAttribute(widget.getElement(), "opacity",String.valueOf(opacity));
		 DOM.setStyleAttribute(widget.getElement(), "filter","alpha(opacity="+opacity*100+ ")");
	}


	/**
	 * Inits the graphical components
	 *
	 */
	private void initComponents() {
		 image = new Image();
		 label = new Label();
		 label.setText("Loading...");
		 label.setStylePrimaryName("loadingText");
		 image.setUrl(GWT.getModuleBaseURL() + "/images/loading.gif");
		 panel.add(image);
		 panel.add(label);

	}


	/**
	 * Sets the widget which is loading or waiting some data.
	 * @param widget a widget included in a <code>FlowPanel</code> container
	 */
	public void setWidgetLoading(Widget widget) {
		widgetLoading = widget;

	}

	/**
	 * Sets the text during the loading
	 * @param text some text to display
	 */
	public void setText(String text) {
		this.label.setText(text);
	}

    /**
     * Returns the text shown during the loading
     * @return the text shown during the loading
     */
	public String getText() {
    	 return this.label.getText();
    }


	/**
	 * Sets the url of the image shown during the loading
	 * @param src the URL of an image
	 */
	public void setIcon(String src) {
		this.image.setUrl(src);
	}


	/**
	 * Returns the url of the icon
	 * @return the url of the icon
	 */
	public String getIcon() {
		return this.image.getUrl();
	}

     /**
      * Displays the text and the icon during the loading.
      * The widget which is loading or waiting is removed from its parent by this
      * <code>Loader</code>
      *
      */
	public void display() {
	    if ( widgetLoading != null && widgetLoading.getParent() != null) {
	    	if ( !(widgetLoading.getParent() instanceof FlowPanel) ) {
				throw new IllegalArgumentException("The widget should have a FlowPanel as parent");
			}
	    	FlowPanel parent = (FlowPanel)widgetLoading.getParent();
	    	widgetIndex = parent.getWidgetIndex(widgetLoading);
	    	parent.remove(widgetIndex);
	    	parent.insert(this,widgetIndex);

	    }
	}

    /**
     * Hides the icon and the text shown during the loading.
     * The widget which was loading or waiting data is re-inserted to its parent.
     *
     */
	public void hide() {
		  if ( widgetLoading != null && getParent() != null) {
			  FlowPanel parent = (FlowPanel)getParent();
			  parent.remove(this);
			  parent.insert(widgetLoading,widgetIndex);
		  }
	}


}//end of class
