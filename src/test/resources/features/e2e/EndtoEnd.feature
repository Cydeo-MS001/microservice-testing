@smoke @e2e
Feature: Manager creates a project and assigns tasks, then completes the project

  As a Manager
  I want to create a project, assign tasks, and complete the project
  So that I can manage and finalize all tasks within a project

  @project
  Scenario: Project end to end test
    # Create Project
    Given I am logged into the "ticketingapp" as a "MANAGER"
    And Accept header is "application/json"
    And Content-Type header is "application/json"
    And I set basePath for "project"
    And I create a random "project" as request body
    When I send POST request to "/create" endpoint
    Then status code should be 201
    And the "message" field should be "Project is successfully created."
    And the "data.projectCode" field should not be null
    And I save "data.projectCode" as a "PROJECT_CODE"

    # Create Task in Same Project
    And I create a random "task" as request body
    And I set basePath for "task"
    When I send POST request to "/create" endpoint
    Then status code should be 201
    And the "message" field should be "Task is successfully created."
    And the "data.taskCode" field should not be null

     # Complete Project
    And I set basePath for "project"
    And Path Param "projectCode" is "PROJECT_CODE"
    When I send PUT request to "/complete/{projectCode}" endpoint
    And the "message" field should be "Project is successfully completed."
    And the "data.projectStatus" field should be "COMPLETED"
    Then status code should be 200

    # All tasks should be COMPLETED
    And I set basePath for "task"
    And I send GET request to "/read/all/{projectCode}" endpoint
    Then status code should be 200
    And all "data.taskStatus" field should be "COMPLETED"
