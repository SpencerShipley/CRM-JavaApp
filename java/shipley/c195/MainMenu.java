package shipley.c195;

import helper.CustomerDB;
import javafx.beans.value.ObservableValue;
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
import helper.*;
import shipley.c195.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


/**
 * MainMenu Controller hosts Appointment and Customer TableViews
 * Also has access to the ReportsForm Controller
 */
public class MainMenu implements Initializable {
    @FXML
    public TableView<Customer> customerTable;
    @FXML
    public TableView<Appointment> appointmentTable;
    @FXML
    public TableColumn<Customer, Integer> IdColumn;
    @FXML
    public TableColumn<Customer, String> nameColumn;
    @FXML
    public TableColumn<Customer, String> addressColumn;
    @FXML
    public TableColumn<Customer, String> zipColumn;
    @FXML
    public TableColumn<Customer, String> stateColumn;
    @FXML
    public TableColumn<Customer, String> countryColumn;
    @FXML
    public TableColumn<Customer, Integer> phoneColumn;
    @FXML
    public TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML
    public TableColumn<Appointment, String> titleColumn;
    @FXML
    public TableColumn<Appointment, String> descriptionColumn;
    @FXML
    public TableColumn<Appointment, String> locationColumn;
    @FXML
    public TableColumn<Appointment, Integer> contactColumn;
    @FXML
    public TableColumn<Appointment, String> typeColumn;
    @FXML
    public TableColumn<Appointment, Timestamp> startColumn;
    @FXML
    public TableColumn<Appointment, Timestamp> endColumn;
    @FXML
    public TableColumn<Appointment, Integer> customerIdColumn;
    @FXML
    public TableColumn<Appointment, Integer> userIdColumn;
    @FXML
    public Button addCustomer;
    @FXML
    public Button updateCustomer;
    @FXML
    public Button deleteCustomer;
    @FXML
    public Button addAppointment;
    @FXML
    public Button updateAppointment;
    @FXML
    public Button deleteAppointment;
    @FXML
    public Button logoutButton;
    @FXML
    public RadioButton monthFilter;
    @FXML
    public RadioButton weekFilter;
    @FXML
    public RadioButton resetFilter;

    private static Customer selectedCustomer;

    public static Customer getSelectedCustomer(){
        return selectedCustomer;
    }

    private static Appointment selectedAppointment;

    public static Appointment getSelectedAppointment(){
        return selectedAppointment;
    }

