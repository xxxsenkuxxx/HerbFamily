package com.herbfamily;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemberDatabase {
	
	private static final String DATABASE_NAME = "database.db";
	private static final String MEMBER_TABLE_NAME = "members";
	private static final String GROUP_TABLE_NAME = "groups";
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
		
		Cursor cursor = db.query(MEMBER_TABLE_NAME, new String[]{"_id", "name", "nickname"}, null, null, null, null, null);
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
	
	public ArrayList<String> getGroups() {
		ArrayList<String> groups = new ArrayList<String>();
		
		Cursor cursor = db.query(GROUP_TABLE_NAME, new String[]{"_id", "name"}, null, null, null, null, null);
		if (cursor == null) {
			return groups;
		}
		
		while (cursor.moveToNext()) {
			groups.add(cursor.getString(cursor.getColumnIndex("name")));
		}
		return groups;
	}
	
	public boolean addMember(String name, String nickname, String phoneNumber) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("nickname", nickname);
		values.put("phone_number", phoneNumber);
		return db.insert(MEMBER_TABLE_NAME, null, values) != -1;
	}
	
	public boolean addGroup(String name) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		return db.insert(GROUP_TABLE_NAME, null, values) != -1;
	}
	
	private class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			createMemberTable(db);
			createGroupTable(db);
		}
		
		private void createMemberTable(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + MEMBER_TABLE_NAME + " (" +
					"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
					"group_id INTEGER NOT NULL DEFAULT 0," +
					"name TEXT NOT NULL," +
					"nickname TEXT NOT NULL DEFAULT ''," + 
					"phone_number TEXT NOT NULL DEFAULT '')");
		}

		private void createGroupTable(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + GROUP_TABLE_NAME + " (" +
					"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
					"name TEXT NOT NULL DEFAULT '')");
		}
	
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (oldVersion != newVersion) {
	            db.execSQL("DROP TABLE IF EXISTS " + MEMBER_TABLE_NAME);
	            db.execSQL("DROP TABLE IF EXISTS " + GROUP_TABLE_NAME);
	            
	            createMemberTable(db);
	            createGroupTable(db);
			}
		}
	}

}
