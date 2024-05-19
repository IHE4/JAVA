package classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Comment {

    public static boolean getEnabledCommentaryById(int id) {
        boolean isEnabled = false;
        String query = "SELECT enable_commentaries FROM films WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    isEnabled = resultSet.getBoolean("enable_commentaries");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting enable_commentary by id: " + e.getMessage());
        }
        return isEnabled;
    }

    public static void viewCommentsByMovieId(int movieId) throws SQLException {
        String query = "SELECT commentaires.commentaire, commentaires.date, commentaires.note, users.firstName FROM commentaires LEFT JOIN users ON commentaires.id_utilisateur = users.id WHERE id_film = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, movieId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    System.out.println("No comments found for this id.");
                    return;
                }
                
                while (rs.next()) {
                    String firstName = rs.getString("firstName");
                    String commentaire = rs.getString("commentaire");
                    Date date = rs.getDate("date");
                    int note = rs.getInt("note");
                    System.out.println(firstName + " : " + date + " (" + note + "/10)");
                    System.out.println(commentaire);
                    System.out.println();
                }
            }
        }
    }

    public static void addCommentary(User user, int idFilm, String commentaire, Date date, int note) {
        String query = "INSERT INTO commentaires (id_film, id_utilisateur, commentaire, date, note) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idFilm);
            stmt.setInt(2, user.getId());
            stmt.setString(3, commentaire);
            stmt.setDate(4, date);
            stmt.setInt(5, note);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding commentary: " + e.getMessage());
        }
    }

    public static void listCommentsByUserByFilm(User user, int idFilm) {
        String query = "SELECT * FROM commentaires WHERE id_film = ? AND id_utilisateur = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, idFilm);
            statement.setInt(2, user.getId());
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("No comments found for this user and film.");
                    return;
                }
                
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String comment = resultSet.getString("commentaire");
                    Date date = resultSet.getDate("date");
                    int note = resultSet.getInt("note");

                    System.out.println("ID : " + id + " " + user.getFirstName() + " : " + date + " (" + note + "/10)");
                    System.out.println(comment);
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error listing commentary: " + e.getMessage());
        }
    }

    public static void removeCommentary(int idComment) {
        String query = "DELETE FROM commentaires WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, idComment);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error removing commentary: " + e.getMessage());
        }
    }
    
    public static void modifyCommentary(int idComment, String newCommentaire, int newNote) {
        String query = "UPDATE commentaires SET commentaire = ?, note = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, newCommentaire);
            statement.setInt(2, newNote);
            statement.setInt(3, idComment);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error modifying commentary: " + e.getMessage());
        }
    }
}
