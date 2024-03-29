/*
	Copyright (c) 2004-2008, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/book/dojo-book-0-9/introduction/licensing
*/


if(!dojo._hasResource["dojox.validate.web"]){dojo._hasResource["dojox.validate.web"]=true;dojo.provide("dojox.validate.web");dojo.require("dojox.validate._base");dojox.validate.isIpAddress=function(_1,_2){var re=new RegExp("^"+dojox.regexp.ipAddress(_2)+"$","i");return re.test(_1);};dojox.validate.isUrl=function(_4,_5){var re=new RegExp("^"+dojox.regexp.url(_5)+"$","i");return re.test(_4);};dojox.validate.isEmailAddress=function(_7,_8){var re=new RegExp("^"+dojox.regexp.emailAddress(_8)+"$","i");return re.test(_7);};dojox.validate.isEmailAddressList=function(_a,_b){var re=new RegExp("^"+dojox.regexp.emailAddressList(_b)+"$","i");return re.test(_a);};dojox.validate.getEmailAddressList=function(_d,_e){if(!_e){_e={};}if(!_e.listSeparator){_e.listSeparator="\\s;,";}if(dojox.validate.isEmailAddressList(_d,_e)){return _d.split(new RegExp("\\s*["+_e.listSeparator+"]\\s*"));}return [];};}