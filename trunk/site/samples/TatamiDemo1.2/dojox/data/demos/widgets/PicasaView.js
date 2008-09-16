/*
	Copyright (c) 2004-2008, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/book/dojo-book-0-9/introduction/licensing
*/


if(!dojo._hasResource["dojox.data.demos.widgets.PicasaView"]){dojo._hasResource["dojox.data.demos.widgets.PicasaView"]=true;dojo.provide("dojox.data.demos.widgets.PicasaView");dojo.require("dijit._Templated");dojo.require("dijit._Widget");dojo.declare("dojox.data.demos.widgets.PicasaView",[dijit._Widget,dijit._Templated],{templateString:"<table class=\"picasaView\">\r\n\t<tbody>\r\n\t\t<tr class=\"picasaTitle\">\r\n\t\t\t<td>\r\n\t\t\t\t<b>\r\n\t\t\t\t\tTitle:\r\n\t\t\t\t</b>\r\n\t\t\t</td>\r\n\t\t\t<td dojoAttachPoint=\"titleNode\">\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t\t<tr>\r\n\t\t\t<td>\r\n\t\t\t\t<b>\r\n\t\t\t\t\tAuthor:\r\n\t\t\t\t</b>\r\n\t\t\t</td>\r\n\t\t\t<td dojoAttachPoint=\"authorNode\">\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t\t<tr>\r\n\t\t\t<td colspan=\"2\">\r\n\t\t\t\t<b>\r\n\t\t\t\t\tSummary:\r\n\t\t\t\t</b>\r\n\t\t\t\t<span class=\"picasaSummary\" dojoAttachPoint=\"descriptionNode\"></span>\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t\t<tr>\r\n\t\t\t<td dojoAttachPoint=\"imageNode\" colspan=\"2\">\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t</tbody>\r\n</table>\r\n\r\n",titleNode:null,descriptionNode:null,imageNode:null,authorNode:null,title:"",author:"",imageUrl:"",iconUrl:"",postCreate:function(){this.titleNode.appendChild(document.createTextNode(this.title));this.authorNode.appendChild(document.createTextNode(this.author));this.descriptionNode.appendChild(document.createTextNode(this.description));var _1=document.createElement("a");_1.setAttribute("href",this.imageUrl);_1.setAttribute("target","_blank");var _2=document.createElement("img");_2.setAttribute("src",this.iconUrl);_1.appendChild(_2);this.imageNode.appendChild(_1);}});}