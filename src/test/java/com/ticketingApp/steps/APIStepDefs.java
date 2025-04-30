package com.ticketingApp.steps;

import com.ticketingApp.utility.api.APIUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


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
        givenPart=APIUtil.initRequest(token,baseURI);

    }
    @Given("Accept header is {string}")
    public void accept_header_is(String acceptHeader) {

    }
    @Given("I set basePath for {string}")
    public void i_set_base_path_for(String basePath) {

    }
    @When("I send GET request to {string} endpoint")
    public void i_send_get_request_to_endpoint(String endpoint) {

    }
    @Then("status code should be {int}")
    public void status_code_should_be(Integer expectedStatusCode) {

    }
    @Then("the {string} field should be {string}")
    public void the_field_should_be(String path, String expectedValue) {

    }
    @Then("the {string} field should contains data")
    public void the_field_should_contains_data(String path) {

    }
    @Then("{string} should be later than {string}")
    public void should_be_later_than(String endDate, String startDate) {

    }
    @Then("following fields should not be null")
    public void following_fields_should_not_be_null(List<String> paths) {

    }



}

