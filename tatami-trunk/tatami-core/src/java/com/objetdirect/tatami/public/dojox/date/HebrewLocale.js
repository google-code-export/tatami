/*
	Copyright (c) 2004-2008, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


if(!dojo._hasResource["dojox.date.HebrewLocale"]){dojo._hasResource["dojox.date.HebrewLocale"]=true;dojo.provide("dojox.date.HebrewLocale");dojo.experimental("dojox.date.HebrewLocale");dojo.require("dojox.date.HebrewDate");dojo.require("dojox.date.HebrewNumerals");dojo.require("dojo.regexp");dojo.require("dojo.string");dojo.require("dojo.i18n");dojo.requireLocalization("dojo.cldr","hebrew",null,"");(function(){function formatPattern(_1,_2,_3,_4,_5){return _5.replace(/([a-z])\1*/ig,function(_6){var s,_8;var c=_6.charAt(0);var l=_6.length;var _b=["abbr","wide","narrow"];switch(c){case "y":if(_3=="he"){s=dojox.date.HebrewNumerals.getYearHebrewLetters(_1.getFullYear());}else{s=String(_1.getFullYear());}break;case "M":var m=_1.getMonth();if(l<3){if(!_1.isLeapYear(_1.getFullYear())&&m>5){m--;}if(_3=="he"){s=dojox.date.HebrewNumerals.getMonthHebrewLetters(m);}else{s=m+1;_8=true;}}else{if(!_1.isLeapYear(_1.getFullYear())&&m==6){m--;}var _d=["months","format",_b[l-3]].join("-");s=_2[_d][m];}break;case "d":if(_3=="he"){s=dojox.date.HebrewNumerals.getDayHebrewLetters(_1.getDate());}else{s=_1.getDate();_8=true;}break;case "E":var d=_1.getDay();if(l<3){s=d+1;_8=true;}else{var _f=["days","format",_b[l-3]].join("-");s=_2[_f][d];}break;case "a":var _10=(_1.getHours()<12)?"am":"pm";s=_2[_10];break;case "h":case "H":case "K":case "k":var h=_1.getHours();switch(c){case "h":s=(h%12)||12;break;case "H":s=h;break;case "K":s=(h%12);break;case "k":s=h||24;break;}_8=true;break;case "m":s=_1.getMinutes();_8=true;break;case "s":s=_1.getSeconds();_8=true;break;case "S":s=Math.round(_1.getMilliseconds()*Math.pow(10,l-3));_8=true;break;default:throw new Error("dojox.date.HebrewLocale.formatPattern: invalid pattern char: "+_5);}if(_8){s=dojo.string.pad(s,l);}return s;});};dojox.date.HebrewLocale.format=function(_12,_13){_13=_13||{};var _14=dojo.i18n.normalizeLocale(_13.locale);var _15=_13.formatLength||"short";var _16=dojox.date.HebrewLocale._getHebrewBundle(_14);var str=[];var _18=dojo.hitch(this,formatPattern,_12,_16,_14,_13.fullYear);if(_13.selector!="time"){var _19=_13.datePattern||_16["dateFormat-"+_15];if(_19){str.push(_processPattern(_19,_18));}}if(_13.selector!="date"){var _1a=_13.timePattern||_16["timeFormat-"+_15];if(_1a){str.push(_processPattern(_1a,_18));}}var _1b=str.join(" ");return _1b;};dojox.date.HebrewLocale.regexp=function(_1c){return dojox.date.HebrewLocale._parseInfo(_1c).regexp;};dojox.date.HebrewLocale._parseInfo=function(_1d){_1d=_1d||{};var _1e=dojo.i18n.normalizeLocale(_1d.locale);var _1f=dojox.date.HebrewLocale._getHebrewBundle(_1e);var _20=_1d.formatLength||"short";var _21=_1d.datePattern||_1f["dateFormat-"+_20];var _22=_1d.timePattern||_1f["timeFormat-"+_20];var _23;if(_1d.selector=="date"){_23=_21;}else{if(_1d.selector=="time"){_23=_22;}else{_23=(typeof (_22)=="undefined")?_21:_21+" "+_22;}}var _24=[];var re=_processPattern(_23,dojo.hitch(this,_buildDateTimeRE,_24,_1f,_1d));return {regexp:re,tokens:_24,bundle:_1f};};dojox.date.HebrewLocale.parse=function(_26,_27){if(!_27){_27={};}var _28=dojox.date.HebrewLocale._parseInfo(_27);var _29=_28.tokens,_2a=_28.bundle;var re=new RegExp("^"+_28.regexp+"$");var _2c=re.exec(_26);var _2d=dojo.i18n.normalizeLocale(_27.locale);if(!_2c){return null;}var _2e,_2f;var _30=[5730,3,23,0,0,0,0];var _31="";var _32=0;var _33=["abbr","wide","narrow"];var _34=dojo.every(_2c,function(v,i){if(!i){return true;}var _37=_29[i-1];var l=_37.length;switch(_37.charAt(0)){case "y":if(_2d=="he"){_30[0]=dojox.date.HebrewNumerals.parseYearHebrewLetters(v);}else{_30[0]=Number(v);}break;case "M":if(l>2){var _39=_2a["months-format-"+_33[l-3]].concat();if(!_27.strict){v=v.replace(".","").toLowerCase();_39=dojo.map(_39,function(s){return s.replace(".","").toLowerCase();});}v=dojo.indexOf(_39,v);if(v==-1){return false;}_32=l;}else{if(_2d=="he"){v=dojox.date.HebrewNumerals.parseMonthHebrewLetters(v);}else{v--;}}_30[1]=Number(v);break;case "D":_30[1]=0;case "d":if(_2d=="he"){_30[2]=dojox.date.HebrewNumerals.parseDayHebrewLetters(v);}else{_30[2]=Number(v);}break;case "a":var am=_27.am||_2a.am;var pm=_27.pm||_2a.pm;if(!_27.strict){var _3d=/\./g;v=v.replace(_3d,"").toLowerCase();am=am.replace(_3d,"").toLowerCase();pm=pm.replace(_3d,"").toLowerCase();}if(_27.strict&&v!=am&&v!=pm){return false;}_31=(v==pm)?"p":(v==am)?"a":"";break;case "K":if(v==24){v=0;}case "h":case "H":case "k":_30[3]=Number(v);break;case "m":_30[4]=Number(v);break;case "s":_30[5]=Number(v);break;case "S":_30[6]=Number(v);}return true;});var _3e=+_30[3];if(_31==="p"&&_3e<12){_30[3]=_3e+12;}else{if(_31==="a"&&_3e==12){_30[3]=0;}}var _3f=new dojox.date.HebrewDate(_30[0],_30[1],_30[2],_30[3],_30[4],_30[5],_30[6]);if((_32>2)&&(_30[1]>5)&&!_3f.isLeapYear(_3f.getFullYear())){_3f=new dojox.date.HebrewDate(_30[0],_30[1]-1,_30[2],_30[3],_30[4],_30[5],_30[6]);}return _3f;};function _processPattern(_40,_41,_42,_43){var _44=function(x){return x;};_41=_41||_44;_42=_42||_44;_43=_43||_44;var _46=_40.match(/(''|[^'])+/g);var _47=_40.charAt(0)=="'";dojo.forEach(_46,function(_48,i){if(!_48){_46[i]="";}else{_46[i]=(_47?_42:_41)(_48);_47=!_47;}});return _43(_46.join(""));};function _buildDateTimeRE(_4a,_4b,_4c,_4d){_4d=dojo.regexp.escapeString(_4d);var _4e=dojo.i18n.normalizeLocale(_4c.locale);return _4d.replace(/([a-z])\1*/ig,function(_4f){var s;var c=_4f.charAt(0);var l=_4f.length;var p2="",p3="";if(_4c.strict){if(l>1){p2="0"+"{"+(l-1)+"}";}if(l>2){p3="0"+"{"+(l-2)+"}";}}else{p2="0?";p3="0{0,2}";}switch(c){case "y":s="\\S+";break;case "M":if(_4e=="he"){s=(l>2)?"\\S+ ?\\S+":"\\S{1,4}";}else{s=(l>2)?"\\S+ ?\\S+":p2+"[1-9]|1[0-2]";}break;case "d":if(_4e=="he"){s="\\S['\"']{1,2}\\S?";}else{s="[12]\\d|"+p2+"[1-9]|30";}break;case "E":if(_4e=="he"){s=(l>3)?"\\S+ ?\\S+":"\\S";}else{s="\\S+";}break;case "h":s=p2+"[1-9]|1[0-2]";break;case "k":s=p2+"\\d|1[01]";break;case "H":s=p2+"\\d|1\\d|2[0-3]";break;case "K":s=p2+"[1-9]|1\\d|2[0-4]";break;case "m":case "s":s=p2+"\\d|[0-5]\\d";break;case "S":s="\\d{"+l+"}";break;case "a":var am=_4c.am||_4b.am||"AM";var pm=_4c.pm||_4b.pm||"PM";if(_4c.strict){s=am+"|"+pm;}else{s=am+"|"+pm;if(am!=am.toLowerCase()){s+="|"+am.toLowerCase();}if(pm!=pm.toLowerCase()){s+="|"+pm.toLowerCase();}}break;default:s=".*";}if(_4a){_4a.push(_4f);}return "("+s+")";}).replace(/[\xa0 ]/g,"[\\s\\xa0]");};})();(function(){var _57=[];dojox.date.HebrewLocale.addCustomFormats=function(_58,_59){_57.push({pkg:_58,name:_59});};dojox.date.HebrewLocale._getHebrewBundle=function(_5a){var _5b={};dojo.forEach(_57,function(_5c){var _5d=dojo.i18n.getLocalization(_5c.pkg,_5c.name,_5a);_5b=dojo.mixin(_5b,_5d);},this);return _5b;};})();dojox.date.HebrewLocale.addCustomFormats("dojo.cldr","hebrew");dojox.date.HebrewLocale.getNames=function(_5e,_5f,_60,_61){var _62;var _63=dojox.date.HebrewLocale._getHebrewBundle;var _64=[_5e,_60,_5f];if(_60=="standAlone"){var key=_64.join("-");_62=_63(_61)[key];if(_62===_63("ROOT")[key]){_62=undefined;}}_64[1]="format";return (_62||_63(_61)[_64.join("-")]).concat();};}