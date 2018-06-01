package com.mwos.ebochs.core.build;

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

public class Compiler {

	// cc1 -m32 -O1 $*.c -o $(objp)$*.gas -I D:\study\30day\tolset\z_tools\haribote
	// cc1 -m32 -gcoff -masm=intel -O1 $*.c -o $(objp)_$*.asm -I
	// D:\study\30day\tolset\z_tools\haribote

	private static String toolchain = "D:\\study\\mwos\\001-data\\toolchain";
	private static String cc1 = toolchain + "\\cc1.exe";
	private static String cmdReg1 = " -m32 -O1 \"%c\" -o \"%obj\"  %inc";
	private static String cmdReg2 = " -m32 -gcoff -masm=intel -O1 \"%c\" -o \"%obj\" %inc";

	private Compiler() {

	}

	public static String compile(String file, IProject project) throws Exception {
		String projectPath = project.getLocationURI().getPath();
		String incs = getInc(project);
		String name = FileUtil.getFileName(file, false);
		String gasExe = cc1 + cmdReg1;
		gasExe = gasExe.replace("%c", file).replace("%obj", "obj\\" + name + ".gas").replace("%inc", incs);
		String asmExe = cc1 + cmdReg2;
		asmExe = asmExe.replace("%c", file).replace("%obj", "obj\\_" + name + ".asm").replace("%inc", incs);
		
		RunResult result = EXERunner.run(gasExe, projectPath);
		EXERunner.runNoResult(asmExe, projectPath);
		if (result.exitValue() == 0) {
			return result.getNormalInfo();
		} else {
			return result.getErrorInfo();
		}
	}
	
	private static String getInc(IProject project) {
		String incs = FileUtil.getIncStr(project.getLocationURI().getPath());
		ICProjectDescription projectDescription = CoreModel.getDefault().getProjectDescription(project);
		ICConfigurationDescription activeConfiguration = projectDescription.getActiveConfiguration(); // or another config
		ICFolderDescription folderDescription = activeConfiguration.getRootFolderDescription(); // or use getResourceDescription(IResource), or pick one from getFolderDescriptions()
		ICLanguageSetting[] languageSettings = folderDescription.getLanguageSettings();
		for(ICLanguageSetting languageSetting:languageSettings) {
			for(ICLanguageSettingEntry includePathSetting:languageSetting.getSettingEntries(ICSettingEntry.INCLUDE_PATH)) {
				String inc = includePathSetting.getValue();
				if(!inc.isEmpty()&&!incs.contains(inc)) {
					incs+=(" -I \""+inc.trim()+"\"");
				}
			}
		}
		
		return incs;
	}
	
	public static void main(String[] args) {
		String file = "src\\s.d";
		String name = file.split("\\\\")[file.split("\\\\").length - 1];
		System.out.println(name);
	}
}
