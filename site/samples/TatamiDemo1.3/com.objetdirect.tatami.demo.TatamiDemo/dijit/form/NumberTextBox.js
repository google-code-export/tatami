if(!dojo._hasResource["dijit.form.NumberTextBox"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dijit.form.NumberTextBox"] = true;
dojo.provide("dijit.form.NumberTextBox");

dojo.require("dijit.form.ValidationTextBox");
dojo.require("dojo.number");

/*=====
dojo.declare(
	"dijit.form.NumberTextBox.__Constraints",
	[dijit.form.RangeBoundTextBox.__Constraints, dojo.number.__FormatOptions, dojo.number.__ParseOptions]
);
=====*/

dojo.declare(
	"dijit.form.NumberTextBoxMixin",
	null,
	{
		// summary:
		//		A mixin for all number textboxes

		regExpGen: dojo.number.regexp,

		/*=====
		// constraints: dijit.form.NumberTextBox.__Constraints 
		constraints: {},
		======*/

		// editOptions: Object
		//		properties to mix into constraints when the value is being edited
		editOptions: { pattern: '#.######' },

		_onFocus: function(){
			this._setValueAttr(this.attr('value'), false);	
			this.inherited(arguments);
		},

		_formatter: dojo.number.format,

		format: function(/*Number*/ value, /*dojo.number.__FormatOptions*/ constraints){
			//	summary: formats the value as a Number, according to constraints

			if(typeof value == "string") { return value; }
			if(isNaN(value)){ return ""; }
			if(this.editOptions && this._focused){
				constraints = dojo.mixin(dojo.mixin({}, this.editOptions), this.constraints);
			}
			return this._formatter(value, constraints);
		},

		parse: dojo.number.parse,
		/*=====
		parse: function(value, constraints){
			//	summary: parses the value as a Number, according to constraints
			//	value: String
			//
			//	constraints: dojo.number.__ParseOptions
		},
		=====*/

		filter: function(/*Number*/ value){
			return (value === null || value === '' || value === undefined)? NaN : this.inherited(arguments); // attr('value', null||''||undefined) should fire onChange(NaN)
		},

		serialize: function(/*Number*/ value, /*Object?*/options){
			return (typeof value != "number" || isNaN(value))? '' : this.inherited(arguments);
		},

		_getValueAttr: function(){
			// summary:
			//		Hook so attr('value') works.
			var v = this.inherited(arguments);
			if(isNaN(v) && this.textbox.value !== ''){ return undefined; }
			return v;
		},

		value: NaN
	}
);

dojo.declare(
	"dijit.form.NumberTextBox",
	[dijit.form.RangeBoundTextBox,dijit.form.NumberTextBoxMixin],
	{
		// summary:
		//		A validating, serializable, range-bound text box.
	}
);

}
