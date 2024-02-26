package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;

import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    @FXML
    Hyperlink logInLink;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logInLink.setOnAction(event -> backToLogIn());
    }

    private void backToLogIn() {
        ViewFactory.showLogInScene();
    }
}
