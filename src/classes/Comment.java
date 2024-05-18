package classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Comment {
	public static boolean getEnabledCommentaryById(int id) {
		boolean isEnabled = false;
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT enable_commentaries FROM films WHERE id = ?")) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				isEnabled = resultSet.getBoolean("enable_commentaries");
			}

			
		} catch (SQLException e) {
			System.out.println("Error getting enable_commentary by id: " + e.getMessage());
		}
		return isEnabled;
	}
	
    public static void ViewCommentsByMovieId(int movieId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT commentaires.commentaire, commentaires.date, commentaires.note, users.firstName FROM commentaires LEFT JOIN users ON commentaires.id_utilisateur = users.id WHERE id_film=?")) {
            pstmt.setInt(1, movieId);
            ResultSet rs = pstmt.executeQuery();
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
