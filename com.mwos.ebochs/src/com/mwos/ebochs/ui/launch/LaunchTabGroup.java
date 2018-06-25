package com.mwos.ebochs.ui.launch;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

public class LaunchTabGroup extends AbstractLaunchConfigurationTabGroup {

	public LaunchTabGroup() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		MainTab main = new MainTab(mode);
		this.setTabs(new ILaunchConfigurationTab[] { main });
	}

}
