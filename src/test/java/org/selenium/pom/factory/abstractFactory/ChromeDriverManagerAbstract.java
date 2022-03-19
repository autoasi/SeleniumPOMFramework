package org.selenium.pom.factory.abstractFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverManagerAbstract extends DriverManagerAbstract {

    // Only the abstract methods need to be implemented
    @Override
    protected void startDriver() {
        WebDriverManager.chromedriver().cachePath("drivers").setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
}
