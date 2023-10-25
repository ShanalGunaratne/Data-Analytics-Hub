package application;

import java.util.ArrayList;
import java.util.List;

public class UserProfile {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private List<SocialMediaPost> socialMediaPosts = new ArrayList<>();
    private boolean isVIP;

    public UserProfile(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isVIP = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<SocialMediaPost> getSocialMediaPosts() {
        return socialMediaPosts;
    }

    public void addSocialMediaPost(SocialMediaPost post) {
        // Add the post only if it has a unique ID
        if (isUniquePostId(post.getId())) {
            socialMediaPosts.add(post);
        } else {
            System.out.println("A post with the same ID already exists. Please choose a unique ID.");
        }
    }

    private boolean isUniquePostId(String postId) {
        for (SocialMediaPost post : socialMediaPosts) {
            if (post.getId().equals(postId)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean isVIP) {
        this.isVIP = isVIP;
    }
}