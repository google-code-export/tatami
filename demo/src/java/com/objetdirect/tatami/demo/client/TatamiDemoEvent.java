package com.objetdirect.tatami.demo.client;

public interface TatamiDemoEvent {
	
	public String SHOW_HOME = "Home";
	public String UPDATE_CONTENT = "UpdateContent";
	public String SHOW_SLIDER_DEMO = "Show_Slider";
	public String SHOW_GFX_DEMO = "Show_GFX";
	public String SHOW_DATE_TIME_DEMO = "Show_Date_Time";
	public String SHOW_COLOR_DEMO = "Show_Color";
	public String SHOW_DND_DEMO = "Show_DND";
	public String SHOW_GRID_DEMO = "Show_Grid";
	public String SHOW_LAYOUT_AND_CHART_DEMO = "Show_Layout_And_Chart";
	public String SHOW_CHART_DEMO = "Show_Chart";
	
	public String INIT_GRID = "Init_Grid";
	public String ADD_ROW = "Add_Row";
	public String REMOVE_ROWS = "Remove_Rows";
	public String DATASTORE_FULLUPDATE = "Update_GridDatastore";
	
	public String INIT_CHART_GRID = "Init Chart Grid";
	public String RESET_CHART = "Reset Chart";
	public String CHART_UPDATE_SERIES = "Update Chart";
	public String CHART_ADD_SERIE = "Add Serie";
	public String CHART_REMOVE_SERIE = "Remove Serie";
	public String CHART_DATASTORE_FULLUPDATE = "Update_ChartGridDataStore";
	public String SHOW_BUG = "SHOW_BUG";
	public String DEBUG = "DEBUG";
	
	//events for GFX Demo : 
	
	public String DRAW_GRAPHIC_OBJECT ="DRAW_GRAPHIC_OBJECT";
	public String DRAW_POLYLINE = "DRAW_POLYLINE";
	public String DRAW_LINE = "DRAW_LINE";
	public String DRAW_RECT = "DRAW_RECT";
	public String DRAW_ELLIPSE = "DRAW_ELLIPSE";
	public String DRAW_TEXT = "DRAW_TEXT";
	public String DRAW_TEXT_PATH = "DRAW_TEXT_PATH";
	public String DRAW_VIRTUAL_GROUP = "DRAW_VIRTUAL_GROUP";
	public String DRAW_CIRCLE = "DRAW_CIRCLE";
	public String DRAW_PATH = "DRAW_PATH";
	public String DRAW_IMAGE = "DRAW_IMAGE";
	public String DELETE_GRAPHIC = "DELETE_GRAPHIC";
	
	
	

}
