import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Room {
    private String roomName;
    private String roomType;
    private int floorNumber;
    private int roomCapacity;
    private static int count;
    private static int absoluteMaxCapacityVariable;
    private int maxCapacityVariable;

    private static List<Room> rooms = new ArrayList<>(){};
    private static List<Integer> roomCapacityArray = new ArrayList<> () {};

    public static void getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many people would you like to book for?  ");
        count = Integer.parseInt(scanner.nextLine());
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
        createAbsoluteMaxCapacityVariable();
        if (count > absoluteMaxCapacityVariable) {
            System.out.println("It is not possible to book for more than " + absoluteMaxCapacityVariable + " people.");
            // get user to re-enter the value
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

        getUserInput();
        checkAbsoluteMaxCapacity();
    }
}
