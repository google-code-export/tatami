(function(){
var $wnd = window;
var $doc = $wnd.document;
var $moduleName, $moduleBase;
var _, package_com_google_gwt_core_client_ = 'com.google.gwt.core.client.', package_com_google_gwt_lang_ = 'com.google.gwt.lang.', package_com_google_gwt_user_client_ = 'com.google.gwt.user.client.', package_com_google_gwt_user_client_impl_ = 'com.google.gwt.user.client.impl.', package_com_google_gwt_user_client_ui_ = 'com.google.gwt.user.client.ui.', package_com_google_gwt_user_client_ui_impl_ = 'com.google.gwt.user.client.ui.impl.', package_com_objetdirect_tatami_client_ = 'com.objetdirect.tatami.client.', package_com_objetdirect_tatami_client_gfx_ = 'com.objetdirect.tatami.client.gfx.', package_com_objetdirect_tatami_demo_client_ = 'com.objetdirect.tatami.demo.client.', package_java_io_ = 'java.io.', package_java_lang_ = 'java.lang.', package_java_util_ = 'java.util.';
function nullMethod(){
}

function equals_8(other){
  return this === other;
}

function hashCode_6(){
  return identityHashCode(this);
}

function toString_13(){
  return this.typeName$ + '@' + this.hashCode$();
}

function Object_0(){
}

_ = Object_0.prototype = {};
_.equals$ = equals_8;
_.hashCode$ = hashCode_6;
_.toString$ = toString_13;
_.toString = function(){
  return this.toString$();
}
;
_.typeName$ = package_java_lang_ + 'Object';
_.typeId$ = 1;
function getTypeName(o){
  return o == null?null:o.typeName$;
}

var sUncaughtExceptionHandler = null;
function getHashCode(o){
  return o == null?0:o.$H?o.$H:(o.$H = getNextHashId());
}

function getHashCode_0(o){
  return o == null?0:o.$H?o.$H:(o.$H = getNextHashId());
}

function getNextHashId(){
  return ++sNextHashId;
}

var sNextHashId = 0;
function $Throwable(this$static, message){
  this$static.message_0 = message;
  return this$static;
}

function toString_16(){
  var className, msg;
  className = getTypeName(this);
  msg = this.message_0;
  if (msg !== null) {
    return className + ': ' + msg;
  }
   else {
    return className;
  }
}

function Throwable(){
}

_ = Throwable.prototype = new Object_0();
_.toString$ = toString_16;
_.typeName$ = package_java_lang_ + 'Throwable';
_.typeId$ = 3;
_.message_0 = null;
function $Exception(this$static, message){
  $Throwable(this$static, message);
  return this$static;
}

function Exception(){
}

_ = Exception.prototype = new Throwable();
_.typeName$ = package_java_lang_ + 'Exception';
_.typeId$ = 4;
function $RuntimeException(this$static, message){
  $Exception(this$static, message);
  return this$static;
}

function RuntimeException(){
}

_ = RuntimeException.prototype = new Exception();
_.typeName$ = package_java_lang_ + 'RuntimeException';
_.typeId$ = 5;
function $JavaScriptException(this$static, name, description){
  $RuntimeException(this$static, 'JavaScript ' + name + ' exception: ' + description);
  return this$static;
}

function JavaScriptException(){
}

_ = JavaScriptException.prototype = new RuntimeException();
_.typeName$ = package_com_google_gwt_core_client_ + 'JavaScriptException';
_.typeId$ = 6;
function $equals(this$static, other){
  if (!instanceOf(other, 2)) {
    return false;
  }
  return equalsImpl(this$static, dynamicCast(other, 2));
}

function $hashCode(this$static){
  return getHashCode(this$static);
}

function createArray(){
  return [];
}

function createObject(){
  return {};
}

function equals_0(other){
  return $equals(this, other);
}

function equalsImpl(o, other){
  return o === other;
}

function hashCode_0(){
  return $hashCode(this);
}

function toString_0(){
  return toStringImpl(this);
}

function toStringImpl(o){
  if (o.toString)
    return o.toString();
  return '[object]';
}

function JavaScriptObject(){
}

_ = JavaScriptObject.prototype = new Object_0();
_.equals$ = equals_0;
_.hashCode$ = hashCode_0;
_.toString$ = toString_0;
_.typeName$ = package_com_google_gwt_core_client_ + 'JavaScriptObject';
_.typeId$ = 7;
function $Array(this$static, length, typeId, queryId, typeName){
  this$static.length_0 = length;
  this$static.queryId = queryId;
  this$static.typeName$ = typeName;
  this$static.typeId$ = typeId;
  return this$static;
}

function _set(array, index, value){
  return array[index] = value;
}

function clonify_0(a, length){
  return clonify(a, length);
}

function clonify(a, length){
  return $Array(new Array_0(), length, a.typeId$, a.queryId, a.typeName$);
}

function getIntValue(values, index){
  return values[index];
}

function getValue(values, index){
  return values[index];
}

function getValueCount(values){
  return values.length;
}

function initDims_0(typeName, typeIdExprs, queryIdExprs, dimExprs, defaultValue){
  return initDims(typeName, typeIdExprs, queryIdExprs, dimExprs, 0, getValueCount(dimExprs), defaultValue);
}

function initDims(typeName, typeIdExprs, queryIdExprs, dimExprs, index, count, defaultValue){
  var i, length, result;
  if ((length = getIntValue(dimExprs, index)) < 0) {
    throw new NegativeArraySizeException();
  }
  result = $Array(new Array_0(), length, getIntValue(typeIdExprs, index), getIntValue(queryIdExprs, index), typeName);
  ++index;
  if (index < count) {
    typeName = $substring(typeName, 1);
    for (i = 0; i < length; ++i) {
      _set(result, i, initDims(typeName, typeIdExprs, queryIdExprs, dimExprs, index, count, defaultValue));
    }
  }
   else {
    for (i = 0; i < length; ++i) {
      _set(result, i, defaultValue);
    }
  }
  return result;
}

function initValues(typeName, typeId, queryId, values){
  var i, length, result;
  length = getValueCount(values);
  result = $Array(new Array_0(), length, typeId, queryId, typeName);
  for (i = 0; i < length; ++i) {
    _set(result, i, getValue(values, i));
  }
  return result;
}

function setCheck(array, index, value){
  if (value !== null && array.queryId != 0 && !instanceOf(value, array.queryId)) {
    throw new ArrayStoreException();
  }
  return _set(array, index, value);
}

function Array_0(){
}

_ = Array_0.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_lang_ + 'Array';
_.typeId$ = 8;
function canCast(srcId, dstId){
  return !(!(srcId && typeIdArray[srcId][dstId]));
}

function dynamicCast(src, dstId){
  if (src != null)
    canCast(src.typeId$, dstId) || throwClassCastException();
  return src;
}

function instanceOf(src, dstId){
  return src != null && canCast(src.typeId$, dstId);
}

function narrow_char(x){
  return x & 65535;
}

function narrow_int(x){
  return ~(~x);
}

function round_int(x){
  if (x > ($clinit_175() , MAX_VALUE))
    return $clinit_175() , MAX_VALUE;
  if (x < ($clinit_175() , MIN_VALUE))
    return $clinit_175() , MIN_VALUE;
  return x >= 0?Math.floor(x):Math.ceil(x);
}

function throwClassCastException(){
  throw new ClassCastException();
}

function throwClassCastExceptionUnlessNull(o){
  if (o !== null) {
    throw new ClassCastException();
  }
  return o;
}

function wrapJSO(jso, seed){
  _ = seed.prototype;
  if (jso && !(jso.typeId$ >= _.typeId$)) {
    var oldToString = jso.toString;
    for (var i in _) {
      jso[i] = _[i];
    }
    jso.toString = oldToString;
  }
  return jso;
}

var typeIdArray;
function caught(e){
  if (instanceOf(e, 3)) {
    return e;
  }
  return $JavaScriptException(new JavaScriptException(), javaScriptExceptionName(e), javaScriptExceptionDescription(e));
}

function javaScriptExceptionDescription(e){
  return e.message;
}

function javaScriptExceptionName(e){
  return e.name;
}

function $clinit_8(){
  $clinit_8 = nullMethod;
  sEventPreviewStack = $ArrayList(new ArrayList());
  {
    impl = new DOMImplOpera();
    $init(impl);
  }
}

function addEventPreview(preview){
  $clinit_8();
  $add_13(sEventPreviewStack, preview);
}

function appendChild(parent, child){
  $clinit_8();
  $appendChild(impl, parent, child);
}

function compare(elem1, elem2){
  $clinit_8();
  return $compare(impl, elem1, elem2);
}

function createButton(){
  $clinit_8();
  return $createElement(impl, 'button');
}

function createDiv(){
  $clinit_8();
  return $createElement(impl, 'div');
}

function createElement(tagName){
  $clinit_8();
  return $createElement(impl, tagName);
}

function createImg(){
  $clinit_8();
  return $createElement(impl, 'img');
}

function createInputCheck(){
  $clinit_8();
  return $createInputElement(impl, 'checkbox');
}

function createInputText(){
  $clinit_8();
  return $createInputElement(impl, 'text');
}

function createLabel(){
  $clinit_8();
  return $createElement(impl, 'label');
}

function createSpan(){
  $clinit_8();
  return $createElement(impl, 'span');
}

function createTBody(){
  $clinit_8();
  return $createElement(impl, 'tbody');
}

function createTD(){
  $clinit_8();
  return $createElement(impl, 'td');
}

function createTR(){
  $clinit_8();
  return $createElement(impl, 'tr');
}

function createTable(){
  $clinit_8();
  return $createElement(impl, 'table');
}

function dispatchEvent(evt, elem, listener){
  $clinit_8();
  var handler;
  handler = sUncaughtExceptionHandler;
  {
    dispatchEventImpl(evt, elem, listener);
  }
}

function dispatchEventImpl(evt, elem, listener){
  $clinit_8();
  var prevCurrentEvent;
  if (elem === sCaptureElem) {
    if (eventGetType(evt) == 8192) {
      sCaptureElem = null;
    }
  }
  prevCurrentEvent = currentEvent;
  currentEvent = evt;
  try {
    listener.onBrowserEvent(evt);
  }
   finally {
    currentEvent = prevCurrentEvent;
  }
}

function eventCancelBubble(evt, cancel){
  $clinit_8();
  $eventCancelBubble(impl, evt, cancel);
}

function eventGetAltKey(evt){
  $clinit_8();
  return $eventGetAltKey(impl, evt);
}

function eventGetClientX(evt){
  $clinit_8();
  return $eventGetClientX(impl, evt);
}

function eventGetClientY(evt){
  $clinit_8();
  return $eventGetClientY(impl, evt);
}

function eventGetCtrlKey(evt){
  $clinit_8();
  return $eventGetCtrlKey(impl, evt);
}

function eventGetFromElement(evt){
  $clinit_8();
  return $eventGetFromElement(impl, evt);
}

function eventGetKeyCode(evt){
  $clinit_8();
  return $eventGetKeyCode(impl, evt);
}

function eventGetMetaKey(evt){
  $clinit_8();
  return $eventGetMetaKey(impl, evt);
}

function eventGetShiftKey(evt){
  $clinit_8();
  return $eventGetShiftKey(impl, evt);
}

function eventGetTarget(evt){
  $clinit_8();
  return $eventGetTarget(impl, evt);
}

function eventGetToElement(evt){
  $clinit_8();
  return $eventGetToElement(impl, evt);
}

function eventGetType(evt){
  $clinit_8();
  return $eventGetTypeInt(impl, evt);
}

function eventPreventDefault(evt){
  $clinit_8();
  $eventPreventDefault(impl, evt);
}

function eventToString(evt){
  $clinit_8();
  return $eventToString(impl, evt);
}

function getAbsoluteLeft(elem){
  $clinit_8();
  return $getAbsoluteLeft(impl, elem);
}

function getAbsoluteTop(elem){
  $clinit_8();
  return $getAbsoluteTop(impl, elem);
}

function getChild(parent, index){
  $clinit_8();
  return $getChild(impl, parent, index);
}

function getChildCount(parent){
  $clinit_8();
  return $getChildCount(impl, parent);
}

function getElementProperty(elem, prop){
  $clinit_8();
  return $getElementProperty(impl, elem, prop);
}

function getElementPropertyBoolean(elem, prop){
  $clinit_8();
  return $getElementPropertyBoolean(impl, elem, prop);
}

function getElementPropertyInt(elem, prop){
  $clinit_8();
  return $getElementPropertyInt(impl, elem, prop);
}

function getEventsSunk(elem){
  $clinit_8();
  return $getEventsSunk(impl, elem);
}

function getFirstChild(elem){
  $clinit_8();
  return $getFirstChild(impl, elem);
}

function getImgSrc(img){
  $clinit_8();
  return $getImgSrc(impl, img);
}

function getParent(elem){
  $clinit_8();
  return $getParent(impl, elem);
}

function insertChild(parent, child, index){
  $clinit_8();
  $insertChild(impl, parent, child, index);
}

function isOrHasChild(parent, child){
  $clinit_8();
  return $isOrHasChild(impl, parent, child);
}

function previewEvent(evt){
  $clinit_8();
  var preview, ret;
  ret = true;
  if (sEventPreviewStack.size > 0) {
    preview = dynamicCast($get_0(sEventPreviewStack, sEventPreviewStack.size - 1), 4);
    if (!(ret = preview.onEventPreview(evt))) {
      eventCancelBubble(evt, true);
      eventPreventDefault(evt);
    }
  }
  return ret;
}

function releaseCapture(elem){
  $clinit_8();
  if (sCaptureElem !== null && compare(elem, sCaptureElem)) {
    sCaptureElem = null;
  }
  $releaseCapture(impl, elem);
}

function removeChild(parent, child){
  $clinit_8();
  $removeChild(impl, parent, child);
}

function removeElementAttribute(elem, attr){
  $clinit_8();
  $removeElementAttribute(impl, elem, attr);
}

function removeEventPreview(preview){
  $clinit_8();
  $remove_10(sEventPreviewStack, preview);
}

function setCapture(elem){
  $clinit_8();
  sCaptureElem = elem;
  $setCapture(impl, elem);
}

function setElementAttribute(elem, attr, value){
  $clinit_8();
  $setElementAttribute(impl, elem, attr, value);
}

function setElementProperty(elem, prop, value){
  $clinit_8();
  $setElementProperty(impl, elem, prop, value);
}

function setElementPropertyBoolean(elem, prop, value){
  $clinit_8();
  $setElementPropertyBoolean(impl, elem, prop, value);
}

function setElementPropertyInt(elem, prop, value){
  $clinit_8();
  $setElementPropertyInt(impl, elem, prop, value);
}

function setEventListener(elem, listener){
  $clinit_8();
  $setEventListener(impl, elem, listener);
}

function setImgSrc(img, src){
  $clinit_8();
  $setImgSrc(impl, img, src);
}

function setInnerHTML(elem, html){
  $clinit_8();
  $setInnerHTML(impl, elem, html);
}

function setInnerText(elem, text){
  $clinit_8();
  $setInnerText(impl, elem, text);
}

function setStyleAttribute(elem, attr, value){
  $clinit_8();
  $setStyleAttribute(impl, elem, attr, value);
}

function sinkEvents(elem, eventBits){
  $clinit_8();
  $sinkEvents(impl, elem, eventBits);
}

function toString_1(elem){
  $clinit_8();
  return $toString(impl, elem);
}

function windowGetClientHeight(){
  $clinit_8();
  return $windowGetClientHeight(impl);
}

function windowGetClientWidth(){
  $clinit_8();
  return $windowGetClientWidth(impl);
}

var currentEvent = null, impl = null, sCaptureElem = null, sEventPreviewStack;
function equals_1(other){
  if (instanceOf(other, 5)) {
    return compare(this, dynamicCast(other, 5));
  }
  return $equals(wrapJSO(this, Element), other);
}

function hashCode_1(){
  return $hashCode(wrapJSO(this, Element));
}

function toString_2(){
  return toString_1(this);
}

function Element(){
}

_ = Element.prototype = new JavaScriptObject();
_.equals$ = equals_1;
_.hashCode$ = hashCode_1;
_.toString$ = toString_2;
_.typeName$ = package_com_google_gwt_user_client_ + 'Element';
_.typeId$ = 11;
function equals_2(other){
  return $equals(wrapJSO(this, Event), other);
}

function hashCode_2(){
  return $hashCode(wrapJSO(this, Event));
}

function toString_3(){
  return eventToString(this);
}

function Event(){
}

_ = Event.prototype = new JavaScriptObject();
_.equals$ = equals_2;
_.hashCode$ = hashCode_2;
_.toString$ = toString_3;
_.typeName$ = package_com_google_gwt_user_client_ + 'Event';
_.typeId$ = 12;
function $clinit_14(){
  $clinit_14 = nullMethod;
  timers = $ArrayList(new ArrayList());
  {
    hookWindowClosing();
  }
}

function $Timer(this$static){
  $clinit_14();
  return this$static;
}

function $cancel(this$static){
  if (this$static.isRepeating) {
    clearInterval(this$static.timerId);
  }
   else {
    clearTimeout(this$static.timerId);
  }
  $remove_10(timers, this$static);
}

function $fireImpl(this$static){
  if (!this$static.isRepeating) {
    $remove_10(timers, this$static);
  }
  this$static.run();
}

function $scheduleRepeating(this$static, periodMillis){
  if (periodMillis <= 0) {
    throw $IllegalArgumentException(new IllegalArgumentException(), 'must be positive');
  }
  $cancel(this$static);
  this$static.isRepeating = true;
  this$static.timerId = createInterval(this$static, periodMillis);
  $add_13(timers, this$static);
}

function clearInterval(id){
  $clinit_14();
  $wnd.clearInterval(id);
}

function clearTimeout(id){
  $clinit_14();
  $wnd.clearTimeout(id);
}

function createInterval(timer, period){
  $clinit_14();
  return $wnd.setInterval(function(){
    timer.fire();
  }
  , period);
}

function fire(){
  var handler;
  handler = sUncaughtExceptionHandler;
  {
    $fireImpl(this);
  }
}

function hookWindowClosing(){
  $clinit_14();
  addWindowCloseListener(new Timer$1());
}

function Timer(){
}

_ = Timer.prototype = new Object_0();
_.fire = fire;
_.typeName$ = package_com_google_gwt_user_client_ + 'Timer';
_.typeId$ = 13;
_.isRepeating = false;
_.timerId = 0;
var timers;
function onWindowClosed(){
  while (($clinit_14() , timers).size > 0) {
    $cancel(dynamicCast($get_0(($clinit_14() , timers), 0), 6));
  }
}

function onWindowClosing(){
  return null;
}

function Timer$1(){
}

_ = Timer$1.prototype = new Object_0();
_.onWindowClosed = onWindowClosed;
_.onWindowClosing = onWindowClosing;
_.typeName$ = package_com_google_gwt_user_client_ + 'Timer$1';
_.typeId$ = 14;
function $clinit_16(){
  $clinit_16 = nullMethod;
  closingListeners = $ArrayList(new ArrayList());
  resizeListeners = $ArrayList(new ArrayList());
  {
    init();
  }
}

function addWindowCloseListener(listener){
  $clinit_16();
  $add_13(closingListeners, listener);
}

function fireClosedImpl(){
  $clinit_16();
  var it, listener;
  for (it = $iterator_1(closingListeners); $hasNext_1(it);) {
    listener = dynamicCast($next_0(it), 7);
    listener.onWindowClosed();
  }
}

function fireClosingImpl(){
  $clinit_16();
  var it, listener, msg, ret;
  ret = null;
  for (it = $iterator_1(closingListeners); $hasNext_1(it);) {
    listener = dynamicCast($next_0(it), 7);
    msg = listener.onWindowClosing();
    {
      ret = msg;
    }
  }
  return ret;
}

function fireResizedImpl(){
  $clinit_16();
  var it, listener;
  for (it = $iterator_1(resizeListeners); $hasNext_1(it);) {
    listener = throwClassCastExceptionUnlessNull($next_0(it));
    null.nullMethod();
  }
}

function getClientHeight(){
  $clinit_16();
  return windowGetClientHeight();
}

function getClientWidth(){
  $clinit_16();
  return windowGetClientWidth();
}

function getScrollLeft(){
  $clinit_16();
  return $doc.documentElement.scrollLeft || $doc.body.scrollLeft;
}

function getScrollTop(){
  $clinit_16();
  return $doc.documentElement.scrollTop || $doc.body.scrollTop;
}

function init(){
  $clinit_16();
  __gwt_initHandlers(function(){
    onResize();
  }
  , function(){
    return onClosing();
  }
  , function(){
    onClosed();
    $wnd.onresize = null;
    $wnd.onbeforeclose = null;
    $wnd.onclose = null;
  }
  );
}

function onClosed(){
  $clinit_16();
  var handler;
  handler = sUncaughtExceptionHandler;
  {
    fireClosedImpl();
  }
}

function onClosing(){
  $clinit_16();
  var handler;
  handler = sUncaughtExceptionHandler;
  {
    return fireClosingImpl();
  }
}

function onResize(){
  $clinit_16();
  var handler;
  handler = sUncaughtExceptionHandler;
  {
    fireResizedImpl();
  }
}

var closingListeners, resizeListeners;
function $appendChild(this$static, parent, child){
  parent.appendChild(child);
}

function $createElement(this$static, tag){
  return $doc.createElement(tag);
}

function $createInputElement(this$static, type){
  var e = $doc.createElement('INPUT');
  e.type = type;
  return e;
}

function $eventCancelBubble(this$static, evt, cancel){
  evt.cancelBubble = cancel;
}

function $eventGetAltKey(this$static, evt){
  return !(!evt.altKey);
}

function $eventGetClientX(this$static, evt){
  return evt.clientX || -1;
}

function $eventGetClientY(this$static, evt){
  return evt.clientY || -1;
}

function $eventGetCtrlKey(this$static, evt){
  return !(!evt.ctrlKey);
}

function $eventGetKeyCode(this$static, evt){
  return evt.which || (evt.keyCode || -1);
}

function $eventGetMetaKey(this$static, evt){
  return !(!evt.metaKey);
}

function $eventGetShiftKey(this$static, evt){
  return !(!evt.shiftKey);
}

function $eventGetTypeInt(this$static, evt){
  switch (evt.type) {
    case 'blur':
      return 4096;
    case 'change':
      return 1024;
    case 'click':
      return 1;
    case 'dblclick':
      return 2;
    case 'focus':
      return 2048;
    case 'keydown':
      return 128;
    case 'keypress':
      return 256;
    case 'keyup':
      return 512;
    case 'load':
      return 32768;
    case 'losecapture':
      return 8192;
    case 'mousedown':
      return 4;
    case 'mousemove':
      return 64;
    case 'mouseout':
      return 32;
    case 'mouseover':
      return 16;
    case 'mouseup':
      return 8;
    case 'scroll':
      return 16384;
    case 'error':
      return 65536;
    case 'mousewheel':
      return 131072;
    case 'DOMMouseScroll':
      return 131072;
  }
}

function $getElementProperty(this$static, elem, prop){
  var ret = elem[prop];
  return ret == null?null:String(ret);
}

function $getElementPropertyBoolean(this$static, elem, prop){
  return !(!elem[prop]);
}

function $getElementPropertyInt(this$static, elem, prop){
  var i = parseInt(elem[prop]);
  if (!i) {
    return 0;
  }
  return i;
}

function $getEventsSunk(this$static, elem){
  return elem.__eventBits || 0;
}

function $getImgSrc(this$static, img){
  return img.src;
}

function $removeChild(this$static, parent, child){
  parent.removeChild(child);
}

function $removeElementAttribute(this$static, elem, attr){
  elem.removeAttribute(attr);
}

function $setElementAttribute(this$static, elem, attr, value){
  elem.setAttribute(attr, value);
}

function $setElementProperty(this$static, elem, prop, value){
  elem[prop] = value;
}

function $setElementPropertyBoolean(this$static, elem, prop, value){
  elem[prop] = value;
}

function $setElementPropertyInt(this$static, elem, prop, value){
  elem[prop] = value;
}

function $setEventListener(this$static, elem, listener){
  elem.__listener = listener;
}

function $setImgSrc(this$static, img, src){
  img.src = src;
}

function $setInnerHTML(this$static, elem, html){
  if (!html) {
    html = '';
  }
  elem.innerHTML = html;
}

function $setInnerText(this$static, elem, text){
  while (elem.firstChild) {
    elem.removeChild(elem.firstChild);
  }
  if (text != null) {
    elem.appendChild($doc.createTextNode(text));
  }
}

function $setStyleAttribute(this$static, elem, attr, value){
  elem.style[attr] = value;
}

function $toString(this$static, elem){
  return elem.outerHTML;
}

function $windowGetClientHeight(this$static){
  return $doc.body.clientHeight;
}

function $windowGetClientWidth(this$static){
  return $doc.body.clientWidth;
}

function DOMImpl(){
}

_ = DOMImpl.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_impl_ + 'DOMImpl';
_.typeId$ = 15;
function $compare(this$static, elem1, elem2){
  return elem1 == elem2;
}

function $eventGetFromElement(this$static, evt){
  return evt.relatedTarget?evt.relatedTarget:null;
}

function $eventGetTarget(this$static, evt){
  return evt.target || null;
}

function $eventGetToElement(this$static, evt){
  return evt.relatedTarget || null;
}

function $eventPreventDefault(this$static, evt){
  evt.preventDefault();
}

function $eventToString(this$static, evt){
  return evt.toString();
}

function $getChild(this$static, elem, index){
  var count = 0, child = elem.firstChild;
  while (child) {
    var next = child.nextSibling;
    if (child.nodeType == 1) {
      if (index == count)
        return child;
      ++count;
    }
    child = next;
  }
  return null;
}

function $getChildCount(this$static, elem){
  var count = 0, child = elem.firstChild;
  while (child) {
    if (child.nodeType == 1)
      ++count;
    child = child.nextSibling;
  }
  return count;
}

function $getFirstChild(this$static, elem){
  var child = elem.firstChild;
  while (child && child.nodeType != 1)
    child = child.nextSibling;
  return child || null;
}

function $getParent(this$static, elem){
  var parent = elem.parentNode;
  if (parent == null) {
    return null;
  }
  if (parent.nodeType != 1)
    parent = null;
  return parent || null;
}

function $init(this$static){
  $wnd.__dispatchCapturedMouseEvent = function(evt){
    if ($wnd.__dispatchCapturedEvent(evt)) {
      var cap = $wnd.__captureElem;
      if (cap && cap.__listener) {
        dispatchEvent(evt, cap, cap.__listener);
        evt.stopPropagation();
      }
    }
  }
  ;
  $wnd.__dispatchCapturedEvent = function(evt){
    if (!previewEvent(evt)) {
      evt.stopPropagation();
      evt.preventDefault();
      return false;
    }
    return true;
  }
  ;
  $wnd.addEventListener('click', $wnd.__dispatchCapturedMouseEvent, true);
  $wnd.addEventListener('dblclick', $wnd.__dispatchCapturedMouseEvent, true);
  $wnd.addEventListener('mousedown', $wnd.__dispatchCapturedMouseEvent, true);
  $wnd.addEventListener('mouseup', $wnd.__dispatchCapturedMouseEvent, true);
  $wnd.addEventListener('mousemove', $wnd.__dispatchCapturedMouseEvent, true);
  $wnd.addEventListener('mousewheel', $wnd.__dispatchCapturedMouseEvent, true);
  $wnd.addEventListener('keydown', $wnd.__dispatchCapturedEvent, true);
  $wnd.addEventListener('keyup', $wnd.__dispatchCapturedEvent, true);
  $wnd.addEventListener('keypress', $wnd.__dispatchCapturedEvent, true);
  $wnd.__dispatchEvent = function(evt){
    var listener, curElem = this;
    while (curElem && !(listener = curElem.__listener))
      curElem = curElem.parentNode;
    if (curElem && curElem.nodeType != 1)
      curElem = null;
    if (listener)
      dispatchEvent(evt, curElem, listener);
  }
  ;
  $wnd.__captureElem = null;
}

function $insertChild(this$static, parent, toAdd, index){
  var count = 0, child = parent.firstChild, before = null;
  while (child) {
    if (child.nodeType == 1) {
      if (count == index) {
        before = child;
        break;
      }
      ++count;
    }
    child = child.nextSibling;
  }
  parent.insertBefore(toAdd, before);
}

function $isOrHasChild(this$static, parent, child){
  while (child) {
    if (parent == child) {
      return true;
    }
    child = child.parentNode;
    if (child && child.nodeType != 1) {
      child = null;
    }
  }
  return false;
}

function $releaseCapture(this$static, elem){
  if (elem == $wnd.__captureElem)
    $wnd.__captureElem = null;
}

function $setCapture(this$static, elem){
  $wnd.__captureElem = elem;
}

function $sinkEvents(this$static, elem, bits){
  elem.__eventBits = bits;
  elem.onclick = bits & 1?$wnd.__dispatchEvent:null;
  elem.ondblclick = bits & 2?$wnd.__dispatchEvent:null;
  elem.onmousedown = bits & 4?$wnd.__dispatchEvent:null;
  elem.onmouseup = bits & 8?$wnd.__dispatchEvent:null;
  elem.onmouseover = bits & 16?$wnd.__dispatchEvent:null;
  elem.onmouseout = bits & 32?$wnd.__dispatchEvent:null;
  elem.onmousemove = bits & 64?$wnd.__dispatchEvent:null;
  elem.onkeydown = bits & 128?$wnd.__dispatchEvent:null;
  elem.onkeypress = bits & 256?$wnd.__dispatchEvent:null;
  elem.onkeyup = bits & 512?$wnd.__dispatchEvent:null;
  elem.onchange = bits & 1024?$wnd.__dispatchEvent:null;
  elem.onfocus = bits & 2048?$wnd.__dispatchEvent:null;
  elem.onblur = bits & 4096?$wnd.__dispatchEvent:null;
  elem.onlosecapture = bits & 8192?$wnd.__dispatchEvent:null;
  elem.onscroll = bits & 16384?$wnd.__dispatchEvent:null;
  elem.onload = bits & 32768?$wnd.__dispatchEvent:null;
  elem.onerror = bits & 65536?$wnd.__dispatchEvent:null;
  elem.onmousewheel = bits & 131072?$wnd.__dispatchEvent:null;
}

function DOMImplStandard(){
}

_ = DOMImplStandard.prototype = new DOMImpl();
_.typeName$ = package_com_google_gwt_user_client_impl_ + 'DOMImplStandard';
_.typeId$ = 16;
function $getAbsoluteLeft(this$static, elem){
  var left = 0;
  var curr = elem.parentNode;
  while (curr != $doc.body) {
    if (curr.tagName != 'TR' && curr.tagName != 'TBODY') {
      left -= curr.scrollLeft;
    }
    curr = curr.parentNode;
  }
  while (elem) {
    left += elem.offsetLeft;
    elem = elem.offsetParent;
  }
  return left;
}

function $getAbsoluteTop(this$static, elem){
  var top = 0;
  var curr = elem.parentNode;
  while (curr != $doc.body) {
    if (curr.tagName != 'TR' && curr.tagName != 'TBODY') {
      top -= curr.scrollTop;
    }
    curr = curr.parentNode;
  }
  while (elem) {
    top += elem.offsetTop;
    elem = elem.offsetParent;
  }
  return top;
}

function DOMImplOpera(){
}

_ = DOMImplOpera.prototype = new DOMImplStandard();
_.typeName$ = package_com_google_gwt_user_client_impl_ + 'DOMImplOpera';
_.typeId$ = 17;
function $addStyleName(this$static, style){
  setStyleName_0(this$static.getStyleElement(), style, true);
}

function $getAbsoluteLeft_0(this$static){
  return getAbsoluteLeft(this$static.getElement());
}

function $getAbsoluteTop_0(this$static){
  return getAbsoluteTop(this$static.getElement());
}

function $getOffsetHeight(this$static){
  return getElementPropertyInt(this$static.element, 'offsetHeight');
}

function $getOffsetWidth(this$static){
  return getElementPropertyInt(this$static.element, 'offsetWidth');
}

function $removeStyleName(this$static, style){
  setStyleName_0(this$static.getStyleElement(), style, false);
}

function $replaceNode(this$static, node, newNode){
  var p = node.parentNode;
  if (!p) {
    return;
  }
  p.insertBefore(newNode, node);
  p.removeChild(node);
}

function $setElement_0(this$static, elem){
  if (this$static.element !== null) {
    $replaceNode(this$static, this$static.element, elem);
  }
  this$static.element = elem;
}

function $setPixelSize(this$static, width, height){
  if (width >= 0) {
    this$static.setWidth(width + 'px');
  }
  if (height >= 0) {
    this$static.setHeight(height + 'px');
  }
}

function $setSize(this$static, width, height){
  this$static.setWidth(width);
  this$static.setHeight(height);
}

function $setStyleName(this$static, style){
  setStyleName(this$static.getStyleElement(), style);
}

function $setStylePrimaryName(this$static, style){
  setStylePrimaryName(this$static.getStyleElement(), style);
}

function getElement_0(){
  return this.element;
}

function getOffsetHeight_0(){
  return $getOffsetHeight(this);
}

function getOffsetWidth_0(){
  return $getOffsetWidth(this);
}

function getStyleElement_0(){
  return this.element;
}

function getStyleName(elem){
  return getElementProperty(elem, 'className');
}

function getTitle_0(){
  return getElementProperty(this.element, 'title');
}

function setHeight_0(height){
  setStyleAttribute(this.element, 'height', height);
}

function setStyleName(elem, styleName){
  setElementProperty(elem, 'className', styleName);
}

function setStyleName_0(elem, style, add){
  var begin, end, idx, last, lastPos, newClassName, oldStyle;
  if (elem === null) {
    throw $RuntimeException(new RuntimeException(), 'Null widget handle. If you are creating a composite, ensure that initWidget() has been called.');
  }
  style = $trim(style);
  if ($length(style) == 0) {
    throw $IllegalArgumentException(new IllegalArgumentException(), 'Style names cannot be empty');
  }
  oldStyle = getStyleName(elem);
  idx = $indexOf_0(oldStyle, style);
  while (idx != (-1)) {
    if (idx == 0 || $charAt(oldStyle, idx - 1) == 32) {
      last = idx + $length(style);
      lastPos = $length(oldStyle);
      if (last == lastPos || last < lastPos && $charAt(oldStyle, last) == 32) {
        break;
      }
    }
    idx = $indexOf_1(oldStyle, style, idx + 1);
  }
  if (add) {
    if (idx == (-1)) {
      if ($length(oldStyle) > 0) {
        oldStyle += ' ';
      }
      setElementProperty(elem, 'className', oldStyle + style);
    }
  }
   else {
    if (idx != (-1)) {
      begin = $trim($substring_0(oldStyle, 0, idx));
      end = $trim($substring(oldStyle, idx + $length(style)));
      if ($length(begin) == 0) {
        newClassName = end;
      }
       else if ($length(end) == 0) {
        newClassName = begin;
      }
       else {
        newClassName = begin + ' ' + end;
      }
      setElementProperty(elem, 'className', newClassName);
    }
  }
}

function setStylePrimaryName(elem, style){
  if (elem === null) {
    throw $RuntimeException(new RuntimeException(), 'Null widget handle. If you are creating a composite, ensure that initWidget() has been called.');
  }
  style = $trim(style);
  if ($length(style) == 0) {
    throw $IllegalArgumentException(new IllegalArgumentException(), 'Style names cannot be empty');
  }
  updatePrimaryAndDependentStyleNames(elem, style);
}

function setTitle_0(title){
  if (title === null || $length(title) == 0) {
    removeElementAttribute(this.element, 'title');
  }
   else {
    setElementAttribute(this.element, 'title', title);
  }
}

function setVisible_0(elem, visible){
  elem.style.display = visible?'':'none';
}

function setVisible_1(visible){
  setVisible_0(this.element, visible);
}

function setWidth_1(width){
  setStyleAttribute(this.element, 'width', width);
}

function sinkEvents_0(eventBitsToAdd){
  sinkEvents(this.getElement(), eventBitsToAdd | getEventsSunk(this.getElement()));
}

function toString_4(){
  if (this.element === null) {
    return '(null handle)';
  }
  return toString_1(this.element);
}

function updatePrimaryAndDependentStyleNames(elem, newPrimaryStyle){
  var classes = elem.className.split(/\s+/);
  if (!classes) {
    return;
  }
  var oldPrimaryStyle = classes[0];
  var oldPrimaryStyleLen = oldPrimaryStyle.length;
  classes[0] = newPrimaryStyle;
  for (var i = 1, n = classes.length; i < n; i++) {
    var name = classes[i];
    if (name.length > oldPrimaryStyleLen && (name.charAt(oldPrimaryStyleLen) == '-' && name.indexOf(oldPrimaryStyle) == 0)) {
      classes[i] = newPrimaryStyle + name.substring(oldPrimaryStyleLen);
    }
  }
  elem.className = classes.join(' ');
}

function UIObject(){
}

_ = UIObject.prototype = new Object_0();
_.getElement = getElement_0;
_.getOffsetHeight = getOffsetHeight_0;
_.getOffsetWidth = getOffsetWidth_0;
_.getStyleElement = getStyleElement_0;
_.getTitle = getTitle_0;
_.setHeight = setHeight_0;
_.setTitle = setTitle_0;
_.setVisible = setVisible_1;
_.setWidth = setWidth_1;
_.sinkEvents = sinkEvents_0;
_.toString$ = toString_4;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'UIObject';
_.typeId$ = 18;
_.element = null;
function $onAttach(this$static){
  if (this$static.isAttached()) {
    throw $IllegalStateException(new IllegalStateException(), "Should only call onAttach when the widget is detached from the browser's document");
  }
  this$static.attached = true;
  setEventListener(this$static.getElement(), this$static);
  this$static.doAttachChildren();
  this$static.onLoad();
}

function $onDetach(this$static){
  if (!this$static.isAttached()) {
    throw $IllegalStateException(new IllegalStateException(), "Should only call onDetach when the widget is attached to the browser's document");
  }
  try {
    this$static.onUnload();
  }
   finally {
    this$static.doDetachChildren();
    setEventListener(this$static.getElement(), null);
    this$static.attached = false;
  }
}

function $removeFromParent(this$static){
  if (instanceOf(this$static.parent_0, 14)) {
    dynamicCast(this$static.parent_0, 14).remove_1(this$static);
  }
   else if (this$static.parent_0 !== null) {
    throw $IllegalStateException(new IllegalStateException(), "This widget's parent does not implement HasWidgets");
  }
}

function $setElement_1(this$static, elem){
  if (this$static.isAttached()) {
    setEventListener(this$static.getElement(), null);
  }
  $setElement_0(this$static, elem);
  if (this$static.isAttached()) {
    setEventListener(elem, this$static);
  }
}

function $setLayoutData(this$static, layoutData){
  this$static.layoutData = layoutData;
}

function $setParent(this$static, parent){
  var oldParent;
  oldParent = this$static.parent_0;
  if (parent === null) {
    if (oldParent !== null && oldParent.isAttached()) {
      this$static.onDetach();
    }
    this$static.parent_0 = null;
  }
   else {
    if (oldParent !== null) {
      throw $IllegalStateException(new IllegalStateException(), 'Cannot set a new parent without first clearing the old parent');
    }
    this$static.parent_0 = parent;
    if (parent.isAttached()) {
      this$static.onAttach();
    }
  }
}

function doAttachChildren_0(){
}

function doDetachChildren_0(){
}

function isAttached_0(){
  return this.attached;
}

function onAttach_0(){
  $onAttach(this);
}

function onBrowserEvent_5(event_0){
}

function onDetach_1(){
  $onDetach(this);
}

function onLoad_1(){
}

function onUnload_1(){
}

function setElement_0(elem){
  $setElement_1(this, elem);
}

function Widget(){
}

_ = Widget.prototype = new UIObject();
_.doAttachChildren = doAttachChildren_0;
_.doDetachChildren = doDetachChildren_0;
_.isAttached = isAttached_0;
_.onAttach = onAttach_0;
_.onBrowserEvent = onBrowserEvent_5;
_.onDetach = onDetach_1;
_.onLoad = onLoad_1;
_.onUnload = onUnload_1;
_.setElement = setElement_0;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Widget';
_.typeId$ = 19;
_.attached = false;
_.layoutData = null;
_.parent_0 = null;
function $adopt(this$static, child){
  $setParent(child, this$static);
}

function $orphan(this$static, child){
  $setParent(child, null);
}

function clear_0(){
  var it;
  it = this.iterator();
  while (it.hasNext()) {
    it.next_0();
    it.remove();
  }
}

function doAttachChildren(){
  var child, it;
  for (it = this.iterator(); it.hasNext();) {
    child = dynamicCast(it.next_0(), 10);
    child.onAttach();
  }
}

function doDetachChildren(){
  var child, it;
  for (it = this.iterator(); it.hasNext();) {
    child = dynamicCast(it.next_0(), 10);
    child.onDetach();
  }
}

function onLoad_0(){
}

function onUnload_0(){
}

function Panel(){
}

_ = Panel.prototype = new Widget();
_.clear = clear_0;
_.doAttachChildren = doAttachChildren;
_.doDetachChildren = doDetachChildren;
_.onLoad = onLoad_0;
_.onUnload = onUnload_0;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Panel';
_.typeId$ = 20;
function $$init(this$static){
  this$static.children = $WidgetCollection(new WidgetCollection(), this$static);
}

function $ComplexPanel(this$static){
  $$init(this$static);
  return this$static;
}

function $add_1(this$static, child, container){
  $removeFromParent(child);
  $add_7(this$static.children, child);
  appendChild(container, child.getElement());
  $adopt(this$static, child);
}

function $adjustIndex(this$static, child, beforeIndex){
  var idx;
  $checkIndexBoundsForInsertion(this$static, beforeIndex);
  if (child.parent_0 === this$static) {
    idx = $getWidgetIndex(this$static, child);
    if (idx < beforeIndex) {
      beforeIndex--;
    }
  }
  return beforeIndex;
}

function $checkIndexBoundsForAccess(this$static, index){
  if (index < 0 || index >= this$static.children.size) {
    throw new IndexOutOfBoundsException();
  }
}

function $checkIndexBoundsForInsertion(this$static, index){
  if (index < 0 || index > this$static.children.size) {
    throw new IndexOutOfBoundsException();
  }
}

function $getWidget(this$static, index){
  return $get(this$static.children, index);
}

function $getWidgetIndex(this$static, child){
  return $indexOf(this$static.children, child);
}

function $insert(this$static, child, container, beforeIndex, domInsert){
  beforeIndex = $adjustIndex(this$static, child, beforeIndex);
  $removeFromParent(child);
  $insert_3(this$static.children, child, beforeIndex);
  if (domInsert) {
    insertChild(container, child.getElement(), beforeIndex);
  }
   else {
    appendChild(container, child.getElement());
  }
  $adopt(this$static, child);
}

function $iterator(this$static){
  return $iterator_0(this$static.children);
}

function $remove_0(this$static, w){
  var elem;
  if (w.parent_0 !== this$static) {
    return false;
  }
  $orphan(this$static, w);
  elem = w.getElement();
  removeChild(getParent(elem), elem);
  $remove_6(this$static.children, w);
  return true;
}

function iterator(){
  return $iterator(this);
}

function remove_1(w){
  return $remove_0(this, w);
}

function ComplexPanel(){
}

_ = ComplexPanel.prototype = new Panel();
_.iterator = iterator;
_.remove_1 = remove_1;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'ComplexPanel';
_.typeId$ = 21;
function $AbsolutePanel(this$static){
  $ComplexPanel(this$static);
  this$static.setElement(createDiv());
  setStyleAttribute(this$static.getElement(), 'position', 'relative');
  setStyleAttribute(this$static.getElement(), 'overflow', 'hidden');
  return this$static;
}

function $add(this$static, w){
  $add_1(this$static, w, this$static.getElement());
}

function $add_0(this$static, w, left, top){
  $removeFromParent(w);
  $setWidgetPositionImpl(this$static, w, left, top);
  $add(this$static, w);
}

function $checkWidgetParent(this$static, w){
  if (w.parent_0 !== this$static) {
    throw $IllegalArgumentException(new IllegalArgumentException(), 'Widget must be a child of this panel.');
  }
}

function $getWidgetLeft(this$static, w){
  $checkWidgetParent(this$static, w);
  return getAbsoluteLeft(w.getElement()) - getAbsoluteLeft(this$static.getElement());
}

function $getWidgetTop(this$static, w){
  $checkWidgetParent(this$static, w);
  return getAbsoluteTop(w.getElement()) - getAbsoluteTop(this$static.getElement());
}

function $remove(this$static, w){
  var removed;
  removed = $remove_0(this$static, w);
  if (removed) {
    changeToStaticPositioning(w.getElement());
  }
  return removed;
}

function $setWidgetPositionImpl(this$static, w, left, top){
  var h;
  h = w.getElement();
  if (left == (-1) && top == (-1)) {
    changeToStaticPositioning(h);
  }
   else {
    setStyleAttribute(h, 'position', 'absolute');
    setStyleAttribute(h, 'left', left + 'px');
    setStyleAttribute(h, 'top', top + 'px');
  }
}

function changeToStaticPositioning(elem){
  setStyleAttribute(elem, 'left', '');
  setStyleAttribute(elem, 'top', '');
  setStyleAttribute(elem, 'position', '');
}

function remove_0(w){
  return $remove(this, w);
}

function AbsolutePanel(){
}

_ = AbsolutePanel.prototype = new ComplexPanel();
_.remove_1 = remove_0;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'AbsolutePanel';
_.typeId$ = 22;
function $clinit_39(){
  $clinit_39 = nullMethod;
  $clinit_100() , implWidget;
}

function $FocusWidget(this$static, elem){
  $clinit_100() , implWidget;
  $setElement(this$static, elem);
  return this$static;
}

function $onBrowserEvent(this$static, event_0){
  switch (eventGetType(event_0)) {
    case 1:
      if (this$static.clickListeners_0 !== null) {
        $fireClick(this$static.clickListeners_0, this$static);
      }

      break;
    case 4096:
    case 2048:
      break;
    case 128:
    case 512:
    case 256:
      break;
  }
}

function $setElement(this$static, elem){
  $setElement_1(this$static, elem);
  this$static.sinkEvents(7041);
}

function $setEnabled(this$static, enabled){
  setElementPropertyBoolean(this$static.getElement(), 'disabled', !enabled);
}

function addClickListener(listener){
  if (this.clickListeners_0 === null) {
    this.clickListeners_0 = $ClickListenerCollection(new ClickListenerCollection());
  }
  $add_13(this.clickListeners_0, listener);
}

function isEnabled_0(){
  return !getElementPropertyBoolean(this.getElement(), 'disabled');
}

function onBrowserEvent(event_0){
  $onBrowserEvent(this, event_0);
}

function setElement(elem){
  $setElement(this, elem);
}

function FocusWidget(){
}

_ = FocusWidget.prototype = new Widget();
_.addClickListener = addClickListener;
_.isEnabled = isEnabled_0;
_.onBrowserEvent = onBrowserEvent;
_.setElement = setElement;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'FocusWidget';
_.typeId$ = 23;
_.clickListeners_0 = null;
function $clinit_21(){
  $clinit_21 = nullMethod;
  $clinit_100() , implWidget;
}

function $ButtonBase(this$static, elem){
  $clinit_100() , implWidget;
  $FocusWidget(this$static, elem);
  return this$static;
}

function setHTML(html){
  setInnerHTML(this.getElement(), html);
}

function ButtonBase(){
}

_ = ButtonBase.prototype = new FocusWidget();
_.setHTML = setHTML;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'ButtonBase';
_.typeId$ = 24;
function $clinit_22(){
  $clinit_22 = nullMethod;
  $clinit_100() , implWidget;
}

function $Button(this$static){
  $clinit_100() , implWidget;
  $ButtonBase(this$static, createButton());
  adjustType(this$static.getElement());
  $setStyleName(this$static, 'gwt-Button');
  return this$static;
}

function $Button_0(this$static, html){
  $clinit_100() , implWidget;
  $Button(this$static);
  this$static.setHTML(html);
  return this$static;
}

function adjustType(button){
  $clinit_22();
  if (button.type == 'submit') {
    try {
      button.setAttribute('type', 'button');
    }
     catch (e) {
    }
  }
}

function Button(){
}

_ = Button.prototype = new ButtonBase();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Button';
_.typeId$ = 25;
function $CellPanel(this$static){
  $ComplexPanel(this$static);
  this$static.table = createTable();
  this$static.body_0 = createTBody();
  appendChild(this$static.table, this$static.body_0);
  this$static.setElement(this$static.table);
  return this$static;
}

function $getWidgetTd(this$static, w){
  if (w.parent_0 !== this$static) {
    return null;
  }
  return getParent(w.getElement());
}

function $setCellHorizontalAlignment(this$static, td, align){
  setElementProperty(td, 'align', align.textAlignString);
}

function $setCellVerticalAlignment(this$static, td, align){
  setStyleAttribute(td, 'verticalAlign', align.verticalAlignString);
}

function $setSpacing(this$static, spacing){
  setElementPropertyInt(this$static.table, 'cellSpacing', spacing);
}

function setCellHeight(w, height){
  var td;
  td = getParent(w.getElement());
  setElementProperty(td, 'height', height);
}

function setCellHorizontalAlignment(w, align){
  var td;
  td = $getWidgetTd(this, w);
  if (td !== null) {
    $setCellHorizontalAlignment(this, td, align);
  }
}

function setCellWidth(w, width){
  var td;
  td = getParent(w.getElement());
  setElementProperty(td, 'width', width);
}

function CellPanel(){
}

_ = CellPanel.prototype = new ComplexPanel();
_.setCellHeight = setCellHeight;
_.setCellHorizontalAlignment = setCellHorizontalAlignment;
_.setCellWidth = setCellWidth;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'CellPanel';
_.typeId$ = 26;
_.body_0 = null;
_.table = null;
function $advanceToFind(this$static, iter, o){
  var t;
  while (iter.hasNext()) {
    t = iter.next_0();
    if (o === null?t === null:o.equals$(t)) {
      return iter;
    }
  }
  return null;
}

function add_0(o){
  throw $UnsupportedOperationException(new UnsupportedOperationException(), 'add');
}

function contains(o){
  var iter;
  iter = $advanceToFind(this, this.iterator(), o);
  return iter !== null;
}

function isEmpty(){
  return this.size_0() == 0;
}

function remove_15(o){
  var iter;
  iter = $advanceToFind(this, this.iterator(), o);
  if (iter !== null) {
    iter.remove();
    return true;
  }
   else {
    return false;
  }
}

function toArray(){
  return this.toArray_0(initDims_0('[Ljava.lang.Object;', [166], [16], [this.size_0()], null));
}

function toArray_0(a){
  var i, it, size;
  size = this.size_0();
  if (a.length_0 < size) {
    a = clonify_0(a, size);
  }
  i = 0;
  for (it = this.iterator(); it.hasNext();) {
    setCheck(a, i++, it.next_0());
  }
  if (a.length_0 > size) {
    setCheck(a, size, null);
  }
  return a;
}

function toString_17(){
  var comma, iter, sb;
  sb = $StringBuffer(new StringBuffer());
  comma = null;
  $append(sb, '[');
  iter = this.iterator();
  while (iter.hasNext()) {
    if (comma !== null) {
      $append(sb, comma);
    }
     else {
      comma = ', ';
    }
    $append(sb, valueOf_2(iter.next_0()));
  }
  $append(sb, ']');
  return $toString_0(sb);
}

function AbstractCollection(){
}

_ = AbstractCollection.prototype = new Object_0();
_.add_1 = add_0;
_.contains = contains;
_.isEmpty = isEmpty;
_.remove_2 = remove_15;
_.toArray = toArray;
_.toArray_0 = toArray_0;
_.toString$ = toString_17;
_.typeName$ = package_java_util_ + 'AbstractCollection';
_.typeId$ = 27;
function $indexOutOfBounds(this$static, i){
  throw $IndexOutOfBoundsException(new IndexOutOfBoundsException(), 'Index: ' + i + ', Size: ' + this$static.size);
}

function $iterator_1(this$static){
  return $AbstractList$IteratorImpl(new AbstractList$IteratorImpl(), this$static);
}

function add_1(index, element){
  throw $UnsupportedOperationException(new UnsupportedOperationException(), 'add');
}

function add_2(obj){
  this.add_0(this.size_0(), obj);
  return true;
}

function equals_10(o){
  var elem, elemOther, iter, iterOther, other;
  if (o === this) {
    return true;
  }
  if (!instanceOf(o, 46)) {
    return false;
  }
  other = dynamicCast(o, 46);
  if (this.size_0() != other.size_0()) {
    return false;
  }
  iter = $iterator_1(this);
  iterOther = other.iterator();
  while ($hasNext_1(iter)) {
    elem = $next_0(iter);
    elemOther = $next_0(iterOther);
    if (!(elem === null?elemOther === null:elem.equals$(elemOther))) {
      return false;
    }
  }
  return true;
}

function hashCode_8(){
  var coeff, iter, k, obj;
  k = 1;
  coeff = 31;
  iter = $iterator_1(this);
  while ($hasNext_1(iter)) {
    obj = $next_0(iter);
    k = 31 * k + (obj === null?0:obj.hashCode$());
  }
  return k;
}

function iterator_3(){
  return $iterator_1(this);
}

function remove_17(index){
  throw $UnsupportedOperationException(new UnsupportedOperationException(), 'remove');
}

function AbstractList(){
}

_ = AbstractList.prototype = new AbstractCollection();
_.add_0 = add_1;
_.add_1 = add_2;
_.equals$ = equals_10;
_.hashCode$ = hashCode_8;
_.iterator = iterator_3;
_.remove_0 = remove_17;
_.typeName$ = package_java_util_ + 'AbstractList';
_.typeId$ = 28;
function $$init_21(this$static){
  {
    $clearImpl(this$static);
  }
}

function $ArrayList(this$static){
  $$init_21(this$static);
  return this$static;
}

function $ArrayList_0(this$static, c){
  $$init_21(this$static);
  $addAll(this$static, c);
  return this$static;
}

function $add_13(this$static, o){
  setImpl(this$static.array, this$static.size++, o);
  return true;
}

function $addAll(this$static, c){
  var changed, iter;
  iter = $iterator_1(c);
  changed = iter.hasNext();
  while (iter.hasNext()) {
    setImpl(this$static.array, this$static.size++, iter.next_0());
  }
  return changed;
}

function $clearImpl(this$static){
  this$static.array = createArray();
  this$static.size = 0;
}

function $get_0(this$static, index){
  if (index < 0 || index >= this$static.size) {
    $indexOutOfBounds(this$static, index);
  }
  return getImpl(this$static.array, index);
}

function $indexOf_2(this$static, o){
  return $indexOf_3(this$static, o, 0);
}

function $indexOf_3(this$static, o, index){
  if (index < 0) {
    $indexOutOfBounds(this$static, index);
  }
  for (; index < this$static.size; ++index) {
    if (equals_13(o, getImpl(this$static.array, index))) {
      return index;
    }
  }
  return (-1);
}

function $remove_9(this$static, index){
  var previous;
  previous = $get_0(this$static, index);
  removeRangeImpl(this$static.array, index, 1);
  --this$static.size;
  return previous;
}

function $remove_10(this$static, o){
  var i;
  i = $indexOf_2(this$static, o);
  if (i == (-1)) {
    return false;
  }
  $remove_9(this$static, i);
  return true;
}

function $set(this$static, index, o){
  var previous;
  previous = $get_0(this$static, index);
  setImpl(this$static.array, index, o);
  return previous;
}

function add_3(index, o){
  if (index < 0 || index > this.size) {
    $indexOutOfBounds(this, index);
  }
  addImpl(this.array, index, o);
  ++this.size;
}

function add_4(o){
  return $add_13(this, o);
}

function addImpl(array, index, o){
  array.splice(index, 0, o);
}

function contains_2(o){
  return $indexOf_2(this, o) != (-1);
}

function equals_13(a, b){
  return a === b || a !== null && a.equals$(b);
}

function get_2(index){
  return $get_0(this, index);
}

function getImpl(array, index){
  return array[index];
}

function isEmpty_0(){
  return this.size == 0;
}

function remove_20(index){
  return $remove_9(this, index);
}

function remove_21(o){
  return $remove_10(this, o);
}

function removeRangeImpl(array, index, count){
  array.splice(index, count);
}

function setImpl(array, index, o){
  array[index] = o;
}

function size_2(){
  return this.size;
}

function toArray_1(a){
  var i;
  if (a.length_0 < this.size) {
    a = clonify_0(a, this.size);
  }
  for (i = 0; i < this.size; ++i) {
    setCheck(a, i, getImpl(this.array, i));
  }
  if (a.length_0 > this.size) {
    setCheck(a, this.size, null);
  }
  return a;
}

function ArrayList(){
}

_ = ArrayList.prototype = new AbstractList();
_.add_0 = add_3;
_.add_1 = add_4;
_.contains = contains_2;
_.get = get_2;
_.isEmpty = isEmpty_0;
_.remove_0 = remove_20;
_.remove_2 = remove_21;
_.size_0 = size_2;
_.toArray_0 = toArray_1;
_.typeName$ = package_java_util_ + 'ArrayList';
_.typeId$ = 29;
_.array = null;
_.size = 0;
function $ChangeListenerCollection(this$static){
  $ArrayList(this$static);
  return this$static;
}

function $fireChange(this$static, sender){
  var it, listener;
  for (it = $iterator_1(this$static); $hasNext_1(it);) {
    listener = dynamicCast($next_0(it), 8);
    listener.onChange_0(sender);
  }
}

function ChangeListenerCollection(){
}

_ = ChangeListenerCollection.prototype = new ArrayList();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'ChangeListenerCollection';
_.typeId$ = 30;
function $clinit_26(){
  $clinit_26 = nullMethod;
  $clinit_100() , implWidget;
}

function $CheckBox(this$static){
  $clinit_100() , implWidget;
  $CheckBox_0(this$static, createInputCheck());
  $setStyleName(this$static, 'gwt-CheckBox');
  return this$static;
}

function $CheckBox_1(this$static, label){
  $clinit_100() , implWidget;
  $CheckBox(this$static);
  $setText(this$static, label);
  return this$static;
}

function $CheckBox_0(this$static, elem){
  var uid;
  $clinit_100() , implWidget;
  $ButtonBase(this$static, createSpan());
  this$static.inputElem = elem;
  this$static.labelElem = createLabel();
  sinkEvents(this$static.inputElem, getEventsSunk(this$static.getElement()));
  sinkEvents(this$static.getElement(), 0);
  appendChild(this$static.getElement(), this$static.inputElem);
  appendChild(this$static.getElement(), this$static.labelElem);
  uid = 'check' + ++uniqueId;
  setElementProperty(this$static.inputElem, 'id', uid);
  setElementProperty(this$static.labelElem, 'htmlFor', uid);
  return this$static;
}

function $isChecked(this$static){
  var propName;
  propName = this$static.isAttached()?'checked':'defaultChecked';
  return getElementPropertyBoolean(this$static.inputElem, propName);
}

function $setChecked(this$static, checked){
  setElementPropertyBoolean(this$static.inputElem, 'checked', checked);
  setElementPropertyBoolean(this$static.inputElem, 'defaultChecked', checked);
}

function $setText(this$static, text){
  setInnerText(this$static.labelElem, text);
}

function isEnabled(){
  return !getElementPropertyBoolean(this.inputElem, 'disabled');
}

function onLoad(){
  setEventListener(this.inputElem, this);
}

function onUnload(){
  setEventListener(this.inputElem, null);
  $setChecked(this, $isChecked(this));
}

function setHTML_0(html){
  setInnerHTML(this.labelElem, html);
}

function CheckBox(){
}

_ = CheckBox.prototype = new ButtonBase();
_.isEnabled = isEnabled;
_.onLoad = onLoad;
_.onUnload = onUnload;
_.setHTML = setHTML_0;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'CheckBox';
_.typeId$ = 31;
_.inputElem = null;
_.labelElem = null;
var uniqueId = 0;
function $ClickListenerCollection(this$static){
  $ArrayList(this$static);
  return this$static;
}

function $fireClick(this$static, sender){
  var it, listener;
  for (it = $iterator_1(this$static); $hasNext_1(it);) {
    listener = dynamicCast($next_0(it), 9);
    listener.onClick(sender);
  }
}

function ClickListenerCollection(){
}

_ = ClickListenerCollection.prototype = new ArrayList();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'ClickListenerCollection';
_.typeId$ = 32;
function $initWidget(this$static, widget){
  if (this$static.widget_0 !== null) {
    throw $IllegalStateException(new IllegalStateException(), 'Composite.initWidget() may only be called once.');
  }
  $removeFromParent(widget);
  this$static.setElement(widget.getElement());
  this$static.widget_0 = widget;
  $setParent(widget, this$static);
}

function getElement(){
  if (this.widget_0 === null) {
    throw $IllegalStateException(new IllegalStateException(), 'initWidget() was never called in ' + getTypeName(this));
  }
  return this.element;
}

function isAttached(){
  if (this.widget_0 !== null) {
    return this.widget_0.isAttached();
  }
  return false;
}

function onAttach(){
  this.widget_0.onAttach();
  this.onLoad();
}

function onDetach(){
  try {
    this.onUnload();
  }
   finally {
    this.widget_0.onDetach();
  }
}

function Composite(){
}

_ = Composite.prototype = new Widget();
_.getElement = getElement;
_.isAttached = isAttached;
_.onAttach = onAttach;
_.onDetach = onDetach;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Composite';
_.typeId$ = 33;
_.widget_0 = null;
function $DeckPanel(this$static){
  $ComplexPanel(this$static);
  this$static.setElement(createDiv());
  return this$static;
}

function $initChildWidget(this$static, w){
  var child;
  child = w.getElement();
  setStyleAttribute(child, 'width', '100%');
  setStyleAttribute(child, 'height', '100%');
  w.setVisible(false);
}

function $insert_0(this$static, w, beforeIndex){
  $insert(this$static, w, this$static.getElement(), beforeIndex, true);
  $initChildWidget(this$static, w);
}

function $remove_1(this$static, w){
  var removed;
  removed = $remove_0(this$static, w);
  if (removed) {
    $resetChildWidget(this$static, w);
    if (this$static.visibleWidget === w) {
      this$static.visibleWidget = null;
    }
  }
  return removed;
}

function $resetChildWidget(this$static, w){
  setStyleAttribute(w.getElement(), 'width', '');
  setStyleAttribute(w.getElement(), 'height', '');
  w.setVisible(true);
}

function $showWidget(this$static, index){
  $checkIndexBoundsForAccess(this$static, index);
  if (this$static.visibleWidget !== null) {
    this$static.visibleWidget.setVisible(false);
  }
  this$static.visibleWidget = $getWidget(this$static, index);
  this$static.visibleWidget.setVisible(true);
}

function remove_2(w){
  return $remove_1(this, w);
}

function DeckPanel(){
}

_ = DeckPanel.prototype = new ComplexPanel();
_.remove_1 = remove_2;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'DeckPanel';
_.typeId$ = 34;
_.visibleWidget = null;
function $SimplePanel(this$static){
  $SimplePanel_0(this$static, createDiv());
  return this$static;
}

function $SimplePanel_0(this$static, elem){
  this$static.setElement(elem);
  return this$static;
}

function $add_4(this$static, w){
  if (this$static.widget_0 !== null) {
    throw $IllegalStateException(new IllegalStateException(), 'SimplePanel can only contain one child widget');
  }
  this$static.setWidget(w);
}

function $setWidget_2(this$static, w){
  if (w === this$static.widget_0) {
    return;
  }
  if (w !== null) {
    $removeFromParent(w);
  }
  if (this$static.widget_0 !== null) {
    this$static.remove_1(this$static.widget_0);
  }
  this$static.widget_0 = w;
  if (w !== null) {
    appendChild(this$static.getContainerElement(), this$static.widget_0.getElement());
    $adopt(this$static, w);
  }
}

function getContainerElement_0(){
  return this.getElement();
}

function iterator_1(){
  return $SimplePanel$1(new SimplePanel$1(), this);
}

function remove_9(w){
  if (this.widget_0 !== w) {
    return false;
  }
  $orphan(this, w);
  removeChild(this.getContainerElement(), w.getElement());
  this.widget_0 = null;
  return true;
}

function setWidget_1(w){
  $setWidget_2(this, w);
}

function SimplePanel(){
}

_ = SimplePanel.prototype = new Panel();
_.getContainerElement = getContainerElement_0;
_.iterator = iterator_1;
_.remove_1 = remove_9;
_.setWidget = setWidget_1;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'SimplePanel';
_.typeId$ = 35;
_.widget_0 = null;
function $clinit_70(){
  $clinit_70 = nullMethod;
  impl_0 = new PopupImpl();
}

function $PopupPanel(this$static){
  $clinit_70();
  $SimplePanel_0(this$static, $createElement_0(impl_0));
  $setPopupPosition(this$static, 0, 0);
  return this$static;
}

function $PopupPanel_0(this$static, autoHide){
  $clinit_70();
  $PopupPanel(this$static);
  this$static.autoHide = autoHide;
  return this$static;
}

function $PopupPanel_1(this$static, autoHide, modal){
  $clinit_70();
  $PopupPanel_0(this$static, autoHide);
  this$static.modal = modal;
  return this$static;
}

function $blur(this$static, elt){
  if (elt.blur) {
    elt.blur();
  }
}

function $getContainerElement(this$static){
  return this$static.getElement();
}

function $hide(this$static){
  $hide_0(this$static, false);
}

function $hide_0(this$static, autoClosed){
  if (!this$static.showing) {
    return;
  }
  this$static.showing = false;
  get().remove_1(this$static);
  this$static.getElement();
}

function $maybeUpdateSize(this$static){
  var w;
  w = this$static.widget_0;
  if (w !== null) {
    if (this$static.desiredHeight !== null) {
      w.setHeight(this$static.desiredHeight);
    }
    if (this$static.desiredWidth !== null) {
      w.setWidth(this$static.desiredWidth);
    }
  }
}

function $onEventPreview(this$static, event_0){
  var allow, eventTargetsPopup, target, type;
  target = eventGetTarget(event_0);
  eventTargetsPopup = isOrHasChild(this$static.getElement(), target);
  type = eventGetType(event_0);
  switch (type) {
    case 128:
      {
        allow = (narrow_char(eventGetKeyCode(event_0)) , getKeyboardModifiers(event_0) , true);
        return allow && (eventTargetsPopup || !this$static.modal);
      }

    case 512:
      {
        allow = (narrow_char(eventGetKeyCode(event_0)) , getKeyboardModifiers(event_0) , true);
        return allow && (eventTargetsPopup || !this$static.modal);
      }

    case 256:
      {
        allow = (narrow_char(eventGetKeyCode(event_0)) , getKeyboardModifiers(event_0) , true);
        return allow && (eventTargetsPopup || !this$static.modal);
      }

    case 4:
    case 8:
    case 64:
    case 1:
    case 2:
      {
        if (($clinit_8() , sCaptureElem) !== null) {
          return true;
        }
        if (!eventTargetsPopup && this$static.autoHide && type == 4) {
          $hide_0(this$static, true);
          return true;
        }
        break;
      }

    case 2048:
      {
        if (this$static.modal && !eventTargetsPopup && target !== null) {
          $blur(this$static, target);
          return false;
        }
      }

  }
  return !this$static.modal || eventTargetsPopup;
}

function $setPopupPosition(this$static, left, top){
  var elem;
  if (left < 0) {
    left = 0;
  }
  if (top < 0) {
    top = 0;
  }
  this$static.leftPosition = left;
  this$static.topPosition = top;
  elem = this$static.getElement();
  setStyleAttribute(elem, 'left', left + 'px');
  setStyleAttribute(elem, 'top', top + 'px');
}

function $setWidget_1(this$static, w){
  $setWidget_2(this$static, w);
  $maybeUpdateSize(this$static);
}

function $setWidth_0(this$static, width){
  this$static.desiredWidth = width;
  $maybeUpdateSize(this$static);
  if ($length(width) == 0) {
    this$static.desiredWidth = null;
  }
}

function $show(this$static){
  if (this$static.showing) {
    return;
  }
  this$static.showing = true;
  addEventPreview(this$static);
  setStyleAttribute(this$static.getElement(), 'position', 'absolute');
  if (this$static.topPosition != (-1)) {
    $setPopupPosition(this$static, this$static.leftPosition, this$static.topPosition);
  }
  $add(get(), this$static);
  this$static.getElement();
}

function getContainerElement(){
  return $getContainerElement(this);
}

function getOffsetHeight(){
  return $getOffsetHeight(this);
}

function getOffsetWidth(){
  return $getOffsetWidth(this);
}

function getStyleElement(){
  return this.getElement();
}

function getTitle(){
  return getElementProperty($getContainerElement(this), 'title');
}

function onDetach_0(){
  removeEventPreview(this);
  $onDetach(this);
}

function onEventPreview_0(event_0){
  return $onEventPreview(this, event_0);
}

function setHeight(height){
  this.desiredHeight = height;
  $maybeUpdateSize(this);
  if ($length(height) == 0) {
    this.desiredHeight = null;
  }
}

function setTitle(title){
  var containerElement;
  containerElement = $getContainerElement(this);
  if (title === null || $length(title) == 0) {
    removeElementAttribute(containerElement, 'title');
  }
   else {
    setElementAttribute(containerElement, 'title', title);
  }
}

function setVisible(visible){
  setStyleAttribute(this.getElement(), 'visibility', visible?'visible':'hidden');
  this.getElement();
}

function setWidget_0(w){
  $setWidget_1(this, w);
}

function setWidth_0(width){
  $setWidth_0(this, width);
}

function PopupPanel(){
}

_ = PopupPanel.prototype = new SimplePanel();
_.getContainerElement = getContainerElement;
_.getOffsetHeight = getOffsetHeight;
_.getOffsetWidth = getOffsetWidth;
_.getStyleElement = getStyleElement;
_.getTitle = getTitle;
_.onDetach = onDetach_0;
_.onEventPreview = onEventPreview_0;
_.setHeight = setHeight;
_.setTitle = setTitle;
_.setVisible = setVisible;
_.setWidget = setWidget_0;
_.setWidth = setWidth_0;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'PopupPanel';
_.typeId$ = 36;
_.autoHide = false;
_.desiredHeight = null;
_.desiredWidth = null;
_.leftPosition = (-1);
_.modal = false;
_.showing = false;
_.topPosition = (-1);
var impl_0;
function $clinit_32(){
  $clinit_32 = nullMethod;
  $clinit_70();
}

function $$init_0(this$static){
  this$static.caption = $HTML(new HTML());
  this$static.panel = $FlexTable(new FlexTable());
}

function $DialogBox(this$static, autoHide){
  $clinit_32();
  $DialogBox_0(this$static, autoHide, true);
  return this$static;
}

function $DialogBox_0(this$static, autoHide, modal){
  $clinit_32();
  $PopupPanel_1(this$static, autoHide, modal);
  $$init_0(this$static);
  $setWidget_0(this$static.panel, 0, 0, this$static.caption);
  this$static.panel.setHeight('100%');
  $setBorderWidth(this$static.panel, 0);
  $setCellPadding(this$static.panel, 0);
  $setCellSpacing(this$static.panel, 0);
  $setHeight(this$static.panel.cellFormatter, 1, 0, '100%');
  $setWidth(this$static.panel.cellFormatter, 1, 0, '100%');
  $setAlignment(this$static.panel.cellFormatter, 1, 0, ($clinit_53() , ALIGN_CENTER), ($clinit_57() , ALIGN_MIDDLE));
  $setWidget_1(this$static, this$static.panel);
  $setStyleName(this$static, 'gwt-DialogBox');
  $setStyleName(this$static.caption, 'Caption');
  $addMouseListener(this$static.caption, this$static);
  return this$static;
}

function $setWidget(this$static, w){
  if (this$static.child !== null) {
    $remove_2(this$static.panel, this$static.child);
  }
  if (w !== null) {
    $setWidget_0(this$static.panel, 1, 0, w);
  }
  this$static.child = w;
}

function onEventPreview(event_0){
  if (eventGetType(event_0) == 4) {
    if (isOrHasChild(this.caption.getElement(), eventGetTarget(event_0))) {
      eventPreventDefault(event_0);
    }
  }
  return $onEventPreview(this, event_0);
}

function onMouseDown(sender, x, y){
  this.dragging = true;
  setCapture(this.caption.getElement());
  this.dragStartX = x;
  this.dragStartY = y;
}

function onMouseEnter(sender){
}

function onMouseLeave(sender){
}

function onMouseMove(sender, x, y){
  var absX, absY;
  if (this.dragging) {
    absX = x + $getAbsoluteLeft_0(this);
    absY = y + $getAbsoluteTop_0(this);
    $setPopupPosition(this, absX - this.dragStartX, absY - this.dragStartY);
  }
}

function onMouseUp(sender, x, y){
  this.dragging = false;
  releaseCapture(this.caption.getElement());
}

function remove_3(w){
  if (this.child !== w) {
    return false;
  }
  $remove_2(this.panel, w);
  return true;
}

function setWidget(w){
  $setWidget(this, w);
}

function setWidth(width){
  $setWidth_0(this, width);
  this.panel.setWidth('100%');
}

function DialogBox(){
}

_ = DialogBox.prototype = new PopupPanel();
_.onEventPreview = onEventPreview;
_.onMouseDown = onMouseDown;
_.onMouseEnter = onMouseEnter;
_.onMouseLeave = onMouseLeave;
_.onMouseMove = onMouseMove;
_.onMouseUp = onMouseUp;
_.remove_1 = remove_3;
_.setWidget = setWidget;
_.setWidth = setWidth;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'DialogBox';
_.typeId$ = 37;
_.child = null;
_.dragStartX = 0;
_.dragStartY = 0;
_.dragging = false;
function $clinit_36(){
  $clinit_36 = nullMethod;
  CENTER = new DockPanel$DockLayoutConstant();
  EAST = new DockPanel$DockLayoutConstant();
  NORTH = new DockPanel$DockLayoutConstant();
  SOUTH = new DockPanel$DockLayoutConstant();
  WEST = new DockPanel$DockLayoutConstant();
}

function $$init_1(this$static){
  this$static.horzAlign = ($clinit_53() , ALIGN_LEFT);
  this$static.vertAlign = ($clinit_57() , ALIGN_TOP);
}

function $DockPanel(this$static){
  $clinit_36();
  $CellPanel(this$static);
  $$init_1(this$static);
  setElementPropertyInt(this$static.table, 'cellSpacing', 0);
  setElementPropertyInt(this$static.table, 'cellPadding', 0);
  return this$static;
}

function $add_2(this$static, widget, direction){
  var layout;
  if (direction === CENTER) {
    if (widget === this$static.center) {
      return;
    }
     else if (this$static.center !== null) {
      throw $IllegalArgumentException(new IllegalArgumentException(), 'Only one CENTER widget may be added');
    }
  }
  $removeFromParent(widget);
  $add_7(this$static.children, widget);
  if (direction === CENTER) {
    this$static.center = widget;
  }
  layout = $DockPanel$LayoutData(new DockPanel$LayoutData(), direction);
  $setLayoutData(widget, layout);
  $setCellHorizontalAlignment_0(this$static, widget, this$static.horzAlign);
  $setCellVerticalAlignment_0(this$static, widget, this$static.vertAlign);
  $realizeTable(this$static);
  $adopt(this$static, widget);
}

function $realizeTable(this$static){
  var bodyElem, centerTd, child, colCount, dir, eastCol, i, it, layout, northRow, row, rowCount, rows, southRow, td, westCol;
  bodyElem = this$static.body_0;
  while (getChildCount(bodyElem) > 0) {
    removeChild(bodyElem, getChild(bodyElem, 0));
  }
  rowCount = 1;
  colCount = 1;
  for (it = $iterator_0(this$static.children); $hasNext_0(it);) {
    child = $next(it);
    dir = child.layoutData.direction;
    if (dir === NORTH || dir === SOUTH) {
      ++rowCount;
    }
     else if (dir === EAST || dir === WEST) {
      ++colCount;
    }
  }
  rows = initDims_0('[Lcom.google.gwt.user.client.ui.DockPanel$TmpRow;', [168], [43], [rowCount], null);
  for (i = 0; i < rowCount; ++i) {
    rows[i] = new DockPanel$TmpRow();
    rows[i].tr = createTR();
    appendChild(bodyElem, rows[i].tr);
  }
  westCol = 0;
  eastCol = colCount - 1;
  northRow = 0;
  southRow = rowCount - 1;
  centerTd = null;
  for (it = $iterator_0(this$static.children); $hasNext_0(it);) {
    child = $next(it);
    layout = child.layoutData;
    td = createTD();
    layout.td = td;
    setElementProperty(layout.td, 'align', layout.hAlign);
    setStyleAttribute(layout.td, 'verticalAlign', layout.vAlign);
    setElementProperty(layout.td, 'width', layout.width_0);
    setElementProperty(layout.td, 'height', layout.height_0);
    if (layout.direction === NORTH) {
      insertChild(rows[northRow].tr, td, rows[northRow].center);
      appendChild(td, child.getElement());
      setElementPropertyInt(td, 'colSpan', eastCol - westCol + 1);
      ++northRow;
    }
     else if (layout.direction === SOUTH) {
      insertChild(rows[southRow].tr, td, rows[southRow].center);
      appendChild(td, child.getElement());
      setElementPropertyInt(td, 'colSpan', eastCol - westCol + 1);
      --southRow;
    }
     else if (layout.direction === WEST) {
      row = rows[northRow];
      insertChild(row.tr, td, row.center++);
      appendChild(td, child.getElement());
      setElementPropertyInt(td, 'rowSpan', southRow - northRow + 1);
      ++westCol;
    }
     else if (layout.direction === EAST) {
      row = rows[northRow];
      insertChild(row.tr, td, row.center);
      appendChild(td, child.getElement());
      setElementPropertyInt(td, 'rowSpan', southRow - northRow + 1);
      --eastCol;
    }
     else if (layout.direction === CENTER) {
      centerTd = td;
    }
  }
  if (this$static.center !== null) {
    row = rows[northRow];
    insertChild(row.tr, centerTd, row.center);
    appendChild(centerTd, this$static.center.getElement());
  }
}

function $setCellHeight(this$static, w, height){
  var data;
  data = w.layoutData;
  data.height_0 = height;
  if (data.td !== null) {
    setStyleAttribute(data.td, 'height', data.height_0);
  }
}

function $setCellHorizontalAlignment_0(this$static, w, align){
  var data;
  data = w.layoutData;
  data.hAlign = align.textAlignString;
  if (data.td !== null) {
    setElementProperty(data.td, 'align', data.hAlign);
  }
}

function $setCellVerticalAlignment_0(this$static, w, align){
  var data;
  data = w.layoutData;
  data.vAlign = align.verticalAlignString;
  if (data.td !== null) {
    setStyleAttribute(data.td, 'verticalAlign', data.vAlign);
  }
}

function $setCellWidth(this$static, w, width){
  var data;
  data = w.layoutData;
  data.width_0 = width;
  if (data.td !== null) {
    setStyleAttribute(data.td, 'width', data.width_0);
  }
}

function remove_4(w){
  var removed;
  removed = $remove_0(this, w);
  if (removed) {
    if (w === this.center) {
      this.center = null;
    }
    $realizeTable(this);
  }
  return removed;
}

function setCellHeight_0(w, height){
  $setCellHeight(this, w, height);
}

function setCellHorizontalAlignment_0(w, align){
  $setCellHorizontalAlignment_0(this, w, align);
}

function setCellWidth_0(w, width){
  $setCellWidth(this, w, width);
}

function DockPanel(){
}

_ = DockPanel.prototype = new CellPanel();
_.remove_1 = remove_4;
_.setCellHeight = setCellHeight_0;
_.setCellHorizontalAlignment = setCellHorizontalAlignment_0;
_.setCellWidth = setCellWidth_0;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'DockPanel';
_.typeId$ = 38;
_.center = null;
var CENTER, EAST, NORTH, SOUTH, WEST;
function DockPanel$DockLayoutConstant(){
}

_ = DockPanel$DockLayoutConstant.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'DockPanel$DockLayoutConstant';
_.typeId$ = 39;
function $DockPanel$LayoutData(this$static, dir){
  this$static.direction = dir;
  return this$static;
}

function DockPanel$LayoutData(){
}

_ = DockPanel$LayoutData.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'DockPanel$LayoutData';
_.typeId$ = 40;
_.direction = null;
_.hAlign = 'left';
_.height_0 = '';
_.td = null;
_.vAlign = 'top';
_.width_0 = '';
function DockPanel$TmpRow(){
}

_ = DockPanel$TmpRow.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'DockPanel$TmpRow';
_.typeId$ = 41;
_.center = 0;
_.tr = null;
function $$init_4(this$static){
  this$static.widgetMap = $HTMLTable$WidgetMapper(new HTMLTable$WidgetMapper());
}

function $HTMLTable(this$static){
  $$init_4(this$static);
  this$static.tableElem = createTable();
  this$static.bodyElem = createTBody();
  appendChild(this$static.tableElem, this$static.bodyElem);
  this$static.setElement(this$static.tableElem);
  this$static.sinkEvents(1);
  return this$static;
}

function $checkCellBounds(this$static, row, column){
  var cellSize;
  $checkRowBounds(this$static, row);
  if (column < 0) {
    throw $IndexOutOfBoundsException(new IndexOutOfBoundsException(), 'Column ' + column + ' must be non-negative: ' + column);
  }
  cellSize = this$static.getCellCount(row);
  if (cellSize <= column) {
    throw $IndexOutOfBoundsException(new IndexOutOfBoundsException(), 'Column index: ' + column + ', Column size: ' + this$static.getCellCount(row));
  }
}

function $checkRowBounds(this$static, row){
  var rowSize;
  rowSize = this$static.getRowCount();
  if (row >= rowSize || row < 0) {
    throw $IndexOutOfBoundsException(new IndexOutOfBoundsException(), 'Row index: ' + row + ', Row size: ' + rowSize);
  }
}

function $cleanCell(this$static, row, column, clearInnerHTML){
  var td;
  td = $getRawElement(this$static.cellFormatter, row, column);
  $internalClearCell(this$static, td, clearInnerHTML);
  return td;
}

function $createCell(this$static){
  return createTD();
}

function $getDOMCellCount(this$static, tableBody, row){
  return tableBody.rows[row].cells.length;
}

function $getDOMRowCount(this$static){
  return $getDOMRowCount_0(this$static, this$static.bodyElem);
}

function $getDOMRowCount_0(this$static, elem){
  return elem.rows.length;
}

function $getWidgetImpl(this$static, row, column){
  var child, e;
  e = $getRawElement(this$static.cellFormatter, row, column);
  child = getFirstChild(e);
  if (child === null) {
    return null;
  }
   else {
    return $getWidget_0(this$static.widgetMap, child);
  }
}

function $insertCell(this$static, row, column){
  var td, tr;
  tr = $getRow(this$static.rowFormatter, this$static.bodyElem, row);
  td = this$static.createCell();
  insertChild(tr, td, column);
}

function $insertRow_0(this$static, beforeRow){
  var tr;
  if (beforeRow != $getRowCount(this$static)) {
    $checkRowBounds(this$static, beforeRow);
  }
  tr = createTR();
  insertChild(this$static.bodyElem, tr, beforeRow);
  return beforeRow;
}

function $internalClearCell(this$static, td, clearInnerHTML){
  var maybeChild, widget;
  maybeChild = getFirstChild(td);
  widget = null;
  if (maybeChild !== null) {
    widget = $getWidget_0(this$static.widgetMap, maybeChild);
  }
  if (widget !== null) {
    $remove_2(this$static, widget);
    return true;
  }
   else {
    if (clearInnerHTML) {
      setInnerHTML(td, '');
    }
    return false;
  }
}

function $remove_2(this$static, widget){
  var elem;
  if (widget.parent_0 !== this$static) {
    return false;
  }
  $orphan(this$static, widget);
  elem = widget.getElement();
  removeChild(getParent(elem), elem);
  $removeWidgetByElement(this$static.widgetMap, elem);
  return true;
}

function $removeCell(this$static, row, column){
  var td, tr;
  $checkCellBounds(this$static, row, column);
  td = $cleanCell(this$static, row, column, false);
  tr = $getRow(this$static.rowFormatter, this$static.bodyElem, row);
  removeChild(tr, td);
}

function $removeRow(this$static, row){
  var column, columnCount;
  columnCount = this$static.getCellCount(row);
  for (column = 0; column < columnCount; ++column) {
    $cleanCell(this$static, row, column, false);
  }
  removeChild(this$static.bodyElem, $getRow(this$static.rowFormatter, this$static.bodyElem, row));
}

function $setBorderWidth(this$static, width){
  setElementProperty(this$static.tableElem, 'border', '' + width);
}

function $setCellFormatter(this$static, cellFormatter){
  this$static.cellFormatter = cellFormatter;
}

function $setCellPadding(this$static, padding){
  setElementPropertyInt(this$static.tableElem, 'cellPadding', padding);
}

function $setCellSpacing(this$static, spacing){
  setElementPropertyInt(this$static.tableElem, 'cellSpacing', spacing);
}

function $setColumnFormatter(this$static, formatter){
  this$static.columnFormatter = formatter;
  $prepareColumnGroup(this$static.columnFormatter);
}

function $setRowFormatter(this$static, rowFormatter){
  this$static.rowFormatter = rowFormatter;
}

function $setWidget_0(this$static, row, column, widget){
  var td;
  this$static.prepareCell(row, column);
  if (widget !== null) {
    $removeFromParent(widget);
    td = $cleanCell(this$static, row, column, true);
    $putWidget(this$static.widgetMap, widget);
    appendChild(td, widget.getElement());
    $adopt(this$static, widget);
  }
}

function clear(){
  var child, col, row;
  for (row = 0; row < this.getRowCount(); ++row) {
    for (col = 0; col < this.getCellCount(row); ++col) {
      child = $getWidgetImpl(this, row, col);
      if (child !== null) {
        $remove_2(this, child);
      }
    }
  }
}

function createCell_0(){
  return $createCell(this);
}

function insertCell_0(row, column){
  $insertCell(this, row, column);
}

function iterator_0(){
  return $widgetIterator(this.widgetMap);
}

function onBrowserEvent_0(event_0){
  switch (eventGetType(event_0)) {
    case 1:
      {
        break;
      }

    default:}
}

function remove_6(widget){
  return $remove_2(this, widget);
}

function removeCell_0(row, column){
  $removeCell(this, row, column);
}

function removeRow_0(row){
  $removeRow(this, row);
}

function HTMLTable(){
}

_ = HTMLTable.prototype = new Panel();
_.clear = clear;
_.createCell = createCell_0;
_.insertCell = insertCell_0;
_.iterator = iterator_0;
_.onBrowserEvent = onBrowserEvent_0;
_.remove_1 = remove_6;
_.removeCell = removeCell_0;
_.removeRow = removeRow_0;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'HTMLTable';
_.typeId$ = 42;
_.bodyElem = null;
_.cellFormatter = null;
_.columnFormatter = null;
_.rowFormatter = null;
_.tableElem = null;
function $FlexTable(this$static){
  $HTMLTable(this$static);
  $setCellFormatter(this$static, $FlexTable$FlexCellFormatter(new FlexTable$FlexCellFormatter(), this$static));
  $setRowFormatter(this$static, new HTMLTable$RowFormatter());
  $setColumnFormatter(this$static, $HTMLTable$ColumnFormatter(new HTMLTable$ColumnFormatter(), this$static));
  return this$static;
}

function $getCellCount(this$static, row){
  $checkRowBounds(this$static, row);
  return $getDOMCellCount(this$static, this$static.bodyElem, row);
}

function $getRowCount(this$static){
  return $getDOMRowCount(this$static);
}

function $insertRow(this$static, beforeRow){
  return $insertRow_0(this$static, beforeRow);
}

function $prepareRow(this$static, row){
  var i, rowCount;
  if (row < 0) {
    throw $IndexOutOfBoundsException(new IndexOutOfBoundsException(), 'Cannot create a row with a negative index: ' + row);
  }
  rowCount = $getRowCount(this$static);
  for (i = rowCount; i <= row; i++) {
    $insertRow(this$static, i);
  }
}

function addCells(table, row, num){
  var rowElem = table.rows[row];
  for (var i = 0; i < num; i++) {
    var cell = $doc.createElement('td');
    rowElem.appendChild(cell);
  }
}

function getCellCount(row){
  return $getCellCount(this, row);
}

function getRowCount(){
  return $getRowCount(this);
}

function insertCell(beforeRow, beforeColumn){
  $insertCell(this, beforeRow, beforeColumn);
}

function prepareCell(row, column){
  var cellCount, required;
  $prepareRow(this, row);
  if (column < 0) {
    throw $IndexOutOfBoundsException(new IndexOutOfBoundsException(), 'Cannot create a column with a negative index: ' + column);
  }
  cellCount = $getCellCount(this, row);
  required = column + 1 - cellCount;
  if (required > 0) {
    addCells(this.bodyElem, row, required);
  }
}

function removeCell(row, col){
  $removeCell(this, row, col);
}

function removeRow(row){
  $removeRow(this, row);
}

function FlexTable(){
}

_ = FlexTable.prototype = new HTMLTable();
_.getCellCount = getCellCount;
_.getRowCount = getRowCount;
_.insertCell = insertCell;
_.prepareCell = prepareCell;
_.removeCell = removeCell;
_.removeRow = removeRow;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'FlexTable';
_.typeId$ = 43;
function $HTMLTable$CellFormatter(this$static, this$0){
  this$static.this$0 = this$0;
  return this$static;
}

function $getCellElement(this$static, table, row, col){
  var out = table.rows[row].cells[col];
  return out == null?null:out;
}

function $getRawElement(this$static, row, column){
  return $getCellElement(this$static, this$static.this$0.bodyElem, row, column);
}

function $setAlignment(this$static, row, column, hAlign, vAlign){
  $setHorizontalAlignment(this$static, row, column, hAlign);
  $setVerticalAlignment(this$static, row, column, vAlign);
}

function $setHeight(this$static, row, column, height){
  var elem;
  this$static.this$0.prepareCell(row, column);
  elem = $getCellElement(this$static, this$static.this$0.bodyElem, row, column);
  setElementProperty(elem, 'height', height);
}

function $setHorizontalAlignment(this$static, row, column, align){
  var elem;
  this$static.this$0.prepareCell(row, column);
  elem = $getCellElement(this$static, this$static.this$0.bodyElem, row, column);
  setElementProperty(elem, 'align', align.textAlignString);
}

function $setVerticalAlignment(this$static, row, column, align){
  this$static.this$0.prepareCell(row, column);
  setStyleAttribute($getCellElement(this$static, this$static.this$0.bodyElem, row, column), 'verticalAlign', align.verticalAlignString);
}

function $setWidth(this$static, row, column, width){
  this$static.this$0.prepareCell(row, column);
  setElementProperty($getCellElement(this$static, this$static.this$0.bodyElem, row, column), 'width', width);
}

function HTMLTable$CellFormatter(){
}

_ = HTMLTable$CellFormatter.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'HTMLTable$CellFormatter';
_.typeId$ = 44;
function $FlexTable$FlexCellFormatter(this$static, this$0){
  $HTMLTable$CellFormatter(this$static, this$0);
  return this$static;
}

function FlexTable$FlexCellFormatter(){
}

_ = FlexTable$FlexCellFormatter.prototype = new HTMLTable$CellFormatter();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'FlexTable$FlexCellFormatter';
_.typeId$ = 45;
function $Grid(this$static){
  $HTMLTable(this$static);
  $setCellFormatter(this$static, $HTMLTable$CellFormatter(new HTMLTable$CellFormatter(), this$static));
  $setRowFormatter(this$static, new HTMLTable$RowFormatter());
  $setColumnFormatter(this$static, $HTMLTable$ColumnFormatter(new HTMLTable$ColumnFormatter(), this$static));
  return this$static;
}

function $Grid_0(this$static, rows, columns){
  $Grid(this$static);
  $resize(this$static, rows, columns);
  return this$static;
}

function $prepareRow_0(this$static, row){
  if (row < 0) {
    throw $IndexOutOfBoundsException(new IndexOutOfBoundsException(), 'Cannot access a row with a negative index: ' + row);
  }
  if (row >= this$static.numRows) {
    throw $IndexOutOfBoundsException(new IndexOutOfBoundsException(), 'Row index: ' + row + ', Row size: ' + this$static.numRows);
  }
}

function $resize(this$static, rows, columns){
  $resizeColumns(this$static, columns);
  $resizeRows(this$static, rows);
}

function $resizeColumns(this$static, columns){
  var i, j;
  if (this$static.numColumns == columns) {
    return;
  }
  if (columns < 0) {
    throw $IndexOutOfBoundsException(new IndexOutOfBoundsException(), 'Cannot set number of columns to ' + columns);
  }
  if (this$static.numColumns > columns) {
    for (i = 0; i < this$static.numRows; i++) {
      for (j = this$static.numColumns - 1; j >= columns; j--) {
        this$static.removeCell(i, j);
      }
    }
  }
   else {
    for (i = 0; i < this$static.numRows; i++) {
      for (j = this$static.numColumns; j < columns; j++) {
        this$static.insertCell(i, j);
      }
    }
  }
  this$static.numColumns = columns;
}

function $resizeRows(this$static, rows){
  if (this$static.numRows == rows) {
    return;
  }
  if (rows < 0) {
    throw $IndexOutOfBoundsException(new IndexOutOfBoundsException(), 'Cannot set number of rows to ' + rows);
  }
  if (this$static.numRows < rows) {
    addRows(this$static.bodyElem, rows - this$static.numRows, this$static.numColumns);
    this$static.numRows = rows;
  }
   else {
    while (this$static.numRows > rows) {
      this$static.removeRow(--this$static.numRows);
    }
  }
}

function addRows(table, rows, columns){
  var td = $doc.createElement('td');
  td.innerHTML = '&nbsp;';
  var row = $doc.createElement('tr');
  for (var cellNum = 0; cellNum < columns; cellNum++) {
    var cell = td.cloneNode(true);
    row.appendChild(cell);
  }
  table.appendChild(row);
  for (var rowNum = 1; rowNum < rows; rowNum++) {
    table.appendChild(row.cloneNode(true));
  }
}

function createCell(){
  var td;
  td = $createCell(this);
  setInnerHTML(td, '&nbsp;');
  return td;
}

function getCellCount_0(row){
  return this.numColumns;
}

function getRowCount_0(){
  return this.numRows;
}

function prepareCell_0(row, column){
  $prepareRow_0(this, row);
  if (column < 0) {
    throw $IndexOutOfBoundsException(new IndexOutOfBoundsException(), 'Cannot access a column with a negative index: ' + column);
  }
  if (column >= this.numColumns) {
    throw $IndexOutOfBoundsException(new IndexOutOfBoundsException(), 'Column index: ' + column + ', Column size: ' + this.numColumns);
  }
}

function Grid(){
}

_ = Grid.prototype = new HTMLTable();
_.createCell = createCell;
_.getCellCount = getCellCount_0;
_.getRowCount = getRowCount_0;
_.prepareCell = prepareCell_0;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Grid';
_.typeId$ = 46;
_.numColumns = 0;
_.numRows = 0;
function $Label(this$static){
  this$static.setElement(createDiv());
  this$static.sinkEvents(131197);
  $setStyleName(this$static, 'gwt-Label');
  return this$static;
}

function $Label_0(this$static, text){
  $Label(this$static);
  $setText_0(this$static, text);
  return this$static;
}

function $addClickListener_0(this$static, listener){
  if (this$static.clickListeners === null) {
    this$static.clickListeners = $ClickListenerCollection(new ClickListenerCollection());
  }
  $add_13(this$static.clickListeners, listener);
}

function $addMouseListener(this$static, listener){
  if (this$static.mouseListeners === null) {
    this$static.mouseListeners = $MouseListenerCollection(new MouseListenerCollection());
  }
  $add_13(this$static.mouseListeners, listener);
}

function $setText_0(this$static, text){
  setInnerText(this$static.getElement(), text);
}

function $setWordWrap(this$static, wrap){
  setStyleAttribute(this$static.getElement(), 'whiteSpace', wrap?'normal':'nowrap');
}

function onBrowserEvent_2(event_0){
  switch (eventGetType(event_0)) {
    case 1:
      if (this.clickListeners !== null) {
        $fireClick(this.clickListeners, this);
      }

      break;
    case 4:
    case 8:
    case 64:
    case 16:
    case 32:
      if (this.mouseListeners !== null) {
        $fireMouseEvent(this.mouseListeners, this, event_0);
      }

      break;
    case 131072:
      break;
  }
}

function Label(){
}

_ = Label.prototype = new Widget();
_.onBrowserEvent = onBrowserEvent_2;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Label';
_.typeId$ = 47;
_.clickListeners = null;
_.mouseListeners = null;
function $HTML(this$static){
  $Label(this$static);
  this$static.setElement(createDiv());
  this$static.sinkEvents(125);
  $setStyleName(this$static, 'gwt-HTML');
  return this$static;
}

function $HTML_0(this$static, html){
  $HTML(this$static);
  $setHTML(this$static, html);
  return this$static;
}

function $HTML_1(this$static, html, wordWrap){
  $HTML_0(this$static, html);
  $setWordWrap(this$static, wordWrap);
  return this$static;
}

function $setHTML(this$static, html){
  setInnerHTML(this$static.getElement(), html);
}

function HTML(){
}

_ = HTML.prototype = new Label();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'HTML';
_.typeId$ = 48;
function $$init_2(this$static){
  {
    $findNext(this$static);
  }
}

function $HTMLTable$1(this$static, this$1){
  this$static.this$1 = this$1;
  $$init_2(this$static);
  return this$static;
}

function $findNext(this$static){
  while (++this$static.nextIndex < this$static.this$1.widgetList.size) {
    if ($get_0(this$static.this$1.widgetList, this$static.nextIndex) !== null) {
      return;
    }
  }
}

function $hasNext(this$static){
  return this$static.nextIndex < this$static.this$1.widgetList.size;
}

function hasNext(){
  return $hasNext(this);
}

function next_0(){
  var result;
  if (!$hasNext(this)) {
    throw new NoSuchElementException();
  }
  result = $get_0(this.this$1.widgetList, this.nextIndex);
  this.lastIndex_0 = this.nextIndex;
  $findNext(this);
  return result;
}

function remove_5(){
  var w;
  if (this.lastIndex_0 < 0) {
    throw new IllegalStateException();
  }
  w = dynamicCast($get_0(this.this$1.widgetList, this.lastIndex_0), 10);
  $removeFromParent(w);
  this.lastIndex_0 = (-1);
}

function HTMLTable$1(){
}

_ = HTMLTable$1.prototype = new Object_0();
_.hasNext = hasNext;
_.next_0 = next_0;
_.remove = remove_5;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'HTMLTable$1';
_.typeId$ = 49;
_.lastIndex_0 = (-1);
_.nextIndex = (-1);
function $HTMLTable$ColumnFormatter(this$static, this$0){
  this$static.this$0 = this$0;
  return this$static;
}

function $prepareColumnGroup(this$static){
  if (this$static.columnGroup === null) {
    this$static.columnGroup = createElement('colgroup');
    insertChild(this$static.this$0.tableElem, this$static.columnGroup, 0);
    appendChild(this$static.columnGroup, createElement('col'));
  }
}

function HTMLTable$ColumnFormatter(){
}

_ = HTMLTable$ColumnFormatter.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'HTMLTable$ColumnFormatter';
_.typeId$ = 50;
_.columnGroup = null;
function $getRow(this$static, elem, row){
  return elem.rows[row];
}

function HTMLTable$RowFormatter(){
}

_ = HTMLTable$RowFormatter.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'HTMLTable$RowFormatter';
_.typeId$ = 51;
function $$init_3(this$static){
  this$static.widgetList = $ArrayList(new ArrayList());
}

function $HTMLTable$WidgetMapper(this$static){
  $$init_3(this$static);
  return this$static;
}

function $getWidget_0(this$static, elem){
  var index;
  index = getWidgetIndex(elem);
  if (index < 0) {
    return null;
  }
  return dynamicCast($get_0(this$static.widgetList, index), 10);
}

function $putWidget(this$static, widget){
  var index;
  if (this$static.freeList === null) {
    index = this$static.widgetList.size;
    $add_13(this$static.widgetList, widget);
  }
   else {
    index = this$static.freeList.index_0;
    $set(this$static.widgetList, index, widget);
    this$static.freeList = this$static.freeList.next;
  }
  setWidgetIndex(widget.getElement(), index);
}

function $removeImpl(this$static, elem, index){
  clearWidgetIndex(elem);
  $set(this$static.widgetList, index, null);
  this$static.freeList = $HTMLTable$WidgetMapper$FreeNode(new HTMLTable$WidgetMapper$FreeNode(), index, this$static.freeList);
}

function $removeWidgetByElement(this$static, elem){
  var index;
  index = getWidgetIndex(elem);
  $removeImpl(this$static, elem, index);
}

function $widgetIterator(this$static){
  return $HTMLTable$1(new HTMLTable$1(), this$static);
}

function clearWidgetIndex(elem){
  elem['__widgetID'] = null;
}

function getWidgetIndex(elem){
  var index = elem['__widgetID'];
  return index == null?-1:index;
}

function setWidgetIndex(elem, index){
  elem['__widgetID'] = index;
}

function HTMLTable$WidgetMapper(){
}

_ = HTMLTable$WidgetMapper.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'HTMLTable$WidgetMapper';
_.typeId$ = 52;
_.freeList = null;
function $HTMLTable$WidgetMapper$FreeNode(this$static, index, next){
  this$static.index_0 = index;
  this$static.next = next;
  return this$static;
}

function HTMLTable$WidgetMapper$FreeNode(){
}

_ = HTMLTable$WidgetMapper$FreeNode.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'HTMLTable$WidgetMapper$FreeNode';
_.typeId$ = 53;
_.index_0 = 0;
_.next = null;
function $clinit_53(){
  $clinit_53 = nullMethod;
  ALIGN_CENTER = $HasHorizontalAlignment$HorizontalAlignmentConstant(new HasHorizontalAlignment$HorizontalAlignmentConstant(), 'center');
  ALIGN_LEFT = $HasHorizontalAlignment$HorizontalAlignmentConstant(new HasHorizontalAlignment$HorizontalAlignmentConstant(), 'left');
  ALIGN_RIGHT = $HasHorizontalAlignment$HorizontalAlignmentConstant(new HasHorizontalAlignment$HorizontalAlignmentConstant(), 'right');
}

var ALIGN_CENTER, ALIGN_LEFT, ALIGN_RIGHT;
function $HasHorizontalAlignment$HorizontalAlignmentConstant(this$static, textAlignString){
  this$static.textAlignString = textAlignString;
  return this$static;
}

function HasHorizontalAlignment$HorizontalAlignmentConstant(){
}

_ = HasHorizontalAlignment$HorizontalAlignmentConstant.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'HasHorizontalAlignment$HorizontalAlignmentConstant';
_.typeId$ = 54;
_.textAlignString = null;
function $clinit_57(){
  $clinit_57 = nullMethod;
  ALIGN_BOTTOM = $HasVerticalAlignment$VerticalAlignmentConstant(new HasVerticalAlignment$VerticalAlignmentConstant(), 'bottom');
  ALIGN_MIDDLE = $HasVerticalAlignment$VerticalAlignmentConstant(new HasVerticalAlignment$VerticalAlignmentConstant(), 'middle');
  ALIGN_TOP = $HasVerticalAlignment$VerticalAlignmentConstant(new HasVerticalAlignment$VerticalAlignmentConstant(), 'top');
}

var ALIGN_BOTTOM, ALIGN_MIDDLE, ALIGN_TOP;
function $HasVerticalAlignment$VerticalAlignmentConstant(this$static, verticalAlignString){
  this$static.verticalAlignString = verticalAlignString;
  return this$static;
}

function HasVerticalAlignment$VerticalAlignmentConstant(){
}

_ = HasVerticalAlignment$VerticalAlignmentConstant.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'HasVerticalAlignment$VerticalAlignmentConstant';
_.typeId$ = 55;
_.verticalAlignString = null;
function $$init_5(this$static){
  this$static.horzAlign = ($clinit_53() , ALIGN_LEFT);
  this$static.vertAlign = ($clinit_57() , ALIGN_TOP);
}

function $HorizontalPanel(this$static){
  $CellPanel(this$static);
  $$init_5(this$static);
  this$static.tableRow = createTR();
  appendChild(this$static.body_0, this$static.tableRow);
  setElementProperty(this$static.table, 'cellSpacing', '0');
  setElementProperty(this$static.table, 'cellPadding', '0');
  return this$static;
}

function $add_3(this$static, w){
  var td;
  td = $createAlignedTd(this$static);
  appendChild(this$static.tableRow, td);
  $add_1(this$static, w, td);
}

function $createAlignedTd(this$static){
  var td;
  td = createTD();
  $setCellHorizontalAlignment(this$static, td, this$static.horzAlign);
  $setCellVerticalAlignment(this$static, td, this$static.vertAlign);
  return td;
}

function $insert_1(this$static, w, beforeIndex){
  var td;
  $checkIndexBoundsForInsertion(this$static, beforeIndex);
  td = $createAlignedTd(this$static);
  insertChild(this$static.tableRow, td, beforeIndex);
  $insert(this$static, w, td, beforeIndex, false);
}

function $remove_3(this$static, w){
  var removed, td;
  td = getParent(w.getElement());
  removed = $remove_0(this$static, w);
  if (removed) {
    removeChild(this$static.tableRow, td);
  }
  return removed;
}

function $setVerticalAlignment_0(this$static, align){
  this$static.vertAlign = align;
}

function remove_7(w){
  return $remove_3(this, w);
}

function HorizontalPanel(){
}

_ = HorizontalPanel.prototype = new CellPanel();
_.remove_1 = remove_7;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'HorizontalPanel';
_.typeId$ = 56;
_.tableRow = null;
function $clinit_63(){
  $clinit_63 = nullMethod;
  $HashMap(new HashMap());
}

function $Image(this$static, url){
  $clinit_63();
  $changeState(this$static, $Image$UnclippedState_0(new Image$UnclippedState(), this$static, url));
  $setStyleName(this$static, 'gwt-Image');
  return this$static;
}

function $addClickListener(this$static, listener){
  if (this$static.clickListeners === null) {
    this$static.clickListeners = $ClickListenerCollection(new ClickListenerCollection());
  }
  $add_13(this$static.clickListeners, listener);
}

function $changeState(this$static, newState){
  this$static.state = newState;
}

function $getHeight_0(this$static){
  return $getHeight(this$static.state, this$static);
}

function $getUrl_0(this$static){
  return $getUrl(this$static.state, this$static);
}

function $getWidth_0(this$static){
  return $getWidth(this$static.state, this$static);
}

function onBrowserEvent_1(event_0){
  switch (eventGetType(event_0)) {
    case 1:
      {
        if (this.clickListeners !== null) {
          $fireClick(this.clickListeners, this);
        }
        break;
      }

    case 4:
    case 8:
    case 64:
    case 16:
    case 32:
      {
        break;
      }

    case 131072:
      break;
    case 32768:
      {
        break;
      }

    case 65536:
      {
        break;
      }

  }
}

function Image_0(){
}

_ = Image_0.prototype = new Widget();
_.onBrowserEvent = onBrowserEvent_1;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Image';
_.typeId$ = 57;
_.clickListeners = null;
_.state = null;
function Image$State(){
}

_ = Image$State.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Image$State';
_.typeId$ = 58;
function $Image$UnclippedState(this$static, image){
  image.setElement(createImg());
  image.sinkEvents(229501);
  return this$static;
}

function $Image$UnclippedState_0(this$static, image, url){
  $Image$UnclippedState(this$static, image);
  $setUrl(this$static, image, url);
  return this$static;
}

function $getHeight(this$static, image){
  return getElementPropertyInt(image.getElement(), 'height');
}

function $getUrl(this$static, image){
  return getImgSrc(image.getElement());
}

function $getWidth(this$static, image){
  return getElementPropertyInt(image.getElement(), 'width');
}

function $setUrl(this$static, image, url){
  setImgSrc(image.getElement(), url);
}

function Image$UnclippedState(){
}

_ = Image$UnclippedState.prototype = new Image$State();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'Image$UnclippedState';
_.typeId$ = 59;
function getKeyboardModifiers(event_0){
  return (eventGetShiftKey(event_0)?1:0) | (eventGetMetaKey(event_0)?8:0) | (eventGetCtrlKey(event_0)?2:0) | (eventGetAltKey(event_0)?4:0);
}

function $MouseListenerCollection(this$static){
  $ArrayList(this$static);
  return this$static;
}

function $fireMouseDown(this$static, sender, x, y){
  var it, listener;
  for (it = $iterator_1(this$static); $hasNext_1(it);) {
    listener = dynamicCast($next_0(it), 11);
    listener.onMouseDown(sender, x, y);
  }
}

function $fireMouseEnter(this$static, sender){
  var it, listener;
  for (it = $iterator_1(this$static); $hasNext_1(it);) {
    listener = dynamicCast($next_0(it), 11);
    listener.onMouseEnter(sender);
  }
}

function $fireMouseEvent(this$static, sender, event_0){
  var from, senderElem, to, x, y;
  senderElem = sender.getElement();
  x = eventGetClientX(event_0) - getAbsoluteLeft(senderElem) + getElementPropertyInt(senderElem, 'scrollLeft') + getScrollLeft();
  y = eventGetClientY(event_0) - getAbsoluteTop(senderElem) + getElementPropertyInt(senderElem, 'scrollTop') + getScrollTop();
  switch (eventGetType(event_0)) {
    case 4:
      $fireMouseDown(this$static, sender, x, y);
      break;
    case 8:
      $fireMouseUp(this$static, sender, x, y);
      break;
    case 64:
      $fireMouseMove(this$static, sender, x, y);
      break;
    case 16:
      from = eventGetFromElement(event_0);
      if (!isOrHasChild(senderElem, from)) {
        $fireMouseEnter(this$static, sender);
      }

      break;
    case 32:
      to = eventGetToElement(event_0);
      if (!isOrHasChild(senderElem, to)) {
        $fireMouseLeave(this$static, sender);
      }

      break;
  }
}

function $fireMouseLeave(this$static, sender){
  var it, listener;
  for (it = $iterator_1(this$static); $hasNext_1(it);) {
    listener = dynamicCast($next_0(it), 11);
    listener.onMouseLeave(sender);
  }
}

function $fireMouseMove(this$static, sender, x, y){
  var it, listener;
  for (it = $iterator_1(this$static); $hasNext_1(it);) {
    listener = dynamicCast($next_0(it), 11);
    listener.onMouseMove(sender, x, y);
  }
}

function $fireMouseUp(this$static, sender, x, y){
  var it, listener;
  for (it = $iterator_1(this$static); $hasNext_1(it);) {
    listener = dynamicCast($next_0(it), 11);
    listener.onMouseUp(sender, x, y);
  }
}

function MouseListenerCollection(){
}

_ = MouseListenerCollection.prototype = new ArrayList();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'MouseListenerCollection';
_.typeId$ = 60;
function $clinit_72(){
  $clinit_72 = nullMethod;
  rootPanels = $HashMap(new HashMap());
}

function $RootPanel(this$static, elem){
  $clinit_72();
  $AbsolutePanel(this$static);
  if (elem === null) {
    elem = getBodyElement();
  }
  this$static.setElement(elem);
  this$static.onAttach();
  return this$static;
}

function get(){
  $clinit_72();
  return get_0(null);
}

function get_0(id){
  $clinit_72();
  var elem, gwt;
  gwt = dynamicCast($get_1(rootPanels, id), 12);
  if (gwt !== null) {
    return gwt;
  }
  elem = null;
  if (rootPanels.size == 0) {
    hookWindowClosing_0();
  }
  $put_0(rootPanels, id, gwt = $RootPanel(new RootPanel(), elem));
  return gwt;
}

function getBodyElement(){
  $clinit_72();
  return $doc.body;
}

function hookWindowClosing_0(){
  $clinit_72();
  addWindowCloseListener(new RootPanel$1());
}

function RootPanel(){
}

_ = RootPanel.prototype = new AbsolutePanel();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'RootPanel';
_.typeId$ = 61;
var rootPanels;
function onWindowClosed_0(){
  var gwt, it;
  for (it = $iterator_3($values(($clinit_72() , rootPanels))); $hasNext_3(it);) {
    gwt = dynamicCast($next_2(it), 12);
    if (gwt.isAttached()) {
      gwt.onDetach();
    }
  }
}

function onWindowClosing_0(){
  return null;
}

function RootPanel$1(){
}

_ = RootPanel$1.prototype = new Object_0();
_.onWindowClosed = onWindowClosed_0;
_.onWindowClosing = onWindowClosing_0;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'RootPanel$1';
_.typeId$ = 62;
function $$init_6(this$static){
  this$static.hasElement = this$static.this$0.widget_0 !== null;
}

function $SimplePanel$1(this$static, this$0){
  this$static.this$0 = this$0;
  $$init_6(this$static);
  return this$static;
}

function hasNext_0(){
  return this.hasElement;
}

function next_1(){
  if (!this.hasElement || this.this$0.widget_0 === null) {
    throw new NoSuchElementException();
  }
  this.hasElement = false;
  return this.returned = this.this$0.widget_0;
}

function remove_8(){
  if (this.returned !== null) {
    this.this$0.remove_1(this.returned);
  }
}

function SimplePanel$1(){
}

_ = SimplePanel$1.prototype = new Object_0();
_.hasNext = hasNext_0;
_.next_0 = next_1;
_.remove = remove_8;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'SimplePanel$1';
_.typeId$ = 63;
_.returned = null;
function $$init_7(this$static){
  this$static.panel = $HorizontalPanel(new HorizontalPanel());
}

function $TabBar(this$static){
  var first, rest;
  $$init_7(this$static);
  $initWidget(this$static, this$static.panel);
  this$static.sinkEvents(1);
  $setStyleName(this$static, 'gwt-TabBar');
  $setVerticalAlignment_0(this$static.panel, ($clinit_57() , ALIGN_BOTTOM));
  first = $HTML_1(new HTML(), '&nbsp;', true);
  rest = $HTML_1(new HTML(), '&nbsp;', true);
  $setStyleName(first, 'gwt-TabBarFirst');
  $setStyleName(rest, 'gwt-TabBarRest');
  first.setHeight('100%');
  rest.setHeight('100%');
  $add_3(this$static.panel, first);
  $add_3(this$static.panel, rest);
  first.setHeight('100%');
  this$static.panel.setCellHeight(first, '100%');
  this$static.panel.setCellWidth(rest, '100%');
  return this$static;
}

function $addTabListener(this$static, listener){
  if (this$static.tabListeners === null) {
    this$static.tabListeners = $TabListenerCollection(new TabListenerCollection());
  }
  $add_13(this$static.tabListeners, listener);
}

function $checkInsertBeforeTabIndex(this$static, beforeIndex){
  if (beforeIndex < 0 || beforeIndex > $getTabCount(this$static)) {
    throw new IndexOutOfBoundsException();
  }
}

function $checkTabIndex(this$static, index){
  if (index < (-1) || index >= $getTabCount(this$static)) {
    throw new IndexOutOfBoundsException();
  }
}

function $getTabCount(this$static){
  return this$static.panel.children.size - 2;
}

function $insertTab(this$static, widget, beforeIndex){
  var decWidget;
  $checkInsertBeforeTabIndex(this$static, beforeIndex);
  decWidget = $TabBar$ClickDecoratorPanel(new TabBar$ClickDecoratorPanel(), widget, this$static);
  $addStyleName(decWidget, 'gwt-TabBarItem');
  $insert_1(this$static.panel, decWidget, beforeIndex + 1);
}

function $onClick(this$static, sender){
  var i;
  for (i = 1; i < this$static.panel.children.size - 1; ++i) {
    if ($getWidget(this$static.panel, i) === sender) {
      $selectTab(this$static, i - 1);
      return;
    }
  }
}

function $removeTab(this$static, index){
  var toRemove;
  $checkTabIndex(this$static, index);
  toRemove = $getWidget(this$static.panel, index + 1);
  if (toRemove === this$static.selectedTab) {
    this$static.selectedTab = null;
  }
  $remove_3(this$static.panel, toRemove);
}

function $selectTab(this$static, index){
  $checkTabIndex(this$static, index);
  if (this$static.tabListeners !== null) {
    if (!$fireBeforeTabSelected(this$static.tabListeners, this$static, index)) {
      return false;
    }
  }
  $setSelectionStyle(this$static, this$static.selectedTab, false);
  if (index == (-1)) {
    this$static.selectedTab = null;
    return true;
  }
  this$static.selectedTab = $getWidget(this$static.panel, index + 1);
  $setSelectionStyle(this$static, this$static.selectedTab, true);
  if (this$static.tabListeners !== null) {
    $fireTabSelected(this$static.tabListeners, this$static, index);
  }
  return true;
}

function $setSelectionStyle(this$static, item, selected){
  if (item !== null) {
    if (selected) {
      $addStyleName(item, 'gwt-TabBarItem-selected');
    }
     else {
      $removeStyleName(item, 'gwt-TabBarItem-selected');
    }
  }
}

function onClick(sender){
  $onClick(this, sender);
}

function TabBar(){
}

_ = TabBar.prototype = new Composite();
_.onClick = onClick;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'TabBar';
_.typeId$ = 64;
_.selectedTab = null;
_.tabListeners = null;
function $TabBar$ClickDecoratorPanel(this$static, child, delegate){
  $SimplePanel(this$static);
  this$static.delegate = delegate;
  this$static.setWidget(child);
  this$static.sinkEvents(1);
  return this$static;
}

function onBrowserEvent_3(event_0){
  switch (eventGetType(event_0)) {
    case 1:
      $onClick(this.delegate, this);
  }
}

function TabBar$ClickDecoratorPanel(){
}

_ = TabBar$ClickDecoratorPanel.prototype = new SimplePanel();
_.onBrowserEvent = onBrowserEvent_3;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'TabBar$ClickDecoratorPanel';
_.typeId$ = 65;
_.delegate = null;
function $TabListenerCollection(this$static){
  $ArrayList(this$static);
  return this$static;
}

function $fireBeforeTabSelected(this$static, sender, tabIndex){
  var it, listener;
  for (it = $iterator_1(this$static); $hasNext_1(it);) {
    listener = dynamicCast($next_0(it), 13);
    if (!listener.onBeforeTabSelected(sender, tabIndex)) {
      return false;
    }
  }
  return true;
}

function $fireTabSelected(this$static, sender, tabIndex){
  var it, listener;
  for (it = $iterator_1(this$static); $hasNext_1(it);) {
    listener = dynamicCast($next_0(it), 13);
    listener.onTabSelected(sender, tabIndex);
  }
}

function TabListenerCollection(){
}

_ = TabListenerCollection.prototype = new ArrayList();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'TabListenerCollection';
_.typeId$ = 66;
function $$init_8(this$static){
  this$static.tabBar = $TabPanel$UnmodifiableTabBar(new TabPanel$UnmodifiableTabBar());
  this$static.deck = $TabPanel$TabbedDeckPanel(new TabPanel$TabbedDeckPanel(), this$static.tabBar);
}

function $TabPanel(this$static){
  var panel;
  $$init_8(this$static);
  panel = $VerticalPanel(new VerticalPanel());
  $add_6(panel, this$static.tabBar);
  $add_6(panel, this$static.deck);
  panel.setCellHeight(this$static.deck, '100%');
  this$static.tabBar.setWidth('100%');
  $addTabListener(this$static.tabBar, this$static);
  $initWidget(this$static, panel);
  $setStyleName(this$static, 'gwt-TabPanel');
  $setStyleName(this$static.deck, 'gwt-TabPanelBottom');
  return this$static;
}

function $add_5(this$static, w, tabWidget){
  $insert_2(this$static, w, tabWidget, this$static.deck.children.size);
}

function $insert_2(this$static, widget, tabWidget, beforeIndex){
  $insertProtected(this$static.deck, widget, tabWidget, beforeIndex);
}

function $selectTab_0(this$static, index){
  $selectTab(this$static.tabBar, index);
}

function iterator_2(){
  return $iterator(this.deck);
}

function onBeforeTabSelected(sender, tabIndex){
  return true;
}

function onTabSelected(sender, tabIndex){
  $showWidget(this.deck, tabIndex);
}

function remove_11(widget){
  return $remove_4(this.deck, widget);
}

function TabPanel(){
}

_ = TabPanel.prototype = new Composite();
_.iterator = iterator_2;
_.onBeforeTabSelected = onBeforeTabSelected;
_.onTabSelected = onTabSelected;
_.remove_1 = remove_11;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'TabPanel';
_.typeId$ = 67;
function $TabPanel$TabbedDeckPanel(this$static, tabBar){
  $DeckPanel(this$static);
  this$static.tabBar = tabBar;
  return this$static;
}

function $insertProtected(this$static, w, tabWidget, beforeIndex){
  var idx;
  idx = $getWidgetIndex(this$static, w);
  if (idx != (-1)) {
    $remove_4(this$static, w);
    if (idx < beforeIndex) {
      beforeIndex--;
    }
  }
  $insertTabProtected(this$static.tabBar, tabWidget, beforeIndex);
  $insert_0(this$static, w, beforeIndex);
}

function $remove_4(this$static, w){
  var idx;
  idx = $getWidgetIndex(this$static, w);
  if (idx != (-1)) {
    $removeTabProtected(this$static.tabBar, idx);
    return $remove_1(this$static, w);
  }
  return false;
}

function clear_1(){
  throw $UnsupportedOperationException(new UnsupportedOperationException(), 'Use TabPanel.clear() to alter the DeckPanel');
}

function remove_10(w){
  return $remove_4(this, w);
}

function TabPanel$TabbedDeckPanel(){
}

_ = TabPanel$TabbedDeckPanel.prototype = new DeckPanel();
_.clear = clear_1;
_.remove_1 = remove_10;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'TabPanel$TabbedDeckPanel';
_.typeId$ = 68;
_.tabBar = null;
function $TabPanel$UnmodifiableTabBar(this$static){
  $TabBar(this$static);
  return this$static;
}

function $insertTabProtected(this$static, widget, beforeIndex){
  $insertTab(this$static, widget, beforeIndex);
}

function $removeTabProtected(this$static, index){
  $removeTab(this$static, index);
}

function TabPanel$UnmodifiableTabBar(){
}

_ = TabPanel$UnmodifiableTabBar.prototype = new TabBar();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'TabPanel$UnmodifiableTabBar';
_.typeId$ = 69;
function $clinit_92(){
  $clinit_92 = nullMethod;
  $clinit_100() , implWidget;
}

function $TextBoxBase(this$static, elem){
  $clinit_100() , implWidget;
  $FocusWidget(this$static, elem);
  return this$static;
}

function $onBrowserEvent_0(this$static, event_0){
  var type;
  $onBrowserEvent(this$static, event_0);
  type = eventGetType(event_0);
  if (type == 1) {
    if (this$static.clickListeners !== null) {
      $fireClick(this$static.clickListeners, this$static);
    }
  }
   else {
  }
}

function addClickListener_0(listener){
  if (this.clickListeners === null) {
    this.clickListeners = $ClickListenerCollection(new ClickListenerCollection());
  }
  $add_13(this.clickListeners, listener);
}

function onBrowserEvent_4(event_0){
  $onBrowserEvent_0(this, event_0);
}

function TextBoxBase(){
}

_ = TextBoxBase.prototype = new FocusWidget();
_.addClickListener = addClickListener_0;
_.onBrowserEvent = onBrowserEvent_4;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'TextBoxBase';
_.typeId$ = 70;
_.clickListeners = null;
function $clinit_93(){
  $clinit_93 = nullMethod;
  $clinit_100() , implWidget;
}

function $TextBox(this$static){
  $clinit_100() , implWidget;
  $TextBoxBase(this$static, createInputText());
  $setStyleName(this$static, 'gwt-TextBox');
  return this$static;
}

function TextBox(){
}

_ = TextBox.prototype = new TextBoxBase();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'TextBox';
_.typeId$ = 71;
function $$init_9(this$static){
  this$static.horzAlign = ($clinit_53() , ALIGN_LEFT);
  this$static.vertAlign = ($clinit_57() , ALIGN_TOP);
}

function $VerticalPanel(this$static){
  $CellPanel(this$static);
  $$init_9(this$static);
  setElementProperty(this$static.table, 'cellSpacing', '0');
  setElementProperty(this$static.table, 'cellPadding', '0');
  return this$static;
}

function $add_6(this$static, w){
  var td, tr;
  tr = createTR();
  td = $createAlignedTd_0(this$static);
  appendChild(tr, td);
  appendChild(this$static.body_0, tr);
  $add_1(this$static, w, td);
}

function $createAlignedTd_0(this$static){
  var td;
  td = createTD();
  $setCellHorizontalAlignment(this$static, td, this$static.horzAlign);
  $setCellVerticalAlignment(this$static, td, this$static.vertAlign);
  return td;
}

function remove_12(w){
  var removed, td;
  td = getParent(w.getElement());
  removed = $remove_0(this, w);
  if (removed) {
    removeChild(this.body_0, getParent(td));
  }
  return removed;
}

function VerticalPanel(){
}

_ = VerticalPanel.prototype = new CellPanel();
_.remove_1 = remove_12;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'VerticalPanel';
_.typeId$ = 72;
function $WidgetCollection(this$static, parent){
  this$static.parent_0 = parent;
  this$static.array = initDims_0('[Lcom.google.gwt.user.client.ui.Widget;', [167], [10], [4], null);
  return this$static;
}

function $add_7(this$static, w){
  $insert_3(this$static, w, this$static.size);
}

function $get(this$static, index){
  if (index < 0 || index >= this$static.size) {
    throw new IndexOutOfBoundsException();
  }
  return this$static.array[index];
}

function $indexOf(this$static, w){
  var i;
  for (i = 0; i < this$static.size; ++i) {
    if (this$static.array[i] === w) {
      return i;
    }
  }
  return (-1);
}

function $insert_3(this$static, w, beforeIndex){
  var i, newArray;
  if (beforeIndex < 0 || beforeIndex > this$static.size) {
    throw new IndexOutOfBoundsException();
  }
  if (this$static.size == this$static.array.length_0) {
    newArray = initDims_0('[Lcom.google.gwt.user.client.ui.Widget;', [167], [10], [this$static.array.length_0 * 2], null);
    for (i = 0; i < this$static.array.length_0; ++i) {
      setCheck(newArray, i, this$static.array[i]);
    }
    this$static.array = newArray;
  }
  ++this$static.size;
  for (i = this$static.size - 1; i > beforeIndex; --i) {
    setCheck(this$static.array, i, this$static.array[i - 1]);
  }
  setCheck(this$static.array, beforeIndex, w);
}

function $iterator_0(this$static){
  return $WidgetCollection$WidgetIterator(new WidgetCollection$WidgetIterator(), this$static);
}

function $remove_5(this$static, index){
  var i;
  if (index < 0 || index >= this$static.size) {
    throw new IndexOutOfBoundsException();
  }
  --this$static.size;
  for (i = index; i < this$static.size; ++i) {
    setCheck(this$static.array, i, this$static.array[i + 1]);
  }
  setCheck(this$static.array, this$static.size, null);
}

function $remove_6(this$static, w){
  var index;
  index = $indexOf(this$static, w);
  if (index == (-1)) {
    throw new NoSuchElementException();
  }
  $remove_5(this$static, index);
}

function WidgetCollection(){
}

_ = WidgetCollection.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'WidgetCollection';
_.typeId$ = 73;
_.array = null;
_.parent_0 = null;
_.size = 0;
function $WidgetCollection$WidgetIterator(this$static, this$0){
  this$static.this$0 = this$0;
  return this$static;
}

function $hasNext_0(this$static){
  return this$static.index_0 < this$static.this$0.size - 1;
}

function $next(this$static){
  if (this$static.index_0 >= this$static.this$0.size) {
    throw new NoSuchElementException();
  }
  return this$static.this$0.array[++this$static.index_0];
}

function hasNext_1(){
  return $hasNext_0(this);
}

function next_2(){
  return $next(this);
}

function remove_13(){
  if (this.index_0 < 0 || this.index_0 >= this.this$0.size) {
    throw new IllegalStateException();
  }
  this.this$0.parent_0.remove_1(this.this$0.array[this.index_0--]);
}

function WidgetCollection$WidgetIterator(){
}

_ = WidgetCollection$WidgetIterator.prototype = new Object_0();
_.hasNext = hasNext_1;
_.next_0 = next_2;
_.remove = remove_13;
_.typeName$ = package_com_google_gwt_user_client_ui_ + 'WidgetCollection$WidgetIterator';
_.typeId$ = 74;
_.index_0 = (-1);
function $clinit_100(){
  $clinit_100 = nullMethod;
  implPanel = $FocusImplOld(new FocusImplOld());
  implWidget = implPanel !== null?$FocusImpl(new FocusImpl()):implPanel;
}

function $FocusImpl(this$static){
  $clinit_100();
  return this$static;
}

function FocusImpl(){
}

_ = FocusImpl.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_impl_ + 'FocusImpl';
_.typeId$ = 75;
var implPanel, implWidget;
function $clinit_99(){
  $clinit_99 = nullMethod;
  $clinit_100();
}

function $$init_10(this$static){
  $createBlurHandler(this$static);
  $createFocusHandler(this$static);
  $createMouseHandler(this$static);
}

function $FocusImplOld(this$static){
  $clinit_99();
  $FocusImpl(this$static);
  $$init_10(this$static);
  return this$static;
}

function $createBlurHandler(this$static){
  return function(evt){
    if (this.parentNode.onblur) {
      this.parentNode.onblur(evt);
    }
  }
  ;
}

function $createFocusHandler(this$static){
  return function(evt){
    if (this.parentNode.onfocus) {
      this.parentNode.onfocus(evt);
    }
  }
  ;
}

function $createMouseHandler(this$static){
  return function(){
    this.firstChild.focus();
  }
  ;
}

function FocusImplOld(){
}

_ = FocusImplOld.prototype = new FocusImpl();
_.typeName$ = package_com_google_gwt_user_client_ui_impl_ + 'FocusImplOld';
_.typeId$ = 76;
function $createElement_0(this$static){
  return createDiv();
}

function PopupImpl(){
}

_ = PopupImpl.prototype = new Object_0();
_.typeName$ = package_com_google_gwt_user_client_ui_impl_ + 'PopupImpl';
_.typeId$ = 77;
function $AbstractDojo(this$static){
  $AbstractDojo_0(this$static, createDiv());
  return this$static;
}

function $AbstractDojo_0(this$static, element){
  $loadDojoWidget(getInstance(), this$static);
  this$static.setElement(element);
  return this$static;
}

function doAfterCreation_0(){
}

function doBeforeDestruction_0(){
}

function free_0(){
  this.dojoWidget = null;
}

function getDojoWidget_0(){
  return this.dojoWidget;
}

function onAttach_2(){
  $onAttach(this);
  $constructDojoWidget(getInstance(), this, this);
}

function onDetach_3(){
  $destroyDojoWidget(getInstance(), this, this);
  $onDetach(this);
}

function onDojoLoad_0(){
}

function AbstractDojo(){
}

_ = AbstractDojo.prototype = new Widget();
_.doAfterCreation = doAfterCreation_0;
_.doBeforeDestruction = doBeforeDestruction_0;
_.free = free_0;
_.getDojoWidget = getDojoWidget_0;
_.onAttach = onAttach_2;
_.onDetach = onDetach_3;
_.onDojoLoad = onDojoLoad_0;
_.typeName$ = package_com_objetdirect_tatami_client_ + 'AbstractDojo';
_.typeId$ = 78;
_.dojoWidget = null;
function $clinit_102(){
  $clinit_102 = nullMethod;
  $clinit_100() , implWidget;
}

function $AbstractDojoFocus_0(this$static, element){
  $clinit_100() , implWidget;
  $FocusWidget(this$static, element);
  $loadDojoWidget(getInstance(), this$static);
  return this$static;
}

function $AbstractDojoFocus(this$static){
  $clinit_100() , implWidget;
  $AbstractDojoFocus_0(this$static, createDiv());
  return this$static;
}

function $addChangeListener(this$static, listener){
  if (this$static.changeListeners === null) {
    this$static.changeListeners = $ChangeListenerCollection(new ChangeListenerCollection());
  }
  $add_13(this$static.changeListeners, listener);
}

function $removeChangeListener(this$static, listener){
  if (this$static.changeListeners !== null) {
    $remove_10(this$static.changeListeners, listener);
  }
}

function doAfterCreation(){
}

function doBeforeDestruction(){
}

function free(){
  this.dojoWidget = null;
}

function getDojoWidget(){
  return this.dojoWidget;
}

function onAttach_1(){
  $constructDojoWidget(getInstance(), this, this);
  $onAttach(this);
}

function onDetach_2(){
  $destroyDojoWidget(getInstance(), this, this);
  $onDetach(this);
}

function onDojoLoad(){
}

function AbstractDojoFocus(){
}

_ = AbstractDojoFocus.prototype = new FocusWidget();
_.doAfterCreation = doAfterCreation;
_.doBeforeDestruction = doBeforeDestruction;
_.free = free;
_.getDojoWidget = getDojoWidget;
_.onAttach = onAttach_1;
_.onDetach = onDetach_2;
_.onDojoLoad = onDojoLoad;
_.typeName$ = package_com_objetdirect_tatami_client_ + 'AbstractDojoFocus';
_.typeId$ = 79;
_.changeListeners = null;
_.dojoWidget = null;
function $clinit_104(){
  $clinit_104 = nullMethod;
  $clinit_100() , implWidget;
}

function $BasePicker(this$static, startDate, endDate){
  $clinit_100() , implWidget;
  $AbstractDojoFocus(this$static);
  return this$static;
}

function $setDate(this$static, date){
  if (this$static.date === null && date !== null || this$static.date !== null && date === null || this$static.date !== null && !$equals_3(this$static.date, date)) {
    this$static.date = date;
    if (this$static.isAttached()) {
      $setDateOnPicker(this$static, getJSDate_0(date));
    }
    if (this$static.changeListeners !== null) {
      $fireChange(this$static.changeListeners, this$static);
    }
  }
}

function $setDateOnPicker(this$static, date){
  $setDojoDate(this$static, this$static.dojoWidget, date);
}

function $setDojoDate(this$static, dojoWidget, date){
  dojoWidget.setValue(date);
}

function doAfterCreation_1(){
  this.setEventCallback(this.dojoWidget);
  if (this.date !== null) {
    $setDateOnPicker(this, getJSDate_0(this.date));
  }
}

function onValueChanged(jsDate){
  var theDate;
  theDate = this.adjust(getJavaDate(jsDate));
  $setDate(this, theDate);
}

function BasePicker(){
}

_ = BasePicker.prototype = new AbstractDojoFocus();
_.doAfterCreation = doAfterCreation_1;
_.onValueChanged_0 = onValueChanged;
_.typeName$ = package_com_objetdirect_tatami_client_ + 'BasePicker';
_.typeId$ = 80;
_.date = null;
function $$init_11(this$static){
  this$static.shadowColor = $Color(new Color(), 0, 0, 0, 15);
}

function $Clock(this$static, url, width){
  $SimplePanel(this$static);
  $$init_11(this$static);
  this$static.setElement(createDiv());
  this$static.current_time = $Date(new Date_0());
  this$static.width_0 = width;
  this$static.center = $Point_1(new Point(), round_int(width / 2), round_int(width / 2));
  this$static.image = url;
  $makeClock(this$static);
  return this$static;
}

function $makeClock(this$static){
  var border, circle, clock, hour_hand_points, minute_hand_points, second_hand_points;
  this$static.canvas_0 = $GraphicCanvas(new GraphicCanvas());
  $setPixelSize(this$static.canvas_0, this$static.width_0, this$static.width_0);
  if (this$static.image !== null) {
    clock = $ImageGfx(new ImageGfx(), this$static.image, this$static.width_0, this$static.width_0);
    $add_10(this$static.canvas_0, clock, 0, 0);
  }
   else {
    border = $Circle(new Circle(), round_int(this$static.width_0 / 3));
    border.setFillColor(($clinit_128() , WHITE));
    $add_10(this$static.canvas_0, border, round_int(this$static.center.x_0), round_int(this$static.center.y_0));
  }
  hour_hand_points = initDims_0('[Lcom.objetdirect.tatami.client.gfx.Point;', [164], [40], [4], null);
  hour_hand_points[0] = $Point_1(new Point(), (-7), 15);
  hour_hand_points[1] = $Point_1(new Point(), 7, 15);
  hour_hand_points[2] = $Point_1(new Point(), 0, (-60));
  hour_hand_points[3] = $Point_1(new Point(), (-7), 15);
  this$static.hour_hand = $Polyline(new Polyline(), hour_hand_points);
  $setStrokeWidth(this$static.hour_hand, 2);
  this$static.hour_hand.setFillColor(($clinit_128() , SILVER));
  $resizeShape(this$static, this$static.hour_hand);
  $add_10(this$static.canvas_0, this$static.hour_hand, round_int(this$static.center.x_0), round_int(this$static.center.y_0));
  this$static.hour_shadow = $Polyline(new Polyline(), hour_hand_points);
  $resizeShape(this$static, this$static.hour_shadow);
  $setShadow(this$static, this$static.hour_shadow, 3, 3);
  minute_hand_points = initDims_0('[Lcom.objetdirect.tatami.client.gfx.Point;', [164], [40], [4], null);
  minute_hand_points[0] = $Point_1(new Point(), (-5), 15);
  minute_hand_points[1] = $Point_1(new Point(), 5, 15);
  minute_hand_points[2] = $Point_1(new Point(), 0, (-100));
  minute_hand_points[3] = $Point_1(new Point(), (-5), 15);
  this$static.minute_hand = $Polyline(new Polyline(), minute_hand_points);
  $resizeShape(this$static, this$static.minute_hand);
  this$static.minute_hand.setFillColor($Color(new Color(), 127, 127, 127, 255));
  $add_10(this$static.canvas_0, this$static.minute_hand, round_int(this$static.center.x_0), round_int(this$static.center.y_0));
  this$static.minute_shadow = $Polyline(new Polyline(), minute_hand_points);
  $resizeShape(this$static, this$static.minute_shadow);
  $setShadow(this$static, this$static.minute_shadow, 3, 3);
  second_hand_points = initDims_0('[Lcom.objetdirect.tatami.client.gfx.Point;', [164], [40], [8], null);
  second_hand_points[0] = $Point_1(new Point(), (-2), 15);
  second_hand_points[1] = $Point_1(new Point(), 2, 15);
  second_hand_points[2] = $Point_1(new Point(), 2, (-105));
  second_hand_points[3] = $Point_1(new Point(), 6, (-105));
  second_hand_points[4] = $Point_1(new Point(), 0, (-116));
  second_hand_points[5] = $Point_1(new Point(), (-6), (-105));
  second_hand_points[6] = $Point_1(new Point(), (-2), (-105));
  second_hand_points[7] = $Point_1(new Point(), (-2), 15);
  this$static.second_hand = $Polyline(new Polyline(), second_hand_points);
  $resizeShape(this$static, this$static.second_hand);
  this$static.second_hand.setFillColor(($clinit_128() , RED));
  $setStrokeColor(this$static.second_hand, ($clinit_128() , PURPLE));
  $add_10(this$static.canvas_0, this$static.second_hand, round_int(this$static.center.x_0), round_int(this$static.center.y_0));
  this$static.second_shadow = $Polyline(new Polyline(), second_hand_points);
  $resizeShape(this$static, this$static.second_shadow);
  $setShadow(this$static, this$static.second_shadow, 4, 4);
  circle = $Circle(new Circle(), 1);
  $add_10(this$static.canvas_0, circle, round_int(this$static.center.x_0), round_int(this$static.center.y_0));
}

function $placeHand(this$static, shape, angle){
  $rotate_0(shape, angle, this$static.center);
}

function $placeHourHand(this$static, h, m, s){
  var angle;
  angle = 30 * (h % 12 + round_int(m / 60) + round_int(s / 3600));
  $placeHand(this$static, this$static.hour_hand, angle - this$static.angleHour);
  $placeHand(this$static, this$static.hour_shadow, angle - this$static.angleHour);
  this$static.angleHour = angle;
}

function $placeMinuteHand(this$static, m, s){
  var angle;
  angle = 6 * (m + round_int(s / 60));
  $placeHand(this$static, this$static.minute_hand, angle - this$static.angleMinute);
  $placeHand(this$static, this$static.minute_shadow, angle - this$static.angleMinute);
  this$static.angleMinute = angle;
}

function $placeSecondHand(this$static, s){
  var angle;
  angle = 6 * s;
  $placeHand(this$static, this$static.second_hand, angle - this$static.angleSecond);
  $placeHand(this$static, this$static.second_shadow, angle - this$static.angleSecond);
  this$static.angleSecond = angle;
}

function $reflectTime(this$static){
  var h, m, s;
  h = $getHours(this$static.current_time);
  m = $getMinutes(this$static.current_time);
  s = $getSeconds(this$static.current_time);
  $placeHourHand(this$static, h, m, s);
  $placeMinuteHand(this$static, m, s);
  $placeSecondHand(this$static, s);
}

function $resizeShape(this$static, shape){
  var factor;
  factor = this$static.width_0 / this$static.defaultWidth;
  if (factor != 1) {
    $scale(shape, factor);
  }
}

function $setShadow(this$static, shadow, dx, dy){
  shadow.setFillColor(this$static.shadowColor);
  $setStrokeStyle(shadow, 'none');
  $setStrokeColor(shadow, this$static.shadowColor);
  $add_10(this$static.canvas_0, shadow, round_int(this$static.center.x_0), round_int(this$static.center.y_0));
  $translate(shadow, dx, dy);
}

function onAttach_3(){
  var timer;
  $onAttach(this);
  $add_4(this, this.canvas_0);
  timer = $Clock$1(new Clock$1(), this);
  $scheduleRepeating(timer, 1000);
}

function Clock(){
}

_ = Clock.prototype = new SimplePanel();
_.onAttach = onAttach_3;
_.typeName$ = package_com_objetdirect_tatami_client_ + 'Clock';
_.typeId$ = 81;
_.angleHour = 0.0;
_.angleMinute = 0.0;
_.angleSecond = 0.0;
_.canvas_0 = null;
_.center = null;
_.current_time = null;
_.defaultWidth = 385;
_.hour_hand = null;
_.hour_shadow = null;
_.image = null;
_.minute_hand = null;
_.minute_shadow = null;
_.second_hand = null;
_.second_shadow = null;
_.width_0 = 385;
function $clinit_105(){
  $clinit_105 = nullMethod;
  $clinit_14();
}

function $Clock$1(this$static, this$0){
  $clinit_105();
  this$static.this$0 = this$0;
  $Timer(this$static);
  return this$static;
}

function run(){
  $reflectTime(this.this$0);
  $setSeconds(this.this$0.current_time, $getSeconds(this.this$0.current_time) + 1);
}

function Clock$1(){
}

_ = Clock$1.prototype = new Timer();
_.run = run;
_.typeName$ = package_com_objetdirect_tatami_client_ + 'Clock$1';
_.typeId$ = 82;
function $clinit_107(){
  $clinit_107 = nullMethod;
  $clinit_100() , implWidget;
}

function $ColorChooser_0(this$static, size){
  $clinit_100() , implWidget;
  $AbstractDojoFocus(this$static);
  setElementProperty(this$static.getElement(), 'id', 'colorP');
  this$static.size = size;
  return this$static;
}

function $ColorChooser(this$static){
  $clinit_100() , implWidget;
  $ColorChooser_0(this$static, '7x10');
  return this$static;
}

function $createColorPalette(this$static, size){
  var widget = new ($wnd.dijit.ColorPalette)({'palette':size});
  return widget;
}

function $setColor(this$static, color){
  if (this$static.color === null && color !== null || this$static.color !== null && color === null || this$static.color !== null && !$equals_1(this$static.color, color)) {
    this$static.color = color;
    if (this$static.changeListeners !== null) {
      $fireChange(this$static.changeListeners, this$static);
    }
  }
}

function $setEventCallback(this$static, dojoWidget){
  dojoWidget.onChange = function(color){
    dojoWidget.gwtWidget.onValueChanged_1(color);
  }
  ;
}

function createDojoWidget(){
  this.dojoWidget = $createColorPalette(this, this.size);
}

function getDojoName(){
  return 'dijit.ColorPalette';
}

function onLoad_2(){
  $setEventCallback(this, this.dojoWidget);
}

function onValueChanged_0(color){
  $setColor(this, color);
}

function ColorChooser(){
}

_ = ColorChooser.prototype = new AbstractDojoFocus();
_.createDojoWidget = createDojoWidget;
_.getDojoName = getDojoName;
_.onLoad = onLoad_2;
_.onValueChanged_1 = onValueChanged_0;
_.typeName$ = package_com_objetdirect_tatami_client_ + 'ColorChooser';
_.typeId$ = 83;
_.color = '#000000';
_.size = '7x10';
function $clinit_108(){
  $clinit_108 = nullMethod;
  $clinit_100() , implWidget;
}

function $ColorPicker_0(this$static, showHsv, showRgb, showHex, animatePoint){
  $clinit_100() , implWidget;
  $AbstractDojoFocus(this$static);
  this$static.showHex = showHex;
  this$static.showRgb = showRgb;
  this$static.animatePoint = animatePoint;
  this$static.showHsv = showHsv;
  return this$static;
}

function $ColorPicker(this$static){
  $clinit_100() , implWidget;
  $ColorPicker_0(this$static, true, true, true, true);
  return this$static;
}

function $createDojoColorPicker(this$static, showHsv, showRgb, showHex, animatePoint){
  var widget = new ($wnd.dojox.widget.ColorPicker)({'showHsv':showHsv, 'showRgb':showRgb, 'showHex':showHex, 'animatePoint':animatePoint});
  return widget;
}

function createDojoWidget_0(){
  this.dojoWidget = $createDojoColorPicker(this, this.showHsv, this.showRgb, this.showHex, this.animatePoint);
}

function getDojoName_0(){
  return 'dojox.widget.ColorPicker';
}

function onLoad_3(){
  startup_0(this);
}

function ColorPicker(){
}

_ = ColorPicker.prototype = new AbstractDojoFocus();
_.createDojoWidget = createDojoWidget_0;
_.getDojoName = getDojoName_0;
_.onLoad = onLoad_3;
_.typeName$ = package_com_objetdirect_tatami_client_ + 'ColorPicker';
_.typeId$ = 84;
_.animatePoint = true;
_.showHex = true;
_.showHsv = true;
_.showRgb = true;
function $clinit_109(){
  $clinit_109 = nullMethod;
  $clinit_100() , implWidget;
}

function $DatePicker(this$static){
  $clinit_100() , implWidget;
  $DatePicker_0(this$static, null, null);
  return this$static;
}

function $DatePicker_0(this$static, startDate, endDate){
  $clinit_100() , implWidget;
  $BasePicker(this$static, startDate, endDate);
  return this$static;
}

function $createDatePicker(this$static, startDate, endDate){
  if (startDate == null)
    startDate = '1492-10-12';
  if (endDate == null)
    endDate = '2492-10-12';
  var widget = new ($wnd.dijit._Calendar)({'constraints':{'min':startDate, 'max':endDate}});
  return widget;
}

function adjust(date){
  return date;
}

function createDojoWidget_1(){
  this.dojoWidget = $createDatePicker(this, null, null);
}

function getDojoName_1(){
  return 'dijit._Calendar';
}

function setEventCallback(dojoWidget){
  dojoWidget.onChange = function(date){
    dojoWidget.gwtWidget.onValueChanged_0(date);
  }
  ;
}

function DatePicker(){
}

_ = DatePicker.prototype = new BasePicker();
_.adjust = adjust;
_.createDojoWidget = createDojoWidget_1;
_.getDojoName = getDojoName_1;
_.setEventCallback = setEventCallback;
_.typeName$ = package_com_objetdirect_tatami_client_ + 'DatePicker';
_.typeId$ = 85;
function getJSDate_0(date){
  return getJSDate($getTime(date));
}

function getJSDate(time){
  return new Date(time);
}

function getJSTime(theDate){
  return theDate.getTime();
}

function getJavaDate(date){
  return $Date_0(new Date_0(), getJSTime(date));
}

function $DojoController(this$static){
  this$static.mapWidget = $HashMap(new HashMap());
  return this$static;
}

function $constructDojoWidget(this$static, widget, gwtWidget){
  var $e0;
  if (widget.getDojoWidget() === null) {
    try {
      widget.createDojoWidget();
      $link(this$static, widget.getDojoWidget(), gwtWidget);
      widget.doAfterCreation();
    }
     catch ($e0) {
      $e0 = caught($e0);
      if (instanceOf($e0, 28)) {
      }
       else 
        throw $e0;
    }
  }
}

function $destroy(this$static, dojoWidget){
  try {
    dojoWidget.destroyRecursive();
  }
   catch (e) {
  }
}

function $destroyDojoWidget(this$static, widget, gwtWidget){
  var dojoWidget;
  dojoWidget = widget.getDojoWidget();
  widget.doBeforeDestruction();
  $unLink(this$static, dojoWidget, gwtWidget);
  $destroy(this$static, dojoWidget);
  widget.free();
}

function $getDomNode(this$static, widget){
  return $getDomNodeDojo(this$static, widget.dojoWidget);
}

function $getDomNodeDojo(this$static, dojoWidget){
  return dojoWidget.domNode;
}

function $link(this$static, dojoWidget, gwtWidget){
  if (dojoWidget !== null) {
    appendChild(gwtWidget.getElement(), $getDomNodeDojo(this$static, dojoWidget));
    $setGWTWidget(this$static, dojoWidget, gwtWidget);
  }
}

function $loadDojoWidget(this$static, widget){
  var $e0, e;
  try {
    if ($require(this$static, widget.getDojoName())) {
      widget.onDojoLoad();
    }
  }
   catch ($e0) {
    $e0 = caught($e0);
    if (instanceOf($e0, 27)) {
      e = $e0;
      throw e;
    }
     else 
      throw $e0;
  }
}

function $require(this$static, widgetDojo){
  var loaded, ok;
  loaded = $containsKey(this$static.mapWidget, widgetDojo);
  if (!loaded) {
    ok = $requireDojo(this$static, widgetDojo);
    if (!ok) {
      throw $IllegalArgumentException(new IllegalArgumentException(), 'bad widget : ' + widgetDojo);
    }
    $put_0(this$static.mapWidget, widgetDojo, ($clinit_165() , TRUE));
  }
  return !loaded;
}

function $requireDojo(this$static, widget){
  var ok = true;
  try {
    $wnd.dojo.require(widget);
  }
   catch (e) {
    ok = false;
  }
  return ok;
}

function $setGWTWidget(this$static, dojoWidget, gwtWidget){
  try {
    dojoWidget.gwtWidget = gwtWidget;
  }
   catch (e) {
  }
}

function $unLink(this$static, dojoWidget, gwtWidget){
  $setGWTWidget(this$static, dojoWidget, null);
  removeChild(gwtWidget.getElement(), $getDomNodeDojo(this$static, dojoWidget));
}

function createArray_0(array){
  var i, jsArray;
  jsArray = createArray();
  for (i = 0; i < array.length_0; i++) {
    jsArray = put(jsArray, i, array[i]);
  }
  return jsArray;
}

function getInstance(){
  if (controller_0 === null) {
    controller_0 = $DojoController(new DojoController());
  }
  return controller_0;
}

function put(array, index, value){
  array[index] = value;
  return array;
}

function startup(dojoWidget){
  dojoWidget.startup();
}

function startup_0(widget){
  startup(widget.getDojoWidget());
}

function DojoController(){
}

_ = DojoController.prototype = new Object_0();
_.typeName$ = package_com_objetdirect_tatami_client_ + 'DojoController';
_.typeId$ = 86;
_.mapWidget = null;
var controller_0 = null;
function $$init_13(this$static){
  this$static.draggables = $HashMap(new HashMap());
  this$static.targets = $HashMap(new HashMap());
  this$static.draggableAffordances = $HashMap(new HashMap());
  this$static.targetAffordances = $HashMap(new HashMap());
  this$static.bases = $HashMap(new HashMap());
  this$static.widgets = $HashMap(new HashMap());
  this$static.listeners = $ArrayList(new ArrayList());
}

function $DragAndDropPanel(this$static){
  $AbsolutePanel(this$static);
  $$init_13(this$static);
  initDragAndDrop();
  return this$static;
}

function $add_8(this$static, widget, left, top){
  var base;
  base = dynamicCast($get_1(this$static.bases, widget), 31);
  if (base !== null) {
    throw $IllegalStateException(new IllegalStateException(), 'Widget already registered in panel');
  }
   else {
    base = $SimplePanel(new SimplePanel());
    $put_0(this$static.bases, widget, base);
    $add_4(base, widget);
    $add_0(this$static, base, left, top);
    initId(base.getElement(), this$static.counter++);
    $put_0(this$static.widgets, wrapJSO(base.getElement(), Element), widget);
  }
}

function $addAffordance(this$static, widget, ddr, affordances, affordance){
  var result, widgetSet;
  result = false;
  widgetSet = dynamicCast($get_1(affordances, affordance), 29);
  if (widgetSet === null) {
    widgetSet = $HashSet(new HashSet());
    $put_0(affordances, affordance, widgetSet);
  }
  if (widgetSet.contains(widget)) {
    result = false;
  }
   else {
    widgetSet.add_1(widget);
    $add_13(ddr.affordances, affordance);
    result = true;
  }
  return result;
}

function $addDragDropListener(this$static, listener){
  $add_13(this$static.listeners, listener);
}

function $addDraggableSlot(this$static, widget, affordance){
  var base;
  base = dynamicCast($get_1(this$static.bases, widget), 31);
  return addDraggableSlotToElement(base.getElement(), affordance);
}

function $addDraggableWidget(this$static, widget, left, top, affordance){
  if (affordance === null) {
    throw $IllegalArgumentException(new IllegalArgumentException(), "Affordance can't be null");
  }
  if ($get_1(this$static.bases, widget) !== null) {
    throw $IllegalStateException(new IllegalStateException(), 'Widget already registered in panel');
  }
   else {
    $add_8(this$static, widget, left, top);
    $setDraggable(this$static, widget, affordance);
  }
}

function $addTargetSlot(this$static, widget, affordance){
  var base;
  base = dynamicCast($get_1(this$static.bases, widget), 31);
  return addTargetSlotToElement(base.getElement(), affordance);
}

function $addTargetWidget(this$static, widget, left, top, affordance){
  if (affordance === null) {
    throw $IllegalArgumentException(new IllegalArgumentException(), "Affordance can't be null");
  }
  if ($get_1(this$static.bases, widget) !== null) {
    throw $IllegalStateException(new IllegalStateException(), 'Widget already registered in panel');
  }
   else {
    $add_8(this$static, widget, left, top);
    $setTarget(this$static, widget, affordance);
  }
}

function $getWidget_1(this$static, e){
  return dynamicCast($get_1(this$static.widgets, wrapJSO(e, Element)), 10);
}

function $getWidgetLeft_0(this$static, widget){
  return $getWidgetLeft(this$static, dynamicCast($get_1(this$static.bases, widget), 10));
}

function $getWidgetTop_0(this$static, widget){
  return $getWidgetTop(this$static, dynamicCast($get_1(this$static.bases, widget), 10));
}

function $rebuild(this$static, widget, ddt){
  var ddr, i, length;
  if (ddt !== null) {
    removeSlot(ddt);
  }
  {
    ddr = dynamicCast($get_1(this$static.draggables, widget), 30);
    if (ddr !== null) {
      ddr.dd = $addDraggableSlot(this$static, widget, dynamicCast($get_0(ddr.affordances, 0), 1));
      setGWTWidget(ddr.dd, this$static);
      length = ddr.affordances.size;
      for (i = 1; i < length; i++) {
        addAffordance(ddr.dd, dynamicCast($get_0(ddr.affordances, i), 1));
      }
    }
  }
  {
    ddr = dynamicCast($get_1(this$static.targets, widget), 30);
    if (ddr !== null) {
      ddr.dd = $addTargetSlot(this$static, widget, dynamicCast($get_0(ddr.affordances, 0), 1));
      length = ddr.affordances.size;
      for (i = 1; i < length; i++) {
        addAffordance(ddr.dd, dynamicCast($get_0(ddr.affordances, i), 1));
      }
    }
  }
}

function $rebuildAll(this$static){
  var ddr, entry, it, widget;
  it = $iterator_4($entrySet(this$static.draggables));
  while ($hasNext_4(it)) {
    entry = $next_3(it);
    widget = dynamicCast(entry.getKey(), 10);
    ddr = dynamicCast(entry.getValue_0(), 30);
    $rebuild(this$static, widget, ddr.dd);
    setGWTWidget(ddr.dd, this$static);
  }
  it = $iterator_4($entrySet(this$static.targets));
  while ($hasNext_4(it)) {
    entry = $next_3(it);
    widget = dynamicCast(entry.getKey(), 10);
    ddr = dynamicCast(entry.getValue_0(), 30);
    $rebuild(this$static, widget, ddr.dd);
  }
}

function $removeAll(this$static){
  var ddr, it;
  it = $iterator_3($values(this$static.draggables));
  while ($hasNext_3(it)) {
    ddr = dynamicCast($next_2(it), 30);
    setGWTWidget(ddr.dd, null);
    removeSlot(ddr.dd);
  }
  it = $iterator_3($values(this$static.targets));
  while ($hasNext_3(it)) {
    ddr = dynamicCast($next_2(it), 30);
    removeSlot(ddr.dd);
  }
}

function $removeWidgetFromDragOrDrop(this$static, widget, widgetMap, affordances){
  var affordance, ddr, i, length, widgetSet;
  ddr = dynamicCast($remove_12(widgetMap, widget), 30);
  length = ddr.affordances.size;
  for (i = 0; i < length; i++) {
    affordance = dynamicCast($get_0(ddr.affordances, i), 1);
    widgetSet = dynamicCast($get_1(affordances, affordance), 29);
    widgetSet.remove_2(widget);
    if (widgetSet.isEmpty()) {
      $remove_12(affordances, affordance);
    }
  }
  return ddr;
}

function $setDraggable(this$static, widget, affordance){
  var dd, ddr, ddt;
  if ($get_1(this$static.bases, widget) === null) {
    throw $IllegalStateException(new IllegalStateException(), 'The widget does not belong to the panel');
  }
  if (affordance === null) {
    throw $IllegalArgumentException(new IllegalArgumentException(), "Affordance can't be null");
  }
  ddr = dynamicCast($get_1(this$static.draggables, widget), 30);
  if (ddr !== null) {
    if ($addAffordance(this$static, widget, ddr, this$static.draggableAffordances, affordance)) {
      if (this$static.isAttached()) {
        addAffordance(ddr.dd, affordance);
      }
    }
  }
   else {
    ddr = $DragAndDropPanel$DDRecord(new DragAndDropPanel$DDRecord());
    if ($addAffordance(this$static, widget, ddr, this$static.draggableAffordances, affordance)) {
      $put_0(this$static.draggables, widget, ddr);
      ddt = dynamicCast($get_1(this$static.targets, widget), 30);
      if (ddt !== null) {
        $rebuild(this$static, widget, ddt.dd);
      }
       else {
        if (this$static.isAttached()) {
          dd = $addDraggableSlot(this$static, widget, affordance);
          setGWTWidget(dd, this$static);
          ddr.dd = dd;
        }
      }
    }
  }
}

function $setTarget(this$static, widget, affordance){
  var dd, ddr;
  if ($get_1(this$static.bases, widget) === null) {
    throw $IllegalStateException(new IllegalStateException(), 'The widget does not belong to the panel');
  }
  if (affordance === null) {
    throw $IllegalArgumentException(new IllegalArgumentException(), "Affordance can't be null");
  }
  ddr = dynamicCast($get_1(this$static.targets, widget), 30);
  if (ddr !== null) {
    if ($addAffordance(this$static, widget, ddr, this$static.targetAffordances, affordance)) {
      if (this$static.isAttached()) {
        addAffordance(ddr.dd, affordance);
      }
    }
  }
   else {
    ddr = $DragAndDropPanel$DDRecord(new DragAndDropPanel$DDRecord());
    if ($addAffordance(this$static, widget, ddr, this$static.targetAffordances, affordance)) {
      if (this$static.isAttached()) {
        dd = $addTargetSlot(this$static, widget, affordance);
        ddr.dd = dd;
      }
      $put_0(this$static.targets, widget, ddr);
    }
  }
}

function $unsetDraggable(this$static, widget){
  var ddr;
  if ($get_1(this$static.bases, widget) === null) {
    throw $IllegalStateException(new IllegalStateException(), 'The widget does not belong to the panel');
  }
  ddr = $removeWidgetFromDragOrDrop(this$static, widget, this$static.draggables, this$static.draggableAffordances);
  if (ddr !== null && this$static.isAttached()) {
    $rebuild(this$static, widget, ddr.dd);
  }
}

function $unsetTarget(this$static, widget){
  var ddr;
  if ($get_1(this$static.bases, widget) === null) {
    throw $IllegalStateException(new IllegalStateException(), 'The widget does not belong to the panel');
  }
  ddr = $removeWidgetFromDragOrDrop(this$static, widget, this$static.targets, this$static.targetAffordances);
  if (ddr !== null && this$static.isAttached()) {
    $rebuild(this$static, widget, ddr.dd);
  }
}

function acceptDrop(e, t){
  var accept, draggable, i, lst, target;
  draggable = $getWidget_1(this, e);
  target = $getWidget_1(this, t);
  accept = true;
  lst = this.listeners.toArray();
  for (i = 0; i < lst.length_0; i++) {
    accept &= dynamicCast(lst[i], 32).acceptDrop_0(draggable, target);
  }
  return accept;
}

function addAffordance(dd, affordance){
  dd.addToGroup(affordance);
}

function addDraggableSlotToElement(e, affordance){
  var slot = new ($wnd.YAHOO.tatami.DDDraggable)(e.id, affordance);
  slot.gwtWidget = null;
  return slot;
}

function addTargetSlotToElement(e, affordance){
  var slot = new ($wnd.YAHOO.util.DDTarget)(e.id, affordance);
  return slot;
}

function dispatchDrop(e, t){
  var draggable, i, lst, target;
  draggable = $getWidget_1(this, e);
  target = $getWidget_1(this, t);
  lst = this.listeners.toArray();
  for (i = 0; i < lst.length_0; i++) {
    dynamicCast(lst[i], 32).onDrop(draggable, target);
  }
}

function initDragAndDrop(){
  if (!dragAndDropLoaded) {
    loadDragAndDrop();
    dragAndDropLoaded = true;
  }
}

function initId(e, counter){
  e.id = 'd_' + counter;
}

function loadDragAndDrop(){
  $wnd.YAHOO.namespace('tatami');
  $wnd.YAHOO.tatami.DDDraggable = function(id, sGroup, config){
    if (!id) {
      return;
    }
    this.initPlayer(id, sGroup, config);
    var fctAccept = function(el, et){
      var r = this.gwtWidget.acceptDrop(el, et);
      return r;
    }
    ;
    var fctDispatch = function(el, et){
      this.gwtWidget.dispatchDrop(el, et);
      return 1;
    }
    ;
    this.accept(fctAccept);
    this.subscribe(fctDispatch);
  }
  ;
  $wnd.YAHOO.extend($wnd.YAHOO.tatami.DDDraggable, $wnd.YAHOO.example.DDPlayer);
}

function onAttach_4(){
  $rebuildAll(this);
  $onAttach(this);
}

function onDetach_4(){
  $removeAll(this);
  $onDetach(this);
}

function remove_14(widget){
  var base, panel;
  base = dynamicCast($get_1(this.bases, widget), 31);
  if (base === null) {
    throw $IllegalStateException(new IllegalStateException(), 'Widget already registered in panel');
  }
   else {
    $unsetDraggable(this, widget);
    $unsetTarget(this, widget);
    $remove_12(this.widgets, wrapJSO(base.getElement(), Element));
    panel = dynamicCast($get_1(this.bases, widget), 31);
    return $remove(this, panel);
  }
}

function removeSlot(dd){
  dd.unreg();
}

function setGWTWidget(slot, gwtWidget){
  slot.gwtWidget = gwtWidget;
}

function DragAndDropPanel(){
}

_ = DragAndDropPanel.prototype = new AbsolutePanel();
_.acceptDrop = acceptDrop;
_.dispatchDrop = dispatchDrop;
_.onAttach = onAttach_4;
_.onDetach = onDetach_4;
_.remove_1 = remove_14;
_.typeName$ = package_com_objetdirect_tatami_client_ + 'DragAndDropPanel';
_.typeId$ = 87;
_.counter = 0;
var dragAndDropLoaded = false;
function $$init_12(this$static){
  this$static.affordances = $ArrayList(new ArrayList());
}

function $DragAndDropPanel$DDRecord(this$static){
  $$init_12(this$static);
  return this$static;
}

function DragAndDropPanel$DDRecord(){
}

_ = DragAndDropPanel$DDRecord.prototype = new Object_0();
_.typeName$ = package_com_objetdirect_tatami_client_ + 'DragAndDropPanel$DDRecord';
_.typeId$ = 88;
_.dd = null;
function $clinit_115(){
  $clinit_115 = nullMethod;
  $clinit_100() , implWidget;
}

function $DropdownContainer(this$static, element, id, startDate, endDate){
  var subElement;
  $clinit_100() , implWidget;
  $TextBox(this$static);
  this$static.id_0 = id;
  $setElement(this$static, element);
  subElement = createDiv();
  setElementProperty(subElement, 'id', id);
  appendChild(element, subElement);
  $loadDojoWidget(getInstance(), this$static);
  return this$static;
}

function $DropdownContainer_0(this$static, id, startDate, endDate){
  $clinit_100() , implWidget;
  $DropdownContainer(this$static, createDiv(), id, startDate, endDate);
  return this$static;
}

function $addChangeListener_0(this$static, listener){
  if (this$static.changeListeners === null) {
    this$static.changeListeners = $ChangeListenerCollection(new ChangeListenerCollection());
  }
  $add_13(this$static.changeListeners, listener);
}

function $setBrowserEventCallback(this$static, dojoWidget){
  dojoWidget.textbox.onbrowserevent = function(e){
    dojoWidget.gwtWidget.onBrowserEvent(e);
  }
  ;
}

function $setCallBackForDropDown(this$static, dojoWidget){
  dojoWidget.onChange = function(date){
    if (date instanceof Date) {
      dojoWidget.gwtWidget.onValueChanged_0(date);
    }
     else {
      if (dojoWidget.getValue() != null) {
        dojoWidget.gwtWidget.onValueChanged_0(dojoWidget.getValue());
      }
    }
  }
  ;
}

function $setDate_0(this$static, date){
  if (this$static.date === null && date !== null || this$static.date !== null && date === null || this$static.date !== null && !$equals_3(this$static.date, date)) {
    this$static.date = date;
    if (this$static.isAttached()) {
      $setDateOnContainer(this$static, getJSDate_0(date));
    }
    if (this$static.changeListeners !== null) {
      $fireChange(this$static.changeListeners, this$static);
    }
  }
}

function $setDateOnContainer(this$static, date){
  $setDojoDate_0(this$static, this$static.dojoWidget, date);
}

function $setDojoDate_0(this$static, dojoWidget, date){
  dojoWidget.setValue(date);
}

function $setDojoEnabled(this$static, dojoWidget, enabled){
  dojoWidget.setDisabled(!enabled);
}

function $setDojoText(this$static, dojoWidget, text){
  dojoWidget.setDisplayedValue(text);
  dojoWidget._onBlur();
}

function $setEnabled_0(this$static, enabled){
  $setEnabled(this$static, enabled);
  if (this$static.isAttached()) {
    $setDojoEnabled(this$static, this$static.dojoWidget, this$static.isEnabled());
  }
}

function $setInternDate(this$static, date){
  if (this$static.date === null && date !== null || this$static.date !== null && date === null || this$static.date !== null && !$equals_3(this$static.date, date)) {
    this$static.date = date;
    if (this$static.changeListeners !== null) {
      $fireChange(this$static.changeListeners, this$static);
    }
  }
}

function $setInvalidMessage(this$static, msg){
  this$static.invalidMessage_0 = msg;
  if (this$static.isAttached() && this$static.invalidMessage_0 !== null) {
    $setMessage(this$static, this$static.invalidMessage_0, this$static.dojoWidget);
  }
}

function $setMessage(this$static, msg, dojoWidget){
  dojoWidget.invalidMessage = msg;
}

function $setPromptMessage(this$static, msg){
  this$static.promptMessage_0 = msg;
  if (this$static.isAttached() && this$static.promptMessage_0 !== null) {
    $setPromptMessage_0(this$static, msg, this$static.dojoWidget);
  }
}

function $setPromptMessage_0(this$static, msg, dojoWidget){
  dojoWidget.promptMessage = msg;
}

function $setText_1(this$static, text){
  this$static.text = text;
  if (this$static.isAttached()) {
    $setDojoText(this$static, this$static.dojoWidget, text);
  }
}

function doAfterCreation_2(){
  startup_0(this);
  $setBrowserEventCallback(this, this.dojoWidget);
  $setCallBackForDropDown(this, this.dojoWidget);
  if (this.date !== null) {
    $setDateOnContainer(this, getJSDate_0(this.date));
  }
  $setEnabled_0(this, this.isEnabled());
  $setInvalidMessage(this, this.invalidMessage_0);
  $setPromptMessage(this, this.promptMessage_0);
  $setText_1(this, this.text);
}

function doBeforeDestruction_1(){
}

function free_1(){
  this.dojoWidget = null;
}

function getDojoWidget_1(){
  return this.dojoWidget;
}

function onAttach_5(){
  $onAttach(this);
  $constructDojoWidget(getInstance(), this, this);
}

function onBrowserEvent_6(e){
  $onBrowserEvent_0(this, e);
}

function onDetach_5(){
  $destroyDojoWidget(getInstance(), this, this);
  $onDetach(this);
}

function onDojoLoad_1(){
}

function setDate(date){
  $setDate_0(this, date);
}

function sinkEvents_1(eventBitsToAdd){
}

function DropdownContainer(){
}

_ = DropdownContainer.prototype = new TextBox();
_.doAfterCreation = doAfterCreation_2;
_.doBeforeDestruction = doBeforeDestruction_1;
_.free = free_1;
_.getDojoWidget = getDojoWidget_1;
_.onAttach = onAttach_5;
_.onBrowserEvent = onBrowserEvent_6;
_.onDetach = onDetach_5;
_.onDojoLoad = onDojoLoad_1;
_.setDate = setDate;
_.sinkEvents = sinkEvents_1;
_.typeName$ = package_com_objetdirect_tatami_client_ + 'DropdownContainer';
_.typeId$ = 89;
_.changeListeners = null;
_.date = null;
_.dojoWidget = null;
_.id_0 = null;
_.invalidMessage_0 = null;
_.promptMessage_0 = null;
_.text = '';
function $clinit_116(){
  $clinit_116 = nullMethod;
  $clinit_100() , implWidget;
}

function $DropdownDatePicker(this$static, id){
  $clinit_100() , implWidget;
  $DropdownDatePicker_0(this$static, id, null, null);
  return this$static;
}

function $DropdownDatePicker_0(this$static, id, startDate, endDate){
  $clinit_100() , implWidget;
  $DropdownContainer_0(this$static, id, startDate, endDate);
  return this$static;
}

function $createDateTextBox(this$static, id, startDate, endDate){
  if (startDate == null)
    startDate = '1492-10-12';
  if (endDate == null)
    endDate = '2492-10-12';
  var dateTextBox = null;
  try {
    dateTextBox = new ($wnd.dijit.form.DateTextBox)({'constraints':{'min':startDate, 'max':endDate}}, $wnd.dojo.byId(id));
  }
   catch (e) {
  }
  return dateTextBox;
}

function createDojoWidget_2(){
  this.dojoWidget = $createDateTextBox(this, this.id_0, null, null);
}

function getDojoName_2(){
  return 'dijit.form.DateTextBox';
}

function onValueChanged_1(date){
  var theDate;
  theDate = getJavaDate(date);
  this.setDate(theDate);
}

function DropdownDatePicker(){
}

_ = DropdownDatePicker.prototype = new DropdownContainer();
_.createDojoWidget = createDojoWidget_2;
_.getDojoName = getDojoName_2;
_.onValueChanged_0 = onValueChanged_1;
_.typeName$ = package_com_objetdirect_tatami_client_ + 'DropdownDatePicker';
_.typeId$ = 90;
function $clinit_117(){
  $clinit_117 = nullMethod;
  $clinit_100() , implWidget;
}

function $DropdownTimePicker(this$static, id){
  $clinit_100() , implWidget;
  $DropdownTimePicker_0(this$static, id, 'HH:mm');
  return this$static;
}

function $DropdownTimePicker_0(this$static, id, timePattern){
  $clinit_100() , implWidget;
  $DropdownTimePicker_1(this$static, id, null, null, timePattern);
  return this$static;
}

function $DropdownTimePicker_1(this$static, id, startDate, endDate, timePattern){
  $clinit_100() , implWidget;
  $DropdownContainer_0(this$static, id, startDate, endDate);
  this$static.timePattern = timePattern;
  return this$static;
}

function $adjust(this$static, date){
  return $Date_0(new Date_0(), $getTime(date) - $getTime(date) % 60000);
}

function $createTimeTextBox(this$static, id, timePattern, startDate, endDate){
  if (startDate == null)
    startDate = '1492-10-12';
  if (endDate == null)
    endDate = '2492-10-12';
  var widget = new ($wnd.dijit.form.TimeTextBox)({'constraints':{'timePattern':timePattern, 'min':startDate, 'max':endDate}}, $wnd.dojo.byId(id));
  return widget;
}

function createDojoWidget_3(){
  this.dojoWidget = $createTimeTextBox(this, this.id_0, this.timePattern, null, null);
}

function getDojoName_3(){
  return 'dijit.form.TimeTextBox';
}

function onValueChanged_2(date){
  var javaDate;
  javaDate = $adjust(this, getJavaDate(date));
  $setInternDate(this, javaDate);
}

function setDate_0(date){
  $setDate_0(this, $adjust(this, date));
}

function DropdownTimePicker(){
}

_ = DropdownTimePicker.prototype = new DropdownContainer();
_.createDojoWidget = createDojoWidget_3;
_.getDojoName = getDojoName_3;
_.onValueChanged_0 = onValueChanged_2;
_.setDate = setDate_0;
_.typeName$ = package_com_objetdirect_tatami_client_ + 'DropdownTimePicker';
_.typeId$ = 91;
_.timePattern = 'HH:mm';
function $$init_14(this$static){
  this$static.items = $ArrayList(new ArrayList());
}

function $FishEye(this$static, itemWidth, itemHeight, itemMaxWidth, itemMaxHeight, orientation, effectUnits, itemPadding, attachEdge, labelEdge, conservativeTrigger){
  $AbstractDojo(this$static);
  $$init_14(this$static);
  this$static.itemWidth = itemWidth;
  this$static.itemHeight = itemHeight;
  this$static.itemMaxWidth = itemMaxWidth;
  this$static.itemMaxHeight = itemMaxHeight;
  this$static.orientation = orientation;
  this$static.effectUnits = effectUnits;
  this$static.itemPadding = itemPadding;
  this$static.attachEdge = attachEdge;
  this$static.labelEdge = labelEdge;
  this$static.conservativeTrigger = conservativeTrigger;
  sinkEvents(this$static.getElement(), 16);
  setEventListener(this$static.getElement(), this$static);
  return this$static;
}

function $FishEye_0(this$static, orientation){
  $FishEye(this$static, 50, 50, 200, 200, orientation, 2, 10, 'center', 'bottom', false);
  return this$static;
}

function $add_9(this$static, icon, caption, command){
  var item;
  item = $FishEye$Item(new FishEye$Item(), icon, caption, command, this$static);
  $add_13(this$static.items, item);
  if (this$static.isAttached()) {
    $buildItem(this$static, item);
  }
}

function $addChildWidget(this$static, dojoWidget, child){
  dojoWidget.addChild(child);
  dojoWidget.startup();
  child.startup();
}

function $buildItem(this$static, item){
  var child;
  child = $createFishEyeItem(this$static, this$static.dojoWidget, item.icon, item.caption);
  $addChildWidget(this$static, this$static.dojoWidget, child);
  item.child = child;
}

function $buildItems(this$static){
  var i, item;
  for (i = 0; i < this$static.items.size; i++) {
    item = dynamicCast($get_0(this$static.items, i), 33);
    $buildItem(this$static, item);
  }
}

function $createFishEye(this$static, itemWidth, itemHeight, itemMaxWidth, itemMaxHeight, orientation, effectUnits, itemPadding, attachEdge, labelEdge, enableCrappySvgSupport, conservativeTrigger){
  var fisheye = new ($wnd.dojox.widget.FisheyeList)({'itemWidth':itemWidth, 'itemHeight':itemHeight, 'itemMaxWidth':itemMaxWidth, 'itemMaxHeight':itemMaxHeight, 'orientation':orientation, 'effectUnits':effectUnits, 'itemPadding':itemPadding, 'attachEdge':attachEdge, 'labelEdge':labelEdge, 'isFixed':true, 'enableCrappySvgSupport':enableCrappySvgSupport, 'conservativeTrigger':conservativeTrigger});
  return fisheye;
}

function $createFishEyeItem(this$static, dojoWidget, icon, caption){
  var child = new ($wnd.dojox.widget.TatamiFisheyeItem)({'parent':dojoWidget, 'iconSrc':icon, 'label':caption});
  return child;
}

function $defineTatamiItem(this$static){
  $wnd.dojo.declare('dojox.widget.TatamiFisheyeItem', $wnd.dojox.widget.FisheyeListItem, {'onClick':function(e){
    this.parent.gwtWidget.dispatchClick(this.iconSrc);
  }
  });
}

function $removeChildWidget(this$static, dojoWidget, child){
  try {
    dojoWidget.removeChild(child);
    dojoWidget._initializePositioning();
  }
   catch (e) {
  }
}

function $removeItem(this$static, item){
  $removeChildWidget(this$static, this$static.dojoWidget, item.child);
  $destroy(getInstance(), item.child);
  item.child = null;
}

function $removeItems(this$static){
  var i, item;
  for (i = 0; i < this$static.items.size; i++) {
    item = dynamicCast($get_0(this$static.items, i), 33);
    $removeItem(this$static, item);
  }
}

function $resetLocation(this$static, dojoWidget){
  dojoWidget._initializePositioning();
}

function createDojoWidget_4(){
  this.dojoWidget = $createFishEye(this, this.itemWidth, this.itemHeight, this.itemMaxWidth, this.itemMaxHeight, this.orientation, this.effectUnits, this.itemPadding, this.attachEdge, this.labelEdge, false, this.conservativeTrigger);
}

function dispatchClick(icon){
  var i, item;
  for (i = 0; i < this.items.size; i++) {
    item = dynamicCast($get_0(this.items, i), 33);
    if ($equals_1(item.icon, icon)) {
      if (item.command !== null) {
        $execute_0(item.command);
      }
    }
  }
}

function doAfterCreation_3(){
  $buildItems(this);
}

function doBeforeDestruction_2(){
  $removeItems(this);
}

function getDojoName_4(){
  return 'dojox.widget.FisheyeList';
}

function onBrowserEvent_7(event_0){
  if (eventGetType(event_0) == 16) {
    if ($getAbsoluteLeft_0(this) != this.x_0 || $getAbsoluteTop_0(this) != this.y_0) {
      this.x_0 = $getAbsoluteLeft_0(this);
      this.y_0 = $getAbsoluteTop_0(this);
      $resetLocation(this, this.dojoWidget);
    }
  }
}

function onDojoLoad_2(){
  $defineTatamiItem(this);
}

function FishEye(){
}

_ = FishEye.prototype = new AbstractDojo();
_.createDojoWidget = createDojoWidget_4;
_.dispatchClick = dispatchClick;
_.doAfterCreation = doAfterCreation_3;
_.doBeforeDestruction = doBeforeDestruction_2;
_.getDojoName = getDojoName_4;
_.onBrowserEvent = onBrowserEvent_7;
_.onDojoLoad = onDojoLoad_2;
_.typeName$ = package_com_objetdirect_tatami_client_ + 'FishEye';
_.typeId$ = 92;
_.attachEdge = 'center';
_.conservativeTrigger = true;
_.effectUnits = 2;
_.itemHeight = 50;
_.itemMaxHeight = 200;
_.itemMaxWidth = 200;
_.itemPadding = 10;
_.itemWidth = 50;
_.labelEdge = 'bottom';
_.orientation = 'horizontal';
_.x_0 = 0;
_.y_0 = 0;
function $FishEye$Item(this$static, icon, caption, command, this$0){
  this$static.icon = icon;
  this$static.caption = caption;
  this$static.command = command;
  return this$static;
}

function FishEye$Item(){
}

_ = FishEye$Item.prototype = new Object_0();
_.typeName$ = package_com_objetdirect_tatami_client_ + 'FishEye$Item';
_.typeId$ = 93;
_.caption = null;
_.child = null;
_.command = null;
_.icon = null;
function $RuleMark(this$static, type, count, size, position){
  var el;
  $AbstractDojo(this$static);
  if (!$equals_1('horizontal', type)) {
    this$static.type_0 = 'vertical';
  }
  el = this$static.getElement();
  this$static.count = count;
  this$static.size = size;
  this$static.position = position;
  return this$static;
}

function $createHorizontalRule(this$static, count, ruleStyle, position){
  return new ($wnd.dijit.form.HorizontalRule)({'count':count, 'ruleStyle':ruleStyle, 'container':position});
}

function $createVerticalRule(this$static, count, ruleStyle, position){
  return new ($wnd.dijit.form.VerticalRule)({'count':count, 'ruleStyle':ruleStyle, 'container':position});
}

function createDojoWidget_6(){
  var style;
  if ($equals_1('vertical', this.type_0)) {
    style = 'width:' + this.size;
    this.dojoWidget = $createVerticalRule(this, this.count, style, this.position);
  }
   else {
    style = 'height:' + this.size;
    this.dojoWidget = $createHorizontalRule(this, this.count, style, this.position);
  }
}

function getDojoName_5(){
  return 'dijit.form.Slider';
}

function RuleMark(){
}

_ = RuleMark.prototype = new AbstractDojo();
_.createDojoWidget = createDojoWidget_6;
_.getDojoName = getDojoName_5;
_.typeName$ = package_com_objetdirect_tatami_client_ + 'RuleMark';
_.typeId$ = 94;
_.count = 3;
_.position = 'containerNode';
_.size = '5px';
_.type_0 = 'horizontal';
function $$init_15(this$static){
  this$static.labels = initDims_0('[Ljava.lang.String;', [165], [1], [0], null);
}

function $RuleLabels(this$static, type, labels, style, position){
  $RuleMark(this$static, type, labels.length_0, '0px', position);
  $$init_15(this$static);
  this$static.labels = labels;
  this$static.style_0 = style;
  return this$static;
}

function $createHorizontalLabels(this$static, labels, style, position){
  return new ($wnd.dijit.form.HorizontalRuleLabels)({'labels':labels, 'labelStyle':style, 'container':position}, $wnd.dojo.doc.createElement('div'));
}

function $createVerticalLabels(this$static, labels, style, position){
  return new ($wnd.dijit.form.VerticalRuleLabels)({'labels':labels, 'labelStyle':style, 'container':position}, $wnd.dojo.doc.createElement('div'));
}

function createDojoWidget_5(){
  if ($equals_1('vertical', this.type_0)) {
    this.dojoWidget = $createVerticalLabels(this, createArray_0(this.labels), this.style_0, this.position);
  }
   else {
    this.dojoWidget = $createHorizontalLabels(this, createArray_0(this.labels), this.style_0, this.position);
  }
}

function RuleLabels(){
}

_ = RuleLabels.prototype = new RuleMark();
_.createDojoWidget = createDojoWidget_5;
_.typeName$ = package_com_objetdirect_tatami_client_ + 'RuleLabels';
_.typeId$ = 95;
_.style_0 = '';
function $clinit_123(){
  $clinit_123 = nullMethod;
  $clinit_100() , implWidget;
}

function $Slider(this$static, type, minimum, maximum, initialValue, showButtons){
  $clinit_100() , implWidget;
  $AbstractDojoFocus(this$static);
  this$static.maximum = maximum;
  this$static.minimum = minimum;
  this$static.value = initialValue;
  this$static.showButtons = showButtons;
  $setType(this$static, type);
  $setElement(this$static, this$static.getElement());
  return this$static;
}

function $addChild(this$static, dojoWidget, child){
  dojoWidget.addChild(child);
  dojoWidget.startup();
}

function $applySize(this$static){
  var element;
  if (this$static.isAttached()) {
    element = $getDomNode(getInstance(), this$static);
    setStyleAttribute(element, 'width', this$static.width_0 + 'px');
    setStyleAttribute(element, 'height', this$static.height_0 + 'px');
  }
}

function $buildRuleMark(this$static, rule){
  var $e0;
  if (rule !== null) {
    try {
      rule.createDojoWidget();
      $addChild(this$static, this$static.dojoWidget, rule.dojoWidget);
    }
     catch ($e0) {
      $e0 = caught($e0);
      if (instanceOf($e0, 28)) {
      }
       else 
        throw $e0;
    }
  }
}

function $changeValue(this$static, value){
  if (this$static.value != value) {
    this$static.value = value;
    if (this$static.changeListeners !== null) {
      $fireChange(this$static.changeListeners, this$static);
    }
  }
}

function $createVerticalSlider(this$static, minimum, maximum, initialValue, showButtons){
  var widget = new ($wnd.dijit.form.VerticalSlider)({'minimum':minimum, 'maximum':maximum, 'showButtons':showButtons, 'value':initialValue, 'style':'height:200px;', 'discreteValues':maximum, 'intermediateChanges':true});
  return widget;
}

function $doSetValue(this$static, dojoWidget, value){
  dojoWidget.setValue(value);
}

function $removeChild_0(this$static, dojoWidget, child){
  dojoWidget.removeChild(child);
}

function $removeDojoRule(this$static, rule){
  if (rule !== null) {
    $removeChild_0(this$static, this$static.dojoWidget, rule.dojoWidget);
    $destroy(getInstance(), rule.dojoWidget);
  }
}

function $removeLabelTop(this$static){
  $removeDojoRule(this$static, this$static.labelTop);
  this$static.labelTop = null;
}

function $removeRuleAndLabel(this$static){
  $removeDojoRule(this$static, this$static.sliderRuleBottom);
  $removeDojoRule(this$static, this$static.sliderRuleTop);
  $removeDojoRule(this$static, this$static.labelTop);
  $removeDojoRule(this$static, this$static.labelBottom);
}

function $removeRuleBottom(this$static){
  $removeDojoRule(this$static, this$static.sliderRuleBottom);
  this$static.sliderRuleBottom = null;
}

function $removeRuleTop(this$static){
  $removeDojoRule(this$static, this$static.sliderRuleTop);
  this$static.sliderRuleTop = null;
}

function $setEnabled_2(this$static, enabled){
  $setEnabled(this$static, enabled);
  if (this$static.isAttached()) {
    $setEnabled_1(this$static, this$static.dojoWidget, enabled);
  }
}

function $setEnabled_1(this$static, dojoWidget, enabled){
  dojoWidget.setDisabled(!enabled);
}

function $setEventCallback_0(this$static, dojoWidget){
  dojoWidget.onChange = function(val){
    dojoWidget.gwtWidget.onValueChanged(val);
  }
  ;
}

function $setLabelsLeft(this$static, labels, style){
  $setLabelsTop(this$static, labels, style);
}

function $setLabelsTop(this$static, labels, style){
  if (this$static.labelTop !== null) {
    $removeLabelTop(this$static);
  }
  if ($equals_1('vertical', this$static.type_0)) {
    this$static.labelTop = $RuleLabels(new RuleLabels(), 'vertical', labels, style, 'leftDecoration');
  }
   else {
    this$static.labelTop = $RuleLabels(new RuleLabels(), 'horizontal', labels, style, 'topDecoration');
  }
  if (this$static.isAttached()) {
    $buildRuleMark(this$static, this$static.labelTop);
  }
}

function $setRuleBottom(this$static, count, size){
  if (this$static.sliderRuleBottom !== null) {
    $removeRuleBottom(this$static);
  }
  if ($equals_1('vertical', this$static.type_0)) {
    this$static.sliderRuleBottom = $RuleMark(new RuleMark(), 'vertical', count, size, 'rightDecoration');
  }
   else {
    this$static.sliderRuleBottom = $RuleMark(new RuleMark(), 'horizontal', count, size, 'bottomDecoration');
  }
  if (this$static.isAttached()) {
    $buildRuleMark(this$static, this$static.sliderRuleBottom);
  }
}

function $setRuleLeft(this$static, count, size){
  $setRuleTop(this$static, count, size);
}

function $setRuleRight(this$static, count, size){
  $setRuleBottom(this$static, count, size);
}

function $setRuleTop(this$static, count, ruleStyle){
  if (this$static.sliderRuleTop !== null) {
    $removeRuleTop(this$static);
  }
  if ($equals_1('vertical', this$static.type_0)) {
    this$static.sliderRuleTop = $RuleMark(new RuleMark(), 'vertical', count, ruleStyle, 'leftDecoration');
  }
   else {
    this$static.sliderRuleTop = $RuleMark(new RuleMark(), 'horizontal', count, ruleStyle, 'topDecoration');
  }
  if (this$static.isAttached()) {
    $buildRuleMark(this$static, this$static.sliderRuleTop);
  }
}

function $setSize_0(this$static, width, height){
  this$static.width_0 = width;
  this$static.height_0 = height;
  $applySize(this$static);
}

function $setType(this$static, type){
  if ($equals_1('horizontal', type) || $equals_1('vertical', type)) {
    this$static.type_0 = type;
  }
   else {
    throw $IllegalArgumentException(new IllegalArgumentException(), 'type : ' + type);
  }
}

function $setValue(this$static, value){
  var $e0;
  $changeValue(this$static, value);
  if (this$static.isAttached()) {
    try {
      $doSetValue(this$static, this$static.dojoWidget, this$static.value);
    }
     catch ($e0) {
      $e0 = caught($e0);
      if (instanceOf($e0, 28)) {
      }
       else 
        throw $e0;
    }
  }
}

function createDojoWidget_7(){
  if ($equals_1('horizontal', this.type_0)) {
    this.dojoWidget = createHorizontalSlider(this.minimum, this.maximum, this.value, this.showButtons);
  }
   else {
    this.dojoWidget = $createVerticalSlider(this, this.minimum, this.maximum, this.value, this.showButtons);
    $setSize_0(this, 10, 200);
  }
}

function createHorizontalSlider(minimum, maximum, initialValue, showButtons){
  $clinit_123();
  var widget = new ($wnd.dijit.form.HorizontalSlider)({'minimum':minimum, 'maximum':maximum, 'showButtons':showButtons, 'value':initialValue, 'discreteValues':maximum, 'intermediateChanges':true});
  return widget;
}

function doAfterCreation_4(){
  var $e0;
  try {
    $doSetValue(this, this.dojoWidget, this.value);
  }
   catch ($e0) {
    $e0 = caught($e0);
    if (instanceOf($e0, 28)) {
    }
     else 
      throw $e0;
  }
}

function doBeforeDestruction_3(){
  $removeRuleAndLabel(this);
}

function getDojoName_6(){
  return 'dijit.form.Slider';
}

function onLoad_4(){
  $setEventCallback_0(this, this.dojoWidget);
  $setEnabled_2(this, this.isEnabled());
  $setValue(this, this.value);
  $applySize(this);
  $buildRuleMark(this, this.sliderRuleTop);
  $buildRuleMark(this, this.sliderRuleBottom);
  $buildRuleMark(this, this.labelTop);
  $buildRuleMark(this, this.labelBottom);
}

function onValueChanged_3(value){
  $changeValue(this, round_int(value));
}

function Slider(){
}

_ = Slider.prototype = new AbstractDojoFocus();
_.createDojoWidget = createDojoWidget_7;
_.doAfterCreation = doAfterCreation_4;
_.doBeforeDestruction = doBeforeDestruction_3;
_.getDojoName = getDojoName_6;
_.onLoad = onLoad_4;
_.onValueChanged = onValueChanged_3;
_.typeName$ = package_com_objetdirect_tatami_client_ + 'Slider';
_.typeId$ = 96;
_.height_0 = 10;
_.labelBottom = null;
_.labelTop = null;
_.maximum = 100;
_.minimum = 0;
_.showButtons = true;
_.sliderRuleBottom = null;
_.sliderRuleTop = null;
_.type_0 = 'horizontal';
_.value = 0;
_.width_0 = 200;
function $clinit_125(){
  $clinit_125 = nullMethod;
  $clinit_100() , implWidget;
}

function $TimePicker_0(this$static, startDate, endDate, _constraints){
  $clinit_100() , implWidget;
  $BasePicker(this$static, startDate, endDate);
  this$static.constraints = _constraints;
  return this$static;
}

function $TimePicker(this$static, constraints){
  $clinit_100() , implWidget;
  $TimePicker_0(this$static, null, null, constraints);
  return this$static;
}

function $createDojoTimePicker(this$static, startDate, endDate, timePattern, clickableIncrement, visibleIncrement, visibleRange){
  if (startDate == null)
    startDate = '1492-10-12';
  if (endDate == null)
    endDate = '2492-10-12';
  var widget = new ($wnd.dijit._TimePicker)({'clickableIncrement':clickableIncrement, 'visibleIncrement':visibleIncrement, 'visibleRange':visibleRange, 'constraints':{'min':startDate, 'max':endDate, 'timePattern':timePattern}});
  return widget;
}

function adjust_0(date){
  return $Date_0(new Date_0(), $getTime(date) - $getTime(date) % 60000);
}

function createDojoWidget_8(){
  this.dojoWidget = $createDojoTimePicker(this, null, null, this.constraints.timePattern, this.constraints.clickableIncrement, this.constraints.visibleIncrement, this.constraints.visibleRange);
}

function getDojoName_7(){
  return 'dijit._TimePicker';
}

function setEventCallback_0(dojoWidget){
  dojoWidget.onValueSelected = function(date){
    dojoWidget.gwtWidget.onValueChanged_0(date);
  }
  ;
}

function TimePicker(){
}

_ = TimePicker.prototype = new BasePicker();
_.adjust = adjust_0;
_.createDojoWidget = createDojoWidget_8;
_.getDojoName = getDojoName_7;
_.setEventCallback = setEventCallback_0;
_.typeName$ = package_com_objetdirect_tatami_client_ + 'TimePicker';
_.typeId$ = 97;
_.constraints = null;
function TimePickerConstraints(){
}

_ = TimePickerConstraints.prototype = new Object_0();
_.typeName$ = package_com_objetdirect_tatami_client_ + 'TimePickerConstraints';
_.typeId$ = 98;
_.clickableIncrement = 'T00:15:00';
_.timePattern = 'HH:mm';
_.visibleIncrement = 'T01:00:00';
_.visibleRange = 'T05:00:00';
function $Toaster(this$static, messageTopic, position){
  $AbstractDojo(this$static);
  this$static.messageTopic = messageTopic;
  this$static.position = position;
  return this$static;
}

function $createToaster(this$static, messageTopic, position){
  var widget = new ($wnd.dojox.widget.Toaster)({'messageTopic':messageTopic, 'positionDirection':position});
  return widget;
}

function createDojoWidget_9(){
  this.dojoWidget = $createToaster(this, this.messageTopic, this.position);
}

function dojoPublishMessage(topic, message, type, delay){
  $wnd.dojo.publish(topic, [{'message':message, 'type':type, 'duration':delay}]);
}

function getDojoName_8(){
  return 'dojox.widget.Toaster';
}

function publishMessage(topic, message){
  dojoPublishMessage(topic, message, 'message', 1000);
}

function Toaster(){
}

_ = Toaster.prototype = new AbstractDojo();
_.createDojoWidget = createDojoWidget_9;
_.getDojoName = getDojoName_8;
_.typeName$ = package_com_objetdirect_tatami_client_ + 'Toaster';
_.typeId$ = 99;
_.messageTopic = null;
_.position = null;
function $$init_17(this$static){
  this$static.fillColor = ($clinit_128() , BLACK);
  this$static.strokeColor = ($clinit_128() , BLACK);
}

function $GraphicObject(this$static){
  $$init_17(this$static);
  this$static.position = $Point(new Point());
  this$static.center = $Point(new Point());
  this$static.bounds = $Rectangle(new Rectangle());
  return this$static;
}

function $applyModification(this$static, newMatrix){
  if (this$static.matrix_0 === null) {
    this$static.matrix_0 = newMatrix;
  }
   else {
    this$static.matrix_0 = multiplyMatrix(newMatrix, this$static.matrix_0);
  }
  if (this$static.shape !== null) {
    applyTransform(this$static.shape, newMatrix);
  }
}

function $applyPattern(this$static, pattern){
  if (pattern !== null && this$static.shape !== null) {
    configureFill(this$static.shape, $getGFXPattern(pattern));
  }
  return this$static;
}

function $configureFill(this$static){
  configureFill(this$static.shape, $getDojoColor(this$static.fillColor));
}

function $configureShape(this$static){
  var dx, dy, jsRect;
  jsRect = $getBounds(this$static, this$static.shape);
  if (jsRect !== null) {
    dx = this$static.bounds.point.x_0;
    dy = this$static.bounds.point.y_0;
    $setRect_0(this$static.bounds, jsRect);
    $translate_1(this$static.bounds, dx, dy);
  }
  $initCenterLocation(this$static);
  $configureFill(this$static);
  $configureStroke(this$static);
  $configureTransform(this$static);
}

function $configureStroke(this$static){
  configureStroke(this$static.shape, $getDojoColor(this$static.strokeColor), this$static.strokeWidth, this$static.strokeStyle);
}

function $configureTransform(this$static){
  if (this$static.matrix_0 !== null) {
    applyTransform(this$static.shape, this$static.matrix_0);
    $setBounds(this$static);
  }
}

function $deleteGfx(this$static, shape){
  shape.removeShape(true);
}

function $getBounds(this$static, shape){
  return shape.getBoundingBox();
}

function $getTransformedBound(this$static, shape){
  return shape.getTransformedBoundingBox();
}

function $hide_1(this$static){
  $deleteGfx(this$static, this$static.shape);
  this$static.shape = null;
}

function $initCenterLocation(this$static){
  this$static.center = $getCenter(this$static.getBounds());
}

function $moveToBack(this$static){
  if (this$static.shape !== null) {
    this$static.shape = $moveToBack_0(this$static, this$static.shape);
  }
}

function $moveToBack_0(this$static, shape){
  return shape.moveToBack();
}

function $moveToFront(this$static){
  if (this$static.shape !== null) {
    this$static.shape = $moveToFront_0(this$static, this$static.shape);
  }
}

function $moveToFront_0(this$static, shape){
  return shape.moveToFront();
}

function $rotate(this$static, angle){
  return $rotate_0(this$static, angle, this$static.center);
}

function $rotate_0(this$static, angle, center){
  var matrixRotated;
  if (angle != 0) {
    matrixRotated = getRotationMatrix(angle, center.x_0, center.y_0);
    $rotate_1(this$static.position, angle, center);
    $rotate_2(this$static.bounds, angle);
    $applyModification(this$static, matrixRotated);
    $setBounds(this$static);
  }
  return this$static;
}

function $scale_0(this$static, factorX, factorY){
  var matrixScaled, newHeight, newWidth, newX, newY;
  if (factorX != 1.0 || factorY != 1.0) {
    matrixScaled = getScalingMatrix(factorX, factorY, this$static.position.x_0, this$static.position.y_0);
    if (!$equals_0(this$static.position, this$static.center)) {
      $setRect(this$static.bounds, this$static.bounds.point.x_0, this$static.bounds.point.y_0, this$static.bounds.width_0 * factorX, this$static.bounds.height_0 * factorY);
      $setLocation_0(this$static.center, $getCenter(this$static.bounds));
    }
     else {
      newWidth = this$static.bounds.width_0 * factorX;
      newHeight = this$static.bounds.height_0 * factorY;
      newX = this$static.center.x_0 - newWidth / 2;
      newY = this$static.center.y_0 - newHeight / 2;
      $setRect(this$static.bounds, newX, newY, newWidth, newHeight);
    }
    $applyModification(this$static, matrixScaled);
    $setBounds(this$static);
  }
  return this$static;
}

function $scale(this$static, factor){
  return $scale_0(this$static, factor, factor);
}

function $setBounds(this$static){
  var points;
  if (this$static.shape !== null) {
    points = $getTransformedBound(this$static, this$static.shape);
    if (points !== null) {
      $setRectFromPoints(this$static.bounds, points);
    }
  }
}

function $setFillColor(this$static, color){
  this$static.fillColor = color;
  if (this$static.shape !== null) {
    $configureFill(this$static);
  }
  return this$static;
}

function $setGroup(this$static, group){
  this$static.groupParent = group;
}

function $setStroke(this$static, color, width){
  this$static.strokeColor = color;
  this$static.strokeWidth = width;
  if (this$static.shape !== null) {
    $configureStroke(this$static);
  }
  return this$static;
}

function $setStrokeColor(this$static, color){
  return this$static.setStroke_0(color, this$static.strokeWidth);
}

function $setStrokeStyle(this$static, style){
  this$static.strokeStyle = style;
  if (this$static.shape !== null) {
    $configureStroke(this$static);
  }
}

function $setStrokeWidth(this$static, width){
  return this$static.setStroke_0(this$static.strokeColor, width);
}

function $show_0(this$static, surface){
  this$static.parent_0 = surface;
  this$static.shape = this$static.createGfx(surface.surface);
  $configureShape(this$static);
}

function $translate(this$static, xLag, yLag){
  var matrixTranslated;
  if (xLag != 0 || yLag != 0) {
    matrixTranslated = getTranslationMatrix(xLag, yLag);
    $translate_0(this$static.position, xLag, yLag);
    $translate_0(this$static.center, xLag, yLag);
    $translate_1(this$static.bounds, xLag, yLag);
    $applyModification(this$static, matrixTranslated);
    $setBounds(this$static);
  }
  return this$static;
}

function applyPattern(pattern){
  return $applyPattern(this, pattern);
}

function applyTransform(shape, matrix){
  shape.applyLeftTransform(matrix);
}

function configureFill(shape, fillColor){
  shape.setFill(fillColor);
}

function configureStroke(shape, strokeColor, strokeWidth, style){
  shape.setStroke({'color':strokeColor, 'width':strokeWidth, 'style':style});
}

function getBounds(){
  return this.bounds;
}

function getRotationMatrix(angle, centerX, centerY){
  return $wnd.dojox.gfx.matrix.rotategAt(angle, centerX, centerY);
}

function getScalingMatrix(factorX, factorY, centerX, centerY){
  return $wnd.dojox.gfx.matrix.scaleAt(factorX, factorY, centerX, centerY);
}

function getShapes(){
  var list;
  list = $ArrayList(new ArrayList());
  $add_13(list, wrapJSO(this.shape, JavaScriptObject));
  return list;
}

function getTranslationMatrix(xLag, yLag){
  return $wnd.dojox.gfx.matrix.translate(xLag, yLag);
}

function multiplyMatrix(mx1, mx2){
  return $wnd.dojox.gfx.matrix.multiply(mx1, mx2);
}

function setFillColor(color){
  return $setFillColor(this, color);
}

function setStroke(color, width){
  return $setStroke(this, color, width);
}

function show(surface){
  $show_0(this, surface);
}

function GraphicObject(){
}

_ = GraphicObject.prototype = new Object_0();
_.applyPattern = applyPattern;
_.getBounds = getBounds;
_.getShapes = getShapes;
_.setFillColor = setFillColor;
_.setStroke_0 = setStroke;
_.show = show;
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'GraphicObject';
_.typeId$ = 100;
_.bounds = null;
_.center = null;
_.groupParent = null;
_.matrix_0 = null;
_.parent_0 = null;
_.position = null;
_.shape = null;
_.strokeStyle = 'Solid';
_.strokeWidth = 1;
function $Circle(this$static, radius){
  $GraphicObject(this$static);
  $setRadius(this$static, radius);
  return this$static;
}

function $createGfx(this$static, surface, radius){
  return surface.createCircle({'cx':0, 'cy':0, 'r':radius});
}

function $setRadius(this$static, radius){
  this$static.radius = radius;
}

function createGfx(surface){
  return $createGfx(this, surface, this.radius);
}

function Circle(){
}

_ = Circle.prototype = new GraphicObject();
_.createGfx = createGfx;
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'Circle';
_.typeId$ = 101;
_.radius = 0;
function $clinit_128(){
  $clinit_128 = nullMethod;
  BLACK = $Color(new Color(), 0, 0, 0, 255);
  SILVER = $Color(new Color(), 192, 192, 192, 255);
  $Color(new Color(), 128, 128, 128, 255);
  WHITE = $Color(new Color(), 255, 255, 255, 255);
  $Color(new Color(), 128, 0, 0, 255);
  RED = $Color(new Color(), 255, 0, 0, 255);
  PURPLE = $Color(new Color(), 128, 0, 128, 255);
  $Color(new Color(), 255, 0, 255, 255);
  $Color(new Color(), 0, 128, 0, 255);
  $Color(new Color(), 0, 255, 0, 255);
  $Color(new Color(), 128, 128, 0, 255);
  $Color(new Color(), 255, 255, 0, 255);
  $Color(new Color(), 0, 0, 128, 255);
  $Color(new Color(), 0, 0, 255, 255);
  $Color(new Color(), 0, 128, 128, 255);
  $Color(new Color(), 0, 255, 255, 255);
}

function $Color(this$static, red, green, blue, alpha){
  $clinit_128();
  $setRed(this$static, red);
  $setGreen(this$static, green);
  $setBlue(this$static, blue);
  $setAlpha(this$static, alpha);
  return this$static;
}

function $createColor(this$static, red, green, blue, alpha){
  return $wnd.dojo.colorFromArray([red, green, blue, alpha]);
}

function $getDojoColor(this$static){
  return $createColor(this$static, this$static.red, this$static.green, this$static.blue, this$static.alpha / 255.0);
}

function $isValidComponent(this$static, component){
  var invalid;
  invalid = component < 0 || component >= 256;
  if (invalid) {
    throw $IllegalArgumentException(new IllegalArgumentException(), 'Component must be between in 0-255 inclusive');
  }
  return !invalid;
}

function $reverse(this$static){
  return $Color(new Color(), 255 - this$static.red, 255 - this$static.green, 255 - this$static.blue, this$static.alpha);
}

function $setAlpha(this$static, alpha){
  if ($isValidComponent(this$static, alpha)) {
    this$static.alpha = alpha;
  }
}

function $setBlue(this$static, blue){
  if ($isValidComponent(this$static, blue)) {
    this$static.blue = blue;
  }
}

function $setGreen(this$static, green){
  if ($isValidComponent(this$static, green)) {
    this$static.green = green;
  }
}

function $setRed(this$static, red){
  if ($isValidComponent(this$static, red)) {
    this$static.red = red;
  }
}

function $toCss(this$static, includeAlpha){
  return toCss($getDojoColor(this$static), includeAlpha);
}

function $toHex(this$static){
  return toHex($getDojoColor(this$static));
}

function getBlue(color){
  $clinit_128();
  return color.b;
}

function getColor(hex){
  $clinit_128();
  var color;
  color = getColorFromHex(hex);
  return $Color(new Color(), getRed(color), getGreen(color), getBlue(color), 255);
}

function getColorFromHex(hex){
  $clinit_128();
  return $wnd.dojo.colorFromHex(hex);
}

function getGreen(color){
  $clinit_128();
  return color.g;
}

function getRed(color){
  $clinit_128();
  return color.r;
}

function toCss(color, includeAlpha){
  $clinit_128();
  return color.toCss(includeAlpha);
}

function toHex(color){
  $clinit_128();
  return color.toHex();
}

function toString_5(){
  return $toCss(this, true);
}

function Color(){
}

_ = Color.prototype = new Object_0();
_.toString$ = toString_5;
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'Color';
_.typeId$ = 102;
_.alpha = 0;
_.blue = 0;
_.green = 0;
_.red = 0;
var BLACK, PURPLE, RED, SILVER, WHITE;
function $Ellipse(this$static, radiusX, radiusY){
  $GraphicObject(this$static);
  this$static.radiusX = radiusX;
  this$static.radiusY = radiusY;
  return this$static;
}

function $createEllipse(this$static, surface, radiusX, radiusY){
  return surface.createEllipse({'cx':0, 'cy':0, 'rx':radiusX, 'ry':radiusY});
}

function createGfx_0(surface){
  return $createEllipse(this, surface, this.radiusX, this.radiusY);
}

function Ellipse(){
}

_ = Ellipse.prototype = new GraphicObject();
_.createGfx = createGfx_0;
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'Ellipse';
_.typeId$ = 103;
_.radiusX = 0;
_.radiusY = 0;
function $clinit_130(){
  $clinit_130 = nullMethod;
  DEFAULT_FONT = $Font(new Font(), 'serif', 10, 'normal', 'normal', 'normal');
}

function $Font(this$static, family, size, style, variant, weight){
  $clinit_130();
  this$static.family = family;
  this$static.size = size;
  this$static.style_0 = style;
  this$static.variant = variant;
  this$static.weight = weight;
  return this$static;
}

function $createFont(this$static){
  return $createFont_0(this$static, this$static.family, this$static.size + 'pt', this$static.style_0, this$static.variant, this$static.weight);
}

function $createFont_0(this$static, family, size, style, variant, weight){
  var font = {'family':family, 'size':size, 'style':style, 'variant':variant, 'weight':weight};
  return font;
}

function Font(){
}

_ = Font.prototype = new Object_0();
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'Font';
_.typeId$ = 104;
_.family = null;
_.size = 0;
_.style_0 = null;
_.variant = null;
_.weight = null;
var DEFAULT_FONT;
function $$init_16(this$static){
  this$static.objects = $ArrayList(new ArrayList());
  this$static.listeners = $ArrayList(new ArrayList());
  this$static.graphicObjects = $HashMap(new HashMap());
}

function $GraphicCanvas_0(this$static, el){
  var controller;
  $$init_16(this$static);
  this$static.setElement(el);
  controller = getInstance();
  $require(controller, 'dojox.gfx');
  this$static.sinkEvents(1);
  this$static.sinkEvents(2);
  this$static.sinkEvents(124);
  return this$static;
}

function $GraphicCanvas(this$static){
  $GraphicCanvas_0(this$static, createDiv());
  return this$static;
}

function $add_10(this$static, graphicObject, x, y){
  if ($add_13(this$static.objects, graphicObject)) {
    $translate(graphicObject, x, y);
    if (this$static.isAttached()) {
      $attachGraphicObject(this$static, graphicObject);
    }
  }
}

function $addGraphicObjectListener(this$static, listener){
  $add_13(this$static.listeners, listener);
  this$static.currentListeners = null;
}

function $attachAllGraphicObjects(this$static){
  var graphicObject, ite, shapes;
  ite = $iterator_1(this$static.objects);
  while ($hasNext_1(ite)) {
    graphicObject = dynamicCast($next_0(ite), 34);
    graphicObject.show(this$static);
    shapes = graphicObject.getShapes();
    $putEventSource_0(this$static, shapes, graphicObject);
  }
}

function $attachGraphicObject(this$static, graphicObject){
  var shapes;
  graphicObject.show(this$static);
  shapes = graphicObject.getShapes();
  $putEventSource(this$static, graphicObject.shape, graphicObject);
}

function $detachAllGraphicObjects(this$static){
  var graphicObject, ite;
  ite = $iterator_1(this$static.objects);
  while ($hasNext_1(ite)) {
    graphicObject = dynamicCast($next_0(ite), 34);
    $hide_1(graphicObject);
  }
}

function $detachGraphicObject(this$static, graphicObject){
  var ite, shape, shapes;
  $hide_1(graphicObject);
  shapes = graphicObject.getShapes();
  ite = $iterator_1(shapes);
  while ($hasNext_1(ite)) {
    shape = dynamicCast($next_0(ite), 2);
    if (shape !== null) {
      $remove_12(this$static.graphicObjects, wrapJSO(getEventSource(shape), JavaScriptObject));
    }
  }
}

function $doClick(this$static, evtSource, evt){
  var graphicObject, ite, listener;
  graphicObject = dynamicCast($get_1(this$static.graphicObjects, wrapJSO(evtSource, Element)), 34);
  ite = $iterator_1($getCurrentListeners(this$static));
  while ($hasNext_1(ite)) {
    listener = dynamicCast($next_0(ite), 35);
    listener.mouseClicked(graphicObject, evt);
  }
}

function $doDoubleClick(this$static, evtSource, evt){
  var graphicObject, ite, listener;
  graphicObject = dynamicCast($get_1(this$static.graphicObjects, wrapJSO(evtSource, Element)), 34);
  ite = $iterator_1($getCurrentListeners(this$static));
  while ($hasNext_1(ite)) {
    listener = dynamicCast($next_0(ite), 35);
    listener.mouseDblClicked(graphicObject, evt);
  }
}

function $doMouseDown(this$static, evtSource, evt){
  var graphicObject, ite, listener;
  graphicObject = dynamicCast($get_1(this$static.graphicObjects, wrapJSO(evtSource, Element)), 34);
  ite = $iterator_1($getCurrentListeners(this$static));
  while ($hasNext_1(ite)) {
    listener = dynamicCast($next_0(ite), 35);
    listener.mousePressed(graphicObject, evt);
  }
}

function $doMouseMove(this$static, evtSource, evt){
  var graphicObject, ite, listener;
  graphicObject = dynamicCast($get_1(this$static.graphicObjects, wrapJSO(evtSource, Element)), 34);
  ite = $iterator_1($getCurrentListeners(this$static));
  while ($hasNext_1(ite)) {
    listener = dynamicCast($next_0(ite), 35);
    listener.mouseMoved(graphicObject, evt);
  }
}

function $doMouseUp(this$static, evtSource, evt){
  var graphicObject, ite, listener;
  graphicObject = dynamicCast($get_1(this$static.graphicObjects, wrapJSO(evtSource, Element)), 34);
  ite = $iterator_1($getCurrentListeners(this$static));
  while ($hasNext_1(ite)) {
    listener = dynamicCast($next_0(ite), 35);
    listener.mouseReleased(graphicObject, evt);
  }
}

function $getCurrentListeners(this$static){
  if (this$static.currentListeners === null) {
    this$static.currentListeners = $ArrayList_0(new ArrayList(), this$static.listeners);
  }
  return this$static.currentListeners;
}

function $putEventSource(this$static, shape, graphicObject){
  $put_0(this$static.graphicObjects, wrapJSO(getEventSource(shape), JavaScriptObject), graphicObject);
}

function $putEventSource_0(this$static, shapes, graphicObject){
  var ite, shape;
  ite = $iterator_1(shapes);
  while ($hasNext_1(ite)) {
    shape = dynamicCast($next_0(ite), 2);
    $putEventSource(this$static, shape, graphicObject);
  }
}

function $remove_7(this$static, graphicObject){
  if ($remove_10(this$static.objects, graphicObject) && this$static.isAttached()) {
    $detachGraphicObject(this$static, graphicObject);
  }
}

function getEventSource(graphicObject){
  return graphicObject.getEventSource();
}

function initGraphics(node, canvas, width, height){
  var surface = $wnd.dojox.gfx.createSurface(node, width, height);
  surface.canvas = canvas;
  surface.handleDragStart = $wnd.dojo.connect(node, 'ondragstart', $wnd.dojo, 'stopEvent');
  surface.handleSelectStart = $wnd.dojo.connect(node, 'onselectstart', $wnd.dojo, 'stopEvent');
  return surface;
}

function onAttach_6(){
  $onAttach(this);
  this.surface = initGraphics(this.getElement(), this, this.getOffsetWidth(), this.getOffsetHeight());
  $attachAllGraphicObjects(this);
}

function onBrowserEvent_8(event_0){
  var type;
  type = eventGetType(event_0);
  if (type == 1) {
    $doClick(this, eventGetTarget(event_0), event_0);
  }
   else if (type == 2) {
    $doDoubleClick(this, eventGetTarget(event_0), event_0);
  }
   else if (type == 4) {
    $doMouseDown(this, eventGetTarget(event_0), event_0);
  }
   else if (type == 8) {
    $doMouseUp(this, eventGetTarget(event_0), event_0);
  }
   else if (type == 64) {
    $doMouseMove(this, eventGetTarget(event_0), event_0);
  }
}

function onDetach_6(){
  $detachAllGraphicObjects(this);
  releaseGraphics(this.getElement(), this.surface);
  $onDetach(this);
}

function releaseCanvas(surface){
  $wnd.dojo.disconnect(surface.handleDragStart);
  $wnd.dojo.disconnect(surface.handleSelectStart);
  surface.canvas = null;
}

function releaseGraphics(element, surface){
  var c, i;
  c = getChildCount(element);
  for (i = 0; i < c; i++) {
    removeChild(element, getChild(element, 0));
  }
  releaseCanvas(surface);
}

function GraphicCanvas(){
}

_ = GraphicCanvas.prototype = new Widget();
_.onAttach = onAttach_6;
_.onBrowserEvent = onBrowserEvent_8;
_.onDetach = onDetach_6;
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'GraphicCanvas';
_.typeId$ = 105;
_.currentListeners = null;
_.surface = null;
function $RectangularShape(this$static, width, height){
  $GraphicObject(this$static);
  this$static.width_0 = width;
  this$static.height_0 = height;
  return this$static;
}

function $setWidth_1(this$static, width){
  var factor;
  factor = width / this$static.width_0;
  this$static.width_0 = width;
  if (this$static.shape !== null) {
    $scale_0(this$static, factor, 1);
  }
}

function RectangularShape(){
}

_ = RectangularShape.prototype = new GraphicObject();
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'RectangularShape';
_.typeId$ = 106;
_.height_0 = 0.0;
_.width_0 = 0.0;
function $ImageGfx(this$static, url, width, height){
  $RectangularShape(this$static, width, height);
  this$static.url = url;
  return this$static;
}

function $createImage(this$static, surface, url, width, height){
  return surface.createImage({'src':url, 'width':width, 'height':height});
}

function createGfx_1(surface){
  return $createImage(this, surface, this.url, this.width_0, this.height_0);
}

function ImageGfx(){
}

_ = ImageGfx.prototype = new RectangularShape();
_.createGfx = createGfx_1;
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'ImageGfx';
_.typeId$ = 107;
_.url = null;
function $Line(this$static, a, b){
  $GraphicObject(this$static);
  this$static.pointA = $Point_2(new Point(), a);
  this$static.pointB = $Point_2(new Point(), b);
  return this$static;
}

function $createLine(this$static, surface, xa, ya, xb, yb){
  return surface.createLine({'x1':xa, 'y1':ya, 'x2':xb, 'y2':yb});
}

function createGfx_2(surface){
  return $createLine(this, surface, this.pointA.x_0, this.pointA.y_0, this.pointB.x_0, this.pointB.y_0);
}

function Line(){
}

_ = Line.prototype = new GraphicObject();
_.createGfx = createGfx_2;
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'Line';
_.typeId$ = 108;
_.pointA = null;
_.pointB = null;
function $Path(this$static){
  $GraphicObject(this$static);
  this$static.commands = $ArrayList(new ArrayList());
  return this$static;
}

function $arcTo(this$static, rx, ry, xAxisRot, largeArcFlag, sweepFlag, x, y){
  var com;
  com = $Path$Command(new Path$Command(), 2, this$static);
  $addInt(com, rx);
  $addInt(com, ry);
  $addInt(com, xAxisRot);
  $addBoolean(com, largeArcFlag);
  $addBoolean(com, sweepFlag);
  $addDouble(com, x);
  $addDouble(com, y);
  $register(this$static, com);
}

function $arcTo_1(this$static, path, rx, ry, xAxisRot, largeArcFlag, sweepFlag, x, y){
  path.arcTo(rx, ry, xAxisRot, largeArcFlag, sweepFlag, x, y);
}

function $arcTo_0(this$static, rx, ry, xAxisRot, largeArcFlag, sweepFlag, pt){
  $arcTo(this$static, rx, ry, xAxisRot, largeArcFlag, sweepFlag, pt.x_0, pt.y_0);
}

function $closePath(this$static, path){
  path.closePath();
}

function $createPath(this$static, surface){
  return surface.createPath();
}

function $curveTo(this$static, x1, y1, x2, y2, x, y){
  var com;
  com = $Path$Command(new Path$Command(), 0, this$static);
  $addDouble(com, x1);
  $addDouble(com, y1);
  $addDouble(com, x2);
  $addDouble(com, y2);
  $addDouble(com, x);
  $addDouble(com, y);
  $register(this$static, com);
}

function $curveTo_0(this$static, path, x1, y1, x2, y2, x, y){
  path.curveTo(x1, y1, x2, y2, x, y);
}

function $execute(this$static, command){
  switch (command.id_0) {
    default:{
        break;
      }

    case 0:
      {
        $curveTo_0(this$static, this$static.shape, $getDouble(command, 0), $getDouble(command, 1), $getDouble(command, 2), $getDouble(command, 3), $getDouble(command, 4), $getDouble(command, 5));
        break;
      }

    case 2:
      {
        $arcTo_1(this$static, this$static.shape, $getInt(command, 0), $getInt(command, 1), $getInt(command, 2), $getBoolean(command, 3), $getBoolean(command, 4), $getDouble(command, 5), $getDouble(command, 6));
        break;
      }

    case 4:
      {
        $lineTo_0(this$static, this$static.shape, $getDouble(command, 0), $getDouble(command, 1));
        break;
      }

    case 7:
      {
        $hLineTo(this$static, this$static.shape, $getDouble(command, 0));
        break;
      }

    case 8:
      {
        $vLineTo(this$static, this$static.shape, $getDouble(command, 0));
        break;
      }

    case 3:
      {
        $moveTo_0(this$static, this$static.shape, $getDouble(command, 0), $getDouble(command, 1));
        break;
      }

    case 5:
      {
        $qCurveTo(this$static, this$static.shape, $getDouble(command, 0), $getDouble(command, 1), $getDouble(command, 2), $getDouble(command, 3));
        break;
      }

    case 6:
      {
        $qSmoothCurveTo(this$static, this$static.shape, $getDouble(command, 0), $getDouble(command, 1));
        break;
      }

    case 1:
      {
        $smoothCurveTo(this$static, this$static.shape, $getDouble(command, 0), $getDouble(command, 0), $getDouble(command, 0), $getDouble(command, 0));
        break;
      }

    case 10:
      {
        $setAbsoluteMode(this$static, this$static.shape, $getBoolean(command, 0));
        break;
      }

    case 9:
      {
        $closePath(this$static, this$static.shape);
        break;
      }

  }
}

function $hLineTo(this$static, path, x){
  path.hLineTo(x);
}

function $lineTo(this$static, x, y){
  var com;
  com = $Path$Command(new Path$Command(), 4, this$static);
  $addDouble(com, x);
  $addDouble(com, y);
  $register(this$static, com);
}

function $lineTo_1(this$static, point){
  $lineTo(this$static, point.x_0, point.y_0);
}

function $lineTo_0(this$static, path, x, y){
  path.lineTo(x, y);
}

function $moveTo(this$static, x, y){
  var com;
  com = $Path$Command(new Path$Command(), 3, this$static);
  $addDouble(com, x);
  $addDouble(com, y);
  $register(this$static, com);
}

function $moveTo_1(this$static, point){
  $moveTo(this$static, point.x_0, point.y_0);
}

function $moveTo_0(this$static, shape, x, y){
  shape.moveTo(x, y);
}

function $qCurveTo(this$static, path, x1, y1, x, y){
  path.qCurveTo(x1, y1, x, y);
}

function $qSmoothCurveTo(this$static, path, x, y){
  path.qSmoothCurveTo(x, y);
}

function $register(this$static, command){
  $add_13(this$static.commands, command);
  if (this$static.shape !== null) {
    $execute(this$static, command);
  }
}

function $setAbsoluteMode_0(this$static, mode){
  var com;
  com = $Path$Command(new Path$Command(), 10, this$static);
  $addBoolean(com, mode);
  $register(this$static, com);
}

function $setAbsoluteMode(this$static, path, mode){
  path.setAbsoluteMode(mode);
}

function $show_1(this$static, canvas){
  var com, ite;
  $show_0(this$static, canvas);
  ite = $iterator_1(this$static.commands);
  while ($hasNext_1(ite)) {
    com = dynamicCast($next_0(ite), 36);
    $execute(this$static, com);
  }
}

function $smoothCurveTo(this$static, path, x2, y2, x, y){
  path.smoothCurveTo(x2, y2, x, y);
}

function $vLineTo(this$static, path, y){
  path.vLineTo(y);
}

function createGfx_3(surface){
  return $createPath(this, surface);
}

function show_0(canvas){
  $show_1(this, canvas);
}

function Path(){
}

_ = Path.prototype = new GraphicObject();
_.createGfx = createGfx_3;
_.show = show_0;
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'Path';
_.typeId$ = 109;
_.commands = null;
function $Path$Command(this$static, id, this$0){
  this$static.id_0 = id;
  this$static.parameters = $ArrayList(new ArrayList());
  return this$static;
}

function $addBoolean(this$static, param){
  var b;
  b = $Boolean(new Boolean_0(), param);
  $add_13(this$static.parameters, b);
}

function $addDouble(this$static, param){
  var d;
  d = $Double(new Double(), param);
  $add_13(this$static.parameters, d);
}

function $addInt(this$static, param){
  var i;
  i = $Integer(new Integer(), param);
  $add_13(this$static.parameters, i);
}

function $getBoolean(this$static, i){
  var o, res;
  o = $get_0(this$static.parameters, i);
  res = true;
  if (instanceOf(o, 39)) {
    res = dynamicCast(o, 39).value;
  }
  return res;
}

function $getDouble(this$static, i){
  var o, res;
  o = $get_0(this$static.parameters, i);
  res = 0.0;
  if (instanceOf(o, 37)) {
    res = dynamicCast(o, 37).value;
  }
  return res;
}

function $getInt(this$static, i){
  var o, res;
  o = $get_0(this$static.parameters, i);
  res = 0;
  if (instanceOf(o, 38)) {
    res = dynamicCast(o, 38).value;
  }
  return res;
}

function Path$Command(){
}

_ = Path$Command.prototype = new Object_0();
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'Path$Command';
_.typeId$ = 110;
_.id_0 = 0;
_.parameters = null;
function $clinit_138(){
  $clinit_138 = nullMethod;
  DEFAULT_PATTERN = $Pattern_0(new Pattern(), ' ', 0, 0, 0, 0);
}

function $Pattern_0(this$static, url, xCoord, yCoord, width, height){
  $clinit_138();
  this$static.url = url;
  this$static.position = $Point_0(new Point(), xCoord, yCoord);
  this$static.width_0 = width;
  this$static.height_0 = height;
  return this$static;
}

function $Pattern(this$static, image, xCoord, yCoord){
  $clinit_138();
  $Pattern_0(this$static, $getUrl_0(image), xCoord, yCoord, $getWidth_0(image), $getHeight_0(image));
  return this$static;
}

function $createPattern(this$static, url, x, y, width, height){
  return {'type':'pattern', 'src':url, 'x':x, 'y':y, 'width':width, 'height':height};
}

function $getGFXPattern(this$static){
  return $createPattern(this$static, this$static.url, this$static.position.x_0, this$static.position.y_0, this$static.width_0, this$static.height_0);
}

function Pattern(){
}

_ = Pattern.prototype = new Object_0();
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'Pattern';
_.typeId$ = 111;
_.height_0 = 10;
_.position = null;
_.url = '';
_.width_0 = 10;
var DEFAULT_PATTERN;
function $Point(this$static){
  $Point_1(this$static, 0, 0);
  return this$static;
}

function $Point_2(this$static, point){
  $Point_0(this$static, point.x_0, point.y_0);
  return this$static;
}

function $Point_1(this$static, x, y){
  $setX_0(this$static, x);
  $setY_0(this$static, y);
  return this$static;
}

function $Point_0(this$static, x, y){
  $setX(this$static, x);
  $setY(this$static, y);
  return this$static;
}

function $createPoint(this$static, x, y){
  return {'x':x, 'y':y};
}

function $distance(this$static, b){
  var dx, dy, squarred;
  dx = b.x_0 - this$static.x_0;
  dy = b.y_0 - this$static.y_0;
  squarred = dx * dx + dy * dy;
  return sqrt(squarred);
}

function $equals_0(this$static, object){
  var equals, point;
  equals = false;
  if (instanceOf(object, 40)) {
    point = dynamicCast(object, 40);
    equals = this$static.x_0 == point.x_0 && this$static.y_0 == point.y_0;
  }
  return equals;
}

function $getGFXPoint(this$static){
  return $createPoint(this$static, this$static.x_0, this$static.y_0);
}

function $getX(this$static, point){
  return point.x;
}

function $getY(this$static, point){
  return point.y;
}

function $rotate_1(this$static, angle, center){
  var cosa, radian, sina, x1, y1;
  radian = toRadians(angle);
  sina = sin(radian);
  cosa = cos(radian);
  x1 = cosa * (this$static.x_0 - center.x_0) - sina * (this$static.y_0 - center.y_0) + center.x_0;
  y1 = sina * (this$static.x_0 - center.x_0) + cosa * (this$static.y_0 - center.y_0) + center.y_0;
  this$static.x_0 = x1;
  this$static.y_0 = y1;
}

function $setLocation(this$static, x, y){
  $setX(this$static, x);
  $setY(this$static, y);
}

function $setLocation_0(this$static, p){
  $setLocation(this$static, p.x_0, p.y_0);
}

function $setPoint(this$static, point){
  $setLocation(this$static, $getX(this$static, point), $getY(this$static, point));
}

function $setX_0(this$static, x){
  this$static.x_0 = x;
}

function $setX(this$static, x){
  this$static.x_0 = x;
}

function $setY_0(this$static, y){
  this$static.y_0 = y;
}

function $setY(this$static, y){
  this$static.y_0 = y;
}

function $translate_0(this$static, dx, dy){
  var newX, newY;
  newX = this$static.x_0 + dx;
  newY = this$static.y_0 + dy;
  $setLocation(this$static, newX, newY);
}

function equals_3(object){
  return $equals_0(this, object);
}

function toString_6(){
  return 'Point:(' + this.x_0 + ',' + this.y_0 + ')';
}

function Point(){
}

_ = Point.prototype = new Object_0();
_.equals$ = equals_3;
_.toString$ = toString_6;
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'Point';
_.typeId$ = 112;
_.x_0 = 0;
_.y_0 = 0;
function $Polyline(this$static, points){
  $GraphicObject(this$static);
  this$static.points = points;
  return this$static;
}

function $createArray(this$static, points){
  var i, jsArray;
  jsArray = createArray();
  for (i = 0; i < points.length_0; i++) {
    jsArray = $put(this$static, jsArray, $getGFXPoint(points[i]), i);
  }
  return jsArray;
}

function $createPolyline(this$static, surface, points){
  return surface.createPolyline({'points':points});
}

function $put(this$static, array, point, index){
  array[index] = point;
  return array;
}

function createGfx_4(surface){
  var arrayPoints;
  arrayPoints = $createArray(this, this.points);
  return $createPolyline(this, surface, arrayPoints);
}

function Polyline(){
}

_ = Polyline.prototype = new GraphicObject();
_.createGfx = createGfx_4;
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'Polyline';
_.typeId$ = 113;
_.points = null;
function $Rect(this$static, width, height){
  $RectangularShape(this$static, width, height);
  return this$static;
}

function $createGfx_0(this$static, surface, width, height){
  return surface.createRect({'width':width, 'height':height});
}

function createGfx_5(surface){
  return $createGfx_0(this, surface, this.width_0, this.height_0);
}

function Rect(){
}

_ = Rect.prototype = new RectangularShape();
_.createGfx = createGfx_5;
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'Rect';
_.typeId$ = 114;
function $Rectangle_0(this$static, x, y, width, height){
  $setRect(this$static, x, y, width, height);
  return this$static;
}

function $Rectangle(this$static){
  $Rectangle_0(this$static, 0, 0, 0, 0);
  return this$static;
}

function $getCenter(this$static){
  return $Point_0(new Point(), $getCenterX(this$static), $getCenterY(this$static));
}

function $getCenterX(this$static){
  return this$static.point.x_0 + this$static.width_0 / 2.0;
}

function $getCenterY(this$static){
  return this$static.point.y_0 + this$static.height_0 / 2.0;
}

function $getPoint(this$static, points, index){
  return points[index];
}

function $getRectHeight(this$static, rect){
  return rect.height;
}

function $getRectWidth(this$static, rect){
  return rect.width;
}

function $getRectX(this$static, rect){
  return rect.x;
}

function $getRectY(this$static, rect){
  return rect.y;
}

function $rotate_2(this$static, angle){
  $rotate_1(this$static.point, angle, $getCenter(this$static));
}

function $setRect(this$static, x, y, w, h){
  this$static.point = $Point_0(new Point(), x, y);
  this$static.width_0 = w;
  this$static.height_0 = h;
}

function $setRect_0(this$static, rect){
  $setRect(this$static, $getRectX(this$static, rect), $getRectY(this$static, rect), $getRectWidth(this$static, rect), $getRectHeight(this$static, rect));
}

function $setRectFromPoints(this$static, points){
  var i, javaPoints;
  javaPoints = initDims_0('[Lcom.objetdirect.tatami.client.gfx.Point;', [164], [40], [4], null);
  for (i = 0; i < javaPoints.length_0; i++) {
    javaPoints[i] = $Point(new Point());
    $setPoint(javaPoints[i], $getPoint(this$static, points, i));
  }
  $setRect(this$static, javaPoints[0].x_0, javaPoints[0].y_0, $distance(javaPoints[0], javaPoints[1]), $distance(javaPoints[0], javaPoints[3]));
}

function $translate_1(this$static, dx, dy){
  $translate_0(this$static.point, dx, dy);
}

function equals_4(obj){
  var equal, r2d;
  equal = false;
  if (obj === this) {
    return true;
  }
  if (instanceOf(obj, 41)) {
    r2d = dynamicCast(obj, 41);
    equal = this.point.x_0 == r2d.point.x_0 && this.point.y_0 == r2d.point.y_0 && this.width_0 == r2d.width_0 && this.height_0 == r2d.height_0;
  }
  return equal;
}

function toString_7(){
  return 'Rectangle [x=' + this.point.x_0 + ',y=' + this.point.y_0 + ',w=' + this.width_0 + ',h=' + this.height_0 + ']';
}

function Rectangle(){
}

_ = Rectangle.prototype = new Object_0();
_.equals$ = equals_4;
_.toString$ = toString_7;
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'Rectangle';
_.typeId$ = 115;
_.height_0 = 0;
_.point = null;
_.width_0 = 0;
function $$init_19(this$static){
  this$static.font = ($clinit_130() , DEFAULT_FONT);
}

function $Text(this$static, text){
  $Text_0(this$static, text, 'none');
  return this$static;
}

function $Text_0(this$static, text, decoration){
  $GraphicObject(this$static);
  $$init_19(this$static);
  this$static.text = text;
  this$static.decoration = decoration;
  return this$static;
}

function $createGfx_1(this$static, surface, text, decoration){
  return surface.createText({'text':text, 'decoration':decoration});
}

function $getFontSize(this$static, size){
  return $wnd.dojox.gfx.normalizedLength(size);
}

function $getLineHeight(this$static){
  return $getFontSize(this$static, this$static.font.size + 'pt');
}

function $setFont_2(this$static, font){
  this$static.font = font;
  if (this$static.shape !== null) {
    $setFont_1(this$static, this$static.shape, $createFont(font));
  }
}

function $setFont_1(this$static, shape, font){
  shape.setFont(font);
}

function createGfx_7(surface){
  return $createGfx_1(this, surface, this.text, this.decoration);
}

function getBounds_0(){
  var height, i, lines, maxWidth, width;
  lines = $split(this.text, '\n|\r');
  maxWidth = 0;
  for (i = 0; i < lines.length_0; i++) {
    maxWidth = max(maxWidth, $length(lines[i]));
  }
  width = maxWidth * $getLineHeight(this) * 0.75;
  height = lines.length_0 * $getLineHeight(this);
  return $Rectangle_0(new Rectangle(), this.position.x_0, this.position.y_0, width, height * 1.5);
}

function show_2(canvas){
  $show_0(this, canvas);
  $setFont_2(this, this.font);
}

function Text(){
}

_ = Text.prototype = new GraphicObject();
_.createGfx = createGfx_7;
_.getBounds = getBounds_0;
_.show = show_2;
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'Text';
_.typeId$ = 116;
_.decoration = null;
_.text = null;
function $$init_18(this$static){
  this$static.font = ($clinit_130() , DEFAULT_FONT);
}

function $TextPath_0(this$static, text, decoration){
  $Path(this$static);
  $$init_18(this$static);
  this$static.text = text;
  this$static.decoration = decoration;
  return this$static;
}

function $TextPath(this$static, text){
  $TextPath_0(this$static, text, 'none');
  return this$static;
}

function $createTextPath(this$static, surface, text, decoration){
  var param = {'text':text, 'decoration':decoration};
  return surface.createTextPath(param);
}

function $setFont_0(this$static, font){
  this$static.font = font;
  if (this$static.shape !== null) {
    $setFont(this$static, this$static.shape, $createFont(font));
  }
}

function $setFont(this$static, shape, font){
  shape.setFont(font);
}

function createGfx_6(surface){
  return $createTextPath(this, surface, this.text, this.decoration);
}

function show_1(canvas){
  $show_1(this, canvas);
  $setFont_0(this, this.font);
}

function TextPath(){
}

_ = TextPath.prototype = new Path();
_.createGfx = createGfx_6;
_.show = show_1;
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'TextPath';
_.typeId$ = 117;
_.decoration = 'none';
_.text = '';
function $VirtualGroup(this$static){
  $GraphicObject(this$static);
  this$static.objects = $ArrayList(new ArrayList());
  return this$static;
}

function $add_11(this$static, group, shape){
  group.add(shape);
}

function $add_12(this$static, object){
  var shapes;
  $add_13(this$static.objects, object);
  $setGroup(object, this$static);
  if (this$static.shape !== null) {
    if (this$static.parent_0 !== null) {
      object.show(this$static.parent_0);
      shapes = object.getShapes();
      $putEventSource_0(this$static.parent_0, shapes, object);
      $add_11(this$static, this$static.shape, object.shape);
    }
  }
}

function $buildObjects(this$static){
  var ite, object, shapes;
  ite = $iterator_1(this$static.objects);
  while ($hasNext_1(ite)) {
    object = dynamicCast($next_0(ite), 34);
    object.show(this$static.parent_0);
    shapes = object.getShapes();
    $putEventSource_0(this$static.parent_0, shapes, object);
    $add_11(this$static, this$static.shape, object.shape);
  }
}

function $createGroup(this$static, surface){
  return surface.createGroup();
}

function applyPattern_0(pattern){
  var ite;
  $applyPattern(this, pattern);
  ite = $iterator_1(this.objects);
  while ($hasNext_1(ite)) {
    dynamicCast($next_0(ite), 34).applyPattern(pattern);
  }
  return this;
}

function createGfx_8(surface){
  return $createGroup(this, surface);
}

function getShapes_0(){
  var ite, list, object;
  list = $ArrayList(new ArrayList());
  $add_13(list, wrapJSO(this.shape, JavaScriptObject));
  ite = $iterator_1(this.objects);
  while ($hasNext_1(ite)) {
    object = dynamicCast($next_0(ite), 34);
    $addAll(list, object.getShapes());
  }
  return list;
}

function setFillColor_0(color){
  var ite;
  $setFillColor(this, color);
  ite = $iterator_1(this.objects);
  while ($hasNext_1(ite)) {
    dynamicCast($next_0(ite), 34).setFillColor(color);
  }
  return this;
}

function setStroke_0(color, width){
  var ite;
  $setStroke(this, color, width);
  ite = $iterator_1(this.objects);
  while ($hasNext_1(ite)) {
    dynamicCast($next_0(ite), 34).setStroke_0(color, width);
  }
  return this;
}

function show_3(surface){
  $show_0(this, surface);
  $buildObjects(this);
}

function VirtualGroup(){
}

_ = VirtualGroup.prototype = new GraphicObject();
_.applyPattern = applyPattern_0;
_.createGfx = createGfx_8;
_.getShapes = getShapes_0;
_.setFillColor = setFillColor_0;
_.setStroke_0 = setStroke_0;
_.show = show_3;
_.typeName$ = package_com_objetdirect_tatami_client_gfx_ + 'VirtualGroup';
_.typeId$ = 118;
_.objects = null;
function $ColorDemo(this$static){
  $initComponents(this$static);
  $initWidget(this$static, this$static.panel);
  return this$static;
}

function $initComponents(this$static){
  var vPanel, vPanel2;
  this$static.panel = $DockPanel(new DockPanel());
  $setSpacing(this$static.panel, 30);
  this$static.colorLabel = $HTML_0(new HTML(), '<b>No color Selected.<\/b>');
  this$static.big = $ColorChooser(new ColorChooser());
  this$static.big.setTitle('70 colors');
  $addChangeListener(this$static.big, this$static);
  this$static.small = $ColorChooser_0(new ColorChooser(), '3x4');
  $addChangeListener(this$static.small, this$static);
  this$static.small.setTitle('12 colors');
  this$static.picker = $ColorPicker(new ColorPicker());
  vPanel = $VerticalPanel(new VerticalPanel());
  $setSpacing(vPanel, 20);
  $add_6(vPanel, $HTML_0(new HTML(), '<b>ColorChooser<\/b>: 2 sizes available, click on color to change the color of text.'));
  $add_6(vPanel, this$static.big);
  $add_6(vPanel, this$static.colorLabel);
  $add_6(vPanel, this$static.small);
  $add_2(this$static.panel, vPanel, ($clinit_36() , WEST));
  vPanel2 = $VerticalPanel(new VerticalPanel());
  $add_6(vPanel2, $HTML_0(new HTML(), "<b>ColorPicker<\/b> : Provides an interactive HSV ColorPicker similar to PhotoShop's color selction tool. Will eventually mixin FormWidget and be used as a suplement or a\t'more interactive' replacement for ColorChooser"));
  $add_6(vPanel2, this$static.picker);
  $add_2(this$static.panel, vPanel2, ($clinit_36() , EAST));
}

function onChange(sender){
  var color;
  color = null;
  if (sender.equals$(this.big)) {
    color = this.big.color;
  }
   else {
    color = this.small.color;
  }
  if (color !== null) {
    $setHTML(this.colorLabel, '<font color="' + color + '">The color selected : ' + color + '<\/font>');
  }
}

function ColorDemo(){
}

_ = ColorDemo.prototype = new Composite();
_.onChange_0 = onChange;
_.typeName$ = package_com_objetdirect_tatami_demo_client_ + 'ColorDemo';
_.typeId$ = 119;
_.big = null;
_.colorLabel = null;
_.panel = null;
_.picker = null;
_.small = null;
function $DateTimeDemo(this$static){
  $initComponents_0(this$static);
  this$static.inputDate.setDate($Date(new Date_0()));
  $initWidget(this$static, this$static.mainPanel);
  return this$static;
}

function $equalsObj(this$static, o1, o2){
  if (o1 === null && o2 === null) {
    return true;
  }
   else if (o1 === null || o2 === null) {
    return false;
  }
   else {
    return $equals_3(o1, o2);
  }
}

function $initComponents_0(this$static){
  var clock, constraints, datePanel, htmlDatePicker, htmlInputDate, htmlInputTime, htmlTimePicker, timePanel;
  this$static.mainPanel = $HorizontalPanel(new HorizontalPanel());
  $setSpacing(this$static.mainPanel, 50);
  timePanel = $VerticalPanel(new VerticalPanel());
  datePanel = $VerticalPanel(new VerticalPanel());
  $setSpacing(datePanel, 20);
  $setSpacing(timePanel, 20);
  htmlInputDate = $HTML_0(new HTML(), '<b>DropdownDatePicker<\/b> : <br>To Help a user to write a well formed Date with a calendar.');
  htmlDatePicker = $HTML_0(new HTML(), '<b>DatePicker<\/b> : <br>   A Calendar object to help user to choose a date and work with this date.');
  htmlInputTime = $HTML_0(new HTML(), '<b>DropdownTimePicker<\/b> : <br>To Help a user to write a well formed time with a pciker object.');
  htmlTimePicker = $HTML_0(new HTML(), '<b>TimePicker<\/b> : <br>   A Picker object to help user to choose a time and work with this time.');
  this$static.inputDate = $DropdownDatePicker(new DropdownDatePicker(), 'inputDate');
  $setInvalidMessage(this$static.inputDate, 'the date is incorrect');
  this$static.datePicker = $DatePicker(new DatePicker());
  $setDate(this$static.datePicker, $Date(new Date_0()));
  $add_6(datePanel, htmlInputDate);
  $add_6(datePanel, this$static.inputDate);
  $add_6(datePanel, htmlDatePicker);
  $add_6(datePanel, this$static.datePicker);
  $add_6(datePanel, $HTML_0(new HTML(), 'If you modify the <b>DatePicker<\/b>, the <b>DropdowDatePicker<\/b> will be modify too and vice-versa'));
  $linkDropdownAndPicker(this$static, this$static.inputDate, this$static.datePicker);
  this$static.inputTime = $DropdownTimePicker(new DropdownTimePicker(), 'inputTime');
  $setPromptMessage(this$static.inputTime, 'HH:mm');
  constraints = new TimePickerConstraints();
  constraints.clickableIncrement = 'T00:30:00';
  this$static.timePicker = $TimePicker(new TimePicker(), constraints);
  $add_6(timePanel, htmlInputTime);
  $add_6(timePanel, this$static.inputTime);
  $add_6(timePanel, htmlTimePicker);
  $add_6(timePanel, this$static.timePicker);
  $add_6(timePanel, $HTML_0(new HTML(), 'If you modify the <b>TimePicker<\/b>, the <b>DropdowTimePicker<\/b> will be modify too and vice-versa'));
  $linkDropdownAndPicker(this$static, this$static.inputTime, this$static.timePicker);
  $add_3(this$static.mainPanel, datePanel);
  $add_3(this$static.mainPanel, timePanel);
  clock = $Clock(new Clock(), 'clock_face.jpg', 385);
  $add_3(this$static.mainPanel, clock);
}

function $linkDropdownAndPicker(this$static, container, picker){
  $addChangeListener_0(container, $DateTimeDemo$1(new DateTimeDemo$1(), this$static, picker, container));
  $addChangeListener(picker, $DateTimeDemo$2(new DateTimeDemo$2(), this$static, container, picker));
}

function DateTimeDemo(){
}

_ = DateTimeDemo.prototype = new Composite();
_.typeName$ = package_com_objetdirect_tatami_demo_client_ + 'DateTimeDemo';
_.typeId$ = 120;
_.datePicker = null;
_.inputDate = null;
_.inputTime = null;
_.mainPanel = null;
_.timePicker = null;
function $DateTimeDemo$1(this$static, this$0, val$picker, val$container){
  this$static.this$0 = this$0;
  this$static.val$picker = val$picker;
  this$static.val$container = val$container;
  return this$static;
}

function onChange_0(sender){
  if (!$equalsObj(this.this$0, this.val$picker.date, this.val$container.date)) {
    $setDate(this.val$picker, this.val$container.date);
  }
}

function DateTimeDemo$1(){
}

_ = DateTimeDemo$1.prototype = new Object_0();
_.onChange_0 = onChange_0;
_.typeName$ = package_com_objetdirect_tatami_demo_client_ + 'DateTimeDemo$1';
_.typeId$ = 121;
function $DateTimeDemo$2(this$static, this$0, val$container, val$picker){
  this$static.this$0 = this$0;
  this$static.val$container = val$container;
  this$static.val$picker = val$picker;
  return this$static;
}

function onChange_1(sender){
  if (!$equalsObj(this.this$0, this.val$container.date, this.val$picker.date)) {
    this.val$container.setDate(this.val$picker.date);
  }
}

function DateTimeDemo$2(){
}

_ = DateTimeDemo$2.prototype = new Object_0();
_.onChange_0 = onChange_1;
_.typeName$ = package_com_objetdirect_tatami_demo_client_ + 'DateTimeDemo$2';
_.typeId$ = 122;
function $DragAndDropDemo(this$static){
  $initComponents_1(this$static);
  $initWidget(this$static, this$static.mainPanel);
  return this$static;
}

function $initComponents_1(this$static){
  this$static.mainPanel = $DockPanel(new DockPanel());
  $setSpacing(this$static.mainPanel, 20);
  this$static.amoursCelebres = $DragAndDropPanel(new DragAndDropPanel());
  $setSize(this$static.amoursCelebres, '480px', '300px');
  this$static.romeoPanel = $SimplePanel(new SimplePanel());
  this$static.romeo = $Image(new Image_0(), 'romeo.png');
  setStyleAttribute(this$static.romeo.getElement(), 'cursor', 'pointer');
  this$static.romeo.setTitle('romeo');
  $add_4(this$static.romeoPanel, this$static.romeo);
  this$static.juliette = $Image(new Image_0(), 'juliette.png');
  this$static.juliette.setTitle('juliette');
  $addDraggableWidget(this$static.amoursCelebres, this$static.romeoPanel, 100, 20, 'romeo_et_juliette');
  $addTargetWidget(this$static.amoursCelebres, this$static.juliette, 300, 20, 'romeo_et_juliette');
  this$static.tristanPanel = $SimplePanel(new SimplePanel());
  this$static.tristan = $Image(new Image_0(), 'tristan.png');
  setStyleAttribute(this$static.tristan.getElement(), 'cursor', 'pointer');
  $add_4(this$static.tristanPanel, this$static.tristan);
  this$static.tristan.setTitle('tristan');
  this$static.iseult = $Image(new Image_0(), 'iseult.png');
  this$static.iseult.setTitle('iseult');
  $addDraggableWidget(this$static.amoursCelebres, this$static.tristanPanel, 300, 180, 'tristan_et_iseult');
  $addTargetWidget(this$static.amoursCelebres, this$static.iseult, 100, 180, 'tristan_et_iseult');
  $addDragDropListener(this$static.amoursCelebres, this$static);
  $add_2(this$static.mainPanel, $HTML_0(new HTML(), '<b>Drag and Drop<\/b> : Recompose the famous lovers by moving the images of the amants.'), ($clinit_36() , NORTH));
  $add_2(this$static.mainPanel, this$static.amoursCelebres, ($clinit_36() , CENTER));
}

function acceptDrop_0(draggable, target){
  return true;
}

function onDrop(draggable, target){
  var couple, dulcinee;
  dulcinee = dynamicCast(target, 42);
  couple = $Image(new Image_0(), 'couple_' + dulcinee.getTitle() + '.png');
  $add_8(this.amoursCelebres, couple, $getWidgetLeft_0(this.amoursCelebres, target) - 50, $getWidgetTop_0(this.amoursCelebres, target) - 25);
}

function DragAndDropDemo(){
}

_ = DragAndDropDemo.prototype = new Composite();
_.acceptDrop_0 = acceptDrop_0;
_.onDrop = onDrop;
_.typeName$ = package_com_objetdirect_tatami_demo_client_ + 'DragAndDropDemo';
_.typeId$ = 123;
_.amoursCelebres = null;
_.iseult = null;
_.juliette = null;
_.mainPanel = null;
_.romeo = null;
_.romeoPanel = null;
_.tristan = null;
_.tristanPanel = null;
function $$init_20(this$static){
  this$static.lastPosition = initValues('[I', 162, (-1), [0, 0]);
  this$static.popup = $PopupPanel_0(new PopupPanel(), true);
  this$static.html = $HTML_0(new HTML(), 'X,Y');
  this$static.currentFillColor = ($clinit_128() , WHITE);
  this$static.currentStrokeColor = ($clinit_128() , BLACK);
  this$static.lastStrokeColor = this$static.currentStrokeColor;
  this$static.lastStrokeSize = this$static.currentStrokeSize;
}

function $GfxDemo(this$static){
  $$init_20(this$static);
  $initComponents_2(this$static);
  $initWidget(this$static, this$static.panel);
  return this$static;
}

function $addToGrid(this$static, grid, row, col, title, icon){
  var button;
  button = $Image(new Image_0(), icon);
  button.setTitle(title);
  $setSize(button, '29px', '29px');
  $setWidget_0(grid, row, col, button);
  $addClickListener(button, this$static);
  return button;
}

function $chooseStrokeSize(this$static, index, size){
  var i;
  for (i = 0; i < this$static.strokeSize.length_0; i++) {
    if (i == index) {
      setStyleAttribute(this$static.strokeSize[i].getElement(), 'borderColor', 'red');
      this$static.currentStrokeSize = size;
    }
     else {
      setStyleAttribute(this$static.strokeSize[i].getElement(), 'borderColor', 'black');
    }
  }
}

function $createImagePattern(this$static, url){
  var image;
  image = $Image(new Image_0(), url);
  $setSize(image, '32px', '32px');
  $addClickListener(image, $GfxDemo$5(new GfxDemo$5(), this$static, url));
  return image;
}

function $createSrokeSize(this$static, size){
  var strokeSize;
  strokeSize = $HTML_0(new HTML(), '&nbsp;&nbsp;&nbsp;');
  $setSize(strokeSize, '32px', '8px');
  strokeSize.setTitle('Size of stroke ' + size);
  setStyleAttribute(strokeSize.getElement(), 'borderTop', 'solid');
  setStyleAttribute(strokeSize.getElement(), 'borderWidth', '' + size);
  $add_6(this$static.buttonPanel, strokeSize);
  this$static.buttonPanel.setCellHorizontalAlignment(strokeSize, ($clinit_53() , ALIGN_CENTER));
  $addClickListener_0(strokeSize, this$static);
  return strokeSize;
}

function $initComponents_2(this$static){
  this$static.panel = $DockPanel(new DockPanel());
  this$static.canvas_0 = $GraphicCanvas(new GraphicCanvas());
  $setStyleName(this$static.canvas_0, 'GfxDemo-canvas');
  this$static.buttonPanel = $VerticalPanel(new VerticalPanel());
  $setSpacing(this$static.buttonPanel, 10);
  $add_4(this$static.popup, this$static.html);
  this$static.gridShape = $Grid_0(new Grid(), 6, 2);
  $setCellSpacing(this$static.gridShape, 5);
  $setCellPadding(this$static.gridShape, 5);
  this$static.gridTransform = $Grid_0(new Grid(), 3, 2);
  $setCellSpacing(this$static.gridTransform, 5);
  $setCellPadding(this$static.gridTransform, 5);
  $setPixelSize(this$static.canvas_0, 600, 600);
  this$static.fill = $HTML_0(new HTML(), '&nbsp;&nbsp;&nbsp;');
  setStyleAttribute(this$static.fill.getElement(), 'backgroundColor', $toHex(this$static.currentFillColor));
  setStyleAttribute(this$static.fill.getElement(), 'border', 'solid');
  setStyleAttribute(this$static.fill.getElement(), 'borderWidth', 'thin');
  setStyleAttribute(this$static.fill.getElement(), 'borderColor', $toHex(this$static.currentStrokeColor));
  $setSize(this$static.fill, '25px', '25px');
  $addClickListener_0(this$static.fill, this$static);
  $add_6(this$static.buttonPanel, this$static.gridShape);
  $add_6(this$static.buttonPanel, this$static.fill);
  this$static.buttonPanel.setCellHorizontalAlignment(this$static.fill, ($clinit_53() , ALIGN_CENTER));
  this$static.strokeSize = initDims_0('[Lcom.google.gwt.user.client.ui.HTML;', [163], [15], [4], null);
  this$static.strokeSize[0] = $createSrokeSize(this$static, 1);
  this$static.strokeSize[1] = $createSrokeSize(this$static, 2);
  this$static.strokeSize[2] = $createSrokeSize(this$static, 3);
  this$static.strokeSize[3] = $createSrokeSize(this$static, 5);
  $add_6(this$static.buttonPanel, this$static.gridTransform);
  this$static.opacity = $Slider(new Slider(), 'horizontal', 0, 255, 255, true);
  $addChangeListener(this$static.opacity, this$static);
  this$static.circleButton = $addToGrid(this$static, this$static.gridShape, 0, 0, 'Circle', 'gfx/circle.gif');
  this$static.ellipseButton = $addToGrid(this$static, this$static.gridShape, 0, 1, 'Ellipse', 'gfx/ellipse.gif');
  this$static.rectButton = $addToGrid(this$static, this$static.gridShape, 1, 0, 'Rect', 'gfx/rect.gif');
  this$static.lineButton = $addToGrid(this$static, this$static.gridShape, 1, 1, 'Line', 'gfx/line.gif');
  this$static.polylineButton = $addToGrid(this$static, this$static.gridShape, 2, 0, 'Polyline', 'gfx/polyline.gif');
  this$static.textButton = $addToGrid(this$static, this$static.gridShape, 2, 1, 'Text', 'gfx/text.gif');
  this$static.imageButton = $addToGrid(this$static, this$static.gridShape, 3, 0, 'Image', 'gfx/image.gif');
  this$static.pathButton = $addToGrid(this$static, this$static.gridShape, 3, 1, 'Path', 'gfx/path.GIF');
  this$static.textPathButton = $addToGrid(this$static, this$static.gridShape, 4, 0, 'Text Path', 'gfx/textpath.gif');
  this$static.virtualButton = $addToGrid(this$static, this$static.gridShape, 4, 1, 'Virtual', 'gfx/group.gif');
  this$static.deleteButton = $addToGrid(this$static, this$static.gridShape, 5, 0, 'Delete', 'gfx/delete.gif');
  this$static.colorButton = $addToGrid(this$static, this$static.gridTransform, 0, 0, 'set color', 'gfx/color.gif');
  this$static.scaleButton = $addToGrid(this$static, this$static.gridTransform, 0, 1, 'Scale', 'gfx/scale.gif');
  this$static.rotateButton = $addToGrid(this$static, this$static.gridTransform, 1, 0, 'Rotate', 'gfx/rotate.gif');
  this$static.backButton = $addToGrid(this$static, this$static.gridTransform, 1, 1, 'Move to back', 'gfx/back.gif');
  this$static.frontButton = $addToGrid(this$static, this$static.gridTransform, 2, 0, 'Move to front', 'gfx/front.gif');
  this$static.propertiesButton = $addToGrid(this$static, this$static.gridTransform, 2, 1, 'Properties', 'gfx/properties.gif');
  $add_2(this$static.panel, this$static.canvas_0, ($clinit_36() , CENTER));
  $add_2(this$static.panel, this$static.buttonPanel, ($clinit_36() , WEST));
  $addGraphicObjectListener(this$static.canvas_0, this$static);
}

function $selectObject(this$static, object){
  if (this$static.current !== null) {
    this$static.current.setStroke_0(this$static.lastStrokeColor, this$static.lastStrokeSize);
  }
  this$static.current = object;
  this$static.lastStrokeColor = this$static.current.strokeColor;
  this$static.lastStrokeSize = this$static.current.strokeWidth;
  this$static.current.setStroke_0(($clinit_128() , RED), 2);
  $removeChangeListener(this$static.opacity, this$static);
  $setValue(this$static.opacity, this$static.current.fillColor.alpha);
  $addChangeListener(this$static.opacity, this$static);
}

function $showGraphicObject(this$static, object, x, y){
  object.setFillColor(this$static.currentFillColor);
  object.setStroke_0(this$static.currentStrokeColor, this$static.currentStrokeSize);
  $add_10(this$static.canvas_0, object, x, y);
}

function $showPath(this$static){
  var p1, p2, p3, p4, t;
  p1 = $Point_1(new Point(), 50, 50);
  p2 = $Point_1(new Point(), 80, 50);
  p3 = $Point_1(new Point(), 50, 100);
  p4 = $Point_1(new Point(), 80, 100);
  t = $Path(new Path());
  t.setFillColor(this$static.currentFillColor);
  t.setStroke_0(this$static.currentStrokeColor, this$static.currentStrokeSize);
  $moveTo_1(t, p1);
  $lineTo_1(t, p2);
  $lineTo_1(t, p3);
  $lineTo_1(t, p4);
  $lineTo_1(t, p1);
  $moveTo(t, (p1.x_0 + p4.x_0) / 2, (p1.y_0 + p4.y_0) / 2);
  $lineTo(t, (p2.x_0 + p3.x_0) / 2, (p2.y_0 + p3.y_0) / 2);
  $moveTo(t, (p1.x_0 + p2.x_0) / 2, (p1.y_0 + p2.y_0) / 2);
  $arcTo_0(t, 20, 30, 35, true, true, p3);
  $lineTo(t, (p3.x_0 + p4.x_0) / 2, (p3.y_0 + p4.y_0) / 2);
  $add_10(this$static.canvas_0, t, 60, 100);
  $setPixelSize(this$static.canvas_0, 600, 600);
}

function $showPolyline(this$static){
  var arrow, poly;
  arrow = initDims_0('[Lcom.objetdirect.tatami.client.gfx.Point;', [164], [40], [8], null);
  arrow[0] = $Point_1(new Point(), (-2), 15);
  arrow[1] = $Point_1(new Point(), 2, 15);
  arrow[2] = $Point_1(new Point(), 2, (-105));
  arrow[3] = $Point_1(new Point(), 6, (-105));
  arrow[4] = $Point_1(new Point(), 0, (-116));
  arrow[5] = $Point_1(new Point(), (-6), (-105));
  arrow[6] = $Point_1(new Point(), (-2), (-105));
  arrow[7] = $Point_1(new Point(), (-2), 15);
  poly = $Polyline(new Polyline(), arrow);
  $showGraphicObject(this$static, poly, 300, 300);
}

function $showPopupColor(this$static){
  var checkFill, colPanel, colorChange, colorChooser, grid, popupColor, tabPanel;
  popupColor = $PopupPanel_0(new PopupPanel(), true);
  $addStyleName(popupColor, 'GfxDemo-popupColor');
  tabPanel = $TabPanel(new TabPanel());
  colPanel = $VerticalPanel(new VerticalPanel());
  $setSpacing(colPanel, 5);
  checkFill = $CheckBox_1(new CheckBox(), 'Background');
  $setChecked(checkFill, true);
  $add_6(colPanel, checkFill);
  colorChooser = $ColorChooser(new ColorChooser());
  $add_6(colPanel, colorChooser);
  $add_5(tabPanel, colPanel, $Label_0(new Label(), 'Color'));
  colorChange = $GfxDemo$4(new GfxDemo$4(), this$static, colorChooser, checkFill);
  $addChangeListener(colorChooser, colorChange);
  grid = $Grid_0(new Grid(), 2, 3);
  $setCellSpacing(grid, 10);
  $setCellPadding(grid, 10);
  $setWidget_0(grid, 0, 0, $createImagePattern(this$static, 'gfx/none.gif'));
  $setWidget_0(grid, 0, 1, $createImagePattern(this$static, 'littleNero.png'));
  $setWidget_0(grid, 0, 2, $createImagePattern(this$static, 'littleTrajan.png'));
  $setWidget_0(grid, 1, 0, $createImagePattern(this$static, 'cubic.jpg'));
  $setWidget_0(grid, 1, 1, $createImagePattern(this$static, 'logo_ft.gif'));
  $setWidget_0(grid, 1, 2, $createImagePattern(this$static, 'od-logo.jpg'));
  $add_5(tabPanel, grid, $Label_0(new Label(), 'Pattern'));
  $add_5(tabPanel, this$static.opacity, $Label_0(new Label(), 'Opacity'));
  $selectTab_0(tabPanel, 0);
  $add_4(popupColor, tabPanel);
  $setPopupPosition(popupColor, $getAbsoluteLeft_0(this$static.colorButton), $getAbsoluteTop_0(this$static.colorButton));
  $show(popupColor);
}

function $showPopupRotate(this$static){
  var label, popupRotate, rotate, rotateChange, rotatePanel;
  if (this$static.current !== null) {
    this$static.rotateDegree = 0;
    popupRotate = $PopupPanel_0(new PopupPanel(), true);
    $addStyleName(popupRotate, 'GfxDemo-popup');
    rotate = $Slider(new Slider(), 'horizontal', 0, 360, 0, true);
    rotatePanel = $HorizontalPanel(new HorizontalPanel());
    $setSpacing(rotatePanel, 5);
    label = $Label(new Label());
    $add_3(rotatePanel, rotate);
    $add_3(rotatePanel, label);
    $setText_0(label, '' + this$static.rotateDegree);
    rotateChange = $GfxDemo$3(new GfxDemo$3(), this$static, rotate, label);
    $addChangeListener(rotate, rotateChange);
    $add_4(popupRotate, rotatePanel);
    $setPopupPosition(popupRotate, $getAbsoluteLeft_0(this$static.rotateButton), $getAbsoluteTop_0(this$static.rotateButton));
    $show(popupRotate);
  }
}

function $showPopupScaler(this$static){
  var labelScaler, popupScaler, scaleChange, scalePanel, scaler;
  if (this$static.current !== null) {
    this$static.scaleFactor = 1;
    popupScaler = $PopupPanel_0(new PopupPanel(), true);
    $addStyleName(popupScaler, 'GfxDemo-popup');
    scaler = $Slider(new Slider(), 'horizontal', (-10), 10, 1, true);
    $setRuleBottom(scaler, 6, '3px');
    scalePanel = $HorizontalPanel(new HorizontalPanel());
    $setSpacing(scalePanel, 5);
    labelScaler = $Label(new Label());
    $add_3(scalePanel, scaler);
    $add_3(scalePanel, labelScaler);
    $setText_0(labelScaler, 'x' + this$static.scaleFactor);
    scaleChange = $GfxDemo$2(new GfxDemo$2(), this$static, scaler, labelScaler);
    $addChangeListener(scaler, scaleChange);
    $add_4(popupScaler, scalePanel);
    $setPopupPosition(popupScaler, $getAbsoluteLeft_0(this$static.scaleButton), $getAbsoluteTop_0(this$static.scaleButton));
    $show(popupScaler);
  }
}

function $showProperties(this$static, object){
  var close, color, dialog, fillColor, label, labelFill, panel;
  dialog = $DialogBox(new DialogBox(), false);
  panel = $Grid_0(new Grid(), 5, 4);
  $setCellPadding(panel, 5);
  $setCellSpacing(panel, 10);
  $setWidget_0(panel, 0, 0, $HTML_0(new HTML(), '<b>Position<\/b>'));
  $setWidget_0(panel, 0, 1, $Label_0(new Label(), object.position.x_0 + ',' + object.position.y_0));
  $setWidget_0(panel, 0, 2, $HTML_0(new HTML(), '<b>Center<\/b>'));
  $setWidget_0(panel, 0, 3, $Label_0(new Label(), object.center.x_0 + ',' + object.center.y_0));
  $setWidget_0(panel, 1, 0, $HTML_0(new HTML(), '<b>Size<\/b>'));
  $setWidget_0(panel, 1, 1, $Label_0(new Label(), '? x ? px'));
  $setWidget_0(panel, 2, 0, $HTML_0(new HTML(), '<b>Color of the stroke<\/b>'));
  color = $toHex(this$static.lastStrokeColor);
  label = $Label_0(new Label(), color);
  label.setTitle(color);
  setStyleAttribute(label.getElement(), 'color', color);
  $setWidget_0(panel, 2, 1, label);
  $setWidget_0(panel, 2, 2, $HTML_0(new HTML(), '<b>Size of the stroke<\/b>'));
  $setWidget_0(panel, 2, 3, $Label_0(new Label(), this$static.lastStrokeSize + 'px'));
  $setWidget_0(panel, 3, 0, $HTML_0(new HTML(), '<b>Fill color<\/b>'));
  fillColor = $toHex(object.fillColor);
  labelFill = $Label_0(new Label(), fillColor);
  labelFill.setTitle(fillColor);
  $setWidget_0(panel, 3, 1, labelFill);
  setStyleAttribute(labelFill.getElement(), 'color', fillColor);
  close = $Button_0(new Button(), 'Close');
  close.addClickListener($GfxDemo$1(new GfxDemo$1(), this$static, dialog));
  $setWidget_0(panel, 4, 0, close);
  $setPopupPosition(dialog, round_int(getClientWidth() / 2), round_int(getClientHeight() / 2));
  $addStyleName(dialog, 'GfxDemo-properties');
  $setWidget(dialog, panel);
  $show(dialog);
  $clinit_183() , out_0 , 'bounds ' + object.getBounds();
}

function $showText(this$static){
  var font, font2, font3, font4, text, text2, text3, text4;
  text = $Text_0(new Text(), 'Tatami GFX,\ncourier 10', 'underline');
  font = $Font(new Font(), 'Courier', 10, 'normal', 'normal', 'normal');
  text2 = $Text_0(new Text(), 'Tatami GFX, courier bolder 10', 'overline');
  font2 = $Font(new Font(), 'Courier', 10, 'normal', 'normal', 'bolder');
  text3 = $Text_0(new Text(), 'Tatami GFX, courier lighter 10', 'line-through');
  font3 = $Font(new Font(), 'Courier', 10, 'normal', 'normal', 'lighter');
  text4 = $Text(new Text(), 'Tatami GFX Arial 24 Bold');
  font4 = $Font(new Font(), 'Arial', 24, 'italic', 'normal', 'bold');
  $setFont_2(text, font);
  $setFont_2(text3, font3);
  $setFont_2(text2, font2);
  $showGraphicObject(this$static, text, 10, 20);
  $showGraphicObject(this$static, text2, 10, 40);
  $showGraphicObject(this$static, text3, 10, 60);
  $showGraphicObject(this$static, text4, 10, 100);
  $setFont_2(text4, font4);
}

function $showTextPath(this$static){
  var CPD, textPath, times;
  textPath = $TextPath(new TextPath(), 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Praesent erat.In malesuada ultricies velit. Vestibulum tempor odio vitae diam. Morbi arcu lectus, laoreet eget, nonummy at, elementum a, quam.');
  $showGraphicObject(this$static, textPath, 10, 10);
  CPD = 30;
  times = $Font(new Font(), 'times', 12, 'normal', 'normal', 'normal');
  $setFont_0(textPath, times);
  $moveTo(textPath, 0, 100);
  $setAbsoluteMode_0(textPath, false);
  $curveTo(textPath, CPD, 0, 100 - CPD, 300, 100, 300);
  $curveTo(textPath, CPD, 0, 100 - CPD, (-300), 100, (-300));
  $curveTo(textPath, CPD, 0, 100 - CPD, 300, 100, 300);
  $curveTo(textPath, CPD, 0, 100 - CPD, (-300), 100, (-300));
  $curveTo(textPath, CPD, 0, 100 - CPD, 300, 100, 300);
}

function $showVirtual(this$static){
  var c, r, virtual;
  virtual = $VirtualGroup(new VirtualGroup());
  r = $Rect(new Rect(), 100, 20);
  c = $Circle(new Circle(), 20);
  $translate(c, 0, 15);
  $translate(r, 0, 25);
  $add_12(virtual, c);
  $add_12(virtual, r);
  $showGraphicObject(this$static, virtual, 300, 300);
}

function $unSelectAll(this$static){
  if (this$static.current !== null) {
    this$static.current.setStroke_0(this$static.lastStrokeColor, this$static.lastStrokeSize);
  }
  this$static.current = null;
}

function mouseClicked(graphicObject, evt){
  if (graphicObject === null) {
    $unSelectAll(this);
  }
}

function mouseDblClicked(graphicObject, evt){
  if (graphicObject !== null) {
    $showProperties(this, graphicObject);
  }
}

function mouseMoved(graphicObject, evt){
  var color, newX, newY, object, reverseColor, x, y;
  if (this.current !== null) {
    x = eventGetClientX(evt);
    y = eventGetClientY(evt);
    newX = x - $getAbsoluteLeft_0(this.canvas_0) + getScrollLeft();
    newY = y - $getAbsoluteTop_0(this.canvas_0) + getScrollTop();
    $setHTML(this.html, newX + ',' + newY + ' [' + x + ',' + y + ']');
    $setPopupPosition(this.popup, 10 + newX + $getAbsoluteLeft_0(this.canvas_0), newY + $getAbsoluteTop_0(this.canvas_0));
    color = this.current.fillColor;
    reverseColor = $reverse(color);
    $setAlpha(reverseColor, 255);
    setStyleAttribute(this.html.getElement(), 'color', $toHex(reverseColor));
    $show(this.popup);
    if (this.movable) {
      if (this.current.groupParent !== null) {
        object = this.current.groupParent;
        $translate(object, newX - this.lastPosition[0], newY - this.lastPosition[1]);
      }
       else {
        $translate(this.current, newX - this.lastPosition[0], newY - this.lastPosition[1]);
      }
      this.lastPosition[0] = newX;
      this.lastPosition[1] = newY;
    }
  }
   else {
    $hide(this.popup);
  }
}

function mousePressed(graphicObject, evt){
  var x, y;
  if (graphicObject !== null) {
    x = eventGetClientX(evt);
    y = eventGetClientY(evt);
    $selectObject(this, graphicObject);
    this.movable = true;
    this.lastPosition[0] = x - $getAbsoluteLeft_0(this.canvas_0) + getScrollLeft();
    this.lastPosition[1] = y - $getAbsoluteTop_0(this.canvas_0) + getScrollTop();
  }
}

function mouseReleased(graphicObject, evt){
  this.movable = false;
}

function onChange_5(sender){
  var color, newColor, value;
  if (sender.equals$(this.opacity)) {
    if (this.current !== null) {
      value = this.opacity.value;
      color = this.current.fillColor;
      newColor = $Color(new Color(), color.red, color.green, color.blue, value);
      this.current.setFillColor(newColor);
    }
  }
}

function onClick_2(sender){
  var img, line, pointA, pointB, rect;
  if (sender.equals$(this.rectButton)) {
    rect = $Rect(new Rect(), 300, 100);
    $showGraphicObject(this, rect, 300, 300);
    $setWidth_1(rect, 100);
  }
   else if (sender.equals$(this.circleButton)) {
    $showGraphicObject(this, $Circle(new Circle(), 50), 300, 300);
  }
   else if (sender.equals$(this.colorButton)) {
    $showPopupColor(this);
  }
   else if (sender.equals$(this.textButton)) {
    $showText(this);
  }
   else if (sender.equals$(this.scaleButton)) {
    $showPopupScaler(this);
  }
   else if (sender.equals$(this.rotateButton)) {
    $showPopupRotate(this);
  }
   else if (sender.equals$(this.imageButton)) {
    img = $ImageGfx(new ImageGfx(), 'od-logo.jpg', 105, 52);
    $showGraphicObject(this, img, 300, 300);
  }
   else if (sender.equals$(this.ellipseButton)) {
    $showGraphicObject(this, $Ellipse(new Ellipse(), 200, 100), 300, 300);
  }
   else if (sender.equals$(this.strokeSize[0])) {
    $chooseStrokeSize(this, 0, 1);
  }
   else if (sender.equals$(this.strokeSize[1])) {
    $chooseStrokeSize(this, 1, 2);
  }
   else if (sender.equals$(this.strokeSize[2])) {
    $chooseStrokeSize(this, 2, 3);
  }
   else if (sender.equals$(this.strokeSize[3])) {
    $chooseStrokeSize(this, 3, 5);
  }
   else if (sender.equals$(this.propertiesButton) && this.current !== null) {
    $showProperties(this, this.current);
  }
   else if (sender.equals$(this.frontButton) && this.current !== null) {
    $moveToFront(this.current);
  }
   else if (sender.equals$(this.backButton) && this.current !== null) {
    $moveToBack(this.current);
  }
   else if (sender.equals$(this.deleteButton) && this.current !== null) {
    $remove_7(this.canvas_0, this.current);
  }
   else if (sender.equals$(this.lineButton)) {
    pointA = $Point_1(new Point(), 50, 50);
    pointB = $Point_1(new Point(), 200, 360);
    line = $Line(new Line(), pointA, pointB);
    $setStrokeStyle(line, 'LongDash');
    $showGraphicObject(this, line, 300, 300);
  }
   else if (sender.equals$(this.pathButton)) {
    $showPath(this);
  }
   else if (sender.equals$(this.virtualButton)) {
    $showVirtual(this);
  }
   else if (sender.equals$(this.textPathButton)) {
    $showTextPath(this);
  }
   else if (sender.equals$(this.polylineButton)) {
    $showPolyline(this);
  }
}

function GfxDemo(){
}

_ = GfxDemo.prototype = new Composite();
_.mouseClicked = mouseClicked;
_.mouseDblClicked = mouseDblClicked;
_.mouseMoved = mouseMoved;
_.mousePressed = mousePressed;
_.mouseReleased = mouseReleased;
_.onChange_0 = onChange_5;
_.onClick = onClick_2;
_.typeName$ = package_com_objetdirect_tatami_demo_client_ + 'GfxDemo';
_.typeId$ = 124;
_.backButton = null;
_.buttonPanel = null;
_.canvas_0 = null;
_.circleButton = null;
_.colorButton = null;
_.current = null;
_.currentStrokeSize = 1;
_.deleteButton = null;
_.ellipseButton = null;
_.fill = null;
_.frontButton = null;
_.gridShape = null;
_.gridTransform = null;
_.imageButton = null;
_.lineButton = null;
_.movable = false;
_.opacity = null;
_.panel = null;
_.pathButton = null;
_.polylineButton = null;
_.propertiesButton = null;
_.rectButton = null;
_.rotateButton = null;
_.rotateDegree = 0;
_.scaleButton = null;
_.scaleFactor = 1;
_.strokeSize = null;
_.textButton = null;
_.textPathButton = null;
_.virtualButton = null;
function $GfxDemo$1(this$static, this$0, val$dialog){
  this$static.val$dialog = val$dialog;
  return this$static;
}

function onClick_0(sender){
  $hide(this.val$dialog);
}

function GfxDemo$1(){
}

_ = GfxDemo$1.prototype = new Object_0();
_.onClick = onClick_0;
_.typeName$ = package_com_objetdirect_tatami_demo_client_ + 'GfxDemo$1';
_.typeId$ = 125;
function $GfxDemo$2(this$static, this$0, val$scaler, val$labelScaler){
  this$static.this$0 = this$0;
  this$static.val$scaler = val$scaler;
  this$static.val$labelScaler = val$labelScaler;
  return this$static;
}

function onChange_2(sender){
  var factor, minus, value;
  value = this.val$scaler.value;
  if (this.this$0.current !== null && value != 0) {
    if (value < 0) {
      minus = (this.val$scaler.minimum - value) * (-1);
      factor = minus / this.val$scaler.maximum / this.this$0.scaleFactor;
      if (this.this$0.current.groupParent !== null) {
        $scale(this.this$0.current.groupParent, factor);
      }
       else {
        $scale(this.this$0.current, factor);
      }
      this.this$0.scaleFactor = minus / this.val$scaler.maximum;
    }
     else {
      factor = value / this.this$0.scaleFactor;
      if (this.this$0.current.groupParent !== null) {
        $scale(this.this$0.current.groupParent, factor);
      }
       else {
        $scale(this.this$0.current, factor);
      }
      this.this$0.scaleFactor = value;
    }
  }
  $setText_0(this.val$labelScaler, 'x' + this.this$0.scaleFactor);
}

function GfxDemo$2(){
}

_ = GfxDemo$2.prototype = new Object_0();
_.onChange_0 = onChange_2;
_.typeName$ = package_com_objetdirect_tatami_demo_client_ + 'GfxDemo$2';
_.typeId$ = 126;
function $GfxDemo$3(this$static, this$0, val$rotate, val$label){
  this$static.this$0 = this$0;
  this$static.val$rotate = val$rotate;
  this$static.val$label = val$label;
  return this$static;
}

function onChange_3(sender){
  var degree, value;
  value = this.val$rotate.value;
  if (this.this$0.current !== null && value != 0) {
    degree = value - this.this$0.rotateDegree;
    if (this.this$0.current.groupParent !== null) {
      $rotate(this.this$0.current.groupParent, degree);
    }
     else {
      $rotate(this.this$0.current, degree);
    }
    this.this$0.rotateDegree = value;
  }
  $setText_0(this.val$label, this.this$0.rotateDegree + '');
}

function GfxDemo$3(){
}

_ = GfxDemo$3.prototype = new Object_0();
_.onChange_0 = onChange_3;
_.typeName$ = package_com_objetdirect_tatami_demo_client_ + 'GfxDemo$3';
_.typeId$ = 127;
function $GfxDemo$4(this$static, this$0, val$colorChooser, val$checkFill){
  this$static.this$0 = this$0;
  this$static.val$colorChooser = val$colorChooser;
  this$static.val$checkFill = val$checkFill;
  return this$static;
}

function onChange_4(sender){
  var color, colorSelected;
  color = this.val$colorChooser.color;
  colorSelected = getColor(color);
  if ($isChecked(this.val$checkFill)) {
    this.this$0.currentFillColor = colorSelected;
    setStyleAttribute(this.this$0.fill.getElement(), 'backgroundColor', color);
    $setAlpha(this.this$0.currentFillColor, this.this$0.opacity.value);
    if (this.this$0.current !== null) {
      this.this$0.current.setFillColor(this.this$0.currentFillColor);
    }
  }
   else {
    this.this$0.currentStrokeColor = colorSelected;
    this.this$0.lastStrokeColor = this.this$0.currentStrokeColor;
    setStyleAttribute(this.this$0.fill.getElement(), 'borderColor', color);
    if (this.this$0.current !== null) {
      $clinit_183() , out_0;
      this.this$0.current.setStroke_0(this.this$0.currentStrokeColor, 1);
    }
  }
}

function GfxDemo$4(){
}

_ = GfxDemo$4.prototype = new Object_0();
_.onChange_0 = onChange_4;
_.typeName$ = package_com_objetdirect_tatami_demo_client_ + 'GfxDemo$4';
_.typeId$ = 128;
function $GfxDemo$5(this$static, this$0, val$url){
  this$static.this$0 = this$0;
  this$static.val$url = val$url;
  return this$static;
}

function onClick_1(sender){
  var pattern;
  if (this.this$0.current !== null) {
    pattern = null;
    if (this.val$url === 'gfx/none.gif') {
      pattern = ($clinit_138() , DEFAULT_PATTERN);
    }
     else {
      pattern = $Pattern(new Pattern(), $Image(new Image_0(), this.val$url), 0, 0);
    }
    this.this$0.current.applyPattern(pattern);
  }
}

function GfxDemo$5(){
}

_ = GfxDemo$5.prototype = new Object_0();
_.onClick = onClick_1;
_.typeName$ = package_com_objetdirect_tatami_demo_client_ + 'GfxDemo$5';
_.typeId$ = 129;
function $SliderDemo(this$static){
  $initComponents_3(this$static);
  $initWidget(this$static, this$static.panel);
  return this$static;
}

function $initComponents_3(this$static){
  var html, labels;
  this$static.panel = $DockPanel(new DockPanel());
  $setSpacing(this$static.panel, 10);
  html = $HTML_0(new HTML(), 'Move the cursor of each <b>Slider<\/b> to modify the size of the image below');
  this$static.verticalSlider = $Slider(new Slider(), 'vertical', 0, 100, 100, true);
  $setRuleLeft(this$static.verticalSlider, 6, '5px');
  $setRuleRight(this$static.verticalSlider, 12, '3px');
  labels = initValues('[Ljava.lang.String;', 165, 1, [' ', '20%', '40%', '60%', '80%', ' ']);
  $setLabelsLeft(this$static.verticalSlider, labels, 'margin: 0px -0.5em 0px -2em;color:gray');
  this$static.horizontalSlider = $Slider(new Slider(), 'horizontal', 0, 100, 100, true);
  $setRuleBottom(this$static.horizontalSlider, 6, '5px');
  $setLabelsTop(this$static.horizontalSlider, labels, 'margin: -0.5em 0px -3.5em 0px;color:gray');
  this$static.horizontalSlider.setWidth('205px');
  this$static.cubicImage = $Image(new Image_0(), 'cubic.jpg');
  $setStyleName(this$static.cubicImage, 'SliderDemo-image');
  $setSize(this$static.cubicImage, '200px', '200px');
  $addChangeListener(this$static.verticalSlider, this$static);
  $setValue(this$static.verticalSlider, 100);
  $setStylePrimaryName(this$static.verticalSlider, 'SliderDemo-vSlider');
  $addChangeListener(this$static.horizontalSlider, this$static);
  $add_2(this$static.panel, html, ($clinit_36() , NORTH));
  $add_2(this$static.panel, this$static.cubicImage, ($clinit_36() , CENTER));
  $setCellWidth(this$static.panel, this$static.cubicImage, '205px');
  $setCellHeight(this$static.panel, this$static.cubicImage, '205px');
  $add_2(this$static.panel, this$static.verticalSlider, ($clinit_36() , WEST));
  $setCellHorizontalAlignment_0(this$static.panel, this$static.verticalSlider, ($clinit_53() , ALIGN_RIGHT));
  $add_2(this$static.panel, this$static.horizontalSlider, ($clinit_36() , SOUTH));
  $setCellVerticalAlignment_0(this$static.panel, this$static.cubicImage, ($clinit_57() , ALIGN_MIDDLE));
  $setCellHorizontalAlignment_0(this$static.panel, this$static.horizontalSlider, ($clinit_53() , ALIGN_LEFT));
}

function onChange_6(sender){
  if (sender.equals$(this.verticalSlider) && this.verticalSlider.value != (-1)) {
    this.cubicImage.setHeight(this.verticalSlider.value * 2 + 'px');
  }
   else if (sender.equals$(this.horizontalSlider) && this.horizontalSlider.value != (-1)) {
    this.cubicImage.setWidth(this.horizontalSlider.value * 2 + 'px');
  }
}

function SliderDemo(){
}

_ = SliderDemo.prototype = new Composite();
_.onChange_0 = onChange_6;
_.typeName$ = package_com_objetdirect_tatami_demo_client_ + 'SliderDemo';
_.typeId$ = 130;
_.cubicImage = null;
_.horizontalSlider = null;
_.panel = null;
_.verticalSlider = null;
function $addItem(this$static, icon, page, title){
  $add_9(this$static.fishEye, icon, title, $TatamiDemo$DemoCommand(new TatamiDemo$DemoCommand(), icon, page, this$static));
}

function $getMessage(this$static, icon){
  return '<table><tr><td>Vous avez s&#233;lectionn&#233;: <\/td><td><img src="' + icon + '"><\/td><\/tr><\/table>';
}

function $initMenuPanel(this$static){
  var clock;
  this$static.menuPanel = $VerticalPanel(new VerticalPanel());
  this$static.fishEye = $FishEye_0(new FishEye(), 'vertical');
  this$static.labelMenu = $HTML_0(new HTML(), 'Menu');
  clock = $Clock(new Clock(), null, 77);
  $setStyleName(this$static.menuPanel, 'TatamiDemo-menu');
  $setSpacing(this$static.menuPanel, 20);
  $add_6(this$static.menuPanel, clock);
  $add_6(this$static.menuPanel, this$static.labelMenu);
  $add_6(this$static.menuPanel, this$static.fishEye);
  $addItem(this$static, 'browser.png', 3, 'sliders');
  $addItem(this$static, 'kalarm.png', 2, 'date-time');
  $addItem(this$static, 'icoColorPic.gif', 7, 'color tools');
  $addItem(this$static, 'amor.png', 6, "drap'n'drop");
  $addItem(this$static, 'blackboard.png', 1, 'GFX');
}

function $initTitlePanel(this$static){
  var logoFT, logoOD, title;
  this$static.titlePanel = $DockPanel(new DockPanel());
  logoOD = $Image(new Image_0(), 'od-logo.gif');
  $setStyleName(logoOD, 'TatamiDemo-logoOD');
  $setPixelSize(logoOD, 126, 64);
  $add_2(this$static.titlePanel, logoOD, ($clinit_36() , WEST));
  title = $HTML_0(new HTML(), 'Tatami version 1.1 (DOJO 1.0 wrapped)');
  $setStyleName(title, 'TatamiDemo-title');
  logoFT = $Image(new Image_0(), 'logo_ft.gif');
  $setStyleName(logoFT, 'TatamiDemo-logoFT');
  $add_2(this$static.titlePanel, title, ($clinit_36() , CENTER));
  $setCellWidth(this$static.titlePanel, title, '100%');
  $add_2(this$static.titlePanel, logoFT, ($clinit_36() , EAST));
}

function $loadPage(this$static){
  var widgetDemo;
  widgetDemo = null;
  switch (this$static.page) {
    default:case 3:
      {
        widgetDemo = $SliderDemo(new SliderDemo());
        break;
      }

    case 2:
      {
        widgetDemo = $DateTimeDemo(new DateTimeDemo());
        break;
      }

    case 6:
      {
        widgetDemo = $DragAndDropDemo(new DragAndDropDemo());
        break;
      }

    case 7:
      {
        widgetDemo = $ColorDemo(new ColorDemo());
        break;
      }

    case 1:
      {
        widgetDemo = $GfxDemo(new GfxDemo());
        break;
      }

  }
  if (widgetDemo !== null) {
    $add_2(this$static.mainPanel, widgetDemo, ($clinit_36() , CENTER));
  }
}

function $onModuleLoad(this$static){
  var body;
  body = $DockPanel(new DockPanel());
  this$static.root = $DockPanel(new DockPanel());
  this$static.mainPanel = $DockPanel(new DockPanel());
  $setStyleName(this$static.mainPanel, 'TatamiDemo-mainPanel');
  this$static.toaster = $Toaster(new Toaster(), 'message', 'bl-up');
  $add_2(body, this$static.root, ($clinit_36() , CENTER));
  $initMenuPanel(this$static);
  $initTitlePanel(this$static);
  this$static.welcome = $HTML(new HTML());
  $setStyleName(this$static.welcome, 'TatamiDemo-welcome');
  $setHTML(this$static.welcome, '<p>The project aims to integrate the Google Web Toolkit (GWT) and the DOJO framework. Indeed the DOJO framework is very rich in term of widgets and utilities (fisheye, slider, drag and drop functionality) and the main interest is to take benefits of the huge work which has been already done by the DOJO community. In other words, it means: <b>the DOJO widgets become GWT widgets, the DOJO utilities become GWT helper.<\/b><\/p><br><p> The project is on the Google code community : <a href="http://code.google.com/p/tatami">Tatami<\/a><\/p><p> Click on an item of the menu at the left to see the widgets that Tatami proposes.<\/p>');
  $add_2(this$static.mainPanel, this$static.welcome, ($clinit_36() , CENTER));
  $add_2(this$static.root, this$static.mainPanel, ($clinit_36() , CENTER));
  $setCellWidth(this$static.root, this$static.mainPanel, '100%');
  $add_2(this$static.root, this$static.menuPanel, ($clinit_36() , WEST));
  $setSpacing(this$static.root, 20);
  $add_2(body, this$static.titlePanel, ($clinit_36() , NORTH));
  $add_2(body, this$static.root, ($clinit_36() , CENTER));
  $add(get(), body);
  $add(get(), this$static.toaster);
}

function $setPage(this$static, page){
  $unLoadPage(this$static);
  this$static.page = page;
  $loadPage(this$static);
}

function $unLoadPage(this$static){
  this$static.mainPanel.clear();
}

function TatamiDemo(){
}

_ = TatamiDemo.prototype = new Object_0();
_.typeName$ = package_com_objetdirect_tatami_demo_client_ + 'TatamiDemo';
_.typeId$ = 131;
_.fishEye = null;
_.labelMenu = null;
_.mainPanel = null;
_.menuPanel = null;
_.page = 0;
_.root = null;
_.titlePanel = null;
_.toaster = null;
_.welcome = null;
function $TatamiDemo$DemoCommand(this$static, icon, page, this$0){
  this$static.this$0 = this$0;
  this$static.icon = icon;
  this$static.page = page;
  return this$static;
}

function $execute_0(this$static){
  publishMessage('message', $getMessage(this$static.this$0, this$static.icon));
  $setPage(this$static.this$0, this$static.page);
}

function TatamiDemo$DemoCommand(){
}

_ = TatamiDemo$DemoCommand.prototype = new Object_0();
_.typeName$ = package_com_objetdirect_tatami_demo_client_ + 'TatamiDemo$DemoCommand';
_.typeId$ = 132;
_.icon = '';
_.page = 0;
function OutputStream(){
}

_ = OutputStream.prototype = new Object_0();
_.typeName$ = package_java_io_ + 'OutputStream';
_.typeId$ = 133;
function FilterOutputStream(){
}

_ = FilterOutputStream.prototype = new OutputStream();
_.typeName$ = package_java_io_ + 'FilterOutputStream';
_.typeId$ = 134;
function PrintStream(){
}

_ = PrintStream.prototype = new FilterOutputStream();
_.typeName$ = package_java_io_ + 'PrintStream';
_.typeId$ = 135;
function ArrayStoreException(){
}

_ = ArrayStoreException.prototype = new RuntimeException();
_.typeName$ = package_java_lang_ + 'ArrayStoreException';
_.typeId$ = 136;
function $clinit_165(){
  $clinit_165 = nullMethod;
  FALSE = $Boolean(new Boolean_0(), false);
  TRUE = $Boolean(new Boolean_0(), true);
}

function $Boolean(this$static, value){
  $clinit_165();
  this$static.value = value;
  return this$static;
}

function equals_5(o){
  return instanceOf(o, 39) && dynamicCast(o, 39).value == this.value;
}

function hashCode_3(){
  var hashCodeForFalse, hashCodeForTrue;
  hashCodeForTrue = 1231;
  hashCodeForFalse = 1237;
  return this.value?1231:1237;
}

function toString_8(){
  return this.value?'true':'false';
}

function valueOf(b){
  $clinit_165();
  return b?TRUE:FALSE;
}

function Boolean_0(){
}

_ = Boolean_0.prototype = new Object_0();
_.equals$ = equals_5;
_.hashCode$ = hashCode_3;
_.toString$ = toString_8;
_.typeName$ = package_java_lang_ + 'Boolean';
_.typeId$ = 137;
_.value = false;
var FALSE, TRUE;
function ClassCastException(){
}

_ = ClassCastException.prototype = new RuntimeException();
_.typeName$ = package_java_lang_ + 'ClassCastException';
_.typeId$ = 138;
function $clinit_178(){
  $clinit_178 = nullMethod;
  {
    initNative();
  }
}

function $Number(this$static){
  $clinit_178();
  return this$static;
}

function initNative(){
  $clinit_178();
  floatRegex = /^[+-]?\d*\.?\d*(e[+-]?\d+)?$/i;
}

function Number_0(){
}

_ = Number_0.prototype = new Object_0();
_.typeName$ = package_java_lang_ + 'Number';
_.typeId$ = 139;
var floatRegex = null;
function $clinit_170(){
  $clinit_170 = nullMethod;
  $clinit_178();
}

function $Double(this$static, value){
  $clinit_170();
  $Number(this$static);
  this$static.value = value;
  return this$static;
}

function equals_6(o){
  return instanceOf(o, 37) && dynamicCast(o, 37).value == this.value;
}

function hashCode_4(){
  return round_int(this.value);
}

function toString_10(b){
  $clinit_170();
  return valueOf_0(b);
}

function toString_9(){
  return toString_10(this.value);
}

function Double(){
}

_ = Double.prototype = new Number_0();
_.equals$ = equals_6;
_.hashCode$ = hashCode_4;
_.toString$ = toString_9;
_.typeName$ = package_java_lang_ + 'Double';
_.typeId$ = 140;
_.value = 0.0;
function $IllegalArgumentException(this$static, message){
  $RuntimeException(this$static, message);
  return this$static;
}

function IllegalArgumentException(){
}

_ = IllegalArgumentException.prototype = new RuntimeException();
_.typeName$ = package_java_lang_ + 'IllegalArgumentException';
_.typeId$ = 141;
function $IllegalStateException(this$static, s){
  $RuntimeException(this$static, s);
  return this$static;
}

function IllegalStateException(){
}

_ = IllegalStateException.prototype = new RuntimeException();
_.typeName$ = package_java_lang_ + 'IllegalStateException';
_.typeId$ = 142;
function $IndexOutOfBoundsException(this$static, message){
  $RuntimeException(this$static, message);
  return this$static;
}

function IndexOutOfBoundsException(){
}

_ = IndexOutOfBoundsException.prototype = new RuntimeException();
_.typeName$ = package_java_lang_ + 'IndexOutOfBoundsException';
_.typeId$ = 143;
function $clinit_175(){
  $clinit_175 = nullMethod;
  $clinit_178();
}

function $Integer(this$static, value){
  $clinit_175();
  $Number(this$static);
  this$static.value = value;
  return this$static;
}

function equals_7(o){
  return instanceOf(o, 38) && dynamicCast(o, 38).value == this.value;
}

function hashCode_5(){
  return this.value;
}

function toString_12(b){
  $clinit_175();
  return valueOf_1(b);
}

function toString_11(){
  return toString_12(this.value);
}

function Integer(){
}

_ = Integer.prototype = new Number_0();
_.equals$ = equals_7;
_.hashCode$ = hashCode_5;
_.toString$ = toString_11;
_.typeName$ = package_java_lang_ + 'Integer';
_.typeId$ = 144;
_.value = 0;
var MAX_VALUE = 2147483647, MIN_VALUE = (-2147483648);
function cos(x){
  return Math.cos(x);
}

function max(x, y){
  return x > y?x:y;
}

function sin(x){
  return Math.sin(x);
}

function sqrt(x){
  return Math.sqrt(x);
}

function toRadians(x){
  return x * 0.017453292519943295;
}

function NegativeArraySizeException(){
}

_ = NegativeArraySizeException.prototype = new RuntimeException();
_.typeName$ = package_java_lang_ + 'NegativeArraySizeException';
_.typeId$ = 145;
function $charAt(this$static, index){
  return this$static.charCodeAt(index);
}

function $equals_1(this$static, other){
  if (!instanceOf(other, 1))
    return false;
  return __equals(this$static, other);
}

function $indexOf_0(this$static, str){
  return this$static.indexOf(str);
}

function $indexOf_1(this$static, str, startIndex){
  return this$static.indexOf(str, startIndex);
}

function $length(this$static){
  return this$static.length;
}

function $split(this$static, regex){
  return $split_0(this$static, regex, 0);
}

function $split_0(this$static, regex, maxMatch){
  var compiled = new RegExp(regex, 'g');
  var out = [];
  var count = 0;
  var trail = this$static;
  var lastTrail = null;
  while (true) {
    var matchObj = compiled.exec(trail);
    if (matchObj == null || (trail == '' || count == maxMatch - 1 && maxMatch > 0)) {
      out[count] = trail;
      break;
    }
     else {
      out[count] = trail.substring(0, matchObj.index);
      trail = trail.substring(matchObj.index + matchObj[0].length, trail.length);
      compiled.lastIndex = 0;
      if (lastTrail == trail) {
        out[count] = trail.substring(0, 1);
        trail = trail.substring(1);
      }
      lastTrail = trail;
      count++;
    }
  }
  if (maxMatch == 0) {
    for (var i = out.length - 1; i >= 0; i--) {
      if (out[i] != '') {
        out.splice(i + 1, out.length - (i + 1));
        break;
      }
    }
  }
  var jr = __createArray(out.length);
  var i = 0;
  for (i = 0; i < out.length; ++i) {
    jr[i] = out[i];
  }
  return jr;
}

function $substring(this$static, beginIndex){
  return this$static.substr(beginIndex, this$static.length - beginIndex);
}

function $substring_0(this$static, beginIndex, endIndex){
  return this$static.substr(beginIndex, endIndex - beginIndex);
}

function $trim(this$static){
  var r1 = this$static.replace(/^(\s*)/, '');
  var r2 = r1.replace(/\s*$/, '');
  return r2;
}

function __createArray(numElements){
  return initDims_0('[Ljava.lang.String;', [165], [1], [numElements], null);
}

function __equals(me, other){
  return String(me) == other;
}

function equals_9(other){
  return $equals_1(this, other);
}

function hashCode_7(){
  var hashCache = hashCache_0;
  if (!hashCache) {
    hashCache = hashCache_0 = {};
  }
  var key = ':' + this;
  var hashCode = hashCache[key];
  if (hashCode == null) {
    hashCode = 0;
    var n = this.length;
    var inc = n < 64?1:n / 32 | 0;
    for (var i = 0; i < n; i += inc) {
      hashCode <<= 1;
      hashCode += this.charCodeAt(i);
    }
    hashCode |= 0;
    hashCache[key] = hashCode;
  }
  return hashCode;
}

function toString_15(){
  return this;
}

function valueOf_0(x){
  return '' + x;
}

function valueOf_1(x){
  return '' + x;
}

function valueOf_2(x){
  return x !== null?x.toString$():'null';
}

_ = String.prototype;
_.equals$ = equals_9;
_.hashCode$ = hashCode_7;
_.toString$ = toString_15;
_.typeName$ = package_java_lang_ + 'String';
_.typeId$ = 2;
var hashCache_0 = null;
function $StringBuffer(this$static){
  $assign(this$static);
  return this$static;
}

function $append(this$static, toAppend){
  if (toAppend === null) {
    toAppend = 'null';
  }
  var last = this$static.js.length - 1;
  var lastLength = this$static.js[last].length;
  if (this$static.length > lastLength * lastLength) {
    this$static.js[last] = this$static.js[last] + toAppend;
  }
   else {
    this$static.js.push(toAppend);
  }
  this$static.length += toAppend.length;
  return this$static;
}

function $assign(this$static){
  $assign_0(this$static, '');
}

function $assign_0(this$static, s){
  this$static.js = [s];
  this$static.length = s.length;
}

function $toString_0(this$static){
  this$static.normalize();
  return this$static.js[0];
}

function normalize(){
  if (this.js.length > 1) {
    this.js = [this.js.join('')];
    this.length = this.js[0].length;
  }
}

function toString_14(){
  return $toString_0(this);
}

function StringBuffer(){
}

_ = StringBuffer.prototype = new Object_0();
_.normalize = normalize;
_.toString$ = toString_14;
_.typeName$ = package_java_lang_ + 'StringBuffer';
_.typeId$ = 146;
function $clinit_183(){
  $clinit_183 = nullMethod;
  out_0 = new PrintStream();
}

function identityHashCode(o){
  $clinit_183();
  return getHashCode_0(o);
}

var out_0;
function $UnsupportedOperationException(this$static, message){
  $RuntimeException(this$static, message);
  return this$static;
}

function UnsupportedOperationException(){
}

_ = UnsupportedOperationException.prototype = new RuntimeException();
_.typeName$ = package_java_lang_ + 'UnsupportedOperationException';
_.typeId$ = 147;
function $AbstractList$IteratorImpl(this$static, this$0){
  this$static.this$0 = this$0;
  return this$static;
}

function $hasNext_1(this$static){
  return this$static.i < this$static.this$0.size_0();
}

function $next_0(this$static){
  if (!$hasNext_1(this$static)) {
    throw new NoSuchElementException();
  }
  return this$static.this$0.get(this$static.last = this$static.i++);
}

function $remove_8(this$static){
  if (this$static.last < 0) {
    throw new IllegalStateException();
  }
  this$static.this$0.remove_0(this$static.last);
  this$static.i = this$static.last;
  this$static.last = (-1);
}

function hasNext_2(){
  return $hasNext_1(this);
}

function next_3(){
  return $next_0(this);
}

function remove_16(){
  $remove_8(this);
}

function AbstractList$IteratorImpl(){
}

_ = AbstractList$IteratorImpl.prototype = new Object_0();
_.hasNext = hasNext_2;
_.next_0 = next_3;
_.remove = remove_16;
_.typeName$ = package_java_util_ + 'AbstractList$IteratorImpl';
_.typeId$ = 148;
_.i = 0;
_.last = (-1);
function $implFindEntry(this$static, key, remove){
  var entry, iter, k;
  for (iter = $iterator_4(this$static.entrySet()); $hasNext_4(iter);) {
    entry = $next_3(iter);
    k = entry.getKey();
    if (key === null?k === null:key.equals$(k)) {
      if (remove) {
        $remove_11(iter);
      }
      return entry;
    }
  }
  return null;
}

function $keySet(this$static){
  var entrySet;
  entrySet = this$static.entrySet();
  return $AbstractMap$1(new AbstractMap$1(), this$static, entrySet);
}

function $values(this$static){
  var entrySet;
  entrySet = $entrySet(this$static);
  return $AbstractMap$3(new AbstractMap$3(), this$static, entrySet);
}

function containsKey(key){
  return $implFindEntry(this, key, false) !== null;
}

function equals_11(obj){
  var iter, key, keys, otherKeys, otherMap, otherValue, value;
  if (obj === this) {
    return true;
  }
  if (!instanceOf(obj, 47)) {
    return false;
  }
  otherMap = dynamicCast(obj, 47);
  keys = $keySet(this);
  otherKeys = otherMap.keySet();
  if (!$equals_2(keys, otherKeys)) {
    return false;
  }
  for (iter = $iterator_2(keys); $hasNext_2(iter);) {
    key = $next_1(iter);
    value = this.get_0(key);
    otherValue = otherMap.get_0(key);
    if (value === null?otherValue !== null:!value.equals$(otherValue)) {
      return false;
    }
  }
  return true;
}

function get_1(key){
  var entry;
  entry = $implFindEntry(this, key, false);
  return entry === null?null:entry.getValue_0();
}

function hashCode_9(){
  var entry, hashCode, iter;
  hashCode = 0;
  for (iter = $iterator_4(this.entrySet()); $hasNext_4(iter);) {
    entry = $next_3(iter);
    hashCode += entry.hashCode$();
  }
  return hashCode;
}

function keySet(){
  return $keySet(this);
}

function toString_18(){
  var comma, entry, iter, s;
  s = '{';
  comma = false;
  for (iter = $iterator_4(this.entrySet()); $hasNext_4(iter);) {
    entry = $next_3(iter);
    if (comma) {
      s += ', ';
    }
     else {
      comma = true;
    }
    s += valueOf_2(entry.getKey());
    s += '=';
    s += valueOf_2(entry.getValue_0());
  }
  return s + '}';
}

function AbstractMap(){
}

_ = AbstractMap.prototype = new Object_0();
_.containsKey = containsKey;
_.equals$ = equals_11;
_.get_0 = get_1;
_.hashCode$ = hashCode_9;
_.keySet = keySet;
_.toString$ = toString_18;
_.typeName$ = package_java_util_ + 'AbstractMap';
_.typeId$ = 149;
function $equals_2(this$static, o){
  var iter, other, otherItem;
  if (o === this$static) {
    return true;
  }
  if (!instanceOf(o, 29)) {
    return false;
  }
  other = dynamicCast(o, 29);
  if (other.size_0() != this$static.size_0()) {
    return false;
  }
  for (iter = other.iterator(); iter.hasNext();) {
    otherItem = iter.next_0();
    if (!this$static.contains(otherItem)) {
      return false;
    }
  }
  return true;
}

function equals_12(o){
  return $equals_2(this, o);
}

function hashCode_10(){
  var hashCode, iter, next;
  hashCode = 0;
  for (iter = this.iterator(); iter.hasNext();) {
    next = iter.next_0();
    if (next !== null) {
      hashCode += next.hashCode$();
    }
  }
  return hashCode;
}

function AbstractSet(){
}

_ = AbstractSet.prototype = new AbstractCollection();
_.equals$ = equals_12;
_.hashCode$ = hashCode_10;
_.typeName$ = package_java_util_ + 'AbstractSet';
_.typeId$ = 150;
function $AbstractMap$1(this$static, this$0, val$entrySet){
  this$static.this$0 = this$0;
  this$static.val$entrySet = val$entrySet;
  return this$static;
}

function $iterator_2(this$static){
  var outerIter;
  outerIter = $iterator_4(this$static.val$entrySet);
  return $AbstractMap$2(new AbstractMap$2(), this$static, outerIter);
}

function contains_0(key){
  return this.this$0.containsKey(key);
}

function iterator_4(){
  return $iterator_2(this);
}

function size_0(){
  return this.val$entrySet.this$0.size;
}

function AbstractMap$1(){
}

_ = AbstractMap$1.prototype = new AbstractSet();
_.contains = contains_0;
_.iterator = iterator_4;
_.size_0 = size_0;
_.typeName$ = package_java_util_ + 'AbstractMap$1';
_.typeId$ = 151;
function $AbstractMap$2(this$static, this$1, val$outerIter){
  this$static.val$outerIter = val$outerIter;
  return this$static;
}

function $hasNext_2(this$static){
  return $hasNext_4(this$static.val$outerIter);
}

function $next_1(this$static){
  var entry;
  entry = $next_3(this$static.val$outerIter);
  return entry.getKey();
}

function hasNext_3(){
  return $hasNext_2(this);
}

function next_4(){
  return $next_1(this);
}

function remove_18(){
  $remove_11(this.val$outerIter);
}

function AbstractMap$2(){
}

_ = AbstractMap$2.prototype = new Object_0();
_.hasNext = hasNext_3;
_.next_0 = next_4;
_.remove = remove_18;
_.typeName$ = package_java_util_ + 'AbstractMap$2';
_.typeId$ = 152;
function $AbstractMap$3(this$static, this$0, val$entrySet){
  this$static.this$0 = this$0;
  this$static.val$entrySet = val$entrySet;
  return this$static;
}

function $iterator_3(this$static){
  var outerIter;
  outerIter = $iterator_4(this$static.val$entrySet);
  return $AbstractMap$4(new AbstractMap$4(), this$static, outerIter);
}

function contains_1(value){
  return $containsValue(this.this$0, value);
}

function iterator_5(){
  return $iterator_3(this);
}

function size_1(){
  return this.val$entrySet.this$0.size;
}

function AbstractMap$3(){
}

_ = AbstractMap$3.prototype = new AbstractCollection();
_.contains = contains_1;
_.iterator = iterator_5;
_.size_0 = size_1;
_.typeName$ = package_java_util_ + 'AbstractMap$3';
_.typeId$ = 153;
function $AbstractMap$4(this$static, this$1, val$outerIter){
  this$static.val$outerIter = val$outerIter;
  return this$static;
}

function $hasNext_3(this$static){
  return $hasNext_4(this$static.val$outerIter);
}

function $next_2(this$static){
  var value;
  value = $next_3(this$static.val$outerIter).getValue_0();
  return value;
}

function hasNext_4(){
  return $hasNext_3(this);
}

function next_5(){
  return $next_2(this);
}

function remove_19(){
  $remove_11(this.val$outerIter);
}

function AbstractMap$4(){
}

_ = AbstractMap$4.prototype = new Object_0();
_.hasNext = hasNext_4;
_.next_0 = next_5;
_.remove = remove_19;
_.typeName$ = package_java_util_ + 'AbstractMap$4';
_.typeId$ = 154;
function $clinit_197(){
  $clinit_197 = nullMethod;
  DAYS = initValues('[Ljava.lang.String;', 165, 1, ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']);
  MONTHS = initValues('[Ljava.lang.String;', 165, 1, ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']);
}

function $Date(this$static){
  $clinit_197();
  $init_0(this$static);
  return this$static;
}

function $Date_0(this$static, date){
  $clinit_197();
  $init_1(this$static, date);
  return this$static;
}

function $equals_3(this$static, obj){
  return instanceOf(obj, 48) && $getTime(this$static) == $getTime(dynamicCast(obj, 48));
}

function $getHours(this$static){
  return this$static.jsdate.getHours();
}

function $getMinutes(this$static){
  return this$static.jsdate.getMinutes();
}

function $getSeconds(this$static){
  return this$static.jsdate.getSeconds();
}

function $getTime(this$static){
  return this$static.jsdate.getTime();
}

function $init_0(this$static){
  this$static.jsdate = new Date();
}

function $init_1(this$static, date){
  this$static.jsdate = new Date(date);
}

function $setSeconds(this$static, seconds){
  this$static.jsdate.setSeconds(seconds);
}

function dayToString(day){
  $clinit_197();
  return DAYS[day];
}

function equals_14(obj){
  return $equals_3(this, obj);
}

function hashCode_11(){
  return narrow_int($getTime(this) ^ $getTime(this) >>> 32);
}

function monthToString(month){
  $clinit_197();
  return MONTHS[month];
}

function padTwo_0(number){
  $clinit_197();
  if (number < 10) {
    return '0' + number;
  }
   else {
    return valueOf_1(number);
  }
}

function toString_19(){
  var d = this.jsdate;
  var padTwo = padTwo_0;
  var day = dayToString(this.jsdate.getDay());
  var month = monthToString(this.jsdate.getMonth());
  var offset = -d.getTimezoneOffset();
  var hourOffset = String(offset >= 0?'+' + Math.floor(offset / 60):Math.ceil(offset / 60));
  var minuteOffset = padTwo(Math.abs(offset) % 60);
  return day + ' ' + month + ' ' + padTwo(d.getDate()) + ' ' + padTwo(d.getHours()) + ':' + padTwo(d.getMinutes()) + ':' + padTwo(d.getSeconds()) + ' GMT' + hourOffset + minuteOffset + ' ' + d.getFullYear();
}

function Date_0(){
}

_ = Date_0.prototype = new Object_0();
_.equals$ = equals_14;
_.hashCode$ = hashCode_11;
_.toString$ = toString_19;
_.typeName$ = package_java_util_ + 'Date';
_.typeId$ = 155;
var DAYS, MONTHS;
function $clinit_202(){
  $clinit_202 = nullMethod;
  UNDEFINED = createUndefinedValue();
}

function $$init_22(this$static){
  {
    $clearImpl_0(this$static);
  }
}

function $HashMap(this$static){
  $clinit_202();
  $$init_22(this$static);
  return this$static;
}

function $clearImpl_0(this$static){
  this$static.hashCodeMap = createArray();
  this$static.stringMap = createObject();
  this$static.nullSlot = wrapJSO(UNDEFINED, JavaScriptObject);
  this$static.size = 0;
}

function $containsKey(this$static, key){
  if (instanceOf(key, 1)) {
    return getStringValue(this$static.stringMap, dynamicCast(key, 1)) !== UNDEFINED;
  }
   else if (key === null) {
    return this$static.nullSlot !== UNDEFINED;
  }
   else {
    return getHashValue(this$static.hashCodeMap, key, key.hashCode$()) !== UNDEFINED;
  }
}

function $containsValue(this$static, value){
  if (this$static.nullSlot !== UNDEFINED && equalsWithNullCheck(this$static.nullSlot, value)) {
    return true;
  }
   else if (containsStringValue(this$static.stringMap, value)) {
    return true;
  }
   else if (containsHashValue(this$static.hashCodeMap, value)) {
    return true;
  }
  return false;
}

function $entrySet(this$static){
  return $HashMap$EntrySet(new HashMap$EntrySet(), this$static);
}

function $get_1(this$static, key){
  var result;
  if (instanceOf(key, 1)) {
    result = getStringValue(this$static.stringMap, dynamicCast(key, 1));
  }
   else if (key === null) {
    result = this$static.nullSlot;
  }
   else {
    result = getHashValue(this$static.hashCodeMap, key, key.hashCode$());
  }
  return result === UNDEFINED?null:result;
}

function $isEmpty(this$static){
  return this$static.size == 0;
}

function $put_0(this$static, key, value){
  var previous;
  if (instanceOf(key, 1)) {
    previous = putStringValue(this$static.stringMap, dynamicCast(key, 1), value);
  }
   else if (key === null) {
    previous = this$static.nullSlot;
    this$static.nullSlot = value;
  }
   else {
    previous = putHashValue(this$static.hashCodeMap, key, value, key.hashCode$());
  }
  if (previous === UNDEFINED) {
    ++this$static.size;
    return null;
  }
   else {
    return previous;
  }
}

function $remove_12(this$static, key){
  var previous;
  if (instanceOf(key, 1)) {
    previous = removeStringValue(this$static.stringMap, dynamicCast(key, 1));
  }
   else if (key === null) {
    previous = this$static.nullSlot;
    this$static.nullSlot = wrapJSO(UNDEFINED, JavaScriptObject);
  }
   else {
    previous = removeHashValue(this$static.hashCodeMap, key, key.hashCode$());
  }
  if (previous === UNDEFINED) {
    return null;
  }
   else {
    --this$static.size;
    return previous;
  }
}

function addAllHashEntries(hashCodeMap, dest){
  $clinit_202();
  for (var hashCode in hashCodeMap) {
    if (hashCode == parseInt(hashCode)) {
      var array = hashCodeMap[hashCode];
      for (var i = 0, c = array.length; i < c; ++i) {
        dest.add_1(array[i]);
      }
    }
  }
}

function addAllStringEntries(stringMap, dest){
  $clinit_202();
  for (var key in stringMap) {
    if (key.charCodeAt(0) == 58) {
      var value = stringMap[key];
      var entry = create(key.substring(1), value);
      dest.add_1(entry);
    }
  }
}

function containsHashValue(hashCodeMap, value){
  $clinit_202();
  for (var hashCode in hashCodeMap) {
    if (hashCode == parseInt(hashCode)) {
      var array = hashCodeMap[hashCode];
      for (var i = 0, c = array.length; i < c; ++i) {
        var entry = array[i];
        var entryValue = entry.getValue_0();
        if (equalsWithNullCheck(value, entryValue)) {
          return true;
        }
      }
    }
  }
  return false;
}

function containsKey_0(key){
  return $containsKey(this, key);
}

function containsStringValue(stringMap, value){
  $clinit_202();
  for (var key in stringMap) {
    if (key.charCodeAt(0) == 58) {
      var entryValue = stringMap[key];
      if (equalsWithNullCheck(value, entryValue)) {
        return true;
      }
    }
  }
  return false;
}

function createUndefinedValue(){
  $clinit_202();
}

function entrySet_0(){
  return $entrySet(this);
}

function equalsWithNullCheck(a, b){
  $clinit_202();
  if (a === b) {
    return true;
  }
   else if (a === null) {
    return false;
  }
   else {
    return a.equals$(b);
  }
}

function get_3(key){
  return $get_1(this, key);
}

function getHashValue(hashCodeMap, key, hashCode){
  $clinit_202();
  var array = hashCodeMap[hashCode];
  if (array) {
    for (var i = 0, c = array.length; i < c; ++i) {
      var entry = array[i];
      var entryKey = entry.getKey();
      if (equalsWithNullCheck(key, entryKey)) {
        return entry.getValue_0();
      }
    }
  }
}

function getStringValue(stringMap, key){
  $clinit_202();
  return stringMap[':' + key];
}

function putHashValue(hashCodeMap, key, value, hashCode){
  $clinit_202();
  var array = hashCodeMap[hashCode];
  if (array) {
    for (var i = 0, c = array.length; i < c; ++i) {
      var entry = array[i];
      var entryKey = entry.getKey();
      if (equalsWithNullCheck(key, entryKey)) {
        var previous = entry.getValue_0();
        entry.setValue_0(value);
        return previous;
      }
    }
  }
   else {
    array = hashCodeMap[hashCode] = [];
  }
  var entry = create(key, value);
  array.push(entry);
}

function putStringValue(stringMap, key, value){
  $clinit_202();
  key = ':' + key;
  var result = stringMap[key];
  stringMap[key] = value;
  return result;
}

function removeHashValue(hashCodeMap, key, hashCode){
  $clinit_202();
  var array = hashCodeMap[hashCode];
  if (array) {
    for (var i = 0, c = array.length; i < c; ++i) {
      var entry = array[i];
      var entryKey = entry.getKey();
      if (equalsWithNullCheck(key, entryKey)) {
        if (array.length == 1) {
          delete hashCodeMap[hashCode];
        }
         else {
          array.splice(i, 1);
        }
        return entry.getValue_0();
      }
    }
  }
}

function removeStringValue(stringMap, key){
  $clinit_202();
  key = ':' + key;
  var result = stringMap[key];
  delete stringMap[key];
  return result;
}

function HashMap(){
}

_ = HashMap.prototype = new AbstractMap();
_.containsKey = containsKey_0;
_.entrySet = entrySet_0;
_.get_0 = get_3;
_.typeName$ = package_java_util_ + 'HashMap';
_.typeId$ = 156;
_.hashCodeMap = null;
_.nullSlot = null;
_.size = 0;
_.stringMap = null;
var UNDEFINED;
function $HashMap$EntryImpl(this$static, key, value){
  this$static.key = key;
  this$static.value = value;
  return this$static;
}

function create(key, value){
  return $HashMap$EntryImpl(new HashMap$EntryImpl(), key, value);
}

function equals_15(other){
  var entry;
  if (instanceOf(other, 49)) {
    entry = dynamicCast(other, 49);
    if (equalsWithNullCheck(this.key, entry.getKey()) && equalsWithNullCheck(this.value, entry.getValue_0())) {
      return true;
    }
  }
  return false;
}

function getKey(){
  return this.key;
}

function getValue_0(){
  return this.value;
}

function hashCode_12(){
  var keyHash, valueHash;
  keyHash = 0;
  valueHash = 0;
  if (this.key !== null) {
    keyHash = this.key.hashCode$();
  }
  if (this.value !== null) {
    valueHash = this.value.hashCode$();
  }
  return keyHash ^ valueHash;
}

function setValue(object){
  var old;
  old = this.value;
  this.value = object;
  return old;
}

function toString_20(){
  return this.key + '=' + this.value;
}

function HashMap$EntryImpl(){
}

_ = HashMap$EntryImpl.prototype = new Object_0();
_.equals$ = equals_15;
_.getKey = getKey;
_.getValue_0 = getValue_0;
_.hashCode$ = hashCode_12;
_.setValue_0 = setValue;
_.toString$ = toString_20;
_.typeName$ = package_java_util_ + 'HashMap$EntryImpl';
_.typeId$ = 157;
_.key = null;
_.value = null;
function $HashMap$EntrySet(this$static, this$0){
  this$static.this$0 = this$0;
  return this$static;
}

function $contains(this$static, o){
  var entry, key, value;
  if (instanceOf(o, 49)) {
    entry = dynamicCast(o, 49);
    key = entry.getKey();
    if ($containsKey(this$static.this$0, key)) {
      value = $get_1(this$static.this$0, key);
      return equalsWithNullCheck(entry.getValue_0(), value);
    }
  }
  return false;
}

function $iterator_4(this$static){
  return $HashMap$EntrySetIterator(new HashMap$EntrySetIterator(), this$static.this$0);
}

function contains_3(o){
  return $contains(this, o);
}

function iterator_6(){
  return $iterator_4(this);
}

function remove_23(entry){
  var key;
  if ($contains(this, entry)) {
    key = dynamicCast(entry, 49).getKey();
    $remove_12(this.this$0, key);
    return true;
  }
  return false;
}

function size_3(){
  return this.this$0.size;
}

function HashMap$EntrySet(){
}

_ = HashMap$EntrySet.prototype = new AbstractSet();
_.contains = contains_3;
_.iterator = iterator_6;
_.remove_2 = remove_23;
_.size_0 = size_3;
_.typeName$ = package_java_util_ + 'HashMap$EntrySet';
_.typeId$ = 158;
function $HashMap$EntrySetIterator(this$static, this$0){
  var list;
  this$static.this$0 = this$0;
  list = $ArrayList(new ArrayList());
  if (this$static.this$0.nullSlot !== ($clinit_202() , UNDEFINED)) {
    $add_13(list, $HashMap$EntryImpl(new HashMap$EntryImpl(), null, this$static.this$0.nullSlot));
  }
  addAllStringEntries(this$static.this$0.stringMap, list);
  addAllHashEntries(this$static.this$0.hashCodeMap, list);
  this$static.iter = $iterator_1(list);
  return this$static;
}

function $hasNext_4(this$static){
  return $hasNext_1(this$static.iter);
}

function $next_3(this$static){
  return this$static.last = dynamicCast($next_0(this$static.iter), 49);
}

function $remove_11(this$static){
  if (this$static.last === null) {
    throw $IllegalStateException(new IllegalStateException(), 'Must call next() before remove().');
  }
   else {
    $remove_8(this$static.iter);
    $remove_12(this$static.this$0, this$static.last.getKey());
    this$static.last = null;
  }
}

function hasNext_5(){
  return $hasNext_4(this);
}

function next_6(){
  return $next_3(this);
}

function remove_22(){
  $remove_11(this);
}

function HashMap$EntrySetIterator(){
}

_ = HashMap$EntrySetIterator.prototype = new Object_0();
_.hasNext = hasNext_5;
_.next_0 = next_6;
_.remove = remove_22;
_.typeName$ = package_java_util_ + 'HashMap$EntrySetIterator';
_.typeId$ = 159;
_.iter = null;
_.last = null;
function $HashSet(this$static){
  this$static.map = $HashMap(new HashMap());
  return this$static;
}

function add_5(o){
  var old;
  old = $put_0(this.map, o, valueOf(true));
  return old === null;
}

function contains_4(o){
  return $containsKey(this.map, o);
}

function isEmpty_1(){
  return $isEmpty(this.map);
}

function iterator_7(){
  return $iterator_2($keySet(this.map));
}

function remove_24(o){
  return $remove_12(this.map, o) !== null;
}

function size_4(){
  return this.map.size;
}

function toString_21(){
  return $keySet(this.map).toString$();
}

function HashSet(){
}

_ = HashSet.prototype = new AbstractSet();
_.add_1 = add_5;
_.contains = contains_4;
_.isEmpty = isEmpty_1;
_.iterator = iterator_7;
_.remove_2 = remove_24;
_.size_0 = size_4;
_.toString$ = toString_21;
_.typeName$ = package_java_util_ + 'HashSet';
_.typeId$ = 160;
_.map = null;
function NoSuchElementException(){
}

_ = NoSuchElementException.prototype = new RuntimeException();
_.typeName$ = package_java_util_ + 'NoSuchElementException';
_.typeId$ = 161;
function init_0(){
  $onModuleLoad(new TatamiDemo());
}

function gwtOnLoad(errFn, modName, modBase){
  $moduleName = modName;
  $moduleBase = modBase;
  if (errFn)
    try {
      init_0();
    }
     catch (e) {
      errFn(modName);
    }
   else {
    init_0();
  }
}

var typeIdArray = [{}, {16:1}, {1:1, 16:1, 44:1, 45:1}, {3:1, 16:1}, {3:1, 16:1, 28:1}, {3:1, 16:1, 28:1}, {3:1, 16:1, 28:1}, {2:1, 16:1}, {16:1}, {16:1}, {16:1}, {2:1, 5:1, 16:1}, {2:1, 16:1}, {6:1, 16:1}, {7:1, 16:1}, {16:1}, {16:1}, {16:1}, {16:1, 17:1}, {10:1, 16:1, 17:1, 18:1}, {10:1, 14:1, 16:1, 17:1, 18:1}, {10:1, 14:1, 16:1, 17:1, 18:1}, {10:1, 14:1, 16:1, 17:1, 18:1}, {10:1, 16:1, 17:1, 18:1, 20:1}, {10:1, 16:1, 17:1, 18:1, 20:1, 24:1, 26:1}, {10:1, 16:1, 17:1, 18:1, 20:1, 24:1, 26:1}, {10:1, 14:1, 16:1, 17:1, 18:1}, {16:1}, {16:1, 46:1}, {16:1, 46:1}, {16:1, 46:1}, {10:1, 16:1, 17:1, 18:1, 20:1, 24:1, 26:1}, {16:1, 46:1}, {10:1, 16:1, 17:1, 18:1}, {10:1, 14:1, 16:1, 17:1, 18:1}, {10:1, 14:1, 16:1, 17:1, 18:1, 31:1}, {4:1, 10:1, 14:1, 16:1, 17:1, 18:1, 31:1}, {4:1, 10:1, 11:1, 14:1, 16:1, 17:1, 18:1, 24:1, 26:1, 31:1}, {10:1, 14:1, 16:1, 17:1, 18:1, 23:1}, {16:1}, {16:1}, {16:1, 43:1}, {10:1, 14:1, 16:1, 17:1, 18:1}, {10:1, 14:1, 16:1, 17:1, 18:1}, {16:1}, {16:1}, {10:1, 14:1, 16:1, 17:1, 18:1}, {10:1, 16:1, 17:1, 18:1, 19:1, 20:1, 21:1, 22:1, 23:1, 24:1, 25:1}, {10:1, 15:1, 16:1, 17:1, 18:1, 19:1, 20:1, 21:1, 22:1, 23:1, 24:1, 25:1, 26:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {10:1, 14:1, 16:1, 17:1, 18:1, 23:1}, {10:1, 16:1, 17:1, 18:1, 20:1, 21:1, 22:1, 42:1}, {16:1}, {16:1}, {16:1, 46:1}, {10:1, 12:1, 14:1, 16:1, 17:1, 18:1}, {7:1, 16:1}, {16:1}, {9:1, 10:1, 16:1, 17:1, 18:1}, {10:1, 14:1, 16:1, 17:1, 18:1, 31:1}, {16:1, 46:1}, {10:1, 13:1, 14:1, 16:1, 17:1, 18:1}, {10:1, 14:1, 16:1, 17:1, 18:1}, {9:1, 10:1, 16:1, 17:1, 18:1}, {10:1, 16:1, 17:1, 18:1, 20:1, 24:1}, {10:1, 16:1, 17:1, 18:1, 20:1, 24:1}, {10:1, 14:1, 16:1, 17:1, 18:1, 23:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {10:1, 16:1, 17:1, 18:1}, {10:1, 16:1, 17:1, 18:1, 20:1}, {10:1, 16:1, 17:1, 18:1, 20:1}, {10:1, 14:1, 16:1, 17:1, 18:1, 31:1}, {6:1, 16:1}, {10:1, 16:1, 17:1, 18:1, 20:1}, {10:1, 16:1, 17:1, 18:1, 20:1}, {10:1, 16:1, 17:1, 18:1, 20:1}, {16:1}, {10:1, 14:1, 16:1, 17:1, 18:1}, {16:1, 30:1}, {10:1, 16:1, 17:1, 18:1, 20:1, 24:1}, {10:1, 16:1, 17:1, 18:1, 20:1, 24:1}, {10:1, 16:1, 17:1, 18:1, 20:1, 24:1}, {10:1, 16:1, 17:1, 18:1}, {16:1, 33:1}, {10:1, 16:1, 17:1, 18:1}, {10:1, 16:1, 17:1, 18:1}, {10:1, 16:1, 17:1, 18:1, 20:1}, {10:1, 16:1, 17:1, 18:1, 20:1}, {16:1}, {10:1, 16:1, 17:1, 18:1}, {16:1, 34:1}, {16:1, 34:1}, {16:1}, {16:1, 34:1}, {16:1}, {10:1, 16:1, 17:1, 18:1}, {16:1, 34:1}, {16:1, 34:1}, {16:1, 34:1}, {16:1, 34:1}, {16:1, 36:1}, {16:1}, {16:1, 40:1}, {16:1, 34:1}, {16:1, 34:1}, {16:1, 41:1}, {16:1, 34:1}, {16:1, 34:1}, {16:1, 34:1}, {8:1, 10:1, 16:1, 17:1, 18:1}, {10:1, 16:1, 17:1, 18:1}, {8:1, 16:1}, {8:1, 16:1}, {10:1, 16:1, 17:1, 18:1, 32:1}, {8:1, 9:1, 10:1, 16:1, 17:1, 18:1, 35:1}, {9:1, 16:1}, {8:1, 16:1}, {8:1, 16:1}, {8:1, 16:1}, {9:1, 16:1}, {8:1, 10:1, 16:1, 17:1, 18:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {3:1, 16:1, 28:1}, {16:1, 39:1}, {3:1, 16:1, 28:1}, {16:1}, {16:1, 37:1, 44:1}, {3:1, 16:1, 27:1, 28:1}, {3:1, 16:1, 28:1}, {3:1, 16:1, 28:1}, {16:1, 38:1, 44:1}, {3:1, 16:1, 28:1}, {16:1, 45:1}, {3:1, 16:1, 28:1}, {16:1}, {16:1, 47:1}, {16:1, 29:1}, {16:1, 29:1}, {16:1}, {16:1}, {16:1}, {16:1, 44:1, 48:1}, {16:1, 47:1}, {16:1, 49:1}, {16:1, 29:1}, {16:1}, {16:1, 29:1}, {3:1, 16:1, 28:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}, {16:1}];

if (com_objetdirect_tatami_demo_TatamiDemo) {
  var __gwt_initHandlers = com_objetdirect_tatami_demo_TatamiDemo.__gwt_initHandlers;  com_objetdirect_tatami_demo_TatamiDemo.onScriptLoad(gwtOnLoad);
}
})();
