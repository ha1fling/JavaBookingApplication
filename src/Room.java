import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomName;
    private int roomCode;
    private String roomType;
    private int floorNumber;
    private int roomCapacity;

    public Room (
            String roomName,
            int roomCode,
            String roomType,
            int floorNumber,
            int roomCapacity
    ){
        this.roomName = roomName;
        this.roomCode = roomCode;
        this.roomType = roomType;
        this.floorNumber = floorNumber;
        this.roomCapacity = roomCapacity;
    }

    public void displayOneRoom() {
        System.out.println("Room Name: " + roomName + "\n"
                + "Room Code: " + roomCode + "\n"
                + "Room Type: " + roomType + "\n"
                + "Floor Number: " + floorNumber + "\n"
                + "Room Capacity: " + roomCapacity + "\n");
    }

    //This method checks whether a room's capacity is large enough to hold the number of guests that the user wants to
    //book for and whether the room is available at the time the user wants to book for
    //It adds each suitable room to the suitableRooms array
    public void checkSuitability() {
        if (roomCapacity >= BookingApplication.getCount() && bookings.size() == 0 ) { //
            displayOneRoom();
            BookingApplication.getSuitableRooms().add(this);
        }
        else if (roomCapacity >= BookingApplication.getCount() && bookings.size() > 0) {
            for(Booking booking : bookings) {
                if (!booking.getBookingStartTime().equals(BookingApplication.getStoredDateTime())) {
                    displayOneRoom();
                    BookingApplication.getSuitableRooms().add(this); // and add room to suitableRooms
                }
            }
        }
    }

    //Initialise array for bookings
    private List<Booking> bookings = new ArrayList<>(){};
    //Method to create booking objects from booking class
    public void addBookingToBookingsArray() {
        bookings.add(new Booking(
                BookingApplication.getStoredUserName(),
                BookingApplication.getStoredDateTime(),
                true, //if user wanted to cancel this could be turned to false in future code
                roomName
        ));
    }

    //Getters
    public String getRoomName() {
        return roomName;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}
