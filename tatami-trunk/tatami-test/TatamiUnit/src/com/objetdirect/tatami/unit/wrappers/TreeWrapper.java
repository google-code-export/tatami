package com.objetdirect.tatami.unit.wrappers;

import java.util.Iterator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.HTMLElement;
import com.objetdirect.tatami.unit.TestGWT;

public class TreeWrapper {

	private HtmlElement treeBaseElement;
	private HtmlElement treeContainer;
	
	TestGWT testGwt;
	
	private static final String containerClass =  "dijitTreeContainer";
	
	public TreeWrapper(HtmlPage page , String id) throws Exception{
		this(page.getHtmlElementById(id));
	}
	
	public TreeWrapper(HtmlElement elem) throws Exception{
		this.treeBaseElement = elem;
		this.testGwt = new TestGWT(elem.getPage().getEnclosingWindow());
		this.treeContainer = treeBaseElement.getOneHtmlElementByAttribute("div", "class", containerClass);
	}
	
	public TreeNode getNode(int index){
		return getChildNode(getRoot(), index);
	}
	
	public TreeNode getChildNode(TreeNode treeNode , int index){
		HtmlElement container = treeNode.getChildrenContainer();
		Iterator<HtmlElement> children = container.getAllHtmlChildElements().iterator();
		int i =0;
		while(children.hasNext()){
			if(index == i){
				return new TreeNode(children.next());
			}
			children.next();
			i++;
		}
		return null;
	}
	
	public TreeNode getRoot(){
		return new TreeNode((HtmlElement) treeContainer.getChildElements().iterator().next());
	}
	
	public class TreeNode{
		
		HtmlElement nodeElement;
		
		HtmlElement rowElement ;
		
		TreeNode(HtmlElement element){
			nodeElement = element;
			rowElement = nodeElement.getOneHtmlElementByAttribute("div", "dojoattachpoint", "rowNode");
		}
		
		public HtmlElement getExpandNode(){
			return rowElement.getOneHtmlElementByAttribute("img", "dojoattachpoint", "expandoNode");
		}
		
		public HtmlElement getNodeContent(){
			return rowElement.getOneHtmlElementByAttribute("span", "dojoattachpoint", "contentNode");
		}
		
		HtmlElement getChildrenContainer(){	
			return nodeElement.getOneHtmlElementByAttribute("div", "class", containerClass);
		}
		
		public String getLabel(){
			return getNodeContent().getOneHtmlElementByAttribute("span", "dojoattachpoint", "labelNode").getTextContent();
		}
		
		public HtmlElement getIconNode(){
			return getNodeContent().getOneHtmlElementByAttribute("img", "dojoattachpoint", "iconNode");
		}
	}
	
}
