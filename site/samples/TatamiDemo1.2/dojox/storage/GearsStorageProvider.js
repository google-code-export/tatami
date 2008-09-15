/*
	Copyright (c) 2004-2008, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/book/dojo-book-0-9/introduction/licensing
*/


if(!dojo._hasResource["dojox.storage.GearsStorageProvider"]){dojo._hasResource["dojox.storage.GearsStorageProvider"]=true;dojo.provide("dojox.storage.GearsStorageProvider");dojo.require("dojox.storage.Provider");dojo.require("dojox.storage.manager");dojo.require("dojox.sql");if(dojo.isGears){(function(){dojo.declare("dojox.storage.GearsStorageProvider",dojox.storage.Provider,{constructor:function(){},TABLE_NAME:"__DOJO_STORAGE",initialized:false,_available:null,initialize:function(){if(dojo.config["disableGearsStorage"]==true){return;}this.TABLE_NAME="__DOJO_STORAGE";try{dojox.sql("CREATE TABLE IF NOT EXISTS "+this.TABLE_NAME+"( "+" namespace TEXT, "+" key TEXT, "+" value TEXT "+")");dojox.sql("CREATE UNIQUE INDEX IF NOT EXISTS namespace_key_index"+" ON "+this.TABLE_NAME+" (namespace, key)");}catch(e){console.debug("dojox.storage.GearsStorageProvider.initialize:",e);this.initialized=false;dojox.storage.manager.loaded();return;}this.initialized=true;dojox.storage.manager.loaded();},isAvailable:function(){return this._available=dojo.isGears;},put:function(_1,_2,_3,_4){if(this.isValidKey(_1)==false){throw new Error("Invalid key given: "+_1);}_4=_4||this.DEFAULT_NAMESPACE;if(dojo.isString(_2)){_2="string:"+_2;}else{_2=dojo.toJson(_2);}try{dojox.sql("DELETE FROM "+this.TABLE_NAME+" WHERE namespace = ? AND key = ?",_4,_1);dojox.sql("INSERT INTO "+this.TABLE_NAME+" VALUES (?, ?, ?)",_4,_1,_2);}catch(e){console.debug("dojox.storage.GearsStorageProvider.put:",e);_3(this.FAILED,_1,e.toString());return;}if(_3){_3(dojox.storage.SUCCESS,_1,null);}},get:function(_5,_6){if(this.isValidKey(_5)==false){throw new Error("Invalid key given: "+_5);}_6=_6||this.DEFAULT_NAMESPACE;var _7=dojox.sql("SELECT * FROM "+this.TABLE_NAME+" WHERE namespace = ? AND "+" key = ?",_6,_5);if(!_7.length){return null;}else{_7=_7[0].value;}if(dojo.isString(_7)&&(/^string:/.test(_7))){_7=_7.substring("string:".length);}else{_7=dojo.fromJson(_7);}return _7;},getNamespaces:function(){var _8=[dojox.storage.DEFAULT_NAMESPACE];var rs=dojox.sql("SELECT namespace FROM "+this.TABLE_NAME+" DESC GROUP BY namespace");for(var i=0;i<rs.length;i++){if(rs[i].namespace!=dojox.storage.DEFAULT_NAMESPACE){_8.push(rs[i].namespace);}}return _8;},getKeys:function(_b){_b=_b||this.DEFAULT_NAMESPACE;if(this.isValidKey(_b)==false){throw new Error("Invalid namespace given: "+_b);}var rs=dojox.sql("SELECT key FROM "+this.TABLE_NAME+" WHERE namespace = ?",_b);var _d=[];for(var i=0;i<rs.length;i++){_d.push(rs[i].key);}return _d;},clear:function(_f){if(this.isValidKey(_f)==false){throw new Error("Invalid namespace given: "+_f);}_f=_f||this.DEFAULT_NAMESPACE;dojox.sql("DELETE FROM "+this.TABLE_NAME+" WHERE namespace = ?",_f);},remove:function(key,_11){_11=_11||this.DEFAULT_NAMESPACE;dojox.sql("DELETE FROM "+this.TABLE_NAME+" WHERE namespace = ? AND"+" key = ?",_11,key);},putMultiple:function(_12,_13,_14,_15){if(this.isValidKeyArray(_12)===false||!_13 instanceof Array||_12.length!=_13.length){throw new Error("Invalid arguments: keys = ["+_12+"], values = ["+_13+"]");}if(_15==null||typeof _15=="undefined"){_15=dojox.storage.DEFAULT_NAMESPACE;}if(this.isValidKey(_15)==false){throw new Error("Invalid namespace given: "+_15);}this._statusHandler=_14;try{dojox.sql.open();dojox.sql.db.execute("BEGIN TRANSACTION");var _16="REPLACE INTO "+this.TABLE_NAME+" VALUES (?, ?, ?)";for(var i=0;i<_12.length;i++){var _18=_13[i];if(dojo.isString(_18)){_18="string:"+_18;}else{_18=dojo.toJson(_18);}dojox.sql.db.execute(_16,[_15,_12[i],_18]);}dojox.sql.db.execute("COMMIT TRANSACTION");dojox.sql.close();}catch(e){console.debug("dojox.storage.GearsStorageProvider.putMultiple:",e);if(_14){_14(this.FAILED,_12,e.toString());}return;}if(_14){_14(dojox.storage.SUCCESS,key,null);}},getMultiple:function(_19,_1a){if(this.isValidKeyArray(_19)===false){throw new ("Invalid key array given: "+_19);}if(_1a==null||typeof _1a=="undefined"){_1a=dojox.storage.DEFAULT_NAMESPACE;}if(this.isValidKey(_1a)==false){throw new Error("Invalid namespace given: "+_1a);}var _1b="SELECT * FROM "+this.TABLE_NAME+" WHERE namespace = ? AND "+" key = ?";var _1c=[];for(var i=0;i<_19.length;i++){var _1e=dojox.sql(_1b,_1a,_19[i]);if(!_1e.length){_1c[i]=null;}else{_1e=_1e[0].value;if(dojo.isString(_1e)&&(/^string:/.test(_1e))){_1c[i]=_1e.substring("string:".length);}else{_1c[i]=dojo.fromJson(_1e);}}}return _1c;},removeMultiple:function(_1f,_20){_20=_20||this.DEFAULT_NAMESPACE;dojox.sql.open();dojox.sql.db.execute("BEGIN TRANSACTION");var _21="DELETE FROM "+this.TABLE_NAME+" WHERE namespace = ? AND key = ?";for(var i=0;i<_1f.length;i++){dojox.sql.db.execute(_21,[_20,_1f[i]]);}dojox.sql.db.execute("COMMIT TRANSACTION");dojox.sql.close();},isPermanent:function(){return true;},getMaximumSize:function(){return this.SIZE_NO_LIMIT;},hasSettingsUI:function(){return false;},showSettingsUI:function(){throw new Error(this.declaredClass+" does not support a storage settings user-interface");},hideSettingsUI:function(){throw new Error(this.declaredClass+" does not support a storage settings user-interface");}});dojox.storage.manager.register("dojox.storage.GearsStorageProvider",new dojox.storage.GearsStorageProvider());})();}}