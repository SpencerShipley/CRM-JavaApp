package shipley.c195;

/**
 * Creates the contact Class and methods getting and setting contact data
 */
public class Contact {
    private int contactID;
    private String contact;

    public Contact(int contactID, String contact) {
        this.contactID = contactID;
        this.contact = contact;
    }

    public int getContactID() {
        return contactID;
    }

    public String getContact() {
        return contact;
    }
}