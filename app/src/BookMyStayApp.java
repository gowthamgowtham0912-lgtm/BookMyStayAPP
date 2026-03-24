public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        System.out.println("===== Book My Stay App v3.1 =====");

        inventory.displayInventory();

        System.out.println();
        System.out.println("Updating availability...");

        inventory.updateRoom("Single Room", 4);
        inventory.updateRoom("Double Room", 2);

        System.out.println();
        inventory.displayInventory();
    }
}

class RoomInventory {

    java.util.HashMap<String, Integer> rooms;

    RoomInventory() {
        rooms = new java.util.HashMap<>();

        rooms.put("Single Room", 5);
        rooms.put("Double Room", 3);
        rooms.put("Suite Room", 2);
    }

    int getAvailability(String type) {
        return rooms.getOrDefault(type, 0);
    }

    void updateRoom(String type, int count) {
        rooms.put(type, count);
    }

    void displayInventory() {
        for (String type : rooms.keySet()) {
            System.out.println(type + " Available: " + rooms.get(type));
        }
    }
}

