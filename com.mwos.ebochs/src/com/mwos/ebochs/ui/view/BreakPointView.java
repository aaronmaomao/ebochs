package com.mwos.ebochs.ui.view;

import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.mwos.ebochs.core.model.IInfoListener;
import com.mwos.ebochs.core.model.InfoCenter;
import com.mwos.ebochs.core.model.InfoCmd;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.swt.widgets.TableItem;

public class BreakPointView extends ViewPart implements IInfoListener{

	public static final String ID = "com.mwos.ebochs.ui.view.BreakPointView"; //$NON-NLS-1$
	private Table table;
	private InfoCenter infoCenter = InfoCenter.getInfoCenter();;

	public BreakPointView() {
		infoCenter.addListener(this);
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		{
			CheckboxTableViewer checkboxTableViewer = CheckboxTableViewer.newCheckList(parent, SWT.BORDER | SWT.FULL_SELECTION);
			checkboxTableViewer.setAllGrayed(true);
			checkboxTableViewer.setAllChecked(true);
			table = checkboxTableViewer.getTable();
			table.setHeaderVisible(true);
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(checkboxTableViewer, SWT.NONE);
				TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
				tblclmnNewColumn.setWidth(50);
				tblclmnNewColumn.setText("状态");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(checkboxTableViewer, SWT.NONE);
				TableColumn tblclmnNewColumn_1 = tableViewerColumn.getColumn();
				tblclmnNewColumn_1.setWidth(100);
				tblclmnNewColumn_1.setText("物理地址");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(checkboxTableViewer, SWT.NONE);
				TableColumn tblclmnNewColumn_2 = tableViewerColumn.getColumn();
				tblclmnNewColumn_2.setWidth(100);
				tblclmnNewColumn_2.setText("位置");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(checkboxTableViewer, SWT.NONE);
				TableColumn tblclmnNewColumn_3 = tableViewerColumn.getColumn();
				tblclmnNewColumn_3.setWidth(100);
				tblclmnNewColumn_3.setText("方法");
			}
			{
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] {"a", "b", "c", "f"});
			}
			{
				TableItem tableItem = new TableItem(table, 0);
				tableItem.setText(new String[] {"a", "b", "c", "f"});
			}
		}

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
	public void notify(Object info) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notify(String cmd, Object info) {
		// TODO Auto-generated method stub
		
	}
	
	public void init() {
		if(!infoCenter.isActive()) {
			return;
		}
		
		infoCenter.synSend(InfoCmd.BP_Get);
	}

	@Override
	public void dispose() {
		super.dispose();
		infoCenter.removeListener(this);
	}
}
