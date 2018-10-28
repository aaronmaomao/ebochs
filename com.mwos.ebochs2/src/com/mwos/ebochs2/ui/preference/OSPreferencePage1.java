package com.mwos.ebochs2.ui.preference;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.wb.swt.ResourceManager;

import com.mwos.ebochs2.model.Toolchain;
import com.mwos.ebochs2.ui.model.custom.CTree;

public class OSPreferencePage1 extends PreferencePage implements IWorkbenchPreferencePage {

	/**
	 * Create the preference page.
	 */
	public OSPreferencePage1() {
	}

	/**
	 * Create contents of the preference page.
	 * 
	 * @param parent
	 */
	@Override
	public Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(1, false));

		Label lblCompiler = new Label(container, SWT.NONE);
		lblCompiler.setText("Toolchain");

		ToolBar toolBar = new ToolBar(container, SWT.FLAT | SWT.RIGHT);

		ToolItem tltmNewItem = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem.setImage(ResourceManager.getPluginImage("com.mwos.ebochs2", "resource/easyui/themes/icons/edit_add.png"));

		ToolItem tltmNewItem_1 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_1.setImage(ResourceManager.getPluginImage("com.mwos.ebochs2", "resource/easyui/themes/icons/edit_remove.png"));

		Tree tree = new Tree(container, SWT.BORDER);
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TreeColumn treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setWidth(30);

		TreeColumn trclmnName = new TreeColumn(tree, SWT.NONE);
		trclmnName.setWidth(100);
		trclmnName.setText("Name");

		TreeColumn trclmnTool = new TreeColumn(tree, SWT.NONE);
		trclmnTool.setWidth(150);
		trclmnTool.setText("Tool");

		CTree cTree = new CTree(tree);
		cTree.addDate(Toolchain.get());
		return container;
	}

	/**
	 * Initialize the preference page.
	 */
	public void init(IWorkbench workbench) {
		// Initialize the preference page
	}

}
