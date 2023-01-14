package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shipley.c195.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

/**
 * Creates CustomerDB class and gathers info about customers in the database
 */
public class AppointmentDB {

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    public static void addAppointment(Appointment newAppointment){
        allAppointments.add(newAppointment);
    }
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        return allAppointments;
    }


    /**
     * Deletes appointment
     */
    public static boolean deleteAppointment(Appointment selectedAppointment){
        if(allAppointments.contains(selectedAppointment)){
            allAppointments.remove(selectedAppointment);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Get all of the appointments from the SQL database
     */
    public static ObservableList<Appointment> getAllAppointmentsSQL() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();


        while(rs.next()) {
            Integer  appointmentID = rs.getInt("appointment_id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            String location= rs.getString("location");
            String type = rs.getString("type");
            Timestamp start = rs.getTimestamp("start");
            Timestamp end = rs.getTimestamp("end");
            Integer customerID = rs.getInt("customer_id");
            Integer userID = rs.getInt("user_id");
            Integer contactID = rs.getInt("contact_id");


            allAppointments.add(new Appointment(appointmentID,title,description,location,contactID,type,start,end,customerID,userID));
        }

        return appointments;
    }

    /**
     * Gets appointments based on Customer ID
     */
    public static ObservableList<Appointment> getCustomerAppointments(int customersID) throws SQLException {
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();
        String sql = "SELECT * from appointments WHERE Customer_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customersID);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int appointmentID= rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentID,title,description,location,contactID,type,start,end,customerID,userID);
            appointmentsList.add(appointment);
        }

        return appointmentsList;
    }

    public static boolean saveAppointment(int appointmentID, String title, String description, String location, int contactID, String type, Timestamp startTimestamp, Timestamp endTimestamp, int customerID, int userID) {

        try {
            String query = "INSERT INTO appointments (Appointment_ID, Title, Description, Contact_ID, Location, Start, End, Customer_ID, User_ID, Type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            /*
            String query = "INSERT INTO appointments SET Appointment_ID='" + appointmentID + "', Title='" + title + "', Description='" +
                    description + "', Contact_ID='" + contactID + "', Location='" + location + "', Start='" + startTimestamp + "', End='" +
                    endTimestamp + "', Customer_ID='" + customerID + "', User_ID='" + userID + "', Type='" + type + "'";
             */
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            ps.setInt(1, appointmentID);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setInt(4, contactID);
            ps.setString(5, location);
            ps.setTimestamp(6, startTimestamp);
            ps.setTimestamp(7, endTimestamp);
            ps.setInt(8, customerID);
            ps.setInt(9, userID);
            ps.setString(10, type);
            int update = ps.executeUpdate();
            if(update == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    public static boolean updateAppointment(int appointmentID, String title, String description, String location, int contactID, String type, Timestamp startTimestamp, Timestamp endTimestamp, int customerID, int userID) {

        try {
            String query = "UPDATE appointments SET (Appointment_ID, Title, Description, Contact_ID, Location, Start, End, Customer_ID, User_ID, Type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(query);
            ps.setInt(1, appointmentID);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setInt(4, contactID);
            ps.setString(5, location);
            ps.setTimestamp(6, startTimestamp);
            ps.setTimestamp(7, endTimestamp);
            ps.setInt(8, customerID);
            ps.setInt(9, userID);
            ps.setString(10, type);
            int update = ps.executeUpdate();
            if(update == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    public static void deleteAppointmentSQL(int appointmentID) {
        try {
            String update = "DELETE FROM appointments WHERE Appointment_ID = " + appointmentID;
            PreparedStatement ps = JDBC.connection.prepareStatement(update);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error with delete execution");
        }
    }

}

/*

 */