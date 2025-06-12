package carsharing.model;

public class Customer {
    private final int id;
    private String name;
    private Integer currentRental;

    public Customer(int id, String name) {
        this(id, name, null);
    }

    public Customer(int id, String name, Integer currentRental) {
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

    public Integer getCurrentRentalId() {
        return currentRental;
    }

    public void setCurrentRental(Integer currentRental) {
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
