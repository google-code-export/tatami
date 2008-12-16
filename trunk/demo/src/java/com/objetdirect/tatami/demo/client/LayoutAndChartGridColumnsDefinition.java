package com.objetdirect.tatami.demo.client;

public interface LayoutAndChartGridColumnsDefinition {

	public final String COMPANY_FIELD = "company";
	public final String COMPANY_COLUMN_NAME = TatamiDemo.getMessages().column_name_company();
	public final String SHOW_GRAPH_FIELD = "showgraph";
	public final String SHOW_GRAPH_COLUMN_NAME = TatamiDemo.getMessages().column_name_showgraph();
	public final String COLOR_FIELD = "color";
	public final String COLOR_COLUMN_NAME = TatamiDemo.getMessages().column_name_color();
	public final String DATA_FIELD = "graphdata";
	public final String[] DEFINED_COLORS = {"aqua", "black", "blue", "fuchsia", "gray", "green", "lime", "maroon", "navy", "olive", "purple", "red", "silver", "teal", "white"};
	public final String[] DEFINED_MONTHS = {"January","February","March","April","May","June","July","August","September","October","November","December"};
	
}
