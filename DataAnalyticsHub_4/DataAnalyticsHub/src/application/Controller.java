package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void handleLogin(String username, String password) {
        if (model.checkLogin(username, password)) {
            view.showDashboard(model.getActiveUser());
        }
    }

    public void handleRegistration(String username, String password, String firstName, String lastName) {
        model.addUserProfile(username, password, firstName, lastName);
    }
    
    public void handleAddPost(SocialMediaPost post) {
        UserProfile activeUser = model.getActiveUser();
        if (activeUser != null) {
            activeUser.addSocialMediaPost(post);
            System.out.println("Post added successfully!");
        } else {
            System.out.println("Please log in to add a post.");
        }
    }

    public void handleUpdateProfile(String username, String password, String firstName, String lastName) {
        UserProfile activeUser = model.getActiveUser();
        if (activeUser != null) {
            // Update user profile
            activeUser.setUsername(username);
            activeUser.setPassword(password);
            activeUser.setFirstName(firstName);
            activeUser.setLastName(lastName);
            System.out.println("Profile updated successfully!");
        } else {
            System.out.println("Please log in to update your profile.");
        }
    }
    public List<SocialMediaPost> handleRetrieveTopNPosts(int n, boolean retrieveAllUsers) {
        if (retrieveAllUsers) {
            // Retrieve top N posts from all users
            List<SocialMediaPost> allPosts = model.getAllPosts();

            // Sort the posts by #likes in descending order
            allPosts.sort(Comparator.comparingInt(SocialMediaPost::getLikes).reversed());

            // Return the top N posts
            return allPosts.stream().limit(n).collect(Collectors.toList());
        } else {
            UserProfile activeUser = model.getActiveUser();
            if (activeUser != null) {
                // Retrieve top N posts for the active user
                List<SocialMediaPost> userPosts = activeUser.getSocialMediaPosts();

                // Sort the posts by #likes in descending order
                userPosts.sort(Comparator.comparingInt(SocialMediaPost::getLikes).reversed());

                // Return the top N posts
                return userPosts.stream().limit(n).collect(Collectors.toList());
            }
        }
        return null; // Return null if no posts are found
    }
       
    public SocialMediaPost handleRetrievePost(String postId) {
        UserProfile activeUser = model.getActiveUser();
        if (activeUser != null) {
            // Retrieve the post by ID from the active user's posts
            for (SocialMediaPost post : activeUser.getSocialMediaPosts()) {
                if (post.getId().equals(postId)) {
                    return post;
                }
            }
        }
        return null; // Post not found
    }
    public boolean handleRemovePost(String postId) {
        UserProfile activeUser = model.getActiveUser();
        if (activeUser != null) {
            // Get the list of social media posts from the active user
            List<SocialMediaPost> userPosts = activeUser.getSocialMediaPosts();

            // Find and remove the post by ID
            for (SocialMediaPost post : userPosts) {
                if (post.getId().equals(postId)) {
                    userPosts.remove(post);
                    return true; // Post removed successfully
                }
            }
        }
        return false; // Post not found
    }
    
    public boolean handleExportPost(String postId, String folderPath, String fileName) {
        SocialMediaPost post = handleRetrievePost(postId);
        if (post != null) {
            try {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Choose Destination Folder");
                Stage stage = new Stage();
                File selectedDirectory = directoryChooser.showDialog(stage);

                if (selectedDirectory != null) {
                    // Create a CSV representation of the post
                    String postCsv = postToCsv(post);

                    // Create a file path for export
                    String exportFilePath = selectedDirectory.getAbsolutePath() + File.separator + fileName + ".csv";

                    // Write the CSV data to the file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(exportFilePath))) {
                        writer.write(postCsv);
                    }

                    return true; // Export successful
                } else {
                    // User canceled folder selection
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false; // Export failed
    }


    // Helper method to convert a SocialMediaPost to CSV format
    private String postToCsv(SocialMediaPost post) {
        // Customize the format as needed, e.g., using commas or semicolons as separators
        return "ID,Content,Author,Likes,Shares\n" +
               post.getId() + "," +
               post.getContent() + "," +
               post.getAuthor() + "," +
               post.getLikes() + "," +
               post.getShares();
    }
    
    public void handleUpgradeToVIP(UserProfile user) {
        // You can implement additional logic here, but for simplicity, we assume
        // the user becomes a VIP once they agree (e.g., by clicking "yes").
        user.setVIP(true);

        // Inform the user about the VIP upgrade
        System.out.println("Congratulations! You are now a VIP user.");

        // Update the view to reflect the VIP status
        view.showDashboard(user);
    }

}