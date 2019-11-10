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
    private static String input;
    private static int count; //user input number of people
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
    public static Boolean correctFormat = false;
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH");
    public static Boolean userBookings = false;
    public static boolean noBookings = true;

    public static void userInput() {
        input = scanner.nextLine().trim(); // input variable to be parsed to different types for each use
    }
    
    public static void initialiseBookingApplication() {
        System.out.print("Would you like to: \n a) View all rooms \n b) Make a booking \n c) View your bookings \n d) Change username \n Please enter a b c or d");
        userInput();
        switch (input.toLowerCase()) {
            case "a":
                displayAllRooms();
                toMainMenu();
            case "b":
                inputUserNameVariable();
                inputDateTimeVariable();
                inputCountVariable();
                displaySuitableRooms();
                bookRoom();
                toMainMenu();
            case "c":
                inputUserNameVariable();
                displayBookingsPerUser();
                toMainMenu();
            case "d":
                inputUserNameVariable();
            default:
                initialiseBookingApplication();
                }
        }

    public static void toMainMenu() {
        System.out.println("Would you like to return to the main menu?");
        userInput();
        switch (input.toLowerCase().substring(0, 1)) {
            case "y":
                initialiseBookingApplication();
            case "n":
                System.exit(0);
        }
    }

    public static void inputUserNameVariable(){
        //username can be any string between 1 and 20 characters
        System.out.println("Input username up to 10 characters (if you enter over 20 your username will be cropped to 20): ");
        userInput();
        if (input.length() > 20){
            storedUserName = (input.substring(0, 19));
        }
        else {
            storedUserName = input;
        }
    }
    public static void inputDateTimeVariable() {
        while (correctFormat == false){
            try {
                System.out.print("Enter date and time in the following format: \n where HH is the start time you want to book for in 24hr format \n dd/MM/yyyy HH ");
                userInput();
                storedDateTime = LocalDateTime.parse(input, dateTimeFormatter);
                correctFormat = true;
            }
            catch(Exception e){
                System.out.println("Please ensure that you enter your date and time in the format dd/MM/yyyy HH");
            }
        }
        outputDateTime = storedDateTime.format(dateTimeFormatter);
        correctFormat = false; //reset the variable so it can be reused
    }
    public static void inputCountVariable(){
        while (correctFormat == false) {
            try {
                System.out.print("How many people would you like to book for?  ");
                count = Integer.parseInt(scanner.nextLine());
                correctFormat = true;
            }
            catch(Exception e){
                System.out.println("Please enter a whole number");
            }
        }
        correctFormat = false;
    }
    public static void createRequestedRoomIndexVariable(){
        userInput();
        requestedRoomCode = Integer.parseInt(input);
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
    }
    public static void checkOpeningHours(){
        //this method will check that the date and time entered are not a Saturday or a Sunday and are not outside of the hours 10-4
        //this could be taken further to incorporate bank holidays and term times.
    }
    public static void displaySuitableRooms() {
        checkMaxCapacity();
        suitableRooms.clear();
        for (Room room : rooms) {
            checkSuitability(room);
        }
        if (suitableRooms.size() == 1) {
            System.out.println("There are no rooms available for your party size and time" );
        }
    }
    public static void checkSuitability(Room room) {
        if (room.roomCapacity >= count && room.bookings.size() == 0 ) { //
            room.displayOneRoom(); // and add room to suitableRooms
            suitableRooms.add(room); // and add room to suitableRooms
        }
        else if (room.roomCapacity >= count && room.bookings.size() > 0) {
            for(Booking booking : room.bookings) {
                if (!booking.bookingStartTime.equals(storedDateTime)) {
                    room.displayOneRoom();
                    suitableRooms.add(room); // and add room to suitableRooms
                }
            }
        }
    }

    public static void bookRoom() {
        while (correctFormat == false) {
            try {
                System.out.println("Please enter a valid room code");
                createRequestedRoomIndexVariable();
                if (requestedRoomIndex < rooms.size()) {
                    correctFormat = true;
                }
            }
            catch(Exception e){
                System.out.println("Please enter a valid room code");
            }
        }
        suitableRooms.clear();
        checkSuitability(rooms.get(requestedRoomIndex));
        if (suitableRooms.size() == 1) {
            rooms.get(requestedRoomIndex).addBookingToBookingsArray();
            System.out.println("You have booked room " + rooms.get(requestedRoomIndex).roomName + " for " + outputDateTime + " o'clock, under the name " + storedUserName + ".");
        } else System.out.println("This room is not available please enter another room code");
        correctFormat = false;
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
        for (Room room: rooms) {
            if (room.bookings.size() == 0) {
                noBookings = true;
            }
            for (Booking booking : room.bookings) {
                if (booking.bookingUserName.equals(storedUserName)) {
                    System.out.println("You have booked room " + booking.bookingRoomName + " for " + booking.bookingStartTime.format(dateTimeFormatter) + " o'clock, under the name " + booking.bookingUserName + ".");
                    noBookings = false;
                }
            }
        }
        if (noBookings == true) {
            System.out.println("There are no rooms booked under the name " + storedUserName + ".");
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
