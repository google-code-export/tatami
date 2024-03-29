package com.objetdirect.tatamix.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Widget;



  public class PaginationBar extends Composite implements Enablable, HasChangeHandlers {
    //UI components
	private FlowPanel layout;
	private SpanPanel pagesPanel;
	private ImageLink firstPage;
	private ImageLink previousPage;
	private ImageLink lastPage;
	private ImageLink nextPage;

    //logical properties
	private int currentPage;
    private int size;
    private boolean enabled;
  
    private String separator = ", ";

    //actions
    private AbstractAction nextAction;
    private AbstractAction lastAction;
    private AbstractAction previousAction;
    private AbstractAction firstAction;

    /**
     * Creates a new Bar with only one page.
     * The default separator is ","
     *
     */
	public PaginationBar() {
		super();
		//inits the properties
		size = 1;
		currentPage = 1;
		enabled = true;
		layout = new FlowPanel();
		initWidget(layout);
		setStylePrimaryName("paginationBar");
		initComponents();
		initActions();
	}

	
	
	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return  addHandler(handler, ChangeEvent.getType());
	}
	
	
	/**
	 * Inits the UI components
	 *
	 */
	private void initComponents() {
		firstPage = new ImageLink();
		nextPage = new ImageLink();
		lastPage = new ImageLink();
		previousPage = new ImageLink();


		SpanPanel buttonBackPanel = new SpanPanel();
		buttonBackPanel.add(firstPage);
		buttonBackPanel.add(previousPage);
		layout.add(buttonBackPanel);
		pagesPanel = new SpanPanel();
		pagesPanel.setStylePrimaryName("pages");
		layout.add(pagesPanel);
		generatePages();
		SpanPanel buttonNextPanel = new SpanPanel();
		buttonNextPanel.add(nextPage);
		buttonNextPanel.add(lastPage);
		layout.add(buttonNextPanel);
	}

	/**
	 * Sets the number of pages available. Call this method reset the current page to 1.
	 * @param pages number of pages
	 */
	public void setSize(int pages) {
		this.size = pages;
		this.currentPage=1;
		generatePages();
	}

	/**
	 * Sets the separator character between each page.
	 * @param separator a string separator.
	 */
	public void setSeparator(String separator) {
		this.separator = separator;
	}

	/**
	 * Inits the action fo change pages
	 *
	 */
	private void initActions() {
		nextAction =  new AbstractAction() {
			public void execute() {
               setCurrentPage(getCurrentPage()+1);
			}
		};
		prepareAction(nextAction,"Next","Next page","fleche_suiv.gif",nextPage);

		previousAction =  new AbstractAction() {
			public void execute() {
				 setCurrentPage(getCurrentPage()-1);
			}
		};
		prepareAction(previousAction,"Previous","Previous page","fleche_prec.gif",previousPage);

		lastAction =  new AbstractAction() {
			public void execute() {
				if ( currentPage != size) {
				  setCurrentPage(size);
				}
			}
		};
		prepareAction(lastAction,"Last","Last page","fleche_droite.gif",lastPage);

		firstAction =  new AbstractAction() {
			public void execute() {
				if ( currentPage != 1) {
				setCurrentPage(1);
				}
			}
		};
		prepareAction(firstAction,"First","First page","fleche_gauche.gif",firstPage);


	}

    /**
     * Adds some properties to the action and links the action with the <code>ImageLink</code>
     * @param action
     * @param name
     * @param title
     * @param icon
     * @param link
     */
	private void prepareAction(AbstractAction action,String name,String title,String icon,ImageLink link) {
		action.putValue(AbstractAction.NAME,name);
		action.putValue(AbstractAction.LONG_DESCRIPTION,title);
		action.putValue(AbstractAction.ICON_SRC, GWT.getModuleBaseURL() + "/images/" + icon);
        link.setCommand(action);

	}



	/**
	 * Returns the current page
	 * @return the current page
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * Sets the current page to select
	 * @param newPage the new page to select
	 */
	public void setCurrentPage(int newPage) {
        if ( newPage > 0 && newPage <= size) {
        	currentPage = newPage;
        	generatePages();
        	if ( enabled)  {
        		NativeEvent event = Document.get().createChangeEvent();
        		DomEvent.fireNativeEvent(event, this);
        	}
        	
        	
        }
	}


   /**
    *
    */
	public boolean isEnabled() {
		return this.enabled;
	}

    /**
     * Enables or disables the bar
     **/
	public void setEnabled(boolean b) {
      this.enabled = b;
      this.nextAction.setEnabled(b);
      this.lastAction.setEnabled(b);
      this.firstAction.setEnabled(b);
      this.previousAction.setEnabled(b);

	}

	/**
	 * Sets the given <code>ImageLink</code>
	 * @param img the <code>ImageLink</code> to set
	 * @param url the url of the icon
	 * @param alt the alt attribute to use
	 * @param title the title
	 */
    private void setImageLink(ImageLink img, String url, String alt,String title) {
    	img.setImageSrc(url);
    	img.setAlt(alt);
    	img.setTitle(title);
    }

     /**
      *Sets the image for button "next page"
      * @param url the url for the icon
      * @param alt the alt attribute used if the url is not found
      * @param title the tooltip text
      */
	public void setNextImage(String url, String alt,String title) {
		setImageLink(nextPage,url,alt,title);
    }


    /**
     * Sets the image for button "last page"
     * @param url url the url for the icon
     * @param alt  the alt attribute used if the url is not found
     * @param title he tooltip text
     */
    public void setLastImage(String url, String alt,String title) {
    	setImageLink(lastPage,url,alt,title);
    }
    /**
     * Sets the image for button "previous page"
     * @param url url the url for the icon
     * @param alt  the alt attribute used if the url is not found
     * @param title he tooltip text
     */
    public void setPreviousImage(String url,String alt,String title) {
    	setImageLink(previousPage,url,alt,title);
    }

    /**
     * Sets the image for button "first page"
     * @param url url the url for the icon
     * @param alt  the alt attribute used if the url is not found
     * @param title he tooltip text
     */
    public void setFirstImage(String url,String alt,String title) {
    	setImageLink(firstPage,url,alt,title);
    }

    /**
     * Generates the list of pages, between the buttons.
     * This method is called each times that the method <code>setCurrentPage</code> is called
     *
     */
    private void generatePages() {
    	pagesPanel.clear();

        for ( int i=1;i<=size;i++) {
        	if ( i == currentPage) {
        		pagesPanel.add(new CurrentPage());
        	} else {
        		Page p = new Page(i);
        		pagesPanel.add(p);
        	}
        	if ( i != size) {
        	pagesPanel.add(new Separator());
        	}
        }
    }


    /**
     *
     * @author tvzl8571
     *
     */
    private class Page extends FocusWidget {

    	private int page;

    	public Page(int number ) {
    		this.page = number;
    		setElement(Document.get().createAnchorElement());
    		
    		getElement().setAttribute("href", "#");
    		getElement().setInnerText(String.valueOf(page));
            addClickHandler(new ClickHandler() {
            	public void onClick(ClickEvent event) {
            		setCurrentPage(page);
            	}
            });
    	}


    	 public void onBrowserEvent(Event event) {
    		    if (DOM.eventGetType(event) == Event.ONCLICK) {
     		      DOM.eventPreventDefault(event);
    		    }
    		    super.onBrowserEvent(event);
    		  }
    }

    private class Separator extends Widget {
    	public Separator() {
    		setElement(Document.get().createSpanElement());
            getElement().setInnerText(separator);
    	}
    }

    private class CurrentPage extends Widget {
    	public CurrentPage() {
    		setElement(Document.get().createElement("strong"));
    		getElement().setInnerText(String.valueOf(currentPage));
    	}
    }

}//end of class
