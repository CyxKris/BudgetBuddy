module com.cyx.budgetbuddy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // JavaFx controls
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    // Icon pack modules
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.boxicons;
    // ORMLite modules
    requires ormlite.jdbc;

    opens com.cyx.budgetbuddy to javafx.fxml;
    opens com.cyx.budgetbuddy.Controllers to javafx.fxml;
    opens com.cyx.budgetbuddy.Views to javafx.fxml;
    exports com.cyx.budgetbuddy;
}