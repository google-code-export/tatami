package com.objetdirect.tatami.demo.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.FishEye;
import com.objetdirect.tatami.client.Toaster;
import com.objetdirect.tatamix.client.hmvc.DefaultView;
import com.objetdirect.tatamix.client.hmvc.Event;
import com.objetdirect.tatamix.client.hmvc.Processor;
import com.objetdirect.tatamix.client.hmvc.ViewEvent;
import com.objetdirect.tatamix.client.widget.AbstractAction;
import com.objetdirect.tatamix.client.widget.ImageLink;
import com.objetdirect.tatamix.client.widget.Paragraph;
import com.objetdirect.tatamix.client.widget.RoundedContainer;
import com.objetdirect.tatamix.client.widget.SpanText;
import com.objetdirect.tatamix.client.widget.Title;

/**
 * The main view of the demo
 * @author Vianney
 *
 */
public class MainView extends DefaultView implements TatamiDemoEvent {


	/**
	 * Content panel, the view are displayed in it. 
	 */
	private FlowPanel content;
	/**
	 * the container for the menu.
	 */
	private FlowPanel menuContainer;
	/**
	 * the header container
	 */
	private FlowPanel header;
	/**
	 * the fisheye Tatami menu.
	 */
	private FishEye menu;
    /**
     * the toaster that displays messages when a item of the menu 
     * is selected. 
     * 
     */
	private Toaster toaster;

	/**
	 * Creates the view.
	 * This view register the events : 
	 * <ul>
	 * <li><strong>UPDATE_CONTENT</strong> to change the content panel</li>
	 * <li><strong>SHOW_HOME</strong> to display the home content</li>
	 * <li><strong>DEBUG</strong> to add the debug item to the menu</li>
	 * </ul>
	 */
	public MainView() {
		super();
		
		
		initComponents();

		Processor updateContent = new Processor() {
			public void run(Event event) {
                if ( event.getData() instanceof Widget) {
                	content.clear();
                	content.add((Widget)event.getData());
                }
			}
		};

		Processor showHome = new Processor() {
			public void run(Event event) {
				initHomeContent();
			}
		};

		
		Processor debug = new Processor() {
			public void run(Event event) {
				initDebugMode();
			}
		};

		register(UPDATE_CONTENT,updateContent);
		register(SHOW_HOME,showHome);
		register(DEBUG,debug);
	}

	
	/**
	 * Adds the Debug item to the menu
	 */
	private void initDebugMode() {
		addMenuItem("debug.png","Debug Test","Bugs to test",SHOW_BUG);
			
	}
	
	
    /**
     * Initializes UI components of the view
     */
	private void initComponents() {
		//sets the header
		header = initHeader();
		header.setStylePrimaryName("header");
		add(header);
	    toaster = new Toaster("message",Toaster.BOTTOM_LEFT_UP);
        initMenu();
        menuContainer = new FlowPanel();
        menuContainer.add(menu);
    
        menuContainer.setStylePrimaryName("menu");
        add(menuContainer);
        content = new FlowPanel();
        content.setStylePrimaryName("content");
        initHomeContent();
        add(content);
        add(toaster);
    }

    /**
     * Initializes the header container
     * @return the header container
     */
	private FlowPanel initHeader() {
		FlowPanel header = new FlowPanel();
		Title title = new Title();
		title.setText(TatamiDemo.getMessages().title_main());
		Title dojo = new Title(Title.H2);
		dojo.setText(TatamiDemo.getMessages().title_dojo());

		ImageLink odLink = new ImageLink();
		odLink.setImageSrc(TatamiDemo.getIconURL("od_logo.png"));
		odLink.setAlt("Objet Direct");
		odLink.setStylePrimaryName("od");
		odLink.setHref("http://www.objetdirect.com");
		ImageLink ftLink = new ImageLink();
		ftLink.setImageSrc(TatamiDemo.getIconURL("logo_ft.gif"));
		ftLink.setAlt("France Telecom");
		ftLink.setHref("http://www.francetelecom.com");
		ftLink.setStylePrimaryName("ft");
		header.add(odLink);
		header.add(ftLink);
		header.add(title);
		header.add(dojo);
         
		return header;
	}

