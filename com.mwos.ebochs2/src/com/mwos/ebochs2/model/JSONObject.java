package com.mwos.ebochs2.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class JSONObject {
	private Map<String, Object> _map;

	public JSONObject() {
		_map = new LinkedHashMap<>();
	}

	public JSONObject(String key, Object value) {
		this();
		this.add(key, value);
	}

	public void add(String key, Object value) {
		if (value == null)
			value = "null";
		_map.put(key, value);
	}

	public <T extends Object> T get(String key, Class<T> clazz) {
		Object t = _map.get(key);
		if (t != null) {
			return (T) t;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		String data = "{";
		for (String key : _map.keySet()) {
			Object value = _map.get(key);
			if (!(value instanceof JSONObject) && !(value instanceof JSONArray) && !(value instanceof Integer)) {
				value = "\"" + value.toString().replaceAll("\"", "&quot;") + "\"";
			}
			data += ("\"" + key + "\":" + value + ",");
		}
		data = (data.substring(0, data.length() - 1) + "}");
		return data;
	}

	public static void main(String[] args) {
	}

}
