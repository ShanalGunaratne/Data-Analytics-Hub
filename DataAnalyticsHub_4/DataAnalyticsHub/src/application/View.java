package application;

import java.io.File;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class View {
    private Stage primaryStage;
    private Model model;
    private Controller controller;

    private TextField usernameField;
    private PasswordField passwordField;
    private TextField newUsernameField;
    private PasswordField newPasswordField;
    private TextField firstNameField;
    private TextField lastNameField;

    public View(Stage primaryStage, Model model) {
        this.primaryStage = primaryStage;
        this.model = model;
        primaryStage.setTitle("Data Analytics Hub");
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void showLoginScreen() {
        Label titleLabel = new Label("Data Analytics Hub");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label loginLabel = new Label("Login");
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            controller.handleLogin(username, password);
        });

        Label registerLabel = new Label("Register");
        TextField newUsernameField = new TextField();
        PasswordField newPasswordField = new PasswordField();
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e ->
                controller.handleRegistration(
                        newUsernameField.getText(),
                        newPasswordField.getText(),
                        firstNameField.getText(),
                        lastNameField.getText()
                ));

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.add(loginLabel, 0, 1);
        gridPane.addRow(2, new Label("Username:"), usernameField);
        gridPane.addRow(3, new Label("Password:"), passwordField);
        gridPane.add(loginButton, 0, 4);
        gridPane.add(registerLabel, 0, 5);
        gridPane.addRow(6, new Label("Username:"), newUsernameField);
        gridPane.addRow(7, new Label("Password:"), newPasswordField);
        gridPane.addRow(8, new Label("First Name:"), firstNameField);
        gridPane.addRow(9, new Label("Last Name:"), lastNameField);
        gridPane.add(registerButton, 0, 10);

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showDashboard(UserProfile userProfile) {
        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("Dashboard");

        Label welcomeLabel = new Label("Welcome, " + userProfile.getFirstName() + " " + userProfile.getLastName() + "!");
        Label dashboardLabel = new Label("This is your dashboard. Please maximise to view everything");

        // Create buttons for adding a social media post and editing the profile
        Button addPostButton = new Button("Add Social Media Post");
        Button editProfileButton = new Button("Edit Profile");
        Button logOutButton = new Button("Log Out");
        Button upgradeToVIPButton = new Button("Upgrade to VIP"); // Add the Upgrade to VIP button

        addPostButton.setOnAction(event -> {
            showAddPostForm(); // Show the form to add a post
        });

        editProfileButton.setOnAction(event -> {
            showEditProfileForm(); // Show the form to edit the profile
        });

        logOutButton.setOnAction(event -> {
            showLoginScreen(); // Log out and return to the login screen
            dashboardStage.close(); // Close the dashboard window
        });
        
        addPostButton.setOnAction(event -> {
            showAddPostForm(); // Show the form to add a post
        });

        editProfileButton.setOnAction(event -> {
            showEditProfileForm(); // Show the form to edit the profile
        });
        
        upgradeToVIPButton.setOnAction(event -> {
            controller.handleUpgradeToVIP(userProfile); // Handle the upgrade to VIP action
        });
        
     // Create a button for retrieving a post by ID
        
        Button retrievePostButton = new Button("Retrieve Post by ID");
        TextField postIdField = new TextField();
        Label retrievedPostLabel = new Label();

        retrievePostButton.setOnAction(event -> {
            String postId = postIdField.getText();
            SocialMediaPost retrievedPost = controller.handleRetrievePost(postId);

            if (retrievedPost != null) {
                // Display the retrieved post
                retrievedPostLabel.setText("Retrieved Post:\n" +
                    "ID: " + retrievedPost.getId() + "\n" +
                    "Content: " + retrievedPost.getContent() + "\n" +
                    "Author: " + retrievedPost.getAuthor() + "\n" +
                    "Likes: " + retrievedPost.getLikes() + "\n" +
                    "Shares: " + retrievedPost.getShares());
            } else {
                // Post not found
                retrievedPostLabel.setText("Post not found.");
            }
        });
        
     // Create a button for removing a post by ID
        
        Button removePostButton = new Button("Remove Post by ID");
        TextField postIdField1 = new TextField();
        Label removedPostLabel = new Label();

        removePostButton.setOnAction(event -> {
            String postId = postIdField1.getText();
            boolean isRemoved = controller.handleRemovePost(postId);

            if (isRemoved) {
                // Display a message indicating the post was removed
                removedPostLabel.setText("Post removed successfully.");
            } else {
                // Post not found
                removedPostLabel.setText("Post not found.");
            }
        });
        
     // Create a button for retrieving top N posts
        Button retrieveTopNPostsButton = new Button("Retrieve Top N Posts");
        TextField nValueField = new TextField();
        CheckBox allUsersCheckbox = new CheckBox("Retrieve from all users");
        Label retrievedTopNPostsLabel = new Label();

        retrieveTopNPostsButton.setOnAction(event -> {
            int n = Integer.parseInt(nValueField.getText());
            boolean retrieveAllUsers = allUsersCheckbox.isSelected();

            List<SocialMediaPost> topNPosts = controller.handleRetrieveTopNPosts(n, retrieveAllUsers);

            if (!topNPosts.isEmpty()) {
                // Display the retrieved top N posts
                StringBuilder topNPostsText = new StringBuilder("Top N Posts:\n");
                for (SocialMediaPost post : topNPosts) {
                    topNPostsText.append("ID: ").append(post.getId()).append("\n");
                    topNPostsText.append("Content: ").append(post.getContent()).append("\n");
                    topNPostsText.append("Author: ").append(post.getAuthor()).append("\n");
                    topNPostsText.append("Likes: ").append(post.getLikes()).append("\n");
                    topNPostsText.append("Shares: ").append(post.getShares()).append("\n\n");
                }
                retrievedTopNPostsLabel.setText(topNPostsText.toString());
            } else {
                // No posts found
                retrievedTopNPostsLabel.setText("No posts found.");
            }
        });
        
     // Create a button for exporting a post to a file
        Button exportPostButton = new Button("Export Post to CSV");
        TextField postIdFieldForExport = new TextField();
        TextField folderPathField = new TextField(); // Field for entering folder path
        TextField fileNameField = new TextField();
        Label exportStatusLabel = new Label();

        exportPostButton.setOnAction(event -> {
            String postIdForExport = postIdFieldForExport.getText();
            String folderPath = folderPathField.getText(); // Get the folder path from the field
            String fileName = fileNameField.getText();

            boolean exportResult = controller.handleExportPost(postIdForExport, folderPath, fileName);

            if (exportResult) {
                exportStatusLabel.setText("Post exported successfully.");
            } else {
                exportStatusLabel.setText("Post export failed. Check the post ID or folder path.");
            }
        });

        // Create a button to open a folder chooser dialog
        Button chooseFolderButton = new Button("Choose Folder");
        chooseFolderButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(dashboardStage);
            if (selectedDirectory != null) {
                folderPathField.setText(selectedDirectory.getAbsolutePath());
            }
        });
        
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(welcomeLabel, dashboardLabel, addPostButton, editProfileButton, postIdField, retrievePostButton, retrievedPostLabel,postIdField1, removePostButton, removedPostLabel, 
        nValueField,retrieveTopNPostsButton, allUsersCheckbox,retrievedTopNPostsLabel,
        new Label("Enter Post ID for Export:"),postIdFieldForExport,
        new Label("Choose Folder Path:"),folderPathField,chooseFolderButton,
        exportPostButton,
        exportStatusLabel, upgradeToVIPButton,logOutButton);

        dashboardStage.setScene(new Scene(vbox, 600, 600));
        dashboardStage.show();
    }
    
    public void showAddPostForm() {
        Stage postFormStage = new Stage();
        postFormStage.setTitle("Add Social Media Post");

        Label titleLabel = new Label("Add Social Media Post");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField postIdField = new TextField();
        TextField postContentField = new TextField();
        TextField postAuthorField = new TextField();
        TextField likesField = new TextField();
        TextField sharesField = new TextField();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            String postId = postIdField.getText();
            String postContent = postContentField.getText();
            String postAuthor = postAuthorField.getText();
            int likes = 0;
            int shares = 0;

            try {
                likes = Integer.parseInt(likesField.getText());
                shares = Integer.parseInt(sharesField.getText());
            } catch (NumberFormatException e) {
                System.out.println("Likes and shares must be numbers.");
                return;
            }

            SocialMediaPost newPost = new SocialMediaPost(postId, postContent, postAuthor, likes, shares);
            controller.handleAddPost(newPost);
            postFormStage.close();
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.addRow(1, new Label("Post ID:"), postIdField);
        gridPane.addRow(2, new Label("Content:"), postContentField);
        gridPane.addRow(3, new Label("Author:"), postAuthorField);
        gridPane.addRow(4, new Label("Likes:"), likesField);
        gridPane.addRow(5, new Label("Shares:"), sharesField);
        gridPane.add(submitButton, 0, 6);

        Scene scene = new Scene(gridPane);
        postFormStage.setScene(scene);
        postFormStage.show();
    }
    
    public void showEditProfileForm() {
        Stage profileFormStage = new Stage();
        profileFormStage.setTitle("Edit Profile");

        Label titleLabel = new Label("Edit Profile");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();

            controller.handleUpdateProfile(username, password, firstName, lastName);
            profileFormStage.close();
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.addRow(1, new Label("Username:"), usernameField);
        gridPane.addRow(2, new Label("Password:"), passwordField);
        gridPane.addRow(3, new Label("First Name:"), firstNameField);
        gridPane.addRow(4, new Label("Last Name:"), lastNameField);
        gridPane.add(submitButton, 0, 5);

        Scene scene = new Scene(gridPane);
        profileFormStage.setScene(scene);
        profileFormStage.show();
    }

}