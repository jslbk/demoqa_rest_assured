package reqres.tests;

import models.login.LoginBodyModel;
import models.registration.RegistrationResponseModel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specifications.LoginSpecs.*;

public class LoginTest extends TestBase {

    @Test
    @Tag("400")
    void testUserNotFound() {
        LoginBodyModel requestBody = new LoginBodyModel();
        requestBody.setEmail("bad@reqres.in");
        requestBody.setPassword("cityslicka");

        RegistrationResponseModel response = step("Make request", () -> given(basicRequestSpec)
                .body(requestBody)

                .when()
                .post("/login")

                .then()
                .spec(basicResponseSpec400)
                .extract().as(RegistrationResponseModel.class));
        step("Check response", () ->
                assertEquals("user not found", response.getError()));
    }

    @Test
    @Tag("200")
    void testLoginSuccessful() {
        LoginBodyModel requestBody = new LoginBodyModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("cityslicka");

        step("Make request", () -> given(basicRequestSpec)
                .body(requestBody)

                .when()
                .post("/login")

                .then()
                .spec(basicResponseSpec200)
                .body("token", notNullValue())
                .extract().as(RegistrationResponseModel.class));
    }

}