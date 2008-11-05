/*
	Copyright (c) 2004-2008, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


if(!dojo._hasResource["dijit.Tree"]){dojo._hasResource["dijit.Tree"]=true;dojo.provide("dijit.Tree");dojo.require("dojo.fx");dojo.require("dijit._Widget");dojo.require("dijit._Templated");dojo.require("dijit._Container");dojo.require("dojo.cookie");dojo.declare("dijit._TreeNode",[dijit._Widget,dijit._Templated,dijit._Container,dijit._Contained],{item:null,isTreeNode:true,label:"",isExpandable:null,isExpanded:false,state:"UNCHECKED",templateString:"<div class=\"dijitTreeNode\" waiRole=\"presentation\"\r\n\t><div dojoAttachPoint=\"rowNode\" class=\"dijitTreeRow\" waiRole=\"presentation\"\r\n\t\t><img src=\"${_blankGif}\" alt=\"\" dojoAttachPoint=\"expandoNode\" class=\"dijitTreeExpando\" waiRole=\"presentation\"\r\n\t\t><span dojoAttachPoint=\"expandoNodeText\" class=\"dijitExpandoText\" waiRole=\"presentation\"\r\n\t\t></span\r\n\t\t><span dojoAttachPoint=\"contentNode\" dojoAttachEvent=\"onmouseenter:_onMouseEnter, onmouseleave:_onMouseLeave\"\r\n\t\t\tclass=\"dijitTreeContent\" waiRole=\"presentation\">\r\n\t\t\t<img src=\"${_blankGif}\" alt=\"\" dojoAttachPoint=\"iconNode\" class=\"dijitTreeIcon\" waiRole=\"presentation\"\r\n\t\t\t><span dojoAttachPoint=\"labelNode\" class=\"dijitTreeLabel\" wairole=\"treeitem\" tabindex=\"-1\" waiState=\"selected-false\" dojoAttachEvent=\"onfocus:_onNodeFocus\"></span>\r\n\t\t</span\r\n\t></div>\r\n\t<div dojoAttachPoint=\"containerNode\" class=\"dijitTreeContainer\" waiRole=\"presentation\" style=\"display: none;\"></div>\r\n</div>\r\n",postCreate:function(){this.setLabelNode(this.label);this._setExpando();this._updateItemClasses(this.item);if(this.isExpandable){dijit.setWaiState(this.labelNode,"expanded",this.isExpanded);}},markProcessing:function(){this.state="LOADING";this._setExpando(true);},unmarkProcessing:function(){this._setExpando(false);},_updateItemClasses:function(_1){var _2=this.tree,_3=_2.model;if(_2._v10Compat&&_1===_3.root){_1=null;}this.iconNode.className="dijitTreeIcon "+_2.getIconClass(_1,this.isExpanded);this.labelNode.className="dijitTreeLabel "+_2.getLabelClass(_1,this.isExpanded);},_updateLayout:function(){var _4=this.getParent();if(!_4||_4.rowNode.style.display=="none"){dojo.addClass(this.domNode,"dijitTreeIsRoot");}else{dojo.toggleClass(this.domNode,"dijitTreeIsLast",!this.getNextSibling());}},_setExpando:function(_5){var _6=["dijitTreeExpandoLoading","dijitTreeExpandoOpened","dijitTreeExpandoClosed","dijitTreeExpandoLeaf"];var _7=["*","-","+","*"];var _8=_5?0:(this.isExpandable?(this.isExpanded?1:2):3);dojo.forEach(_6,function(s){dojo.removeClass(this.expandoNode,s);},this);dojo.addClass(this.expandoNode,_6[_8]);this.expandoNodeText.innerHTML=_7[_8];},expand:function(){if(this.isExpanded){return;}this._wipeOut&&this._wipeOut.stop();this.isExpanded=true;dijit.setWaiState(this.labelNode,"expanded","true");dijit.setWaiRole(this.containerNode,"group");this.contentNode.className="dijitTreeContent dijitTreeContentExpanded";this._setExpando();this._updateItemClasses(this.item);if(!this._wipeIn){this._wipeIn=dojo.fx.wipeIn({node:this.containerNode,duration:dijit.defaultDuration});}this._wipeIn.play();},collapse:function(){if(!this.isExpanded){return;}this._wipeIn&&this._wipeIn.stop();this.isExpanded=false;dijit.setWaiState(this.labelNode,"expanded","false");this.contentNode.className="dijitTreeContent";this._setExpando();this._updateItemClasses(this.item);if(!this._wipeOut){this._wipeOut=dojo.fx.wipeOut({node:this.containerNode,duration:dijit.defaultDuration});}this._wipeOut.play();},setLabelNode:function(_a){this.labelNode.innerHTML="";this.labelNode.appendChild(dojo.doc.createTextNode(_a));},setChildItems:function(_b){var _c=this.tree,_d=_c.model;this.getChildren().forEach(function(_e){dijit._Container.prototype.removeChild.call(this,_e);},this);this.state="LOADED";if(_b&&_b.length>0){this.isExpandable=true;dojo.forEach(_b,function(_f){var id=_d.getIdentity(_f),_11=_c._itemNodeMap[id],_12=(_11&&!_11.getParent())?_11:this.tree._createTreeNode({item:_f,tree:_c,isExpandable:_d.mayHaveChildren(_f),label:_c.getLabel(_f)});this.addChild(_12);_c._itemNodeMap[id]=_12;if(this.tree.persist){if(_c._openedItemIds[id]){_c._expandNode(_12);}}},this);dojo.forEach(this.getChildren(),function(_13,idx){_13._updateLayout();});}else{this.isExpandable=false;}if(this._setExpando){this._setExpando(false);}if(this==_c.rootNode){var fc=this.tree.showRoot?this:this.getChildren()[0],_16=fc?fc.labelNode:this.domNode;_16.setAttribute("tabIndex","0");_c.lastFocused=fc;}},removeChild:function(_17){this.inherited(arguments);var _18=this.getChildren();if(_18.length==0){this.isExpandable=false;this.collapse();}dojo.forEach(_18,function(_19){_19._updateLayout();});},makeExpandable:function(){this.isExpandable=true;this._setExpando(false);},_onNodeFocus:function(evt){var _1b=dijit.getEnclosingWidget(evt.target);this.tree._onTreeFocus(_1b);},_onMouseEnter:function(evt){dojo.addClass(this.contentNode,"dijitTreeNodeHover");},_onMouseLeave:function(evt){dojo.removeClass(this.contentNode,"dijitTreeNodeHover");}});dojo.declare("dijit.Tree",[dijit._Widget,dijit._Templated],{store:null,model:null,query:null,label:"",showRoot:true,childrenAttr:["children"],openOnClick:false,openOnDblClick:false,templateString:"<div class=\"dijitTreeContainer\" waiRole=\"tree\"\r\n\tdojoAttachEvent=\"onclick:_onClick,onkeypress:_onKeyPress,ondblclick:_onDblClick\">\r\n</div>\r\n",isExpandable:true,isTree:true,persist:true,dndController:null,dndParams:["onDndDrop","itemCreator","onDndCancel","checkAcceptance","checkItemAcceptance","dragThreshold"],onDndDrop:null,itemCreator:null,onDndCancel:null,checkAcceptance:null,checkItemAcceptance:null,dragThreshold:0,_publish:function(_1e,_1f){dojo.publish(this.id,[dojo.mixin({tree:this,event:_1e},_1f||{})]);},postMixInProperties:function(){this.tree=this;this._itemNodeMap={};if(!this.cookieName){this.cookieName=this.id+"SaveStateCookie";}},postCreate:function(){if(this.persist){var _20=dojo.cookie(this.cookieName);this._openedItemIds={};if(_20){dojo.forEach(_20.split(","),function(_21){this._openedItemIds[_21]=true;},this);}}if(!this.model){this._store2model();}this.connect(this.model,"onChange","_onItemChange");this.connect(this.model,"onChildrenChange","_onItemChildrenChange");this.connect(this.model,"onDelete","_onItemDelete");this._load();this.inherited(arguments);if(this.dndController){if(dojo.isString(this.dndController)){this.dndController=dojo.getObject(this.dndController);}var _22={};for(var i=0;i<this.dndParams.length;i++){if(this[this.dndParams[i]]){_22[this.dndParams[i]]=this[this.dndParams[i]];}}this.dndController=new this.dndController(this,_22);}},_store2model:function(){this._v10Compat=true;dojo.deprecated("Tree: from version 2.0, should specify a model object rather than a store/query");var _24={id:this.id+"_ForestStoreModel",store:this.store,query:this.query,childrenAttrs:this.childrenAttr};if(this.params.mayHaveChildren){_24.mayHaveChildren=dojo.hitch(this,"mayHaveChildren");}if(this.params.getItemChildren){_24.getChildren=dojo.hitch(this,function(_25,_26,_27){this.getItemChildren((this._v10Compat&&_25===this.model.root)?null:_25,_26,_27);});}this.model=new dijit.tree.ForestStoreModel(_24);this.showRoot=Boolean(this.label);},_load:function(){this.model.getRoot(dojo.hitch(this,function(_28){var rn=this.rootNode=this.tree._createTreeNode({item:_28,tree:this,isExpandable:true,label:this.label||this.getLabel(_28)});if(!this.showRoot){rn.rowNode.style.display="none";}this.domNode.appendChild(rn.domNode);this._itemNodeMap[this.model.getIdentity(_28)]=rn;rn._updateLayout();this._expandNode(rn);}),function(err){console.error(this,": error loading root: ",err);});},mayHaveChildren:function(_2b){},getItemChildren:function(_2c,_2d){},getLabel:function(_2e){return this.model.getLabel(_2e);},getIconClass:function(_2f,_30){return (!_2f||this.model.mayHaveChildren(_2f))?(_30?"dijitFolderOpened":"dijitFolderClosed"):"dijitLeaf";},getLabelClass:function(_31,_32){},_onKeyPress:function(e){if(e.altKey){return;}var dk=dojo.keys;var _35=dijit.getEnclosingWidget(e.target);if(!_35){return;}var key=e.charOrCode;if(typeof key=="string"){if(!e.altKey&&!e.ctrlKey&&!e.shiftKey&&!e.metaKey){this._onLetterKeyNav({node:_35,key:key.toLowerCase()});dojo.stopEvent(e);}}else{var map=this._keyHandlerMap;if(!map){map={};map[dk.ENTER]="_onEnterKey";map[this.isLeftToRight()?dk.LEFT_ARROW:dk.RIGHT_ARROW]="_onLeftArrow";map[this.isLeftToRight()?dk.RIGHT_ARROW:dk.LEFT_ARROW]="_onRightArrow";map[dk.UP_ARROW]="_onUpArrow";map[dk.DOWN_ARROW]="_onDownArrow";map[dk.HOME]="_onHomeKey";map[dk.END]="_onEndKey";this._keyHandlerMap=map;}if(this._keyHandlerMap[key]){this[this._keyHandlerMap[key]]({node:_35,item:_35.item});dojo.stopEvent(e);}}},_onEnterKey:function(_38){this._publish("execute",{item:_38.item,node:_38.node});this.onClick(_38.item,_38.node);},_onDownArrow:function(_39){var _3a=this._getNextNode(_39.node);if(_3a&&_3a.isTreeNode){this.focusNode(_3a);}},_onUpArrow:function(_3b){var _3c=_3b.node;var _3d=_3c.getPreviousSibling();if(_3d){_3c=_3d;while(_3c.isExpandable&&_3c.isExpanded&&_3c.hasChildren()){var _3e=_3c.getChildren();_3c=_3e[_3e.length-1];}}else{var _3f=_3c.getParent();if(!(!this.showRoot&&_3f===this.rootNode)){_3c=_3f;}}if(_3c&&_3c.isTreeNode){this.focusNode(_3c);}},_onRightArrow:function(_40){var _41=_40.node;if(_41.isExpandable&&!_41.isExpanded){this._expandNode(_41);}else{if(_41.hasChildren()){_41=_41.getChildren()[0];if(_41&&_41.isTreeNode){this.focusNode(_41);}}}},_onLeftArrow:function(_42){var _43=_42.node;if(_43.isExpandable&&_43.isExpanded){this._collapseNode(_43);}else{var _44=_43.getParent();if(_44&&_44.isTreeNode&&!(!this.showRoot&&_44===this.rootNode)){this.focusNode(_44);}}},_onHomeKey:function(){var _45=this._getRootOrFirstNode();if(_45){this.focusNode(_45);}},_onEndKey:function(_46){var _47=this;while(_47.isExpanded){var c=_47.getChildren();_47=c[c.length-1];}if(_47&&_47.isTreeNode){this.focusNode(_47);}},_onLetterKeyNav:function(_49){var _4a=_49.node,_4b=_4a,key=_49.key;do{_4a=this._getNextNode(_4a);if(!_4a){_4a=this._getRootOrFirstNode();}}while(_4a!==_4b&&(_4a.label.charAt(0).toLowerCase()!=key));if(_4a&&_4a.isTreeNode){if(_4a!==_4b){this.focusNode(_4a);}}},_onClick:function(e){var _4e=e.target;var _4f=dijit.getEnclosingWidget(_4e);if(!_4f||!_4f.isTreeNode){return;}if((this.openOnClick&&_4f.isExpandable)||(_4e==_4f.expandoNode||_4e==_4f.expandoNodeText)){if(_4f.isExpandable){this._onExpandoClick({node:_4f});}}else{this._publish("execute",{item:_4f.item,node:_4f});this.onClick(_4f.item,_4f);this.focusNode(_4f);}dojo.stopEvent(e);},_onDblClick:function(e){var _51=e.target;var _52=dijit.getEnclosingWidget(_51);if(!_52||!_52.isTreeNode){return;}if((this.openOnDblClick&&_52.isExpandable)||(_51==_52.expandoNode||_51==_52.expandoNodeText)){if(_52.isExpandable){this._onExpandoClick({node:_52});}}else{this._publish("execute",{item:_52.item,node:_52});this.onDblClick(_52.item,_52);this.focusNode(_52);}dojo.stopEvent(e);},_onExpandoClick:function(_53){var _54=_53.node;this.focusNode(_54);if(_54.isExpanded){this._collapseNode(_54);}else{this._expandNode(_54);}},onClick:function(_55,_56){},onDblClick:function(_57,_58){},onOpen:function(_59,_5a){},onClose:function(_5b,_5c){},_getNextNode:function(_5d){if(_5d.isExpandable&&_5d.isExpanded&&_5d.hasChildren()){return _5d.getChildren()[0];}else{while(_5d&&_5d.isTreeNode){var _5e=_5d.getNextSibling();if(_5e){return _5e;}_5d=_5d.getParent();}return null;}},_getRootOrFirstNode:function(){return this.showRoot?this.rootNode:this.rootNode.getChildren()[0];},_collapseNode:function(_5f){if(_5f.isExpandable){if(_5f.state=="LOADING"){return;}_5f.collapse();this.onClose(_5f.item,_5f);if(this.persist&&_5f.item){delete this._openedItemIds[this.model.getIdentity(_5f.item)];this._saveState();}}},_expandNode:function(_60){if(!_60.isExpandable){return;}var _61=this.model,_62=_60.item;switch(_60.state){case "LOADING":return;case "UNCHECKED":_60.markProcessing();var _63=this;_61.getChildren(_62,function(_64){_60.unmarkProcessing();_60.setChildItems(_64);_63._expandNode(_60);},function(err){console.error(_63,": error loading root children: ",err);});break;default:_60.expand();this.onOpen(_60.item,_60);if(this.persist&&_62){this._openedItemIds[_61.getIdentity(_62)]=true;this._saveState();}}},blurNode:function(){var _66=this.lastFocused;if(!_66){return;}var _67=_66.labelNode;dojo.removeClass(_67,"dijitTreeLabelFocused");_67.setAttribute("tabIndex","-1");dijit.setWaiState(_67,"selected",false);this.lastFocused=null;},focusNode:function(_68){_68.labelNode.focus();},_onBlur:function(){this.inherited(arguments);if(this.lastFocused){var _69=this.lastFocused.labelNode;dojo.removeClass(_69,"dijitTreeLabelFocused");}},_onTreeFocus:function(_6a){if(_6a){if(_6a!=this.lastFocused){this.blurNode();}var _6b=_6a.labelNode;_6b.setAttribute("tabIndex","0");dijit.setWaiState(_6b,"selected",true);dojo.addClass(_6b,"dijitTreeLabelFocused");this.lastFocused=_6a;}},_onItemDelete:function(_6c){var _6d=this.model.getIdentity(_6c);var _6e=this._itemNodeMap[_6d];if(_6e){var _6f=_6e.getParent();if(_6f){_6f.removeChild(_6e);}delete this._itemNodeMap[_6d];_6e.destroyRecursive();}},_onItemChange:function(_70){var _71=this.model,_72=_71.getIdentity(_70),_73=this._itemNodeMap[_72];if(_73){_73.setLabelNode(this.getLabel(_70));_73._updateItemClasses(_70);}},_onItemChildrenChange:function(_74,_75){var _76=this.model,_77=_76.getIdentity(_74),_78=this._itemNodeMap[_77];if(_78){_78.setChildItems(_75);}},_onItemDelete:function(_79){var _7a=this.model,_7b=_7a.getIdentity(_79),_7c=this._itemNodeMap[_7b];if(_7c){_7c.destroyRecursive();delete this._itemNodeMap[_7b];}},_saveState:function(){if(!this.persist){return;}var ary=[];for(var id in this._openedItemIds){ary.push(id);}dojo.cookie(this.cookieName,ary.join(","),{expires:365});},destroy:function(){if(this.rootNode){this.rootNode.destroyRecursive();}if(this.dndController&&!dojo.isString(this.dndController)){this.dndController.destroy();}this.rootNode=null;this.inherited(arguments);},destroyRecursive:function(){this.destroy();},_createTreeNode:function(_7f){return new dijit._TreeNode(_7f);}});dojo.declare("dijit.tree.TreeStoreModel",null,{store:null,childrenAttrs:["children"],labelAttr:"",root:null,query:null,constructor:function(_80){dojo.mixin(this,_80);this.connects=[];var _81=this.store;if(!_81.getFeatures()["dojo.data.api.Identity"]){throw new Error("dijit.Tree: store must support dojo.data.Identity");}if(_81.getFeatures()["dojo.data.api.Notification"]){this.connects=this.connects.concat([dojo.connect(_81,"onNew",this,"_onNewItem"),dojo.connect(_81,"onDelete",this,"_onDeleteItem"),dojo.connect(_81,"onSet",this,"_onSetItem")]);}},destroy:function(){dojo.forEach(this.connects,dojo.disconnect);},getRoot:function(_82,_83){if(this.root){_82(this.root);}else{this.store.fetch({query:this.query,onComplete:dojo.hitch(this,function(_84){if(_84.length!=1){throw new Error(this.declaredClass+": query "+dojo.toJson(this.query)+" returned "+_84.length+" items, but must return exactly one item");}this.root=_84[0];_82(this.root);}),onError:_83});}},mayHaveChildren:function(_85){return dojo.some(this.childrenAttrs,function(_86){return this.store.hasAttribute(_85,_86);},this);},getChildren:function(_87,_88,_89){var _8a=this.store;var _8b=[];for(var i=0;i<this.childrenAttrs.length;i++){var _8d=_8a.getValues(_87,this.childrenAttrs[i]);_8b=_8b.concat(_8d);}var _8e=0;dojo.forEach(_8b,function(_8f){if(!_8a.isItemLoaded(_8f)){_8e++;}});if(_8e==0){_88(_8b);}else{var _90=function _90(_91){if(--_8e==0){_88(_8b);}};dojo.forEach(_8b,function(_92){if(!_8a.isItemLoaded(_92)){_8a.loadItem({item:_92,onItem:_90,onError:_89});}});}},getIdentity:function(_93){return this.store.getIdentity(_93);},getLabel:function(_94){if(this.labelAttr){return this.store.getValue(_94,this.labelAttr);}else{return this.store.getLabel(_94);}},newItem:function(_95,_96){var _97={parent:_96,attribute:this.childrenAttrs[0]};return this.store.newItem(_95,_97);},pasteItem:function(_98,_99,_9a,_9b){var _9c=this.store,_9d=this.childrenAttrs[0];if(_99){dojo.forEach(this.childrenAttrs,function(_9e){if(_9c.containsValue(_99,_9e,_98)){if(!_9b){var _9f=dojo.filter(_9c.getValues(_99,_9e),function(x){return x!=_98;});_9c.setValues(_99,_9e,_9f);}_9d=_9e;}});}if(_9a){_9c.setValues(_9a,_9d,_9c.getValues(_9a,_9d).concat(_98));}},onChange:function(_a1){},onChildrenChange:function(_a2,_a3){},onDelete:function(_a4,_a5){},_onNewItem:function(_a6,_a7){if(!_a7){return;}this.getChildren(_a7.item,dojo.hitch(this,function(_a8){this.onChildrenChange(_a7.item,_a8);}));},_onDeleteItem:function(_a9){this.onDelete(_a9);},_onSetItem:function(_aa,_ab,_ac,_ad){if(dojo.indexOf(this.childrenAttrs,_ab)!=-1){this.getChildren(_aa,dojo.hitch(this,function(_ae){this.onChildrenChange(_aa,_ae);}));}else{this.onChange(_aa);}}});dojo.declare("dijit.tree.ForestStoreModel",dijit.tree.TreeStoreModel,{rootId:"$root$",rootLabel:"ROOT",query:null,constructor:function(_af){this.root={store:this,root:true,id:_af.rootId,label:_af.rootLabel,children:_af.rootChildren};},mayHaveChildren:function(_b0){return _b0===this.root||this.inherited(arguments);},getChildren:function(_b1,_b2,_b3){if(_b1===this.root){if(this.root.children){_b2(this.root.children);}else{this.store.fetch({query:this.query,onComplete:dojo.hitch(this,function(_b4){this.root.children=_b4;_b2(_b4);}),onError:_b3});}}else{this.inherited(arguments);}},getIdentity:function(_b5){return (_b5===this.root)?this.root.id:this.inherited(arguments);},getLabel:function(_b6){return (_b6===this.root)?this.root.label:this.inherited(arguments);},newItem:function(_b7,_b8){if(_b8===this.root){this.onNewRootItem(_b7);return this.store.newItem(_b7);}else{return this.inherited(arguments);}},onNewRootItem:function(_b9){},pasteItem:function(_ba,_bb,_bc,_bd){if(_bb===this.root){if(!_bd){this.onLeaveRoot(_ba);}}dijit.tree.TreeStoreModel.prototype.pasteItem.call(this,_ba,_bb===this.root?null:_bb,_bc===this.root?null:_bc);if(_bc===this.root){this.onAddToRoot(_ba);}},onAddToRoot:function(_be){},onLeaveRoot:function(_bf){},_requeryTop:function(){var _c0=this.root.children||[];this.store.fetch({query:this.query,onComplete:dojo.hitch(this,function(_c1){this.root.children=_c1;if(_c0.length!=_c1.length||dojo.some(_c0,function(_c2,idx){return _c1[idx]!=_c2;})){this.onChildrenChange(this.root,_c1);}})});},_onNewItem:function(_c4,_c5){this._requeryTop();this.inherited(arguments);},_onDeleteItem:function(_c6){if(dojo.indexOf(this.root.children,_c6)!=-1){this._requeryTop();}this.inherited(arguments);}});}