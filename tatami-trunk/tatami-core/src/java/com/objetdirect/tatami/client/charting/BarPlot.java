package com.objetdirect.tatami.client.charting;

public class BarPlot extends Plot {

	final public static String PLOT_TYPE_BARS = "Bars";
	final public static String PLOT_TYPE_COLUMNS = "Columns";
	final public static String PLOT_TYPE_BARS_STACKED = "StackedBars";
	final public static String PLOT_TYPE_COLUMNS_STACKED = "StackedColumns";
	final public static String PLOT_TYPE_BARS_CLUSTERED = "ClusteredBars";
	final public static String PLOT_TYPE_COLUMNS_CLUSTERED = "ClusteredColumns";

	public BarPlot() {
		super(BarPlot.PLOT_TYPE_COLUMNS);
	}

	public BarPlot(String type) {
		super(type);
	}

	/**
	 * @param gap
	 *            : on a Column or Bar chart, sets the gap between two bars
	 */
	public void setGap(int gap) {
		options.put("gap", gap);
	}

}
