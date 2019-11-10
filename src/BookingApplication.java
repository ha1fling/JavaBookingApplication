import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingApplication {
    public static List<Room> getSuitableRooms() {
        return suitableRooms;
    }

    public static int getCount() {
        return count;
    }

    public static String getStoredUserName() {
        return storedUserName;
    }

    public static LocalDateTime getStoredDateTime() {
        return storedDateTime;
    }

    private static List<Room> suitableRooms = new ArrayList<>(){};
    private static String input;
    private static int count;
    private static String storedUserName;
    private static int requestedRoomCode;
    private static int requestedRoomIndex;
    private static int maxCapacityVariable;
    private static Scanner scanner = new Scanner(System.in);
    private static List<Room> rooms = new ArrayList<>(){};
    private static List<Integer> roomCapacityArray = new ArrayList<> () {};
    private static LocalDateTime storedDateTime;
    private static String outputDateTime;
    private static Boolean correctFormat = false;
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH");
    private static boolean noBookings = true;

    public static void populateRoomsArray(){
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
    }
    public static void initialiseBookingApplication() {

        System.out.print("Would you like to: \n a) View all rooms \n b) Make a booking \n c) View your bookings \n d) Change username \n Please enter a b c or d");
        userInput();
        switch (input.toLowerCase()) {
            case "a":
                displayAllRooms();
                toMainMenu();
            case "b":
                inputDateTimeVariable();
                inputCountVariable();
                displaySuitableRooms();
                bookRoom();
                toMainMenu();
            case "c":
                displayBookingsPerUser();
                toMainMenu();
            case "d":
                inputUserNameVariable();
            default:
                initialiseBookingApplication();
        }
    }
    public static void userInput() {
        input = scanner.nextLine().trim(); // input variable to be parsed to different types for each use
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

    public static void createRoomCapacityArray(){
        roomCapacityArray.clear();
        for (Room room: rooms){
            roomCapacityArray.add(room.getRoomCapacity());
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
    public static void displaySuitableRooms() {
        checkMaxCapacity();
        suitableRooms.clear();
        for (Room room : rooms) {
            room.checkSuitability();
        }
        if (suitableRooms.size() == 1) {
            System.out.println("There are no rooms available for your party size and time" );
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
        rooms.get(requestedRoomIndex).checkSuitability();
        if (suitableRooms.size() == 1) {
            rooms.get(requestedRoomIndex).addBookingToBookingsArray();
            System.out.println("You have booked room " + rooms.get(requestedRoomIndex).getRoomName() + " for " + outputDateTime + " o'clock, under the name " + storedUserName + ".");
        } else System.out.println("This room is not available please enter another room code");
        correctFormat = false;
    }
    public static void displayBookingsPerUser(){
        for (Room room: rooms) {
            if (room.getBookings().size() == 0) {
                noBookings = true;
            }
            for (Booking booking : room.getBookings()) {
                if (booking.getBookingUserName().equals(storedUserName)) {
                    System.out.println("You have booked room " + booking.getBookingRoomName() + " for " + booking.getBookingStartTime().format(dateTimeFormatter) + " o'clock, under the name " + booking.getBookingUserName() + ".");
                    noBookings = false;
                }
            }
        }
        if (noBookings == true) {
            System.out.println("There are no rooms booked under the name " + storedUserName + ".");
        }
    }
    public static void main(String[] args) {
        try {
            populateRoomsArray();
            inputUserNameVariable();
            initialiseBookingApplication();
        }
        catch(Exception e) {
            System.out.println("The application has run into an unexpected error.");
            toMainMenu();
        }
    }
}