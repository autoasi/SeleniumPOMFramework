package org.selenium.pom.tests.atomic;

import org.selenium.pom.base.BaseTest_TestNG;
import org.selenium.pom.pages.ProductPage;
import org.selenium.pom.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest_TestNG {

    @Test
    public void searchWithPartialMatch(){
        String searchFor = "Blue";
        StorePage storePage = new StorePage(getDriver()).
                load().
                search(searchFor);
        Assert.assertEquals(storePage.getTitle(), "Search results: “" + searchFor + "”");
    }

    @Test
    public void searchWithExactMatch(){
        String searchFor = "Blue Shoes";
        ProductPage productPage = new StorePage(getDriver()).
                load().
                searchExactMatch(searchFor);
        Assert.assertEquals(productPage.getTitle(), searchFor);
    }

    @Test
    public void searchNonExistingProduct() {
        String searchFor = "red box";
        StorePage storePage = new StorePage(getDriver()).
                load().
                enterTextInSearchFld(searchFor).
                clickSearchBtn();
        Assert.assertEquals(storePage.getInfoMessage(), "No products were found matching your selection.");
    }
}

