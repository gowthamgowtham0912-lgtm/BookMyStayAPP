public class BookMyStayApp {

    public static void main(String[] args) {

        java.util.Map<String, Integer> inventory = new java.util.HashMap<>();
        inventory.put("Deluxe", 2);
        inventory.put("Standard", 3);
        inventory.put("Suite", 1);

        java.util.Queue<Reservation> bookingQueue = new java.util.LinkedList<>();
        bookingQueue.offer(new Reservation("Alice", "Deluxe"));
        bookingQueue.offer(new Reservation("Bob", "Standard"));
        bookingQueue.offer(new Reservation("Charlie", "Suite"));
        bookingQueue.offer(new Reservation("David", "Deluxe"));
        bookingQueue.offer(new Reservation("Eve", "Standard"));

        java.util.List<Reservation> confirmedBookings = new java.util.ArrayList<>();

        class BookingThread extends Thread {
            public void run() {
                while (true) {
                    Reservation res;
                    synchronized (bookingQueue) {
                        if (bookingQueue.isEmpty()) return;
                        res = bookingQueue.poll();
                    }

                    boolean success = allocateRoom(res, inventory, confirmedBookings);
                    if (success) {
                        System.out.println("Booking confirmed: " + res);
                    } else {
                        System.out.println("Booking failed (no rooms): " + res.guestName + " [" + res.roomType + "]");
                    }
                }
            }
        }

        // Simulate multiple threads
        Thread t1 = new BookingThread();
        Thread t2 = new BookingThread();
        Thread t3 = new BookingThread();

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== Final Confirmed Bookings ===");
        synchronized (confirmedBookings) {
            for (Reservation r : confirmedBookings) {
                System.out.println(r);
            }
        }

        System.out.println("\n=== Final Inventory ===");
        synchronized (inventory) {
            for (String type : inventory.keySet()) {
                System.out.println(type + " - Available: " + inventory.get(type));
            }
        }
    }

    static boolean allocateRoom(Reservation r, java.util.Map<String, Integer> inventory,
                                java.util.List<Reservation> confirmedBookings) {
        synchronized (inventory) {
            int available = inventory.getOrDefault(r.roomType, 0);
            if (available <= 0) return false;
            inventory.put(r.roomType, available - 1);
        }

        synchronized (confirmedBookings) {
            r.roomId = r.roomType.substring(0, 1) + (int)(Math.random() * 100);
            confirmedBookings.add(r);
        }
        return true;
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