    /**
     * Takes the user back to the LoginForm after pressing.
     */
    public void onLogoutButton(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Takes user to AddCustomerForm
     */
    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("AddCustomerForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Takes user to UpdateCustomerForm unless they selected a null row.
     * If their selection is null, an error message pops up.
     */
    public void onUpdateCustomer(ActionEvent actionEvent) throws IOException {
        Alert warning = new Alert(Alert.AlertType.WARNING);
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            warning.setContentText("Selected Customer is null");
            warning.showAndWait();
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("UpdateCustomerForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Deletes selected customer.
     * If the user's selection is null, then an error message pops up.
     * If the selected customer has appointments in the Appointment List,
     * then an error message pops up.
     */
    public void onDeleteCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        Alert warning = new Alert(Alert.AlertType.WARNING);
        if (selectedCustomer == null) {
            warning.setContentText("Selected Customer is null");
            warning.showAndWait();
        } else {
            int customersID = selectedCustomer.getCustomerID();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("You are about to remove this Customer. Do you wish to continue?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK && AppointmentDB.getCustomerAppointments(customersID).size() == 0) {
                CustomerDB.deleteCustomer(selectedCustomer);

            } else {
                alert.setTitle("Alert");
                warning.setContentText("Selected Customer has associated appointments in the database and cannot be deleted. " +
                        "Please remove associated appointments and try again");
                warning.showAndWait();
            }
        }
    }

    /**
     * Takes user to the AddAppointmentForm
     */
    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
            Parent parent = FXMLLoader.load(getClass().getResource("AddAppointmentForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
    }

    /**
     * Takes the user to the UpdateAppointmentForm unless selection is null.
     * If the user's selection is null, an error message pops up.
     */
    public void onUpdateAppointment(ActionEvent actionEvent) throws IOException {
        Alert warning = new Alert(Alert.AlertType.WARNING);
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            warning.setContentText("Selected Appointment is null");
            warning.showAndWait();
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("UpdateAppointmentForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Deletes selected appointment.
     * If appointment selection is null, an error message pops up.
     */
    public void onDeleteAppointment(ActionEvent actionEvent) throws IOException {
        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        Alert warning = new Alert(Alert.AlertType.WARNING);
        if (selectedAppointment == null) {
            warning.setContentText("Selected Appointment is null");
            warning.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("You are about to remove this Appointment. Do you wish to continue?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                AppointmentDB.deleteAppointment(selectedAppointment);
                int appointmentID = selectedAppointment.getAppointmentID();
                AppointmentDB.deleteAppointmentSQL(appointmentID);
                String appointmentType = selectedAppointment.getType();
                Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                confirmation.setTitle("Confirmation");
                confirmation.setContentText("Appointment ID: " + appointmentID + "\n" + "Appointment Type: " + appointmentType + "\n" + "This appointment has been deleted.");
                confirmation.showAndWait();
            }
        }
    }

    /**
     * Filters the TablewView to only show appointments from the last 24 hours
     * and the next 30 days.
     */
    public void onMonthFilter(ActionEvent actionEvent) throws IOException, SQLException {
        ObservableList<Appointment> allAppointments = AppointmentDB.getAllAppointments();
        ObservableList<Appointment> monthList = FXCollections.observableArrayList();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp halfMonthAhead = new Timestamp(now.getTime() + ((21600 * 60)* 1000));
        Timestamp monthAhead = new Timestamp(halfMonthAhead.getTime() + ((21600 * 60)* 1000));
        Timestamp pastDay = new Timestamp(now.getTime() - ((1440 * 60)* 1000));

        for (Appointment appointment : allAppointments) {
            if (appointment.getEnd().before(monthAhead) &&
                    appointment.getEnd().after(pastDay)) {
                monthList.add(appointment);
            }
        }
        appointmentTable.setItems(monthList);
    }

    /**
     * Filters the TableView to only show appointments from the last 24 hours
     * and the next 7 days
     */
    public void onWeekFilter(ActionEvent actionEvent) throws IOException, SQLException {
        ObservableList<Appointment> allAppointments = AppointmentDB.getAllAppointments();
        ObservableList<Appointment> weekList = FXCollections.observableArrayList();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp weekAhead = new Timestamp(now.getTime() + ((10080 * 60)* 1000));
        Timestamp pastDay = new Timestamp(now.getTime() - ((1440 * 60)* 1000));

        for (Appointment appointment : allAppointments) {
            if (appointment.getEnd().before(weekAhead) &&
                    appointment.getEnd().after(pastDay)) {
                weekList.add(appointment);
            }
        }
        appointmentTable.setItems(weekList);
    }

    /**
     * Removes previous filters and repopulates all appointments
     */
    public void onResetFilter(ActionEvent actionEvent) throws IOException, SQLException {

        ObservableList<Appointment> allAppointments = AppointmentDB.getAllAppointments();
        appointmentTable.setItems(allAppointments);

    }

    /**
     * Takes customer to the ReportsForm
     */
    public void onReportsButton(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("ReportsForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public static boolean upcomingAppointment = false;
    /**
     * Initializes the MainMenu and sends an Alert if there is an appointment
     * within the next 15 minutes.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customer> allCustomers = null;
        try {
            allCustomers = CustomerDB.getAllCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ObservableList<Appointment> allAppointments = null;
        try {
            allAppointments = AppointmentDB.getAllAppointments();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        for (Appointment appointment : allAppointments) {
            Integer tempID = appointment.getAppointmentID();
            Timestamp start = appointment.getStart();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            Timestamp lookAhead = new Timestamp(now.getTime() + ((15 * 60)* 1000));
            if (start.before(lookAhead) && start.after(now) || start.equals(now)) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Upcoming Appointment");
                alert.setContentText("There is an appointment starting within the next 15 minutes. \n" +
                                    "Appointment ID: " + tempID + "\n" +
                                    "Time & Date: " + start);
                alert.showAndWait();
                upcomingAppointment = true;
            }
        }
        if (!upcomingAppointment) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("No Immediate Appointment");
            alert.setContentText("There are no appointments in the next 15 minutes.");
            alert.showAndWait();
        }

        customerTable.setItems(allCustomers);
        IdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("state"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("country"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        zipColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("zip"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("phoneNumber"));

        appointmentTable.setItems(allAppointments);
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactID"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("end"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userID"));
    }

}
