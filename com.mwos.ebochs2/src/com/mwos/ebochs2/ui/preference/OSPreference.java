package com.mwos.ebochs2.ui.preference;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.mwos.ebochs2.Activator;
import com.mwos.ebochs2.model.Toolchain;

public class OSPreference extends PreferencePage implements IWorkbenchPreferencePage {

	public static final String TCS = Activator.PLUGIN_ID + ".TOOLCHAINS";
	public static final String SELECT_TC = Activator.PLUGIN_ID + ".TOOLCHAINS";

	/**
	 * Create the preference page.
	 */
	public OSPreference() {
	}

	/**
	 * Create contents of the preference page.
	 * 
	 * @param parent
	 */
	@Override
	public Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		return container;
	}

	/**
	 * Initialize the preference page.
	 */
	public void init(IWorkbench workbench) {
		// Initialize the preference page
	}

	public static Toolchain getSelectTC() {
		String select = Activator.getDefault().getPreferenceStore().getDefaultString(SELECT_TC);
		if(!select.isEmpty()) {
		}else {
			return null;
		}
		return null;
	}

}
