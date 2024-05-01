package application;

import java.util.HashMap;

public class AuthentificationServices {
    // Simulating a simple in-memory database with user credentials
    private static HashMap<String, String> userCredentials = new HashMap<>();

    public static boolean register(String email, String password) {
        if (userCredentials.containsKey(email)) {
            System.out.println("User already exists!");
            return false;
        } else {
            userCredentials.put(email, password);
            System.out.println("Registration successful!");
            return true;
        }
    }

    public static boolean login(String email, String password) {
        if (password.equals(userCredentials.getOrDefault(email, null))) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid credentials!");
            return false;
        }
    }
}
