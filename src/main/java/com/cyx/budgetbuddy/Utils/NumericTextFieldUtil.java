package com.cyx.budgetbuddy.Utils;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * Utility class for enabling numeric input validation on JavaFX TextFields.
 */
public class NumericTextFieldUtil {

    /**
     * Adds numeric input validation to the provided TextField.
     *
     * @param textField TextField to which numeric input validation will be applied
     */
    public static void addNumericValidation(TextField textField) {
        // Add an event filter to the TextField to intercept key typed events
        textField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            // If the character typed is not numeric, consume the event to prevent it from being processed
            if (!isNumeric(event.getCharacter())) {
                event.consume();
            }
        });
    }

    /**
     * Checks if the provided string represents a numeric value.
     *
     * @param str String to be checked for numeric value
     * @return true if the string represents a numeric value, false otherwise
     */
    private static boolean isNumeric(String str) {
        // Regular expression to match numeric values (including negative numbers and decimals)
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
