package com.ticketingApp.utility.api;

import com.ticketingApp.utility.config.ConfigurationReader;
import com.ticketingApp.utility.session.SessionHelper;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.ticketingApp.utility.constants.EndpointConstants.*;
import static com.ticketingApp.utility.constants.SessionConstants.BASEPATH;
import static io.restassured.RestAssured.baseURI;

public class APIUtil {
    public static Logger LOG = LogManager.getLogger();

    /**
     *  Used to create token based on provided
     * @param role
     * @return token as String
     */
    public static String createToken(String role) {
        String accessToken = RestAssured.given()
                .formParams(prepFormData(role))
                .when()
                .post(TOKEN_URL)
                .then().statusCode(200)
                .extract()
                .jsonPath().get("access_token");

        LOG.info("Role {} Token {}", role, accessToken);
        return "Bearer " + accessToken;
    }

    /**
     * Prepare request body as formParameter to create a token based on
     * @param role
     * @return Map<String, String> as content Body
     */
    public static Map<String, String> prepFormData(String role) {
        Map<String, String> formParamMap = new HashMap<>();
        formParamMap.put("client_id", "ticketing-app");
        formParamMap.put("client_secret", System.getenv("SECRET"));
        formParamMap.put("grant_type", "password");
        formParamMap.put("scope", "openid");
        formParamMap.put("username", System.getenv(role + "_EMAIL"));
        formParamMap.put("password", System.getenv(role + "_PASSWORD"));
        return formParamMap;
    }

    /**
     * Set Base Uri based on provided parameter as @serviceName
     */
    public static String setBaseURI(String serviceName) {
        RestAssured.baseURI = ConfigurationReader.getProperty(serviceName);
        LOG.info("BaseURI is {}", baseURI);
        return baseURI;
    }

    /**
     * Set Base Path based on provided parameter as @serviceName
     */
    public static String setBasePath(String serviceName) {
        String basePath;
        switch (serviceName) {
            case "project":
                basePath = PROJECT_BASEPATH;
                break;
            case "task":
                basePath = TASK_BASEPATH;
                break;
            case "user":
                basePath = USER_BASEPATH;
                break;
            default:
                throw new RuntimeException("Base Path not found:" + serviceName);
        }
        SessionHelper.setSessionObject(BASEPATH, basePath);
        LOG.info("Base path is {}", basePath);
        return basePath;
    }

    /**
     * It is used to init a request with provided parameters
     */
    public static RequestSpecification initRequest(String token, String baseURI) {
        return RestAssured
                .given()
                .log().uri()
                .header("Authorization", token)
                .baseUri(baseURI);
    }
}
