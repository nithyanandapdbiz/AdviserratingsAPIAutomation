package com.api.model;

import com.api.utils.PropertiesFile;

public class TokenAPIPost {

	// Define a method to construct a TokenAPIRequest based on the environment
	public TokenAPIRequest TokenPayLoad()
	{
	    // Replace this with logic to determine the actual environment value
	    String environment = PropertiesFile.getProperty("environment"); 

	    // Declare variables to store API key and API value
	    String posttokenapivalue;
	    String posttokenapikey;

	    // Use a switch-case statement to set API key and API value based on the environment
	    switch (environment) {
	        case "staging":
	            // Get API value and key for the staging environment from a properties file
	            posttokenapivalue = PropertiesFile.getProperty("postTokenAPIValue.staging");
	            posttokenapikey = PropertiesFile.getProperty("postTokenAPIKey.staging");
	            break;

	        case "production":
	            // Get API value and key for the production environment from a properties file
	            posttokenapivalue = PropertiesFile.getProperty("postTokenAPIValue.production");
	            posttokenapikey = PropertiesFile.getProperty("postTokenAPIKey.production");
	            break;

	        default:
	            // Handle the case where the environment is not recognized by throwing an exception
	            throw new IllegalArgumentException("Unknown environment: " + environment);
	    }

	    // Create a TokenAPIRequest object and set its API key and API value
	    TokenAPIRequest requestBody = new TokenAPIRequest();
	    requestBody.setApiKey(posttokenapikey);
	    requestBody.setApiSecret(posttokenapivalue);

	    // Return the constructed TokenAPIRequest
	    return requestBody;
	}

}