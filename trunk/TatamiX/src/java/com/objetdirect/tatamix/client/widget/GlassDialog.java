package com.objetdirect.tatamix.client.widget;




import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
/**
 * This widget aims to display a <code>DialogBox</code> with a glass panel behind it.
 * This dialog box is not modal by default, this is the glass panel which avoid the browser events
 * for the sub widget.
 * @author Vianney Grassaud
 * 
 *
 */
public class GlassDialog extends DialogBox implements ResizeHandler,CloseHandler<PopupPanel> {

	/** the glass panel */
	private DivElement mask;
	
	private FocusPanel widget;
	//layout for the widget
	private FlowPanel layout;
	
	private HandlerRegistration resizeRegistration;
	
	
	//the default zIndex
	private int zIndex = 6;

	/**
	 * Creates the dialog box. The dialog box is always modal
	 * @param autoHide set the auto hide enable or not
	 */
  public GlassDialog(boolean autoHide) {
	  this(autoHide,false);

  }


  /**
   * Creates a dialog
   * @param autoHide enable or not the auto hide
   * @param modal the dialog will be modal or not by default is not.
   */
  public GlassDialog(boolean autoHide,boolean modal) {
	  super(autoHide,modal);
	  
		mask = Document.get().createDivElement();
	    
		Style style = mask.getStyle();
		style.setProperty("backgroundColor", "#aaa");
		style.setProperty("position", "absolute");
		style.setProperty("top", "0px");
		style.setProperty("left", "0px");
		style.setProperty("visibility", "visible");
			
		
		addCloseHandler(this);
		layout = new FlowPanel();
		widget = new FocusPanel();
	    
    	layout.add(widget);
      
		super.setWidget(layout);
  }



  

  /**
   * Sets the zIndex for the glass panel.
   * @param zIndex a index in the zOrder
   */
  public void setZIndex(int zIndex) {
	  this.zIndex = zIndex;
  }
  
  /**
   * Returns the zIndex used by the glass panel
   * @return the zIndex used by the glass panel
   */
  public int getZIndex() {
	  return this.zIndex;
  }
  
  /**
   * Sets the widget to be displayed in the dialog box.
   * @param widget a widget
   */
  public void setWidget(Widget widget) {
      if ( this.widget.getWidget() != null) {
    	  this.widget.clear();
      }
	  this.widget.setWidget(widget);
  }

	/**
	 * Shows the dialog box with the glass panel behind
	 */
	public void show() {
		
        
		
		
		
		BodyElement body = Document.get().getBody();
		
        int width = getMaxWidth();
        int height = getMaxHeight();

		Style style = mask.getStyle();
        style.setPropertyPx("width",width);
        style.setPropertyPx("height",height);
		style.setProperty("opacity", "0.5");
		style.setProperty("filter","alpha(opacity=50)");
		style.setProperty( "zIndex",String.valueOf(getZIndex()));
		Style popupStyle = getElement().getStyle();
		popupStyle.setProperty("zIndex", String.valueOf(getZIndex()+1));
		
		body.appendChild(mask);
				
		
		super.show();

		resizeRegistration = Window.addResizeHandler(this);
		
		this.widget.setFocus(true);

	}

	/**
	 * Returns the max height between the window and the document to fix the height of the glass panel 
	 * @return the max height between the window and the document to fix the height of the glass panel 
	 */
	private int getMaxHeight() {
		return   Math.max(RootPanel.get().getOffsetHeight(),Window.getClientHeight()) + Window.getScrollTop();
	}

	/**
	 * Returns the max width between the window and the document to fix the width of the glass panel 
	 * @return the max width between the window and the document to fix the width of the glass panel 
	 */
	private int getMaxWidth() {
		return   Math.max(RootPanel.get().getOffsetWidth(),Window.getClientWidth()) + Window.getScrollLeft();
	}

	/**
	 * Hides the dialog is the parameter is equal to <code>false</code>
	 */
	public void setVisible(boolean b) {
		if ( !b) {
		  hide();
		}
		super.setVisible(b);
	}

    /**
     * Resizes the mask. Note the given argument are not used	
     */
	public void onResize(ResizeEvent event) {
		Style style = mask.getStyle();
		style.setPropertyPx("width",getMaxWidth());
		style.setPropertyPx("height",getMaxHeight());
	}

	/**
	 * Removes the mask when the dialog is closed.
	 * 
	 */
	public void onClose(CloseEvent<PopupPanel> event) {
		 BodyElement body = Document.get().getBody();
	        if ( body.isOrHasChild(mask)) {
	        	body.removeChild(mask);
	        }
			
			//remove the window resize listener
	        if ( resizeRegistration != null) {
	        	resizeRegistration.removeHandler();
	        }
	        
		}
	


}//end of class

