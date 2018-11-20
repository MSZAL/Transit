package csc472.depaul.edu.transit.Train;


import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper{
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context, String databaseName){
        super(context, databaseName, null, DATABASE_VERSION);
    }

}
