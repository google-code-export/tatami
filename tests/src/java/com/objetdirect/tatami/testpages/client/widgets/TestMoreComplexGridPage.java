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
 * Authors:  Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s):
 */
package com.objetdirect.tatami.testpages.client.widgets;

import java.util.Date;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.Button;
import com.objetdirect.tatami.client.DropdownDatePicker;
import com.objetdirect.tatami.client.NumberSpinner;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.grid.Cell;
import com.objetdirect.tatami.client.grid.Grid;
import com.objetdirect.tatami.client.grid.GridDataStore;
import com.objetdirect.tatami.client.grid.GridListener;
import com.objetdirect.tatami.client.grid.GridView;
import com.objetdirect.tatami.client.grid.formatters.BooleanFormatter;
import com.objetdirect.tatami.client.grid.formatters.DateFormatter;
import com.objetdirect.tatami.testpages.client.TestPage;

public class TestMoreComplexGridPage extends TestPage{

	public TestMoreComplexGridPage(){
		 super(TestMoreComplexGridPage.class.getName(), "Test Complex Grid");
	}
	
	public Widget getTestPage() {
		DockPanel dPan = new DockPanel();
		dPan.setWidth("800px");
		
		personalInfoPanel = new PersonalInfoPanel();
		Grid grid = initGrid();
		FormGridListener formGridListener = new FormGridListener();
		grid.addGridListener(formGridListener);
		
		Panel buttonPanel = initEmployeesManagementPanel();
		Panel navigatorPanel = initPageNavigatorPanel();
		Panel filterPanel = initFilterPanel();
		
		dPan.add(navigatorPanel , DockPanel.NORTH);
		dPan.add(grid , DockPanel.CENTER);
		dPan.add(personalInfoPanel , DockPanel.EAST);
		dPan.add(buttonPanel , DockPanel.SOUTH);
		dPan.add(filterPanel , DockPanel.WEST);
		return dPan;
	}

	private final String[] fonctions = {"Developper" , "CEO" , "DHR"};
	
	
	private final String idAttr  = "id" ; 
	private final String firstNameAttr = "firstName";
	private final String lastNameAttr = "lastName";
	private final String birthDateAttr = "birthDate";
	private final String salaryAttr = "salary";
	private final String functionAttr = "function";
	private final String subscriberAttr = "subscriber";
	
	
	private Grid grid;
	private PersonalInfoPanel personalInfoPanel;
	
	private Button addEmployeeButton;
	private Button removeEmployeeButton;
	
	private Button previousPageButton;
	private Button nextPageButton;
	private NumberSpinner pageSelector;
	
	ListBox filterListBox;
	TextBox filterTextBox;
	Grid filterListGrid;
	
