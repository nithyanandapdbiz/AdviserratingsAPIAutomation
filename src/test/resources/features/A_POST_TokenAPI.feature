Feature: Validating Token API

@APITest
Scenario: Generating the Token 
Given Token Payload
When user calls TokenAPI with Post http request
Then the API call got success with status code 201
And Validating response body from the TokenAPI
When user store token value to Global Constant