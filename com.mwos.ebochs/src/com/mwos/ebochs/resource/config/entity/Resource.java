package com.mwos.ebochs.resource.config.entity;

public class Resource {

	public static final String CODEPART = "codepart";

	private String type;
	private String name;

	private OSConfig config;

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

	public void setConfig(OSConfig config) {
		this.config = config;
	}

	public boolean equal(Resource old) {
		if (!this.type.equals(old.type))
			return false;
		if (!this.name.equals(old.name))
			return false;

		if (this.type.equals(Resource.CODEPART)) {
			CodePart newCp = config.getCodePart(this.name);
			CodePart oldCp = old.config.getCodePart(old.name);
			if (newCp == null || oldCp == null)
				return false;

			if (newCp.getCodes().size() != oldCp.getCodes().size())
				return false;

			for (int i = 0; i < newCp.getCodes().size(); i++) {
				if (!newCp.getCodes().get(i).equal(oldCp.getCodes().get(i)))
					return false;
			}
		}

		return true;
	}
}
