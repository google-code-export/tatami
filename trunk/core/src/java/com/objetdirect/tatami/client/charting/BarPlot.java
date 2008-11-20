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

public class BarPlot<T extends Number> extends Plot<T> {

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
	 * @param gap :  sets the gap between two bars
	 */
	public void setGap(int gap) {
		options.put("gap", gap);
	}

}
