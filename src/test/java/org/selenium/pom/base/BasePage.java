package org.selenium.pom.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selenium.pom.utils.ConfigLoader;

import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WebDriverWait waitLong;
    protected WebDriverWait waitShort;

    // class constructor
    @Deprecated
    public BasePage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
        waitLong = new WebDriverWait(driver, 45);
        waitShort = new WebDriverWait(driver, 10);
    }

    public void load(String endPoint){
        driver.get(ConfigLoader.getInstance().getBaseUrl() + endPoint);
    }

    public void waitForOverlaysToDisappear(By overlay){
        List<WebElement> overlays =  driver.findElements(overlay);
        System.out.println("overlays size = " + overlays.size());
        if (overlays.size() > 0){
            // Explicit wait - wait for all overlays to become invisible
            wait.until(
                    ExpectedConditions.invisibilityOfAllElements(overlays)
            );
            System.out.println("Overlays are invisible.");
        }else{
            System.out.println("Overlays not found.");
        }
    }

    public WebElement waitForElementTobeVisible(By element){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }
}


