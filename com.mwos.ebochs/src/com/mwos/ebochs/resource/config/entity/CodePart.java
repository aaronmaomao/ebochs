package com.mwos.ebochs.resource.config.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mwos.ebochs.core.FileUtil;
import com.mwos.ebochs.core.build.AbstractBuilder;
import com.mwos.ebochs.core.build.BuildResult;
import com.mwos.ebochs.ui.view.ConsoleFactory;

public class CodePart {
	private String type;
	private OSConfig config;
	private String src = "";
	private String out = "";
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
						ConsoleFactory.outErrMsg("----- 编译错误:\t" + src + "\r\n" + res.getAllMsg() + "\r\n", config.getProject());
						return null;
					} else {
						ConsoleFactory.outMsg("----- 编译成功:\t" + src + "\r\n" + res.getAllMsg() + "\r\n", config.getProject());
						return out;
					}
				} catch (Exception e) {
					e.printStackTrace();
					ConsoleFactory.outErrMsg("----- 系统异常:\t" + src + "\r\n", config.getProject());
					return null;
				}
			}
		} else {
			try {
				BuildResult res = builder.buildMWE(this);
				if (!res.isSuccess()) {
					ConsoleFactory.outErrMsg("----- 构建失败:\t" + getOut() + "\r\n" + res.getAllMsg() + "\r\n", config.getProject());
					return null;
				} else {
					ConsoleFactory.outMsg("----- 构建成功:\t" + getOut() + "\r\n" + res.getAllMsg() + "\r\n", config.getProject());
					return out;
				}
			} catch (Exception e) {
				e.printStackTrace();
				ConsoleFactory.outErrMsg("----- 系统异常:\t" + getOut() + "\r\n", config.getProject());
				return null;
			}
		}
	}

	public void setOut(String out) {
		if (!out.contains("/")) {
			out = "/obj/" + FileUtil.getFileName(out, true);
		}
		this.out = out;
	}

	public Code getCode(String src) {
		if (StringUtils.isNotBlank(this.src) && src.equals(this.src)) {
			Code c = new Code();
			c.setSrc(src);
			c.setOut(out);
			return c;
		} else {
			for (Code code : codes) {
				if (code.getSrc().equals(src))
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
		if (StringUtils.isEmpty(out)) {
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
		private boolean linkOnly = false;

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

		public void setLinkOnly(String linkOnly) {
			if (linkOnly.equals("true"))
				this.linkOnly = true;
			else
				this.linkOnly = false;
		}

		public void setOut(String out) {
			if (!out.contains("/"))
				out = "/obj/" + FileUtil.getFileName(out, true);
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

		public String build(AbstractBuilder builder) {
			if (!linkOnly) {
				if (!new File(cp.getConfig().getProject().getLocationURI().getPath() + getOut()).exists()) {
					try {
						BuildResult res = builder.compile(src, out, cp.getConfig().getProject());
						if (!res.isSuccess()) {
							ConsoleFactory.outErrMsg("----- 编译错误:\t" + src + "\r\n" + res.getAllMsg() + "\r\n", cp.getConfig().getProject());
							return null;
						} else {
							ConsoleFactory.outMsg("----- 编译成功:\t" + src + "\r\n" + res.getAllMsg() + "\r\n", cp.getConfig().getProject());
							return out;
						}
					} catch (Exception e) {
						e.printStackTrace();
						ConsoleFactory.outErrMsg("----- 系统出错：\r\n" + e.getMessage() + "\r\n", cp.getConfig().getProject());
						return null;
					}
				} else {
					return out;
				}
			}
			return src;
		}
	}
}
