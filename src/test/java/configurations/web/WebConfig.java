package configurations.web;

import org.aeonbits.owner.Config;

import java.net.URL;

@Config.Sources({
        "classpath:${host}.properties"
})
public interface WebConfig extends Config {
    @Key("baseUrl")
    @DefaultValue("https://demoqa.com")
    String baseUrl();

    @Key("baseUri")
    @DefaultValue("https://demoqa.com")
    String baseUri();

    @Key("browser")
    @DefaultValue("chrome")
    String browser();

    @Key("browserVersion")
    @DefaultValue("100.0")
    String browserVersion();

    @Key("browserSize")
    @DefaultValue("1920x1080")
    String browserSize();

    @Key("isRemote")
    @DefaultValue("false")
    boolean isRemote();

    @Key("remoteUrl")
    @DefaultValue("https://user1:1234@selenoid.autotests.cloud/wd/hub")
    URL remoteUrl();
}