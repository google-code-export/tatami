package com.objetdirect.tatami.client.charting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.DojoAfterCreationEventsSource;
import com.objetdirect.tatami.client.DojoAfterCreationListener;
import com.objetdirect.tatami.client.DojoController;
import com.objetdirect.tatami.client.HasDojo;
import com.objetdirect.tatami.client.JSHelper;
import com.objetdirect.tatami.client.charting.effects.Effect;

public class Chart2D extends Widget implements HasDojo, DojoAfterCreationEventsSource  {

	private List<Plot> plots = new ArrayList<Plot>();
	
	private List<Axis> axes = new ArrayList<Axis>();
	
	private List<Serie> series = new ArrayList<Serie>();
	
	private JavaScriptObject dojoWidget;
	
	private String theme ;

	private Axis defaultXAxis;
	
	private Axis defaultYAxis;
	
	private Collection<DojoAfterCreationListener> afterCreationListeners = new ArrayList<DojoAfterCreationListener>();

	public Chart2D(String width,String height){
		DojoController.getInstance().loadDojoWidget(this);
		setElement(DOM.createDiv());
		setSize(width, height);
	}
	
	public void createDojoWidget() throws Exception {
		dojoWidget = createDojoChart(getElement(),this.getOffsetWidth() , this.getOffsetHeight());
		refreshChart();
	}

	
	private native JavaScriptObject createDojoChart(Element elem , int width , int height)/*-{
		var chart = new $wnd.dojox.charting.Chart2D(elem);
		return chart;
	}-*/;
	
	private native void updateDojoSeries(String seriesName, JavaScriptObject data, JavaScriptObject dojoWidget)/*-{
		dojoWidget.updateSeries(seriesName,data);
	}-*/;
	
	private native void addDojoPlot(String name , JavaScriptObject options , JavaScriptObject dojoWidget)/*-{
		dojoWidget.addPlot(name,options);
	}-*/;
	
	private native void addDojoAxis(String name , JavaScriptObject options , JavaScriptObject dojoWidget)/*-{
		dojoWidget.addAxis(name,options);
	}-*/;
	
	private native void addDojoSerie(String name ,  JavaScriptObject data , JavaScriptObject
			options , JavaScriptObject dojoWidget)/*-{
		dojoWidget.addSeries(name,data,options);
	}-*/;
	
	public native void dojoSetTheme(JavaScriptObject dojoWidget, String theme)/*-{
		dojoWidget.setTheme($wnd.dojo.getObject("dojox.charting.themes."+theme));
	}-*/;
	
	private native void dojoRender(JavaScriptObject dojoWidget)/*-{
		dojoWidget.render();
	}-*/;
	
	public String getDojoName() {
		return "dojox.charting.Chart2D";
	}
	
	public List<Plot> getPlots(){
		return plots;
	}
	
	public void setPlots(List<Plot> plots) {
		this.plots = plots;
	}
	
	public void addPlot(Plot<?> plot){
		this.plots.add(plot);
	}
	
	
	private void destroyPlot(Plot<?> plot){
		for(Effect effect : plot.getEffects()){
			effect.destroyEffect();
		}
	}
	
	public void setTheme(String theme){
		DojoController.getInstance().require("dojox.charting.themes."+theme);
		this.theme = theme;
	}
	
	public void setDefaultXAxis(Axis axis){
		axis.setName("x");
		defaultXAxis = axis;
		axes.add(axis);
	}
	
	public void setDefaultYAxis(Axis axis){
		axis.setName("y");
		defaultYAxis = axis;
		axes.add(axis);
	}
	
	public void addAxis(Axis axis){
		if(!axes.contains(axis)){
			axes.add(axis);
		}
	}
	
	/**
	 * Renders the chart
	 */
	public void refreshChart(){
		if(dojoWidget != null){
			for (Plot<?> plot : plots) {
				Map<String,Object> options = plot.getOptions();
				if(plot.getXAxis() != null){
					addAxis(plot.getXAxis());
					options.put("hAxis", plot.getXAxis().getId());
				}
				if(plot.getYAxis() != null){
					addAxis(plot.getYAxis());
					options.put("vAxis", plot.getYAxis().getId());
				}
				addDojoPlot(plot.getName(),JSHelper.convertObjectToJSObject(plot.getOptions()), dojoWidget);
				for(Serie<?> serie : plot.getSeries()){
					serie.getOptions().put("plot",plot.getName());
					series.add(serie);
				}
				for(Effect effect : plot.getEffects()){
					effect.initEffect(this, plot);
				}
			}
			for (Axis axis : axes) {
				addDojoAxis(axis.getId(), JSHelper.convertObjectToJSObject(axis.getOptions()), dojoWidget);
			}
			for (Serie<?> serie : series) {
				addDojoSerie(serie.getName(),JSHelper.convertObjectToJSObject(serie.getData()),JSHelper.convertObjectToJSObject(serie.getOptions()),dojoWidget);
			}
			if(theme != null){
				dojoSetTheme(dojoWidget, theme);
			}
			dojoRender(dojoWidget);
		}
	}


	public void doAfterCreation() {
		for (DojoAfterCreationListener listener : afterCreationListeners) {
			listener.dojoAfterCreation(this);
		}
	}

	public void doBeforeDestruction() {
		
	}

	public void removePlot(Plot<?> plot){
		destroyPlot(plot);
		plots.remove(plot);
	}
	
	public void free() {
		for (Iterator<Plot> iterator = plots.iterator(); iterator.hasNext();) {
			Plot<?> plot =  iterator.next();
			destroyPlot(plot);
			iterator.remove();
		}
	}

	public JavaScriptObject getDojoWidget() {
		return dojoWidget;
	}

	public void onDojoLoad() {
		
	}
	@Override
	public void onAttach(){
		super.onAttach();
		try {
			createDojoWidget();
			doAfterCreation();
			DojoController.getInstance().setGWTWidget(dojoWidget, this);
		} catch (Exception e) {
			GWT.log("ERROR : ", e);
		}
		
	}
	
	@Override
	public void onDetach(){
		doBeforeDestruction();
		DojoController.getInstance().setGWTWidget(dojoWidget, null);
		destroy(dojoWidget);
		free();
		super.onDetach();
	}
	
	public native void destroy(JavaScriptObject dojoWidget)/*-{
		dojoWidget.destroy();
	}-*/;
	
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

	public void updateSerie(Serie<?> serieToUpdate){
		updateDojoSeries(serieToUpdate.getName(),JSHelper.convertObjectToJSObject(serieToUpdate.getData()),dojoWidget);
		dojoRender(dojoWidget);
	}
	
	public void updateSeries(Serie[] seriesToUpdate){
		for (Serie<?> serie : seriesToUpdate) {
			updateDojoSeries(serie.getName(),JSHelper.convertObjectToJSObject(serie.getData()),dojoWidget);
		}
		dojoRender(dojoWidget);
	}

	public void addAfterCreationListener(DojoAfterCreationListener listener) {
		afterCreationListeners.add(listener);
	}

	public void removeAfterCreationListener(DojoAfterCreationListener listener) {
		afterCreationListeners.remove(listener);
	}
	
	public Axis getDefaultXAxis() {
		return defaultXAxis;
	}

	public Axis getDefaultYAxis() {
		return defaultYAxis;
	}
	
}
