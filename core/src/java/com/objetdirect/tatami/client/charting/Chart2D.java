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
 * Authors: Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s): 
 */
package com.objetdirect.tatami.client.charting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.DojoAfterCreationEventsSource;
import com.objetdirect.tatami.client.DojoAfterCreationListener;
import com.objetdirect.tatami.client.DojoController;
import com.objetdirect.tatami.client.HasAdaptiveSize;
import com.objetdirect.tatami.client.HasDojo;
import com.objetdirect.tatami.client.JSHelper;
import com.objetdirect.tatami.client.charting.effects.Effect;

/**
 * This class wraps a dojo chart2D
 * 
 * 
 * 
 * @author rdunklau
 * 
 *
 */
public class Chart2D extends Widget implements HasDojo, DojoAfterCreationEventsSource,HasAdaptiveSize  {

	/**
	 * The plots which must be drawn on this chart
	 */
	private List<Plot> plots = new ArrayList<Plot>();
	
	/**
	 * Axes : the axes 
	 */
	private List<Axis> axes = new ArrayList<Axis>();
	
	private Map<String,Serie> series = new HashMap<String,Serie>();
	
	private JavaScriptObject dojoWidget;
	
	private String theme ;

	private Axis defaultXAxis;
	
	private Axis defaultYAxis;
	
	private Collection<DojoAfterCreationListener> afterCreationListeners = new ArrayList<DojoAfterCreationListener>();
	
	private Element dojoElem;

	/**
	 * Initializes the chart with a certain size 
	 * 
	 * @param width: the chart width in css units
	 * @param height: the chart the chart width in css units in css units
	 */
	public Chart2D(String width,String height){
		DojoController.getInstance().loadDojoWidget(this);
		defineTatamiChart();
		setElement(DOM.createDiv());
		dojoElem = DOM.createDiv();
		DOM.setStyleAttribute(dojoElem, "width", "100%");
		DOM.setStyleAttribute(dojoElem, "height", "100%");
		DOM.appendChild(getElement(),dojoElem);
		setSize(width, height);
	}
	
	/**
	 * Initializes the chart with a size set to 100%,100%
	 */
	public Chart2D(){
		this("100%","100%");
	}
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#createDojoWidget()
	 */
	public void createDojoWidget() throws Exception {
		dojoWidget = createDojoChart(dojoElem);
		refreshChart();
	}

	/**
	 * Internal method used to extend the resize function, in order to accept numbers too.
	 * @return
	 */
	private native JavaScriptObject defineTatamiChart()/*-{
		$wnd.dojo.declare("dojox.charting.TatamiChart2D",$wnd.dojox.charting.Chart2D,{
			resize: function(width, height){
				var box;
				if(width && height){
					box = {w: width, h: height};
					$wnd.dojo.marginBox(this.node, box);
				}else if(width){
					box = width;
					$wnd.dojo.marginBox(this.node, box);
				}else{
					box = $wnd.dojo.marginBox(this.node);
				}
				this.surface.setDimensions(box.w+"", box.h+"");
				this.dirty = true;
				this.coords = null;
				return this.render();
			}
		});
	}-*/;
	
	/**
	 * Internal method used to create the dojo chart
	 * 
	 * @param elem: the element used to generate the chart
	 * @return
	 */
	private native JavaScriptObject createDojoChart(Element elem)/*-{
		var chart = new $wnd.dojox.charting.TatamiChart2D(elem);
		return chart;
	}-*/;
	
	/**
	 * Internal method used to update a serie in  the dojo chart object
	 * Warning: the serie is not fully updated, only its data is changed.
	 * If you want to update the whole serie, remove it then add it.
	 * 
	 * @param seriesName: the series name, as dojo knows it (use serie.getName())
	 * @param data: the new data
	 * @param dojoWidget: the JavaScript object representing the chart
	 */
	private native void updateDojoSeries(String seriesName, JavaScriptObject data, JavaScriptObject dojoWidget)/*-{
		dojoWidget.updateSeries(seriesName,data);
	}-*/;
	
