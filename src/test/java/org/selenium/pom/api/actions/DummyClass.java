package org.selenium.pom.api.actions;

import org.selenium.pom.objects.User;
import org.selenium.pom.utils.FakerUtils;

public class DummyClass {

    public static void main(String[] args){
        //new SignUpAPI().getAccount();

        //System.out.println(new SignUpAPI().fetchRegisterNonceValue_Groovy());

        //System.out.println(new SignUpAPI().fetchRegisterNonceValue_JSoup());

        // Register user
        String username = "demouser" + FakerUtils.generateRandomNumber();
        User user = new User().
                setUsername(username).
                setPassword("demopwd").
                setEmail(username + "@askomdch.com");
        SignUpAPI signUpAPI = new SignUpAPI();
        signUpAPI.register(user);
        System.out.println("REGISTER COOKIES:");
        System.out.println(signUpAPI.getCookies());

        // User is now logged in
        CartAPI cartAPI = new CartAPI(signUpAPI.getCookies()); // use register cookies
        cartAPI.addToCart(1215,1);
        System.out.println("CART COOKIES:");
        System.out.println(cartAPI.getCookies());
    }
}
