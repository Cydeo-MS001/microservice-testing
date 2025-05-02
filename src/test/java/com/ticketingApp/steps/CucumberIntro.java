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

import java.util.Objects;

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
        givenPart.pathParam("id", "71");

        //    Add baseUrı
        givenPart.baseUri("https://api.escuelajs.co");

        //    When I send GET request to "api/v1/products/{id}" endpoint
        response = givenPart.when().get("api/v1/products/{id}");
        jp = response.jsonPath();
        thenPart = response.then();

        //    Then status code should be 200
        Assert.assertEquals(200, response.statusCode());
        thenPart.statusCode(200);

        //    And the "id" field should be "71"
        //       (String path,String value)

        thenPart.body("id", Matchers.is(71));

        //    And the "category.name" field should be "Clothes"
    }

    @Test
    public void database() {
        DB_Util.createConnection("task.service");

        DB_Util.runQuery("SELECT * FROM TASKS");

        int rowCount = DB_Util.getRowCount();
        System.out.println("rowCount = " + rowCount);
    }

    @Test
    public void dynamicData() throws InterruptedException {

        Response response = null;
        ;

        String email = "";

        for (int i = 0; i < 6; i++) {
            response = RestAssured.get("https://35dd6236-da70-4507-8d18-3f2030ed0722.mock.pstmn.io/api/v1/projects");
            JsonPath jp = response.jsonPath();
            email = jp.getString("data.findAll {it.step=='FinalResult'}.email");
            if(email.contains("@")){
                break;
            }
            System.out.println(i);
            Thread.sleep(10000);
        }

        System.out.println(email);

    }
}
