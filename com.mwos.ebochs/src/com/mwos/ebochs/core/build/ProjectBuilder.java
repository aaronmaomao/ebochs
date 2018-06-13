package com.mwos.ebochs.core.build;

import java.io.File;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

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
		BuildTool.cleanAll(this.getProject());
	}

	private void doBuilds(IResourceDelta deltas[]) {
		if (OSDevPreference.getValue(OSDevPreference.TOOLCHAIN).isEmpty()) {
			ConsoleFactory.outInfoMsg("未找到工具链!", getProject());
		}
		if (deltas != null) {
			for (IResourceDelta delta : deltas) {
				if (new File(delta.getResource().getLocationURI().getPath()).isFile()) {
					if (delta.getResource().getName().endsWith(".c")) {
						doBuildC((IFile) delta.getResource());
					} else if (delta.getResource().getName().endsWith(".asm")) {
						doBuildAsm((IFile) delta.getResource());
					} else if (delta.getResource().getName().equals("OS.xml")) {
						doBuildOSXml();
					}
				} else {
					doBuilds(delta.getAffectedChildren());
				}
			}
		}
	}

	private boolean doBuildC(IFile file) {
		try {
			BuildResult res = BuildTool.compileC(file);
			if (!res.isSuccess()) {
				IMarker marker = file.createMarker("com.ebochs.BuildErrorMarker");
				marker.setAttribute("Description", "编译错误");
				ConsoleFactory.outErrMsg("------ 编译错误:\t" + file.getProjectRelativePath() + "\r\n" + res.getAllMsg(), file.getProject());
				BuildTool.clean(file.getProjectRelativePath().toString(), file.getProject());
			} else {
				ConsoleFactory.outMsg("------ 编译成功:\t" + file.getProjectRelativePath() + "\r\n" + res.getAllMsg(), file.getProject());
				file.deleteMarkers("com.ebochs.BuildErrorMarker", false, IFile.DEPTH_INFINITE);
			}
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean doBuildAsm(IFile file) {
		try {
			BuildResult res = BuildTool.compileAsm(file);
			if (!res.isSuccess()) {
				IMarker marker = file.createMarker("com.ebochs.BuildErrorMarker");
				marker.setAttribute("Description", "编译错误");
				ConsoleFactory.outErrMsg(file + " ------ 编译错误\r\n" + res.getAllMsg(), file.getProject());
				BuildTool.clean(file.getProjectRelativePath().toString(), file.getProject());
			}else {
				ConsoleFactory.outMsg("------ 编译成功:\t" + file.getProjectRelativePath() + "\r\n" + res.getAllMsg(), file.getProject());
				file.deleteMarkers("com.ebochs.BuildErrorMarker", false, IFile.DEPTH_INFINITE);
			}
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean doBuildOSXml() {
		return false;
	}
}
