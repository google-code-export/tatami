/*
	Copyright (c) 2004-2008, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/book/dojo-book-0-9/introduction/licensing
*/


if(!dojo._hasResource["dojox.lang.functional.listcomp"]){dojo._hasResource["dojox.lang.functional.listcomp"]=true;dojo.provide("dojox.lang.functional.listcomp");(function(){var _1=/\bfor\b|\bif\b/gm;var _2=function(s){var _4=s.split(_1),_5=s.match(_1),_6=["var r = [];"],_7=[];for(var i=0;i<_5.length;){var a=_5[i],f=_4[++i];if(a=="for"&&!/^\s*\(\s*(;|var)/.test(f)){f=f.replace(/^\s*\(/,"(var ");}_6.push(a,f,"{");_7.push("}");}return _6.join("")+"r.push("+_4[0]+");"+_7.join("")+"return r;";};dojo.mixin(dojox.lang.functional,{buildListcomp:function(s){return "function(){"+_2(s)+"}";},compileListcomp:function(s){return new Function([],_2(s));},listcomp:function(s){return (new Function([],_2(s)))();}});})();}