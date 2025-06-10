package carsharing;

import java.util.List;

public interface CustomerDAO {
    List<Customer> findALL();
    void addCustomer(Customer customer);
    void updateRental(Customer customer, Car car);
}
