package com.mwos.ebochs.ui.view;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;

import com.mwos.ebochs.core.model.IInfoListener;
import com.mwos.ebochs.core.model.InfoCenter;

public class CPUView extends ViewPart implements IInfoListener {

	public static final String ID = "com.mwos.ebochs.ui.view.CPUView"; //$NON-NLS-1$
	private Table table;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Table table_1;
	private InfoCenter infoCenter = InfoCenter.getInfoCenter();;

	public CPUView() {
		infoCenter.addListener(this);
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		ExpandBar expandBar = new ExpandBar(parent, SWT.V_SCROLL);
		expandBar.setToolTipText("");

		ExpandItem genRegExp = new ExpandItem(expandBar, SWT.NONE);
		genRegExp.setExpanded(true);
		genRegExp.setText("通用寄存器");

		Composite composite = new Composite(expandBar, SWT.NONE);
		genRegExp.setControl(composite);
		TableColumnLayout tcl_composite = new TableColumnLayout();
		composite.setLayout(tcl_composite);

		TableViewer tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.setColumnProperties(new String[] {});
		table = tableViewer.getTable();
		table.setToolTipText("asd");
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tcl_composite.setColumnData(tblclmnNewColumn, new ColumnPixelData(60, true, true));
		tblclmnNewColumn.setText("寄存器");

		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tcl_composite.setColumnData(tblclmnNewColumn_1, new ColumnPixelData(120, true, true));
		tblclmnNewColumn_1.setText("值");

		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tcl_composite.setColumnData(tblclmnNewColumn_2, new ColumnPixelData(60, true, true));
		tblclmnNewColumn_2.setText("寄存器");

		TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
		tcl_composite.setColumnData(tblclmnNewColumn_3, new ColumnPixelData(120, true, true));
		tblclmnNewColumn_3.setText("值");

		TableItem tableItem = new TableItem(table, SWT.NONE);
		tableItem.setText(new String[] { "eax", "0x00000000", "ebp", "0x00000000" });

		TableItem tableItem_1 = new TableItem(table, 0);
		tableItem_1.setText(new String[] { "ebx", "0x00000000", "esp", "0x00000000" });

		TableItem tableItem_2 = new TableItem(table, 0);
		tableItem_2.setImage(2, ResourceManager.getPluginImage("com.mwos.ebochs", "resource/icons/basket.png"));
		tableItem_2.setText(new String[] { "ecx", "0x00000000", "esi", "0x00000000" });

		TableItem tableItem_3 = new TableItem(table, 0);
		tableItem_3.setText(new String[] { "edx", "0x00000000", "edi", "0x00000000" });

		TableItem tableItem_4 = new TableItem(table, 0);
		tableItem_4.setText(new String[] {});

		TableItem tableItem_5 = new TableItem(table, 0);
		tableItem_5.setText(new String[] { "cs", "0xf000", "eip", "0x00000000" });

		TableItem tableItem_6 = new TableItem(table, 0);
		tableItem_6.setText(new String[] { "ss", "0x0000", "esp", "0x00000000" });

		TableItem tableItem_7 = new TableItem(table, 0);
		tableItem_7.setText(new String[] { "ds", "0x0000", "fs", "0x00000000" });

		TableItem tableItem_8 = new TableItem(table, 0);
		tableItem_8.setText(new String[] { "es", "0x0000", "gs", "0x00000000" });
		genRegExp.setHeight(genRegExp.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);

		ExpandItem segRegExp = new ExpandItem(expandBar, SWT.NONE);
		segRegExp.setExpanded(true);
		segRegExp.setText("控制寄存器");

		Composite composite_1 = new Composite(expandBar, SWT.NONE);
		segRegExp.setControl(composite_1);
		formToolkit.adapt(composite_1);
		formToolkit.paintBordersFor(composite_1);
		TableColumnLayout tcl_composite_1 = new TableColumnLayout();
		composite_1.setLayout(tcl_composite_1);

		table_1 = new Table(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		formToolkit.adapt(table_1);
		formToolkit.paintBordersFor(table_1);
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);

		TableColumn tableColumn = new TableColumn(table_1, SWT.NONE);
		tableColumn.setText("寄存器");
		tcl_composite_1.setColumnData(tableColumn, new ColumnPixelData(60, true, true));

		TableColumn tableColumn_1 = new TableColumn(table_1, SWT.NONE);
		tableColumn_1.setText("值");
		tcl_composite_1.setColumnData(tableColumn_1, new ColumnPixelData(120, true, true));

		TableColumn tableColumn_2 = new TableColumn(table_1, SWT.NONE);
		tableColumn_2.setText("寄存器");
		tcl_composite_1.setColumnData(tableColumn_2, new ColumnPixelData(60, true, true));

		TableColumn tableColumn_3 = new TableColumn(table_1, SWT.NONE);
		tableColumn_3.setText("值");
		tcl_composite_1.setColumnData(tableColumn_3, new ColumnPixelData(120, true, true));

		TableItem tableItem_9 = new TableItem(table_1, 0);
		tableItem_9.setText(new String[] { "eax", "0x00000000", "ebp", "0x00000000" });
		segRegExp.setHeight(segRegExp.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	public void notify(String rec) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notify(String cmd, String rec) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}
	
}
