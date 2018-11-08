package com.mwos.ebochs2.ui.preference;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.wb.swt.ResourceManager;

import com.mwos.ebochs2.Activator;
import com.mwos.ebochs2.model.Tool;
import com.mwos.ebochs2.model.Toolchain;
import org.eclipse.swt.widgets.Label;

public class OSPreferencePage1 extends PreferencePage implements IWorkbenchPreferencePage {

	private TreeViewer treeViewer;
	private Toolchain[] allTC;
	private Toolchain defaultTC;

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
		GridLayout gl_container = new GridLayout(1, false);
		gl_container.verticalSpacing = 0;
		gl_container.marginWidth = 0;
		gl_container.horizontalSpacing = 0;
		gl_container.marginHeight = 0;
		container.setLayout(gl_container);

		ToolBar toolBar = new ToolBar(container, SWT.FLAT | SWT.RIGHT);

		ToolItem tltmNewItem = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showAddDialog(null);
			}
		});
		tltmNewItem.setImage(ResourceManager.getPluginImage("com.mwos.ebochs2", "resource/easyui/themes/icons/edit_add.png"));

		ToolItem toolItem = new ToolItem(toolBar, SWT.NONE);
		toolItem.setImage(ResourceManager.getPluginImage("com.mwos.ebochs2", "resource/easyui/themes/icons/edit_remove.png"));

		ToolItem toolItem_1 = new ToolItem(toolBar, SWT.NONE);
		toolItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Object select = treeViewer.getStructuredSelection().getFirstElement();
				if (select != null && select instanceof Toolchain) {
					showAddDialog((Toolchain) select);
				}
			}
		});
		toolItem_1.setImage(ResourceManager.getPluginImage("com.mwos.ebochs2", "resource/easyui/themes/icons/pencil.png"));

		treeViewer = new TreeViewer(container, SWT.BORDER);
		Tree tree = treeViewer.getTree();
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);

		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TreeViewerColumn treeViewerColumn_1 = new TreeViewerColumn(treeViewer, SWT.NONE);
		TreeColumn trclmnNewColumn = treeViewerColumn_1.getColumn();
		trclmnNewColumn.setWidth(150);
		trclmnNewColumn.setText("Name");

		TreeViewerColumn treeViewerColumn_2 = new TreeViewerColumn(treeViewer, SWT.NONE);
		TreeColumn trclmnNewColumn_1 = treeViewerColumn_2.getColumn();
		trclmnNewColumn_1.setWidth(150);
		trclmnNewColumn_1.setText("Tools");

		treeViewer.setContentProvider(new TCTreeContentProvider());
		treeViewer.setLabelProvider(new TCTreeLabelProvider());

		initTreeData();

		return container;
	}

	@Override
	protected void performApply() {
		Object input = treeViewer.getInput();
		if (input instanceof Toolchain[]) {
		}
	}

	@Override
	public boolean performOk() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Initialize the preference page.
	 */
	public void init(IWorkbench workbench) {
		allTC = OSPreference.getAllTc();
		defaultTC = OSPreference.getSelectTc();
	}

	private void initTreeData() {
		if (allTC != null) {
			treeViewer.setInput(Toolchain.get());
		}
	}

	private void setCheckedTC(String name) {

	}

	private void showAddDialog(Toolchain toolchain) {
		Dialog dialog = new Dialog(Activator.getDefault().getWorkbench().getModalDialogShellProvider()) {
			private ToolDialog toolDialog;

			@Override
			protected boolean isResizable() {
				return true;
			}

			@Override
			protected Point getInitialSize() {
				return new Point(500, 400);
			}

			@Override
			protected Control createDialogArea(Composite parent) {
				toolDialog = new ToolDialog(toolchain, parent, SWT.NONE);
				toolDialog.setLayoutData(new GridData(GridData.FILL_BOTH));
				return toolDialog;
			}
			
			@Override
			protected void okPressed() {
				this.getButton(IDialogConstants.OK_ID).setEnabled(false);;
			}
			
		};
		dialog.open();
	}
}
