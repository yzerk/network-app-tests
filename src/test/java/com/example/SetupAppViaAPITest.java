package com.example;

import com.example.api.client.UnifiApiClient;
import com.example.helper.AppContainerController;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = "api")
public class SetupAppViaAPITest extends BaseTest {

    UnifiApiClient apiClient = new UnifiApiClient();

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
        var countryCode = "840";
        var timeZone = "Europe/Riga";

        apiClient.createAdmin(adminUserName, adminEmail, adminPassword);
        apiClient.configureServer(serverName, countryCode, timeZone);

        String actualAdminName = apiClient.getAdminData().getName();
        assertThat(actualAdminName).as("Admin Name").isEqualTo(adminUserName);

        String actualConfiguredCountry = apiClient.getConfiguredCountry();
        assertThat(actualConfiguredCountry).as("Country").isEqualTo(countryCode);
    }
}
