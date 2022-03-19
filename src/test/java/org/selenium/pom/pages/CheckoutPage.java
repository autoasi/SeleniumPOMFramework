package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.User;

public class CheckoutPage extends BasePage {

    private final By billingFirstNameFld = By.id("billing_first_name");
    private final By billingLastNameFld = By.id("billing_last_name");
    private final By billingAddressFld = By.id("billing_address_1");
    private final By billingCityFld = By.id("billing_city");
    private final By billingPostcodeFld = By.id("billing_postcode");
    private final By billingEmailFld = By.id("billing_email");
    private final By placeOrderBtn = By.id("place_order");
    private final By orderMessage = By.cssSelector(".woocommerce-notice");
    private final By clickHereTologinLink = By.className("showlogin");
    private final By usernameFld = By.id("username");
    private final By passwordFld = By.id("password");
    private final By loginBtn = By.name("login");
    private final By overlay = By.cssSelector(".blockUI.blockOverlay");
    private final By countryDropdown = By.id("billing_country");
    private final By stateDropdown = By.id("billing_state");
    private final By directBankTransferRadio = By.id("payment_method_bacs");
    private final By productName = By.cssSelector("td[class='product-name']");
    private final By errorMessage = By.cssSelector("div[class='woocommerce-notices-wrapper'] li:nth-child(1)");
    private final By cashOnDeliveryRadio = By.id("payment_method_cod");
    private final By clickHereToEnterYourCouponLink = By.className("showcoupon");
    private final By couponFld = By.id("coupon_code");
    private final By applyCouponBtn = By.name("apply_coupon");
    //private final By cartTotalAmountLink = By.xpath("//tr[@class='order-total']//bdi[1]");
    private final By cartTotalAmountLink = By.cssSelector("tr[class='order-total'] span[class='woocommerce-Price-amount amount']");
    private final By cartSubtotalAmount = By.cssSelector("tr[class='cart-subtotal'] bdi:nth-child(1)");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage enterBillingFirstName(String firstName){
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(billingFirstNameFld));
        e.clear();
        e.sendKeys(firstName);
        return this;
    }

    public CheckoutPage load(){
        load("/checkout/");
        return this;
    }

    public CheckoutPage enterBillingLastName(String lastName){
        WebElement e = waitForElementTobeVisible(billingLastNameFld);
        e.clear();
        e.sendKeys(lastName);
        return this;
    }

    public CheckoutPage enterBillingAddress(String address){
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(billingAddressFld));
        e.clear();
        e.sendKeys(address);
        return this;
    }

    public CheckoutPage selectBillingCountry(String countryName){
        Select select = new Select(driver.findElement(countryDropdown));
        select.selectByVisibleText(countryName);
        return this;
    }

    public CheckoutPage selectBillingState(String stateName){
        if(!stateName.isEmpty()) {
            Select select = new Select(driver.findElement(stateDropdown));
            select.selectByVisibleText(stateName);
        }
        return this;
    }

    public CheckoutPage selectDirectBankTransfer(){
        WebElement e = wait.until(ExpectedConditions.elementToBeClickable(directBankTransferRadio));
        if(!e.isSelected()){
            e.click();
        }
        return this;
    }

    public CheckoutPage selectCashOnDelivery(){
        WebElement e = wait.until(ExpectedConditions.elementToBeClickable(cashOnDeliveryRadio));
        if(!e.isSelected()){
            e.click();
        }
        return this;
    }

    public CheckoutPage enterBillingCity(String city){
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(billingCityFld));
        e.clear();
        e.sendKeys(city);
        return this;
    }

    public CheckoutPage enterBillingPostcode(String postcode){
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(billingPostcodeFld));
        e.clear();
        e.sendKeys(postcode);
        return this;
    }

    public CheckoutPage enterBillingEmail(String email){
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(billingEmailFld));
        e.clear();
        e.sendKeys(email);
        return this;
    }

    public CheckoutPage clickPlaceOrderBtn(){
        waitForOverlaysToDisappear(overlay);
        driver.findElement(placeOrderBtn).click(); // explicit wait is not required
        return this;
    }

    public String getOrderMessage(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderMessage)).getText();
    }

    public CheckoutPage clickHereToLoginLink(){
        wait.until(ExpectedConditions.elementToBeClickable(clickHereTologinLink)).click();
        return this;
    }

    public CheckoutPage enterUsername(String username){
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameFld)).sendKeys(username);
        return this;
    }

    public CheckoutPage enterPassword(String password){
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordFld)).sendKeys(password);
        return this;
    }

    public CheckoutPage clickLoginBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
        return this;
    }

    private CheckoutPage waitForLoginBtnDToDisappear(){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loginBtn));
        return this;
    }

    public CheckoutPage login(User user){
        return enterUsername(user.getUsername()).
                enterPassword(user.getPassword()).
                clickLoginBtn().waitForLoginBtnDToDisappear();
    }

    public CheckoutPage applyCoupon(String coupon){
        return clickToEnterCouponLink().
                enterCoupon(coupon).
                clickApplyCouponBtn();
    }
    public CheckoutPage setBillingAddress(BillingAddress billingAddress){
        return enterBillingFirstName(billingAddress.getFirstName()).
                enterBillingLastName(billingAddress.getLastName()).
                selectBillingCountry(billingAddress.getCountry()).
                selectBillingState(billingAddress.getState()).
                enterBillingAddress(billingAddress.getAddressLineOne()).
                enterBillingCity(billingAddress.getCity()).
                enterBillingPostcode(billingAddress.getPostcode()).
                enterBillingEmail(billingAddress.getEmail());
    }

    public String getProductName() throws Exception {
        // Option 1: Wait until an loginBtn element is no longer attached to the DOM.
        //wait.until(ExpectedConditions.stalenessOf((WebElement) loginBtn));

        // Option 2: Wait for the productName element
        int i = 5;
        while(i > 0){
            try{
                return  wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
            }catch(StaleElementReferenceException e) {
                System.out.println("Element not found:" + e);
            }
            Thread.sleep(1000);
            i--;
        }
        throw new Exception(("Element not found: " + loginBtn.toString()));
    }

    public String getErrorMessage(){
        return  wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    public CheckoutPage clickToEnterCouponLink(){
        wait.until(ExpectedConditions.elementToBeClickable(clickHereToEnterYourCouponLink)).click();
        return this;
    }

    public CheckoutPage enterCoupon(String coupon){
        wait.until(ExpectedConditions.visibilityOfElementLocated(couponFld)).sendKeys(coupon);
        return this;
    }

    public CheckoutPage clickApplyCouponBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(applyCouponBtn)).click();
        waitForOverlaysToDisappear(overlay);
        return this;
    }

    public double getCartTotalAmount(){
        return  Double.parseDouble(wait.until(ExpectedConditions.visibilityOfElementLocated(cartTotalAmountLink)).getText().replace("$",""));
    }

    public double getCartSubTotalAmount(){
        return  Double.parseDouble(wait.until(ExpectedConditions.visibilityOfElementLocated(cartSubtotalAmount)).getText().replace("$",""));
    }
}
