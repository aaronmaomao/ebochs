package com.mwos.ebochs.view;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;

public class RegisterVPart extends ViewPart {
	public static final String ID = "com.mwos.ebochs.view.registerVPart"; //$NON-NLS-1$
	private Table table;
	private TableViewer tableViewer;

	public RegisterVPart() {
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		tableViewer = new TableViewer(container,
				SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		{
			TableColumn nameColumn = new TableColumn(table, SWT.LEFT);
			nameColumn.setImage(ResourceManager.getPluginImage("com.mwos.ebochs", "resource/image/panda.png"));
			nameColumn.setToolTipText("\u59D3\u540D");
			nameColumn.setMoveable(true);
			nameColumn.setWidth(100);
			nameColumn.setText("\u59D3\u540D");
		}
		{
			TableColumn addrColumn = new TableColumn(table, SWT.LEFT);
			addrColumn.setImage(ResourceManager.getPluginImage("com.mwos.ebochs", "resource/image/window.png"));
			addrColumn.setMoveable(true);
			addrColumn.setWidth(100);
			addrColumn.setText("\u5730\u5740");
		}
		tableViewer.setContentProvider(new PersonViewContentProvider(PersonManager.getManager()));

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
}
