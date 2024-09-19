package com.example.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;

public class ServerCreationPage extends BasePage {

    private final SelenideElement nextButton = $x("//button[@type='submit']");
    private final SelenideElement agreementCheckbox = $x("//input[@id='tosAndEula']");
    private final SelenideElement serverNameInput = $x("//input[@id='controllerName']");
    private final SelenideElement countryDropdown = $x("//div[@data-testid='inputReadOnly']");
    private final ElementsCollection countryDropdownValues = $$x("//li[contains(@id, 'dropdownOptions')]");

    public ServerCreationPage setServerName(String name) {
        serverNameInput.should(Condition.enabled).click();
        serverNameInput.sendKeys(Keys.chord(System.getProperty("os.name").toLowerCase().contains("mac") ? Keys.COMMAND : Keys.CONTROL, "a"));
        serverNameInput.sendKeys(Keys.DELETE);
        serverNameInput.should(Condition.empty).sendKeys(name);
        return this;
    }

    public ServerCreationPage setCountry(String country) {
        countryDropdown.click();
        countryDropdownValues.findBy(Condition.text(country)).click();
        return this;
    }

    public ServerCreationPage checkAgreement() {
        agreementCheckbox.click();
        return this;
    }

    public ServerCreationPage clickRestoreServerFromBackupButton() {
        return this;
    }

    public SignInToUIAccountPage clickNextButton() {
        nextButton.click();
        return page(SignInToUIAccountPage.class);
    }
}
