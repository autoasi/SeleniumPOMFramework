package org.selenium.pom.api.actions;

import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.selenium.pom.objects.User;
import org.selenium.pom.utils.ConfigLoader;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class SignUpAPI {
    private Cookies cookies; // From Rest Assured

    public Cookies getCookies(){
        return cookies;
    }

    private String fetchRegisterNonceValue_Groovy(){
        Response response = getAccount();
        // Groovy GPath - to retrieve the value attribute
        return response.htmlPath().getString("**.findAll { it.@name == 'woocommerce-register-nonce' }.@value");
    }

    private String fetchRegisterNonceValue_JSoup(){
        Response response = getAccount();
        // Parse the response into JSoup document
        Document doc = Jsoup.parse(response.body().prettyPrint()); // use JSoup library
        Element element = doc.selectFirst("#woocommerce-register-nonce"); // use CSS locator
        return element.attr("value");
    }

    // GET call
    public Response getAccount(){
        Cookies cookies = new Cookies();
        Response response = given().
                baseUri(ConfigLoader.getInstance().getBaseUrl()).
                cookies(cookies).
                log().all().
                when().
                get("/account").
                then().
                log().all().
                extract().
                response();
        if(response.getStatusCode() != 200){
            throw new RuntimeException("Failed to fetch the account, HTTP status code: " +response.getStatusCode());
        }
        return response;
    }

    // POST call
    public Response register(User user){
        Cookies cookies = new Cookies();
        Header header = new Header("content-type", "application/x-www-form-urlencoded"); // From request header
        Headers headers = new Headers(header);
        // Initialise values from Data Form - request body
        HashMap<String,String> formParams = new HashMap<>();
        formParams.put("username", user.getUsername());
        formParams.put("email", user.getEmail());
        formParams.put("password", user.getPassword());
        formParams.put("woocommerce-register-nonce", fetchRegisterNonceValue_JSoup());
        formParams.put("register", "Register");

        Response response = given().
                baseUri(ConfigLoader.getInstance().getBaseUrl()).
                headers(headers).
                formParams(formParams).
                cookies(cookies).
                log().all().
                when().
                post("/account").
                then().
                log().all().
                extract().
                response();
        if(response.getStatusCode() != 302){
            throw new RuntimeException("Failed to register the account, HTTP status code: " + response.getStatusCode());
        }
        this.cookies = response.getDetailedCookies(); // get all cookies
        return response;
    }
}
