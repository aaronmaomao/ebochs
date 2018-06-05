package com.mwos.ebochs.ui.launch;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.mwos.ebochs.resource.project.OSProjectNature;

public class ResourceUtil {
	private ResourceUtil() {
	}

//	public static List<IProject> getProjects() {
//		IProject[] allProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
//		for (IProject p : allProjects) {
//			try {
//				if(p.exists()&&p.isOpen()) {
//					p.getNature(OSProjectNature.NatureId);
//				}
//				
//			} catch (CoreException e) {
//				e.printStackTrace();
//			}
//		}
//	}
}
