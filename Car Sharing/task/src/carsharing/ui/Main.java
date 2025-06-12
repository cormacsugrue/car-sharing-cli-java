package carsharing.ui;
import carsharing.dao.impl.DBCarDAO;
import carsharing.dao.impl.DBCompanyDAO;
import carsharing.dao.impl.DBCustomerDAO;


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
        DBCompanyDAO companyDAO = new DBCompanyDAO(dbURL);
        DBCarDAO carDAO = new DBCarDAO(dbURL);
        DBCustomerDAO customerDAO = new DBCustomerDAO(dbURL);

        CarSharingUI ui = new CarSharingUI(companyDAO, carDAO, customerDAO);
        ui.run();
    }
}