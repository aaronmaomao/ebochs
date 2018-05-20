package com.mwos.ebochs.core.launch;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

public class Test extends Composite {
	private Text text;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public Test(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		Group grpBochsArguments = new Group(this, SWT.NONE);
		grpBochsArguments.setText("Bochs Arguments");
		grpBochsArguments.setLayout(new GridLayout(2, false));
		GridData gd_grpBochsArguments = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		gd_grpBochsArguments.widthHint = 441;
		grpBochsArguments.setLayoutData(gd_grpBochsArguments);
		
		Label lblNewLabel = new Label(grpBochsArguments, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("args");
		
		text = new Text(grpBochsArguments, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
