package api;

import io.restassured.http.ContentType;
import models.demoqa.CredentialsModel;
import models.demoqa.LoginResponseModel;

import static io.restassured.RestAssured.given;
import static specifications.ApiSpec.responseSpec200;

public class AuthorizationApi {

    public LoginResponseModel login(CredentialsModel credentials) {
        return given()
                .body(credentials)
                .contentType(ContentType.JSON)

                .when()
                .post("/Account/v1/Login")

                .then()
                .spec(responseSpec200)
                .extract().as(LoginResponseModel.class);
    }

}
