package com.herbfamily;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class HerbFamilyActivity extends ListActivity {
	
	private MemberListAdapter adapter;
	private ArrayList<Member> members;
	private MemberDatabase database;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		database = new MemberDatabase(this);
		members = database.getMembers();
		setupWidgets();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		database.close();
	}

	private void setupWidgets() {
		adapter = new MemberListAdapter(this);
		setListAdapter(adapter);
		Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
		buttonAdd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(HerbFamilyActivity.this, NewMemberListActivity.class);
				startActivity(intent);
			}
		});
		
		Button buttonAddSample = (Button) findViewById(R.id.buttonAddSample);
		buttonAddSample.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (-1 != database.addSampleMember("name", "nickname")) {
					members = database.getMembers();
					adapter.notifyDataSetChanged();
				}
			}
		});
	}

	private class MemberListAdapter extends ArrayAdapter<Member> {
		private Context context;

		public MemberListAdapter(Context context) {
			super(context, R.layout.member_list_row);
			this.context = context;
		}

		@Override
		public int getCount() {
			return members.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(R.layout.member_list_row, null);
				
				holder = new ViewHolder();
				holder.text = (TextView)convertView.findViewById(R.id.memberName);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			Member member = (Member)members.get(position);
			holder.text.setText(member.getName() + "(" + member.getNickname() + ")");
			
			return convertView;
		}
		
		private class ViewHolder {
			private TextView text;
		}
	}
}