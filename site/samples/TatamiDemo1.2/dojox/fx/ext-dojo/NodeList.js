/*
	Copyright (c) 2004-2008, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/book/dojo-book-0-9/introduction/licensing
*/


if(!dojo._hasResource["dojox.fx.ext-dojo.NodeList"]){dojo._hasResource["dojox.fx.ext-dojo.NodeList"]=true;dojo.provide("dojox.fx.ext-dojo.NodeList");dojo.experimental("dojox.fx.ext-dojo.NodeList");dojo.require("dojo.NodeList-fx");dojo.require("dojox.fx");dojo.extend(dojo.NodeList,{sizeTo:function(_1){return this._anim(dojox.fx,"sizeTo",_1);},slideBy:function(_2){return this._anim(dojox.fx,"slideBy",_2);},highlight:function(_3){return this._anim(dojox.fx,"highlight",_3);},fadeTo:function(_4){return this._anim(dojo,"_fade",_4);},wipeTo:function(_5){return this._anim(dojox.fx,"wipeTo",_5);}});}