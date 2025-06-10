package carsharing;

public class Customer {
    private final int id;
    private String name;
    private int currentRental;

    public Customer(int id, String name, int currentRental) {
        this.id = id;
        this.name = name;
        this.currentRental = currentRental;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentRental() {
        return currentRental;
    }

    public void setCurrentRental(int currentRental) {
        this.currentRental = currentRental;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append(". ").append(name);
        return sb.toString();
    }
}
