package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.pages.components.ProductThumbnail;

public class StorePage extends BasePage{

    private final By searchFld = By.id("woocommerce-product-search-field-0");
    private final By searchBtn = By.cssSelector("button[value='Search']");
    private final By title = By.cssSelector(".woocommerce-products-header__title.page-title");
    private final By infoMessage = By.className("woocommerce-info");
    private final ProductThumbnail productThumbnail; // Need to create a getter

    // Because the class inherits the BasePage class we need to implement the BasePage constructor
    public StorePage(WebDriver driver) {
        super(driver);
        productThumbnail = new ProductThumbnail(driver);
    }

    public ProductThumbnail getProductThumbnail() {
        return productThumbnail;
    }

    public Boolean isLoaded(){
        return wait.until(ExpectedConditions.urlContains("/store"));
    }

    public StorePage enterTextInSearchFld(String txt){
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchFld)).sendKeys(txt);
        return this; // Returns the current class - for Builder Pattern
    }

    public StorePage clickSearchBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
        return this; // Returns the current class - for Builder Pattern
    }

    public StorePage load(){
        load("/store"); // navigates to store page
        return this;
    }

    public StorePage search(String txt){
        enterTextInSearchFld(txt).clickSearchBtn();
        wait.until(ExpectedConditions.urlMatches(".*/?s=" + txt +"&post_type=product"));
        return this; // Returns the current class - for Builder Pattern
    }

    public ProductPage searchExactMatch(String txt){
        enterTextInSearchFld(txt).clickSearchBtn();
        wait.until(ExpectedConditions.urlMatches(".*/product/"));
        return new ProductPage(driver);
    }

    public String getTitle(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).getText();
    }

    private By getProductElement(String productName){
        return By.xpath("//h2[normalize-space()='" + productName + "']");
    }

    public ProductPage NavigateToProductPage(String productName){
        wait.until(ExpectedConditions.elementToBeClickable(getProductElement(productName))).click();
        return new ProductPage(driver);
    }

    public String getInfoMessage(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(infoMessage)).getText();
    }
}
