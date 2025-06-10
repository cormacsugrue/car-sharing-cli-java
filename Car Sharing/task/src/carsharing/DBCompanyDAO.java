package carsharing;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.SQLException;
import java.util.List;

public class DBCompanyDAO implements CompanyDAO{
    private final String connectionUrl;
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS company CASCADE";
    private static final String CREATE_DB = """
                                            CREATE TABLE IF NOT EXISTS COMPANY (
                                                id INT PRIMARY KEY AUTO_INCREMENT,
                                                name VARCHAR(50) NOT NULL UNIQUE
                                            )
                                            """;
    private static final String SELECT_ALL = "SELECT * FROM COMPANY";
    private static final String SELECT = "SELECT * FROM COMPANY WHERE id = ?";
    private static final String INSERT_DATA = "INSERT INTO COMPANY (name) VALUES (?)";
    private static final String DELETE_DATA = "DELETE FROM COMPANY WHERE id = ?";

    private final DBClient dbClient;

    public DBCompanyDAO(String connectionUrl) {
        this.connectionUrl = connectionUrl;
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(connectionUrl);

        this.dbClient = new DBClient(dataSource);
//        dbClient.run(DROP_TABLE);
        dbClient.run(CREATE_DB);

    }

    @Override
    public List<Company> findAll() {
        return dbClient.query(
                SELECT_ALL,
                rs -> new Company(
                        rs.getInt("id"),
                        rs.getString("name")
                )
        );
    }

    @Override
    public Company findById(int id) {
        return dbClient.queryOne(
                SELECT,
                rs -> new Company(
                        rs.getInt("id"),
                        rs.getString("name")
                ),
                id
        );
    }

    @Override
    public void add(Company company) {
        dbClient.run(INSERT_DATA,  company.getName());
    }
}
