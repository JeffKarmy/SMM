package com.jeffrkarmy.sheetmetalreference.Class;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrkar on 8/10/2016.
 */
public class HistoryDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "historyDB.db";
    private static final String TABLE_HISTORY = "HISTORY";
    private static final String COLUMN_HISTORY_ID = "HISTORY_ID";
    private static final String COLUMN_MT = "MT";
    private static final String COLUMN_RADIUS = "RADIUS";
    private static final String COLUMN_ANGLE = "ANGLE";
    private static final String COLUMN_DEDUCTION = "BD";
    private static final String COLUMN_DATE = "DATE";
    private static final String COLUMN_DESCRIPTION = "DESCRIPTION";

    public HistoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_HISTORY_TABLE = "create table " + TABLE_HISTORY + "( " + COLUMN_HISTORY_ID + " integer primary key not null, " + COLUMN_MT + " integer, " + COLUMN_RADIUS + " integer, " + COLUMN_ANGLE + " integer, " + COLUMN_DEDUCTION + " integer, " + COLUMN_DATE + " text, " + COLUMN_DESCRIPTION + " text);";
        db.execSQL(CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY + ";");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * Insert entry into database.
     *
     * @param historyDB object
     * @return Row ID of the newly inserted row, or -1 if an error occurred.
     */
    public long addEntry(HistoryDB historyDB) {
        try {
            ContentValues values = new ContentValues();

            if (!checkEntre(historyDB.getMaterialThickness(), historyDB.getRadius(), historyDB.getAngle())) {
                values.put(COLUMN_MT, historyDB.getMaterialThickness());
                values.put(COLUMN_RADIUS, historyDB.getRadius());
                values.put(COLUMN_ANGLE, historyDB.getAngle());
                values.put(COLUMN_DEDUCTION, historyDB.getBendDeduction());
                values.put(COLUMN_DATE, historyDB.getDate());
                SQLiteDatabase db = this.getWritableDatabase();
                Long success = db.insert(TABLE_HISTORY, null, values);
                db.close();
                return success;
            }
        } catch (Exception ex) {
            return -1; // -1 if error
        }
        return 0;
    }

    /**
     * Gets all entries from the database
     *
     * @return All entries in the database as a list.
     */
    public List<HistoryDB> getAllEntries() {
        HistoryDB historyDB;
        List<HistoryDB> entryList;
        Cursor cursor;
        String query = "select * from " + TABLE_HISTORY + ";";
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery(query, null);

            entryList = new ArrayList<>();
            cursor.moveToFirst();
            do {
                historyDB = new HistoryDB();
                historyDB.setId(cursor.getInt(0)); // id
                historyDB.setMaterialThickness(cursor.getDouble(1)); //Material thickness
                historyDB.setRadius(cursor.getDouble(2));  //Radius
                historyDB.setAngle(cursor.getDouble(3)); //Angle
                historyDB.setBendDeduction(cursor.getDouble(4)); //Bend deduction
                historyDB.setDate(cursor.getString(5)); //Date
                entryList.add(historyDB);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            entryList = null;
        }
        return entryList;
    }

    /**
     * Gets all entries from the database.
     *
     * @return all entries as an array.
     */
    public HistoryDB[] getAllEntriesArray() {

        HistoryDB historyDB;
        HistoryDB[] entryArray;
        Cursor cursor;
        String query = "select * from " + TABLE_HISTORY + ";";
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery(query, null);
            entryArray = new HistoryDB[cursor.getCount()];
            cursor.moveToFirst();
            do {
                historyDB = new HistoryDB();
                historyDB.setId(cursor.getInt(0)); //id
                historyDB.setMaterialThickness(cursor.getDouble(1)); //Material thickness
                historyDB.setRadius(cursor.getDouble(2));  //Radius
                historyDB.setAngle(cursor.getDouble(3)); //Angle
                historyDB.setBendDeduction(cursor.getDouble(4)); //Bend deduction
                historyDB.setDate(cursor.getString(5)); //Date
                entryArray[cursor.getPosition()] = historyDB;
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        } catch (Exception ex) {
            entryArray = null;
        }
        return entryArray;
    }

    /**
     * Delete an entry from the database.
     *
     * @param id Database row id primary key.
     * @return Number of rows affected, or 0
     */
    public int deleteEntrie(int id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(TABLE_HISTORY, COLUMN_HISTORY_ID + "=" + id, null);
        } catch (Exception ex) {
            String e = ex.toString();
            return 0;
        }

    }

    /**
     * Delete a row from the database.
     *
     * @param mt material thickness
     * @param r  radius
     * @param a  angle
     * @return The number of rows affected
     */
    public int deleteEntries(double mt, double r, double a) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            int success = db.delete(TABLE_HISTORY, COLUMN_MT + "=" + mt + " AND " + COLUMN_RADIUS + "=" + r + " AND " + COLUMN_ANGLE + "=" + a, null);
            db.close();
            return success;
        } catch (Exception ex) {
            return 0;
        }

    }

    /**
     * Check to see if an entrie exists in the database
     *
     * @param MT Material Thickness
     * @param R  Radius
     * @param A  Angle
     * @return Boolean, true if exists
     */
    private boolean checkEntre(double MT, double R, double A) {
        String query = "SELECT * FROM " + TABLE_HISTORY + " WHERE " + COLUMN_MT + "=" + MT + " AND " + COLUMN_RADIUS + "=" + R + " AND " + COLUMN_ANGLE + "=" + A + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Boolean exists = (cursor.getCount() > 0);
        db.close();
        cursor.close();
        return exists;
    }

    /**
     * Check if a database has any rows.
     *
     * @return true if database table has rows.
     */
    public boolean hasEntries() {
        boolean count = false;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_HISTORY + ";", null);
            cursor.moveToFirst();
            count = (cursor.getInt(0) > 0);
            db.close();
            cursor.close();
        } catch (Exception ex) {
            return count;
        }
        return count;
    }

    /**
     * Gets all entries for history DB ordered by material thickness.
     *
     * @return returns an array of HistoryDB objects.
     */
    public HistoryDB[] getAllEntriesArrayByMT() {

        HistoryDB historyDB;
        HistoryDB[] entryArray;
        Cursor cursor;
        String query = "select * from " + TABLE_HISTORY + " order by " + COLUMN_MT + ";";
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery(query, null);

            entryArray = new HistoryDB[cursor.getCount()];
            cursor.moveToFirst();
            do {
                historyDB = new HistoryDB();
                historyDB.setMaterialThickness(cursor.getDouble(1)); //Material thickness
                historyDB.setRadius(cursor.getDouble(2));  //Radius
                historyDB.setAngle(cursor.getDouble(3)); //Angle
                historyDB.setBendDeduction(cursor.getDouble(4)); //Bend deduction
                historyDB.setDate(cursor.getString(5)); //Date
                entryArray[cursor.getPosition()] = historyDB;
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            entryArray = null;
        }
        return entryArray;
    }

    /**
     * Gets all entries for history DB ordered by radius.
     *
     * @return returns an array of HistoryDB objects.
     */
    public HistoryDB[] getAllEntriesArrayByRadius() {

        HistoryDB historyDB;
        HistoryDB[] entryArray;
        Cursor cursor;
        String query = "select * from " + TABLE_HISTORY + " order by " + COLUMN_RADIUS + ";";
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery(query, null);

            entryArray = new HistoryDB[cursor.getCount()];
            cursor.moveToFirst();
            do {
                historyDB = new HistoryDB();
                historyDB.setId(cursor.getInt(0)); //id
                historyDB.setMaterialThickness(cursor.getDouble(1)); //Material thickness
                historyDB.setRadius(cursor.getDouble(2));  //Radius
                historyDB.setAngle(cursor.getDouble(3)); //Angle
                historyDB.setBendDeduction(cursor.getDouble(4)); //Bend deduction
                historyDB.setDate(cursor.getString(5)); //Date
                entryArray[cursor.getPosition()] = historyDB;
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            entryArray = null;
        }
        return entryArray;
    }

    /**
     * Gets all entries for history DB ordered by angle.
     *
     * @return returns an array of HistoryDB objects.
     */
    public HistoryDB[] getAllEntriesArrayByAngle() {

        HistoryDB historyDB;
        HistoryDB[] entryArray;
        Cursor cursor;
        String query = "select * from " + TABLE_HISTORY + " order by " + COLUMN_ANGLE + ";";
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery(query, null);

            entryArray = new HistoryDB[cursor.getCount()];
            cursor.moveToFirst();
            do {
                historyDB = new HistoryDB();
                historyDB.setId(cursor.getInt(0)); //id
                historyDB.setMaterialThickness(cursor.getDouble(1)); //Material thickness
                historyDB.setRadius(cursor.getDouble(2));  //Radius
                historyDB.setAngle(cursor.getDouble(3)); //Angle
                historyDB.setBendDeduction(cursor.getDouble(4)); //Bend deduction
                historyDB.setDate(cursor.getString(5)); //Date
                entryArray[cursor.getPosition()] = historyDB;
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            entryArray = null;
        }
        return entryArray;
    }

    /**
     * Delete all entries in the database.
     *
     * @return Number of rows affected.
     */
    public int deleteAllHistoryDBEntries() {
        SQLiteDatabase db = this.getWritableDatabase();
        int success = db.delete(TABLE_HISTORY, null, null);
        db.close();
        return success;
    }
}
