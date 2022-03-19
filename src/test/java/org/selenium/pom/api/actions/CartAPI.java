package org.selenium.pom.api.actions;

import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.selenium.pom.utils.ConfigLoader;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class CartAPI {
    private Cookies cookies;

    public CartAPI(){}

    public CartAPI(Cookies cookies){
        this.cookies = cookies;
    }

    public Cookies getCookies(){
        return cookies;
    }

    public Response addToCart(int productId, int quantity){
        Header header = new Header("content-type", "application/x-www-form-urlencoded; charset=UTF-8"); // Get from request header
        Headers headers = new Headers(header);
        // Initialise values from request Data Form
        HashMap<String,Object> formParams = new HashMap<>();
        formParams.put("product_sku", "");
        formParams.put("product_id", productId);
        formParams.put("quantity", quantity);

        if(cookies == null){cookies = new Cookies();}

        Response response = given().
                baseUri(ConfigLoader.getInstance().getBaseUrl()).
                headers(headers).
                formParams(formParams).
                cookies(cookies).
                log().all().
                when().
                post("/?wc-ajax=add_to_cart").
                then().
                log().all().
                extract().
                response();
        if(response.getStatusCode() != 200){
            throw new RuntimeException("Failed to add product" + productId +
                    " to the cart, HTTP status code: " + response.getStatusCode());
        }
        this.cookies = response.getDetailedCookies(); // get all cookies
        return response;
    }
}
