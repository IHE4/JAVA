package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShippingCart {
	
	public static void Pay(User user, double totalPrice) {
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement statement = conn.prepareStatement("UPDATE users SET money = money - ? WHERE users.id = ?")) {
	        statement.setDouble(1, totalPrice);
	        statement.setInt(2, user.getId());
	        int rowsAffected = statement.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Payment successful. " + user.getFirstName());
	        } else {
	            System.out.println("No rows updated. Please check the user ID.");
	        }
	    } catch (SQLException e) {
	        System.out.println("Erreur lors de la mise à jour du solde : " + e.getMessage());
	    }
	}
	
	public static int verifyShippingCart(User user) {
		int nbFilms = -1;
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM shippingcart JOIN historique_achat ON shippingcart.film_id = historique_achat.id_film  WHERE shippingcart.user_id = ?")) {
			statement.setInt(1, user.getId());
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				nbFilms = resultSet.getInt("COUNT(*)");
			}
		} catch (SQLException e) {
			System.out.println("your shipping cart is empty");
		}
		return nbFilms;
	}
	
	
	public static double viewShippingCart(User user) {
		double totalPrice = 0.0;
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement statement = conn.prepareStatement("SELECT * FROM shippingcart sc LEFT JOIN films f ON sc.film_id = f.id WHERE sc.user_id = ?")) {
	        statement.setInt(1, user.getId());
	        try (ResultSet resultSet = statement.executeQuery()) {
	            totalPrice = 0.0;
	        	System.out.println("Contenu du panier de " + user.getFirstName() + ":");
	            while (resultSet.next()) {
	                String filmTitle = resultSet.getString("title");
	                double price = resultSet.getDouble("price");
	                totalPrice += price;
	                System.out.println(" - Film : " + filmTitle + " " + price + "$");
	            }
	            System.out.println("total price : " + totalPrice + " $");
	        }
	
	    } catch (SQLException e) {
	        System.out.println("Erreur lors de l'affichage du panier : " + e.getMessage());
	    }
	    return totalPrice;
	}
	
	public static ArrayList<Integer> idsFromShippingCart(User user) {
		ArrayList<Integer> ids = new ArrayList<Integer>();
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement statement = conn.prepareStatement("SELECT film_id FROM shippingcart sc LEFT JOIN films f ON sc.film_id = f.id WHERE sc.user_id = ?")) {
	        statement.setInt(1, user.getId());
	        try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	            	int id = resultSet.getInt("film_id");
	            	ids.add(id);
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Erreur lors de l'affichage des ids : " + e.getMessage());
	    }
		return ids;
	}
	
	
	public static void addToShippingCart(User user, int film_index) {
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement statement = conn.prepareStatement("INSERT INTO shippingcart (user_id, film_id) VALUES (?, ?)")) {
			statement.setInt(1, user.getId());
			statement.setInt(2, film_index);
			statement.executeUpdate();
	
		} catch (SQLException e) {
			System.out.println("Error adding film to shipping cart: " + e.getMessage());
		}
	}

	public static void removeFromShippingCart(User user, int film_index) {
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement statement = conn.prepareStatement("DELETE FROM shippingcart WHERE user_id = ? AND film_id = ?")) {
			statement.setInt(1, user.getId());
			statement.setInt(2, film_index);
			statement.executeUpdate();
	
		} catch (SQLException e) {
			System.out.println("Error removing film from shipping cart: " + e.getMessage());
		}
	}

	public static void modifyShippingCart(User user, int film_index, int new_film_index) {
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement statement = conn.prepareStatement("UPDATE shippingcart SET user_id = ? AND film_id = ? WHERE user_id = ? AND film_id = ?")) {
			statement.setInt(1, user.getId());
			statement.setInt(2, new_film_index);
			statement.setInt(3, user.getId());
			statement.setInt(4, film_index);
			statement.executeUpdate();
	
		} catch (SQLException e) {
			System.out.println("Error modifying film in shipping cart: " + e.getMessage());
		}
	}
}
