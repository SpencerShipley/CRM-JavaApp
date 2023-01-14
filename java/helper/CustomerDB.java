package helper;

import shipley.c195.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates CustomerDB class and gathers info about customers in the database
 */
public class CustomerDB {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public static void addCustomer(Customer newCustomer){
        allCustomers.add(newCustomer);
    }
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        return allCustomers;
    }

    /**
     * Deletes customer
     */
    public static boolean deleteCustomer(Customer selectedCustomer){
        if(allCustomers.contains(selectedCustomer)){
            allCustomers.remove(selectedCustomer);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * gets all customer from the SQL database
     */
    public static ObservableList<Customer> getAllCustomersSQL() throws SQLException {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        String sql = "SELECT customer_id, customer_name, address, postal_code, phone, division_id FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();



        while(rs.next()) {
            Integer customerId = rs.getInt("customer_id");
            String customerName = rs.getString("customer_name");
            String customerAddress = rs.getString("address");
            String zip = rs.getString("postal_code");
            String phone = rs.getString("phone");
            Integer stateID = rs.getInt("division_id");
            String state = StateDB.getStateNameByStateID(stateID);
            Integer tempCountryID = Integer.parseInt(CountriesDB.getCountryIDByStateID(stateID));
            String countryName = CountriesDB.getCountryNameByCountryID(tempCountryID);
            Customer newCustomer = new Customer(customerId,customerName,customerAddress,zip,phone, state, countryName);
            allCustomers.add(newCustomer);
            customers.add(new Customer(customerId,customerName,customerAddress,zip,phone, state, countryName));
        }
        return customers;
    }

    /**
     * gets all customer IDs from the SQL database
     */
    public static ObservableList<Integer> getAllCustomerIDs() throws SQLException {
        ObservableList<Integer> customerIDs = FXCollections.observableArrayList();

        String sql = "SELECT customer_id FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Integer id = rs.getInt("customer_id");
            customerIDs.add(id);
        }

        return customerIDs;
    }

}