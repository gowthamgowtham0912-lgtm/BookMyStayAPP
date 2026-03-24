public class BookMyStayApp {

    static class Reservation {
        String guestName;
        String roomType;

        Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String toString() {
            return "Reservation [Guest=" + guestName + ", RoomType=" + roomType + "]";
        }
    }

    public static void main(String[] args) {

        java.util.Queue<Reservation> bookingQueue = new java.util.LinkedList<>();

        bookingQueue.offer(new Reservation("Alice", "Deluxe"));
        bookingQueue.offer(new Reservation("Bob", "Standard"));
        bookingQueue.offer(new Reservation("Charlie", "Suite"));

        System.out.println("Booking requests added to queue.\n");

        System.out.println("Requests in FIFO order:");
        for (Reservation r : bookingQueue) {
            System.out.println(r);
        }

        if (!bookingQueue.isEmpty()) {
            System.out.println("\nNext request to process: " + bookingQueue.peek());
        }
    }
}