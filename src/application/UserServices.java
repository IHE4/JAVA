package application;

import java.util.ArrayList;
import java.util.List;

public class UserServices {
    static List<User> users = new ArrayList<>();

    public static void addUser(User user) {
        users.add(user);
        System.out.println("User added successfully!");
    }



    
}
