package carsharing.dao.impl;

import carsharing.util.DBClient;
import carsharing.dao.CarDAO;
import carsharing.model.Car;
import org.h2.jdbcx.JdbcDataSource;

import java.util.List;

public class DBCarDAO implements CarDAO {

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS CAR cascade";
    private static final String CREATE_DB = """
                                            CREATE TABLE IF NOT EXISTS car (
                                                id INT PRIMARY KEY AUTO_INCREMENT,
                                                name VARCHAR(50) NOT NULL UNIQUE,
                                                company_id INT NOT NULL,
                                                CONSTRAINT fk_company FOREIGN KEY (company_id)
                                                REFERENCES company(id)
                                            )
                                            """;
    private static final String SELECT_ALL = "SELECT * FROM CAR";
    private static final String SELECT_BY_COMPANY = "SELECT * FROM CAR WHERE company_id = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM CAR WHERE id = ?";
    private static final String INSERT_DATA = "INSERT INTO CAR (name, company_id) VALUES (?,?)";
    private static final String DELETE_DATA = "DELETE FROM COMPANY WHERE id = ?";
    private static final String SELECT_ALL_NOT_RENTED = """
                                            SElECT car.* 
                                            FROM car
                                            LEFT JOIN customer ON car.id = customer.rented_car_id
                                            WHERE customer.rented_car_id IS NULL
                                            AND car.company_id = ?
                                            """;

    private final DBClient dbClient;

    public DBCarDAO(String connectionUrl) {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(connectionUrl);

        this.dbClient = new DBClient(dataSource);
//        dbClient.run(DROP_TABLE);
        dbClient.run(CREATE_DB);
    }

    @Override
    public List<Car> findAll() {
        return dbClient.query(
                SELECT_ALL,
                rs -> new Car(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("company_id")
                )
        );
    }


    @Override
    public void add(Car car) {
        dbClient.run(INSERT_DATA, car.getName(), car.getCompanyID());
    }

    @Override
    public List<Car> findByCompanyID(int companyID) {
        return dbClient.query(
                SELECT_BY_COMPANY,
                rs -> new Car(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("company_id")
                ),
                companyID
        );
    }

    @Override
    public Car findById(int id) {
        return dbClient.queryOne(
                SELECT_BY_ID,
                rs -> new Car(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("company_id")
                ),
                id
        );
    }

    @Override
    public List<Car> findAllNotRented(int id) {
        return dbClient.query(
                SELECT_ALL_NOT_RENTED,
                rs -> new Car(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("company_id")
                ),
                id
        );
    }
}
