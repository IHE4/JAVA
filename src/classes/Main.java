package classes;

import java.util.Scanner;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {

	public static void main(String[] args) throws SQLException {

		User user = null;
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			
			while (user == null) {

				System.out.println("Main Menu:");
				System.out.println("1. Login");
				System.out.println("2. Register");
				System.out.println("3. View Movies");
				System.out.println("4. Quit");
				System.out.print("Enter your choice:\n ");
				int choice = scanner.nextInt();

				switch (choice) {
				case 1:
					// login function

					System.out.print("Enter your email: ");
					String email = scanner.nextLine();

					System.out.print("Enter your password: ");
					String password = scanner.nextLine();


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
					System.out.print("Enter email: ");
					String email1 = scanner.nextLine();
					System.out.print("Enter password: ");
					String password1 = scanner.nextLine();
					System.out.print("Enter first name: ");
					String firstName = scanner.nextLine();
					System.out.print("Enter last name: ");
					String lastName = scanner.nextLine();

					System.out.print("Enter date of birth (YYYY-MM-DD): ");
					String dateOfBirthStr = scanner.nextLine();
					LocalDate dateOfBirth1 = LocalDate.parse(dateOfBirthStr);
					Date dateOfBirth11 = Date.valueOf(dateOfBirth1);

					System.out.print("Enter address: ");
					String address = scanner.nextLine();
					System.out.print("Enter secret phrase: ");
					String secretPhrase = scanner.nextLine();

					boolean isAdmin = false;

					System.out.print("Enter initial money: ");
					float money = scanner.nextFloat();
					scanner.nextLine();
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
					System.exit(0);
					break;

				default:
					System.out.println("Invalid choice. Please try again.");
				}
			}

			while(user != null) {
				System.out.println("Navigation Menu:");
				System.out.println("1. View Movies");
				System.out.println("2. View Shipping Cart");
				System.out.println("3. Disconnect");
				System.out.println("4. Quit");
				if (user.getIsAdmin() == true)
				{
					System.out.println("5. AdminPanel");
				}
				System.out.println("User connected : " + user.getFirstName() + " " + user.getLastName() + " (admin:" + user.getIsAdmin() + ")");
				System.out.print("Enter your choice: \n");
				
				int choice = scanner.nextInt();

				switch (choice) {
				case 1:
					Movie.listMovies();

					System.out.println("Film Menu:");
					System.out.println("1. add To Shipping Cart");
					System.out.println("2. Comment a Movie");
					System.out.println("3. View Comments");
					System.out.println("4. Exit");
			
					System.out.print("Enter your choice: \n");
					int choice2 = scanner.nextInt();
					
					switch (choice2) {
						case 1:
							System.out.println("title of movie to add to your shipping cart :\n");
							String title = scanner.nextLine();
							int film_index = Movie.getIndexMovieByTitle(title);
							ShippingCart.addToShippingCart(user, film_index);
							
							
							break;
						case 2:
							//comment a movie
							System.out.println("title of movie you want see commentaries : \n");
							title = scanner.nextLine();
							film_index = Movie.getIndexMovieByTitle(title);
							// implement
							break;
						case 3:
							//view comments
							System.out.println("title of movie you want see commentaries : \n");
							title = scanner.nextLine();
							film_index = Movie.getIndexMovieByTitle(title);
							Movie.commentsByMovieId(film_index);
							break;
						case 4:
							//exit
							break;
						case 5:
							if (user.getIsAdmin() == true) {
								//handleAdminPanel();
							}
							else {
								System.out.println("current user isn't admin...");
							}
							break;
						default :
							break;
					}
					
					break;
				case 2:
					break;
				case 3:
					user = null;
					break;
				case 4:
					System.out.println("Goodbye!");
					System.exit(0);
					break;
				default:
					System.out.println("Invalid choice. Please try again.");
				}
			}
			
			scanner.close();
			
		}
	}

}
