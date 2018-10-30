package com.mwos.ebochs2.ui.preference;

import javax.swing.table.TableCellEditor;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.internal.UIPlugin;
import org.eclipse.wb.swt.ResourceManager;

import com.mwos.ebochs2.Activator;
import com.mwos.ebochs2.model.Toolchain;
import com.mwos.ebochs2.ui.model.custom.CTree;
import com.mwos.ebochs2.ui.model.custom.CTreeProvider;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;

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
		GridLayout gl_container = new GridLayout(1, false);
		gl_container.verticalSpacing = 0;
		gl_container.marginWidth = 0;
		gl_container.horizontalSpacing = 0;
		gl_container.marginHeight = 0;
		container.setLayout(gl_container);

		ToolBar toolBar = new ToolBar(container, SWT.FLAT | SWT.WRAP | SWT.RIGHT | SWT.SHADOW_OUT);

		ToolItem tltmNewItem = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showAddDialog();
			}
		});
		tltmNewItem.setImage(ResourceManager.getPluginImage("com.mwos.ebochs2", "resource/easyui/themes/icons/edit_add.png"));

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

		TreeEditor treeEditor = new TreeEditor(tree);
		treeEditor.horizontalAlignment = SWT.LEFT;

		return container;
	}

	/**
	 * Initialize the preference page.
	 */
	public void init(IWorkbench workbench) {
		// Initialize the preference page
	}

	private void initTreeData() {

	}

	private void showAddDialog() {
		Dialog dialog = new Dialog(Activator.getDefault().getWorkbench().getModalDialogShellProvider()) {

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
				Composite composite = (Composite) super.createDialogArea(parent);
				composite.setLayout(new GridLayout(3, false));

				Label label = new Label(composite, SWT.NONE);
				label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
				label.setText("名称");

				Text text = new Text(composite, SWT.BORDER);
				text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				new Label(composite, SWT.NONE);

				Label label_1 = new Label(composite, SWT.NONE);
				label_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
				label_1.setText("位置");

				Text text_1 = new Text(composite, SWT.BORDER);
				text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

				Button btnBrowse = new Button(composite, SWT.NONE);
				btnBrowse.setText("Browse...");

				Table table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
				table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
				table.setHeaderVisible(true);
				table.setLinesVisible(true);

				TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
				tblclmnName.setWidth(100);
				tblclmnName.setText("Name");

				TableColumn tblclmnLocation = new TableColumn(table, SWT.NONE);
				tblclmnLocation.setWidth(230);
				tblclmnLocation.setText("Location");

				TableEditor tableEditor = new TableEditor(table);
				tableEditor.grabHorizontal = true;
				
				TableItem item0 = new TableItem(table, SWT.NONE);
				item0.setText(new String[] { "编译器", "" });
				Combo combo = new Combo(table, SWT.NONE);
				combo.setSize(SWT.DEFAULT, 10);
				combo.setItems(new String[] {"gcc.ece", "abd.exe"});
				tableEditor.setEditor(combo, item0, 1);
				
				
				TableItem item1 = new TableItem(table, SWT.NONE);
				item1.setText(new String[] { "编译器", "" });
				TableItem item2 = new TableItem(table, SWT.NONE);
				item2.setText(new String[] { "编译器", "" });
				return composite;
			}
		};
		dialog.open();
	}
}
