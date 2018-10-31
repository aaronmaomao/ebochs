package com.mwos.ebochs2;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class UITest {

	protected Shell shell;
	private Text text;
	private Text text_1;
	private Table table;

	/**
	 * Launch the application.
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
		shell.setSize(549, 492);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		CTabFolder tabFolder = new CTabFolder(shell, SWT.NONE);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText("New Item");
		
		Composite composite = new Composite(tabFolder, SWT.BORDER);
		tabItem.setControl(composite);
		composite.setLayout(new GridLayout(3, false));
		
		Label label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("名称");
		
		text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite, SWT.NONE);
		
		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("位置");
		
		text_1 = new Text(composite, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnBrowse = new Button(composite, SWT.NONE);
		btnBrowse.setText("Browse...");
		
		TableViewer tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		tableViewer.setContentProvider(new IStructuredContentProvider() {
			
			@Override
			public Object[] getElements(Object inputElement) {
				// TODO Auto-generated method stub
				return null;
			}
			
		});

		CellEditor[] cellEditor = new CellEditor[4];
		cellEditor[0] = new TextCellEditor();
		cellEditor[1] = new TextCellEditor();
		cellEditor[2] = new TextCellEditor();
		cellEditor[3] = new TextCellEditor();
		tableViewer.setColumnProperties(new String[] {"Tool","Location"});
		tableViewer.setCellEditors(cellEditor);
		tableViewer.setCellModifier(new ICellModifier() {
			
			@Override
			public void modify(Object element, String property, Object value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object getValue(Object element, String property) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean canModify(Object element, String property) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnTool = tableViewerColumn.getColumn();
		tblclmnTool.setWidth(100);
		tblclmnTool.setText("Tool");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnLocation = tableViewerColumn_1.getColumn();
		tblclmnLocation.setWidth(200);
		tblclmnLocation.setText("Location");
		EditingSupport editingSupport = new EditingSupport(tableViewerColumn_1.getViewer()) {
			
			@Override
			protected void setValue(Object element, Object value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected Object getValue(Object element) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			protected CellEditor getCellEditor(Object element) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			protected boolean canEdit(Object element) {
				// TODO Auto-generated method stub
				return false;
			}
		};

	//	tableViewer.setColumnViewerEditor(columnViewerEditor);
	//	tableViewer.setInput(new String[] {"编译器","C://d.exe"});
		
		CTabItem tbtmNewItem = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("New Item");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem.setControl(composite_1);

	}
}
