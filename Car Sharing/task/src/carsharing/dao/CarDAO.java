package carsharing.dao;

import carsharing.model.Car;

import java.util.List;

public interface CarDAO {
     List<Car> findAll();
     void add(Car car);
     List<Car> findByCompanyID(int companyID);
     Car findById(int companyID);
     List<Car> findAllNotRented(int id);

}
