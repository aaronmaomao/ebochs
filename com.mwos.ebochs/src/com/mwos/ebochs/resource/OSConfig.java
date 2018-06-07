package com.mwos.ebochs.resource;

import java.util.ArrayList;
import java.util.List;

public class OSConfig {

	private String name;
	private String version;
	private String bits;
	private Platform platform;

}

class Source{
	public static final String CORE = "core";
	public static final String APP = "core";
	
	private String type;
	private String name;
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