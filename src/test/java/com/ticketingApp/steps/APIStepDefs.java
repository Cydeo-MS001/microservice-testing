package com.ticketingApp.steps;

import com.ticketingApp.utility.api.APIUtil;
import com.ticketingApp.utility.data.DataGenerator;
import com.ticketingApp.utility.data.DateHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public class APIStepDefs {
    public Logger LOG = LogManager.getLogger();

    RequestSpecification givenPart;
    Response response;
    ValidatableResponse thenPart;
    JsonPath jp;

    @Given("I am logged into the {string} as a {string}")
    public void i_am_logged_into_the_as_a(String service, String role) {

        String baseURI = APIUtil.setBaseURI(service);
        String token = APIUtil.createToken(role);
        givenPart = APIUtil.initRequest(token, baseURI);

    }

    @Given("Accept header is {string}")
    public void accept_header_is(String acceptHeader) {
        givenPart.accept(acceptHeader);

    }

    // givenPart.path("taskCode",)

    @Given("I set basePath for {string}")
    public void i_set_base_path_for(String serviceName) {
        givenPart.basePath(APIUtil.setBasePath(serviceName));
    }

    @When("I send GET request to {string} endpoint")
    public void i_send_get_request_to_endpoint(String endpoint) {
        response = givenPart.when().get(endpoint);
        jp = response.jsonPath();
        thenPart = response.then();
        LOG.info("Response body {}",response.prettyPrint());
    }

    @Then("status code should be {int}")
    public void status_code_should_be(int expectedStatusCode) {
        Assert.assertEquals(expectedStatusCode,response.statusCode());
    }

    @Then("the {string} field should be {string}")
    public void the_field_should_be(String path, String expectedValue) {
       Assert.assertEquals(expectedValue,jp.getString(path));
    }
    List<Map<String,String>> allData;
    @Then("the {string} field should contains data")
    public void the_field_should_contains_data(String path) {
        allData=jp.getList(path);
        System.out.println("allData = " + allData);
        Assert.assertTrue(Objects.nonNull(allData));

    }

    @Then("{string} should be later than {string}")
    public void should_be_later_than(String endDate, String startDate) {
        for (Map<String, String> eachMap : allData) {
            boolean endDateAfterStartDate = DateHelper.isEndDateAfterStartDate(eachMap.get(endDate), eachMap.get(startDate));
            Assert.assertTrue(endDateAfterStartDate);
        }

    }

    @Then("following fields should not be null")
    public void following_fields_should_not_be_null(List<String> paths) {
        for (Map<String, String> eachMap : allData) {
            for (String eachPath : paths) {
                Assert.assertTrue(Objects.nonNull(eachMap.get(eachPath)));
            }
        }
    }

    @Given("Content-Type header is {string}")
    public void content_type_header_is(String contentType) {
        givenPart.contentType(contentType);
    }
    @Given("I create a random {string} as request body")
    public void i_create_a_random_as_request_body(String data) {
        givenPart.body(DataGenerator.createBody(data));
    }
    @When("I send POST request to {string} endpoint")
    public void i_send_post_request_to_endpoint(String endpoint) {
        response = givenPart.when().post(endpoint);
        jp = response.jsonPath();
        thenPart = response.then();

        LOG.info("Response body {}",response.prettyPrint());

    }


    @And("Path Param {string} is {string}")
    public void pathParamIs(String pathParam, String value) {
        givenPart.pathParam(pathParam,DataGenerator.createPathParam(value));

    }
}

