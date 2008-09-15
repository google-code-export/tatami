/*
	Copyright (c) 2004-2008, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/book/dojo-book-0-9/introduction/licensing
*/


if(!dojo._hasResource["dojo.number"]){dojo._hasResource["dojo.number"]=true;dojo.provide("dojo.number");dojo.require("dojo.i18n");dojo.requireLocalization("dojo.cldr","number",null,"de,de-de,en,en-au,en-gb,en-us,es,es-es,fr,it,ja,ja-jp,ko,ko-kr,ROOT,pt,zh,zh-cn,zh-tw");dojo.require("dojo.string");dojo.require("dojo.regexp");dojo.number.format=function(_1,_2){_2=dojo.mixin({},_2||{});var _3=dojo.i18n.normalizeLocale(_2.locale);var _4=dojo.i18n.getLocalization("dojo.cldr","number",_3);_2.customs=_4;var _5=_2.pattern||_4[(_2.type||"decimal")+"Format"];if(isNaN(_1)){return null;}return dojo.number._applyPattern(_1,_5,_2);};dojo.number._numberPatternRE=/[#0,]*[#0](?:\.0*#*)?/;dojo.number._applyPattern=function(_6,_7,_8){_8=_8||{};var _9=_8.customs.group;var _a=_8.customs.decimal;var _b=_7.split(";");var _c=_b[0];_7=_b[(_6<0)?1:0]||("-"+_c);if(_7.indexOf("%")!=-1){_6*=100;}else{if(_7.indexOf("‰")!=-1){_6*=1000;}else{if(_7.indexOf("¤")!=-1){_9=_8.customs.currencyGroup||_9;_a=_8.customs.currencyDecimal||_a;_7=_7.replace(/\u00a4{1,3}/,function(_d){var _e=["symbol","currency","displayName"][_d.length-1];return _8[_e]||_8.currency||"";});}else{if(_7.indexOf("E")!=-1){throw new Error("exponential notation not supported");}}}}var _f=dojo.number._numberPatternRE;var _10=_c.match(_f);if(!_10){throw new Error("unable to find a number expression in pattern: "+_7);}return _7.replace(_f,dojo.number._formatAbsolute(_6,_10[0],{decimal:_a,group:_9,places:_8.places}));};dojo.number.round=function(_11,_12,_13){var _14=String(_11).split(".");var _15=(_14[1]&&_14[1].length)||0;if(_15>_12){var _16=Math.pow(10,_12);if(_13>0){_16*=10/_13;_12++;}_11=Math.round(_11*_16)/_16;_14=String(_11).split(".");_15=(_14[1]&&_14[1].length)||0;if(_15>_12){_14[1]=_14[1].substr(0,_12);_11=Number(_14.join("."));}}return _11;};dojo.number._formatAbsolute=function(_17,_18,_19){_19=_19||{};if(_19.places===true){_19.places=0;}if(_19.places===Infinity){_19.places=6;}var _1a=_18.split(".");var _1b=(_19.places>=0)?_19.places:(_1a[1]&&_1a[1].length)||0;if(!(_19.round<0)){_17=dojo.number.round(_17,_1b,_19.round);}var _1c=String(Math.abs(_17)).split(".");var _1d=_1c[1]||"";if(_19.places){_1c[1]=dojo.string.pad(_1d.substr(0,_19.places),_19.places,"0",true);}else{if(_1a[1]&&_19.places!==0){var pad=_1a[1].lastIndexOf("0")+1;if(pad>_1d.length){_1c[1]=dojo.string.pad(_1d,pad,"0",true);}var _1f=_1a[1].length;if(_1f<_1d.length){_1c[1]=_1d.substr(0,_1f);}}else{if(_1c[1]){_1c.pop();}}}var _20=_1a[0].replace(",","");pad=_20.indexOf("0");if(pad!=-1){pad=_20.length-pad;if(pad>_1c[0].length){_1c[0]=dojo.string.pad(_1c[0],pad);}if(_20.indexOf("#")==-1){_1c[0]=_1c[0].substr(_1c[0].length-pad);}}var _21=_1a[0].lastIndexOf(",");var _22,_23;if(_21!=-1){_22=_1a[0].length-_21-1;var _24=_1a[0].substr(0,_21);_21=_24.lastIndexOf(",");if(_21!=-1){_23=_24.length-_21-1;}}var _25=[];for(var _26=_1c[0];_26;){var off=_26.length-_22;_25.push((off>0)?_26.substr(off):_26);_26=(off>0)?_26.slice(0,off):"";if(_23){_22=_23;delete _23;}}_1c[0]=_25.reverse().join(_19.group||",");return _1c.join(_19.decimal||".");};dojo.number.regexp=function(_28){return dojo.number._parseInfo(_28).regexp;};dojo.number._parseInfo=function(_29){_29=_29||{};var _2a=dojo.i18n.normalizeLocale(_29.locale);var _2b=dojo.i18n.getLocalization("dojo.cldr","number",_2a);var _2c=_29.pattern||_2b[(_29.type||"decimal")+"Format"];var _2d=_2b.group;var _2e=_2b.decimal;var _2f=1;if(_2c.indexOf("%")!=-1){_2f/=100;}else{if(_2c.indexOf("‰")!=-1){_2f/=1000;}else{var _30=_2c.indexOf("¤")!=-1;if(_30){_2d=_2b.currencyGroup||_2d;_2e=_2b.currencyDecimal||_2e;}}}var _31=_2c.split(";");if(_31.length==1){_31.push("-"+_31[0]);}var re=dojo.regexp.buildGroupRE(_31,function(_33){_33="(?:"+dojo.regexp.escapeString(_33,".")+")";return _33.replace(dojo.number._numberPatternRE,function(_34){var _35={signed:false,separator:_29.strict?_2d:[_2d,""],fractional:_29.fractional,decimal:_2e,exponent:false};var _36=_34.split(".");var _37=_29.places;if(_36.length==1||_37===0){_35.fractional=false;}else{if(_37===undefined){_37=_36[1].lastIndexOf("0")+1;}if(_37&&_29.fractional==undefined){_35.fractional=true;}if(!_29.places&&(_37<_36[1].length)){_37+=","+_36[1].length;}_35.places=_37;}var _38=_36[0].split(",");if(_38.length>1){_35.groupSize=_38.pop().length;if(_38.length>1){_35.groupSize2=_38.pop().length;}}return "("+dojo.number._realNumberRegexp(_35)+")";});},true);if(_30){re=re.replace(/(\s*)(\u00a4{1,3})(\s*)/g,function(_39,_3a,_3b,_3c){var _3d=["symbol","currency","displayName"][_3b.length-1];var _3e=dojo.regexp.escapeString(_29[_3d]||_29.currency||"");_3a=_3a?"\\s":"";_3c=_3c?"\\s":"";if(!_29.strict){if(_3a){_3a+="*";}if(_3c){_3c+="*";}return "(?:"+_3a+_3e+_3c+")?";}return _3a+_3e+_3c;});}return {regexp:re.replace(/[\xa0 ]/g,"[\\s\\xa0]"),group:_2d,decimal:_2e,factor:_2f};};dojo.number.parse=function(_3f,_40){var _41=dojo.number._parseInfo(_40);var _42=(new RegExp("^"+_41.regexp+"$")).exec(_3f);if(!_42){return NaN;}var _43=_42[1];if(!_42[1]){if(!_42[2]){return NaN;}_43=_42[2];_41.factor*=-1;}_43=_43.replace(new RegExp("["+_41.group+"\\s\\xa0"+"]","g"),"").replace(_41.decimal,".");return Number(_43)*_41.factor;};dojo.number._realNumberRegexp=function(_44){_44=_44||{};if(!("places" in _44)){_44.places=Infinity;}if(typeof _44.decimal!="string"){_44.decimal=".";}if(!("fractional" in _44)||/^0/.test(_44.places)){_44.fractional=[true,false];}if(!("exponent" in _44)){_44.exponent=[true,false];}if(!("eSigned" in _44)){_44.eSigned=[true,false];}var _45=dojo.number._integerRegexp(_44);var _46=dojo.regexp.buildGroupRE(_44.fractional,function(q){var re="";if(q&&(_44.places!==0)){re="\\"+_44.decimal;if(_44.places==Infinity){re="(?:"+re+"\\d+)?";}else{re+="\\d{"+_44.places+"}";}}return re;},true);var _49=dojo.regexp.buildGroupRE(_44.exponent,function(q){if(q){return "([eE]"+dojo.number._integerRegexp({signed:_44.eSigned})+")";}return "";});var _4b=_45+_46;if(_46){_4b="(?:(?:"+_4b+")|(?:"+_46+"))";}return _4b+_49;};dojo.number._integerRegexp=function(_4c){_4c=_4c||{};if(!("signed" in _4c)){_4c.signed=[true,false];}if(!("separator" in _4c)){_4c.separator="";}else{if(!("groupSize" in _4c)){_4c.groupSize=3;}}var _4d=dojo.regexp.buildGroupRE(_4c.signed,function(q){return q?"[-+]":"";},true);var _4f=dojo.regexp.buildGroupRE(_4c.separator,function(sep){if(!sep){return "(?:0|[1-9]\\d*)";}sep=dojo.regexp.escapeString(sep);if(sep==" "){sep="\\s";}else{if(sep==" "){sep="\\s\\xa0";}}var grp=_4c.groupSize,_52=_4c.groupSize2;if(_52){var _53="(?:0|[1-9]\\d{0,"+(_52-1)+"}(?:["+sep+"]\\d{"+_52+"})*["+sep+"]\\d{"+grp+"})";return ((grp-_52)>0)?"(?:"+_53+"|(?:0|[1-9]\\d{0,"+(grp-1)+"}))":_53;}return "(?:0|[1-9]\\d{0,"+(grp-1)+"}(?:["+sep+"]\\d{"+grp+"})*)";},true);return _4d+_4f;};}