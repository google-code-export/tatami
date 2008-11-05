/*
	Copyright (c) 2004-2008, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


if(!dojo._hasResource["dojox.widget.Calendar"]){dojo._hasResource["dojox.widget.Calendar"]=true;dojo.provide("dojox.widget.Calendar");dojo.experimental("dojox.widget.Calendar");dojo.require("dijit._Calendar");dojo.require("dijit._Container");dojo.declare("dojox.widget._CalendarBase",[dijit._Widget,dijit._Templated,dijit._Container],{templateString:"<div class=\"dojoxCalendar\">\r\n    <div tabindex=\"0\" class=\"dojoxCalendarContainer\" style=\"visibility: visible; width: 180px; heightL 138px;\" dojoAttachPoint=\"container\">\r\n\t\t<div style=\"display:none\">\r\n\t\t\t<div dojoAttachPoint=\"previousYearLabelNode\"></div>\r\n\t\t\t<div dojoAttachPoint=\"nextYearLabelNode\"></div>\r\n\t\t\t<div dojoAttachPoint=\"monthLabelSpacer\"></div>\r\n\t\t</div>\r\n        <div class=\"dojoxCalendarHeader\">\r\n            <div>\r\n                <div class=\"dojoxCalendarDecrease\" dojoAttachPoint=\"decrementMonth\"></div>\r\n            </div>\r\n            <div class=\"\">\r\n                <div class=\"dojoxCalendarIncrease\" dojoAttachPoint=\"incrementMonth\"></div>\r\n            </div>\r\n            <div class=\"dojoxCalendarTitle\" dojoAttachPoint=\"header\" dojoAttachEvent=\"onclick: onHeaderClick\">\r\n            </div>\r\n        </div>\r\n        <div class=\"dojoxCalendarBody\" dojoAttachPoint=\"containerNode\"></div>\r\n        <div class=\"\">\r\n            <div class=\"dojoxCalendarFooter\" dojoAttachPoint=\"footer\">                        \r\n            </div>\r\n        </div>\r\n    </div>\r\n</div>\r\n",_views:null,useFx:true,widgetsInTemplate:true,value:null,constructor:function(){this._views=[];this.value=this.value||new Date();},postCreate:function(){this._height=dojo.style(this.containerNode,"height");this.displayMonth=new Date(this.value);var _1={parent:this,getValue:dojo.hitch(this,function(){return new Date(this.displayMonth);}),getLang:dojo.hitch(this,function(){return this.lang;}),isDisabledDate:dojo.hitch(this,this.isDisabledDate),getClassForDate:dojo.hitch(this,this.getClassForDate),addFx:this.useFx?dojo.hitch(this,this.addFx):function(){}};dojo.forEach(this._views,function(_2){var _3=document.createElement("div");var _4=new _2(_1,_3);this.addChild(_4);this.header.appendChild(_4.getHeader());dojo.style(_4.getHeader(),"display","none");dojo.style(_4.domNode,"visibility","hidden");dojo.connect(_4,"onValueSelected",this,"_onDateSelected");_4.setValue(this.value);},this);if(this._views.length<2){dojo.style(this.header,"cursor","auto");}this.inherited(arguments);this._children=this.getChildren();this._currentChild=0;var _5=new Date();this.footer.innerHTML="Today: "+dojo.date.locale.format(_5,{formatLength:"full",selector:"date",locale:this.lang});dojo.connect(this.footer,"onclick",this,"goToToday");dojo.style(this._children[0].domNode,"top","0px");dojo.style(this._children[0].domNode,"visibility","visible");dojo.style(this._children[0].getHeader(),"display","");var _6=this;var _7=function(_8,_9,_a){dijit.typematic.addMouseListener(_6[_8],_6,function(_b){if(_b>=0){_6._adjustDisplay(_9,_a);}},0.8,500);};_7("incrementMonth","month",1);_7("decrementMonth","month",-1);},addFx:function(_c,_d){},setValue:function(_e){if(!this.value||dojo.date.compare(_e,this.value)){_e=new Date(_e);this.displayMonth=new Date(_e);if(!this.isDisabledDate(_e,this.lang)){this.value=_e;this.value.setHours(0,0,0,0);this.onChange(this.value);}this._children[this._currentChild].setValue(this.value);return true;}return false;},isDisabledDate:function(_f,_10){},onValueSelected:function(_11){},_onDateSelected:function(_12,_13){this.displayMonth=_12;if(this.setValue(_12)){if(!this._transitionVert(-1)){if(!_13&&_13!==0){_13=this.value;}this.onValueSelected(_13);}}},onChange:function(_14){},onHeaderClick:function(e){this._transitionVert(1);},goToToday:function(){this.setValue(new Date());this.onValueSelected(this.value);},_transitionVert:function(_16){var _17=this._children[this._currentChild];var _18=this._children[this._currentChild+_16];if(!_18){return false;}var _19=dojo.style(this.containerNode,"height");_18.setValue(this.displayMonth);dojo.style(_17.header,"display","none");dojo.style(_18.header,"display","");dojo.style(_18.domNode,"top",(_19*-1)+"px");dojo.style(_18.domNode,"visibility","visible");this._currentChild+=_16;var _1a=_19*_16;var _1b=0;dojo.style(_18.domNode,"top",(_1a*-1)+"px");var _1c=dojo.animateProperty({node:_17.domNode,properties:{top:_1a}});var _1d=dojo.animateProperty({node:_18.domNode,properties:{top:_1b}});_1c.play();_1d.play();return true;},_slideTable:function(_1e,_1f,_20){var _21=_1e.domNode;var _22=_21.cloneNode(true);var _23=dojo.style(_21,"width");_21.parentNode.appendChild(_22);dojo.style(_21,"left",(_23*_1f)+"px");_20();var _24=dojo.animateProperty({node:_22,properties:{left:_23*_1f*-1},duration:500,onEnd:function(){_22.parentNode.removeChild(_22);}});var _25=dojo.animateProperty({node:_21,properties:{left:0},duration:500});_24.play();_25.play();},_addView:function(_26){this._views.push(_26);},getClassForDate:function(_27,_28){},_adjustDisplay:function(_29,_2a,_2b){var _2c=this._children[this._currentChild];var _2d=this.displayMonth=_2c.adjustDate(this.displayMonth,_2a);this._slideTable(_2c,_2a,function(){_2c.setValue(_2d);});}});dojo.declare("dojox.widget._CalendarView",dijit._Widget,{headerClass:"",cloneClass:function(_2e,n){var _30=dojo.query(_2e,this.domNode)[0];for(var i=0;i<n;i++){_30.parentNode.appendChild(_30.cloneNode(true));}},_setText:function(_32,_33){while(_32.firstChild){_32.removeChild(_32.firstChild);}_32.appendChild(dojo.doc.createTextNode(_33));},getHeader:function(){if(!this.header){this.header=document.createElement("div");dojo.addClass(this.header,this.headerClass);}return this.header;},onValueSelected:function(_34){},adjustDate:function(_35,_36){return dojo.date.add(_35,this.datePart,_36);}});dojo.declare("dojox.widget._CalendarDay",null,{parent:null,constructor:function(){this._addView(dojox.widget._CalendarDayView);}});dojo.declare("dojox.widget._CalendarDayView",[dojox.widget._CalendarView,dijit._Templated],{templateString:"<div class=\"dijitCalendarDayLabels\" style=\"left: 0px;\" dojoAttachPoint=\"dayContainer\">\r\n\t<div dojoAttachPoint=\"header\">\r\n\t\t<div dojoAttachPoint=\"monthAndYearHeader\">\r\n\t        <span dojoAttachPoint=\"monthLabelNode\" class=\"dojoxCalendarMonthLabelNode\"></span>\r\n\t\t\t<span dojoAttachPoint=\"headerComma\" class=\"dojoxCalendarComma\">,</span>\r\n\t\t\t<span dojoAttachPoint=\"yearLabelNode\"></span>\r\n\t\t</div>\r\n\t</div>\r\n    <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"margin: auto;\">\r\n        <thead>\r\n            <tr>\r\n                <td class=\"dijitCalendarDayLabelTemplate\"><div class=\"dijitCalendarDayLabel\"></div></td>\r\n            </tr>\r\n        </thead>\r\n        <tbody dojoAttachEvent=\"onclick: _onDayClick\">\r\n            <tr class=\"dijitCalendarWeekTemplate\">\r\n                <td class=\"dojoxCalendarNextMonth dijitCalendarDateTemplate\">\r\n                    <div class=\"dijitCalendarDateLabel\"></div>\r\n                </td>\r\n            </tr>\r\n        </tbody>\r\n    </table>\r\n</div>\r\n",datePart:"month",dayWidth:"narrow",postCreate:function(){this.cloneClass(".dijitCalendarDayLabelTemplate",6);this.cloneClass(".dijitCalendarDateTemplate",6);this.cloneClass(".dijitCalendarWeekTemplate",5);var _37=dojo.date.locale.getNames("days",this.dayWidth,"standAlone",this.getLang());var _38=dojo.cldr.supplemental.getFirstDayOfWeek(this.getLang());dojo.query(".dijitCalendarDayLabel",this.domNode).forEach(function(_39,i){this._setText(_39,_37[(i+_38)%7]);},this);this.addFx(".dijitCalendarDateTemplate div",this.domNode);},_onDayClick:function(e){var _3c=this.getValue();var p=e.target.parentNode;var c="dijitCalendar";var d=dojo.hasClass(p,c+"PreviousMonth")?-1:(dojo.hasClass(p,c+"NextMonth")?1:0);if(d){_3c=dojo.date.add(_3c,"month",d);}_3c.setDate(e.target._date);this.value=_3c;this.parent._onDateSelected(_3c);},setValue:function(_40){this._populateDays();},_populateDays:function(){var _41=this.getValue();_41.setDate(1);var _42=_41.getDay();var _43=dojo.date.getDaysInMonth(_41);var _44=dojo.date.getDaysInMonth(dojo.date.add(_41,"month",-1));var _45=new Date();var _46=this.value;var _47=dojo.cldr.supplemental.getFirstDayOfWeek(this.getLang());if(_47>_42){_47-=7;}dojo.query(".dijitCalendarDateTemplate",this.domNode).forEach(function(_48,i){i+=_47;var _4a=new Date(_41);var _4b,_4c="dijitCalendar",adj=0;if(i<_42){_4b=_44-_42+i+1;adj=-1;_4c+="Previous";}else{if(i>=(_42+_43)){_4b=i-_42-_43+1;adj=1;_4c+="Next";}else{_4b=i-_42+1;_4c+="Current";}}if(adj){_4a=dojo.date.add(_4a,"month",adj);}_4a.setDate(_4b);if(!dojo.date.compare(_4a,_45,"date")){_4c="dijitCalendarCurrentDate "+_4c;}if(!dojo.date.compare(_4a,_46,"date")){_4c="dijitCalendarSelectedDate "+_4c;}if(this.isDisabledDate(_4a,this.getLang())){_4c=" dijitCalendarDisabledDate "+_4c;}var _4e=this.getClassForDate(_4a,this.getLang());if(_4e){_4c+=_4e+" "+_4c;}_48.className=_4c+"Month dijitCalendarDateTemplate";_48.dijitDateValue=_4a.valueOf();var _4f=dojo.query(".dijitCalendarDateLabel",_48)[0];this._setText(_4f,_4a.getDate());_4f._date=_4a.getDate();},this);var _50=dojo.date.locale.getNames("months","wide","standAlone",this.getLang());this._setText(this.monthLabelNode,_50[_41.getMonth()]);this._setText(this.yearLabelNode,_41.getFullYear());}});dojo.declare("dojox.widget._CalendarMonth",null,{headerClass:"dojoxCalendarYearHeader",constructor:function(){this._addView(dojox.widget._CalendarMonthView);}});dojo.declare("dojox.widget._CalendarMonthView",[dojox.widget._CalendarView,dijit._Templated],{templateString:"<div class=\"dojoxCalendarMonthLabels\" style=\"left: 0px;\"  \r\n\tdojoAttachPoint=\"monthContainer\" dojoAttachEvent=\"onclick: onClick\">\r\n    <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"margin: auto;\">\r\n        <tbody>\r\n            <tr class=\"dojoxCalendarMonthGroupTemplate\">\r\n                <td class=\"dojoxCalendarMonthTemplate\">\r\n                    <div class=\"dojoxCalendarMonthLabel\"></div>\r\n                </td>\r\n             </tr>\r\n        </tbody>\r\n    </table>\r\n</div>\r\n",datePart:"year",postCreate:function(){this.cloneClass(".dojoxCalendarMonthTemplate",3);this.cloneClass(".dojoxCalendarMonthGroupTemplate",2);this._populateMonths();this.addFx(".dojoxCalendarMonthLabel",this.domNode);},setValue:function(_51){this.header.innerHTML=_51.getFullYear();},_getMonthNames:function(_52){this._monthNames=this._monthNames||dojo.date.locale.getNames("months",_52,"standAlone",this.getLang());return this._monthNames;},_populateMonths:function(){var _53=this._getMonthNames("abbr");dojo.query(".dojoxCalendarMonthLabel",this.monthContainer).forEach(dojo.hitch(this,function(_54,cnt){this._setText(_54,_53[cnt]);}));},onClick:function(evt){if(!dojo.hasClass(evt.target,"dojoxCalendarMonthLabel")){dojo.stopEvent(evt);return;}var _57=evt.target.parentNode.cellIndex+(evt.target.parentNode.parentNode.rowIndex*4);var _58=this.getValue();_58.setMonth(_57);this.onValueSelected(_58,_57);}});dojo.declare("dojox.widget._CalendarYear",null,{parent:null,constructor:function(){this._addView(dojox.widget._CalendarYearView);}});dojo.declare("dojox.widget._CalendarYearView",[dojox.widget._CalendarView,dijit._Templated],{templateString:"<div class=\"dojoxCalendarYearLabels\" style=\"left: 0px;\" dojoAttachPoint=\"yearContainer\">\r\n    <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"margin: auto;\" dojoAttachEvent=\"onclick: onClick\">\r\n        <tbody>\r\n            <tr class=\"dojoxCalendarYearGroupTemplate\">\r\n                <td class=\"dojoxCalendarNextMonth dojoxCalendarYearTemplate\">\r\n                    <div class=\"dojoxCalendarYearLabel\">\r\n                    </div>\r\n                </td>\r\n            </tr>\r\n        </tbody>\r\n    </table>\r\n</div>\r\n",postCreate:function(){this.cloneClass(".dojoxCalendarYearTemplate",3);this.cloneClass(".dojoxCalendarYearGroupTemplate",2);this._populateYears();this.addFx(".dojoxCalendarYearLabel",this.domNode);},setValue:function(_59){this._populateYears(_59.getFullYear());},_populateYears:function(_5a){if(this._displayedYear&&_5a&&_5a>=this._displayedYear-5&&_5a<=this._displayedYear+6){return;}this._displayedYear=_5a||this.getValue().getFullYear();var _5b=this._displayedYear-5;dojo.query(".dojoxCalendarYearLabel",this.yearContainer).forEach(dojo.hitch(this,function(_5c,cnt){this._setText(_5c,_5b+(cnt));}));this._setText(this.getHeader(),_5b+" - "+(_5b+11));},adjustDate:function(_5e,_5f){return dojo.date.add(_5e,"year",_5f*12);},onClick:function(evt){if(!dojo.hasClass(evt.target,"dojoxCalendarYearLabel")){dojo.stopEvent(evt);return;}var _61=Number(evt.target.innerHTML);var _62=this.getValue();_62.setYear(_61);this.onValueSelected(_62,_61);}});dojo.declare("dojox.widget.Calendar",[dojox.widget._CalendarBase,dojox.widget._CalendarDay,dojox.widget._CalendarMonth,dojox.widget._CalendarYear],{});dojo.declare("dojox.widget.DailyCalendar",[dojox.widget._CalendarBase,dojox.widget._CalendarDay],{});dojo.declare("dojox.widget.MonthlyCalendar",[dojox.widget._CalendarBase,dojox.widget._CalendarMonth],{});dojo.declare("dojox.widget.YearlyCalendar",[dojox.widget._CalendarBase,dojox.widget._CalendarYear],{});}