package csc472.depaul.edu.transit.Train;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class CTATrainDatabase {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static CTATrainDatabase instance;

    private CTATrainDatabase (Context context){
        this.openHelper = new DatabaseOpenHelper(context, "CTATrainsStops.db");
    }

    public static CTATrainDatabase getInstance(Context context){
        if (instance == null){
            instance = new CTATrainDatabase(context);
        }
        return instance;
    }

    public ArrayList<CTATrainStop> getAllStops(){
        ArrayList<CTATrainStop> AllStops = new ArrayList<>();

        this.openDatabase();
        String sql = "SELECT * FROM Stops";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int stopId = cursor.getInt(0);
            String direction = cursor.getString(1);
            String stopName = cursor.getString(2);
            int parentId = cursor.getInt(3);

            CTATrainStop stop = new CTATrainStop(stopId, direction, stopName, parentId);
            AllStops.add(stop);
            cursor.moveToNext();
        }

        cursor.close();
        this.closeDatabase();

        return AllStops;
    }


    private void openDatabase(){
        this.database = openHelper.getWritableDatabase();
    }

    private void closeDatabase(){
        if (database != null){
            this.database.close();
        }
    }

}
