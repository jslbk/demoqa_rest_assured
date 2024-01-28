package reqrest.tests;

import io.restassured.RestAssured;
import models.UserResponseModel;
import models.login.LoginBodyModel;
import models.registration.RegistrationResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specifications.LoginSpecs.*;

public class HomeworkTest {


    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    @Tag("200")
    void testRegisterSuccessfulWithSteps() {
        LoginBodyModel requestBody = new LoginBodyModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("pistol");

        RegistrationResponseModel response = step("Make request", () -> given(loginRequestSpec)
                .body(requestBody)

                .when()
                .post("/register")

                .then()
                .spec(loginResponseSpec200)
                .body("token", notNullValue())
                .extract().as(RegistrationResponseModel.class));
        step("Check response", () ->
                assertEquals(4, response.getId()));
    }

    @Test
    @Tag("400")
    void testRegisterBadEmail() {
        LoginBodyModel requestBody = new LoginBodyModel();
        requestBody.setEmail("bad@reqres.in");
        requestBody.setPassword("pistol");

        RegistrationResponseModel response = step("Make request", () -> given(loginRequestSpec)
                .body(requestBody)

                .when()
                .post("/register")

                .then()
                .spec(loginResponseSpec400)
                .extract().as(RegistrationResponseModel.class));
        step("Check response", () -> {
            assertEquals("Note: Only defined users succeed registration", response.getError());
        });
    }

    @Test
    @Tag("400")
    void testRegisterEmptyEmail() {
        LoginBodyModel requestBody = new LoginBodyModel();
        requestBody.setEmail("");
        requestBody.setPassword("pistol");

        RegistrationResponseModel response = step("Make request", () -> given(loginRequestSpec)
                .body(requestBody)

                .when()
                .post("/register")

                .then()
                .spec(loginResponseSpec400)
                .extract().as(RegistrationResponseModel.class));
        step("Check response", () -> {
            assertEquals("Missing email or username", response.getError());
        });
    }

    @Test
    @Tag("400")
    void testRegisterEmptyPassword() {
        LoginBodyModel requestBody = new LoginBodyModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("");

        RegistrationResponseModel response = step("Make request", () -> given(loginRequestSpec)
                .body(requestBody)

                .when()
                .post("/register")

                .then()
                .spec(loginResponseSpec400)
                .extract().as(RegistrationResponseModel.class));
        step("Check response", () -> {
            assertEquals("Missing password", response.getError());
        });
    }

    @Test
    @Tag("404")
    void testSingleUserNotFound() {
        step("Make request and check status", () -> given(loginRequestSpec)
                .when()
                .get("/users/23")

                .then()
                .spec(loginResponseSpec404)
                .extract().as(UserResponseModel.class));
    }

    @Test
    @Tag("200")
    void testSingleUserEmail() {
        UserResponseModel response = step("Make request", () -> given(loginRequestSpec)

                .when()
                .get("/users/4")

                .then()
                .spec(loginResponseSpec200)
                .extract().as(UserResponseModel.class));
        step("Check response", () -> {
            assertEquals("eve.holt@reqres.in", response.getData().getEmail());
            assertEquals("Eve", response.getData().getFirstName());
            assertEquals("Holt", response.getData().getLastName());
            assertEquals("https://reqres.in/img/faces/4-image.jpg", response.getData().getAvatar());
        });
    }


}