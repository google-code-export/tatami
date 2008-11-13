package com.objetdirect.tatamix.client.widget;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class DefaultTableCellRenderer implements TableCellRenderer {

	public Widget getTableCellRendererWidget(Table table, Object value,	boolean isSelected, int row, int column) {
		Label label = new Label();
		if (value != null) {
			label.setText(value.toString());
		} else {
			label.setText("null");
		}
		return label;
	}

}
