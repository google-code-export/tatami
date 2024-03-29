if(!dojo._hasResource["dojox.charting.plot2d.Pie"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.charting.plot2d.Pie"] = true;
dojo.provide("dojox.charting.plot2d.Pie");

dojo.require("dojox.charting.Element");
dojo.require("dojox.charting.axis2d.common");
dojo.require("dojox.charting.plot2d.common");

dojo.require("dojox.lang.functional");
dojo.require("dojox.gfx");

(function(){
	var df = dojox.lang.functional, du = dojox.lang.utils,
		dc = dojox.charting.plot2d.common,
		da = dojox.charting.axis2d.common,
		g = dojox.gfx;

	dojo.declare("dojox.charting.plot2d.Pie", dojox.charting.Element, {
		defaultParams: {
			labels:			true,
			ticks:			false,
			fixed:			true,
			precision:		1,
			labelOffset:	20,
			labelStyle:		"default",	// default/rows/auto
			htmlLabels:		true		// use HTML to draw labels
		},
		optionalParams: {
			font:		"",
			fontColor:	"",
			radius:		0
		},

		constructor: function(chart, kwArgs){
			this.opt = dojo.clone(this.defaultParams);
			du.updateWithObject(this.opt, kwArgs);
			du.updateWithPattern(this.opt, kwArgs, this.optionalParams);
			this.run = null;
			this.dyn = [];
		},
		destroy: function(){
			this.resetEvents();
			this.inherited(arguments);
		},
		clear: function(){
			this.dirty = true;
			this.dyn = [];
			this.run = null;
			return this;
		},
		setAxis: function(axis){
			// nothing
			return this;
		},
		addSeries: function(run){
			this.run = run;
			return this;
		},
		calculateAxes: function(dim){
			// nothing
			return this;
		},
		getRequiredColors: function(){
			return this.run ? this.run.data.length : 0;
		},

		// events
		plotEvent: function(o){
			// intentionally empty --- used for events
		},
		connect: function(object, method){
			this.dirty = true;
			return dojo.connect(this, "plotEvent", object, method);
		},
		events: function(){
			var ls = this.plotEvent._listeners;
			if(!ls || !ls.length){ return false; }
			for(var i in ls){
				if(!(i in Array.prototype)){
					return true;
				}
			}
			return false;
		},
		resetEvents: function(){
			this.plotEvent({type: "onplotreset", plot: this});
		},
		_connectEvents: function(shape, o){
			shape.connect("onmouseover", this, function(e){
				o.type  = "onmouseover";
				o.event = e;
				this.plotEvent(o);
			});
			shape.connect("onmouseout", this, function(e){
				o.type  = "onmouseout";
				o.event = e;
				this.plotEvent(o);
			});
			shape.connect("onclick", this, function(e){
				o.type  = "onclick";
				o.event = e;
				this.plotEvent(o);
			});
		},

		render: function(dim, offsets){
			if(!this.dirty){ return this; }
			this.dirty = false;
			this.cleanGroup();
			var s = this.group, color, t = this.chart.theme;
			this.resetEvents();

			if(!this.run || !this.run.data.length){
				return this;
			}

			// calculate the geometry
			var rx = (dim.width  - offsets.l - offsets.r) / 2,
				ry = (dim.height - offsets.t - offsets.b) / 2,
				r  = Math.min(rx, ry),
				taFont = "font" in this.opt ? this.opt.font : t.axis.font,
				size = taFont ? g.normalizedLength(g.splitFontString(taFont).size) : 0,
				taFontColor = "fontColor" in this.opt ? this.opt.fontColor : t.axis.fontColor,
				start = 0, step, filteredRun, slices, labels, shift, labelR,
				run = this.run.data,
				events = this.events();
			if(typeof run[0] == "number"){
				filteredRun = df.map(run, "Math.max(x, 0)");
				if(df.every(filteredRun, "<= 0")){
					return this;
				}
				slices = df.map(filteredRun, "/this", df.foldl(filteredRun, "+", 0));
				if(this.opt.labels){
					labels = dojo.map(slices, function(x){
						return x > 0 ? this._getLabel(x * 100) + "%" : "";
					}, this);
				}
			}else{
				filteredRun = df.map(run, "Math.max(x.y, 0)");
				if(df.every(filteredRun, "<= 0")){
					return this;
				}
				slices = df.map(filteredRun, "/this", df.foldl(filteredRun, "+", 0));
				if(this.opt.labels){
					labels = dojo.map(slices, function(x, i){
						if(x <= 0){ return ""; }
						var v = run[i];
						return "text" in v ? v.text : this._getLabel(x * 100) + "%";
					}, this);
				}
			}
			if(this.opt.labels){
				shift = df.foldl1(df.map(labels, function(label){
					return dojox.gfx._base._getTextBox(label, {font: taFont}).w;
				}, this), "Math.max(a, b)") / 2;
				if(this.opt.labelOffset < 0){
					r = Math.min(rx - 2 * shift, ry - size) + this.opt.labelOffset;
				}
				labelR = r - this.opt.labelOffset;
			}
			if("radius" in this.opt){
				r = this.opt.radius;
				labelR = r - this.opt.labelOffset;
			}
			var	circle = {
					cx: offsets.l + rx,
					cy: offsets.t + ry,
					r:  r
				};

			this.dyn = [];
			// draw slices
			dojo.some(slices, function(slice, i){
				if(slice <= 0){
					// degenerated slice
					return false;	// continue
				}
				var v = run[i];
				if(slice >= 1){
					// whole pie
					var color, fill, stroke;
					if(typeof v == "object"){
						color  = "color"  in v ? v.color  : new dojo.Color(t.next("color"));
						fill   = "fill"   in v ? v.fill   : dc.augmentFill(t.series.fill, color);
						stroke = "stroke" in v ? v.stroke : dc.augmentStroke(t.series.stroke, color);
					}else{
						color  = new dojo.Color(t.next("color"));
						fill   = dc.augmentFill(t.series.fill, color);
						stroke = dc.augmentStroke(t.series.stroke, color);
					}
					var shape = s.createCircle(circle).setFill(fill).setStroke(stroke);
					this.dyn.push({color: color, fill: fill, stroke: stroke});

					if(events){
						var o = {
							element: "slice",
							index:   i,
							run:     this.run,
							plot:    this,
							shape:   shape,
							x:       i,
							y:       typeof v == "number" ? v : v.y,
							cx:      circle.cx,
							cy:      circle.cy,
							cr:      r
						};
						this._connectEvents(shape, o);
					}

					return true;	// stop iteration
				}
				// calculate the geometry of the slice
				var end = start + slice * 2 * Math.PI;
				if(i + 1 == slices.length){
					end = 2 * Math.PI;
				}
				var	step = end - start,
					x1 = circle.cx + r * Math.cos(start),
					y1 = circle.cy + r * Math.sin(start),
					x2 = circle.cx + r * Math.cos(end),
					y2 = circle.cy + r * Math.sin(end);
				// draw the slice
				var color, fill, stroke;
				if(typeof v == "object"){
					color  = "color"  in v ? v.color  : new dojo.Color(t.next("color"));
					fill   = "fill"   in v ? v.fill   : dc.augmentFill(t.series.fill, color);
					stroke = "stroke" in v ? v.stroke : dc.augmentStroke(t.series.stroke, color);
				}else{
					color  = new dojo.Color(t.next("color"));
					fill   = dc.augmentFill(t.series.fill, color);
					stroke = dc.augmentStroke(t.series.stroke, color);
				}
				var shape = s.createPath({}).
						moveTo(circle.cx, circle.cy).
						lineTo(x1, y1).
						arcTo(r, r, 0, step > Math.PI, true, x2, y2).
						lineTo(circle.cx, circle.cy).
						closePath().
						setFill(fill).
						setStroke(stroke);
				this.dyn.push({color: color, fill: fill, stroke: stroke});

				if(events){
					var o = {
						element: "slice",
						index:   i,
						run:     this.run,
						plot:    this,
						shape:   shape,
						x:       i,
						y:       typeof v == "number" ? v : v.y,
						cx:      circle.cx,
						cy:      circle.cy,
						cr:      r
					};
					this._connectEvents(shape, o);
				}

				start = end;

				return false;	// continue
			}, this);
			// draw labels
			if(this.opt.labels){
				start = 0;
				dojo.some(slices, function(slice, i){
					if(slice <= 0){
						// degenerated slice
						return false;	// continue
					}
					if(slice >= 1){
						// whole pie
						var v = run[i], elem = da.createText[this.opt.htmlLabels && dojox.gfx.renderer != "vml" ? "html" : "gfx"]
									(this.chart, s, circle.cx, circle.cy + size / 2, "middle",
										labels[i], taFont, (typeof v == "object" && "fontColor" in v) ? v.fontColor : taFontColor);
						if(this.opt.htmlLabels){ this.htmlElements.push(elem); }
						return true;	// stop iteration
					}
					// calculate the geometry of the slice
					var end = start + slice * 2 * Math.PI, v = run[i];
					if(i + 1 == slices.length){
						end = 2 * Math.PI;
					}
					var	labelAngle = (start + end) / 2,
						x = circle.cx + labelR * Math.cos(labelAngle),
						y = circle.cy + labelR * Math.sin(labelAngle) + size / 2;
					// draw the label
					var elem = da.createText[this.opt.htmlLabels && dojox.gfx.renderer != "vml" ? "html" : "gfx"]
									(this.chart, s, x, y, "middle",
										labels[i], taFont,
										(typeof v == "object" && "fontColor" in v)
											? v.fontColor : taFontColor);
					if(this.opt.htmlLabels){ this.htmlElements.push(elem); }
					start = end;
					return false;	// continue
				}, this);
			}
			return this;
		},

		// utilities
		_getLabel: function(number){
			return this.opt.fixed ? number.toFixed(this.opt.precision) : number.toString();
		}
	});
})();

}
