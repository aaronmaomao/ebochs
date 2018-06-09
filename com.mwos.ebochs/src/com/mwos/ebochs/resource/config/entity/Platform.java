package com.mwos.ebochs.resource.config.entity;

import java.util.ArrayList;
import java.util.List;

public class Platform {
	private String arch;
	private List<String> cpus;

	public Platform() {
		cpus = new ArrayList<String>();
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
	
	public void addCpu(String cpu) {
		cpus.add(cpu);
	}
}
