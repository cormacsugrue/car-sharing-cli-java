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

    public void run(String sql, Object... Params) {

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            for (int i = 0; i < Params.length; i++) {
                statement.setObject(i + 1, Params[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("A company with that name already exists. Please provide a different name", e);
        }
    }

    public Company select(String sql, Object... params) {

        Company result = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ){
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    result = new Company(id, name);

                    if(resultSet.next()) {
                        throw new IllegalStateException("Query returned more than one object");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Company> selectForList(String sql, Object... params) {

        List<Company> companies = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
        ){
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    Company nextCompany = new Company(resultSet.getInt("id"), resultSet.getString("name"));
                    companies.add(nextCompany);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }
}
