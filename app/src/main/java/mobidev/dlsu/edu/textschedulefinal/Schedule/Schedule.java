package mobidev.dlsu.edu.textschedulefinal.Schedule;

import java.util.Date;

/**
 * Created by Nobody on 12/12/2017.
 */

public class Schedule {
    public static final String TABLE_NAME = "Schedule";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_REQUEST = "request";
    public static final String COLUMN_NUMBER ="number";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_DATE = "date";

    private long id;
    private String number;
    private int request;
    private String content;
    private long date;

    public Schedule() {
    }
    public Schedule(long id, String number, int request, String content, long date) {
        this.id = id;
        this.number = number;
        this.request = request;
        this.content = content;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
