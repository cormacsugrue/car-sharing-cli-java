package carsharing;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Main {

    private static final String DB_URL_PREFIX = "jdbc:h2:./src/carsharing/db/";

    public static void main(String[] args) {

        String dbFilename = "carsharing";

        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("-databaseFileName") && !args[i + 1].startsWith("-")) {
                dbFilename = args[i + 1];
                break;
            }
        }
        final String dbURL = DB_URL_PREFIX + dbFilename;
        DBCompanyDAO CompanyDAO = new DBCompanyDAO(dbURL);
        CarSharingUI ui = new CarSharingUI(CompanyDAO);
        ui.run();
    }
}