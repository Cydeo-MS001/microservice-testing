package com.ticketingApp.steps;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class APIStepDefs {
    public Logger LOG = LogManager.getLogger();

    RequestSpecification givenPart;
    Response response;
    ValidatableResponse thenPart;
    JsonPath jp;

}

