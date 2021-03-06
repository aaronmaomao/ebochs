package com.mwos.ebochs.ui.view;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.mwos.ebochs.Activator;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Label;

public class DebugView extends ViewPart {

	public static final String ID = "com.mwos.ebochs.ui.view.DebugView"; //$NON-NLS-1$

	public DebugView() {
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		Browser browser = new Browser(composite, SWT.NONE);
		
		browser.setUrl(Activator.getResourceURL("/resource/view/debug.html"));

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
