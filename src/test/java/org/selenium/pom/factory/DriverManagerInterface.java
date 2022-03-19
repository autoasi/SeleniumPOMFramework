package org.selenium.pom.factory;

import org.openqa.selenium.WebDriver;

// Factory design pattern
public interface DriverManagerInterface {
    WebDriver createDriver();
}
