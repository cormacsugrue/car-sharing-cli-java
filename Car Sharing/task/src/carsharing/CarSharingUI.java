package carsharing;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Scanner;

public class CarSharingUI {
    private final CompanyDAO companyDAO;
    private final Scanner scanner = new Scanner(System.in);

    public CarSharingUI(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public void run() {
        mainMenu();
    }

    private void mainMenu() {
        boolean running = true;

        while(running) {
            printMainMenu();
            String input = scanner.nextLine();
            try {
                int command = Integer.parseInt(input);
                switch (command) {
                    case 1 -> managerMenu();
                    case 0 -> {
                        running = false;
                    }
                    default -> System.out.println("Invalid input, please input valid number");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please input valid number");
            }



        }

    }

    private void managerMenu() {
        boolean running = true;

        while(running) {
            printManagerMenu();
            String input = scanner.nextLine();

            try {
                int command = Integer.parseInt(input);
                switch (command) {
                    case 1 -> listCompanies();
                    case 2 -> addCompany();
                    case 0 -> running = false;
                    default -> System.out.println("Invalid input, Please enter a number between 0 and 2");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, Please enter a number between 0 and 2");
            }
        }
    }

    private void listCompanies() {

        List<Company> companies = companyDAO.findAll();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println();
            System.out.println("Choose the company:");
            companyDAO.findAll().stream()
                    .forEach(company -> System.out.println(company.toString()));
            System.out.println("0. Back");
            selectCompany();
        }
    }

    private void selectCompany() {
        boolean running = true;

        while(running) {
            String input = scanner.nextLine();

            try {
                int command = Integer.parseInt(input);
                if(command == 0) {
                    running = false;
                } else {
                    companyMenu(command);
                    running = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, Please input an integer value");
            }
        }
    }

    private void companyMenu(int companyID) {
        boolean running = true;
        Company company = companyDAO.findById(companyID);
        if(company == null) {
            System.out.println("Invalid input, Please enter a number from the given list");
            running = false;
        } else {
            System.out.println("'" + companyDAO.findById(companyID).getName() + "' company");
        }

        while(running) {
            System.out.println("""
                    1. Car list
                    2. Create a car
                    0. Back
                   """);
            String input = scanner.nextLine();

            try {
                int command = Integer.parseInt(input);
                
                switch (command) {
                    case 1 -> listCars();
                    case 2 -> createCar();
                    case 0 -> running = false;
                    default -> System.out.println("Invalid input, Please enter a number between 0 and 2");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, Please enter a number between 0 and 2");
            }
            
        }
    }

    private void createCar() {
    }

    private void listCars() {
    }

    private void addCompany() {
        System.out.println("\nEnter the company name: ");
        String companyName = scanner.nextLine();

        Company newCompany = new Company(1, companyName);
        try {
            companyDAO.add(newCompany);
            System.out.println("The company was created!");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("already exists")) {
                System.out.println(e.getMessage());
            }

        }
    }

    private void printMainMenu() {
        System.out.print("""
                
                1. Log in as a manager
                0. Exit
                """);
    }

    private void printManagerMenu() {
        System.out.print("""
                
                1. Company list
                2. Create a company
                0. Back
                """);
    }
}
