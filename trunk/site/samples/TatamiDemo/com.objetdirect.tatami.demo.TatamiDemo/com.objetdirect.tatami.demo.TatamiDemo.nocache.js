alert("coucou");
function com_objetdirect_tatami_demo_TatamiDemo(){
  var $wnd = window, $doc = document, external = $wnd.external, scriptsDone, loadDone, bodyDone, base = '', metaProps = {}, values = [], providers = [], answers = [], onLoadErrorFunc, propertyErrorFunc;
  if (!$wnd.__gwt_stylesLoaded) {
    $wnd.__gwt_stylesLoaded = {};
  }
  if (!$wnd.__gwt_scriptsLoaded) {
    $wnd.__gwt_scriptsLoaded = {};
  }
  function isHostedMode(){
    try {
      return external && (external.gwtOnLoad && $wnd.location.search.indexOf('gwt.hybrid') == -1);
    }
     catch (e) {
      return false;
    }
  }

  function maybeStartModule(){
    if (scriptsDone && (loadDone && bodyDone)) {
      var iframe = $doc.getElementById('com.objetdirect.tatami.demo.TatamiDemo');
      var frameWnd = iframe.contentWindow;
      frameWnd.__gwt_initHandlers = com_objetdirect_tatami_demo_TatamiDemo.__gwt_initHandlers;
      if (isHostedMode()) {
        frameWnd.__gwt_getProperty = function(name){
          return computePropValue(name);
        }
        ;
      }
      com_objetdirect_tatami_demo_TatamiDemo = null;
      frameWnd.gwtOnLoad(onLoadErrorFunc, 'com.objetdirect.tatami.demo.TatamiDemo', base);
    }
  }

  function computeScriptBase(){
    var thisScript, markerId = '__gwt_marker_com.objetdirect.tatami.demo.TatamiDemo', markerScript;
    $doc.write('<script id="' + markerId + '"><\/script>');
    markerScript = $doc.getElementById(markerId);
    thisScript = markerScript && markerScript.previousSibling;
    while (thisScript && thisScript.tagName != 'SCRIPT') {
      thisScript = thisScript.previousSibling;
    }
    function getDirectoryOfFile(path){
      var eq = path.lastIndexOf('/');
      return eq >= 0?path.substring(0, eq + 1):'';
    }

    ;
    if (thisScript && thisScript.src) {
      base = getDirectoryOfFile(thisScript.src);
    }
    if (base == '') {
      var baseElements = $doc.getElementsByTagName('base');
      if (baseElements.length > 0) {
        base = baseElements[baseElements.length - 1].href;
      }
       else {
        var loc = $doc.location;
        var href = loc.href;
        base = getDirectoryOfFile(href.substr(0, href.length - loc.hash.length));
      }
    }
     else if (base.match(/^\w+:\/\//)) {
    }
     else {
      var img = $doc.createElement('img');
      img.src = base + 'clear.cache.gif';
      base = getDirectoryOfFile(img.src);
    }
    if (markerScript) {
      markerScript.parentNode.removeChild(markerScript);
    }
  }

  function processMetas(){
    var metas = document.getElementsByTagName('meta');
    for (var i = 0, n = metas.length; i < n; ++i) {
      var meta = metas[i], name = meta.getAttribute('name'), content;
      if (name) {
        if (name == 'gwt:property') {
          content = meta.getAttribute('content');
          if (content) {
            var value, eq = content.indexOf('=');
            if (eq >= 0) {
              name = content.substring(0, eq);
              value = content.substring(eq + 1);
            }
             else {
              name = content;
              value = '';
            }
            metaProps[name] = value;
          }
        }
         else if (name == 'gwt:onPropertyErrorFn') {
          content = meta.getAttribute('content');
          if (content) {
            try {
              propertyErrorFunc = eval(content);
            }
             catch (e) {
              alert('Bad handler "' + content + '" for "gwt:onPropertyErrorFn"');
            }
          }
        }
         else if (name == 'gwt:onLoadErrorFn') {
          content = meta.getAttribute('content');
          if (content) {
            try {
              onLoadErrorFunc = eval(content);
            }
             catch (e) {
              alert('Bad handler "' + content + '" for "gwt:onLoadErrorFn"');
            }
          }
        }
      }
    }
  }

  function __gwt_isKnownPropertyValue(propName, propValue){
    return propValue in values[propName];
  }

  function __gwt_getMetaProperty(name){
    var value = metaProps[name];
    return value == null?null:value;
  }

  function unflattenKeylistIntoAnswers(propValArray, value){
    var answer = answers;
    for (var i = 0, n = propValArray.length - 1; i < n; ++i) {
      answer = answer[propValArray[i]] || (answer[propValArray[i]] = []);
    }
    answer[propValArray[n]] = value;
  }

  function computePropValue(propName){
    var value = providers[propName](), allowedValuesMap = values[propName];
    if (value in allowedValuesMap) {
      return value;
    }
    var allowedValuesList = [];
    for (var k in allowedValuesMap) {
      allowedValuesList[allowedValuesMap[k]] = k;
    }
    if (propertyErrorFunc) {
      propertyErrorFunc(propName, allowedValuesList, value);
    }
    throw null;
  }

  providers['user.agent'] = function(){
    var ua = navigator.userAgent.toLowerCase();
    var makeVersion = function(result){
      return parseInt(result[1]) * 1000 + parseInt(result[2]);
    }
    ;
    if (ua.indexOf('opera') != -1) {
      return 'opera';
    }
     else if (ua.indexOf('webkit') != -1) {
      return 'safari';
    }
     else if (ua.indexOf('msie') != -1) {
      var result = /msie ([0-9]+)\.([0-9]+)/.exec(ua);
      if (result && result.length == 3) {
        if (makeVersion(result) >= 6000) {
          return 'ie6';
        }
      }
    }
     else if (ua.indexOf('gecko') != -1) {
      var result = /rv:([0-9]+)\.([0-9]+)/.exec(ua);
      if (result && result.length == 3) {
        if (makeVersion(result) >= 1008)
          return 'gecko1_8';
      }
      return 'gecko';
    }
    return 'unknown';
  }
  ;
  values['user.agent'] = {'gecko':0, 'gecko1_8':1, 'ie6':2, 'opera':3, 'safari':4};
  com_objetdirect_tatami_demo_TatamiDemo.onInjectionDone = function(){
    scriptsDone = true;
    maybeStartModule();
  }
  ;
  com_objetdirect_tatami_demo_TatamiDemo.onScriptLoad = function(){
    loadDone = true;
    maybeStartModule();
  }
  ;
  computeScriptBase();
  processMetas();
  var strongName;
  if (isHostedMode()) {
    strongName = 'hosted.html?com_objetdirect_tatami_demo_TatamiDemo';
  }
   else {
    try {
      unflattenKeylistIntoAnswers(['gecko'], '1A1C559DBB0EC7357440EF93094C49B1');
      unflattenKeylistIntoAnswers(['opera'], '2012669F5014B945D81E66ED2A7FE9D0');
      unflattenKeylistIntoAnswers(['ie6'], '2B96FAFB041666D1E7F27FFDD0E69495');
      unflattenKeylistIntoAnswers(['safari'], '64F9C9A3771ADBAB790EA5133D2BDB83');
      unflattenKeylistIntoAnswers(['gecko1_8'], 'D84305E2BECFF91620914F783C09DAD3');
      strongName = answers[computePropValue('user.agent')];
    }
     catch (e) {
      return;
    }
    strongName += '.cache.html';
  }
  var onBodyDoneTimerId;
  function onBodyDone(){
    if (!bodyDone) {
      bodyDone = true;
      maybeStartModule();
      if ($doc.removeEventListener) {
        $doc.removeEventListener('DOMContentLoaded', onBodyDone, false);
      }
      if (onBodyDoneTimerId) {
        clearInterval(onBodyDoneTimerId);
      }
    }
  }

  var scriptInjected;
  function maybeInjectFrame(){
    if (!scriptInjected && $doc.body) {
      scriptInjected = true;
      var iframe = $doc.createElement('iframe');
      iframe.src = "javascript:''";
      iframe.id = 'com.objetdirect.tatami.demo.TatamiDemo';
      iframe.style.cssText = 'position:absolute;width:0;height:0;border:none';
      $doc.body.appendChild(iframe);
      iframe.src = base + strongName;
    }
  }

  if ($doc.addEventListener) {
    $doc.addEventListener('DOMContentLoaded', function(){
      maybeInjectFrame();
      onBodyDone();
    }
    , false);
  }
  var onBodyDoneTimerId = setInterval(function(){
    maybeInjectFrame();
    if (/loaded|complete/.test($doc.readyState)) {
      onBodyDone();
    }
  }
  , 50);
  if (!__gwt_stylesLoaded['tatami.css']) {
    __gwt_stylesLoaded['tatami.css'] = true;
    document.write('<link rel="stylesheet" href="' + base + 'tatami.css">');
  }
  if (!__gwt_scriptsLoaded['yui/yahoo.js']) {
    __gwt_scriptsLoaded['yui/yahoo.js'] = true;
    document.write('<script language="javascript" src="' + base + 'yui/yahoo.js"><\/script>');
  }
  if (!__gwt_scriptsLoaded['yui/dom.js']) {
    __gwt_scriptsLoaded['yui/dom.js'] = true;
    document.write('<script language="javascript" src="' + base + 'yui/dom.js"><\/script>');
  }
  if (!__gwt_scriptsLoaded['yui/event.js']) {
    __gwt_scriptsLoaded['yui/event.js'] = true;
    document.write('<script language="javascript" src="' + base + 'yui/event.js"><\/script>');
  }
  if (!__gwt_scriptsLoaded['yui/dragdrop.js']) {
    __gwt_scriptsLoaded['yui/dragdrop.js'] = true;
    document.write('<script language="javascript" src="' + base + 'yui/dragdrop.js"><\/script>');
  }
  if (!__gwt_scriptsLoaded['yui/DDPlayer.js']) {
    __gwt_scriptsLoaded['yui/DDPlayer.js'] = true;
    document.write('<script language="javascript" src="' + base + 'yui/DDPlayer.js"><\/script>');
  }
  if (!__gwt_scriptsLoaded['dojo/dojo.js']) {
    __gwt_scriptsLoaded['dojo/dojo.js'] = true;
    document.write('<script language="javascript" src="' + base + 'dojo/dojo.js"><\/script>');
  }
  if (!__gwt_scriptsLoaded['autoEvents.js']) {
    __gwt_scriptsLoaded['autoEvents.js'] = true;
    document.write('<script language="javascript" src="' + base + 'autoEvents.js"><\/script>');
  }
  $doc.write("<script>com_objetdirect_tatami_demo_TatamiDemo.onInjectionDone('com.objetdirect.tatami.demo.TatamiDemo')<\/script>");
}

com_objetdirect_tatami_demo_TatamiDemo.__gwt_initHandlers = function(resize, beforeunload, unload){
  var $wnd = window, oldOnResize = $wnd.onresize, oldOnBeforeUnload = $wnd.onbeforeunload, oldOnUnload = $wnd.onunload;
  $wnd.onresize = function(evt){
    try {
      resize();
    }
     finally {
      oldOnResize && oldOnResize(evt);
    }
  }
  ;
  $wnd.onbeforeunload = function(evt){
    var ret, oldRet;
    try {
      ret = beforeunload();
    }
     finally {
      oldRet = oldOnBeforeUnload && oldOnBeforeUnload(evt);
    }
    if (ret != null) {
      return ret;
    }
    if (oldRet != null) {
      return oldRet;
    }
  }
  ;
  $wnd.onunload = function(evt){
    try {
      unload();
    }
     finally {
      oldOnUnload && oldOnUnload(evt);
    }
  }
  ;
}
;
com_objetdirect_tatami_demo_TatamiDemo();
