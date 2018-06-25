package com.mwos.ebochs.ui.launch;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;

public class ShortLaunch implements ILaunchShortcut2 {

	@Override
	public void launch(ISelection selection, String mode) {
		// TODO Auto-generated method stub
		if (selection instanceof TreeSelection) {
			Object ele = ((TreeSelection) selection).getFirstElement();
			if (ele instanceof IResource) {
				ILaunchConfiguration cfg = getLaunchConfiguration(((IResource) ele).getProject().getName(), mode);
				if (cfg == null) {
					cfg = createLaunchConfiguration(((IResource) ele).getProject().getName(), mode);
				}
				try {
					cfg.launch(mode, null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		// TODO Auto-generated method stub
		IEditorInput ie = editor.getEditorInput();
		IProject p = ((FileEditorInput) ie).getFile().getProject();
		ILaunchConfiguration cfg = getLaunchConfiguration(p.getName(), mode);
		if (cfg == null) {
			cfg = createLaunchConfiguration(p.getName(), mode);
		}
		try {
			cfg.launch(mode, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}

	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(ISelection selection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource getLaunchableResource(ISelection selection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource getLaunchableResource(IEditorPart editorpart) {
		// TODO Auto-generated method stub
		IEditorInput ie = editorpart.getEditorInput();
		if (ie instanceof FileEditorInput) {
			return ((FileEditorInput) ie).getFile().getProject();
		}
		return null;
	}

	private ILaunchConfiguration getLaunchConfiguration(String name, String mode) {
		ILaunchManager mg = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = mg.getLaunchConfigurationType("com.mwos.ebochs.launchConfigurationType");
		try {
			ILaunchConfiguration[] cfgs = DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurations(type);
			for (ILaunchConfiguration cfg : cfgs) {
				if (cfg.getName().equals("OS_" + name)) {
					return cfg;
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ILaunchConfiguration createLaunchConfiguration(String name, String mode) {
		ILaunchManager mg = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = mg.getLaunchConfigurationType("com.mwos.ebochs.launchConfigurationType");
		try {
			ILaunchConfigurationWorkingCopy wc = type.newInstance(null, "OS_" + name);
			wc = MainTab.initCfg(wc, name, mode);
			return wc.doSave();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}
}
