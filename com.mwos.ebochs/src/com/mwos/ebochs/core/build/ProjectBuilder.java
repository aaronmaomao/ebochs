package com.mwos.ebochs.core.build;

import java.io.File;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTProblem;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.internal.core.dom.parser.c.CVisitor;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.mwos.ebochs.resource.config.OSConfigFactory;
import com.mwos.ebochs.resource.config.entity.CodePart.Code;
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
		if (deltas != null) {
			for (IResourceDelta delta : deltas) {
				if (new File(delta.getResource().getLocationURI().getPath()).isFile()) {
					if ((delta.getResource().getName().endsWith(".c") || delta.getResource().getName().endsWith(".asm"))
							&& !delta.getProjectRelativePath().toString().contains("obj/")) {
						doBuild((IFile) delta.getResource());
					} else if (delta.getResource().getName().equals("OS.xml")) {
						doBuildOSXml();
					}
				} else {
					doBuilds(delta.getAffectedChildren());
				}
			}
		}
	}

	private boolean doBuild(IFile file) {
		try {
			DefaultBuilder builder = AbstractBuilder.getBuilder(DefaultBuilder.class);
			String name = file.getProjectRelativePath().toString();
			Code c = OSConfigFactory.getConfig(getProject()).getCode(name);
			BuildResult res = null;
			if (c != null) {
				res = builder.compile(name, c.getOut(), getProject());
			} else {
				res = builder.compile(name, getProject());
			}

			if (!res.isSuccess()) {
				ConsoleFactory.outErrMsg("----- 编译错误:\t" + name + "\r\n" + res.getAllMsg() + "\r\n", getProject());
			} else {
				ConsoleFactory.outMsg("----- 编译成功:\t" + name + "\r\n" + res.getAllMsg() + "\r\n", getProject());
			}
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			ConsoleFactory.outErrMsg("----- 系统出错：\r\n" + e.getMessage() + "\r\n", getProject());
		}
		return false;
	}

	private boolean doBuildOSXml() {
		OSConfigFactory.getBuildConfig(getProject());
		return false;
	}

	public static boolean hasDomError(IFile file) {

		IASTTranslationUnit ast = null;
		try {
			ICProject cproject = CoreModel.getDefault().getCModel().getCProject(file.getProject().getName());
			ICElement element = cproject.findElement(file.getProjectRelativePath()); // this apparently throws exception for non-source
																						// files instead of returning
																						// null
			ITranslationUnit unit = (ITranslationUnit) element; // not clear whether we need to check for null or instanceof
			ast = unit.getAST();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		if (ast == null) {
			return false;
		}
		IASTProblem[] problems = ast.getPreprocessorProblems();
		if (problems != null && problems.length > 0)
			return true;

		problems = CVisitor.getProblems(ast);

		if (problems == null || problems.length == 0)
			return false;
		else
			return true;
		// for (IASTProblem problem : problems) {
		// if ((problem.getID() & (IProblem.SYNTAX_ERROR | IProblem.SCANNER_RELATED)) !=
		// 0) {
		// return true;
		// }
		// }
		// problems= CPPVisitor.getProblems(ast);
		// for (IASTProblem problem : problems) {
		// if ((problem.getID() & (IProblem.SYNTAX_ERROR | IProblem.SCANNER_RELATED)) !=
		// 0) {
		// return true;
		// }
		// }
		// return false;
	}
}
