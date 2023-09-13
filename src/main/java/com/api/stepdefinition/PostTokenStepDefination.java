package com.api.stepdefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.junit.Assert.*;
import java.io.IOException;

import com.api.model.TokenAPIPost;
import com.api.model.TokenAPIResponse;
import com.api.utils.GlobalConstants;
import com.api.utils.JsonReader;
import com.api.utils.PropertiesFile;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PostTokenStepDefination {
	Response response;
	TokenAPIPost requestBody;
	TokenAPIResponse expectedData;
	JsonReader jsonReader;
	TokenAPIResponse post;
	String tokendata;
	// Initialize a logger for logging
	
	private static final Logger LOG = LogManager.getLogger(PostTokenStepDefination.class);

	@Given("Token Payload")
	public void token_payload() {

		requestBody = new TokenAPIPost();
	}

	@When("user calls TokenAPI with Post http request")
	public void user_calls_with_post_http_request() {

		try {
			// Construct the base URI by formatting the baseURL with the environment
			// property
			String baseURI = String.format(PropertiesFile.getProperty("baseURL"),
					PropertiesFile.getProperty("environment"));

			// Get the API path for the postToken API from properties
			String posttokenapipath = PropertiesFile.getProperty("postTokenAPIPath");

			// Set the base URI for the REST Assured request
			RestAssured.baseURI = baseURI;

			// Log the request details using Log4j
			LOG.info("Sending a POST request to: " + posttokenapipath);
			LOG.info("Request body: " + requestBody.TokenPayLoad());

			// Send a POST request to the postToken API
			response = RestAssured.given().log().all() // Log request details (including headers and body)
					.contentType(ContentType.JSON) // Set content type as JSON
					.body(requestBody.TokenPayLoad()) // Set the request body
					.post(posttokenapipath); // Specify the API path for the POST request

			// Log the response details using Log4j
			LOG.info("Response status code: " + response.getStatusCode());
			LOG.info("Response body: " + response.getBody().asString());
		} catch (Exception e) {
			// Handle exceptions that may occur during the execution of the REST request
			LOG.error("An exception occurred: " + e.getMessage());
			e.printStackTrace(); // Print the exception trace (for debugging)
			// You can also log or handle the exception in a way that's appropriate for your
			// application
		}
	}

	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {

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
			LOG.error("An exception occurred: " + e.getMessage());

			// You can also log or handle the exception in a way that's appropriate for your
			// application
			e.printStackTrace(); // Print the exception trace (for debugging)
		}

	}

	@Then("Validating response body from the TokenAPI")
	public void validating_response_body_from_the() throws IOException {
		try {
			// Create a JsonReader instance
			JsonReader jsonReader = new JsonReader();

			// Read and parse the expected data from a JSON file into a POJO
			expectedData = jsonReader.getJsonAsPojo("PostTokenExpectedData.json", TokenAPIResponse.class);

			// Validating the Token Type in the response
			TokenAPIResponse post = response.as(TokenAPIResponse.class);
			 tokendata=post.getToken().toString();
			assertEquals("Token Type validation failed.", post.getType(), expectedData.getType());

			// Validating the ExpiresIN in the response
			assertEquals("ExpiresIn validation failed.", post.getExpiresIn(), expectedData.getExpiresIn());

			// Log a message indicating that the validations passed
			LOG.info("Token Type and ExpiresIn validations passed.");
		} catch (Exception e) {
			// Handle exceptions that may occur during validation
			LOG.error("An exception occurred: " + e.getMessage());

			// You can also log or handle the exception in a way that's appropriate for your
			// application
			e.printStackTrace(); // Print the exception trace (for debugging)
		}

	}

	@When("user store token value to Global Constant")
	public void user_store_token_value_to_Global_Constatnt() throws IOException {
		try {
			// Storing the Token to Global Constant
			String Token = "Bearer " + tokendata;

			// Print the Token to the console
			System.out.println("Token: " + Token);

			// Store the Token in a Global Constant
			GlobalConstants.Token = Token;

			// Log a message indicating that the Token has been stored
			LOG.info("Token has been stored in GlobalConstants.Token: " + Token);
		} catch (Exception e) {
			// Handle exceptions that may occur during Token storage
			LOG.error("An exception occurred while storing the Token: " + e.getMessage());

			// You can also log or handle the exception in a way that's appropriate for your
			// application
			e.printStackTrace(); // Print the exception trace (for debugging)
		}

	}

}