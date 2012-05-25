package com.herbfamily;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class HerbFamilyActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ListView listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(new MemberListAdapter(this));
		
		Button button = (Button)findViewById(R.id.buttonAdd);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(HerbFamilyActivity.this, NewMemberListActivity.class);
				startActivity(intent);
			}
		});
	}

	private class MemberListAdapter extends ArrayAdapter {
		private Context context;

		public MemberListAdapter(Context context) {
			super(context, R.layout.member_list_row);
			this.context = context;
		}

		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(R.layout.member_list_row, null);
			}
			return convertView;
		}

	}
}