	private static int idSeq = 0;
	
	

	
	private Panel initFilterPanel(){
		VerticalPanel filterPanel = new VerticalPanel();
		DOM.setElementAttribute(filterPanel.getElement(), "id", "FilterVPANEL");
		filterListBox = new ListBox();
		filterListBox.addItem("First Name" , firstNameAttr );
		filterListBox.addItem("Last Name" , lastNameAttr);
		filterListBox.addItem("BirthDate" , birthDateAttr);
		filterListBox.addItem("Salary" , salaryAttr);
		filterListBox.addItem("Function" , functionAttr);
		DOM.setElementAttribute(filterListBox.getElement(), "id", "FilterListBox");
		filterPanel.add(filterListBox);
		filterTextBox = new TextBox();
		DOM.setElementAttribute(filterTextBox.getElement(), "id", "FilterTextBox");
		filterPanel.add(filterTextBox);
		Button button = new Button("Filter");
		DOM.setElementAttribute(button.getElement(), "id", "FilterButton");
		GridDataStore filterStore = new GridDataStore();
		filterListGrid = new Grid();
		filterListGrid.setStore(filterStore);
		filterListGrid.addColumn("Field" , "fieldName");
		filterListGrid.addColumn("Criteria");
		Cell removeIconCell = new Cell("Delete");
		removeIconCell.setDefaultValue("<b>REMOVEIT</b>");
		filterListGrid.addCell(removeIconCell);
		filterListGrid.setRenderGridOnLoad(true);
		filterListGrid.setWidth("300px");
		filterListGrid.setHeight("100px");
		filterListGrid.setAutoHeight(false);
		DOM.setElementAttribute(filterListGrid.getElement(), "id", "FilterGridContainer");
		filterListGrid.addGridListener(new GridListener(){
		
			public void onCellClick(Grid sendingrid, int rowIndex, int colIndex,
					String colField) {
				if(colIndex == 2){
					grid.removeFilter((String) sendingrid.getDataAt(rowIndex, 0));
					sendingrid.removeRow(rowIndex);
					grid.updateGrid();
				}
			}

			public void onDataChange(Grid grid, Item itemWhichChanged,
				String attributeName, Object oldValue, Object newValue) {
			}

			public void onSelectionChanged(Grid grid) {
				
			}

			public void onCellDblClick(Grid grid, int rowIndex, int colIndex,
					String colField) {
				// TODO Auto-generated method stub
				
			}
		}); 
		filterPanel.add(filterListGrid);
		
		
		button.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				String field = filterListBox.getValue(filterListBox.getSelectedIndex());
				String value = filterTextBox.getText();
				Object[] newRow = {field, value};
				filterListGrid.addRow(newRow);
				grid.addFilter(field, value );
				grid.updateGrid();
			};
		});
		
		filterPanel.add(button);
		return filterPanel;
	}
	
	
	private Panel initPageNavigatorPanel(){
		class PageNavigatorListener implements ClickListener, ChangeListener{
			public void onClick(Widget sender) {
				if(sender == previousPageButton){
					grid.previousPage();
				}
				if(sender == nextPageButton){
					grid.nextPage();
				}
			}
			public void onChange(Widget sender) {
				if( sender == pageSelector){
					grid.goToPage(pageSelector.getValue().intValue());
				}
			}
		}
		PageNavigatorListener navigatorListener = new PageNavigatorListener();
		HorizontalPanel panel = new HorizontalPanel();
		previousPageButton = new Button("Previous");
		previousPageButton.addClickListener(navigatorListener);
		panel.add(previousPageButton);
		DOM.setElementAttribute(previousPageButton.getElement(), "id", "previousPageButton");
		pageSelector = new NumberSpinner();
		pageSelector.addChangeListener(navigatorListener);
		panel.add(pageSelector);
		nextPageButton = new Button("Next");
		nextPageButton.addClickListener(navigatorListener);
		panel.add(nextPageButton);
		DOM.setElementAttribute(nextPageButton.getElement(), "id", "nextPageButton");
		return panel;
	}
	
	
	private Panel initEmployeesManagementPanel(){
		class ButtonPanelListener implements ClickListener{

			public void onClick(Widget sender) {
				if(sender == addEmployeeButton){
					Item item = new Item();
					idSeq++;
					item.setValue(idAttr, new Integer(idSeq));
					item.setValue(firstNameAttr, "(Insert First Name)");
					item.setValue(lastNameAttr, "(Insert Last Name)");
					item.setValue(birthDateAttr, new Date("01/01/1901"));
					item.setValue(salaryAttr, new Integer("0"));
					item.setValue(functionAttr, "(Insert Function)");
					item.setValue(subscriberAttr, Boolean.FALSE);
					int[] indexes = grid.getSelectedIndexes();
					if(indexes.length > 0){
						grid.addRow(item , indexes[0]);	
					}else{
						grid.addRow(item);
					}
					personalInfoPanel.loadInfo(item);
				}
				if(sender == removeEmployeeButton){
					grid.removeSelectedRows();
				}
			}
		}
		ButtonPanelListener listener = new ButtonPanelListener();
		HorizontalPanel buttonPanel = new HorizontalPanel();
		addEmployeeButton = new Button("Add Employee" , "TatamiDemo-addEmployeeButtonIcon");
		addEmployeeButton.addClickListener(listener);
		buttonPanel.add(addEmployeeButton);
		removeEmployeeButton = new Button("Remove selected employees" , "TatamiDemo-remEmployeeButtonIcon" );
		removeEmployeeButton.addClickListener(listener);
		buttonPanel.add(removeEmployeeButton);
		return buttonPanel;
	}
	
	
	
	
	private class PersonalInfoPanel extends VerticalPanel implements ChangeListener, ClickListener{
		
		private TextBox lastName = new TextBox();
		
		private TextBox firstName = new TextBox();
		
		private DropdownDatePicker picker = new DropdownDatePicker();
		
		private NumberSpinner spinner = new NumberSpinner();
		
		private ListBox function = new ListBox(false);
		
		private CheckBox subscriber = new CheckBox();
		
		private Item currentInfos ;
		
		public PersonalInfoPanel(){
			super();
			
			this.setSpacing(10);
			
			HorizontalPanel nameRow = new HorizontalPanel();
			Label lName = new Label("Last Name : ");
			lName.setWidth("100px");
			nameRow.add(lName);
			nameRow.add(lastName);
			
			this.add(nameRow);
			lastName.addChangeListener(this);
			DOM.setElementAttribute(lastName.getElement(), "id", "LastNameTextBox");
			firstName.addChangeListener(this);
			DOM.setElementAttribute(firstName.getElement(), "id", "FirstNameTextBox");
			picker.addChangeListener(this);
			DOM.setElementAttribute(picker.getElement(), "id", "DateTextBox");
			spinner.addChangeListener(this);
			DOM.setElementAttribute(spinner.getElement(), "id", "SalarySpinner");
			function.addChangeListener(this);
			DOM.setElementAttribute(function.getElement(), "id", "PositionComboBox");
			subscriber.addClickListener(this);
			DOM.setElementAttribute(subscriber.getElement(), "id", "SubscriberCheckBox");
			HorizontalPanel firstNameRow = new HorizontalPanel(); 
			Label fName = new Label("First Name : ");
			fName.setWidth("100px");
			firstNameRow.add(fName);
			firstNameRow.add(firstName);
			this.add(firstNameRow);
			
			HorizontalPanel birthDateRow = new HorizontalPanel();
			Label bDate = new Label("BirthDate : ");
			bDate.setWidth("100px");
			birthDateRow.add(bDate);
			birthDateRow.add(picker);
			this.add(birthDateRow);
			
			HorizontalPanel salaryRow = new HorizontalPanel();
			Label salarylabel = new Label("Salary : ");
			salarylabel.setWidth("100px");
			salaryRow.add(salarylabel);
			salaryRow.add(spinner);
			this.add(salaryRow);
			
			
			HorizontalPanel functionRow = new HorizontalPanel();
			Label functionLabel = new Label("Function : ");
			functionLabel.setWidth("100px");
			functionRow.add(functionLabel);
			for (int i = 0; i < fonctions.length; i++) {
				function.addItem(fonctions[i]);
			}
			functionRow.add(function);
			this.add(functionRow);
			
			
			HorizontalPanel subscriberRow = new HorizontalPanel();
			Label subscriberLabel = new Label("Subscriber : ");
			subscriberLabel.setWidth("100px");
			subscriberRow.add(subscriberLabel);
			subscriberRow.add(subscriber);
			this.add(subscriberRow);
		}
		
		public void loadInfo(Item item){
			currentInfos = item;
			lastName.setText((String) item.getValue(lastNameAttr , "Unknown"));
			firstName.setText((String) item.getValue(firstNameAttr , "Unknown"));
			picker.setDate((Date)item.getValue(birthDateAttr , "01/01/01"));
			spinner.setValue((Number)(item.getValue(salaryAttr , "0")));
			int index = -1;
			String currFunction = (String) item.getValue(functionAttr, "Unknown");
			for (int i = 0; i < fonctions.length; i++) {
				if(currFunction.compareTo(fonctions[i]) == 0){
					index = i;
					break;
				}
			}
			function.setSelectedIndex(index);
			subscriber.setChecked(((Boolean)item.getValue(subscriberAttr, Boolean.FALSE)).booleanValue());
		}

		public Item asItem(){
			return currentInfos;
		}

		public void onChange(Widget sender) {
			if(sender == lastName){
				asItem().setValue(lastNameAttr , lastName.getText());
			}
			if(sender == firstName){
				asItem().setValue(firstNameAttr , firstName.getText());
			}
			if(sender == picker){
				asItem().setValue(birthDateAttr , picker.getDate());
			}
			if(sender == spinner){
				asItem().setValue( salaryAttr ,new Integer(spinner.getValue().intValue()));
			}
			if(sender == function){
				asItem().setValue(functionAttr, function.getValue(function.getSelectedIndex()));
			}
			
			
		}

		public void onClick(Widget sender) {
			if(sender == subscriber){
				asItem().setValue(subscriberAttr , new Boolean(subscriber.isChecked()));
			}			
		}
		
		
		
	}
	
	
	private Grid initGrid(){
		
		Cell idCell = new Cell(idAttr , "Unique Id");
		Cell firstNameCell = new Cell(firstNameAttr , "First Name ");
		Cell lastNameCell = new Cell(lastNameAttr , "Last Name ");
		Cell birthDateCell = new Cell(birthDateAttr , "Birthdate");
		birthDateCell.setFormatter(new DateFormatter(DateFormatter.displayDateOnly));
		Cell salaryCell = new Cell(salaryAttr , "Salary" );
		Cell functionCell = new Cell(functionAttr , "Function" );
		Cell subscriberCell = new Cell(subscriberAttr , "Subscriber");
		subscriberCell.setFormatter(new BooleanFormatter());
		
		GridView view = new GridView();
		
		view.addCellToLastRow(idCell);
		view.addCellToLastRow(firstNameCell);
		view.addCellToLastRow(lastNameCell);
		view.addCellToLastRow(birthDateCell);
		view.addCellToLastRow(salaryCell);
		view.addCellToLastRow(functionCell);
		view.addCellToLastRow(subscriberCell);
		String[] firstNames = { "Jean" , "John" , "William" , "Edgar" , "Alex" , "Sabrina" , "Anne" , "Orson" , "Li" , "Jose" , "Ibrahim" , "Emmanuel" , "James" , "Xavier" , "Cory" , "Sarah" , "Curt"};
		String[] lastNames = { "Poe" , "Doe" , "Turing" , "Scottgard" , "Dupond" , " Dupont" , "Rada" , "Chen" , "Barros" , "Borges" , "Scott" , "Bond" , "Doctorow" , "Hitcher" , "Connor" , "Cobain" , "Russel"};
		GridDataStore store = new GridDataStore(); 
		grid = new Grid(store , view);
		grid.setRenderGridOnLoad(true);
		grid.setRowsPerPage(50);
		grid.setMaximumFetchCountAtAtime(10);
		grid.setSortIndex(0, true);
		grid.setUserSortable(true);
		grid.setAutoHeight(false);
		grid.setAutoWidth(false);
		grid.setHeight("260px");
		grid.setWidth("600px");
		DOM.setElementAttribute(grid.getElement(), "id", "GridContainer");
		Object[] row1 = {null, firstNames[0], lastNames[0], new Date(38 + 50 , 5 , 22) ,new Integer(3000 + 1000), fonctions[0] , Boolean.TRUE};
		Object[] row2 = {null, firstNames[1], lastNames[0], new Date(38 + 40 , 5 , 22) ,new Integer(2000 + 1000), fonctions[1] , Boolean.TRUE};
		Object[] row3 = {null, firstNames[1], lastNames[2], new Date(38 + 20 , 5 , 22) ,new Integer(1000 + 1000), fonctions[2] , Boolean.FALSE};
		Object[] rows = {row1 , row2 , row3};
		idSeq++;
		for(int i = 0 ; i < 170 ; i++){
			Object[] rowToAdd = (Object[]) rows[i%3];
			rowToAdd[0] = new Integer(idSeq++);
			grid.addRow(rowToAdd);
		}
		return grid;
	}
	
	
	
	private class FormGridListener implements GridListener{
	
			public FormGridListener(){
				
			}
		
			
			public void onSelectionChanged(Grid grid) {
				Item[] items = grid.getSelectedItems();
				if(items.length > 0){
					personalInfoPanel.loadInfo(items[0]);
				}
		
			}
		
			public void onDataChange(Grid grid, Item itemWhichChanged , String attribute , Object oldValue , Object newValue) {
			
			}
		
			public void onCellClick(Grid grid , int rowIndex, int colIndex, String colName) {
				// TODO Auto-generated method stub
			}


			public void onCellDblClick(Grid grid, int rowIndex, int colIndex,
					String colField) {
				
			}


			
	}
	
	
}
