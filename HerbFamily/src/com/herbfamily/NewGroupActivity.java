package com.herbfamily;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewGroupActivity extends ListActivity implements View.OnClickListener {

	private ArrayList<Group> groups;
	private static final String TAG = NewGroupActivity.class.getSimpleName();
	private GroupListAdapter groupListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_group);
		
		updateGroupList();
		setupWidgets();
	}

	//TODO updateGroupList 에서 groups 를 다시 가져오게 되고 화면 갱신은 하지 않게 되어 있다.
	private void updateGroupList() {
		MemberDatabase database = new MemberDatabase(this);
		groups = database.getGroups();
		database.close();
	}

	
	private void setupWidgets() {
		((Button) findViewById(R.id.buttonAddNewGroup)).setOnClickListener(this);
		
		groupListAdapter = new GroupListAdapter(this); 
		setListAdapter(groupListAdapter);
	}

	private class GroupListAdapter extends BaseAdapter {

		private Context context;

		public GroupListAdapter(Context context) {
			this.context = context;
		}

		public int getCount() {
			return groups.size();
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
				convertView = inflater.inflate(R.layout.add_group_row, null);
				
				holder = new ViewHolder();
				holder.groupNameTextView = (TextView)convertView.findViewById(R.id.groupNameTextView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			Group group = (Group)groups.get(position);
			holder.groupNameTextView.setText(group.getName() + "(" + group.getMemberCount() + ")");
			return convertView;
		}
		
		private class ViewHolder {
			private TextView groupNameTextView;
		}
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonAddNewGroup:
			MemberDatabase database = new MemberDatabase(this);
			String name = ((EditText)findViewById(R.id.editTextGroupName)).getText().toString();
			
			if (name.trim().equals("")) {
				Toast.makeText(this, "그룹 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
			} else if (database.existedGroupName(name)) {
				Toast.makeText(this, "사용중인 이름입니다.", Toast.LENGTH_SHORT).show();
			} else {
				if (database.addGroup(name)) {
					updateGroupList();
					groupListAdapter.notifyDataSetChanged();
				}
			}
			database.close();
			break;
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, MembersInGroupActivity.class);
//		Intent intent = new Intent(this, NewMemberFormActivity.class);
//		Contact contact = (Contact)contacts.get(position);
//		intent.putExtra("contactId", contact.getId());
//		intent.putExtra("contactName", contact.getName());
//		intent.putExtra("contactPhoneNumber", contact.getPhoneNumber());
//		startActivity(intent);
	}
}