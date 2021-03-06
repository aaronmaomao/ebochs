package com.mwos.ebochs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.wb.swt.ResourceManager;
import org.osgi.framework.BundleContext;

import com.mwos.ebochs.resource.project.OSProjectNature;
import com.mwos.ebochs.ui.preference.OSDevPreference;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.mwos.ebochs"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	public static ImageDescriptor getPluginImageDescriptor(String path) {
		return ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, path);
	}

	public static Image getImage(String path) {
		return getPluginImageDescriptor(path).createImage();
	}

	@Override
	protected void initializeDefaultPreferences(IPreferenceStore store) {
		// TODO Auto-generated method stub
		if (store.getDefaultString(OSDevPreference.TOOLCHAIN) == null)
			store.setDefault(OSDevPreference.TOOLCHAIN, "");

		if (store.getDefaultString(OSDevPreference.BOCHS) == null)
			store.setDefault(OSDevPreference.BOCHS, "");

		if (store.getDefaultString(OSDevPreference.VBOX) == null)
			store.setDefault(OSDevPreference.VBOX, "");
	}

	public static List<IProject> getOSProject() {
		List<IProject> projects = new ArrayList<>();
		for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			try {
				if (p.exists() && p.isOpen() && p.getNature(OSProjectNature.NatureId) != null) {
					projects.add(p);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return projects;
	}

	public static IProject getCurrentProject() {
		IProject project = null;
		//
		// //1.根据当前编辑器获取工程
		// IEditorPart part = getActiveEditor();
		// if(part != null){
		// Object object = part.getEditorInput().getAdapter(IFile.class);
		// if(object != null){
		// project = ((IFile)object).getProject();
		// }
		// }
		//
		// if(project == null){
		// ISelectionService selectionService =
		// Workbench.getInstance().getActiveWorkbenchWindow().getSelectionService();
		// ISelection selection = selectionService.getSelection();
		// if(selection instanceof IStructuredSelection) {
		// Object element = ((IStructuredSelection)selection).getFirstElement();
		// }
		// }

		return project;
	}

	public static IProject getOSProject(String name) {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(name);
	}

	public static String getResourceURL(String url) {
		try {
			return FileLocator.toFileURL(Activator.getDefault().getBundle().getResource(url)).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
