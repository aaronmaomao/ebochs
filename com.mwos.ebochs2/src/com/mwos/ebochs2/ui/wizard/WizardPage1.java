package com.mwos.ebochs2.ui.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Link;

public class WizardPage1 extends WizardPage {
	private Text text;
	private Text text_1;

	public WizardPage1() {
		super("wizardPage");
		setTitle("Wizard Page title");
		setDescription("Wizard Page description");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(1, false));
		
		Group grpProject = new Group(container, SWT.NONE);
		grpProject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpProject.setText("Project");
		grpProject.setLayout(new GridLayout(3, false));
		
		Label lblName = new Label(grpProject, SWT.NONE);
		lblName.setText("Name");
		
		text = new Text(grpProject, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(grpProject, SWT.NONE);
		
		Button btnNewButton_1 = new Button(grpProject, SWT.CHECK);
		btnNewButton_1.setSelection(true);
		btnNewButton_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnNewButton_1.setText("Use default location");
		new Label(grpProject, SWT.NONE);
		
		Label lblLocation = new Label(grpProject, SWT.NONE);
		lblLocation.setEnabled(false);
		lblLocation.setText("Location");
		
		text_1 = new Text(grpProject, SWT.BORDER);
		text_1.setEditable(false);
		text_1.setEnabled(false);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnNewButton = new Button(grpProject, SWT.NONE);
		btnNewButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnNewButton.setEnabled(false);
		btnNewButton.setText("Browse...");
		
		Group grpProjectSetting = new Group(container, SWT.NONE);
		grpProjectSetting.setLayout(new GridLayout(3, false));
		grpProjectSetting.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpProjectSetting.setText("Project setting");
		
		Label lblToolchain = new Label(grpProjectSetting, SWT.NONE);
		lblToolchain.setText("Toolchain");
		
		Combo combo = new Combo(grpProjectSetting, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Link link = new Link(grpProjectSetting, SWT.NONE);
		link.setText("<a>Configure</a>");
	}
}
