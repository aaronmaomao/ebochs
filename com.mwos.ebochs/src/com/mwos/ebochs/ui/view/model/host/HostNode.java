package com.mwos.ebochs.ui.view.model.host;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;

public class HostNode extends Node {

	private String machineName;
	private String location;
	private String cpu;
	private String memory;
	private String status;

	public HostNode(Tree parent, int style) {
		super(parent, style);
	}

	@Override
	public void showMenu() {
		Menu menu = new Menu(this.getParent());
		MenuItem newItem = new MenuItem(menu, SWT.PUSH);
		newItem.setText("新增");

		MenuItem newMemberItem = new MenuItem(menu, SWT.PUSH);
		newMemberItem.setText("新增2");

		MenuItem editItem = new MenuItem(menu, SWT.PUSH);
		editItem.setText("编辑");

		MenuItem deleteItem = new MenuItem(menu, SWT.PUSH);
		deleteItem.setText("删除");
		this.getParent().setMenu(menu);

	}
}
