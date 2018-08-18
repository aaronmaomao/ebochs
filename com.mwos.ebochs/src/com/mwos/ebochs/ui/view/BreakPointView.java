package com.mwos.ebochs.ui.view;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;

import com.mwos.ebochs.core.model.IInfoListener;
import com.mwos.ebochs.core.model.InfoCenter;
import com.mwos.ebochs.core.model.InfoCmd;
import com.mwos.ebochs.core.model.cmd.CmdStr;

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

	private Set<String> cares = new HashSet<>();
	
	public BreakPointView() {
		this.cares.add(CmdStr.AddBP);
		this.cares.add(CmdStr.DelBP);
		this.cares.add(CmdStr.ChangedBp);
		this.center.addListener(this);
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
		if(cmd.equals(CmdStr.AddBP)) {
			
		}
		
	}
	
	public void init() {
//		if(!infoCenter.isActive()) {
//			return;
//		}
//		
//		infoCenter.synSend(InfoCmd.BP_Get);
	}

	@Override
	public void dispose() {
		super.dispose();
		sendToCenter(CmdStr.RemoveListener, this);
	}

	@Override
	public Set<String> getCare() {
		// TODO Auto-generated method stub
		return this.cares;
	}
	
}
