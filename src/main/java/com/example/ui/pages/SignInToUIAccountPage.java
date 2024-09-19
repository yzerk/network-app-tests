package com.example.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class SignInToUIAccountPage extends BasePage {

    private final SelenideElement advancedSetupButton = $x("//button[.//*[text()='Advanced Setup']]");
    private final SelenideElement skipButton = $x("//div[contains(@class, 'overlay')]//button[.//*[text()='Skip']]");

    public SetCredentialsPage clickAdvancedSetupButton() {
        advancedSetupButton.should(visible).click();
        skipButton.should(enabled).click();
        return page(SetCredentialsPage.class);
    }
}
