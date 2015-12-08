package com.exercise.favorites.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.exercise.favorites.domain.VenueFavorite;

public class VenueFavoriteService {

	private static final String TEMP_FILE = "C:/temp/myTempFile.txt";
	private static final String FAVORITE_FILE = "C:/temp/favVenues.txt";
	private File file;

	public VenueFavoriteService() {
	}

	public void saveFavorite(VenueFavorite favorite) throws IOException {
		file = new File(FAVORITE_FILE);
		if (!file.exists())
			file.createNewFile();

		BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		writer.write(favorite.toString());
		writer.newLine();
		writer.close();
	}

	public void editFavorite(VenueFavorite favorite) throws Exception {
		file = new File(FAVORITE_FILE);
		if (!file.exists())
			throw new Exception("No favorites saved!");
		File tempFile = new File(TEMP_FILE);

		BufferedReader reader = new BufferedReader(new FileReader(file));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String currentLine;

		while ((currentLine = reader.readLine()) != null) {
			String trimmedLine = currentLine.trim();
			if (trimmedLine.startsWith(favorite.getVenue().getId()))
				writer.write(favorite.toString() + System.getProperty("line.separator"));
			else
				writer.write(currentLine + System.getProperty("line.separator"));
		}
		writer.close();
		reader.close();
		file.delete();
		if (!tempFile.renameTo(file))
			throw new Exception("File rename failed.");
	}

	public void deleteFavorite(String venueId) throws Exception {
		file = new File(FAVORITE_FILE);
		if (!file.exists())
			throw new Exception("No favorites saved!");
		File tempFile = new File(TEMP_FILE);

		BufferedReader reader = new BufferedReader(new FileReader(file));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String currentLine;

		while ((currentLine = reader.readLine()) != null) {
			// trim newline when comparing with lineToRemove
			String trimmedLine = currentLine.trim();
			if (trimmedLine.startsWith(venueId))
				continue;
			writer.write(currentLine + System.getProperty("line.separator"));
		}
		writer.close();
		reader.close();
		file.delete();
		if (!tempFile.renameTo(file))
			throw new Exception("File rename failed.");
	}

	public List<String> getFavorites() throws Exception {
		file = new File(FAVORITE_FILE);
		if (!file.exists())
			throw new Exception("No favorites saved!");

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String currentLine;
		List<String> favorites = new ArrayList<String>();

		while ((currentLine = reader.readLine()) != null) {
			String trimmedLine = currentLine.trim();
			favorites.add(trimmedLine);
		}
		reader.close();
		return favorites;
	}
}
