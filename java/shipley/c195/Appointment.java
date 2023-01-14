package shipley.c195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Creates the appointment Class and methods getting and setting appointment data
 */
public class Appointment {

    private static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    public static void addCustomer(Appointment appointment){appointmentList.add(appointment);}
    public static ObservableList<Appointment> getAppointments(){return appointmentList;}

    public Integer appointmentID;
    public String title;
    public String description;
    public String location;
    public String type;
    public Timestamp start;
    public Timestamp end;
    public Integer customerID;
    public Integer userID;
    public Integer contactID;
    public String contact;
    private ObservableList<Customer> associatedCustomer = FXCollections.observableArrayList();

    public Appointment(int appointmentID, String title, String description, String location, int contactID, String type, Timestamp start, Timestamp end, int customerID, int userID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }
    public void setType(int max) {
        this.type = type;
    }

    public void addAssociatedCustomer(Customer customer){associatedCustomer.add(customer);
    }

    public Timestamp getStart() { return start;
    }
    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getContactID() {
        return contactID;
    }
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public boolean deleteAssociatedCustomer(Customer selectedAssociatedCustomer){
        if(associatedCustomer.contains(selectedAssociatedCustomer)){
            associatedCustomer.remove(selectedAssociatedCustomer);
            return true;
        }
        else
            return false;
    }

    public ObservableList<Customer> getAllAssociatedCustomer(){
        return associatedCustomer;
    }

}
