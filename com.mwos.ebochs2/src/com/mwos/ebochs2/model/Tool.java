package com.mwos.ebochs2.model;

public class Tool implements IJSONSerial {
	private String name;

	public Tool() {
		// TODO Auto-generated constructor stub
	}

	public Tool(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public JSONObject getSerial() {
		JSONObject object = new JSONObject("name",this.name);
		return object;
	}

	@Override
	public void setSerial(JSONObject serial) {
		this.name = serial.get("name", String.class);
	}
}
