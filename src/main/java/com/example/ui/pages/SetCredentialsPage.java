package com.example.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class SetCredentialsPage extends BasePage {

    private final SelenideElement userNameInput = $x("//input[@id='localAdminUsername']");
    private final SelenideElement passwordInput = $x("//input[@id='localAdminPassword']");
    private final SelenideElement confirmPasswordInput = $x("//input[@id='localAdminPasswordConfirm']");
    private final SelenideElement emailInput = $x("//input[@id='localAdminEmail']");
    private final SelenideElement finishButton = $x("//button[.//*[text()='Finish']]");

    public SetCredentialsPage setUserName(String name) {
        userNameInput.should(visible).val(name);
        return this;
    }

    public SetCredentialsPage setPassword(String password) {
        passwordInput.val(password);
        return this;
    }

    public SetCredentialsPage confirmPassword(String password) {
        confirmPasswordInput.should(visible).val(password);
        return this;
    }

    public SetCredentialsPage setEmail(String email) {
        emailInput.val(email);
        return this;
    }

    public DashboardPage clickFinishButton() {
        finishButton.should(enabled).click();
        return page(DashboardPage.class);
    }
}
