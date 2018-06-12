package com.mwos.ebochs.core.build;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.exe.EXERunner;
import com.mwos.ebochs.core.exe.RunResult;

public class Compiler {

	// cc1 -m32 -O1 $*.c -o $(objp)$*.gas -I D:\study\30day\tolset\z_tools\haribote
	// cc1 -m32 -gcoff -masm=intel -O1 $*.c -o $(objp)_$*.asm -I
	// D:\study\30day\tolset\z_tools\haribote

	// private static String toolchain = "D:\\study\\mwos\\001-data\\toolchain";
	// private static String cc1 = toolchain + "\\cc1.exe";
	// private static String cmdReg1 = " -m32 -O1 %c -o %obj %inc";
	// private static String cmdReg2 = " -m32 -gcoff -masm=intel -O1 \"%c\" -o
	// \"%obj\" %inc";

	public static final String c2gas = "c2gas";
	public static final String c2asm = "c2asm";
	public static final String gas2asm = "gas2asm";
	public static final String nask = "nask";

	private Compiler() {

	}

	public static BuildResult compileC(String file, IProject project) throws Exception {
		String projectPath = project.getLocationURI().getPath();
		String fileName = FileUtil.getFileName(file, false);

		String inc = CompilerUtil.getInc(project);
		String obj = CompilerUtil.getObjDir(file, project);

		String cmd_c2gas = CompilerUtil.getCmd(c2gas).replace("%.c", file).replace("%.gas", CompilerUtil.getObj(file, "%.gas", project)).replace("%inc", inc);
		RunResult c2gasResult = EXERunner.run(cmd_c2gas, projectPath);
		if (c2gasResult.exitValue() != 0) {
			return new BuildResult(c2gasResult);
		}

		String cmd_c2asm = CompilerUtil.getCmd(c2asm).replace("%.c", file).replace("%.asm", CompilerUtil.getObj(file, "_%.asm", project)).replace("%inc", inc);
		EXERunner.run(cmd_c2asm, projectPath);

		String cmd_gas2asm = CompilerUtil.getCmd(gas2asm).replace("%.gas", CompilerUtil.getObj(file, "%.gas", project)).replace("%.asm",
				CompilerUtil.getObj(file, "%.asm", project));
		EXERunner.run(cmd_gas2asm, projectPath);

		String cmd_nask = CompilerUtil.getCmd(nask).replace("%.asm", obj + "/" + fileName + ".asm").replace("%.obj", obj + "/" + fileName + ".obj").replace("%.lst",
				obj + "/" + fileName + ".lst");

		RunResult naskResult = EXERunner.run(cmd_nask, projectPath);

		return new BuildResult(c2gasResult).merge(new BuildResult(naskResult));
	}

	public static BuildResult compileAsm(String file, IProject project) throws IOException, InterruptedException {
		String obj = CompilerUtil.getObjDir(file, project);
		String fileName = FileUtil.getFileName(file, false);
		String projectPath = project.getLocationURI().getPath();
		String cmd_nask = CompilerUtil.getCmd(nask).replace("%.asm", obj + "/" + fileName + ".asm").replace("%.obj", obj + "/" + fileName + ".obj").replace("%.lst",
				obj + "/" + fileName + ".lst");

		RunResult result = EXERunner.run(cmd_nask, projectPath);

		return new BuildResult(result);
	}

	public static void main(String[] args) {
		System.out.println(StringUtils.isEmpty(""));
	}
}
