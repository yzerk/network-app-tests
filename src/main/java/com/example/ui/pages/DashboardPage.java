package com.example.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.example.ui.components.NavigationComponent;
import lombok.Getter;

import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage extends BasePage {

    @Getter
    private NavigationComponent navigation = page(NavigationComponent.class);
    private final ElementsCollection activities = $$x("//div[./span[text()='Admin Activity']]//following-sibling::div");

    public DashboardPage waitForLoad() {
        $x("//img[@alt='network-server']").shouldBe(visible);
        return this;
    }

    public List<String> getActivities() {
        return activities.texts();
    }
}
