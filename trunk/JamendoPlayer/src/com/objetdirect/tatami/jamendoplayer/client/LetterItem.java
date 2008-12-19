package com.objetdirect.tatami.jamendoplayer.client;


import com.objetdirect.tatami.client.tree.Tree;

public class LetterItem extends SearchTreeItem {

	public static final String type="letter";
	public static final String letterAttr="letter";
	
	private String letter;


	public String getLetter() {
		return letter;
	}

	public LetterItem(String letter){
		setId("Letter"+letter);
		setLabel(letter);
		this.letter = letter;
		this.setValue(typeKey, 	type);
		this.setValue(Tree.labelClassAttribute ,"letterItemLabel");
		this.setValue(Tree.folderClosedClassAttribute ,"dijitFolderClosed");
		this.setValue(Tree.folderOpenedClassAttribute ,"dijitFolderOpened");
		this.setValue(Tree.leafClassAttribute ,"dijitFolderClosed");
	}
	
	
	@Override
	public String getLabel() {
		return (String) attributes.get(letterAttr);
	}

	@Override
	public String[] getLabelAttributes() {
		return new String[]{letterAttr};
	}

	@Override
	public void setLabel(String label) {
		attributes.put(letterAttr, label);
	}

	public String getLabelAttribute(){
		return letterAttr;
	}
	
	
}
