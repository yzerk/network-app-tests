package com.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.example.helper.AppContainerController;
import com.example.helper.PropertyLoader;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import io.qameta.allure.testng.AllureTestNg;

import java.util.Arrays;

import static com.codeborne.selenide.Selenide.*;

@Listeners({AllureTestNg.class})
public class BaseTest {

    public static final String APP_URL = PropertyLoader.getProperty("baseUrl");

    @BeforeClass
    public void setUp() {
        var groups = Arrays.asList(this.getClass().getAnnotation(Test.class).groups());
        if (groups.contains("api")) {
            configureAPI();
        } else if (groups.contains("ui")) {
            configureUI();
        } else {
            throw new IllegalArgumentException("Test group is not defined");
        }
    }

    private void configureUI() {
        Configuration.browserSize = "maximize";
        Configuration.timeout = 10000;
        Configuration.baseUrl = APP_URL;
        Configuration.browser = "chrome";
        Configuration.headless = false;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        Configuration.browserCapabilities = capabilities;

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));
    }

    private void configureAPI() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured());
    }

    @AfterClass
    public void tearDown() {
        closeWebDriver();
    }

    @BeforeSuite
    public void runApp() {
        AppContainerController controller = new AppContainerController();
        controller.appContainerUp();
    }
}
