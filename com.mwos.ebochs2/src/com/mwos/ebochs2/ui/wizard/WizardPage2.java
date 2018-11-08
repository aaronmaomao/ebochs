package com.mwos.ebochs2.ui.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;

public class WizardPage2 extends WizardPage {
	private Text text;

	public WizardPage2() {
		super("wizardPage");
		setTitle("Wizard Page title");
		setDescription("Wizard Page description");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(1, false));
		
		Group grpOsSetting = new Group(container, SWT.NONE);
		grpOsSetting.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpOsSetting.setText("OS setting");
		grpOsSetting.setLayout(new GridLayout(4, false));
		
		Label lblOsName = new Label(grpOsSetting, SWT.NONE);
		lblOsName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOsName.setText("OS Name");
		
		text = new Text(grpOsSetting, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblOsBit = new Label(grpOsSetting, SWT.NONE);
		lblOsBit.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOsBit.setText("OS Bit");
		
		Combo combo = new Combo(grpOsSetting, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

}
