package com.mwos.ebochs.core.build;

import java.io.IOException;

import org.eclipse.cdt.core.model.CoreModel;
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

	private static final String c2gas = "c2gas";
	private static final String c2asm = "c2asm";
	private static final String gas2asm = "gas2asm";
	private static final String nask = "nask";

	private Compiler() {

	}

	public static BuildResult compileC(String file, IProject project) throws Exception {
		String projectPath = project.getLocationURI().getPath();
		String fileName = FileUtil.getFileName(file, false);

		String cmd_c2gas = getCmd(Compiler.c2gas).replace("%.c", file).replace("%.gas", fileName + ".gas").replace("%.inc", getInc(project));
		RunResult c2gasResult = EXERunner.run(cmd_c2gas, projectPath);
		if (c2gasResult.exitValue() != 0) {
			return new BuildResult(c2gasResult);
		}

		String cmd_c2asm = getCmd(Compiler.c2asm).replace("%.c", file).replace("%.asm", fileName + ".asm").replace("%.inc", getInc(project));
		EXERunner.run(cmd_c2asm, projectPath);

		String cmd_gas2asm = getCmd(Compiler.gas2asm).replace("%.gas", fileName + ".gas").replace("%.asm", fileName + ".asm");
		EXERunner.run(cmd_gas2asm, projectPath);

		BuildResult naskResult = compileAsm("obj\\" + fileName + ".asm", project);

		return new BuildResult(c2gasResult).merge(naskResult);
	}

	public static BuildResult compileAsm(String file, IProject project) throws IOException, InterruptedException {
		String projectPath = project.getLocationURI().getPath();
		String fileName = FileUtil.getFileName(file, false);

		String cmd_nask = getCmd(Compiler.nask).replace("%.asm", file).replace("%.obj", fileName + ".obj").replace("%.lst", fileName + ".lst");
		RunResult result = EXERunner.run(cmd_nask, projectPath);

		return new BuildResult(result);
	}

	private static String getCmd(String type) {
		String cmd = "";
		if (type.equals(c2gas)) {
			cmd = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "\\cc1.exe -m32 -O1 %.c -o obj\\%.gas %.inc";
		} else if (type.equals(c2asm)) {
			cmd = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "\\cc1.exe -m32 -gcoff -masm=intel -O1 %.c -o obj\\_%.asm %.inc";
		} else if (type.equals(gas2asm)) {
			cmd = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "\\gas2nask.exe obj\\%.gas obj\\%.asm";
		} else if (type.equals(nask)) {
			cmd = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "\\nask.exe %.asm obj\\%.obj obj\\%.lst";
		}
		return cmd;
	}

	private static String getInc(IProject project) {
		String incs = FileUtil.getIncStr(project.getLocationURI().getPath());
		ICProjectDescription projectDescription = CoreModel.getDefault().getProjectDescription(project);
		ICConfigurationDescription activeConfiguration = projectDescription.getActiveConfiguration(); // or another config
		ICFolderDescription folderDescription = activeConfiguration.getRootFolderDescription(); // or use getResourceDescription(IResource),
																								// or pick one from getFolderDescriptions()
		ICLanguageSetting[] languageSettings = folderDescription.getLanguageSettings();
		for (ICLanguageSetting languageSetting : languageSettings) {
			for (ICLanguageSettingEntry includePathSetting : languageSetting.getSettingEntries(ICSettingEntry.INCLUDE_PATH)) {
				String inc = includePathSetting.getValue();
				if (!inc.isEmpty() && !incs.contains(inc)) {
					incs += (" -I " + inc.trim());
				}
			}
		}

		return incs;
	}

	// public static void main(String[] args) {
	// String file = "src\\s.d";
	// String name = file.split("\\\\")[file.split("\\\\").length - 1];
	// System.out.println(name);
	// }
}
