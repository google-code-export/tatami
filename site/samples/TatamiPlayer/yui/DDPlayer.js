/* Copyright (c) 2006 Yahoo! Inc. All rights reserved. */

/////////////////////////////////////////////////////////////////////////

/**
 * @class a YAHOO.util.DDFramed implementation. During the drag over event, the
 * dragged element is inserted before the dragged-over element.
 *
 * @extends YAHOO.util.DDProxy
 * @constructor
 * @param {String} id the id of the linked element
 * @param {String} sGroup the group of related DragDrop objects
 */
YAHOO.example.DDPlayer = function(id, sGroup, config) {
    this.initPlayer(id, sGroup);
};

// YAHOO.example.DDPlayer.prototype = new YAHOO.util.DDProxy();
YAHOO.extend(YAHOO.example.DDPlayer, YAHOO.util.DDProxy);

YAHOO.example.DDPlayer.TYPE = "DDPlayer";

YAHOO.example.DDPlayer.prototype.initPlayer = function(id, sGroup, config) {
    if (!id) { return; }

    this.init(id, sGroup, config);
    this.initFrame();

    this.logger = this.logger || YAHOO;
    var s = this.getDragEl().style;
    s.borderColor = "transparent";
    // s.backgroundColor = "#cccccc";
    s.opacity = 0.76;
    s.filter = "alpha(opacity=76)";

    // specify that this is not currently a drop target
    this.isTarget = false;

    this.originalStyles = [];

    this.type = YAHOO.example.DDPlayer.TYPE;
    this.slot = null;

    this.startPos = YAHOO.util.Dom.getXY( this.getEl() );
    this.logger.log(id + " startpos: " + this.startPos);
    this.acceptFct = null;
    this.dropFct = null;
};

YAHOO.example.DDPlayer.prototype.startDrag = function(x, y) {
    this.logger.log(this.id + " startDrag");

    var dragEl = this.getDragEl();
    var clickEl = this.getEl();

    dragEl.innerHTML = clickEl.innerHTML;
    dragEl.className = clickEl.className;
    dragEl.style.color = this.DDM.getStyle(clickEl, "color");;
    dragEl.style.backgroundColor = this.DDM.getStyle(clickEl, "backgroundColor");

    var s = clickEl.style;
    s.opacity = .1;
    s.filter = "alpha(opacity=10)";

    var targets = YAHOO.util.DDM.getRelated(this, true);
    this.logger.log(targets.length + " targets");
    for (var i=0; i<targets.length; i++) {
        
        var targetEl = this.getTargetDomRef(targets[i]);

        if (!this.originalStyles[targetEl.id]) {
            this.originalStyles[targetEl.id] = targetEl.className;
        }

        targetEl.className = "target";
    }
};

YAHOO.example.DDPlayer.prototype.getTargetDomRef = function(oDD) {
    return oDD.getEl();
};

YAHOO.example.DDPlayer.prototype.endDrag = function(e) {
    // reset the linked element styles
    var s = this.getEl().style;
    s.opacity = 1;
    s.filter = "alpha(opacity=100)";

    this.resetTargets();
};

YAHOO.example.DDPlayer.prototype.resetTargets = function() {

    // reset the target styles
    var targets = YAHOO.util.DDM.getRelated(this, true);
    for (var i=0; i<targets.length; i++) {
        var targetEl = this.getTargetDomRef(targets[i]);
        var oldStyle = this.originalStyles[targetEl.id];
        if (oldStyle) {
            targetEl.className = oldStyle;
        }
    }
};

YAHOO.example.DDPlayer.prototype.onDragDrop = function(e, id) {
    // get the drag and drop object that was targeted
    var oDD;
    
    if ("string" == typeof id) {
        oDD = YAHOO.util.DDM.getDDById(id);
    } else {
        oDD = YAHOO.util.DDM.getBestMatch(id);
    }

    var el = this.getEl();

    this.slot = oDD;
    if (this.acceptFct) {
    	var res = this.acceptFct(this.getEl(), this.slot.getEl());
    	if (res==false) {
            YAHOO.util.Dom.setXY(this.getEl(), this.startPos);
		    this.resetTargets();
            return;
    	}
    }
    YAHOO.util.DDM.moveToEl(el, oDD.getEl());
    this.resetTargets();
    if (this.dropFct) {
    	this.dropFct(this.getEl(), this.slot.getEl());
    }    
};

YAHOO.example.DDPlayer.prototype.accept = function(fct) {
    this.acceptFct = fct;
};

YAHOO.example.DDPlayer.prototype.subscribe = function(fct) {
    this.dropFct = fct;
};

YAHOO.example.DDPlayer.prototype.swap = function(el1, el2) {
    var dom = YAHOO.util.Dom;
    var pos1 = dom.getXY(el1);
    var pos2 = dom.getXY(el2);
    dom.setXY(el1, pos2);
    dom.setXY(el2, pos1);
};

YAHOO.example.DDPlayer.prototype.onDragOver = function(e, id) {};

YAHOO.example.DDPlayer.prototype.onDrag = function(e, id) {};

function ddplayerIsOk() {
}
