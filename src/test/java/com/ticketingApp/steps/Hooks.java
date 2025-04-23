package com.ticketingApp.steps;

import com.ticketingApp.utility.data.DataGenerator;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {
    public Logger LOG= LogManager.getLogger();

    @Before
    public void addTestData(){
        // This code fragment used to get request details in allure report
        RestAssured.filters(new AllureRestAssured());

        // This code fragment used to create test data before starting test
        DataGenerator.testDataCreate();
    }
    @After
    public void endScenario(Scenario scenario){
        LOG.info("Test Result for {} is {}",scenario.getName(),scenario.getStatus());
        LOG.info("-----------");
    }
}
