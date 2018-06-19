package com.mwos.ebochs.resource.config.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.build.AbstractBuilder;
import com.mwos.ebochs.core.build.BuildResult;
import com.mwos.ebochs.ui.view.ConsoleFactory;

public class CodePart {
	private String type;
	private OSConfig config;
	private String src = "";
	private String out;
	private List<Code> codes;

	public CodePart(OSConfig config) {
		this.config = config;
		codes = new ArrayList<>();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setObj(String obj) {
		this.out = obj;
	}

	public List<Code> getCodes() {
		return codes;
	}

	public String getOut() {
		return out;
	}
	
	public String build(AbstractBuilder builder) {
		if (StringUtils.isNotBlank(src)) {
			if (new File(config.getProject().getLocationURI().getPath() + out).exists()) {
				return out;
			} else {
				try {

					BuildResult res = builder.compile(src, out, config.getProject());
					if (!res.isSuccess()) {
						ConsoleFactory.outErrMsg("\r\n----- 编译错误:\t" + src + "\r\n" + res.getAllMsg() + "\r\n", config.getProject());
						return null;
					} else {
						ConsoleFactory.outMsg("\r\n----- 编译成功:\t" + src + "\r\n" + res.getAllMsg() + "\r\n", config.getProject());
						return out;
					}
				} catch (Exception e) {
					e.printStackTrace();
					ConsoleFactory.outErrMsg("\r\n----- 系统异常:\t" + src + "\r\n", config.getProject());
					return null;
				}
			}
		} else {
			List<String> objs = new ArrayList<>();
			for (Code code : codes) {
				String obj = code.getOut();
				if (obj == null) {
					ConsoleFactory.outInfoMsg("\r\n----- 构建失败:\t" + out + "\r\n", config.getProject());
					return null;
				}
				objs.add(obj);
				try {
					BuildResult res = builder.link(out, "3355k", objs.toArray(new String[] {}), config.getProject());
					if (!res.isSuccess()) {
						ConsoleFactory.outErrMsg("\r\n----- 链接失败:\t" + out + "\r\n" + res.getAllMsg() + "\r\n", config.getProject());
						return null;
					} else {
						ConsoleFactory.outMsg("\r\n----- 链接成功:\t" + out + "\r\n" + res.getAllMsg() + "\r\n", config.getProject());
						return out;
					}
				} catch (Exception e) {
					e.printStackTrace();
					ConsoleFactory.outErrMsg("\r\n----- 链接错误:\t" + out + "\r\n", config.getProject());
					return null;
				}
			}
		}
		return null;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public Code getCode(String src) {
		if(StringUtils.isNotBlank(this.src)&&src.equals(this.src)) {
			Code c = new Code();
			c.setSrc(src);
			c.setOut(out);
			return c;
		}else {
			for(Code code:codes) {
				if(code.getSrc().equals(src))
					return code;
			}
		}
		return null;
	}

	public void addCode(Code c) {
		for (Code temp : codes) {
			if (temp.getSrc().equals(c.getSrc())) {
				return;
			}
		}
		c.setCp(this);
		this.codes.add(c);
	}

	public OSConfig getConfig() {
		return config;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		if (StringUtils.isBlank(out)) {
			out = "/obj/" + FileUtil.getFileName(src, false) + ".obj";
		}
		this.src = src;
	}

	public boolean equal(CodePart old) {
		if (!this.type.equals(old.type))
			return false;
		if (!this.out.equals(old.out))
			return false;
		if (this.codes.size() != old.codes.size())
			return false;

		for (int i = 0; i < this.codes.size(); i++) {
			if (!this.codes.get(i).equal(old.codes.get(i)))
				return false;
		}

		return true;
	}

	public class Code {
		private String src;
		private String out = "";
		private CodePart cp;

		public String getSrc() {
			return src;
		}

		public void setSrc(String src) {
			if (StringUtils.isBlank(out)) {
				out = "/obj/" + FileUtil.getFileName(src, false) + ".obj";
			}
			this.src = src;
		}

		public String getOut() {
			return out;
		}

		public void setOut(String out) {
			this.out = out;
		}

		public void setCp(CodePart cp) {
			this.cp = cp;
		}

		public boolean equal(Code old) {
			if (!this.src.equals(old.src))
				return false;
			return true;
		}

		public String generateOut(AbstractBuilder builder) {
			IProject prj = cp.getConfig().getProject();
			File objF = new File(prj.getLocationURI().getPath() + out);
			if (!objF.exists()) {
				try {
					BuildResult res = builder.compile(src, out, prj);
					if (!res.isSuccess()) {
						ConsoleFactory.outErrMsg("\r\n----- 编译失败:\t" + src + "\r\n" + res.getAllMsg() + "\r\n", prj);
						return null;
					} else {
						ConsoleFactory.outMsg("\r\n----- 编译成功:\t" + src + "\r\n" + res.getAllMsg() + "\r\n", prj);
						return out;
					}
				} catch (Exception e) {
					e.printStackTrace();
					ConsoleFactory.outErrMsg("\r\n----- 系统异常:\t" + src + "\r\n", prj);
					return null;
				}
			} else {
				return out;
			}
		}
	}
}
