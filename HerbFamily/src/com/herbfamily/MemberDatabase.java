package com.herbfamily;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemberDatabase {
	
	private static final String DATABASE_NAME = "member.db";
	private static final String TABLE_NAME = "members";
	private static final int DATABASE_VERSION = 1;
	private DatabaseHelper databaseHelper;
	private SQLiteDatabase db;
	
	public MemberDatabase(Context context) {
		databaseHelper = new DatabaseHelper(context);
		db = databaseHelper.getWritableDatabase();
	}
	
	public void close() {
		databaseHelper.close();
	}
	
	public ArrayList<Member> getMembers() {
		ArrayList<Member> members = new ArrayList<Member>();
		
		Cursor cursor = db.query(TABLE_NAME, new String[]{"_id", "name", "nickname"}, null, null, null, null, null);
		if (cursor == null) {
			return members;
		}
		
		while (cursor.moveToNext()) {
			members.add(new Member(
					cursor.getInt(cursor.getColumnIndex("_id")), 
					cursor.getString(cursor.getColumnIndex("name")), 
					cursor.getString(cursor.getColumnIndex("nickname"))));
		}
		return members;
	}
	
	public long addSampleMember(String name, String nickname) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("nickname", nickname);
		return db.insert(TABLE_NAME, null, values);
	}
	
	public long addMember(String name, String nickname, String phoneNumber) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("nickname", nickname);
		values.put("phone_number", phoneNumber);
		return db.insert(TABLE_NAME, null, values);
	}

	private class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			createMemberTable(db);
		}

		private void createMemberTable(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
					"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
					"name TEXT NOT NULL," +
					"nickname TEXT NOT NULL DEFAULT ''," + 
					"phone_number TEXT NOT NULL DEFAULT '')");
		}
	
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (oldVersion != newVersion) {
	            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	            createMemberTable(db);
			}
		}
	}

	

	

}
