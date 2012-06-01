package com.herbfamily;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewGroupActivity extends Activity implements View.OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_group);
		
		((Button)findViewById(R.id.buttonAddNewGroup)).setOnClickListener(this);
		((Button)findViewById(R.id.buttonCancelGroup)).setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonAddNewGroup:
			break;
		case R.id.buttonCancelGroup:
			finish();
			break;
		}
	}
}