package classes;

import java.sql.Connection;
import java.sql.Date;
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
    
    public static void viewComments() {
        String query = "SELECT commentaires.id, commentaires.commentaire, commentaires.date, commentaires.note, users.firstName " +
                       "FROM commentaires " +
                       "LEFT JOIN users ON commentaires.id_utilisateur = users.id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (!rs.isBeforeFirst()) {
                System.out.println("No comments found.");
                return;
            }
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String commentaire = rs.getString("commentaire");
                Date date = rs.getDate("date");
                int note = rs.getInt("note");
                
                System.out.println("id : " + id + " " + firstName + " : " + date + " (" + note + "/10)");
                System.out.println(commentaire);
                System.out.println();
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        }
    }
    
    public static String getCommentFromID(int id) {
        String query = "SELECT commentaire FROM commentaires WHERE id = ?";
        String comment = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);) {
        	pstmt.setInt(1, id);
        	ResultSet rs = pstmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("No comments found.");
                return null;
            }
            
            while (rs.next()) {
                comment = rs.getString("commentaire");
            }   
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        }
		return comment;
    }

}

