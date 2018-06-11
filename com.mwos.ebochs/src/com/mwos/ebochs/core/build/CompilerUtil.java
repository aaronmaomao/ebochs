package com.mwos.ebochs.core.build;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.parser.ExtendedScannerInfo;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IScannerInfoProvider;
import org.eclipse.core.resources.IProject;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.ui.preference.OSDevPreference;

public class CompilerUtil {
	private CompilerUtil() {
	}

	public static String cmd_c2gas(String c, String obj, String inc) {
		String name = FileUtil.getFileName(c, false);
		String cmd = getCmd(Compiler.c2gas);
		return cmd.replace("%.c", c).replace("%.gas", obj + "/" + name + ".gas").replace("%inc", inc);
	}

	public static String cmd_c2asm(String c, String obj, String inc) {
		String name = FileUtil.getFileName(c, false);
		String cmd = getCmd(Compiler.c2asm);
		return cmd.replace("%.c", c).replace("%.asm", obj + "/_" + name + ".asm").replace("%inc", inc);
	}

	public static String cmd_gas2asm(String gas, String obj) {
		String name = FileUtil.getFileName(gas, false);
		String cmd = getCmd(Compiler.gas2asm);
		return cmd.replace("%.gas", gas).replace("%.asm", obj + "/" + name + ".asm");
	}

	public static String cmd_nask(String asm, String obj) {
		String name = FileUtil.getFileName(asm, false);
		String cmd = getCmd(Compiler.gas2asm);
		return cmd.replace("%.asm", asm).replace("%.obj", obj + "/" + name + ".obj").replace("%.lst", obj + "/" + name + ".lst");
	}

	private static String getCmd(String type) {
		String cmd = "";
		if (type.equals(Compiler.c2gas)) {
			cmd = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "\\cc1.exe -m32 -O1 %.c -o %.gas %inc";
		} else if (type.equals(Compiler.c2asm)) {
			cmd = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "\\cc1.exe -m32 -gcoff -masm=intel -O1 %.c -o %.asm %inc";
		} else if (type.equals(Compiler.gas2asm)) {
			cmd = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "\\gas2nask.exe %.gas %.asm";
		} else if (type.equals(Compiler.nask)) {
			cmd = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "\\nask.exe %.asm %.obj %.lst";
		}
		return cmd;
	}

	public static String getInc(IProject project) {
		String incs = FileUtil.getIncStr(project.getLocationURI().getPath());
		IScannerInfoProvider provider = CCorePlugin.getDefault().getScannerInfoProvider(project);
		if (provider != null) {
			IScannerInfo info = provider.getScannerInformation(project);
			if (info instanceof ExtendedScannerInfo) {
				for (String inc : ((ExtendedScannerInfo) info).getLocalIncludePath()) {
					if (!incs.contains(inc))
						incs += (" -I " + inc.trim());
				}
			}
		}

		// ICProjectDescription projectDescription =
		// CoreModel.getDefault().getProjectDescription(project);
		// ICConfigurationDescription activeConfiguration =
		// projectDescription.getActiveConfiguration(); // or another config
		// ICFolderDescription folderDescription =
		// activeConfiguration.getRootFolderDescription(); // or use
		// getResourceDescription(IResource),
		// // or pick one from getFolderDescriptions()
		// ICLanguageSetting[] languageSettings =
		// folderDescription.getLanguageSettings();
		// for (ICLanguageSetting languageSetting : languageSettings) {
		// for (ICLanguageSettingEntry includePathSetting :
		// languageSetting.getSettingEntries(ICSettingEntry.INCLUDE_PATH)) {
		// includePathSetting.getValue();
		// if (!inc.isEmpty() && !incs.contains(inc)) {
		// incs += (" -I " + inc.trim());
		// }
		// }
		// }

		return incs;
	}

}
