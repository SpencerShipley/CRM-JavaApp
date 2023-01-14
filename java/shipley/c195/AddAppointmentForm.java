package shipley.c195;

import helper.*;
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
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.chrono.ChronoZonedDateTime;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * AddAppointmentForm
 */
public class AddAppointmentForm implements Initializable {
    @FXML
    public TextField appointmentID;
    @FXML
    public TextField title;
    @FXML
    public TextField description;
    @FXML
    public TextField location;
    @FXML
    public ChoiceBox contact;
    @FXML
    public TextField type;
    @FXML
    public DatePicker start;
    @FXML
    public ChoiceBox startTime;
    @FXML
    public DatePicker end;
    @FXML
    public ChoiceBox endTime;
    @FXML
    public ChoiceBox customerID;
    @FXML
    public ChoiceBox userID;
    @FXML
    public Button saveButton;
    @FXML
    public Button cancelButton;



    public AddAppointmentForm() throws SQLException {
    }

    /**
     * initialized the form.
     * third lambda expression
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerID.setItems(CustomerDB.getAllCustomerIDs());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            userID.setItems(UserDB.getAllUserIDs());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            contact.setItems(ContactDB.getAllContactIDs());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        startTime.setItems(TimeValues.getTimes());
        endTime.setItems(TimeValues.getTimes());
        String convertedID = String.valueOf(newID);
        appointmentID.setText(convertedID);
        cancelButton.setOnAction( actionEvent -> {
            try {
                closeWindow(actionEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    ObservableList<Appointment> appointmentList = AppointmentDB.getAllAppointments();
    int newID = appointmentList.size() + 1;

    /**
     * saves data and takes user back to the MainMenu
     * or throws error
     */
    public void onSaveButton(ActionEvent actionEvent) throws IOException, SQLException {
        Alert warning = new Alert(Alert.AlertType.WARNING);
        int tempAppointmentID = Integer.parseInt(appointmentID.getText());
        String tempTitle = title.getText();
        String tempDescription = description.getText();
        String tempLocation = location.getText();
        Integer tempContact = Integer.parseInt(contact.getValue().toString());
        String tempType = type.getText();
        LocalDate tempDayStart = start.getValue();
        LocalTime tempTimeStart = (LocalTime) startTime.getValue();
        LocalDateTime tempStart = tempDayStart.atTime(tempTimeStart);
        Timestamp startTimestamp = Timestamp.valueOf(tempStart);
        LocalDate tempDayEnd = end.getValue();
        LocalTime tempTimeEnd = (LocalTime) endTime.getValue();
        LocalDateTime tempEnd = tempDayEnd.atTime(tempTimeEnd);
        Timestamp endTimestamp = Timestamp.valueOf(tempEnd);
        int tempCustomerID = Integer.parseInt(customerID.getValue().toString());
        int tempUserID = Integer.parseInt(userID.getValue().toString());

        LocalDate holderDate = LocalDate.of(2019, 10, 16);
        LocalTime holderTime = LocalTime.of(8, 00);
        LocalTime holderEndTime = LocalTime.of(22, 00);
        ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());
        ZoneId estID = ZoneId.of("America/New_York");
        ZonedDateTime inputZDT = ZonedDateTime.of(holderDate, tempTimeStart, localZoneID);
        ZonedDateTime inputToEst = inputZDT.withZoneSameInstant(estID);
        ZonedDateTime inputEndZDT = ZonedDateTime.of(holderDate, tempTimeEnd, localZoneID);
        ZonedDateTime inputEndToEst = inputEndZDT.withZoneSameInstant(estID);
        ZonedDateTime comparisonZDT = ZonedDateTime.of(holderDate, holderTime, estID);
        ZonedDateTime comparisonToEst = comparisonZDT.withZoneSameInstant(estID);
        ZonedDateTime lateComparisonZDT = ZonedDateTime.of(holderDate, holderEndTime, estID);
        ZonedDateTime lateComparisonToEst = lateComparisonZDT.withZoneSameInstant(estID);


        if ((inputToEst.isBefore(comparisonToEst)) || (inputEndToEst.isAfter(lateComparisonToEst))) {
            warning.setTitle("Alert");
            warning.setContentText("Appointment falls outside of business hours. Business hours are between 8am and 10pm EST");
            warning.showAndWait();
            return;
        }

        for (Appointment appointment: AppointmentDB.getAllAppointments()) {

            Timestamp start = appointment.getStart();
            Timestamp end = appointment.getEnd();


            if (tempCustomerID == appointment.getCustomerID() && tempAppointmentID != appointment.getAppointmentID() &&
                    startTimestamp.after(start) && startTimestamp.before(end)) {
                warning.setTitle("Alert");
                warning.setContentText("Customer is already scheduled for this time. Check the appointment table and try again.");
                warning.showAndWait();
                return;
            }

            if (tempCustomerID == appointment.getCustomerID() && tempAppointmentID != appointment.getAppointmentID() &&
                    endTimestamp.after(start) && endTimestamp.before(end)) {
                warning.setTitle("Alert");
                warning.setContentText("Customer is already scheduled for this time. Check the appointment table and try again.");
                warning.showAndWait();
                return;
            }

            if (tempCustomerID == appointment.getCustomerID() && tempAppointmentID != appointment.getAppointmentID() &&
                    start.after(startTimestamp) && start.before(endTimestamp)) {
                warning.setTitle("Alert");
                warning.setContentText("Customer is already scheduled for this time. Check the appointment table and try again.");
                warning.showAndWait();
                return;
            }

        }
        Appointment newAppointment = new Appointment(tempAppointmentID, tempTitle,tempDescription,tempLocation,tempContact, tempType, startTimestamp, endTimestamp, tempCustomerID, tempUserID);
        AppointmentDB.addAppointment(newAppointment);
        AppointmentDB.saveAppointment(tempAppointmentID, tempTitle,tempDescription,tempLocation,tempContact, tempType, startTimestamp, endTimestamp, tempCustomerID, tempUserID);
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

    public void onAppointmentID(ActionEvent actionEvent) throws IOException {

    }


}

/*

 */