if(!dojo._hasResource["dojox.html.styles"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.html.styles"] = true;
dojo.provide("dojox.html.styles");
	
	// summary
	//		Methods for creating and minipulating dynamic CSS Styles and Style Sheets
	//
	// USAGE:
	//		| dojox.html.createStyle("#myDiv input", "font-size:24px");
	//			Creates Style #myDiv input, which can now be applied to myDiv, and 
	//			the inner input will be targeted
	//		| dojox.html.createStyle(".myStyle", "color:#FF0000");
	//			Now the class myStyle can be assigned to a node's className
	
(function(){
	
	var dynamicStyleMap = {};
	var pageStyleSheets = {};
	var titledSheets = [];
	var styleIndicies = [];
	
	dojox.html.insertCssRule = function(selector, declaration, styleSheetName){
		// summary
		//	Creates a style and attaches it to a dynamically created stylesheet
		//	arguments:
		//		selector: 	
		//					A fully qualified class name, as it would appear in
		//					a CSS dojo.doc. Start classes with periods, target
		//					nodes with '#'. Large selectors can also be created
		//					like:
		//					| "#myDiv.myClass span input"
		//		declaration:
		//					A single string that would make up a style block, not
		//					including the curly braces. Include semi-colons between
		//					statements. Do not use JavaScript style declarations
		//					in camel case, use as you would in a CSS dojo.doc:
		//					| "color:#ffoooo;font-size:12px;margin-left:5px;"
		//		styleSheetName: ( optional )
		//					Name of the dynamic style sheet this rule should be 
		//					inserted into. If is not found by that name, it is
		//					created. If no name is passed, the name "default" is 
		//					used.
		//
		var ss = dojox.html.getDynamicStyleSheet(styleSheetName);
		var styleText = selector + " {" + declaration + "}";
		
		if(dojo.isIE){
			// Note: check for if(ss.cssText) does not work
			ss.cssText+=styleText;
		}else if(ss.sheet){
			ss.sheet.insertRule(styleText, ss._indicies.length);
		}else{
			ss.appendChild(dojo.doc.createTextNode(styleText));
		}
		ss._indicies.push(selector+" "+declaration);
		return selector;
	
	}
	
	dojox.html.removeCssRule = function(selector, declaration, styleSheetName){
		// summary
		//	Removes a cssRule base on the selector and declaration passed
		//	The declaration is needed for cases of dupe selectors
		// NOTE: Only removes DYNAMICALLY created cssRules. If you 
		//	created it with dojox.html.insertCssRule, it can be removed.
		//
		var ss;
		var index=-1;
		for(var nm in dynamicStyleMap){
			if(styleSheetName && styleSheetName!=nm) {continue;}
			ss = dynamicStyleMap[nm];
			for(var i=0;i<ss._indicies.length;i++){
				if(selector+" "+declaration == ss._indicies[i]){
					index = i;
					break;
				}
			}
			if(index>-1) { break; }
		}
		if(!ss){
			console.log("No dynamic style sheet has been created from which to remove a rule.");
			return false;
		}
		if(index==-1){
			console.log("The css rule was not found and could not be removed.");
			return false;
		}
		
		ss._indicies.splice(index, 1);
		
		
		
		if(dojo.isIE){ 
			// Note: check for if(ss.removeRule) does not work
			ss.removeRule(index);
		}else if(ss.sheet){
			ss.sheet.deleteRule(index);
		}else if(document.styleSheets[0]){
			console.log("what browser hath useth thith?")
			//
		}
		return true;
		
	}
	
	/*
	dojox.html.modifyCssRule = function(selector, declaration, styleSheetName){
		Not implemented - it seems to have some merit for changing some complex 
		selectors. It's not much use for changing simple ones like "span".
		For now, simply write a new rule which will cascade over the first.
		// summary
		//	Modfies an existing cssRule
	}
	*/
	
	dojox.html.getStyleSheet = function(styleSheetName){
		// summary
		//	Returns a style sheet based on the argument.
		//	Searches dynamic style sheets first. If no matches,
		//	searches document style sheets.
		//
		// argument: (optional)
		//		A title or an href to a style sheet. Title can be 
		//		an attribute in a tag, or a dynamic style sheet 
		//		reference. Href can be the name of the file.
		//		If no argument, the assumed crated dynamic style 
		//		sheet is used.
		
		// try dynamic sheets first 
		if(dynamicStyleMap[styleSheetName || "default"]){
			return dynamicStyleMap[styleSheetName || "default"];
		}
		if(!styleSheetName){
			// no arg is nly good for the default style sheet 
			// and it has not been created yet.
			return false;
		}
		
		var allSheets = dojox.html.getStyleSheets();
		
		// now try document style sheets by name
		if(allSheets[styleSheetName]){
			return dojox.html.getStyleSheets()[styleSheetName];
		}
		
		// check for partial matches in hrefs (so that a fully 
		//qualified name does not have to be passed)
		for ( var nm in allSheets){
			if(	allSheets[nm].href && allSheets[nm].href.indexOf(styleSheetName)>-1){
				return allSheets[nm];
			}
		}
		return false;
	}
	
	dojox.html.getDynamicStyleSheet = function(styleSheetName){
		// summary
		//	Creates and returns a dynamically created style sheet
		// used for dynamic styles
		//
		//	argument:
		//		styleSheetName /* optional String */
		//			The name given the style sheet so that multiple 
		//			style sheets can be created and referenced. If 
		//			no argument is given, the name "default" is used.
		//
		if(!styleSheetName){ styleSheetName="default"; }
		
		if(!dynamicStyleMap[styleSheetName]){
			if(dojo.doc.createStyleSheet){ //IE
				dynamicStyleMap[styleSheetName] = dojo.doc.createStyleSheet(styleSheetName);
				dynamicStyleMap[styleSheetName].title = styleSheetName;
			}else{
				dynamicStyleMap[styleSheetName] = dojo.doc.createElement("style");
				dynamicStyleMap[styleSheetName].setAttribute("type", "text/css");
				dojo.doc.getElementsByTagName("head")[0].appendChild(dynamicStyleMap[styleSheetName]);
				console.log(styleSheetName, " ss created: ", dynamicStyleMap[styleSheetName].sheet);
			}
			dynamicStyleMap[styleSheetName]._indicies = [];
		}
		
		
		return dynamicStyleMap[styleSheetName];
	}

	dojox.html.enableStyleSheet = function(styleSheetName){
		// summary
		//	Enables the style sheet with the name passed in the
		//	argument. Deafults to the default style sheet.
		//
		var ss = dojox.html.getStyleSheet(styleSheetName);
		if(ss){ 
			if(ss.sheet){
				ss.sheet.disabled = false; 
			}else{
				ss.disabled = false; 
			}
		}
	}

	dojox.html.disableStyleSheet = function(styleSheetName){
		// summary
		//	Disables the dynamic style sheet with the name passed in the
		//	argument. If no arg is passed, defaults to the default style sheet.
		//
		var ss = dojox.html.getStyleSheet(styleSheetName);
		if(ss){ 
			if(ss.sheet){
				ss.sheet.disabled = true; 
			}else{
				ss.disabled = true; 
			}
		}
	}
	
	dojox.html.activeStyleSheet = function(title /* optional */){
		// summary
		//	Getter/Setter
		//
		//	If passed a title, enables a that style sheet. All other
		//	toggle-able style sheets are disabled.
		//
		//	If no argument is passed, returns currently enabled
		//	style sheet.
		var sheets = dojox.html.getToggledStyleSheets();
		if(arguments.length == 1){
			//console.log("sheets:", sheets);
			dojo.forEach(sheets, function(s){
				s.disabled = (s.title == title) ? false : true;
				//console.log("SWITCHED:", s.title, s.disabled, s.id);
			});
		}else{
			for(var i=0; i<sheets.length;i++){
				if(sheets[i].disabled == false){
					return sheets[i];
				}
			}
		}
		return true;
	}
	
	dojox.html.getPreferredStyleSheet = function(){
		// summary
		//	Returns the style sheet that was initially enabled
		//	on document launch.
		
		//TODO
	}
	
	
	
	
	dojox.html.getToggledStyleSheets = function(){
		// summary
		//	Searches HTML for style sheets that are "toggle-able" - 
		//	can be enabled and disabled. These would include sheets
		//	with the title attribute, as well as the REL attribute.
		//
		//	returns:
		//		An array of all toggle-able style sheets
		//
		//	TODO: 	Sets of style sheets could be grouped according to
		//			an ID and used in sets, much like different
		//			groups of radio buttons. It would not however be
		//			according to W3C spec
		//
		if(!titledSheets.length){
			var sObjects = dojox.html.getStyleSheets();
			for(var nm in sObjects){
				
				if(sObjects[nm].title){
					//console.log("TITLE:", sObjects[nm].title, sObjects[nm])
					titledSheets.push(sObjects[nm]);
				}
			}
		}
		return titledSheets;
	}
	
	
	dojox.html.getStyleSheets = function(){
		// summary
		//	Collects all the style sheets referenced in the HTML page,
		//	including any incuded via @import. 
		//
		//	returns: 
		//		An hash map of all the style sheets.
		//
		//	TODO: 	Does not recursively search for @imports, so it will
		//			only go one level deep.
		//
		if(pageStyleSheets.collected) {return pageStyleSheets;}
		
		var sheets = dojo.doc.styleSheets;
		//console.log("styleSheets:", sheets);
		dojo.forEach(sheets, function(n){
			var s = (n.sheet) ? n.sheet : n;
			var name = s.title || s.href;
			if(dojo.isIE){
				// IE attaches a style sheet for VML - do not include this
				if(s.cssText.indexOf("#default#VML")==-1){
					
					
					if(s.href){
						// linked		
						pageStyleSheets[name] = s;
					
					}else if(s.imports.length){
						// Imported via @import
						dojo.forEach(s.imports, function(si){
							pageStyleSheets[si.title || si.href] = si;
						});
						
					}else{
						//embedded within page
						pageStyleSheets[name] = s;
					}
				}
				
			}else{
				//linked or embedded
				pageStyleSheets[name] = s;
				pageStyleSheets[name].id = s.ownerNode.id;
				dojo.forEach(s.cssRules, function(r){
					if(r.href){
						// imported
						pageStyleSheets[r.href] = r.styleSheet;
						pageStyleSheets[r.href].id = s.ownerNode.id;
					}
				});
			
			}
			
		});
		
		//console.log("pageStyleSheets:", pageStyleSheets);
		
		
		pageStyleSheets.collected = true;
		return pageStyleSheets;
	}
	

})();

}
