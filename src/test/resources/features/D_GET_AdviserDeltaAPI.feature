Feature: Validating Adviser Delta API

@APITest
Scenario: To Verifying Adviser Delta API                                   
  Given user calls Adviser Delta API with GET http request
  Then the API call got success with status code 200 for Adviser Delta API
  And user validates the response with JSON schema "GetAdviserDeltaExpectedData.json" for Adviser Delta API