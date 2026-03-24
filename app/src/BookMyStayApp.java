import java.io.*;
import java.util.*;

public class BookMyStayApp {

    public static void main(String[] args) {

        String bookingFile = "booking_history.dat";
        String inventoryFile = "inventory.dat";

        List<Reservation> bookingHistory = loadBookingHistory(bookingFile);
        Map<String, Integer> inventory = loadInventory(inventoryFile);

        // Initialize if files do not exist
        if (bookingHistory.isEmpty()) {
            bookingHistory.add(new Reservation("Alice", "Deluxe", "D23"));
            bookingHistory.add(new Reservation("Bob", "Standard", "S5"));
            bookingHistory.add(new Reservation("Charlie", "Suite", "S87"));
        }

        if (inventory.isEmpty()) {
            inventory.put("Deluxe", 2);
            inventory.put("Standard", 3);
            inventory.put("Suite", 1);
        }

        System.out.println("=== Current Booking History ===");
        for (Reservation r : bookingHistory) System.out.println(r);

        System.out.println("\n=== Current Inventory ===");
        for (String type : inventory.keySet()) System.out.println(type + " - " + inventory.get(type));

        // Simulate shutdown: persist data
        saveBookingHistory(bookingFile, bookingHistory);
        saveInventory(inventoryFile, inventory);

        System.out.println("\nSystem state persisted. You can restart the application to recover state.");
    }

    static List<Reservation> loadBookingHistory(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Reservation>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    static Map<String, Integer> loadInventory(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Map<String, Integer>) ois.readObject();
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    static void saveBookingHistory(String filename, List<Reservation> history) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(history);
        } catch (IOException e) {
            System.out.println("Failed to save booking history: " + e.getMessage());
        }
    }

    static void saveInventory(String filename, Map<String, Integer> inventory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(inventory);
        } catch (IOException e) {
            System.out.println("Failed to save inventory: " + e.getMessage());
        }
    }
}

class Reservation implements Serializable {
    String guestName;
    String roomType;
    String roomId;

    Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return guestName + " [" + roomType + ", RoomID=" + roomId + "]";
    }
}