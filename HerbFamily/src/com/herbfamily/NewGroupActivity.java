package com.herbfamily;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
			MemberDatabase database = new MemberDatabase(this);
			EditText name = (EditText)findViewById(R.id.editTextGroupName);
			database.addGroup(name.getText().toString());
			database.close();
			break;
		case R.id.buttonCancelGroup:
			finish();
			break;
		}
	}
	
	
}