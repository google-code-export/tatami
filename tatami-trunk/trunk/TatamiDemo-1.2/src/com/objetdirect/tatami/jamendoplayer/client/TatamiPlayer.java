package com.objetdirect.tatami.jamendoplayer.client;

import java.util.Collection;
import java.util.Iterator;





import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalSplitPanelWithEvents;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.Button;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.dnd.DnD;
import com.objetdirect.tatami.client.dnd.DnDGenericBehavior;
import com.objetdirect.tatami.client.dnd.DndTreeElement;
import com.objetdirect.tatami.client.dnd.IDnDSource;
import com.objetdirect.tatami.client.dnd.IDnDTarget;
import com.objetdirect.tatami.client.dnd.WidgetTarget;
import com.objetdirect.tatami.client.dnd.DnDBehaviors.BehaviorScopeException;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TatamiPlayer implements EntryPoint {

	
	
	Panel centerPanel;
	Panel treePanel;
	Panel gridPanel;
	Panel headerPanel;
	Panel controlBarPanel;
	Panel buttonPanel;
	Panel contextInfoPanel;
	HorizontalSplitPanelWithEvents splitPanel;
	RootPanel main;
	Panel searchPanel;
	PopupPanel savePlaylistDialog = new SavePlaylistDialog();
	PopupPanel loadPlaylistDialog = new LoadPlaylistDialog();
	
	public void onModuleLoad() {
		main = RootPanel.get("mainPanel");
		main.setVisible(false);
		initHeaderPanel();
		initControlBar();
		initCenterPanel();
		RootPanel.get().add(new OSDToaster());
		main.add(headerPanel);
		main.add(controlBarPanel);
		main.add(centerPanel);
		initDnd();
		assignIds();
		Window.addWindowResizeListener(new WindowResizeListener() {
			public void onWindowResized(int width, int height) {
				splitPanel.setSize("100%", "100%");
				splitPanel.setSplitPosition("20%");
				main.setSize("0","0");
				main.setSize("100%","100%");
			}
		});
		resizeGrid();
		main.setVisible(true);
		DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById("splashscreen"));
		splitPanel.setSplitPosition("20%");
	}
	
  
	private void assignIds(){
		DOM.setElementAttribute(centerPanel.getElement(), "id", "centerPanel");
		DOM.setElementAttribute(treePanel.getElement(), "id", "treePanel");
		DOM.setElementAttribute(gridPanel.getElement(), "id", "gridPanel");
		DOM.setElementAttribute(headerPanel.getElement(), "id", "headerPanel");
		DOM.setElementAttribute(controlBarPanel.getElement(), "id", "controlBarPanel");
	}
	
	private void initCenterPanel(){
		initTreePanel();
		initGridPanel();
		centerPanel = new SimplePanel();
		splitPanel = new HorizontalSplitPanelWithEvents();
		splitPanel.addChangeListener(new ChangeListener(){
			public void onChange(Widget sender) {
				resizeGrid();
			}
		});
		splitPanel.add(treePanel);
		splitPanel.add(gridPanel);
		centerPanel.add(splitPanel);
		splitPanel.setSplitPosition("20%");
		resizeGrid();
	}
	
	
	
	
	private void resizeGrid(){
		gridPanel.setSize(splitPanel.getRightWidth()+"px" , "100%");
		PlaylistGrid.getInstance().setSize("100%","49em");
	}
	
	
	private void initHeaderPanel(){
		headerPanel = new FlowPanel();
		HTML tatamiLink = new HTML("<a href='http://code.google.com/p/tatami/'></a>");
		HTML jamendoLink = new HTML("<a href='http://www.jamendo.com'></a>");
		tatamiLink.setStyleName("tatamiLink");
		jamendoLink.setStyleName("jamendoLink");
		headerPanel.add(tatamiLink);
		headerPanel.add(jamendoLink);
		headerPanel.add(new HTML("Welcome to Tatami Jamendo player !  "));
	}
  
  private void initTreePanel(){
	  treePanel = new SimplePanel();
	  SearchTree.getInstance().setSize("100%", "100%");
	  treePanel.add(SearchTree.getInstance());
	  return ;
  }
  
  private void initGridPanel(){
	  gridPanel = new SimplePanel();
	  SearchTree.getInstance().getStore().addLoadItemListener(PlaylistGrid.getInstance());
	  gridPanel.add(PlaylistGrid.getInstance());
	  Player.addOnPlayCallback(PlaylistGrid.getInstance());
  }
  
  
  
  
  private void initControlBar(){
	 controlBarPanel = new FlowPanel();
	 controlBarPanel.add(new CurrentInfoPanel());
	 controlBarPanel.add(new ControlPanel());
	 Button clearPlaylistButton = new Button("",new ClickListener(){
			public void onClick(Widget sender) {
				PlaylistGrid.getInstance().clearGrid();
			}
		});
	 clearPlaylistButton.setIconClass("clearPlaylistIcon");
	 clearPlaylistButton.setStyleName("loadPlaylistButton");
	 Button loadPlaylistButton = new Button("",new ClickListener(){
			public void onClick(Widget sender) {
				loadPlaylistDialog.show();
			}
		});
	 loadPlaylistButton.setIconClass("loadPlaylistIcon");
	 loadPlaylistButton.setStyleName("loadPlaylistButton");
	 Button savePlaylistButton = new Button("",new ClickListener(){
			public void onClick(Widget sender) {
				savePlaylistDialog.show();
			}
		});
	 savePlaylistButton.setIconClass("savePlaylistIcon");
	 savePlaylistButton.setStyleName("savePlaylistButton");
	 controlBarPanel.add(clearPlaylistButton);
	 controlBarPanel.add(loadPlaylistButton);
	 controlBarPanel.add(savePlaylistButton);
  }
  
  private void initDnd(){
	  DnD.registerTreeSource(SearchTree.getInstance());
	  DnD.registerTarget(gridPanel);
	  DnD.registerElement(gridPanel, PlaylistGrid.getInstance());
	  try {
		DnD.registerBehavior(new DnDGenericBehavior<DndTreeElement>(){
			@Override
			public boolean onDrop(Collection<DndTreeElement> dndElements,
					IDnDSource<? extends DndTreeElement> source,
					IDnDTarget target, String targetNodeId, boolean isCopy) {
				if(((WidgetTarget)target).getPanel() == gridPanel){
					for (Iterator<DndTreeElement> iterator = dndElements.iterator(); iterator
							.hasNext();) {
						DndTreeElement dndTreeElement =  iterator
								.next();
						Item item = dndTreeElement.getTreeItem();
						try{
							PlaylistGrid.getInstance().addItemToPlaylist(item);
						}catch(Exception e){
							Window.alert("You can only add artists, albums, or tracks to the playlist");
						}
					}
					return true;
				}
				return false;
			}
		  }, SearchTree.getInstance(), gridPanel);
	  	} catch (BehaviorScopeException e) {
	  		e.printStackTrace();
	  	}
  }
  
  
  
}
