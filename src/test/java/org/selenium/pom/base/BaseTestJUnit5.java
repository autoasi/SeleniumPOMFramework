package org.selenium.pom.base;

import org.openqa.selenium.WebDriver;
import org.selenium.pom.factory.DriverManager;

public class BaseTestJUnit5 {

    protected WebDriver driver;

    //@BeforeEach
    public void startDriver(){
        // The browser value will be taken from JVM argument when running from IDE or Maven command line
        //String browser = System.getProperty("browser");

        // Or browser value can be set in the code
        String browser = "FIREFOX";

        // Initialise the driver instance
        driver = new DriverManager().initialiseDriver(browser);
        System.out.println("JUNIT START - CURRENT THREAD: " + Thread.currentThread().getId() + ", " +
                "DRIVER = " + driver);
    }

    //@AfterEach
    public void quitDriver(){
        System.out.println("JUNIT QUIT - CURRENT THREAD: " + Thread.currentThread().getId() + ", " +
                "DRIVER = " + driver);
        driver.quit();
    }
}
