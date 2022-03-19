package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.pages.components.PageHeader;
import org.selenium.pom.pages.components.ProductThumbnail;

public class HomePage extends BasePage {
    // Composition - create instance of the components with getter and setter methods (Encapsulation)
    private PageHeader pageHeader;
    private ProductThumbnail productThumbnail;

    // Because the class inherits the BasePage class we need to implement the BasePage constructor
    public HomePage(WebDriver driver){
        super(driver);
        pageHeader = new PageHeader(driver);
        productThumbnail = new ProductThumbnail(driver);
    }

    public HomePage load(){
        load("/"); // calling the load method from BaePage
        wait.until(ExpectedConditions.titleContains("AskOmDch")); // Explicit wait for page to load
        return this;
    }

    private By getFeaturedProductElement(String productName){
        return By.xpath("//h2[normalize-space()='" + productName + "']");
    }

    public ProductPage clickFeaturedProduct(String productName){
        wait.until(ExpectedConditions.elementToBeClickable(getFeaturedProductElement(productName))).click();
        return new ProductPage(driver);
    }

    public PageHeader getPageHeader() {
        return pageHeader;
    }

    public void setPageHeader(PageHeader pageHeader) {
        this.pageHeader = pageHeader;
    }

    public ProductThumbnail getProductThumbnail() {
        return productThumbnail;
    }

    public void setProductThumbnail(ProductThumbnail productThumbnail) {
        this.productThumbnail = productThumbnail;
    }
}
