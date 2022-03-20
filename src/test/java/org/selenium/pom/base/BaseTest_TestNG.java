package org.selenium.pom.base;

import io.restassured.http.Cookies;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.selenium.pom.constants.BrowserType;
import org.selenium.pom.factory.DriverManagerFactory;
import org.selenium.pom.factory.abstractFactory.DriverManagerAbstract;
import org.selenium.pom.utils.CookieUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class BaseTest_TestNG {
    // Using private access modifier instead of public so sub classes can use it (This is Encapsulation)
    private final ThreadLocal<DriverManagerAbstract> driverManager = new ThreadLocal<>();
    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>(); // Declare driver as ThreadLocal for parallel execution

    private void setDriverManager(DriverManagerAbstract driverManager){
        this.driverManager.set(driverManager);
    }

    protected DriverManagerAbstract getDriverManager(){
        return this.driverManager.get();
    }

    private void setDriver(WebDriver driver){
        this.driver.set(driver);
    }

    protected WebDriver getDriver(){
        return this.driver.get();
    }

    @Parameters("browser") // reads the browser parameter from testng.xml
    @BeforeMethod
    public synchronized void startDriver(@Optional String browser){
        //browser = System.getProperty("browser",browser); // this works for Maven command and testng.xml or JVM argument
        if(browser == null) browser = "CHROME"; // When running from IDE

        //System.setProperty("allure.results.directory", "target/allure-results/report1");

        // Initialise the driver instance
        // Option 1: Using DriverManager class
        //setDriver(new DriverManager().initialiseDriver(browser));
        // Option 2: Using Interface
        setDriver(DriverManagerFactory.getManager(BrowserType.valueOf(browser)).createDriver());
        // Option 3: Using Abstract class
        //setDriverManager(DriverManagerFactoryAbstract.getManager(BrowserType.valueOf(browser)));
        //setDriver(getDriverManager().getDriver());


        System.out.println("TESTNG START - CURRENT THREAD: " + Thread.currentThread().getId() + ", " +
                "DRIVER = " + getDriver());
    }

    @Parameters("browser") // reads the browser parameter from testng.xml
    @AfterMethod
    public synchronized void quitDriver(@Optional String browser, ITestResult result) throws InterruptedException, IOException {
        if(browser == null) browser = "CHROME"; // When running from IDE
        Thread.sleep(300);
        System.out.println("TESTNG STOP - CURRENT THREAD: " + Thread.currentThread().getId() + ", " +
                "DRIVER = " + getDriver());

        // Take screenshot on failure
        if(result.getStatus() == ITestResult.FAILURE){
            File destFile = new File("screenshots" + File.separator + browser + File.separator +
                    result.getTestClass().getRealClass().getSimpleName() + "_" +
                    result.getMethod().getMethodName() + ".png");
            //takeScreenshot(destFile);
            takeScreenshotUsingAShot(destFile);
        }

        getDriver().quit(); // Using class or interface (Option 1 or 2)
        //getDriverManager().quitDriver(); // Using abstract class (Option 3)
    }

    public void injectCookiesToBrowser(Cookies cookies){
        List<Cookie> seleniumCookies = CookieUtils.convertRestAssuredCookiesToSeleniumCookies(cookies);
        for(Cookie cookie: seleniumCookies){
            getDriver().manage().addCookie(cookie);
        }
    }

    private void takeScreenshot(File destFile) throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
        File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, destFile);
    }

    private void takeScreenshotUsingAShot(File destFile){
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(getDriver());
        try{
            ImageIO.write(screenshot.getImage(), "PNG", destFile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
