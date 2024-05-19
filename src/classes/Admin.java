package classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Admin {

	public static ArrayList<User> listUser() {
		User user = null;
		ArrayList<User> listUsers = new ArrayList<User>();
	    try (Connection conn = DatabaseConnection.getConnection()) {
	        String query = "SELECT * FROM users";
	        try (PreparedStatement statement = conn.prepareStatement(query)) {
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    user = new User();
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
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return listUsers;
	}
} 
