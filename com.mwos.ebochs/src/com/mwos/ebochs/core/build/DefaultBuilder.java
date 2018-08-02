package com.mwos.ebochs.core.build;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.exe.EXERunner;
import com.mwos.ebochs.core.exe.RunResult;
import com.mwos.ebochs.resource.config.entity.Image;
import com.mwos.ebochs.resource.config.entity.ImgFile;
import com.mwos.ebochs.resource.project.OSProject;
import com.mwos.ebochs.ui.preference.OSDevPreference;

public class DefaultBuilder extends AbstractBuilder {

	public DefaultBuilder() throws Exception {
		super();
	}

	private static final String c2gas = "c2gas";
	private static final String c2asm = "c2asm";
	private static final String gas2asm = "gas2asm";
	private static final String nask = "nask";
	private static final String link = "obj2bim";
	private static final String edimg = "edimg.exe";
	private static final String bin2obj = "bin2obj.exe";
	private static final String bim2mwe = "bim2hrb.exe";

	public static final String toolchain = "default_toolchain";

	private static Map<IProject, DefaultBuilder> _instance = new HashMap<>();

	@Override
	public BuildResult compileC(String src, String out, IProject project) throws Exception {
		String prjPath = project.getLocationURI().getPath();
		String name = FileUtil.getFileName(src, false);
		List<String> incs = OSProject.getIncDirs(project);

		// 汇编为AT汇编
		String cmd_c2gas = cmd_c2gas(src, getIncStr(incs));
		RunResult c2gasResult = EXERunner.run(cmd_c2gas, prjPath);
		if (c2gasResult.exitValue() != 0) {
			return new BuildResult(c2gasResult);
		}

		// 汇编为intel汇编
		String cmd_c2asm = cmd_c2asm(src, getIncStr(incs));
		EXERunner.run(cmd_c2asm, prjPath);

		// AT转intel汇编
		String cmd_gas2asm = cmd_gas2asm(src);
		EXERunner.run(cmd_gas2asm, prjPath);

		// 编译
		String cmd_nask = cmd_nask("obj/" + name + ".asm", out);
		RunResult naskResult = EXERunner.run(cmd_nask, prjPath);

		return new BuildResult(c2gasResult).merge(new BuildResult(naskResult));
	}

	@Override
	public BuildResult compileAsm(String src, String out, IProject project) throws Exception {
		String prjPath = project.getLocationURI().getPath();

		// 编译
		String cmd_nask = cmd_nask(src, out);
		RunResult naskResult = EXERunner.run(cmd_nask, prjPath);

		return new BuildResult(naskResult);
	}

	@Override
	public BuildResult link(String out, String stack, String objs[], IProject project) throws Exception {
		String prjPath = project.getLocationURI().getPath();

		// 链接
		String cmd_link = cmd_link(out, stack, objs);
		RunResult naskResult = EXERunner.run(cmd_link, prjPath);
		return new BuildResult(naskResult);
	}

	@Override
	public BuildResult buildImg(Image img) throws Exception {
		String prjPath = img.getConfig().getProject().getLocation().toString();

		// 构建镜像
		String cmd_img = cmd_img(img);
		RunResult naskResult = EXERunner.run(cmd_img, prjPath);
		return new BuildResult(naskResult);
	}

	@Override
	public BuildResult bin2obj(String src, String out, String name, IProject project) throws Exception {
		String prjPath = project.getProject().getLocationURI().getPath();

		// 将二进制文件转为可连接目标文件
		String cmd_bin2obj = cmd_bin2obj(src, out, name);
		RunResult naskResult = EXERunner.run(cmd_bin2obj, prjPath);
		return new BuildResult(naskResult);
	}

	@Override
	public BuildResult bim2mwe(String src, String out, String dsSize, IProject project) throws Exception {
		String prjPath = project.getProject().getLocationURI().getPath();

		// 把可执行二进制文件封装为OS可执行文件
		String cmd_bim2hrb = cmd_bim2hrb(src, out, dsSize);
		RunResult naskResult = EXERunner.run(cmd_bim2hrb, prjPath);
		return new BuildResult(naskResult);
	}

	@Override
	public String getToolChain() {
		return OSDevPreference.TOOLCHAIN;
	}

