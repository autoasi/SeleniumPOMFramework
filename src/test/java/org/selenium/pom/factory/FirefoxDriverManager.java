package org.selenium.pom.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxDriverManager implements DriverManagerInterface{

    private WebDriver driver;

    @Override
    public WebDriver createDriver() {
        WebDriverManager.firefoxdriver().cachePath("drivers").setup();
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        return driver;
    }
}
