package com.objetdirect.tatami.jamendoplayer.client;

import java.util.Collection;
import java.util.Iterator;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
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
import com.objetdirect.tatami.client.layout.BorderContainer;
import com.objetdirect.tatami.client.layout.ContentPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TatamiPlayer implements EntryPoint {

	
	
	ContentPanel headerPanel;
	FlowPanel buttonPanel;
	RootPanel main;
	Panel searchPanel;
	ContentPanel gridPanel;
	ContentPanel bottomcontainer;
	ContentPanel treePanel;
	ContentPanel centerPanel;
	PopupPanel savePlaylistDialog = new SavePlaylistDialog();
	PopupPanel loadPlaylistDialog = new LoadPlaylistDialog();
	
	public void onModuleLoad() {
		initHeaderPanel();
		initCenterPanel();
		initBottomBar();
		initTreePanel();
		BorderContainer container = new BorderContainer();
		DOM.setElementAttribute(container.getElement(),"id","mainPanel");
		
		container.add(headerPanel,"top");
		container.add(bottomcontainer,"bottom");
		container.add(treePanel,BorderContainer.REGION_LEFT,true,"50","Infinity");
		container.add(centerPanel,BorderContainer.REGION_CENTER);
		cancelSplashScreenScript();
		DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById("splashscreen"));
		RootPanel.get().add(new OSDToaster());
		RootPanel.get().add(container);
		initDnd();
	}
  
	private native void cancelSplashScreenScript()/*-{
		$wnd.isLoaded = true;
	}-*/;
	
	private void initHeaderPanel(){
		FlowPanel header = new FlowPanel();
		headerPanel = new ContentPanel(header);
		headerPanel.setHeight("100px");
		HTML tatamiLink = new HTML("<a href='http://code.google.com/p/tatami/'></a>");
		HTML jamendoLink = new HTML("<a href='http://www.jamendo.com'></a>");
		tatamiLink.setStyleName("tatamiLink");
		jamendoLink.setStyleName("jamendoLink");
		header.add(tatamiLink);
		header.add(jamendoLink);
		header.add(new HTML("Welcome to Tatami Jamendo player !  "));
	}
  
  private void initCenterPanel(){
	  initGridPanel();
	  BorderContainer centerContainer = new BorderContainer();
	  centerPanel = new ContentPanel(centerContainer);
	  FlowPanel playlistControls = new FlowPanel();
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
	 playlistControls.add(clearPlaylistButton);
	 playlistControls.add(loadPlaylistButton);
	 playlistControls.add(savePlaylistButton);
	 ContentPanel playlistControlsCP = new ContentPanel(playlistControls);
	 centerContainer.add(playlistControlsCP,BorderContainer.REGION_TOP);
	 centerContainer.add(gridPanel,BorderContainer.REGION_CENTER);
	 centerContainer.setGutters(false);
	 centerContainer.setSize("100%","100%");
  }
	
  private void initGridPanel(){
	  SearchTree.getInstance().getStore().addLoadItemListener(PlaylistGrid.getInstance());
	  gridPanel = new ContentPanel(PlaylistGrid.getInstance());
  }
  private void initTreePanel(){
	  treePanel = new ContentPanel(SearchTree.getInstance());
	  treePanel.setWidth("20%");
  }
  
  private void initBottomBar(){
	initControlBar();
	BorderContainer bottom = new BorderContainer();
	bottomcontainer = new ContentPanel(bottom);
	ContentPanel buttonCp = new ContentPanel(buttonPanel);
	bottom.add(buttonCp,BorderContainer.REGION_CENTER);
	ContentPanel currentInfoCp = new ContentPanel(new CurrentInfoPanel());
	currentInfoCp.setWidth("300px");
	bottom.add(currentInfoCp,BorderContainer.REGION_RIGHT);
	bottom.setHeight("100%");
	bottomcontainer.setHeight("100px");
  }
  
  private void initControlBar(){
	 buttonPanel = new ControlPanel();
  }
  
  private void initDnd(){
	  DnD.registerTreeSource(SearchTree.getInstance());
	  DnD.registerTarget(PlaylistGrid.getInstance());
	  try {
		DnD.registerBehavior(new DnDGenericBehavior<DndTreeElement>(){
			@Override
			public boolean onDrop(Collection<DndTreeElement> dndElements,
					IDnDSource<? super DndTreeElement> source,
					IDnDTarget target, String targetNodeId, boolean isCopy) {
				if(((WidgetTarget)target).getWidget() == PlaylistGrid.getInstance()){
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

			@Override
			public void onDndStart(
					Collection<DndTreeElement> elementBeingDragged,
					IDnDSource<? super DndTreeElement> source, boolean ctrlKey) {
				super.onDndStart(elementBeingDragged, source, ctrlKey);
			}
		  }, SearchTree.getInstance(), PlaylistGrid.getInstance());
	  	} catch (BehaviorScopeException e) {
	  		e.printStackTrace();
	  	}
  }
  
  
  
}
