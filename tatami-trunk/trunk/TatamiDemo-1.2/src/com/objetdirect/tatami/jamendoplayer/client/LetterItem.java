package com.objetdirect.tatami.jamendoplayer.client;

import com.objetdirect.tatami.client.tree.Tree;

public class LetterItem extends SearchTreeItem {

	private int nbChildPagesAlreadyLoaded = 1;
	private String[] letters;
	private int currentLetterRequested = 0;
	
	public int getCurrentLetterRequested() {
		return currentLetterRequested;
	}

	public void setCurrentLetterRequested(int currentLetterRequested) {
		this.currentLetterRequested = currentLetterRequested;
	}

	public int getNbChildPagesAlreadyLoaded() {
		return nbChildPagesAlreadyLoaded;
	}

	public void setNbChildPagesAlreadyLoaded(int nbChildPagesAlreadyLoaded) {
		this.nbChildPagesAlreadyLoaded = nbChildPagesAlreadyLoaded;
	}

	public String[] getLetters() {
		return letters;
	}

	public LetterItem(String[] letters){
		String label = letters.length > 1 ? letters[0] + "-" + letters[letters.length -1] : letters[0]; 
		setLabel(label);
		setId("Letter"+label);
		this.letters = letters;
		this.setValue("type", 	"letter");
		loadingItem = new LoadingItem("Loading artists ...","__ARTISTLOADING__");
		this.setValue(Tree.folderClosedClassAttribute , "letterItem");
		this.setValue(Tree.folderOpenedClassAttribute ,"letterItemOpen");
		this.setValue(Tree.labelClassAttribute ,"letterItemLabel");
		this.addChild(loadingItem);
	}
	
	public void removeLoadingChild(){
		this.removeChild(loadingItem);
	}
	
	public void addLoadingChild(){
		this.addChild(loadingItem);
	}

}
