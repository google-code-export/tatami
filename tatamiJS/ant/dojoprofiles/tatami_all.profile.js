dependencies = {
	//Strip all console.* calls except console.warn and console.error. This is basically a work-around
	//for trac issue: http://bugs.dojotoolkit.org/ticket/6849 where Safari 3's console.debug seems
	//to be flaky to set up (apparently fixed in a webkit nightly).
	//But in general for a build, console.warn/error should be the only things to survive anyway.
	stripConsole: "normal",
	layers: [
		{
			name: "../dojo/tatami_widgets_module.js",
			layerDependencies: [
				"dijit/dijit.js"
			],
			dependencies: [
				"dojox.widget.Toaster",
				"dojo.dnd.Source",
				"dojox.grid.DataGrid",
				"dijit.form.Button",
				"dijit.form.Slider",
				"dijit.Tree",
				"dojo.data.api.Identity",
				"dojo.data.api.Request",
				"dijit._tree.dndSource",
				"dijit.form.NumberSpinner",
				"dijit.form.ValidationTextBox",
				"dijit.form.TextBox",
				"dijit.form.DateTextBox",
				"dijit.form.NumberTextBox",
				"dojo.date",
				"dojo.date.locale",
				"dijit.form._DateTimeTextBox",
				"dojox.grid.cells.dijit",
				"dijit.form.TimeTextBox",
				"dijit.form.ComboBox",
				"dijit.Toolbar",
				"dijit.Editor"
				
			]
		},
		{
			name: "../dojo/tatami_charting_module.js",
			dependencies: [
			    "dojox.gfx.Shape",
				"dojox.gfx.Path",
				"dojox.charting.Chart2D",
				"dojox.charting.plot2D.Areas",
				"dojox.charting.plot2D.Bars",
				"dojox.charting.plot2D.Bubble",
				"dojox.charting.plot2D.ClusteredBars",
				"dojox.charting.plot2D.ClusteredColumns",
				"dojox.charting.plot2D.Columns",
				"dojox.charting.plot2D.Grid",
				"dojox.charting.plot2D.Lines",
				"dojox.charting.plot2D.Markers",
				"dojox.charting.plot2D.MarkersOnly",
				"dojox.charting.plot2D.Pie",
				"dojox.charting.plot2D.Scatter",
				"dojox.charting.plot2D.Stacked",
				"dojox.charting.plot2D.StackedAreas",
				"dojox.charting.plot2D.StackedBars",
				"dojox.charting.plot2D.StackedColumns",
				"dojox.charting.plot2D.StackedLines",
				"dojox.charting.action2D.Magnify",
				"dojox.charting.action2D.Tooltip",
				"dojox.charting.action2D.Shake",
				"dojox.charting.action2D.MoveSlice",
				"dojox.charting.action2D.Highlight",
				"dojox.charting.widget.Legend",
				"dojo.fx",
				"dojox.charting.action2d.Tooltip",
				"dojo.fx.easing",
				"dijit.Tooltip",
				"dojo.parser",
				"dojo.date.stamp",
				"dojox.lang.functional.scan"
			]
		},
		{
			name: "../dojo/tatami_gfx_module.js",
			dependencies: [
				"dojox.gfx",
				"dojox.gfx.matrix",
				"dojox.gfx.path",
				"dojox.gfx.shape"
			]
		},
		{
			name: "../dojo/tatami_all_module.js",
			dependencies: [
				
			],
			layerDependencies: [
			    "../dojo/tatami_widgets_module.js",
			    "../dojo/tatami_gfx_module.js",
			    "../dojo/tatami_charting_module.js"
			]
		}
		
	],
	prefixes: [
		[ "dijit", "../dijit" ],
		[ "dojox", "../dojox" ],
		[ "yui", "../yui" ]
	]
}