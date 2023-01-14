package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Creates CustomerDB class and gathers info about customers in the database
 */
public class ContactDB {

    /**
     * Gets all Contact IDs from the SQL database
     */
    public static ObservableList<Integer> getAllContactIDs() throws SQLException {
        ObservableList<Integer> contactIDs = FXCollections.observableArrayList();
        String sql = "SELECT Contact_ID from contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            Integer contactID = rs.getInt("Contact_ID");
            contactIDs.add(contactID);
        }
        return contactIDs;
    }

}
