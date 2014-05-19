package com.example.enoticeapp;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class OfflineData extends SQLiteOpenHelper {
	
	public static String database="enotice";

	public OfflineData(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		Log.d("Offline","Constructor");
		
		
	}

	public static int database_version = 1;
	public static String table1 = "notices";
	public static String table2 = "flag";
	public static String id = "id";
	public static String title = "title";
	public static String desc = "description";
	private String strQuery = "CREATE TABLE IF NOT EXISTS "+table1+" ( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT,"
			+" "+title+" TEXT, "
			+" "+desc+" TEXT)";
	private String strQuery2 = "CREATE TABLE IF NOT EXISTS "+table2+" ( flag INTEGER )";
	
	//Following method get called only once when database is created.
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("OfflineData","onCreate called");
		
		db.execSQL(strQuery2);
		db.execSQL(strQuery);
		Log.d("OfflineData","Table Created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		if (oldVersion < newVersion) {
			//
			db.execSQL("DROP TABLE IF EXISTS "+table1);
			db.execSQL("DROP TABLE IF EXISTS "+table2);
			//
		}
		onCreate(db);
		
	}
}
