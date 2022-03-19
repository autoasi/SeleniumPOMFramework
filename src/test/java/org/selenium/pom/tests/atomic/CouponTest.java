package org.selenium.pom.tests.atomic;

import org.selenium.pom.api.actions.CartAPI;
import org.selenium.pom.base.BaseTest_TestNG;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.DecimalFormat;

public class CouponTest extends BaseTest_TestNG {

    @Test
    public void freeshipCoupon() throws IOException, InterruptedException {
        BillingAddress billingAddress = JacksonUtils.deserializedJson("myBillingAddress.json", BillingAddress.class);
        double taxRate = 0.075;
        final DecimalFormat df = new DecimalFormat("0.00");

        // load checkout page
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();

        CartAPI cartAPI = new CartAPI();
        cartAPI.addToCart(1215,1);
        injectCookiesToBrowser(cartAPI.getCookies());

        double subTotalCartBefore =checkoutPage.
                load().
                setBillingAddress(billingAddress).
                getCartSubTotalAmount();
        checkoutPage.applyCoupon("freeship");

        double taxAmount = subTotalCartBefore * taxRate;
        double expectedAmount = Double.parseDouble(df.format(subTotalCartBefore  + taxAmount ));
        Assert.assertEquals(checkoutPage.getCartTotalAmount(), expectedAmount);
    }

    @Test
    public void off25Coupon() throws IOException, InterruptedException {
        double taxRate = 0.075;
        BillingAddress billingAddress = JacksonUtils.deserializedJson("myBillingAddress.json", BillingAddress.class);
        final DecimalFormat df = new DecimalFormat("0.00");

        // load checkout page
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();

        CartAPI cartAPI = new CartAPI();
        cartAPI.addToCart(1215,1);
        injectCookiesToBrowser(cartAPI.getCookies());

        double subTotalCartBefore = checkoutPage.
                load().
                setBillingAddress(billingAddress).
                getCartSubTotalAmount();
        checkoutPage.applyCoupon("off25");

        System.out.println(subTotalCartBefore);
        double subTotalCartAfter = subTotalCartBefore - 5;
        double taxAmount = (subTotalCartAfter + 5) * taxRate;
        double expectedAmount = Double.parseDouble(df.format(subTotalCartAfter  + taxAmount + 5));
        Thread.sleep(3000);
        Assert.assertEquals(checkoutPage.getCartTotalAmount(), expectedAmount);

    }

    @Test
    public void offcart5Coupon() throws IOException, InterruptedException {
        BillingAddress billingAddress = JacksonUtils.deserializedJson("myBillingAddress.json", BillingAddress.class);
        double taxRate = 0.075;

        // load checkout page
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();

        CartAPI cartAPI = new CartAPI();
        cartAPI.addToCart(1215,1);
        injectCookiesToBrowser(cartAPI.getCookies());

        double subTotalCartBefore = checkoutPage.
                load().
                setBillingAddress(billingAddress).
                getCartSubTotalAmount();

        checkoutPage.applyCoupon("offcart5");

        double taxAmount = (subTotalCartBefore - 5) * taxRate;
        double expectedAmount = (subTotalCartBefore - 5) + taxAmount + 5 ;
        Assert.assertEquals(checkoutPage.getCartTotalAmount(), expectedAmount);

    }

}
