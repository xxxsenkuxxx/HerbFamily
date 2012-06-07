package com.herbfamily;


import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NewMemberListActivity extends ListActivity {
	
	private static final String TAG = NewMemberListActivity.class.getSimpleName();
	private ArrayList<Contact> contacts = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_member_list);
		
		//TODO 연락처에 추가된 사용자가 없으면 아무것도 나타나지 않는다. - 메시지 처리.
		contacts = getContacts();
		setListAdapter(new ContactsAdapter(this));
	}
	
	private ArrayList<Contact> getContacts() {
		
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		
		Cursor cursor = getContactsCursor();
		if (cursor == null) {
			return contacts;
		}
		
		while (cursor.moveToNext()) {
			int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
			String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			String phoneNumber = getPhoneNumber(contactId);
			contacts.add(new Contact(contactId, contactName, phoneNumber));
		}
		cursor.close();
		return contacts;
	}

	private String getPhoneNumber(int contactId) {
		String phoneNumber = "";
		Cursor phoneCursor = getPhoneCursor(contactId);
		if (phoneCursor != null) {
			if (phoneCursor.moveToNext()) {
				phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			}
			phoneCursor.close();
		}
		return phoneNumber;
	}
	
	private Cursor getPhoneCursor(int contactId) {
		String [] projection = new String[] {
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
				ContactsContract.CommonDataKinds.Phone.NUMBER
		};
		return managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
	}

	private Cursor getContactsCursor() {
		String [] projection = new String[] {
				ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.Contacts.HAS_PHONE_NUMBER
		};
		
		String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '1'";
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
		
		return managedQuery(ContactsContract.Contacts.CONTENT_URI, projection, selection, null, sortOrder);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, NewMemberFormActivity.class);
		Contact contact = (Contact)contacts.get(position);
		intent.putExtra("contactId", contact.getId());
		intent.putExtra("contactName", contact.getName());
		intent.putExtra("contactPhoneNumber", contact.getPhoneNumber());
		startActivity(intent);
	}

	private class ContactsAdapter extends BaseAdapter {
		private Context context = null;
		public ContactsAdapter(Context context) {
			this.context = context;
		}

		public int getCount() {
			return contacts.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(R.layout.contacts_row, null);
				
				holder = new ViewHolder();
				holder.contactName = (TextView)convertView.findViewById(R.id.contactName);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			Contact contact = (Contact)contacts.get(position);
			holder.contactName.setText(contact.getName() + "(" + contact.getPhoneNumber() + ")");
			return convertView;
		}
		
		private class ViewHolder {
			private TextView contactName = null;
		}
	}
}
