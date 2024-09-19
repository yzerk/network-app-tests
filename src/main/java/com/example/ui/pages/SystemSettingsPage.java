package com.example.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class SystemSettingsPage extends BasePage {

    private final SelenideElement deviceNameInput = $x("//input[@id='super_identity.name']");
    private final SelenideElement countryInput = $x("//input[@id='country.code']");

    @Step
    public String getDeviceName() {
        return deviceNameInput.val();
    }

    @Step
    public String getCountry() {
        return countryInput.val();
    }
}
