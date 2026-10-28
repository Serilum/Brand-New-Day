package com.serilum.brandnewday.util;

import com.serilum.brandnewday.data.Constants;
import com.natamus.collective.functions.DataFunctions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatStorage {
	private static final Path DIRECTORY = Path.of(DataFunctions.getGameDirectory(), "data", Reference.MOD_ID);

	public static Map<String, Long> load(String worldKey) {
		Path file = fileFor(worldKey);
		if (file == null || !Files.exists(file)) {
			return null;
		}

		Map<String, Long> data = new HashMap<>();
		try {
			for (String line : Files.readAllLines(file)) {
				int split = line.indexOf('=');
				if (split <= 0) {
					continue;
				}

				try {
					data.put(line.substring(0, split), Long.parseLong(line.substring(split + 1).trim()));
				}
				catch (NumberFormatException ignored) {
				}
			}
		}
		catch (IOException e) {
			Constants.logger.warn(Constants.logPrefix + "Something went wrong while reading the saved stats.");
			e.printStackTrace();
			return null;
		}

		return data;
	}

	public static void save(String worldKey, Map<String, Long> data) {
		Path file = fileFor(worldKey);
		if (file == null) {
			return;
		}

		List<String> lines = new ArrayList<>();
		for (Map.Entry<String, Long> entry : data.entrySet()) {
			lines.add(entry.getKey() + "=" + entry.getValue());
		}

		try {
			Files.createDirectories(DIRECTORY);
			Files.write(file, lines);
		}
		catch (IOException e) {
			Constants.logger.warn(Constants.logPrefix + "Something went wrong while saving the stats.");
			e.printStackTrace();
		}
	}

	private static Path fileFor(String worldKey) {
		if (worldKey == null || worldKey.isEmpty()) {
			return null;
		}

		return DIRECTORY.resolve(worldKey.replaceAll("[^a-zA-Z0-9._-]", "_") + ".txt");
	}
}
