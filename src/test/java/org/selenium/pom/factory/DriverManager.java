package org.selenium.pom.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.selenium.pom.constants.BrowserType;


public class DriverManager {

    public WebDriver initialiseDriver(String browser){
        WebDriver driver;

        switch (BrowserType.valueOf(browser)) {
            case CHROME: {
                WebDriverManager.chromedriver().cachePath("drivers").setup();
                driver = new ChromeDriver();
                break;
            }
            case FIREFOX: {
                WebDriverManager.firefoxdriver().cachePath("drivers").setup();
                driver = new FirefoxDriver();
                break;
            }
            default: throw new IllegalStateException("Invalid browser name: " + browser);
        }

        driver.manage().window().maximize();
        // Implicit wait
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15)); // wait up to 15 sec for element to appear
        return driver;
    }
}
