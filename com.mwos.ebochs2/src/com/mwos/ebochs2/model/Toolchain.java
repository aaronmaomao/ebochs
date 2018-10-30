package com.mwos.ebochs2.model;

import java.util.ArrayList;
import java.util.List;

public class Toolchain implements IJSONSerial {
	private String name;
	private List<Tool> tools;

	public Toolchain() {
		tools = new ArrayList<>();
	}

	public Toolchain(String name) {
		this();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addTool(Tool tool) {
		tools.add(tool);
	}

	public Tool getTool(String name) {
		for (Tool t : tools) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}

	public List<Tool> getTools() {
		return tools;
	}

	public static Toolchain get() {
		Toolchain toolchain = new Toolchain("原生工具链");
		Tool t1 = new Tool("编译器");
		Tool t2 = new Tool("链接器");
		toolchain.addTool(t1);
		toolchain.addTool(t2);
		return toolchain;
	}

	@Override
	public JSONObject getSerial() {
		JSONObject object = new JSONObject("name", name);
		JSONArray array = new JSONArray();
		for (Tool t : tools) {
			array.add(t.getSerial());
		}
		object.add("tools", array);
		return object;
	}

	@Override
	public void setSerial(JSONObject serial) {
		this.name = serial.get("name", String.class);
		JSONArray array = serial.get("tools", JSONArray.class);
		for (JSONObject tool : array) {
			this.tools.add(IJSONSerial.deSerial(tool, Tool.class));
		}
	}
}
