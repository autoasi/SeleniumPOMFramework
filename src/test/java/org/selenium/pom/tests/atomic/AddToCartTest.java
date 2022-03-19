package org.selenium.pom.tests.atomic;

import org.selenium.pom.base.BaseTest_TestNG;
import org.selenium.pom.dataproviders.SharedDataProvider;
import org.selenium.pom.objects.Product;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.ProductPage;
import org.selenium.pom.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddToCartTest extends BaseTest_TestNG {

    @Test(dataProvider = "getFeaturedProduct", dataProviderClass = SharedDataProvider.class)
    public void AddToCartFromStorePage(Product product) throws IOException {
        CartPage cartPage = new StorePage(getDriver()).load().
                getProductThumbnail().clickAddToCartBtn(product.getName()).
                clickViewCart();
        Assert.assertEquals(cartPage.getProductName(), product.getName());
    }

    @Test(dataProvider = "getFeaturedProduct", dataProviderClass = SharedDataProvider.class)
    public void AddFeaturedProductToCart(Product product) throws IOException {
        CartPage cartPage = new HomePage(getDriver()).load().
                getProductThumbnail().clickAddToCartBtn(product.getName()).
                clickViewCart();
        Assert.assertEquals(cartPage.getProductName(), product.getName());
    }

    @Test
    public void AddToCartFromProductPage() throws IOException, InterruptedException {
        Product product = new Product(1215);
        ProductPage productPage = new ProductPage(getDriver()).
                load(product).
                clickAddToCartBtn();
        Assert.assertTrue(productPage.getInfoMessage().contains("“" + product.getName() +  "” has been added to your cart."));

    }

}
