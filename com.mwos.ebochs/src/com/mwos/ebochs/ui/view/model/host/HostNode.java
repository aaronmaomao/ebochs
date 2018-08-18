package com.mwos.ebochs.ui.view.model.host;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;

import com.mwos.ebochs.core.vm.bochs.DebugModel;

public class HostNode extends Node {

	private String machineName;
	private String location;
	private String cpu;
	private String memory;

	public HostNode(Tree parent, int style) {
		super(parent, style);
	}

	public void setDM(DebugModel dm) {
		this.dm = dm;
	}
	
	public DebugModel getDM() {
		return dm;
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

	@Override
	public void dispose() {
		dm = null;
		super.dispose();
	}
}
