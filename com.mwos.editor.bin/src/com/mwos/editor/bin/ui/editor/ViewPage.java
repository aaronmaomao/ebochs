package com.mwos.editor.bin.ui.editor;

import org.eclipse.swt.widgets.Composite;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;

import com.mwos.editor.bin.service.BinService;

import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * 
 * @author maozhengjun
 * @time 2019年4月23日 下午7:17:42
 */
public class ViewPage extends Composite {
	private Table table;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ViewPage(BinService service, Composite parent, int style) {
		super(parent, style);
		setLayout(new BorderLayout(0, 0));
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(BorderLayout.NORTH);
		
		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.CENTER);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TableViewer tableViewer = new TableViewer(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setFont(SWTResourceManager.getFont("Consolas", 10, SWT.NORMAL));
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		ByteCellEditorSupport byteCellEditorSupport = new ByteCellEditorSupport(tableViewer);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setWidth(72);
		tblclmnNewColumn.setText("地址");
		
		TableViewerColumn tableViewerColumn_0 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_0 = tableViewerColumn_0.getColumn();
		tblclmnNewColumn_0.setWidth(35);
		tblclmnNewColumn_0.setText("0");
		tableViewerColumn_0.setEditingSupport(byteCellEditorSupport);
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setWidth(35);
		tblclmnNewColumn_1.setText("1");
		tableViewerColumn_1.setEditingSupport(byteCellEditorSupport);
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_2.setWidth(35);
		tblclmnNewColumn_2.setText("2");
		tableViewerColumn_2.setEditingSupport(byteCellEditorSupport);
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_3 = tableViewerColumn_3.getColumn();
		tblclmnNewColumn_3.setWidth(35);
		tblclmnNewColumn_3.setText("3");
		tableViewerColumn_3.setEditingSupport(byteCellEditorSupport);
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumn = tableViewerColumn_4.getColumn();
		tableColumn.setWidth(35);
		tableColumn.setText("4");
		tableViewerColumn_4.setEditingSupport(byteCellEditorSupport);
		
		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumn_1 = tableViewerColumn_5.getColumn();
		tableColumn_1.setWidth(35);
		tableColumn_1.setText("5");
		tableViewerColumn_5.setEditingSupport(byteCellEditorSupport);
		
		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumn_2 = tableViewerColumn_6.getColumn();
		tableColumn_2.setWidth(35);
		tableColumn_2.setText("7");
		tableViewerColumn_6.setEditingSupport(byteCellEditorSupport);
		
		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumn_3 = tableViewerColumn_7.getColumn();
		tableColumn_3.setWidth(35);
		tableColumn_3.setText("8");
		tableViewerColumn_7.setEditingSupport(byteCellEditorSupport);
		
		TableViewerColumn tableViewerColumn_8 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumn_4 = tableViewerColumn_8.getColumn();
		tableColumn_4.setWidth(35);
		tableColumn_4.setText("9");
		tableViewerColumn_8.setEditingSupport(byteCellEditorSupport);
		
		TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumn_5 = tableViewerColumn_9.getColumn();
		tableColumn_5.setWidth(35);
		tableColumn_5.setText("A");
		tableViewerColumn_9.setEditingSupport(byteCellEditorSupport);
		
		TableViewerColumn tableViewerColumn_10 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumn_6 = tableViewerColumn_10.getColumn();
		tableColumn_6.setWidth(35);
		tableColumn_6.setText("B");
		tableViewerColumn_10.setEditingSupport(byteCellEditorSupport);
		
		TableViewerColumn tableViewerColumn_11 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumn_7 = tableViewerColumn_11.getColumn();
		tableColumn_7.setWidth(35);
		tableColumn_7.setText("C");
		tableViewerColumn_11.setEditingSupport(byteCellEditorSupport);
		
		TableViewerColumn tableViewerColumn_12 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumn_8 = tableViewerColumn_12.getColumn();
		tableColumn_8.setWidth(35);
		tableColumn_8.setText("D");
		tableViewerColumn_12.setEditingSupport(byteCellEditorSupport);
		
		TableViewerColumn tableViewerColumn_13 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumn_9 = tableViewerColumn_13.getColumn();
		tableColumn_9.setWidth(35);
		tableColumn_9.setText("E");
		tableViewerColumn_13.setEditingSupport(byteCellEditorSupport);
		
		TableViewerColumn tableViewerColumn_14 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumn_10 = tableViewerColumn_14.getColumn();
		tableColumn_10.setWidth(35);
		tableColumn_10.setText("F");
		tableViewerColumn_14.setEditingSupport(byteCellEditorSupport);
		
		TableViewerColumn tableViewerColumn_15 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_4 = tableViewerColumn_15.getColumn();
		tblclmnNewColumn_4.setWidth(110);
		tblclmnNewColumn_4.setText("文本");
		
		tableViewer.setContentProvider(new ByteContentProvider());
		tableViewer.setLabelProvider(new ByteLabelProvider());
		tableViewer.setInput(service.refresh());
	}

	@Override
	protected void checkSubclass() {
	}
}
