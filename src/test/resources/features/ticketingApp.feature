@sample
Feature: Manager
  As a Manager
  I want to read all projects

  Scenario: Successfully read all projects managed by the manager
    Given I am logged into the "ticketingapp" as a "MANAGER"
    And Accept header is "application/json"
    And I set basePath for "project"
    When I send GET request to "read/all/manager" endpoint
    Then status code should be 200
    And the "message" field should be "Projects are successfully retrieved."
    And the "data" field should contains data
    And "endDate" should be later than "startDate"
    And following fields should not be null
      | projectName     |
      | projectCode     |
      | assignedManager |
      | projectDetail   |
      | projectStatus   |