package com.example.api.client;

import com.example.helper.PropertyLoader;
import com.example.api.pojo.AdminData;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UnifiApiClient {

    public static final String ADMIN_CREATE_URL = "/api/cmd/sitemgr";
    public static final String SERVER_NAME_URL = "/api/set/setting/super_identity";
    public static final String SERVER_COUNTRY_CODE_URL = "/api/set/setting/country";
    public static final String SERVER_LOCALE_URL = "/api/set/setting/locale";
    public static final String SET_SERVER_INSTALLED_FLAG_URL = "/api/cmd/system";
    public static final String SELF_URL = "/api/self";
    public static final String COUNTRY_URL = "/api/s/default/get/setting/country";
    public static final String LOGIN_URL = "/api/login";

    private Cookies cookies;
    private String userName;
    private String password;

    static {
        // ignore certificates
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = RestAssured.config()
                .sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation());

        // enable logging
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(PropertyLoader.getProperty("baseUrl"))
                .setContentType(ContentType.JSON)
                .build();
    }

    public UnifiApiClient() {
        userName = String.valueOf(PropertyLoader.getProperty("defaultUnifiAdminName"));
        password = String.valueOf(PropertyLoader.getProperty("defaultUnifiAdminEmail"));
    }

    private RequestSpecification getClient() {
        return given();
    }

    private RequestSpecification getAuthClient() {
        return getClient().cookies(getCookies());
    }

    private Cookies getCookies() {
        if (cookies == null) {
            cookies = login(userName, password)
                    .then()
                    .statusCode(200)
                    .extract()
                    .detailedCookies();
        }
        return cookies;
    }

    private Response login(String name, String password) {
        var authPayload = Map.of(
                "username", name,
                "password", password,
                "remember", false,
                "strict", true
        );
        return getClient()
                .body(authPayload)
                .post(LOGIN_URL);
    }

    public Response createAdmin(String name, String email, String password) {
        this.userName = name;
        this.password = password;
        var createAdminPayload = Map.of(
                "cmd", "add-default-admin",
                "name", name,
                "email", email,
                "x_password", password
        );
        return getClient().body(createAdminPayload).post(ADMIN_CREATE_URL);
    }

    public void configureServer(String name, String countryCode, String timeZone) {
        setServerName(name).then().statusCode(200);
        setServerCountry(countryCode).then().statusCode(200);
        setServerTimeZone(timeZone).then().statusCode(200);
        markAppAsInstalled().then().statusCode(200);
    }

    private Response setServerName(String name) {
        return getClient()
                .body(Map.of("name", name))
                .post(SERVER_NAME_URL);
    }

    private Response setServerCountry(String countryCode) {
        return getClient()
                .body(Map.of("code", countryCode))
                .post(SERVER_COUNTRY_CODE_URL);
    }

    private Response setServerTimeZone(String timeZone) {
        return getClient()
                .body(Map.of("timezone", timeZone))
                .post(SERVER_LOCALE_URL);
    }

    private Response markAppAsInstalled() {
        return getClient()
                .body(Map.of("cmd", "set-installed"))
                .post(SET_SERVER_INSTALLED_FLAG_URL);
    }

    public AdminData getAdminData() {
        return getAuthClient()
                .get(SELF_URL)
                .then()
                .statusCode(200)
                .extract().jsonPath().getObject("data[0]", AdminData.class);
    }

    public String getConfiguredCountry() {
        return getAuthClient()
                .get(COUNTRY_URL)
                .then()
                .statusCode(200)
                .extract()
                .path("data[0].code");
    }
}
