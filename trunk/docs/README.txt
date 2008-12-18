To edit tatami documentation, you will need LYX :

http://www.lyx.org/

You will need to set the lyx html export properties if you want to update the html output.
Go to the LYX settings menu, and configure the latex to HTML exporter:
the converter text box should be filled with: 
htlatex $$i "html,4,frames"
Then, click on modify, apply and save.

In the document, two "branches" are defined: pdf and html.
You must disable the "html" branch if you are exporting to PDF.
Go to the Document/Properties menu, then in the "branches" item, disable the
HTML branch.

Please email me if you are experiencing trouble with the documentation:
	rdunklau@gmail.com

