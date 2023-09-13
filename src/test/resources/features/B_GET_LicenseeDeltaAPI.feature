Feature: Validating Licensee Delta API

@APITest
Scenario: To Verifying Licensee Delta API
Given user has access to "Licensee Delta API" endpoint
When user execute GET http request for Licensee Delta API
Then user should be displayed with status code 200 for Licensee Delta API
And user validates the response with JSON schema "GetLicenseeDeltaExpectedData.json" for Licensee Delta API