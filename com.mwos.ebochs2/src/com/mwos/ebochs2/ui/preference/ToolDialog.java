package com.mwos.ebochs2.ui.preference;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.mwos.ebochs2.model.Tool;
import com.mwos.ebochs2.model.Toolchain;

public class ToolDialog extends Composite {
	private Text text;
	private Text text_1;
	private TableViewer tableViewer;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ToolDialog(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, false));

		Label label = new Label(this, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("名称");

		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);

		Label label_1 = new Label(this, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("位置");

		text_1 = new Text(this, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnBrowse = new Button(this, SWT.NONE);
		btnBrowse.setText("Browse...");

		tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Tool");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setWidth(200);
		tblclmnNewColumn_1.setText("Path");
		tableViewerColumn_1.setEditingSupport(new ToolPathEditing(tableViewerColumn_1.getViewer()));

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText("Args");

		tableViewer.setLabelProvider(new ToolTableLabelProvider());
		tableViewer.setContentProvider(new ToolTableContentProvider());
	}

	public ToolDialog(Toolchain toolchain, Composite parent, int style) {
		this(parent, style);
		if (toolchain != null) {
			setToolChain(toolchain);
		}
	}

	public void setToolChain(Toolchain toolchain) {
		text.setText(toolchain.getName());
		text_1.setText(toolchain.getLocation());
		Tool[] tools = toolchain.getTools().toArray(new Tool[] {});
		tableViewer.setInput(tools);
	}

	@Override
	protected void checkSubclass() {
	}

}
