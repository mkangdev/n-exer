package com.exercise.favorites.domain;

import org.json.JSONException;
import org.json.JSONObject;


public class VenueFavorite {

	private final Venue venue;
	private final String keywords;

	/**
	 * JSON format: {"venueid":"venueId", "venuename":"venueName",
	 * "venueaddress":"venueAddress", "venueurl":"www.google.com",
	 * "venuephone":"123456"}, "keywords":"words here"}
	 * 
	 * @param favoriteAsJson
	 * @return
	 * @throws JSONException
	 */
	public static VenueFavorite parseFromJson(String favoriteAsJson) throws JSONException {
		JSONObject obj = new JSONObject(favoriteAsJson);
		Venue favVenue = new Venue(obj.getString("venueid").trim(), obj.getString("venuename").trim(),
				obj.getString("venueaddress").trim(), obj.getString("venueurl").trim(),
				obj.getString("venuephone").trim());
		return new VenueFavorite(favVenue, obj.getString("keywords").trim());
	}

	public VenueFavorite(Venue venue, String keywords) {
		this.venue = venue;
		this.keywords = keywords;
	}

	public Venue getVenue() {
		return venue;
	}

	public String getKeywords() {
		return keywords;
	}

	@Override
	public String toString() {
		return venue.getId() + ";" + venue.getName() + ";" + venue.getAddress() + ";" + venue.getUrl() + ";"
				+ venue.getPhone() + ";" + keywords;
	}
}
