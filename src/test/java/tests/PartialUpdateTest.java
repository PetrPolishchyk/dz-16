package tests;

import apiobjects.BookingCreator;
import apiobjects.BookingDates;
import apiobjects.RequestToCreatingToken;
import apiobjects.ResponseWithCreatedBooking;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
//3. Вибрати одне з id з п.2 та змінити ціну бронювання (PATCH)
public class PartialUpdateTest extends BasicTestConfiguration {

    @Test
    public void getChangeTheTotalPrice(){
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

        //get the id of the first booking
        Response responseOfGet = RestAssured.given()
                .header("Accept", "application/json")
                .get("booking");
        List<Integer> bookingIds = responseOfGet.jsonPath().get("bookingid");
        Integer firstBookingId = bookingIds.get(0);

        //get the booking object of chosen id and change the price of the object
        Response responseChosenBooking = RestAssured.given()
                .header("Accept", "application/json")
                .get("booking/{id}", firstBookingId);
        BookingCreator chosenBook = responseChosenBooking.as(BookingCreator.class);
        chosenBook.setTotalprice(5000);

        //change the price
        Response responseOfUpdate = RestAssured.given()
                .contentType("application/json")
                .accept("application/json")
                .cookie("token={token}", token)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .body(chosenBook)
                .patch("booking/{id}", firstBookingId);
        responseOfUpdate.then().body("totalprice", equalTo(5000));
        responseOfUpdate.prettyPrint();
    }
}
