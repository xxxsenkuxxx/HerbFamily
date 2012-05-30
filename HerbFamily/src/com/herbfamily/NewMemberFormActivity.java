package com.herbfamily;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * @author 근환
 */
public class NewMemberFormActivity extends Activity {
	
	private MemberDatabase memberDatabase = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_member_form);
		
		memberDatabase = new MemberDatabase(this);
		
		Button buttonSave = (Button)findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText name = (EditText)findViewById(R.id.editTextName);
				EditText nickname = (EditText)findViewById(R.id.editTextNickname);
				EditText phoneNumber = (EditText)findViewById(R.id.editTextPhoneNumber);
				
				memberDatabase.addMember(name.getText().toString(), 
						nickname.getText().toString(), 
						phoneNumber.getText().toString());
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		memberDatabase.close();
	}
	
}
