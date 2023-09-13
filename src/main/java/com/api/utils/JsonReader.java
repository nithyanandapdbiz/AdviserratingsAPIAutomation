package com.api.utils;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.core.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class JsonReader {

	private ObjectMapper objectMapper;
	File jsonFile;
	Map<String, Object> body;

	private static String stagingDataPath = new File(PropertiesFile.getProperty("test.staging.data.path"))
			.getAbsolutePath() + File.separator;
	private static String prodDataPath = new File(PropertiesFile.getProperty("test.prod.data.path")).getAbsolutePath()
			+ File.separator;
	private static String dataPath;

	public JsonReader() {
		objectMapper = new ObjectMapper();
	}

	public Map<String, Object> jsonData(String jsonFileName) {
		try {
			if (PropertiesFile.getProperty("environment").equals("staging")) {
				body = objectMapper.readValue(new File(stagingDataPath + jsonFileName), Map.class);
				if (body == null) {
					throw new RuntimeException("NO DATA FOUND in JSON file '" + jsonFileName + "'");
				}
				dataPath = stagingDataPath;
			}
		}

		catch (FileNotFoundException e) {
			throw new RuntimeException("JSON file not found at path: " + dataPath + jsonFileName);
		} catch (IOException e) {
			throw new RuntimeException("IOException while reading file: " + jsonFileName);
		}
		return body;
	}

	public <T> T getJsonAsPojo(String jsonFileName, Class<T> pojoClass) throws IOException {
		if (PropertiesFile.getProperty("environment").equals("staging")) {
			jsonFile = new File(stagingDataPath + jsonFileName);
			if (!jsonFile.exists()) {
				throw new IllegalArgumentException("JSON file does not exist: " + jsonFileName);
			}
			dataPath = stagingDataPath;
		}
		return objectMapper.readValue(jsonFile, pojoClass);
	}

}
