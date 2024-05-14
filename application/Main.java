package application;

import java.util.Scanner; 
import application.Film;


public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("Welcome to the Online Movie Sales Platform!");
            System.out.println("1. Login/Register");
            System.out.println("2. View Movies");
            System.out.println("3. Manage Account");
            System.out.println("4. Admin Panel");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over

            switch (choice) {
                case 1:
                    handleLoginRegister();
                    break;
                case 2:
                    // MovieService.listFilms(); // Uncomment if MovieService is implemented
                    System.out.println("Listing movies is not implemented yet.");
                    break;
                case 3:
                    // UserService.manageUserAccount(); // Uncomment if UserService is implemented
                    System.out.println("User account management is not implemented yet.");
                    break;
                case 4:
                    // AdminService.adminPanel(); // Uncomment if AdminService is implemented
                    System.out.println("Admin panel is not implemented yet.");
                    break;
                case 5:
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        scanner.close();
    }

    private static void handleLoginRegister() {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine();  // Consume newline left-over

        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (option == 1) {
            User loggedInUser = AuthentificationServices.login(email, password);
            if (loggedInUser != null) {
                System.out.println("Login successful! Welcome, " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName() + "!");
            } else {
                System.out.println("Invalid credentials! Please try again.");
            }
        } else if (option == 2) {
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter date of birth (YYYY-MM-DD): ");
            String dateOfBirth = scanner.nextLine();
            System.out.print("Enter address: ");
            String address = scanner.nextLine();
            System.out.print("Enter secret phrase: ");
            String secretPhrase = scanner.nextLine();
            System.out.print("Are you an administrator? (true/false): ");
            boolean isAdmin = scanner.nextBoolean();
            System.out.print("Enter initial money: ");
            float money = scanner.nextFloat();
            scanner.nextLine();  // Consume newline left-over
            boolean success = AuthentificationServices.register(email, password, firstName, lastName, dateOfBirth, address, secretPhrase, isAdmin, money);
            if (success) {
                System.out.println("Registration successful! You can now login.");
            } else {
                System.out.println("Registration failed! The email is already registered.");
            }
        }
    }

}
   