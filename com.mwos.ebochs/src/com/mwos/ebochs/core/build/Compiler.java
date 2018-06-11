package com.mwos.ebochs.core.build;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.parser.ExtendedScannerInfo;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IScannerInfoProvider;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICFolderDescription;
import org.eclipse.cdt.core.settings.model.ICLanguageSetting;
import org.eclipse.cdt.core.settings.model.ICLanguageSettingEntry;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICSettingEntry;
import org.eclipse.core.resources.IProject;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.exe.EXERunner;
import com.mwos.ebochs.core.exe.RunResult;
import com.mwos.ebochs.ui.preference.OSDevPreference;

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

	public static BuildResult compileC(String file, String objDir, IProject project) throws Exception {
		String projectPath = project.getLocationURI().getPath();
		String fileName = FileUtil.getFileName(file, false);
		if (StringUtils.isEmpty(objDir)) {
			objDir = "/obj";

		} else if (!objDir.startsWith("/") && !objDir.startsWith("\\")) {
			objDir = "/" + objDir;
		}

		File objF = new File(projectPath + objDir);
		if (!objF.exists() || !objF.isDirectory())
			objF.mkdirs();

		String inc = CompilerUtil.getInc(project);

		String cmd_c2gas = CompilerUtil.cmd_c2gas(file, objDir, inc);
		RunResult c2gasResult = EXERunner.run(cmd_c2gas, projectPath);
		if (c2gasResult.exitValue() != 0) {
			return new BuildResult(c2gasResult);
		}

		String cmd_c2asm = CompilerUtil.cmd_c2asm(file, objDir, inc);
		EXERunner.run(cmd_c2asm, projectPath);

		String cmd_gas2asm = CompilerUtil.cmd_gas2asm(objDir + "/" + fileName + ".gas", objDir);
		EXERunner.run(cmd_gas2asm, projectPath);

		BuildResult naskResult = compileAsm(objDir + "/" + fileName + ".asm", objDir, project);

		return new BuildResult(c2gasResult).merge(naskResult);
	}

	public static BuildResult compileAsm(String file, String objDir, IProject project) throws IOException, InterruptedException {
		String projectPath = project.getLocationURI().getPath();
		String fileName = FileUtil.getFileName(file, false);
		if (StringUtils.isEmpty(objDir)) {
			objDir = "/obj";

		} else if (!objDir.startsWith("/") && !objDir.startsWith("\\")) {
			objDir = "/" + objDir;
		}

		String cmd_nask = CompilerUtil.cmd_nask(file, objDir);
		RunResult result = EXERunner.run(cmd_nask, projectPath);

		return new BuildResult(result);
	}

	public static void main(String[] args) {
		System.out.println(StringUtils.isEmpty(""));
	}
}
