package com.mwos.ebochs2.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSONArray implements Iterable<JSONObject> {
	private List<JSONObject> list;

	public JSONArray() {
		list = new ArrayList<>();
	}

	public void add(JSONObject element) {
		list.add(element);
	}

	public int size() {
		return list.size();
	}

	@Override
	public String toString() {
		String jsonStr = "[";
		for (JSONObject object : list) {
			jsonStr += (object.toString() + ",\r\n");
		}

		return jsonStr = jsonStr.substring(0, jsonStr.lastIndexOf(",")) + "]";
	}

	@Override
	public Iterator<JSONObject> iterator() {
		return list.iterator();
	}

}
