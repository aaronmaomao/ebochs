package com.mwos.ebochs.ui.view.model.host;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
	private String name = "";
	private String text = "";
	private Node parent;
	private List<Node> childs;

	public Node() {
		// TODO Auto-generated constructor stub
		childs = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public List<Node> getChilds() {
		return childs;
	}

	public void addChild(Node node) {
		this.childs.add(node);
		node.setParent(this);
	}

	public void removeChild(Node node) {
		this.childs.remove(node);
	}
}
