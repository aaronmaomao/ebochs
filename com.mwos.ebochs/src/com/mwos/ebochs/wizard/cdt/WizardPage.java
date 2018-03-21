package com.mwos.ebochs.wizard.cdt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class WizardPage extends org.eclipse.jface.wizard.WizardPage {

	/**
	 * Create the wizard.
	 */
	public WizardPage() {
		super("wizardPage");
		setTitle("Wizard Page title");
		setDescription("Wizard Page description");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
	}

}
