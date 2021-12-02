package model;

/**
 * This class creates Contact objects.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public class Contact {
    private int contactId;
    private String contactName;
    private String contactEmail;

    /**
     * Instantiates a new Contact.
     *
     * @param contactId    the contact id
     * @param contactName  the contact name
     * @param contactEmail the contact email
     */
    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }


    /**
     * Gets contact id.
     *
     * @return contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Gets contact name.
     *
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Gets contact email.
     *
     * @return the contact email
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Sets contact id.
     *
     * @param contactId the contact id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Sets contact name.
     *
     * @param contactName the contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Sets contact email.
     *
     * @param contactEmail the contact email
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public String toString() {
        return contactName;
    }
}
