package com.herbfamily;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class HerbFamilyActivity extends ListActivity implements
		View.OnClickListener {

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

		// TODO onResume 에서 계속해서 DB 를 읽게 되네, 별로 좋치 않다. - NewForm 에서 activity
		// return 값을 reflash 해야 겠다.
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

		((Button) findViewById(R.id.buttonOpenMemberAddition))
				.setOnClickListener(this);
		((Button) findViewById(R.id.buttonOpenGroupAddidtion))
				.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonOpenMemberAddition:
			startActivity(new Intent(this, NewMemberListActivity.class));
			break;
		case R.id.buttonOpenGroupAddidtion:
			startActivity(new Intent(this, NewGroupActivity.class));
			break;
		}
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
				holder.text = (TextView) convertView.findViewById(R.id.memberName);
				holder.btn1 = (Button) convertView.findViewById(R.id.buttonCall);
				holder.btn2 = (Button) convertView.findViewById(R.id.buttonSMS);
				convertView.setTag(holder);
				
				holder.btn1.setOnClickListener(new Button.OnClickListener(){
					public void onClick(View v) {
							Intent call = new Intent(Intent.ACTION_CALL);
							call.setData(Uri.parse("tel:01076487749"));
							startActivity(call);
						}
					});
				
				holder.btn2.setOnClickListener(new Button.OnClickListener(){
					public void onClick(View v) {
						Uri smsUri = Uri.parse("tel:01076487749");  
						Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);  
						intent.putExtra("sms_body", "아놔 ~~  네 번호가 왜 안찍히냐고!!!!!!!!!!!");  
						intent.setType("vnd.android-dir/mms-sms");   
						startActivity(intent); 
						}

					});

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			

			Member member = (Member) members.get(position);
			holder.text.setText(member.getName() + "(" + member.getNickname()
					+ ")");
			
			return convertView;
		}

		private class ViewHolder {
			private TextView text;
			private Button btn1, btn2;
		}
	}

}