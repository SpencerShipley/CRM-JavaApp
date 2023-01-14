package shipley.c195;

import helper.AppointmentDB;
import helper.CustomerDB;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.TimeZone;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LoginForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    public static void main (String[]args) throws SQLException {
        JDBC.openConnection();
        try {
            CustomerDB.getAllCustomersSQL();
            AppointmentDB.getAllAppointmentsSQL();
            TimeValues.insertAllTime();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        launch();
    }

}