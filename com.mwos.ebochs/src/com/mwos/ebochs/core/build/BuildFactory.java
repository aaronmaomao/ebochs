package com.mwos.ebochs.core.build;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.ui.view.ConsoleFactory;

public class BuildFactory {
	public static boolean doBuildAsm(String file, IProject p) {
		try {
			clean(file, p);
			BuildResult res = Compiler.compileAsm(file, p);
			if (res.isSuccess()) {
				ConsoleFactory.outMsg(file + " ----- 编译成功\r\n" + res.getMsg(), p);
			} else {
				ConsoleFactory.outErrMsg(file + " ----- 编译错误\r\n" + res.getAllMsg(), p);
			}
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			ConsoleFactory.outErrMsg(file + " ----- 编译异常\r\n", p);
			return false;
		}
	}

	public static boolean doBuildC(String file, IProject p) {
		try {
			clean(file, p);
			BuildResult res = Compiler.compileC(file, p);
			if (res.isSuccess()) {
				ConsoleFactory.outMsg(file + " ----- 编译成功\r\n" + res.getMsg(), p);
			} else {
				ConsoleFactory.outErrMsg(file + " ----- 编译错误\r\n" + res.getAllMsg(), p);
			}
			return res.isSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			ConsoleFactory.outErrMsg(file + " ----- 编译异常\r\n", p);
			return false;
		}
	}
	
	public static boolean doBuildOSXml(IProject project) {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void cleanAll(IProject project) {
		String path = project.getLocationURI().getPath() + "\\obj";
		File f = new File(path);
		f.delete();
		f.mkdirs();
	}

	private static void clean(String name, IProject project) {
		String fileName = FileUtil.getFileName(name, false);
		String path = project.getLocationURI().getPath() + CompilerUtil.getObjDir(name, project);
		// "^aa.*?bb$" aa开头，bb结尾
		List<File> objs = FileUtil.listFiles(path, new String[] { "^" + fileName + "\\..*?", "^_" + fileName + "\\..*?", fileName }, true);
		if (objs != null)
			for (File f : objs) {
				f.delete();
			}
	}

}
