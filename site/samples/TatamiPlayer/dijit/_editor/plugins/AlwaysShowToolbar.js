if(!dojo._hasResource["dijit._editor.plugins.AlwaysShowToolbar"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit._editor.plugins.AlwaysShowToolbar"] = true;
dojo.provide("dijit._editor.plugins.AlwaysShowToolbar");

dojo.declare("dijit._editor.plugins.AlwaysShowToolbar", dijit._editor._Plugin,
	{
	// summary:
	//		For auto-expanding editors, this plugin will keep the
	//		editor's toolbar visible even when the top of the editor
	//		has scrolled off the top of the viewport (usually when editing a long
	//		document).
	// description:
	//		Specify this in extraPlugins (or plugins) parameter and also set
	//		height to "".
	// example:
	//	|	<div dojoType="dijit.Editor" height=""
	//	|	extraPlugins="['dijit._editor.plugins.AlwaysShowToolbar']">

	_handleScroll: true,
	setEditor: function(e){
		if(!e.iframe){
			console.log('Port AlwaysShowToolbar plugin to work with Editor without iframe');
			return;
		}

		this.editor = e;

		e.onLoadDeferred.addCallback(dojo.hitch(this, this.enable));
	},
	enable: function(d){
		this._updateHeight();
		this.connect(window, 'onscroll', "globalOnScrollHandler");
		this.connect(this.editor, 'onNormalizedDisplayChanged', "_updateHeight");
		return d;
	},
	_updateHeight: function(){
		// summary:
		//		Updates the height of the editor area to fit the contents.
		var e = this.editor;
		if(!e.isLoaded){ return; }
		if(e.height){ return; }

		var height = dojo.marginBox(e.editNode).h;
		if(dojo.isOpera){
			height = e.editNode.scrollHeight;
		}
		// console.debug('height',height);
		// alert(this.editNode);

		//height maybe zero in some cases even though the content is not empty,
		//we try the height of body instead
		if(!height){
			height = dojo.marginBox(e.document.body).h;
		}

		if(height == 0){
			console.debug("Can not figure out the height of the editing area!");
			return; //prevent setting height to 0
		}
		if(height != this._lastHeight){
			this._lastHeight = height;
			// this.editorObject.style.height = this._lastHeight + "px";
			dojo.marginBox(e.iframe, { h: this._lastHeight });
		}
	},
	_lastHeight: 0,
	globalOnScrollHandler: function(){
		var isIE6 = dojo.isIE < 7;
		if(!this._handleScroll){ return; }
		var tdn = this.editor.toolbar.domNode;
		var db = dojo.body;

		if(!this._scrollSetUp){
			this._scrollSetUp = true;
			this._scrollThreshold = dojo._abs(tdn, true).y;
//			console.log("threshold:", this._scrollThreshold);
			//what's this for?? comment out for now
//			if((isIE6)&&(db)&&(dojo.style(db, "backgroundIimage")=="none")){
//				db.style.backgroundImage = "url(" + dojo.uri.moduleUri("dijit", "templates/blank.gif") + ")";
//				db.style.backgroundAttachment = "fixed";
//			}
		}

		var scrollPos = dojo._docScroll().y;
		var s = tdn.style;

		if(scrollPos > this._scrollThreshold && scrollPos < this._scrollThreshold+this._lastHeight){
			// dojo.debug(scrollPos);
			if(!this._fixEnabled){
				var tdnbox = dojo.marginBox(tdn);
				this.editor.iframe.style.marginTop = tdnbox.h+"px";

				if(isIE6){
					s.left = dojo._abs(tdn).x;
					if(tdn.previousSibling){
						this._IEOriginalPos = ['after',tdn.previousSibling];
					}else if(tdn.nextSibling){
						this._IEOriginalPos = ['before',tdn.nextSibling];
					}else{
						this._IEOriginalPos = ['last',tdn.parentNode];
					}
					dojo.body().appendChild(tdn);
					dojo.addClass(tdn,'dijitIEFixedToolbar');
				}else{
					s.position = "fixed";
					s.top = "0px";
				}

				dojo.marginBox(tdn, { w: tdnbox.w });
				s.zIndex = 2000;
				this._fixEnabled = true;
			}
			// if we're showing the floating toolbar, make sure that if
			// we've scrolled past the bottom of the editor that we hide
			// the toolbar for this instance of the editor.

			// TODO: when we get multiple editor toolbar support working
			// correctly, ensure that we check this against the scroll
			// position of the bottom-most editor instance.
			var eHeight = (this.height) ? parseInt(this.editor.height) : this.editor._lastHeight;
			s.display = (scrollPos > this._scrollThreshold+eHeight) ? "none" : "";
		}else if(this._fixEnabled){
			this.editor.iframe.style.marginTop = '';
			s.position = "";
			s.top = "";
			s.zIndex = "";
			s.display = "";
			if(isIE6){
				s.left = "";
				dojo.removeClass(tdn,'dijitIEFixedToolbar');
				if(this._IEOriginalPos){
					dojo.place(tdn, this._IEOriginalPos[1], this._IEOriginalPos[0]);
					this._IEOriginalPos = null;
				}else{
					dojo.place(tdn, this.editor.iframe, 'before');
				}
			}
			s.width = "";
			this._fixEnabled = false;
		}
	},
	destroy: function(){
		this._IEOriginalPos = null;
		this._handleScroll = false;
		dojo.forEach(this._connects, dojo.disconnect);
//		clearInterval(this.scrollInterval);

		if(dojo.isIE < 7){
			dojo.removeClass(this.editor.toolbar.domNode, 'dijitIEFixedToolbar');
		}
	}
});

}
