package com.mwos.ebochs.view;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.jface.action.Action;
import org.eclipse.wb.swt.ResourceManager;

import com.mwos.ebochs.Activator;
import com.mwos.ebochs.view.entity.Memory;
import com.mwos.ebochs.view.provider.MemContentProvider;
import com.mwos.ebochs.view.provider.MemLabelProvider;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.CellEditor;

public class MemVPart extends ViewPart {

	public static final String ID = "com.mwos.ebochs.view.MemVPart"; //$NON-NLS-1$
	private Action refreshAction;
	private Action newVAction;
	private Action testAction;
	private Table table;

	public MemVPart() {
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		{
			Group group = new Group(composite, SWT.NONE);
			group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			group.setText("\u9009\u62E9\u5185\u5B58");
		}
		{
			TableViewer tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
			tableViewer.addDoubleClickListener(new IDoubleClickListener() {
				
				@Override
				public void doubleClick(DoubleClickEvent event) {
					IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				}
			});
			table = tableViewer.getTable();
			table.setLinesVisible(true);
			table.setHeaderVisible(true);
			table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				TableColumn addrColumn = tableViewerColumn.getColumn();
				addrColumn.setWidth(80);
				addrColumn.setText("\u5730\u5740/\u4F4D");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				tableViewerColumn.setEditingSupport(new EditingSupport(tableViewer) {
					protected boolean canEdit(Object element) {
						// TODO Auto-generated method stub
						return true;
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
				TableColumn addrColumn0 = tableViewerColumn.getColumn();
				addrColumn0.setWidth(30);
				addrColumn0.setText("0");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				TableColumn addrColumn1 = tableViewerColumn.getColumn();
				addrColumn1.setWidth(30);
				addrColumn1.setText("1");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				TableColumn addrColumn2 = tableViewerColumn.getColumn();
				addrColumn2.setWidth(30);
				addrColumn2.setText("2");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				TableColumn addrColumn3 = tableViewerColumn.getColumn();
				addrColumn3.setWidth(30);
				addrColumn3.setText("3");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				TableColumn addrColumn4 = tableViewerColumn.getColumn();
				addrColumn4.setWidth(30);
				addrColumn4.setText("4");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				TableColumn addrColumn5 = tableViewerColumn.getColumn();
				addrColumn5.setWidth(30);
				addrColumn5.setText("5");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				TableColumn addrColumn6 = tableViewerColumn.getColumn();
				addrColumn6.setWidth(30);
				addrColumn6.setText("6");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				TableColumn addrColumn7 = tableViewerColumn.getColumn();
				addrColumn7.setWidth(30);
				addrColumn7.setText("7");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				TableColumn addrColumn8 = tableViewerColumn.getColumn();
				addrColumn8.setWidth(30);
				addrColumn8.setText("8");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				TableColumn addrColumn9 = tableViewerColumn.getColumn();
				addrColumn9.setWidth(30);
				addrColumn9.setText("9");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				TableColumn addrColumnA = tableViewerColumn.getColumn();
				addrColumnA.setWidth(30);
				addrColumnA.setText("A");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				TableColumn addrColumnB = tableViewerColumn.getColumn();
				addrColumnB.setWidth(30);
				addrColumnB.setText("B");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				TableColumn addrColumnC = tableViewerColumn.getColumn();
				addrColumnC.setWidth(30);
				addrColumnC.setText("C");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				TableColumn addrColumnD = tableViewerColumn.getColumn();
				addrColumnD.setWidth(30);
				addrColumnD.setText("D");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				TableColumn addrColumnE = tableViewerColumn.getColumn();
				addrColumnE.setWidth(30);
				addrColumnE.setText("E");
			}
			{
				TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
				TableColumn addrColumnF = tableViewerColumn.getColumn();
				addrColumnF.setWidth(30);
				addrColumnF.setText("F");
			}
			tableViewer.setContentProvider(new MemContentProvider());
			tableViewer.setLabelProvider(new MemLabelProvider());
			tableViewer.setInput(Memory.getMems());
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
		{
			refreshAction = new Action("\u5237\u65B0") {
				@Override
				public void run() {
					super.run();
				}
			};
			refreshAction.setToolTipText("\u5237\u65B0");
			refreshAction.setImageDescriptor(
					ResourceManager.getPluginImageDescriptor("com.mwos.ebochs", "resource/icons/arrow_refresh.png"));
		}
		{
			newVAction = new Action("New Action") {

			};
			newVAction.setImageDescriptor(
					ResourceManager.getPluginImageDescriptor("com.mwos.ebochs", "resource/icons/add.png"));
		}
		{
			testAction = new Action("Test Action") {

			};
			testAction.setImageDescriptor(
					ResourceManager.getPluginImageDescriptor("com.mwos.ebochs", "resource/icons/emoticon_happy.png"));
		}
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
		toolbarManager.add(refreshAction);
		toolbarManager.add(newVAction);
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
		menuManager.add(testAction);
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
}
