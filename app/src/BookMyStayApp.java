public class BookMyStayApp {

    public static void main(String[] args) {

        java.util.Map<String, Integer> roomInventory = new java.util.HashMap<>();
        roomInventory.put("Deluxe", 2);
        roomInventory.put("Standard", 3);
        roomInventory.put("Suite", 1);

        java.util.List<Reservation> bookingHistory = new java.util.ArrayList<>();
        java.util.Stack<String> releasedRoomIds = new java.util.Stack<>();

        // Simulate confirmed bookings
        bookingHistory.add(new Reservation("Alice", "Deluxe", "D23"));
        bookingHistory.add(new Reservation("Bob", "Standard", "S5"));
        bookingHistory.add(new Reservation("Charlie", "Suite", "S87"));

        System.out.println("=== Initial Bookings ===");
        printBookings(bookingHistory);

        // Cancellation requests
        String[] cancellations = {"Bob", "Charlie", "Eve"}; // Eve does not exist

        for (String guestName : cancellations) {
            try {
                cancelBooking(guestName, bookingHistory, roomInventory, releasedRoomIds);
                System.out.println("Cancellation successful for: " + guestName);
            } catch (InvalidCancellationException e) {
                System.out.println("Cancellation failed for " + guestName + ": " + e.getMessage());
            }
        }

        System.out.println("\n=== Final Booking History ===");
        printBookings(bookingHistory);

        System.out.println("\n=== Current Inventory ===");
        for (String type : roomInventory.keySet()) {
            System.out.println(type + " - Available: " + roomInventory.get(type));
        }

        System.out.println("\n=== Released Room IDs Stack ===");
        System.out.println(releasedRoomIds);
    }

    static void cancelBooking(String guestName, java.util.List<Reservation> history,
                              java.util.Map<String, Integer> inventory, java.util.Stack<String> rollbackStack)
            throws InvalidCancellationException {

        Reservation toCancel = null;
        for (Reservation r : history) {
            if (r.guestName.equals(guestName)) {
                toCancel = r;
                break;
            }
        }

        if (toCancel == null) {
            throw new InvalidCancellationException("Reservation not found or already cancelled.");
        }

        // Restore inventory
        inventory.put(toCancel.roomType, inventory.get(toCancel.roomType) + 1);
        rollbackStack.push(toCancel.roomId);

        // Remove from booking history
        history.remove(toCancel);
    }

    static void printBookings(java.util.List<Reservation> history) {
        if (history.isEmpty()) {
            System.out.println("No bookings.");
        } else {
            for (Reservation r : history) {
                System.out.println(r);
            }
        }
    }
}

class Reservation {
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

class InvalidCancellationException extends Exception {
    InvalidCancellationException(String message) {
        super(message);
    }
}