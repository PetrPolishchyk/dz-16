package tests;

import apiobjects.BookingCreator;
import apiobjects.RequestToCreatingToken;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

//4. Вибрати інший id з отриманих у п.2 та змінити ім’я та additionalneeds (PUT)
public class BookingUpdateTest extends BasicTestConfiguration{
    @Test
    public void getChangeTheNameAndNeeds(){
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

        //get the id of the second booking
        Response responseOfGet = RestAssured.given()
                .header("Accept", "application/json")
                .get("booking");
        List<Integer> bookingIds = responseOfGet.jsonPath().get("bookingid");
        Integer secondBookingId = bookingIds.get(1);

        //get the booking object of chosen id and change the name and additionalneeds of the object
        Response responseChosenBooking = RestAssured.given()
                .header("Accept", "application/json")
                .get("booking/{id}", secondBookingId);
        BookingCreator chosenBook = responseChosenBooking.as(BookingCreator.class);
        chosenBook.setFirstname("Peter");
        chosenBook.setAdditionalneeds("To finish this interesting task)");

        //change data
        Response responseOfUpdatedBook = RestAssured.given()
                .contentType("application/json")
                .accept("application/json")
                .cookie("token={token}", token)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .body(chosenBook)
                .put("booking/{id}", secondBookingId);
        responseOfUpdatedBook.then().body("firstname", equalTo("Peter"));
        responseOfUpdatedBook.then().body("additionalneeds", equalTo("To finish this interesting task)"));
        responseOfUpdatedBook.prettyPrint();
    }
}
