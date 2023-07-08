package tests;

import apiobjects.RequestToCreatingToken;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

//Видалити одне бронювання з отриманих у п.2 (DELETE)
public class DeleteTheBooking extends BasicTestConfiguration {
    @Test
    public void deleteChosenBooking(){
        setupConfigurationsOnlyURL();
        //create token
        RequestToCreatingToken tokenBody = new RequestToCreatingToken().builder()
                .username("admin")
                .password("password123")
                .build();
        Response responseToken = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(tokenBody)
                .post("auth");
        String token = responseToken.jsonPath().get("token");

        //get the id of the third booking
        Response responseOfGet = RestAssured.given()
                .header("Accept", "application/json")
                .get("booking");
        List<Integer> bookingIds = responseOfGet.jsonPath().get("bookingid");
        Integer thirdBookingId = bookingIds.get(2);

        //delete the chosen booking
        Response response = RestAssured.given()
                .cookie("token={token}", token)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .delete("booking/{id}", thirdBookingId);
        response.then().statusCode(201);
        response.prettyPrint();

    }
}
