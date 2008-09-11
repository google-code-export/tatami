if(!dojo._hasResource["dojox.charting.plot2d.ClusteredBars"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.charting.plot2d.ClusteredBars"] = true;
dojo.provide("dojox.charting.plot2d.ClusteredBars");

dojo.require("dojox.charting.plot2d.common");
dojo.require("dojox.charting.plot2d.Bars");

dojo.require("dojox.lang.functional");
dojo.require("dojox.lang.functional.reversed");

(function(){
	var df = dojox.lang.functional, dc = dojox.charting.plot2d.common,
		purgeGroup = df.lambda("item.purgeGroup()");

	dojo.declare("dojox.charting.plot2d.ClusteredBars", dojox.charting.plot2d.Bars, {
		render: function(dim, offsets){
			if(this.dirty){
				dojo.forEach(this.series, purgeGroup);
				this.cleanGroup();
				var s = this.group;
				df.forEachRev(this.series, function(item){ item.cleanGroup(s); });
			}
			var t = this.chart.theme, color, stroke, fill, f,
				gap = this.opt.gap < this._vScaler.scale / 3 ? this.opt.gap : 0,
				thickness = (this._vScaler.scale - 2 * gap) / this.series.length;
			for(var i = this.series.length - 1; i >= 0; --i){
				var run = this.series[i];
				if(!this.dirty && !run.dirty){ continue; }
				run.cleanGroup();
				var s = run.group;
				if(!run.fill || !run.stroke){
					// need autogenerated color
					color = run.dyn.color = new dojo.Color(t.next("color"));
				}
				stroke = run.stroke ? run.stroke : dc.augmentStroke(t.series.stroke, color);
				fill = run.fill ? run.fill : dc.augmentFill(t.series.fill, color);
				var baseline = Math.max(0, this._hScaler.bounds.lower),
					xoff = offsets.l + this._hScaler.scale * (baseline - this._hScaler.bounds.lower),
					yoff = dim.height - offsets.b - this._vScaler.scale * (1.5 - this._vScaler.bounds.lower) + 
						gap + thickness * (this.series.length - i - 1);
				for(var j = 0; j < run.data.length; ++j){
					var v = run.data[j],
						width  = this._hScaler.scale * (v - baseline),
						height = thickness, w = Math.abs(width);
					if(w >= 1 && height >= 1){
						var shape = s.createRect({
							x: xoff + (width < 0 ? width : 0),
							y: yoff - this._vScaler.scale * j,
							width: w, height: height
						}).setFill(fill).setStroke(stroke);
						run.dyn.fill   = shape.getFill();
						run.dyn.stroke = shape.getStroke();
					}
				}
				run.dirty = false;
			}
			this.dirty = false;
			return this;
		}
	});
})();

}
