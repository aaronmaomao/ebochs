package com.mwos.ebochs.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OSConfig {

	private String name;
	private String version;
	private String bits;
	private Platform platform;
	private List<Image> images;
	private HashMap<String, Source> sources;

	public OSConfig() {
		images = new ArrayList<>();
		sources = new LinkedHashMap<>();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBits() {
		return bits;
	}

	public void setBits(String bits) {
		this.bits = bits;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public String getName() {
		return name;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Platform getPlatform() {
		return platform;
	}

	public HashMap<String, Source> getSources() {
		return sources;
	}

	public void addImage(Image image) {
		this.images.add(image);
	}

	public void addSource(Source s) {
		this.sources.put(s.getName(), s);
	}

	public Source getSource(String name) {
		return this.sources.get(name);
	}

	public boolean isIncludeCode(String code) {
		for (Source s : sources.values()) {
			if (s.isInclude(code))
				return true;
		}

		return false;
	}
	
	public class Source {
		public static final String CORE = "core";
		public static final String APP = "app";

		private String type;
		private String name;
		private Map<String, Code> codes;

		public Source(String type, String name) {
			this();
			this.type = type;
			this.name = name;
		}

		public Source() {
			this.codes = new LinkedHashMap<>();
		}

		public String getType() {
			return type;
		}

		public String getName() {
			return name;
		}

		public Map<String, Code> getCodes() {
			return codes;
		}

		public Code getCode(String name) {
			return codes.get(name);
		}

		public void addCode(String code) {
			Code c = new Code();
			c.setName(code);
			this.codes.put(code, c);
		}

		public void addCode(Code code) {
			this.codes.put(code.getName(), code);
		}

		public boolean isInclude(String code) {
			return this.codes.containsKey(code);
		}

		public void setType(String type) {
			this.type = type;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

}



class Code {
	public final static String BOOT = "bootloader";
	public final static String NORMAL = "normal";

	private String type = NORMAL;
	private String name;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

class Image {
	private String name;
	private String size;
	private String driver;
	private String format;
	private String bootloader;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getBootloader() {
		return bootloader;
	}

	public void setBootloader(String bootloader) {
		this.bootloader = bootloader;
	}

}

class Platform {
	private String arch;
	private List<String> cpus;

	public Platform() {
		cpus = new ArrayList<>();
	}

	public String getArch() {
		return arch;
	}

	public void setArch(String arch) {
		this.arch = arch;
	}

	public List<String> getCpus() {
		return cpus;
	}

	public void setCpus(List<String> cpus) {
		this.cpus = cpus;
	}

	public void addCpu(String cpu) {
		for (String c : cpus) {
			if (cpu.trim().equals(c))
				return;
		}
		this.cpus.add(cpu);
	}
}