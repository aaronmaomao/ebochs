package com.mwos.ebochs.ui;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;

import com.mwos.ebochs.core.FileUtil;

public class UiUtil {
	public static String chooseFolder(String name, Composite container) {
		DirectoryDialog filedlg = new DirectoryDialog(container.getShell(), SWT.OPEN);
		filedlg.setText("选择" + name);
		filedlg.setFilterPath("SystemDrive");
		filedlg.setMessage("");
		String selected = filedlg.open();
		if (selected != null) {
			File file = new File(selected);
			if (file.exists()) {
				return selected;
			}
		}
		return null;
	}
	
	public static String  chooseFile(String name, Composite container) {
		FileDialog filedlg = new FileDialog(container.getShell(), SWT.OPEN);
		filedlg.setText(name);
		filedlg.setFilterPath("SystemRoot");
		filedlg.setFileName(name);
		filedlg.setFilterExtensions(new String[] { "*"+FileUtil.getExt(name) ,"*"});
		String selected = filedlg.open();
		if (selected != null) {
			File file = new File(selected);
			if (file.exists()) {
				return selected;
			}
		}
		return null;
	}
}
