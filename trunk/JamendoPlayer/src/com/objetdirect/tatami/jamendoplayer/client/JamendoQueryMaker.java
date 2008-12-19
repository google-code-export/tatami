package com.objetdirect.tatami.jamendoplayer.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

public class JamendoQueryMaker {
	
	
	interface JSONHandler{ 
		public void handleJSON(JavaScriptObject obj ,  SearchTreeItem item);
	}
	
	
	private static class RequestObject{
		private JSONHandler handler;
		private SearchTreeItem item;
		private Element scriptElem;
		
		
		public Element getScriptElem() {
			return scriptElem;
		}
		public JSONHandler getHandler() {
			return handler;
		}
		public RequestObject(JSONHandler handler, SearchTreeItem item, Element scriptElem) {
			super();
			this.handler = handler;
			this.item = item;
			this.scriptElem = scriptElem;
		}
		public SearchTreeItem getItem() {
			return item;
		}
	}
	
	static{
		declareCallback();
	}
	
	private static int idgenerator = 0;

	private static  Map<Integer , RequestObject> idHandlersMap = new HashMap<Integer, RequestObject>();

	public static void makeJSONRequest(String url, JSONHandler handler , SearchTreeItem item){
		idgenerator++;
		Element elem = DOM.createElement("script");
		DOM.setElementAttribute(elem, "type", "text/javascript");
		DOM.setElementAttribute(elem, "src", url + "&jsoncallbackfunction=jsonCallback&jsoncallbackid="+idgenerator);
		idHandlersMap.put(idgenerator, new RequestObject(handler,item ,elem));
		DOM.appendChild(RootPanel.getBodyElement(), elem);
	}
	
	private static native void declareCallback()/*-{
		if($wnd.jsonCallback == undefined){
			$wnd.jsonCallback = function(id , jsonObj) {
				@com.objetdirect.tatami.jamendoplayer.client.JamendoQueryMaker::dispatchJSON(Lcom/google/gwt/core/client/JavaScriptObject;I)(jsonObj, id);
			}
		}
	}-*/;
	
	public static void dispatchJSON(JavaScriptObject jsonObj, int id){
		RequestObject request = idHandlersMap.remove(id);
		DOM.removeChild((Element) request.getScriptElem().getParentElement(),request.getScriptElem());
		request.getHandler().handleJSON(jsonObj, request.getItem());
	}

	public static native JavaScriptObject extractItemsFromResponse(JavaScriptObject object , int i)/*-{
		return object[i];
	}-*/;

	public static native int getLength(JavaScriptObject object)/*-{
		return object.length;
	}-*/;

	static native String extractFieldFromOBJ(JavaScriptObject jsonItem , String fieldName)/*-{
		return jsonItem[fieldName] +"";
	}-*/;

	public static  void searchGenre(JSONHandler handler, SearchTreeItem parent){
		makeJSONRequest("http://api.jamendo.com/get2/id+idstr+weight/tag/jsoncallback/?order=rating_desc&n=50", handler,  parent);
	}

	public static  void searchAlbumByArtist(JSONHandler handler  , ArtistItem parent) {
		makeJSONRequest("http://api.jamendo.com/get2/id+name+url+image+genre+duration+artist_name/album/jsoncallback/?artist_id="+parent.getId(), handler,  parent);
	}

	public static void searchTracksByAlbum(JSONHandler handler , AlbumItem parent) {
		makeJSONRequest("http://api.jamendo.com/get2/track_id+track_name+track_url+track_duration+album_name+album_image+numalbum+artist_id+artist_name+album_url/track/jsoncallback/album_track+album_artist/?order=numalbum&album_id="+parent.getId(), handler,  parent);
	}

	public static void searchArtistByLetter(JSONHandler handler , LetterItem parent){
		String letter = parent.getLetter();
		makeJSONRequest("http://api.jamendo.com/get2/id+name+url+image+artist_name/artist/jsoncallback/?n=2000&artist_hasalbums=1&order=artist_name&artist_firstletter="+letter+"&tag_id="+parent.getParentItem().getId() , handler,  parent);
	}

	public static void getMusicById(String id , JSONHandler handler){
		makeJSONRequest("http://api.jamendo.com/get2/track_id+track_name+track_url+track_duration+album_name+album_image+numalbum+artist_id+artist_name/track/jsoncallback/album_track+album_artist/?order=numalbum&track_id="+id, handler,  null);
	}
	
	public static void getTrackTags(String id , JSONHandler handler){
		makeJSONRequest("http://www.jamendo.com/get2/name+weight/tag/jsoncallback/track_tag/?track_id="+id, handler,  null);
	}
	
}
