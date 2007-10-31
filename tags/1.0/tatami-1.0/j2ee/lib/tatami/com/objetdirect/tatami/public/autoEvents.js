

/**
 Cette methode cree un �v�nement souris sur un element de l'arbre DOM. 
 Util pour simuler un comportement des objets javascript int�ractifs. 
 @param e Element de l'arbre DOM o� l'on doit d�clancher l'�v�nement souris
 @param eventType le type de l'�v�nement souris : onclick, onmousemove
 @param x position de la souris en abscisse
 @param y position de la souris en ordonn�es
 @param button le bouton de la souris emetteur de l'ev�nement pour un click, mouse pressed, mouse released
 @param click le nombre de click 
*/

 function fireMouseEvent (e,eventType, x,y, button, click) {
    if ( document.createEvent) {
  	  var evObj = document.createEvent('MouseEvents');
	  evObj.initMouseEvent(
	  eventType,    // le type d'evenement souris
	  true,       // est-ce que l'evenement doit se propager (bubbling)?
	  true,       // est-ce que le d�faut pour cet evenement peut �tre annul�?
	  document.defaultView,     // l' 'AbstractView' pour cet evenement
	  click,          // details -- Pour les evenements click, le nombre de clicks
	  x,          // screenX
	  y,          // screenY
	  x,          // clientX
	  y,          // clientY
   	  false,      // est-ce que la touche Ctrl est press�e?
	  false,      // est-ce que la touche Alt est press�e?
	  false,      // est-ce que la touche Shift est press�e?
	  false,      // est-ce que la touche Meta est press�e?
	  button,          // quel est le bouton pr�ss�
	  e      // l'�lement source de cet evenement
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
v�rifie que ce fichier est bien cherg� dans un browser, 
m�thode obsol�te pour une version sup�rieur � 1.3 de GWT
*/
function autoEventsIsOk() {
 //ne fait rien
}
