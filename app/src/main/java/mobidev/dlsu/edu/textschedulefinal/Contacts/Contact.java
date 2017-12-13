package mobidev.dlsu.edu.textschedulefinal.Contacts;

/**
 * Created by user on 12/11/2017.
 */

public class Contact {

    private String displayName;

    public Contact(String displayName, String number) {
        this.displayName = displayName;
        this.number = number;
    }

    public String getDisplayName() {

        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    private String number;

    public Contact() {

    }

}
