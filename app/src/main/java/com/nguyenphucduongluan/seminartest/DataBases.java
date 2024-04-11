package com.nguyenphucduongluan.seminartest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBases extends SQLiteOpenHelper {
    public static final String DB_NAME = "products.db";
    public static final int DB_VERSION = 1;

    public static final String TBL_NAME = "products";
    public static final String COL_CODE = "product_code";
    public static final String COL_NAME = "product_name";
    public static final String COL_PRICE = "product_price";
    public static final String COL_DESCRIPTION = "product_decription";
    public static final String COL_PIC = "product_image";
    public static MainActivity activity;
    public DataBases(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =
                "CREATE TABLE IF NOT EXISTS " + TBL_NAME +
                        " ( " + COL_CODE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_NAME + " VARCHAR(50), " +
                        COL_PRICE + " REAL, " +
                        COL_DESCRIPTION + " VARCHAR, " +
                        COL_PIC + " BLOB" + " )";
        db.execSQL(sql);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(db);
    }
    //Thực hiện câu lệnh insert, update, delete

    public boolean execSql(String sql){
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
            return true;
        }
        catch (Exception e) {
            Log.e("Error: ", e.toString());
            return false;
        }
    }
    public int numbOfRows() {
        Cursor c =  querryData(" SELECT * FROM " + TBL_NAME);
        int numbOfRows = c.getCount();
        c.close();
        return numbOfRows;
    }

    Cursor querryData(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

}
