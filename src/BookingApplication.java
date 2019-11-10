import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingApplication {

    //My personal design choice would have all the variables above the methods, however for easy explanation and
    //commenting, I have added them in within the methods above where they are first used

    //Create array list named rooms
    private static List<Room> rooms = new ArrayList<>(){};
    //Add objects to rooms array list for each room
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

    //Declare variable input of type string
    private static String input;
    //Create scanner to take input from user
    private static Scanner scanner = new Scanner(System.in);
    //Use scanner to get input from user and remove any spaces at the beginning and end to reduce input errors
    //This input is a String, but will be parsed to the data type required for each scenario
    public static void userInput() {
        input = scanner.nextLine().trim();
    }

    //This method gives the user a menu of functions that can be carried out by the booking application
    public static void initialiseBookingApplication() {
        System.out.print("Would you like to: \n a) View all rooms \n b) Make a booking \n c) View your bookings \n d) Change username \n Please enter a b c or d");
        userInput();
        switch (input.toLowerCase()) {
            //the input is parsed to lower case so that whether the user inputs a or A it will select the first case
            case "a":
                displayAllRooms();
                toMainMenu();
            case "b":
                inputDateTimeVariable();
                inputCountVariable();
                createRoomCapacityArray();
                createMaxCapacityVariable();
                checkMaxCapacity();
                displaySuitableRooms();
                bookRoom();
                toMainMenu();
            case "c":
                displayBookingsPerUser();
                toMainMenu();
            case "d":
                inputUserNameVariable();
            default:
                //Catch errors:
                initialiseBookingApplication();
        }
    }

    //This method allows the user to return to the function menu, or to terminate the program
    public static void toMainMenu() {
        System.out.println("Would you like to return to the main menu?");
        userInput();
        switch (input.toLowerCase().substring(0, 1)) {
            case "y":
                initialiseBookingApplication();
            case "n":
                System.exit(0);
            default:
                //Catch errors:
                System.out.println("Please enter yes/y or no/n.");
                toMainMenu();
        }
    }

    private static String storedUserName;
    //This method takes in a string input and saves it as the username.
    public static void inputUserNameVariable(){
        System.out.println("Please choose a username (if you enter over 20 chars your username will be cropped to 20): ");
        userInput();
        //This if statement takes the input and checks if it is longer than 20 chars and if it is above 20, trims to 20
        if (input.length() > 20){
            storedUserName = (input.substring(0, 19));
        }
        else {
            storedUserName = input;
        }
    }

    private static LocalDateTime storedDateTime;
    private static String outputDateTime;

    //This checks that the input has been entered in the correct format using a try catch loop that runs while this
    //bool is false
    //the bool is changed to true if the method works
    private static Boolean correctFormat = false;

    //Formats a Local Date Time object in the pattern denoted:
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH");

    //This method takes in the time and date that the user wants to book for in British format as a string and saves it
    //as a LocalDateTime object.
    //It currently only checks the input format is correct by catching any errors
    //For a real-life booking application, this could check the date and time entered against opening hours, weekends
    //and bank holidays and also in a world without time travel it should probably only work for bookings in the future
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

    private static int count;
    //This method allows the user to input how many people they would like to book a room for
    public static void inputCountVariable(){
        while (correctFormat == false) {
            try {
                System.out.print("How many people would you like to book for?  ");
                userInput();
                count = Integer.parseInt(input); //input needs to be an integer and not a string
                correctFormat = true; //this will end the while loop as correctFormat is not false anymore
            }
            catch(Exception e){
                System.out.println("Please enter a whole number");
            }
        }
        correctFormat = false; //reset the variable so it can be reused
    }

    private static int requestedRoomIndex;
    private static int requestedRoomCode;
    //Room codes run from 101-109 which on an array are indices 0-8 so to get from room code to room index you minus 101
    //Choices for the room code to start at 101 are arbitrary and are a personal preference (101-109 looks better than
    // 0-8 or 1-9.
    public static void createRequestedRoomIndexVariable(){
        userInput();
        requestedRoomCode = Integer.parseInt(input);
        requestedRoomIndex = requestedRoomCode-101;
    }

    //This method loops through each Room object in the rooms array and calls displayOneRoom method so that the details
    //for each room is printed out
    public static void displayAllRooms() {
        for (Room room: rooms) {
            room.displayOneRoom();
        }
    }

    //Initialise an empty array list called roomCapacityArray
    private static List<Integer> roomCapacityArray = new ArrayList<> () {};
    //This method first empties the roomCapacityArray and then populates it by looping through room objects in the
    //room array and adding the room capacity of each room to the array
    public static void createRoomCapacityArray(){
        roomCapacityArray.clear();
        for (Room room: rooms){
            roomCapacityArray.add(room.getRoomCapacity());
        }
    }

    private static int maxCapacityVariable;
    //this method follows on from the previous by looping through the roomCapacityArray to find the largest integer
    //which will in turn be the largest number of people any of the rooms can hold
    //using this method rather than setting the maxCapacityVariable to the integer 70, means that maxCapacityVariable
    //will change should more rooms be added in the future without the variable needing to be manually re-entered
    public static void createMaxCapacityVariable(){
        for (Integer eachRoomCapacity: roomCapacityArray){
            if (eachRoomCapacity > maxCapacityVariable) {
                maxCapacityVariable = eachRoomCapacity;
            }
        }
    }

    //This method checks that the user isn't trying to book for more people than any of the rooms can hold.
    public static void checkMaxCapacity() {
        if (count > maxCapacityVariable) {
            System.out.println("It is not possible to book for more than " + maxCapacityVariable + " people.");
            inputCountVariable();
        }
    }

    private static List<Room> suitableRooms = new ArrayList<>(){};
    //This method loops through room objects in the room array and runs the checkSuitability method in the room class
    //against each instance to check if the room is free and also if it can accommodate the number of guests
    //in the checkSuitability method each room that is suitable is added to the suitableRoom array, so that if the array
    //empty (it is cleared each time this method is run to ensure this works) then it means that there are no suitable
    //rooms for the user
    public static void displaySuitableRooms() {
        suitableRooms.clear();
        for (Room room : rooms) {
            room.checkSuitability();
        }
        if (suitableRooms.size() == 0) {
            System.out.println("There are no rooms available for your party size and time" );
            toMainMenu();
        }
    }

    //This method allows the user to book a room by entering the room code of their chosen room, when they enter the
    //room code, even though it wouldn't have been displayed if it wasn't suitable, this method checks whether the room
    //is suitable again before booking the room for the user.
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
        suitableRooms.clear(); //reset size of suitableRooms to zero
        rooms.get(requestedRoomIndex).checkSuitability();
        if (suitableRooms.size() == 1) {
            rooms.get(requestedRoomIndex).addBookingToBookingsArray();
            System.out.println("You have booked room " + rooms.get(requestedRoomIndex).getRoomName() + " for " + outputDateTime + " o'clock, under the name " + storedUserName + ".");
        } else System.out.println("This room is not available please enter another room code");
        correctFormat = false;
    }

    //The user intitially has no bookings according to the computer
    private static boolean noBookings = true;
    //This method checks if the user has any bookings and changes noBookings to false if the user has booked any rooms
    //The method loops through the room objects in the rooms array, and then loops through the bookings array for each
    //room to check if the username entered matches the username saved on any bookings, if it does it shows the user
    //what bookings they have made
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

    //Main method with a try catch to avoid failure of the program. If it fails, the error is caught and the user is
    //redirected to the functions/ options menu
    public static void main(String[] args) {
        try {
            populateRoomsArray(); // create room objects for rooms 101-109
            inputUserNameVariable();  //username is entered once and then can be changed as option (d) in the menu
            initialiseBookingApplication();
        }
        catch(Exception e) {
            System.out.println("The application has run into an unexpected error.");
            toMainMenu();
        }
    }


    //Getters
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
}