package classes;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        User user = null;
        Scanner mainScanner = new Scanner(System.in);

        while (true) {
            while (user == null) {
                System.out.println("Main Menu:");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. View Movies");
                System.out.println("4. Quit");
                System.out.println("Enter your choice:");
                int choice = mainScanner.nextInt();

                switch (choice) {
                    case 1:
                        // login function
                    	Scanner loginScanner = new Scanner(System.in);
                        System.out.println("Enter your email:");
                        String email = loginScanner.nextLine();
                        System.out.println("Enter your password:");
                        String password = loginScanner.nextLine();
                        try {
                            user = User.login(email, password);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (user != null) {
                            System.out.println("Login successful!");
                            System.out.println("Welcome, " + user.getFirstName() + "!");
                        } else {
                            System.out.println("Login failed..");
                        }
                        break;

                    case 2:
                        // register function
                        Scanner registerScanner = new Scanner(System.in);
                        System.out.print("Enter email: ");
                        String email1 = registerScanner.nextLine();
                        System.out.print("Enter password: ");
                        String password1 = registerScanner.nextLine();
                        System.out.print("Enter first name: ");
                        String firstName = registerScanner.nextLine();
                        System.out.print("Enter last name: ");
                        String lastName = registerScanner.nextLine();
                        System.out.print("Enter date of birth (YYYY-MM-DD): ");
                        String dateOfBirthStr = registerScanner.nextLine();
                        LocalDate dateOfBirth1 = LocalDate.parse(dateOfBirthStr);
                        Date dateOfBirth11 = Date.valueOf(dateOfBirth1);

                        System.out.print("Enter address: ");
                        String address = registerScanner.nextLine();
                        System.out.print("Enter secret phrase: ");
                        String secretPhrase = registerScanner.nextLine();

                        boolean isAdmin = false;

                        System.out.print("Enter initial money: ");
                        float money = registerScanner.nextFloat();
                        registerScanner.nextLine(); // consume newline character

                        user = User.register(email1, password1, firstName, lastName, dateOfBirth11, address, secretPhrase, isAdmin, money);
                        if (user == null) {
                            System.out.println("Registration failed! The email is already registered.");
                        }
                        break;

                    case 3:
                        // list movies
                        Movie.listMovies();
                        break;

                    case 4:
                        // quit
                        System.out.println("Goodbye!");
                        mainScanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

            while (user != null) {
                System.out.println("Navigation Menu:");
                System.out.println("1. View Movies");
                System.out.println("2. View Shipping Cart");
                System.out.println("3. Disconnect");
                System.out.println("4. Quit");
                if (user.getIsAdmin()) {
                    System.out.println("5. Admin Panel");
                }
                System.out.println("User connected: " + user.getFirstName() + " " + user.getLastName() + " (admin: " + user.getIsAdmin() + ")");
                System.out.print("Enter your choice: ");
                int choice = mainScanner.nextInt();
                mainScanner.nextLine(); // consume newline character

                switch (choice) {
                    case 1:
                        Movie.listMovies();

                        System.out.println("Film Menu:");
                        System.out.println("1. Add to Shipping Cart");
                        System.out.println("2. Comment a Movie");
                        System.out.println("3. View Comments");
                        System.out.println("4. Exit");

                        System.out.print("Enter your choice: ");
                        int choice2 = mainScanner.nextInt();
                        mainScanner.nextLine(); // consume newline character

                        switch (choice2) {
                            case 1:
                                Scanner addCartScanner = new Scanner(System.in);
                                System.out.print("Title of movie to add to your shipping cart: ");
                                String title = addCartScanner.nextLine();
                                int filmIndex = Movie.getIndexMovieByTitle(title);
                                ShippingCart.addToShippingCart(user, filmIndex);
                                break;

                            case 2:
                                Scanner commentScanner = new Scanner(System.in);
                                System.out.print("Title of movie you want to comment: ");
                                title = commentScanner.nextLine();
                                int filmIndex2 = Movie.getIndexMovieByTitle(title);
                                // implement comment function here
                                break;

                            case 3:
                                Scanner viewCommentsScanner = new Scanner(System.in);
                                System.out.print("Title of movie you want to see comments: ");
                                title = viewCommentsScanner.nextLine();
                                filmIndex = Movie.getIndexMovieByTitle(title);
                                Movie.commentsByMovieId(filmIndex);
                                break;

                            case 4:
                                break;

                            case 5:
                                if (user.getIsAdmin()) {
                                    // handleAdminPanel();
                                } else {
                                    System.out.println("Current user isn't an admin...");
                                }
                                break;

                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                        break;

                    case 2:
                        // View Shipping Cart function
                        break;

                    case 3:
                        user = null;
                        break;

                    case 4:
                        System.out.println("Goodbye!");
                        mainScanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
}


