package com.api.stepdefinition;

import static org.junit.Assert.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.utils.PropertiesFile;
import com.api.utils.ResponseHandler;
import com.api.utils.TestContext;

import io.cucumber.java.en.*;
import io.restassured.module.jsv.JsonSchemaValidator;


public class GetAdviserStepdefinition {
	
	private TestContext context;
	private static final Logger LOG = LogManager.getLogger(GetAdviserStepdefinition.class);
	
	public GetAdviserStepdefinition(TestContext context) {
		this.context = context;
	}
	
	@Given("user has access to endpoint {string}")
    public void userHasAccessToEndpoint(String endpoint) throws Throwable {
		context.session.put("endpoint", PropertiesFile.getProperty(endpoint));
    }

    @When("user makes a request to view Adviser")
    public void userMakesARequestToViewAdvisers() throws Throwable {
    	context.response = context.requestSetup().when().get(context.session.get("endpoint").toString());
		//int bookingID = context.response.getBody().jsonPath().getInt("[0].bookingid");
		//LOG.info("Booking ID: "+bookingID);
		//assertNotNull("Booking ID not found!", bookingID);
		//context.session.put("bookingID", bookingID);
    }

    @Then("^user should get the response code 200$")
    public void user_should_get_the_response_code_200() throws Throwable {
       
    }

    @And("^user should see all the Advisers$")
    public void user_should_see_all_the_advisers() throws Throwable {
     
    }
}
