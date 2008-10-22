package com.objetdirect.tatami.client.charting;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.AbstractDojo;
import com.objetdirect.tatami.client.JSHelper;

public class Chart2D extends AbstractDojo {

	private List<Plot> plots = new ArrayList<Plot>();
	private List<Axis> axes = new ArrayList<Axis>();
	private List<List<?>> series = new ArrayList<List<?>>();
	
	public void createDojoWidget() throws Exception {
		dojoWidget = createDojoChart();
		refreshChart();
	}

	private native JavaScriptObject createDojoChart()/*-{
		return new $wnd.dojox.charting.Chart2D();
	}-*/;
	
	private native void addDojoPlot(String name , JavaScriptObject options , JavaScriptObject dojoWidget)/*-{
		dojoWidget.addPlot(name,options);
	}-*/;
	
	private native void addDojoAxis(String name , JavaScriptObject options , JavaScriptObject dojoWidget)/*-{
		dojoWidget.addAxis(name,options);
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
	
	public void addPlot(Plot plot){
		this.plots.add(plot);
	}
	
	public void removePlot(Plot plot){
		this.plots.remove(plot);
	}
	
	public List<Axis> getAxis() {
		return axes;
	}

	public void setAxis(List<Axis> axis) {
		this.axes = axis;
	}
	
	public void addAxis(Axis axis){
		this.axes.add(axis);
	}
	
	public void removeAxis(Axis axis){
		this.axes.remove(axis);
	}
	
	public List<List<?>> getSeries() {
		return series;
	}

	public void setSeries(List<List<?>> series) {
		this.series = series;
	}
	
	public void addAxis(List<?> serie){
		this.series.add(serie);
	}
	
	public void removeAxis(List<?> serie){
		this.series.remove(serie);
	}
	
	public void refreshChart(){
		if(dojoWidget != null){
			for (Plot plot : plots) {
				addDojoPlot(plot.getName(), JSHelper.convertObjectToJSObject(plot.getOptions()), dojoWidget);
			}
			for (Axis axis : axes) {
				addDojoAxis(axis.getName(), JSHelper.convertObjectToJSObject(axis.getOptions()), dojoWidget);
			}
			dojoRender(dojoWidget);
		}
	}
	

}
