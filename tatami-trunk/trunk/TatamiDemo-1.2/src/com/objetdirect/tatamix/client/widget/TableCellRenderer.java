package com.objetdirect.tatamix.client.widget;

import com.google.gwt.user.client.ui.Widget;

/**
 * Interface permettant de creer un rendu sous forme de <code>Widget</code>.
 * Le <code>Widget</code> ainsi créé represente la valeur passée en parametre
 *  
 * @author Vianney
 *
 */
public interface TableCellRenderer {

	/**
	 * Renvoie le rendu d'une cellulue pour la table donnée. 
	 * @param table <code>Table</code> table contenant la cellule
	 * @param value valeur de la cellule
	 * @param isSelected precise si la cellule est selectionnée. 
	 * @param row numero de ligne de la celule
	 * @param column numéro de colonne de la cellule
	 * @return un <code>Widget<code> ne doit pas être <code>null</code>
	 */
	public Widget getTableCellRendererWidget(Table table, Object value, boolean isSelected, int row, int column);
		
	
	
}
