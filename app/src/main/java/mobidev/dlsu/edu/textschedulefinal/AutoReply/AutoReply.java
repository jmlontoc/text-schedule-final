package mobidev.dlsu.edu.textschedulefinal.AutoReply;

import java.util.ArrayList;

import mobidev.dlsu.edu.textschedulefinal.Contacts.Contact;

public class AutoReply {

    public static final String TABLE_NAME = "Auto_Reply";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_REPLY = "reply";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_ACTIVE = "is_active";

    public static final String TABLE2_NAME = "Auto_Reply_Recipients";
    public static final String COLUMN_AR_ID = "auto_reply_id";
    public static final String COLUMN_CONTACT_NAME = "name";
    public static final String COLUMN_CONTACT_NUMBER = "number";

    private long id;
    private String reply;
    private int isActive;
    private ArrayList<Contact> contacts;

    public long getId() {
        return id;
    }

    public AutoReply(long id, String reply, String message) {
        this.id = id;
        this.reply = reply;
        this.message = message;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public AutoReply(String reply, String message, int isActive) {
        this.reply = reply;
        this.isActive = isActive;
        this.message = message;
        this.contacts = new ArrayList<>();
    }

    public AutoReply(String reply, String message) {
        this.reply = reply;
        this.message = message;
    }

    public AutoReply() {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;




}
