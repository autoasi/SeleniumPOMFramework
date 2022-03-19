package org.selenium.pom.tests.atomic;

import io.qameta.allure.*;
import org.selenium.pom.base.BaseTest_TestNG;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.ProductPage;
import org.selenium.pom.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Headers")
@Feature("Main menu")
public class NavigationTest extends BaseTest_TestNG {

    @Story("As a user when I click the Store link in main menu then I will navigate to the Store page ")
    @Link("https://example.org") // Link to any given url
    @TmsLink("12345")  // Link to Test Management System
    @Issue("1234") // Link to defect number
    @Description("Ensure user can navigate from Home page to Store page using the main menu")
    @Test(description = "Navigate from Home page to Store page using main menu" )
    public void NavigateFromHomeToStoreUsingMainMenu(){
        StorePage storePage = new HomePage(getDriver()).
                load().
                getPageHeader().
                navigateToStoreUsingMenu();
        Assert.assertEquals(storePage.getTitle(), "StoreX");
    }

    @Description("")
    @Test(description = "Navigate from Store page to Product page")
    public void NavigateFromStoreToProductPage(){
        String productName = "Blue Shoes";
        ProductPage productPage = new StorePage(getDriver()).
                load().
                NavigateToProductPage(productName);
        Assert.assertEquals(productPage.getTitle(), productName);
    }

    @Description("")
    @Test(description = "Navigate from Home page to Featured product page")
    public void NavigateFromHomeToFeaturedProductPage(){
        String productName = "Blue Shoes";
        ProductPage productPage = new HomePage(getDriver()).
                load().
                clickFeaturedProduct(productName);
        Assert.assertEquals(productPage.getTitle(), productName);
    }
}
