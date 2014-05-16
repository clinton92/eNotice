package com.example.enoticeapp;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OfflineData extends SQLiteOpenHelper {
	
	public static String database="enotice";
	public OfflineData(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
	}

	public static int database_version = 1;
	public static String table = "notices";
	public static String id = "id";
	public static String title = "title";
	public static String desc = "description";
	private String strQuery = "CREATE TABLE IF NOT EXISTS "+table+" ( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT,"
			+" "+title+" TEXT, "
			+" "+desc+" TEXT)";
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(strQuery);
		Log.d("table create","table created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		if (oldVersion < newVersion) {
			//
			db.execSQL("DROP IF EXISTS "+table);
			//
		}
		
	}
}