	/**
	 * Internal method used to add a plot to the dojo chart object
	 * 
	 * @param name: the plot name (gotten from plot.getName())
	 * @param options: a javascript object containing the plot options (plot type etc)
	 * @param dojoWidget: the dojo chart object
	 */
	private native void addDojoPlot(String name , JavaScriptObject options , JavaScriptObject dojoWidget)/*-{
		dojoWidget.addPlot(name,options);
	}-*/;
	
	/**
	 * Internal method used to remove a plot from the dojo chart object
	 * 
	 * @param name: the plot name (gotten from plot.getName())
	 * @param dojoWidget: the dojo chart object
	 */
	private native void removeDojoPlot(String name , JavaScriptObject dojoWidget)/*-{
	dojoWidget.removePlot(name);
}-*/;
	
	/**
	 * Internal method used to add an axis to the dojo chart object
	 * 
	 * @param name: the axis name (gotten from axis.getName())
	 * @param options: a javascript object containing the axis options (axis type etc)
	 * @param dojoWidget: the dojo chart object
	 */
	private native void addDojoAxis(String name , JavaScriptObject options , JavaScriptObject dojoWidget)/*-{
		dojoWidget.addAxis(name,options);
	}-*/;
	
	/**
	 * Internal method used to add a Serie to the dojo chart object
	 * 
	 * @param name: the serie's name, gotten from serie.getName()
	 * @param data: a javascript representation of this serie's data
	 * @param options: the series options (like markers, stroke etc)
	 * @param dojoWidget: the dojo chart object
	 */
	private native void addDojoSerie(String name ,  JavaScriptObject data , JavaScriptObject
			options , JavaScriptObject dojoWidget)/*-{
		dojoWidget.addSeries(name,data,options);
	}-*/;
	
	/**
	 * Internal method used to remove a Serie from the dojo chart object
	 * 
	 * @param name: the serie's name
	 * @param dojoWidget: the dojo chart object
	 */
	private native void removeDojoSerie(String name , JavaScriptObject dojoWidget)/*-{
		dojoWidget.removeSeries(name);
	}-*/;
	
	/**
	 * Internal method used to set a charting theme to the chart
	 * 
	 * @param dojoWidget: the dojo chart object
	 * @param theme: the theme name
	 */
	public native void dojoSetTheme(JavaScriptObject dojoWidget, String theme)/*-{
		dojoWidget.setTheme($wnd.dojo.getObject("dojox.charting.themes."+theme));
	}-*/;
	
	/**
	 * Re-render the dojowidget, as it is
	 * 
	 * @param dojoWidget: the dojo chart object
	 */
	private native void dojoRender(JavaScriptObject dojoWidget)/*-{
		dojoWidget.render();
	}-*/;
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#getDojoName()
	 */
	public String getDojoName() {
		return "dojox.charting.Chart2D";
	}
	
	/**
	 * @return the plots to draw on this chart
	 */
	public List<Plot> getPlots(){
		return plots;
	}
	
	/**
	 * @param plots: the plots to draw on this chart
	 */
	public void setPlots(List<Plot> plots) {
		this.plots = plots;
	}
	
	/**
	 * Adds a plot to this chart. It will be drawn the next time refreshChart is called.
	 * @param plot : a plot to add
	 */
	public void addPlot(Plot<?> plot){
		this.plots.add(plot);
	}
	
	
	/**
	 * Internal method used to destroy the javascript objects associated to a plot.
	 * 
	 * @param plot
	 */
	private void destroyPlot(Plot<?> plot){
		for(Effect effect : plot.getEffects()){
			effect.destroyEffect();
		}
	}
	
	/**
	 * Sets a dojo theme to the chart
	 * @see Themes
	 * 
	 * @param theme: the theme name
	 */
	public void setTheme(String theme){
		DojoController.getInstance().require("dojox.charting.themes."+theme);
		this.theme = theme;
	}
	
	/**
	 * Sets an axis as the default x axis for this chart.
	 * The default XAxis is used for all plots which does not have
	 * their own xaxis
	 * 
	 * @param axis
	 */
	public void setDefaultXAxis(Axis axis){
		axis.setName("x");
		defaultXAxis = axis;
		axes.add(axis);
	}
	
	/**
	 * Sets an axis as the default y axis for this chart.
	 * The default YAxis is used for all plots which does not have
	 * their own yaxis
	 * 
	 * @param axis
	 */
	public void setDefaultYAxis(Axis axis){
		axis.setName("y");
		defaultYAxis = axis;
		axes.add(axis);
	}
	
