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
        	AuthentificationServices.login(email, password);
        } else if (option == 2) {
        	AuthentificationServices.register(email, password);
        }
    }
}
   