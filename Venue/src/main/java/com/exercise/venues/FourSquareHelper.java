package com.exercise.venues;

import java.util.ArrayList;
import java.util.List;

import com.exercise.venues.domain.Venue;
import com.exercise.venues.domain.VenueSearchFilter;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.CompleteVenue;
import fi.foyt.foursquare.api.entities.Photo;
import fi.foyt.foursquare.api.entities.PhotoGroup;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

/**
 * Helper class for Foursquare API calls. 
 * Doesn't work :)
 * 
 * @author Mari
 *
 */
public class FourSquareHelper {

	private FoursquareApi foursquareApi;

	public FourSquareHelper() {
		foursquareApi = new FoursquareApi("CYEMKOM4OLTP5PHMOFVUJJAMWT5CH5G1JBCYREATW21XLLSZ",
				"GYNP4URASNYRNRGXR5UEN2TGTKJHXY5FGSAXTIHXEUG1GYM2", "");
		foursquareApi.setVersion("20151201");
	}

	public List<Venue> searchVenues(VenueSearchFilter searchFilter) throws Exception {
		List<Venue> venueResult = new ArrayList<Venue>();
		Result<VenuesSearchResult> searchResult = null;
		if (!searchFilter.getCoordinates().isEmpty())
			searchResult = foursquareApi.venuesSearch(searchFilter.getCoordinates(), 0.0, 0.0, 0.0,
					searchFilter.getSearchString(), 10, "", "", "", "", "");
		else
			searchResult = foursquareApi.venuesSearch(searchFilter.getName(), searchFilter.getSearchString(), 10, "",
					"", "", "", "");

		if (searchResult.getMeta().getCode() == 200) {
			// all ok
			for (CompactVenue compactVenue : searchResult.getResult().getVenues()) {
				venueResult.add(
						new Venue(compactVenue.getId(), compactVenue.getName(), compactVenue.getLocation().getAddress(),
								compactVenue.getUrl(), compactVenue.getContact().getPhone()));
			}
		} else {
			throw new Exception("Foursquare search error. Error type: " + searchResult.getMeta().getErrorType()
					+ ". Error detail: " + searchResult.getMeta().getErrorDetail());
		}

		return venueResult;
	}

	public List<String> getPhotos(String venueId) throws Exception {
		List<String> urls = new ArrayList<String>();
		Result<PhotoGroup> searchResult = foursquareApi.venuesPhotos(venueId, "", 10, 10);
		if (searchResult.getMeta().getCode() == 200) {
			// all ok
			for (Photo photo : searchResult.getResult().getItems()) {
				urls.add(photo.getUrl());
			}
		} else {
			throw new Exception("Foursquare search error. Error type: " + searchResult.getMeta().getErrorType()
					+ ". Error detail: " + searchResult.getMeta().getErrorDetail());
		}
		return urls;
	}

	public Venue getVenue(String venueId) throws Exception {
		Result<CompleteVenue> result = foursquareApi.venue(venueId);
		Venue venue = null;
		if (result.getMeta().getCode() == 200) {
			// all ok
			CompleteVenue complVenue = result.getResult();
			venue = new Venue(complVenue.getId(), complVenue.getName(), complVenue.getLocation().getAddress(),
					complVenue.getUrl(), complVenue.getContact().getPhone());
		} else {
			throw new Exception("Foursquare search error. Error type: " + result.getMeta().getErrorType()
					+ ". Error detail: " + result.getMeta().getErrorDetail());
		}
		return venue;
	}
}
