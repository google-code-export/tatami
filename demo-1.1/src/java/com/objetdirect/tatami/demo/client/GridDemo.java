package com.objetdirect.tatami.demo.client;

import java.util.Date;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.Button;
import com.objetdirect.tatami.client.data.AbstractDataStore;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.grid.Cell;
import com.objetdirect.tatami.client.grid.Grid;
import com.objetdirect.tatami.client.grid.GridDataStore;
import com.objetdirect.tatami.client.grid.GridLayout;
import com.objetdirect.tatami.client.grid.GridView;
import com.objetdirect.tatami.client.grid.editor.CheckBoxEditor;
import com.objetdirect.tatami.client.grid.editor.ComboBoxEditor;
import com.objetdirect.tatami.client.grid.editor.DateEditor;
import com.objetdirect.tatami.client.grid.editor.NumberSpinnerEditor;
import com.objetdirect.tatami.client.grid.editor.TextEditor;
import com.objetdirect.tatami.client.grid.formatters.CurrencyFormatter;
import com.objetdirect.tatami.client.grid.formatters.DateFormatter;
import com.objetdirect.tatamix.client.hmvc.CompositeView;
import com.objetdirect.tatamix.client.hmvc.ControllerProcessor;
import com.objetdirect.tatamix.client.hmvc.Event;
import com.objetdirect.tatamix.client.hmvc.ViewEvent;
import com.objetdirect.tatamix.client.widget.Paragraph;

public class GridDemo extends CompositeView implements TatamiDemoEvent,GridColumnsDefinition{

	private Grid grid;


	private FlowPanel layout;
	private GridLayout gridLayout;
	Button addButton;
	Button removeButton;
	
	
	public GridDemo(){
		layout = new FlowPanel();
		ControllerProcessor dataStoreUpdateProcessor = new ControllerProcessor(){
			public void run(Event event) {
				grid.setStore((AbstractDataStore) event.getSource());
			}
		};
		register(DATASTORE_FULLUPDATE, dataStoreUpdateProcessor);
	}
	
	public void init(){
		initWidget(layout);
		initComponents();
		setStylePrimaryName("grid");
	}
	
	private void initComponents() {
		Paragraph intro = new Paragraph();
		intro.setHTML(TatamiDemo.getMessages().grid_intro());
		layout.add(intro);
		initGridLayout();
		grid = new Grid();
		grid.setLayout(gridLayout);
		grid.setWidth("100%");
		grid.setElasticView(2);
		grid.updateView();
		grid.setRowBar(true);
		grid.setHeight("400px");
		grid.setSortIndex(0, true);
		fire(new ViewEvent(INIT_GRID,GridDemo.this));
		layout.add(grid);
		initButtons();
		layout.add(addButton);
		layout.add(removeButton);
	}
	
	private void initButtons(){
		addButton = new Button("Add row");
		addButton.addClickListener(new ClickListener(){
			public void onClick(Widget arg0) {
				fire(new ViewEvent(ADD_ROW,GridDemo.this));
			}
		});
		removeButton = new Button("Remove selected row(s)");
		removeButton.addClickListener(new ClickListener(){
			public void onClick(Widget arg0) {
				fire(new ViewEvent(REMOVE_ROWS,grid.getSelectedItems()));
			}
		});
	}
	
	private void initGridLayout(){
		gridLayout = new GridLayout();
		//Creating the first (left) view
		//This view contains only one cell, which label is "Name".
		//and wich content is the "myname" field from the data store objects.
		//This cell is also not resizable.
		//It also has a fixed width, and is not scrollable
		GridView view = new GridView();
		Cell firstName = new Cell(FIRST_NAME_FIELD , FIRST_NAME_COLUMN_NAME);
		firstName.setIsNotResizable(Boolean.TRUE);
		view.addCellToLastRow(firstName);
//		Cell lastName = new Cell(LAST_NAME_FIELD , LAST_NAME_COLUMN_NAME);
//		lastName.setIsNotResizable(Boolean.TRUE);
//		view.addCellToLastRow(lastName);
//		view.setWidth("15%");
		view.setScrollable(false);
		//Creating the second (right) view
		//This view has 2 rows
		GridView view2 = new GridView();
		//We add a cell to the (current) last row
		view2.addCellToLastRow(new Cell(PHONE_NUMBER_FIELD , PHONE_NUMBER__COLUMN_NAME));
		
		//We define a cell containing the salary attribute, and
		//add it to the row
		//This cell is editable (with a number spinner)
		//and is formatted as a currency
		Cell salaryCell = new Cell(SALARY_FIELD ,SALARY_COLUMN_NAME);
		salaryCell.setEditor(new NumberSpinnerEditor());
		salaryCell.setFormatter(new CurrencyFormatter().setCurrency("EUR"));
		salaryCell.setWidth("80px");
		view2.addCellToLastRow(salaryCell);
		
		//We define a cell containing the date attribute, and add it to
		//the row.
		//This cell is also editable (with a DateEditor)
		//and is formatted as a date. 
		//Please note that we define specific style settings for this cell
		Cell birthDateCell = new Cell(BIRTHDATE_FIELD ,BIRTHDATE_COLUMN_NAME);
		birthDateCell.setEditor(new DateEditor());
		birthDateCell.setFormatter(new DateFormatter(DateFormatter.displayDateOnly));
		birthDateCell.setCellStyles("text-align : right;");
		birthDateCell.setWidth("80px");
		view2.addCellToLastRow(birthDateCell);
		
		//We define a cell containing the description
		//This cell will be added to a second row, and will have 
		//a colspan of 3
		Cell descriptionCell = new Cell(DESCRIPTION_FIELD , DESCRIPTION_COLUMN_NAME);
		descriptionCell.setColSpan(new Integer(3));
		descriptionCell.setWidth("80px");
		descriptionCell.setEditor(new TextEditor());
		view2.addCellToRow(descriptionCell, 1);
		
		Cell marriedCell = new Cell(MARITAL_STATUS_FIELD , MARITAL_STATUS_COLUMN_NAME);
		marriedCell.setEditor(new CheckBoxEditor(true));
		marriedCell.setRowSpan(2);
		view2.addCellToRow(marriedCell,0);
		
		
		//We define a cell containing the appreciation,
		//and add it to the first row.
		//This cell has a rowspan of 2
		Cell appreciationCell = new Cell(APPRECIATION_FIELD, APPRECIATION_COLUMN_NAME);
		appreciationCell.setEditor(new ComboBoxEditor(APPRECIATIONS));
		appreciationCell.setRowSpan(new Integer(2));
		appreciationCell.setWidth("80px");
		view2.addCellToRow(appreciationCell,0);
		
		//We define a cell containing an constant value (an image)
		//and add it just like the previous one
		Cell imageCell = new Cell("Send e-mail");
		imageCell.setDefaultValue("<a href='mailto:johnDoe@tatami.google.code'><img src='./images/mail_forward.png' alt='send mail'></img></a>");
		imageCell.setRowSpan(new Integer(2));
		imageCell.setWidth("auto");
		view2.addCellToRow(imageCell,0);
		//We add the views to the layout
		gridLayout.addView(view);
		gridLayout.addView(view2);
	}
	
	public Grid getGrid() {
		return grid;
	}
	
}
