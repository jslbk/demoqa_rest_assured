package demoqa.tests;

import io.qameta.allure.*;
import models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static demoqa.tests.TestData.*;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Successful login tests")
@Feature("User login")
@Epic("Login")
@Story("User is able to login into the book store")
@Owner("Julyan Slabko")
@Tag("demoqa_api")
@Severity(SeverityLevel.NORMAL)
public class LoginTest extends TestBase {

    @Test
    @DisplayName("User is successfully logged in through the UI test")
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
    @DisplayName("User is successfully logged in through the API test")
    void testLoginSuccessful() {
        LoginResponseModel authData = authApi.login(credentials);

        step("Check user is logged in and add new book via UI", () -> {
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", authData.getUserId()));
            getWebDriver().manage().addCookie(new Cookie("expires", authData.getExpires()));
            getWebDriver().manage().addCookie(new Cookie("token", authData.getToken()));

            open("/profile");
            assertEquals(credentials.getUserName(), $("#userName-value").getText());
        });
    }
}