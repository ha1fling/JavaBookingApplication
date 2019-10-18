public class Room {
    private String roomName;
    private String roomType;
    private int floorNumber;
    private int roomCapacity;
    private int count;

    public Room (
            String roomName,
            String roomType,
            int floorNumber,
            int roomCapacity)

    public static void main(String[] args) {

        room1.displayAllRooms();

        Room room1 = new Room(
                "Taff",
                "Meeting Room",
                2,
                8
        );
        Room room2 = new Room(
                "Llangorse",
                "Large meeting room",
                2,
                8
        );
        Room room3 = new Room(
                "Pen y Fan",
                "Teaching Space",
                2,
                70
        );
        Room room4 = new Room(
                "Usk",
                "Small meeting room",
                3,
                8
        );
        Room room5 = new Room(
                "Bala",
                "Large meeting room",
                3,
                24
        );
        Room room6 = new Room(
                "Cadair Idris",
                "Large teaching space",
                3,
                70
        );
        Room room7 = new Room(
                "Wye",
                "Small meeting room",
                4,
                8
        );
        Room room8 = new Room(
                "Gower",
                "Open meeting/ breakout space",
                4,
                70
        );
        Room room9 = new Room(
                "Snowdon",
                "Teaching space",
                4,
                70
        );
    }
}
