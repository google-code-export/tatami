/*
	Copyright (c) 2004-2008, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


if(!dojo._hasResource["dojox.lang.functional.zip"]){dojo._hasResource["dojox.lang.functional.zip"]=true;dojo.provide("dojox.lang.functional.zip");(function(){var df=dojox.lang.functional;dojo.mixin(df,{zip:function(){var n=arguments[0].length,m=arguments.length,i;for(i=1;i<m;n=Math.min(n,arguments[i++].length)){}var t=new Array(n),j;for(i=0;i<n;++i){var p=new Array(m);for(j=0;j<m;p[j]=arguments[j][i],++j){}t[i]=p;}return t;},unzip:function(a){return df.zip.apply(null,a);}});})();}