package com.herbfamily;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewGroupActivity extends ListActivity implements
		View.OnClickListener {

	private ArrayList<Group> groups;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_group);
		init();
		setupWidgets();
	}

	private void init() {
		MemberDatabase database = new MemberDatabase(this);
		groups = database.getGroups();
		database.close();
	}

	private void setupWidgets() {
		((Button) findViewById(R.id.buttonAddNewGroup))
				.setOnClickListener(this);
		((Button) findViewById(R.id.buttonCancelGroup))
				.setOnClickListener(this);

		setListAdapter(new GroupListAdapter(this));
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
				convertView = inflater.inflate(R.layout.contacts_row, null);
				
				holder = new ViewHolder();
				holder.groupNameTextView = (TextView)convertView.findViewById(R.id.contactName);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			Group group = (Group)groups.get(position);
			holder.groupNameTextView.setText(group.getName() + "(" + group.getMemberCount() + "명)");
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
			EditText name = (EditText) findViewById(R.id.editTextGroupName);
			database.addGroup(name.getText().toString());
			database.close();
			break;
		case R.id.buttonCancelGroup:
			finish();
			break;
		}
	}

}