package com.mwos.ebochs.core.build;

import java.io.File;
import java.util.Map;

import org.eclipse.core.resources.ICommand;
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
	}

	private void doBuilds(IResourceDelta deltas[]) {
		if (OSDevPreference.getValue(OSDevPreference.TOOLCHAIN).isEmpty()) {
			ConsoleFactory.outInfoMsg("未找到工具链!", getProject());
		}
		if (deltas != null) {
			for (IResourceDelta delta : deltas) {
				if (new File(delta.getResource().getLocationURI().getPath()).isFile()) {
					if (delta.getResource().getName().endsWith(".c")) {
						doBuildC(delta.getProjectRelativePath().toString());
					} else if (delta.getResource().getName().endsWith(".asm")) {
						doBuildAsm(delta.getProjectRelativePath().toString());
					}
				} else {
					doBuilds(delta.getAffectedChildren());
				}
			}
		}
	}

	private void doBuildAsm(String file) {
		try {
			BuildResult res = Compiler.compileAsm(file, this.getProject());
			if (res.isSuccess()) {
				ConsoleFactory.outMsg(file + " ----- 编译成功\r\n" + res.getMsg(), this.getProject());
			} else {
				ConsoleFactory.outErrMsg(file + " ----- 编译错误\r\n" + res.getAllMsg(), this.getProject());
			}
		} catch (Exception e) {
			e.printStackTrace();
			ConsoleFactory.outErrMsg(file + " ----- 编译异常\r\n", this.getProject());
		}
	}

	private void doBuildC(String file) {
		try {
			BuildResult res = Compiler.compileC(file, this.getProject());
			if (res.isSuccess()) {
				ConsoleFactory.outMsg(file + " ----- 编译成功\r\n" + res.getMsg(), this.getProject());
			} else {
				ConsoleFactory.outErrMsg(file + " ----- 编译错误\r\n" + res.getAllMsg(), this.getProject());
			}
		} catch (Exception e) {
			e.printStackTrace();
			ConsoleFactory.outErrMsg(file + " ----- 编译异常\r\n", this.getProject());
		}
	}
}
