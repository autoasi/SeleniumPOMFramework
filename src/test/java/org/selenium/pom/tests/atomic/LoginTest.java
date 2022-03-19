package org.selenium.pom.tests.atomic;

import org.selenium.pom.api.actions.CartAPI;
import org.selenium.pom.api.actions.SignUpAPI;
import org.selenium.pom.base.BaseTest_TestNG;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.utils.FakerUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest extends BaseTest_TestNG {

    @Test
    public void loginDuringCheckout() throws Exception {
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
        injectCookiesToBrowser(cartAPI.getCookies()); // inject cookies only after loading the website
        checkoutPage.load(); // refresh website
        checkoutPage.
                clickHereToLoginLink().
                login(user);
        Assert.assertTrue(checkoutPage.getProductName().contains(product.getName()));
    }

    @Test
    public void loginFails() throws IOException, InterruptedException {
        User user = new User().
                setUsername("wronguser").
                setPassword("demopwd");
        CartAPI cartAPI = new CartAPI();
        Product product = new Product(1215);
        cartAPI.addToCart(product.getId(), 1);

        // Actual test starts here
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(cartAPI.getCookies()); // inject cookies only after loading the website
        checkoutPage.load(); // refresh website
        checkoutPage.
                clickHereToLoginLink().
                login(user);
        Assert.assertEquals(checkoutPage.getErrorMessage(),"Error: The username wronguser is not registered " +
                "on this site. If you are unsure of your username, try your email address instead.");
    }
}
