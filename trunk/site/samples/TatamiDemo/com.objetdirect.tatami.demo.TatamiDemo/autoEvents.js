

/**
 Cette methode cree un évènement souris sur un element de l'arbre DOM. 
 Util pour simuler un comportement des objets javascript intéractifs. 
 @param e Element de l'arbre DOM où l'on doit déclancher l'évènement souris
 @param eventType le type de l'évènement souris : onclick, onmousemove
 @param x position de la souris en abscisse
 @param y position de la souris en ordonnées
 @param button le bouton de la souris emetteur de l'evènement pour un click, mouse pressed, mouse released
 @param click le nombre de click 
*/

 function fireMouseEvent (e,eventType, x,y, button, click) {
    if ( document.createEvent) {
  	  var evObj = document.createEvent('MouseEvents');
	  evObj.initMouseEvent(
	  eventType,    // le type d'evenement souris
	  true,       // est-ce que l'evenement doit se propager (bubbling)?
	  true,       // est-ce que le défaut pour cet evenement peut être annulé?
	  document.defaultView,     // l' 'AbstractView' pour cet evenement
	  click,          // details -- Pour les evenements click, le nombre de clicks
	  x,          // screenX
	  y,          // screenY
	  x,          // clientX
	  y,          // clientY
   	  false,      // est-ce que la touche Ctrl est pressée?
	  false,      // est-ce que la touche Alt est pressée?
	  false,      // est-ce que la touche Shift est pressée?
	  false,      // est-ce que la touche Meta est pressée?
	  button,          // quel est le bouton préssé
	  e      // l'élement source de cet evenement
	 );


	 e.dispatchEvent(evObj);
 	 } else {	 
	 var evObj = document.createEventObject();

	 evObj.screenX = x;
	 evObj.screenY = y;
	 evObj.clientX = x;
	 evObj.clientY = y;
	 evObj.button = button;
     evObj.detail = click;
	 e.fireEvent("on" +eventType, evObj);
	 }
}

/*
vérifie que ce fichier est bien chergé dans un browser, 
méthode obsolète pour une version supérieur à 1.3 de GWT
*/
function autoEventsIsOk() {
 //ne fait rien
}
