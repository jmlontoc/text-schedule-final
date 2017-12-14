package mobidev.dlsu.edu.textschedulefinal.AutoReply;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import mobidev.dlsu.edu.textschedulefinal.Contacts.Contact;

/**
 * Created by user on 12/11/2017.
 */

public class AutoReplyDBHelper extends SQLiteOpenHelper {


    public static final String SCHEMA = "textschedule";
    public static int VERSION = 3;

    public AutoReplyDBHelper(Context context) {
        super(context, SCHEMA, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE " + AutoReply.TABLE_NAME + " ("
                + AutoReply.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + AutoReply.COLUMN_MESSAGE + " TEXT,"
                + AutoReply.COLUMN_REPLY + " TEXT,"
                + AutoReply.COLUMN_ACTIVE + " INTEGER"
                + ");";

        String sql2 = "CREATE TABLE " + AutoReply.TABLE2_NAME + " ("
                    + AutoReply.COLUMN_AR_ID + " INTEGER,"
                    + AutoReply.COLUMN_CONTACT_NAME + " TEXT,"
                    + AutoReply.COLUMN_CONTACT_NUMBER + " TEXT,"
                    + " PRIMARY KEY ("+ AutoReply.COLUMN_AR_ID +"),"
                    + " FOREIGN KEY ("+ AutoReply.COLUMN_AR_ID +") "
                    + "REFERENCES " + AutoReply.TABLE_NAME + "("+ AutoReply.COLUMN_ID +")"
                    + ");";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String sql = "DROP TABLE IF EXISTS " + AutoReply.TABLE_NAME + ";";
        String sql2 = "DROP TABLE IF EXISTS " + AutoReply.TABLE2_NAME + ";";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);
        onCreate(sqLiteDatabase);
    }

    public long addAutoReply(AutoReply ar, SQLiteDatabase db) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(AutoReply.COLUMN_MESSAGE, ar.getMessage());
        contentValues.put(AutoReply.COLUMN_REPLY, ar.getReply());
        contentValues.put(AutoReply.COLUMN_ACTIVE, ar.getIsActive());

        long id = db.insert(AutoReply.TABLE_NAME, null, contentValues);
        // db.close();
        return id;
    }

    public long addAutoReply(AutoReply ar) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(AutoReply.COLUMN_MESSAGE, ar.getMessage());
        contentValues.put(AutoReply.COLUMN_REPLY, ar.getReply());

        long id = db.insert(AutoReply.TABLE_NAME, null, contentValues);

        for (Contact contact: ar.getContacts()) {
            ContentValues cv = new ContentValues();

            cv.put(AutoReply.COLUMN_AR_ID, id);
            cv.put(AutoReply.COLUMN_CONTACT_NAME, contact.getDisplayName());
            cv.put(AutoReply.COLUMN_CONTACT_NUMBER, contact.getNumber());

            long aid = db.insert(AutoReply.TABLE2_NAME, null, cv);
        }

        db.close();
        return id;
    }

    public boolean deleteAutoReply(long id) {
        SQLiteDatabase db = getWritableDatabase();

        int rows = db.delete(AutoReply.TABLE_NAME,
                             AutoReply.COLUMN_ID + "=?",
                             new String[] {id+""});

        db.delete(AutoReply.TABLE2_NAME, AutoReply.COLUMN_AR_ID + "=?", new String[]{id+""});

        return rows > 0;
    }

    public boolean editAutoReply(AutoReply newAR, long currentID) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(AutoReply.COLUMN_MESSAGE, newAR.getMessage());
        contentValues.put(AutoReply.COLUMN_REPLY, newAR.getReply());

        ContentValues cv = new ContentValues();

        for (Contact contact : newAR.getContacts()) {
            cv.put(AutoReply.COLUMN_CONTACT_NAME, contact.getDisplayName());
            cv.put(AutoReply.COLUMN_CONTACT_NUMBER, contact.getNumber());

            db.update(AutoReply.TABLE2_NAME, cv, AutoReply.COLUMN_AR_ID + "=?",
                      new String[]{currentID+""});
        }

        int rowsAffected = db.update(AutoReply.TABLE_NAME,
                contentValues,
                AutoReply.COLUMN_ID + "=?",
                new String[]{currentID+""});


        db.close();

        return rowsAffected >0;

    }

    public Cursor getAllAutoReplies() {
        return getWritableDatabase().query(
              AutoReply.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public AutoReply getAutoReply(long id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(
                AutoReply.TABLE_NAME,
                null,
                AutoReply.COLUMN_ID + "=?",
                new String[]{ id+""},
                null,
                null,
                null
        );

        AutoReply ar = null;

        if (c.moveToFirst()) {
            ar = new AutoReply();

            ar.setIsActive(c.getInt(c.getColumnIndex(AutoReply.COLUMN_ACTIVE)));
            ar.setId(c.getLong(c.getColumnIndex(AutoReply.COLUMN_ID)));
            ar.setMessage(c.getString(c.getColumnIndex(AutoReply.COLUMN_MESSAGE)));
            ar.setReply(c.getString(c.getColumnIndex(AutoReply.COLUMN_REPLY)));

        }

        c.close();
        db.close();

        return ar;
    }
}
