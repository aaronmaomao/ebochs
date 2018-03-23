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

public class MemVPart extends ViewPart {

	public static final String ID = "com.mwos.ebochs.view.MemVPart"; //$NON-NLS-1$
	private Action refreshAction;
	private Action newVAction;
	private Action testAction;

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
