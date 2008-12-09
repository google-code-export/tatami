package com.objetdirect.tatami.client.layout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;
import com.objetdirect.tatami.client.DojoController;

/**
 * This class facilitates the integration of dojo's layouts
 * by conveniently overriding the ComplexPanel methods. 
 * 
 * It only accepts other DojoPanel, but subclasses can be more 
 * restrictive if needed (most dojo layouts only accepts ContentPanel as
 * a child).
 * 
 * @author rdunklau
 *
 */
public abstract class AbstractDojoComplexPanel extends ComplexPanel implements DojoPanel{

	private JavaScriptObject dojoWidget;
	
	public AbstractDojoComplexPanel(){
		setElement(DOM.createDiv());
		DojoController.getInstance().loadDojoWidget(this);
	}
	
	public void createDojoWidget() throws Exception {
		dojoWidget = createDojoLayout();
		for(Widget widget : getChildren()){
			DojoPanel panel = ((DojoPanel)widget);
			dojoAddChild(dojoWidget, panel.getDojoWidget() , null );
		}
	}

	/**
	 * Adds a dojoWidget to a dojolayout
	 * 
	 * This method is called after dojo widget creation (which happens just
	 * after the attachment) and when adding widgets to the panel.
	 * It asks dojo to manage the widget attachment. Hence, no DOM nodes are managed
	 * by GWT when adding a widget to a dojo complex panel.
	 * 
	 * @param dojoLayout : the layout to which the dojopanel must be added
	 * @param dojoPanel: the dojo panel which must be added to the dojoLayout
	 * @param index: an index position to insert the child widgets, which is not necessarily taken 
	 * into account by dojo.
	 */
	protected native void dojoAddChild(JavaScriptObject dojoLayout, JavaScriptObject dojoPanel , Integer index)/*-{
		if(index){
			dojoLayout.addChild(dojoPanel , index);
		}else{
			dojoLayout.addChild(dojoPanel , index);
		}
	}-*/;
	
	/**
	 * Removes a dojoWidget from a dojoLayout. 
	 * 
	 * @param dojoLayout
	 * @param dojoPanel
	 */
	protected native void dojoRemoveChild(JavaScriptObject dojoLayout, JavaScriptObject dojoPanel)/*-{
		dojoLayout.removeChild(dojoPanel);
	}-*/;
	
	/**
	 * This method must be implemented. It must create a DojoLayout, and return it as 
	 * a javascript object
	 * 
	 * @return: the dojolayout widget which has been created
	 */
	public abstract JavaScriptObject createDojoLayout();		
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Panel#add(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public void add(Widget child) {
		insert(child,null,-1,false);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.ComplexPanel#remove(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean remove(Widget child) {
		//We override the remove method from complexpanel, since
		//GWT must not manipulate the dom in the 
		//dojo layout widgets
		if (child.getParent() != this) {
		   return false;
		}
		orphan(child);
		getChildren().remove(child);
		dojoRemoveChild(dojoWidget, ((DojoPanel)child).getDojoWidget());
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.ComplexPanel#add(com.google.gwt.user.client.ui.Widget, com.google.gwt.user.client.Element)
	 */
	@Override
	protected void add(Widget child, Element container) {
		add(child);
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Panel#doAttachChildren()
	 */
	@Override
	protected void doAttachChildren() {
		super.doAttachChildren();
	}


	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onAttach()
	 */
	@Override
	public void onAttach() {
		super.onAttach();
		DojoController.getInstance().constructDojoWidget(this, this);
	}

	@Override
	public void onDetach() {
		DojoController.getInstance().destroyDojoWidget(this,this);
		super.onDetach();
	}

	public void doAfterCreation() {
		DojoController.startup(this);
	}
	
	/**
	 * This method acts as a delegate for the dojo resize method.
	 * The resize method will resize the widget accordingly to changes made to parents.
	 * It will also propagate the resize to its children.
	 * 
	 * @param dojoWidget : the dojowidget wich must be resized.
	 */
	protected native void dojoUpdateSize(JavaScriptObject dojoWidget)/*-{
		dojoWidget.resize();
	}-*/;

	public void doBeforeDestruction() {
	}

	public void free() {
		dojoWidget = null;
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#getDojoName()
	 */
	public abstract String getDojoName();
	

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
	}

	@Override
	public void setHeight(String height) {
		this.setHeight(height,true);
	}
	
	/**
	 * @param height: the new height in CSS units
	 * @param resizeDojo: whether we should ask the underlying dojo
	 * layout object to adapt its size.
	 */
	private void setHeight(String height , boolean resizeDojo) {
		super.setHeight(height);
		if(resizeDojo && dojoWidget != null){
			dojoUpdateSize(dojoWidget);
		}
	}

	@Override
	public void setSize(String width, String height) {
		//This method has been overriden in order to only ask the dojo
		//widget to resize itself
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
	
	/**
	 * @param width: the new height in CSS units
	 * @param resizeDojo: whether we should ask the underlying dojo
	 * layout object to adapt its size.
	 */
	private void setWidth(String width , boolean resizeDojo) {
		super.setWidth(width);
		if(resizeDojo && dojoWidget != null){
			dojoUpdateSize(dojoWidget);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.ComplexPanel#insert(com.google.gwt.user.client.ui.Widget, com.google.gwt.user.client.Element, int, boolean)
	 */
	@Override
	protected void insert(Widget child, Element container, int beforeIndex,
		      boolean domInsert) {
		//This method was overriden to prevent GWT from managing the DOM tree beneath 
		//the dojo layout widget. 
		//The dojowidget object is extracted from the DojoPanel, and added directly 
		//to the dojolayout object represtend by the current AbstractDojoComplexPanel
		DojoPanel actualChild = null;
		if(!(child instanceof DojoPanel)){
			throw new IllegalArgumentException("A dojo complex panel can only accept panel. You should wrap your widget in a com.objetdirect.tatami.layout.ContentPanel");
		}else{
	    	actualChild = (DojoPanel) child;
	    }
		child.removeFromParent();
		if(!domInsert){
			getChildren().add(actualChild.asWidget());
		}else{
			getChildren().insert(actualChild.asWidget(),beforeIndex);
		}
		if(dojoWidget != null){
			if(domInsert){
				dojoAddChild(dojoWidget, actualChild.getDojoWidget(),beforeIndex);
			}else{
				dojoAddChild(dojoWidget, actualChild.getDojoWidget(),null);
			}
		}
		adopt(child);
	}
	
}
