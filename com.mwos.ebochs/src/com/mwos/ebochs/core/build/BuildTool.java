package com.mwos.ebochs.core.build;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.exe.EXERunner;
import com.mwos.ebochs.core.exe.RunResult;
import com.mwos.ebochs.ui.preference.OSDevPreference;

public class BuildTool {
	public static final String c2gas = "c2gas";
	public static final String c2asm = "c2asm";
	public static final String gas2asm = "gas2asm";
	public static final String nask = "nask";
	public static final String link = "obj2bim";

	public static BuildResult compileC(IFile file) throws Exception {

		String prjPath = file.getProject().getLocationURI().getPath();
		String name = FileUtil.getFileName(file.getProjectRelativePath().toString(), false);
		String inc = BuildUtil.getProjectInc(file.getProject());

		// 汇编为AT汇编
		String cmd_c2gas = cmd_c2gas(file.getProjectRelativePath().toString(), inc);
		RunResult c2gasResult = EXERunner.run(cmd_c2gas, prjPath);
		if (c2gasResult.exitValue() != 0) {
			return new BuildResult(c2gasResult);
		}

		// 汇编为intel汇编
		String cmd_c2asm = cmd_c2asm(file.getProjectRelativePath().toString(), inc);
		EXERunner.run(cmd_c2asm, prjPath);

		// AT转intel汇编
		String cmd_gas2asm = cmd_gas2asm(file.getProjectRelativePath().toString());
		EXERunner.run(cmd_gas2asm, prjPath);

		// 编译
		String cmd_nask = cmd_nask("obj/"+name+".asm");
		RunResult naskResult = EXERunner.run(cmd_nask, prjPath);

		return new BuildResult(c2gasResult).merge(new BuildResult(naskResult));
	}

	public static BuildResult compileAsm(IFile file) throws Exception {

		String prjPath = file.getProject().getLocationURI().getPath();

		// 编译
		String cmd_nask = cmd_nask(file.getProjectRelativePath().toString());
		RunResult naskResult = EXERunner.run(cmd_nask, prjPath);

		return new BuildResult(naskResult);
	}

	
	public static void cleanAll(IProject project) {
		String path = project.getLocationURI().getPath() + "\\obj";
		File f = new File(path);
		f.delete();
		f.mkdirs();
	}

	public static void clean(String name, IProject project) {
		String fileName = FileUtil.getFileName(name, false);
		String path = project.getLocationURI().getPath() + "/obj";
		// "^aa.*?bb$" aa开头，bb结尾
		List<File> objs = FileUtil.listFiles(path, new String[] { "^" + fileName + "\\..*?", "^_" + fileName + "\\..*?", fileName }, true);
		if (objs != null)
			for (File f : objs)
				f.delete();
	}

	private static String getBuildCmd(String type) {
		String cmd = "";
		if (type.equals(BuildTool.c2gas)) {
			cmd = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "\\cc1.exe -m32 -O1 %.c -o %.gas %inc";
		} else if (type.equals(BuildTool.c2asm)) {
			cmd = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "\\cc1.exe -m32 -gcoff -masm=intel -O1 %.c -o %.asm %inc";
		} else if (type.equals(BuildTool.gas2asm)) {
			cmd = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "\\gas2nask.exe %.gas %.asm";
		} else if (type.equals(BuildTool.nask)) {
			cmd = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "\\nask.exe %.asm %.obj %.lst";
		} else if (type.equals(BuildTool.link)) {
			cmd = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "\\obj2bim.exe  @"
					+ OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "/lib/haribote.rul out:%.out stack:%.stack map:%.map %objs";
		}
		return cmd;
	}

	private static String cmd_c2gas(String file, String inc) {
		String name = FileUtil.getFileName(file, false);
		String cmd = getBuildCmd(c2gas);
		cmd = cmd.replace("%.c", file);
		cmd = cmd.replace("%.gas", "obj/" + name + ".gas");
		cmd = cmd.replace("%inc", inc);
		return cmd;
	}

	private static String cmd_c2asm(String file, String inc) {
		String name = FileUtil.getFileName(file, false);
		String cmd = getBuildCmd(c2asm);
		cmd = cmd.replace("%.c", file);
		cmd = cmd.replace("%.asm", "obj/" + name + "_.asm");
		cmd = cmd.replace("%inc", inc);
		return cmd;
	}

	private static String cmd_gas2asm(String file) {
		String name = FileUtil.getFileName(file, false);
		String cmd = getBuildCmd(gas2asm);
		cmd = cmd.replace("%.gas", "obj/"+name+".gas");
		cmd = cmd.replace("%.asm", "obj/" + name + ".asm");
		return cmd;
	}

	private static String cmd_nask(String file) {
		String name = FileUtil.getFileName(file, false);
		String cmd = getBuildCmd(nask);
		cmd = cmd.replace("%.asm", file);
		cmd = cmd.replace("%.obj", "obj/" + name + ".obj");
		cmd = cmd.replace("%.lst", "obj/" + name + ".lst");
		return cmd;
	}

	private static String cmd_link(String file, String stack, String[] objs) {
		String name = FileUtil.getFileName(file, false);
		String cmd = getBuildCmd(link);
		cmd = cmd.replace("%.out", file);
		cmd = cmd.replace("%.map", "obj/" + name + ".map");
		String objStr = "";
		for (String obj : objs) {
			objStr += (" " + obj);
		}
		cmd = cmd.replace("%objs", objStr);
		return cmd;
	}

}
