package com.objetdirect.tatamix.client.widget;

import com.google.gwt.user.client.ui.Widget;

/**
 * Interface permettant de creer un rendu sous forme de <code>Widget</code>.
 * Le <code>Widget</code> ainsi cr�� represente la valeur pass�e en parametre
 *  
 * @author Vianney
 *
 */
public interface TableCellRenderer {

	/**
	 * Renvoie le rendu d'une cellulue pour la table donn�e. 
	 * @param table <code>Table</code> table contenant la cellule
	 * @param value valeur de la cellule
	 * @param isSelected precise si la cellule est selectionn�e. 
	 * @param row numero de ligne de la celule
	 * @param column num�ro de colonne de la cellule
	 * @return un <code>Widget<code> ne doit pas �tre <code>null</code>
	 */
	public Widget getTableCellRendererWidget(Table table, Object value, boolean isSelected, int row, int column);
		
	
	
}
