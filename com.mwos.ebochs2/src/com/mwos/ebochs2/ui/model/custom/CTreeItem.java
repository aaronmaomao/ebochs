package com.mwos.ebochs2.ui.model.custom;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TreeItem;

public class CTreeItem {
	private TreeItem item;
	private CTree cTree;
	private List<CTreeItem> childs;
	private CTreeItem parent;

	public CTreeItem() {
	}

	public CTreeItem(CTreeItem parent) {
		super();
		this.parent = parent;
	}

	public CTreeItem(CTree cTree) {
		this.cTree = cTree;
		this.parent = this;
	}

	public CTreeItem getParent() {
		return parent;
	}

	public TreeItem getItem() {
		return item;
	}

	public void show() {
		CTreeItem cti = this;
		while (cti != cti.getParent()) {
			cti = cti.getParent();
		}
		if (cti.getItem() == null || cti.getItem().isDisposed()) {
			recure(cti, true);
		}
	}

	private void recure(CTreeItem citem, boolean isRoot) {
		TreeItem item;
		if (isRoot)
			item = new TreeItem(citem.cTree.getTree(), SWT.NONE);
		else
			item = new TreeItem(citem.item, SWT.NONE);
		citem.item = item;
		for (CTreeItem child : citem.childs) {
			recure(child, false);
		}
	}
}
