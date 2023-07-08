package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.notNullValue;

//2. Отримати id усіх доступних бронювань книжок (GET /booking)
public class GetAllBookingTest extends BasicTestConfiguration {
    @Test
    public void getAllBooking(){
        setupConfigurationsOnlyURL();
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .get("booking");
        response.then().body("bookingid", notNullValue());
        response.prettyPrint();
    }
}
