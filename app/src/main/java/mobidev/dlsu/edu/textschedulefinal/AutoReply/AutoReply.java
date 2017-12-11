package mobidev.dlsu.edu.textschedulefinal.AutoReply;

public class AutoReply {

    public static final String TABLE_NAME = "Auto_Reply";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_REPLY = "reply";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_ACTIVE = "is_active";
    private long id;
    private String reply;
    private int isActive;

    public long getId() {
        return id;
    }

    public AutoReply(long id, String reply, String message) {
        this.id = id;
        this.reply = reply;
        this.message = message;
        this.isActive = 1;
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

    public AutoReply(String reply, String message) {
        this.reply = reply;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public AutoReply() {

    }




}