	private String getBuildCmd(String type) {
		String cmd = "";
		if (type.equals(DefaultBuilder.c2gas)) {
			cmd = toolchainPath + "\\cc1.exe -m32 -O1 %.c -o %.gas %inc";
		} else if (type.equals(DefaultBuilder.c2asm)) {
			cmd = toolchainPath + "\\cc1.exe -m32 -gcoff -masm=intel -O1 %.c -o %.asm %inc";
		} else if (type.equals(DefaultBuilder.gas2asm)) {
			cmd = toolchainPath + "\\gas2nask.exe %.gas %.asm";
		} else if (type.equals(DefaultBuilder.nask)) {
			cmd = toolchainPath + "\\nask.exe %.asm %.obj %.lst";
		} else if (type.equals(DefaultBuilder.link)) {
			cmd = toolchainPath + "\\obj2bim.exe  @" + toolchainPath + "/lib/haribote.rul out:%.out stack:%.stack map:%.map %objs";
		} else if (type.equals(DefaultBuilder.edimg)) {
			cmd = toolchainPath + "\\edimg.exe imgin:" + toolchainPath + "/%.fat wbinimg src:%.mbr len:512 from:0 to:0 %.copy imgout:%.name";
		} else if (type.equals(DefaultBuilder.bin2obj)) {
			cmd = toolchainPath + "\\bin2obj.exe %.src %.out %.name";
		} else if (type.equals(DefaultBuilder.bim2mwe)) {
			cmd = toolchainPath + "\\bim2hrb.exe %.src %.out %.dsSize";
		}
		return cmd;
	}

	private String cmd_c2gas(String file, String inc) {
		String name = FileUtil.getFileName(file, false);
		String cmd = getBuildCmd(c2gas);
		cmd = cmd.replace("%.c", file);
		cmd = cmd.replace("%.gas", "obj/" + name + ".gas");
		cmd = cmd.replace("%inc", inc);
		return cmd;
	}

	private String cmd_c2asm(String file, String inc) {
		String name = FileUtil.getFileName(file, false);
		String cmd = getBuildCmd(c2asm);
		cmd = cmd.replace("%.c", file);
		cmd = cmd.replace("%.asm", "obj/" + name + "_.asm");
		cmd = cmd.replace("%inc", inc);
		return cmd;
	}

	private String cmd_gas2asm(String file) {
		String name = FileUtil.getFileName(file, false);
		String cmd = getBuildCmd(gas2asm);
		cmd = cmd.replace("%.gas", "obj/" + name + ".gas");
		cmd = cmd.replace("%.asm", "obj/" + name + ".asm");
		return cmd;
	}

	private String cmd_nask(String file, String out) {
		String name = FileUtil.getFileName(file, false);
		String cmd = getBuildCmd(nask);
		cmd = cmd.replace("%.asm", file);
		if (out.startsWith("/"))
			out = out.substring(1, out.length());
		cmd = cmd.replace("%.obj", out);
		cmd = cmd.replace("%.lst", "obj/" + name + ".lst");
		return cmd;
	}

	private String cmd_link(String out, String stack, String[] objs) {
		String name = FileUtil.getFileName(out, false);
		String cmd = getBuildCmd(link);
		cmd = cmd.replace("%.out", "obj/"+name+".bim");
		cmd = cmd.replace("%.stack", stack);
		cmd = cmd.replace("%.map", "obj/" + name + ".map");
		String objStr = "";
		for (String obj : objs) {
			objStr += (" " + FileUtil.getRelPath(obj));
		}
		cmd = cmd.replace("%objs", objStr);
		return cmd;
	}

	private String cmd_img(Image img) {
		String cmd = getBuildCmd(edimg);
		cmd = cmd.replace("%.fat", "fat12.tek");
		cmd = cmd.replace("%.mbr", FileUtil.getRelPath(img.getMbr()));
		String files = "";
		for (ImgFile file : img.getImgFiles()) {
			files += (" copy from:" + FileUtil.getRelPath(file.getSrc()) + " to:@: ");
		}

		cmd = cmd.replace("%.copy", files);
		cmd = cmd.replace("%.name", "obj/images/" + img.getName());
		return cmd;
	}

	private String cmd_bin2obj(String src, String out, String name) {
		String cmd = getBuildCmd(bin2obj);
		cmd = cmd.replace("%.src", src);
		cmd = cmd.replace("%.out", FileUtil.getRelPath(out));
		cmd = cmd.replace("%.name", name);
		return cmd;
	}

	private String cmd_bim2hrb(String src, String out, String dsSize) {
		String cmd = getBuildCmd(bim2mwe);
		cmd = cmd.replace("%.src", src);
		cmd = cmd.replace("%.out", FileUtil.getRelPath(out));
		cmd = cmd.replace("%.dsSize", dsSize);
		return cmd;
	}

	private String getIncStr(List<String> incs) {
		String temp = "";
		for (String inc : incs) {
			temp += (" -I " + inc);
		}
		return temp;
	}

}
