package com.mwos.ebochs2.test;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.mwos.ebochs2.model.Toolchain;

import org.eclipse.swt.widgets.TableItem;
import swing2swt.layout.BorderLayout;
import org.eclipse.wb.swt.SWTResourceManager;

public class UITest {

	protected Shell shell;
	private Text txt;
	private Text txtLocation;
	private Table table;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UITest window = new UITest();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(457, 363);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		CTabFolder tabFolder = new CTabFolder(shell, SWT.BORDER);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		CTabItem tbtmNewItem = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("New Item");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem.setControl(composite);
		composite.setLayout(new GridLayout(3, false));

		Label label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("名称");

		txt = new Text(composite, SWT.BORDER);
		txt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite, SWT.NONE);

		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setText("位置");

		txtLocation = new Text(composite, SWT.BORDER);
		txtLocation.setEditable(false);
		txtLocation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.setText("Browse...");

		TableViewer tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnTool = tableViewerColumn.getColumn();
		tblclmnTool.setWidth(100);
		tblclmnTool.setText("Tool");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnLocation = tableViewerColumn_1.getColumn();
		tblclmnLocation.setWidth(200);
		tblclmnLocation.setText("Path");
		tableViewerColumn_1.setEditingSupport(new ToolEditSupport(tableViewerColumn_1.getViewer()));
		
		tableViewer.setLabelProvider(new LabelProvider());
		tableViewer.setContentProvider(new ContentProvider());
		tableViewer.setCellModifier(new CellModify());
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn_2.getColumn();
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Args");
		
		CTabItem tbtmNewItem_1 = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem_1.setText("New Item");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		
		tbtmNewItem_1.setControl(composite_1);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblNewLabel = new Label(composite_1, SWT.BORDER | SWT.CENTER);
		lblNewLabel.setText("1");
		lblNewLabel.addListener(SWT.Resize, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				System.out.println("aaa");
				
			}
		});
		
		lblNewLabel.setBounds(10, 10, 200, 100);
		lblNewLabel.setSize(20, 300);
		lblNewLabel.computeSize(200, 20);
		lblNewLabel.setImage(SWTResourceManager.getImage("/Users/admin/Desktop/icon.png"));
		lblNewLabel.pack();
		
		tableViewer.setInput(Toolchain.get());
	}
}
