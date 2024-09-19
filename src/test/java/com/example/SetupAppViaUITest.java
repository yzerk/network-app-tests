package com.example;

import com.example.helper.AppContainerController;
import com.example.ui.pages.ServerCreationPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = "ui")
public class SetupAppViaUITest extends BaseTest {

    @BeforeClass
    public void before() {
        // There is docker app setup in BeforeSuite as common for all tests
        // This container restart action is just for specific test where it's expected to have clear server at start
        // Other tests will not have this @BeforeClass to reduce time on docker container installation
        new AppContainerController().restartApp();
    }

    @Test
    public void checkAppSetup() {
        // Test Data:
        var adminUserName = "testAdmin";
        var adminPassword = "testPassword";
        var adminEmail = "network-admin@gmail.com";
        var serverName = "Test UniFi Network";
        var countryName = "United States";
        var timeZone = "Europe/Riga";

        var setCredPage = open("/", ServerCreationPage.class)
                .setServerName(serverName)
                .setCountry(countryName)
                .checkAgreement()
                .clickNextButton()
                .clickAdvancedSetupButton();

        var dashboard = setCredPage
                .setUserName(adminUserName)
                .setPassword(adminPassword)
                .confirmPassword(adminPassword)
                .setEmail(adminEmail)
                .clickFinishButton()
                .waitForLoad();

        assertThat(dashboard.getActivities()).as("Activities")
                .anyMatch(activity -> activity.contains(adminUserName + " opened UniFi Network via the web."));

        var systemSettingsPage = dashboard
                .getNavigation()
                .openSettings()
                .openSystem();

        assertThat(systemSettingsPage.getDeviceName()).isEqualTo(serverName);
        assertThat(systemSettingsPage.getCountry()).isEqualTo(countryName);
    }


}
