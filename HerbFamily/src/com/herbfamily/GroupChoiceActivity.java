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
import android.widget.TextView;

public class GroupChoiceActivity extends ListActivity {
	
	ArrayList<String>groups = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choice_group);
		
		MemberDatabase database = new MemberDatabase(this);
		groups = database.getGroups();
		if (groups.size() == 0) {
			
		}
		
		Button buttonClose = (Button)findViewById(R.id.buttonCloseChoiceGroup);
		buttonClose.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				finish();
			}
		});
		
		Button buttonChoiceGroup = (Button)findViewById(R.id.buttonChoiceGroup);
		if (groups.size() == 0) {
			// 선택하면 바로 적용하면 되지 않을까?
			buttonChoiceGroup.setEnabled(false);
		}
		buttonChoiceGroup.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//TODO 
				//choice group action
			}
		});
		
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
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(R.layout.group_row, null);
			}
			
			TextView groupName = (TextView)convertView.findViewById(R.id.textViewgroupName);
			groupName.setText((String)groups.get(position));
			
			return convertView;
			
		}
		
	}
}
