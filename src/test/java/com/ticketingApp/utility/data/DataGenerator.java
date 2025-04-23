package com.ticketingApp.utility.data;

import com.github.javafaker.Faker;
import com.ticketingApp.utility.session.SessionHelper;
import com.ticketingApp.utility.api.APIUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.ticketingApp.utility.constants.EndpointConstants.*;
import static com.ticketingApp.utility.constants.SessionConstants.*;

public class DataGenerator {
    public static Faker faker = new Faker();
    public static Logger LOG = LogManager.getLogger();
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new Random();

    /**
     * Used to create  body based on required
     * @param data as Project or Task
     * @return  Map<String, String> as contentBody
     */
    public static Map<String, String> createBody(String data) {
        Map<String, String> dataMap;
        switch (data) {
            case "project":
                dataMap = createProjectPayload();
                break;
            case "task":
                dataMap = createTaskPayload();
                break;
            default:
                throw new RuntimeException("Data Type is not known: " + data);
        }
        LOG.info("-------- Created Body is {}", dataMap);
        return dataMap;
    }

    /**
     * Creates Project payload
     * @return Map<String, String> as contentBody
     */
    public static Map<String, String> createProjectPayload() {
        Map<String, String> projectMap = new HashMap<>();
        projectMap.put("projectCode", generateCode());
        projectMap.put("projectName", faker.artist().name());
        projectMap.put("projectDetail", "Learning Microservice Testing");
        projectMap.put("projectStatus", "OPEN");
        projectMap.put("endDate", DateHelper.dateToString(LocalDate.now().plusDays(45)));
        projectMap.put("startDate", DateHelper.dateToString(LocalDate.now()));
        return projectMap;
    }

    /**
     * Creates Task payload
     * @return Map<String, String> as contentBody
     */
    public static Map<String, String> createTaskPayload() {

        Map<String, String> taskMap = new HashMap<>();
        taskMap.put("taskCode", generateCode());
        taskMap.put("taskSubject", faker.artist().name());
        taskMap.put("taskDetail", "Learning Microservice Testing");
        taskMap.put("taskStatus", "OPEN");
        taskMap.put("projectCode", SessionHelper.getSessionObject(PROJECT_CODE));
        taskMap.put("assignedEmployee", System.getenv("EMPLOYEE_EMAIL"));
        return taskMap;
    }

    /**
     * Project||Task code has 5 digit and first 2 should be letter
     * @returns Project||Task code as following format PJ001 || TS001
     */
    private static String generateCode() {
        String prefix = createPrefix(2);
        int randomNum = faker.number().numberBetween(100, 1000);
        return prefix + randomNum;
    }

    /**
     * Create prefix for project and tasks
     */
    public static String createPrefix(int length) {
        StringBuilder prefix = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALPHABET.length());
            prefix.append(ALPHABET.charAt(index));
        }
        return prefix.toString();
    }

    /**
     * Creates path param based on provided
     * @param value
     * @return as String
     */
    public static String createPathParam(String value) {
        String paramValue;
        switch (value) {
            case "VALID PROJECT CODE":
                paramValue = SessionHelper.getSessionObject(PROJECT_CODE);
                break;
            case "VALID TASK CODE":
                paramValue = SessionHelper.getSessionObject(TASK_CODE1);
                break;
            case "INVALID PROJECT CODE":
                paramValue = "SP0001";
                break;
            case "PROJECT_CODE":
            case "TASK_CODE":
                paramValue = SessionHelper.getSessionObject(value);
                break;
            default:
                throw new RuntimeException("Path Parameter can not be created: " + value);
        }
        LOG.info("Path Param Value is: {}", paramValue);
        return paramValue;
    }

    /**
     * Test Data Generator for all suites
     * Create 1 Project and 3 task for it
     * Works with Before Hooks
     */
    public static void testDataCreate() {
        String role="MANAGER";
        String projectCode = createProject(role);
        String taskCode1 = createTask(projectCode,role);
        String taskCode2 = createTask(projectCode,role);
        String taskCode3 = createTask(projectCode,role);

        LOG.info("TEST DATA is CREATED with PROJECT CODE {} TASK {} TASK {} TASK {} ", projectCode, taskCode1, taskCode2, taskCode3);

        SessionHelper.setSessionObject(PROJECT_CODE, projectCode);
        SessionHelper.setSessionObject(TASK_CODE1, taskCode1);
        SessionHelper.setSessionObject(TASK_CODE2, taskCode2);
        SessionHelper.setSessionObject(TASK_CODE3, taskCode3);

    }

    /**
     * Used to create Project for testdata
     */
    public static String createProject(String role) {
        JsonPath jp = RestAssured
                .given().log().uri()
                .baseUri(APIUtil.setBaseURI("project.service"))
                .header("Authorization", APIUtil.createToken(role))
                .contentType(ContentType.JSON)
                .body(createProjectPayload())
                .post(CREATE_PROJECT).then()
                .statusCode(201)
                .extract().jsonPath();

        String validProjectCode = jp.getString("data.projectCode");
        LOG.info("Valid Project Code is {}", validProjectCode);
        return validProjectCode;
    }

    /**
     * Used to create Tasks for testdata
     */
    public static String createTask(String projectCode,String role) {
        Map<String, String> taskPayload = createTaskPayload();
        taskPayload.put("projectCode", projectCode);
        System.out.println("taskPayload = " + taskPayload);
        JsonPath jp = RestAssured
                .given().log().uri()
                .baseUri(APIUtil.setBaseURI("task.service"))
                .header("Authorization", APIUtil.createToken(role))
                .contentType(ContentType.JSON)
                .body(taskPayload)
                .post(CREATE_TASK).then()
                .statusCode(201)
                .extract().jsonPath();

        String validTaskCode = jp.getString("data.taskCode");
        LOG.info("Valid Task Code is {}", validTaskCode);
        return validTaskCode;
    }

    public static void deleteAll() {
        JsonPath jp = RestAssured
                .given().log().uri().baseUri(APIUtil.setBaseURI("project.service"))
                .header("Authorization", APIUtil.createToken("MANAGER"))
                .get(READ_ALL_PROJECTS).prettyPeek().then()
                .statusCode(200).extract().jsonPath();
        List<String> allCodeProject = jp.getList("data.projectCode");

        for (int pc = 3; pc < allCodeProject.size(); pc++) {
            RestAssured
                    .given().log().uri().baseUri(APIUtil.setBaseURI("project.service"))
                    .header("Authorization", APIUtil.createToken("MANAGER"))
                    .contentType(ContentType.JSON)
                    .pathParam("projectCode", allCodeProject.get(pc))
                    .delete(DELETE_PROJECT_BY_PROJECT_CODE);
        }
    }

}



