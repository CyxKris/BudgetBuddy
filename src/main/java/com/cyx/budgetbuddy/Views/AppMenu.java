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
public class AppMenu extends VBox {
    private String username;
    private final Hyperlink dashboardLink;
    private final Hyperlink transactionsLink;
    private final Hyperlink settingsLink;
    private final Button logoutButton;

    public AppMenu(String username) {
        super();
        this.username = username;
        dashboardLink = new Hyperlink("Dashboard");
        transactionsLink = new Hyperlink("Transactions");
        settingsLink = new Hyperlink("Settings");

        dashboardLink.getStyleClass().add("link");
        transactionsLink.getStyleClass().add("link");
        settingsLink.getStyleClass().add("link");

        FontIcon dashboardIcon = new FontIcon(BoxiconsSolid.DASHBOARD);
        FontIcon transactionsIcon = new FontIcon(BoxiconsSolid.SPREADSHEET);
        FontIcon settingsIcon = new FontIcon(BoxiconsSolid.COG);
        FontIcon logOutIcon = new FontIcon(BoxiconsSolid.LOG_OUT_CIRCLE);

        dashboardIcon.setIconSize(24);
        transactionsIcon.setIconSize(24);
        settingsIcon.setIconSize(24);
        logOutIcon.setIconSize(24);

        dashboardLink.setGraphic(dashboardIcon);
        transactionsLink.setGraphic(transactionsIcon);
        settingsLink.setGraphic(settingsIcon);

        logoutButton = new Button("Log Out");

        setMenuStyling();
        setMenuComponents(logOutIcon);
    }

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

    private void setMenuComponents(FontIcon logOutIcon) {
        VBox profileBox = new VBox(8);
        profileBox.setAlignment(Pos.CENTER);
        profileBox.setPrefSize(100, 200);
        profileBox.getStyleClass().add("profile");

        ImageView profileImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/sample.png"))));
        profileImage.setFitWidth(100);
        profileImage.setFitHeight(100);

        // Create a Rectangle to serve as the clipping shape
        Rectangle clip = new Rectangle(profileImage.getFitWidth(), profileImage.getFitHeight());
        clip.setArcWidth(100); // Adjust the corner radius as needed
        clip.setArcHeight(100); // Adjust the corner radius as needed

        // Clip the ImageView with the Rectangle
        profileImage.setClip(clip);

        Text greetingText = new Text("Hello, " + username + ".");
        greetingText.getStyleClass().add("greeting-text");

        profileBox.getChildren().addAll(profileImage, greetingText);

        logoutButton.getStyleClass().add("logout-button");
        logoutButton.setGraphic(logOutIcon);

        this.getChildren().addAll(profileBox, dashboardLink, transactionsLink, settingsLink, logoutButton);
    }

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
