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

}
