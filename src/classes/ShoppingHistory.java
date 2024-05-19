package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShoppingHistory {
	
	public static void addFilm(User user, int film_index) {
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement statement = conn.prepareStatement("INSERT INTO historique_achat (user_id, film_id) VALUES (?, ?)")) {
			statement.setInt(1, user.getId());
			statement.setInt(2, film_index);
			statement.executeUpdate();
	
		} catch (SQLException e) {
			System.out.println("Error adding film to shopping history: " + e.getMessage());
		}
	}
	
	public static void removeFilm(User user, int film_index) {
	    String query = "DELETE FROM historique_achat WHERE user_id = ? AND film_id = ?";

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement statement = conn.prepareStatement(query)) {

	        statement.setInt(1, user.getId());
	        statement.setInt(2, film_index);
	        statement.executeUpdate();

	    } catch (SQLException e) {
	        System.out.println("Error removing film from shopping history: " + e.getMessage());
	    }
	}
	
	public static void modifyFilm(User user, int oldFilmIndex, int newFilmIndex) {
	    ShoppingHistory.removeFilm(user, oldFilmIndex);
	    ShoppingHistory.addFilm(user, newFilmIndex);
	}
	
}
