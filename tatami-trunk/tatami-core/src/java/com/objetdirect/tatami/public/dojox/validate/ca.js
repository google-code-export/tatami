/*
	Copyright (c) 2004-2008, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


if(!dojo._hasResource["dojox.validate.ca"]){dojo._hasResource["dojox.validate.ca"]=true;dojo.provide("dojox.validate.ca");dojo.require("dojox.validate._base");dojox.validate.ca.isPhoneNumber=function(_1){return dojox.validate.us.isPhoneNumber(_1);};dojox.validate.ca.isProvince=function(_2){var re=new RegExp("^"+dojox.regexp.ca.province()+"$","i");return re.test(_2);};dojox.validate.ca.isSocialInsuranceNumber=function(_4){var _5={format:["###-###-###","### ### ###","#########"]};return dojox.validate.isNumberFormat(_4,_5);};dojox.validate.ca.isPostalCode=function(_6){var re=new RegExp("^"+dojox.regexp.ca.postalCode()+"$","i");return re.test(_6);};}