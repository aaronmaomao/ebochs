package com.mwos.ebochs.ui.launch.run;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

public class LaunchTabGroupRun extends AbstractLaunchConfigurationTabGroup {

	public LaunchTabGroupRun() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		this.setTabs(new ILaunchConfigurationTab[] {});
	}

}
