package com.objetdirect.tatami.testpages.client;

import java.util.Date;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.grid.Cell;
import com.objetdirect.tatami.client.grid.Grid;
import com.objetdirect.tatami.client.grid.GridDataStore;
import com.objetdirect.tatami.client.grid.GridLayout;
import com.objetdirect.tatami.client.grid.GridListener;
import com.objetdirect.tatami.client.grid.GridView;
import com.objetdirect.tatami.client.grid.editor.DateEditor;
import com.objetdirect.tatami.client.grid.editor.NumberSpinnerEditor;
import com.objetdirect.tatami.client.grid.formatters.CurrencyFormatter;
import com.objetdirect.tatami.client.grid.formatters.DateFormatter;

public class TestLayoutGridPage extends TestPage{

	protected TestLayoutGridPage() {
		super("com.objetdirect.tatami.testpages.client.TestLayoutGridPage", "Test Layout Grid");
	}
	Grid grid;
	
	public Widget getTestPage() {
		grid = new Grid();
		VerticalPanel panel = new VerticalPanel();
		GridDataStore store = new GridDataStore("myidAttr");
		grid.setStore(store);
		Item itemJohn = new Item();
		itemJohn.setValue("myidAttr", "14563" );
		itemJohn.setValue("myname", "John Doe" );
		itemJohn.setValue("number", "0123456789");
		itemJohn.setValue("salary",  new Integer("500"));
		itemJohn.setValue("date", new Date("03/25/1981"));
		itemJohn.setValue("position", "CEO");
		itemJohn.setValue("available", Boolean.FALSE);
		itemJohn.setValue("mySecret", "!!!");
		itemJohn.setValue("description", "I m John Doe and this is my description" );
		itemJohn.setValue("appreciation", new Integer(2) );
		store.add(itemJohn);
		
		
		Item itemJane = new Item();
		itemJane.setValue("myidAttr", "87321" );
		itemJane.setValue("number", "9876543210");
		itemJane.setValue("salary",  new Integer("1200"));
		itemJane.setValue("date", new Date("11/12/1978"));
		itemJane.setValue("position", "Developer");
		itemJane.setValue("available", Boolean.TRUE);
		itemJane.setValue("myname", "Jane Doe" );
		itemJane.setValue("description", "I m Jane Doe and this is my description" );
		itemJane.setValue("appreciation", new Integer(8) );
		itemJane.setValue("mySecret", "!!!");
		
		store.add(itemJane);
		
		
		
		
		GridLayout layout = new GridLayout();
		
		//Creating the first (left) view
		//This view contains only one cell, which label is "Name".
		//and wich content is the "myname" field from the data store objects.
		//This cell is also not resizable.
		//It also has a fixed width, and is not scrollable
		GridView view = new GridView();
		Cell cell = new Cell("myname" , "Name");
		cell.setIsNotResizable(Boolean.FALSE);
		cell.setWidth("100px");
		view.addCellToLastRow(cell);
		//view.setScrollable(false);
		//view.setWidth("100px");
		
		//Creating the second (right) view
		//This view has 2 rows
		GridView view2 = new GridView();
		
		//We add a cell to the (current) last row
		view2.addCellToLastRow(new Cell("number" ,"Phone Number"));
		
		//We define a cell containing the salary attribute, and
		//add it to the row
		//This cell is editable (with a number spinner)
		//and is formatted as a currency
		Cell salaryCell = new Cell("salary" ,"Salary");
		salaryCell.setEditor(new NumberSpinnerEditor());
		salaryCell.setFormatter(new CurrencyFormatter("EUR"));
		salaryCell.setWidth("50px");
		view2.addCellToLastRow(salaryCell);
		
		//We define a cell containing the date attribute, and add it to
		//the row.
		//This cell is also editable (with a DateEditor)
		//and is formatted as a date. 
		//Please note that we define specific style settings for this cell
		Cell birthDateCell = new Cell("date" ,"Birthdate");
		birthDateCell.setEditor(new DateEditor());
		birthDateCell.setFormatter(new DateFormatter(DateFormatter.displayDateOnly));
		birthDateCell.setWidth("150px");
		birthDateCell.setCellStyles("text-align : right;");
		view2.addCellToLastRow(birthDateCell);
		
		//We define a cell containing the description
		//This cell will be added to a second row, and will have 
		//a colspan of 3
		Cell descriptionCell = new Cell("description" , "Description");
		descriptionCell.setColSpan(new Integer(3));
		view2.addCellToRow(descriptionCell, 1);
		
		//We define a cell containing the appreciation,
		//and add it to the first row.
		//This cell has a rowspan of 2
		Cell appreciationCell = new Cell("appreciation" , "Mark");
		appreciationCell.setEditor(new NumberSpinnerEditor(0f , 10f, 0.5f));
		appreciationCell.setRowSpan(new Integer(2));
		view2.addCellToRow(appreciationCell,0);
		
		
		//We define a cell containing an constant value (an image)
		//and add it just like the previous one
		Cell imageCell = new Cell("Send e-mail");
		imageCell.setDefaultValue("<img src='./mail-message-new.png' alt='send mail'></img>");
		imageCell.setRowSpan(new Integer(2));
		view2.addCellToRow(imageCell,0);
		//We add the views to the layout
		layout.addView(view);
		layout.addView(view2);
		
		grid.setLayout(layout);
		grid.setAutoWidth(false);
		grid.setWidth("500px");
		grid.setHeight("400px");
		grid.setRenderGridOnLoad(true);
		grid.setElasticView(2);
		grid.updateView();
		grid.setRowBar(true);
		DOM.setElementAttribute(grid.getElement(),"id","GridContainer");
		grid.setHeight("200px");
		grid.setSortIndex(0, true);
		panel.add(grid);
		class myGridListener implements GridListener{


			//The html which content will be changed each time a grid event occurs
			private HTML html;

			//What should be updated when a cell is clicked on.
			private String lastClickedCellContent;


			//What should be updated when the rows selection change
			private String selectedIndexesCellContent;


			//What should be displayed when an item’s value change
			private String itemChangedContent;


			//The constructor takes an “HTML” object in which
			// it will write the cell content.
			public myGridListener(HTML html){
				this.html = html;
			}


			//Updates the html object content 
			private void refresh(){
				this.html.setText(lastClickedCellContent + "\n" + selectedIndexesCellContent  +"\n" + itemChangedContent);
			}
			
			public void onCellClick(Grid grid, int rowIndex , int colIndex , String colName){
				Object cellContent;
				if(colName != null){
					cellContent = grid.getItemFromRow(rowIndex).getValues(grid.getLayout().getCellAmongAllViews(colIndex).getField());
				}else{
					cellContent = null;
				}
				lastClickedCellContent ="The last clicked cell contained : " + cellContent + " and was at row  " + rowIndex + " and at column " + colIndex;
				this.refresh();
			}

			public void onSelectionChanged(Grid grid){
				int[] indexes = grid.getSelectedIndexes();
				selectedIndexesCellContent = "Selected rows : ";
				for(int i = 0; i < indexes.length; i ++ ){
					selectedIndexesCellContent += i +" ";
				}
				this.refresh();	
			}

		public void onDataChange(Grid grid , Item itemWhichChanged, String attributeName, Object oldValue, Object newValue){
			itemChangedContent = " The item at row " + grid.getRowFromItem(itemWhichChanged) + " has changed. His attribute " + attributeName + " changed its value from " + oldValue.toString() + " to "+ newValue.toString();
			this.refresh();
		}


		public void onCellDblClick(Grid grid, int rowIndex, int colIndex,
				String colField) {
		}

		}

		HTML html = new HTML();
		DOM.setElementAttribute(html.getElement(),"id","ChangeNotifier");
		grid.addGridListener(new myGridListener(html));
		panel.add(html);
		return panel;
	}

	
}
