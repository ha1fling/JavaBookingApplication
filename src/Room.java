//import com.sun.org.apache.xpath.internal.operations.Bool;
//import jdk.vm.ci.meta.Local;
//import java.awt.print.Book;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Room {
    private String roomName;
    private int roomCode;
    private String roomType;
    private int floorNumber;
    private int roomCapacity;
    private List<Booking> bookings = new ArrayList<>(){}; // does this need to be static?
    private static List<Room> suitableRooms = new ArrayList<>(){};
    private static int count; //user input number of people
    private static String abc; //user input a b or c
    private static String storedUserName;
    private static int requestedRoomCode;
    private static int requestedRoomIndex;
    private static Boolean alreadyBooked;
    private static int maxCapacityVariable;
    private static Scanner scanner = new Scanner(System.in);
    private static List<Room> rooms = new ArrayList<>(){};
    private static List<Integer> roomCapacityArray = new ArrayList<> () {};
    private static LocalDateTime storedDateTime;
    private static String outputDateTime;
    private static DateTimeFormatter dateTimeFormatter;
    
    public static void initialiseBookingApplication() {

        System.out.print("Would you like to: \n a) View all rooms \n b) Make a booking \n c) View your bookings \n Please enter a b or c ");
        abc = scanner.nextLine();
        if (abc.equals("a")) {
            displayAllRooms();
            initialiseBookingApplication();
        }
        else if (abc.equals("b")) {
            inputUserNameVariable();
            inputDateTimeVariable();
            initialiseBookingApplication();
        }
        else if (abc.equals("c")) {
            displayBookingsPerUser();
            initialiseBookingApplication();
        }
        //else if (abcd.equals("d")) {
        //inputUserNameVariable(); // this will overwrite the current user name variable
        else {
            inputUserNameVariable();
            System.out.println("Please enter a, b or c");}
            // check that a b or c is entered
            // ask the user to only input a b or c
    }
    // create a class that allows the user to choose whether they want to be returned to the main menu
    public static void inputUserNameVariable(){
        System.out.println("Input username: ");
        storedUserName = scanner.nextLine();
    }
    public static void inputDateTimeVariable() {
        System.out.print("Enter date and time in the following format: \n where HH is the start time you want to book for in 24hr format \n dd/MM/yyyy HH ");
        String inputDateTime = scanner.nextLine();
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH");
        storedDateTime = LocalDateTime.parse(inputDateTime, dateTimeFormatter);
        outputDateTime = storedDateTime.format(dateTimeFormatter);
        // check format is entered correctly
        // check day is Monday - Friday and time is between 10 and 4
        inputCountVariable();
    }
    public static void inputCountVariable(){
        System.out.print("How many people would you like to book for?  ");
        count = Integer.parseInt(scanner.nextLine());
        checkMaxCapacity();
        // Check that a number is entered
    }
    public static void inputRequestedRoomCode(){
        System.out.println("Please enter the room code for the room you would like to book: ");
        requestedRoomCode = Integer.parseInt(scanner.nextLine());
        // catch error
    }
    public static void createRequestedRoomIndexVariable() {
        requestedRoomIndex = requestedRoomCode-101;
    }
    public static void displayAllRooms() {
        for (Room room: rooms) {
            room.displayOneRoom();
        }
    }
    public void displayOneRoom() {
        System.out.println("Room Name: " + roomName + "\n"
                + "Room Code: " + roomCode + "\n"
                + "Room Type: " + roomType + "\n"
                + "Floor Number: " + floorNumber + "\n"
                + "Room Capacity: " + roomCapacity + "\n");
    }
    public static void createRoomCapacityArray(){
        roomCapacityArray.clear();
        for (Room room: rooms){
            roomCapacityArray.add(room.roomCapacity);
        }
    }
    public static void createMaxCapacityVariable(){
        createRoomCapacityArray();
        for (Integer eachRoomCapacity: roomCapacityArray){
            if (eachRoomCapacity > maxCapacityVariable) {
                maxCapacityVariable = eachRoomCapacity;
            }
        }
    }
    public static void checkMaxCapacity() {
        createMaxCapacityVariable();
        if (count > maxCapacityVariable) {
            System.out.println("It is not possible to book for more than " + maxCapacityVariable + " people.");
        }
        else {
            displaySuitableRooms();
        }
    }
    public static void checkOpeningHours(){
        //this method will check that the date and time entered are not a Saturday or a Sunday and are not outside of the hours 10-4
        //this could be taken further to incorporate bank holidays and term times.
    }
    public static void displaySuitableRooms() {
        suitableRooms.clear();
        for (Room room : rooms) {
            checkSuitability(room);
        }
        if (suitableRooms.size() == 1) {
            System.out.println("There are no rooms available for your party size and time" );
        }
        else bookRoom() ;
    }
    public static void checkSuitability(Room room) {
        if (room.roomCapacity >= count) { //if count is equal or less than roomCapacity AND the room is available
            if (room.bookings.size() == 0) {
                room.displayOneRoom(); // and add room to suitableRooms
                suitableRooms.add(room); // and add room to suitableRooms
            } else {
                for (Booking booking : room.bookings) {
                    if (booking.bookingStartTime != storedDateTime) {
                        //create variable booked=false for each loop
                        // if all variable of booked=false {
                        room.displayOneRoom();
                        suitableRooms.add(room); // and add room to suitableRooms
                        //{
                    } else if (booking.bookingStartTime == storedDateTime && booking.bookingActive == true) {
                        //create variable booked=false for each loop
                        // if all variable of booked=false {
                        room.displayOneRoom();// and add room to suitableRooms
                        suitableRooms.add(room); // and add room to suitableRooms
                    }
                }
            }
        }
    }
    public static void bookRoom() {
        inputRequestedRoomCode();
        createRequestedRoomIndexVariable();
        suitableRooms.clear();
         checkSuitability(rooms.get(requestedRoomIndex));
         if (suitableRooms.size() == 1) {
             rooms.get(requestedRoomIndex).addBookingToBookingsArray();
             System.out.println("You have booked room " + rooms.get(requestedRoomIndex).roomName + " for " + outputDateTime + " o'clock, under the name " + storedUserName + ".");
         }
         else System.out.println("This room is not available please enter another room code" );
    }
    public void addBookingToBookingsArray() {
        bookings.add(new Booking (
                storedUserName,
                storedDateTime,
                true, //if user wanted to cancel this could be turned to false in future code
                rooms.get(requestedRoomIndex).roomName
        ));
        // add a new instance of booking to the bookings array for chosen Room instance.
        // instance will have property bookingUser = storedUserName, bookingStartTime = storedDateTime and bookingActive = true.
    }

    public static void displayBookingsPerUser(){
        for (Room room: rooms){
            for(Booking booking : room.bookings) {
                if (booking.bookingUserName == storedUserName) {
                    //make sure this happens for every booking and that it doesn't stop after the first output
                    System.out.println("You have booked room " + rooms.get(requestedRoomIndex).roomName + " for " + outputDateTime + " o'clock, under the name " + storedUserName + ".");
                }
                else System.out.println("There are no rooms booked under the name " + storedUserName + ".");
            }
        }
    }
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
    }

    public static void main(String[] args) {
        rooms.add(new Room(
                "Taff",
                101,
                "Meeting Room",
                2,
                8
        ));
        rooms.add(new Room(
                "Llangorse",
                102,
                "Large meeting room",
                2,
                8
        ));
        rooms.add(new Room(
                "Pen y Fan",
                103,
                "Teaching Space",
                2,
                70
        ));
        rooms.add(new Room(
                "Usk",
                104,
                "Small meeting room",
                3,
                8
        ));
        rooms.add(new Room(
                "Bala",
                105,
                "Large meeting room",
                3,
                24
        ));
        rooms.add(new Room(
                "Cadair Idris",
                106,
                "Large teaching space",
                3,
                70
        ));
        rooms.add(new Room(
                "Wye",
                107,
                "Small meeting room",
                4,
                8
        ));
        rooms.add(new Room(
                "Gower",
                108,
                "Open meeting/ breakout space",
                4,
                70
        ));
        rooms.add(new Room(
                "Snowdon",
                109,
                "Teaching space",
                4,
                70
        ));

        initialiseBookingApplication();
    }
}
