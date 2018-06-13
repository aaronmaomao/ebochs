package com.mwos.ebochs.core.build;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.xml.sax.SAXException;

import com.mwos.ebochs.core.ExceptionUtil;
import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.resource.config.OSConfigFactory;
import com.mwos.ebochs.resource.config.entity.CodePart;
import com.mwos.ebochs.resource.config.entity.CodePart.Code;
import com.mwos.ebochs.resource.config.entity.Image;
import com.mwos.ebochs.resource.config.entity.OSConfig;
import com.mwos.ebochs.resource.config.entity.ImgFile;
import com.mwos.ebochs.ui.preference.OSDevPreference;
import com.mwos.ebochs.ui.view.ConsoleFactory;

public class ProjectBuilder extends IncrementalProjectBuilder {

	public static final String ID = "com.mwos.ebochs.OSBuilder";

	public ProjectBuilder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		switch (kind) {
		case FULL_BUILD:
			break;
		case AUTO_BUILD:
		case INCREMENTAL_BUILD:
			doBuilds(getDelta(getProject()).getAffectedChildren(IResourceDelta.ADDED | IResourceDelta.CHANGED));
			break;
		default:
			break;
		}
		return null;
	}

	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		super.clean(monitor);
		BuildFactory.cleanAll(this.getProject());
	}

	private void doBuilds(IResourceDelta deltas[]) {
		if (OSDevPreference.getValue(OSDevPreference.TOOLCHAIN).isEmpty()) {
			ConsoleFactory.outInfoMsg("未找到工具链!", getProject());
		}
		if (deltas != null) {
			for (IResourceDelta delta : deltas) {
				if (new File(delta.getResource().getLocationURI().getPath()).isFile()) {
					if (delta.getResource().getName().endsWith(".c")) {
						BuildFactory.doBuildC(delta.getProjectRelativePath().toString(), this.getProject());
					} else if (delta.getResource().getName().endsWith(".asm")) {
						BuildFactory.doBuildAsm(delta.getProjectRelativePath().toString(), this.getProject());
					} else if (delta.getResource().getName().equals("OS.xml")) {
						BuildFactory.doBuildOSXml(this.getProject());
					}
				} else {
					doBuilds(delta.getAffectedChildren());
				}
			}
		}
	}
}
