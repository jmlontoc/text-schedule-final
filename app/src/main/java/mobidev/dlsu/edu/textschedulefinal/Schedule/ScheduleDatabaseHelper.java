package mobidev.dlsu.edu.textschedulefinal.Schedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nobody on 12/6/2017.
 */

public class ScheduleDatabaseHelper extends SQLiteOpenHelper {

    public static final String SCHEMA = "schedule";
    public static final int VERSION = 1;

    public ScheduleDatabaseHelper(Context context) {
        super(context, SCHEMA, null, VERSION);
    }
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // ROLE: create the tables for the schema
        // It will only be called once by the system
        // -- when the schema with given name doesn't exist yet

        String sql = "CREATE TABLE " + Schedule.TABLE_NAME + " ("
                + Schedule.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Schedule.COLUMN_NUMBER + " TEXT,"
                + Schedule.COLUMN_REQUEST + " INTEGER,"
                + Schedule.COLUMN_CONTENT + " TEXT,"
                + Schedule.COLUMN_DATE + " LONG"
                + ");";
        sqLiteDatabase.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,
                          int i, int i1) {
        // ROLE: update the current schema
        // Will be called when version number is newer/higher

        // migration
        // drop current tables
        String sql = "DROP TABLE IF EXISTS " + Schedule.TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);
        // call onCreate
        onCreate(sqLiteDatabase);
    }
    public long addSchedule(Schedule schedule){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Schedule.COLUMN_NUMBER, schedule.getNumber());
        contentValues.put(Schedule.COLUMN_CONTENT, schedule.getContent());
        contentValues.put(Schedule.COLUMN_DATE, schedule.getDate());
        contentValues.put(Schedule.COLUMN_REQUEST, schedule.getRequest());
        long id = db.insert(Schedule.TABLE_NAME,
                null,
                contentValues);
        // db.close();
        return id;
    }
    public boolean editFood(Schedule newScheduleDetails, long currentId){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Schedule.COLUMN_NUMBER, newScheduleDetails.getNumber());
        contentValues.put(Schedule.COLUMN_REQUEST, newScheduleDetails.getRequest());
        contentValues.put(Schedule.COLUMN_CONTENT, newScheduleDetails.getContent());
        contentValues.put(Schedule.COLUMN_DATE, newScheduleDetails.getDate());

        int rowsAffected = db.update(Schedule.TABLE_NAME,
                contentValues,
                Schedule.COLUMN_ID + "=?",
                new String[]{currentId+""});
        // db.close();

        return rowsAffected >0;
    }

    // deleteFood
    public boolean deleteSchedule(long id){
        SQLiteDatabase db = getWritableDatabase();
        int rowsAffected = db.delete(Schedule.TABLE_NAME,
                Schedule.COLUMN_ID + "=?",
                new String[]{id+""} );
        // db.close();
        return rowsAffected >0;
    }

    public Schedule getSchedule(long id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Schedule.TABLE_NAME,
                null,
                Schedule.COLUMN_ID + "=?",
                new String[]{ id+"" },
                null,
                null,
                null);
        Schedule s = null;
        if(c.moveToFirst()){
            s = new Schedule();
            s.setRequest(c.getInt(c.getColumnIndex(Schedule.COLUMN_REQUEST)));
            s.setContent(c.getString(c.getColumnIndex(Schedule.COLUMN_CONTENT)));
            s.setDate(c.getLong(c.getColumnIndex(Schedule.COLUMN_DATE)));
            s.setNumber(c.getString(c.getColumnIndex(Schedule.COLUMN_NUMBER)));
            s.setId(c.getLong(c.getColumnIndex(Schedule.COLUMN_ID)));
        }

        c.close();
        // db.close();

        return s;
    }
    public Cursor getAllScheduleCursor(){
        String orderBy = Schedule.COLUMN_DATE + " ASC";
        return getReadableDatabase().query(Schedule.TABLE_NAME, null,null,null,null,null,orderBy);
    }
}
