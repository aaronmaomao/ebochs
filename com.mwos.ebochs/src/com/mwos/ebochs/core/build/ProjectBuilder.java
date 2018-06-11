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
		cleanAll(this.getProject());
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
					} else if (delta.getResource().getName().equals("OS.xml")) {
						doBuildOSXml();
					}
				} else {
					doBuilds(delta.getAffectedChildren());
				}
			}
		}
	}

	private boolean doBuildAsm(String file) {
		try {
			clean(file, this.getProject());
			BuildResult res = Compiler.compileAsm(file, this.getProject());
			if (res.isSuccess()) {
				ConsoleFactory.outMsg(file + " ----- 编译成功\r\n" + res.getMsg(), this.getProject());
			} else {
				ConsoleFactory.outErrMsg(file + " ----- 编译错误\r\n" + res.getAllMsg(), this.getProject());
			}
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			ConsoleFactory.outErrMsg(file + " ----- 编译异常\r\n", this.getProject());
			return false;
		}
	}

	private boolean doBuildC(String file) {
		try {
			clean(file, this.getProject());
			BuildResult res = Compiler.compileC(file, this.getProject());
			if (res.isSuccess()) {
				ConsoleFactory.outMsg(file + " ----- 编译成功\r\n" + res.getMsg(), this.getProject());
			} else {
				ConsoleFactory.outErrMsg(file + " ----- 编译错误\r\n" + res.getAllMsg(), this.getProject());
			}
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			ConsoleFactory.outErrMsg(file + " ----- 编译异常\r\n", this.getProject());
			return false;
		}
	}

	private boolean doBuildFile(String file) {
		
		if (file.endsWith(".c")) {
			return doBuildC(file);
		} else if (file.endsWith(".asm")) {
			return doBuildAsm(file);
		}

		return false;
	}

	private boolean doBuildOSXml() {
		try {
			OSConfig config = OSConfigFactory.getBuildConfig(this.getProject());
			if (!config.needBuild())
				return false;
			for (Image image : config.getImages()) {
				for (ImgFile resource : image.getImgFiles()) {
					if (resource.getType().equals(ImgFile.CODEPART)) {
						CodePart codePart = config.getCodePart(resource.getName());
						if (codePart == null) {
							ConsoleFactory.outErrMsg(resource.getName() + " ----- 构建异常：未找到资源\r\n", this.getProject());
							return false;
						}
						for (Code code : codePart.getCodes()) {
							if (!doBuildFile(code.getName())) {
								ConsoleFactory.outErrMsg(resource.getName() + " : " + code.getName() + " ----- 构建异常\r\n",
										this.getProject());
								return false;
							}
						}
					}
				}
			}

			return true;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			ConsoleFactory.outErrMsg(this.getProject().getName() + " ----- 构建失败\r\n" + ExceptionUtil.getMsg(e), this.getProject());
			e.printStackTrace();
			return false;
		}
	}

	private void cleanAll(IProject project) {
		String path = project.getLocationURI().getPath() + "\\obj";
		File f = new File(path);
		f.delete();
		f.mkdirs();
	}

	private void clean(String name, IProject project) {
		name = FileUtil.getFileName(name, false);
		String path = project.getLocationURI().getPath() + "\\obj";
		// "^aa.*?bb$" aa开头，bb结尾
		List<File> objs = FileUtil.listFiles(path, new String[] { "^" + name + "\\..*?", "^_" + name + "\\..*?", name }, true);
		if (objs != null)
			for (File f : objs) {
				f.delete();
			}
	}
}
