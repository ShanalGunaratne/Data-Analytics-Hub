package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Model {
    private Map<String, UserProfile> userProfiles = new HashMap<>();
    private UserProfile activeUser = null;
    private List<SocialMediaPost> allPosts = new ArrayList<>();

    public void addUserProfile(String username, String password, String firstName, String lastName) {
        if (!userProfiles.containsKey(username)) {
            UserProfile userProfile = new UserProfile(username, password, firstName, lastName);
            userProfiles.put(username, userProfile);
            System.out.println("Registration successful");
        } else {
            System.out.println("Username already exists");
        }
    }

    public boolean checkLogin(String username, String password) {
        if (userProfiles.containsKey(username)) {
            UserProfile userProfile = userProfiles.get(username);
            if (userProfile.getPassword().equals(password)) {
                activeUser = userProfile;
                System.out.println("Login successful");
                return true;
            } else {
                System.out.println("Incorrect password");
            }
        } else {
            System.out.println("User not found");
        }
        return false;
    }

    public UserProfile getActiveUser() {
        return activeUser;
    }
    
 // New method to retrieve all posts
    public List<SocialMediaPost> getAllPosts() {
        return allPosts;
    }

    // Add a method to add a post to the allPosts list
    public void addPostToAll(SocialMediaPost post) {
        allPosts.add(post);
    }
}