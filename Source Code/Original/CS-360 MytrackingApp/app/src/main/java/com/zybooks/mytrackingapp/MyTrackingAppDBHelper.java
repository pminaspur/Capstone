// Dbhelper  : This Class is created to keep records in the database.
//there are 3 tables: login table, weight table and target table
// //Modified By : Pushpa Laxman
//  Modified Date: 12/14/2024

package com.zybooks.mytrackingapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyTrackingAppDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "new_my_weight_tracking.db";
    private static final int VERSION = 3;

    public MyTrackingAppDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final class LoginTable {
        //declared variables for the login table
        private static final String TABLE = "login";
        private static final String COL_ID = "_id";
        private static final String COL_USERNAME = "username";
        private static final String COL_PASSWORD = "password";
    }

    private static final class WeightTable {
        //declared variables for the weight table
        private static final String TABLE = "user_weight";
        private static final String COL_ID = "_id";
        private static final String COL_DATE = "date";
        private static final String COL_DAILY_WEIGHT_RECORD = "dailyweight";
    }

    private static final class WeightTargetTable {
        //declared variables for the weight target table
        private static final String TABLE = "user_weight_target";
        private static final String COL_ID = "_id";
        private static final String COL_TARGET_WEIGHT_RECORD = "target_weight";
    }

    @Override
    //created 3 tables in the database
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + LoginTable.TABLE + " (" +
                LoginTable.COL_ID + " integer primary key autoincrement, " +
                LoginTable.COL_USERNAME + " text, " +
                LoginTable.COL_PASSWORD + " text)");

        db.execSQL("create table " + WeightTable.TABLE + " (" +
                WeightTable.COL_ID + " integer primary key autoincrement, " +
                WeightTable.COL_DATE + " text, " +
                WeightTable.COL_DAILY_WEIGHT_RECORD + " real)");

        db.execSQL("create table " + WeightTargetTable.TABLE + " (" +
                WeightTargetTable.COL_ID + " integer primary key autoincrement, " +
                WeightTargetTable.COL_TARGET_WEIGHT_RECORD + " real)");
    }

    @Override
    //
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + LoginTable.TABLE);
        db.execSQL("drop table if exists " + WeightTable.TABLE);
        db.execSQL("drop table if exists " + WeightTargetTable.TABLE);
        onCreate(db);
    }



    // user adding the login details
    public long addUserLogin(String userName, String userPassword) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LoginTable.COL_USERNAME, userName);
        values.put(LoginTable.COL_PASSWORD,userPassword);
        long userId = db.insert(LoginTable.TABLE, null, values);
        db.close();
        return userId;
    }

    //validating the user login details, if new user asking to create account.
    public boolean validateUserLogin(String userName, String userPassword) {
        SQLiteDatabase db = getReadableDatabase();
        long id = 0;
        String sql = "select * from " + LoginTable.TABLE + " where "+ LoginTable.COL_USERNAME + "='"+userName+ "' and "+ LoginTable.COL_PASSWORD + "='"+userPassword+"'";
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor != null) {
            while (cursor.moveToNext())
            {
                id = cursor.getLong(0);
            }
        }
        db.close();
        if(id>0)
        {
            return true;
        }
        else {
            return false;
        }
    }
     //adding the user daily weight in the database
    public long addUserDailyWeight(String date, Float dailyweight) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WeightTable.COL_DATE, date);
        values.put(WeightTable.COL_DAILY_WEIGHT_RECORD,dailyweight);
        long weightId = db.insert(WeightTable.TABLE, null, values);
        db.close();
        return weightId;
    }
    //updating the daily weight
    public int updateUserDailyWeight(long id, String date, Float dailyweight) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WeightTable.COL_DATE, date);
        values.put(WeightTable.COL_DAILY_WEIGHT_RECORD,dailyweight);
        int rowsAffected = db.update(WeightTable.TABLE,values, WeightTable.COL_ID+"=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }
    //deleting the user weight
    public int deleteUserDailyWeight(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsDeleted = db.delete(WeightTable.TABLE, WeightTable.COL_ID+"=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted;
    }



    public ArrayList<WeightModel> getAllWeightDetails() {
        ArrayList<WeightModel> items = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        //get all the records from the weight table
        String sql = "select * from " + WeightTable.TABLE;
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                WeightModel item = readItem(cursor);
                items.add(item);
            }
        }
        return items;
    }

    @SuppressLint("Range")
    private WeightModel readItem(Cursor cursor) {
        WeightModel record = new WeightModel();
        record.setWeightId(cursor.getLong(cursor.getColumnIndex(WeightTable.COL_ID)));
        record.setDate(cursor.getString(cursor.getColumnIndex(WeightTable.COL_DATE)));
        record.setDaily_weight(cursor.getFloat(cursor.getColumnIndex(WeightTable.COL_DAILY_WEIGHT_RECORD)));
        return record;
    }

    public Cursor GetData() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + WeightTable.TABLE;
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }
    // user can change the target weight
    public void UserWeightTarget(Float target_weight) {
        long current_target = 0;
        long current_id = 0;
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select * from " + WeightTargetTable.TABLE;
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor != null) {
            while (cursor.moveToNext())
            {
                current_id = cursor.getLong(0);
                current_target = cursor.getLong(1);
            }
        }
        if(current_target == 0)
        {
            ContentValues values = new ContentValues();
            values.put(WeightTargetTable.COL_TARGET_WEIGHT_RECORD, target_weight);
            long Id = db.insert(WeightTargetTable.TABLE, null, values);
            db.close();
        }
        else
        {
            ContentValues values = new ContentValues();
            values.put(WeightTargetTable.COL_TARGET_WEIGHT_RECORD,target_weight);
            int rowsAffected = db.update(WeightTargetTable.TABLE,values, WeightTargetTable.COL_ID+"=?", new String[]{String.valueOf(current_id)});
            db.close();
        }
    }
}
