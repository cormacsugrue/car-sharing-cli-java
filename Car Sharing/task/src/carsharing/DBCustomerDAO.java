package carsharing;

import org.h2.jdbcx.JdbcDataSource;

import java.util.List;

public class DBCustomerDAO implements CustomerDAO{

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS customer";
    private static final String CREATE_DB = """
                                            CREATE TABLE IF NOT EXISTS customer (
                                                id INT PRIMARY KEY AUTO_INCREMENT,
                                                name VARCHAR(50) NOT NULL UNIQUE,
                                                rented_car_id INT,
                                                CONSTRAINT fk_rented_car FOREIGN KEY (rented_car_id)
                                                REFERENCES car(id)
                                            )
                                            """;
    private static final String SELECT_ALL = "SELECT * FROM customer";
    private static final String INSERT_DATA = "INSERT INTO customer (name) VALUES (?)";
    private static final String UPDATE_RENTAL = """
                                                UPDATE customer
                                                SET rented_car_id = ?
                                                WHERE id = ?
                                                """;
    private static final String SELECT_BY_ID = """
                                                SELECT * FROM customer
                                                WHERE id = ?
                                                """;


    private final DBClient dbClient;

    public DBCustomerDAO(String connectionUrl) {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(connectionUrl);
        this.dbClient = new DBClient(dataSource);
//        dbClient.run(DROP_TABLE);
        dbClient.run(CREATE_DB);
    }

    @Override
    public List<Customer> findALL() {
        return dbClient.query(
                SELECT_ALL,
                rs -> new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getObject("rented_car_id", Integer.class)
                )
        );
    }

    @Override
    public Customer findById(int id) {
        return dbClient.queryOne(
                SELECT_BY_ID,
                rs -> new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getObject("rented_car_id", Integer.class)
                ),
                id
        );
    }


    @Override
    public void addCustomer(Customer customer) {
        dbClient.run(INSERT_DATA, customer.getName());
    }

    @Override
    public void updateRental(Customer customer, Integer carId) {
        dbClient.run(UPDATE_RENTAL, carId, customer.getId());
    }
}
