package com.mwos.ebochs2.model;

import org.eclipse.json.provisonnal.com.eclipsesource.json.JsonObject;

public class Tool implements ISeriable {
	private String name;
	private String path = "";

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String getSerial() {
		JsonObject object = new JsonObject();
		object.add("name", name);
		object.add("path", path);
		return object.toString();
	}

	@Override
	public boolean setSerial(String str) {
		JsonObject object = JsonObject.readFrom(str);
		name = object.getString("name", null);
		path = object.getString("path", null);
		return true;
	}

}
