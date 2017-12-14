package mobidev.dlsu.edu.textschedulefinal.Status;

import java.util.ArrayList;

import mobidev.dlsu.edu.textschedulefinal.Contacts.Contact;

public class Status {

    public static final String TABLE_NAME = "Status";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_REPLY = "reply";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_ACTIVE = "is_active";

    public static final String TABLE2_NAME = "Status_Recipients";
    public static final String COLUMN_STATUS_ID = "status_id";
    public static final String COLUMN_CONTACT_NAME = "name";
    public static final String COLUMN_CONTACT_NUMBER = "number";

    private long id;
    private String reply;
    private int isActive;
    private ArrayList<Contact> contacts;

    public long getId() {
        return id;
    }

    public Status(long id, String reply, String status) {
        this.id = id;
        this.reply = reply;
        this.status = status;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public Status(String reply, String status, int isActive) {
        this.reply = reply;
        this.isActive = isActive;
        this.status = status;
        this.contacts = new ArrayList<>();
    }

    public Status(String reply, String message) {
        this.reply = reply;
        this.status = status;
    }

    public Status() {
        this.contacts = new ArrayList<>();
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReply() {
        return reply;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getstatus() {
        return status;
    }

    public void setMessage(String status) {
        this.status = status;
    }

    private String status;




}
