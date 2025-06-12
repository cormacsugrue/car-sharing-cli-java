# Car Sharing CLI – Java

**Car Sharing CLI** is a console application that simulates a simple car rental management system. This project was created for educational purposes (JetBrains Academy / Hyperskill) to practice Java programming, JDBC (using an H2 database), and basic design patterns like DAO (Data Access Object).

## Project Overview

The application lets you:

- Manage a list of car rental **Companies** (create new companies, list existing ones).
- For each company, manage its **Cars** (add new cars and list available cars).
- Manage **Customers** who can rent cars from the companies.
- Allow customers to rent a car and return a rented car via the console interface.

The program is interactive and menu-driven, running in the console. It stores data persistently using an embedded H2 database, so your companies, cars, and customers will be saved between runs (the data is stored in a local file).

## Features

- **Company Management (Manager interface):**

    - View all companies in the database.
    - Create a new company.

- **Car Management (Manager interface):**

    - Select a company to view its car fleet.
    - Add new cars under a company.
    - List cars available under a company.

- **Customer Operations:**

    - Create a new customer profile.
    - Log in as a customer to rent a car:
        - Choose a company and then a car to rent.
        - Return a currently rented car.
        - View the car you have rented.

- The menu system ensures that you can navigate between these options, and “0. Back” or “0. Exit” options are provided at appropriate points to go back to the previous menu or exit the application.

## Technologies Used

- **Java 17** (or above) – The project is written in Java and utilizes standard libraries for console I/O.
- **JDBC with H2 Database** – An embedded H2 database is used to persist data. Tables are created automatically if they don’t exist. (No separate DB setup is required.)
- **Gradle** – The project is built with Gradle. The Gradle configuration uses the Application plugin to allow running the app easily, and includes the H2 database dependency.

## Project Structure

The code is organized into the following Java packages under `src/main/java/carsharing`:

- **Data models**: `Car`, `Company`, `Customer` (POJOs with fields like `id`, `name`, and relational IDs such as `companyId` or `rentedCarId`).
- **DAO interfaces**: `CompanyDAO`, `CarDAO`, `CustomerDAO` defining CRUD and rental operations for each entity.
- **JDBC implementations**: `DBCompanyDAO`, `DBCarDAO`, `DBCustomerDAO` implementing the interfaces, handling schema creation (`CREATE TABLE IF NOT EXISTS`) and SQL queries/updates.
- **Utilities**: `DBClient` for managing the H2 database connection, and `ResultSetMapper` to map `ResultSet` rows to model objects.
- **User interface classes**: `CarSharingUI` for menu-driven interaction (printing menus, reading input, calling DAOs) and `Main` as the application entry point (initializes DAOs and starts the UI loop).

This structure cleanly separates concerns:

- **Models** know only about data fields.
- **DAO interfaces** define operations without SQL.
- **DAO implementations** contain all database-specific code.
- **Utilities** provide shared, low-level support.
- **UI** handles only user interaction, with no SQL or data mapping logic.## Getting Started

### Prerequisites

- Java JDK (17 or above recommended to match the project settings).
- Gradle (if you want to run via Gradle; a Gradle wrapper is included so you can use `./gradlew` without a separate installation).

### Building and Running

1. **Clone the repository** (or download the source code):

   ```bash
   git clone https://github.com/cormacsugrue/car-sharing-cli-java.git
   cd car-sharing-cli-java
   ```

2. **Build the project** (if desired):

   ```bash
   ./gradlew build
   ```

   This will compile the code and run any tests (if present).

3. **Run the application**:

   ```bash
   ./gradlew run
   ```

   This uses Gradle’s Application plugin to compile and launch the app. You should see a console menu printed in your terminal. *Alternatively:* You can open the project in an IDE like IntelliJ IDEA and run the `Main` class.

4. **Using the app**: Follow the on-screen menu prompts:

    - Choose **1** to log in as manager – from there you can list or create companies, then select a company to manage its cars.
    - Choose **2** to log in as customer – you will be asked to select a customer profile (or create one by choosing option **3** in the main menu first). As a customer, you can then rent or return a car.
    - Choose **3** (from main menu) to create a new customer.
    - Choose **0** to exit the program.

When you run the app the first time, it will create an H2 database file (likely named `carsharing.mv.db`) in the project directory. This file stores the companies, cars, and customers. You can delete it to reset the data.

## Example Interaction

```
1. Log in as a manager
2. Log in as a customer
3. Create a customer
0. Exit
> 1

Choose a company:
1. CarToGo
2. DriveNow
0. Back
> 1

Car list for "CarToGo":
1. Toyota Corolla
2. Hyundai Elantra
0. Back

...
```

## Contributing

This project is for learning purposes, so contributions are not expected. However, if you want to experiment or improve the project:

- Feel free to fork the repository and submit pull requests.
- You can try adding features like deleting entries, more detailed validation, or a friendlier UI.

## Learning Outcomes

Working on this project helps practice:

- Organizing a Java project with multiple classes and layers (UI, DAO, etc.).
- Using JDBC to connect to a database (H2) and executing SQL from Java.
- Handling basic user input and program flow in a console application.
- Understanding how to separate concerns to make code more maintainable.

## License

This code is provided as-is for educational use. (You can specify a license if needed.)

