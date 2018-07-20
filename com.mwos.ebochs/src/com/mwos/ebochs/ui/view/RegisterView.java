package com.mwos.ebochs.ui.view;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

public class RegisterView extends ViewPart {

	public static final String ID = "com.mwos.ebochs.ui.view.RegisterView"; //$NON-NLS-1$
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	public RegisterView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		{
			Section sctnNewSection = formToolkit.createSection(container, Section.TWISTIE | Section.TITLE_BAR);
			sctnNewSection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			formToolkit.paintBordersFor(sctnNewSection);
			sctnNewSection.setText("通用寄存器");
		}
		{
			Section sctnNewSection_1 = formToolkit.createSection(container, Section.TWISTIE | Section.TITLE_BAR);
			sctnNewSection_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			formToolkit.paintBordersFor(sctnNewSection_1);
			sctnNewSection_1.setText("控制寄存器");
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
