package org.selenium.pom.dataproviders;

import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.Arrays;

public class SharedDataProvider {

    @DataProvider(name = "getFeaturedProduct", parallel = false)
    public Object[] getFeaturedProduct() throws IOException {
        Product[] products = JacksonUtils.deserializedJson("products.json", Product[].class);
        // return array with only featured products
        return Arrays.stream(products).filter(Product::isFeatured).toArray(Product[]::new);
    }

    @DataProvider(name = "getBillingAddress", parallel = false)
    public Object[] getBillingAddress() throws IOException {
        return JacksonUtils.deserializedJson("billingAddress.json", BillingAddress[].class);
    }
}
