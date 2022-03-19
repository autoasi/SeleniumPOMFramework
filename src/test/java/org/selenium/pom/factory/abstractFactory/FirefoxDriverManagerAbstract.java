package org.selenium.pom.factory.abstractFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxDriverManagerAbstract extends DriverManagerAbstract {

    // Only the abstract methods need to be implemented
    @Override
    protected void startDriver() {
        WebDriverManager.firefoxdriver().cachePath("drivers").setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }
}
