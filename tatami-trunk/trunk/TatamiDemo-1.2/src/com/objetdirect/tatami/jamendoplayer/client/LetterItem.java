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
		this.setValue(Tree.folderClosedClassAttribute , "letterItem");
		this.setValue(Tree.folderOpenedClassAttribute ,"letterItemOpen");
		this.setValue(Tree.labelClassAttribute ,"letterItemLabel");
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

}
