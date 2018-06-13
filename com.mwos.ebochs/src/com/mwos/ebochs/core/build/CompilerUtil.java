package com.mwos.ebochs.core.build;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
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

	public static String getCmd(String type) {
		String cmd = "";
		if (type.equals(Compiler.c2gas)) {
			cmd = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "\\cc1.exe -m32 -O1 %.c -o %.gas %inc";
		} else if (type.equals(Compiler.c2asm)) {
			cmd = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "\\cc1.exe -m32 -gcoff -masm=intel -O1 %.c -o %.asm %inc";
		} else if (type.equals(Compiler.gas2asm)) {
			cmd = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "\\gas2nask.exe %.gas %.asm";
		} else if (type.equals(Compiler.nask)) {
			cmd = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "\\nask.exe %.asm %.obj %.lst";
		} else if (type.equals(Compiler.link)) {
			cmd = OSDevPreference.getValue(OSDevPreference.TOOLCHAIN) + "\\obj2bim.exe  @"+OSDevPreference.getValue(OSDevPreference.TOOLCHAIN)+"/lib/haribote.rul out:%.out stack:%.stack map:%.map %objs";
		}
		return cmd;
	}

	public static String getObj(String file, String objName, IProject p) {
		String fileName = FileUtil.getFileName(file, false);
		String getObjDir = "/obj";
		String dirs[] = file.split("/");
		for (int i = 0; i < dirs.length - 1; i++) {
			if (!dirs[i].isEmpty())
				getObjDir += ("/" + dirs[i]);
		}
		File f = new File(p.getLocationURI().getPath() + getObjDir);
		if (!f.exists())
			f.mkdirs();
		if (StringUtils.isNotBlank(objName)) {
			getObjDir += objName.replace("%", fileName);
		}
		return getObjDir;
	}

	public static String getObjDir(String file, IProject p) {
		String getObjDir = "/obj";
		String dirs[] = file.split("/");
		for (int i = 0; i < dirs.length - 1; i++) {
			if (!dirs[i].isEmpty())
				getObjDir += ("/" + dirs[i]);
		}
		File f = new File(p.getLocationURI().getPath() + getObjDir);
		if (!f.exists())
			f.mkdirs();
		return getObjDir;
	}

	// public static void main(String[] args) {
	// System.out.println(getObjDir(""));
	// }

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
