package org.selenium.pom.tests.atomic;

import org.selenium.pom.api.actions.CartAPI;
import org.selenium.pom.api.actions.SignUpAPI;
import org.selenium.pom.base.BaseTest_TestNG;
import org.selenium.pom.dataproviders.SharedDataProvider;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.utils.FakerUtils;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class CheckoutTest extends BaseTest_TestNG {

    @Test
    public void GuestCheckoutUsingBankTransfer() throws IOException {
        BillingAddress billingAddress = JacksonUtils.deserializedJson("myBillingAddress.json", BillingAddress.class);

        // load checkout page
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();

        CartAPI cartAPI = new CartAPI();
        cartAPI.addToCart(1215,1);
        injectCookiesToBrowser(cartAPI.getCookies());

        checkoutPage.
                load().
                setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                clickPlaceOrderBtn();
        Assert.assertEquals(checkoutPage.getOrderMessage(), "Thank you. Your order has been received.");
    }

    @Test
    public void LoginAndCheckoutUsingDirectBankTransfer() throws IOException {
        BillingAddress billingAddress = JacksonUtils.deserializedJson("myBillingAddress.json", BillingAddress.class);

        // Setup application state using API and not UI
        String username = "demouser" + FakerUtils.generateRandomNumber();
        User user = new User().
                setUsername(username).
                setPassword("demopwd").
                setEmail(username + "@askomdch.com");

        SignUpAPI signUpAPI = new SignUpAPI();
        signUpAPI.register(user);
        CartAPI cartAPI = new CartAPI(signUpAPI.getCookies());
        Product product = new Product(1215);
        cartAPI.addToCart(product.getId(), 1);

        // Actual test starts here
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(signUpAPI.getCookies()); // inject cookies only after loading the website
        checkoutPage.
                load().
                setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                clickPlaceOrderBtn();
        Assert.assertEquals(checkoutPage.getOrderMessage(), "Thank you. Your order has been received.");
    }

    @Test
    public void GuestCheckoutUsingCashOnDelivery() throws IOException {
        BillingAddress billingAddress = JacksonUtils.deserializedJson("myBillingAddress.json", BillingAddress.class);

        // load checkout page
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();

        CartAPI cartAPI = new CartAPI();
        cartAPI.addToCart(1215,1);
        injectCookiesToBrowser(cartAPI.getCookies());

        checkoutPage.
                load().
                setBillingAddress(billingAddress).
                selectCashOnDelivery().
                clickPlaceOrderBtn();
        Assert.assertEquals(checkoutPage.getOrderMessage(), "Thank you. Your order has been received.");
    }

    @Test
    public void LoginAndCheckoutUsingCashOnDelivery() throws IOException {
        BillingAddress billingAddress = JacksonUtils.deserializedJson("myBillingAddress.json", BillingAddress.class);

        // Setup application state using API and not UI
        String username = "demouser" + FakerUtils.generateRandomNumber();
        User user = new User().
                setUsername(username).
                setPassword("demopwd").
                setEmail(username + "@askomdch.com");

        SignUpAPI signUpAPI = new SignUpAPI();
        signUpAPI.register(user);
        CartAPI cartAPI = new CartAPI(signUpAPI.getCookies());
        Product product = new Product(1215);
        cartAPI.addToCart(product.getId(), 1);

        // Actual test starts here
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(signUpAPI.getCookies()); // inject cookies only after loading the website
        checkoutPage.
                load().
                setBillingAddress(billingAddress).
                selectCashOnDelivery().
                clickPlaceOrderBtn();
        Assert.assertEquals(checkoutPage.getOrderMessage(), "Thank you. Your order has been received.");
    }

    @Test(dataProvider = "getBillingAddress", dataProviderClass = SharedDataProvider.class)
    public void LoginAndCheckoutUsingDirectBankTransferWillBilling(BillingAddress billingAddress) throws IOException {
        //BillingAddress billingAddress = JacksonUtils.deserializedJson("myBillingAddress.json", BillingAddress.class);

        // Setup application state using API and not UI
        String username = "demouser" + FakerUtils.generateRandomNumber();
        User user = new User().
                setUsername(username).
                setPassword("demopwd").
                setEmail(username + "@askomdch.com");

        SignUpAPI signUpAPI = new SignUpAPI();
        signUpAPI.register(user);
        CartAPI cartAPI = new CartAPI(signUpAPI.getCookies());
        Product product = new Product(1215);
        cartAPI.addToCart(product.getId(), 1);

        // Actual test starts here
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(signUpAPI.getCookies()); // inject cookies only after loading the website
        checkoutPage.
                load().
                setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                clickPlaceOrderBtn();
        Assert.assertEquals(checkoutPage.getOrderMessage(), "Thank you. Your order has been received.");
    }
}
