package com.objetdirect.tatami.unit.wrappers;

import java.util.Arrays;
import java.util.Iterator;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.objetdirect.tatami.unit.TestGWT;

public class TreeWrapper {

	private HtmlElement treeBaseElement;
	private HtmlElement treeContainer;
	private TreeNode root ;
	
	TestGWT testGwt;
	
	private static final String containerClass =  "dijitTreeContainer";
	
	public TreeWrapper(HtmlPage page , String id) throws Exception{
		this(page.getHtmlElementById(id));
	}
	
	public TreeWrapper(HtmlElement elem) throws Exception{
		this.treeBaseElement = elem;
		this.testGwt = new TestGWT(elem.getPage().getEnclosingWindow());
		System.out.println(treeBaseElement.asXml());
		this.treeContainer = treeBaseElement.getOneHtmlElementByAttribute("div", "wairole","tree");
	}
	
	public TreeNode getNode(int index){
		return getChildNode(getRoot(), index);
	}
	
	public static TreeNode getChildNode(TreeNode treeNode , int index){
		HtmlElement container = treeNode.getChildrenContainer();
		Iterator<HtmlElement> children = container.getChildElements().iterator();
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
	
	
	public static TreeNode getChildNodeByPath(TreeNode treeNode ,int... index){
		if(index.length == 0){
			return treeNode;
		}else if(index.length == 1){
			return TreeWrapper.getChildNode(treeNode, index[0]);
		}else{
			return getChildNodeByPath(getChildNodeByPath(treeNode,index[0]), Arrays.copyOfRange(index,1,index.length));
		}
	}
	
	
	public TreeNode getRoot(){
		if(root == null){
			root = new TreeNode((HtmlElement) treeContainer.getChildElements().iterator().next());
		}
		return root;
	}
	
	public static class TreeNode{
		
		HtmlElement nodeElement;
		
		

		HtmlElement rowElement ;
		
		TreeNode(HtmlElement element){
			nodeElement = element;
			rowElement = nodeElement.getOneHtmlElementByAttribute("div", "dojoattachpoint", "rowNode");
		}
		
		public TreeNode getChildNodeByPath(int... index){
			return TreeWrapper.getChildNodeByPath(this, index);
		}
		
		public HtmlElement getNodeElement() {
			return nodeElement;
		}
		
		public HtmlElement getExpandNode(){
			return rowElement.getOneHtmlElementByAttribute("img", "dojoattachpoint", "expandoNode");
		}
		
		public HtmlElement getNodeContent(){
			return rowElement.getOneHtmlElementByAttribute("span", "dojoattachpoint", "contentNode");
		}
		
		HtmlElement getChildrenContainer(){	
			System.out.println(nodeElement.asXml());
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
