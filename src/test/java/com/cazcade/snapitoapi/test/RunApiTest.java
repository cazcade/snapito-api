package com.cazcade.snapitoapi.test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.specification.ResponseSpecification;
import org.junit.Test;

import java.util.Arrays;

import static com.jayway.restassured.RestAssured.given;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class RunApiTest {
    public static final String env = System.getProperty("target.subdomain", "api");
    public static final String apiHost = "http://" + env + ".snapito.com/";

    @Test
    public void test() {
        RestAssured.baseURI = apiHost;
        RestAssured.port = 80;
        ResponseSpecBuilder validImageBuilder = new ResponseSpecBuilder();
        validImageBuilder.expectStatusCode(200);
        validImageBuilder.expectContentType("image/png");
        ResponseSpecification validImage = validImageBuilder.build();

        for (String host : Arrays.asList("google.com", "example.com")) {
            for (int freshness : Arrays.asList(-1, 0, 1, 60)) {
                given().param("url", host).param("freshness", freshness).when().get().then().spec(validImage);
            }
        }
    }
}