    /**
     * Initializes the home content.
     */
	private void initHomeContent() {
		content.clear();
		RoundedContainer wrapper = new RoundedContainer();
		Paragraph p = new Paragraph();
		p.setHTML(TatamiDemo.getMessages().paragraph_home_first());
		wrapper.addWidget(p);
		Paragraph p2 = new Paragraph();
		p2.setHTML(TatamiDemo.getMessages().paragraph_home_second());
		wrapper.addWidget(p2);
		Paragraph p3 = new Paragraph();
		p3.setHTML(TatamiDemo.getMessages().paragraph_home_third());
		wrapper.addWidget(p3);
		content.add(wrapper);
	}

/**
 * Initializes the fisheye menu.
 */
	private void initMenu() {
		 menu = new FishEye();
		 addMenuItem("globe.png",TatamiDemo.getMessages().menu_home(),TatamiDemo.getMessages().menu_home_explain(),SHOW_HOME);
		 addMenuItem("slider.png",TatamiDemo.getMessages().menu_slider(),TatamiDemo.getMessages().menu_slider_explain(),SHOW_SLIDER_DEMO);
		 addMenuItem("clock.png",TatamiDemo.getMessages().menu_dateTime(),TatamiDemo.getMessages().menu_dateTime_explain(),SHOW_DATE_TIME_DEMO);
		 addMenuItem("color.png",TatamiDemo.getMessages().menu_color(),TatamiDemo.getMessages().menu_color_explain(),SHOW_COLOR_DEMO);
		 addMenuItem("amor.png",TatamiDemo.getMessages().menu_dnd(),TatamiDemo.getMessages().menu_dnd_explain(),SHOW_DND_DEMO);
		 addMenuItem("gfx.png",TatamiDemo.getMessages().menu_gfx(),TatamiDemo.getMessages().menu_gfx_explain(),SHOW_GFX_DEMO);
		 addMenuItem("x_office_spreadsheet.png",TatamiDemo.getMessages().menu_grid(),TatamiDemo.getMessages().menu_grid_explain(),SHOW_GRID_DEMO);
		 addMenuItem("chartMenuIcon.png",TatamiDemo.getMessages().menu_chart_and_layout() ,TatamiDemo.getMessages().menu_chart_and_layout_explain(),SHOW_LAYOUT_AND_CHART_DEMO);
		 addMenuItem("x-office-drawing.png",TatamiDemo.getMessages().menu_chart() ,TatamiDemo.getMessages().menu_chart_explain(),SHOW_CHART_DEMO);
	}


	/**
	 * Adds a item to the fisheye menu.
	 * The menu should be instantiated before.
	 * @param icon the icon file to represent the item.
	 * @param label the label for the item.
	 * @param desc a short description for the item.
	 * @param eventID the event fired when the item will be selected.
	 */
	private void addMenuItem(String icon,String label,String desc,String eventID) {
		String urlIcon = TatamiDemo.getIconURL(icon);
		MenuCommand cmd = new MenuCommand(eventID,urlIcon,label,desc);
		menu.add(urlIcon,label,cmd);
	}


    /**
     * Inner class to associate logic event with the <code>Command</code>
     * of the fisheye menu.
     * @author vgrassaud
     *
     */
	private class MenuCommand extends AbstractAction {
		private String eventID = "-1";

		public MenuCommand(String eventID,String icon,String name,String message) {
			this.eventID = eventID;
		    putValue(ICON_SRC,icon);
		    putValue(NAME,name);
		    putValue(LONG_DESCRIPTION,message);
		}

		public void execute() {
			Toaster.publishMessage("message", getMessage());
			fire(new ViewEvent(eventID,MainView.this));
		}


		/**
		 * Creates the message for the Toggler component
		 * @return The HTML code corresponding to a message
		 */
		private String getMessage() {
			StringBuffer buffer = new StringBuffer();
			String src = (String)getValue(ICON_SRC);
			SpanText span = new SpanText();
			span.setHTML((String)getValue(LONG_DESCRIPTION));
			buffer.append(span);
			buffer.append(" - ");
			Image img = new Image(src);
			buffer.append(img);
			return buffer.toString();
		}

	}

}//end of class
