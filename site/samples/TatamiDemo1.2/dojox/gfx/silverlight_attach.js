/*
	Copyright (c) 2004-2008, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/book/dojo-book-0-9/introduction/licensing
*/


dojo.require("dojox.gfx.silverlight");dojo.experimental("dojox.gfx.silverlight_attach");(function(){dojox.gfx.attachNode=function(_1){return null;if(!_1){return null;}var s=null;switch(_1.tagName.toLowerCase()){case dojox.gfx.Rect.nodeType:s=new dojox.gfx.Rect(_1);break;case dojox.gfx.Ellipse.nodeType:if(_1.width==_1.height){s=new dojox.gfx.Circle(_1);}else{s=new dojox.gfx.Ellipse(_1);}break;case dojox.gfx.Polyline.nodeType:s=new dojox.gfx.Polyline(_1);break;case dojox.gfx.Path.nodeType:s=new dojox.gfx.Path(_1);break;case dojox.gfx.Line.nodeType:s=new dojox.gfx.Line(_1);break;case dojox.gfx.Image.nodeType:s=new dojox.gfx.Image(_1);break;case dojox.gfx.Text.nodeType:s=new dojox.gfx.Text(_1);_3(s);break;default:return null;}_4(s);if(!(s instanceof dojox.gfx.Image)){_5(s);_6(s);}_7(s);return s;};dojox.gfx.attachSurface=function(_8){return null;};var _5=function(_9){return null;};var _6=function(_a){return null;};var _7=function(_b){return null;};var _3=function(_c){return null;};var _4=function(_d){return null;};})();