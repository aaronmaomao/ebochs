package com.mwos.ebochs.ui.view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;

public class A {

	protected Shell shell;
	private Table table;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			A window = new A();
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
		shell.setImage(ResourceManager.getPluginImage("com.mwos.ebochs", "resource/icons/arrow_refresh_small.png"));
		shell.setSize(543, 561);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(1, false));
		
		TableViewer tableViewer = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		tableViewerColumn.setEditingSupport(new EditingSupport(tableViewer) {
			protected boolean canEdit(Object element) {
				// TODO Auto-generated method stub
				return false;
			}
			protected CellEditor getCellEditor(Object element) {
				// TODO Auto-generated method stub
				return null;
			}
			protected Object getValue(Object element) {
				// TODO Auto-generated method stub
				return null;
			}
			protected void setValue(Object element, Object value) {
				// TODO Auto-generated method stub
			}
		});
		TableColumn tcReg1 = tableViewerColumn.getColumn();
		tcReg1.setWidth(60);
		tcReg1.setText("寄存器");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		
		TableColumn tcValue1 = tableViewerColumn_1.getColumn();
		tcValue1.setWidth(100);
		tcValue1.setText("值");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tcReg2 = tableViewerColumn_2.getColumn();
		tcReg2.setWidth(60);
		tcReg2.setText("寄存器");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tcValue2 = tableViewerColumn_3.getColumn();
		tcValue2.setWidth(100);
		tcValue2.setText("值");
		
		TableItem tiEax = new TableItem(table, SWT.NONE);
		tiEax.setText(new String[] {"eax", "0x00000000", "ebp", "0x00000000"});
		
		TableItem tiEcx = new TableItem(table, 0);
		tiEcx.setText(new String[] {"ecx", "0x00000000", "esp", "0x00000000"});
		
		TableItem tiEdx = new TableItem(table, 0);
		tiEdx.setText(new String[] {"edx", "0x00000000", "esi", "0x00000000"});
		
		TableItem tiEbx = new TableItem(table, 0);
		tiEbx.setText(new String[] {"ebx", "0x00000000", "edi", "0x00000000"});
		
		TableItem tableItem_4 = new TableItem(table, 0);
		tableItem_4.setText(new String[] {});
		
		TableItem tiCs = new TableItem(table, 0);
		tiCs.setText(new String[] {"cs", "0x0000", "eip", "0x00000000"});
		
		TableItem tiSs = new TableItem(table, 0);
		tiSs.setText(new String[] {"ss", "0x0000", "esp", "0x00000000"});
		
		TableItem tiDs = new TableItem(table, 0);
		tiDs.setText(new String[] {"ds", "0x0000", "es", "0x0000"});
		
		TableItem tiFs = new TableItem(table, 0);
		tiFs.setText(new String[] {"fs", "0x0000", "gs", "0x0000"});

	}
}
