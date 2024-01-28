package reqrest.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.lombok.LoginBodyLombokModel;
import models.lombok.LoginResponseLombokModel;
import models.pojo.LoginBodyModel;
import models.pojo.LoginResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeworkTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    @Tag("200")
    void testRegisterSuccessfulPojo() {
        LoginBodyModel requestBody = new LoginBodyModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("pistol");


        LoginResponseModel response = given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .log().uri()

                .when()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", notNullValue())
                .extract().as(LoginResponseModel.class);

        assertEquals("4", response.getId());
    }

    @Test
    @Tag("200")
    void testRegisterSuccessfulLombok() {
        LoginBodyLombokModel requestBody = new LoginBodyLombokModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("pistol");


        LoginResponseLombokModel response = given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .log().uri()

                .when()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", notNullValue())
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("4", response.getId());
    }


}