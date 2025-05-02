package com.ticketingApp.steps;

import com.ticketingApp.utility.api.APIUtil;
import com.ticketingApp.utility.db.DB_Util;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class CucumberIntro {


    //    Given I am logged into the "fakestoreapp" as a "MANAGER"
    //    And Accept header is "application/json"
    //    And Path Param "id" is "71"
    //    When I send GET request to "api/v1/products/{id}" endpoint
    //    Then status code should be 200
    //    And the "id" field should be "71"

    @Test
    public void test() {

        RequestSpecification givenPart = RestAssured.given().log().uri();
        Response response;
        JsonPath jp;
        ValidatableResponse thenPart;

        //    Given I am logged into the "fakestoreapp" as a "MANAGER"
              // givenPart.header("Authorization",APIUtil.createToken("MANAGER"));

        //    And Accept header is "application/json"
              givenPart.accept("application/json");

        //    And Path Param "id" is "71"
              givenPart.pathParam("id","71");

        //    Add baseUrı
              givenPart.baseUri("https://api.escuelajs.co");

        //    When I send GET request to "api/v1/products/{id}" endpoint
              response = givenPart.when().get("api/v1/products/{id}");
              jp = response.jsonPath();
              thenPart = response.then();

        //    Then status code should be 200
              Assert.assertEquals(200,response.statusCode());
              thenPart.statusCode(200);

        //    And the "id" field should be "71"
        //       (String path,String value)

              thenPart.body("id",Matchers.is(71));

        //    And the "category.name" field should be "Clothes"
    }

    @Test
    public void database() {
        DB_Util.createConnection("task.service");
        DB_Util.createConnection("project.service");
        DB_Util.createConnection("user.service");
    }
}
