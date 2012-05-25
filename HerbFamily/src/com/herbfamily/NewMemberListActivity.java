package com.herbfamily;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;

public class NewMemberListActivity extends Activity {
	
	private static final String TAG = NewMemberListActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_member_list);
		
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String [] projection = new String[] {
				ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME
		};
		
		String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '1'";
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
		Cursor cursor = managedQuery(uri, projection, selection, null, sortOrder);
		
		Log.e(TAG, "Start Up NewMemberListActivity");
		
		if (cursor.moveToFirst()) {
			do {
				int index = cursor.getColumnIndex("_id");
				Log.e(TAG, "index["+index+"]");
				long id = cursor.getLong(index);
				String name = cursor.getString(1);
				Log.e(TAG, id + ":" + name);
			}while (cursor.moveToNext());
			cursor.close();
		} else {
			Log.e(TAG, "Fail cursor to first");
		}
	}
}
