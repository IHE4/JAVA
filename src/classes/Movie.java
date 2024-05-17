package classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public  class Movie {

	public static int getIndexMovieByTitle(String title) {
		int movie_index = 0;
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT id_film FROM films WHERE title = ?")) {
			statement.setString(1, title);
			ResultSet resultSet = statement.executeQuery();
			movie_index = resultSet.getInt("id_film");
		} catch (SQLException e) {
			System.out.println("Error getting movie by title: " + e.getMessage());
		}
		return movie_index;
	}
	
    public static void commentsByMovieId(int movieId) throws SQLException {
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

    public static void listMovies() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Films")) {

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                //int id  = resultSet.getInt("id");
                String titre = resultSet.getString("title");
                String realisateur = resultSet.getString("realisator");
                Date date_publication = resultSet.getDate("publication_date");
                String theme = resultSet.getString("theme");
                String producteur = resultSet.getString("producer");
                String resume = resultSet.getString("resume");
                int categorie_age = resultSet.getInt("age_category");
                String acteurs_principaux = resultSet.getString("main_actors");
                boolean activer_commentaire = resultSet.getBoolean("enable_commentaries");
                String code = resultSet.getString("code");

                System.out.println("Title: " + titre);
                System.out.println("Director: " + realisateur);
                System.out.println("Release Year: " + date_publication);
                System.out.println("Theme: " + theme);
                System.out.println("Producer: " + producteur);
                System.out.println("Summary: " + resume);
                System.out.println("Age Category: " + categorie_age);
                System.out.println("Main Actors: " + acteurs_principaux.split(","));
                System.out.println("Comment Enabled: " + activer_commentaire);
                System.out.println("Code: " + code);
                System.out.println();
            }
        } catch (SQLException e) {
            System.err.println("Error loading films from database: " + e.getMessage());
        }
    }
    
    public void addCommentary(int id_film, int id_utilisateur, String commentaire, Date date, int note) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO commentaires (film_id, utilisateur_id, commentaire, date, note) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setInt(1, id_film);
            stmt.setInt(2, id_utilisateur);
            stmt.setString(3, commentaire);
            stmt.setDate(4, date);
            stmt.setInt(5, note);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error adding commentary: " + e.getMessage());
        }
    }
    
    
}


