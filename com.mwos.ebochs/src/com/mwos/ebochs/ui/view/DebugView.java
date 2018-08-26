package com.mwos.ebochs.ui.view;

import java.io.IOException;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.mwos.ebochs.Activator;

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
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		{
			Browser browser = new Browser(container, SWT.NONE);
			try {
				browser.setUrl(FileLocator.toFileURL(Activator.getDefault().getBundle().getResource("resource/view/debug.html")).toString());
			} catch (IOException e) {
				e.printStackTrace();
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

}
