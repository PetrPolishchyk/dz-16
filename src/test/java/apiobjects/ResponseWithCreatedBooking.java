package apiobjects;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ResponseWithCreatedBooking {
    private Integer bookingid;
    private BookingCreator booking;
}
