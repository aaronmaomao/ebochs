package com.mwos.ebochs.core.build;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.resource.project.OSProject;
import com.mwos.ebochs.ui.preference.OSDevPreference;
import com.mwos.ebochs.ui.view.ConsoleFactory;

public class DefaultBuilder extends AbstractBuilder{
	
	public static final String c2gas = "c2gas";
	public static final String c2asm = "c2asm";
	public static final String gas2asm = "gas2asm";
	public static final String nask = "nask";
	public static final String link = "obj2bim";
	
	public static final String toolchain = "default_toolchain";

	public DefaultBuilder(OSProject project) throws Exception {
		super(project,toolchain);
	}

	@Override
	public BuildResult compileC(String src, String obj, String inc) {
		// TODO Auto-generated method stub
		return null;
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
		cmd = cmd.replace("%.stack", stack);
		cmd = cmd.replace("%.map", "obj/" + name + ".map");
		String objStr = "";
		for (String obj : objs) {
			objStr += (" " + obj);
		}
		cmd = cmd.replace("%objs", objStr);
		return cmd;
	}
}
