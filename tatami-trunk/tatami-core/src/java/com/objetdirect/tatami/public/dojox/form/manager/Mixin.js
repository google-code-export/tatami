/*
	Copyright (c) 2004-2008, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


if(!dojo._hasResource["dojox.form.manager.Mixin"]){dojo._hasResource["dojox.form.manager.Mixin"]=true;dojo.provide("dojox.form.manager.Mixin");dojo.require("dijit._Widget");(function(){var fm=dojox.form.manager,aa=fm.actionAdapter=function(_3){return function(_4,_5,_6){if(dojo.isArray(_5)){dojo.forEach(_5,function(_7){_3.call(this,_4,_7,_6);},this);}else{_3.apply(this,arguments);}};},ia=fm.inspectorAdapter=function(_9){return function(_a,_b,_c){return _9.call(this,_a,dojo.isArray(_b)?_b[0]:_b,_c);};},ce=fm.changeEvent=function(_e){var _f="onclick";switch(_e.tagName.toLowerCase()){case "textarea":_f="onkeyup";break;case "select":_f="onchange";break;case "input":switch(_e.type.toLowerCase()){case "text":case "password":_f="onkeyup";break;}break;}return _f;},_10={domNode:1,containerNode:1,srcNodeRef:1,bgIframe:1},_11=function(_12){var _13=_12.attr("name");if(_13){if(_13 in this._widgets){var a=this._widgets[_13];if(dojo.isArray(a)){a.push(_12);}else{this._widgets[_13]=[a,_12];}}else{this._widgets[_13]=_12;}}},_15=function(_16){var _17=dojo.attr(_16,"name");if(_17){for(var n=_16;n!==this.domNode;n=n.parentNode){if(dojo.attr(n,"widgetId")){return;}}if(_16.tagName.toLowerCase()=="input"&&_16.type.toLowerCase()=="radio"){var a=this._nodes[_17];if(a&&dojo.isArray(a)){a.push(_16);}else{this._nodes[_17]=[_16];}}else{this._nodes[_17]=_16;}}};dojo.declare("dojox.form.manager.Mixin",null,{startup:function(){if(this._started){return;}this._widgets={};dojo.forEach(this.getDescendants(),_11,this);this._nodes={};dojo.query("input, select, textarea, button",this.domNode).forEach(_15,this);for(var _1a in this._widgets){var _1b=this._widgets[_1a],_1c=[];if(dojo.isArray(_1b)){dojo.forEach(_1b,function(w){var o=w.attr("observer");if(o&&typeof o=="string"){_1c=_1c.concat(o.split(","));}});dojo.forEach(_1b,function(w){dojo.forEach(_1c,function(o){o=dojo.trim(o);if(o&&this[o]&&dojo.isFunction(this[o])){this.connect(w,"onChange",o);}},this);},this);}else{var o=_1b.attr("observer");if(o&&typeof o=="string"){dojo.forEach(o.split(","),function(o){o=dojo.trim(o);if(o&&this[o]&&dojo.isFunction(this[o])){this.connect(_1b,"onChange",o);}},this);}}}for(var _1a in this._nodes){if(_1a in this._widgets){continue;}var _23=this._nodes[_1a],_1c=[];if(dojo.isArray(_23)){dojo.forEach(_23,function(n){var o=dojo.attr(n,"observer");if(o&&typeof o=="string"){_1c=_1c.concat(o.split(","));}});dojo.forEach(_23,function(n){dojo.forEach(_1c,function(o){o=dojo.trim(o);if(o&&this[o]&&dojo.isFunction(this[o])){this.connect(n,"onclick",o);}},this);},this);}else{var o=dojo.attr(_23,"observer");if(o&&typeof o=="string"){var _28=ce(_23);dojo.forEach(o.split(","),function(o){o=dojo.trim(o);if(o&&this[o]&&dojo.isFunction(this[o])){this.connect(_23,_28,o);}},this);}}}this.inherited(arguments);},inspectFormWidgets:function(_2a,_2b,_2c){var _2d,_2e={};if(_2b){if(dojo.isArray(_2b)){dojo.forEach(_2b,function(_2f){if(_2f in this._widgets){_2e[_2f]=_2a.call(this,_2f,this._widgets[_2f],_2c);}},this);}else{for(_2d in _2b){if(_2d in this._widgets){_2e[_2d]=_2a.call(this,_2d,this._widgets[_2d],_2b[_2d]);}}}}else{for(_2d in this._widgets){_2e[_2d]=_2a.call(this,_2d,this._widgets[_2d],_2c);}}return _2e;},inspectFormElements:function(_30,_31,_32){var _33,_34={};if(_31){if(dojo.isArray(_31)){dojo.forEach(_31,function(_35){if(_35 in this._nodes){_34[_35]=_30.call(this,_35,this._nodes[_35],_32);}},this);}else{for(_33 in _31){if(_33 in this._nodes){_34[_33]=_30.call(this,_33,this._nodes[_33],_31[_33]);}}}}else{for(_33 in this._nodes){_34[_33]=_30.call(this,_33,this._nodes[_33],_32);}}return _34;},inspectAttachedPoints:function(_36,_37,_38){var _39,_3a={};if(_37){if(dojo.isArray(_37)){dojo.forEach(_37,function(_3b){var _3c=this[_3b];if(_3c&&_3c.tagName&&_3c.cloneNode){_3a[_3b]=_36.call(this,_3b,_3c,_38);}},this);}else{for(_39 in _37){var _3d=this[_39];if(_3d&&_3d.tagName&&_3d.cloneNode){_3a[_39]=_36.call(this,_39,_3d,_37[_39]);}}}}else{for(_39 in this){if(!(_39 in _10)){var _3d=this[_39];if(_3d&&_3d.tagName&&_3d.cloneNode){_3a[_39]=_36.call(this,_39,_3d,_38);}}}}return _3a;},inspect:function(_3e,_3f,_40){var _41=this.inspectFormWidgets(function(_42,_43,_44){if(dojo.isArray(_43)){return _3e.call(this,_42,dojo.map(_43,function(w){return w.domNode;}),_44);}return _3e.call(this,_42,_43.domNode,_44);},_3f,_40);dojo.mixin(_41,this.inspectFormElements(_3e,_3f,_40));return dojo.mixin(_41,this.inspectAttachedPoints(_3e,_3f,_40));}});})();dojo.extend(dijit._Widget,{observer:""});}