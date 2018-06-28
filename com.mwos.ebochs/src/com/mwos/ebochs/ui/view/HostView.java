package com.mwos.ebochs.ui.view;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.mwos.ebochs.core.model.IInfoListener;
import com.mwos.ebochs.core.model.InfoCenter;

import org.eclipse.swt.custom.TableTree;
import org.eclipse.jface.viewers.TableTreeViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TreeColumn;

public class HostView extends ViewPart implements IInfoListener {

	public static final String ID = "com.mwos.ebochs.ui.view.Host"; //$NON-NLS-1$

	private InfoCenter infoCenter;
	private boolean enable = false;

	public HostView() { 
		infoCenter = InfoCenter.getCurrentInfoCenter();
		if (infoCenter != null) {
			infoCenter.addListener(this);
		}
		enable = infoCenter == null ? false : true;
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

		Tree hostTree = new Tree(container, SWT.BORDER);

		hostTree.setLinesVisible(true);

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
	
	private void initTree(Tree tree) {
	}
}
