package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates CustomerDB class and gathers info about customers in the database
 */
public class UserDB {

    /**
     * gets all User Ids from the SQL database
     */
    public static ObservableList<Integer> getAllUserIDs() throws SQLException {
        ObservableList<Integer> userIDs = FXCollections.observableArrayList();
        String sql = "SELECT User_ID from users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            Integer userID = rs.getInt("User_ID");
            userIDs.add(userID);
        }
        return userIDs;
    }

    /**
     * function to authenticate logins
     */
    public static int userValidation(String username, String password) throws SQLException {
        try {
            String sql = "SELECT * FROM users WHERE User_Name = '" + username + "' AND Password = '" + password + "'";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                if (rs.getString("User_Name").equals(username) && rs.getString("Password").equals(password)) {
                    return rs.getInt("User_ID");
                }
            }
        }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        return 0;
    }

}

