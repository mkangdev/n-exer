package com.exercise.venues.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.json.JSONObject;

import com.exercise.venues.domain.Venue;
import com.exercise.venues.domain.VenueSearchFilter;

@Path("/venuesWS")
public class VenueWebService {

	private VenueService venueService;

	@PostConstruct
	private void createServices() {
		venueService = new VenueService();
	}

	/**
	 * Searches venues by coordinates OR name and also by possible search
	 * string. Returns max 10 venues.
	 * 
	 * @param searchValue
	 *            in JSON format e.g.: {"coordinates":"1.111, 2.222", "name":
	 *            "Cafe Rooster", "searchstring":""}
	 * @return List of venues in JSON format e.g.: [{"id":"xxx",
	 *         "name":"Rooster", "address":"Saaristonkatu", "url":"ccc",
	 *         "phone":"123456"}, {"id":yyy" ...}] Or error message in the case
	 *         of error.
	 */
	@GET
	@Path("/searchVenues/{searchValue}")
	@Produces("application/json")
	@Consumes("application/json")
	public String searchVenues(@PathParam("searchValue") String searchValue) {
		List<Venue> venues = new ArrayList<Venue>();
		try {
			venues = venueService.searchVenues(VenueSearchFilter.parseFromJson(searchValue));
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return JSONObject.valueToString(venues);
	}

	/**
	 * Gets photos for given venue.
	 * 
	 * @param venueId
	 *            in JSON format e.g. {"venueid":"A9ABCD"}
	 * @return List of urls in JSON format.
	 */
	@GET
	@Path("/getPhotos/{venueId}")
	@Produces("application/json")
	@Consumes("application/json")
	public String getVenuePhotos(@PathParam("venueId") String venueId) {
		List<String> photoUrls = new ArrayList<String>();
		JSONObject obj = new JSONObject(venueId);
		try {
			String venueIdString = obj != null ? obj.get("venueid").toString().trim() : "";
			photoUrls = venueService.getVenuePhotos(venueIdString);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return JSONObject.valueToString(photoUrls);
	}
	
	/**
	 * Gets venue data for the given id.
	 * 
	 * @param venueId
	 *            in JSON format e.g. {"venueid":"A9ABCD"}
	 * @return Venue
	 */
	@GET
	@Path("/getVenue/{venueId}")
	@Produces("application/json")
	@Consumes("application/json")
	public String getVenue(@PathParam("venueId") String venueId) {
		Venue venue = null;
		JSONObject obj = new JSONObject(venueId);
		try {
			String venueIdString = obj != null ? obj.get("venueid").toString().trim() : "";
			venue = venueService.getVenue(venueIdString);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return JSONObject.valueToString(venue);
	}
}
