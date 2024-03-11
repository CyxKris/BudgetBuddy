package com.cyx.budgetbuddy.Controllers;

import com.cyx.budgetbuddy.Database.TransactionDao;
import com.cyx.budgetbuddy.Utils.NumericTextFieldUtil;
import com.cyx.budgetbuddy.Views.AppView;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class TransactionPopupController implements Initializable {

    @FXML
    private DialogPane addTransactionPopup;

    @FXML
    private TextField transactionAmount;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private DatePicker date;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    TransactionDao transactionDao = new TransactionDao();

    // Logger for handling logging messages
    private static final Logger logger = Logger.getLogger(TransactionPopupController.class.getName());

    public TransactionPopupController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Adding the utilities to ensure only numerical values are accepted in the transactionAmount text field
        NumericTextFieldUtil.addNumericValidation(transactionAmount);

        categoryChoiceBox.getItems().addAll("Income", "Expense", "Subscription");

        descriptionField.textProperty().addListener((ov, oldValue, newValue) -> {
            if (descriptionField.getText().length() > 225) {
                String s = descriptionField.getText().substring(0, 225);
                descriptionField.setText(s);
            }
        });

        BooleanBinding fieldsEmpty = Bindings.createBooleanBinding(() ->   transactionAmount.getText().trim().isEmpty() ||
            descriptionField.getText().trim().isEmpty() ||
            date.getValue() == null ||
            categoryChoiceBox.getValue() == null,
            transactionAmount.textProperty(),
            descriptionField.textProperty(),
            date.valueProperty(),
            categoryChoiceBox.valueProperty()
        );

        saveButton.disableProperty().bind(fieldsEmpty);

        cancelButton.setOnAction(this::closeDialog);

    }


    private void saveData(ActionEvent event) throws SQLException {

        java.util.Date transactionDate = Date.valueOf(date.getValue());


        transactionDao.createTransaction(AppView.getUser(), Double.parseDouble(transactionAmount.getText()), transactionDate, categoryChoiceBox.getValue(), descriptionField.getText());

        closeDialog(event);
    }

    private void closeDialog(ActionEvent event) {
        Stage dialog = (Stage) ((Node)event.getSource()).getScene().getWindow();
        dialog.close();
    }
}
