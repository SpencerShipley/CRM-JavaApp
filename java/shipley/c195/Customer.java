package shipley.c195;

import helper.CustomerDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Creates the customer Class and methods getting and setting customer data
 */
public class Customer {
    private static ObservableList<Customer> customersList = FXCollections.observableArrayList();
    public static void addCustomer(Customer customer){customersList.add(customer);}
    ObservableList<Customer> customers = CustomerDB.getAllCustomers();
    public void insertCustomers(){customersList.add((Customer) customers);}

    public static ObservableList<Customer> getCustomers(){return customersList;}

    public Integer customerID;
    public String name;
    public String address;
    public String zip;
    public String state;
    public String country;
    public String phoneNumber;
    public Customer(int customerID, String name, String address, String zip, String phoneNumber, String state, String country) throws SQLException {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.state = state;
        this.country = country;
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the id
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID the id to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    /**
     * @param country the name to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the price to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the stock
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip the stock to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * return the zip
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the min to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
