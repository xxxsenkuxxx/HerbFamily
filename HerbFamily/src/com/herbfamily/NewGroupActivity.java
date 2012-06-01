package com.herbfamily;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewGroupActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_group);
		
		Button buttonAddGroup = (Button)findViewById(R.id.buttonAddNewGroup);
		buttonAddGroup.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			}
		});
		
		Button buttonGroupCancel = (Button)findViewById(R.id.buttonCancelGroup);
		buttonGroupCancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
}
