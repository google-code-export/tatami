package com.objetdirect.tatami.client.layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.DojoAfterCreationEventsSource;
import com.objetdirect.tatami.client.DojoAfterCreationListener;
import com.objetdirect.tatami.client.DojoController;
import com.objetdirect.tatami.client.HasAdaptiveSize;

/**
 * This class wraps a dojo ContentPanel.
 * Its main purpose is to allow GWT widget to be embedded in a Dojo layout
 * widget. Since dojo layouts only accepts dojo widgets, it is a convenient way 
 * to wrap any gwt widget for dojo. 
 * 
 * This widget is very different from others in Tatami, since the GWT div node is 
 * not used when this panel is added to a dojo panel.
 * Instead, all set sizes are applied to the dojo domnode.
 * If you have to work with the DOM element,use getDojoElement() instead of getElement() 
 * 
 * 
 * 
 * @author rdunklau
 *
 */
public class ContentPanel extends SimplePanel implements DojoPanel,HasAdaptiveSize,DojoAfterCreationEventsSource {
	
	
	private JavaScriptObject dojoWidget;
	private Widget widget;
	protected List<Widget> children = new ArrayList<Widget>();
	private Map<String, Object> properties = new HashMap<String, Object>();
	private String width;
	private String height;
	private Element dojoNode;
	private List<DojoAfterCreationListener> afterCreationListeners;
	
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.layout.DojoPanel#asPanel()
	 */
	public Panel asPanel(){
		return this;
	}
	
	/**
	 * Construct a content panel from a single widget
	 * @param content
	 */
	public ContentPanel(Widget content){
		super();
		DojoController.getInstance().loadDojoWidget(this);
		defineContentPane();
		setWidget(content);
	}
	
	public Element getDojoElement(){
		return dojoNode;
	}
	
	/**
	 * Private method used to internally change the dojowidget content
	 */
	private void updateContent(){
		if(this.widget != null && dojoWidget != null){
			setContent(widget.getElement(),dojoWidget);
		}
	}
	
	/**
	 * This method is hooked to dojo resize method, and is meant to propagate
	 * the resize operation to children that need it.
	 * 
	 * 
	 * 
	 * @param width
	 * @param height
	 */
	private void resizeChildren(double width, double height){
		if(widget instanceof HasAdaptiveSize && width > 0 && height > 0){
			((HasAdaptiveSize)widget).adaptSize();
		}
	}
	
	
	/**
	 * Internal method used to extend :
	 * 	- dojo's contentpanel to hook the resizeChildren method to the
	 * dojo resize method
	 *	- dojo's HTML Setter in order to not destroy any dom node contained in 
	 *	the panel
	 * @return
	 */
	private native JavaScriptObject defineContentPane()/*-{
		$wnd.dojo.declare("dojox.layout.TatamiContentPane",$wnd.dijit.layout.ContentPane,{
			resize : function(size){
				this.inherited(arguments);
				this.gwtWidget.@com.objetdirect.tatami.client.layout.ContentPanel::resizeChildren(DD)(this._contentBox.w,this._contentBox.h);
			},
			destroy: function(preserveDom){
				this._contentSetter.empty();
				this.inherited(arguments);
			}
		});
		$wnd.dojo.declare("dojox.layout.TatamiContentPaneHtmlSetter",$wnd.dojo.html._ContentSetter,{
			empty: function() {
				if(this.node && this.node.hasChildNodes()){
					for(var i=0;i< this.node.childNodes.length;i++){
						this.node.removeChild(this.node.childNodes[i]);
					}
				}
			}
		});
	}-*/;
	
	

