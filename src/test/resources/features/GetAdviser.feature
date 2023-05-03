@adviserAPI @getAdviser
Feature: To get the list of Adviser

  @getAllAdviser
  Scenario: Get list of Adviser
    Given user has access to endpoint "getAdviserEndpoint"
    When user makes a request to view Adviser
    Then user should get the response code 200
    And user should see all the Advisers
    