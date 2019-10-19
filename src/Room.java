//import com.sun.org.apache.xpath.internal.operations.Bool;
//import jdk.vm.ci.meta.Local;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Room {
    private String roomName;
    private String roomType;
    private int floorNumber;
    private int roomCapacity;
    private List<Booking> bookings = new ArrayList<>(){};
    private static int count; //user input number of people
    private static String abc; //user input a b or c
    private static String year;
    private static String month;
    private static String day;
    private static String time;
    private static String storedUserName;
    private static Boolean alreadyBooked;
    private static int absoluteMaxCapacityVariable; //of all rooms
    private static Boolean roomAvailable;
    private static Scanner scanner = new Scanner(System.in);
    private static List<Room> rooms = new ArrayList<>(){};
    private static List<Integer> roomCapacityArray = new ArrayList<> () {};
    private static LocalDateTime storedDateTime;
    private static DateTimeFormatter dateTimeFormatter;
    
    public static void initialiseBookingApplication() {
        System.out.print("Would you like to: \n a) View all rooms \n b) Make a booking \n c) View your bookings \n Please enter a b or c ");
        abc = scanner.nextLine();
        if (abc.equals("a")) {displayAllRooms(); }
        else if (abc.equals("b")) {
            checkAbsoluteMaxCapacity();
        }
        else if (abc.equals("c")) {}
        else {
            System.out.println("Please enter a, b or c");}
    }
    public static void inputCountVariable(){
        System.out.print("How many people would you like to book for?  ");
        count = Integer.parseInt(scanner.nextLine());
    }
    public static void inputUserNameVariable(){
        System.out.println("Choose a user name: ");
        storedUserName = scanner.nextLine();
    }
    public static void inputDateTimeVariable() {
        System.out.print("Enter date and time in the following format: \n where HH is the start time you want to book for in 24hr format \n dd/MM/yyyy HH ");
        String inputDateTime = scanner.nextLine();
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH");
        storedDateTime = LocalDateTime.parse(inputDateTime, dateTimeFormatter);
        // check day is Monday - Friday and time is between 10 and 4
    }
    public static void outputDateTimeVariable() {
        String outputDateTime = storedDateTime.format(dateTimeFormatter);
        System.out.println(outputDateTime);
    }
    public static void displayAllRooms() {
        for (Room room: rooms) {
            room.displayOneRoom();
        }
    }
    public void displayOneRoom() {
        System.out.println("Room Name: " + roomName + "\n"
                + "Room Type: " + roomType + "\n"
                + "Floor Number: " + floorNumber + "\n"
                + "Room Capacity: " + roomCapacity + "\n");
    }
    public static void createRoomCapacityArray(){
        for (Room room: rooms){
            roomCapacityArray.add(room.roomCapacity);
        }
    }
    public static void createAbsoluteMaxCapacityVariable(){
        createRoomCapacityArray();
        for (Integer eachRoomCapacity: roomCapacityArray){
            if (eachRoomCapacity > absoluteMaxCapacityVariable) {
                absoluteMaxCapacityVariable = eachRoomCapacity;
            }
        }
    }
    public static void checkAbsoluteMaxCapacity() {
        inputCountVariable();
        createAbsoluteMaxCapacityVariable();
        if (count > absoluteMaxCapacityVariable) {
            System.out.println("It is not possible to book for more than " + absoluteMaxCapacityVariable + " people.");
        }
        else {
            displaySuitableRooms();
        }
    }
    public static void checkWeekday(){ }
    public static void displaySuitableRooms() {
        for (Room room : rooms) {
            checkSuitability(room);
            // if room.suitableRoomArray.size() == 0 sout "There are no suitable rooms available at this time"
        }
        System.out.println("Hello World!");
    }
    public static void checkSuitability(Room room) {
        if (room.roomCapacity >= count) { //if count is equal or less than roomCapacity AND the room is available
            if (room.bookings.size() == 0) {
                room.displayOneRoom(); // and add room to suitableRoomArray
            }
            else {
                for(Booking booking : room.bookings) {
                    if (booking.bookingStartTime != storedDateTime) {
                        //create variable booked=false for each loop
                        // if all variable of booked=false {
                        room.displayOneRoom(); // and add room to suitableRoomArray
                        //{
                    } else if (booking.bookingStartTime == storedDateTime && booking.bookingActive == true) {
                        //create variable booked=false for each loop
                        // if all variable of booked=false {
                        room.displayOneRoom(); // and add room to suitableRoomArray
                    }
                }
            }
        }
    }
    public static void bookRoom() {
        // user input which room they want to book
        // (room instance with roomName == (userInput)) == newVariable
        // newVariable.bookRoom2
    }
    public void bookRoom2() {
        // add a new instance of booking to the bookings array for chosen Room instance.
        // instance will have property bookingUser = storedUserName, bookingStartTime = storedDateTime and bookingActive = true.
    }

    public static void displayBookingsPerUser(Room room){
        for(Booking booking : room.bookings) {
            if (booking.bookingUserName == storedUserName) {
                //make sure this happens for every booking and that it doesn't stop after the first output
                System.out.println(storedUserName);
        }
    }
}


    public Room (
            String roomName,
            String roomType,
            int floorNumber,
            int roomCapacity
    ){
        this.roomName = roomName;
        this.roomType = roomType;
        this.floorNumber = floorNumber;
        this.roomCapacity = roomCapacity;
    }
    public class Booking {
        private String bookingUserName;
        private LocalDateTime bookingStartTime;
        private Boolean bookingActive;
        // NEED TO ADD VARIABLE FOR ROOM NAME
        public Booking (
                String bookingUserName,
                LocalDateTime bookingStartTime,
                Boolean bookingActive
        ){
            this.bookingUserName = bookingUserName;
            this.bookingStartTime = bookingStartTime;
            this.bookingActive = bookingActive;
        }
    }

    public static void main(String[] args) {
        rooms.add(new Room(
                "Taff",
                "Meeting Room",
                2,
                8
        ));
        rooms.add(new Room(
                "Llangorse",
                "Large meeting room",
                2,
                8
        ));
        rooms.add(new Room(
                "Pen y Fan",
                "Teaching Space",
                2,
                70
        ));
        rooms.add(new Room(
                "Usk",
                "Small meeting room",
                3,
                8
        ));
        rooms.add(new Room(
                "Bala",
                "Large meeting room",
                3,
                24
        ));
        rooms.add(new Room(
                "Cadair Idris",
                "Large teaching space",
                3,
                70
        ));
        rooms.add(new Room(
                "Wye",
                "Small meeting room",
                4,
                8
        ));
        rooms.add(new Room(
                "Gower",
                "Open meeting/ breakout space",
                4,
                70
        ));
        rooms.add(new Room(
                "Snowdon",
                "Teaching space",
                4,
                70
        ));

        initialiseBookingApplication();
    }
}
