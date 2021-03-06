package com.mwos.ebochs2.ui.preference;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.json.provisonnal.com.eclipsesource.json.JsonArray;
import org.eclipse.json.provisonnal.com.eclipsesource.json.JsonObject;
import org.eclipse.json.provisonnal.com.eclipsesource.json.JsonValue;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.mwos.ebochs2.Activator;
import com.mwos.ebochs2.model.ISeriable;
import com.mwos.ebochs2.model.toolchain.Toolchain;

public class OSPreference extends PreferencePage implements IWorkbenchPreferencePage {

	public static final String ALL_TC = Activator.PLUGIN_ID + ".ALL_TOOLCHAINS";
	public static final String DEFAULT_TC = Activator.PLUGIN_ID + ".DEFAULT_TC";

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

	public static Toolchain getSelectTc() {
		String defaultTC = Activator.getDefault().getPreferenceStore().getString(DEFAULT_TC);
		if (!defaultTC.isEmpty()) {
			Toolchain toolchain = ISeriable.toObject(JsonObject.readFrom(defaultTC), Toolchain.class);
			return toolchain;
		} else {
			return null;
		}
	}

	public static Toolchain[] getAllTc() {
		String all = Activator.getDefault().getPreferenceStore().getString(ALL_TC);
		if (!all.isEmpty()) {
			JsonArray array = JsonArray.readFrom(all);
			Toolchain[] tcs = new Toolchain[array.size()];
			for (int i = 0; i < array.size(); i++) {
				tcs[i] = ISeriable.toObject(JsonObject.readFrom(array.get(i).toString()), Toolchain.class);
			}
			return tcs;
		} else {
			return null;
		}
	}

}