	/**
	 * Creates the content pane
	 * 
	 * 
	 * @return
	 */
	private native JavaScriptObject createDojoContentPane()/*-{
		var cpaneOptions ={gwtWidget:this };
		var contentPane = new $wnd.dojox.layout.TatamiContentPane(cpaneOptions);
		contentPane._contentSetter = new $wnd.dojox.layout.TatamiContentPaneHtmlSetter({node:contentPane.containerNode});
		return contentPane;
	}-*/;

	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#getDojoName()
	 */
	public String getDojoName() {
		return "dijit.layout.ContentPane";
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.SimplePanel#getWidget()
	 */
	public Widget getWidget() {
		return widget;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.SimplePanel#setWidget(com.google.gwt.user.client.ui.Widget)
	 */
	public void setWidget(Widget w) {
		if(this.widget != null){
			remove(this.widget);
		}
		w.removeFromParent();
		this.widget = w;
		updateContent();
		adopt(widget);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.SimplePanel#iterator()
	 */
	public Iterator<Widget> iterator() {
	    // Return a simple iterator that enumerates the 0 or 1 elements in this
	    // panel.
	    return new Iterator<Widget>() {
	      boolean hasElement = widget != null;
	      Widget returned = null;

	      public boolean hasNext() {
	        return hasElement;
	      }

	      public Widget next() {
	        if (!hasElement || (widget == null)) {
	          throw new NoSuchElementException();
	        }
	        hasElement = false;
	        return (returned = widget);
	      }

	      public void remove() {
	        if (returned != null) {
	          ContentPanel.this.remove(returned);
	        }
	      }
	    };
	  }


	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#doBeforeDestruction()
	 */
	public void doBeforeDestruction() {
	}

	/**
	 * Internal method used to update dojo Content
	 * 
	 * @param elem
	 * @param dojoWidget
	 */
	private native void setContent(JavaScriptObject elem , JavaScriptObject  dojoWidget)/*-{
		dojoWidget.attr('content',elem);
	}-*/;


	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#free()
	 */
	public void free() {
		this.dojoWidget = null;
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#getDojoWidget()
	 */
	public JavaScriptObject getDojoWidget() {
		return dojoWidget;
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#onDojoLoad()
	 */
	public void onDojoLoad() {
		defineContentPane();
	}
	

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onAttach()
	 */
	public void onAttach() {
		if(dojoWidget == null){
			createDojoWidget();
			doAfterCreation();
		}
		super.onAttach();
		DojoController.startup(this);
	}
	
	/**
	 * Ask for dojo's content pane to resize itself
	 * 
	 * @param dojoWidget
	 */
	protected native void dojoUpdateSize(JavaScriptObject dojoWidget)/*-{
		dojoWidget.resize();
	}-*/;

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Panel#doAttachChildren()
	 */
	@Override
	protected void doAttachChildren() {
		updateContent();
		super.doAttachChildren();
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Panel#doDetachChildren()
	 */
	@Override
	protected void doDetachChildren() {
		super.doDetachChildren();
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onDetach()
	 */
	public void onDetach() {
		super.onDetach();
		if(dojoWidget != null){
			DojoController.getInstance().destroy(dojoWidget);
		}
		free();
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.SimplePanel#add(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public void add(Widget child) {
		super.add(child);
		setWidget(child);
	}

	/**
	 * @return a javascript object representing the dojo
	 * content pane widget
	 */
	public JavaScriptObject createDojoLayout() {
		return createDojoContentPane();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.SimplePanel#remove(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean remove(Widget arg0) {
		if(arg0 != widget){
			return false;
		}else{
			orphan(widget);
			this.widget = null;
			updateContent();
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#createDojoWidget()
	 */
	public void createDojoWidget()  {
		dojoWidget = createDojoLayout();
		dojoNode = DojoController.getInstance().getDomNode(this);
		DOM.setStyleAttribute(dojoNode, "width", width);
		DOM.setStyleAttribute(dojoNode, "height", height);
	}
	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#setHeight(java.lang.String)
	 */
	@Override
	public void setHeight(String height) {
		this.setHeight(height,true);
	}
	
	/**
	 * Resize the height of the widget. 
	 * 
	 * @param height: the new widget's height
	 * @param resizeDojo: whether the widget should ask dojo's widget to resize
	 * itself
	 */
	public void setHeight(String height , boolean resizeDojo) {
		super.setHeight(height);
		this.height = height;
		if(dojoNode != null){
			DOM.setStyleAttribute(dojoNode, "height", height);
		}
		if(resizeDojo && dojoWidget != null){
			dojoUpdateSize(dojoWidget);
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#setSize(java.lang.String, java.lang.String)
	 */
	@Override
	public void setSize(String width, String height) {
		setHeight(height,false);
		setWidth(width,false);
		if(dojoWidget != null){
			dojoUpdateSize(dojoWidget);
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#setWidth(java.lang.String)
	 */
	@Override
	public void setWidth(String width) {
		this.setWidth(width,true);
	}
	
	/**
	 * 
	 * 
	 * @param width : the new widget's width
	 * @param resizeDojo: whether the widget should ask dojo's widget to resize
	 * itself
	 * @param resizeDojo
	 */
	public void setWidth(String width , boolean resizeDojo) {
		super.setWidth(width);
		this.width = width;
		if(dojoNode != null){
			DOM.setStyleAttribute(dojoNode, "width", width);
		}
		if(resizeDojo && dojoWidget != null){
			dojoUpdateSize(dojoWidget);
		}
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#doAfterCreation()
	 */
	public void doAfterCreation() {
		for(String key : properties.keySet()){
			dojoSetProperty(key, properties.get(key), dojoWidget);
		}
		if(afterCreationListeners != null){
			for(DojoAfterCreationListener listener : afterCreationListeners){
				listener.dojoAfterCreation(this);
			}
		}
	}
	
	/**
	 * Set a property to the content panel.
	 * Containers may need to set properties on a panel, that dojo's container itself 
	 * will read.
	 * Example of properties that may be set : minimum size, region, splitters...
	 * 
	 * @param propertyName
	 * @param propertyValue
	 * @param dojoWidget
	 */
	private native void dojoSetProperty(String propertyName,Object propertyValue,JavaScriptObject dojoWidget)/*-{
		dojoWidget[propertyName] = propertyValue;
	}-*/;
	
	/**
	 * Set a property to the content panel.
	 * Containers may need to set properties on a panel, that dojo's container itself 
	 * will read.
	 * Example of properties that may be set : minimum size, region, splitters...
	 * 
	 * @param propertyName
	 * @param propertyValue
	 */
	public void addProperty(String propertyName , Object propertyValue){
		properties.put(propertyName,propertyValue);
		if(dojoWidget != null){
			dojoSetProperty(propertyName,propertyValue,dojoWidget);
		}
	}
	
	/**
	 * Removes a property from a dojowidget content panel
	 * 
	 * @param propertyName
	 */
	public void removeProperty(String propertyName){
		if(properties.remove(propertyName)!=null){
			if(dojoWidget != null){
				dojoSetProperty(propertyName,null,dojoWidget);
			}
		}
	}
	
	/**
	 * Returns the properties set to the dojowidget
	 * 
	 * @return
	 */
	public Map<String,Object> getProperties(){
		return this.properties ;
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasAdaptiveSize#adaptSize()
	 */
	public void adaptSize() {
		if(dojoWidget != null){
			dojoUpdateSize(dojoWidget);
		}
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.DojoAfterCreationEventsSource#addAfterCreationListener(com.objetdirect.tatami.client.DojoAfterCreationListener)
	 */
	public void addAfterCreationListener(DojoAfterCreationListener listener) {
		if(afterCreationListeners == null){
			afterCreationListeners = new ArrayList<DojoAfterCreationListener>();
		}
		afterCreationListeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.DojoAfterCreationEventsSource#removeAfterCreationListener(com.objetdirect.tatami.client.DojoAfterCreationListener)
	 */
	public void removeAfterCreationListener(DojoAfterCreationListener listener) {
		if(afterCreationListeners == null){
			return ;
		}
		afterCreationListeners.remove(listener);
	}
	
}
