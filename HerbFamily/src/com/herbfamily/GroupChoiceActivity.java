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
import android.widget.ListView;
import android.widget.TextView;

public class GroupChoiceActivity extends ListActivity {
	
	protected static final int REQUEST_CODE = 1;
	ArrayList<Group>groups = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choice_group);
		
		MemberDatabase database = new MemberDatabase(this);
		groups = database.getGroups();
		
		Button buttonClose = (Button)findViewById(R.id.buttonCloseChoiceGroup);
		buttonClose.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				finish();
			}
		});
		setListAdapter(new GroupListAdapter(this));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Group group = groups.get(position);
		
		Intent intent = new Intent();
		intent.putExtra("groupId", group.getId());
		intent.putExtra("groupName", group.getName());
		setResult(RESULT_OK, intent);
		finish();
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
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(R.layout.group_row, null);
			}
			
			TextView groupName = (TextView)convertView.findViewById(R.id.textViewgroupName);
			Group group = groups.get(position);
			groupName.setText(group.getName());
			
			return convertView;
		}
		
	}
}
