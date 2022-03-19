package org.selenium.pom.objects;

public class BillingAddress {

    private String firstName;
    private String lastName;
    private String addressLineOne;
    private String city;
    private String postcode;
    private String email;
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String state;

    // Constructor - default
    public BillingAddress(){}

    // Constructor - overload / parameterised
    public BillingAddress(String firstName, String lastName, String addressLineOne,
                          String city, String postcode, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressLineOne = addressLineOne;
        this.city = city;
        this.postcode = postcode;
        this.email = email;

    }
    public String getFirstName() {
        return firstName;
    }

    public BillingAddress setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public BillingAddress setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAddressLineOne() {
        return addressLineOne;
    }

    public BillingAddress setAddressLineOne(String addressLineOne) {
        this.addressLineOne = addressLineOne;
        return this;
    }

    public String getCity() {
        return city;
    }

    public BillingAddress setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPostcode() {
        return postcode;
    }

    public BillingAddress setPostcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public BillingAddress setEmail(String email) {
        this.email = email;
        return this;
    }
}
