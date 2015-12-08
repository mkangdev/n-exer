package com.exercise.favorites.domain;

import org.json.JSONObject;

public class Venue {

	private String id;
	private String name;
	private String address;
	private String url;
	private String phone;

	public Venue(String id, String name, String address, String url, String phone) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.url = url;
		this.phone = phone;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getUrl() {
		return url;
	}

	public String getPhone() {
		return phone;
	}

	public String toJSONString() {
		JSONObject searchFilterJSON = new JSONObject();
		searchFilterJSON.put("id", id);
		searchFilterJSON.put("name", name);
		searchFilterJSON.put("address", address);
		searchFilterJSON.put("url", url);
		searchFilterJSON.put("phone", phone);
		return "{}";
	}
}
