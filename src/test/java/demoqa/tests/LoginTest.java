package demoqa.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import reqres.tests.TestBase;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static demoqa.tests.TestData.login;
import static demoqa.tests.TestData.password;
import static io.restassured.RestAssured.given;

public class LoginTest extends TestBase {

    @Test
    void testUiLoginSuccessful() {
        open("/login");
        if ($(".fc-dialog-container").isDisplayed()) {
            $(".fc-cta-consent").click();
        }
        $("#userName").setValue(login);
        $("#password").setValue(password).pressEnter();
        $("#userName-value").shouldHave(text(login));
    }

    @Test
    void testLoginSuccessful() {
        String authData = "{\"userName\": \"jtest\", \"password\": \"jTest-1234%\"}";

        Response authResponse = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(ContentType.JSON)
                .body(authData)

                .when()
                .post("/Account/v1/Login")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().response();

        open("/favicon.ico");

        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));

        open("/profile");
        $("#userName-value").shouldHave(text(login));
    }

}
