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

public class GetPracticeDeltaStepDefination {
	Response response;
	JsonReader jsonReader;
	// Initialize a logger for logging
	private static final Logger LOG = LogManager.getLogger(GetPracticeDeltaStepDefination.class);

	@Given("user calls Practice Delta API with GET http request")
	public void user_calls_practice_delta_api_with_get_http_request() throws IOException {
		try {
			// Construct the base URI by formatting the baseURL with the environment
			// property
			String baseURI = String.format(PropertiesFile.getProperty("baseURL"),
					PropertiesFile.getProperty("environment"));

			// Get the API path for the getPracticeDelta API from properties
			String getpracticedelta = PropertiesFile.getProperty("getPracticeDelta");

			// Set the base URI for the REST Assured request
			RestAssured.baseURI = baseURI;

			// Send a GET request to the getPracticeDelta API
			response = RestAssured.given().log().all() // Log request details (including headers and body)
					.contentType(ContentType.JSON) // Set content type as JSON
					.header("Authorization", GlobalConstants.Token) // Set Authorization header
					.get(getpracticedelta); // Specify the API path for the GET request

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

	@Then("the API call got success with status code {int} for Practice Delta API")
	public void the_api_call_got_success_with_status_code_for_practice_delta_api(Integer int1) {
		try {
			// Log the response details using Log4j
			LOG.info("Logging response details:");

			// Assert the status code using the 'int1' variable
			response.then().log().all().assertThat().statusCode(int1);

			// Log a message indicating that the status code assertion passed
			LOG.info("Status code assertion passed: " + int1);

			// Add additional assertions or actions as needed
		} catch (Exception e) {
			// Handle exceptions that may occur during the response validation
			LOG.info("An exception occurred: " + e.getMessage());

			// You can also log or handle the exception in a way that's appropriate for your
			// application
			e.printStackTrace(); // Print the exception trace (for debugging)
		}
	}

	@Then("user validates the response with JSON schema {string} for Practice Delta API")
	public void validating_response_body_from_the_practice_delta_api(String jsonType) throws IOException {
		try {
			// Create a JsonReader instance to read expected data from a JSON file
			JsonReader data = new JsonReader();
			Map<String, Object> sd = data.jsonData("GetPracticeDeltaExpectedData.json");

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

			// Assert that the 'id' from the expected data matches the 'id' in the response
			// JSON
			assertEquals("ID validation failed.", firstElement.get("id"), someValue.get("id").asInt());

			// Log a message indicating that the validation passed
			LOG.info("ID validation passed.");
		} catch (Exception e) {
			// Handle exceptions that may occur during validation
			LOG.info("An exception occurred: " + e.getMessage());

			// You can also log or handle the exception in a way that's appropriate for your
			// application
			e.printStackTrace(); // Print the exception trace (for debugging)
		}
	}
}