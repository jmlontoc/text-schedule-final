package mobidev.dlsu.edu.textschedulefinal.AutoReply;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 12/11/2017.
 */

public class AutoReplyDBHelper extends SQLiteOpenHelper {


    public static final String SCHEMA = "textschedule";
    public static int VERSION = 1;

    public AutoReplyDBHelper    (Context context) {
        super(context, SCHEMA, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE " + AutoReply.TABLE_NAME + " ("
                + AutoReply.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + AutoReply.COLUMN_MESSAGE + " TEXT,"
                + AutoReply.COLUMN_REPLY + " TEXT,"
                + AutoReply.COLUMN_ACTIVE + "INTEGER"
                + ");";
        sqLiteDatabase.execSQL(sql);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String sql = "DROP TABLE IF EXISTS " + AutoReply.TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public long addAutoReply(AutoReply ar, SQLiteDatabase db) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(AutoReply.COLUMN_MESSAGE, ar.getMessage());
        contentValues.put(AutoReply.COLUMN_REPLY, ar.getReply());
        contentValues.put(AutoReply.COLUMN_ACTIVE, ar.getIsActive());

        long id = db.insert(AutoReply.TABLE_NAME, null, contentValues);
        db.close();
        return id;
    }

    public boolean editAutoReply(AutoReply newAR, long currentID) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(AutoReply.COLUMN_MESSAGE, newAR.getMessage());
        contentValues.put(AutoReply.COLUMN_REPLY, newAR.getReply());
        contentValues.put(AutoReply.COLUMN_ACTIVE, newAR.getIsActive());

        int rowsAffected = db.update(AutoReply.TABLE_NAME,
                contentValues,
                AutoReply.COLUMN_ID + "=?",
                new String[]{currentID+""});
        db.close();

        return rowsAffected >0;

    }

    public boolean deleteAutoReply(long id) {

        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(AutoReply.TABLE_NAME, AutoReply.COLUMN_ID + "=?",
                new String[] {id+""});

        db.close();
        return rows > 0;
    }
}
