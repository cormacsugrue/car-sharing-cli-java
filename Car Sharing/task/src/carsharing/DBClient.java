package carsharing;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBClient {
    private final DataSource dataSource;

    public DBClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void run(String sql, Object... params) {

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing sql statement" + sql + e);
        }
    }


    public <T> List<T> query(String sql, ResultSetMapper<T> mapper, Object... params) {
        List<T> results = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    results.add(mapper.map(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public <T> T queryOne(String sql, ResultSetMapper mapper, Object... params) {

        List<T> results = query(sql, mapper, params);

        if (results.isEmpty()) {
            return null;
        }
        if (results.size() > 1) {
            throw new IllegalStateException("Expected at most one result but returned: " + results.size());
        }
        return results.get(0);

    }
}
