package org.selenium.pom.tests.e2e;

//import org.junit.jupiter.api.Assertions;
import org.selenium.pom.base.BaseTestJUnit5;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.StorePage;
import org.selenium.pom.utils.JacksonUtils;

import java.io.IOException;

public class JUnit5_MyFirstTesCase extends BaseTestJUnit5 {

    //@Test
    public void guestCheckoutUsingDirectBankTransfer() throws InterruptedException, IOException {
        String searchFor = "Blue";
        BillingAddress billingAddress = JacksonUtils.deserializedJson("myBillingAddress.json", BillingAddress.class);
        Product product = new Product(1215);
        // Home page
        HomePage homePage = new HomePage(driver).load();
        StorePage storePage = homePage.getPageHeader().navigateToStoreUsingMenu();
        //storePage
        storePage.isLoaded();
        storePage.search(searchFor); // Functional page Object - method provides a functional solution and not single action
        Thread.sleep(1500);
        //Assertions.assertEquals("Search results: “" + searchFor + "”", storePage.getTitle());
        storePage.getProductThumbnail().clickAddToCartBtn(product.getName());
        // Cart Page
        CartPage cartPage = storePage.getProductThumbnail().clickViewCart();
        cartPage.isLoaded();
        //Assertions.assertEquals(product.getName(), cartPage.getProductName());
        // Checkout Page
        CheckoutPage checkoutPage = cartPage.
                checkout().
                setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                clickPlaceOrderBtn();
        //Assertions.assertEquals("Thank you. Your order has been received.", checkoutPage.getOrderMessage());
    }

    //@Test
    public void loginAndCheckoutUsingDirectBAnkTransfer() throws InterruptedException, IOException {
        String searchFor = "Blue";
        BillingAddress billingAddress = JacksonUtils.deserializedJson("myBillingAddress.json", BillingAddress.class);
        User user = new User("demoasi","demopwd");
        Product product = new Product(1215);

        HomePage homePage = new HomePage(driver).load();
        StorePage storePage = homePage.getPageHeader().navigateToStoreUsingMenu();
        storePage.search(searchFor);
        Thread.sleep(1500);
        //Assertions.assertEquals("Search results: “" + searchFor + "”", storePage.getTitle());
        storePage.getProductThumbnail().clickAddToCartBtn(product.getName());
        CartPage cartPage = storePage.getProductThumbnail().clickViewCart();
        //Assertions.assertEquals(product.getName(), cartPage.getProductName());
        CheckoutPage checkoutPage = cartPage.checkout();
        checkoutPage.clickHereToLoginLink();
        checkoutPage.
                login(user).
                setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                clickPlaceOrderBtn();
        //Assertions.assertEquals("Thank you. Your order has been received.", checkoutPage.getOrderMessage());
    }
}
