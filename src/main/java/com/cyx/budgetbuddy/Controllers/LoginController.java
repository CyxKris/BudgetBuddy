package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    Button signInButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // When the signInButton is clicked, the sign in scene shows (to create new user)
        signInButton.setOnAction(event -> onLogin());
    }

    private void onLogin() {
        // Accessing the viewFactory class to show the sign in scene
        ViewFactory.showSignInScene();
    }
}
