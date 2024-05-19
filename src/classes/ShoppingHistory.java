package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShoppingHistory {

    public static void addFilm(User user, int filmIndex) {
        String query = "INSERT INTO historique_achat (user_id, film_id) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, user.getId());
            statement.setInt(2, filmIndex);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding film to shopping history: " + e.getMessage());
        }
    }

    public static void removeFilm(User user, int filmIndex) {
        String query = "DELETE FROM historique_achat WHERE user_id = ? AND film_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, user.getId());
            statement.setInt(2, filmIndex);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error removing film from shopping history: " + e.getMessage());
        }
    }

    public static void modifyFilm(User user, int oldFilmIndex, int newFilmIndex) {
        removeFilm(user, oldFilmIndex);
        addFilm(user, newFilmIndex);
    }
    
    
}

