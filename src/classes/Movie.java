package classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Movie {

	public static int getIndexMovieByTitle(String title) {
		int id = -1;
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT id FROM films WHERE title = ?")) {

			pstmt.setString(1, title);

			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				id = rs.getInt("id");
			}
			DatabaseConnection.closeConnection(conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public static double getMeanNote(int filmId) {
		double mean = -1.0;
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT AVG(note) FROM `commentaires` WHERE id_film = ?")) {
			stmt.setInt(1, filmId);
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				mean = resultSet.getDouble("AVG(note)");

			}
		} catch (SQLException e) {
			System.err.println("Error loading mean from database: " + e.getMessage());
		}
		return mean;
	}

    public static void listMovies() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Films")) {

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                int id  = resultSet.getInt("id");
                String titre = resultSet.getString("title");
                double price = resultSet.getDouble("price");
                String realisateur = resultSet.getString("realisator");
                Date date_publication = resultSet.getDate("publication_date");
                String theme = resultSet.getString("theme");
                String producteur = resultSet.getString("producer");
                String resume = resultSet.getString("resume");
                int categorie_age = resultSet.getInt("age_category");
                String acteurs_principaux = resultSet.getString("main_actors");
                boolean activer_commentaire = resultSet.getBoolean("enable_commentaries");
                String code = resultSet.getString("code");

                System.out.print("Title: " + titre);
                System.out.println(" " + Movie.getMeanNote(id) + "/10");
                System.out.println("Price: " + price);
                System.out.println("Director: " + realisateur);
                System.out.println("Release Year: " + date_publication);
                System.out.println("Theme: " + theme);
                System.out.println("Producer: " + producteur);
                System.out.println("Summary: " + resume);
                System.out.println("Age Category: " + categorie_age);
                System.out.println("Main Actors: " + acteurs_principaux);
                System.out.println("Comment Enabled: " + activer_commentaire);
                System.out.println("Code: " + code);
                System.out.println();
                
            }
        } catch (SQLException e) {
            System.err.println("Error loading films from database: " + e.getMessage());
        }
    }
    
    public static void addCommentary(int id_film, int id_utilisateur, String commentaire, Date date, int note) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO commentaires (id_film, id_utilisateur, commentaire, date, note) VALUES (?, ?, ?, ?, ?)")) {
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


