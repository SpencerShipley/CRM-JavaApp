package helper;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shipley.c195.Country;
import helper.JDBC;

/**
 * Creates CustomerDB class and gathers info about customers in the database
 */
public class CountriesDB {

    /**
     * gets all countries from the SQL database
     */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> countryObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Country country = new Country(
                    id,
                    countryName
            );

            countryObservableList.add(country);
        }
        return countryObservableList;
    }

    /**
     * Gets all the country IDs from the SQL database
     */
    public static ObservableList<Integer> getAllCountryIDs() throws SQLException {
        ObservableList<Integer> countryObservableList = FXCollections.observableArrayList();
        String sql = "SELECT Country_ID from countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Country_ID");
            countryObservableList.add(id);
        }
        return countryObservableList;
    }

    /**
     * gets all of the country names from the SQL database
     */
    public static ObservableList<String> getAllCountryNames() throws SQLException {
        ObservableList<String> countryObservableList = FXCollections.observableArrayList();
        String sql = "SELECT Country from countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String countryName = rs.getString("Country");
            countryObservableList.add(countryName);
        }
        return countryObservableList;
    }

    /**
     * Gets all of the Country IDs from the SQL database based on Country ID
     */
    public static Country getCountryByID(int countryID) throws SQLException {
        String sql = "SELECT * from countries WHERE Country_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryID);
        ResultSet rs = ps.executeQuery();
        Country country = null;

        while(rs.next()) {
            int id = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            country = new Country(
                    id,
                    countryName
            );
        }
        return country;
    }

    /**
     * Gets all countries from the SQL database based on Country Name
     */
    public static Country getCountryByName(String name) throws SQLException {
        String sql = "SELECT * from countries WHERE Country=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        Country country = null;

        while(rs.next()) {
            int id = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            country = new Country(
                    id,
                    countryName
            );
        }
        return country;
    }

    /**
     * Gets country ID from the SQL databased based on State ID
     */
    public static String getCountryIDByStateID(int stateID) throws SQLException {

        String sql = "SELECT * from first_level_divisions WHERE Division_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, stateID);
        ResultSet rs = ps.executeQuery();
        String countryID = null;

        while(rs.next()) {
            String tempCountryID = rs.getString("Country_ID");
            countryID = tempCountryID;
        }
        return countryID;
    }

    /**
     * gets Country Name from the SQL database by country ID
     */
    public static String getCountryNameByCountryID(int countryID) throws SQLException {
        String sql = "SELECT * from countries WHERE Country_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryID);
        ResultSet rs = ps.executeQuery();
        String countryName = null;

        while(rs.next()) {
            String tempCountryName = rs.getString("Country");
            countryName = tempCountryName;
        }
        return countryName;
    }

    }

