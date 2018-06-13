package com.mwos.ebochs.core.build;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.parser.ExtendedScannerInfo;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IScannerInfoProvider;
import org.eclipse.core.resources.IProject;

import com.mwos.ebochs.core.FileUtil;

public class BuildUtil {
	public static String getProjectInc(IProject project) {
		String incs = FileUtil.getIncStr(project.getLocationURI().getPath());
		IScannerInfoProvider provider = CCorePlugin.getDefault().getScannerInfoProvider(project);
		if (provider != null) {
			IScannerInfo info = provider.getScannerInformation(project);
			if (info instanceof ExtendedScannerInfo) {
				for (String inc : ((ExtendedScannerInfo) info).getIncludePaths()) {
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
