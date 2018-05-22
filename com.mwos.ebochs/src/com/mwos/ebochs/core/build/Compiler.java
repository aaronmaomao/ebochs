package com.mwos.ebochs.core.build;

import java.io.File;

import org.eclipse.core.resources.IProject;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.exe.EXERunner;
import com.mwos.ebochs.core.exe.RunResult;

public class Compiler {

	// cc1 -m32 -O1 $*.c -o $(objp)$*.gas -I D:\study\30day\tolset\z_tools\haribote
	// cc1 -m32 -gcoff -masm=intel -O1 $*.c -o $(objp)_$*.asm -I
	// D:\study\30day\tolset\z_tools\haribote

	private static String toolchain = "D:\\study\\mwos\\001-data\\toolchain";
	private static String cc1 = toolchain + "\\cc1.exe";
	private static String cmdReg1 = " -m32 -O1 \"%c\" -o \"%obj\" -I %inc";
	private static String cmdReg2 = " -m32 -gcoff -masm=intel -O1 \"%c\" -o \"%obj\" -I %inc";

	private Compiler() {

	}

	public static String compile(String file, IProject project) throws Exception {
		String projectPath = project.getLocationURI().getPath();
		String name = FileUtil.getFileName(file, false);
		String gasExe = cc1 + cmdReg1;
		gasExe = gasExe.replace("%c", file).replace("%obj", "obj\\" + name + ".gas").replace("%inc", FileUtil.getIncStr(projectPath));
		String asmExe = cc1 + cmdReg2;
		asmExe = asmExe.replace("%c", file).replace("%obj", "obj\\_" + name + ".asm").replace("%inc", FileUtil.getIncStr(projectPath));
		
		RunResult result = EXERunner.run(gasExe, projectPath);
		EXERunner.runNoResult(asmExe, projectPath);
		if (result.exitValue() == 0) {
			return result.getNormalInfo();
		} else {
			return result.getErrorInfo();
		}
	}

	public static void main(String[] args) {
		String file = "src\\s.d";
		String name = file.split("\\\\")[file.split("\\\\").length - 1];
		System.out.println(name);
	}
}
