import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Room {
    private String roomName;
    private String roomType;
    private int floorNumber;
    private int roomCapacity;
    private static int count; //user input number of people
    private static String abc; //user input a b or c
    private static String time;
    private static String userName;
    private static int absoluteMaxCapacityVariable; //of all rooms
    private static int maxCapacityVariable; //of rooms available at chosen time
    private static Boolean roomAvailable;
    private static Scanner scanner = new Scanner(System.in);

    private static List<Room> rooms = new ArrayList<>(){};
    private static List<BookingSlot> slots = new ArrayList<BookingSlot>(){};
    private static List<Integer> roomCapacityArray = new ArrayList<> () {};



    public static void initialiseBookingApplication() {
        System.out.print("Would you like to: \n a) View all rooms \n b) Make a booking \n c) View your bookings \n Please enter a b or c ");
        abc = scanner.nextLine();
        if (abc.equals("a")) {displayAllRooms(); }
        else if (abc.equals("b")) {
//            checkMaxCapacity();
            checkAvailability();
        }
        else if (abc.equals("c")) {}
        else {
            System.out.println("Please enter a, b or c");}
    }
    public static void inputCountVariable(){
        System.out.print("How many people would you like to book for?  ");
        count = Integer.parseInt(scanner.nextLine());
    }
    public static void inputTimeVariable(){
        System.out.print("What time would you like to book the room for? \n Options are: \n a) 10am-11am \n b) 11am-12pm \n c) 12pm-1pm \n d) 1pm-2pm \n e) 2pm-3pm \n f) 3pm-4pm \n g) 5pm-6pm");
        time = (scanner.nextLine());
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

    //this will be the variable of the max people that can be hosted at a stated time
//    public static void createMaxCapacityVariable(){
//        for (Integer eachRoomCapacity: roomCapacityArray){
//            if (eachRoomCapacity > absoluteMaxCapacityVariable) {
//                absoluteMaxCapacityVariable = eachRoomCapacity;
//            }
//        }
//    }
    public static void checkMaxCapacity() {
        inputCountVariable();
        createAbsoluteMaxCapacityVariable();
        if (count > absoluteMaxCapacityVariable) {
            System.out.println("It is not possible to book for more than " + absoluteMaxCapacityVariable + " people.");
        }
        else if (count > absoluteMaxCapacityVariable) { //this will become maxCapacityVariable when booking times have been implemented
            System.out.println("We have rooms that can host this number, but they are not available at this time.");
        }
        else {
            displaySuitableSizeRooms();
            }
        }
    public static void checkAvailability(){
        inputTimeVariable();
    }
    public static void displaySuitableSizeRooms() {
        for (Room room : rooms) {
            if (room.roomCapacity >= count){
                room.displayOneRoom();
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

     public static class BookingSlot {
        private String startTime;
        private String endTime;
        private Boolean available;

        public BookingSlot(
                String startTime,
                String endTime,
                Boolean available
        ) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.available = available;
        }
    }

    public static void main(String[] args) {

        slots.add(new BookingSlot(
                "10am",
                "11am",
                true
        ));
        slots.add(new BookingSlot(
                "11am",
                "12pm",
                true
        ));
        slots.add(new BookingSlot(
                "12pm",
                "1pm",
                true
        ));
        slots.add(new BookingSlot(
                "1pm",
                "2pm",
                true
        ));
        slots.add(new BookingSlot(
                "2pm",
                "3pm",
                true
        ));
        slots.add(new BookingSlot(
                "3pm",
                "4pm",
                true
        ));
        slots.add(new BookingSlot(
                "4pm",
                "5pm",
                true
        ));
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