	/**
	 * Internal method
	 * Adds an axis with the name "x" or "y" will be used as a default x or y axis,
	 * whereas another axis has a name which plots will refer to
	 * 
	 * @param axis
	 */
	private void addAxis(Axis axis){
		if(!axes.contains(axis)){
			axes.add(axis);
		}
	}
	
	/**
	 * Renders the chart, applying all previous modifications that occured
	 * on axes, series or plots
	 */
	public void refreshChart(){
		if(dojoWidget != null){
			//Initializing a newSeries map, in order to "clean" the old series
			Map<String,Serie> newSeries = new HashMap<String,Serie>();
			for (Plot<?> plot : plots) {
				//Since we do not want to add twice the same plot to the
				//dojo chart,the plot is first removed from the dojo chart. 
				removeDojoPlot(plot.getName(), dojoWidget);
				Map<String,Object> options = plot.getOptions();
				
				//Adding the axes options to the plot options
				if(plot.getXAxis() != null){
					addAxis(plot.getXAxis());
					options.put("hAxis", plot.getXAxis().getId());
				}
				if(plot.getYAxis() != null){
					addAxis(plot.getYAxis());
					options.put("vAxis", plot.getYAxis().getId());
				}
				
				//Adding the plot to the dojo chart, using its name and its options
				addDojoPlot(plot.getName(),JSHelper.convertObjectToJSObject(plot.getOptions()), dojoWidget);
				
				//Adding this plot's series to the new series
				for(Serie<?> serie : plot.getSeries()){
					serie.getOptions().put("plot",plot.getName());
					newSeries.put(serie.getName(),serie);
				}
				
				//Initilazing effects
				for(Effect effect : plot.getEffects()){
					effect.initEffect(this, plot);
				}
			}
			
			//We remove all old series before adding the new ones.
			Set<String> oldKeys = series.keySet();
			for(String key : oldKeys){
				removeDojoSerie(series.get(key).getName(),dojoWidget);
			}
			
			//Adding the new series 
			Set<String> newKeys = newSeries.keySet();
			for(String key : newKeys){
				Serie serie = newSeries.get(key);
				addDojoSerie(serie.getName(),JSHelper.convertObjectToJSObject(serie.getData()),JSHelper.convertObjectToJSObject(serie.getOptions()),dojoWidget);
			}
			//Adding the axis to the chart
			for (Axis axis : axes) {
				addDojoAxis(axis.getId(), JSHelper.convertObjectToJSObject(axis.getOptions()), dojoWidget);
			}
			//Setting the theme if needed
			if(theme != null){
				dojoSetTheme(dojoWidget, theme);
			}
			series = newSeries;
			
			//Finally, we ask dojo to render the chart
			dojoRender(dojoWidget);
		}
	}


	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#doAfterCreation()
	 */
	public void doAfterCreation() {
		for (DojoAfterCreationListener listener : afterCreationListeners) {
			listener.dojoAfterCreation(this);
		}
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#doBeforeDestruction()
	 */
	public void doBeforeDestruction() {
		
	}

	/**
	 * Removes a plot
	 * 
	 * @param plot: the plot to remove
	 */
	public void removePlot(Plot<?> plot){
		if(plots.remove(plot)){
			removeDojoPlot(plot.getName(), dojoWidget);
			destroyPlot(plot);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasDojo#free()
	 */
	public void free() {
		for (Iterator<Plot> iterator = plots.iterator(); iterator.hasNext();) {
			Plot<?> plot =  iterator.next();
			destroyPlot(plot);
			iterator.remove();
		}
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
		
	}
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onAttach()
	 */
	@Override
	public void onAttach(){
		try {
			createDojoWidget();
			doAfterCreation();
			DojoController.getInstance().setGWTWidget(dojoWidget, this);
			adaptSize();
		} catch (Exception e) {
			GWT.log("ERROR : ", e);
		}
		super.onAttach();
	}
	 
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onDetach()
	 */
	@Override
	public void onDetach(){
		doBeforeDestruction();
		DojoController.getInstance().setGWTWidget(dojoWidget, null);
		DojoController.getInstance().destroy(dojoWidget);
		free();
		super.onDetach();
	}
	
	
	/**
	 * Adds a background grid to the chart.
	 * 
	 * 
	 * @param hMajorLines: displays or not the horizontal major lines (aligned on major ticks)
	 * @param hMinorLines: displays or not the horizontal minor lines (aligned on minor ticks)
	 * @param vMajorLines: displays or not the vertical major lines (aligned on major ticks)
	 * @param vMinorLines: displays or not the vertical minor lines (aligned on minor ticks)
	 * @return
	 */
	public Plot<?> addGrid(Boolean hMajorLines,Boolean hMinorLines,Boolean vMajorLines,Boolean vMinorLines){
		Plot<?> gridPlot = new Plot<Object>(Plot.PLOT_TYPE_GRID);
		gridPlot.setGridHMajorLines(hMajorLines);
		gridPlot.setGridHMinorLines(hMinorLines);
		gridPlot.setGridVMajorLines(vMajorLines);
		gridPlot.setGridVMinorLines(vMinorLines);
		gridPlot.setXAxis(defaultXAxis);
		gridPlot.setYAxis(defaultYAxis);
		addPlot(gridPlot);
		return gridPlot;
	}

	/**
	 * Updates a serie's data on the chart.
	 * WARNING: this method only updates the series data. If the serie's options
	 * have changed, call refreshChart instead.
	 * 
	 * @param serieToUpdate
	 */
	public void updateSerie(Serie<?> serieToUpdate){
		updateDojoSeries(serieToUpdate.getName(),JSHelper.convertObjectToJSObject(serieToUpdate.getData()),dojoWidget);
		dojoRender(dojoWidget);
	}
	
	/**
	 * Updates an array of series at one time.
	 * @param seriesToUpdate
	 */
	public void updateSeries(Serie[] seriesToUpdate){
		for (Serie<?> serie : seriesToUpdate) {
			updateDojoSeries(serie.getName(),JSHelper.convertObjectToJSObject(serie.getData()),dojoWidget);
		}
		dojoRender(dojoWidget);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.DojoAfterCreationEventsSource#addAfterCreationListener(com.objetdirect.tatami.client.DojoAfterCreationListener)
	 */
	public void addAfterCreationListener(DojoAfterCreationListener listener) {
		afterCreationListeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.DojoAfterCreationEventsSource#removeAfterCreationListener(com.objetdirect.tatami.client.DojoAfterCreationListener)
	 */
	public void removeAfterCreationListener(DojoAfterCreationListener listener) {
		afterCreationListeners.remove(listener);
	}
	
	/**
	 * @return: the current default x axis.
	 */
	public Axis getDefaultXAxis() {
		return defaultXAxis;
	}

	/**
	 * @return: the current default y axis.
	 */
	public Axis getDefaultYAxis() {
		return defaultYAxis;
	}
	
	
	/**
	 * Internal method used to propagate size changes to the chart
	 * 
	 * @param dojoChart
	 * @param width
	 * @param height
	 */
	private native void dojoUpdateSize(JavaScriptObject dojoChart,Integer width,Integer height)/*-{
		dojoChart.resize(width,height);
	}-*/;

	/* (non-Javadoc)
	 * @see com.objetdirect.tatami.client.HasAdaptiveSize#adaptSize()
	 */
	public void adaptSize() {
		if(dojoWidget != null){
			dojoUpdateSize(dojoWidget,getOffsetWidth(),getOffsetHeight());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#setHeight(java.lang.String)
	 */
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
			dojoUpdateSize(dojoWidget,getOffsetWidth(),getOffsetHeight());
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#setSize(java.lang.String, java.lang.String)
	 */
	@Override
	public void setSize(String width, String height) {
		//This method has been overriden in order to only ask the dojo
		//widget to resize itself
		setHeight(height,false);
		setWidth(width,false);
		if(dojoWidget != null){
			dojoUpdateSize(dojoWidget,getOffsetWidth(),getOffsetHeight());
		}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#setWidth(java.lang.String)
	 */
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
			dojoUpdateSize(dojoWidget,getOffsetWidth(),getOffsetHeight());
		}
	}
	
	
	
}
