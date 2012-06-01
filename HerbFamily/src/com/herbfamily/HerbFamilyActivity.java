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
import android.widget.TextView;

public class HerbFamilyActivity extends ListActivity {
	
	private MemberListAdapter adapter;
	private ArrayList<Member> members;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	@Override
	public void onResume() {
		super.onResume();

		//TODO onResume 에서 계속해서 DB 를 읽게 되네, 별로 좋치 않다. - NewForm 에서 activity return 값을 reflash 해야 겠다.
		MemberDatabase database = new MemberDatabase(this);
		members = database.getMembers();
		database.close();
		setupWidgets();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
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
	}

	private class MemberListAdapter extends BaseAdapter {
		private Context context;

		public MemberListAdapter(Context context) {
			this.context = context;
		}

		public int getCount() {
			return members.size();
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