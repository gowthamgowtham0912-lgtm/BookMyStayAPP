public class BookMyStayApp {

    public static void main(String[] args) {

        java.util.Map<String, java.util.List<Service>> reservationServices = new java.util.HashMap<>();

        java.util.List<Reservation> reservations = new java.util.ArrayList<>();
        reservations.add(new Reservation("Alice", "Deluxe", "D23"));
        reservations.add(new Reservation("Bob", "Standard", "S5"));
        reservations.add(new Reservation("Charlie", "Suite", "S87"));

        Service breakfast = new Service("Breakfast", 20.0);
        Service spa = new Service("Spa Access", 50.0);
        Service airportPickup = new Service("Airport Pickup", 30.0);

        // Attach services to reservations
        addServiceToReservation(reservationServices, reservations.get(0), breakfast);
        addServiceToReservation(reservationServices, reservations.get(0), spa);
        addServiceToReservation(reservationServices, reservations.get(1), airportPickup);
        addServiceToReservation(reservationServices, reservations.get(2), spa);

        // Display reservations with selected services
        for (Reservation r : reservations) {
            System.out.println("Reservation: " + r);
            java.util.List<Service> services = reservationServices.getOrDefault(r.roomId, new java.util.ArrayList<>());
            double totalCost = 0;
            System.out.println("  Selected Services:");
            for (Service s : services) {
                System.out.println("    - " + s.name + " ($" + s.cost + ")");
                totalCost += s.cost;
            }
            System.out.println("  Total Add-On Cost: $" + totalCost + "\n");
        }
    }

    static void addServiceToReservation(java.util.Map<String, java.util.List<Service>> map, Reservation r, Service s) {
        map.computeIfAbsent(r.roomId, k -> new java.util.ArrayList<>()).add(s);
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

class Service {
    String name;
    double cost;

    Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }
}