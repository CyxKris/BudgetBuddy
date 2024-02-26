module com.cyx.budgetbuddy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    // Icon pack modules
    requires org.kordamp.ikonli.boxicons;

    opens com.cyx.budgetbuddy to javafx.fxml;
    opens com.cyx.budgetbuddy.Controllers to javafx.fxml;
    opens com.cyx.budgetbuddy.Views to javafx.fxml;
    exports com.cyx.budgetbuddy;
}