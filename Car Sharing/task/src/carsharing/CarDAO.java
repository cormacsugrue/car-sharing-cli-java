package carsharing;

import java.util.List;

public interface CarDAO {
     List<Car> findAll();
     Car findByID(int id);
     void add(Car car);

}
