package com.exercise.venues.service;

import java.util.List;

import com.exercise.venues.FourSquareHelper;
import com.exercise.venues.domain.Venue;
import com.exercise.venues.domain.VenueSearchFilter;

public class VenueService {

	private FourSquareHelper fourSquarehelper;

	public VenueService() {
		fourSquarehelper = new FourSquareHelper();
	}
	
	public List<Venue> searchVenues(VenueSearchFilter searchFilter) throws Exception{
		return fourSquarehelper.searchVenues(searchFilter);
	}

	public List<String> getVenuePhotos(String venueId) throws Exception {
		return fourSquarehelper.getPhotos(venueId);
	}
	
	public Venue getVenue(String venueId) throws Exception{
		return fourSquarehelper.getVenue(venueId);
	}
}
