import java.time.LocalDateTime;

public class Booking {
    private String bookingUserName;
    private LocalDateTime bookingStartTime;
    private Boolean bookingActive;

    private String bookingRoomName;
    public Booking (
            String bookingUserName,
            LocalDateTime bookingStartTime,
            Boolean bookingActive,
            String bookingRoomName
    ){
        this.bookingUserName = bookingUserName;
        this.bookingStartTime = bookingStartTime;
        this.bookingActive = bookingActive;
        this.bookingRoomName = bookingRoomName;
    }

    public String getBookingUserName() {
        return bookingUserName;
    }

    public LocalDateTime getBookingStartTime() {
        return bookingStartTime;
    }

    public String getBookingRoomName() {
        return bookingRoomName;
    }
}
