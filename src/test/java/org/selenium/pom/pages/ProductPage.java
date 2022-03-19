package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.objects.Product;

public class ProductPage extends BasePage {

    private final By productTitle = By.cssSelector(".product_title.entry-title");
    private final By addToCartBtn = By.name("add-to-cart");
    private final By infoMessageText = By.className("woocommerce-message");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public ProductPage load(Product product){
        load("/product/" + product.getName().replace(" ","-")); // navigates to product page
        return this;
    }

    public String getTitle(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productTitle)).getText();
    }

    public ProductPage clickAddToCartBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
        return this;
    }

    public String getInfoMessage(){
        /*WebElement switchLabel = wait.until(ExpectedConditions.elementToBeClickable(infoMessageText));

        String pseudo = ((JavascriptExecutor)driver)
                .executeScript("return window.getComputedStyle(arguments[0], ':before').getPropertyValue('text');",switchLabel).toString();
        return pseudo;*/
        return wait.until(ExpectedConditions.visibilityOfElementLocated(infoMessageText)).getText();
    }

}
