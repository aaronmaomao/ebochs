package com.mwos.ebochs.core.build;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.resource.config.entity.CodePart.Code;
import com.mwos.ebochs.resource.config.entity.Image;
import com.mwos.ebochs.ui.preference.OSDevPreference;

public abstract class AbstractBuilder {

	protected String toolchainPath;

	private static Map<String, AbstractBuilder> _builderInstance = new HashMap<>();

	public AbstractBuilder() throws Exception {
		String tcPath = OSDevPreference.getValue(getToolChain());
		if (tcPath.isEmpty()) {
			throw new Exception("未找到工具链：" + getToolChain());
		}
		toolchainPath = tcPath;
	}

	public abstract BuildResult compileC(String src, String out, IProject project) throws Exception;

	public abstract BuildResult compileAsm(String src, String out, IProject project) throws Exception;

	public abstract BuildResult link(String out, String stack, String objs[], IProject project) throws Exception;

	public abstract BuildResult bin2obj(String src, String out, String name, IProject project) throws Exception;

	public abstract BuildResult buildImg(Image img) throws Exception;

	public BuildResult compile(String src, IProject project) throws Exception {
		String out = "obj/" + FileUtil.getFileName(src, false) + ".obj";
		return compile(src, out, project);
	}

	public BuildResult compile(String src, String out, IProject project) throws Exception {
		if (src.toLowerCase().endsWith(".c")) {
			return this.compileC(src, out, project);
		} else if (src.toLowerCase().endsWith(".asm")) {
			return this.compileAsm(src, out, project);
		} else if (src.toLowerCase().endsWith(".font")) {
			return this.bin2obj(src, out, "_font", project);
		} else {
			BuildResult res = new BuildResult(false, "未知的文件类型");
			return res;
		}
	}

	public BuildResult compile(Code code, IProject project) throws Exception {
		return compile(code.getSrc(), code.getOut(), project);
	}

	public abstract String getToolChain();

	public static <T extends AbstractBuilder> T getBuilder(Class<T> builderClass) throws Exception {
		T builder = (T) _builderInstance.get(builderClass.getName());
		if (builder == null) {
			builder = builderClass.newInstance();
			_builderInstance.put(builderClass.getName(), builder);
		}
		return builder;
	}
}
