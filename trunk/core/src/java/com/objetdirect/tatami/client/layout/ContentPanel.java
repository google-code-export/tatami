package com.objetdirect.tatami.client.layout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.tools.ant.taskdefs.Java;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.AbstractDojo;
import com.objetdirect.tatami.client.DojoController;
import com.objetdirect.tatami.client.HasDojo;

public class ContentPanel extends SimplePanel implements DojoPanel {
	
	
	private JavaScriptObject dojoWidget;
	private Widget widget;
	protected List<Widget> children = new ArrayList<Widget>();
	private Map<String, Object> properties = new HashMap<String, Object>();
	
	public Widget asWidget(){
		return this;
	}
	
	public ContentPanel(Widget content){
		super();
		DojoController.getInstance().loadDojoWidget(this);
		defineContentPane();
		setWidget(content);
	}
	private void updateContent(){
		if(this.widget != null && dojoWidget != null){
			setContent(widget.getElement(),dojoWidget);
		}
	}
	
	private void resizeChildren(double width, double height){
		if(widget != null && width > 0 && height > 0){
			widget.setSize(width+"px", height+"px");
		}
	}
	
	private native JavaScriptObject defineContentPane()/*-{
		$wnd.dojo.declare("dojox.layout.TatamiContentPane",$wnd.dijit.layout.ContentPane,{
			resize : function(size){
				this.inherited(arguments);
				this.gwtWidget.@com.objetdirect.tatami.client.layout.ContentPanel::resizeChildren(DD)(this._contentBox.w,this._contentBox.h);
			}
		});
	}-*/;
	
	

	private native JavaScriptObject createDojoContentPane()/*-{
		var cpaneOptions ={gwtWidget:this};
		return  new $wnd.dojox.layout.TatamiContentPane(cpaneOptions);
	}-*/;

	public String getDojoName() {
		return "dijit.layout.ContentPane";
	}
	
	public Widget getWidget() {
		return widget;
	}

	public void setWidget(Widget w) {
		if(this.widget != null){
			detachChild(this.widget);
		}
		this.widget = w;
		updateContent();
	}
	
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


	
	public void doBeforeDestruction() {
	}

	protected native void attachChild(Widget widget)/*-{
		widget.@com.google.gwt.user.client.ui.Widget::onAttach()();
	}-*/;
	
	protected native void detachChild(Widget widget)/*-{
		widget.@com.google.gwt.user.client.ui.Widget::onDetach()();
	}-*/;
	
	private native void setContent(Element elem , JavaScriptObject  dojoWidget)/*-{
		dojoWidget.attr('content',elem);
	}-*/;


	public void free() {
		this.dojoWidget = null;
	}

	public JavaScriptObject getDojoWidget() {
		return dojoWidget;
	}

	public void onDojoLoad() {
		defineContentPane();
	}
	

	public void onAttach() {
		super.onAttach();
		createDojoWidget();
		doAfterCreation();
		updateContent();
	}
	
	protected native void dojoUpdateSize(JavaScriptObject dojoWidget)/*-{
		dojoWidget.resize();
	}-*/;

	@Override
	protected void doAttachChildren() {
		super.doAttachChildren();
	}
	
	@Override
	protected void doDetachChildren() {
		super.doDetachChildren();
	}
	
	public void onDetach() {
		doBeforeDestruction();
		DojoController.getInstance().destroy(dojoWidget);
		free();
		super.onDetach();
	}
	
	@Override
	public void add(Widget child) {
		super.add(child);
		setWidget(child);
	}

	public JavaScriptObject createDojoLayout() {
		return createDojoContentPane();
	}

	@Override
	public boolean remove(Widget arg0) {
		if(arg0 != widget){
			return false;
		}else{
			setWidget(null);
			return true;
		}
	}

	public void createDojoWidget()  {
		dojoWidget = createDojoLayout();
	}
	
	
	@Override
	public void setHeight(String height) {
		this.setHeight(height,true);
	}
	
	public void setHeight(String height , boolean resizeDojo) {
		super.setHeight(height);
		if(resizeDojo && dojoWidget != null){
			dojoUpdateSize(dojoWidget);
		}
	}

	@Override
	public void setSize(String width, String height) {
		setHeight(height,false);
		setWidth(width,false);
		if(dojoWidget != null){
			dojoUpdateSize(dojoWidget);
		}
	}

	@Override
	public void setWidth(String width) {
		this.setHeight(width,true);
	}
	
	public void setWidth(String width , boolean resizeDojo) {
		super.setWidth(width);
		if(resizeDojo && dojoWidget != null){
			dojoUpdateSize(dojoWidget);
		}
	}

	public void doAfterCreation() {
		for(String key : properties.keySet()){
			dojoSetProperty(key, properties.get(key), dojoWidget);
		}
		DojoController.startup(this);
	}
	
	private native void dojoSetProperty(String propertyName,Object propertyValue,JavaScriptObject dojoWidget)/*-{
		dojoWidget[propertyName] = propertyValue;
	}-*/;
	
	public void addProperty(String propertyName , Object propertyValue){
		properties.put(propertyName,propertyValue);
		if(dojoWidget != null){
			dojoSetProperty(propertyName,propertyValue,dojoWidget);
		}
	}
	
	public void removeProperty(String propertyName){
		if(properties.remove(propertyName)!=null){
			if(dojoWidget != null){
				dojoSetProperty(propertyName,null,dojoWidget);
			}
		}
	}
	
	public Map<String,Object> getProperties(){
		return this.properties ;
	}
	
}
