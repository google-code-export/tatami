/*
	Copyright (c) 2004-2008, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


if(!dojo._hasResource["dojox.form.manager.EnableMixin"]){dojo._hasResource["dojox.form.manager.EnableMixin"]=true;dojo.provide("dojox.form.manager.EnableMixin");dojo.require("dojox.form.manager.Mixin");(function(){var fm=dojox.form.manager,aa=fm.actionAdapter,ia=fm.inspectorAdapter;dojo.declare("dojox.form.manager.EnableMixin",null,{gatherEnableState:function(_4){var _5=this.inspectFormWidgets(ia(function(_6,_7){return !_7.attr("disabled");}),_4);dojo.mixin(_5,this.inspectFormElements(ia(function(_8,_9){return !dojo.attr(_9,"disabled");}),_4));return _5;},enable:function(_a,_b){if(arguments.length<2||_b===undefined){_b=true;}var _c=!_b;this.inspectFormWidgets(aa(function(_d,_e,_f){_e.attr("disabled",_f);}),_a,_c);this.inspectFormElements(aa(function(_10,_11,_12){dojo.attr(_11,"disabled",_12);}),_a,_c);return this;},disable:function(_13){var _13=this.gatherEnableState();this.enable(_13,false);return _13;}});})();}