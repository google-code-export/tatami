/*
	Copyright (c) 2004-2008, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/book/dojo-book-0-9/introduction/licensing
*/


if(!dojo._hasResource["dojox.dtl.filter.logic"]){dojo._hasResource["dojox.dtl.filter.logic"]=true;dojo.provide("dojox.dtl.filter.logic");dojo.mixin(dojox.dtl.filter.logic,{default_:function(_1,_2){return _1||_2||"";},default_if_none:function(_3,_4){return (_3===null)?_4||"":_3||"";},divisibleby:function(_5,_6){return (parseInt(_5)%parseInt(_6))==0;},_yesno:/\s*,\s*/g,yesno:function(_7,_8){if(!_8){_8="yes,no,maybe";}var _9=_8.split(dojox.dtl.filter.logic._yesno);if(_9.length<2){return _7;}if(_7){return _9[0];}if((!_7&&_7!==null)||_9.length<3){return _9[1];}return _9[2];}});}