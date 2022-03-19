package org.selenium.pom.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;

public class CartPage extends BasePage {
    // Page object model
    //private final By productName = By.cssSelector("td[class='product-name'] a");
    //private final By checkoutBtn = By.cssSelector(".checkout-button");
    //private final By cartTitle = By.cssSelector(".has-text-align-center");
    // Page Factory
    @FindBy(css = "td[class='product-name'] a") private WebElement productName;
    @FindBy(how = How.CSS, using = ".checkout-button") private WebElement checkoutBtn;
    @FindBy(css = ".has-text-align-center") @CacheLookup private WebElement cartTitle;

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this); /// can be moved to the BasePage constructor
    }

    public Boolean isLoaded(){
        //return wait.until(ExpectedConditions.textToBe(cartTitle,"Cart"));
        return wait.until(ExpectedConditions.textToBePresentInElement(cartTitle,"Cart"));
    }

    public String getProductName(){
        //return driver.findElement(productName).getText();
        //return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
        return wait.until(ExpectedConditions.visibilityOf(productName)).getText();
    }

    public CheckoutPage checkout(){
        // Explicit wait - return the element so we can perform the action click
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
        //driver.findElement(checkoutBtn).click();
        return new CheckoutPage(driver);
    }
}
