if(!dojo._hasResource["dojox.lang.functional.object"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.lang.functional.object"] = true;
dojo.provide("dojox.lang.functional.object");

dojo.require("dojox.lang.functional.lambda");

// This module adds high-level functions and related constructs:
//	- object/dictionary helpers

// Defined methods:
//	- take any valid lambda argument as the functional argument
//	- skip all attributes that are present in the empty object 
//		(IE and/or 3rd-party libraries).

(function(){
	var d = dojo, df = dojox.lang.functional, empty = {};

	d.mixin(df, {
		// object helpers
		keys: function(/*Object*/ obj){
			// summary: returns an array of all keys in the object
			var t = [];
			for(var i in obj){
				if(i in empty){ continue; }
				t.push(i);
			}
			return	t; // Array
		},
		values: function(/*Object*/ obj){
			// summary: returns an array of all values in the object
			var t = [];
			for(var i in obj){
				if(i in empty){ continue; }
				t.push(obj[i]);
			}
			return	t; // Array
		},
		filterIn: function(/*Object*/ obj, /*Function|String|Array*/ f, /*Object?*/ o){
			// summary: creates new object with all attributes that pass the test 
			//	implemented by the provided function.
			o = o || d.global; f = df.lambda(f);
			var t = {}, v;
			for(var i in obj){
				if(i in empty){ continue; }
				v = obj[i];
				if(f.call(o, v, i, obj)){ t[i] = v; }
			}
			return t;	// Object
		},
		forIn: function(/*Object*/ obj, /*Function|String|Array*/ f, /*Object?*/ o){
			// summary: iterates over all object attributes.
			o = o || d.global; f = df.lambda(f);
			for(var i in obj){
				if(i in empty){ continue; }
				f.call(o, obj[i], i, obj);
			}
			return o;	// Object
		},
		mapIn: function(/*Object*/ obj, /*Function|String|Array*/ f, /*Object?*/ o){
			// summary: creates new object with the results of calling 
			//	a provided function on every attribute in this object.
			o = o || d.global; f = df.lambda(f);
			var t = {};
			for(var i in obj){
				if(i in empty){ continue; }
				t[i] = f.call(o, obj[i], i, obj);
			}
			return t;	// Object
		}
	});
})();

}
