package com.api.stepdefinition;

import com.api.utils.GlobalConstants;
import com.api.utils.JsonReader;
import com.api.utils.PropertiesFile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.Assert.assertEquals;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GetLicenseeDeltaStepDefination {

	private String baseURI;
	Response response;
	// Initialize a logger for logging
    
	private static final Logger LOG = LogManager.getLogger(GetLicenseeDeltaStepDefination.class);

	@Given("user has access to {string} endpoint")
	public void userHasAccessToEndpoint(String endpoint) {
		 try {
	            // Construct the base URI by formatting the baseURL with the environment property
	            String baseURI = String.format(PropertiesFile.getProperty("baseURL"), PropertiesFile.getProperty("environment"));

	            // Set the base URI for the REST Assured request
	            RestAssured.baseURI = baseURI;

	            // Log a message indicating that the base URI has been set
	            LOG.info("Base URI has been set to: " + baseURI);
	        } catch (Exception e) {
	            // Handle exceptions that may occur during base URI setting
	        	LOG.error("An exception occurred: " + e.getMessage());

	            // You can also log or handle the exception in a way that's appropriate for your application
	            e.printStackTrace(); // Print the exception trace (for debugging)
	        }
	}

	@When("user execute GET http request for Licensee Delta API")
	public void userMakesARequestToViewBookingIDs() {
		String getlicenseedelta = PropertiesFile.getProperty("getLicenseeDelta");

		try {
            // Send a GET request using REST Assured
            response = RestAssured.given()
                .log().all() // Log request details (including headers and body)
                .contentType(PropertiesFile.getProperty("content.type")) // Set content type
                .header("Authorization", GlobalConstants.Token) // Set Authorization header
                .get(getlicenseedelta); // Specify the API path for the GET request

            // Log the response details using Log4j
            LOG.info("Response status code: " + response.getStatusCode());
            LOG.info("Response body: " + response.getBody().asString());

            // Add handling for the response here, e.g., assertion, validation, etc.
            // You can access the response using the 'response' object.
        } catch (Exception e) {
            // Handle exceptions that may occur during the request execution
        	LOG.error("An exception occurred: " + e.getMessage());

            // You can also log or handle the exception in a way that's appropriate for your application
            e.printStackTrace(); // Print the exception trace (for debugging)
        }
	}

	@Then("user should be displayed with status code {int} for Licensee Delta API")
	public void the_api_call_got_success_with_status_code(Integer statusCode) {

		try {
			// Log the response details using Log4j
			LOG.info("Logging response details:");

			// Assert the status code using the 'int1' variable
			response.then().log().all().assertThat().statusCode(statusCode);

			// Log a message indicating that the status code assertion passed
			LOG.info("Status code assertion passed: " + statusCode);

			// Add additional assertions or actions as needed
		} catch (Exception e) {
			// Handle exceptions that may occur during the response validation
			LOG.info("An exception occurred: " + e.getMessage());

			// You can also log or handle the exception in a way that's appropriate for your
			// application
			e.printStackTrace(); // Print the exception trace (for debugging)
		}

	}

	@Then("user validates the response with JSON schema {string} for Licensee Delta API")
	public void validating_response_body_from_the_licensee_delta(String jsonFileName) throws IOException {

		try {
            // Create a JsonReader instance to read expected data from a JSON file
            JsonReader data = new JsonReader();
            Map<String, Object> sd = data.jsonData(jsonFileName);

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

            // Assert that the 'licenceNumber' from the expected data matches the 'licenceNumber' in the response JSON
            assertEquals("licenceNumber validation failed.", firstElement.get("licenceNumber"), someValue.get("licenceNumber").asInt());

            // Log a message indicating that the validation passed
            LOG.info("licenceNumber validation passed.");
        } catch (Exception e) {
            // Handle exceptions that may occur during validation
        	LOG.info("An exception occurred: " + e.getMessage());

            // You can also log or handle the exception in a way that's appropriate for your application
            e.printStackTrace(); // Print the exception trace (for debugging)
        }

	}

}
