package tests;

import apiobjects.BookingCreator;
import apiobjects.BookingDates;
import apiobjects.ResponseWithCreatedBooking;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.Matchers.equalTo;

//1. Створити бронювання книжки

public class CreateBookingTest extends BasicTestConfiguration {

    private final LocalDate dateCheckIn = LocalDate.of(2022, Month.JULY, 8);
    private final LocalDate dateCheckOut = LocalDate.of(2023, Month.JULY, 8);

    @Test
    public void createBooking(){
        setupConfigurationsOnlyURL();
        new BookingCreator();
        BookingCreator createBook = BookingCreator.builder()
                .firstname("Alan")
                .lastname("Foster")
                .totalprice(1200)
                .depositpaid(true)
                .bookingdates(BookingDates.builder().checkin(dateCheckIn).checkout(dateCheckOut).build())
                .additionalneeds("Reading at lunch")
                .build();
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(createBook)
                .post("/booking");
        response.prettyPrint();
        response.then().body("booking.firstname", equalTo("Alan"));
        int newBookingId = response.as(ResponseWithCreatedBooking.class).getBookingid();
        System.out.println(newBookingId);
    }

}
