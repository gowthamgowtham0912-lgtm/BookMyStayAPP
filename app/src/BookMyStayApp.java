public class BookMyStayApp {

    public static void main(String[] args) {

        java.util.Map<String, Integer> roomInventory = new java.util.HashMap<>();
        roomInventory.put("Deluxe", 2);
        roomInventory.put("Standard", 3);
        roomInventory.put("Suite", 1);

        java.util.List<Reservation> confirmedBookings = new java.util.ArrayList<>();

        Reservation[] requests = {
                new Reservation("Alice", "Deluxe"),
                new Reservation("Bob", "Standard"),
                new Reservation("Charlie", "Penthouse"), // Invalid room type
                new Reservation("David", "Suite"),
                new Reservation("Eve", "Deluxe"),
                new Reservation("Frank", "Deluxe") // Should fail if inventory exhausted
        };

        for (Reservation r : requests) {
            try {
                validateAndAllocate(r, roomInventory);
                confirmedBookings.add(r);
                System.out.println("Booking confirmed: " + r);
            } catch (InvalidBookingException e) {
                System.out.println("Booking failed: " + e.getMessage());
            }
        }

        System.out.println("\n=== Final Confirmed Bookings ===");
        for (Reservation r : confirmedBookings) {
            System.out.println(r);
        }
    }

    static void validateAndAllocate(Reservation r, java.util.Map<String, Integer> inventory) throws InvalidBookingException {
        if (!inventory.containsKey(r.roomType)) {
            throw new InvalidBookingException("Invalid room type: " + r.roomType);
        }
        int available = inventory.get(r.roomType);
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + r.roomType);
        }
        inventory.put(r.roomType, available - 1);
        r.roomId = r.roomType.substring(0,1) + (available); // Simple room ID
    }
}

class Reservation {
    String guestName;
    String roomType;
    String roomId;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return guestName + " [" + roomType + ", RoomID=" + roomId + "]";
    }
}

class InvalidBookingException extends Exception {
    InvalidBookingException(String message) {
        super(message);
    }
}