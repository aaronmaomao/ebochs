package com.mwos.ebochs2.ui.model.custom;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import com.mwos.ebochs2.model.Toolchain;

public class CTree {
	private Tree tree;
	private CTreeProvider provider;

	public CTree(Tree tree) {
		this.tree = tree;
	}

	public void setProvider(CTreeProvider provider) {
		this.provider = provider;
	}

	public void addDate(Object object) {
		Toolchain tc = (Toolchain) object;
		TreeColumn[] columns = tree.getColumns();
		for (TreeColumn column : columns) {
			
			
			
		}
	}

	public void loadDate(Object object) {

	}

	public Tree getTree() {
		return tree;
	}

}
