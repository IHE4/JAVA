package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthentificationServices {
    // On supprime la constante USER_FILE
    
    public static boolean register(String email, String password, String firstName, String lastName, String dateOfBirth, String address, String secretPhrase, boolean isAdmin, float money) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Users (email, password, firstName, lastName, dateOfBirth, address, secretPhrase, isAdmin, money) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);")) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, firstName);
            stmt.setString(4, lastName);
            stmt.setString(5, dateOfBirth);
            stmt.setString(6, address);
            stmt.setString(7, secretPhrase);
            stmt.setBoolean(8, isAdmin);
            stmt.setFloat(9, money);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    public static User login(String email, String password) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE email = ? AND password = ?;")) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                // Récupérer les autres colonnes
                // Créer et retourner un objet User
            }
        } catch (SQLException e) {
            System.err.println("Error logging in: " + e.getMessage());
        }
        return null;
    }

    // La méthode loadUsers n'est plus nécessaire car nous allons charger les utilisateurs au fur et à mesure que nous en avons besoin.

    // La méthode saveUsers n'est plus nécessaire car nous n'avons plus besoin de sauvegarder les utilisateurs dans un fichier.
}
