Feature: Validating Practice Delta API

@APITest
Scenario: To Verifying Practice Delta API
Given user calls Practice Delta API with GET http request
Then the API call got success with status code 200 for Practice Delta API
And user validates the response with JSON schema "GetPracticeDeltaExpectedData.json" for Practice Delta API