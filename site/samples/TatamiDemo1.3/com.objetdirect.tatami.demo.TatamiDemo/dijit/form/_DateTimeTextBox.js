if(!dojo._hasResource["dijit.form._DateTimeTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form._DateTimeTextBox"] = true;
dojo.provide("dijit.form._DateTimeTextBox");

dojo.require("dojo.date");
dojo.require("dojo.date.locale");
dojo.require("dojo.date.stamp");
dojo.require("dijit.form.ValidationTextBox");

/*=====
dojo.declare(
	"dijit.form._DateTimeTextBox.__Constraints",
	[dijit.form.RangeBoundTextBox.__Constraints, dojo.date.locale.__FormatOptions]
);
=====*/

dojo.declare(
	"dijit.form._DateTimeTextBox",
	dijit.form.RangeBoundTextBox,
	{
		// summary:
		//		A validating, serializable, range-bound date or time text box.
		//
		// constraints: dijit.form._DateTimeTextBox.__Constraints 

		/*=====
		constraints: {},
		======*/
		regExpGen: dojo.date.locale.regexp,
		compare: dojo.date.compare,
		format: function(/*Date*/ value, /*dojo.date.locale.__FormatOptions*/ constraints){
			//	summary: formats the value as a Date, according to constraints
			if(!value){ return ''; }
			return dojo.date.locale.format(value, constraints);
		},
		parse: function(/*String*/ value, /*dojo.date.locale.__FormatOptions*/ constraints){
			//	summary: parses the value as a Date, according to constraints
			return dojo.date.locale.parse(value, constraints) || (this._isEmpty(value)? null : undefined);
		},

		serialize: dojo.date.stamp.toISOString,

		//	value: Date
		//		The value of this widget as a JavaScript Date object.  Use attr("value") / attr("value", val) to manipulate.
		//		When passed to the parser in markup, must be specified according to `dojo.date.stamp.fromISOString`
		value: new Date(""),	// value.toString()="NaN"

		//	popupClass: String
		//		Name of the popup widget class used to select a date/time
		popupClass: "", // default is no popup = text only
		
		_selector: "",

		postMixInProperties: function(){
			//dijit.form.RangeBoundTextBox.prototype.postMixInProperties.apply(this, arguments);
			this.inherited(arguments);
			if(!this.value || this.value.toString() == dijit.form._DateTimeTextBox.prototype.value.toString()){
				this.value = null;
			}
			var constraints = this.constraints;
			constraints.selector = this._selector;
			constraints.fullYear = true; // see #5465 - always format with 4-digit years
			var fromISO = dojo.date.stamp.fromISOString;
			if(typeof constraints.min == "string"){ constraints.min = fromISO(constraints.min); }
 			if(typeof constraints.max == "string"){ constraints.max = fromISO(constraints.max); }
		},
		
		_onFocus: function(/*Event*/ evt){
			// summary: open the TimePicker popup
			this._open();
		},

		_setValueAttr: function(/*Date*/ value, /*Boolean?*/ priorityChange, /*String?*/ formattedValue){
			// summary:
			//	Sets the date on this textbox.  Note that `value` must be a Javascript Date object.
			this.inherited(arguments);
			if(this._picker){
				// #3948: fix blank date on popup only
				if(!value){value=new Date();}
				this._picker.attr('value', value);
			}
		},

		_open: function(){
			// summary:
			//	opens the TimePicker, and sets the onValueSelected value

			if(this.disabled || this.readOnly || !this.popupClass){return;}

			var textBox = this;

			if(!this._picker){
				var PopupProto=dojo.getObject(this.popupClass, false);
				this._picker = new PopupProto({
					onValueSelected: function(value){
						if(textBox._tabbingAway){
							delete textBox._tabbingAway;
						}else{
							textBox.focus(); // focus the textbox before the popup closes to avoid reopening the popup
						}
						setTimeout(dojo.hitch(textBox, "_close"), 1); // allow focus time to take

						// this will cause InlineEditBox and other handlers to do stuff so make sure it's last
						dijit.form._DateTimeTextBox.superclass._setValueAttr.call(textBox, value, true);
					},
					lang: textBox.lang,
					constraints: textBox.constraints,
					isDisabledDate: function(/*Date*/ date){
						// summary:
						// 	disables dates outside of the min/max of the _DateTimeTextBox
						var compare = dojo.date.compare;
						var constraints = textBox.constraints;
						return constraints && (constraints.min && (compare(constraints.min, date, "date") > 0) || 
							(constraints.max && compare(constraints.max, date, "date") < 0));
					}
				});
				this._picker.attr('value', this.attr('value') || new Date());
			}
			if(!this._opened){
				dijit.popup.open({
					parent: this,
					popup: this._picker,
					around: this.domNode,
					onCancel: dojo.hitch(this, this._close),
					onClose: function(){ textBox._opened=false; }
				});
				this._opened=true;
			}
			
			dojo.marginBox(this._picker.domNode,{ w:this.domNode.offsetWidth });
		},

		_close: function(){
			if(this._opened){
				dijit.popup.close(this._picker);
				this._opened=false;
			}			
		},

		_onBlur: function(){
			// summary: called magically when focus has shifted away from this widget and it's dropdown
			this._close();
			if(this._picker){
				// teardown so that constraints will be rebuilt next time (redundant reference: #6002)
				this._picker.destroy();
				delete this._picker;
			}
			this.inherited(arguments);
			// don't focus on <input>.  the user has explicitly focused on something else.
		},

		_getDisplayedValueAttr: function(){
			return this.textbox.value;
		},

		_setDisplayedValueAttr: function(/*String*/ value, /*Boolean?*/ priorityChange){
			this._setValueAttr(this.parse(value, this.constraints), priorityChange, value);
		},

		destroy: function(){
			if(this._picker){
				this._picker.destroy();
				delete this._picker;
			}
			this.inherited(arguments);
		},

		_onKeyPress: function(/*Event*/e){
			var p = this._picker, dk = dojo.keys;
			// Handle the key in the picker, if it has a handler.  If the handler
			// returns false, then don't handle any other keys.
			if(p && this._opened && p.handleKey){
				if(p.handleKey(e) === false){ return; }
			}
			if(this._opened && e.charOrCode == dk.ESCAPE && !e.shiftKey && !e.ctrlKey && !e.altKey){
				this._close();
				dojo.stopEvent(e);
			}else if(!this._opened && e.charOrCode == dk.DOWN_ARROW){
				this._open();
				dojo.stopEvent(e);
			}else if(dijit.form._DateTimeTextBox.superclass._onKeyPress.apply(this, arguments)){
				if(e.charOrCode === dk.TAB){
					this._tabbingAway = true;
				}else if(this._opened && (e.keyChar || e.charOrCode === dk.BACKSPACE || e.charOrCode == dk.DELETE)){
					// Replace the element - but do it after a delay to allow for 
					// filtering to occur
					setTimeout(dojo.hitch(this, function(){
						dijit.placeOnScreenAroundElement(p.domNode.parentNode, this.domNode, {'BL':'TL', 'TL':'BL'}, p.orient ? dojo.hitch(p, "orient") : null);
					}), 1);
				}
			}
		}
	}
);

}
