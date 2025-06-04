package carsharing;

public class Car {
    private final int id;
    private String name;
    private int companyID;

    public Car(int id, String name, int companyID) {
        this.id = id;
        this.name = name;
        this.companyID = companyID;
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

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(id).append(". ");
        sb.append(name);
        return sb.toString();
    }
}
