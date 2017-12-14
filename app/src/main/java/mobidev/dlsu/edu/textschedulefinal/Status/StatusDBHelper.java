package mobidev.dlsu.edu.textschedulefinal.Status;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import mobidev.dlsu.edu.textschedulefinal.Status.Status;
import mobidev.dlsu.edu.textschedulefinal.Contacts.Contact;

/**
 * Created by user on 12/14/2017.
 */

public class StatusDBHelper extends SQLiteOpenHelper {
    
    public static final String SCHEMA = "textschedule_";
    public static final int VERSION = 3;
    
    
    public StatusDBHelper(Context context) {
        super(context, SCHEMA, null, VERSION);
        
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE " + Status.TABLE_NAME + " ("
                + Status.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Status.COLUMN_STATUS + " TEXT,"
                + Status.COLUMN_REPLY + " TEXT,"
                + Status.COLUMN_ACTIVE + " INTEGER"
                + ");";

        String sql2 = "CREATE TABLE " + Status.TABLE2_NAME + " ("
                + Status.COLUMN_CONTACT_NUMBER + " TEXT,"
                + Status.COLUMN_STATUS_ID + " INTEGER,"
                + Status.COLUMN_CONTACT_NAME + " TEXT,"
                + " PRIMARY KEY ("+ Status.COLUMN_STATUS_ID +", "+ Status.COLUMN_CONTACT_NUMBER +"),"
                + " FOREIGN KEY ("+ Status.COLUMN_STATUS_ID +") "
                + "REFERENCES " + Status.TABLE_NAME + "("+ Status.COLUMN_ID +")"
                + ");";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String sql = "DROP TABLE IF EXISTS " + Status.TABLE_NAME + ";";
        String sql2 = "DROP TABLE IF EXISTS " + Status.TABLE2_NAME + ";";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);
        onCreate(sqLiteDatabase);
    }

    public long addStatus(Status s) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Status.COLUMN_STATUS, s.getstatus());
        contentValues.put(Status.COLUMN_REPLY, s.getReply());

        long id = db.insert(Status.TABLE_NAME, null, contentValues);

        for (Contact contact: s.getContacts()) {
            ContentValues cv = new ContentValues();

            cv.put(Status.COLUMN_STATUS_ID, id);
            cv.put(Status.COLUMN_CONTACT_NAME, contact.getDisplayName());
            cv.put(Status.COLUMN_CONTACT_NUMBER, contact.getNumber());

            long aid = db.insert(Status.TABLE2_NAME, null, cv);
        }

        db.close();
        return id;
    }

    public boolean deleteStatus(long id) {
        SQLiteDatabase db = getWritableDatabase();

        int rows = db.delete(Status.TABLE_NAME,
                Status.COLUMN_ID + "=?",
                new String[] {id+""});

        db.delete(Status.TABLE2_NAME, Status.COLUMN_STATUS_ID + "=?", new String[]{id+""});

        return rows > 0;
    }

    public boolean editStatus(Status newAR, long currentID) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Status.COLUMN_STATUS, newAR.getstatus());
        contentValues.put(Status.COLUMN_REPLY, newAR.getReply());

        ContentValues cv = new ContentValues();

        for (Contact contact : newAR.getContacts()) {
            cv.put(Status.COLUMN_CONTACT_NAME, contact.getDisplayName());
            cv.put(Status.COLUMN_CONTACT_NUMBER, contact.getNumber());

            db.update(Status.TABLE2_NAME, cv, Status.COLUMN_STATUS_ID + "=?",
                    new String[]{currentID+""});
        }

        int rowsAffected = db.update(Status.TABLE_NAME,
                contentValues,
                Status.COLUMN_ID + "=?",
                new String[]{currentID+""});


        db.close();

        return rowsAffected >0;

    }

    public Cursor getAllStatuses() {
        return getWritableDatabase().query(
                Status.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public Status getStatus(long id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(
                Status.TABLE_NAME,
                null,
                Status.COLUMN_ID + "=?",
                new String[]{ id+""},
                null,
                null,
                null
        );

        Status ar = null;

        if (c.moveToFirst()) {
            ar = new Status();

            ar.setIsActive(c.getInt(c.getColumnIndex(Status.COLUMN_ACTIVE)));
            ar.setId(c.getLong(c.getColumnIndex(Status.COLUMN_ID)));
            ar.setMessage(c.getString(c.getColumnIndex(Status.COLUMN_STATUS)));
            ar.setReply(c.getString(c.getColumnIndex(Status.COLUMN_REPLY)));

        }

        c.close();
        db.close();

        return ar;
    }

    public Cursor getStatusRecipients(long id) {
        return getWritableDatabase().query(
                Status.TABLE2_NAME,
                null,
                Status.COLUMN_STATUS_ID + "=?",
                new String[]{id+""},
                null,
                null,
                null
        );
    }
}
