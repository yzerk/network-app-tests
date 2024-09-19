package com.example.ui.components;

import com.codeborne.selenide.SelenideElement;
import com.example.ui.pages.SystemSettingsPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class NavigationComponent {
    private final SelenideElement navigationSettings = $x("//a[@data-testid='navigation-settings']");

    public SettingsComponent openSettings() {
        navigationSettings
                .should(visible).click();
        return page(SettingsComponent.class);
    }

    public static class SettingsComponent {
        SelenideElement systemSettings = $x("//span[@data-testid='system']");

        public SystemSettingsPage openSystem() {
            systemSettings
                    .should(visible).click();
            return page(SystemSettingsPage.class);
        }
    }
}
