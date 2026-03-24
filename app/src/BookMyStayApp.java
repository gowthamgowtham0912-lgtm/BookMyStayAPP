public class BookMyStayApp {

    public static void main(String[] args) {

        java.util.List<Reservation> bookingHistory = new java.util.ArrayList<>();

        // Simulate confirmed bookings
        bookingHistory.add(new Reservation("Alice", "Deluxe", "D23"));
        bookingHistory.add(new Reservation("Bob", "Standard", "S5"));
        bookingHistory.add(new Reservation("Charlie", "Suite", "S87"));
        bookingHistory.add(new Reservation("David", "Deluxe", "D57"));
        bookingHistory.add(new Reservation("Eve", "Standard", "S16"));

        // Display all booking history
        System.out.println("=== Booking History ===");
        for (Reservation r : bookingHistory) {
            System.out.println(r);
        }

        // Generate summary report
        java.util.Map<String, Integer> summary = new java.util.HashMap<>();
        for (Reservation r : bookingHistory) {
            summary.put(r.roomType, summary.getOrDefault(r.roomType, 0) + 1);
        }

        System.out.println("\n=== Booking Summary Report ===");
        for (String type : summary.keySet()) {
            System.out.println(type + " rooms booked: " + summary.get(type));
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