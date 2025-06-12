package carsharing.ui;

import carsharing.dao.CarDAO;
import carsharing.dao.CompanyDAO;
import carsharing.dao.CustomerDAO;
import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.model.Customer;

import java.util.List;
import java.util.Scanner;

public class CarSharingUI {
    private final CompanyDAO companyDAO;
    private final CarDAO carDAO;
    private final CustomerDAO customerDAO;
    private final Scanner scanner = new Scanner(System.in);

    public CarSharingUI(CompanyDAO companyDAO, CarDAO carDAO, CustomerDAO customerDAO) {
        this.carDAO = carDAO;
        this.companyDAO = companyDAO;
        this.customerDAO = customerDAO;

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
                    case 2 -> listCustomers();
                    case 3 -> createCustomer();
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
            int command = requestNavInput();
                switch (command) {
                    case 1 -> {
                        List<Company> currentCompanies = listCompanies();
                        if (currentCompanies.isEmpty()) break;
                        int input = requestNavInput();
                        int companyId =  input - 1;
                        if (input == 0) break; //
                        if (!currentCompanies.isEmpty()) {
                            companyMenu(currentCompanies.get(companyId));
                        }
                    }
                    case 2 -> addCompany();
                    case 0 -> running = false;
                    default -> System.out.println("Invalid input, Please enter a number between 0 and 2");
                }
        }
    }

    private void customerMenu(Customer customer) {

        boolean running = true;

        while(running) {
            printCustomerMenu();
            // Refresh in memory snapshot of customer
            customer = customerDAO.findById(customer.getId());
            int userCommand = requestNavInput();


            switch (userCommand) {
                case 1 -> rentACar(customer);
                case 2 -> returnCar(customer);
                case 3 -> displayUserRental(customer);
                case 0 -> running = false;
                default -> System.out.println("Invalid input, Please enter a number between 0 and 2");
            }



        }
    }

    private void rentACar(Customer customer) {

        if (customer.getCurrentRentalId() != null) {
            System.out.println("\nYou've already rented a car!");
            return;
        }
        List<Company> currentCompanies = listCompanies();
        if (!currentCompanies.isEmpty()) {
            int companyId = requestNavInput() - 1;
            List<Car> cars = listCars(currentCompanies.get(companyId));
            if (!cars.isEmpty()) {
                int choice = requestNavInput();
                if (choice == 0) return;              // user hit “Back”
                int idx = choice - 1;
                if (idx < 0 || idx >= cars.size()) {
                    System.out.println("Invalid selection");
                    return;
                }
                Car picked = cars.get(idx);
                customerDAO.updateRental(customer, picked.getId());  // use actual PK
                System.out.println("You rented '" + picked.getName() + "'");

            }
        }
    }

    private void returnCar(Customer customer) {
        if (customer.getCurrentRentalId() == null) {
            System.out.println("\nYou didn't rent a car!");
        }
        else {
            customerDAO.updateRental(customer, null);
            System.out.println("You've returned a rented car!");
        }
    }
    private void displayUserRental(Customer customer) {
        Integer rentalId = customer.getCurrentRentalId();
        if (rentalId == null) {
            System.out.println("You didn't rent a car!");
        } else {
            Car rental = carDAO.findById(rentalId);
            StringBuilder sb = new StringBuilder("\nYour rented car:\n");
            sb.append(rental.getName());
            sb.append("\nCompany: \n");
            sb.append(companyDAO.findById(rental.getCompanyID()).getName());
            System.out.println(sb);
        }
    }

    // returns true if companies found, else returns false
    private List<Company> listCompanies() {

        List<Company> companies = companyDAO.findAll();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println();
            System.out.println("Choose the company:");
            companyDAO.findAll().stream()
                    .forEach(company -> System.out.println(company.toString()));
            System.out.println("0. Back");
        }
        return companies;
    }

    private void listCustomers() {
        List<Customer> customers = customerDAO.findALL();
        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
        } else {
            System.out.println();
            System.out.println("Choose a customer:");
            customers.forEach(customer -> System.out.println(customer.toString()));
            System.out.println("0. Back");
            selectCustomer(customers);
        }
    }

    private void selectCustomer(List<Customer> customers) {
        boolean running = true;

        while (running) {
            String input = scanner.nextLine();

            try {
                int command = Integer.parseInt(input);
                if (command == 0) {
                    running = false;
                } else {
                    customerMenu(customers.get(command - 1));
                    running = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, Please input an integer value");
            }
        }
    }

    private int requestNavInput() {
        boolean running = true;
        int command = -1;
        while(running) {
            String input = scanner.nextLine();

            try {
                command = Integer.parseInt(input);
//                    companyMenu(command);
                    running = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, Please input an integer value");
            }
        }
        return command;
    }

    private void companyMenu(Company company) {
        boolean running = true;

        System.out.println();
        if(company == null) {
            System.out.println("Invalid input, Please enter a number from the given list");
            running = false;
        } else {
            System.out.println("'" + company.getName() + "' company");
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
                    case 1 -> listCars(company);
                    case 2 -> createCar(company);
                    case 0 -> running = false;
                    default -> System.out.println("Invalid input, Please enter a number between 0 and 2");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, Please enter a number between 0 and 2");
            }

        }
    }

    private void createCar(Company company) {

        System.out.println("\nEnter the car name: ");
        String carName = scanner.nextLine();

        Car newCar = new Car(1, carName, company.getId() );
        try {
            carDAO.add(newCar);
            System.out.println("The car was added!\n");

        } catch (RuntimeException e) {
            if (e.getMessage().contains("already exists")) {
                System.out.println(e.getMessage());
            }

        }
    }

    private void createCustomer() {
        System.out.println("\nEnter the customer name:");
        String customerName = scanner.nextLine();

        Customer customer = new Customer(0, customerName, null);
        try {
            customerDAO.addCustomer(customer);
            System.out.println("The customer was added!");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("already exists")) {
                System.out.println(e.getMessage());
            }
        }
    }

    private List<Car> listCars(Company company) {
        List<Car> cars = carDAO.findAllNotRented(company.getId());
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
            System.out.println();
        } else {
            System.out.println();
            System.out.println("Car list:");
            for (int i = 0; i < cars.size(); i++) {
                System.out.print((i + 1) + ". ");
                System.out.println(cars.get(i).getName());
            }
            System.out.println();
        }
        return cars;
    }

    private void addCompany() {
        System.out.println("\nEnter the company name:");
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
                2. Log in as a customer
                3. Create a customer
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

    private void printCustomerMenu() {
        System.out.print("""
               
               1. Rent a car
               2. Return a rented car
               3. My rented car
               0. Back
               """);
    }
}
