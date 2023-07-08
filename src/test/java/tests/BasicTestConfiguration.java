package tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;

public class BasicTestConfiguration {
    //http://restful-booker.herokuapp.com/
    //http://restful-booker.herokuapp.com/apidoc/index.html

    public void setupConfigurationsOnlyURL(){
        RestAssured.baseURI = "http://restful-booker.herokuapp.com";
    }
    public void setupConfigurationWithHeader(){
        RestAssured.baseURI = "http://restful-booker.herokuapp.com";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Accept", "application/json")
                .build();
    }
}
