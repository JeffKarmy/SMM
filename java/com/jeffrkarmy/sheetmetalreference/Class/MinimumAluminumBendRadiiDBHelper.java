package com.jeffrkarmy.sheetmetalreference.Class;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by jrkar on 8/25/2016.
 */
public class MinimumAluminumBendRadiiDBHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.jeffrkarmy.sheetmetalreference/databases/";
    private static String DB_NAME = "minimum_aluminum_bend_radii.db";
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public MinimumAluminumBendRadiiDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            //do nothing - database already exist
        } else {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            //database does't exist yet.
        }

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {


        try {
            //Open your local db as the input stream
            InputStream myInput = myContext.getAssets().open(DB_NAME);

            // Path to the just created empty db
            String outFileName = DB_PATH + DB_NAME;

            //Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            //transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException ex) {

        }

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public AlloyOjb[] getAllEntriesArray(int alloy) {

        AlloyOjb alloyOjb;
        AlloyOjb[] entryArray;
        String[] thicknessArray = {"1/64", "1/32", "1/16", "1/8", "3/16", "1/4", "3/8", "1/2"};
        Cursor cursor;
        String query = "select TEMPER, \n" +
                "\t\tONE_SIXTY_FOURTH, \n" +
                "\t\tONE_THIRTY_SECOND, \n" +
                "\t\tONE_SIXTEENTH, \n" +
                "\t\tONE_EIGHTH, \n" +
                "\t\tTHREE_SIXTEENTH, \n" +
                "\t\tONE_QUARTER, \n" +
                "\t\tTHREE_EIGHTHS, \n" +
                "\t\tONE_HALF from ALUMINUM_MINIMUM_BEND_RADII WHERE ALLOY = " + alloy + ";";
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery(query, null);
            int count = cursor.getCount() * 8;
            int x = 0;
            entryArray = new AlloyOjb[count];
            cursor.moveToFirst();
            do {
                int h = 1;
                int counter = cursor.getColumnCount();
                for (int i = 0; i < counter - 1; i++) {
                    alloyOjb = new AlloyOjb();
                    alloyOjb.setAlloy(alloy);
                    alloyOjb.setTemper(cursor.getString(0));
                    alloyOjb.setThickness(thicknessArray[i]);
                    alloyOjb.setRadius(cursor.getString(h));
                    h++;
                    entryArray[x] = alloyOjb;
                    x++;
                }
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            entryArray = null;
        }
        return entryArray;
    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

}
