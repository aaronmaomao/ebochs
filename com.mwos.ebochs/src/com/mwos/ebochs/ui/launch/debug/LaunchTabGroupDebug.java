package com.mwos.ebochs.ui.launch.debug;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

public class LaunchTabGroupDebug extends AbstractLaunchConfigurationTabGroup {

	public LaunchTabGroupDebug() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		this.setTabs(new ILaunchConfigurationTab[] {});
	}

}
