package com.mwos.ebochs.preference;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class OSPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	/**
	 * @wbp.parser.constructor
	 */
	public OSPreferencePage() {
		// TODO Auto-generated constructor stub
	}

	public OSPreferencePage(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public OSPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		return container;
	}

}
