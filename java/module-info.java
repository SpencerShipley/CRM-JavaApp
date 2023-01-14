module shipley.c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens shipley.c195 to javafx.fxml;
    exports shipley.c195;
}