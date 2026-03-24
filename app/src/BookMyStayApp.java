public class BookMyStayApp {

    public static void main(String[] args) {

        java.util.Queue<Reservation> bookingQueue = new java.util.LinkedList<>();
        bookingQueue.offer(new Reservation("Alice", "Deluxe"));
        bookingQueue.offer(new Reservation("Bob", "Standard"));
        bookingQueue.offer(new Reservation("Charlie", "Suite"));
        bookingQueue.offer(new Reservation("David", "Deluxe"));
        bookingQueue.offer(new Reservation("Eve", "Standard"));

        InventoryService inventory = new InventoryService();

        while (!bookingQueue.isEmpty()) {
            Reservation res = bookingQueue.poll();
            boolean success = inventory.allocateRoom(res);
            if (success) {
                System.out.println("Reservation confirmed: " + res);
            } else {
                System.out.println("Reservation failed (no rooms available) for: " + res.guestName + " [" + res.roomType + "]");
            }
        }

        inventory.printInventoryStatus();
    }
}

// Reservation class
class Reservation {
    String guestName;
    String roomType;
    String assignedRoomId;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    void assignRoom(String roomId) {
        this.assignedRoomId = roomId;
    }

    @Override
    public String toString() {
        return "Reservation [Guest=" + guestName + ", RoomType=" + roomType + ", RoomID=" + assignedRoomId + "]";
    }
}

// InventoryService class
class InventoryService {
    java.util.Map<String, Integer> roomInventory = new java.util.HashMap<>();
    java.util.Map<String, java.util.Set<String>> allocatedRooms = new java.util.HashMap<>();
    java.util.Random random = new java.util.Random();

    InventoryService() {
        roomInventory.put("Deluxe", 2);
        roomInventory.put("Standard", 3);
        roomInventory.put("Suite", 1);

        allocatedRooms.put("Deluxe", new java.util.HashSet<>());
        allocatedRooms.put("Standard", new java.util.HashSet<>());
        allocatedRooms.put("Suite", new java.util.HashSet<>());
    }

    boolean allocateRoom(Reservation res) {
        String type = res.roomType;
        int available = roomInventory.getOrDefault(type, 0);
        if (available <= 0) return false;

        java.util.Set<String> assignedSet = allocatedRooms.get(type);
        String roomId;
        do {
            roomId = type.substring(0, 1) + (random.nextInt(100) + 1);
        } while (assignedSet.contains(roomId));

        res.assignRoom(roomId);
        assignedSet.add(roomId);
        roomInventory.put(type, available - 1);
        return true;
    }

    void printInventoryStatus() {
        System.out.println("\nCurrent Inventory Status:");
        for (String type : roomInventory.keySet()) {
            System.out.println(type + " - Available: " + roomInventory.get(type)
                    + ", Allocated: " + allocatedRooms.get(type));
        }
    }
}