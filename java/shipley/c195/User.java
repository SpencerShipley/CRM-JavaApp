package shipley.c195;

/**
 * Creates the User Class and methods getting and setting user data
 */
public class User {
    private int userID;
    private String userName;
    private String password;

    public User(int contactID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
