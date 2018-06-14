package com.mwos.ebochs.core.build;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTProblem;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.internal.core.dom.parser.c.CVisitor;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.resource.config.OSConfigFactory;
import com.mwos.ebochs.resource.config.entity.CodePart;
import com.mwos.ebochs.resource.config.entity.CodePart.Code;
import com.mwos.ebochs.resource.config.entity.Image;
import com.mwos.ebochs.resource.config.entity.ImgFile;
import com.mwos.ebochs.resource.config.entity.OSConfig;
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
		if (file.getName().endsWith(".c")) {
			return doBuildC(file);
		} else if (file.getName().endsWith(".asm")) {
			return doBuildAsm(file);
		}
		return false;
	}

	private boolean doBuildC(IFile file) {
		try {

			if (hasDomError(file)) {
				ConsoleFactory.outErrMsg("------ 源码错误:\t" + file.getProjectRelativePath()+"\r\n", file.getProject());
				return false;
			}

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
			if (hasDomError(file)) {
				ConsoleFactory.outErrMsg("------ 源码错误:\t" + file.getProjectRelativePath(), file.getProject());
				return false;
			}
			BuildResult res = BuildTool.compileAsm(file);
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

	private boolean doBuildOSXml() {
		try {
			OSConfig config = OSConfigFactory.getBuildConfig(getProject());
			for (Image img : config.getImages()) {
				for (ImgFile ifile : img.getImgFiles()) {
					List<CodePart> cps = config.getCodePart(ifile);
					for (CodePart cp : cps) {
						boolean b = buildCodePart(cp);
						if (!b)
							return b;
					}
					if(mergeImgFile(ifile)==null)
						return false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean buildCodePart(CodePart cp) {
		String objDir = this.getProject().getLocationURI().getPath() + "/obj/";
		List<String> objs = new ArrayList<>();
		if (!cp.getSrc().isEmpty()) {
			String name = FileUtil.getFileName(cp.getSrc(), false);
			IFile file = this.getProject().getFile(new Path(cp.getSrc()));
			if (!file.exists()) {
				ConsoleFactory.outErrMsg("------ 构建错误, 文件不存在:\t" + cp.getObj() + " > " + cp.getSrc() + "\r\n", file.getProject());
				return false;
			}
			if (!new File(objDir + name + ".obj").exists()) {
				if (!doBuild(file))
					return false;
			}
			return true;
		} else {
			for (Code code : cp.getCodes()) {
				String name = FileUtil.getFileName(code.getName(), false);
				IFile file = this.getProject().getFile(new Path(code.getName()));
				if (!file.exists()) {
					ConsoleFactory.outErrMsg("------ 构建错误, 文件不存在:\t" + cp.getObj() + " > " + code.getName() + "\r\n", file.getProject());
					return false;
				}
				if (!new File(objDir + name + ".obj").exists()) {
					if (!doBuild(file))
						return false;
				}
				objs.add("obj/" + name + ".obj");
			}

			try {
				BuildResult res = BuildTool.link(cp.getObj(), "3325k", this.getProject(), objs.toArray(new String[0]));
				if (!res.isSuccess()) {
					ConsoleFactory.outErrMsg("------ 构建错误:\t" + cp.getObj() + "\r\n" + res.getAllMsg(), getProject());
				} else {
					ConsoleFactory.outMsg("------ 构建成功:\t" + cp.getObj() + "\r\n" + res.getAllMsg(), getProject());
				}
				return res.isSuccess();
			} catch (Exception e) {
				e.printStackTrace();
				ConsoleFactory.outErrMsg("------ 链接错误:\t" + cp.getObj() + " : " + cp.getObj() + "\r\n", getProject());
				return false;
			}
		}

	}
	

	private File mergeImgFile(ImgFile img) {
		File file = new File(img.getLocation());
		if(!file.exists()&&img.getSubs().size()<=0) {
			return null;
		}
		if(!file.exists()&&img.getSubs().size()>0) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for(ImgFile sub:img.getSubs()) {
			File subF = mergeImgFile(sub);
			if(subF==null) {
				return null;
			}else {
				file = FileUtil.merge(file, subF);
			}
		}
		
		return file;
		
	}

	public static boolean hasDomError(IFile file) {

		IASTTranslationUnit ast = null;
		try {
			ICProject cproject = CoreModel.getDefault().getCModel().getCProject(file.getProject().getName());
			ICElement element = cproject.findElement(file.getProjectRelativePath()); // this apparently throws exception for non-source files instead of returning
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
