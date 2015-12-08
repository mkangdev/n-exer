package com.exercise.venues.domain;

import org.json.JSONObject;

public class VenueSearchFilter {

	private String coordinates;
	private String name;
	private String searchString;

	/**
	 * Json format: {"coordinates":"1.111, 2.222", "name":"venueName", "searchstring":"oulu"}
	 * 
	 * @param jsonSearchFilter
	 * @return
	 * @throws Exception
	 */
	public static VenueSearchFilter parseFromJson(String jsonSearchFilter) throws Exception {
		JSONObject obj = new JSONObject(jsonSearchFilter);
		String tempCoords = obj.get("coordinates").toString().trim();
		String tempName = obj.get("name").toString().trim();
		if (tempCoords.isEmpty() && tempName.isEmpty())
			throw new Exception("Either Coordinates or name must be given.");
		return new VenueSearchFilter(tempCoords, tempName, obj.get("searchString").toString());
	}

	private VenueSearchFilter(String coordinates, String name, String searchString) {
		this.coordinates = coordinates;
		this.name = name;
		this.searchString = searchString;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public String getName() {
		return name;
	}

	public String getSearchString() {
		return searchString;
	}
}
