package com.objetdirect.tatami.demo.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.objetdirect.tatami.client.Button;
import com.objetdirect.tatami.client.gfx.Color;
import com.objetdirect.tatami.client.gfx.GraphicCanvas;
import com.objetdirect.tatami.client.gfx.Rect;
import com.objetdirect.tatami.client.gfx.VirtualGroup;
import com.objetdirect.tatamix.client.hmvc.DefaultView;
import com.objetdirect.tatamix.client.hmvc.ViewEvent;

/**
 * This view aims to produce some bugs that have been reported by 
 * some users on Tatami components.
 * 
 * @author vgrassaud
 *
 */
public class BugView extends DefaultView implements TatamiDemoEvent, ClickHandler {

	//the bugs
	private  enum  BUG {
		BUG_GFX,BUG_GFX_DIALOG
		
	} ;
	
	/**
	 * Map to associate a bug with a button.
	 */
	private Map<Button,BUG> buttonsMap;
	private FlowPanel content;
	
	/**
	 * Creates the view
	 */
	public BugView() {
		super();
		buttonsMap = new HashMap<Button,BUG>();
		initComponents();
		
	}
	
	/**
	 * Adds the button to execute the bug tests
	 */
	private void initComponents() {
				
		createButton(BUG.BUG_GFX,"GFX Bug","canvas size problem");
		createButton(BUG.BUG_GFX_DIALOG,"GFX Bug2","canvas not displayed in DialogBox");
		
		content = new FlowPanel();
		content.setStylePrimaryName("BugContent");
		add(content);
		
	}
	
	/**
	 * Creates a button. the created button is added to the view. 
	 * @param bug the bug to associate with the button
	 * @param label the label for the button
	 * @param title the title for the button can be <code>null</code>
	 * @return the created button.
	 */
	private Button createButton(BUG bug, String label,String title) {
		Button button = new Button();
		button.setLabel(label);
		button.setTitle(title);
		button.addClickHandler(this);
		buttonsMap.put(button,bug);
		add(button);
		return button;
	}
	
	
	/**
	 * A bug was reported on GFX components. 
	 * If the size of a <code>GraphicCanvas</code> is not set before that 
	 * the canvas is attached to the browser, the graphical objects are not displayed.
	 * The bug is fixed now. We can set the size of the canvas at any time. A default size is also 
	 * set on canvas.
	 * 
	 */
	public void testBugGFXByAlex() {
		GraphicCanvas canvas = createCanvas();
		content.add(canvas);
		canvas.setSize("600", "600");   
		}
	
	/**
	 * Creates a <code>GraphicalCanvas</code>
	 * @return a GraphicalCanvas
	 */
	private GraphicCanvas createCanvas() {
		  GraphicCanvas canvas = new GraphicCanvas();
		  VirtualGroup group = new VirtualGroup();
		  Rect rect = new Rect(20,20);
		  Rect rect2 = new Rect(20,20);
		  rect2.translate(20, 20);
			  canvas.setStyleName("canvas");
		  canvas.add(group, 20, 20);      
		  group.add(rect);
		  group.add(rect2);
		  group.add(new com.objetdirect.tatami.client.gfx.Text("CenterX:"+rect.getCenterX()));
		  rect.setFillColor(Color.OLIVE);
		  rect2.setFillColor(Color.OLIVE);
		  rect.setOpacity(50);
 
		
		   return canvas;
	}
	
	
	/**
	 * Test the bug with <code>GraphicalCanvas</code> displayed in 
	 * GWT <code>DialogBox</code>.
	 * In fact this is not a Tatami or Dojo bug. It's a problem with CSS and 
	 * Internet Explorer. 
	 * If we set a CSS style like this : 
	 * <pre>
	 * .gwt-DialogBox td {
     *  text-align: right;
     *  }
	 * </pre>
	 * The canvas won't be displayed, because the canvas takes the property
	 *  position with the absolute value. 
	 * Here there is an article which explains how IE manage the layout : 
	 * 
	 *  <a href="http://www.satzansatz.de/cssd/onhavinglayoutrev04-20050824.html">http://www.satzansatz.de/cssd/onhavinglayoutrev04-20050824.html (en)</a><br>
	 *  <a href="http://www.test.blog-and-blues.org/haslayout/index.html"> http://www.test.blog-and-blues.org/haslayout/index.html(fr)</a>
	 */
	public void testBugGXFOnDialog() {
		DialogBox dialog = new DialogBox(true);
		dialog.setText("GFX dialog");
		GraphicCanvas canvas = createCanvas();
		canvas.setDimensions(300, 300);
		dialog.setWidget(canvas);
		dialog.center();
		
		
	}
	
	
	public void onClick(ClickEvent event) {
		
		BUG bug = this.buttonsMap.get(event.getSource());
		if ( bug !=null) {
			manageButton(bug);
		}
		
		
	}
	
	/**
	 * Displays the test that reproduce a bug reported by a user of Tatami
	 * @param bug the bug to manage
	 */
	public void manageButton(BUG bug) {
		switch (bug) {
		 default: {
			 fire(new ViewEvent(SHOW_HOME,this));
			 break;
		 }
		 case BUG_GFX: {
			 testBugGFXByAlex();
			 break;
		 }
		 case BUG_GFX_DIALOG: {
			 testBugGXFOnDialog();
			 break;
		 }
		
		}
	}
	
}//end of class
