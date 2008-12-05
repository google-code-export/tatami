package com.objetdirect.tatami.client.grid;

import com.google.gwt.core.client.JavaScriptObject;

public class GridSelectionManager {

	Grid grid;
	
	public final static String SELECTION_MODE_EXTENDED = "extended";
	public final static String SELECTION_MODE_MULTIPLE = "multiple";
	public final static String SELECTION_MODE_SINGLE = "single";
	public final static String SELECTION_MODE_NONE = "none";
	
	
	public GridSelectionManager(Grid grid){
		this.grid = grid;
	}
	
	public void addToSelection(int index){
		if(grid.getDojoWidget() != null){
			dojoAddToSelection(grid.getDojoWidget(), index);
		}
	}
	
	private native void dojoAddToSelection(JavaScriptObject dojowidget, int index)/*-{
		dojowidget.selection.addToSelection(index);
	}-*/;
	
	public void deselect(int index){
		if(grid.getDojoWidget() != null){
			dojoDeSelect(grid.getDojoWidget(), index);
		}
	}
	
	private native void dojoDeSelect(JavaScriptObject dojowidget, int index)/*-{
		dojowidget.selection.deselect(index);
	}-*/;
	
	public void deselectAll(){
		if(grid.getDojoWidget() != null){
			dojoDeselectAll(grid.getDojoWidget());
		}
	}
	
	private native void dojoDeselectAll(JavaScriptObject dojowidget)/*-{
		dojowidget.selection.deselectAll();
	}-*/;
	
	public void setMode(String mode){
		dojoSetMode(grid.getDojoWidget(), mode);
	}
	
	private native void dojoSetMode(JavaScriptObject dojowidget,String mode)/*-{
		dojowidget.selection.setMode(mode);
	}-*/;
	
}
