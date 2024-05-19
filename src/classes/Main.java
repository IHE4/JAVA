package classes;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws SQLException {
		User user = User.login("aa@gmail.com", "aa");

		while (true) {
			/*
			Scanner mainScanner = new Scanner(System.in);
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
					Date dateOfBirth11 = java.sql.Date.valueOf(dateOfBirth1);

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
			*/

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
				navScanner.nextLine();

				switch (choice) {
				case 1:
					Movie.listMovies();

					System.out.println("Film Menu:");
					System.out.println("1. Add to Shipping Cart");
					System.out.println("2. Comment a Movie");
					System.out.println("3. Remove a Comment");
					System.out.println("3. View Comments");
					System.out.println("4. Exit");

					System.out.print("Enter your choice: ");
					int choice2 = navScanner.nextInt();
					navScanner.nextLine(); // consume newline character

					switch (choice2) {
					case 1:
						Scanner addCartScanner = new Scanner(System.in);
						System.out.print("Title of movie to add to your shipping cart: ");
						String title = addCartScanner.nextLine();
						int filmIndex = Movie.getIndexMovieByTitle(title);
						ShippingCart.addToShippingCart(user, filmIndex);
						addCartScanner.close();
						break;

					case 2:
						Scanner commentScanner = new Scanner(System.in);
						System.out.println("Title of movie you want to comment: ");
						title = commentScanner.nextLine();
						int filmIndex2 = Movie.getIndexMovieByTitle(title);
						if (Comment.getEnabledCommentaryById(filmIndex2) == false)
						{
							System.out.println("commentaries are not enabled for this movie...\n");
							break;
						}

						System.out.println("leave a comment : \n");
						String commentaire = commentScanner.nextLine();

						System.out.println("leave a note 0-10 : \n");
						int note = commentScanner.nextInt();

						Date date = null;

						Comment.addCommentary(user, filmIndex2, commentaire, date, note);

						break;

					case 3:
						//remove a comment
						Scanner commentScanner1 = new Scanner(System.in);
						System.out.println("enter title of movie you want to see your comments :");
						String title1 = commentScanner1.nextLine();
						int filmIndex3 = Movie.getIndexMovieByTitle(title1);
						
						Comment.listCommentsByUserByFilm(user, filmIndex3);
						System.out.println("enter id of comment you want to remove:");
						int id_comment = commentScanner1.nextInt();
						//Comment.verifCommentByid(user, id_comment);
						Comment.removeCommentary(id_comment);
						
						break;

					case 4:
						Scanner viewCommentsScanner = new Scanner(System.in);
						System.out.print("Title of movie you want to see comments: ");
						title1 = viewCommentsScanner.nextLine();
						filmIndex = Movie.getIndexMovieByTitle(title1);
						Comment.ViewCommentsByMovieId(filmIndex);
						break;

					default:
						System.out.println("Invalid choice. Please try again.");
						break;
					}


				case 2:
					// View Shipping Cart function
					double totalPrice = ShippingCart.viewShippingCart(user);
					Scanner shippingCartScanner = new Scanner(System.in);
					System.out.println("Shipping Cart Menu:");
					System.out.println("1. Pay");
					System.out.println("2. Remove movie");
					System.out.println("3. Exit");
					System.out.println("Enter your choice:");
					int choice3 = shippingCartScanner.nextInt();

					switch (choice3) {
						case 1:
	
							if (ShippingCart.verifyShippingCart(user) > 0) {
								System.out.println("A movie is already bought... Verify your shipping cart");
								break;
							}
	
							ShippingCart.Pay(user, totalPrice);
							ArrayList<Integer> ids = ShippingCart.idsFromShippingCart(user);
							for (int i = 0; i < ids.size(); i++) {
								ShippingCart.removeFromShippingCart(user, ids.get(i));
								ShoppingHistory.addFilm(user, ids.get(i));
							}
	
							break;
	
						case 2:
							System.out.println("Title of movie you want to remove :");
							Scanner removeMovieScanner = new Scanner(System.in);
							String title = removeMovieScanner.nextLine();
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
						AdminScanner.close();
						ArrayList<User> listUsers = Admin.listUser();
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
;								boolean isAdmin = registerScanner.nextBoolean();

								System.out.print("Enter initial money: ");
								float money = registerScanner.nextFloat();
								
								registerScanner.nextLine();
								registerScanner.close();
								User.register(email1, password1, firstName, lastName, dateOfBirth11, address, secretPhrase, isAdmin, money);

						        break;
						    case 2:
						        // Code pour supprimer un utilisateur
						    	Scanner removeUserScanner = new Scanner(System.in);
						    	for (int i = 0; i < listUsers.size(); i++) {
						    		System.out.println(listUsers.get(i).getId() + " " + listUsers.get(i).getFirstName() + listUsers.get(i).getIsAdmin());
						    	}
						    	System.out.println("Which User you want to remove ? :");
						    	int id_remove = removeUserScanner.nextInt();
						    	User.removeUser(id_remove);
						    	
						        break;
						    case 3:
						        // Code pour modifier un utilisateur
						    	Scanner modifyUserScanner = new Scanner(System.in);
						    	for (int i = 0; i < listUsers.size(); i++) {
						    		System.out.println(listUsers.get(i).getId() + " " + listUsers.get(i).getFirstName() + listUsers.get(i).getIsAdmin());
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
								
								/*
								System.out.print("Enter date of birth (YYYY-MM-DD): ");
								String dateOfBirthStr2 = modifyUserScanner.nextLine();
								*/
								
								Date dateOfBirth = null;

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
						        
						        System.out.println("Entrez la date de publication (format JJ/MM/AAAA) :");
						        String publicationDateInput = scanner.nextLine();
						        LocalDate dateOfBirth3 = LocalDate.parse(publicationDateInput);
								Date publicationDate = java.sql.Date.valueOf(dateOfBirth3);
						        
						        System.out.println("Entrez le thème du film :");
						        String theme = scanner.nextLine();
						        System.out.println("Entrez le nom du producteur :");
						        String producer = scanner.nextLine();
						        System.out.println("Entrez le résumé du film :");
						        String resume = scanner.nextLine();
						        System.out.println("Entrez la catégorie d'âge :");
						        int ageCategory = scanner.nextInt();
						        System.out.println("Entrez les acteurs principaux :");
						        String mainActors = scanner.nextLine();
						        System.out.println("Le film permet-il les commentaires ? (true/false) :");
						        boolean enableCommentaries = scanner.nextBoolean();
						        System.out.println("Entrez le code du film :");
						        String code = scanner.nextLine();
						        System.out.println("Entrez le prix du film :");
						        double price = scanner.nextDouble();
						        
						        Movie.addFilm(title, realisator, publicationDate, theme, producer, resume, ageCategory, mainActors, enableCommentaries, code, price);
						        
						        
						    	
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
				}
			}
		}
	}
}


