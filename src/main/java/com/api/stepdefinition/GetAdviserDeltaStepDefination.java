package com.api.stepdefinition;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.api.utils.GlobalConstants;
import com.api.utils.JsonReader;
import com.api.utils.PropertiesFile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetAdviserDeltaStepDefination {
	// Initialize a logger for logging
	private static final Logger LOG = LogManager.getLogger(GetAdviserDeltaStepDefination.class);
	Response response;
	JsonReader jsonReader;

	@Given("user calls Adviser Delta API with GET http request")
	public void user_calls_adviser_delta_api_with_get_http_request() throws IOException {
		try {
			// Construct the base URI by formatting the baseURL with the environment
			// property
			String baseURI = String.format(PropertiesFile.getProperty("baseURL"),
					PropertiesFile.getProperty("environment"));

			// Get the API path for the getAdviserDelta API from properties
			String getadviserdelta = PropertiesFile.getProperty("getAdviserDelta");

			// Set the base URI for the REST Assured request
			RestAssured.baseURI = baseURI;

			// Send a GET request to the getAdviserDelta API
			response = RestAssured.given().log().all() // Log request details (including headers and body)
					.contentType(ContentType.JSON) // Set content type as JSON
					.header("Authorization", GlobalConstants.Token) // Set Authorization header
					.get(getadviserdelta); // Specify the API path for the GET request

			// Log the response details using Log4j
			LOG.info("Response status code: " + response.getStatusCode());		
			LOG.info("Response body: " + response.getBody().asString());

			// Add handling for the response here, e.g., assertion, validation, etc.
			// You can access the response using the 'response' object.
		} catch (Exception e) {
			// Handle exceptions that may occur during the request execution
			LOG.info("An exception occurred: " + e.getMessage());

			// You can also log or handle the exception in a way that's appropriate for your
			// application
			e.printStackTrace(); // Print the exception trace (for debugging)
		}
	}

	@Then("the API call got success with status code {int} for Adviser Delta API")
	public void the_api_call_got_success_with_status_code_for_adviser_delta_api(Integer int1) {
		String getadviserdelta = PropertiesFile.getProperty("getAdviserDelta");
		try {
			// Send a GET request using REST Assured
			response = RestAssured.given().log().all() // Log request details (including headers and body)
					.contentType(PropertiesFile.getProperty("content.type")) // Set content type
					.header("Authorization", GlobalConstants.Token) // Set Authorization header
					.get(getadviserdelta); // Specify the API path for the GET request

			// Log the response details using Log4j
			LOG.info("Response status code: " + response.getStatusCode());
			LOG.info("Response body: " + response.getBody().asString());

			// Add handling for the response here, e.g., assertion, validation, etc.
			// You can access the response using the 'response' object.
		} catch (Exception e) {
			// Handle exceptions that may occur during the request execution
			LOG.info("An exception occurred: " + e.getMessage());

			// You can also log or handle the exception in a way that's appropriate for your
			// application
			e.printStackTrace(); // Print the exception trace (for debugging)
		}
	}

	@Then("user validates the response with JSON schema {string} for Adviser Delta API")
	public void validating_response_body_from_the_adviser_delta_api(String jsonName) throws IOException {
		try {
			// Create a JsonReader instance to read expected data from a JSON file
			JsonReader data = new JsonReader();
			Map<String, Object> sd = data.jsonData(jsonName);

			// Extract the 'new' array from the expected data
			List<Map<String, Object>> newArray = (List<Map<String, Object>>) sd.get("new");

			// Get the first element from the 'new' array
			Map<String, Object> firstElement = newArray.get(0);

			// Create an ObjectMapper instance to parse the response JSON
			ObjectMapper objectMapper = new ObjectMapper();

			// Parse the response JSON as a JsonNode
			JsonNode jsonNode = objectMapper.readTree(response.getBody().asString());

			// Get the first element from the 'new' array in the response JSON
			JsonNode someValue = jsonNode.path("new").get(0);

			// Assert that the 'adviserNumber' from the expected data matches the
			// 'adviserNumber' in the response JSON
			assertEquals("adviserNumber validation failed.", firstElement.get("adviserNumber"),
					someValue.get("adviserNumber").asInt());

			// Log a message indicating that the validation passed
			LOG.info("adviserNumber validation passed.");
		} catch (Exception e) {
			// Handle exceptions that may occur during validation
			LOG.info("An exception occurred: " + e.getMessage());

			// You can also log or handle the exception in a way that's appropriate for your
			// application
			e.printStackTrace(); // Print the exception trace (for debugging)
		}
	}
}