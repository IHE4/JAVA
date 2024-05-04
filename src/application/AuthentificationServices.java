package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthentificationServices {
    private static final String USER_FILE = "C:\\Users\\richy\\eclipse-workspace\\projet_java\\src\\application\\user.csv";
    private static final String DELIMITER = ";";
    private static List<User> users;

    static {
        users = loadUsers();
    }

    public static boolean register(String email, String password, String firstName, String lastName, String dateOfBirth, String address, String secretPhrase, boolean isAdmin, float money) {
        int nextId = users.size() + 1;
        User newUser = new User(nextId, email, password, firstName, lastName, dateOfBirth, address, secretPhrase, isAdmin, money);
        users.add(newUser);
        saveUsers();
        return true;
    }

    public static User login(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private static List<User> loadUsers() {
        List<User> loadedUsers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
        	br.readLine();
        	String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(DELIMITER);
                int id = Integer.parseInt(parts[0]);
                String email = parts[1];
                String password = parts[2];
                String firstName = parts[3];
                String lastName = parts[4];
                String dateOfBirth = parts[5];
                String address = parts[6];
                String secretPhrase = parts[7];
                boolean isAdmin = Boolean.parseBoolean(parts[8]);
                float money = (float) Double.parseDouble(parts[9]);
                User user = new User(id, email, password, firstName, lastName, dateOfBirth, address, secretPhrase, isAdmin, money);
                loadedUsers.add(user);
            }
        } catch (IOException e) {
            System.err.println("Error loading users from file: " + e.getMessage());
        }
        return loadedUsers;
    }

    private static void saveUsers() {
        try (FileWriter writer = new FileWriter(USER_FILE)) {
            for (User user : users) {
                writer.write(user.toString() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving users to file: " + e.getMessage());
        }
    }
}



