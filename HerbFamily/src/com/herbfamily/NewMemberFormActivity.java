package com.herbfamily;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewMemberFormActivity extends Activity {
	
	private MemberDatabase memberDatabase = null;
	private Contact contact = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_member_form);
		
		int contactId = getIntent().getIntExtra("contactId", 0);
		if (contactId == 0) {
			finish();
		}
		
		contact = getContact(contactId);
		if (contact == null) {
			finish();
		}
		setupWidgets();
	}

	private Contact getContact(int contactId) {
		Contact contact = null;
		Cursor cursor = getContactCursor(contactId);
		if (cursor == null) {
			return contact;
		}
		
		if (cursor.moveToFirst()) {
			int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
			String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			contact = new Contact(id, name);
		}
		cursor.close();
		return contact;
	}

	private Cursor getContactCursor(int contactId) {
		String [] projection = new String[] {
				ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME,
		};
		String selection = ContactsContract.Contacts._ID + " = " + contactId;
		
		return managedQuery(ContactsContract.Contacts.CONTENT_URI, projection, selection, null, null);
	}
	
	private void setupWidgets() {
		
		TextView name = (TextView)findViewById(R.id.editTextName);
		name.setText(contact.getName());
		
		Button buttonSave = (Button)findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText name = (EditText)findViewById(R.id.editTextName);
				EditText nickname = (EditText)findViewById(R.id.editTextNickname);
				EditText phoneNumber = (EditText)findViewById(R.id.editTextPhoneNumber);
				
				memberDatabase = new MemberDatabase(NewMemberFormActivity.this);
				memberDatabase.addMember(name.getText().toString(), 
						nickname.getText().toString(), 
						phoneNumber.getText().toString());
				memberDatabase.close();
				finish();
			}
		});
		
		Button buttonCancel = (Button)findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
}
