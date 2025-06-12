package carsharing.dao;

import carsharing.model.Customer;

import java.util.List;

public interface CustomerDAO {
    List<Customer> findALL();
    Customer findById(int id);
    void addCustomer(Customer customer);
    void updateRental(Customer customer, Integer carId);
}
