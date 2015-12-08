package com.exercise.favorites.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.json.JSONObject;

import com.exercise.favorites.domain.VenueFavorite;

@Path("/venueFavoritesWS")
public class VenueFavoritesWebService {

	private VenueFavoriteService venueFavoriteService;

	@PostConstruct
	private void createServices() {
		venueFavoriteService = new VenueFavoriteService();
	}

	/**
	 * Save venue as favorite.
	 * 
	 * @param favorite
	 *            in JSON format e.g. {"venueid":"venueId",
	 *            "venuename":"venueName", "venueaddress":"venueAddress",
	 *            "venueurl":"www.google.com", "venuephone":"123456"},
	 *            "keywords":"words here"}
	 * @return "OK" for successful save, error message otherwise.
	 */
	@GET
	@Path("/saveFavoriteVenue/{venueFavorite}")
	@Consumes("application/json")
	@Produces("application/json")
	public String saveFavoriteVenue(@PathParam("venueFavorite") String favorite) {
		try {
			venueFavoriteService.saveFavorite(VenueFavorite.parseFromJson(favorite));
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "{\"result\":\"ok\"}";
	}
	
	/**
	 * Get list of favorite venues.
	 *
	 * @return favorites as json
	 */
	@GET
	@Path("/getFavoriteVenues")
	@Produces("application/json")
	public String getFavoriteVenue() {
		List<String> result = new ArrayList<String>();
		try {
			result = venueFavoriteService.getFavorites();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		JSONObject obj = new JSONObject();
		obj.put("favorites", result);
		return obj.toString();
	}

	/**
	 * Edit venue favorite. Finds the favorite to edit by the venueid given in
	 * parameter and updates it with new info.
	 * 
	 * @param favorite
	 *            in JSON format e.g. {"venueid":"venueId",
	 *            "venuename":"venueName", "venueaddress":"venueAddress",
	 *            "venueurl":"www.google.com", "venuephone":"123456"},
	 *            "keywords":"words here"}
	 * @return "OK" for successfull save, error message otherwise.
	 */
	@GET
	@Path("/editFavoriteVenue/{venueFavorite}")
	@Consumes("application/json")
	@Produces("application/json")
	public String editFavoriteVenue(@PathParam("venueFavorite") String favorite) {
		try {
			venueFavoriteService.editFavorite(VenueFavorite.parseFromJson(favorite));
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "{\"result\":\"ok\"}";
	}

	/**
	 * Deletes a favorite venue by id given in the parameter.
	 * 
	 * @param venueId, json format: {"venueid":"id"}
	 * @return
	 */
	@GET
	@Path("/deleteFavoriteVenue/{venueId}")
	@Consumes("application/json")
	@Produces("application/json")
	public String deleteFavoriteVenue(@PathParam("venueId") String venueId) {
		JSONObject obj = new JSONObject(venueId);
		try {
			String venueIdString = obj != null ? obj.get("venueid").toString().trim() : "";
			venueFavoriteService.deleteFavorite(venueIdString);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "{\"result\":\"ok\"}";
	}
}
