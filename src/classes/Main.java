package classes;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws SQLException {
		//User user = null;
		User user = User.login("aa@gmail.com", "aa");
		Scanner mainScanner = new Scanner(System.in);

		while (true)
		{
			while (user == null) {
				System.out.println("Main Menu:");
				System.out.println("1. Login");
				System.out.println("2. Register");
				System.out.println("3. View Movies");
				System.out.println("4. Quit");
				System.out.print("Enter your choice: ");

				while (!mainScanner.hasNextInt()) {
					System.out.println("Invalid input. Please enter a number.");
					mainScanner.next(); // Discard invalid input
				}

				int choice = mainScanner.nextInt();
				mainScanner.nextLine(); // Consume newline character

				switch (choice) {
				case 1:
					// login function
					System.out.print("Enter your email: ");
					String email = mainScanner.nextLine();
					System.out.print("Enter your password: ");
					String password = mainScanner.nextLine();

					try {
						user = User.login(email, password);
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (user != null) {
						System.out.println("Login successful!");
						System.out.println("Welcome, " + user.getFirstName() + "!");
					} else {
						System.out.println("Login failed.");
					}
					break;

				case 2:
					// register function
					System.out.print("Enter email: ");
					String email1 = mainScanner.nextLine();
					System.out.print("Enter password: ");
					String password1 = mainScanner.nextLine();
					System.out.print("Enter first name: ");
					String firstName = mainScanner.nextLine();
					System.out.print("Enter last name: ");
					String lastName = mainScanner.nextLine();
					System.out.print("Enter date of birth (YYYY-MM-DD): ");
					String dateOfBirthStr = mainScanner.nextLine();

					LocalDate dateOfBirth1 = LocalDate.parse(dateOfBirthStr);
					Date dateOfBirth11 = java.sql.Date.valueOf(dateOfBirth1);

					System.out.print("Enter address: ");
					String address = mainScanner.nextLine();
					System.out.print("Enter secret phrase: ");
					String secretPhrase = mainScanner.nextLine();

					boolean isAdmin = false;

					System.out.print("Enter initial money: ");
					while (!mainScanner.hasNextFloat()) {
						System.out.println("Invalid input. Please enter a number.");
						mainScanner.next(); // Discard invalid input
					}

					float money = mainScanner.nextFloat();
					mainScanner.nextLine(); // Consume newline character

					user = User.addUser(email1, password1, firstName, lastName, dateOfBirth11, address, secretPhrase, isAdmin, money);

					if (user != null) {
						System.out.println("Registration successful!");
						System.out.println("Welcome, " + user.getFirstName() + "!");
					} else {
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
				Scanner navScanner = new Scanner(System.in);
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
				int choice = navScanner.nextInt();

				switch (choice) {
				case 1:
					Movie.listMovies();
					System.out.println("Film Menu:");
					System.out.println("1. Add to Shipping Cart");
					System.out.println("2. Comment a Movie");
					System.out.println("3. Remove a Comment");
					System.out.println("4. View Comments");
					System.out.println("5. Exit");

					System.out.print("Enter your choice: ");
					int choice2 = navScanner.nextInt();
					navScanner.nextLine(); // consume newline character

					switch (choice2) {
					case 1:
						System.out.print("Title of movie to add to your shipping cart: ");
						String title = navScanner.nextLine();
						int filmIndex = Movie.getIndexMovieByTitle(title);
						ShippingCart.addToShippingCart(user, filmIndex);
						break;

					case 2:
						System.out.print("Title of movie you want to comment: ");
						title = navScanner.nextLine();
						int filmIndex2 = Movie.getIndexMovieByTitle(title);
						if (!Comment.getEnabledCommentaryById(filmIndex2)) {
							System.out.println("Commentaries are not enabled for this movie...\n");
							break;
						}

						System.out.print("Leave a comment: ");
						String commentaire = navScanner.nextLine();

						System.out.print("Leave a note (0-10): ");
						int note = navScanner.nextInt();
						navScanner.nextLine(); // consume newline character

						Date date = new Date(System.currentTimeMillis()); // Set the current date
						Comment.addCommentary(user, filmIndex2, commentaire, date, note);
						break;

					case 3:
						System.out.print("Enter title of movie you want to see your comments: ");
						String title1 = navScanner.nextLine();
						int filmIndex3 = Movie.getIndexMovieByTitle(title1);

						Comment.listCommentsByUserByFilm(user, filmIndex3);
						System.out.print("Enter id of comment you want to remove: ");
						int id_comment = navScanner.nextInt();
						navScanner.nextLine(); // consume newline character

						Comment.removeCommentary(id_comment);
						break;

					case 4:
						System.out.print("Title of movie you want to see comments: ");
						title1 = navScanner.nextLine();
						filmIndex = Movie.getIndexMovieByTitle(title1);
						Comment.viewCommentsByMovieId(filmIndex);;
						break;

					case 5:
						break;

					default:
						System.out.println("Invalid choice. Please try again.");
						break;
					}
					break;

				case 2:
					double totalPrice = ShippingCart.viewShippingCart(user);
					System.out.println("Shipping Cart Menu:");
					System.out.println("1. Pay");
					System.out.println("2. Remove movie");
					System.out.println("3. Exit");
					System.out.print("Enter your choice: ");
					int choice3 = navScanner.nextInt();
					navScanner.nextLine(); // consume newline character

					switch (choice3) {
					case 1:
						if (ShippingCart.verifyShippingCart(user) > 0) {
							System.out.println("A movie is already bought... Verify your shipping cart");
							break;
						}

						ShippingCart.Pay(user, totalPrice);
						ArrayList<Integer> ids = ShippingCart.idsFromShippingCart(user);
						for (int id : ids) {
							ShippingCart.removeFromShippingCart(user, id);
							ShoppingHistory.addFilm(user, id);
						}
						break;

					case 2:
						System.out.print("Title of movie you want to remove: ");
						String title = navScanner.nextLine();
						int filmIndex = Movie.getIndexMovieByTitle(title);
						ShippingCart.removeFromShippingCart(user, filmIndex);
						System.out.println(title + " removed from your shipping cart.");
						break;

					case 3:
						break;

					default:
						System.out.println("Invalid choice. Please try again.");
						break;
					}
					break;

				case 3:
					user = null;
					break;

				case 4:
					System.out.println("Goodbye!");
					navScanner.close();
					System.exit(0);
					break;

				case 5:
					// handle admin panel
					if (user.getIsAdmin()) {
						Scanner AdminScanner = new Scanner(System.in);

						System.out.println("Admin Menu:");
						System.out.println("1. Add User");
						System.out.println("2. Remove User");
						System.out.println("3. Modify User");
						System.out.println("4. Add Film");
						System.out.println("5. Remove Film");
						System.out.println("6. Modify Film");
						System.out.println("7. Add Comment");
						System.out.println("8. Remove Comment");
						System.out.println("9. Modify Comment");
						System.out.println("10. Add To Shipping Cart");
						System.out.println("11. Remove To Shipping Cart");
						System.out.println("12. Modify Shipping Cart");
						System.out.println("13. Add To Shopping History");
						System.out.println("14. Remove To Shopping History");
						System.out.println("15. Modify Shopping History");
						System.out.println("17. AI 1");
						System.out.println("18. AI 2");
						System.out.println("19. Exit");

						System.out.print("Enter your choice: ");
						int choice1 = AdminScanner.nextInt();
						//AdminScanner.close();
						ArrayList<User> listUsers = Admin.listUsers();
						switch (choice1) {
						case 1:
							// Code pour ajouter un utilisateur
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
							Date dateOfBirth11 = java.sql.Date.valueOf(dateOfBirth1);

							System.out.print("Enter address: ");
							String address = registerScanner.nextLine();
							System.out.print("Enter secret phrase: ");
							String secretPhrase = registerScanner.nextLine();

							System.out.println("Enter is Admin : (true/false)");
							boolean isAdmin = registerScanner.nextBoolean();

							System.out.print("Enter initial money: ");
							float money = registerScanner.nextFloat();

							registerScanner.nextLine();
							registerScanner.close();
							User.addUser(email1, password1, firstName, lastName, dateOfBirth11, address, secretPhrase, isAdmin, money);

							break;
						case 2:
							// Code pour supprimer un utilisateur
							Scanner removeUserScanner = new Scanner(System.in);
							for (int i = 0; i < listUsers.size(); i++) {
								System.out.println(" ID : " + listUsers.get(i).getId() + " " + listUsers.get(i).getFirstName() + " (" + listUsers.get(i).getIsAdmin() + ")");
							}
							System.out.println("Which User you want to remove ? : ");
							int id_remove = removeUserScanner.nextInt();
							User.removeUser(id_remove);

							break;
						case 3:
							// Code pour modifier un utilisateur
							Scanner modifyUserScanner = new Scanner(System.in);
							for (int i = 0; i < listUsers.size(); i++) {
								System.out.println(" ID : " + listUsers.get(i).getId() + " " + listUsers.get(i).getFirstName() + " (" + listUsers.get(i).getIsAdmin() + ")");
							}
							System.out.println("Which User you want to modify ? :");
							int id_modify = modifyUserScanner.nextInt();

							Scanner modifyScanner = new Scanner(System.in);
							System.out.print("Enter email: ");
							String email2 = modifyUserScanner.nextLine();
							System.out.print("Enter password: ");
							String password2 = modifyUserScanner.nextLine();
							System.out.print("Enter first name: ");
							String firstName2 = modifyUserScanner.nextLine();
							System.out.print("Enter last name: ");
							String lastName2 = modifyUserScanner.nextLine();

							System.out.print("Enter date of birth (YYYY-MM-DD): ");
							String dateOfBirthStr2 = mainScanner.nextLine();

							LocalDate dateOfBirth2 = LocalDate.parse(dateOfBirthStr2);
							Date dateOfBirth = java.sql.Date.valueOf(dateOfBirth2);


							System.out.print("Enter address: ");
							String address2 = modifyUserScanner.nextLine();
							System.out.print("Enter secret phrase: ");
							String secretPhrase2 = modifyUserScanner.nextLine();

							System.out.println("Enter is Admin : (true/false)");
							;								boolean isAdmin2 = modifyUserScanner.nextBoolean();

							System.out.print("Enter initial money: ");
							float money2 = modifyUserScanner.nextFloat();

							User.modifyUser(id_modify, email2, password2, firstName2, lastName2, dateOfBirth, address2, secretPhrase2, isAdmin2, money2);
							
							break;
						case 4:
							// Code pour ajouter un film
					        Scanner scanner = new Scanner(System.in);

					        System.out.println("Entrez le titre du film :");
					        String title = scanner.nextLine();

					        System.out.println("Entrez le nom du réalisateur :");
					        String realisator = scanner.nextLine();

					        System.out.println("Entrez la date de publication (format AAAA-MM-JJ) :");
					        String publicationDateInput = scanner.nextLine();
					        LocalDate publicationDateLocal = LocalDate.parse(publicationDateInput);
					        Date publicationDate = java.sql.Date.valueOf(publicationDateLocal);

					        System.out.println("Entrez le thème du film :");
					        String theme = scanner.nextLine();

					        System.out.println("Entrez le nom du producteur :");
					        String producer = scanner.nextLine();

					        System.out.println("Entrez le résumé du film :");
					        String resume = scanner.nextLine();

					        System.out.println("Entrez la catégorie d'âge :");
					        int ageCategory = scanner.nextInt();
					        scanner.nextLine(); // Consume the leftover newline character

					        System.out.println("Entrez les acteurs principaux :");
					        String mainActors = scanner.nextLine();

					        System.out.println("Le film permet-il les commentaires ? (true/false) :");
					        boolean enableCommentaries = scanner.nextBoolean();
					        scanner.nextLine(); // Consume the leftover newline character

					        System.out.println("Entrez le code du film :");
					        String code = scanner.nextLine();

					        System.out.println("Entrez le prix du film :");
					        double price = scanner.nextDouble();
					        scanner.nextLine(); // Consume the leftover newline character

					        Movie.addFilm(title, realisator, publicationDate, theme, producer, resume, ageCategory, mainActors, enableCommentaries, code, price);

					        scanner.close();

							break;
						case 5:
							// Code pour supprimer un film
							break;
						case 6:
							// Code pour modifier un film
							break;
						case 7:
							// Code pour ajouter un commentaire
							break;
						case 8:
							// Code pour supprimer un commentaire
							break;
						case 9:
							// Code pour modifier un commentaire
							break;
						case 10:
							// Code pour ajouter un article au panier d'expédition
							break;
						case 11:
							// Code pour supprimer un article du panier d'expédition
							break;
						case 12:
							// Code pour modifier le panier d'expédition
							break;
						case 13:
							// Code pour ajouter un article à l'historique d'achat
							break;
						case 14:
							// Code pour supprimer un article de l'historique d'achat
							break;
						case 15:
							// Code pour modifier l'historique d'achat
							break;
						case 17:
							// Code pour l'IA 1
							break;
						case 18:
							// Code pour l'IA 2
							break;
						case 19:
							// Code pour quitter l'application
							break;
						default:
							System.out.println("Invalid choice, please try again.");
						}
					} 
					else {
						System.out.println("Current user isn't an admin...");
					}
					break;

				default:
					System.out.println("Invalid choice. Please try again.");
					break;
				}
			}
		}
	}
}


