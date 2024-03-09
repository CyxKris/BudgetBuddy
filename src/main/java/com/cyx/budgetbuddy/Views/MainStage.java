package com.cyx.budgetbuddy.Views;

import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Singleton class representing the main stage of the application.
 */
public class MainStage extends Stage {

    // Define a static and volatile instance of MainStage to ensure thread safety
    private static volatile MainStage mainStage;

    // Private constructor to prevent external instantiation
    private MainStage() {
        // Setting the title and icon of the stage
        Image icon = new Image(String.valueOf(getClass().getResource("/Images/bb-icon.png")));
        this.getIcons().add(icon); // Add the icon to the stage
        this.setTitle("BudgetBuddy"); // Set the title of the stage
        this.setResizable(false); // Preventing users from resizing the window
    }

    // Method to get the singleton instance of MainStage
    public static MainStage getInstance() {
        MainStage stage = mainStage; // Get the current value of mainStage

        // If the stage is not initialized, create a new instance in a thread-safe manner
        if (stage == null) {
            synchronized (MainStage.class) { // Synchronize to ensure thread safety
                stage = mainStage; // Check again to avoid race condition
                // Race condition occurs when multiple threads access shared data and attempt to modify it at the same time. In the context of the getInstance() method, race condition can occur if multiple threads simultaneously check for the existence of the mainStage instance and attempt to create a new instance. The synchronized block ensures that only one thread at a time can enter this critical section, preventing the race condition and ensuring that only one instance of MainStage is created.
                if (stage == null) {
                    mainStage = stage = new MainStage(); // Create a new instance of MainStage
                }
            }
        }

        return stage; // Return the singleton instance
    }
}
