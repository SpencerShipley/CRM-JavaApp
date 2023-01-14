package shipley.c195;

import helper.CountriesDB;
import helper.CustomerDB;
import helper.StateDB;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * AddCustomerForm
 */
public class AddCustomerForm {
    @FXML
    public Label stateLabel;
    @FXML
    public TextField customerID;
    @FXML
    public TextField name;
    @FXML
    public TextField address;
    @FXML
    public TextField zip;
    @FXML
    public ChoiceBox state;
    @FXML
    public ChoiceBox country;
    @FXML
    public TextField phoneNumber;
    @FXML
    public Button cancelButton;

    public AddCustomerForm() throws SQLException {
    }

    /**
     * initialized the form.
     * fourth lambda expression
     */
    public void initialize() throws SQLException {
        country.setItems(CountriesDB.getAllCountryNames());
        String convertedID = String.valueOf(newID);
        customerID.setText(convertedID);

        cancelButton.setOnAction( actionEvent -> {
            try {
                closeWindow(actionEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    ObservableList<Customer> customerList = CustomerDB.getAllCustomers();
    int newID = customerList.size() + 1;

    /**
     * populates states or provinces based on country selection
     */
    public void onCountry(ActionEvent actionEvent) throws SQLException {
        if (country.getValue() != null) {
            String countryTemp = country.getValue().toString();
            Country country = CountriesDB.getCountryByName(countryTemp);
            int countryID = country.getCountryID();
            state.setItems(StateDB.getAllStatesByCountryID(countryID));
        } else {
            return;
        }

    }

    /**
     * saves data and takes user back to the MainMenu
     * or throws error
     */
    public void onSaveButton(ActionEvent actionEvent) throws IOException, SQLException {
        Alert warning = new Alert(Alert.AlertType.WARNING);
        int id = Integer.parseInt(customerID.getText());
        String tempName = name.getText();
        String tempAddress = address.getText();
        String tempZip = zip.getText();
        String tempPhone = phoneNumber.getText();
        String tempStateValue = state.getValue().toString();
        State stateName = StateDB.getStateByStateName(tempStateValue);
        int tempState = stateName.getState();
        String tempCountry = country.getValue().toString();
        Customer newCustomer = new Customer(id, tempName,tempAddress,tempZip,tempPhone, tempStateValue, tempCountry);
        CustomerDB.addCustomer(newCustomer);
        closeWindow(actionEvent);
    }

    /**
     * Closes Window
     */
    private void closeWindow(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}
