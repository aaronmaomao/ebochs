package com.mwos.ebochs.core.build;

import org.eclipse.core.resources.IFile;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.exe.EXERunner;
import com.mwos.ebochs.core.exe.RunResult;

public class BuildTool {
	public static final String c2gas = "c2gas";
	public static final String c2asm = "c2asm";
	public static final String gas2asm = "gas2asm";
	public static final String nask = "nask";
	public static final String link = "obj2bim";

	public static BuildResult compileC(IFile file) throws Exception {
		
		
		String path = file.getFullPath().toString();
		
		
		String projectPath = project.getLocationURI().getPath();
		String fileName = FileUtil.getFileName(file, false);

		String inc = CompilerUtil.getInc(project);
		String obj = CompilerUtil.getObjDir(file, project);

		String cmd_c2gas = CompilerUtil.getCmd(c2gas).replace("%.c", path).replace("%.gas", CompilerUtil.getObj(file, "%.gas", project)).replace("%inc", inc);
		RunResult c2gasResult = EXERunner.run(cmd_c2gas, projectPath);
		if (c2gasResult.exitValue() != 0) {
			return new BuildResult(c2gasResult);
		}

		String cmd_c2asm = CompilerUtil.getCmd(c2asm).replace("%.c", file).replace("%.asm", CompilerUtil.getObj(file, "_%.asm", project)).replace("%inc", inc);
		EXERunner.run(cmd_c2asm, projectPath);

		String cmd_gas2asm = CompilerUtil.getCmd(gas2asm).replace("%.gas", CompilerUtil.getObj(file, "%.gas", project)).replace("%.asm",
				CompilerUtil.getObj(file, "%.asm", project));
		EXERunner.run(cmd_gas2asm, projectPath);

		String cmd_nask = CompilerUtil.getCmd(nask).replace("%.asm", obj + "/" + fileName + ".asm").replace("%.obj", obj + "/" + fileName + ".obj").replace("%.lst",
				obj + "/" + fileName + ".lst");

		RunResult naskResult = EXERunner.run(cmd_nask, projectPath);

		return new BuildResult(c2gasResult).merge(new BuildResult(naskResult));
	}
	
	private 

}
