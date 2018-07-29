package com.mwos.ebochs.ui.view.model.host;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.mwos.ebochs.core.vm.bochs.DebugModel;

public abstract class Node extends TreeItem{
	
	protected DebugModel dm;

	public DebugModel getDm() {
		return dm;
	}

	public Node(Tree parent, int style) {
		super(parent, style);
	}

	public Node(Node parent,int style) {
		super(parent, style);
	}
	
	@Override
	protected void checkSubclass() {
	}
	
	public abstract void showMenu();
	
	
}
