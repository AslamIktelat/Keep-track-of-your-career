package com.example.finalpro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE1_NAME = "application";
    public static final String TABLE2_NAME = "interview";
    public static final String TABLE3_NAME = "jobOffer";

    // Table1 columns
    public static final String _ID = "_id";
    public static final String Description = "description";
    public static final String Company = "company";
    public static final String SendDate = "senddate";
    public static final String Status = "status";

    // Table2 columns
    //public static final String _ID = "_id";
    public static final String WhenT = "interviewdate";/*whenSent*/
    public static final String Where = "whereP";
    public static final String WithWho = "withwho";

    // Table3 columns
    //public static final String _ID = "_id";
    public static final String Position = "position";
    public static final String Days = "days";
    public static final String Hours = "hours";
    public static final String PayCheck = "paycheck";
    public static final String Bonus = "bonus";


    // Database Information
    static final String DB_NAME = "MyDb.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE1 = "create table " + TABLE1_NAME + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Description + " TEXT NOT NULL, " + Company + " TEXT NOT NULL,"+SendDate +" DATE NOT NULL,"+Status+" TEXT NOT NULL);";

    private static final String CREATE_TABLE2 = "create table " + TABLE2_NAME + "(" + _ID + " INTEGER PRIMARY KEY, " + WhenT + " DATE NOT NULL, " + Where + " TEXT NOT NULL,"+WithWho+" TEXT NOT NULL);";

    private static final String CREATE_TABLE3 = "create table " + TABLE3_NAME + "(" + _ID
            + " INTEGER," + Position + " TEXT NOT NULL, " + Days + " INTEGER NOT NULL,"+ Hours +" INTEGER NOT NULL ,"+ PayCheck +" INTEGER NOT NULL ,"+ Bonus +" TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);
        db.execSQL(CREATE_TABLE3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
        onCreate(db);
    }
}
