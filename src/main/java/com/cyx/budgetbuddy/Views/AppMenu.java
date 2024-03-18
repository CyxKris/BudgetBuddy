package com.cyx.budgetbuddy.Views;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.kordamp.ikonli.boxicons.BoxiconsSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;
/**
 * Custom menu component for the application sidebar.
 * Displays navigation links and user profile information.
 */
public class AppMenu extends VBox {

    private static ImageView profileImage;
    private static String username;
    private final Hyperlink dashboardLink;
    private final Hyperlink transactionsLink;
    private final Hyperlink settingsLink;
    private final Button logoutButton;

    /**
     * Constructs the application menu with navigation links and logout button.
     * @param user_name The username of the currently logged-in user.
     */
    public AppMenu(String user_name) {
        super();
        username = user_name;
        dashboardLink = new Hyperlink("Dashboard");
        transactionsLink = new Hyperlink("Transactions");
        settingsLink = new Hyperlink("Settings");

        // Set style classes for links
        dashboardLink.getStyleClass().add("link");
        transactionsLink.getStyleClass().add("link");
        settingsLink.getStyleClass().add("link");

        // Initialize icons for menu items
        FontIcon dashboardIcon = new FontIcon(BoxiconsSolid.DASHBOARD);
        FontIcon transactionsIcon = new FontIcon(BoxiconsSolid.SPREADSHEET);
        FontIcon settingsIcon = new FontIcon(BoxiconsSolid.COG);
        FontIcon logOutIcon = new FontIcon(BoxiconsSolid.LOG_OUT_CIRCLE);

        // Set icon sizes
        dashboardIcon.setIconSize(24);
        transactionsIcon.setIconSize(24);
        settingsIcon.setIconSize(24);
        logOutIcon.setIconSize(24);

        // Set icons for menu links
        dashboardLink.setGraphic(dashboardIcon);
        transactionsLink.setGraphic(transactionsIcon);
        settingsLink.setGraphic(settingsIcon);

        logoutButton = new Button("Log Out");

        // Apply styling to the menu
        setMenuStyling();
        // Add menu components with appropriate styling
        setMenuComponents(logOutIcon);
    }


    /**
     * Apply styling to the menu.
     */
    private void setMenuStyling() {
        this.setAlignment(Pos.CENTER);
        this.setPrefSize(200, 640);
        this.setMinSize(200, 640);
        this.setMaxSize(200, 640);
        this.getStyleClass().add("menu-bar");
        this.setSpacing(60);
        this.setPadding(new Insets(20, 0, 20, 0));
        this.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("/Css/app.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource("/Css/app-menu.css")).toExternalForm()
        );
    }

    /**
     * Add menu components with appropriate styling.
     * @param logOutIcon The icon for the logout button.
     */
    private void setMenuComponents(FontIcon logOutIcon) {
        // Create user profile box
        VBox profileBox = new VBox(8);
        profileBox.setAlignment(Pos.CENTER);
        profileBox.setPrefSize(100, 200);
        profileBox.getStyleClass().add("profile");

        // Load and display user profile image
//        profileImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(AppView.getUser().getProfileImagePath()))));
        if (AppView.getUser().getProfileImagePath().equals("/Images/default.png")) {
            profileImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(AppView.getUser().getProfileImagePath()))));
        } else {
            profileImage = new ImageView(new Image(AppView.getUser().getProfileImagePath()));
        }

        profileImage.setFitWidth(100);
        profileImage.setFitHeight(100);

        // Create a Rectangle to serve as the clipping shape for the profile image
        Rectangle clip = new Rectangle(profileImage.getFitWidth(), profileImage.getFitHeight());
        clip.setArcWidth(100); // Adjust the corner radius as needed
        clip.setArcHeight(100); // Adjust the corner radius as needed

        // Clip the ImageView with the Rectangle
        profileImage.setClip(clip);

        // Set greeting text and add to profile box
        Text greetingText = new Text("Hello, " + username + ".");
        greetingText.getStyleClass().add("greeting-text");

        // Add profile image and greeting text to profile box
        profileBox.getChildren().addAll(profileImage, greetingText);

        // Apply styling to logout button
        logoutButton.getStyleClass().add("logout-button");
        logoutButton.setGraphic(logOutIcon);

        // Add menu components to menu
        this.getChildren().addAll(profileBox, dashboardLink, transactionsLink, settingsLink, logoutButton);
    }

    public static void setProfileImage(Image newProfileImage) {
        profileImage.setImage(newProfileImage);
    }

    public static void setUsername(String newUsername) {
        username = newUsername;
    }

    // Getters for menu components
    public Hyperlink getDashboardLink() {
        return dashboardLink;
    }

    public Hyperlink getTransactionsLink() {
        return transactionsLink;
    }

    public Hyperlink getSettingsLink() {
        return settingsLink;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }
}
