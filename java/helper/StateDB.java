package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shipley.c195.Country;
import shipley.c195.State;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Creates CustomerDB class and gathers info about customers in the database
 */
public class StateDB {

    /**
     * Gets all states and provinces from the SQL database
     */
    public static ObservableList<State> getAllStates() throws SQLException {
        ObservableList<State> stateObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Division_ID");
            String name = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            State state = new State(
                    id,
                    name,
                    countryID
            );

            stateObservableList.add(state);
        }
        return stateObservableList;
    }

    /**
     * gets all states and provinces from the SQL database by Division ID
     */
    public static State getStateByStateID(int stateID) throws SQLException {
        String sql = "SELECT * from first_level_divisions WHERE Division_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, stateID);
        ResultSet rs = ps.executeQuery();
        State state = null;

        while(rs.next()) {
            int id = rs.getInt("Division_ID");
            String name = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            state = new State(
                    id,
                    name,
                    countryID
            );
        }
        return state;
    }

    /**
     * Gets the name of the state or province by division ID
     */
    public static String getStateNameByStateID(int stateID ) throws SQLException {
        String sql = "SELECT Division from first_level_divisions WHERE Division_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, stateID);
        ResultSet rs = ps.executeQuery();
        String name = null;

        while(rs.next()) {
            String tempName = rs.getString("Division");
            name = new String(
                    tempName
            );
        }
        return name;
    }

    /**
     * Gets the state or province from the SQL database by Division name
     */
    public static State getStateByStateName(String stateName) throws SQLException {
        String sql = "SELECT * from first_level_divisions WHERE Division=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, stateName);
        ResultSet rs = ps.executeQuery();
        State state = null;

        while(rs.next()) {
            int id = rs.getInt("Division_ID");
            String name = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            state = new State(
                    id,
                    name,
                    countryID
            );
        }
        return state;
    }

    /**
     * gets all states or provinces by country ID
     */
    public static ObservableList<String> getAllStatesByCountryID(int countryID) throws SQLException {
        ObservableList<String> stateObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from first_level_divisions WHERE Country_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryID);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String name = rs.getString("Division");

            stateObservableList.add(name);
        }
        return stateObservableList;
    }

}
