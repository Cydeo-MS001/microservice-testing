@smoke @taskService
Feature: Task Service Access and Manipulation
  As a user
  I should be able to access the task service
  So that I can manipulate tasks as expected

  Scenario: Creating a new task and verifying it is creation
    Given I am logged into the "task.service" as a "MANAGER"
    And Accept header is "application/json"
    And Content-Type header is "application/json"
    And I create a random "task" as request body
    When I send POST request to "/create" endpoint
    Then status code should be 201
    And the "message" field should be "Task is successfully created."
