package shipley.c195;

import helper.UserDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLClientInfoException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
* Login Form Controller. First page you see when you launch the app.
*/

public class LoginForm implements Initializable {
    @FXML
    public Label location;
    @FXML
    public Label passwordLabel;
    @FXML
    public Label usernameLabel;
    @FXML
    public Label loginLabel;
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    public Button loginButton;
    @FXML
    public Button exitButton;
    private String warningTitle;
    private String warningText;

    /**
    * Translation function to translate the labels to French.
    */
    public void translate() throws IOException {
        if (lang == "French") {
            passwordLabel.setText("le mot de passe");
            usernameLabel.setText("nom d'utilisateur");
        }
    }
    Locale locale = Locale.getDefault();
    String lang = locale.getDisplayLanguage();

    /**
     * Initializes the Login Form.
     * Shows the zone ID of the user in the bottom right corner
     * First Lambda expression is used here
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            translate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        location.setText(String.valueOf(ZoneId.systemDefault()));
        exitButton.setOnAction( actionEvent -> System.exit(0));

        Locale locale = Locale.getDefault();
        resourceBundle = ResourceBundle.getBundle("languages/login", locale);
        usernameLabel.setText(resourceBundle.getString("username"));
        passwordLabel.setText(resourceBundle.getString("password"));
        loginLabel.setText(resourceBundle.getString("loginlabel"));
        loginButton.setText(resourceBundle.getString("login"));
        exitButton.setText(resourceBundle.getString("exit"));
        warningTitle = resourceBundle.getString("warningtitle");
        warningText = resourceBundle.getString("warningtext");
    }

    /**
     * Login button function.
     * Validates the user and sends all login activity to "login_activity.txt"
     * Gives warning label in French or English depending on the user's Locale
     */
    public void onLoginButton(ActionEvent actionEvent) throws IOException, SQLException {
        Alert warning = new Alert(Alert.AlertType.WARNING);
        String user = username.getText();
        String pass = password.getText();
        int userId = UserDB.userValidation(user, pass);

        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        Timestamp now = new Timestamp(System.currentTimeMillis());


            if (userId > 0) {
                openWindow(actionEvent);
                printWriter.print(user + " Success " + now + "\n");
            } else {
                warning.setTitle(warningTitle);
                warning.setContentText(warningText);
                warning.showAndWait();
                printWriter.print(user + " Failed " + now + "\n");
            }
            printWriter.close();

    }

    /**
     * Opens MainMenu
     */
    private void openWindow(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}


/*
FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        Timestamp now = new Timestamp(System.currentTimeMillis());

printWriter.print(user + "Success" + now + "\n");
printWriter.print(user + "Failed" + now + "\n");
 */