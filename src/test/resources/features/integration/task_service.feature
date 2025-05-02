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

  Scenario: Retrieving a task by valid taskCode
    Given I am logged into the "task.service" as a "EMPLOYEE"
    And Accept header is "application/json"
    And Path Param "taskCode" is "VALID TASK CODE"
    When I send GET request to "read/{taskCode}" endpoint
    And the "data.taskCode" field should not be null

  Scenario: Fetch tasks for a specific project
    Given I am logged into the "task.service" as a "MANAGER"
    And Accept header is "application/json"
    And Path Param "projectCode" is "VALID PROJECT CODE"
    When I send GET request to "/read/all/{projectCode}" endpoint
    And status code should be 200
    And the "message" field should be "Tasks are successfully retrieved."
    And the "data" field should contains data
    And following fields should not be null
      | taskCode         |
      | taskSubject      |
      | taskStatus       |
      | projectCode      |
      | assignedEmployee |