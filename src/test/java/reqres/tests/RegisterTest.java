package reqres.tests;

import models.UserResponseModel;
import models.registration.RegistrationBodyModel;
import models.registration.RegistrationResponseModel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specifications.LoginSpecs.*;

public class RegisterTest extends TestBase {

    @Test
    @Tag("200")
    void testRegisterSuccessfulWithSteps() {
        RegistrationBodyModel requestBody = new RegistrationBodyModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("pistol");

        RegistrationResponseModel response = step("Make request", () -> given(basicRequestSpec)
                .body(requestBody)

                .when()
                .post("/register")

                .then()
                .spec(basicResponseSpec200)
                .body("token", notNullValue())
                .extract().as(RegistrationResponseModel.class));
        step("Check response", () ->
                assertEquals(4, response.getId()));
    }

    @Test
    @Tag("400")
    void testRegisterBadEmail() {
        RegistrationBodyModel requestBody = new RegistrationBodyModel();
        requestBody.setEmail("bad@reqres.in");
        requestBody.setPassword("pistol");

        RegistrationResponseModel response = step("Make request", () -> given(basicRequestSpec)
                .body(requestBody)

                .when()
                .post("/register")

                .then()
                .spec(basicResponseSpec400)
                .extract().as(RegistrationResponseModel.class));
        step("Check response", () ->
                assertEquals("Note: Only defined users succeed registration", response.getError()));
    }

    @Test
    @Tag("400")
    void testRegisterEmptyEmail() {
        RegistrationBodyModel requestBody = new RegistrationBodyModel();
        requestBody.setEmail("");
        requestBody.setPassword("pistol");

        RegistrationResponseModel response = step("Make request", () -> given(basicRequestSpec)
                .body(requestBody)

                .when()
                .post("/register")

                .then()
                .spec(basicResponseSpec400)
                .extract().as(RegistrationResponseModel.class));
        step("Check response", () ->
                assertEquals("Missing email or username", response.getError()));
    }

    @Test
    @Tag("400")
    void testRegisterEmptyPassword() {
        RegistrationBodyModel requestBody = new RegistrationBodyModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("");

        RegistrationResponseModel response = step("Make request", () -> given(basicRequestSpec)
                .body(requestBody)

                .when()
                .post("/register")

                .then()
                .spec(basicResponseSpec400)
                .extract().as(RegistrationResponseModel.class));
        step("Check response", () ->
                assertEquals("Missing password", response.getError()));
    }

    @Test
    @Tag("404")
    void testSingleUserNotFound() {
        step("Make request and check status", () -> given(basicRequestSpec)

                .when()
                .get("/users/23")

                .then()
                .statusCode(404)
                .extract().as(UserResponseModel.class));
    }

    @Test
    @Tag("200")
    void testSingleUserEmail() {
        UserResponseModel response = step("Make request", () -> given(basicRequestSpec)

                .when()
                .get("/users/4")

                .then()
                .spec(basicResponseSpec200)
                .extract().as(UserResponseModel.class));
        step("Check response", () -> {
            assertEquals("eve.holt@reqres.in", response.getData().getEmail());
            assertEquals("Eve", response.getData().getFirstName());
            assertEquals("Holt", response.getData().getLastName());
            assertEquals("https://reqres.in/img/faces/4-image.jpg", response.getData().getAvatar());
        });
    }


}