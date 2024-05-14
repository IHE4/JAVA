package application;

public class AdminService {
    public static void addMovie(Film film) {
        MovieService.addFilm(film);
    }

    public static void removeMovie(String code) {
        // Implementation of movie removal logic
        System.out.println("Movie with code " + code + " removed successfully.");
    }

    public static void displayAllUsers() {
        // Assuming a method in UserService that can return all users
        for (User user : UserServices.users) {
            System.out.println("User: " + user.getEmail());
        }
    }

    
}
