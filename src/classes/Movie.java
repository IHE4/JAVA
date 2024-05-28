package classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Movie {
		
	public static void getTopMovies() {
		int id = -1;
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT f.id, f.title, AVG(c.note) as moyenne_note FROM films f JOIN commentaires c ON f.id = c.id_film GROUP BY f.title ORDER BY moyenne_note DESC LIMIT 3;")) {
			ResultSet resultSet = pstmt.executeQuery();      
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No movies found");
                return;
            }
            while (resultSet.next()) {
            	id = resultSet.getInt("id");
                String titre = resultSet.getString("title");
                

                System.out.print("Title: " + titre);
                System.out.println(" " + Movie.getMeanNote(id) + "/10");
                System.out.println();  
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int getIndexMovieByTitle(String title) {
		int id = -1;
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT id FROM films WHERE title = ?")) {

			pstmt.setString(1, title);

			ResultSet rs = pstmt.executeQuery();
			
			if (!rs.isBeforeFirst()) {
                System.out.println("No movies found for this title");
            }
			while (rs.next()) {
				id = rs.getInt("id");
			}
			conn.close();

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
			
			if (!resultSet.isBeforeFirst()) {
                System.out.println("No Average Found");   
            }
			
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
            
            if (!resultSet.isBeforeFirst()) {
                System.out.println("No movies found");
                return;
                
            }
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
    
    public static void addFilm(String title, String realisator, Date publicationDate, String theme, String producer, String resume, int ageCategory, String mainActors, boolean enableCommentaries, String code, double price) throws SQLException {
        String query = "INSERT INTO films (title, realisator, publication_date, theme, producer, resume, age_category, main_actors, enable_commentaries, code, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, realisator);
            stmt.setDate(3, publicationDate);
            stmt.setString(4, theme);
            stmt.setString(5, producer);
            stmt.setString(6, resume);
            stmt.setInt(7, ageCategory);
            stmt.setString(8, mainActors);
            stmt.setBoolean(9, enableCommentaries);
            stmt.setString(10, code);
            stmt.setDouble(11, price);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("New film added successfully.");
            } else {
                System.out.println("Failed to add new film.");
            }

        } catch (SQLException e) {
            System.err.println("Error adding film: " + e.getMessage());
        }
    }
    
    public static void removeFilm(int id) throws SQLException {
        String query = "DELETE FROM films WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Film removed successfully.");
            } else {
                System.out.println("Failed to remove film.");
            }
        } catch (SQLException e) {
            System.err.println("Error removing film: " + e.getMessage());
        }
    }
    
    public static void modifyFilm(int id, String title, String realisator, String publication_date, String theme, String producer, String resume, int age_category, String main_actors, boolean enable_commentaries, String code, double price) throws SQLException {
        String query = "UPDATE films SET title = ?, realisator = ?, publication_date = ?, theme = ?, producer = ?, resume = ?, age_category = ?, main_actors = ?, enable_commentaries = ?, code = ?, price = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, realisator);
            stmt.setDate(3, java.sql.Date.valueOf(publication_date));
            stmt.setString(4, theme);
            stmt.setString(5, producer);
            stmt.setString(6, resume);
            stmt.setInt(7, age_category);
            stmt.setString(8, main_actors);
            stmt.setBoolean(9, enable_commentaries);
            stmt.setString(10, code);
            stmt.setDouble(11, price);
            stmt.setInt(12, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Film modified successfully.");
            } else {
                System.out.println("Failed to modify film.");
            }
        } catch (SQLException e) {
            System.err.println("Error modifying film: " + e.getMessage());
        }
    }
    
    
}


