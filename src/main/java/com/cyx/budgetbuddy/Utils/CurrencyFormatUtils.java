package com.cyx.budgetbuddy.Utils;
import java.text.DecimalFormat;

public class CurrencyFormatUtils {
    // Define a DecimalFormat object with a specific pattern for formatting currency
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("₦#,##0.0");

    // Method to format a double value representing currency into a string using the defined pattern
    public static String formatCurrency(double amount) {
        // Format the given amount using the DecimalFormat object
        return DECIMAL_FORMAT.format(amount);
    }
}
