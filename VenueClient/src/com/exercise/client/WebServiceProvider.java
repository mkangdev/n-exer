package com.exercise.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebServiceProvider {

	private final String VENUE_SERVICE_URL = "http://localhost:8080/Venue/venuesWS/";
	private final String SEARCH_VENUES = "searchVenues/";
	private final String GET_PHOTOS = "getPhotos/";
	private final String GET_VENUE = "getVenue/";

	private final String VENUE_FAVORITE_SERVICE_URL = "http://localhost:8080/VenueFavorite/venueFavoritesWS/";
	private final String SAVE_FAVORITE = "saveFavoriteVenue/";
	private final String GET_FAVORITES = "getFavoriteVenues/";
	private final String EDIT_FAVORITE = "editFavoriteVenue/";
	private final String DELETE_FAVORITE = "deleteFavoriteVenue/";

	private Client client;

	public WebServiceProvider() {
		client = ClientBuilder.newClient();
	}

	public String searchVenues(String json) {
		// This will always return error because of the foursquare problem I
		// have
		return callWebService(VENUE_SERVICE_URL + SEARCH_VENUES + json);
	}

	public String getPhotos(String json) {
		// This will always return error because of the foursquare problem I
		// have
		return callWebService(VENUE_SERVICE_URL + GET_PHOTOS + json);
	}

	public String saveVenueFavorite(String json) {
		// This won't work because of the foursquare problem so let's just
		// create dummy favorite. Plan was to get the venue by the given id and
		// collect the data to save from that
		// String venue = callWebService(VENUE_SERVICE_URL + GET_VENUE + json);

		Random r = new Random();
		String fav = "%7B\"venueid\":\"id" + r.nextInt(1000)
				+ "\",\"venuename\":\"venueName\", \"venueaddress\":\"venueAddress\",\"venueurl\":"
				+ "\"www.google.com\",\"venuephone\":\"123456\",\"keywords\":\"words here\"%7D";
		
		String result = callWebService(VENUE_FAVORITE_SERVICE_URL + SAVE_FAVORITE + fav);
		return result;
	}

	public List<String> getVenueFavorites() throws JSONException {
		List<String> favorites = new ArrayList<String>();
		String favs = callWebService(VENUE_FAVORITE_SERVICE_URL + GET_FAVORITES);
		JSONObject obj = new JSONObject(favs);
		JSONArray array = (JSONArray) obj.get("favorites");
		for (int i = 0; i < array.length(); i++) {
			favorites.add(array.getString(i));
		}
		return favorites;
	}

	public String editVenueFavorite(String json) {
		return callWebService(VENUE_FAVORITE_SERVICE_URL + EDIT_FAVORITE + json);
	}

	public String deleteVenueFavorite(String json) {
		return callWebService(VENUE_FAVORITE_SERVICE_URL + DELETE_FAVORITE + json);
	}

	private String callWebService(String target) {
		WebTarget webtarget = client.target(target);
		String response = webtarget.request(MediaType.APPLICATION_JSON).get(String.class);
		return response;
	}

}
