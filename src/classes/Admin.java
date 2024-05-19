package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Admin {
    public static ArrayList<User> listUsers() {
        ArrayList<User> listUsers = new ArrayList<>();

        String query = "SELECT * FROM users";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setDateOfBirth(resultSet.getDate("dateOfBirth"));
                user.setAddress(resultSet.getString("address"));
                user.setSecretPhrase(resultSet.getString("secretPhrase"));
                user.setIsAdmin(resultSet.getBoolean("isAdmin"));
                user.setMoney(resultSet.getDouble("money"));
                listUsers.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error listing users: " + e.getMessage());
        }
        return listUsers;
    }
}

