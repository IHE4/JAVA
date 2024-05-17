package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShippingCart {
	
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

	public void removeFromShippingCart(User user, int film_index) {
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement statement = conn.prepareStatement("DELETE FROM shippingcart WHERE user_id = ? AND film_id = ?")) {
			statement.setInt(1, user.getId());
			statement.setInt(2, film_index);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error removing film from shipping cart: " + e.getMessage());
		}
	}

	public void modifyShippingCart(User user, int film_index) {
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement statement = conn.prepareStatement("UPDATE shippingcart SET user_id = ? AND film_id = ? WHERE user_id = ? AND film_id = ?")) {
			statement.setInt(1, user.getId());
			statement.setInt(2, film_index);
			statement.setInt(3, user.getId());
			statement.setInt(4, film_index);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error modifying film in shipping cart: " + e.getMessage());
		}
	}
}
