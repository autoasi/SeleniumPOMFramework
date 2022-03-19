package org.selenium.pom.tests.e2e;

import org.selenium.pom.base.BaseTest_TestNG;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.StorePage;
import org.selenium.pom.utils.ConfigLoader;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestNG_MyFirstTestCase extends BaseTest_TestNG {

    @Test
    public void guestCheckoutUsingDirectBankTransfer() throws InterruptedException, IOException {
        String searchFor = "Blue";

        /*BillingAddress billingAddress = new BillingAddress().
                setFirstName("demo").
                setLastName("user").
                setAddressLineOne("San Francisco").
                setCity("Sam Francisco").
                setPostcode("94188").
                setEmail("askme@gmail.com");*/

        //BillingAddress billingAddress = new BillingAddress("demo","user",
        //       "San Francisco","San Francisco","94188","askme@gmail.com");

        BillingAddress billingAddress = JacksonUtils.deserializedJson("myBillingAddress.json", BillingAddress.class);
        Product product = new Product(1215);

        // The driver instance created already in the super class BaseTest and accessible as its protected
        // Home Page
        HomePage homePage = new HomePage(getDriver()).load();
        StorePage storePage = homePage.getPageHeader().navigateToStoreUsingMenu();
        //storePage
        storePage.isLoaded();
        storePage.search(searchFor); // Functional page Object - method provides a functional solution and not single action
        Thread.sleep(1500);
        Assert.assertEquals(storePage.getTitle(), "Search results: “" + searchFor + "”");
        //Assert.assertTrue(storePage.getTitle().contains("Search results: "));
        storePage.getProductThumbnail().clickAddToCartBtn(product.getName());

        // Cart Page
        CartPage cartPage = storePage.getProductThumbnail().clickViewCart();
        cartPage.isLoaded();
        Assert.assertEquals(cartPage.getProductName(), product.getName());

        // Checkout Page
        // Builder Pattern - chain the objects that under the same page (Structural page Object)
        CheckoutPage checkoutPage = cartPage.
                checkout().
                setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                clickPlaceOrderBtn();
        Assert.assertEquals(checkoutPage.getOrderMessage(), "Thank you. Your order has been received.");
    }

    @Test
    public void loginAndCheckoutUsingDirectBAnkTransfer() throws InterruptedException, IOException {
        String searchFor = "Blue";
        BillingAddress billingAddress = JacksonUtils.deserializedJson("myBillingAddress.json", BillingAddress.class);
        User user = new User(ConfigLoader.getInstance().getUsername(),
                ConfigLoader.getInstance().getPassword());
        Product product = new Product(1215);

        HomePage homePage = new HomePage(getDriver()).load();
        StorePage storePage = homePage.getPageHeader().navigateToStoreUsingMenu();
        storePage.search(searchFor);
        Thread.sleep(1500);
        Assert.assertEquals(storePage.getTitle(), "Search results: “" + searchFor + "”");
        storePage.getProductThumbnail().clickAddToCartBtn(product.getName());
        CartPage cartPage = storePage.getProductThumbnail().clickViewCart();
        Assert.assertEquals(cartPage.getProductName(), product.getName());
        CheckoutPage checkoutPage = cartPage.checkout();
        checkoutPage.clickHereToLoginLink();
        checkoutPage.
                login(user).
                setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                clickPlaceOrderBtn();
        Assert.assertEquals(checkoutPage.getOrderMessage(), "Thank you. Your order has been received.");
    }
}
