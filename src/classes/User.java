package classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private String email;
    private String password;
    private String lastName;
    private String firstName;
    private Date dateOfBirth;
    private String address;
    private String secretPhrase;
    private boolean isAdmin;
    private double money;

    public User(int id, String email, String password, String lastName, String firstName, Date dateOfBirth,
                String address, String secretPhrase, boolean isAdmin, double money) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.secretPhrase = secretPhrase;
        this.isAdmin = isAdmin;
        this.money = money;
    }

    public User() {
        // Default constructor
    }

    public static User login(String email, String password) {
        User user = null;
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(
                            resultSet.getInt("id"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("lastName"),
                            resultSet.getString("firstName"),
                            resultSet.getDate("dateOfBirth"),
                            resultSet.getString("address"),
                            resultSet.getString("secretPhrase"),
                            resultSet.getBoolean("isAdmin"),
                            resultSet.getDouble("money")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static User addUser(String email, String password, String firstName, String lastName, Date dateOfBirth,
                                String address, String secretPhrase, boolean isAdmin, double money) {
        User user = null;
        String query = "INSERT INTO Users (email, password, firstName, lastName, dateOfBirth, address, secretPhrase, isAdmin, money) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, firstName);
            stmt.setString(4, lastName);
            stmt.setDate(5, dateOfBirth);
            stmt.setString(6, address);
            stmt.setString(7, secretPhrase);
            stmt.setBoolean(8, isAdmin);
            stmt.setDouble(9, money);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        user = new User(userId, email, password, lastName, firstName, dateOfBirth, address, secretPhrase, isAdmin, money);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
        }
        return user;
    }

    public static void removeUser(int userId) {
        String query = "DELETE FROM Users WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User with ID " + userId + " has been deleted.");
            } else {
                System.out.println("No user with ID " + userId + " found.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }

    public static void modifyUser(int userId, String email, String password, String firstName, String lastName, Date dateOfBirth,
                                  String address, String secretPhrase, boolean isAdmin, double money) {
        String query = "UPDATE Users SET email = ?, password = ?, firstName = ?, lastName = ?, dateOfBirth = ?, address = ?, secretPhrase = ?, isAdmin = ?, money = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, firstName);
            stmt.setString(4, lastName);
            stmt.setDate(5, dateOfBirth);
            stmt.setString(6, address);
            stmt.setString(7, secretPhrase);
            stmt.setBoolean(8, isAdmin);
            stmt.setDouble(9, money);
            stmt.setInt(10, userId);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User with ID " + userId + " has been updated.");
            } else {
                System.out.println("No user with ID " + userId + " found.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSecretPhrase() {
        return secretPhrase;
    }

    public void setSecretPhrase(String secretPhrase) {
        this.secretPhrase = secretPhrase;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
