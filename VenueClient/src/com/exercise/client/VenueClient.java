package com.exercise.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONException;

public class VenueClient {

	private enum Activity {
		SEARCH, PHOTOS, SAVE_FAVORITE, SHOW_FAVORITES, EDIT_FAVORITE, DELETE_FAVORITE, EXIT
	}

	private static Scanner scanner;
	private static WebServiceProvider serviceProvider;

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		serviceProvider = new WebServiceProvider();

		Activity selectedActivity = null;
		do {
			selectedActivity = askForActivity();
			if (selectedActivity == null)
				System.out.println("Invalid selection.");

			switch (selectedActivity) {
			case SEARCH: {
				searchVenues();
				break;
			}
			case PHOTOS: {
				getPhotos();
				break;
			}
			case SAVE_FAVORITE: {
				saveFavorite();
				break;
			}
			case SHOW_FAVORITES: {
				showFavorites();
				break;
			}
			case EDIT_FAVORITE: {
				editFavorite();
				break;
			}
			case DELETE_FAVORITE: {
				deleteFavorite();
				break;
			}
			case EXIT:
				break;
			}

		} while (!Activity.EXIT.equals(selectedActivity));
	}

	private static Activity askForActivity() {
		System.out.println("Choose:");
		System.out.println("1) Search venues");
		System.out.println("2) Get photos for a venue");
		System.out.println("3) Save venue as favorite");
		System.out.println("4) Show list of venue favorites");
		System.out.println("5) Edit venue favorite");
		System.out.println("6) Delete venue favorite");
		System.out.println("7) Exit");

		String selection = scanner.next();
		switch (Integer.parseInt(selection)) {
		case 1:
			return Activity.SEARCH;
		case 2:
			return Activity.PHOTOS;
		case 3:
			return Activity.SAVE_FAVORITE;
		case 4:
			return Activity.SHOW_FAVORITES;
		case 5:
			return Activity.EDIT_FAVORITE;
		case 6:
			return Activity.DELETE_FAVORITE;
		case 7:
			return Activity.EXIT;
		default:
			return null;
		}
	}

	private static void searchVenues() {
		System.out.println("Search by 1) name 2) coordinates: ");
		int searchBy = Integer.parseInt(scanner.next());
		System.out.println("Enter name/coordinates(e.g. 1.111, 2.222): ");
		String nameOrCoordinates = scanner.next();
		System.out.println("Enter search string (optional): ");
		String searchString = scanner.next();

		StringBuilder searchFilter = new StringBuilder();
		searchFilter.append("%7B\"coordinates\":\"");
		searchFilter.append(searchBy == 2 ? nameOrCoordinates : "");
		searchFilter.append("\",");
		searchFilter.append("\"name\":\"");
		searchFilter.append(searchBy == 1 ? nameOrCoordinates : "");
		searchFilter.append("\",");
		searchFilter.append("\"searchString\":\"");
		searchFilter.append(searchString);
		searchFilter.append("\"%7D");

		String result = serviceProvider.searchVenues(searchFilter.toString());
		System.out.println(result);
	}

	private static void getPhotos() {
		System.out.println("Enter venue id: ");
		String venueId = scanner.next();

		String result = serviceProvider.getPhotos(createVenueIdJson(venueId));
		System.out.println(result);
	}

	private static void saveFavorite() {
		System.out.println("Enter venue id: ");
		String venueId = scanner.next();

		String result = serviceProvider.saveVenueFavorite(createVenueIdJson(venueId));
		System.out.println(result);
	}

	private static void showFavorites() {
		List<String> result = new ArrayList<String>();
		try {
			result = serviceProvider.getVenueFavorites();
		} catch (JSONException e) {
			System.out.println(e.getMessage());
			return;
		}
		for (String string : result) {
			System.out.println(string);
		}
	}

	private static void editFavorite() {
		System.out.println("Enter venue id: ");
		String venueId = scanner.next();
		System.out.println("Enter new name: ");
		String name = scanner.next();

		StringBuilder editedFavorite = new StringBuilder();
		editedFavorite.append("%7B\"venueid\":\"");
		editedFavorite.append(venueId);
		editedFavorite.append("\",");
		editedFavorite.append("\"venuename\":\"");
		editedFavorite.append(name);
		editedFavorite.append("\",");
		editedFavorite.append("\"venueaddress\":\"");
		editedFavorite.append("venueaddress");
		editedFavorite.append("\",");
		editedFavorite.append("\"venueurl\":\"");
		editedFavorite.append("www.google.fi");
		editedFavorite.append("\",");
		editedFavorite.append("\"venuephone\":\"");
		editedFavorite.append("111111");
		editedFavorite.append("\",");
		editedFavorite.append("\"keywords\":\"");
		editedFavorite.append("keywords");
		editedFavorite.append("\"%7D");

		String result = serviceProvider.editVenueFavorite(editedFavorite.toString());
		System.out.println(result);
	}

	private static void deleteFavorite() {
		System.out.println("Enter venue id: ");
		String venueId = scanner.next();

		String result = serviceProvider.deleteVenueFavorite(createVenueIdJson(venueId));
		System.out.println(result);
	}

	private static String createVenueIdJson(String venueId) {
		StringBuilder venueIdJson = new StringBuilder();
		venueIdJson.append("%7B\"venueid\":\"");
		venueIdJson.append(venueId);
		venueIdJson.append("\"%7D");
		return venueIdJson.toString();
	}
}
