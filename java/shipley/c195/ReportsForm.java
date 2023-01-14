package shipley.c195;

import helper.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ResourceBundle;


/**
 * ReportsForm controller
 * Generates the 2 requires reports and also generates a report for
 * total number of appointments by customer ID
 */
public class ReportsForm implements Initializable {
    @FXML
    public Button returnButton;
    @FXML
    public Label monthReport;
    @FXML
    public Label typeReport;
    @FXML
    public Label customerReport;
    @FXML
    public ChoiceBox contactBox;
    @FXML
    public ChoiceBox customerBox;
    @FXML
    public ChoiceBox monthBox;
    @FXML
    public ChoiceBox typeBox;
    @FXML
    public TableView<Appointment> appointmentTable;
    @FXML
    public TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML
    public TableColumn<Appointment, String> titleColumn;
    @FXML
    public TableColumn<Appointment, String> descriptionColumn;
    @FXML
    public TableColumn<Appointment, String> typeColumn;
    @FXML
    public TableColumn<Appointment, Timestamp> startColumn;
    @FXML
    public TableColumn<Appointment, Timestamp> endColumn;
    @FXML
    public TableColumn<Appointment, Integer> customerIdColumn;

    /**
     * Initializes the ReportsForm
     * Contains the 2nd lambda expression
     */

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactBox.setItems(ContactDB.getAllContactIDs());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            customerBox.setItems(CustomerDB.getAllCustomerIDs());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        insertAllMonths();
        try {
            insertAllTypes();
            typeBox.setItems(getTypes());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        monthBox.setItems(getMonths());
        returnButton.setOnAction( actionEvent -> {
            try {
                closeWindow(actionEvent);
                monthBox.getItems().clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    private static ObservableList<Integer> monthList= FXCollections.observableArrayList();
    public static ObservableList<Integer> getMonths(){
        return monthList;
    }

    /**
     * Creates data to add the to monthBox
     */
    public static ObservableList<Integer> insertAllMonths() {
        for (int i = 1; i<13; i++){
            monthList.add(Integer.valueOf(i));

        }

        return monthList;

    }

    private static ObservableList<String> typeList= FXCollections.observableArrayList();
    public static ObservableList<String> getTypes(){
        return typeList;
    }

    /**
     * Gathers data to add to typeBox
     */
    public static ObservableList<String> insertAllTypes() throws SQLException {
        ObservableList<Appointment> allAppointments = AppointmentDB.getAllAppointments();
        for (Appointment appointment : allAppointments){
            String tempType = appointment.getType();
            typeList.add(tempType);

        }

        return typeList;

    }

    /**
     * Generates report based on the total number of appointments for a Customer
     * This is the added report
     */
    public void onCustomerBox(ActionEvent actionEvent) throws IOException, SQLException {
        ObservableList<Appointment> allAppointments = AppointmentDB.getAllAppointments();
        ObservableList<Appointment> customerAppointmentList = FXCollections.observableArrayList();
        Object customerIDSelection = customerBox.getValue();


        for (Appointment appointment : allAppointments) {
            Object tempCustomerID = appointment.customerID;
            if (customerIDSelection == tempCustomerID) {
                customerAppointmentList.add(appointment);
            }
        }
        int size = customerAppointmentList.size();
        customerReport.setText("Total: " + size);

    }

    /**
     * Generates report by populated appointments into the TableView for the
     * appointments that the selected Contact has.
     */
    public void onContactBox(ActionEvent actionEvent) throws IOException, SQLException {
        ObservableList<Appointment> allAppointments = AppointmentDB.getAllAppointments();
        ObservableList<Appointment> contactList = FXCollections.observableArrayList();
        Object contactIDSelection = contactBox.getValue();

        for (Appointment appointment : allAppointments) {
            Object tempContactID = appointment.contactID;
            if (contactIDSelection == tempContactID) {
                contactList.add(appointment);
            }
        }
        appointmentTable.setItems(contactList);
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("end"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
    }

    /**
     * Generates report based on how many total appointments are in a given month
     */
    public void onMonthBox(ActionEvent actionEvent) throws IOException, SQLException {
        ObservableList<Appointment> allAppointments = AppointmentDB.getAllAppointments();
        ObservableList<Appointment> monthList = FXCollections.observableArrayList();
        Object monthSelection = monthBox.getValue();

        for (Appointment appointment : allAppointments) {
            String tempStart = appointment.start.toString();
            String[] tempSplit = tempStart.split(" ", 2);
            String[] tempTempSplit = tempSplit[0].split("-", 3);
            String tempString = tempTempSplit[1];
            Integer tempMonth = Integer.valueOf(tempString);
            if (monthSelection == tempMonth) {
                monthList.add(appointment);
            }
        }
        int size = monthList.size();
        monthReport.setText("Total: " + size);
    }

    /**
     * Generates report based on the total number of appointments for a Customer
     *
     */
    public void onTypeBox(ActionEvent actionEvent) throws IOException, SQLException {
        ObservableList<Appointment> allAppointments = AppointmentDB.getAllAppointments();
        ObservableList<Appointment> typeAppointmentList = FXCollections.observableArrayList();
        Object typeSelection = typeBox.getValue();


        for (Appointment appointment : allAppointments) {
            Object tempType = appointment.getType();
            if (typeSelection == tempType) {
                typeAppointmentList.add(appointment);
            }
        }
        int size = typeAppointmentList.size();
        typeReport.setText("Total: " + size);

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
