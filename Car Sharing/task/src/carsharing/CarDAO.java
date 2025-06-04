package carsharing;

import java.util.List;

public interface CarDAO {
     List<Car> findAll();
     void add(Car car);
     List<Car> findByCompanyID(int companyID);

}
