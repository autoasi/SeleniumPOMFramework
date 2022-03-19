package org.selenium.pom.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.pages.StorePage;

// Composition
public class PageHeader extends BasePage {
    // Encapsulation
    private final By storeMenuLink = By.cssSelector("#menu-item-1227 > a");

    public PageHeader(WebDriver driver) {
        super(driver);
    }

    public StorePage navigateToStoreUsingMenu(){
        driver.findElement(storeMenuLink).click();
        // Fluent interface - returns the object of the new page (only if navigating to new page)
        // This is called also decoupling of pages
        return new StorePage(driver);
    }

}
