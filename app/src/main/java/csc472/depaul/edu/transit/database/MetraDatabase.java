package csc472.depaul.edu.transit.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MetraDatabase {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static MetraDatabase instance;

    /* Constructor */
    private MetraDatabase(Context context) {
        this.openHelper = new DatabaseOpenHelper(context, "test.db");
    }

    /* Singleton */
    public static MetraDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new MetraDatabase(context);
        }
        return instance;
    }

    /* Opens database connection */
    private void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /* Closes database connection */
    private void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public void testQuery() {
        open(); // Opens the database
        Cursor cursor = database.rawQuery("Select * from Students", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String firstName = cursor.getString(0);
            Log.d("SQLLITE", firstName);

            cursor.moveToNext();
        }

        cursor.close();
        close(); // Closes the database
    }

}