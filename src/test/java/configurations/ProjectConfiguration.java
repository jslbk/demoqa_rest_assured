package configurations;

import com.codeborne.selenide.Configuration;
import configurations.web.WebConfig;
import io.restassured.RestAssured;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Arrays;
import java.util.Map;

public class ProjectConfiguration {
    private final WebConfig webConfig;

    public ProjectConfiguration(WebConfig webConfig) {
        this.webConfig = webConfig;
    }

    public void webConfig() {
        Configuration.baseUrl = webConfig.baseUrl();
        RestAssured.baseURI = webConfig.baseUri();
        Configuration.browser = webConfig.browser().toString();
        Configuration.browserVersion = webConfig.browserVersion();
        Configuration.browserSize = webConfig.browserSize();
        Configuration.pageLoadStrategy ="eager";

        if (webConfig.isRemote()) {
            Configuration.remote = String.valueOf(webConfig.remoteUrl());
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true,
                    "env", Arrays.asList("LANG=ru_RU.UTF-8", "LANGUAGE=ru:ru", "LC_ALL=ru_RU.UTF=8")
            ));

            Configuration.browserCapabilities = capabilities;
        }
    }
}
