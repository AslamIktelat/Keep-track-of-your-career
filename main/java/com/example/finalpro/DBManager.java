package com.example.finalpro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insertToT1(String description, String company, String senddate, String status) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.Description, description);
        contentValue.put(DatabaseHelper.Company, company);
        contentValue.put(DatabaseHelper.SendDate, senddate);
        contentValue.put(DatabaseHelper.Status, status);
        return database.insert(DatabaseHelper.TABLE1_NAME, null, contentValue);
    }


    public long insertToT2(long _id,String when, String where, String withwho) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper._ID, _id);
        contentValue.put(DatabaseHelper.WhenT, when);
        contentValue.put(DatabaseHelper.Where, where);
        contentValue.put(DatabaseHelper.WithWho, withwho);
      return database.insert(DatabaseHelper.TABLE2_NAME, null, contentValue);
    }


    public long insertToT3(long _id,String position,String days,String hours,String paycheck,String bonus) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper._ID, _id);
        contentValue.put(DatabaseHelper.Position, position);
        contentValue.put(DatabaseHelper.Days, days);
        contentValue.put(DatabaseHelper.Hours, hours);
        contentValue.put(DatabaseHelper.PayCheck, paycheck);
        contentValue.put(DatabaseHelper.Bonus, bonus);
        database.delete(DatabaseHelper.TABLE2_NAME, DatabaseHelper._ID + "=" + _id, null);//Delete from Table2(the job interview table)
       return database.insert(DatabaseHelper.TABLE3_NAME, null, contentValue);
    }



    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.Description, DatabaseHelper.Company, DatabaseHelper.SendDate, DatabaseHelper.Status };
        Cursor cursor = database.query(DatabaseHelper.TABLE1_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetchByCompanyN(String name) {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.Description, DatabaseHelper.Company, DatabaseHelper.SendDate, DatabaseHelper.Status };
        Cursor cursor = database.query(DatabaseHelper.TABLE1_NAME, columns, DatabaseHelper.Company+" LIKE '%"+name+"%' or "+DatabaseHelper.Description+" LIKE '%"+name+"%'", null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor GetDataById_JI(long id) {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.WhenT, DatabaseHelper.Where, DatabaseHelper.WithWho };
        Cursor cursor = database.query(DatabaseHelper.TABLE2_NAME, columns, DatabaseHelper._ID+"="+id,null , null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor GetDataById_JO(long id) {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.Position, DatabaseHelper.Days, DatabaseHelper.Hours , DatabaseHelper.PayCheck, DatabaseHelper.Bonus};
        Cursor cursor = database.query(DatabaseHelper.TABLE3_NAME, columns, DatabaseHelper._ID+"="+id,null , null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.Status, status);
        if(status.equals("Rejected"))//delete from table2 or table3
        {
            database.delete(DatabaseHelper.TABLE2_NAME, DatabaseHelper._ID + "=" + _id, null);
            database.delete(DatabaseHelper.TABLE3_NAME, DatabaseHelper._ID + "=" + _id, null);
        }
        int i = database.update(DatabaseHelper.TABLE1_NAME, contentValues, DatabaseHelper._ID +"="+_id, null); //
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE1_NAME, DatabaseHelper._ID + "=" + _id, null);
        database.delete(DatabaseHelper.TABLE2_NAME, DatabaseHelper._ID + "=" + _id, null);
        database.delete(DatabaseHelper.TABLE3_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

}
