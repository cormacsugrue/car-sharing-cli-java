package carsharing;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.SQLException;
import java.util.List;

public class DBCompanyDAO implements CompanyDAO{
    private final String connectionUrl;
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS company";
    private static final String CREATE_DB = """
                                            CREATE TABLE IF NOT EXISTS COMPANY (
                                                company_id INT PRIMARY KEY AUTO_INCREMENT,
                                                name VARCHAR(50) NOT NULL UNIQUE
                                            )
                                            """;
    private static final String SELECT_ALL = "SELECT * FROM COMPANY";
    private static final String SELECT = "SELECT * FROM COMPANY WHERE company_id = ?";
    private static final String INSERT_DATA = "INSERT INTO COMPANY (name) VALUES (?)";
    private static final String DELETE_DATA = "DELETE FROM COMPANY WHERE company_id = ?";

    private final DBClient dbClient;

    public DBCompanyDAO(String connectionUrl) {
        this.connectionUrl = connectionUrl;
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(connectionUrl);

        this.dbClient = new DBClient(dataSource);
        dbClient.run(DROP_TABLE);
        dbClient.run(CREATE_DB);

    }

    @Override
    public List<Company> findAll() {
        return dbClient.query(
                SELECT_ALL,
                rs -> new Company(
                        rs.getInt("company_id"),
                        rs.getString("name")
                )
        );
    }

    @Override
    public Company findById(int id) {
        return dbClient.queryOne(
                SELECT,
                rs -> new Company(
                        rs.getInt("company_id"),
                        rs.getString("name")
                )
        );
    }

    @Override
    public void add(Company company) {
        dbClient.run(INSERT_DATA,  company.getName());
    }
}
