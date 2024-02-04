package demoqa.tests;

import api.AuthorizationApi;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import configurations.ConfigReader;
import configurations.ProjectConfiguration;
import configurations.web.WebConfig;
import helpers.Attachments;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

public class TestBase {

    private static final WebConfig webConfig = ConfigReader.Instance.read();
    AuthorizationApi authApi = new AuthorizationApi();

    @BeforeAll
    static void beforeAll() {
        ProjectConfiguration projectConfiguration = new ProjectConfiguration(webConfig);
        projectConfiguration.webConfig();

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attachments.screenshotAs("Screenshot");
        Attachments.pageSource();
        Attachments.addVideo();
        Selenide.closeWebDriver();
    }

}